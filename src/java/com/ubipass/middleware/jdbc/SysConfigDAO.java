//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/SysConfigDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:25:21 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.SysConfigPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data access class to access sysconfig table.
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision $
 */
public class SysConfigDAO {

    /**
     * Get the Size of EventQueue
     *
     * @return queue size
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static int getQueueSize()
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // set default size 1000
        int queueSize = 1000;

        try {
            conn = DBConnPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT queueSize FROM sysconfig");

            if (rs.next()) {
                queueSize = rs.getInt(1);
            }

            return queueSize;
        } catch (SQLException e) {
            throw new DBOperateException("[SysConfigDAO] getQueueSize() failed: " + e.getMessage());
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
            } catch (Exception e) {

            }
        }
    }

    /**
     * Find the systemName.
     *
     * @return system name
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static String getSystemName()
        throws NotConnectException, DBOperateException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String systemName = null;

        try {
            conn = DBConnPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT systemName FROM SysConfig");

            if (rs.next()) {
                systemName = rs.getString(1);
            }

            return systemName;
        } catch (SQLException e) {
            throw new DBOperateException("[SysConfigDAO] getSystemName() failed:" + e.getMessage());
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
            } catch (Exception e) {

            }
        }
    }


    /**
     * Update the parameter of table Sysconfig
     *
     * @param sysConfigPO
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void configSys(SysConfigPO sysConfigPO)
        throws DBOperateException, NotConnectException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();

            pstmt = conn.prepareStatement("UPDATE sysconfig SET systemname=?,queuesize=?,soapstartup=?,messagestartup=?");
            pstmt.setString(1, sysConfigPO.getSystemName().trim());
            int queuesize = 1000;

            if (queuesize < sysConfigPO.getQueueSize()) {
                queuesize = sysConfigPO.getQueueSize();
            }

            pstmt.setInt(2, queuesize);
            pstmt.setString(3, sysConfigPO.getSoapStartup().trim());
            pstmt.setString(4, sysConfigPO.getMessageStartup().trim());
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[SysConfigDAO] configSys() failed: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {

            }
        }

    }

    /**
     * Get the parameters of table Sysconfig.
     *
     * @return SysConfigPO
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static SysConfigPO getSysConfig()
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            pstmt = conn.prepareStatement("SELECT systemname,queuesize,messagestartup,soapstartup FROM sysconfig");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                SysConfigPO s = new SysConfigPO();
                s.setSystemName(rs.getString(1));
                s.setQueueSize(rs.getInt(2));
                s.setMessageStartup(rs.getString(3));
                s.setSoapStartup(rs.getString(4));
                return s;
            } else {
                throw new DBOperateException("[SysConfigDAO] getSysConfig() failed: no data in table sysconfig");
            }
        } catch (SQLException e) {
            throw new DBOperateException("[SysConfigDAO] getSysConfig() failed: " + e.getMessage());
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

}
