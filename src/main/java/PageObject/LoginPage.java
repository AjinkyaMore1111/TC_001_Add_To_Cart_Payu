package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.WaitUtilities;

public class LoginPage {

    WebDriver driver;
    WaitUtilities waitUtils;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtilities(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@aria-label='user']")
    WebElement UserIcon;

    @FindBy(xpath = "//*[@id='mobileNo']")
    WebElement MobileNo;

    @FindBy(xpath = "//*[@id='modal-btn']")
    WebElement Continue;

    @FindBy(xpath = "//*[@name='otp1']")
    WebElement Password;

    @FindBy(xpath = "//*[@type='submit']")
    WebElement LoginBtn;

    public void ClickOnUserIcon() throws InterruptedException {

        // Wait for element to be present in DOM
        waitUtils.waitForPresence(By.xpath("//i[contains(@class,'lp--user')]"));

        // Wait for element to be visible
        waitUtils.waitForVisibility(UserIcon);

        // Scroll to element
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", UserIcon);
        Thread.sleep(1000);

        // JS Click — more reliable than Actions in headless mode
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", UserIcon);

        System.out.println("User Icon Clicked");
    }

    public void Enter_Mobile_No(String mobile) {
        waitUtils.waitForVisibility(MobileNo);
        MobileNo.clear();
        MobileNo.sendKeys(mobile);
        System.out.println("Mobile No Entered");
    }

    public void clickonContinue() {
        waitUtils.waitForClickability(Continue);
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", Continue);
        System.out.println("Continue Clicked");
    }

    public void Enter_Password(long pwd) {
        waitUtils.waitForVisibility(Password);
        Password.clear();
        Password.sendKeys(String.valueOf(pwd));
        System.out.println("Password Entered");
    }

    public void clickLogin1() {
        waitUtils.waitForClickability(LoginBtn);
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", LoginBtn);
        System.out.println("Login Clicked");
    }
}
