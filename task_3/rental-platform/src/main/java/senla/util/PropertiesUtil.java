package senla.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadFile();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadFile() {
        try (var stream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке application.properties", e);
        }
    }
}
