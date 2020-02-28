package dg

import dg.dao.AbstractDAO

/**
 * DAO to interact with files
 */
class FileDAO:AbstractDAO() {

  /**
   * Create a new file record entry, returning the file ID
   */
  fun create( fileName:String, fileHash:String ):Int {
    val data:Array<Any> = arrayOf(fileName,fileHash)

    return insert("insert into file_uploads (filename,filehash,creation_date,modification_date) "
                  + "values (?,?,now(),now())", data)
  }
}
