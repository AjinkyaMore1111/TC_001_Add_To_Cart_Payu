package Test_Cases;

import java.io.IOException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import PageObject.AddToCart;
import PageObject.CODCredential;
import PageObject.SearchProduct;
import PageObject.withoutCoupons;
import Utilities.ExcelUtils;
import baseData.BaseData;

@Listeners(Utilities.Listener.class)
public class TC_004_Add_Test extends BaseData {
	  static final String EXCEL_PATH = System.getProperty("user.dir") + "\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";
	
	  WebDriver driver;
	 @Test(priority = 1,groups="regression")
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
	            waitUtils.fluentWait(d -> !d.getCurrentUrl().contains("checkout"));

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
}
