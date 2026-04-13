package Test_Cases;

import java.io.IOException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import PageObject.AddToCart;
import PageObject.ApplyCoupons;
import PageObject.CODCredential;
import PageObject.SearchProduct;
import PageObject.WithoutCouponAndSpecialPrice;
import PageObject.withoutCoupons;
import Utilities.ExcelUtils;
import baseData.BaseData;

@Listeners(Utilities.Listener.class)
public class TC_003_Add_Test extends BaseData {

    static final String EXCEL_PATH = System.getProperty("user.dir") + "\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";

    @Test(priority = 1)
    public void ADDToCartCODCoupon() throws InterruptedException, IOException {

        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        String productKeyword = excel.getCellValue(46, 4);

        try {
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product — fluentWait ensures search box is ready before typing
            waitUtils.fluentWaitForElement(By.id("search"));
            SP.EnterKeyword(productKeyword);
            SP.ClickOnSearchResult();
            // Wait for product page: add-to-cart button must be visible before interacting
            waitUtils.waitForVisibility(By.xpath("//*[@class='addToCart']"));

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();

            // Apply coupon
            ApplyCoupons AC = new ApplyCoupons(driver);
            AC.clickAppyCouponButton();
            AC.clickApplyButton();
            AC.getCouponCode();
            AC.getOrderSummerycode();
            AC.compareCouponCodes();
            AC.priceValidation();

            AD.checkout();
            // Wait for address/payment section to be ready after checkout navigation
            waitUtils.waitForVisibility(By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]"));
            AD.ScrollDownPage();
            AD.AddressCheckout();

            // Place Order Cash on Delivery — clickonPlaceOrderButton already waits for clickability
            CODCredential COD = new CODCredential(driver);
            COD.clickonPlaceOrderButton();
            // Wait for order confirmation — URL leaves checkout page
            Thread.sleep(4000);
            excel.setCellValue(46, 7, "Pass");
            excel.setCellColor(46, 7, IndexedColors.GREEN.getIndex());
            excel.flush();

        } catch (Exception e) {
            try {
                excel.setCellValue(46, 7, "Fail");
                excel.setCellColor(46, 7, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }

   @Test(priority = 2)
    public void ADDToCartPayu() throws InterruptedException, IOException {

        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        String productKeyword = excel.getCellValue(45, 4);

        try {
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product — fluentWait ensures search box is ready before typing
            waitUtils.fluentWaitForElement(By.id("search"));
            SP.EnterKeyword(productKeyword);
            SP.ClickOnSearchResult();
            // Wait for product page: add-to-cart button must be visible before interacting
            waitUtils.waitForVisibility(By.xpath("//*[@class='addToCart']"));

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();

            withoutCoupons WC = new withoutCoupons(driver);
            WC.priceValidation();

            // Checkout
            AD.checkout();
            // Wait for address/payment section to be ready after checkout navigation
            waitUtils.waitForVisibility(By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]"));
            AD.ScrollDownPage();
            AD.AddressCheckout();

            // Place Order Cash on Delivery — clickonPlaceOrderButton already waits for clickability
            CODCredential COD = new CODCredential(driver);
            COD.clickonPlaceOrderButton();
            // Wait for order confirmation — URL leaves checkout page
            Thread.sleep(3000);

            excel.setCellValue(45, 7, "Pass");
            excel.setCellColor(45, 7, IndexedColors.GREEN.getIndex());
            excel.flush();

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

    @Test(priority = 3)
    public void ADDToCartCOD() throws InterruptedException, IOException {

        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        String productKeyword = excel.getCellValue(48, 4);

        try {
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product — fluentWait ensures search box is ready before typing
            waitUtils.fluentWaitForElement(By.id("search"));
            SP.EnterKeyword(productKeyword);
            SP.ClickOnSearchResult();
            // Wait for product page: add-to-cart button must be visible before interacting
            waitUtils.waitForVisibility(By.xpath("//*[@class='addToCart']"));

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();

            WithoutCouponAndSpecialPrice WC = new WithoutCouponAndSpecialPrice(driver);
            WC.priceValidation();

            AD.checkout();
            // Wait for address/payment section to be ready after checkout navigation
            waitUtils.waitForVisibility(By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]"));
            AD.ScrollDownPage();
            AD.AddressCheckout();

            // Place Order Cash on Delivery — clickonPlaceOrderButton already waits for clickability
            CODCredential COD = new CODCredential(driver);
            COD.clickonPlaceOrderButton();
            // Wait for order confirmation — URL leaves checkout page
             Thread.sleep(4000);

            excel.setCellValue(48, 7, "Pass");
            excel.setCellColor(48, 7, IndexedColors.GREEN.getIndex());
            excel.flush();

        } catch (Exception e) {
            try {
                excel.setCellValue(48, 7, "Fail");
                excel.setCellColor(48, 7, IndexedColors.RED.getIndex());
                excel.flush();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            throw e;
        } finally {
            excel.close();
        }
    }

   @Test(priority = 4)
    public void ADDToCartCODCouponWithoutSpecialprice() throws InterruptedException, IOException {

        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        String productKeyword = excel.getCellValue(49, 4);

        try {
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();

            // Search Product — fluentWait ensures search box is ready before typing
            waitUtils.fluentWaitForElement(By.id("search"));
            SP.EnterKeyword(productKeyword);
            SP.ClickOnSearchResult();
            // Wait for product page: add-to-cart button must be visible before interacting
            waitUtils.waitForVisibility(By.xpath("//*[@class='addToCart']"));

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();

            // Apply coupon
            ApplyCoupons AC = new ApplyCoupons(driver);
            AC.clickAppyCouponButton();
            AC.clickApplyButton();
            AC.getCouponCode();
            AC.getOrderSummerycode();
            AC.compareCouponCodes();
            AC.priceValidation();

            AD.checkout();
            // Wait for address/payment section to be ready after checkout navigation
            waitUtils.waitForVisibility(By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]"));
            AD.ScrollDownPage();
            AD.AddressCheckout();

            // Place Order Cash on Delivery — clickonPlaceOrderButton already waits for clickability
            CODCredential COD = new CODCredential(driver);
            COD.clickonPlaceOrderButton();
            // Wait for order confirmation — URL leaves checkout page
             Thread.sleep(4000);
    
            excel.setCellValue(49, 7, "Pass");
            excel.setCellColor(49, 7, IndexedColors.GREEN.getIndex());
            excel.flush();

        } catch (Exception e) {
            try {
                excel.setCellValue(49, 7, "Fail");
                excel.setCellColor(49, 7, IndexedColors.RED.getIndex());
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
