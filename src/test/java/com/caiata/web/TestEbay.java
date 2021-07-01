package com.caiata.web;

import com.caiata.steps.EbaySteps;
import com.caiata.utils.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.caiata.utils.GlobalParameters.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEbay {

    static private ModelloEbay modello = new ModelloEbay();
    static private WebDriver driver = null;
    static private Properties webProp = null;
    static private EbaySteps steps = null;
    static private DefaulChromeOptions defaulChromeOptions;
    static private DefaultFirefoxOptions defaultFirefoxOptions;
    static private DefaultEdgeOptions defaultEdgeOptions;
    static private boolean mobile = false;
    static private boolean firefox = false;
    static private boolean edge = false;
    static private ExtentReports extentReports;
    static private ExtentTest extentTest;

    @BeforeAll
    static void beforeAll() {
        ManagementDriver.setMobile(true);
        mobile = ManagementDriver.isMobile();

        ManagementDriver.setFirefox(false);
        firefox = ManagementDriver.isFirefox();

        ManagementDriver.setEdge(true);
        edge = ManagementDriver.isEdge();

        if(edge){
            defaultEdgeOptions = new DefaultEdgeOptions(new EdgeOptions());
            if(mobile){
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Iphone X");
                defaultEdgeOptions.setCapability("mobileEmulation", mobileEmulation);
            }

            webProp = new Utility().loadProp("ebay");
            ManagementDriver.startEdgeDriver(defaultEdgeOptions);
            driver = ManagementDriver.getEdgeDriver();
            steps = new EbaySteps();

        }else if(firefox){
            defaultFirefoxOptions = new DefaultFirefoxOptions(new FirefoxOptions());
            if(mobile) {
                defaultFirefoxOptions.addArguments("--width=275");
                defaultFirefoxOptions.addArguments("--height=812");
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("general.useragent.override", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) FxiOS/33.0 Mobile/15E148 Safari/605.1.15");
                defaultFirefoxOptions.setProfile(firefoxProfile);
            }
            webProp = new Utility().loadProp("ebay");
            ManagementDriver.startFirefoxDriver(defaultFirefoxOptions);
            driver = ManagementDriver.getFirefoxDriver();
            steps = new EbaySteps();
        }else {
            defaulChromeOptions = new DefaulChromeOptions(new ChromeOptions());
            if (mobile) {
                defaulChromeOptions.addArguments("--window-size=375,812");
                defaulChromeOptions.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1");
                webProp = new Utility().loadProp("mobileEbay");
                ManagementDriver.startDriver(defaulChromeOptions);
                driver = ManagementDriver.getChromeDriver();
                steps = new EbaySteps();
            }
            webProp = new Utility().loadProp("ebay");
            ManagementDriver.startDriver(defaulChromeOptions);
            driver = ManagementDriver.getChromeDriver();
            steps = new EbaySteps();
        }

        extentReports = new ExtentReports(REPORT_PATH + File.separator + "report" + EXT_HTML, false);
        extentReports.loadConfig(new File(REPORT_CONFIG_XML));
    }

    @BeforeEach
    void beforeEach() {
    }

    @Order(1)
    @ParameterizedTest(name = "q = {0}")
    @ValueSource(strings = {"iphone","ipad"})
    @DisplayName("controllare esistenza prodotto ricercato")
    void test_001(String q, TestInfo testInfo) {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        steps.search(webProp, q);
        extentTest.log(LogStatus.INFO, "Faccio la ricerca.");
        if(!mobile) {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            String result = driver.findElement(By.cssSelector(".srp-controls__count-heading > span:nth-child(1)")).getText();
            System.out.println(q + " Trovati: " + result);
            if (result.length() < 1) {
                fail(q + "Risultato non presente.");
            }
        }
    }

    //----test che faccio fallire----
    @Order(2)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone, cell phone" , "ipad, tablet"})
    @DisplayName("controllare esistenza prodotto ricercato con lista")
    void test_002(String q, String categoria, TestInfo testInfo) {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        steps.selezionaCategoria(webProp, categoria);
        extentTest.log(LogStatus.INFO, "Seleziono la categoria,");
        steps.search(webProp,q);
        extentTest.log(LogStatus.INFO, "Faccio la ricerca,");
        String risultato = driver.findElement(By.xpath(webProp.getProperty("xpath.category.result"))).getText();
        assertFalse(risultato.toLowerCase().contains(categoria));
        extentTest.log(LogStatus.FAIL,"Failed",extentTest.addBase64ScreenShot(Utility.getScreenCast()));
    }

    @Order(3)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone, cell phone, xpath.category.selected.cell" , "ipad, tablet, xpath.category.selected.tablet"})
    @DisplayName("controllare selezione categoria")
    void test_003(String q, String categoria, String key,TestInfo testInfo) {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        steps.selezionaCategoria(webProp, categoria);
        extentTest.log(LogStatus.INFO, "Seleziono la categoria,");
        assertTrue(driver.findElement(By.xpath(webProp.getProperty(key))).isEnabled());
        extentTest.log(LogStatus.PASS, "OK");
    }

    @Order(4)
    @Test
    @DisplayName("Stampa delle categorie.")
    void test_004(TestInfo testInfo){
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        String[] tmp = steps.getMenuCategory(webProp);
        System.out.println("----CATEGORIE----");
        for(int i = 0; i < tmp.length ; i++){
            System.out.println(tmp[i]);
        }
        extentTest.log(LogStatus.PASS, "OK");
    }

    @Order(5)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone, cell phone"})
    @DisplayName("Stampa di tutti i risultati.")
    void test_005(String q, String categoria,TestInfo testInfo) {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        steps.selezionaCategoria(webProp, categoria);
        extentTest.log(LogStatus.INFO, "Seleziono la categoria,");
        steps.search(webProp, q);
        extentTest.log(LogStatus.INFO, "Faccio la ricerca,");
        modello.stampaElementi(steps.getElementi(webProp));
        extentTest.log(LogStatus.INFO, "Stampo tutti gli elementi trovati.");
        extentTest.log(LogStatus.PASS, "OK");
    }

    @Order(6)
    @Test
    @DisplayName("test colore")
    public void test_006(TestInfo testInfo){
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        Color searchButtonBackgroundColour = Color.fromString(driver.findElement(By.id(webProp.getProperty("id.btn.search"))).getCssValue("background-color"));
        extentTest.log(LogStatus.INFO, "Cerco il colore del tasto search.");
        assert searchButtonBackgroundColour.asHex().equals("#3665f3");
        extentTest.log(LogStatus.PASS, "OK");
    }

    @Order(7)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone,3"})
    @Tag("mobile")
    @DisplayName("controllare esistenza prodotto ricercato e muovi tra le pagine")
    void test_007(String q, String pgn, TestInfo testInfo) throws InterruptedException {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        steps.search(webProp, q);
        extentTest.log(LogStatus.INFO, "Faccio la ricerca.");
        for(int i = 1; i < Integer.parseInt(pgn); i++) {
            driver.findElement(By.className(webProp.getProperty("class.btn.next"))).click();
            Thread.sleep(4000);
            assertTrue(driver.getCurrentUrl().contains(String.valueOf(i+1)));
            extentTest.log(LogStatus.INFO, "SCREENSHOT",extentTest.addBase64ScreenShot(Utility.getScreenCast()));
        }
        for(int i = Integer.parseInt(pgn); i > 1; i--) {
            Thread.sleep(4000);
            driver.findElement(By.className(webProp.getProperty("class.btn.previous"))).click();
            assertTrue(driver.getCurrentUrl().contains(String.valueOf(i-1)));
            extentTest.log(LogStatus.PASS, "OK.");
        }
    }

    @Order(8)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"iphone"})
    @Tag("mobile")
    @DisplayName("Stampa di tutti i risultati.")
    void test_008(String q,TestInfo testInfo) {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie,");
        steps.search(webProp, q);
        extentTest.log(LogStatus.INFO, "Faccio la ricerca.");
        modello.stampaElementi(steps.getElementiMobile(webProp));
        extentTest.log(LogStatus.INFO, "Stampo tutti gli elementi da mobile.");
        extentTest.log(LogStatus.PASS, "OK");
    }

    @Order(9)
    @ParameterizedTest(name = "q = {0} , categoria = {0}")
    @CsvSource({"pluto , cognome, semoh62083@jmpant.com, A12345!"})
    @Tag("mobile")
    @DisplayName("registrazione su ebay")
    void test_009(String nome, String cognome, String email, String password,TestInfo testInfo) {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        //steps.closeBanner(webProp);
        //extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie.");
        steps.registrazioneEbay(webProp,nome,cognome,email,password);
        extentTest.log(LogStatus.INFO, "Effettuo la registrazione su Ebay.");
        extentTest.log(LogStatus.PASS, "OK");
    }

    @Order(10)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone"})
    @DisplayName("aggiungi al carrello")
    void test_010(String q, TestInfo testInfo) throws InterruptedException {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        //steps.closeBanner(webProp);
        //extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie.");
        steps.search(webProp, q);
        extentTest.log(LogStatus.INFO, "Faccio la ricerca.");
        boolean r = steps.addCart(webProp);
        extentTest.log(LogStatus.INFO, "Aggiungo i prodotti al carrello.");
        Thread.sleep(2000);
        assertTrue(r);
        extentTest.log(LogStatus.INFO, "Verifico il prezzo.");
        extentTest.log(LogStatus.PASS, "OK");
    }

    @Order(11)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone"})
    @DisplayName("aggiungi al carrello")
    void test_011(String q,TestInfo testInfo) throws InterruptedException {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("ebay.url"));
        extentTest.log(LogStatus.INFO, "Apro ebay.com.");
        steps.closeBanner(webProp);
        extentTest.log(LogStatus.INFO, "Chiudo il banner dei cookie.");
        steps.search(webProp, q);
        extentTest.log(LogStatus.INFO, "Faccio la ricerca.");
        steps.addRem(webProp);
        extentTest.log(LogStatus.INFO, "Aggiungo e rimuovo dalla schermata del carrello.");
        extentTest.log(LogStatus.PASS, "OK");
    }

    @AfterEach
    void tearDown() {
        extentReports.endTest(extentTest);
    }

    @AfterAll
    static void tearDownAll() {
        ManagementDriver.stopDriver();
        extentReports.flush();
    }
}
