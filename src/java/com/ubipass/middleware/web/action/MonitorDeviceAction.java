/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/MonitorDeviceAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.ems.exception.DBOperateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Monitor Device action class.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.18 $
 */
public class MonitorDeviceAction extends Action {

    /**
     * Action to monitor device
     *
     * @param mapping  ActionMapping
     * @param form     ActionForm
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward forward to Struts page
     * @throws Exception
     * @see org.apache.struts.action.Action
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        request.getSession().setAttribute("page", "1,3");
        String deviceIdString = request.getParameter("deviceId");
        deviceIdString = (deviceIdString == null) ? "" : deviceIdString.trim();

        if (deviceIdString.equals("")) {
            throw new NoParameterException("Device ID is not defined");
        }

        int deviceId;

        try {
            deviceId = Integer.parseInt(deviceIdString);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Device ID is not a number");
        }

        try {
            if (!request.isUserInRole("Administrator") &&
                !DeviceDAO.isDeviceAssignedToUser(request.getRemoteUser(), deviceId)) {
                // device isn't assigned to the user
                throw new Exception("Device is not assigned to the user");
            }
        } catch (DBOperateException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        } catch (NotConnectException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        DevicePo po;

        try {
            po = DeviceDAO.selectDeviceById(deviceId);
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        if (po == null) {
            // device does not exist
            throw new Exception("Device does not exist");
        }

        request.setAttribute("deviceID", po.getDeviceID());
        request.setAttribute("deviceName", po.getDeviceName());
        request.setAttribute("deviceId", deviceIdString);

        String clear = request.getParameter("clear");
        clear = (clear == null) ? "" : clear;
        request.setAttribute("clear", clear);

        if (!clear.equals("")) {
            // set view time
            request.getSession().setAttribute("monitorViewTime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        }

        return mapping.findForward("startMonitor");

    }

}
