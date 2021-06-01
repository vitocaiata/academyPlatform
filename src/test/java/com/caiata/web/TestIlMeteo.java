package com.caiata.web;

import com.caiata.EbaySteps;
import com.caiata.IlMeteoSteps;
import com.caiata.ManagementDriver;
import com.caiata.Utility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

public class TestIlMeteo{

        static private WebDriver driver = null;
        static private Properties webProp = null;
        static private IlMeteoSteps steps = null;

        @BeforeAll
        static void beforeAll() {
            webProp = new Utility().loadProp("ilmeteo");
            ManagementDriver.startDriver();
            driver = ManagementDriver.getDriver();
            steps = new IlMeteoSteps();
        }

        @BeforeEach
        void beforeEach() {
        }


        @Order(1)
        @ParameterizedTest(name = "q = {0}")
        @ValueSource(strings = {"Picerno"})
        @DisplayName("controllare esistenza prodotto ricercato")
        void test_003(String q) {
            driver.get(webProp.getProperty("ilmeteo.url"));
            steps.closeBanner(webProp);
            steps.search(webProp, q);
            String result = driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText();
            System.out.println(result);
            if(!result.contains(q.toUpperCase())){
                fail("Città non trovata.");
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
