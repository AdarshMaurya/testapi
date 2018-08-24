package com.softhinkers.script.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.softhinkers.script.core.BasicAuthInterceptor;
import com.softhinkers.script.utils.ReadTextFile;
import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {
    private static Properties config = null;
    private static boolean isInitalized = false;
    public static Logger log = null;
    public static OkHttpClient client = null;
    public static List<String> urlList = null;

    protected BaseTest() {
        if (!isInitalized) {
            initLogs();
            initConfig();
            initUrlFromEndPointFile();
            initClient();
        }
    }

    @BeforeClass
    public void setup() {
        log.info("Before class initialized ");
    }

    /**
     * Initialize Logger.
     */
    private static void initLogs() {
        if (log == null) {
            // Initialize Log4j logs
            DOMConfigurator.configure(System.getProperty("user.dir") + File.separator + "config" + File.separator + "log4j.xml");
            log = Logger.getLogger("MyLogger");
            log.info("Logger is initialized..");
        }
    }

    private static void initConfig() {
        if (config == null) {
            try {
                //initialize config properties file
                config = new Properties();
                String config_fileName = "config.properties";
                String config_path = System.getProperty("user.dir") + File.separator + "config" + File.separator + config_fileName;
                FileInputStream config_ip = null;

                config_ip = new FileInputStream(config_path);
                config.load(config_ip);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initClient() {
        try {
            client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(config.getProperty("user"), config.getProperty("password")))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initUrlFromEndPointFile() {
        if ("true".equals(config.getProperty("urlEndPoints")) && config.getProperty("urlEndPoints") != null) {
            if (urlList == null) {
                try {
                    String config_fileName = "urlEndPoints.txt";
                    String config_path = System.getProperty("user.dir") + File.separator + "config" + File.separator + config_fileName;
                    urlList = new ReadTextFile(config_path).getListUrlString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @AfterClass
    public void teardown() {
        log.info("After class tear down initialized");
    }

}

