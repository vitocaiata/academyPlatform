package com.caiata.utils;

import org.openqa.selenium.firefox.FirefoxOptions;

public class DefaultFirefoxOptions extends FirefoxOptions {

    public DefaultFirefoxOptions (FirefoxOptions customFirefoxOptions){
        if(customFirefoxOptions == null) return;
            for(String key : customFirefoxOptions.getCapabilityNames()){
                Object value = customFirefoxOptions.getCapability(key);
                setCapability(key,value);
            }
        }
    }
