//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/util/log/SystemLogger.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/06/01 06:22:46 $

package com.ubipass.middleware.util.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * SystemLogger class for system Logging.
 *
 * @author Dong Hongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.10 $
 */
public class SystemLogger {

    private static final String CONFIG_FILE = "conf/systemlog.properties";
    private static final String LOG_FILE = "logs/middleware.log";
    private static final String FILE_KEY = "log4j.appender.IntFile.File";

    private static String servletRoot;

    /**
     * constructor
     */
    public SystemLogger() {
    }

    private static Logger logger;


    /**
     * logging debug info
     *
     * @param s logged message
     */
    public static void debug(String s) {
        logger.debug(s);
    }

    /**
     * logging info info
     *
     * @param s logged message
     */
    public static void info(String s) {
        logger.info(s);
    }

    /**
     * logging warn info
     *
     * @param s logged messge
     */
    public static void warn(String s) {
        logger.warn(s);
    }

    /**
     * logging error info
     *
     * @param s logged message
     */
    public static void error(String s) {
        logger.error(s);
    }

    /**
     * logging fatal info
     *
     * @param s
     */
    public static void fatal(String s) {
        logger.fatal(s);
    }

    /**
     * set absolute path of web application root
     * and set config file and log file of log4j.
     *
     * @param path absolute path of web application root
     */
    public static void setServletRoot(String path) {
        servletRoot = path;
        Properties properties = new Properties();

        try {
            InputStream stream = new FileInputStream(path + CONFIG_FILE);
            properties.load(stream);
        } catch (Exception e) {

        }

        // set FILE property
        properties.setProperty(FILE_KEY, path + LOG_FILE);

        PropertyConfigurator.configure(properties);
        logger = Logger.getLogger(SystemLogger.class);
    }

    /**
     * Return servlet root.
     *
     * @return servlet root
     */
    public static String getServletRoot() {
        return servletRoot;
    }

}
