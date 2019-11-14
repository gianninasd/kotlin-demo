package dg

import com.google.gson.Gson
import khttp.post
import java.lang.Exception
import java.util.*
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
        val gsonTranslator = Gson()
        val uri = "/cardpayments/v1/accounts/1001289630/auths"
        val headers = mapOf("Authorization" to "Basic $apiKey", "Content-Type" to "application/json")
        val guid = UUID.randomUUID().toString()

        val req = CardRequest(guid)
        req.amount = 1500
        req.card.cardNum = "4111111111111111"
        req.card.cardExpiry.month = "10"
        req.card.cardExpiry.year = "2021"
        req.billingDetails.zip = "H8P3S2"

        try {
            logger.info("Sending ${req.merchantRefNum}")
            // make request
            val res = post(url = url + uri, headers = headers, data = gsonTranslator.toJson(req))

            // process response
            if( res.statusCode == 200 ) {
                logger.info("Successful Response: ${res.statusCode} with message: ${res.text}")
            }
            else {
                logger.info("Failed Response: ${res.statusCode} with message: ${res.text}")
            }
        }
        catch ( ex:Exception ) {
            logger.severe("FAILED ${ex.toString()}")
        }
    }
}