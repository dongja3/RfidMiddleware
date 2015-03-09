//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/PrintingMonitorTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:26 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.po.ObservationPO;
import com.ubipass.middleware.jdbc.po.TaskPO;

/**
 * Printing Monitor Task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.8 $
 */
public class PrintingMonitorTask extends MonitorTask {

    /**
     * Constructor.
     *
     * @param taskpo
     * @throws Exception
     */
    public PrintingMonitorTask(TaskPO taskpo) throws Exception {

        super(taskpo);

    }

    /**
     * Detect newly added observation.
     *
     * @throws Exception
     */
    protected void monitor() throws Exception {

        observationList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

        if (observationList != null && observationList.size() > 0) {
            // update countId
            countId = observationList.get(observationList.size() - 1).getId();

            for (ObservationPO observation : observationList) {
                // read ID is product category ID
                // send tag print command
                sendPML(pmlBuilder.printTagPml(observation.getTagId()));
            }
        }

    }

}
