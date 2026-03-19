package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class LoginPage {

	WebDriver driver;   // ← stores driver passed from test

    // Constructor receives driver ✅
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // ← initializes @FindBy
    }

    @FindBy(xpath = "//i[contains(@class,'lp--user')]")
    WebElement UserIcon;

    @FindBy(xpath = "//*[@id='mobileNo']")
    WebElement MobileNo;
    
    @FindBy(xpath = "//*[@type='submit']")
    WebElement Continue;
    

    @FindBy(xpath = "//*[@name='otp1']")
    WebElement Password;

    @FindBy(xpath = "//*[@type='submit']")
    WebElement LoginBtn;

    public void ClickOnUserIcon() {
    	Actions action=new Actions(driver);
		action.moveToElement(UserIcon);
		action.click(UserIcon).build().perform();
    
    }

    public void Enter_Mobile_No(String mobile) {
        MobileNo.sendKeys(mobile);
    }

    public void clickonContinue()
    {
    	Continue.click();
    }
    
    public void Enter_Password(long pwd) {
        Password.sendKeys(String.valueOf(pwd));
    }

    public void clickLogin1() {
        LoginBtn.click();
    }
}
