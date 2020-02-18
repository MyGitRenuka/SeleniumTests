package com.selTestng.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selTestng.handlers.ConditionalWaitHandler;
import com.selTestng.validations.Verifier;

/**
 * @author Renuka R Hosamani
 *
 * 
 */
public class HomePage {
	
	private WebDriver driver;
	private ConditionalWaitHandler conditionalWaits;
	
	public WebDriver getDriver() {
		return this.driver;
	}

	public void getUrl(String url) {
		this.driver.get(url);
	}
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//*[@id='nav-link-accountList']/span[1]")
	public WebElement SignIn;
	
	@FindBy(xpath="//*[@id='twotabsearchtextbox']")
	public WebElement searchTextbox;
	
	@FindBy(xpath="//*[@value='Go']")
	public WebElement searchSubmit;
			
	
	public WebElement getSearchSubmit() {
		return searchSubmit;
	}

	public ProductsPage goToProductsPage() {
		getSearchSubmit().click();
		return new ProductsPage(driver);
	}
		
	public WebElement getSearchTextbox() {
		return searchTextbox;
	}

	public void enterSearchText(String searchFor) {
		getSearchTextbox().sendKeys(searchFor);
	}
	
	public WebElement getSignIn() {
		return SignIn;
	}
		
	public void setConditionalWait(WebDriverWait webdriverWait,long timeout) {
		conditionalWaits = new ConditionalWaitHandler(webdriverWait, timeout);
	}
	
	public void waitForSignInVisibility() {		
		String message="SIGN-IN BUTTON PRESENT";
		Verifier.verifyTrue(getSignIn().isDisplayed(), message);
	}
	
	public SignInPage goToSignInPage() {
		getSignIn().click();	
		return new SignInPage(driver);
	}
	
	public boolean signInPresent() {
		return getSignIn().isDisplayed();
	}

}
