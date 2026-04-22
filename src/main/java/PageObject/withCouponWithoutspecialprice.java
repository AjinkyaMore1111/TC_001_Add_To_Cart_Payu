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

public class withCouponWithoutspecialprice {

	WebDriver driver;
	WaitUtilities waitUtils;
	
	
	
	public withCouponWithoutspecialprice(WebDriver driver){
	this.driver = driver; //class variable assign to constructor
	 this.waitUtils = new WaitUtilities(driver);
	PageFactory.initElements(driver, this);
	}
	
   @FindBy(xpath = "//button[text()='Apply Coupon']")
   WebElement AppyCouponButton;
   
   @FindBy(xpath = "(//button[text()='Apply'])[5]")
   WebElement ApplyButton;
   
   @FindBy(xpath = "//span[contains(@class,'color--success') and contains(@class,'font--uppercase')]")
   WebElement couponCodeElement;
   
   @FindBy(xpath = "//span[starts-with(text (), 'Discount')]")
   WebElement OrderSummery;
   
   @FindBy(xpath  = "//span[@class = 'fs--20 font--bold font--family-Montserrat color--black white--space-nowrap']")
   WebElement getPrice;
   
   
   public void clickAppyCouponButton() {
	   
	   AppyCouponButton.click();
   }
   
   public void clickApplyButton() {
	   waitUtils.waitForVisibility(ApplyButton);
	   ApplyButton.click();
   }

   public String getCouponCode() {
	    String text = couponCodeElement.getText(); // "(Test10)"
	    String couponCode = text.replaceAll("[()]", "").trim(); // "Test10"
	    System.out.println(couponCode);
	    return couponCode;
	}
   
   public String getOrderSummerycode() {
	   String text = OrderSummery.getText(); // "Discount (Test 10%)"
	    String summarycouponCode = text.replaceAll("Discount", "")  // " (Test 10%)"
	                                   .replaceAll("[()%]", "")     // " Test 10 "
	                                   .replaceAll("\\s+", "")      // "Test10"
	                                   .trim();
	    String summarycouponCodes = summarycouponCode.replaceAll("[^0-9]", "");
	    System.out.println("coupon percentage: " + summarycouponCodes); // "Test10"
	    return summarycouponCode;
	    
   }
   public void compareCouponCodes() {
	    String couponCode = getCouponCode();             // "Test10"
	    String summaryCode = getOrderSummerycode();      // "TEST10"

	    System.out.println("Coupon Code: " + couponCode);
	    System.out.println("Summary Code: " + summaryCode);

	    boolean isMatch = couponCode.equalsIgnoreCase(summaryCode); // true
	    Assert.assertTrue(isMatch, "Coupon code mismatch!");
	    System.out.println("Coupon Code Matched Successfully!");
	    
	}
   
   public void priceValidation(ExcelUtils excel, int row) throws IOException {
	    String priceText = getPrice.getText();
	    priceText = priceText.replaceAll("[^0-9]", "");
	    int comparePrice = Integer.parseInt(priceText);

	    int hardcodedPrice = 29212;

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
