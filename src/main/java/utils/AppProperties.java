package utils;

import java.util.Properties;

public class AppProperties {
    private static Properties properties = new Properties();

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        AppProperties.properties = properties;
    }
}
