package PageObject;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utilities.WaitUtilities;

public class SearchProduct {

    WebDriver driver;
    WaitUtilities waitUtils;
    WebDriverWait wait;
    JavascriptExecutor js;

    public SearchProduct(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtilities(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@class='header--menu-search header--menu-search-icon position--relative pt--10 pb--10 "
            + "pl--15 fs--12  font--family-Montserrat font--normal color--grey-light font--left ']")
    WebElement Searchbox;

    @FindBy(xpath = "//input[@id='search']")
    WebElement Searchkeyword;

    @FindBy(xpath = "//div[contains(@class,'fullScreen header-search-modal')]//div[contains(@class,'bg--white')]//div[2]//ul[1]//li[1]//div[1]//a[1]")
    WebElement SearchResult;

    public void clickSearchbox() {
        waitUtils.waitForClickability(Searchbox);
        js.executeScript("arguments[0].click();", Searchbox);
    }

    public void EnterKeyword(String keyword) throws InterruptedException {
        WebElement input = waitUtils.fluentWaitForElement(By.id("search"));
        input.clear();
        input.sendKeys(keyword);
        // Wait for search results to appear instead of Thread.sleep
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class,'fullScreen header-search-modal')]//ul")
        ));
        input.sendKeys(Keys.SPACE);
        Thread.sleep(2000);
        input.sendKeys(Keys.SPACE);
        
    }

    public void ClickOnSearchResult() {
        waitUtils.waitForClickability(SearchResult);
        js.executeScript("arguments[0].click();", SearchResult);
    }
}
