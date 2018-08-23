package com.softhinkers.script.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.softhinkers.script.core.BasicAuthInterceptor;
import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseTest {
    private static Properties config = null;
    private static boolean isInitalized = false;
    public static Logger log = null;
    public static OkHttpClient client = null;

    protected BaseTest() {
        if (!isInitalized) {
            initLogs();
            initConfig();
            initClient();
        }
    }

    @BeforeClass
    public void setup () {
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
            System.exit(0);
        }
    }

    @AfterClass
    public void teardown () {
        log.info("After class tear down initialized");
    }

}

