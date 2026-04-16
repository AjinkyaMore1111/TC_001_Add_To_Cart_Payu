package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utilities.WaitUtilities;

public class ApplyCoupon {
	
	WebDriver driver;
	WaitUtilities waitUtils;
	
	public ApplyCoupon(WebDriver driver){
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
   
   @FindBy(xpath = "//span[@class='fs--16 font--bold font--family-Montserrat color--black white--space-nowrap']")
   WebElement CouponPercentage;
   
   @FindBy(xpath = "//span[@class='fs--16 font--bold font--family-Montserrat color--success white--space-nowrap']")
   WebElement DiscountPrice;
   
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
   
   public String getCouponPercentage() {
	    String couponCode = getOrderSummerycode();  // "TEST10"
	    String summarycouponCodes = couponCode.replaceAll("[^0-9]", "");
	    int couponPercentage = Integer.parseInt(summarycouponCodes);  // 10
	    // Extract price from WebElement → "₹ 23,000" → "23000"
	    String priceText = CouponPercentage.getText();                        // "₹ 23,000"
	    int priceValue = Integer.parseInt(priceText.replaceAll("[^0-9]", "")); // 23000
	    // Calculate discount
	    int afterCouponValue = (priceValue * couponPercentage) / 100;  // 2300
	    System.out.println(afterCouponValue);
	    return String.valueOf(afterCouponValue);  // "2300"
	}
   
   public String getDiscountPrice() {
	   String discountText = DiscountPrice.getText();
	    String cleanValue = discountText.replaceAll("[^0-9,]", "").replace(",", "");
	    return cleanValue;     
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
   
   public void compareDiscountValue() {
	   String calclatedDiscount = getCouponPercentage();
	   String UiDiscount = getDiscountPrice();
	   boolean valuMatch  = calclatedDiscount.equals(UiDiscount);
	   Assert.assertTrue(valuMatch, "Coupon Discount mismatch");
	   System.out.println("Coupon value validate Successfully!");
			
	   
   }
   
}
