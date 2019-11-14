package dg

/**
 * Holds data for a card request
 */
data class CardRequest(var merchantRefNum:String) {

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
}