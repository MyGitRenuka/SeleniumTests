package com.selTestng.handlers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selTestng.utility.Log;



public class ConditionalWaitHandler {
	
	private Logger log = Log.getLogger(ConditionalWaitHandler.class);
	private WebDriverWait webdriverWait;
	private long timeout;
	
	public ConditionalWaitHandler(WebDriverWait webdriverWait,long timeout) {
		this.webdriverWait=webdriverWait;
		this.timeout = timeout;
	}
	
	public void waitForElementVisibility(WebElement element,String elementName) {
		log.info("Waiting for visibility of element " + elementName + " for [" + timeout + "] seconds");		
		webdriverWait.until(ExpectedConditions.visibilityOf(element));						
	}
	
	public void waitUntilElementClickable(WebElement element,String elementName) {
		log.info("Waiting for " + elementName + " to be clickable with timeout [" + timeout + "] seconds");
		webdriverWait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
}
