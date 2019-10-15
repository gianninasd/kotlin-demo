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

    File(fileName).forEachLine {
        println("$it")
    }
}