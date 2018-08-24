package com.softhinkers.script.sanitysuite;

import com.softhinkers.script.base.BaseTest;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.softhinkers.script.utils.Constants.TEST_FOO;
import static com.softhinkers.script.utils.XmlParser.checkXml;
import static org.testng.Assert.fail;

public class SampleTest extends BaseTest {

    Request request = null;
    Response response = null;

    @BeforeMethod
    public void setUp() throws IOException {
        log.info("Before Method Set Up.");
        request = new Request.Builder().url(TEST_FOO).build();
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            fail("Response code " + response.code());
            return;
        }
    }

    @AfterMethod
    public void responseClose() {
        log.info("After Method Set Up.");
        response.body().close();
    }

    @Test(priority = 1, description = "Test to Fetch First Name")
    public void testToFetchFirstName() throws IOException {
        log.info("Test to Fetch First Name: " + response.code());
        String xml = response.body().string();
        checkXml(xml,"firstname", "sample_name");
    }

    @Test(priority = 1, description = "Test To Fetch Last Name")
    public void testToFetchLastName() throws IOException {
        log.info("Test To Fetch Last Name: " + response.code());
        log.info("Response code " );
        String xml = response.body().string();
        checkXml(xml,"lastname", "sample_names");
    }

    @Test(priority = 1, description = "Test To Fetch State Name")
    public void testToFetchStateName() throws IOException {
        log.info("Test To Fetch Last Name" + response.code());
        String xml = response.body().string();
        checkXml(xml,"state", "sample_state_name");
    }


}
