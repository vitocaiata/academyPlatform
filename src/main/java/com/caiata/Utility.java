package com.caiata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.caiata.GlobalParameters.RESOURCES_PATH;

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
}
