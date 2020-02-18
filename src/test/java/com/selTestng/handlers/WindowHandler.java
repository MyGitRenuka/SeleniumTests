package com.selTestng.handlers;

import java.util.Set;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.selTestng.utility.Log;


public class WindowHandler {
	
	private WebDriver driver;
	private Logger log = Log.getLogger(WindowHandler.class);

	public WindowHandler(WebDriver driver) {
		this.driver = driver;
	}
	
	public void goToParentWindow() {
		log.info("Switching to parent window");
		driver.switchTo().defaultContent();
	}
	public Set<String> getAllWindows() {
		return driver.getWindowHandles();
	}

	public void goToWindow(String name) {
		driver.switchTo().window(name);
	}
	
	public String getCurrentWindow() {
		return driver.getWindowHandle();
	}
	
	public void goToChildWindow() {
		String mainWindow = getCurrentWindow();		
		for(String window: getAllWindows())
		{
			if(!window.equalsIgnoreCase(mainWindow))
			{
				goToWindow(window);
				log.info("Switched to child window");
			}
		}
		
	}
	
	public void goToChildWindowByIndex(int index) {
		int i=1;
		for(String window: getAllWindows())
		{
			if(i == index)
			{
				driver.switchTo().window(window);
				log.info("Switched to child window with index " + index);
			}
			else
				i++;
		}
	}
	
	public void closeAllAndGoToMainWindow() {
		String mainWindow = getCurrentWindow();		
		for(String window: getAllWindows())
		{
			if(!window.equalsIgnoreCase(mainWindow))
			{
				driver.close();
				log.info("Closed a child window");
			}
		}
		driver.switchTo().window(mainWindow);
		log.info("Switched to main window");
	}

}
