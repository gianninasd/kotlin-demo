package dg.sample

/**
 * Represents a payment record to process
 */
class PaymentRecord {

    var merchantRefNum: String? = null
    var amount: String? = null
    var cardNumber: String? = ""
    var expiryMonth: String? = null
    var expiryYear: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var postalCode: String? = null

    override fun toString(): String {
        return "PaymentRecord{merchantRefNum=$merchantRefNum, amount=$amount" +
                //", cardNumber=xxxx" + cardNumber?.substring(cardNumber.length.minus(4)) +
                ", email=$email" +
                ", postalCode=$postalCode}"
    }

    companion object Factory {
        fun parse( record:String ):PaymentRecord {
            var tokenPosition = 0
            val paymentRecord = PaymentRecord()

            val tokens = record.split(",")

            for (token in tokens) {
                when(tokenPosition) {
                    0 -> paymentRecord.merchantRefNum = token
                    1 -> paymentRecord.amount = token
                    2 -> paymentRecord.cardNumber = token
                    3 -> paymentRecord.expiryMonth = token
                    4 -> paymentRecord.expiryYear = token
                    5 -> paymentRecord.firstName = token
                    6 -> paymentRecord.lastName = token
                    7 -> paymentRecord.email = token
                    8 -> paymentRecord.postalCode = token
                }

                tokenPosition++
            }

            return paymentRecord
        }
    }
}