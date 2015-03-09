//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/HttpDestinationDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:25:21 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.HttpDestinationPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data access class to manage http destination table
 *
 * @author shenxiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.9 $
 */
public class HttpDestinationDAO {

    /**
     * select httpdestination via taskid
     *
     * @param taskId
     * @return List<HttpDestinationPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static ArrayList<HttpDestinationPO> selectHttpDestinationByTaskId(int taskId) throws NotConnectException, DBOperateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();
            String sql = "SELECT * FROM httpDestination WHERE taskid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, taskId);
            rs = pstmt.executeQuery();

            ArrayList<HttpDestinationPO> httpDestinationList = new ArrayList<HttpDestinationPO>();

            while (rs.next()) {
                HttpDestinationPO httpDestination = new HttpDestinationPO();
                httpDestination.setId(rs.getInt("id"));
                httpDestination.setTaskId(rs.getInt("taskId"));
                httpDestination.setIp(rs.getString("ip"));
                httpDestination.setPort(rs.getInt("port"));
                httpDestination.setPath(rs.getString("path"));
                httpDestination.setUsername(rs.getString("username"));
                httpDestination.setPassword(rs.getString("password"));

                httpDestinationList.add(httpDestination);
            }

            return httpDestinationList;
        } catch (SQLException e) {
            throw new DBOperateException("[HttpDestinationDAO] selectHttpDestinationByTaskId() failed: " + e.getMessage());
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

