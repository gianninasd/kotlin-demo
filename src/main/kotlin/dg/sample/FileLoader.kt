package dg.sample

import java.util.*
import java.util.logging.Logger

class FileLoader(val config:Properties) {

    val logger:Logger = Logger.getLogger(javaClass.name)

    fun readFile() {
        logger.info("in FileLoader ... $config")
    }
}