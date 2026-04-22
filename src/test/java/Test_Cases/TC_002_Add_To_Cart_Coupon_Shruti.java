package Test_Cases;

import java.io.IOException;


import org.apache.poi.ss.usermodel.IndexedColors;

import org.testng.annotations.Test;

import PageObject.AddToCart;
import PageObject.ApplyCoupons;
import PageObject.CODCredential;

import PageObject.SearchProduct;
import PageObject.USD_WithoutCoupon;
import PageObject.WithoutCouponAndSpecialPrice;
import PageObject.withoutCoupons;
import Utilities.ExcelUtils;
import baseData.BaseData;

public class TC_002_Add_To_Cart_Coupon_Shruti extends BaseData {

	static final String EXCEL_PATH = System.getProperty("user.dir")+"\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";

	  @Test(priority = 1 , groups = {"Coupon"})
	    public void ADDToCartCODCoupon() throws InterruptedException, IOException {

	    	
	        // Read product keyword from Excel: row 30 (index 29), column E (index 4)
	        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
	        excel.useSheet(0);
	       // String productKeyword = excel.getCellValue(29, 4);

	        String productKeyword = excel.getCellValue(46, 4);
	        
	        
	        try {
	            SearchProduct SP = new SearchProduct(driver);
	            SP.clickSearchbox();

	            // Search Product
	            waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
	            Thread.sleep(2000);
	            SP.EnterKeyword(productKeyword);
	            Thread.sleep(3000);
	            SP.ClickOnSearchResult();
	            Thread.sleep(3000);

	            // Add To Cart
	            AddToCart AD = new AddToCart(driver);
	            AD.ClickOnAddtocart();
	            AD.carticonClick();
	            
	            // Add coupon
	            ApplyCoupons AC = new ApplyCoupons(driver);
	            AC.clickAppyCouponButton();
	            AC.clickApplyButton();
	            AC.getCouponCode();
	            AC.getOrderSummerycode();
	            AC.compareCouponCodes();
	            AC.priceValidation(excel, 46);
	           
	            
	         // Add To Cart
	            AD.checkout();
	            AD.clickradiobutton();
	            Thread.sleep(2000);
	            
	            AD.ScrollDownPage();
	            AD.AddressCheckout();
	            //AD.ClickOnPayNow();
	            
	           /*
	         // Payu Credential
	            PayuCredential PC = new PayuCredential(driver);
	            Thread.sleep(3000);
	            PC.EnterPayu_UserName();
	            PC.EnterPayu_Password();
	            PC.ClickOnLogin();
	            PC.ClickOnSimlulateSubmit_BTN();
	      */
	         // Place Order Cash on Delivery
	            CODCredential COD = new CODCredential(driver);
	            Thread.sleep(5000);
	            COD.clickonPlaceOrderButton();
	            Thread.sleep(12000);
	            
	            
	            excel.setCellValue(46, 7, "Pass");
	            excel.setCellColor(46, 7, IndexedColors.GREEN.getIndex());
	            excel.flush();
	            Thread.sleep(5000);
	       

	        }    catch (Exception e) {
	            // Update status: Fail — row 3 (index 2), column H (index 7)
	            try {
	                excel.setCellValue(46, 7, "Fail");
	                excel.setCellColor(46, 7, IndexedColors.RED.getIndex());
	                excel.flush();
	            } catch (IOException ioEx) {
	                ioEx.printStackTrace();
	            }
	            throw e;
	        }finally {
	            excel.close();
	        }
	    
	    }
	     
	  @Test(priority = 2, groups = {"Coupon"})
	        public void ADDToCartPayu() throws InterruptedException, IOException {

	            // Read product keyword from Excel
	            ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
	            excel.useSheet(0);

	            String productKeyword = excel.getCellValue(45, 4);

	            try {
	            	Thread.sleep(1000);
	                SearchProduct SP = new SearchProduct(driver);
	                SP.clickSearchbox();

	                // Search Product
	                waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
	                Thread.sleep(2000);
	                SP.EnterKeyword(productKeyword);
	                Thread.sleep(3000);
	                SP.ClickOnSearchResult();
	                Thread.sleep(3000);

	                // Add To Cart
	                AddToCart AD = new AddToCart(driver);
	                AD.ClickOnAddtocart();
	                AD.carticonClick();

	               
	                withoutCoupons WC = new withoutCoupons(driver);
	                Thread.sleep(10000);
	                WC.priceValidation(excel, 45);

	                // Checkout
	                AD.checkout();
	                AD.clickradiobutton();
	                Thread.sleep(2000);
	                AD.ScrollDownPage();
	                AD.AddressCheckout();
	                Thread.sleep(2000);
	                //AD.ClickOnPayNow();

	                /*
	                // Payu Credential
	                PayuCredential PC = new PayuCredential(driver);
	                PC.EnterPayu_UserName();
	                PC.EnterPayu_Password();
	                PC.ClickOnLogin();
	                PC.ClickOnSimlulateSubmit_BTN();
	                */
	                
	                // Place Order Cash on Delivery
	                CODCredential COD = new CODCredential(driver);
	              
	                COD.clickonPlaceOrderButton();
	               
	                Thread.sleep(15000);
	              
	               
	                
	                excel.setCellValue(45, 7, "Pass");
	                excel.setCellColor(45, 7, IndexedColors.GREEN.getIndex());
	                excel.flush();
	                Thread.sleep(2000);
	             
	                
	            } catch (Exception e) {
	                try {
	                    excel.setCellValue(45, 7, "Fail");
	                    excel.setCellColor(45, 7, IndexedColors.RED.getIndex());
	                    excel.flush();
	                } catch (IOException ioEx) {
	                    ioEx.printStackTrace();
	                }
	                throw e;
	            } finally {
	                excel.close();
	            }
	        }
	      
	      
	     
	    @Test(priority = 3, groups = {"Coupon"})
	        public void ADDToCartCOD() throws InterruptedException, IOException {

	        	
	            // Read product keyword from Excel: row 30 (index 29), column E (index 4)
	            ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
	            excel.useSheet(0);
	           // String productKeyword = excel.getCellValue(29, 4);

	            String productKeyword = excel.getCellValue(48, 4);
	            
	            
	            try {
	            	Thread.sleep(1000);
	                SearchProduct SP = new SearchProduct(driver);
	                SP.clickSearchbox();

	                // Search Product
	                waitUtils.fluentWaitForElement(org.openqa.selenium.By.id("search"));
	                Thread.sleep(2000);
	                SP.EnterKeyword(productKeyword);
	                Thread.sleep(3000);
	                SP.ClickOnSearchResult();
	                Thread.sleep(3000);

	                // Add To Cart
	                AddToCart AD = new AddToCart(driver);
	                AD.ClickOnAddtocart();
	                AD.carticonClick();
	              
	                
	                WithoutCouponAndSpecialPrice WC = new WithoutCouponAndSpecialPrice(driver);
	                WC.priceValidation(excel, 48);
	      
	             // Add To Cart
	                AD.checkout();
	                AD.clickradiobutton();
	                Thread.sleep(2000);
	                AD.ScrollDownPage();
	                AD.AddressCheckout();
	                //AD.ClickOnPayNow();
	                
	              /*
	             // Payu Credential
	                PayuCredential PC = new PayuCredential(driver);
	                PC.EnterPayu_UserName();
	                PC.EnterPayu_Password();
	                PC.ClickOnLogin();
	                PC.ClickOnSimlulateSubmit_BTN();
	              */
	                // Place Order Cash on Delivery
	                CODCredential COD = new CODCredential(driver);
	                COD.clickonPlaceOrderButton();
	                Thread.sleep(5000);
	           
	                
	                excel.setCellValue(48, 7, "Pass");
	                excel.setCellColor(48, 7, IndexedColors.GREEN.getIndex());
	                excel.flush();
	                Thread.sleep(2000);
	            

	            }    catch (Exception e) {
	                // Update status: Fail — row 3 (index 2), column H (index 7)
	                try {
	                    excel.setCellValue(48, 7, "Fail");
	                    excel.setCellColor(48, 7, IndexedColors.RED.getIndex());
	                    excel.flush();
	                } catch (IOException ioEx) {
	                    ioEx.printStackTrace();
	                }
	                throw e;
	            }finally {
	                excel.close();
	            }
	        
	    }
	        
	        
	    @Test(priority = 4, groups = {"Coupon"})
	        public void ADDToCartCODCouponWithoutSpecialprice() throws InterruptedException, IOException {

	        	
	            // Read product keyword from Excel: row 30 (index 29), column E (index 4)
	            ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
	            excel.useSheet(0);
	           // String productKeyword = excel.getCellValue(29, 4);

	            String productKeyword = excel.getCellValue(49, 4);
	            
	            
	            try {
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

	                // Add To Cart
	                AddToCart AD = new AddToCart(driver);
	                AD.ClickOnAddtocart();
	                AD.carticonClick();
	                
	                // Add coupon
	                ApplyCoupons AC = new ApplyCoupons(driver);
	                AC.clickAppyCouponButton();
	                AC.clickApplyButton();
	                AC.getCouponCode();
	                AC.getOrderSummerycode();
	                AC.compareCouponCodes();
	                AC.priceValidation(excel, 49);
	               
	                
	             // Add To Cart
	                AD.checkout();
	                AD.clickradiobutton();
	                Thread.sleep(2000);
	                AD.ScrollDownPage();
	                AD.AddressCheckout();
	               // AD.ClickOnPayNow();
	                
	               /*
	             // Payu Credential
	                PayuCredential PC = new PayuCredential(driver);
	                Thread.sleep(3000);
	                PC.EnterPayu_UserName();
	                PC.EnterPayu_Password();
	                PC.ClickOnLogin();
	                PC.ClickOnSimlulateSubmit_BTN();
	          */
	                // Place Order Cash on Delivery
	                CODCredential COD = new CODCredential(driver);
	                COD.clickonPlaceOrderButton();
	                Thread.sleep(5000);
	               
	                
	                excel.setCellValue(49, 7, "Pass");
	                excel.setCellColor(49, 7, IndexedColors.GREEN.getIndex());
	                excel.flush();
	                Thread.sleep(2000);
	             

	            }    catch (Exception e) {
	                // Update status: Fail — row 3 (index 2), column H (index 7)
	                try {
	                    excel.setCellValue(49, 7, "Fail");
	                    excel.setCellColor(49, 7, IndexedColors.RED.getIndex());
	                    excel.flush();
	                } catch (IOException ioEx) {
	                    ioEx.printStackTrace();
	                }
	                throw e;
	            }finally {
	                excel.close();
	            }
	        
	        }
	        
	       @Test(priority = 5, groups = {"Coupon"})
	        public void USDWithoutCoupon() throws InterruptedException, IOException {

	            // Read product keyword from Excel
	            ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
	            excel.useSheet(0);

	            String productKeyword = excel.getCellValue(47, 4);

	            try {
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
	             
	                
	                
	                // Add To Cart
	                AddToCart AD = new AddToCart(driver);
	                AD.convertToUSD();
	                AD.selectUnitedStates();
	                Thread.sleep(10000);
	                AD.ClickOnAddtocart();
	                AD.carticonClick();

	                Thread.sleep(3000);
	                USD_WithoutCoupon WC = new USD_WithoutCoupon(driver);
	                WC.priceValidation_USD(excel, 47);
	              
	                
	                // Checkout
	             
	                AD.checkout();
	                
	                /*
	                AD.ScrollDownPage();
	                Thread.sleep(1000);
	                AD.AddressCheckout();
	                
	                //AD.ClickOnPayNow();

	               
	                // Payu Credential
	                PayuCredential PC = new PayuCredential(driver);
	                PC.EnterPayu_UserName();
	                PC.EnterPayu_Password();
	                PC.ClickOnLogin();
	                PC.ClickOnSimlulateSubmit_BTN();
	               
	                
	                // Place Order Cash on Delivery
	                CODCredential COD = new CODCredential(driver);
	                COD.clickonPlaceOrderButton();
	                Thread.sleep(15000);
	              */
	               
	                
	                excel.setCellValue(47, 7, "Pass");
	                excel.setCellColor(47, 7, IndexedColors.GREEN.getIndex());
	                excel.flush();
	                Thread.sleep(2000);
	             
	                
	            } catch (Exception e) {
	                try {
	                    excel.setCellValue(47, 7, "Fail");
	                    excel.setCellColor(47, 7, IndexedColors.RED.getIndex());
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
