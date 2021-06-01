package com.caiata.web;

import com.caiata.ManagementDriver;
import com.caiata.Utility;
import com.sun.corba.se.impl.protocol.AddressingDispositionException;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WindowType;
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

    @Order(1)
    @Test
    @DisplayName("simulazione tasti navigazione browser")
    void test_001(){
        String title = "";
        String currentURL = "";

        ManagementDriver.getDriver().get(webProp.getProperty("google.url"));
        ManagementDriver.getDriver().get(webProp.getProperty("ebay.url"));

        title = ManagementDriver.getDriver().getTitle();
        currentURL = ManagementDriver.getDriver().getCurrentUrl();

        System.out.println("titolo = " + title);
        System.out.println("URL = " + currentURL);

        ManagementDriver.getDriver().navigate().back();

        title = ManagementDriver.getDriver().getTitle();
        currentURL = ManagementDriver.getDriver().getCurrentUrl();

        System.out.println("titolo = " + title);
        System.out.println("URL = " + currentURL);

        ManagementDriver.getDriver().navigate().forward();
        ManagementDriver.getDriver().navigate().refresh();
    }

    @Order(2)
    @Test
    @DisplayName("test windows broswer")
    void test_002(){
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

        System.out.println("handle window = " + handleWin );
        System.out.println("width = " + width );
        System.out.println("height = " + height);
        System.out.println("Pos x = " + x);
        System.out.println("Pos y = " + y);

        ManagementDriver.getDriver().manage().window().setSize(new Dimension(1024,768));
        ManagementDriver.getDriver().manage().window().setPosition(new Point(500,0));
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
    }


    @AfterEach
    void tearDown(){ }

    @AfterAll
    void tearDownAll(){
        //ManagementDriver.stopDriver();
    }

}
