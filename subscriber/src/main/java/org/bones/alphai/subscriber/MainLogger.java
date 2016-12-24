package org.bones.alphai.subscriber;

import java.io.IOException;
import java.util.logging.*;


public class MainLogger {

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static public void setup(String logFilePath, String logLevel) throws IOException
    {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        //Suppress console logger
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler(logFilePath);

        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }
}
