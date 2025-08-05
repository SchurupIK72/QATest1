package util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {
    public static Logger setupLogger(String name) {
        Logger logger = Logger.getLogger(name);
        try {
            LogManager.getLogManager().reset();

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            logger.addHandler(ch);

            FileHandler fh = new FileHandler("logs.txt", true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);
            logger.addHandler(fh);

            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.out.println("Logger failed: " + e.getMessage());
        }

        return logger;
    }
}