// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/UsersDAO.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/05/08 08:35:18 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.jdbc.po.ListUserPO;
import com.ubipass.middleware.jdbc.po.UserPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage User, Role and userReaders table
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.28 $
 */
public class UsersDAO {
    /**
     * List all User.
     *
     * @return list of userPO
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<ListUserPO> listAllUser() throws NotConnectException,
        DBOperateException {

        List<ListUserPO> users = new ArrayList<ListUserPO>();
        Connection conn = null;
        PreparedStatement showAllUsers = null;
        PreparedStatement getUserRoleType = null;
        ResultSet usersResult = null;
        ResultSet roleResult = null;

        try {
            conn = DBConnPool.getConnection();
            showAllUsers = conn.prepareStatement("SELECT id, username, description FROM users ORDER BY username");
            getUserRoleType = conn.prepareStatement("SELECT roletype FROM roles WHERE username=?");

            usersResult = showAllUsers.executeQuery();

            while (usersResult.next()) {
                ListUserPO r = new ListUserPO();
                r.setId(usersResult.getInt(1));
                r.setUserName(usersResult.getString(2));
                r.setDescription(usersResult.getString(3));

                getUserRoleType.setString(1, r.getUserName());
                roleResult = getUserRoleType.executeQuery();

                while (roleResult.next()) {
                    r.getRoleType().add(roleResult.getString(1));
                }

                roleResult.close();
                roleResult = null;

                users.add(r);
            }

            return users;
        } catch (SQLException e) {
            throw new DBOperateException("[UserDAO] listAllUser() failed: "
                + e.getMessage());
        } finally {
            try {
                if (usersResult != null)
                    usersResult.close();

                if (roleResult != null)
                    roleResult.close();

                if (getUserRoleType != null)
                    getUserRoleType.close();

                if (showAllUsers != null)
                    showAllUsers.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

            }
        }
    }

    /**
     * Get a user by user id.
     *
     * @param id user id
     * @return UserPO
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static UserPO getUserByID(int id) throws NotConnectException,
        DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT users.username,roles.roletype,users.description"
                + " FROM users,roles WHERE users.id=? AND roles.username=users.username");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                UserPO user = new UserPO();
                user.setUserName(rs.getString(1));
                user.setRole(rs.getString(2));
                user.setDescription(rs.getString(3));
                return user;
            }

            return null;
        } catch (SQLException e) {
            throw new DBOperateException("[UserDAO] pstmt() failed: "
                + e.getMessage());
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
     * Get devices assigned to the user.
     *
     * @param id user id
     * @return list of DevicePo
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static List<DevicePo> getSelectDeviceByUserID(int id)
        throws DBOperateException, NotConnectException {

        List<DevicePo> result = new ArrayList<DevicePo>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT devices.id,devices.devicename FROM devices,userdevice,users "
                + "WHERE devices.id=userdevice.deviceid AND users.id=userdevice.userid AND users.id=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DevicePo dPO = new DevicePo();
                dPO.setId(rs.getInt(1));
                dPO.setDeviceName(rs.getString(2));
                result.add(dPO);
            }

            return result;
        } catch (SQLException e) {
            throw new DBOperateException("[UserDAO] getSelectDeviceByUserID() failed: "
                + e.getMessage());
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
     * Get devices not assigned to the user.
     *
     * @param id user id
     * @return list of DevicePo
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static List<DevicePo> getUnSelectDeviceByUserID(int id)
        throws DBOperateException, NotConnectException {
        List<DevicePo> result = new ArrayList<DevicePo>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT devices.id,devices.devicename FROM devices WHERE devices.id NOT IN ( "
                + "SELECT userdevice.deviceid FROM users,userdevice WHERE users.id=userdevice.userid AND users.id=?)");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                DevicePo dPO = new DevicePo();
                dPO.setId(rs.getInt(1));
                dPO.setDeviceName(rs.getString(2));
                result.add(dPO);
            }

            return result;
        } catch (SQLException e) {
            throw new DBOperateException("[UserDAO] getUnSelectDeviceByUserID() failed: "
                + e.getMessage());
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
     * Add/update a user.
     *
     * @param devices
     * @param id
     * @param desc
     * @param userName
     * @param roleType
     * @param password
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static void updateUserDevice(String[] devices, int id, String desc,
                                        String userName, String roleType, String password)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement getUserByUser = null;
        PreparedStatement updateUser = null;
        PreparedStatement updateUserPsw = null;
        PreparedStatement addUser = null;
        PreparedStatement updateUserRole = null;
        PreparedStatement addUserRole = null;
        PreparedStatement instertUserDevice = null;
        PreparedStatement deleteUserDevice = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            addUser = conn.prepareStatement("INSERT INTO users(userName,description,passwd) VALUES(?,?,?)");

            getUserByUser = conn.prepareStatement("SELECT id FROM users WHERE username=?");

            addUserRole = conn.prepareStatement("INSERT INTO roles(username,roletype) VALUES(?,?)");

            updateUser = conn.prepareStatement("UPDATE users SET description=? WHERE id=?");

            updateUserPsw = conn.prepareStatement("UPDATE users SET passwd=? WHERE id=?");

            deleteUserDevice = conn.prepareStatement("DELETE FROM userdevice WHERE userid=?");

            instertUserDevice = conn.prepareStatement("INSERT INTO userdevice(deviceid,userid) VALUES(?,?)");

            updateUserRole = conn.prepareStatement("UPDATE roles SET roletype=? WHERE username=?");

            if (id == 0) {
                // add a new user
                addUser.setString(1, userName);
                addUser.setString(2, desc);
                addUser.setString(3, password);
                addUser.executeUpdate();

                // get newly added user id
                getUserByUser.setString(1, userName);
                rs = getUserByUser.executeQuery();
                rs.next();
                id = rs.getInt(1);

                addUserRole.setString(1, userName);
                addUserRole.setString(2, roleType);
                addUserRole.executeUpdate();
            } else {
                // update a user
                updateUser.setString(1, desc);
                updateUser.setInt(2, id);
                updateUser.executeUpdate();

                if (!password.equals("")) {
                    updateUserPsw.setString(1, password);
                    updateUserPsw.setInt(2, id);
                    updateUserPsw.executeUpdate();
                }

                updateUserRole.setString(1, roleType);
                updateUserRole.setString(2, userName);
                updateUserRole.executeUpdate();

                deleteUserDevice.setInt(1, id);
                deleteUserDevice.executeUpdate();

            }

            // insert records in table userdevice
            if (devices != null && devices.length > 0) {
                for (String device : devices) {
                    instertUserDevice.setInt(1, Integer.parseInt(device));
                    instertUserDevice.setInt(2, id);
                    instertUserDevice.addBatch();
                }

                instertUserDevice.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[UserDAO] updateUserDevice() failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (getUserByUser != null)
                    getUserByUser.close();

                if (deleteUserDevice != null)
                    deleteUserDevice.close();

                if (updateUser != null)
                    updateUser.close();

                if (updateUserPsw != null)
                    updateUserPsw.close();

                if (addUser != null)
                    addUser.close();

                if (updateUserRole != null)
                    updateUserRole.close();

                if (addUserRole != null)
                    addUserRole.close();

                if (instertUserDevice != null)
                    instertUserDevice.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

            }
        }

    }

    /**
     * Delete a user.
     *
     * @param id user id
     * @throws Exception
     */
    public static void deleteUserById(int id)
        throws Exception {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();
            UserPO user = getUserByID(id);

            //administrator can not delete
            if (user.getUserName().trim().equals("admin")) {
                throw new Exception("Administrator cannot be deleted");
            }

            pstmt = conn.prepareStatement("DELETE FROM users WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[UserDAO] deleteUserById() failed: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();

                if (conn != null)
                    conn.close();
            } catch (SQLException e) {

            }
        }

    }

    /**
     * Check if the userName has existed.
     *
     * @param userName
     * @return false if user name has existed
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isUniqueUserName(String userName)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE userName=?");
            pstmt.setString(1, userName);
            rs = pstmt.executeQuery();

            return !(rs.next() && rs.getInt(1) > 0);
        } catch (SQLException e) {
            throw new DBOperateException("[UserDAO] isUniqueUserName() failed: "
                + e.getMessage());
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
     * Get user's role.
     *
     * @param userName
     * @param password
     * @return role of the user
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static String getUserRole(String userName, String password)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT roles.roleType FROM users, roles WHERE users.userName=roles.userName AND users.userName=? AND users.passwd=?");
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next())
                return rs.getString(1);
            else
                return null;
        } catch (SQLException e) {
            throw new DBOperateException("[UserDAO] getUserRole() failed: "
                + e.getMessage());
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

}