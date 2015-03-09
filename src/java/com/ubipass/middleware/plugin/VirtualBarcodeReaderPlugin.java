//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/VirtualBarcodeReaderPlugin.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/18 05:29:06 $

package com.ubipass.middleware.plugin;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

/**
 * Virtual barcode reader plug-in.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public class VirtualBarcodeReaderPlugin extends VirtualReaderPlugin {

    /**
     * Constructor.
     */
    public VirtualBarcodeReaderPlugin() {
        tagIdLength = 96;
    }

    /**
     * Generate random tag id and write them to the queue.
     */
    public void run() {

        if (openMode == PRINTER_MODE) {
            return;
        }

        Event event;
        String id;
        Random rnd = new Random();

        while (isRunning) {
            // simulate read one tag
            // generate random tag ID
            id = convertTagID(new BigInteger(tagIdLength, rnd).toString(16));
            event = new EventImpl();
            event.setEventType(Event.READ);
            event.setReaderID(readerID);
            event.setTagID(id);
            event.setEventTime(new Date().getTime());

            try {
                queue.put(event);
            } catch (Exception e) {

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
