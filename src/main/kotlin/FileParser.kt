import dg.sample.FileLoader
import dg.sample.PaymentRecord
import java.io.File
import java.util.*
import java.util.logging.Logger

val logger:Logger = Logger.getLogger("blue")

/**
 * Where application starts
 */
fun main( args:Array<String> ) {
    val osName = System.getProperty("os.name")
    val osVersion  = System.getProperty("os.version")

    logger.info("Kotlin ${Runtime.version()} File Loader running on $osName $osVersion")

    // load application config
    val config = loadProperties()

    val fileLoader = FileLoader(config)
    fileLoader.readFile()

    if( args.size == 0 ) {
        println("Filename missing as argument")
    }
    else {
        processFile( args[0] )
    }

    println("//////////////////////////////")
}

/**
 * Loads our configuration file from the classpath
 */
fun loadProperties():Properties {
    val properties = Properties()
    properties.load(ClassLoader.getSystemResourceAsStream("config.properties"))
    val x = properties.getProperty("client.workingDir")
    logger.info("x is $x")
    return properties
}

/**
 * Reads the file provided and parses each record
 */
fun processFile( fileName:String ) {
    //logger.info("Parsing file: $fileName")
    println("Parsing file: $fileName")
    var totalRecords = 0

    File(fileName).forEachLine {
        if (it.isNotEmpty()) {
            val rec:PaymentRecord = PaymentRecord.parse(it)
            println("rec: $rec")
            totalRecords++
        }
    }

    println("Total records parsed: $totalRecords")
}