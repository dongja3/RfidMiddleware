//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/ObservationDAO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/15 03:31:31 $

package com.ubipass.middleware.jdbc;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.plugin.Event;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.exception.NotConnectException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data access class to manage observaton table
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.15 $
 */
public class ObservationDAO {

    private Connection conn = null;

    private PreparedStatement sqlAddEventInsert = null;
    private PreparedStatement sqlReadEventInsert = null;
    private PreparedStatement sqlEventUpdate = null;
    private PreparedStatement sqlSystemRemoveTimeUpdate1 = null;
    private PreparedStatement sqlSystemRemoveTimeUpdate2 = null;
    private PreparedStatement sqlReaderRemoveTimeUpdate1 = null;
    private PreparedStatement sqlReaderRemoveTimeUpdate2 = null;

    /**
     * Initialize database connection and prepared statements.
     *
     * @throws NotConnectException
     * @throws SQLException
     */
    public ObservationDAO() throws NotConnectException, SQLException {

        conn = DBConnPool.getConnection();

        // insert an ADD event to table observaton
        sqlAddEventInsert = conn.prepareStatement("INSERT INTO observation(tagID,deviceID,detectTime) VALUES(?,?,?)");

        // insert an READ event to table observaton
        sqlReadEventInsert = conn.prepareStatement("INSERT INTO observation(tagID,deviceID,detectTime,removeTime) VALUES(?,?,?,?)");

        // update an event's remove time in table observation
        sqlEventUpdate = conn.prepareStatement("UPDATE observation set removeTime=? WHERE deviceID=? AND tagID=? AND removeTime IS NULL");

        // update removeTime in table observation for specific device
        sqlReaderRemoveTimeUpdate1 = conn.prepareStatement("UPDATE observation set removeTime=? WHERE deviceID=? AND removeTime IS NULL AND detectTime<=?");
        sqlReaderRemoveTimeUpdate2 = conn.prepareStatement("UPDATE observation set removeTime=detectTime WHERE deviceID=? AND removeTime IS NULL");

        // update removeTime in table observation
        sqlSystemRemoveTimeUpdate1 = conn.prepareStatement("UPDATE observation set removeTime=? WHERE removeTime IS NULL AND detectTime<=?");
        sqlSystemRemoveTimeUpdate2 = conn.prepareStatement("UPDATE observation set removeTime=detectTime WHERE removeTime IS NULL");

    }

    /**
     * Insert an ADD event to table observation
     *
     * @param event
     * @throws DBOperateException
     */
    public synchronized void insertAddEvent(Event event) throws DBOperateException {

        try {
            sqlAddEventInsert.setString(1, event.getTagID());
            sqlAddEventInsert.setString(2, event.getReaderID());
            sqlAddEventInsert.setLong(3, event.getEventTime());
            sqlAddEventInsert.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DBOperateException(e.getMessage());
            } catch (SQLException e1) {
                throw new DBOperateException(e.getMessage());
            }
        }

    }

    /**
     * Insert a READ event to table observation
     *
     * @param event
     * @throws DBOperateException
     */
    public synchronized void insertReadEvent(Event event) throws DBOperateException {

        try {
            sqlReadEventInsert.setString(1, event.getTagID());
            sqlReadEventInsert.setString(2, event.getReaderID());
            sqlReadEventInsert.setLong(3, event.getEventTime());
            sqlReadEventInsert.setLong(4, event.getEventTime());
            sqlReadEventInsert.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DBOperateException(e.getMessage());
            } catch (SQLException e1) {
                throw new DBOperateException(e.getMessage());
            }
        }

    }

    /**
     * Update record in table observation
     *
     * @param event
     * @throws DBOperateException
     */
    public synchronized void updateEvent(Event event) throws DBOperateException {

        try {
            sqlEventUpdate.setLong(1, event.getEventTime());
            sqlEventUpdate.setString(2, event.getReaderID());
            sqlEventUpdate.setString(3, event.getTagID());

            sqlEventUpdate.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DBOperateException(e.getMessage());
            } catch (SQLException e1) {
                throw new DBOperateException(e.getMessage());
            }
        }

    }

    /**
     * Update removeTime in table observation for specific device.
     *
     * @param deviceID   device id
     * @param removeTime remove time
     * @throws DBOperateException
     */
    public synchronized void updateReaderRemoveTime(String deviceID, long removeTime)
        throws DBOperateException {

        try {
            sqlReaderRemoveTimeUpdate1.setLong(1, removeTime);
            sqlReaderRemoveTimeUpdate1.setString(2, deviceID);
            sqlReaderRemoveTimeUpdate1.setLong(3, removeTime);
            sqlReaderRemoveTimeUpdate1.executeUpdate();

            sqlReaderRemoveTimeUpdate2.setString(1, deviceID);
            sqlReaderRemoveTimeUpdate2.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DBOperateException(e.getMessage());
            } catch (SQLException e1) {
                throw new DBOperateException(e.getMessage());
            }
        }

    }

    /**
     * Update removeTime in table observation for whole system.
     *
     * @param removeTime remove time
     * @throws DBOperateException
     */
    public synchronized void updateSystemRemoveTime(long removeTime)
        throws DBOperateException {

        try {
            // must ensure removeTime >= detectTime
            sqlSystemRemoveTimeUpdate1.setLong(1, removeTime);
            sqlSystemRemoveTimeUpdate1.setLong(2, removeTime);
            sqlSystemRemoveTimeUpdate1.executeUpdate();

            sqlSystemRemoveTimeUpdate2.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DBOperateException(e.getMessage());
            } catch (SQLException e1) {
                throw new DBOperateException(e.getMessage());
            }
        }

    }

    /**
     * Close the statements and database connection
     */
    protected void finalize() {

        try {
            if (sqlAddEventInsert != null) {
                sqlAddEventInsert.close();
            }

            if (sqlReadEventInsert != null) {
                sqlReadEventInsert.close();
            }

            if (sqlEventUpdate != null) {
                sqlEventUpdate.close();
            }

            if (sqlReaderRemoveTimeUpdate1 != null) {
                sqlReaderRemoveTimeUpdate1.close();
            }

            if (sqlReaderRemoveTimeUpdate2 != null) {
                sqlReaderRemoveTimeUpdate2.close();
            }
            if (sqlSystemRemoveTimeUpdate1 != null) {
                sqlSystemRemoveTimeUpdate1.close();
            }

            if (sqlSystemRemoveTimeUpdate2 != null) {
                sqlSystemRemoveTimeUpdate2.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {

        }

    }

}
