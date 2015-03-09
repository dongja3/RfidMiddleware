//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/util/DBConnPool.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/09 06:04:47 $

package com.ubipass.middleware.util;

import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.util.log.SystemLogger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Database connection pool.
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.16 $
 */
public class DBConnPool {

    /**
     * static DataSource reference
     */
    private static DataSource ds = null;

    /**
     * Private constructor to prevent from instantiation.
     */
    private DBConnPool() {

    }

    /**
     * Get a database connection from connection pool.
     *
     * @return Conncection conn
     * @throws NotConnectException
     */
    public static Connection getConnection()
        throws NotConnectException {

        if (ds == null) {
            throw new NotConnectException("Datasource configuration error");
        }

        Connection conn;

        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
        } catch (Exception e) {
            SystemLogger.error("[DBConnPool] getConnection() error: " + e.getMessage());
            throw new NotConnectException(e.getMessage());
        }

        return conn;

    }

    /**
     * static block using jndi to get datasource
     */
    static {
        try {
            Context initCtx;
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/middlewareDB");
        } catch (NamingException e) {
            SystemLogger.fatal("Datasource configuration error: " + e.getMessage());
        }
    }

}
