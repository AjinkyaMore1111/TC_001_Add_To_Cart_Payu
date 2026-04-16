package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utilities.WaitUtilities;

public class USD_WithoutCoupon {
	
	WebDriver driver;
	WaitUtilities waitUtils;
	
	public USD_WithoutCoupon(WebDriver driver){
	this.driver = driver; //class variable assign to constructor
	 this.waitUtils = new WaitUtilities(driver);
	PageFactory.initElements(driver, this);
	}
	 
   
   @FindBy(xpath  = "//span[@class = 'fs--20 font--bold font--family-Montserrat color--black white--space-nowrap']")
   WebElement getPrice;
   
  
   public void priceValidation_USD() {
	   
	    String priceText = getPrice.getText();
	    //String PriceTextRemoveSpace=priceText.trim();
	    priceText = priceText.replaceAll("[$]","").trim();
	    
	    
	    
	    int comparePrice = Integer.parseInt(priceText);

	    int hardcodedPrice = 441;

	    Assert.assertEquals(comparePrice, hardcodedPrice, "Price validation failed!");
	    System.out.println("Price matched: " + comparePrice);
	    
	}
   
	
  

}
