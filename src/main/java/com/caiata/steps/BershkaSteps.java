package com.caiata.steps;

import com.caiata.utils.ManagementDriver;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.ENTER;

public class BershkaSteps {

    static private AndroidDriver<?> androidDriver = ManagementDriver.getAndroidDriver();

    /*public boolean inizializzaApp(Properties androidProp){
            ManagementDriver.waitAndroidById(androidProp.getProperty("id.accept.cookie")).click();
            return true;
    }

    public void clickGo(Properties androidProp){
        ManagementDriver.waitAndroidById(androidProp.getProperty("id.btn.go")).click();
    }

    public void inizializzaPaese(Properties androidProp){
        ManagementDriver.waitAndroidById(androidProp.getProperty("id.btn.continue")).click();
        ManagementDriver.waitAndroidById(androidProp.getProperty("id.btn.accept")).click();
        ManagementDriver.waitAndroidById(androidProp.getProperty("id.btn.localizationPermission")).click();
    }

     */

    public void threadFunziona(Properties androidProp){
        try{
            Thread.sleep(20000);
            androidDriver.findElement(By.id(androidProp.getProperty("id.accept.cookie"))).click();
            Thread.sleep(20000);
            androidDriver.findElement(By.id(androidProp.getProperty("id.btn.go"))).click();
            Thread.sleep(20000);
            androidDriver.findElement(By.id(androidProp.getProperty("id.btn.continue"))).click();
            Thread.sleep(20000);
            androidDriver.findElement(By.id(androidProp.getProperty("id.btn.accept"))).click();
            Thread.sleep(20000);
            androidDriver.findElement(By.id(androidProp.getProperty("id.btn.localizationPermission"))).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
