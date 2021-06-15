package com.caiata.web;

import com.caiata.steps.BershkaSteps;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Properties;

import static com.caiata.utils.GlobalParameters.*;
import static com.caiata.utils.GlobalParameters.REPORT_CONFIG_XML;
import static org.junit.jupiter.api.Assertions.fail;

public class Test_Bersha {

    static private AndroidDriver<?> androidDriver = null;
    static private Properties androidProp = null;
    static private DesiredCapabilities defaultDesiredCapabilities;
    static private ExtentReports extentReports;
    static private ExtentTest extentTest;
    private static BershkaSteps steps = null;

    @BeforeAll
    static void beforeAll() {
        defaultDesiredCapabilities = new DesiredCapabilities(new DesiredCapabilities());
        defaultDesiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        defaultDesiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        defaultDesiredCapabilities.setCapability(MobileCapabilityType.APP, RESOURCES_PATH + File.separator +"Bershka Fashion and trends online_v2.57.2_apkpure.com" + EXT_ANDROID);

        ManagementDriver.startMobileDriver(ANDROID,defaultDesiredCapabilities);
        androidDriver = ManagementDriver.getAndroidDriver();

        extentReports = new ExtentReports(REPORT_PATH + File.separator + "Report_Bershka" + EXT_HTML, true);
        extentReports.loadConfig(new File(REPORT_CONFIG_XML));
        androidProp = new Utility().loadProp("bershka");
        steps = new BershkaSteps();
    }

    @BeforeEach
    void beforeEach() {
    }

    @Test
    @Order(1)
    @DisplayName("test inizializza bershka")
    void test_001(TestInfo testInfo){
        try {
            extentTest = extentReports.startTest(testInfo.getDisplayName());
            steps.inizializza(androidProp);
            extentTest.log(LogStatus.INFO, "Inizializzo l'app Bershka.", "");

            steps.addCart(androidProp);
            extentTest.log(LogStatus.INFO, "Aggiungo al carrello.", "");

            steps.prezzo(androidProp);
            extentTest.log(LogStatus.INFO, "Confronto prezzo.", "");

            steps.svuotaCarrello(androidProp);
            extentTest.log(LogStatus.INFO, "Svuoto il carrello.", "");

            extentTest.log(LogStatus.INFO, "Torno indietro.", "");
            if(steps.indietro(androidProp)) {
                extentTest.log(LogStatus.INFO, extentTest.addBase64ScreenShot(Utility.getScreenCastMobile()),"Errore nel tornare indietro." );
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            extentTest.log(LogStatus.INFO, extentTest.addBase64ScreenShot(Utility.getScreenCastMobile()),"Errore." + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        extentReports.endTest(extentTest);
    }

    @AfterAll
    static void tearDownAll() {
        ManagementDriver.stopDriver();
        extentReports.flush();
    }
}
