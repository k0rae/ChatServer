package chat.server;

import java.util.logging.*;

import java.io.FileInputStream;
import java.util.Properties;

public class Logger {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    static {
        try {
            Properties props = new Properties();
            FileInputStream configFile = new FileInputStream("config.properties");
            props.load(configFile);
            configFile.close();

            boolean logToConsole = Boolean.parseBoolean(props.getProperty("logToConsole", "true"));
            boolean logToFile = Boolean.parseBoolean(props.getProperty("logToFile", "false"));
            String logFileName = props.getProperty("logFileName", "log.txt");

            LOGGER.setLevel(Level.ALL);
            if (logToFile) {
                FileHandler fileHandler = new FileHandler(logFileName);
                fileHandler.setLevel(Level.ALL);
                LOGGER.addHandler(fileHandler);
            }
            if (logToConsole) {
                ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.setLevel(Level.ALL);
                LOGGER.addHandler(consoleHandler);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Не удалось загрузить конфигурационный файл", e);
        }
    }

    private Logger() {}

    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }

}