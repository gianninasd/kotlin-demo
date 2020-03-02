import dg.ConfigUtils
import dg.CryptoUtil
import dg.SecretKeyNotFoundException
import dg.dao.RecordDAO
import java.time.Duration
import java.time.LocalDateTime
import java.util.logging.Level
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
  val config = ConfigUtils.loadProperties("config.properties")

  try {
    val secretKey = System.getenv("DG_SECRET_KEY") ?: throw SecretKeyNotFoundException("")

    val recordDAO = RecordDAO(config)
    val crypto = CryptoUtil(secretKey)

    val startTime = LocalDateTime.now()

    // get next records to process and reset statistics
    val recs = recordDAO.getAllWithStatusInitial()
    logger.info("Processing ${recs.size} record(s)")
    var requestCnt = 0
    var successCnt = 0
    var failedCnt = 0
    var submitCnt = 0

    if( recs.size > 0 ) {
      for(rec in recs) {
        val line = crypto.decrypt(rec.rawData)
        //ecs.submit( new ProcessRequest(recordDAO, config.client, rec.recordId, line) )
        submitCnt++
      }
    }

    val endTime = LocalDateTime.now()
    val duration = Duration.between(endTime, startTime)
    logger.info("Processed $requestCnt record(s) in $duration - $successCnt succeeded, $failedCnt failed")
  }
  catch (ex:SecretKeyNotFoundException) {
    logger.severe("Encryption key not found in environment variable DG_SECRET_KEY")
  }
  catch (ex:Exception) {
    logger.log( Level.SEVERE,"Unknown error occurred!?", ex)
  }
}