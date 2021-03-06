package dg

import dg.dao.FileDAO
import dg.dao.RecordDAO
import java.io.File
import java.security.MessageDigest
import java.util.*

/**
 * Service class for all file operations
 */
class FileService(private val secretKey:String, config:Properties) {

  private val fileDAO = FileDAO(config)
  private val recordDAO = RecordDAO(config)

  /**
   * Generates an ack file in the folder specified
   */
  fun createAck( path:String, fileName:String, responseCode:String, responseMessage:String ) {
    File("$path/$fileName.ack.csv").writeText("ACK,$responseCode,$responseMessage")
  }

  /**
   * Extracts only the filename without the extension
   */
  fun extractFileName( fullFileName:String ):String {
    val idx = fullFileName.indexOf(".csv")
    return fullFileName.substring(0, idx)
  }

  /**
   * Creates a file record in the DB
   * Raises DupeFileException if file was previously uploaded in the last 24 hrs
   */
  fun create( workingDir:String, fileName:String ):Int {
    val fileHash = calculateHash(workingDir, fileName)
    val cnt = fileDAO.countInLast24hrs(fileHash)
    val fileId = fileDAO.create(fileName, fileHash)

    if( cnt > 0 )
      throw DupeFileException("")

    return fileId
  }

  /**
   * Store the raw record in encrypted format
   */
  fun storeRecord( fileId:Int, record:String ) {
    val crypto = CryptoUtil(this.secretKey)
    val token = crypto.encrypt(record)

    recordDAO.createInitial(fileId, token)
  }

  /**
   * Generates the hash value of the file contents
   */
  private fun calculateHash( workingDir:String, fileName:String ):String {
    val file = File("$workingDir/$fileName")
    val digest = MessageDigest.getInstance("SHA-1")
    val inputStream = file.inputStream()
    val buffer = ByteArray(16384)
    var len:Int

    while( true ) {
      len = inputStream.read(buffer)

      if( len <= 0 ) {
        break
      }

      digest.update(buffer, 0, len)
    }

    val sha1sum = digest.digest()
    var result = ""

    for( b in sha1sum) {
      result += toHex(b.toInt())
    }

    return result
  }

  private fun hexChr(b:Int):String {
    return Integer.toHexString(b and 0xF)
  }

  private fun toHex(b:Int):String {
    return hexChr((b and 0xF0) shr 4) + hexChr(b and 0x0F)
  }
}