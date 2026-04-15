package baseData;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import Utilities.WaitUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseData {

    public WebDriver driver;
    public WaitUtilities waitUtils;

    @BeforeSuite
    public void OpenBrowser() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordCheck");
        options.addArguments("--password-store=basic");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("safebrowsing.enabled", false);
        prefs.put("safebrowsing.disable_download_protection", true);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-features=SafeBrowsingEnhancedProtection");
        options.addArguments("--disable-features=VaapiVideoDecoder");
        options.addArguments("--safebrowsing-disable-auto-update");
        options.addArguments("--disable-notifications");
        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");

        // Run headless on Linux server (no display), headed on Windows
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        // Bypass bot detection
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

        waitUtils = new WaitUtilities(driver);
        waitUtils.setImplicitWait(20);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        driver.navigate().to("https://uatui.luxepolis.com/");

        // Retry if application error detected
        String pageSource = driver.getPageSource();
        if (pageSource.contains("Application error") ||
            pageSource.contains("client-side exception")) {
            System.out.println("App error on load, retrying...");
            driver.navigate().refresh();
        }

        // Wait for full page load
        waitUtils.fluentWait(d -> ((JavascriptExecutor) d)
            .executeScript("return document.readyState")
            .equals("complete"));

        System.out.println("Browser Opened Successfully");
    }

    @BeforeClass
    public void login() throws InterruptedException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        System.out.println("[" + sdf.format(new Date()) + "] Clicking User Icon...");
        WebElement userIcon = waitUtils.waitForClickability(
            By.xpath("//i[@class='lp--user fs--22 undefined']")
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", userIcon);
        System.out.println("[" + sdf.format(new Date()) + "] User Icon Clicked");

        System.out.println("[" + sdf.format(new Date()) + "] Entering Mobile No...");
        waitUtils.waitForVisibility(By.xpath("//*[@id='mobileNo']"))
            .sendKeys("9960130048");
        System.out.println("[" + sdf.format(new Date()) + "] Mobile No Entered");

        System.out.println("[" + sdf.format(new Date()) + "] Clicking Continue...");
        waitUtils.waitForClickability(By.xpath("//*[@id='modal-btn']")).click();
        System.out.println("[" + sdf.format(new Date()) + "] Continue Clicked");

        System.out.println("[" + sdf.format(new Date()) + "] Waiting for OTP Field...");
        WebElement otpField = waitUtils.waitForVisibility(
            By.xpath("//input[@name='otp1']"), 30
        );

        System.out.println("[" + sdf.format(new Date()) + "] Entering OTP...");
        otpField.clear();
        otpField.sendKeys("746587");
        System.out.println("[" + sdf.format(new Date()) + "] OTP Entered");

        System.out.println("[" + sdf.format(new Date()) + "] Clicking Submit...");
        waitUtils.waitForClickability(By.xpath("//*[@type='submit']")).click();
        System.out.println("[" + sdf.format(new Date()) + "] Submit Clicked");

        System.out.println("[" + sdf.format(new Date()) + "] Verifying Login...");
        waitUtils.waitForInvisibility(By.xpath("//input[@name='otp1']"));
        System.out.println("[" + sdf.format(new Date()) + "] Login Successful");
    }

    @AfterClass
    public void CloseBrowser() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser Closed Successfully");
        }
    }
}
