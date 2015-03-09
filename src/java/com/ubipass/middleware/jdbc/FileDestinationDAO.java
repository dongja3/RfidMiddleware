//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/FileDestinationDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 07:25:21 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.FileDestinationPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data access class to manage file destination table
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.16 $
 */
public class FileDestinationDAO {

    /**
     * Get File Destination by Task Id
     *
     * @param taskId
     * @return List <FileDestinationPO>
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static ArrayList<FileDestinationPO> getFileDestinationByTaskId(int taskId)
        throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<FileDestinationPO> listPo = new ArrayList<FileDestinationPO>();

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn
                .prepareStatement("SELECT filedestination.id, filedestination.folder, tasks.taskName FROM filedestination, tasks WHERE filedestination.taskid=tasks.id AND tasks.id =?");
            pstmt.setInt(1, taskId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                FileDestinationPO fileDestPo = new FileDestinationPO();
                fileDestPo.setId(rs.getInt(1));
                fileDestPo.setTaskId(taskId);
                fileDestPo.setFolder(rs.getString(2));
                fileDestPo.setTaskName(rs.getString(3));
                listPo.add(fileDestPo);
            }

            return listPo;
        } catch (SQLException e) {
            throw new DBOperateException("[FileDestinationDAO[ getFileDestinationByTaskId() failed:"
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
            } catch (Exception e) {

            }
        }

    }

}
