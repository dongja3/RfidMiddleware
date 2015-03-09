/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ExecuteTaskAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.ems.exception.DBOperateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class to start/stop task.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.4 $
 */
public class ExecuteTaskAction extends Action {

    /**
     * Action to start/stop task.
     *
     * @param mapping  ActionMapping
     * @param form     ActionForm
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward forward to Struts page
     * @throws Exception
     * @throws Exception
     * @see org.apache.struts.action.Action
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        request.getSession().setAttribute("page", "3,7");
        String stopTaskId = request.getParameter("stopTask");
        String startTaskId = request.getParameter("startTask");
        int taskid;

        if (stopTaskId != null) {
            // stop task
            try {
                taskid = Integer.parseInt(stopTaskId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task ID is not a number");
            }

            try {
                if (!request.isUserInRole("Administrator") &&
                    !TaskDAO.isTaskAssignedToUser(request.getRemoteUser(), taskid)) {
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

            //check if the task is ManualTask or not
            if (!isManualTask(taskid)) {
                TMSWorker.stopMonitorTask(taskid);
            }
        } else {
            // start task
            try {
                taskid = Integer.parseInt(startTaskId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task ID is not a number");
            }

            try {
                if (!request.isUserInRole("Administrator") &&
                    !TaskDAO.isTaskAssignedToUser(request.getRemoteUser(), taskid)) {
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

            if (!isManualTask(taskid)) {
                try {
                    TMSWorker.startMonitorTask(taskid);
                } catch (Exception e) {
                    SystemLogger.error(e.getMessage());
                    throw e;
                }
            }
        }

        return mapping.findForward("success");

    }

    /**
     * Check if task is a manual task.
     *
     * @param id task id
     * @return true if task is a manual task
     * @throws Exception if task does not exist or DB operation error
     */
    private boolean isManualTask(int id)
        throws Exception {
        TaskPO task = TaskDAO.getTaskByID(id);

        if (task == null) {
            throw new Exception("Task does not exist");
        }

        return task.getTaskType().equals("M");
    }

}
