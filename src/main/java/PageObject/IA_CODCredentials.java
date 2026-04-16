package PageObject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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

    // ── Currency selector (e.g. "INR" / "USD" top left of site) ─────────
    // Update this XPath to match the actual currency element on the UAT site
    @FindBy(xpath = "//*[contains(@class,'currency') or contains(@id,'currency')]")
    WebElement currencyEl;

    // Get current selected currency from the page
    public String getCurrentCurrency() {
        try {
            String currency = currencyEl.getText().trim().toUpperCase();
            System.out.println("Current Currency : " + currency);
            return currency;
        } catch (Exception e) {
            System.out.println("Could not detect currency, defaulting to INR.");
            return "INR";
        }
    }

    // Check if Place Order button is disabled
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

    // Business Rule
    // USD  → COD never available
    // INR  → COD available only between 10,000 and 40,000
    public boolean isCODEligible(int amount) {
        String currency = getCurrentCurrency();

        if (currency.contains("USD")) {
            System.out.println("Currency is USD — COD is NOT available.");
            return false; // USD users never get COD
        }

        // INR — amount must be within range
        return (amount >= 10000 && amount <= 40000);
    }

    
    // Click cart icon → wait for cart to open → click delete icon
    // Called whenever Place Order is disabled (USD or out-of-range INR)
    public void clickCartIconAndDeleteProduct() throws InterruptedException {
        System.out.println(">>> Navigating to cart to remove product...");

        // Step 1: Click cart icon
        waitutils.waitForClickability(cartIcon);
        js.executeScript("arguments[0].click();", cartIcon);
        System.out.println("Cart icon clicked.");
        Thread.sleep(2000);

        // Step 2: Wait for delete icon to be visible
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

    
    // Click Place Order
    public void clickonPlaceOrderButton() {
        waitutils.waitForClickability(placeOrder);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrder);
        js.executeScript("arguments[0].click();", placeOrder);
    }

    // MAIN VALIDATION
    //
    // USD user: → COD not available → Place Order disabled
    //           → ✅ PASS → remove product from cart → stop
 
    // INR user, amount < 10,000 or > 40,000: → COD not available → Place Order disabled
    //                                        → ✅ PASS → remove product from cart → stop
 
    // INR user, 10,000 ≤ amount ≤ 40,000:  → COD available → Place Order enabled
    //                                      → ✅ PASS → click Place Order → continue
    public void validateCODAndPlaceOrder(int amount) throws InterruptedException {

        String currency    = getCurrentCurrency();
        boolean expectedCOD = isCODEligible(amount);
        boolean isDisabled  = isPlaceOrderDisabled();

        System.out.println("─────────────────────────────────────────────");
        System.out.println("Currency          : " + currency);
        System.out.println("Amount            : " + amount);
        System.out.println("Expected COD      : " + (expectedCOD ? "ENABLED"  : "DISABLED"));
        System.out.println("Place Order button: " + (isDisabled  ? "DISABLED" : "ENABLED"));
        System.out.println("─────────────────────────────────────────────");

        // ── USD: COD never available ─────────────────────────────────────
        if (currency.contains("USD")) {

            if (isDisabled) {
                System.out.println("✅ PASS: USD user — COD correctly NOT available."
                    + " Place Order is DISABLED.");
                clickCartIconAndDeleteProduct(); // remove product and stop

            } else {
                System.out.println("❌ FAIL: USD user — COD should NOT be available"
                    + " but Place Order is ENABLED.");
                clickCartIconAndDeleteProduct(); // clean up
                Assert.fail("USD user — Place Order should be DISABLED but is ENABLED.");
            }

        }

        // ── INR: amount outside 10,000–40,000 ───────────────────────────
        else if (!expectedCOD) {

            if (isDisabled) {
                System.out.println("✅ PASS: INR amount " + amount
                    + " outside range — COD correctly DISABLED.");
                clickCartIconAndDeleteProduct(); // remove product and stop

            } else {
                System.out.println("❌ FAIL: INR amount " + amount
                    + " outside range — COD should be DISABLED but is ENABLED.");
                clickCartIconAndDeleteProduct(); // clean up
                Assert.fail("Place Order should be DISABLED for amount "
                    + amount + " but is ENABLED.");
            }

        }

        // ── INR: amount within 10,000–40,000 ────────────────────────────
        else {

            if (!isDisabled) {
                System.out.println("✅ PASS: INR amount " + amount
                    + " within range — COD correctly ENABLED. Placing order.");
                clickonPlaceOrderButton(); // proceed to place order

            } else {
                System.out.println("❌ FAIL: INR amount " + amount
                    + " within range — COD should be ENABLED but is DISABLED.");
                clickCartIconAndDeleteProduct(); // clean up
                Assert.fail("Place Order should be ENABLED for amount "
                    + amount + " but is DISABLED.");
            }
        }
    }
}
