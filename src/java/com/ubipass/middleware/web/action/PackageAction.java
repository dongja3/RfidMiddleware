/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/PackageAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:33 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.TaskDeviceDAO;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.TaskDevicePO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.util.exception.NotConnectException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * PackageAction Class.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.27 $
 */
public class PackageAction extends Action {

    /**
     * Action to do manual packaging
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

        request.getSession().setAttribute("page", "3,7");
        String taskIdString = request.getParameter("taskId") == null ? ""
            : request.getParameter("taskId").trim();

        if (!taskIdString.equals("")) {
            int taskId;
            List<TaskDevicePO> taskPo;

            // retrieve deviceID by taskid from TaskDeviceDAO
            try {
                taskId = Integer.parseInt(taskIdString);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task ID is not a nubmer");
            }

            try {
                if (!request.isUserInRole("Administrator") &&
                    !TaskDAO.isTaskAssignedToUser(request.getRemoteUser(), taskId)) {
                    // task isn't assigned to the user
                    throw new Exception("Task is not assigned to the user");
                }
            } catch (DBOperateException e) {
                SystemLogger.error(e.getMessage());
                throw e;
            } catch (NotConnectException e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            try {
                taskPo = TaskDeviceDAO.selectTaskDeviceByTaskId(taskId);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            if (taskPo.size() == 0) {
                // task doesn't contain a device
                throw new Exception("No device is assigned to the task");
            }

            TaskDevicePO device = taskPo.get(0);
            String deviceID = device.getDeviceID();

            if (!DeviceList.getDeviceStatus(deviceID)) {
                // device is not running
                request.setAttribute("deviceName", device.getDeviceName());
                request.setAttribute("taskId", String.valueOf(taskId));
                return mapping.findForward("NoRunDevice");
            }

            request.setAttribute("taskId", String.valueOf(taskId));
            request.setAttribute("deviceID", deviceID);
            request.setAttribute("deviceName", device.getDeviceName());

            String clear = request.getParameter("clear");
            clear = (clear == null) ? "" : clear;
            request.setAttribute("clear", clear);

            String startId = request.getParameter("startId") == null ? "" : request.getParameter("startId").trim();
            request.setAttribute("startId", startId);

            if (!clear.equals("")) {
                request.getSession().setAttribute("packageViewTime", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            }

            return mapping.findForward("List");
        } else {
            throw new NoParameterException("Task ID is not defined");
        }

    }

}
