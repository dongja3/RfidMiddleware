//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/DeviceDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 08:35:18 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class to manage devices table
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.40 $
 */
public class DeviceDAO {
    /**
     * Return DevicePo with specified row id in table devices.
     *
     * @param id device id
     * @return DevicePo
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static DevicePo selectDeviceById(int id)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            pstmt = conn.prepareStatement("SELECT deviceName,deviceType,connectType,"
                + "deviceID,userName,passwd,connectionName,port,persistTime,class,"
                + "startUp,description,command FROM devices WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            DevicePo devicePo = new DevicePo();

            devicePo.setDeviceName(rs.getString(1));
            devicePo.setDeviceType(rs.getString(2));
            devicePo.setConnectionType(rs.getString(3));
            devicePo.setDeviceID(rs.getString(4));
            devicePo.setUserName(rs.getString(5));
            devicePo.setPasswd(rs.getString(6));
            devicePo.setConnectionName(rs.getString(7));
            devicePo.setPort(rs.getInt(8));
            devicePo.setPersistTime(rs.getInt(9));
            devicePo.setClassName(rs.getString(10));
            devicePo.setStartup(rs.getString(11));
            devicePo.setDescription(rs.getString(12));
            devicePo.setCommand(rs.getString(13));
            devicePo.setId(id);

            return devicePo;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] selectDeviceById() failed: "
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
     * Update a row in table devices.
     *
     * @param devicePo
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static void updateDeviceById(DevicePo devicePo)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("UPDATE devices SET deviceName=?,deviceType=?,connectType=?,"
                + "deviceID=?,userName=?,passwd=?,connectionName=?,"
                + "port=?,persistTime=?,class=?,startUp=?,description=?,command=? WHERE id=?");

            pstmt.setString(1, devicePo.getDeviceName().trim());
            pstmt.setString(2, devicePo.getDeviceType().trim());
            pstmt.setString(3, devicePo.getConnectionType().trim());
            pstmt.setString(4, devicePo.getDeviceID().trim());
            pstmt.setString(5, devicePo.getUserName().trim());
            pstmt.setString(6, devicePo.getPasswd().trim());
            pstmt.setString(7, devicePo.getConnectionName().trim());
            pstmt.setInt(8, devicePo.getPort());
            pstmt.setInt(9, devicePo.getPersistTime());
            pstmt.setString(10, devicePo.getClassName().trim());
            pstmt.setString(11, devicePo.getStartup().trim());
            pstmt.setString(12, devicePo.getDescription().trim());
            pstmt.setString(13, devicePo.getCommand().trim());
            pstmt.setInt(14, devicePo.getId());

            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[DeviceDAO] updateDeviceById() failed: " + e.getMessage());
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
     * Get all devices from table devices.
     *
     * @return List of DevicePo
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<DevicePo> getAllDevices()
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT connectType,deviceType,deviceName,"
                + "deviceID,userName,passwd,connectionName,port,persistTime,class,startup,description,id FROM devices ORDER BY deviceName");

            ArrayList<DevicePo> deviceList = new ArrayList<DevicePo>();

            while (rs.next()) {
                // create a DevicePo;
                DevicePo device = new DevicePo();

                // set all Device's parameters
                device.setConnectionType(rs.getString("connectType"));
                device.setDeviceType(rs.getString("deviceType"));
                device.setDeviceName(rs.getString("deviceName"));
                device.setDeviceID(rs.getString("deviceID"));
                device.setUserName(rs.getString("userName"));
                device.setPasswd(rs.getString("passwd"));
                device.setConnectionName(rs.getString("connectionName"));
                device.setPort(rs.getInt("port"));
                device.setPersistTime(rs.getInt("persistTime"));
                device.setClassName(rs.getString("class"));
                device.setStartup(rs.getString("startup"));
                device.setDescription(rs.getString("description"));
                device.setId(rs.getInt("id"));

                // add Device to ArrayList
                deviceList.add(device);
            }

            return deviceList;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] getAllDevices() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

    }

    /**
     * Get all devices assigned to the user.
     *
     * @param user user name
     * @return List of DevicePo
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<DevicePo> getAllDevices(String user)
        throws NotConnectException, DBOperateException {

        if (user == null || user.equals(""))
            return null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT devices.connectType,devices.deviceType,devices.deviceName,devices.deviceID,devices.userName,devices.passwd,devices.connectionName,devices.port,devices.persistTime,devices.class,devices.startup,devices.description,devices.id "
                + "FROM devices,users,userdevice WHERE users.id = userdevice.userid AND userdevice.deviceid=devices.id AND users.username=? ORDER BY devices.deviceName");
            pstmt.setString(1, user);
            rs = pstmt.executeQuery();
            ArrayList<DevicePo> deviceList = new ArrayList<DevicePo>();

            while (rs.next()) {
                // create a DevicePo;
                DevicePo device = new DevicePo();

                // set all Device's parameters
                device.setConnectionType(rs.getString("connectType"));
                device.setDeviceType(rs.getString("deviceType"));
                device.setDeviceName(rs.getString("deviceName"));
                device.setDeviceID(rs.getString("deviceID"));
                device.setUserName(rs.getString("userName"));
                device.setPasswd(rs.getString("passwd"));
                device.setConnectionName(rs.getString("connectionName"));
                device.setPort(rs.getInt("port"));
                device.setPersistTime(rs.getInt("persistTime"));
                device.setClassName(rs.getString("class"));
                device.setStartup(rs.getString("startup"));
                device.setDescription(rs.getString("description"));
                device.setId(rs.getInt("id"));

                // add Device to ArrayList
                deviceList.add(device);
            }

            return deviceList;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] getAllDevices() failed: " + e.getMessage());
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
     * Add a device.
     *
     * @param device
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static void insertDevice(DevicePo device)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("INSERT INTO devices(connectType,deviceType,deviceName,deviceID,userName,"
                + "passwd,connectionName,port,persistTime,class,startup,command,description) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pstmt.setString(1, device.getConnectionType().trim());
            pstmt.setString(2, device.getDeviceType().trim());
            pstmt.setString(3, device.getDeviceName().trim());
            pstmt.setString(4, device.getDeviceID().trim());
            pstmt.setString(5, device.getUserName().trim());
            pstmt.setString(6, device.getPasswd().trim());
            pstmt.setString(7, device.getConnectionName().trim());
            pstmt.setInt(8, device.getPort());
            pstmt.setInt(9, device.getPersistTime());
            pstmt.setString(10, device.getClassName().trim());
            pstmt.setString(11, device.getStartup().trim());
            pstmt.setString(12, device.getCommand().trim());
            pstmt.setString(13, device.getDescription().trim());

            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[DeviceDAO] insertDevice() failed: " + e.getMessage());
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
     * Get device command.
     *
     * @param deviceId device id
     * @return device command
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static String getDeviceCommand(String deviceId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            pstmt = conn.prepareStatement("SELECT command FROM devices WHERE deviceid=?");
            pstmt.setString(1, deviceId);

            rs = pstmt.executeQuery();

            if (rs.next())
                return rs.getString(1);
            else
                return null;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] getDeviceCommand() failed: " + e.getMessage());
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

    /**
     * Check if device name has existed.
     * <p/>
     * id =0 for adding device to check
     * id !=0 for updating device to check
     *
     * @param deviceName
     * @param id
     * @return false if the device name has existed
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isUniqueDeviceName(String deviceName, int id) throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet r = null;

        try {
            conn = DBConnPool.getConnection();

            if (id != 0) {
                // update a row
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM devices WHERE deviceName=? AND id<>?");
                pstmt.setInt(2, id);
            } else {
                // insert a row
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM devices WHERE deviceName=?");
            }

            pstmt.setString(1, deviceName);
            r = pstmt.executeQuery();

            return !(r.next() && r.getInt(1) > 0);
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] isUniqueDeviceName() failed: " + e.getMessage());
        } finally {
            try {
                if (r != null)
                    r.close();

                if (pstmt != null)
                    pstmt.close();

                if (conn != null)
                    conn.close();
            } catch (SQLException e1) {

            }
        }
    }

    /**
     * Check if deviceID has existed.
     * <p/>
     * id =0 for adding device to check
     * id !=0 for updating device to check
     *
     * @param deviceID
     * @param id
     * @return false if the deviceID has exist.
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isUniqueDeviceID(String deviceID, int id) throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet r = null;

        try {
            conn = DBConnPool.getConnection();

            if (id != 0) {
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM devices WHERE deviceid=? AND id<>?");
                pstmt.setInt(2, id);
            } else {
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM devices WHERE deviceid=?");
            }

            pstmt.setString(1, deviceID);
            r = pstmt.executeQuery();

            return !(r.next() && r.getInt(1) > 0);
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] isUniqueDeviceID() failed: " + e.getMessage());
        } finally {
            try {
                if (r != null)
                    r.close();

                if (pstmt != null)
                    pstmt.close();

                if (conn != null)
                    conn.close();
            } catch (SQLException e1) {

            }
        }
    }

    /**
     * Check if device is assigned to the user.
     *
     * @param user     user name
     * @param deviceId device id
     * @return true if device is assigned to the user
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isDeviceAssignedToUser(String user, int deviceId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM users, userdevice WHERE userdevice.userid=users.id AND userdevice.deviceid=? AND users.username=?");
            pstmt.setInt(1, deviceId);
            pstmt.setString(2, user);

            rs = pstmt.executeQuery();

            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] isDeviceAssignedToUser() failed: " + e.getMessage());
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

    /**
     * Check if device is assigned to the user.
     *
     * @param user     user name
     * @param deviceId device id
     * @return true if device is assigned to the user
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isDeviceAssignedToUser(String user, String deviceId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            pstmt = conn.prepareStatement("SELECT id FROM devices WHERE deviceID =?");
            pstmt.setString(1, deviceId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return isDeviceAssignedToUser(user, rs.getInt("id"));
            } else
                return false;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceDAO] isDeviceAssignedToUser() failed: " + e.getMessage());
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
