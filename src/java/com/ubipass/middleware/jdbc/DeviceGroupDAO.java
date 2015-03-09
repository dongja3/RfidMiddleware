//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/DeviceGroupDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 08:35:18 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage DeviceGroups table
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.30 $
 */
public class DeviceGroupDAO {
    /**
     * List all device groups from table Groups
     *
     * @return List
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<DeviceGroupPO> listReaderGroup()
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // open jdbc connction pool
            conn = DBConnPool.getConnection();

            // create a prepared statement for select
            pstmt = conn.prepareStatement("SELECT groups.id,groups.groupname,groups.description FROM groups ORDER BY groups.groupname");
            List<DeviceGroupPO> readerGroupList = new ArrayList<DeviceGroupPO>();
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DeviceGroupPO deviceGroupPO = new DeviceGroupPO();
                deviceGroupPO.setReaderGroupId(rs.getInt(1));
                deviceGroupPO.setReaderGroupName(rs.getString(2));
                deviceGroupPO.setDescription(rs.getString(3));
                readerGroupList.add(deviceGroupPO);
            }

            return readerGroupList;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceGroupDAO] listReaderGroup() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();

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
     * Get device groups from table Groups
     *
     * @return List of device group s
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<DeviceGroupPO> getAllDeviceGroups()
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // open jdbc connection pool
            conn = DBConnPool.getConnection();

            // create a prepared statement for select
            pstmt = conn.prepareStatement("SELECT id, groupname, description FROM groups");
            List<DeviceGroupPO> readerGroupList = new ArrayList<DeviceGroupPO>();
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DeviceGroupPO deviceGroupPO = new DeviceGroupPO();
                deviceGroupPO.setReaderGroupId(rs.getInt(1));
                deviceGroupPO.setReaderGroupName(rs.getString(2));
                deviceGroupPO.setDescription(rs.getString(3));
                readerGroupList.add(deviceGroupPO);
            }

            return readerGroupList;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceGroupDAO] getAllDeviceGroups() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();

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
     * Delete a group from table Groups
     *
     * @param id group id
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void deleteGroup(int id)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();

            // create a prepared statement for delete table groups
            pstmt = conn.prepareStatement("DELETE FROM groups WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {

            }

            throw new DBOperateException("[DeviceGroupDAO] deleteGroup() failed: " + e.getMessage());
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
     * Insert a new group to table Groups
     *
     * @param readerGrouppo
     * @param readerId      list of devices
     * @return true if operation succeeds
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static boolean insertGroup(DeviceGroupPO readerGrouppo, List<DevicePo> readerId)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement addSqlGroup = null;
        PreparedStatement insertSqlReaderGroup = null;
        PreparedStatement sqlGroupId = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            if (readerGrouppo == null
                || readerGrouppo.getReaderGroupName() == null
                || readerGrouppo.getReaderGroupName().equals("")) {
                return false;
            }

            // create a prepared statement for insert table groups
            addSqlGroup = conn.prepareStatement("INSERT INTO groups(groupname,description) VALUES(?,?)");

            insertSqlReaderGroup = conn.prepareStatement("INSERT INTO devicegroups(deviceid,groupid) VALUES(?,?)");

            addSqlGroup.setString(1, readerGrouppo.getReaderGroupName());
            addSqlGroup.setString(2, readerGrouppo.getDescription());
            addSqlGroup.executeUpdate();

            // get newly added group id by group name
            sqlGroupId = conn.prepareStatement("SELECT id FROM groups WHERE groupname=?");
            sqlGroupId.setString(1, readerGrouppo.getReaderGroupName());
            rs = sqlGroupId.executeQuery();
            rs.next();

            int groupId = rs.getInt(1);

            if (readerId != null && readerId.size() > 0) {
                for (int i = 0; i < readerId.size(); i++) {
                    DevicePo r = readerId.get(i);
                    insertSqlReaderGroup.setInt(1, r.getId());
                    insertSqlReaderGroup.setInt(2, groupId);
                    insertSqlReaderGroup.addBatch();
                }

                insertSqlReaderGroup.executeBatch();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {

            }

            throw new DBOperateException("[DeviceGroupDAO] insertGroup() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (sqlGroupId != null) {
                    sqlGroupId.close();
                }

                if (addSqlGroup != null) {
                    addSqlGroup.close();
                }

                if (insertSqlReaderGroup != null) {
                    insertSqlReaderGroup.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

    }

    /**
     * Select a group by id.
     *
     * @param groupId group id
     * @return device group PO
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static DeviceGroupPO selectGroupForId(int groupId)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            // create a prepared statement
            pstmt = conn.prepareStatement("SELECT id, groupname, description FROM groups WHERE id=?");
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();

            if (!rs.next())
                return null;

            DeviceGroupPO dg = new DeviceGroupPO();
            dg.setReaderGroupId(rs.getInt(1));
            dg.setReaderGroupName(rs.getString(2));
            dg.setDescription(rs.getString(3));

            return dg;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceGroupDAO] selectGroupForId() failed: " + e.getMessage());
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
     * Return all devices that are not assigned to a group.
     *
     * @param groupId group id
     * @return list of devices
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<DevicePo> unSelectedReadersForGroup(int groupId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<DevicePo> readers = new ArrayList<DevicePo>();

        try {
            conn = DBConnPool.getConnection();

            // create a prepared statement for showing unselected readers
            pstmt = conn.prepareStatement("SELECT devices.id,devices.devicename FROM devices "
                + "WHERE devices.id NOT IN (SELECT devicegroups.deviceid FROM groups,devicegroups "
                + "WHERE groups.id=devicegroups.groupid AND groups.id=?)");
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DevicePo devicePo = new DevicePo();
                devicePo.setId(rs.getInt(1));
                devicePo.setDeviceName(rs.getString(2));
                readers.add(devicePo);
            }

            return readers;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceGroupDAO] unSelectedReadersForGroup() failed:" + e.getMessage());
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
     * Return selected devices for a group.
     *
     * @param groupId group id
     * @return List of devices
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static List<DevicePo> selectedReadersForGroup(int groupId)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement selectedSqlReaders = null;
        List<DevicePo> readers = new ArrayList<DevicePo>();

        try {
            conn = DBConnPool.getConnection();

            // create a prepared statement for showing selected readers
            selectedSqlReaders = conn.prepareStatement("SELECT devices.id,devices.devicename FROM groups,devicegroups,devices "
                + "WHERE groups.id=devicegroups.groupid AND devices.id=devicegroups.deviceid AND groups.id=?");

            selectedSqlReaders.setInt(1, groupId);
            rs = selectedSqlReaders.executeQuery();

            while (rs.next()) {
                DevicePo devicePo = new DevicePo();
                devicePo.setId(rs.getInt(1));
                devicePo.setDeviceName(rs.getString(2));
                readers.add(devicePo);
            }

            return readers;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceGroupDAO] selectedReadersForGroup() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (selectedSqlReaders != null) {
                    selectedSqlReaders.close();
                }

                if (selectedSqlReaders != null) {
                    selectedSqlReaders.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

    }

    /**
     * Return all devices.
     *
     * @return List of device
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<DevicePo> getAllDevices()
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<DevicePo> readers = new ArrayList<DevicePo>();

        try {
            conn = DBConnPool.getConnection();

            // create a prepared statement for showing all devices
            pstmt = conn.prepareStatement("SELECT id, devicename FROM devices");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DevicePo devicePo = new DevicePo();
                devicePo.setId(rs.getInt(1));
                devicePo.setDeviceName(rs.getString(2));
                readers.add(devicePo);
            }

            return readers;
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceGroupDAO] getAllDevices() failed: " + e.getMessage());
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
     * Update a group.
     *
     * @param groupPO
     * @param devices list of devices in the group
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void updateDeviceGroup(DeviceGroupPO groupPO, List<DevicePo> devices)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement insertSqlReaderGroup = null;
        PreparedStatement updateSqlReaderGroups = null;
        PreparedStatement deleteSqlReaderGroup = null;

        try {
            conn = DBConnPool.getConnection();

            // create a prepared statement for updating group
            updateSqlReaderGroups = conn.prepareStatement("UPDATE groups set groupname=? ,description=? WHERE id=?");
            updateSqlReaderGroups.setString(1, groupPO.getReaderGroupName());
            updateSqlReaderGroups.setString(2, groupPO.getDescription());
            updateSqlReaderGroups.setInt(3, groupPO.getReaderGroupId());
            updateSqlReaderGroups.executeUpdate();

            deleteSqlReaderGroup = conn.prepareStatement("DELETE FROM devicegroups where groupid=?");
            deleteSqlReaderGroup.setInt(1, groupPO.getReaderGroupId());
            deleteSqlReaderGroup.executeUpdate();

            insertSqlReaderGroup = conn.prepareStatement("INSERT INTO devicegroups(deviceid,groupid) VALUES(?,?)");

            if (devices.size() != 0) {
                for (int i = 0; i < devices.size(); i++) {
                    DevicePo r = devices.get(i);
                    insertSqlReaderGroup.setInt(1, r.getId());
                    insertSqlReaderGroup.setInt(2, groupPO.getReaderGroupId());
                    insertSqlReaderGroup.addBatch();
                }

                insertSqlReaderGroup.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[DeviceGroupDAO] updateDeviceGroup() failed: " + e.getMessage());
        } finally {
            try {
                if (updateSqlReaderGroups != null) {
                    updateSqlReaderGroups.close();
                }

                if (deleteSqlReaderGroup != null) {
                    deleteSqlReaderGroup.close();
                }
                if (insertSqlReaderGroup != null) {
                    insertSqlReaderGroup.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

    }

    /**
     * Delete a device.
     *
     * @param id device id
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void deleteDeviceById(int id)
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("DELETE FROM devices WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[DeviceGroupDAO] deleteDeviceById() failed: " + e.getMessage());
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
     * Check if groupName has existed.
     * <p/>
     * id =0 for adding group to check
     * id !=0 for updating group to check
     *
     * @param groupName
     * @param id
     * @return false if the group name has existed
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isUniqueGroupName(String groupName, int id) throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            if (id != 0) {
                // update a row
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM groups WHERE groupName=? AND id<>?");
                pstmt.setInt(2, id);
            } else {
                // insert a row
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM groups WHERE groupName=?");
            }

            pstmt.setString(1, groupName);
            rs = pstmt.executeQuery();

            return !(rs.next() && rs.getInt(1) > 0);
        } catch (SQLException e) {
            throw new DBOperateException("[DeviceGroupDAO] isUniqueGroupName() failed: " + e.getMessage());
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