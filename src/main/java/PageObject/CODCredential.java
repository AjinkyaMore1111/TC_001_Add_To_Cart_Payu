package PageObject;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utilities.WaitUtilities;

public class CODCredential {

    WebDriver driver;
    WaitUtilities waitutils;
    WebDriverWait wait;
    JavascriptExecutor js;

    public CODCredential(WebDriver driver) {
        this.driver = driver;
        this.waitutils = new WaitUtilities(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(@class,'payment--buttons') and contains(text(),'Place Order')]")
    WebElement PlaceOrder;

    public void clickonPlaceOrderButton() {
        waitutils.waitForClickability(PlaceOrder);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", PlaceOrder);
        js.executeScript("arguments[0].click();", PlaceOrder);
    }
}
