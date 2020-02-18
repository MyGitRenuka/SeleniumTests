package com.selTestng.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.selTestng.utility.Log;
import com.selTestng.validations.Verifier;

public class DropDownHandler {
	
	private WebDriver driver;
	private Logger log = Log.getLogger(DropDownHandler.class);
	
	public DropDownHandler(WebDriver driver){
		this.driver = driver;
		log.info("DropDownHandler initialised..");
	}
		
	public Select getDropDown(WebElement element)
	{
		return new Select(element);			
	}

	
	public boolean isDropdown(WebElement element){
		try{
			getDropDown(element);
			return true;
		}
		catch(UnexpectedTagNameException e){
			log.info("Not a dropdown");
			log.info(e.getCause());
			return false;
		}
	}
	
	public void selectUsingText(WebElement element,String visibleText,boolean stopOnFailure)
	{
		if(isDropdown(element)) {
			try{
				getDropDown(element).selectByVisibleText(visibleText);
			}
			catch(NoSuchElementException e) 
			{
				log.info(e.getCause());
				if(stopOnFailure)
					Verifier.verifyTrue(false, "Selected dropdown using text");		
			}	
		}		
	}
	
	public void selectValue(WebElement element,String value,boolean stopOnFailure)
	{
		if(isDropdown(element)) {
				try {getDropDown(element).selectByValue(value);
			}
			catch(NoSuchElementException e) {
				log.info(e.getCause());
				if(stopOnFailure)
					Verifier.verifyTrue(false, "Selected dropdown using value");			
			}	
		}
	}
	
	public void selectIndex(WebElement element,int index,boolean stopOnFailure)
	{
		if(isDropdown(element)) {
			try 
			{
				getDropDown(element).selectByIndex(index);
			}
			catch(NoSuchElementException e) {
				log.info(e.getCause());
				if(stopOnFailure)
					Verifier.verifyTrue(false, "Selected dropdown using index");				
			}
		}
	}
	
	
	public void deselectText(WebElement element,String visibleText,boolean stopOnFailure)
	{
		if(isDropdown(element)) {
			try
			{
				getDropDown(element).deselectByVisibleText(visibleText);
			}
			catch(Exception e) {
				log.info(e.getCause());
				if(stopOnFailure)
					Verifier.verifyTrue(false, "Deselected dropdown using text");		
			}
			
		}	
	}
	
	public void deselectValue(WebElement element,String value,boolean stopOnFailure)
	{
		if(isDropdown(element)) {
			try
			{
				getDropDown(element).deselectByValue(value);
			}
			catch(Exception e)
			{
				log.info(e.getCause());
				if(stopOnFailure)
					Verifier.verifyTrue(false, "Deselected dropdown using value");		
			}
		}	
	}
	
	public void deselectIndex(WebElement element,int index,boolean stopOnFailure)
	{
		if(isDropdown(element)) {
			try{
				getDropDown(element).deselectByIndex(index);
			}
			catch(Exception e)
			{
				log.info(e.getCause());
				if(stopOnFailure)
					Verifier.verifyTrue(false, "Deselected dropdown using index");		
			}
		}
	}

	public List<String> getDropdownOptions(WebElement element, boolean returnSortedList){
		List<String> options = new ArrayList<String>();
		if(isDropdown(element)) {
			for(WebElement option: getDropDown(element).getOptions())
			{
				options.add(option.getText());
			}
			if(returnSortedList)
				Collections.sort(options);
		}
		return options;		
	}

	public List<String> getSelectedOptions(WebElement element, boolean returnSortedList){
		List<String> options = new ArrayList<String>();
		if(isDropdown(element)) 
		{
			for(WebElement option: getDropDown(element).getAllSelectedOptions())
			{
				options.add(option.getText());
			}
			if(returnSortedList)
				Collections.sort(options);
		}
		return options;		
	}
}
