//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/PackingByIdleMonitorTask.java,v $
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
 * @version $Revision: 1.40 $
 */
public class PackingByIdleMonitorTask extends MonitorTask {

    private int period;
    private int idle;

    private List<ObservationPO> newList;

    /**
     * Constructor
     *
     * @param taskpo
     * @throws Exception
     */
    public PackingByIdleMonitorTask(TaskPO taskpo) throws Exception {

        super(taskpo);

        period = taskpo.getParameter();

    }

    /**
     * Packaging by idle time.
     *
     * @throws Exception
     */
    public void monitor() throws Exception {

        observationList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

        if (observationList != null && observationList.size() > 0) {
            // new tag detected
            countId = observationList.get(observationList.size() - 1).getId();
            idle = 0;

            while (idle < period) {
                // sleep 1s
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }

                // detect new tags
                newList = ObservationQueryDAO.getObservationsByDeviceId(device.getDeviceID(), countId);

                if (newList != null && newList.size() > 0) {
                    // new tag detected
                    idle = 0;
                    observationList.addAll(newList);

                    // update countId
                    countId = observationList.get(observationList.size() - 1).getId();
                } else {
                    // no new tag detected
                    if (!isNeedDBAccess()) {
                        // device stopped
                        break;
                    }

                    idle++;
                }
            }

            // write PML
            sendPML(pmlBuilder.buildPml(observationList));
        }

    }

}
