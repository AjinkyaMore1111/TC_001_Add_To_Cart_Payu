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

public class CODValidation {

    WebDriver driver;
    WaitUtilities waitutils;
    WebDriverWait wait;
    JavascriptExecutor js;

    public CODValidation(WebDriver driver) {
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

    // ── Click Place Order ────────────────────────────────────────────────
    public void clickonPlaceOrderButton() {
        waitutils.waitForClickability(placeOrder);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", placeOrder);
        js.executeScript("arguments[0].click();", placeOrder);
    }

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
    public boolean isCODEligible(int amount) {
        return (amount >= 10000 && amount <= 40000);
    }

    // ── NEW: Click cart icon → wait for cart → click delete icon ─────────
    public void clickCartIconAndDeleteProduct() throws InterruptedException {
        System.out.println("Place Order DISABLED — navigating to cart to remove product.");

        // Step 1: Click cart icon (top right)
        waitutils.waitForClickability(cartIcon);
        js.executeScript("arguments[0].click();", cartIcon);
        System.out.println("Cart icon clicked.");
        Thread.sleep(2000);

        // Step 2: Wait for delete icon to be visible on product card
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//i[contains(@class,'lp--delete')]")
        ));

        // Step 3: Scroll to delete icon and click it
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", deleteIcon);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", deleteIcon);
        System.out.println("Delete icon clicked — product removed from cart.");
        Thread.sleep(1000);
    }

    // ── MAIN VALIDATION ──────────────────────────────────────────────────
    public void validateCODAndPlaceOrder(int amount) throws InterruptedException {

        System.out.println("─────────────────────────────────────────");
        System.out.println("Amount : " + amount);
        System.out.println("─────────────────────────────────────────");

        boolean expectedCOD = isCODEligible(amount);

        if (isPlaceOrderDisabled()) {

            if (!expectedCOD) {
                // ✅ Disabled and expected to be disabled → PASS → remove product
                System.out.println("PASS: Place Order DISABLED for amount: " + amount
                    + " — COD correctly unavailable.");
                clickCartIconAndDeleteProduct(); // go to cart and delete product

            } else {
                // ❌ Disabled but should be enabled → FAIL
                System.out.println("FAIL: Place Order DISABLED for amount: " + amount
                    + " — COD should be available but is not.");
                clickCartIconAndDeleteProduct(); // clean up cart even on fail
                Assert.fail("Place Order button should be ENABLED for amount "
                    + amount + " but it is DISABLED.");
            }

        } else {

            if (expectedCOD) {
                // Enabled and expected to be enabled → PASS → place order
                System.out.println("PASS: Place Order ENABLED for amount: " + amount
                    + " — Proceeding to place order.");
                clickonPlaceOrderButton();

            } else {
                // Enabled but should be disabled → FAIL
                System.out.println("FAIL: Place Order ENABLED for amount: " + amount
                    + " — COD should NOT be available.");
                Assert.fail("Place Order button should be DISABLED for amount "
                    + amount + " but it is ENABLED.");
            }
        }
    }
}


