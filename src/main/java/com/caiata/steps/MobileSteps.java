package com.caiata.steps;

import com.caiata.utils.ManagementDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MobileSteps {

    static private AndroidDriver<?> androidDriver = ManagementDriver.getAndroidDriver();

    public void login(Properties prop) {
        androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
        androidDriver.findElement(By.id(prop.getProperty("app.id.username"))).sendKeys("admin");
        androidDriver.findElement(By.id(prop.getProperty("app.id.psw"))).sendKeys("admin");
        androidDriver.findElement(By.id(prop.getProperty("app.id.btn.login"))).click();
    }

    public boolean aggiungiUtente(Properties prop) {
            androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
            WebElement element = androidDriver.findElement(By.id(prop.getProperty("id.app.benvenuto")));
            if (element.isDisplayed()) {
                androidDriver.findElement(By.id(prop.getProperty("app.id.btn.aggiungi"))).click();
                androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
                androidDriver.findElement(By.id(prop.getProperty("app.id.newUtente"))).sendKeys("Vito");
                androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
                androidDriver.findElement(By.id(prop.getProperty("app.id.btn.aggUtente"))).click();
            }else{
                return false;
            }
            return true;
    }

    public boolean eliminaUtenti(Properties prop) {
            androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
            androidDriver.findElement(By.id(prop.getProperty("app.id.btn.elimina"))).click();
            androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
            androidDriver.findElement(By.id(prop.getProperty("app.id.btn.si"))).click();
            return true;
    }

    public void backClear(Properties prop){
            androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
            androidDriver.findElement(By.xpath(prop.getProperty("app.xpath.btn.back"))).click();
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS) ;
            androidDriver.findElement(By.id(prop.getProperty("app.id.username"))).sendKeys("admin");
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS) ;
            androidDriver.findElement(By.id(prop.getProperty("app.id.psw"))).sendKeys("admin");
            androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
            androidDriver.findElement(By.id(prop.getProperty("app.id.btn.reset"))).click();
    }

    public boolean errore(Properties prop){
        androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
        androidDriver.findElement(By.id(prop.getProperty("app.id.username"))).sendKeys("admin");
        androidDriver.findElement(By.id(prop.getProperty("app.id.btn.login"))).click();
        androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
        boolean result = androidDriver.findElement(By.id(prop.getProperty("app.id.errore"))).isDisplayed();
        try {
            if (result) {
                androidDriver.findElement(By.id(prop.getProperty("app.id.btnok"))).click();
                assertTrue(result);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Errore.");
        }
        return true;
    }

    public void loginUser(Properties prop) {
        androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS) ;
        androidDriver.findElement(By.id(prop.getProperty("app.id.username"))).sendKeys("user");
        androidDriver.findElement(By.id(prop.getProperty("app.id.psw"))).sendKeys("user");
        androidDriver.findElement(By.id(prop.getProperty("app.id.btn.login"))).click();
    }

    public boolean welcome(Properties prop){
        androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
        WebElement element = androidDriver.findElement(By.id(prop.getProperty("id.app.benvenuto")));
        assertEquals(true, element.getText().contains("user"));
        return true;
    }

    public List<WebElement> lista(Properties prop){
        androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
        return androidDriver.findElement(By.id(prop.getProperty("id.lista"))).findElements(By.className("android.widget.TextView"));
    }

    public boolean listaUser(Properties prop, String nome){
        androidDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS) ;
        for(WebElement element : lista(prop)){
            if(element.getText().contains(nome)){
                System.out.println("Nome già esistente.");
                return false;
            }
        }
        return true;
    }

}
