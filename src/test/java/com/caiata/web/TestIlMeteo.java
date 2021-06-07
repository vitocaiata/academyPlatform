package com.caiata.web;

import com.caiata.steps.IlMeteoSteps;
import com.caiata.utils.DefaulChromeOptions;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.Utility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestIlMeteo{

        static private WebElement webElement;
        static private WebDriver driver = null;
        static private Properties webProp = null;
        static private IlMeteoSteps steps = null;

        @BeforeAll
        static void beforeAll() {
            ManagementDriver.startDriver(new DefaulChromeOptions(new ChromeOptions()));
            webProp = new Utility().loadProp("ilmeteo");
            driver = ManagementDriver.getDriver();
            steps = new IlMeteoSteps();
           // webElement = new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> driver.findElement(By.id(("tab" + 1))));

        }

        @BeforeEach
        void beforeEach() {
        }


        @Order(1)
        @ParameterizedTest(name = "q = {0}")
        @ValueSource(strings = {"Picerno", "ciao"})
        @DisplayName("controllare esistenza prodotto ricercato")
        void test_001(String q) {
            driver.get(webProp.getProperty("ilmeteo.url"));
            steps.closeBannerFW(webProp);
            steps.search(webProp, q);
            String result = driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText();
            System.out.println(result);
            if(!result.contains(q.toUpperCase())){
                fail("Città non trovata.");
            }
        }

        @Order(2)
        @ParameterizedTest(name = "q = {0}")
        @ValueSource(strings = {""})
        @DisplayName("controllare titoli")
        void test_002(){
            String nome;
            driver.get(webProp.getProperty("ilmeteo.url"));
            try{
                for(int i = 1; i <= 14; i++){
                    webElement = new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> driver.findElement(By.id(("tab" + 1))));
                    //webElement = driver.findElement(By.id(("tab" + 1)));
                    nome = webElement.getText();
                    webElement.click();
                    //Thread.sleep(500);
                    if(!nome.equals("Home")){
                    }else{
                        if(!driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(nome.toLowerCase())){
                            System.out.println(nome + " Non trovato!");
                        }else{
                            System.out.println(nome + " Trovato!");
                        }
                        assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(nome.toLowerCase()));
                    }
                }
            }catch(NoSuchElementException | TimeoutException e){
                System.out.println("Banner non trovato !");
            }
        }

    @ParameterizedTest(name = "{0}")
    @DisplayName("controllo titoli con csv")
    @CsvSource({"tab1","tab2","tab3","tab4","tab5","tab6","tab7","tab8","tab9","tab10","tab11","tab12","tab13","tab14"})
    @Order(3)
    void test_003_Controllo(String tab){
        String nome;
        driver.get(webProp.getProperty("ilmeteo.url"));
        try {
            webElement = new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> driver.findElement(By.id((tab))));
            nome = webElement.getText();
            webElement.click();
            assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).isEnabled());
            if (!nome.equals("Home")) {
                assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(nome.toLowerCase()));
            }
            }catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Banner non Trovato");
        }
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("controllo titoli con csv")
    @CsvSource({"tab1","tab2","tab3","tab4","tab5","tab6","tab7","tab8","tab9","tab10","tab11","tab12","tab13","tab14"})
    @Order(3)
    void test_003_ControlloFW(String tab){
        String nome;
        driver.get(webProp.getProperty("ilmeteo.url"));
        try {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(NoSuchElementException.class);
            webElement = wait.until(driver -> driver.findElement(By.id("id.page.title")));

            nome = webElement.getText();
            webElement.click();
            assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).isEnabled());
            if (!nome.equals("Home")) {
                assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(nome.toLowerCase()));
            }
        }catch (NoSuchElementException | TimeoutException e) {
            System.out.println("Banner non Trovato");
        }
    }

    @Order(4)
    @Test
    @DisplayName("controllo titoli con lista")
    void test_004() throws InterruptedException {
        driver.get(webProp.getProperty("ilmeteo.url"));
        String handle = driver.getWindowHandle();
        for(WebElement element : steps.getMenuTabs(webProp)){
            String elementText = element.getText();
            if(!elementText.equals("Home")){
                String a = element.getAttribute("href");
                driver.switchTo().newWindow(WindowType.TAB);
                driver.get(a);
                //webElement = new WebDriverWait(driver, Duration.ofSeconds(3)).until(driver -> driver.findElement(By.id(webProp.getProperty("id.page.title"))));
                Thread.sleep(500);
                assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(elementText.toLowerCase()));
                driver.close();
                driver.switchTo().window(handle);
            }
        }
        /*
        for(int i = 2; i < steps.getMenuTabs(webProp).size(); i++){
            webElement = steps.getMenuTabs(webProp).get(i);
            webElement = driver.findElement(By.id("tab"+i));
            webElement.click();

            webElement = driver.findElement(By.id("tab" + i));
            Thread.sleep(500);
            WebElement tmp = driver.findElement(By.id(webProp.getProperty("id.page.title")));
            assertTrue(tmp.getText().toLowerCase().contains(webElement.getText().toLowerCase()));

            System.out.println("Titolo superiore : " + webElement.getText());
            System.out.println("Titolo stampato sotto : " + tmp.getText());
        }*/
    }

    @AfterEach
    void tearDown() { }

    @AfterAll
    static void tearDownAll() {ManagementDriver.stopDriver();}
}
