/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ViewEPCAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:33 $
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
import com.ubipass.middleware.plugin.Tag;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.util.exception.NotConnectException;

/**
 * View EPC action class.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.25 $
 */
public class ViewEPCAction extends Action {

    /**
     * Action to view tag.
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
        String deviceId = request.getParameter("deviceId") == null ? "" : request.getParameter("deviceId").trim();

        if (!deviceId.equals("")) {
            DevicePo po;
            int id;

            try {
                id = Integer.parseInt(deviceId);
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

            try {
                po = DeviceDAO.selectDeviceById(id);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            if (po == null) {
                throw new Exception("Device does not exist");
            }

            request.setAttribute("device", po);

            if (!DeviceList.getDeviceStatus(po.getDeviceID())) {
                request.setAttribute("notRun", "true");
                return mapping.findForward("List");
            }

            Tag[] tag = DeviceList.getTagList(po.getDeviceID());

            request.setAttribute("tag", tag);
            return mapping.findForward("List");
        } else {
            throw new NoParameterException("Device ID is not defined");
        }

    }

}
