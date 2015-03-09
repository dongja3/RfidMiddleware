//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/RemoveTagMonitorTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:26 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;

import java.util.Date;

/**
 * RemoveTag Monitor Task.
 *
 * @author Shen Xiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.32 $
 */

public class RemoveTagMonitorTask extends MonitorTask {

    private long lastQueryTime;

    /**
     * Constructor.
     *
     * @param taskpo
     * @throws Exception
     */
    public RemoveTagMonitorTask(TaskPO taskpo) throws Exception {

        super(taskpo);

        lastQueryTime = new Date().getTime();

    }

    /**
     * Get REMOVE observation.
     *
     * @throws Exception
     */
    public void monitor() throws Exception {

        observationList = ObservationQueryDAO.selectRemoveObservationByDeviceId(device.getDeviceID(), lastQueryTime, new Date().getTime());

        if (observationList != null && observationList.size() > 0) {
            // update lastQueryTime
            lastQueryTime = (observationList.get(observationList.size() - 1)).getRemoveTime() + 1;

            sendPML(pmlBuilder.buildPml(observationList));
        }

    }

}
