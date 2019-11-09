import dg.sample.PaymentRecord
import java.io.File
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.logging.Logger

val logger:Logger = Logger.getLogger("fileParser")

/**
 * Where application starts
 */
fun main( args:Array<String> ) {
    val osName = System.getProperty("os.name")
    val osVersion  = System.getProperty("os.version")

    logger.info("Kotlin ${KotlinVersion.CURRENT} File Loader running on $osName $osVersion using JVM ${Runtime.version()}")

    // load application config
    val config = loadProperties()
    val workingDir = config.getProperty("client.workingDir")

    logger.info("Processing files in $workingDir")

    File(workingDir).walk().forEach {file ->
        if(file.isFile) {
            val fullFileName = file.name
            var cnt = 0
            val startTime = LocalDateTime.now()

            logger.info("Processing [$fullFileName] records")

            file.forEachLine {
                if (it.isNotEmpty()) {
                    val rec:PaymentRecord = PaymentRecord.parse(it)
                    println("rec: $rec")
                    cnt++
                }
            }

            val endTime = LocalDateTime.now()
            val duration = Duration.between(startTime, endTime)
            logger.info("Finished storing $cnt records in ${duration.seconds} seconds")
        }
    }
}

/**
 * Loads our configuration file from the classpath
 */
fun loadProperties():Properties {
    val properties = Properties()
    properties.load(ClassLoader.getSystemResourceAsStream("config.properties"))
    return properties
}