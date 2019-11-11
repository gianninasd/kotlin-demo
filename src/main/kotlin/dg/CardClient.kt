package dg

import khttp.post
import java.lang.Exception
import java.util.logging.Logger

/**
 * Client class used to call an external REST API for transaction processing
 */
class CardClient(val url:String, val apiKey:String) {

    private val logger: Logger = Logger.getLogger(javaClass.name)

    /**
     * ends a purchase request to a remote REST API
     */
    fun purchase() {
        val uri = "/cardpayments/v1/accounts/1001289630/auths"
        val headers = mapOf("Authorization" to "Basic $apiKey", "Content-Type" to "application/json")
        val payload = mapOf("some" to "data")

        try {
            // make request
            val req = post(url = url + uri, headers = headers, data = payload)

            // process response
            if( req.statusCode == 200 ) {

            }
            else {
                logger.info("Response: ${req.statusCode} with message: ${req.text}")
            }
        }
        catch ( ex:Exception ) {
            logger.severe("FAILED ${ex.toString()}")
        }
    }
}