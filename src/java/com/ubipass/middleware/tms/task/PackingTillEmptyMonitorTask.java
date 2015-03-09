//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/PackingTillEmptyMonitorTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:26 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.po.ObservationPO;
import com.ubipass.middleware.jdbc.po.TaskPO;

import java.util.List;

/**
 * PackagingTillEmpty Monitor Task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.38 $
 */
public class PackingTillEmptyMonitorTask extends MonitorTask {

    protected List<ObservationPO> newList;

    /**
     * Constructor.
     *
     * @param taskpo
     * @throws Exception
     */
    public PackingTillEmptyMonitorTask(TaskPO taskpo) throws Exception {

        super(taskpo);

    }

    /**
     * Packaging till empty.
     *
     * @throws Exception
     */
    public void monitor() throws Exception {

        observationList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

        if (observationList != null && observationList.size() > 0) {
            // new tag detected
            countId = observationList.get(observationList.size() - 1).getId();

            while (ObservationQueryDAO.isTagDetectedByDevice(device.getDeviceID())) {
                // device is not empty
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }

                // detect new tags
                newList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

                if (newList != null && newList.size() > 0) {
                    // new tag detected
                    observationList.addAll(newList);

                    // update countId
                    countId = observationList.get(observationList.size() - 1).getId();
                }
            }

            // write PML
            sendPML(pmlBuilder.buildPml(observationList));
        }

    }

}
