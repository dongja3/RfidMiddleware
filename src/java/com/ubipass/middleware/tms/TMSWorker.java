//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/TMSWorker.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/10 09:18:57 $

package com.ubipass.middleware.tms;

import com.ubipass.middleware.ems.EMSUtil;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.tms.task.MonitorTask;
import com.ubipass.middleware.tms.task.NewTagMonitorTask;
import com.ubipass.middleware.tms.task.PackingByIdleMonitorTask;
import com.ubipass.middleware.tms.task.PackingByNumberMonitorTask;
import com.ubipass.middleware.tms.task.PackingTillEmptyMonitorTask;
import com.ubipass.middleware.tms.task.PrintingMonitorTask;
import com.ubipass.middleware.tms.task.RemoveTagMonitorTask;
import com.ubipass.middleware.util.ThreadPool;
import com.ubipass.middleware.util.log.SystemLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * TMS Utility class.
 *
 * @author Shen Xiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.51 $
 */
public class TMSWorker {

    // map to store tasks
    private static Map<String, Stoppable> taskMap = null;

    // destination queue
    private static ArrayBlockingQueue<FileDestination> fileDestinationQueue = null;

    private static final int QUEUE_SIZE = 1000;

    private static boolean isRunning = false;

    /**
     * Start task management system.
     *
     * @throws Exception
     */
    public static void startUp() throws Exception {

        // check the license
        long expiredDate;

        try {
            expiredDate = EMSUtil.checkLicence();
        } catch (Exception e) {
            SystemLogger.error("System cannot find valid license");
            return;
        }

        if (expiredDate < new Date().getTime()) {
            SystemLogger.error("License has expired");
            return;
        }

        SystemLogger.info("[TMS] TMS starting...");

        taskMap = new HashMap<String, Stoppable>();

        fileDestinationQueue = new ArrayBlockingQueue<FileDestination>(QUEUE_SIZE);

        TMSWorker.startFileSendTask();
        TMSWorker.startAutoStartupMonitorTask();

        isRunning = true;
        SystemLogger.info("[TMS] TMS started successfully");

    }

    /**
     * Safe stop task management system.
     */
    public static void safeStop() {

        if (!isRunning) {
            SystemLogger.info("[TMS] TMS stopped");
            return;
        }

        // stop LocalFile Send task
        Stoppable localFileSendTask = taskMap.remove("FILE");
        localFileSendTask.safeStop();

        // stop monitor tasks
        for (Stoppable task : TMSWorker.taskMap.values()) {
            task.safeStop();
        }

        isRunning = false;
        SystemLogger.info("[TMS] TMS stopped successfully");

    }

    /**
     * Start a monitor task by id.
     *
     * @param id task id
     * @throws Exception
     */
    public static void startMonitorTask(int id) throws Exception {

        if (TMSWorker.taskMap.containsKey(String.valueOf(id))) {
            // task is running
            return;
        }

        // get monitor task info. from database
        TaskPO task = TaskDAO.selectAutoTypeTaskById(id);

        if (task == null) {
            throw new Exception("[TMSWorker] startMonitorTask() error: Task does not exist or it is not automatic task");
        } else {
            startMonitorTask(task);
        }

    }

    /**
     * Stop a monitor task by id.
     *
     * @param id task id
     */
    public static void stopMonitorTask(int id) {

        String taskId = String.valueOf(id);

        if (TMSWorker.taskMap.containsKey(taskId)) {
            Stoppable task = TMSWorker.taskMap.get(String.valueOf(id));

            try {
                task.safeStop();
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                return;
            }

            // wait until task stopped
            while (TMSWorker.taskMap.containsKey(taskId)) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        }

    }

    /**
     * Check if task is running.
     *
     * @param id
     * @return true if the task is running
     */
    public static boolean isMonitorTaskWorking(int id) {

        return TMSWorker.getTaskMap().containsKey(String.valueOf(id));

    }

    /**
     * Start monitor task.
     *
     * @param taskpo
     * @throws Exception
     */
    private static void startMonitorTask(TaskPO taskpo)
        throws Exception {

        if (taskpo == null) {
            return;
        }

        ExecutorService ex = ThreadPool.getInstance();
        MonitorTask task = getMonitorTask(taskpo);

        ex.execute(task);

    }

    /**
     * Start all monitor task whose startup type is automatic.
     *
     * @throws Exception
     */
    private static void startAutoStartupMonitorTask() throws Exception {

        List<TaskPO> taskList = TaskDAO.getAutomaticTask();

        for (TaskPO taskpo : taskList) {
            TMSWorker.startMonitorTask(taskpo);
        }

    }

    /**
     * Start LocalFile Send Task thread.
     */
    private static void startFileSendTask() {

        ExecutorService ex = ThreadPool.getInstance();

        LocalFileSendTask t = new LocalFileSendTask();
        ex.execute(t);

        taskMap.put("FILE", t);

    }

    /**
     * Get monitor task.
     *
     * @param taskpo
     * @return Task
     * @throws Exception
     */
    private static MonitorTask getMonitorTask(TaskPO taskpo) throws Exception {

        MonitorTask t;

        if (taskpo.getTriggerMode() == null) {
            throw new Exception("[TMSWorker] getMonitorTask() error: Task trigger mode is not defined");
        }
        // new tag
        else if (taskpo.getTriggerMode().equals("A")) {
            t = new NewTagMonitorTask(taskpo);
        }
        // remove tag
        else if (taskpo.getTriggerMode().equals("R")) {
            t = new RemoveTagMonitorTask(taskpo);
        }
        // packaging by number
        else if (taskpo.getTriggerMode().equals("N")) {
            t = new PackingByNumberMonitorTask(taskpo);
        }
        // packaging till empty
        else if (taskpo.getTriggerMode().equals("E")) {
            t = new PackingTillEmptyMonitorTask(taskpo);
        }

        // pringTag
        else if (taskpo.getTriggerMode().equals("P")) {
            t = new PrintingMonitorTask(taskpo);
        }

        // packaging by idle
        else if (taskpo.getTriggerMode().equals("I")) {
            t = new PackingByIdleMonitorTask(taskpo);
        } else {
            throw new Exception("Unknown task trigger mode");
        }

        return t;

    }

    /**
     * Return task map.
     *
     * @return task map
     */
    public static Map<String, Stoppable> getTaskMap() {
        return taskMap;
    }

    /**
     * Return file destination queue.
     *
     * @return Returns the fileDestinationQueue.
     */
    public static ArrayBlockingQueue<FileDestination> getFileDestinationQueue() {
        return fileDestinationQueue;
    }

}
