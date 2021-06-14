package com.caiata.web;

import com.caiata.steps.AmazonSteps;
import com.caiata.steps.MobileSteps;
import com.caiata.utils.DefaulChromeOptions;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.caiata.utils.GlobalParameters.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Test_MOBILE {

    static private AndroidDriver<?> androidDriver = null;
    static private Properties androidProp = null;
    static private DesiredCapabilities defaultDesiredCapabilities;
    static private ExtentReports extentReports;
    static private ExtentTest extentTest;
    private static MobileSteps steps = null;


    @BeforeAll
    static void beforeAll() {
        defaultDesiredCapabilities = new DesiredCapabilities(new DesiredCapabilities());
        defaultDesiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        defaultDesiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        defaultDesiredCapabilities.setCapability(MobileCapabilityType.APP, RESOURCES_PATH +File.separator +"app" + EXT_ANDROID);

        ManagementDriver.startMobileDriver(ANDROID,defaultDesiredCapabilities);
        androidDriver = ManagementDriver.getAndroidDriver();

        extentReports = new ExtentReports(REPORT_PATH + File.separator + "Report_Mobile" + EXT_HTML, true);
        extentReports.loadConfig(new File(REPORT_CONFIG_XML));
        androidProp = new Utility().loadProp("android");
        steps = new MobileSteps();
    }

    @BeforeEach
    void beforeEach() {
    }

    @Test
    @DisplayName("Test app")
    void test_001(TestInfo testInfo){
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        steps.login(androidProp);
        extentTest.log(LogStatus.INFO, "Inserisco l'username e la password ed effettuo il login.", "");
        if(steps.aggiungiUtente(androidProp)){
            extentTest.log(LogStatus.INFO, extentTest.addBase64ScreenShot(Utility.getScreenCastMobile()),"Aggiungo utente 'Vito'.");
        }
        if(steps.eliminaUtenti(androidProp)){
            extentTest.log(LogStatus.INFO, extentTest.addBase64ScreenShot(Utility.getScreenCastMobile()),"Elimino tutti gli utenti.");
        }
        steps.backClear(androidProp);
        extentTest.log(LogStatus.INFO, "Torno alla schermata Login, inserisco i dati e faccio refresh campi.", "");
        if(steps.errore(androidProp)){
            extentTest.log(LogStatus.INFO, extentTest.addBase64ScreenShot(Utility.getScreenCastMobile()),"Verifico errore in caso di mancata password.");
        }
    }

    @Test
    @DisplayName("test user")
    void test_002(TestInfo testInfo){
        extentTest = extentReports.startTest(testInfo.getDisplayName());
        steps.loginUser(androidProp);
        extentTest.log(LogStatus.INFO,"", "Inserisco l'username e la password ed effettuo il login.");
        if(steps.welcome(androidProp)){
            extentTest.log(LogStatus.INFO, extentTest.addBase64ScreenShot(Utility.getScreenCastMobile()),"Verifico welcome user.");
        }

    }

    @ParameterizedTest(name = "q = {0}")
    @CsvSource({"Vito, Giuseppe, Gennaro"})
    @DisplayName("test user")
    void test_003(String q, String g, String r,TestInfo testInfo){
        steps.loginUser(androidProp);

        /*androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS) ;
        if(steps.listaUser(androidProp,q)) {
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.btn.aggiungi"))).click();
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.newUtente"))).sendKeys(q);
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.btn.aggUtente"))).click();
        }

         */

        androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS) ;
        if(steps.listaUser(androidProp,g)) {
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.btn.aggiungi"))).click();
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.newUtente"))).sendKeys(g);
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.btn.aggUtente"))).click();
        }

        androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS) ;
        if(steps.listaUser(androidProp,r)) {
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.btn.aggiungi"))).click();
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.newUtente"))).sendKeys(r);
            androidDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            androidDriver.findElement(By.id(androidProp.getProperty("app.id.btn.aggUtente"))).click();
        }
    }

    @AfterEach
    void tearDown() {
        extentReports.endTest(extentTest);
    }

    @AfterAll
    static void tearDownAll() {
        //ManagementDriver.stopDriver();
        extentReports.flush();
    }
}
