package PageObject;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.ExcelUtils;
import Utilities.WaitUtilities;

public class IA_CODCredentials {

	 WebDriver driver;
	    WaitUtilities waitutils;
	    WebDriverWait wait;
	    JavascriptExecutor js;

	    public IA_CODCredentials(WebDriver driver) {
	        this.driver = driver;
	        this.waitutils = new WaitUtilities(driver);
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	        this.js = (JavascriptExecutor) driver;
	        PageFactory.initElements(driver, this);
	    }

	    // ── Place Order button ───────────────────────────────────────────────
	    @FindBy(xpath = "//button[contains(@class,'payment--buttons') and contains(text(),'Place Order')]")
	    WebElement placeOrder;

	    // ── Cart icon (top right) ────────────────────────────────────────────
	    @FindBy(xpath = "//*[@type='button' and @aria-label='cart']")
	    WebElement cartIcon;

	    // ── Delete/Trash icon on product card ────────────────────────────────
	    @FindBy(xpath = "//i[contains(@class,'lp--delete')]")
	    WebElement deleteIcon;

	    // ── Check if Place Order button is disabled ──────────────────────────
	    public boolean isPlaceOrderDisabled() {
	        try {
	            String disabledAttr = placeOrder.getAttribute("disabled");
	            String classAttr    = placeOrder.getAttribute("class");

	            boolean disabled =
	                (disabledAttr != null) ||
	                (classAttr != null && classAttr.toLowerCase().contains("disabled"));

	            System.out.println("Place Order disabled attr : " + disabledAttr);
	            System.out.println("Place Order class         : " + classAttr);
	            System.out.println("Is Place Order DISABLED?  : " + disabled);

	            return disabled;

	        } catch (Exception e) {
	            System.out.println("Could not read Place Order button: " + e.getMessage());
	            return false;
	        }
	    }

	    // ── Business Rule ────────────────────────────────────────────────────
	    // International address → COD is NEVER available (always disabled)
	    public boolean isCODEligibleForInternational() {
	        System.out.println("Address Type : INTERNATIONAL");
	        System.out.println("COD Rule     : COD is NEVER available for International Address");
	        return false; // International address → COD always disabled
	    }

	    // ── Click cart icon → wait → click delete icon ───────────────────────
	    public void clickCartIconAndDeleteProduct() throws InterruptedException {
	        System.out.println("─────────────────────────────────────────");
	        System.out.println("Navigating to cart to remove product...");
	        System.out.println("─────────────────────────────────────────");

	        // Step 1: Click cart icon
	        waitutils.waitForClickability(cartIcon);
	        js.executeScript("arguments[0].click();", cartIcon);
	        System.out.println("Cart icon clicked.");
	        Thread.sleep(2000);

	        // Step 2: Wait for delete icon
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//i[contains(@class,'lp--delete')]")
	        ));

	        // Step 3: Click delete icon
	        js.executeScript("arguments[0].scrollIntoView({block:'center'});", deleteIcon);
	        Thread.sleep(500);
	        js.executeScript("arguments[0].click();", deleteIcon);
	        System.out.println("Product deleted from cart.");
	        Thread.sleep(1000);
	    }

	    // ── Click Place Order ────────────────────────────────────────────────
	    public void clickonPlaceOrderButton() {
	        waitutils.waitForClickability(placeOrder);
	        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrder);
	        js.executeScript("arguments[0].click();", placeOrder);
	    }

	    // ── MAIN VALIDATION FOR INTERNATIONAL ADDRESS ────────────────────────
	    //
	    // Rule: International address → COD is NEVER available
	    //
	    // CONDITION 1: Place Order DISABLED → PASS (COD correctly unavailable)
	    //              → remove product from cart → stop
	    //
	    // CONDITION 2: Place Order ENABLED  → FAIL (COD should NOT be available)
	    //              → remove product from cart → Assert.fail
	    // ────────────────────────────────────────────────────────────────────
	    public void validateCODForInternationalAddress(int amount, ExcelUtils excel, int row) throws InterruptedException, IOException {

	        boolean isDisabled = isPlaceOrderDisabled();

	        System.out.println("─────────────────────────────────────────");
	        System.out.println("Address Type      : INTERNATIONAL");
	        System.out.println("Amount            : " + amount);
	        System.out.println("Expected COD      : DISABLED (COD never available for International)");
	        System.out.println("Place Order button: " + (isDisabled ? "DISABLED" : "ENABLED"));
	        System.out.println("─────────────────────────────────────────");

	        if (isDisabled) {

	            // ✅ CONDITION 1: Place Order DISABLED → PASS
	            System.out.println("─────────────────────────────────────────");
	            System.out.println("CONDITION  : International Address — COD should be DISABLED");
	            System.out.println("EXPECTED   : Place Order button DISABLED");
	            System.out.println("ACTUAL     : Place Order button is DISABLED");
	            System.out.println("RESULT     : PASS ✅ — COD correctly unavailable for International Address");
	            System.out.println("─────────────────────────────────────────");
	            clickCartIconAndDeleteProduct(); // remove product and stop
	            excel.setCellValue(row, 8, "Pass");
		        excel.setCellColor(row, 8, IndexedColors.GREEN.getIndex());
		        excel.flush();

	        } else {

	            // ❌ CONDITION 2: Place Order ENABLED → FAIL
	            System.out.println("─────────────────────────────────────────");
	            System.out.println("CONDITION  : International Address — COD should be DISABLED");
	            System.out.println("EXPECTED   : Place Order button DISABLED");
	            System.out.println("ACTUAL     : Place Order button is ENABLED");
	            System.out.println("RESULT     : FAIL ❌ — COD should NOT be available for International Address");
	            System.out.println("─────────────────────────────────────────");
	            clickCartIconAndDeleteProduct(); // clean up cart even on fail
	            Assert.fail("FAIL: International Address — Place Order should be DISABLED but is ENABLED for amount: " + amount);
	            excel.setCellValue(row, 8, " — COD should be available but is not.");
		        excel.setCellColor(row, 8, IndexedColors.RED.getIndex());
		        excel. flush();
	
	        }
	    }

}
