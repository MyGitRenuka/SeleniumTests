package com.selTestng.handlers;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selTestng.utility.Log;
import com.selTestng.validations.Verifier;

public class FrameHandler {
	
	private WebDriver driver;
	private Logger log = Log.getLogger(WindowHandler.class);

	public FrameHandler(WebDriver driver) {
		this.driver = driver;
	}
	
	public void switchtoFrameByIndex(int index) {		
		try{
			driver.switchTo().frame(index);
		}
		catch(NoSuchFrameException e)
		{
			log.info(e.getCause());
			Verifier.verifyTrue(false, "Switched to frame by index " + index);
		}
	}
	
	public void switchtoFrameByName(String name) {		
		try{
			driver.switchTo().frame(name);
		}
		catch(NoSuchFrameException e)
		{
			log.info(e.getCause());
			Verifier.verifyTrue(false, "Switched to frame by name " + name);
		}
	}
	
	public void switchtoFrameByElement(WebElement element) {		
		try{
			driver.switchTo().frame(element);
		}
		catch(NoSuchFrameException e)
		{
			log.info(e.getCause());
			Verifier.verifyTrue(false, "Switched to frame by element " + element);
		}
		catch(StaleElementReferenceException e)
		{
			log.info(e.getCause());
			Verifier.verifyTrue(false, "Switched to frame by element " + element);
		}
	}

}
