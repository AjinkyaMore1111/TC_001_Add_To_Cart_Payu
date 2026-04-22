package PageObject;

import java.io.File;
import java.io.IOException;
import java.time.Duration;



import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import Utilities.ExcelUtils;
import Utilities.WaitUtilities;

public class AddToCart   {

    WebDriver driver;
    WaitUtilities waitUtils;
    WebDriverWait wait;
    JavascriptExecutor js;

    
    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtilities(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@type='button' and @aria-label='cart']")
    WebElement Carticon;

    @FindBy(xpath = "//*[@class='addToCart']")
    WebElement AddToCart;

    @FindBy(xpath = "//*[text()='Grand Total']")
    WebElement scrolldown;

    @FindBy(xpath = "//button[contains(text(),'Pay Now')]")
    WebElement paynow;
   
    @FindBy(xpath = "//*[@class = 'lp--caret-down fs--10 color--white']")
    WebElement convertUSD;
    
    @FindBy(xpath = "//span[contains(text(),'United States')]")
    WebElement selectUSD;
    
    @FindBy(xpath = "(//label[contains(@class,'cursor-pt')])[2]")
    WebElement radiobutton;

    //take xpath and stored platformfees using page factory//
    @FindBy(xpath = "//*[contains(text(),'Platform Fees')]/following-sibling::*")
    WebElement platformFees;
    
    static final String EXCEL_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "Test_cases" + File.separator + "LuxepolisTest_Cases.xlsx";
    
    // ===================== METHOD: VerifyPlatformFees =====================
    // PURPOSE: Get actual fees from Webpage, compare with expected from Excel
    //          Write Pass/Fail result back to Excel with Green/Red color
    public boolean VerifyPlatformFees(int rowValue, int cellValue, int sheetindex, String workbook) throws InterruptedException, IOException 
	  {
    	
        // STEP 1 - Open Excel file and go to correct sheet
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);     // open the Excel file
        excel.useSheet(sheetindex);                        //go to sheet tab by index
		
        // STEP 2 - Wait for page to fully load
        Thread.sleep(4000);                      //⏳ Wait 4 seconds for page to fully load
 
        // STEP 3 - Read ACTUAL fees from webpage
		  String fees = platformFees.getText().trim();   // getText() = reads visible text from platformFees element)(// trim() = removes leading/trailing spaces)																
		    
		// Remove currency symbol if present e.g. "₹200" → "200"//
		  fees = fees.replace("₹", "").trim();
		  System.out.println("Actual Platform Fees: " + fees);
		    
		  // Assert with meaningful message
		   String Expected_fees="200";
		    System.out.println("Expected Plarform Fees"+Expected_fees);
		   
		  System.out.println(rowValue+") fees :-"+fees+" Expected_fees :-"+Expected_fees);

		    //soft assert  means if result fail or pass it will not halt or stop ,is contiune  running //
		    SoftAssert assert1=new SoftAssert();
		    assert1.assertEquals(fees, Expected_fees,
			        "Platform Fees mismatch! Expected: "+Expected_fees+" | Actual: " + fees);
		   
		     
		    if (fees.equals(Expected_fees))
		    {
		        System.out.println("Platform Fees: MATCH ✓");
		        excel.setCellValue(rowValue, cellValue, "Pass");
                excel.setCellColor(rowValue, cellValue, IndexedColors.GREEN.getIndex());
                excel.flush();
		        return true;

 
		    } else {
		        System.out.println("Platform Fees: NOT MATCH ✗");
		        excel.setCellValue(rowValue, cellValue, "Fail");
                excel.setCellColor(rowValue, cellValue, IndexedColors.RED.getIndex());
                excel.flush();
 		        return false;
		        
		    }	 	    		    
	  }
    

    public void carticonClick() {
        waitUtils.waitForClickability(Carticon);
        js.executeScript("arguments[0].click();", Carticon);
    }

    public void ClickOnAddtocart() {
        waitUtils.waitForClickability(AddToCart);
        js.executeScript("arguments[0].click();", AddToCart);
    }

    public void checkout() {
        WebElement btn = waitUtils.fluentWaitForElement(
            By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]")
        );
        js.executeScript("arguments[0].click();", btn);
    }

    public void ScrollDownPage() throws InterruptedException {

    
    	
    	
    	WebElement ele=driver.findElement(By.xpath("//*[text()='Checkout']"));
    	
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	Thread.sleep(3000);
    	//js.executeScript("window.scrollBy(0,500)", "");
    	js.executeScript("arguments[0].scrollIntoView[true];",ele);
    }

    public void AddressCheckout() {
    	
    	
    	
        WebElement btn = waitUtils.fluentWaitForElement(
            By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]")
        );
        js.executeScript("arguments[0].click();", btn);
    }

    public void ClickOnPayNow() {
        waitUtils.waitForClickability(paynow); 
        js.executeScript("arguments[0].click();", paynow);
    }
    
    public void convertToUSD () {
 	   
 	   convertUSD.click();
    }
 	
    public void selectUnitedStates(){
    
    	selectUSD.click();
    }
    
    public void clickradiobutton() {
    	//WebElement radiobtn = waitUtils.fluentWaitForElement(
               // By.xpath("(//label[contains(@class,'cursor-pt')])[2]"  );
        //js.executeScript("arguments[0].click();", radiobtn);
    	
    	waitUtils.waitForClickability(radiobutton);
        js.executeScript("arguments[0].click();", radiobutton);
    }

 	
}
