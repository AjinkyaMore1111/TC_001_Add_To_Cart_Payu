package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {
	
	public static ExtentReports extent;
	
	public static ExtentReports getExtentReports()
	{
		
		ExtentSparkReporter Report=new ExtentSparkReporter("C:\\Users\\ajinkya.more\\eclipse-workspaceAjinkya\\Ajinkya7-3\\luxepolis-test-framework\\src\\test\\Luxepolis_TestCase_Report\\Luxepolis.html");
		extent =new ExtentReports();
		extent.attachReporter(Report);
		
		
		return extent;
		
	}

}
