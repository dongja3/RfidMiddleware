//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/GetTagListServlet.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/26 06:31:16 $

package com.ubipass.middleware.web;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.ems.EMSUtil;
import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.ObservationQueryDAO;
import com.ubipass.middleware.jdbc.TaskDeviceDAO;
import com.ubipass.middleware.jdbc.po.ObservationPO;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.util.log.SystemLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Servlet class for device monitoring and manual packaging.
 *
 * @author Feng jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.72 $
 */
public class GetTagListServlet extends HttpServlet {

    /**
     * Response to HTTP GET command.
     *
     * @param request
     * @param response
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // get parameters from URL
        String taskId = request.getParameter("taskId") == null ? "" : request.getParameter("taskId");
        boolean isClear = (request.getParameter("clear") == null || request.getParameter("clear").equals("")) ? false : true;
        String startIdString = request.getParameter("startId") == null ? "" : request.getParameter("startId");
        String deviceId;

        if (!taskId.equals("")) {
            HttpSession session = request.getSession(false);
            String sessionID = request.getRequestedSessionId();

            // manipulate 2 attributes in the session:
            // 1: name:  taskId + "|MStartID|" + sessionID
            //    value: startId
            // 2: name:  taskId + "|M|" + sessionID
            //    value: current thread id
            if (isClear) {
                // need to reset start id attribute
                session.removeAttribute(taskId + "|MStartID|" + sessionID);
            }

            // set current thread id
            session.setAttribute(taskId + "|M|" + sessionID, String.valueOf(Thread.currentThread().getId()));

            int startId = 0;

            if (!startIdString.equals("")) {
                try {
                    startId = Integer.parseInt(startIdString.trim());
                } catch (NumberFormatException e) {
                    throw new ServletException("Parameter startId is not a number");
                }

                if (startId < 0) {
                    startId = 0;
                }
            }

            try {
                // get task corresponding device id
                deviceId = TaskDeviceDAO.getDeviceIDFromTask(Integer.parseInt(taskId));

                manualPackage(request, response, deviceId, taskId, startId);
            } catch (NumberFormatException e) {
                throw new ServletException("Parameter taskId is not a number");
            } catch (NotConnectException e) {
                SystemLogger.error("[GetTagListServlet] doGet() error: " + e.getMessage());
                throw new ServletException(e.getMessage());
            } catch (DBOperateException e) {
                SystemLogger.error("[GetTagListServlet] doGet() error: " + e.getMessage());
                throw new ServletException(e.getMessage());
            }
        } else {
            throw new ServletException("Parameter taskId is not defined");
        }

    }

    /**
     * Manaual package using the device.
     *
     * @param response
     * @param request
     * @param deviceId
     * @param taskId
     * @param startId
     */
    private void manualPackage(HttpServletRequest request, HttpServletResponse response, String deviceId, String taskId, int startId)
        throws NotConnectException, DBOperateException, IOException {

        HttpSession session = request.getSession(false);
        String sessionID = request.getRequestedSessionId();

        int seqNum = 1;
        List<ObservationPO> tags;

        if (startId == 0) {
            Object value = session.getAttribute(taskId + "|MStartID|" + sessionID);

            if (value == null || value.equals("")) {
                startId = ObservationQueryDAO.getCountIdForDevice(deviceId);
                session.setAttribute(taskId + "|MStartID|" + sessionID, new Integer(startId));
            } else {
                startId = ((Integer) value).intValue();
            }
        }

        setStartId(response, startId);

        PrintWriter out = response.getWriter();

        // clear the window first
        out.println("<script>parent.mainFrame.innerText='';</script>");
        out.flush();

        while (isRunThread(request, out, taskId)) {
            if (isNeedDBAccess(deviceId)) {
                tags = ObservationQueryDAO.getObservationsByDeviceId(deviceId, startId);

                if (tags != null && tags.size() > 0) {
                    // update startId
                    startId = tags.get(tags.size() - 1).getId();
                    setEndId(response, startId);

                    printTagInfo(seqNum, tags, out);

                    // update seqNum
                    seqNum += tags.size();
                }
            }

            try {
                // sleep 1s
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }

    }

    /**
     * Print tag information.
     *
     * @param seqNum
     * @param tags
     * @param out
     */
    private void printTagInfo(int seqNum, List<ObservationPO> tags, PrintWriter out) {

        StringBuffer script = new StringBuffer("<script>parent.mainFrame.document.writeln('<center><table width=\"500\">");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        for (ObservationPO tag : tags) {
            script.append("<tr class=\"bg-shallow\"><td  width=\"30\" align=\"right\" style=\"font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px;COLOR:#333333; BACKGROUND-COLOR: #F9F9F9\"><font color=\"green\">")
                .append(seqNum++).append("</font></td><td  width=\"200\" style=\"font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px;COLOR:#333333; BACKGROUND-COLOR: #F9F9F9\" >&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"green\">")
                .append(tag.getTagId()).append("</font></td><td width=\"220\" style=\"font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px;COLOR:#333333; BACKGROUND-COLOR: #F9F9F9\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"green\">")
                .append(format.format(new Date(tag.getAddTime()))).append("</font></td></tr>");
        }

        script.append("</table></center>');parent.mainFrame.document.body.bottomMargin='0';parent.mainFrame.document.body.topMargin='0';parent.mainFrame.scrollTo(0,999999999);</script>");

        out.println(script.toString());
        out.flush();

    }

    /**
     * Return true if servlet shd continue to run.
     *
     * @param request
     * @param taskId
     * @param out
     * @return boolean
     */
    private boolean isRunThread(HttpServletRequest request, PrintWriter out, String taskId) {

        if (request.isRequestedSessionIdValid()) {
            Object value = request.getSession(false).getAttribute(taskId + "|M|" + request.getRequestedSessionId());

            if (value != null && value.equals(String.valueOf(Thread.currentThread().getId()))) {
                return true;
            }
        }

        // execute javascript to close the window
        out.println("<script>parent.close();</script>");
        out.flush();

        return false;

    }

    /**
     * Set start ID.
     *
     * @param response
     * @param startId
     */
    private void setStartId(HttpServletResponse response, int startId)
        throws IOException {

        PrintWriter out = response.getWriter();

        out = response.getWriter();
        out.println("<script>parent.document.forms[1].startId.value="
            + startId + ";</script>");
        out.flush();

    }

    /**
     * Set end ID.
     *
     * @param response
     * @param endId
     */
    private void setEndId(HttpServletResponse response, int endId)
        throws IOException {

        if (endId != 0) {
            PrintWriter out = response.getWriter();
            out.println("<script>parent.document.forms[0].endIDPacking.value="
                + endId + ";</script>");
            out.flush();
        }

    }

    /**
     * Check if access database for the device is needed.
     *
     * @param deviceId device id
     * @return true if DB access is needed
     */
    private boolean isNeedDBAccess(String deviceId) {

        return DeviceList.getDeviceStatus(deviceId)
            || !EMSUtil.eventQueue.isEmpty();

    }

}