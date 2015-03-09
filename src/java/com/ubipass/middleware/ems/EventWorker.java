//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/ems/EventWorker.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 08:14:58 $

package com.ubipass.middleware.ems;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.plugin.Event;
import com.ubipass.middleware.util.log.SystemLogger;

/**
 * EventWorker Class implements Runnable to get Event from
 * EventQueue and then send it to EventLogger.
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.12 $
 */
class EventWorker implements Runnable {

    private boolean isRunning;

    /**
     * Constuctor.
     */
    public EventWorker() {
        isRunning = true;
    }

    /**
     * Stop worker thread.
     */
    public void stopEventWorker() {
        isRunning = false;
    }

    /**
     * Monitor event queue and write event to database.
     */
    public void run() {

        Event event;

        while (isRunning) {
            try {
                // get event from eventqueue
                event = EMSUtil.eventQueue.take();
            } catch (InterruptedException e) {
                continue;
            }

            try {
                // log event to database by eventlogger
                EMSUtil.logEvent(event);
            } catch (DBOperateException e) {
                SystemLogger.error("[EventWorker] run() error: " + e.getMessage());
                return;
            }
        }

        // now isRunning is set to false, need to write all remaining events in queue to dbLogger
        while (true) {
            try {
                event = EMSUtil.eventQueue.remove();
            } catch (Exception e) {
                // no element in the queue
                return;
            }

            try {
                // log event to database by eventlogger
                EMSUtil.logEvent(event);
            } catch (DBOperateException e) {
                SystemLogger.error("[EventWorker] run() error: " + e.getMessage());
                return;
            }
        }

    }

}
