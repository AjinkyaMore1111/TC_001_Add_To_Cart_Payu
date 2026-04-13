package PageObject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.WaitUtilities;

public class OnlinePayment {

	

	 WebDriver driver;
	    WaitUtilities waitUtils;
	    WebDriverWait wait;

	    public OnlinePayment(WebDriver driver) {
	        this.driver = driver;
	        this.waitUtils = new WaitUtilities(driver);
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        PageFactory.initElements(driver, this);
	    }
	    
	  //Payment Online
	    
	    
	    @FindBy(xpath="//*[text()='Net Banking']")
	    WebElement Netbanking;
	    
	    @FindBy(xpath="//*[@id=\"net-banking-list-TESTPGNB-pop\"]/section[1]/div/div[1]/span/span")
	    WebElement TestBank;
	    
	    @FindBy(xpath="//li[@id='net-banking-list-TESTPGNB-pop']//button[@type='button'][normalize-space()='PROCEED']")
	    WebElement Proceed;
	    
	    
	    public void clickonNetbacnking()
	    {
	    	waitUtils.waitForVisibility(Netbanking);
	    	Actions action=new Actions(driver);
	    	action.moveToElement(Netbanking).click().build().perform();
	    	
	    }
	    
	    public void ClickOnTestBank()
	    {
	    	waitUtils.waitForClickability(TestBank).click();
	    }
	    
	    
	    public void ClickOnProceed()
	    {
	 	  Actions action=new Actions(driver);
	 	  action.moveToElement(Proceed).click().build().perform();
	    }
	
}
