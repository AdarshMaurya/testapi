package com.softhinkers.script.sanitysuite;

import com.softhinkers.script.base.TestBase;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.softhinkers.script.util.Constants.TEST_FOO;
import static com.softhinkers.script.util.XmlParser.checkXml;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class SampleTest extends TestBase {

    Request request = null;
    Response response = null;

    @AfterMethod
    public void setUp(){

    }

    @AfterMethod
    public void responseClose() {
        response.body().close();
    }

    @Test(priority = 1, description = "Fetch Xml File")
    public void fetchXmlFile() throws IOException {
        log.info("Fetch Xml File.");
        //for(int i =0;i<=10; i++) {
        request = new Request.Builder().url(TEST_FOO).build();
        response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            fail("Response code " + response.code());
            return;
        }
        log.info("Response code " + response.code());
        String xml = response.body().string();
        assertEquals(checkXml(xml,"firstname", "sample_name"),true);

        // }
    }
}
