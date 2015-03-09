//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/ObservationQueryDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/19 03:12:32 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.ObservationPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class to query Observation table
 *
 * @author shenxiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.38 $
 */
public class ObservationQueryDAO {

    /**
     * Return newly removed observations by deviceid and lastQueryTime.
     *
     * @param deviceId
     * @param lastQueryTime
     * @param nowQueryTime
     * @return List <ObservationPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<ObservationPO> selectRemoveObservationByDeviceId(String deviceId, long lastQueryTime, long nowQueryTime)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            String sql = "SELECT * FROM observation WHERE deviceid=? AND removeTime>=? AND removeTime<? ORDER BY removeTime";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deviceId);
            pstmt.setLong(2, lastQueryTime);
            pstmt.setLong(3, nowQueryTime);
            rs = pstmt.executeQuery();

            List<ObservationPO> obsevationList = new ArrayList<ObservationPO>();

            while (rs.next()) {
                ObservationPO observation = new ObservationPO();
                observation.setId(rs.getInt("id"));
                observation.setTagId(rs.getString("tagId"));
                observation.setDeviceId(rs.getString("deviceId"));
                observation.setAddTime(rs.getLong("detectTime"));
                observation.setRemoveTime(rs.getLong("removeTime"));
                obsevationList.add(observation);
            }

            return obsevationList;
        } catch (SQLException e) {
            throw new DBOperateException("[ObservationQueryDAO] selectRemoveObservationByDeviceId() failed: "
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
     * Return observation whose id is between startId and endId for the device.
     *
     * @param deviceId
     * @param startId
     * @param endId
     * @return List <ObservationPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<ObservationPO> getPackageByDeviceId(String deviceId, int startId, int endId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            String sql = "SELECT * FROM observation WHERE deviceid=? AND id>? AND id<=? ORDER BY id";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deviceId);
            pstmt.setInt(2, startId);
            pstmt.setInt(3, endId);
            rs = pstmt.executeQuery();

            List<ObservationPO> obsevationList = new ArrayList<ObservationPO>();

            while (rs.next()) {
                ObservationPO observation = new ObservationPO();
                observation.setTagId(rs.getString("tagId"));
                observation.setDeviceId(rs.getString("deviceId"));
                observation.setAddTime(rs.getLong("detectTime"));
                obsevationList.add(observation);
            }

            return obsevationList;
        } catch (SQLException e) {
            throw new DBOperateException("[ObservationQueryDAO] getPackageByDeviceId() failed: "
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
     * Get the count id for the device.
     *
     * @param deviceId
     * @return count id
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static int getCountIdForDevice(String deviceId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            // check if observation table contains events which remove time is null;
            // if found then return the min id of all the records
            String sql = "SELECT MIN(id) FROM observation WHERE deviceid=? AND removeTime IS NULL";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deviceId);
            rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) != 0) {
                // note return value minus 1 so that this observation will be included
                return (rs.getInt(1) - 1);
            }

            rs.close();
            rs = null;
            pstmt.close();
            pstmt = null;

            // if observation table does not contain any event which remove time is null;
            // return the max id of the record for the device
            sql = "SELECT MAX(id) FROM observation WHERE deviceid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deviceId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DBOperateException("[ObservationQueryDAO] getCountIdForDevice() failed: "
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
     * Check if currently tag is detected by the device.
     *
     * @param deviceId device id
     * @return true if now reader is detecting some tags
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isTagDetectedByDevice(String deviceId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            String sql = "SELECT COUNT(*) FROM observation WHERE deviceid=? AND removeTime IS NULL";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deviceId);
            rs = pstmt.executeQuery();

            rs.next();

            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new DBOperateException("[ObservationQueryDAO] getObservationsByDeviceId() failed: "
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
     * Select observations whose id > given id for the device.
     *
     * @param deviceId device id
     * @param id       starting id
     * @return List <ObservationPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<ObservationPO> getObservationsByDeviceId(String deviceId, int id)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            String sql = "SELECT * FROM observation WHERE deviceid=? AND id>? ORDER BY id";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, deviceId);
            pstmt.setInt(2, id);
            rs = pstmt.executeQuery();
            List<ObservationPO> obsevationList = new ArrayList<ObservationPO>();

            while (rs.next()) {
                ObservationPO observation = new ObservationPO();
                observation.setId(rs.getInt("id"));
                observation.setTagId(rs.getString("tagId"));
                observation.setDeviceId(rs.getString("deviceId"));
                observation.setAddTime(rs.getLong("detectTime"));
                obsevationList.add(observation);
            }

            return obsevationList;
        } catch (SQLException e) {
            throw new DBOperateException("[ObservationQueryDAO] getObservationsByDeviceId() failed: "
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

}
