package dg

import java.lang.Exception

/**
 * Exception thrown when the file was previously uploaded in the last 24 hrs
 */
class DupeFileException(message: String?):Exception(message) {
}