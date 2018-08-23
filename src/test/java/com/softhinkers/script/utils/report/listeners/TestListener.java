package com.softhinkers.script.utils.report.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.softhinkers.script.base.BaseTest;
import com.softhinkers.script.utils.report.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends BaseTest implements ITestListener {

    //Extent Report Declarations
    private static ExtentReports extent = ExtentManager.createInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        log.info("Reporting started.");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        log.info("Reporting finished.");
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " passed!");
        test.get().pass("Test passed");
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " failed!");
        test.get().fail(result.getThrowable());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " skipped!");
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
    }

    public static ThreadLocal<ExtentTest> getTest() {
        return test;
    }
}