package Test_Cases;

	import java.io.IOException;
	import org.apache.poi.ss.usermodel.IndexedColors;
	import org.testng.annotations.Listeners;
	import org.testng.annotations.Test;
	import PageObject.CODCredential;
import PageObject.CODValidation;
import PageObject.International_Address;
    import PageObject.SearchProduct;
	import Utilities.ExcelUtils;
	import baseData.BaseData;

	@Listeners(Utilities.Listener.class)
	public class TC_002_Add_to_Cart_International_COD_Sandeep extends BaseData {

	    static final String EXCEL_PATH =
	        System.getProperty("user.dir") + "\\src\\main\\Test_cases\\LuxepolisTest_Cases.xlsx";

	@Test(priority = 1, groups = {"International"})
	public void ADDToCartCOD1() throws InterruptedException, IOException { 

	    ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
	    excel.useSheet(0);

	    String productKeyword = excel.getCellValue(41, 4); // col E = SKU

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
	        International_Address IA = new International_Address(driver);
	        IA.convertToUSD();
	        Thread.sleep(3000);
	        IA.selectUnitedStates();
	        Thread.sleep(3000);
	        IA.ClickOnAddtocart();
	        IA.carticonClick();
	        IA.checkout();
	        Thread.sleep(3000);
	        IA.ScrollDownPage();
	        IA.radiobutton();
	        Thread.sleep(3000);
	        IA.AddressCheckout();
	        Thread.sleep(3000);
	        

	        // ── 3. Validate COD ─────────────────────────────────────────
	        // If Place Order is DISABLED:  → clicks cart icon → deletes product → marks Pass/Fail 
	        // If Place Order is ENABLED: → clicks Place Order → marks Pass
	        CODValidation COD = new CODValidation(driver);
	        COD.validateCODAndPlaceOrder(132); // ← only change needed here

	        // ── 4. Write Pass to Excel ───────────────────────────────────
	        excel.setCellValue(41, 7, "Pass");
	        excel.setCellColor(41, 7, IndexedColors.GREEN.getIndex());
	        excel.setCellValue(41, 6, "COD option showing disabled");
	        excel.setCellColor(41, 6, IndexedColors.GREEN.getIndex());
	        excel.flush();

	    } catch (Exception e) {
	        try {
	            excel.setCellValue(41, 7, "Fail");
	            excel.setCellColor(41, 7, IndexedColors.RED.getIndex());
	            excel.setCellValue(41, 6, "COD option showing enable");
	            excel.setCellColor(41, 6, IndexedColors.GREEN.getIndex());
	            excel.flush();
	        } catch (IOException ioEx) {
	            ioEx.printStackTrace();
	        }
	        throw e;
	    } finally {
	        excel.close();
	    }
	} 
	 
	    @Test(priority = 2 ,groups = {"International"})
		public void ADDToCartCOD2() throws InterruptedException, IOException {

		    ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
		    excel.useSheet(0);

		    String productKeyword = excel.getCellValue(42, 4); // col E = SKU

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
		        International_Address IA = new International_Address(driver);
		        /*IA.convertToUSD();
		        Thread.sleep(3000);
		        IA.selectUnitedStates();
		        Thread.sleep(3000);*/
		        IA.ClickOnAddtocart();
		        IA.carticonClick();
		        IA.checkout();
		        Thread.sleep(3000);
		        IA.ScrollDownPage();
		        IA.radiobutton();
		        Thread.sleep(3000);
		        IA.AddressCheckout();
		        Thread.sleep(3000);
		        

		        // ── 3. Validate COD ─────────────────────────────────────────
		        // If Place Order is DISABLED: → clicks cart icon → deletes product → marks Pass/Fail  
		        // If Place Order is ENABLED: → clicks Place Order → marks Pass
		        
		        CODValidation COD = new CODValidation(driver);
		        COD.validateCODAndPlaceOrder(144); // ← only change needed here

		        // ── 4. Write Pass to Excel ───────────────────────────────────
		        excel.setCellValue(42, 7, "Pass");
		        excel.setCellColor(42, 7, IndexedColors.GREEN.getIndex());
		        excel.setCellValue(42, 6, "COD option showing disabled");
		        excel.setCellColor(42, 6, IndexedColors.GREEN.getIndex());
		        excel.flush();

		    } catch (Exception e) {
		        try {
		            excel.setCellValue(42, 7, "Fail");
		            excel.setCellColor(42, 7, IndexedColors.RED.getIndex());
		            excel.setCellValue(42, 6, "COD option showing enable");
		            excel.setCellColor(42, 6, IndexedColors.GREEN.getIndex());
		            excel.flush();
		        } catch (IOException ioEx) {
		            ioEx.printStackTrace();
		        }
		        throw e;
		    } finally {
		        excel.close();
		    }
		}

	@Test(priority = 3, groups = {"International"})
	public void ADDToCartCOD3() throws InterruptedException, IOException {

	    ExcelUtils excel = new ExcelUtils(EXCEL_PATH);
	    excel.useSheet(0);

	    String productKeyword = excel.getCellValue(43, 4); // col E = SKU

	    try {
	        // ── 1. Search Product ───────────────────────────────────────
	    	Thread.sleep(5000);
	    	SearchProduct SP = new SearchProduct(driver);
	        SP.clickSearchbox();
	        Thread.sleep(3000);
	        SP.EnterKeyword(productKeyword);
	        Thread.sleep(4000);
	        SP.ClickOnSearchResult();
	        Thread.sleep(4000);

	        // ── 2. Add to Cart & Checkout ───────────────────────────────
	        International_Address IA = new International_Address(driver);
	        IA.ClickOnAddtocart();
	        IA.carticonClick();
	        IA.checkout();
	        Thread.sleep(3000);
	        IA.ScrollDownPage();
	        IA.radiobutton();
	        Thread.sleep(3000);
	        IA.AddressCheckout();
	        Thread.sleep(3000);

	        // ── 3. Validate COD ─────────────────────────────────────────
	        // If Place Order is DISABLED:  → clicks cart icon → deletes product → marks Pass/Fail
	        // If Place Order is ENABLED: → clicks Place Order → marks Pass  
	        CODValidation COD = new CODValidation(driver);
	        COD.validateCODAndPlaceOrder(41462); // ← only change needed here

	        // ── 4. Write Pass to Excel ───────────────────────────────────
	        excel.setCellValue(43, 7, "Pass");
	        excel.setCellColor(43, 7, IndexedColors.GREEN.getIndex());
	        excel.flush();

	    } catch (Exception e) {
	        try {
	            excel.setCellValue(43, 7, "Fail");
	            excel.setCellColor(43, 7, IndexedColors.RED.getIndex());
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

