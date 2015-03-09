//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/task/Task.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:28:08 $

package com.ubipass.middleware.tms.task;

import com.ubipass.middleware.jdbc.FileDestinationDAO;
import com.ubipass.middleware.jdbc.HttpDestinationDAO;
import com.ubipass.middleware.jdbc.SysConfigDAO;
import com.ubipass.middleware.jdbc.TaskDeviceDAO;
import com.ubipass.middleware.jdbc.po.FileDestinationPO;
import com.ubipass.middleware.jdbc.po.HttpDestinationPO;
import com.ubipass.middleware.jdbc.po.ObservationPO;
import com.ubipass.middleware.jdbc.po.TaskDevicePO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.tms.util.PmlBuilder;
import com.ubipass.middleware.tms.FileDestination;
import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.tms.HTTPSendTask;
import com.ubipass.middleware.util.ThreadPool;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Base class for monitor task and manual task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public class Task {

    public static final int FULL_PML = 0;

    public static final int SIMPLIFIED_PML = 1;

    protected TaskPO taskpo;

    // device assigned to the task
    protected TaskDevicePO device = null;

    protected List<ObservationPO> observationList;

    // destination list associated with the task
    protected List<HttpDestinationPO> httpDesList = null;
    protected List<FileDestinationPO> localFileDesList = null;

    protected ArrayBlockingQueue<FileDestination> fileDestinationQueue = null;

    protected PmlBuilder pmlBuilder;

    /**
     * Constructor.
     *
     * @param taskpo
     * @throws Exception
     */
    public Task(TaskPO taskpo) throws Exception {

        this.taskpo = taskpo;

        // get task's device
        List<TaskDevicePO> taskDeviceList = TaskDeviceDAO.selectTaskDeviceByTaskId(taskpo.getId());

        // current Middleware design requires that only one device is assigned to a task
        if (taskDeviceList.size() > 0) {
            device = taskDeviceList.get(0);
        } else {
            throw new Exception("No device is assigned to task "
                + taskpo.getTaskName());
        }

        int format;

        if (("F").equalsIgnoreCase(taskpo.getFormatType())) {
            format = FULL_PML;
        } else {
            format = SIMPLIFIED_PML;
        }

        String pmlTopLevelID;
        String pmlReaderID;

        if ("M".equalsIgnoreCase(taskpo.getTopLevelIDType()))
            pmlTopLevelID = SysConfigDAO.getSystemName();
        else
            pmlTopLevelID = device.getGroupName();

        if ("N".equalsIgnoreCase(taskpo.getReaderIDType()))
            pmlReaderID = device.getDeviceName();
        else
            pmlReaderID = device.getDeviceID();

        // get proper command
        String command = taskpo.getCommand();

        if (command == null || command.length() == 0) {
            command = device.getCommand();
        }

        pmlBuilder = new PmlBuilder(pmlTopLevelID, pmlReaderID, command, format, taskpo.getParameter());

        httpDesList = HttpDestinationDAO.selectHttpDestinationByTaskId(taskpo.getId());
        localFileDesList = FileDestinationDAO.getFileDestinationByTaskId(taskpo.getId());

        // get queue from TMSWorker
        fileDestinationQueue = TMSWorker.getFileDestinationQueue();

    }

    /**
     * Add entries in destination queue.
     *
     * @param pml
     */
    protected void sendPML(String pml) {

        for (HttpDestinationPO po : httpDesList) {
            // run a thread to send PML to http destination
            ThreadPool.getInstance().execute(new HTTPSendTask(po, pml));
        }

        // put destination into filedestination queue
        if (localFileDesList.size() > 0) {
            Calendar cal = Calendar.getInstance();

            // construct file name
            String fileName = "[" + taskpo.getTaskName() + "]_"
                + cal.get(Calendar.YEAR) + "_"
                + getTwoDigits(cal.get(Calendar.MONTH) + 1) + "_"
                + getTwoDigits(cal.get(Calendar.DAY_OF_MONTH)) + "_"
                + getTwoDigits(cal.get(Calendar.HOUR_OF_DAY)) + "_"
                + getTwoDigits(cal.get(Calendar.MINUTE)) + "_"
                + getTwoDigits(cal.get(Calendar.SECOND)) + ".xml";

            for (FileDestinationPO po : localFileDesList) {
                try {
                    fileDestinationQueue.put(new FileDestination(po.getFolder(), fileName, pml));
                } catch (InterruptedException ex) {

                }
            }
        }

    }

    /**
     * Convert 2-digit integer to string.
     *
     * @param i integer
     * @return 2-digit string with leading zero if necessary
     */
    private String getTwoDigits(int i) {

        String str = String.valueOf(i);

        if (i < 10) {
            // prepend zero
            str = "0" + str;
        }

        return str;

    }

}
