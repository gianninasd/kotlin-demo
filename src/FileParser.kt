import dg.sample.FileLoader
import dg.sample.PaymentRecord
import java.io.File
import java.util.logging.Logger

val logger:Logger = Logger.getLogger("blue")

/**
 * Where application starts
 */
fun main( args:Array<String> ) {
    println("////////// SAMPLE 2 //////////")

    logger.info("Processing files in <XXX>")

    val fileLoader = FileLoader()
    fileLoader.readFile()

    if( args.size == 0 ) {
        println("Filename missing as argument");
    }
    else {
        processFile( args[0] );
    }

    println("//////////////////////////////")
}

/**
 * Reads the file provided and parses each record
 */
fun processFile( fileName:String ) {
    //logger.info("Parsing file: $fileName")
    println("Parsing file: $fileName");
    var totalRecords:Int = 0

    File(fileName).forEachLine {
        if (it.isNotEmpty()) {
            val rec:PaymentRecord = PaymentRecord.parse(it)
            println("rec: $rec")
            totalRecords++
        }
    }

    println("Total records parsed: $totalRecords");
}