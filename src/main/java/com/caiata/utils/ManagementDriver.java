package com.caiata.utils;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import static com.caiata.utils.GlobalParameters.CHROME_DRIVER_PATH_WIN;
import static com.caiata.utils.GlobalParameters.MY_SO;

public class ManagementDriver {

    private static ChromeDriver driver;
    private static boolean mobile = false;

    public static void startDriver(DefaulChromeOptions defaultChromeOptions){
        if(MY_SO.contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_WIN);
        }else {
            System.setProperty("org.freemarker.loggerLibrary", "none");
        }

        driver = new ChromeDriver(defaultChromeOptions);

        //new ChromeOptions().setPageLoadStrategy(PageLoadStrategy.NORMAL);
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

    public static boolean isMobile() {
        return mobile;
    }

    public static void setMobile(boolean m) {
        mobile = m;
    }
}
