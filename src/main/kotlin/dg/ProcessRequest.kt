package dg

import dg.dao.RecordDAO
import java.util.*
import java.util.concurrent.Callable

/**
 * Class that will execute within a thread pool and calls a remote API
 */
class ProcessRequest(
  val recordDAO:RecordDAO,
  val config:Properties,
  val recordId:Int,
  val line:String):Callable<CardResponse> {

  override fun call():CardResponse {
    val guid = UUID.randomUUID().toString()
    //def client = new CardClient( cardUrl: config.url, apiKey: config.apiKey )

    /*try {
      def parser = new LineParser()
      def lineReq = parser.parse(recordId, line)
      lineReq.guid = guid
      lineReq.ref = guid // we do this to make sure records work due to test data, not needed in PROD

      logger.info "Sending reference ${lineReq.ref} with amount ${lineReq.amount}"
      recordDAO.updateSent(lineReq)

      return client.purchase( lineReq )
    }
    catch( Exception ex ) {
      logger.warn "$guid with $recordId - Line processing failed: $ex"
      def resp = new CardResponse(recordId, 'ERROR', guid)
      resp.message = ex.getMessage()
      return resp
    }*/
    return CardResponse()
  }
}