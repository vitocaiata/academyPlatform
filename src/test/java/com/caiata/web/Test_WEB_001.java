package com.caiata.web;

import com.caiata.ManagementDriver;
import com.caiata.Utility;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Properties;

import static com.caiata.GlobalParameters.CHROME_DRIVER_PATH;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test_WEB_001 {

    static Properties webProp = null;


    @BeforeAll
    static void beforeAll(){
        webProp = new Utility().loadProp("web");
        ManagementDriver.startDriver();

    }

    @BeforeEach
    void beforeEach(){
    }

    @Test
    @DisplayName("test 001")
    void test_001(){
        ManagementDriver.getDriver().get(webProp.getProperty("google.url"));
    }

    @AfterEach
    void tearDown(){ }

    @AfterAll
    void tearDownAll(){
        ManagementDriver.stopDriver();
    }

}
