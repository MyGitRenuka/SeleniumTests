package com.selTestng.utility;

import java.io.FileReader;
import java.util.Properties;

/**
 * @author Renuka R Hosamani
 *
 * 
 */
public class Config {
	
	public static Properties testConfig;
	
	public static void setProperties() {
		FileReader reader;
		try {
			reader = new FileReader("src/test/resources/config/testConfig.properties");
			testConfig = new Properties();
			testConfig.load(reader);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}
	
	
}
