package Utilities;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtilities {
	
	
	 private WebDriver driver;

	    // Default timeout and polling constants (override as needed)
	    private static final int DEFAULT_EXPLICIT_TIMEOUT  = 20;  // seconds
	    private static final int DEFAULT_FLUENT_TIMEOUT    = 30;  // seconds
	    private static final int DEFAULT_POLLING_INTERVAL  = 500; // milliseconds
	    private static final int DEFAULT_IMPLICIT_TIMEOUT  = 10;  // seconds

	    public WaitUtilities(WebDriver driver) {
	        this.driver = driver;
	    }

	    // ─────────────────────────────────────────────────────────────
	    // 1. IMPLICIT WAIT
	    // ─────────────────────────────────────────────────────────────

	    /**
	     * Sets implicit wait with default timeout (10 sec).
	     * Apply once after driver initialization — applies globally.
	     */
	    public void setImplicitWait() {
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_TIMEOUT));
	    }

	    /**
	     * Sets implicit wait with a custom timeout in seconds.
	     */
	    public void setImplicitWait(int timeoutInSeconds) {
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeoutInSeconds));
	    }

	    /**
	     * Removes implicit wait (sets to 0) — useful before using Explicit/Fluent wait.
	     */
	    public void removeImplicitWait() {
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	    }

	    // ─────────────────────────────────────────────────────────────
	    // 2. EXPLICIT WAIT
	    // ─────────────────────────────────────────────────────────────

	    /**
	     * Waits until the element located by the given locator is VISIBLE.
	     */
	    public WebElement waitForVisibility(By locator) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }

	    /**
	     * Waits until the given WebElement is VISIBLE.
	     */
	    public WebElement waitForVisibility(WebElement element) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.visibilityOf(element));
	    }

	    /**
	     * Waits until the element located by the given locator is CLICKABLE.
	     */
	    public WebElement waitForClickability(By locator) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.elementToBeClickable(locator));
	    }

	    /**
	     * Waits until the given WebElement is CLICKABLE.
	     */
	    public WebElement waitForClickability(WebElement element) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.elementToBeClickable(element));
	    }

	    /**
	     * Waits until the element located by the given locator is PRESENT in DOM.
	     */
	    public WebElement waitForPresence(By locator) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	    }

	    /**
	     * Waits until the element located by the given locator becomes INVISIBLE.
	     */
	    public boolean waitForInvisibility(By locator) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	    }

	    /**
	     * Waits until the page title CONTAINS the given text.
	     */
	    public boolean waitForTitleContains(String titleText) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.titleContains(titleText));
	    }

	    /**
	     * Waits until the URL CONTAINS the given fraction.
	     */
	    public boolean waitForUrlContains(String urlFraction) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_EXPLICIT_TIMEOUT));
	        return wait.until(ExpectedConditions.urlContains(urlFraction));
	    }

	    /**
	     * Explicit wait with a CUSTOM timeout in seconds.
	     */
	    public WebElement waitForVisibility(By locator, int timeoutInSeconds) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }

	    // ─────────────────────────────────────────────────────────────
	    // 3. FLUENT WAIT
	    // ─────────────────────────────────────────────────────────────

	    /**
	     * Fluent wait for an element with default timeout (30s) and polling (500ms).
	     * Ignores NoSuchElementException during polling.
	     */
	    public WebElement fluentWaitForElement(By locator) {
	        Wait<WebDriver> wait = new FluentWait<>(driver)
	                .withTimeout(Duration.ofSeconds(DEFAULT_FLUENT_TIMEOUT))
	                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING_INTERVAL))
	                .ignoring(NoSuchElementException.class)
	                .withMessage("Element not found after " + DEFAULT_FLUENT_TIMEOUT + "s: " + locator);

	        return wait.until(drv -> drv.findElement(locator));
	    }

	    /**
	     * Fluent wait for an element with CUSTOM timeout and polling interval.
	     */
	    public WebElement fluentWaitForElement(By locator, int timeoutInSeconds, int pollingInMillis) {
	        Wait<WebDriver> wait = new FluentWait<>(driver)
	                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
	                .pollingEvery(Duration.ofMillis(pollingInMillis))
	                .ignoring(NoSuchElementException.class)
	                .withMessage("Element not found after " + timeoutInSeconds + "s: " + locator);

	        return wait.until(drv -> drv.findElement(locator));
	    }

	    /**
	     * Fluent wait for element VISIBILITY with custom timeout and polling.
	     */
	    public WebElement fluentWaitForVisibility(By locator, int timeoutInSeconds, int pollingInMillis) {
	        Wait<WebDriver> wait = new FluentWait<>(driver)
	                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
	                .pollingEvery(Duration.ofMillis(pollingInMillis))
	                .ignoring(NoSuchElementException.class)
	                .withMessage("Element not visible after " + timeoutInSeconds + "s: " + locator);

	        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }

	    /**
	     * Generic fluent wait — pass any custom condition as a lambda.
	     * Example: fluentWait(drv -> drv.findElement(By.id("id")).isDisplayed())
	     */
	    public <T> T fluentWait(Function<WebDriver, T> condition) {
	        Wait<WebDriver> wait = new FluentWait<>(driver)
	                .withTimeout(Duration.ofSeconds(DEFAULT_FLUENT_TIMEOUT))
	                .pollingEvery(Duration.ofMillis(DEFAULT_POLLING_INTERVAL))
	                .ignoring(NoSuchElementException.class);

	        return wait.until(condition);
	    }}
