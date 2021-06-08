package com.caiata.steps;

import com.caiata.utils.ManagementDriver;
import com.caiata.utils.ModelloEbay;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EbaySteps {

    private float val1 = 0;
    private float val2 = 0;
    private float somma = 0;
    private ModelloEbay modello = new ModelloEbay();
    private WebElement webElement;
    private WebDriver driver = ManagementDriver.getDriver();

    public void search(Properties prop, String q) {
        webElement = driver.findElement(By.name(prop.getProperty("name.input.search")));
        webElement.clear();
        webElement.sendKeys(q);
        webElement.sendKeys(Keys.ENTER);
    }

    public void closeBanner(Properties prop){
        try {
            //webElement = new WebDriverWait(driver, Duration.ofSeconds(5))
              //      .until(driver -> driver.findElement(By.id(prop.getProperty("id.banner.gdp"))));
            Thread.sleep(4000);
            webElement = driver.findElement(By.id(prop.getProperty("id.banner.gdp")));
            if (webElement.isDisplayed()) {
                driver.findElement(By.id(prop.getProperty("id.btn.gdp.accept"))).click();
                System.out.println("Banner trovato e chiuso");
            }
        }catch(NoSuchElementException | TimeoutException | InterruptedException e){
            System.out.println("Banner non trovato.");
        }
    }

    public void closeBannerFW(Properties prop){
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            webElement = wait.until(driver -> driver.findElement(By.id("id.banner.gdp")));
            if (webElement.isDisplayed()) {
                driver.findElement(By.id(prop.getProperty("id.btn.gdp.accept"))).click();
                System.out.println("Banner trovato e chiuso");
            }
        }catch(NoSuchElementException  | TimeoutException e){
            System.out.println("Banner non trovato.");
        }
    }

    public List<WebElement> getCategorie(Properties prop){
        return driver.findElement(By.id(prop.getProperty("id.select.category"))).findElements(By.tagName("option"));
    }

    public void selezionaCategoria(Properties webProp, String categoria) {
        driver.findElement(By.id(webProp.getProperty("id.select.category"))).click();
        for(WebElement element : getCategorie(webProp)){
            if(element.getText().toLowerCase().contains(categoria)){
                element.click();
            }
        }
    }

    public String[] getMenuCategory(Properties webProp){
        List<WebElement> listaElementi = driver.findElements(By.id(webProp.getProperty("id.select.category")));
        String[] tmp = new String[listaElementi.size()];

        for(int i = 0; i<listaElementi.size() ; i++){
                tmp[i] = listaElementi.get(i).getText();
        }
        return tmp;
    }


    public ArrayList<ModelloEbay> getElementi(Properties prop){
        ArrayList<ModelloEbay> listaModello = new ArrayList<>();
        for(WebElement element : driver.findElement(By.xpath(prop.getProperty("xpath.div"))).findElements(By.className("s-item"))){
            listaModello.add(new ModelloEbay(element.findElement(By.tagName("h3")).getText(),
                    element.findElement(By.className("s-item__subtitle")).getText(),
                            element.findElement(By.className("s-item__detail--primary")).getText(),
                                    element.findElement(By.tagName("img")).getAttribute("src")));
        }
        return listaModello;
    }

    public ArrayList<ModelloEbay> getElementiMobile(Properties prop){
        ArrayList<ModelloEbay> listaModello = new ArrayList<>();
        for(WebElement element : driver.findElement(By.xpath(prop.getProperty("xpath.div"))).findElements(By.className("s-item"))){
            listaModello.add(new ModelloEbay(element.findElement(By.tagName("h3")).getText(),
                    element.findElement(By.className(prop.getProperty("class.subtitle"))).getText(),
                    element.findElement(By.className(prop.getProperty("class.prize"))).getText(),
                    element.findElement(By.tagName("img")).getAttribute("src")));
        }
        return listaModello;
    }

    public boolean addCart(Properties prop) throws InterruptedException {

        ArrayList<ModelloEbay> carrello = new ArrayList<>();

        driver.findElement(By.xpath(prop.getProperty("xpath.product"))).click();
        Thread.sleep(3000);

        carrello.add(new ModelloEbay(
                driver.findElement(By.className("vi-bin-primary-price__main-price")).getText()
        ));
        String prezzo = driver.findElement(By.className("vi-bin-primary-price__main-price")).getText();
        val1 = Float.parseFloat(prezzo.substring(1));
        System.out.println("Prezzo del primo articolo : "+val1);


        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement aggiungi = driver.findElement(By.id(prop.getProperty("id.button.addcart")));
        js.executeScript("arguments[0].scrollIntoView();", aggiungi);
        aggiungi.click();


        driver.navigate().back();
        driver.findElement(By.xpath(prop.getProperty("xpath.product2"))).click();
        Thread.sleep(3000);

        carrello.add(new ModelloEbay(
                driver.findElement(By.className("vi-bin-primary-price__main-price")).getText()
        ));
        String prezzo2 = driver.findElement(By.className("vi-bin-primary-price__main-price")).getText();
        val2 = Float.parseFloat(prezzo2.substring(1));
        System.out.println("Prezzo del secondo articolo " + val2);

        aggiungi = driver.findElement(By.id(prop.getProperty("id.button.addcart")));
        js.executeScript("arguments[0].scrollIntoView();", aggiungi);
        aggiungi.click();

        somma = val1 + val2;
        System.out.println("Totale del tuo carrello = " + somma);

        Thread.sleep(4000);
        driver.findElement(By.xpath(prop.getProperty("xpath.cart.icon"))).click();
        WebElement cart = driver.findElement(By.xpath(prop.getProperty("xpath.totale.carrello")));
        js.executeScript("arguments[0].scrollIntoView();", cart);

        String c = driver.findElement(By.xpath(prop.getProperty("xpath.totale.carrello").substring(1))).getText();

        if(somma != Float.parseFloat(c)){
            return false;
        }
        return true;
    }

    public void add(Properties prop) throws InterruptedException {

        driver.findElement(By.xpath(prop.getProperty("xpath.product"))).click();
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement aggiungi = driver.findElement(By.id(prop.getProperty("id.button.addcart")));
        js.executeScript("arguments[0].scrollIntoView();", aggiungi);
        aggiungi.click();
        driver.navigate().back();

        driver.findElement(By.xpath(prop.getProperty("xpath.product2"))).click();
        Thread.sleep(3000);
        aggiungi = driver.findElement(By.id(prop.getProperty("id.button.addcart")));
        js.executeScript("arguments[0].scrollIntoView();", aggiungi);
        aggiungi.click();

        Thread.sleep(4000);
        driver.findElement(By.xpath(prop.getProperty("xpath.cart.icon"))).click();

        webElement = driver.findElement(By.xpath(prop.getProperty("xpath.search")));
        webElement.clear();
        webElement.sendKeys("2");
        webElement.sendKeys(Keys.ENTER);


        Thread.sleep(4000);
        driver.findElement(By.xpath(prop.getProperty("xpath.btn.rmv"))).click();
    }

}

