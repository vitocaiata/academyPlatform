package com.caiata.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.LocationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Posizione {

    static private WebDriver driver = ManagementDriver.getDriver();

    public void geoLocationTest(Properties prop) throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();
        Map coordinates = new HashMap()
        {{
            put("latitude", 37.774929);
            put("longitude", -122.419416);
            put("accuracy", 0);
        }};
        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
        driver.get("http://maps.google.it");
        Thread.sleep(10000);
        driver.findElement(By.xpath(prop.getProperty("xpath.btn.my.location"))).click();
    }

    public void geolocalizza(){
        Map prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.geolocation", 1); // 1:allow 2:block

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        ChromeDriver driver = new ChromeDriver(options);

        ((LocationContext)driver).setLocation(new Location(37.774929, -122.419416, 0));
        driver.get("https://html5demos.com/geo/");
    }
}