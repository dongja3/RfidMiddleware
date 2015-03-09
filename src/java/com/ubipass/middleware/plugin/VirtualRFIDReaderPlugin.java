//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/VirtualRFIDReaderPlugin.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/18 05:29:06 $

package com.ubipass.middleware.plugin;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

/**
 * Virtual RFID fixed reader plug-in.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public class VirtualRFIDReaderPlugin extends VirtualReaderPlugin {

    private Tag[] tags;
    private int tagListSize;

    /**
     * Constructor.
     */
    public VirtualRFIDReaderPlugin() {
        tags = null;
        tagListSize = 6;
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
        String id[] = new String[tagListSize];
        Tag[] taglist = new Tag[tagListSize];
        Random rnd = new Random();
        long date;

        while (isRunning) {
            date = new Date().getTime();

            // simulate tags moving in
            for (int i = 0; i < tagListSize; i++) {
                // generate random tag ID
                id[i] = convertTagID(new BigInteger(tagIdLength, rnd).toString(16));
                event = new EventImpl();
                event.setEventType(Event.ADD);
                event.setReaderID(readerID);
                event.setTagID(id[i]);
                event.setEventTime(date + i);

                try {
                    queue.put(event);
                } catch (Exception e) {

                }

                taglist[i] = new TagImpl(event.getTagID(), event.getEventTime());
            }

            tags = taglist;

            // sleep persistTime seconds before moving out
            try {
                Thread.sleep(persistTime * 1000);
            } catch (Exception e) {

            }

            date = new Date().getTime();

            // simulate tags moving out
            for (int i = 0; i < tagListSize; i++) {
                event = new EventImpl();
                event.setEventType(Event.REMOVE);
                event.setReaderID(readerID);
                event.setTagID(id[i]);

                // note that use different timestamp for remove time
                event.setEventTime(date + i);

                try {
                    queue.put(event);
                } catch (Exception e) {

                }
            }

            tags = null;

            // sleep 2s
            try {
                Thread.sleep(2000);
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
        return tags;
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
     * @return false
     */
    public boolean isOneTimeReader() {
        return false;
    }

}
