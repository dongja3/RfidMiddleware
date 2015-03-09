//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/LocalFileSendTask.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/04/20 07:08:44 $

package com.ubipass.middleware.tms;

import com.ubipass.middleware.util.log.SystemLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Task to write PML to local file.
 *
 * @author Shen Jun
 * @author $Author: donghongshan $
 * @version $Revision: 1.18 $
 */
public class LocalFileSendTask implements Runnable, Stoppable {

    private boolean isRunning;
    private ArrayBlockingQueue<FileDestination> fileDesQueue = null;

    /**
     * Constructor.
     */
    public LocalFileSendTask() {
        fileDesQueue = TMSWorker.getFileDestinationQueue();
        isRunning = true;
    }

    /**
     * Stop the thread.
     */
    public void safeStop() {
        isRunning = false;
    }

    /**
     * Get element from file destination queue and write PML to file.
     */
    public void run() {

        while (isRunning) {
            try {
                writePmlToFile(fileDesQueue.take());
            } catch (Exception e) {
                SystemLogger.error("[TMS] LocalFileSendTask run() error: "
                    + e.getMessage());
            }
        }

        // now running is set to false, still need to handle remaining element in the queue
        FileDestination dest;

        while (true) {
            try {
                dest = fileDesQueue.remove();
            } catch (Exception e) {
                // no element in the queue
                return;
            }

            try {
                writePmlToFile(dest);
            } catch (Exception e) {
                SystemLogger.error("[TMS] LocalFileSendTask run() error: "
                    + e.getMessage());
            }
        }

    }

    /**
     * Write PML to local file.
     *
     * @param dest FileDestination
     * @throws Exception
     */
    private void writePmlToFile(FileDestination dest)
        throws Exception {

        File fileDir = new File(dest.getFolder());

        // check the folder exists or not, if no create it
        if (!fileDir.exists() && !fileDir.mkdirs()) {
            throw new Exception("Cannot create folder " + fileDir.getPath());
        }

        // write message
        File file = new File(dest.getFolder(), dest.getFileName());
        FileOutputStream out = new FileOutputStream(file);
        PrintStream p = new PrintStream(out);
        p.println(dest.getMessage());
        p.close();
        out.close();

    }

}
