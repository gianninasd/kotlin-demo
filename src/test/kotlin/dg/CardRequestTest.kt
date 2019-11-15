package dg

import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

/**
 * Contains unit tests related to the CardRequest class
 */
class CardRequestTest {

    private var rec = PaymentRecord()

    @BeforeEach
    fun initPaymentRecord() {
        rec.merchantRefNum = "test1"
        rec.amount = "1500"
        rec.cardNumber = ""
        rec.expiryMonth = "10"
        rec.expiryYear = "2020"
        rec.email = "john@boo.com"
        rec.firstName = "John"
        rec.lastName = "Doe"
        rec.postalCode = "H8P1K4"
    }

    @Test
    fun testGoodConversion() {
        val req = CardRequest.createFrom(rec)
        assertEquals(rec.merchantRefNum, req.merchantRefNum)
        assertEquals(rec.amount, req.amount.toString())
        assertEquals(rec.cardNumber, req.card.cardNum)
        assertEquals(rec.expiryMonth, req.card.cardExpiry.month)
        assertEquals(rec.expiryYear, req.card.cardExpiry.year)
        assertEquals(rec.postalCode, req.billingDetails.zip)
    }

    @Test
    fun testToString() {
        val req = CardRequest.createFrom(rec)
        assertEquals("CardRequest(merchantRefNum=test1)", req.toString())
    }
}