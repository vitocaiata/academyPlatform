package com.caiata.web;

import com.caiata.Utility;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Properties;

import static com.caiata.GlobalParameters.CHROME_DRIVER_PATH;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test_WEB_001 {

    static Properties webProp = null;
    static ChromeDriver driver;

    @BeforeAll
    static void beforeAll(){
        webProp = new Utility().loadProp("web");
        System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_PATH);
        driver = new ChromeDriver();

    }

    @BeforeEach
    void beforeEach(){
    }

    @Test
    @DisplayName("test 001")
    void test_001(){
        //new ChromeOptions().setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver.get(webProp.getProperty("google.url"));
        //driver.quit();
    }

    @AfterEach
    void tearDown(){

    }

    @AfterAll
    void tearDownAll(){}
}
