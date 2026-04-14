package Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listener implements ITestListener {

    private static final String EXCEL_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "Test_cases" + File.separator + "LuxepolisTest_Cases.xlsx";

    ExtentReports extent = ExtentReport.getExtentReports();
    ExtentTest Test;

    static int passCount = 0;
    static int failCount = 0;
    static int skipCount = 0;
    static List<String> failedTests = new ArrayList<>();

    @Override
    public void onTestStart(ITestResult result) {
        Test = extent.createTest(result.getName());
        Test.log(Status.INFO, "Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passCount++;
        Test.log(Status.PASS, "Test Case Passed ✅");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failCount++;
        String failInfo = "❌ " + result.getName()
            + " → " + result.getThrowable().getMessage();
        failedTests.add(failInfo);
        Test.log(Status.FAIL, "Test Case Failed ❌");
        Test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipCount++;
        Test.log(Status.SKIP, "Test Case Skipped ⚠️");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        // Flush report to disk first
        extent.flush();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        System.out.println("Report Generated: " + ExtentReport.ReportPath);

        // Send one combined email with Excel + HTML report
        String status = failCount > 0 ? "Fail" : "Pass";
        EmailUtils.sendExcelReport(EXCEL_PATH, ExtentReport.ReportPath, status);
    }
}
