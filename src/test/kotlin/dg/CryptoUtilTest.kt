package dg

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Contains unit tests related to the CryptoUtils class
 */
class CryptoUtilTest {

  @Test
  fun testGood() {
    val secretKey = "pdb0rprC20AdctIqTVi0"
    val value = "jim7025,1500,373511000000005,10,2020,Rick,Hunter,rick@sdf3.com,M5H 2N6"
    val util = CryptoUtil(secretKey)
    val encryptedValue = util.encrypt(value)
    println("encryptedValue = $encryptedValue")
    assertNotNull(encryptedValue)

    val originalValue = util.decrypt(encryptedValue)
    println("originalValue = $originalValue")
    assertNotNull(originalValue)
    assertEquals(value, originalValue)
  }
}