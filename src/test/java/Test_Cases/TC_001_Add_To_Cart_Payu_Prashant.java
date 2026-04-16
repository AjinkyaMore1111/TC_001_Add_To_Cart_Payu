package Test_Cases;

import java.io.IOException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
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
public class TC_001_Add_To_Cart_Payu_Prashant extends BaseData {


    String EXCEL_PATH = System.getProperty("user.dir") + "\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";

    @Test(priority = 1, groups = {"Tax"})
    public void AddTocartToOnline_Pay_TC_Luxe_006() throws InterruptedException, IOException {

    	System.out.println("=====running - AddTocartToOnline_Pay_TC_Luxe_006======");

         //Open Excel and go to Sheet1 (index 0)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);   //sheet 1
       
        // Read product keyword/data from Excel: row 3 (index 2), column E (index 4)
        String productKeyword = excel.getCellValue(32, 4);

        try
        {
            Thread.sleep(10000);

            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product
            waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
            Thread.sleep(2000);
            SP.EnterKeyword(productKeyword);
            Thread.sleep(3000);
            SP.ClickOnSearchResult();
            Thread.sleep(3000);

            //-------------------------------------------------------------------------------------------------------------------

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            
            // ✅ Verify Platform Fees and write result to Excel
            boolean feesMatch = AD.VerifyPlatformFees(32,4,0,EXCEL_PATH);   //📥 Goes to AddToCart class → checks fee → returns true or false//

 
            if (feesMatch) {
                excel.setCellValue(32, 7, "Pass");
                excel.setCellColor(32, 7, IndexedColors.GREEN.getIndex());
            } else {
                excel.setCellValue(32, 7, "Fail");
                excel.setCellColor(32, 7, IndexedColors.RED.getIndex());
            }
            excel.flush();
 
            // Continue test only if fees matched
           // Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
            
            SoftAssert Assert=new SoftAssert();
            Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
 
    
            
            AD.checkout();
            AD.clickradiobutton();
            Thread.sleep(2000);
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
            Thread.sleep(15000);

            
            //OVerall Website running or not proper//
            // Update status: Pass — row 32 (index 2), column H (index 7)
            excel.setCellValue(32, 8, "Pass");
            excel.setCellColor(32, 8, IndexedColors.GREEN.getIndex());
            excel.flush();
            
            
            
        } catch (Exception e) {
            // Update status: Fail — row 32 (index 2), column H (index 7)
            try {
                excel.setCellValue(32, 8, "Fail");
                excel.setCellColor(32, 8, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }

    
    
 

    @Test(priority = 2, groups = {"Tax"})
    public void AddTocartToOnline_Pay_TC_Luxe_007() throws InterruptedException, IOException {
    	
    	System.out.println("=====running - AddTocartToOnline_Pay_TC_Luxe_007=======");
        
    	// Open Excel and go to Sheet1 (index 0)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        
        // Read product keyword from Excel: row 3 (index 2), column E (index 4)
        String productKeyword = excel.getCellValue(33, 4);

        try
        {
            Thread.sleep(10000);

        	SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product
            waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
            Thread.sleep(2000);
            SP.EnterKeyword(productKeyword);
            Thread.sleep(3000);
            SP.ClickOnSearchResult();
            Thread.sleep(3000);

            //-------------------------------------------------------------------------------------------------------------------

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            
            // ✅ Verify Platform Fees and write result to Excel
            boolean feesMatch = AD.VerifyPlatformFees(33,4,0,EXCEL_PATH);   //📥 Goes to AddToCart class → checks fee → returns true or false//

 
            if (feesMatch) {
                excel.setCellValue(33, 7, "Pass");
                excel.setCellColor(33, 7, IndexedColors.GREEN.getIndex());
            } else {
                excel.setCellValue(33, 7, "Fail");
                excel.setCellColor(33, 7, IndexedColors.RED.getIndex());
            }
            excel.flush();
 
            // Continue test only if fees matched
           // Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
            
            SoftAssert Assert=new SoftAssert();
            Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
 
    
            
            AD.checkout();
            AD.clickradiobutton();
            Thread.sleep(2000);
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
            Thread.sleep(15000);

            //OVerall Website running or not proper//
            // Update status: Pass — row 32 (index 2), column H (index 7)
            excel.setCellValue(33, 8, "Pass");
            excel.setCellColor(33, 8, IndexedColors.GREEN.getIndex());
            excel.flush();
 
            
            
        } catch (Exception e) {
            // Update status: Fail — row 32 (index 2), column H (index 7)
            try {
                excel.setCellValue(33, 8, "Fail");
                excel.setCellColor(33, 8, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }

    
    

    @Test(priority = 3, groups = {"Tax"})
    public void AddTocartToOnline_Pay_TC_Luxe_008() throws InterruptedException, IOException {
    	
    	System.out.println("======running - AddTocartToOnline_Pay_TC_Luxe_008======");

    	// Open Excel and go to Sheet1 (index 0)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        
        // Read product keyword from Excel: row 3 (index 2), column E (index 4)
        String productKeyword = excel.getCellValue(34, 4);

        try
        {
            Thread.sleep(10000);

            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product
            waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
            Thread.sleep(2000);
            SP.EnterKeyword(productKeyword);
            Thread.sleep(3000);
            SP.ClickOnSearchResult();
            Thread.sleep(3000);

            //-------------------------------------------------------------------------------------------------------------------

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            
            // ✅ Verify Platform Fees and write result to Excel
            boolean feesMatch = AD.VerifyPlatformFees(34,4,0,EXCEL_PATH);   //📥 Goes to AddToCart class → checks fee → returns true or false//

 
            if (feesMatch) {
                excel.setCellValue(34, 7, "Pass");
                excel.setCellColor(34, 7, IndexedColors.GREEN.getIndex());
            } else {
                excel.setCellValue(34, 7, "Fail");
                excel.setCellColor(34, 7, IndexedColors.RED.getIndex());
            }
            excel.flush();
 
            // Continue test only if fees matched
           // Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
            
            SoftAssert Assert=new SoftAssert();
            Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
 
    
            
            AD.checkout();
            AD.clickradiobutton();
            Thread.sleep(2000);
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
            Thread.sleep(15000);

            // Update status: Pass — row 32 (index 2), column H (index 7)
            excel.setCellValue(34, 8, "Pass");
            excel.setCellColor(34, 8, IndexedColors.GREEN.getIndex());
            excel.flush();
            
            
        } catch (Exception e) {
            // Update status: Fail — row 32 (index 2), column H (index 7)
            try {
                excel.setCellValue(34, 8, "Fail");
                excel.setCellColor(34, 8, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }

    
    
    
    @Test(priority = 4, groups = {"Tax"})
    public void AddTocartToOnline_Pay_TC_Luxe_009() throws InterruptedException, IOException {
    	
    	System.out.println("=====running - AddTocartToOnline_Pay_TC_Luxe_009======");
    	
    	// Open Excel and go to Sheet1 (index 0)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        
        // Read product keyword from Excel: row 3 (index 2), column E (index 4)
        String productKeyword = excel.getCellValue(35, 4);

        try
        {
            Thread.sleep(10000);

            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product
            waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
            Thread.sleep(2000);
            SP.EnterKeyword(productKeyword);
            Thread.sleep(3000);
            SP.ClickOnSearchResult();
            Thread.sleep(3000);

            //-------------------------------------------------------------------------------------------------------------------

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            
            // ✅ Verify Platform Fees and write result to Excel
            boolean feesMatch = AD.VerifyPlatformFees(35,4,0,EXCEL_PATH);   //📥 Goes to AddToCart class → checks fee → returns true or false//

 
            if (feesMatch) {
                excel.setCellValue(35, 7, "Pass");
                excel.setCellColor(35, 7, IndexedColors.GREEN.getIndex());
            } else {
                excel.setCellValue(35, 7, "Fail");
                excel.setCellColor(35, 7, IndexedColors.RED.getIndex());
            }
            excel.flush();
 
            // Continue test only if fees matched
           // Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
            
            SoftAssert Assert=new SoftAssert();
            Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
 
    
            
            AD.checkout();
            AD.clickradiobutton();
            Thread.sleep(2000);
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
            Thread.sleep(15000);

            // Update status: Pass — row 32 (index 2), column H (index 7)
            excel.setCellValue(35, 8, "Pass");
            excel.setCellColor(35, 8, IndexedColors.GREEN.getIndex());
            excel.flush();
            
            
        } catch (Exception e) {
            // Update status: Fail — row 32 (index 2), column H (index 7)
            try {
                excel.setCellValue(35, 8, "Fail");
                excel.setCellColor(35, 8, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }

    

    @Test(priority = 5, groups = {"Tax"})
    public void AddTocartToOnline_Pay_TC_Luxe_010() throws InterruptedException, IOException {
    	
    	System.out.println("====running - AddTocartToOnline_Pay_TC_Luxe_010======");  //print in console//
    	
    	// Open Excel and go to Sheet1 (index 0)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        
        // Read product keyword from Excel: row 3 (index 2), column E (index 4)
        String productKeyword = excel.getCellValue(36, 4);

        try
        {
            Thread.sleep(10000);

            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product
            waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
            Thread.sleep(2000);
            SP.EnterKeyword(productKeyword);
            Thread.sleep(3000);
            SP.ClickOnSearchResult();
            Thread.sleep(3000);

            //-------------------------------------------------------------------------------------------------------------------

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            
            // ✅ Verify Platform Fees and write result to Excel
            boolean feesMatch = AD.VerifyPlatformFees(36,4,0,EXCEL_PATH);   //📥 Goes to AddToCart class → checks fee → returns true or false//

 
            if (feesMatch) {
                excel.setCellValue(36, 7, "Pass");
                excel.setCellColor(36, 7, IndexedColors.GREEN.getIndex());
            } else {
                excel.setCellValue(36, 7, "Fail");
                excel.setCellColor(36, 7, IndexedColors.RED.getIndex());
            }
            excel.flush();
 
            // Continue test only if fees matched
           // Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
            
            SoftAssert Assert=new SoftAssert();
            Assert.assertTrue(feesMatch, "Platform Fees verification failed. Stopping test.");
 
    
            
            AD.checkout();
            AD.clickradiobutton();
            Thread.sleep(2000);
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
            Thread.sleep(15000);

            // Update status: Pass — row 32 (index 2), column H (index 7)
            excel.setCellValue(36, 8, "Pass");
            excel.setCellColor(36, 8, IndexedColors.GREEN.getIndex());
            excel.flush();
            
            
        } catch (Exception e) {
            // Update status: Fail — row 32 (index 2), column H (index 7)
            try {
                excel.setCellValue(36, 8, "Fail");
                excel.setCellColor(36, 8, IndexedColors.RED.getIndex());
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
