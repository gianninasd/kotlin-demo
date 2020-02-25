package dg

/**
 * Service class for all file operations
 */
class FileService {

  /**
   * extracts only the filename without the extension
   */
  fun extractFileName( fullFileName:String ):String {
    val idx = fullFileName.indexOf(".csv")
    return fullFileName.substring(0, idx)
  }
}