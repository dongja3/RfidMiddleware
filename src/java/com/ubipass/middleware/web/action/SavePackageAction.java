/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SavePackageAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:33 $
 * 
 */

package com.ubipass.middleware.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ubipass.middleware.tms.task.ManualPackingTask;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.ems.exception.DBOperateException;

/**
 * Action class to do manual packaging.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.25 $
 */
public class SavePackageAction extends Action {

    /**
     * Action to do manual packaging.
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

        String taskId = request.getParameter("taskId");
        String startId = request.getParameter("startId");
        String endId = request.getParameter("endId");

        if (taskId != null && !taskId.equals("")
            && startId != null && !startId.equals("")
            && endId != null && !endId.equals("")) {
            int id;

            try {
                id = Integer.parseInt(taskId);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task ID is not a number");
            }

            try {
                if (!request.isUserInRole("Administrator") &&
                    !TaskDAO.isTaskAssignedToUser(request.getRemoteUser(), id)) {
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

            TaskPO po;

            try {
                po = TaskDAO.getTaskByID(id);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            if (po == null) {
                throw new Exception("Task does not exist");
            }

            try {
                new ManualPackingTask(po).doPack(Integer.parseInt(startId), Integer.parseInt(endId));
            } catch (NumberFormatException e) {
                throw new Exception("startId and/or endId is not number");
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            return mapping.findForward("Successful");
        } else {
            throw new NoParameterException("Some parameters are not defined");
        }

    }

}
