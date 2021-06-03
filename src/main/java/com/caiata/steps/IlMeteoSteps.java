package com.caiata.steps;

import com.caiata.ManagementDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class IlMeteoSteps {
    private WebElement webElement;
    private WebDriver driver = ManagementDriver.getDriver();

    public void search(Properties prop, String q) {
        webElement = driver.findElement(By.id(prop.getProperty("id.input.search")));
        webElement.clear();
        webElement.sendKeys(q);
        driver.findElement(By.id(prop.getProperty("id.btn.search"))).click();
    }

    public void closeBanner(Properties prop){
        try {
            //Thread.sleep(4000);
            webElement = new WebDriverWait(driver,Duration.ofSeconds(3)).until(driver -> driver.findElement(By.xpath(prop.getProperty("xpath.banner.cookie"))));
            //webElement = driver.findElement(By.xpath(prop.getProperty("xpath.banner.cookie")));
            if (webElement.isDisplayed()) {
                driver.findElement(By.xpath(prop.getProperty("xpath.btn.accept.cookie"))).click();
                System.out.println("Banner trovato e chiuso");
            }
        }catch(NoSuchElementException | TimeoutException e){
            System.out.println("Banner non trovato.");
        }
    }

    public void closeBannerFW(Properties prop){
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
            webElement = wait.until(driver -> driver.findElement(By.xpath(prop.getProperty("xpath.banner.cookie"))));
            if (webElement.isDisplayed()) {
                driver.findElement(By.xpath(prop.getProperty("xpath.btn.accept.cookie"))).click();
                System.out.println("Banner trovato e chiuso");
            }
        }catch(NoSuchElementException | TimeoutException e){
            System.out.println("Banner non trovato.");
        }
    }

    public List<WebElement> getMenuTabs(Properties prop) throws InterruptedException {
        Thread.sleep(500);
        return driver.findElement(By.id(prop.getProperty("id.page.menu"))).findElements(By.tagName("a"));
       // return driver.findElements(By.xpath("//*[@class=\"tab\"]/span"));
    }
}

