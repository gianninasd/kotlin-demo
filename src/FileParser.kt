import dg.sample.PaymentRecord
import java.io.File

/**
 * Where application starts
 */
fun main( args:Array<String> ) {
    println("////////// SAMPLE 2 //////////")

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