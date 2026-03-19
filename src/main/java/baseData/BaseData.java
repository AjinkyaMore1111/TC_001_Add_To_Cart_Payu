package baseData;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseData {
	
	
	
    public WebDriver driver;   // ← public so child class can access

    @BeforeClass
    public void OpenBrowser() {
        
        // Setup Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        
        // Maximize window
        driver.manage().window().maximize();
        
        // Open URL
        
        driver.navigate().to("https://uatui.luxepolis.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        System.out.println("Browser Opened Successfully ✅");
    }

   @AfterClass
    public void CloseBrowser() {
        if(driver != null) {
            driver.quit();
            System.out.println("Browser Closed Successfully ✅");
        }
    }
}
