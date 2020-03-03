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
}