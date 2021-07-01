package com.caiata.cucumber;

import com.caiata.steps.EbaySteps;
import com.caiata.utils.DefaulChromeOptions;
import com.caiata.utils.ManagementDriver;
import com.caiata.utils.ModelloEbay;
import com.caiata.utils.Utility;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Properties;

public class EbayCucumber {

    static private ModelloEbay modello = new ModelloEbay();
    static private WebDriver driver = null;
    static private Properties webProp = null;
    static private EbaySteps steps = null;
    static private DefaulChromeOptions defaulChromeOptions;

    @Given("the user start driver")
    public void beforeAll(){
        defaulChromeOptions = new DefaulChromeOptions(new ChromeOptions());
        webProp = new Utility().loadProp("ebay");
        ManagementDriver.startDriver(defaulChromeOptions);
        driver = ManagementDriver.getChromeDriver();
        steps = new EbaySteps();
    }

    @When("the user search item (.*)")
    public void search(String q){
        driver.get(webProp.getProperty("ebay.url"));
        steps.closeBanner(webProp);
        steps.search(webProp,q);
    }


    @Then("close driver")
    public void closeBrowser() { ManagementDriver.stopDriver(); }
}
