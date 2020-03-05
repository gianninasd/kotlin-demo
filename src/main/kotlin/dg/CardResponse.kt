package dg

/**
 * Holds data for card response
 */
data class CardResponse(var recordId:Int = 0, var decision:String = "", var guid:String = "") {
  var ref = ""
  var txnId = ""
  var status = ""
  var errorCode = ""
  var message = ""
  var modificationDate = ""

  override fun toString():String {
    if( decision == "SUCCESS" )
      return "$guid SUCCESS id: $txnId"
    else
      return "$guid FAILED id: $txnId Error code: $errorCode - $message"
  }
}