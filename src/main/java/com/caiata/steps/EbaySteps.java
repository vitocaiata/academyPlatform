package com.caiata.steps;

import com.caiata.ManagementDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class EbaySteps {

    private WebElement webElement;
    private WebDriver driver = ManagementDriver.getDriver();

    public void search(Properties prop, String q) {
        webElement = driver.findElement(By.name(prop.getProperty("name.input.search")));
        webElement.clear();
        webElement.sendKeys(q);
        driver.findElement(By.id(prop.getProperty("id.btn.search"))).click();
    }

    public void closeBanner(Properties prop){
        try {
            webElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(driver -> driver.findElement(By.id(prop.getProperty("id.banner.gdp"))));
            //Thread.sleep(4000);
            //webElement = driver.findElement(By.id(prop.getProperty("id.banner.gdp")));
            if (webElement.isDisplayed()) {
                driver.findElement(By.id(prop.getProperty("id.btn.gdp.accept"))).click();
                System.out.println("Banner trovato e chiuso");
            }
        }catch(NoSuchElementException | TimeoutException  e){
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
}
