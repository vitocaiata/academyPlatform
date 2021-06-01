package com.caiata.web;

import com.caiata.EbaySteps;
import com.caiata.ManagementDriver;
import com.caiata.Utility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

public class TestEbay {

    static private WebDriver driver = null;
    static private Properties webProp = null;
    static private EbaySteps steps = null;

    @BeforeAll
    static void beforeAll() {
        webProp = new Utility().loadProp("ebay");
        ManagementDriver.startDriver();
        driver = ManagementDriver.getDriver();
        steps = new EbaySteps();

    }

    @BeforeEach
    void beforeEach() {
    }


    @Order(1)
    @ParameterizedTest(name = "q = {0}")
    @ValueSource(strings = {"iphone","ipad"})
    @DisplayName("controllare esistenza prodotto ricercato")
    void test_003(String q) {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.search(webProp, q);
        String result = driver.findElement(By.xpath(webProp.getProperty("xpath.span.result"))).getText();
        System.out.println(q + " Trovati: " + result);
        if(result.length() < 1){
            fail(q + "Risultato non presente.");
        }
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
        //ManagementDriver.stopDriver();
    }
}
