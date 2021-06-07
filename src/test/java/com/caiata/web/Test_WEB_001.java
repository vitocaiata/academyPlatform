package com.caiata.web;

import com.caiata.utils.DefaulChromeOptions;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.Posizione;
import com.caiata.utils.Utility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Properties;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test_WEB_001 {

    //static private ManagementDriver managementDriver = null;
    static private WebDriver driver = null;
    static private Properties webProp = null;
    static private Posizione posizione = null;
    static private DefaulChromeOptions defaulChromeOptions;

    @BeforeAll
    static void beforeAll() {
        defaulChromeOptions = new DefaulChromeOptions(new ChromeOptions());
        defaulChromeOptions.addArguments("--window-size=375,812");
        defaulChromeOptions.addArguments("--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1");

        ManagementDriver.startDriver(defaulChromeOptions);
        posizione = new Posizione();
        driver = ManagementDriver.getDriver();
        //webProp = new Utility().loadProp("web");
        webProp = new Utility().loadProp("mobile");

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

        ManagementDriver.getDriver().get(webProp.getProperty("google.url"));
        ManagementDriver.getDriver().get(webProp.getProperty("ebay.url"));

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

        ManagementDriver.getDriver().get(webProp.getProperty("ebay.url"));

        handleWin = ManagementDriver.getDriver().getWindowHandle();
        width = ManagementDriver.getDriver().manage().window().getSize().getWidth();
        height = ManagementDriver.getDriver().manage().window().getSize().getHeight();
        x = ManagementDriver.getDriver().manage().window().getPosition().getX();
        y = ManagementDriver.getDriver().manage().window().getPosition().getY();

        System.out.println("handle window = " + handleWin);
        System.out.println("width = " + width);
        System.out.println("height = " + height);
        System.out.println("Pos x = " + x);
        System.out.println("Pos y = " + y);

        ManagementDriver.getDriver().manage().window().setSize(new Dimension(1024, 768));
        ManagementDriver.getDriver().manage().window().setPosition(new Point(500, 0));
        ManagementDriver.getDriver().manage().window().minimize();
        ManagementDriver.getDriver().manage().window().maximize();
        ManagementDriver.getDriver().manage().window().fullscreen();
        ManagementDriver.getDriver().switchTo().newWindow(WindowType.TAB);
        ManagementDriver.getDriver().get(webProp.getProperty("google.url"));
        ManagementDriver.getDriver().close();
        ManagementDriver.getDriver().switchTo().window(handleWin);
        ManagementDriver.getDriver().switchTo().newWindow(WindowType.WINDOW);
        ManagementDriver.getDriver().get(webProp.getProperty("google.url"));
        ManagementDriver.getDriver().close();
        ManagementDriver.getDriver().switchTo().window(handleWin);
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
    void test_004(){
        posizione.geolocalizza();

    }

    @Order(5)
    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"iphone"})
    @DisplayName("test ricerca su google")
    void test_005(String q) throws InterruptedException {
        driver.get(webProp.getProperty("google.url"));
        posizione.accettaCookie(webProp);
        posizione.ricerca(webProp,q);
        //new Utility().getScreen();

    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    void tearDownAll() {
        //ManagementDriver.stopDriver();
    }

}
