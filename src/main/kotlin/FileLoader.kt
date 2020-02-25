import dg.ConfigUtils
import dg.SecretKeyNotFoundException
import java.util.logging.Logger

/**
 * Groovy script for reading a CSV file and storing data in a database
 */
fun main( args:Array<String> ) {
	val logger:Logger = Logger.getLogger("fileLoader")
	val osName = System.getProperty("os.name")
	val osVersion  = System.getProperty("os.version")

	logger.info("Kotlin ${KotlinVersion.CURRENT} File Loader running on $osName $osVersion")

	// TODO get config filename from args

	// load application config
	val config = ConfigUtils.loadProperties()

	try {
		val secretKey = System.getenv("DG_SECRET_KEY") ?: throw SecretKeyNotFoundException("")

		var cnt = 0
		val workingDir = config.getProperty("client.workingDir")

		logger.info("Processing files in $workingDir")
	}
	catch (ex:SecretKeyNotFoundException) {
		logger.severe("Encryption key not found in environment variable DG_SECRET_KEY")
	}
	catch (ex:Exception) {
		logger.severe("Unknown error occured!? $ex")
	}
}