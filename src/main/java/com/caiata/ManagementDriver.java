package com.caiata;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.caiata.GlobalParameters.CHROME_DRIVER_PATH_WIN;

public class ManagementDriver {
    static ChromeDriver driver;

    public static void startDriver(){
        System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_PATH_WIN);
        System.setProperty("org.freemarker.loggerLibrary","none");
        driver = new ChromeDriver();
        new ChromeOptions().setPageLoadStrategy(PageLoadStrategy.NORMAL);
        System.err.close();
        System.setErr(System.out);
        new Utility().loadProp("log4j");
        BasicConfigurator.configure();
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public static void stopDriver(){
        driver.quit();
    }
}
