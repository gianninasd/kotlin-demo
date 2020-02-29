package dg

import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

const val ALGO = "AES"
const val TRANSFORM_ALGO = "AES/CBC/PKCS5Padding"
const val MD_ALGO = "SHA-1"

/**
 * Utils to encrypt/decrypt a string value
 * Note: all toByteArray() methods are using default UTF-8 encoding
 */
class CryptoUtil(private val secretKey:String) {

  // transforms the secret key into bytes for future operations
  private fun prepareSecret( secret:String ):ByteArray {
    var secretAsBytes = secret.toByteArray()
    val sha = MessageDigest.getInstance(MD_ALGO)
    secretAsBytes = sha.digest(secretAsBytes)
    return secretAsBytes.copyOf(16) // only first 16 bytes since its AES w/ CBC
  }

  // encrypts the given value, returning a scrambled string
  fun encrypt( value:String ):String {
    val secretAsBytes = prepareSecret(this.secretKey)
    val ss = SecretKeySpec(secretAsBytes, ALGO)
    val ivspec = IvParameterSpec(secretAsBytes)

    val cipher = Cipher.getInstance(TRANSFORM_ALGO)
    cipher.init(Cipher.ENCRYPT_MODE, ss, ivspec)
    val cipherBytes = cipher.doFinal(value.toByteArray())
    return Base64.getEncoder().encodeToString(cipherBytes)
  }

  /**
   * Decrypts the given value, returning the original string
   */
  fun decrypt( value:String ):String {
    val secretAsBytes = prepareSecret(this.secretKey)
    val ss = SecretKeySpec(secretAsBytes, ALGO)
    val ivspec = IvParameterSpec(secretAsBytes)

    val cipher = Cipher.getInstance(TRANSFORM_ALGO)
    cipher.init(Cipher.DECRYPT_MODE, ss, ivspec)
    val bytes = cipher.doFinal(Base64.getDecoder().decode(value))
    return String(bytes)
  }
}