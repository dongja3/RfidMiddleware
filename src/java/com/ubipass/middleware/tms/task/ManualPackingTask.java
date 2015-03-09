//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/ManualPackingTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:26 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;

/**
 * Manual Packing Task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.5 $
 */
public class ManualPackingTask extends Task {

    /**
     * Constructor.
     *
     * @param task
     * @throws Exception
     */
    public ManualPackingTask(TaskPO task) throws Exception {
        super(task);
    }

    /**
     * Do manual packaging.
     *
     * @param startId
     * @param endId
     * @throws Exception
     */
    public void doPack(int startId, int endId) throws Exception {

        observationList = ObservationQueryDAO.getPackageByDeviceId(device.getDeviceID(), startId, endId);

        sendPML(pmlBuilder.buildPml(observationList));

    }

}
