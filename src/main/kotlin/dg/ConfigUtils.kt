package dg

import java.util.*

/**
 * Contains utility methods to load the configuration file
 */
class ConfigUtils {

	companion object {
		/**
		 * Loads our configuration file from the classpath
		 */
		fun loadProperties( fileName:String ):Properties {
			val properties = Properties()
			properties.load(ClassLoader.getSystemResourceAsStream(fileName))
			return properties
		}
	}
}