package Test_Cases;




import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;

import PageObject.AddToCart;

import baseData.BaseData;


@Listeners(Utilities.Listener.class)
public class TC_001_Add_To_Cart_COD extends BaseData  {


@Test(priority=1)	
public void AddTocartToCOD()
{
	
AddToCart AD=new AddToCart(driver);
AD.clickSearchbox();
new WebDriverWait(driver, Duration.ofSeconds(10))
    .until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
AD.EnterKeyword();

} 
     
     
}
