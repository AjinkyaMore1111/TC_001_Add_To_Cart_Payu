package PageObject;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utilities.WaitUtilities;

public class International_Address {

    WebDriver driver;
    WaitUtilities waitUtils;
    WebDriverWait wait;
    JavascriptExecutor js;

    public International_Address(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtilities(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@type='button' and @aria-label='cart']")
    WebElement Carticon;

    @FindBy(xpath = "//*[@class='addToCart']")
    WebElement AddToCart;
    
    @FindBy(xpath = "//*[@class = 'lp--caret-down fs--10 color--white']")
    WebElement convertUSD;
    
    @FindBy(xpath = "//span[contains(text(),'United States')]")
    WebElement selectUSD;
    
    @FindBy(xpath = "//label[contains(@for,'147')][1]")
    WebElement radiobutton;

    @FindBy(xpath = "//*[text()='Grand Total']")
    WebElement scrolldown;
       
    @FindBy(xpath = "//i[contains(@class,'lp--delete')]")
    WebElement delete;

    @FindBy(xpath = "//button[contains(text(),'Pay Now')]")
    WebElement paynow;

    public void ClickOnAddtocart() {
        waitUtils.waitForClickability(AddToCart);
        js.executeScript("arguments[0].click();", AddToCart);
    }

    public void carticonClick() {
        waitUtils.waitForClickability(Carticon);
        js.executeScript("arguments[0].click();", Carticon);
    }

    public void checkout() {
        WebElement btn = waitUtils.fluentWaitForElement(
                By.xpath("//*[contains(@class,'btn btn--background') and @type='button']")
        );
        js.executeScript("arguments[0].click();", btn);
    }

    public void ScrollDownPage() {
        js.executeScript("window.scrollBy(0,500)");
    }

    public void AddressCheckout() {
        WebElement btn = waitUtils.fluentWaitForElement(
                By.xpath("//*[contains(@class,'btn btn--background') and @type='button']")
        );
        js.executeScript("arguments[0].click();", btn);
    }

    public void ClickOnPayNow() {
        waitUtils.waitForClickability(paynow);
        js.executeScript("arguments[0].click();", paynow);
    }

    public void removeProductFromCart() {
    	waitUtils.waitForClickability(delete);
        js.executeScript("arguments[0].click();", delete);
		
	}
    
    public void radiobutton() {
    	waitUtils.waitForClickability(radiobutton);
        js.executeScript("arguments[0].click();", radiobutton);
    }
    public void convertToUSD () {
    	convertUSD.click();
     }
 	
     public void selectUnitedStates(){
             selectUSD.click();
     }
		
} 

