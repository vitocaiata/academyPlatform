package com.caiata.utils;

import org.openqa.selenium.edge.EdgeOptions;

public class DefaultEdgeOptions extends EdgeOptions {
    public DefaultEdgeOptions (EdgeOptions customEdgeOptions){
        if(customEdgeOptions == null) return;
        for(String key : customEdgeOptions.getCapabilityNames()){
            Object value = customEdgeOptions.getCapability(key);
            setCapability(key,value);
        }
    }
}
