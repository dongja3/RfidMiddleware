//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/VirtualFileReaderPlugin.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/06/01 06:32:10 $

package com.ubipass.middleware.plugin;

import com.ubipass.middleware.util.log.SystemLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * Virtual file reader plug-in.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public class VirtualFileReaderPlugin extends VirtualReaderPlugin {

    private static final String ID_FILE = "tagid.txt";

    /**
     * Get simulated tag ID from tag id file.
     */
    public void run() {

        if (openMode == PRINTER_MODE) {
            return;
        }

        BufferedReader reader;
        Event event;
        String id;

        while (isRunning) {
            // try to open tag id file
            try {
                reader = new BufferedReader(new FileReader(SystemLogger.getServletRoot() + ID_FILE));
            } catch (IOException e) {
                // something wrong
                reader = null;
            }

            if (reader != null) {
                try {
                    id = reader.readLine();

                    while (id != null) {
                        // not the end of the file
                        event = new EventImpl();
                        event.setEventType(Event.READ);
                        event.setReaderID(readerID);
                        event.setTagID(id);
                        event.setEventTime(new Date().getTime());

                        try {
                            queue.put(event);
                        } catch (Exception e) {

                        }

                        id = reader.readLine();
                    }
                } catch (IOException e) {
                    // ignore the error
                } finally {
                    try {
                        reader.close();
                    } catch (IOException e) {

                    }
                }
            }

            // sleep persistTime seconds
            try {
                Thread.sleep(persistTime * 1000);
            } catch (Exception e) {

            }

        }

    }

    /**
     * Return list of tags that reader can detect now.
     *
     * @return Array of Tag
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public Tag[] getTagList()
        throws DeviceOperationException, UnsupportedFeatureException {
        throw new UnsupportedFeatureException("The device does not support getTagList()");
    }

    /**
     * Check if device is a reader.
     *
     * @return true
     */
    public boolean isReader() {
        return true;
    }

    /**
     * Check if device is a printer.
     *
     * @return true
     */
    public boolean isPrinter() {
        return true;
    }

    /**
     * Check if device is an one-time reader.
     *
     * @return true
     */
    public boolean isOneTimeReader() {
        return true;
    }

}
