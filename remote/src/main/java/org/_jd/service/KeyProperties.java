package org._jd.service;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KeyProperties {
    public String getKey(String filename, String key){
        Properties keyProperties = new Properties();
        try {
            InputStream path = this.getClass().getClassLoader().getResourceAsStream(filename);
            keyProperties.load(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyProperties.getProperty(key);
    }
}
