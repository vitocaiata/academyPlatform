package com.caiata.steps;

import com.caiata.utils.ManagementDriver;
import org.openqa.selenium.*;

import java.util.Properties;

public class AmazonSteps {

    private WebElement webElement;
    private WebDriver driver = ManagementDriver.getChromeDriver();

    public void search(Properties prop, String q) {
        webElement = driver.findElement(By.id(prop.getProperty("id.search")));
        webElement.clear();
        webElement.sendKeys(q);
        webElement.sendKeys(Keys.ENTER);
    }

    public boolean closeBanner(Properties prop) {
        try {
            Thread.sleep(4000);
            webElement = driver.findElement(By.id(prop.getProperty("id.banner")));
            if (webElement.isDisplayed()) {
                driver.findElement(By.id(prop.getProperty("id.btn.accept"))).click();
                System.out.println("Banner trovato e chiuso");
            }
        } catch (NoSuchElementException | TimeoutException | InterruptedException e) {
            System.out.println("Banner non trovato.");
            return false;
        }
        return true;
    }

    public void ordina(Properties prop){
        try{
            Thread.sleep(4000);
            driver.findElement(By.xpath(prop.getProperty("xpath.ordina"))).click();
            driver.findElement(By.id(prop.getProperty("id.decrescente"))).click();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean fasciaPrezzo(Properties prop){
        try{
            Thread.sleep(4000);
            webElement = driver.findElement(By.id(prop.getProperty("id.minimo")));
            webElement.clear();
            webElement.sendKeys("250");
            Thread.sleep(1000);
            webElement = driver.findElement(By.id(prop.getProperty("id.massimo")));
            webElement.clear();
            webElement.sendKeys("500");
            webElement.sendKeys(Keys.ENTER);
        }catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void selezioneMarca(Properties prop){
        try {
            Thread.sleep(3000);
            driver.findElement(By.cssSelector("ul[aria-labelledby='p_89-title']")).findElements(By.cssSelector("a[class='a-link-normal s-navigation-item']")).get(0).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean selezioneProdotto(Properties prop){
        try {
            Thread.sleep(3000);
            driver.findElement(By.xpath(prop.getProperty("xpath.elemento"))).click();
            Thread.sleep(3000);
            driver.findElement(By.cssSelector("select[name = 'quantity']")).findElements(By.tagName("option")).get(1).click();
            Thread.sleep(1000);
            driver.findElement(By.id(prop.getProperty("id.btn.addcart"))).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(prop.getProperty("xpath.btn.assicurazione"))).click();
        } catch (InterruptedException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean svuotaCarrello(Properties prop){
        try {
            Thread.sleep(1000);
            driver.findElement(By.id(prop.getProperty("id.cart"))).click();
            Thread.sleep(2000);
            for(int i = 0; i<=1; i++){
                driver.findElements(By.cssSelector("input[value='Rimuovi']")).get(i).click();
            }
        } catch (InterruptedException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

