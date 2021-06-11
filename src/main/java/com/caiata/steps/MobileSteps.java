package com.caiata.steps;

import com.caiata.utils.ManagementDriver;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Properties;

public class MobileSteps {

    static private AndroidDriver<?> androidDriver = ManagementDriver.getAndroidDriver();

    public void login(Properties prop) {
        androidDriver.findElement(By.id(prop.getProperty("app.id.username"))).sendKeys("admin");
        androidDriver.findElement(By.id(prop.getProperty("app.id.psw"))).sendKeys("admin");
        androidDriver.findElement(By.id(prop.getProperty("app.id.btn.login"))).click();
    }

    public void aggiungiUtente(Properties prop) {
        try {
            Thread.sleep(2000);
            WebElement element = androidDriver.findElement(By.id(prop.getProperty("id.app.benvenuto")));
            if (element.isDisplayed()) {
                androidDriver.findElement(By.id(prop.getProperty("app.id.btn.aggiungi"))).click();
                Thread.sleep(2000);
                androidDriver.findElement(By.id(prop.getProperty("app.id.newUtente"))).sendKeys("Vito");
                Thread.sleep(2000);
                androidDriver.findElement(By.id(prop.getProperty("app.id.btn.aggUtente"))).click();
            }
        } catch (InterruptedException e) {
            System.err.println("Errore !");
        }
    }

    public void eliminaUtenti(Properties prop) {
        try {
            Thread.sleep(2000);
            androidDriver.findElement(By.id(prop.getProperty("app.id.btn.elimina"))).click();
            Thread.sleep(2000);
            androidDriver.findElement(By.id(prop.getProperty("app.id.btn.si"))).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void backClear(Properties prop){
        try {
            Thread.sleep(2000);
            androidDriver.findElement(By.xpath(prop.getProperty("app.xpath.btn.back"))).click();
            Thread.sleep(1000);
            androidDriver.findElement(By.id(prop.getProperty("app.id.username"))).sendKeys("admin");
            Thread.sleep(1000);
            androidDriver.findElement(By.id(prop.getProperty("app.id.psw"))).sendKeys("admin");
            Thread.sleep(2000);
            androidDriver.findElement(By.id(prop.getProperty("app.id.btn.reset"))).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
