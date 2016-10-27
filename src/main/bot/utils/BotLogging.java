package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Petr on 25.10.2016.
 */
public class BotLogging {
    private static final Logger logger = LogManager.getLogger(BotLogging.class);

    public static Logger getLogger() {
        return logger;
    }
}
