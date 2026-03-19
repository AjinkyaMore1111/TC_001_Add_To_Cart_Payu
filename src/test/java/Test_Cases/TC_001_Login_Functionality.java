package Test_Cases;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import PageObject.LoginPage;
import baseData.BaseData;

@Listeners(Utilities.Listener.class)	
public class TC_001_Login_Functionality extends BaseData  {

	@Test
	public void login() throws InterruptedException {
		
		 System.out.println("Driver inherited from BaseData ✅");
	        LoginPage LP = new LoginPage(driver);

	        // ✅ Increased sleep for page load
	        Thread.sleep(8000);
	        LP.ClickOnUserIcon();

	        // ✅ Wait handled inside LoginPage methods now
	        LP.Enter_Mobile_No("9960130048");

	        LP.clickonContinue();

	        LP.Enter_Password(746587);

	        LP.clickLogin1();

	        Thread.sleep(3000);
  

      
    }
}	

