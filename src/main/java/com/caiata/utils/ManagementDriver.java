package com.caiata.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


import java.net.URL;
import java.time.Duration;
import java.util.List;

import static com.caiata.utils.GlobalParameters.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ManagementDriver {

    private static EdgeDriver edgeDriver;
    private static FirefoxDriver firefoxDriver;
    private static ChromeDriver chromeDriver;
    private static boolean mobile = false;
    private static AndroidDriver<?> androidDriver;
    private static IOSDriver<?> iosDriver;
    private static DesiredCapabilities desiredCapabilities;
    private static boolean firefox = false;
    private static boolean edge = false;

    public static void startDriver(DefaulChromeOptions defaultChromeOptions){
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_WIN);
        System.setProperty("org.freemarker.loggerLibrary", "none");

        chromeDriver = new ChromeDriver(defaultChromeOptions);

        //new ChromeOptions().setPageLoadStrategy(PageLoadStrategy.NORMAL);
        System.err.close();
        System.setErr(System.out);
        new Utility().loadProp("log4j");
        BasicConfigurator.configure();
    }

    public static WebDriver getDriver(){
        if(firefoxDriver != null){
            return firefoxDriver;
        }else if(chromeDriver != null){
            return chromeDriver;
        }else if(androidDriver != null){
            return androidDriver;
        }else if(edgeDriver !=null){
            return edgeDriver;
        }
        return null;
    }

    public static void startMobileDriver(String platform, DesiredCapabilities desiredCapabilities){
        try {
            if(platform.equals("ANDROID")) {
                androidDriver = new AndroidDriver<WebElement>(new URL(SERVER_APPIUM), desiredCapabilities);
            }else if(platform.equals("IOS")){
                iosDriver = new IOSDriver<WebElement>(new URL(SERVER_APPIUM),desiredCapabilities);
            }else{
                System.out.println("Errore caricamento driver.");
            }
            } catch (Exception e) {
            System.err.println("Errore Mobile Driver " + e.getMessage());
        }
    }

    public static void startFirefoxDriver(DefaultFirefoxOptions defaultFirefoxOptions){
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH_WIN);
        System.setProperty("org.freemarker.loggerLibrary", "none");

        firefoxDriver = new FirefoxDriver(defaultFirefoxOptions);

        System.err.close();
        System.setErr(System.out);
        new Utility().loadProp("log4j");
        BasicConfigurator.configure();

    }

    public static void startEdgeDriver(DefaultEdgeOptions defaultEdgeOptions){
        System.setProperty("webdriver.edge.driver",EDGE_DRIVER_PATH);
        System.setProperty("org.freemarker.loggerLibrary", "none");

        edgeDriver = new EdgeDriver(defaultEdgeOptions);

        System.err.close();
        System.setErr(System.out);
        new Utility().loadProp("log4j");
        BasicConfigurator.configure();
    }

    public static EdgeDriver getEdgeDriver() {
        return edgeDriver;
    }

    public static WebDriver getFirefoxDriver() {
        return firefoxDriver;
    }

    public static boolean isFirefox() {
        return firefox;
    }

    public static void setFirefox(boolean firefox) {
        ManagementDriver.firefox = firefox;
    }

    public static WebDriver getChromeDriver(){
        return chromeDriver;
    }

    public static boolean isMobile() {
        return mobile;
    }

    public static void setMobile(boolean m) {
        mobile = m;
    }

    public static boolean isEdge() {
        return edge;
    }

    public static void setEdge(boolean edge) {
        ManagementDriver.edge = edge;
    }

    public static AndroidDriver getAndroidDriver() {
        return androidDriver;
    }

    public static IOSDriver<?> getIosDriver() {
        return iosDriver;
    }

    public static Wait<ChromeDriver> getWaitChromeDriver(){
        return new FluentWait<>(chromeDriver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
    }

    public static List<WebElement> waitListAndroidById(String value){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getAndroidDriver())
                .withTimeout(Duration.ofSeconds(50))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        return (List<WebElement>) wait.until(driver -> androidDriver.findElements(By.id(value)));
    }

    public static WebElement waitAndroidById(String value){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getAndroidDriver())
                .withTimeout(Duration.ofSeconds(3))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);
        return wait.until(driver -> androidDriver.findElement(By.id(value)));
    }

    public static Wait<IOSDriver> getWaitIos(){
        return new FluentWait<IOSDriver>(getIosDriver())
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
    }

    public static void stopDriver(){
        if(chromeDriver !=null) chromeDriver.quit();
        if(edgeDriver !=null) edgeDriver.quit();
        if(firefoxDriver !=null) firefoxDriver.quit();
        if(androidDriver!=null) androidDriver.quit();
        if(iosDriver != null) iosDriver.quit();
    }
}
