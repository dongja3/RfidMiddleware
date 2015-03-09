/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/PluginsDAO.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/26 07:25:21 $
 * 
 */

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.PluginsPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PluginsDAO class, manage Plugin table.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.15 $
 */
public class PluginsDAO {

    /**
     * Return list of Plugin PO.
     *
     * @return List <PluginsPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static List<PluginsPO> getAllPlugins()
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        List<PluginsPO> pluginList = new ArrayList<PluginsPO>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Open jdbc connection pool
            conn = DBConnPool.getConnection();
            pstmt = conn
                .prepareStatement("SELECT id, pluginname, class FROM plugins");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PluginsPO plugin = new PluginsPO();
                plugin.setId(rs.getInt(1));
                plugin.setPluginName(rs.getString(2));
                plugin.setPluginClass(rs.getString(3));
                pluginList.add(plugin);
            }

            return pluginList;
        } catch (SQLException e) {
            throw new DBOperateException("[PluginsDAO] getAllPlugins() failed: " + e.getMessage());
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