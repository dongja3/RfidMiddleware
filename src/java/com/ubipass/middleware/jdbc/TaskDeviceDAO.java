//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/TaskDeviceDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:25:21 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.TaskDevicePO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * TaskDeviceDAO
 *
 * @author shenxiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.17 $
 */
public class TaskDeviceDAO {

    /**
     * Return TaskDevicePO by taskId from table TaskDevice,DeviceGroup,Groups.
     *
     * @param taskId
     * @return List <TaskDevice>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static ArrayList<TaskDevicePO> selectTaskDeviceByTaskId(int taskId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            String sql = "SELECT groups.groupName AS groupName, groups.id AS groupId,"
                + " devices.deviceId AS deviceId, devices.deviceName AS deviceName, devices.id AS did, devices.command AS command "
                + " FROM groups,taskDevice,devices WHERE taskDevice.taskId=?"
                + " AND taskDevice.groupId=groups.id AND taskDevice.deviceId=devices.id";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, taskId);
            rs = pstmt.executeQuery();

            ArrayList<TaskDevicePO> taskDeviceList = new ArrayList<TaskDevicePO>();

            while (rs.next()) {
                TaskDevicePO taskDevicePO = new TaskDevicePO();

                taskDevicePO.setGroupName(rs.getString("groupName"));
                taskDevicePO.setGroupID(rs.getInt("groupId"));
                taskDevicePO.setDeviceID(rs.getString("deviceId"));
                taskDevicePO.setDeviceName(rs.getString("deviceName"));
                taskDevicePO.setDid(rs.getInt("did"));
                taskDevicePO.setTaskID(taskId);
                taskDevicePO.setCommand(rs.getString("command"));
                taskDeviceList.add(taskDevicePO);
            }

            return taskDeviceList;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDeviceDAO] selectTaskDeviceByTaskId() failed: "
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
     * Return deviceID with the specified taskid.
     *
     * @param taskId
     * @return String deviceId
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static String getDeviceIDFromTask(int taskId) throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT devices.deviceid FROM devices,taskdevice WHERE devices.id=taskdevice.deviceid AND taskdevice.taskid=?");
            pstmt.setInt(1, taskId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }

            return null;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDeviceDAO] getDeviceIDFromTask() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (pstmt != null)
                    pstmt.close();

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

    }

    /**
     * Return deviceName with the specified taskid.
     *
     * @param taskId
     * @return String deviceName
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static String getDeviceNameFromTask(int taskId) throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT devices.devicename FROM devices,taskdevice WHERE devices.id=taskdevice.deviceid AND taskdevice.taskid=?");
            pstmt.setInt(1, taskId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }

            return null;
        } catch (SQLException e) {
            throw new DBOperateException("[TaskDeviceDAO] getDeviceNameFromTask() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (pstmt != null)
                    pstmt.close();

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

    }

}
