package dg.sample

import java.util.logging.Logger

class FileLoader {

    val logger:Logger = Logger.getLogger(javaClass.name)

    fun readFile() {
        logger.info("in FileLoader ... ")
    }
}