package com.caiata.web;

import com.caiata.steps.AmazonSteps;
import com.caiata.utils.DefaulChromeOptions;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Properties;

import static com.caiata.utils.GlobalParameters.*;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_Amazon {

    static private WebDriver driver = null;
    static private Properties webProp = null;
    static private AmazonSteps steps = null;
    static private DefaulChromeOptions defaulChromeOptions;
    static private boolean mobile = false;
    static private ExtentReports extentReports;
    static private ExtentTest extentTest;

    @BeforeAll
    static void beforeAll() {
        defaulChromeOptions = new DefaulChromeOptions(new ChromeOptions());
        webProp = new Utility().loadProp("amazon");
        ManagementDriver.startDriver(defaulChromeOptions);
        driver = ManagementDriver.getChromeDriver();
        steps = new AmazonSteps();
        extentReports = new ExtentReports(REPORT_PATH + File.separator + "report" + EXT_HTML, false);
        extentReports.loadConfig(new File(REPORT_CONFIG_XML));
    }

    @BeforeEach
    void beforeEach() {
    }

    @Order(1)
    @ParameterizedTest(name = "q = {0}")
    @ValueSource(strings = {"iphone 12"})
    @DisplayName("controllare esistenza prodotto ricercato")
    void test_001(String q, TestInfo testInfo) {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("amazon.url"));
        extentTest.log(LogStatus.INFO, " ", "Apro Amazon.it.");
        if(steps.closeBanner(webProp)) {
            extentTest.log(LogStatus.INFO, " ", "Chiudo il banner dei cookie.");
            extentTest.log(LogStatus.INFO, "SCREENSHOT",extentTest.addBase64ScreenShot(Utility.getScreenCast()));
        }
        steps.search(webProp, q);
        extentTest.log(LogStatus.INFO, " ", "Faccio la ricerca.");
        steps.ordina(webProp);
        extentTest.log(LogStatus.INFO, " ", "Ordino in modo decrescente.");
        if(steps.fasciaPrezzo(webProp)) {
            extentTest.log(LogStatus.INFO, " ", "Inserisco la fascia di prezzo.");
            extentTest.log(LogStatus.INFO, "SCREENSHOT",extentTest.addBase64ScreenShot(Utility.getScreenCast()));
        }
        steps.selezioneMarca(webProp);
        extentTest.log(LogStatus.INFO, " ", "Seleziono la marca 'Apple'.");
        if(steps.selezioneProdotto(webProp)) {
            extentTest.log(LogStatus.INFO, " ", "Seleziono il prodotto, aumento la quantità e aggiungo l'assicurazione.");
            extentTest.log(LogStatus.INFO, "SCREENSHOT",extentTest.addBase64ScreenShot(Utility.getScreenCast()));
        }
        if(steps.svuotaCarrello(webProp)) {
            extentTest.log(LogStatus.INFO, " ", "Elimino gli elementi dal carrello.");
            extentTest.log(LogStatus.INFO, "SCREENSHOT",extentTest.addBase64ScreenShot(Utility.getScreenCast()));
        }

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
