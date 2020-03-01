import dg.ConfigUtils
import dg.DupeFileException
import dg.FileService
import dg.SecretKeyNotFoundException
import java.io.File
import java.io.FileNotFoundException
import java.time.Duration
import java.time.LocalDateTime
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Kotlin script for reading a CSV file and storing data in a database
 */
fun main() {
  val logger:Logger = Logger.getLogger("fileLoader")
  val osName = System.getProperty("os.name")
  val osVersion  = System.getProperty("os.version")

	logger.info("Kotlin ${KotlinVersion.CURRENT} File Loader running on $osName ($osVersion)")

	// load application config
	val config = ConfigUtils.loadProperties("config.properties")

	try {
		val secretKey = System.getenv("DG_SECRET_KEY") ?: throw SecretKeyNotFoundException("")

		var cnt:Int
		val workingDir = config.getProperty("client.workingDir")
		val service = FileService(secretKey, config)

		// TODO add infinite loop
		logger.info("Processing files in $workingDir")

		File(workingDir).walk().forEach { file ->
			if(file.isFile) {
				val fullFileName = file.name
				var fileName = ""

				try {
					fileName = service.extractFileName(fullFileName)
					val fileId = service.create(workingDir, fullFileName)

					logger.info("Processing [$fullFileName] records with file id $fileId")
					val startTime = LocalDateTime.now()
					cnt = 0

					// loop thru each line in file, ignoring lines starting with a pound character
					file.forEachLine {
						if( !it.startsWith("#") ) {
							service.storeRecord( fileId, it )
							cnt++
						}
					}

					val endTime = LocalDateTime.now()
					val duration = Duration.between(endTime, startTime)
					logger.info("Finished storing $cnt records for file id $fileId in $duration")
					service.createAck(workingDir,fileName,"0","File received")
				}
				catch( ex:FileNotFoundException) {
					logger.warning("File [$fullFileName] not found")
				}
				catch( ex:DupeFileException ) {
					logger.warning("File [$fullFileName] already uploaded in the last 24 hrs")
					service.createAck(workingDir,fileName,"-1","Duplicate file")
				}
				catch( ex:Exception ) {
					logger.severe("Unknown error occurred!? $ex")
					service.createAck(workingDir,fileName,"-99","Unknown error")
				}
			}
		}
	}
	catch (ex:SecretKeyNotFoundException) {
		logger.severe("Encryption key not found in environment variable DG_SECRET_KEY")
	}
	catch (ex:Exception) {
		logger.severe("Unknown error occurred!? $ex")
		logger.log( Level.SEVERE,"Unknown error occurred!?2", ex)
		ex.printStackTrace()
	}
}