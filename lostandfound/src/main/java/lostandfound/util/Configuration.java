package lostandfound.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public final class Configuration {
	private static final Configuration INSTANCE = new Configuration();
	private Properties props;
	
	private Configuration() {
		this.props = loadProperties();
	}
	
	public static void initialize() {}
	
	/**
	 * Returns instance of the Configuration object.
	 * @return Configuration object
	 */
	public static Configuration getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Searches for the property with the specified key in this property list.
	 * Returns null if the property is not found.
	 * @param key Property key to search for
	 * @return Value of the property specified
	 */
	public String getProperty( String key ) {
		return this.props.getProperty( key );
	}
	
	/**
	 * Searches for the property with the specified key in this property list.
	 * Returns null if the property is not found.
	 * @param key Property key to search for
	 * @param defaultVaue Value to return if the key is not found
	 * @return Value of the property specified
	 */
	public String getProperty( String key, String defaultValue ) {
		return this.props.getProperty( key, defaultValue );
	}
	
	/**
	 * Loads properties from configuration file either located at config.properties
	 * or defined by the PROP_FILE property on JVM startup.
	 * @return Properties object created from the properties file
	 */
	private static Properties loadProperties() {
		Properties props = new Properties();
		String propFile = System.getProperty( "PROP_FILE", "config.properties" );
		try ( FileInputStream inputStream = new FileInputStream( propFile ) ) {
			props.load( inputStream );
		} catch ( IOException e ) {
			throw new RuntimeException( "Unable to open properties file: " + e.getMessage() );
		}
		
		return props;
	}
}
