package com.caiata.web;

import com.caiata.IlMeteoSteps;
import com.caiata.ManagementDriver;
import com.caiata.Utility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestIlMeteo{

        static private WebElement webElement;
        static private WebDriver driver = null;
        static private Properties webProp = null;
        static private IlMeteoSteps steps = null;

        @BeforeAll
        static void beforeAll() {
            webProp = new Utility().loadProp("ilmeteo");
            ManagementDriver.startDriver();
            driver = ManagementDriver.getDriver();
            steps = new IlMeteoSteps();
        }

        @BeforeEach
        void beforeEach() {
        }


        @Order(1)
        @ParameterizedTest(name = "q = {0}")
        @ValueSource(strings = {"Picerno"})
        @DisplayName("controllare esistenza prodotto ricercato")
        void test_001(String q) {
            driver.get(webProp.getProperty("ilmeteo.url"));
            steps.closeBanner(webProp);
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
                    webElement = driver.findElement(By.id(("tab" + 1)));
                    nome = webElement.getText();
                    webElement.click();
                    Thread.sleep(500);
                    if(nome.equals("Home")){
                        continue;
                    }else{
                        if(!driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(nome.toLowerCase())){
                            System.out.println(nome + " Non trovato!");
                        }else{
                            System.out.println(nome + " Trovato!");
                        }
                        assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(nome.toLowerCase()));
                    }
                }
            }catch(InterruptedException e){
                System.out.println("Banner non trovato !");
            }
        }

    @ParameterizedTest(name = "{0}")
    @DisplayName("controllo titoli con csv")
    @CsvSource({"tab1","tab2","tab3","tab4","tab5","tab6","tab7","tab8","tab9","tab10","tab11","tab12","tab13","tab14"})
    @Order(3)
    void Test_003_Controllo(String tab){
        String nome;
        driver.get(webProp.getProperty("ilmeteo.url"));
        try {
            webElement = driver.findElement(By.id((tab)));
            nome = webElement.getText();
            webElement.click();
            Thread.sleep(500);
            assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).isEnabled());
            if (!nome.equals("Home"))
                assertTrue(driver.findElement(By.id(webProp.getProperty("id.page.title"))).getText().toLowerCase().contains(nome.toLowerCase()));
        }catch (InterruptedException e) {
            System.out.println("Banner non Trovato");
        }
    }

        @AfterEach
        void tearDown() {
        }

        @AfterAll
        static void tearDownAll() {
            ManagementDriver.stopDriver();
        }
    }
