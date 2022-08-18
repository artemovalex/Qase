package org.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();

        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    public static Properties loadProperties() {
        Properties properties = loadProperties("config.properties");
        String user = properties.getProperty("user");
        Properties userProperties = loadProperties(user + ".properties");
        properties.putAll(userProperties);
        return properties;
    }
}