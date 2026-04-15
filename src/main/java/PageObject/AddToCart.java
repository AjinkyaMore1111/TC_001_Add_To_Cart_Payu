package PageObject;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.asserts.SoftAssert;


import Utilities.WaitUtilities;

public class AddToCart {

    WebDriver driver;
    WaitUtilities waitUtils;
    WebDriverWait wait;
    JavascriptExecutor js;

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtilities(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@type='button' and @aria-label='cart']")
    WebElement Carticon;

    @FindBy(xpath = "//*[contains(@class,'addToCart')]")
    WebElement AddToCart;

    @FindBy(xpath = "//*[text()='Grand Total']")
    WebElement scrolldown;

    @FindBy(xpath = "//button[contains(text(),'Pay Now')]")
    WebElement paynow;
    
	
    @FindBy(xpath = "//*[contains(text(),'Platform Fees')]/following-sibling::*")
    WebElement platformFees;

	 
    
    
    
	
	  public boolean VerifyPlatformFees() throws InterruptedException {
		  Thread.sleep(4000);
		    
		    String fees = platformFees.getText().trim();
		    
		    // Remove currency symbol if present e.g. "₹1,081" → "1,081"
		    fees = fees.replace("₹", "").trim();
		    
		    System.out.println("Actual Platform Fees: " + fees);
		    
		    // Assert with meaningful message
		   
		    
		    String Expected_fees="1,080";
		    System.out.println("Expected Plarform Fees"+Expected_fees);
		    
		    SoftAssert assert1=new SoftAssert();
		    assert1.assertEquals(fees, Expected_fees, 
			        "Platform Fees mismatch! Expected: "+Expected_fees+" | Actual: " + fees);
		    
		    if (fees.equals(Expected_fees)) {
		        System.out.println("Platform Fees: MATCH ✓");
		        return true;
		    } else {
		        System.out.println("Platform Fees: NOT MATCH ✗");
		        return false;
		    }
		    
		    
	  }
	 

    public void carticonClick() {
        waitUtils.waitForClickability(Carticon);
        js.executeScript("arguments[0].click();", Carticon);
    }

    public void ClickOnAddtocart() {
        waitUtils.waitForClickability(AddToCart);
        js.executeScript("arguments[0].click();", AddToCart);
    }

    public void checkout() {
        WebElement btn = waitUtils.fluentWaitForElement(
            By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]")
        );
        js.executeScript("arguments[0].click();", btn);
    }

    public void ScrollDownPage() {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("window.scrollBy(0,500)", "");
    }

    public void AddressCheckout() {
        By locator = By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]");
        waitUtils.fluentWaitForElement(locator);
        js.executeScript("arguments[0].click();", driver.findElement(locator));
    }

    public void ClickOnPayNow() {
        waitUtils.waitForClickability(paynow);
        js.executeScript("arguments[0].click();", paynow);
    }
}
