package com.caiata.utils;

import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.codec.binary.Base64;
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

    public static String getScreen(){
        String sDate = null;
        try{
            SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMddHHmm");
            sDate = oSDF.format(new Date());
            byte[] imageByte = ((TakesScreenshot)ManagementDriver.getChromeDriver()).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(SCREENSHOT_PATH + "/" + sDate + ".png"), imageByte);
        }catch(IOException e){
            Assert.fail("Errore: " + e.getMessage());
        }
        return sDate;
    }

    public static String getScreenBase64(){
        String base64 = null;
        try{
            base64 = ((TakesScreenshot)ManagementDriver.getChromeDriver()).getScreenshotAs(OutputType.BASE64);
        }catch(Exception e){
            Assert.fail("Errore: " + e.getMessage());
        }
        return "data:image/png;base64," + base64;
    }

    public static String getScreenCast(){
        String sDate = null;
        String base64 = null;
        try{
            SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMddHHmm");
            sDate = oSDF.format(new Date());
            byte[] imageByte = ((TakesScreenshot)ManagementDriver.getChromeDriver()).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(SCREENSHOT_PATH + "/" + sDate + ".png"), imageByte);
            base64 = new String(Base64.encodeBase64(imageByte));
        }catch(IOException e){
            Assert.fail("Errore: " + e.getMessage());
        }
        return "data:image/png;base64," + base64;
    }

}
