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
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;

import static com.softhinkers.script.utils.Constants.BASE_URL;
import static com.softhinkers.script.utils.XmlParser.checkXml;
import static org.testng.Assert.fail;

public class SampleTest2 extends BaseTest {
    Request request = null;
    Response response = null;

    @BeforeMethod
    public void setUp() {
        log.info("Before Method Set Up.");
    }

    @Test(description = "Test to Fetch Last Name")// threadPoolSize=3
    public void fileTestToFetchLastName(ITestContext context, Method method) throws IOException {
        String xml = repeatTestGetXMLResponseBody(context, method, urlList);
        checkXml(xml, "lastname", "sample_name");
    }

    @Test(description = "Test to Fetch State Name")//threadPoolSize=3
    public void fileTestToFetchStateName(ITestContext context, Method method) throws IOException {
        String xml = repeatTestGetXMLResponseBody(context, method, urlList);
        checkXml(xml, "state", "sample_state_name");
    }

    @AfterMethod
    public void tearDown() {
        log.info("After Method Set Up.");
    }


    private String repeatTestGetXMLResponseBody(ITestContext context, Method method, List<String> urlList) throws IOException {
        String testUrl = null;
        if (urlList != null && urlList.size() > 1) {
            ITestNGMethod currentTestNGMethod = null;
            for (ITestNGMethod testNGMethod : context.getAllTestMethods()) {
                if (testNGMethod.getMethodName().equals(method.getName())) {
                    currentTestNGMethod = testNGMethod;
                    break;
                }
            }
            assert currentTestNGMethod != null;
            testUrl = BASE_URL + urlList.get(currentTestNGMethod.getCurrentInvocationCount());
        }
        return sendRequest(testUrl);
    }

    private String sendRequest(String testUrl) throws IOException {
        if (testUrl != null) {
            request = new Request.Builder().url(testUrl).build();
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                fail("Response code " + response.code());
                return null;
            }
            String responseBody = response.body().string();
            response.body().close();
            return responseBody;

        }
        return null;
    }
}
