//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/MonitorTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:08 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.ems.EMSUtil;
import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.tms.task.Task;
import com.ubipass.middleware.tms.Stoppable;
import com.ubipass.middleware.tms.TMSWorker;

/**
 * Monitor task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public abstract class MonitorTask extends Task implements Runnable, Stoppable {

    protected boolean isRunning;

    protected int countId;

    /**
     * Constructor.
     *
     * @param taskpo
     * @throws Exception
     */
    public MonitorTask(TaskPO taskpo) throws Exception {

        super(taskpo);

        isRunning = (httpDesList.size() + localFileDesList.size()) > 0;

        // get current count id
        countId = ObservationQueryDAO.getCountIdForDevice(device
            .getDeviceID());

        if (isRunning) {
            // add the task to taskmap in TMSWorker
            TMSWorker.getTaskMap().put(String.valueOf(taskpo.getId()), this);
        }

    }

    /**
     * Safe stop the task.
     */
    public void safeStop() {
        isRunning = false;
        TMSWorker.getTaskMap().remove(String.valueOf(taskpo.getId()));
    }

    /**
     * Implement runnable(), periodically call monitor().
     */
    public void run() {

        SystemLogger.info("Task " + taskpo.getTaskName()
            + " started successfully");

        while (isRunning) {
            try {
                // sleep 1s
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }

            // check if device is running
            if (isNeedDBAccess()) {
                try {
                    monitor();
                } catch (Exception e) {
                    SystemLogger.error(e.getMessage());
                }
            }
        }

        // remove the entry in task map
        TMSWorker.getTaskMap().remove(String.valueOf(taskpo.getId()));

        SystemLogger.info("Task " + taskpo.getTaskName()
            + " stopped successfully");

    }

    /**
     * Abstract method, subclass must implement monitoring business logic.
     *
     * @throws Exception
     */
    protected abstract void monitor() throws Exception;

    /**
     * Check if monitor() needs to access database for the device.
     *
     * @return true if DB access is needed
     */
    protected boolean isNeedDBAccess() {

        return DeviceList.getDeviceStatus(device.getDeviceID())
            || !EMSUtil.eventQueue.isEmpty();

    }

}