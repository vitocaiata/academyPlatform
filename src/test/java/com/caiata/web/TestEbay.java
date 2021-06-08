package com.caiata.web;

import com.caiata.steps.EbaySteps;
import com.caiata.utils.DefaulChromeOptions;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.ModelloEbay;
import com.caiata.utils.Utility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;



import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEbay {

    static private ModelloEbay modello = new ModelloEbay();
    static private WebDriver driver = null;
    static private Properties webProp = null;
    static private EbaySteps steps = null;
    static private DefaulChromeOptions defaulChromeOptions;
    static private boolean mobile = false;
    private float val = 0;

    @BeforeAll
    static void beforeAll() {
        ManagementDriver.setMobile(true);
        mobile = ManagementDriver.isMobile();

        defaulChromeOptions = new DefaulChromeOptions(new ChromeOptions());

        if(mobile){
            defaulChromeOptions.addArguments("--window-size=375,812");
            defaulChromeOptions.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1");
        }

        webProp = new Utility().loadProp("mobileEbay");
        ManagementDriver.startDriver(defaulChromeOptions);
        driver = ManagementDriver.getDriver();
        steps = new EbaySteps();
    }

    @BeforeEach
    void beforeEach() {
    }

    @Order(7)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone,3"})
    @Tag("mobile")
    @DisplayName("controllare esistenza prodotto ricercato e muovi tra le pagine")
    void test_007(String q, String pgn) throws InterruptedException {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.search(webProp, q);
        for(int i = 1; i < Integer.parseInt(pgn); i++) {
            driver.findElement(By.className(webProp.getProperty("class.btn.next"))).click();
            Thread.sleep(4000);
            new Utility().getScreen();
            assertTrue(driver.getCurrentUrl().contains(String.valueOf(i+1)));
        }
        for(int i = Integer.parseInt(pgn); i > 1; i--) {
            Thread.sleep(4000);
            driver.findElement(By.className(webProp.getProperty("class.btn.previous"))).click();
            assertTrue(driver.getCurrentUrl().contains(String.valueOf(i-1)));
        }
    }

    @Order(1)
    @ParameterizedTest(name = "q = {0}")
    @ValueSource(strings = {"iphone","ipad"})
    @DisplayName("controllare esistenza prodotto ricercato")
    void test_001(String q) {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.search(webProp, q);
        if(!mobile) {
            String result = driver.findElement(By.xpath(webProp.getProperty("xpath.span.result"))).getText();
            System.out.println(q + " Trovati: " + result);
            if (result.length() < 1) {
                fail(q + "Risultato non presente.");
            }
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

    @Order(8)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone"})
    @Tag("mobile")
    @DisplayName("Stampa di tutti i risultati.")
    void test_008(String q) {
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.search(webProp, q);
        modello.stampaElementi(steps.getElementiMobile(webProp));
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

    @Order(9)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"pluto , cognome, semoh62083@jmpant.com, A12345!"})
    @Tag("mobile")
    @DisplayName("registrazione su ebay")
    void test_009(String nome, String cognome, String email, String password) throws InterruptedException {
        driver.get(webProp.getProperty("ebay.url"));
        //steps.closeBanner(webProp);
        Thread.sleep(2000);
        driver.findElement(By.xpath(webProp.getProperty("menu"))).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath(webProp.getProperty("button.register"))).click();
        Thread.sleep(3000);
        driver.findElement(By.id(webProp.getProperty("first.name"))).sendKeys(nome);
        Thread.sleep(3000);
        driver.findElement(By.id(webProp.getProperty("last.name"))).sendKeys(cognome);
        Thread.sleep(3000);
        driver.findElement(By.id(webProp.getProperty("email.id"))).sendKeys(email);
        Thread.sleep(3000);
        driver.findElement(By.id(webProp.getProperty("password.id"))).sendKeys(password);
        Thread.sleep(3000);
        driver.findElement(By.xpath(webProp.getProperty("button.create.account"))).click();
    }

    @Order(10)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone"})
    @DisplayName("aggiungi al carrello")
    void test_010(String q) throws InterruptedException {
        driver.get(webProp.getProperty("ebay.url"));
        //steps.closeBanner(webProp);
        steps.search(webProp, q);
        boolean r = steps.addCart(webProp);
        Thread.sleep(2000);
        assertTrue(r);
    }

    @Order(10)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone"})
    @DisplayName("aggiungi al carrello")
    void test_011(String q) throws InterruptedException {
        driver.get(webProp.getProperty("ebay.url"));
        //steps.closeBanner(webProp);
        steps.search(webProp, q);
        steps.add(webProp);
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
        //ManagementDriver.stopDriver();
    }
}
