package PageObject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.WaitUtilities;

public class PayuCredential {

	
	 WebDriver driver;
	    WaitUtilities waitUtils;
	    WebDriverWait wait;

	    public PayuCredential(WebDriver driver) {
	        this.driver = driver;
	        this.waitUtils = new WaitUtilities(driver);
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        PageFactory.initElements(driver, this);
	    }
	    
	    
	    //Online Credential
	    
	    @FindBy(xpath="//*[@id='username']")
	    WebElement payuUserName;
	     
	    @FindBy(xpath="//*[@id='password']")
	    WebElement PayuPassword; 
	    
	    @FindBy(xpath="//*[@id=\"CredForm\"]/input[2]")
	    WebElement ClickOnLogin;
	    
	    @FindBy(xpath="//*[@value='Simulate Success Response']")
	    WebElement Simulate_Success;
	    
	    
	    public void EnterPayu_UserName()
	    {
	 	   payuUserName.sendKeys("payu");
	    }
	                
	    public void EnterPayu_Password()
	    {
	 	   PayuPassword.sendKeys("payu");
	    }
	           
	    public void ClickOnLogin()
	    {
	 	   ClickOnLogin.click();
	    }
	    public void ClickOnSimlulateSubmit_BTN()
	    {
	 	Actions action=new Actions(driver);
	 	action.moveToElement(Simulate_Success).click().build().perform();
	 	}
}

