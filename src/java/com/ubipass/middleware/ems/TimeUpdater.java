//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/ems/TimeUpdater.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/10 09:34:20 $

package com.ubipass.middleware.ems;

import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.ThreadPool;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.util.log.SystemLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * Task to save current system time to table sysconfig.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.8 $
 */
final class TimeUpdater implements Runnable {

    private boolean isRunning;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private long expiredDate;

    /**
     * Constuctor.
     *
     * @param expiredDate
     * @throws NotConnectException
     * @throws SQLException
     */
    public TimeUpdater(long expiredDate) throws NotConnectException, SQLException {
        conn = DBConnPool.getConnection();
        ps = conn.prepareStatement("UPDATE sysconfig SET currentTime=?");
        this.expiredDate = expiredDate;
    }

    /**
     * Stop the thread.
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Update column currentTime in table sysconfig.
     */
    public void run() {

        isRunning = true;
        long now;

        while (isRunning) {
            now = new Date().getTime();

            if (expiredDate < now) {
                // license expired
                // stop EMS and TMS
                SystemLogger.error("License has expired");

                // stop TMS server
                try {
                    TMSWorker.safeStop();
                } catch (Exception e) {
                    SystemLogger.error("[TMS] TMS stop error: " + e.getMessage());
                }

                // stop EMS server
                EMSUtil.stopEMS();

                // shutdown thread pool
                ThreadPool.shutdown();

                isRunning = false;
                return;
            }

            try {
                // save current system time
                ps.setLong(1, now);
                ps.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                SystemLogger.error("[TimeUpdater] run() error: " + e.getMessage());
                isRunning = false;
                break;
            }

            // sleep 2s
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }
        }

    }

    /**
     * Close the connection.
     */
    protected void finalize() {

        try {
            if (ps != null) {
                ps.close();
                ps = null;
            }
        } catch (Exception e) {

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {

            }
        }

    }

}
