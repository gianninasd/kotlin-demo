import dg.ConfigUtils
import java.util.logging.Logger

/**
 * Kotlin script for reading records from a database and calling an external REST API in a multi-threaded fashion
 */
fun main() {
  val logger:Logger = Logger.getLogger("fileLoader")
  val osName = System.getProperty("os.name")
  val osVersion = System.getProperty("os.version")

  logger.info("Kotlin ${KotlinVersion.CURRENT} File Processor running on $osName ($osVersion)")

  // load application config
  //val config = ConfigUtils.loadProperties("config.properties")
}