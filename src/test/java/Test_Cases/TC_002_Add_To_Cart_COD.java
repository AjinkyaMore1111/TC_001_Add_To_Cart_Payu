package Test_Cases;

import java.io.IOException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import PageObject.AddToCart;

import PageObject.CODCredential;
import PageObject.SearchProduct;
import Utilities.ExcelUtils;
import baseData.BaseData;

@Listeners(Utilities.Listener.class)
public class TC_002_Add_To_Cart_COD extends BaseData {

    private static final String EXCEL_PATH = System.getProperty("user.dir") + "\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";

    @Test(priority = 1)
    public void ADDToCartCOD() throws InterruptedException, IOException {

        // Read product keyword from Excel: row 30 (index 29), column E (index 4)
        ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
        excel.useSheet(0);
        String productKeyword = excel.getCellValue(29, 4);

        try {
            // Search Product — EnterKeyword waits for the search field internally
            SearchProduct SP = new SearchProduct(driver);
            SP.clickSearchbox();
            SP.EnterKeyword(productKeyword);
            SP.ClickOnSearchResult();
            // Wait for product page: add-to-cart button must be visible before interacting
            waitUtils.waitForVisibility(org.openqa.selenium.By.xpath("//*[@class='addToCart']"));

            // Add To Cart
            AddToCart AD = new AddToCart(driver);
            AD.ClickOnAddtocart();
            AD.carticonClick();
            AD.checkout();

            // Wait for address/payment section to be ready after checkout navigation
            waitUtils.waitForVisibility(org.openqa.selenium.By.xpath("//*[contains(@class,'btn btn--background') and contains(@type,'button')]"));
            AD.ScrollDownPage();
            AD.AddressCheckout();

            // Place Order Cash on Delivery
            CODCredential COD = new CODCredential(driver);
            COD.clickonPlaceOrderButton();
            // Wait for order confirmation — URL leaves checkout page
            waitUtils.fluentWait(d -> !d.getCurrentUrl().contains("checkout"));

            // Update status: Pass — row 30 (index 29), column H (index 7)
            excel.setCellValue(29, 7, "Pass");
            excel.setCellColor(29, 7, IndexedColors.GREEN.getIndex());
            excel.flush();

        } catch (Exception e) {
            // Update status: Fail — row 30 (index 29), column H (index 7)
            try {
                excel.setCellValue(29, 7, "Fail");
                excel.setCellColor(29, 7, IndexedColors.RED.getIndex());
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
