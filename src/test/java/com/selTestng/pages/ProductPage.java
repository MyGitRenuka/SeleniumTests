package com.selTestng.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.selTestng.init.TestInit;
import com.selTestng.validations.Verifier;

/**
 * @author Renuka R Hosamani
 *
 * 
 */
public class ProductPage {
	
	private WebDriver driver;
	private static Logger log = Logger.getLogger(ProductPage.class);
			
	public WebDriver getDriver() {
		return this.driver;
	}
	public ProductPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@id='priceblock_ourprice' or @id='priceblock_saleprice']")
	public WebElement salePrice;
	
	@FindBy(xpath="//*[@id='centerCol']//*[@id='acrPopover']/span[1]/a/i[1]")
	public WebElement ratings;

	public WebElement getSalePrice() {
		return salePrice;
	}

	public WebElement getRatings() {
		return ratings;
	}
	
	public boolean priceDisplayed() {	
		return getSalePrice().isDisplayed();
	}
	
	public boolean reviewDisplayed() {
		return getRatings().isDisplayed();
	}
			
	public void verifyPrice(String price) {
		String message = "Actual and expected sale price are same";
		log.info("Expected price is " + price + " and actual price is " + getSalePrice().getText());		
		if(priceDisplayed() && price.equals(getSalePrice().getText())) {
			Verifier.verifyTrue(true, message);
		}
		else {
			log.info("Verification failed for [" + message + "]");
			log.info(TestInit.captureScreen(driver, "ProductPriceNotMatching"));
			Verifier.verifyTrue(false, message);
		}
	}
	
	public void verifyRating(String rating,String ratingFromJson) {
		String message = "Actual and expected review ratings are same";
		if(reviewDisplayed() && getRatings().getAttribute("textContent").contains(ratingFromJson)) {
			Verifier.verifyTrue(true, message);
		}
		else {
			log.info("Verification failed for [" + message + "]");
			log.info(TestInit.captureScreen(driver, "ReviewRatingNotMatching"));
			Verifier.verifyTrue(false, message);
		}
	}
	
}

