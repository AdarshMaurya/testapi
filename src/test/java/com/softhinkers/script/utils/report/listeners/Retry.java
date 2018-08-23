package com.softhinkers.script.utils.report.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static com.softhinkers.script.utils.report.listeners.TestListener.getTest;

public class Retry implements IRetryAnalyzer {

    private int count = 0;
    private static int maxTry = 0; //Run the failed test 1 times

    @Override
    public boolean retry(ITestResult iTestResult) {
        System.out.println("Retrying to test failed test case again");
        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
            if (count < maxTry) {                            //Check if maxtry count is reached
                count++;                                     //Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed
                extendReportsFailOperations(iTestResult);    //ExtentReports fail operations
                return true;                                 //Tells TestNG to re-run the test
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
    }

    private void extendReportsFailOperations(ITestResult result) {
        getTest().get().fail(getTestMethodName(result));
    }

    private static String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }

}