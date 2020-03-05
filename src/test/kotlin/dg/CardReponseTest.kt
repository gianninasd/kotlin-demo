package dg

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CardReponseTest {

  @Test
  fun test_ToString_Success() {
    val res = CardResponse(0,"SUCCESS","guid1234")
    res.txnId = "0011"
    assertEquals(res.toString(), "guid1234 SUCCESS id: 0011")
  }

  @Test
  fun test_ToString_Failed() {
    val res = CardResponse(0,"FAILED","guid1234")
    res.txnId = "0011"
    res.errorCode = "1007"
    res.message = "insufficient funds"
    assertEquals(res.toString(), "guid1234 FAILED id: 0011 Error code: 1007 - insufficient funds")
  }
}