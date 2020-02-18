package com.selTestng.utility;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Renuka R Hosamani
 *
 * 
 */
public class Log {
	
private static boolean flag=false;
	
	public static <T> Logger getLogger(Class<T> cls){
		if(flag){
			return Logger.getLogger(cls);
		}
		PropertyConfigurator.configure("log4j.properties");
		flag = true;
		return Log.getLogger(cls);
	}
	
}
