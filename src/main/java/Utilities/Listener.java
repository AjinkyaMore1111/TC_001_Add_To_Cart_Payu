package Utilities;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listener implements ITestListener{
	
	
	ExtentReports extent=ExtentReport.getExtentReports();
	
	ExtentTest Test;
	@Override
	public void onTestStart(ITestResult result)
	{
		Test=extent.createTest(result.getName());
	}
    @Override
	public void onTestSuccess(ITestResult result)
	{
		Test.log(Status.PASS,"Test Case Passed");
	}
	@Override
	public void onTestSkipped(ITestResult result)
	{
		Test.log(Status.SKIP,"Test Case Skipped");
	}
	@Override
	public void onTestFailure(ITestResult result)
	{
		Test.log(Status.FAIL,"Test Case Failed");
	}
	@Override
	public void onFinish(org.testng.ITestContext context)
	{
		extent.flush();
	}
}
