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
		fun loadProperties(): Properties {
			val properties = Properties()
			properties.load(ClassLoader.getSystemResourceAsStream("config.properties"))
			return properties
		}
	}
}