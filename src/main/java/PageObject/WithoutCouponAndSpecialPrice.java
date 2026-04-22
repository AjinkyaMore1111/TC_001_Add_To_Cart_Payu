package PageObject;

import java.io.IOException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utilities.ExcelUtils;
import Utilities.WaitUtilities;

public class WithoutCouponAndSpecialPrice {
	
	
	WebDriver driver;
	WaitUtilities waitUtils;
	
	public WithoutCouponAndSpecialPrice(WebDriver driver){
	this.driver = driver; //class variable assign to constructor
	 this.waitUtils = new WaitUtilities(driver);
	PageFactory.initElements(driver, this);
	}
	 
   
   @FindBy(xpath  = "//span[@class = 'fs--20 font--bold font--family-Montserrat color--black white--space-nowrap']")
   WebElement getPrice;
   
  
   public void priceValidation(ExcelUtils excel, int row) throws IOException {
	    String priceText = getPrice.getText();
	    priceText = priceText.replaceAll("[^0-9]", "");
	    int comparePrice = Integer.parseInt(priceText);

	    int hardcodedPrice = 32457;

	    if (comparePrice == hardcodedPrice) {
	        System.out.println("Price Matched Successfully! Expected: " + hardcodedPrice 
	                         + " | Actual: " + comparePrice + " → PASS");
	        excel.setCellValue(row, 8, "Pass");
	        excel.setCellColor(row, 8, IndexedColors.GREEN.getIndex());
	        excel.flush();
	    } else {
	        System.out.println("Price Mismatch! Expected: " + hardcodedPrice 
	                         + " | Actual: " + comparePrice + " → FAIL");
	        excel.setCellValue(row, 8, "Price Mismatch!");
	        excel.setCellColor(row, 8, IndexedColors.RED.getIndex());
	        excel.flush();
	        Assert.fail("Price validation failed! Expected: " + hardcodedPrice 
	                  + " | Actual: " + comparePrice);
	    }
	}
   



}
