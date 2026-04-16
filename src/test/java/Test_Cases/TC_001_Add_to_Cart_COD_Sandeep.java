package Test_Cases;

import java.io.IOException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import PageObject.CODValidation;
import PageObject.EnableorDisabled;
import PageObject.SearchProduct;
import Utilities.ExcelUtils;
import baseData.BaseData;

@Listeners(Utilities.Listener.class)
public class TC_001_Add_to_Cart_COD_Sandeep extends BaseData {

    static final String EXCEL_PATH =
        System.getProperty("user.dir") + "\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";

@Test(priority = 1 , groups = {"Domestic"})
    public void ADDToCartCOD() throws InterruptedException, IOException {

        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);

        String productKeyword = excel.getCellValue(38, 4); // col E = SKU

        try {
            // ── 1. Search Product ───────────────────────────────────────
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();
            Thread.sleep(3000);
            SP.EnterKeyword(productKeyword);
            Thread.sleep(4000);
            SP.ClickOnSearchResult();
            Thread.sleep(4000);

            // ── 2. Add to Cart & Checkout ───────────────────────────────
            EnableorDisabled ED = new EnableorDisabled(driver);
            ED.ClickOnAddtocart();
            ED.carticonClick();
            ED.checkout();
            Thread.sleep(3000);
            ED.clickradiobutton();
            Thread.sleep(3000);
            ED.ScrollDownPage();
            ED.AddressCheckout();
            Thread.sleep(3000);

            // ── 3. Validate COD ─────────────────────────────────────────
            // If Place Order is DISABLED:  → clicks cart icon → deletes product → marks Pass/Fail 
            // If Place Order is ENABLED: → clicks Place Order → marks Pass  
            CODValidation COD = new CODValidation(driver);
            COD.validateCODAndPlaceOrder(9612); // ← only change needed here

            // ── 4. Write Pass to Excel ───────────────────────────────────
            excel.setCellValue(38, 7, "Pass");
            excel.setCellColor(38, 7, IndexedColors.GREEN.getIndex());
            excel.setCellValue(38, 6, "COD option showing disabled");
            excel.setCellColor(38, 6, IndexedColors.GREEN.getIndex());
            excel.flush();

        } catch (Exception e) {
            try {
                excel.setCellValue(38, 7, "Fail");
                excel.setCellColor(38, 7, IndexedColors.RED.getIndex());
                excel.setCellValue(38, 6, "COD option showing enable");
                excel.setCellColor(38, 6, IndexedColors.GREEN.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }  

        

@Test(priority = 2, groups = {"Domestic"})
public void ADDToCartCOD1() throws InterruptedException, IOException {

    ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
    excel.useSheet(0);

    // Read SKU and amount from Excel row 38
    String productKeyword = excel.getCellValue(39, 4);  // col E = SKU

    try {
        // ── 1. Search Product ───────────────────────────────────────
        SearchProduct SP = new SearchProduct(driver);
        SP.clickSearchbox();
        Thread.sleep(3000);
        SP.EnterKeyword(productKeyword);
        Thread.sleep(4000);
        SP.ClickOnSearchResult();
        Thread.sleep(4000);

        // ── 2. Add to Cart & Checkout ───────────────────────────────
        EnableorDisabled ED = new EnableorDisabled(driver);
        ED.ClickOnAddtocart();
        ED.carticonClick();
        ED.checkout();
        Thread.sleep(5000);
        ED.clickradiobutton();
        Thread.sleep(3000);
        ED.ScrollDownPage();       
        Thread.sleep(3000);
        ED.AddressCheckout();
        Thread.sleep(3000);
        

     // Place Order Cash on Delivery
        CODValidation COD = new CODValidation(driver);
        COD.isPlaceOrderDisabled();
        COD.validateCODAndPlaceOrder(10470);
        COD.isCODEligible(10000);
        COD.clickonPlaceOrderButton();
        

        // Update status: Pass — row 4 (index 3), column H (index 7)
        excel.setCellValue(39, 7, "Pass");
        excel.setCellColor(39, 7, IndexedColors.GREEN.getIndex());
        excel.flush();

    } catch (Exception e) {
        // Update status: Fail — row 4 (index 3), column H (index 7)
        try {
            excel.setCellValue(39, 7, "Fail");
            excel.setCellColor(39, 7, IndexedColors.RED.getIndex());
            excel.flush();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        throw e;
    } finally {
        excel.close();
    }   
} 

@Test(priority = 3, groups = {"Domestic"})
public void ADDToCartCOD2() throws InterruptedException, IOException {

    ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
    excel.useSheet(0);

    String productKeyword = excel.getCellValue(40, 4); // col E = SKU

    try {
        // ── 1. Search Product ───────────────────────────────────────
    	Thread.sleep(20000);
    	SearchProduct SP = new SearchProduct(driver);
        SP.clickSearchbox();
        Thread.sleep(3000);
        SP.EnterKeyword(productKeyword);
        Thread.sleep(4000);
        SP.ClickOnSearchResult();
        Thread.sleep(4000);

        // ── 2. Add to Cart & Checkout ───────────────────────────────
        EnableorDisabled ED = new EnableorDisabled(driver);
        ED.ClickOnAddtocart();
        ED.carticonClick();
        ED.checkout();
        Thread.sleep(3000);
        ED.clickradiobutton();
        Thread.sleep(3000);
        ED.ScrollDownPage();
        ED.AddressCheckout();
        Thread.sleep(3000);

        // ── 3. Validate COD ─────────────────────────────────────────
        // If Place Order is DISABLED: → clicks cart icon → deletes product → marks Pass/Fail
        // If Place Order is ENABLED:  → clicks Place Order → marks Pass 
        CODValidation COD = new CODValidation(driver);
        COD.validateCODAndPlaceOrder(41462); // ← only change needed here

        // ── 4. Write Pass to Excel ───────────────────────────────────
        excel.setCellValue(40, 7, "Pass");
        excel.setCellColor(40, 7, IndexedColors.GREEN.getIndex());
        excel.flush();

    } catch (Exception e) {
        try {
            excel.setCellValue(40, 7, "Fail");
            excel.setCellColor(40, 7, IndexedColors.RED.getIndex());
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
 
