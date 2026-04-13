package Test_Cases;

import java.io.IOException;

import org.apache.poi.ss.usermodel.IndexedColors;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import PageObject.AddToCart;
import PageObject.CODCredential;
import PageObject.OnlinePayment;
import PageObject.PayuCredential;
import PageObject.SearchProduct;
import Utilities.ExcelUtils;
import baseData.BaseData;

@Listeners(Utilities.Listener.class)
public class TC_001_Add_To_Cart_Payu extends BaseData {

    static final String EXCEL_PATH = System.getProperty("user.dir") + "\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";

    @Test(priority = 1)
    public void AddTocartToOnline_Pay() throws InterruptedException, IOException {

        // Read product keyword from Excel: row 3 (index 2), column E (index 4)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        
        
        String productKeyword = excel.getCellValue(2, 4);

        try {
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product — fluentWait ensures search box is ready before typing
            waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
            SP.EnterKeyword(productKeyword);
            SP.ClickOnSearchResult();
            // Wait for product page: add-to-cart button must be visible before interacting
            waitUtils.waitForVisibility(org.openqa.selenium.By.xpath("//*[@class='addToCart']"));

            //-------------------------------------------------------------------------------------------------------------------

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            // ✅ Verify Platform Fees and write result to Excel
			/*
			 * boolean feesMatch = AD.VerifyPlatformFees();
			 * 
			 * if (feesMatch) { excel.setCellValue(2, 8, "Pass"); excel.setCellColor(2, 8,
			 * IndexedColors.GREEN.getIndex()); } else { excel.setCellValue(2, 8, "Fail");
			 * excel.setCellColor(2, 8, IndexedColors.RED.getIndex()); } excel.flush();
			 * 
			 * SoftAssert softAssert = new SoftAssert(); softAssert.assertTrue(feesMatch,
			 * "Platform Fees verification failed. Stopping test."); softAssert.assertAll();
			 */

            AD.checkout();
            // Wait for address/checkout button to be ready after navigation
            waitUtils.waitForVisibility(org.openqa.selenium.By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]"));
            AD.ScrollDownPage();
            AD.AddressCheckout();
            AD.ClickOnPayNow();

            //---------------------------------------------------------------------------------------------------------------------
            // Online Payment
            OnlinePayment OP = new OnlinePayment(driver);
            OP.clickonNetbacnking();
            OP.ClickOnTestBank();
            OP.ClickOnProceed();

            //---------------------------------------------------------------------------------------------------------------------
            // Payu Credential
            PayuCredential PC = new PayuCredential(driver);
            PC.EnterPayu_UserName();
            PC.EnterPayu_Password();
            PC.ClickOnLogin();
            PC.ClickOnSimlulateSubmit_BTN();
            
            
         

            // Update status: Pass — row 3 (index 2), column H (index 7)
            excel.setCellValue(2, 7, "Pass");
            excel.setCellColor(2, 7, IndexedColors.GREEN.getIndex());
            excel.flush();

        } catch (Exception e) {
            // Update status: Fail — row 3 (index 2), column H (index 7)
            try {
                excel.setCellValue(2, 7, "Fail");
                excel.setCellColor(2, 7, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }

    //@Test(priority = 2)
    public void ADDToCartCOD() throws InterruptedException, IOException {

        // Read product keyword from Excel: row 4 (index 3), column E (index 4)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        String productKeyword = excel.getCellValue(3, 4);

        try {
            // Search Product
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();
            SP.EnterKeyword(productKeyword);
            SP.ClickOnSearchResult();
            waitUtils.waitForVisibility(org.openqa.selenium.By.xpath("//*[@class='addToCart']"));

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            AD.checkout();

            // Select Domestic Address
            //AddressDomInt ADR = new AddressDomInt(driver);
            //ADR.ClickOnDomestic_Address();
            waitUtils.waitForVisibility(org.openqa.selenium.By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]"));
            AD.ScrollDownPage();
            AD.AddressCheckout();
           // AD.ClickOnPayNow();

            // Place Order Cash on Delivery
            CODCredential COD = new CODCredential(driver);
            COD.clickonPlaceOrderButton();

            
            
            
            
            
            // Update status: Pass — row 4 (index 3), column H (index 7)
            excel.setCellValue(3, 7, "Pass");
            excel.setCellColor(3, 7, IndexedColors.GREEN.getIndex());
            excel.flush();

        } catch (Exception e) {
            // Update status: Fail — row 4 (index 3), column H (index 7)
            try {
                excel.setCellValue(3, 7, "Fail");
                excel.setCellColor(3, 7, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }
}
