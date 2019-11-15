package dg

/**
 * Holds data for a card request
 */
data class CardRequest(var merchantRefNum:String) {

    @Transient
    var guid = "" // for internal use only and excluded from the json representation
    var amount = 0
    var settleWithAuth = true
    var card = Card()
    var billingDetails = BillingDetails("")

    inner class BillingDetails(var zip:String)

    inner class Card {
        var cardNum:String = ""
        var cardExpiry = CardExpiry("","")

        inner class CardExpiry(var month:String, var year:String)
    }

    companion object Factory {
        /**
         * Creates an instance of this class based on PaymentRecord
         */
        fun createFrom(record: PaymentRecord): CardRequest {
            var req = CardRequest(record.merchantRefNum)
            req.amount = record.amount.toInt()
            req.billingDetails.zip = record.postalCode
            req.card.cardNum = record.cardNumber
            req.card.cardExpiry.month = record.expiryMonth
            req.card.cardExpiry.year = record.expiryYear
            return req
        }
    }
}