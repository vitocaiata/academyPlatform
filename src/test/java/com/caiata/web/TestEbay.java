package com.caiata.web;

import com.caiata.steps.EbaySteps;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.ModelloEbay;
import com.caiata.utils.Utility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;


import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEbay {

    static private ModelloEbay modello = new ModelloEbay();
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
    void test_001(String q) {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBannerFW(webProp);
        steps.search(webProp, q);
        String result = driver.findElement(By.xpath(webProp.getProperty("xpath.span.result"))).getText();
        System.out.println(q + " Trovati: " + result);
        if(result.length() < 1){
            fail(q + "Risultato non presente.");
        }
    }

    @Order(2)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone, cell phone" , "ipad, tablet"})
    @DisplayName("controllare esistenza prodotto ricercato con lista")
    void test_002(String q, String categoria) {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.selezionaCategoria(webProp, categoria);
        steps.search(webProp,q);
        String risultato = driver.findElement(By.xpath(webProp.getProperty("xpath.category.result"))).getText();
        assertTrue(risultato.toLowerCase().contains(categoria));
    }

    @Order(3)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone, cell phone, xpath.category.selected.cell" , "ipad, tablet, xpath.category.selected.tablet"})
    @DisplayName("controllare selezione categoria")
    void test_003(String q, String categoria, String key) {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.selezionaCategoria(webProp, categoria);
        assertTrue(driver.findElement(By.xpath(webProp.getProperty(key))).isEnabled());
    }

    @Order(4)
    @Test
    @DisplayName("Stampa delle categorie.")
    void test_004(){
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        String[] tmp = steps.getMenuCategory(webProp);
        System.out.println("----CATEGORIE----");
        for(int i = 0; i < tmp.length ; i++){
            System.out.println(tmp[i]);
        }
    }

    @Order(5)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone, cell phone"})
    @DisplayName("Stampa di tutti i risultati.")
    void test_005(String q, String categoria) {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.selezionaCategoria(webProp, categoria);
        steps.search(webProp, q);
        modello.stampaElementi(steps.getElementi(webProp));
    }
    
    @Order(6)
    @Test
    @DisplayName("test colore")
    public void test_006(){
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        Color searchButtonBackgroundColour = Color.fromString(driver.findElement(By.id(webProp.getProperty("id.btn.search"))).getCssValue("background-color"));
        assert searchButtonBackgroundColour.asHex().equals("#3665f3");
    }




    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
        ManagementDriver.stopDriver();
    }
}
