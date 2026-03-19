package baseData;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseData {
	
	 public WebDriver driver;

	    @BeforeClass
	    public void OpenBrowser() {

	        // ✅ Chrome Options — Required for Jenkins
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("--window-size=1920,1080"); // ✅ Fixed size
	        options.addArguments("--disable-gpu");
	        options.addArguments("--no-sandbox");            // ✅ Jenkins fix
	        options.addArguments("--disable-dev-shm-usage");// ✅ Jenkins fix
	        options.addArguments("--disable-extensions");
	        options.addArguments("--remote-allow-origins=*");

	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver(options);

	        // ✅ Maximize window
	        driver.manage().window().maximize();

	        // ✅ Increase timeouts for Jenkins
	        driver.manage().timeouts()
	            .implicitlyWait(Duration.ofSeconds(20));
	        driver.manage().timeouts()
	            .pageLoadTimeout(Duration.ofSeconds(60));

	        driver.navigate().to("https://uatui.luxepolis.com");

	        // ✅ Wait for page to fully load
	        WebDriverWait wait = new WebDriverWait(driver,
	            Duration.ofSeconds(30));
	        wait.until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                return ((JavascriptExecutor) d)
	                    .executeScript("return document.readyState")
	                    .equals("complete");
	            }
	        });

	        System.out.println("Browser Opened Successfully ✅");
	    }

	    @AfterClass
	    public void CloseBrowser() {
	        if (driver != null) {
	            driver.quit();
	            System.out.println("Browser Closed Successfully ✅");
	        }
	    }
	
    }
