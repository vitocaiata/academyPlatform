package com.caiata;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.caiata.GlobalParameters.CHROME_DRIVER_PATH;

public class ManagementDriver {
    static ChromeDriver driver;

    public static void startDriver(){
        System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        new ChromeOptions().setPageLoadStrategy(PageLoadStrategy.NORMAL);

    }

    public static WebDriver getDriver(){
        return driver;
    }

    public static void stopDriver(){
        driver.quit();
    }
}
