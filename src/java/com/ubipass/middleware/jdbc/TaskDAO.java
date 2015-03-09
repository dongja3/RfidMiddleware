//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/TaskDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 08:35:18 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.FileDestinationPO;
import com.ubipass.middleware.jdbc.po.HttpDestinationPO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class to manage task table
 *
 * @author shenxiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.48 $
 */
public class TaskDAO {
    /**
     * Return TaskPO where startup='A' and taskType='A'.
     *
     * @return List <TaskPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<TaskPO> getAutomaticTask() throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tasks WHERE startup='A' AND taskType='A'");
            rs = pstmt.executeQuery();
            List<TaskPO> taskList = new ArrayList<TaskPO>();

            while (rs.next()) {
                TaskPO task = new TaskPO();
                task.setId(rs.getInt("id"));
                task.setTaskName(rs.getString("taskName"));
                task.setTriggerMode(rs.getString("triggerMode"));
                task.setParameter(rs.getInt("parameter"));
                task.setFormatType(rs.getString("formatType"));
                task.setTopLevelIDType(rs.getString("topLevelIDType"));
                task.setReaderIDType(rs.getString("readerIDType"));
                task.setCommand(rs.getString("command"));
                taskList.add(task);
            }

            return taskList;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] getAutomaticTask() failed: "
                + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }


    /**
     * Return TaskPO by taskId and TaskType='A'.
     *
     * @param id
     * @return TaskPO
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static TaskPO selectAutoTypeTaskById(int id) throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tasks WHERE id=? AND TaskType='A'");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                TaskPO task = new TaskPO();
                task.setId(rs.getInt("id"));
                task.setTaskName(rs.getString("taskName"));
                task.setTriggerMode(rs.getString("triggerMode"));
                task.setParameter(rs.getInt("parameter"));
                task.setFormatType(rs.getString("formatType"));
                task.setTopLevelIDType(rs.getString("topLevelIDType"));
                task.setReaderIDType(rs.getString("readerIDType"));
                task.setCommand(rs.getString("command"));
                return task;
            }
            return null;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] selectAutoTypeTaskById() failed: "
                + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    /**
     * Return All TaskPO
     *
     * @return List <TaskPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<TaskPO> getAllTask() throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT id, taskName, taskType, startup, triggerMode, parameter, formatType, "
                + "topLevelIDType, readerIDType, command, description FROM tasks ORDER BY tasks.taskname");
            rs = pstmt.executeQuery();
            List<TaskPO> taskList = new ArrayList<TaskPO>();

            while (rs.next()) {
                TaskPO task = new TaskPO();
                task.setId(rs.getInt(1));
                task.setTaskName(rs.getString(2));
                task.setTaskType(rs.getString(3));
                task.setStartUp(rs.getString(4));
                task.setTriggerMode(rs.getString(5));
                task.setParameter(rs.getInt(6));
                task.setFormatType(rs.getString(7));
                task.setTopLevelIDType(rs.getString(8));
                task.setReaderIDType(rs.getString(9));
                task.setCommand(rs.getString(10));
                task.setDescription(rs.getString(11));
                taskList.add(task);
            }

            return taskList;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] getAllTask() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    /**
     * Return the list of tasks which a user can access.
     *
     * @param userName name of user
     * @return List of TaskPO
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<TaskPO> getTaskByUser(String userName) throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT tasks.id, tasks.taskName, tasks.taskType, "
                + "tasks.startup, tasks.triggerMode, tasks.parameter, tasks.formatType, "
                + "tasks.topLevelIDType, tasks.readerIDType, tasks.command, tasks.description "
                + "FROM users, userdevice, taskdevice, tasks WHERE users.username=? AND users.id=userdevice.userid AND "
                + "userdevice.deviceid=taskdevice.deviceid AND taskdevice.taskid=tasks.id ORDER BY tasks.taskname");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();
            List<TaskPO> taskList = new ArrayList<TaskPO>();

            while (rs.next()) {
                TaskPO task = new TaskPO();
                task.setId(rs.getInt(1));
                task.setTaskName(rs.getString(2));
                task.setTaskType(rs.getString(3));
                task.setStartUp(rs.getString(4));
                task.setTriggerMode(rs.getString(5));
                task.setParameter(rs.getInt(6));
                task.setFormatType(rs.getString(7));
                task.setTopLevelIDType(rs.getString(8));
                task.setReaderIDType(rs.getString(9));
                task.setCommand(rs.getString(10));
                task.setDescription(rs.getString(11));
                taskList.add(task);
            }

            return taskList;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] getTaskByUser() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }


    /**
     * Return TaskPO by id
     *
     * @param id
     * @return List <TaskPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static TaskPO getTaskByID(int id) throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT id, taskName, taskType, startup, triggerMode, parameter, formatType, "
                + "topLevelIDType, readerIDType, command, description FROM tasks WHERE id=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            TaskPO task = null;

            if (rs.next()) {
                task = new TaskPO();
                task.setId(rs.getInt(1));
                task.setTaskName(rs.getString(2));
                task.setTaskType(rs.getString(3));
                task.setStartUp(rs.getString(4));
                task.setTriggerMode(rs.getString(5));
                task.setParameter(rs.getInt(6));
                task.setFormatType(rs.getString(7));
                task.setTopLevelIDType(rs.getString(8));
                task.setReaderIDType(rs.getString(9));
                task.setCommand(rs.getString(10));
                task.setDescription(rs.getString(11));
            }

            return task;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] getTaskByID() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    /**
     * Insert a task.
     *
     * @param task
     * @param groupId
     * @param deviceId
     * @param httpList
     * @param fileList
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static void insertTask(TaskPO task, String groupId, String deviceId,
                                  List<HttpDestinationPO> httpList, List<FileDestinationPO> fileList)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement psselectAll = null;
        PreparedStatement psInsertTaskDevice = null;
        PreparedStatement psInsertTaskHttp = null;
        PreparedStatement psInsertTaskFile = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            ps = conn.prepareStatement("INSERT INTO tasks(taskName, taskType, startup, "
                + "triggerMode, parameter, formatType, topLevelIDType, readerIDType, "
                + "command, description)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)");

            psselectAll = conn.prepareStatement("SELECT id FROM tasks WHERE taskName=?");

            psInsertTaskDevice = conn.prepareStatement("INSERT INTO taskdevice(taskid,deviceid,groupid ) VALUES(?,?,?)");

            psInsertTaskHttp = conn.prepareStatement("INSERT INTO httpdestination(taskid,ip,port,path,username,password) VALUES (?,?,?,?,?,?)");

            psInsertTaskFile = conn.prepareStatement("INSERT INTO filedestination(taskid,folder) VALUES (?,?)");

            // insert one row in table tasks
            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskType());
            ps.setString(3, task.getStartUp());
            ps.setString(4, task.getTriggerMode());
            ps.setInt(5, task.getParameter());
            ps.setString(6, task.getFormatType());
            ps.setString(7, task.getTopLevelIDType());
            ps.setString(8, task.getReaderIDType());
            ps.setString(9, task.getCommand());
            ps.setString(10, task.getDescription());
            ps.executeUpdate();

            // get id of newly added task
            psselectAll.setString(1, task.getTaskName());
            rs = psselectAll.executeQuery();
            rs.next();
            int taskid = rs.getInt(1);

            // insert one row in taskdevice
            psInsertTaskDevice.setInt(1, taskid);
            psInsertTaskDevice.setInt(2, Integer.parseInt(deviceId));
            psInsertTaskDevice.setInt(3, Integer.parseInt(groupId));
            psInsertTaskDevice.executeUpdate();

            // insert rows in HTTPDestination
            for (int i = 0; i < httpList.size(); i++) {
                HttpDestinationPO t = httpList.get(i);
                psInsertTaskHttp.setInt(1, taskid);
                psInsertTaskHttp.setString(2, t.getIp());
                psInsertTaskHttp.setInt(3, t.getPort());
                psInsertTaskHttp.setString(4, t.getPath());
                psInsertTaskHttp.setString(5, t.getUsername());
                psInsertTaskHttp.setString(6, t.getPassword());
                psInsertTaskHttp.addBatch();
            }

            psInsertTaskHttp.executeBatch();

            // insert rows in FileDestination
            for (int i = 0; i < fileList.size(); i++) {
                FileDestinationPO t = fileList.get(i);
                psInsertTaskFile.setInt(1, taskid);
                psInsertTaskFile.setString(2, t.getFolder());
                psInsertTaskFile.addBatch();
            }

            psInsertTaskFile.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[TaskDAO] insertTask() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (psselectAll != null) {
                    psselectAll.close();
                }

                if (psInsertTaskHttp != null) {
                    psInsertTaskHttp.close();
                }

                if (psInsertTaskFile != null) {
                    psInsertTaskFile.close();
                }

                if (psInsertTaskDevice != null) {
                    psInsertTaskDevice.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    /**
     * Update a task.
     *
     * @param task
     * @param groupId
     * @param deviceId
     * @param httpList
     * @param fileList
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static void updateTask(TaskPO task, String groupId, String deviceId,
                                  List<HttpDestinationPO> httpList, List<FileDestinationPO> fileList)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement psInsertTaskDevice = null;
        PreparedStatement psDelTaskDevice = null;
        PreparedStatement psDeleteTaskHttp = null;
        PreparedStatement psInsertTaskHttp = null;
        PreparedStatement psDeleteTaskFile = null;
        PreparedStatement psInsertTaskFile = null;

        try {
            conn = DBConnPool.getConnection();

            // update row in table tasks
            ps = conn.prepareStatement("UPDATE tasks SET taskName=?, taskType=?, startup=?, triggerMode=?, "
                + "parameter=?, formatType=?, topLevelIDType=?, "
                + "readerIDType=?, command=?, description=? WHERE id=?");

            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskType());
            ps.setString(3, task.getStartUp());
            ps.setString(4, task.getTriggerMode());
            ps.setInt(5, task.getParameter());
            ps.setString(6, task.getFormatType());
            ps.setString(7, task.getTopLevelIDType());
            ps.setString(8, task.getReaderIDType());
            ps.setString(9, task.getCommand());
            ps.setString(10, task.getDescription());
            ps.setInt(11, task.getId());

            ps.executeUpdate();

            // delete rows in taskdevice
            psDelTaskDevice = conn.prepareStatement("DELETE FROM taskdevice WHERE taskid=?");
            psDelTaskDevice.setInt(1, task.getId());

            psDelTaskDevice.executeUpdate();

            // insert rows in taskdevice
            psInsertTaskDevice = conn.prepareStatement("INSERT INTO taskdevice  "
                + "(taskid,deviceid,groupid ) VALUES(?,?,?)");

            psInsertTaskDevice.setInt(1, task.getId());
            psInsertTaskDevice.setInt(2, Integer.parseInt(deviceId));
            psInsertTaskDevice.setInt(3, Integer.parseInt(groupId));
            psInsertTaskDevice.executeUpdate();

            // delete rows in HTTPDestination
            psDeleteTaskHttp = conn.prepareStatement("DELETE FROM httpdestination WHERE taskid=?");
            psDeleteTaskHttp.setInt(1, task.getId());

            psDeleteTaskHttp.executeUpdate();

            // insert rows in HTTPDestination
            psInsertTaskHttp = conn.prepareStatement("INSERT INTO httpdestination(taskid,ip,port,path,username,password) VALUES (?,?,?,?,?,?)");

            for (int i = 0; i < httpList.size(); i++) {
                HttpDestinationPO t = httpList.get(i);

                psInsertTaskHttp.setInt(1, task.getId());
                psInsertTaskHttp.setString(2, t.getIp());
                psInsertTaskHttp.setInt(3, t.getPort());
                psInsertTaskHttp.setString(4, t.getPath());
                psInsertTaskHttp.setString(5, t.getUsername());
                psInsertTaskHttp.setString(6, t.getPassword());
                psInsertTaskHttp.addBatch();
            }

            psInsertTaskHttp.executeBatch();

            // delete rows in FileDestination
            psDeleteTaskFile = conn.prepareStatement("DELETE FROM filedestination WHERE taskid=?");
            psDeleteTaskFile.setInt(1, task.getId());

            psDeleteTaskFile.executeUpdate();

            // insert rows in FileDestination
            psInsertTaskFile = conn.prepareStatement("INSERT  INTO filedestination(taskid,folder) VALUES (?,?)");

            for (int i = 0; i < fileList.size(); i++) {
                FileDestinationPO t = fileList.get(i);

                psInsertTaskFile.setInt(1, task.getId());
                psInsertTaskFile.setString(2, t.getFolder());
                psInsertTaskFile.addBatch();
            }

            psInsertTaskFile.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[TaskDAO] updateTask() failed: " + e.getMessage());
        } finally {
            try {
                if (psDelTaskDevice != null) {
                    psDelTaskDevice.close();
                }

                if (psInsertTaskDevice != null) {
                    psInsertTaskDevice.close();
                }

                if (psDeleteTaskHttp != null) {
                    psDeleteTaskHttp.close();
                }

                if (psInsertTaskHttp != null) {
                    psInsertTaskHttp.close();
                }

                if (psDeleteTaskFile != null) {
                    psDeleteTaskFile.close();
                }

                if (psInsertTaskFile != null) {
                    psInsertTaskFile.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    /**
     * Delete a task.
     *
     * @param id task id
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void deleteTasksById(int id) throws DBOperateException, NotConnectException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("DELETE FROM tasks WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[TaskDAO] deleteTasksById() failed: "
                + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    /**
     * See if a device is used by task(s).
     *
     * @param deviceid device id
     * @return true if the device is used by task(s)
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static boolean isDeviceUsedByTask(int deviceid) throws DBOperateException, NotConnectException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM taskdevice WHERE deviceid=?");
            pstmt.setInt(1, deviceid);
            rs = pstmt.executeQuery();
            rs.next();

            return (rs.getInt(1) > 0);
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] isDeviceUsedByTask() failed: "
                + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }


    /**
     * See if a group's device is used by task(s).
     *
     * @param deviceid
     * @param groupid
     * @return true if the group's device is used by task(s)
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static boolean isGroupAndDeviceUsedByTask(int deviceid, int groupid) throws DBOperateException, NotConnectException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM taskdevice WHERE deviceid=? AND groupid=?");
            pstmt.setInt(1, deviceid);
            pstmt.setInt(2, groupid);
            rs = pstmt.executeQuery();
            rs.next();

            return (rs.getInt(1) > 0);
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] isGroupAndDeviceUsedByTask() failed: "
                + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    /**
     * Check if the taskName has existed when adding or updating.
     *
     * @param taskName
     * @param id
     * @return false the taskName has existed.
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static boolean isTaskNameUnique(String taskName, int id)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            if (id == 0) {
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM tasks WHERE taskName=?");
                pstmt.setString(1, taskName);
            } else {
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM tasks WHERE taskName=? and id!=?");
                pstmt.setString(1, taskName);
                pstmt.setInt(2, id);
            }

            rs = pstmt.executeQuery();

            return !(rs.next() && rs.getInt(1) > 0);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[UserDAO] isUniqueUserName() failed: " + e.getMessage());
        } catch (NotConnectException e) {
            throw new NotConnectException("[UserDAO] isUniqueUserName() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (pstmt != null)
                    pstmt.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

            }
        }

    }

    /**
     * Check if task is assigned to the user.
     *
     * @param user   user name
     * @param taskId task id
     * @return true if task is assigned to the user
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isTaskAssignedToUser(String user, int taskId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM users, userdevice, taskdevice WHERE userdevice.userid=users.id AND taskdevice.deviceid = userdevice.deviceid AND taskdevice.taskid=? AND users.username=?");
            pstmt.setInt(1, taskId);
            pstmt.setString(2, user);

            rs = pstmt.executeQuery();

            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDAO] isTaskAssignedToUser() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (pstmt != null)
                    pstmt.close();

                if (conn != null)
                    conn.close();
            } catch (SQLException e1) {

            }
        }

    }

}
