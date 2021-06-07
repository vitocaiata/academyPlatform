package com.caiata.utils;

import org.openqa.selenium.chrome.ChromeOptions;

public class DefaulChromeOptions extends ChromeOptions {

    public DefaulChromeOptions (ChromeOptions customChromeOptions){
        if(customChromeOptions == null) return;
        for(String key : customChromeOptions.getCapabilityNames()){
            Object value = customChromeOptions.getCapability(key);
            setCapability(key,value);
        }
    }
}
