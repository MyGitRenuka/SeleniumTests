package com.selTestng.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.selTestng.init.TestInit;
import com.selTestng.pages.HomePage;
import com.selTestng.pages.SignInPage;
import com.selTestng.utility.Config;
import com.selTestng.utility.Log;
import com.selTestng.validations.Verifier;

public class SignInTest extends TestInit {
	
	HomePage homePage;
	private static final Logger log = Log.getLogger(SignInTest.class);
	
	@Test(dataProvider="testdataProvider")
	public void verifySignIn(String username,Object password) {
		
		System.out.println("\n\nUSERNAME: " + username);
		System.out.println("PASSWORD: " + password);

		homePage = new HomePage(driver);
		SignInPage signInPage;
		
		getApplicationUrl(Config.testConfig.getProperty("url"));
		Verifier.verifyTrue(homePage.signInPresent(), "Sign in option present");
		log.info("Sign in option present");
		
		signInPage = homePage.goToSignInPage();
		log.info("Navigated to sign in page");
		
		signInPage.enterEmail(username);
		signInPage.continueToPassword();
		log.info("Email entered successfully");
		
		signInPage.enterPassword(password.toString());
		log.info("Password entered successfully");
		
		signInPage.submitSignIn();
		log.info("Sign in submitted successfully");
		
		signInPage.clickOnNavigationMenu();
		signInPage.setConditionalWait(setExplicitWait(driver),explicitWait);
		signInPage.waiForSignOutVisibility();
		signInPage.signOut();
		
	}

	@DataProvider(name="testdataProvider")
	public Object[][] getData(){
		
		return getDataFromExcel(Config.testConfig.getProperty("loginDataFile"), 
				Config.testConfig.getProperty("loginDataSheet"),Config.testConfig.getProperty("testCase"));
	}

	
}
