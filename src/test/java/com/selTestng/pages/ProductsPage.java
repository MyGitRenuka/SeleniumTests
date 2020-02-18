package com.selTestng.pages;

import java.util.List;
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
public class ProductsPage {
	
	private WebDriver driver;
	private static Logger log = Logger.getLogger(ProductsPage.class);

	public ProductsPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h2/a/span[@dir='auto']")
	public List<WebElement> productLinks;

	public List<WebElement> getProductLinks() {
		return productLinks;
	}
	
	public ProductPage goToProductPage(String product) {
				
		String message= "Product \"" +  product + " \" is in the search result"; 		
		if(isProductInList(product))
		{
			log.info(message);
			List<WebElement> list_of_prods = getProductLinks();		
			for(WebElement ele: list_of_prods)
			{
				if(product.equals(ele.getText())) {
					ele.click();
					break;
				}
			}
		}
		else
		{
			log.info("Verification failed for [" + message + "]");
			log.info(TestInit.captureScreen(driver, "ProductNotInList"));
			Verifier.verifyTrue(false, message);
		}		
		return new ProductPage(driver);
	}
	
	public boolean isProductInList(String product) {
		List<WebElement> list_of_prods = getProductLinks();
		for(WebElement ele: list_of_prods)
		{
			if(product.equals(ele.getText())) {
				return true;
			}
		}
		return false;		
	}
			
}
