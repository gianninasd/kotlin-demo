package dg

import java.lang.Exception

// Exception thrown when the secret key is not an environment variable
class SecretKeyNotFoundException(message: String?) : Exception(message) {
}