//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/NewTagMonitorTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:26 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;

/**
 * NewTag Monitor Task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.37 $
 */
public class NewTagMonitorTask extends MonitorTask {

    /**
     * Constructor
     *
     * @param taskpo
     * @throws Exception
     */
    public NewTagMonitorTask(TaskPO taskpo) throws Exception {

        super(taskpo);

    }

    /**
     * Detect newly added observation.
     *
     * @throws Exception
     */
    public void monitor() throws Exception {

        observationList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

        if (observationList != null && observationList.size() > 0) {
            // update countId
            countId = observationList.get(observationList.size() - 1).getId();

            sendPML(pmlBuilder.buildPml(observationList));
        }

    }

}
