//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/MonitorDeviceServlet.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/26 06:33:02 $

package com.ubipass.middleware.web;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.ems.EMSUtil;
import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.ObservationQueryDAO;
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
 * Device monitoring servlet, display real-time read tag information.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.41 $
 */
public class MonitorDeviceServlet extends HttpServlet {

    /**
     * Response to GET request.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // get parameters from URL
        String deviceID = request.getParameter("deviceID") == null ? "" : request.getParameter("deviceID");
        boolean isClear = (request.getParameter("clear") == null || request.getParameter("clear").equals("")) ? false : true;

        if (!deviceID.equals("")) {
            HttpSession session = request.getSession(false);
            String sessionID = request.getRequestedSessionId();

            // manipulate 2 attributes in the session:
            // 1: name:  deviceID + "|startId|" + sessionID
            //    value: startId
            // 2: name:  deviceID + "|" + sessionID
            //    value: current thread id
            if (isClear) {
                // need to reset start id attribute
                session.removeAttribute(deviceID + "|startId|" + sessionID);
            }

            // set current thread id
            session.setAttribute(deviceID + "|" + sessionID, String.valueOf(Thread.currentThread().getId()));

            try {
                monitorTag(response, request, deviceID);
            } catch (NotConnectException e) {
                SystemLogger.error("[MonitorDeviceServlet] doGet() error: " + e.getMessage());
                throw new ServletException(e.getMessage());
            } catch (DBOperateException e) {
                SystemLogger.error("[MonitorDeviceServlet] doGet() error: " + e.getMessage());
                throw new ServletException(e.getMessage());
            }
        } else {
            throw new ServletException("Parameter deviceID is not defined");
        }
        
    }


    /**
     * Monitor tag read by the device and print new tag inforamtion if found.
     *
     * @param response
     * @param request
     * @param deviceID
     * @throws NotConnectException
     * @throws DBOperateException
     * @throws IOException
     */
    private void monitorTag(HttpServletResponse response, HttpServletRequest request, String deviceID)
        throws NotConnectException, DBOperateException, IOException {

        HttpSession session = request.getSession(false);
        String sessionID = request.getRequestedSessionId();

        int startId;
        int seqNum = 1;
        List<ObservationPO> tags;

        Object value = session.getAttribute(deviceID + "|startId|" + sessionID);

        if (value == null || value.equals("")) {
            startId = ObservationQueryDAO.getCountIdForDevice(deviceID);
            session.setAttribute(deviceID + "|startId|" + sessionID, new Integer(startId));
        } else {
            startId = ((Integer) value).intValue();
        }

        PrintWriter out = response.getWriter();
        
        // clear the window first
        out.println("<script>parent.mainFrame.innerText='';</script>");
        out.flush();

        while (isRunThread(deviceID, request, out)) {
            if (isNeedDBAccess(deviceID)) {
                tags = ObservationQueryDAO.getObservationsByDeviceId(deviceID, startId);

                if (tags != null && tags.size() > 0) {
                    // update startId
                    startId = tags.get(tags.size() - 1).getId();

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
            script.append("<tr><td  width=\"30\" align=\"right\" style=\"font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px;COLOR:#333333; BACKGROUND-COLOR: #F9F9F9 \"><font color=\"green\">")
                .append(seqNum++).append("</font></td><td  width=\"200\"  style=\"font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px;COLOR:#333333; BACKGROUND-COLOR: #F9F9F9\">&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"green\">")
                .append(tag.getTagId()).append("</font></td><td width=\"220\"  style=\"font-family:Lucida Console,Lucida Sans Unicode; font-size: 13px;COLOR:#333333; BACKGROUND-COLOR: #F9F9F9\" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"green\">")
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
     * @param deviceID
     * @param out
     * @return boolean
     */
    private boolean isRunThread(String deviceID, HttpServletRequest request, PrintWriter out) {

        if (request.isRequestedSessionIdValid()) {
            Object value = request.getSession(false).getAttribute(deviceID + "|" + request.getRequestedSessionId());

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