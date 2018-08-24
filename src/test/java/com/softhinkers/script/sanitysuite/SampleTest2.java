package com.softhinkers.script.sanitysuite;

import com.softhinkers.script.base.BaseTest;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.softhinkers.script.utils.Constants.BASE_URL;
import static com.softhinkers.script.utils.XmlParser.checkXml;
import static org.testng.Assert.fail;

public class SampleTest2 extends BaseTest {

    Request request = null;
    Response response = null;
    String testUrl = null;

    @BeforeMethod
    public void setUp(ITestContext context) {
        log.info("Before Method Set Up.");
    }

    @Test(description = "Test to Fetch Last Name", invocationCount = 10)//, invocationCount=10, threadPoolSize=3
    public void testToFetchLastName(ITestContext context) throws IOException {
        invokeTestNumber(context, 0);

        request = new Request.Builder().url(testUrl).build();
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            fail("Response code " + response.code());
            return;
        }
        String xml = response.body().string();
        checkXml(xml, "lastname", "sample_name");

    }

    @Test(description = "Test to Fetch State Name", invocationCount = 10)//, invocationCount=10, threadPoolSize=3
    public void testToFetchStateName(ITestContext context) throws IOException {
        invokeTestNumber(context, 1);

        request = new Request.Builder().url(testUrl).build();
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            fail("Response code " + response.code());
            return;
        }
        String xml = response.body().string();
        checkXml(xml, "state", "sample_state_name");
    }

    @AfterMethod
    public void tearDown() {
        log.info("After Method Set Up.");
        response.body().close();
    }


    private void invokeTestNumber(ITestContext context, int testNumber) {
        int currentCount = context.getAllTestMethods()[testNumber].getCurrentInvocationCount();
        if (urlList != null) {
            testUrl = BASE_URL + urlList.get(currentCount);
        } else {
            testUrl = BASE_URL;
        }
    }

}
