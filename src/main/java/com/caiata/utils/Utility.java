package com.caiata.utils;

import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;
import java.text.SimpleDateFormat;


import static com.caiata.utils.GlobalParameters.RESOURCES_PATH;
import static com.caiata.utils.GlobalParameters.SCREENSHOT_PATH;

public class Utility {

    public Properties loadProp(String propName){

        String propPath = RESOURCES_PATH  + File.separator + "properties" + File.separator + propName + ".properties";
        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(propPath));
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("File " + propName + " non trovato.");
        }
        return prop;
    }

    public void getScreen(){
        try{
            SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMddHHmmss");
            String sDate = oSDF.format(new Date());
            byte[] imageByte = ((TakesScreenshot)ManagementDriver.getDriver()).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(SCREENSHOT_PATH + "/" + sDate + ".png"), imageByte);
        }catch(IOException e){
            Assert.fail("Errore: " + e.getMessage());
        }
    }
}
