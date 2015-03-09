//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/PackingByNumberMonitorTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:26 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.po.ObservationPO;
import com.ubipass.middleware.jdbc.po.TaskPO;

import java.util.List;

/**
 * PackagingByIdle Monitor Task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.34 $
 */
public class PackingByNumberMonitorTask extends MonitorTask {

    private int number;
    private int count;

    private List<ObservationPO> newList;

    /**
     * Constructor.
     *
     * @param taskpo
     * @throws Exception
     */
    public PackingByNumberMonitorTask(TaskPO taskpo) throws Exception {

        super(taskpo);

        number = taskpo.getParameter();

    }

    /**
     * Packaging by number.
     *
     * @throws Exception
     */
    public void monitor() throws Exception {

        observationList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

        if (observationList != null && observationList.size() > 0) {
            // new tag detected
            count = observationList.size();
            countId = observationList.get(count - 1).getId();

            while (count < number) {
                // sleep 1s
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }

                // detect new tags
                newList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

                if (newList != null && newList.size() > 0) {
                    // new tag detected
                    count += newList.size();
                    observationList.addAll(newList);

                    // update countId
                    countId = observationList.get(observationList.size() - 1).getId();
                } else {
                    // no new tag detected
                    // check if device is running
                    if (!isNeedDBAccess()) {
                        break;
                    }
                }
            }

            // write PML
            sendPML(pmlBuilder.buildPml(observationList));
        }
    }

}
