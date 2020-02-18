package com.selTestng.validations;

import org.apache.log4j.Logger;
import org.testng.Assert;

public class Verifier {
	
	private static Logger log = Logger.getLogger(Verifier.class);
			
	public static void verifyTrue(boolean condition, String conditionToBeVerified){
		Assert.assertTrue(condition,conditionToBeVerified);
	}
	
	public static void verifyFalse(boolean condition, String conditionToBeVerified){
		Assert.assertFalse(condition,conditionToBeVerified);
	}
}
