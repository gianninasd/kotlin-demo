package dg

/**
 * Service class for all file operations
 */
class FileService {

  val fileDAO = FileDAO()

  init {

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
    //val fileHash = calculateHash(workingDir, fileName)
    //val cnt = fileDAO.countInLast24hrs(fileHash)
    val fileId = fileDAO.create(fileName, "xx"/*fileHash*/)

    /*if( cnt > 0 )
      throw new DupeFileException()*/

    return fileId
  }
}