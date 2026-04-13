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

public class AddressDomInt {

    WebDriver driver;
    WaitUtilities waitutils;
    WebDriverWait wait;
    JavascriptExecutor js;

    public AddressDomInt(WebDriver driver) {
        this.driver = driver;
        this.waitutils = new WaitUtilities(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//label[@for='14726']")
    WebElement InternationalLabel;

    @FindBy(xpath = "//label[@for='14727']")
    WebElement DomesticLabel;

    public void clickOnInternational_Address() {
        waitutils.waitForPresence(By.xpath("//label[@for='14726']"));
        js.executeScript("arguments[0].click();", InternationalLabel);
    }

    public void ClickOnDomestic_Address() {
        // Wait for radio input to be present in DOM
        waitutils.waitForPresence(By.id("14727"));
        // Set checked and fire both click + change events so React state updates
        js.executeScript(
            "var el = document.getElementById('14727');" +
            "el.checked = true;" +
            "el.dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true}));" +
            "el.dispatchEvent(new Event('change', {bubbles: true}));"
        );
    }
    
    
}
