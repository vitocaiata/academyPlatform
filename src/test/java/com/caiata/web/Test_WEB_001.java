package com.caiata.web;

import com.caiata.utils.DefaulChromeOptions;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.Posizione;
import com.caiata.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.Properties;

import static com.caiata.utils.GlobalParameters.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test_WEB_001 {

    //static private ManagementDriver managementDriver = null;
    static private WebDriver driver = null;
    static private Properties webProp = null;
    static private Posizione posizione = null;
    static private DefaulChromeOptions defaulChromeOptions;
    static private ExtentReports extentReports;
    static private ExtentTest extentTest;

    @BeforeAll
    static void beforeAll() {
        defaulChromeOptions = new DefaulChromeOptions(new ChromeOptions());
        defaulChromeOptions.addArguments("--window-size=375,812");
        defaulChromeOptions.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1");

        ManagementDriver.startDriver(defaulChromeOptions);
        posizione = new Posizione();
        driver = ManagementDriver.getChromeDriver();
        //webProp = new Utility().loadProp("web");
        webProp = new Utility().loadProp("mobile");

        extentReports = new ExtentReports(REPORT_PATH + File.separator + "report" + EXT_HTML, false);
        extentReports.loadConfig(new File(REPORT_CONFIG_XML));
    }

    @BeforeEach
    void beforeEach() {
    }

    @Order(1)
    @Test
    @DisplayName("simulazione tasti navigazione browser")
    void test_001() {
        String title = "";
        String currentURL = "";

        ManagementDriver.getChromeDriver().get(webProp.getProperty("google.url"));
        ManagementDriver.getChromeDriver().get(webProp.getProperty("ebay.url"));

        title = driver.getTitle();
        currentURL = driver.getCurrentUrl();

        System.out.println("titolo = " + title);
        System.out.println("URL = " + currentURL);

        driver.navigate().back();

        title = driver.getTitle();
        currentURL = driver.getCurrentUrl();

        System.out.println("titolo = " + title);
        System.out.println("URL = " + currentURL);

        driver.navigate().forward();
        driver.navigate().refresh();
    }

    @Order(2)
    @Test
    @DisplayName("test windows broswer")
    void test_002() {
        String handleWin = "";
        int width = 0;
        int height = 0;
        int x = 0;
        int y = 0;

        ManagementDriver.getChromeDriver().get(webProp.getProperty("ebay.url"));

        handleWin = ManagementDriver.getChromeDriver().getWindowHandle();
        width = ManagementDriver.getChromeDriver().manage().window().getSize().getWidth();
        height = ManagementDriver.getChromeDriver().manage().window().getSize().getHeight();
        x = ManagementDriver.getChromeDriver().manage().window().getPosition().getX();
        y = ManagementDriver.getChromeDriver().manage().window().getPosition().getY();

        System.out.println("handle window = " + handleWin);
        System.out.println("width = " + width);
        System.out.println("height = " + height);
        System.out.println("Pos x = " + x);
        System.out.println("Pos y = " + y);

        ManagementDriver.getChromeDriver().manage().window().setSize(new Dimension(1024, 768));
        ManagementDriver.getChromeDriver().manage().window().setPosition(new Point(500, 0));
        //ManagementDriver.getChromeDriver().manage().window().minimize();
        ManagementDriver.getChromeDriver().manage().window().maximize();
        ManagementDriver.getChromeDriver().manage().window().fullscreen();
        //ManagementDriver.getChromeDriver().switchTo().newWindow(WindowType.TAB);
        ManagementDriver.getChromeDriver().get(webProp.getProperty("google.url"));
        ManagementDriver.getChromeDriver().close();
        ManagementDriver.getChromeDriver().switchTo().window(handleWin);
        //ManagementDriver.getChromeDriver().switchTo().newWindow(WindowType.WINDOW);
        ManagementDriver.getChromeDriver().get(webProp.getProperty("google.url"));
        ManagementDriver.getChromeDriver().close();
        ManagementDriver.getChromeDriver().switchTo().window(handleWin);
    }

    @Order(3)
    @Test
    @DisplayName("test posizione Google Maps")
    void test_003() throws InterruptedException {
        posizione.geoLocationTest(webProp);

    }

    @Order(4)
    @Test
    @DisplayName("test posizione demos")
    void test_004(TestInfo testInfo){
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        posizione.geolocalizza();
        extentTest.log(LogStatus.INFO, "Apro la mia posizione");
        extentTest.log(LogStatus.PASS, extentTest.addBase64ScreenShot(Utility.getScreenBase64()));

    }

    @Order(5)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone"})
    @DisplayName("test ricerca su google")
    void test_005(String q, TestInfo testInfo) throws InterruptedException {
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        driver.get(webProp.getProperty("google.url"));
        extentTest.log(LogStatus.INFO, "Apro google");
        posizione.accettaCookie(webProp);
        extentTest.log(LogStatus.INFO, "Accetto i cookie");
        posizione.ricerca(webProp,q);
        extentTest.log(LogStatus.INFO, "Ricerco iphone");
        extentTest.log(LogStatus.PASS, "OK",extentTest.addBase64ScreenShot(Utility.getScreenCast()));

        //new Utility().getScreen();

    }

    @AfterEach
    void tearDown() {
        extentReports.endTest(extentTest);
    }

    @AfterAll
    void tearDownAll() {
        ManagementDriver.stopDriver();
        extentReports.flush();
    }

}
