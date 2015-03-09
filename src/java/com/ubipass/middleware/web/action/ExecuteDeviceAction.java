/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ExecuteDeviceAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 * 
 */

package com.ubipass.middleware.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.plugin.DeviceOperationException;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.util.exception.NotConnectException;

/**
 * Action class to start/stop device.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.4 $
 */
public class ExecuteDeviceAction extends Action {

    /**
     * Action to strat/stop device
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
        String deviceID = request.getParameter("deviceID") == null ? "" : request.getParameter("deviceID").trim();
        String action = request.getParameter("Action") == null ? "" : request.getParameter("Action").trim();

        if (deviceID.equals("")) {
            throw new NoParameterException("Device ID is not defined");
        }

        if (action.equals("")) {
            throw new NoParameterException("Action is not defined");
        }

        int id;

        try {
            id = Integer.parseInt(deviceID);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Device ID is not a number");
        }

        try {
            if (!request.isUserInRole("Administrator") &&
                !DeviceDAO.isDeviceAssignedToUser(request.getRemoteUser(), id)) {
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
            po = DeviceDAO.selectDeviceById(id);
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        if (po == null) {
            throw new Exception("Device does not exist");
        }

        // Start Device by DeviceID
        if (action.equals("start")) {
            try {
                DeviceList.addDevice(po);
            } catch (DeviceOperationException e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            return mapping.findForward("List");
        }

        // Stop Device By DeviceID
        if (action.equals("stop")) {
            try {
                DeviceList.stopDevice(po.getDeviceID());
            } catch (DeviceOperationException e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            return mapping.findForward("List");
        }

        // wrong action parameter
        throw new Exception("Unknown action parameter is defined");

    }

}
