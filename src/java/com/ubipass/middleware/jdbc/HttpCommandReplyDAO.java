//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/HttpCommandReplyDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 08:35:18 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.po.HttpCommandReplyPO;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * HttpCommandReplyDAO class
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.23 $
 */
public class HttpCommandReplyDAO {
    /**
     * Get all httpCommandReply records.
     *
     * @return list of HttpCommandReplyPO
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static ArrayList<HttpCommandReplyPO> getHttpList()
        throws DBOperateException, NotConnectException {

        Connection conn = null;
        ResultSet rs = null;
        ArrayList<HttpCommandReplyPO> httpList = new ArrayList<HttpCommandReplyPO>();
        PreparedStatement pstmt = null;

        try {
            conn = DBConnPool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM httpcommandreply");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // create a HttpCommandReplyPO reference;
                HttpCommandReplyPO httpPo = new HttpCommandReplyPO();

                httpPo.setId(rs.getInt("id"));
                httpPo.setIp(rs.getString("ip"));
                httpPo.setPort(rs.getInt("port"));
                httpPo.setPath(rs.getString("path"));
                httpPo.setUserName(rs.getString("username"));
                httpPo.setPassword(rs.getString("password"));

                // add httpPo to ArrayList
                httpList.add(httpPo);
            }
        } catch (SQLException e) {
            throw new DBOperateException("[HttpCommandReplyDAO] getHttpList() failed:"
                + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null)
                    pstmt.close();

                if (conn != null)
                    conn.close();
            } catch (SQLException e1) {

            }
        }

        return httpList;

    }

    /**
     * Add a record to table HttpCommandReply.
     *
     * @param hcrp
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void addHttpCommandReply(HttpCommandReplyPO hcrp)
        throws DBOperateException, NotConnectException {

        Connection conn = DBConnPool.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO httpcommandreply(ip,port,path,username,password)VALUES(?,?,?,?,?)");
            pstmt.setString(1, hcrp.getIp().trim());
            pstmt.setInt(2, hcrp.getPort());
            pstmt.setString(3, hcrp.getPath().trim());
            pstmt.setString(4, hcrp.getUserName().trim());
            pstmt.setString(5, hcrp.getPassword().trim());
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[HttpCommandReplyDAO] addHttpCommandReply() failed:"
                + e.getMessage());

        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e1) {

            }
        }

    }


    /**
     * Update Http command reply.
     *
     * @param hcrp
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void updateHttpCommandReply(HttpCommandReplyPO hcrp)
        throws DBOperateException, NotConnectException {

        Connection conn = DBConnPool.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("UPDATE httpcommandreply SET ip=?,port=?,path=?,username=?,password=? WHERE id=?");
            pstmt.setString(1, hcrp.getIp().trim());
            pstmt.setInt(2, hcrp.getPort());
            pstmt.setString(3, hcrp.getPath().trim());
            pstmt.setString(4, hcrp.getUserName().trim());
            pstmt.setString(5, hcrp.getPassword().trim());
            pstmt.setInt(6, hcrp.getId());
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[HttpCommandReplyDAO] updateHttpCommandReply() failed:"
                + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e1) {

            }
        }

    }

    /**
     * Delete record in table HttpCommandReply by id.
     *
     * @param id
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static void delteHttpCommandReply(int id) throws DBOperateException,
        NotConnectException {

        Connection conn = DBConnPool.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("DELETE FROM httpcommandreply WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {

            }

            throw new DBOperateException("[HttpCommandReplyDAO] deleteHttpCommandReply() failed:"
                + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e1) {

            }
        }

    }

    /**
     * Select a record from table HttpCommandReply by id.
     *
     * @param id
     * @return HttpCommandReplyPO
     * @throws DBOperateException
     * @throws NotConnectException
     */
    public static HttpCommandReplyPO selectHttpCommandReplyById(int id)
        throws DBOperateException, NotConnectException {

        Connection conn = DBConnPool.getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("SELECT ip,port,path,username,password FROM httpcommandreply WHERE id=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                HttpCommandReplyPO p = new HttpCommandReplyPO();
                p.setId(id);
                p.setIp(rs.getString(1));
                p.setPort(rs.getInt(2));
                p.setPath(rs.getString(3));
                p.setUserName(rs.getString(4));
                p.setPassword(rs.getString(5));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DBOperateException("[HttpCommandReply] selectHttpCommandById() failed:" + e.getMessage());
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

    /**
     * Check if IP Address has existed.
     * <p/>
     * id =0 for adding httpcommandReplys to check
     * id !=0 for updating httpcommandReplys to check
     *
     * @param ip
     * @param id
     * @return false if the ip has existed
     * @throws NotConnectException
     * @throws DBOperateException
     */
    public static boolean isUniqueIPForHttpCommandReplys(String ip, int id) throws NotConnectException, DBOperateException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnPool.getConnection();

            if (id != 0) {
                // update a row
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM httpcommandreply WHERE ip=? AND id<>?");
                pstmt.setInt(2, id);
            } else {
                // insert a row
                pstmt = conn.prepareStatement("SELECT COUNT(*) FROM httpcommandreply WHERE ip=?");
            }

            pstmt.setString(1, ip);
            rs = pstmt.executeQuery();

            return !(rs.next() && rs.getInt(1) > 0);
        } catch (SQLException e) {
            throw new DBOperateException("[HttpCommandReplysDAO] isUniqueIPForHttpCommandReplys() failed: " + e.getMessage());
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
