//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/MainServlet.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/10 09:14:14 $

package com.ubipass.middleware.web;

import com.ubipass.middleware.ems.EMSUtil;
import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.util.ThreadPool;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.command.HttpCmdReplyList;

import javax.servlet.http.HttpServlet;
import java.util.Date;

/**
 * Main servlet loaded when web server starts up.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.31 $
 */
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Servlet initialization.
     */
    public void init() {

        // initialize log4j
        SystemLogger.setServletRoot(getServletContext().getRealPath("/"));
        SystemLogger.info("Middleware is starting...");

        EMSUtil.setPath(getServletContext().getRealPath("/"));

        // check the license
        long expiredDate;

        try {
            expiredDate = EMSUtil.checkLicence();
        } catch (Exception e) {
            SystemLogger.error("System cannot find valid license");
            SystemLogger.error("Middleware startup failed");
            return;
        }

        if (expiredDate < new Date().getTime()) {
            SystemLogger.error("License has expired");
            SystemLogger.error("Middleware startup failed");
            return;
        }

        // startup EMS server
        boolean isSucceed = EMSUtil.startupEMS();

        if (!isSucceed) {
            SystemLogger.fatal("EMS startup failed");
        }

        if (isSucceed) {
            // startup TMS server
            try {
                TMSWorker.startUp();
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                SystemLogger.fatal("TMS startup failed");
                isSucceed = false;
            }
        }

        if (isSucceed) {
            try {
                // load all http command reply records
                HttpCmdReplyList.loadHttpList();
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                SystemLogger.fatal("HTTP Command Interface startup failed");
                isSucceed = false;
            }
        }

        if (isSucceed) {
            SystemLogger.info("Middleware started successfully");
        } else {
            SystemLogger.info("Middleware startup failed");
        }
    }

    /**
     * Servlet cleanup.
     */
    public void destroy() {

        SystemLogger.info("Middleware is stopping...");

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

        SystemLogger.info("Middleware stopped");

    }

}
