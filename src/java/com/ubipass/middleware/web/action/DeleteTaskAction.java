// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/DeleteTaskAction.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/04/18 08:22:32 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.DeleteTaskForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class to delete task.
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.21 $
 */
public class DeleteTaskAction extends Action {

    /**
     * Action to delete task.
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

        if (form == null) {
            throw new NoParameterException();
        }

        DeleteTaskForm deleteTaskForm = (DeleteTaskForm) form;

        String[] taskIds = deleteTaskForm.getTaskId();

        if (taskIds == null || taskIds.length == 0) {
            throw new NoParameterException("Task ID is not defined");
        }

        int ids[] = new int[taskIds.length];

        // check task IDs and task status
        for (int i = 0; i < taskIds.length; i++) {
            try {
                ids[i] = Integer.parseInt(taskIds[i]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Task ID is not a number");
            }

            if (TMSWorker.isMonitorTaskWorking(ids[i])) {
                throw new Exception("Running task cannot be deleted");
            }
        }

        try {
            for (int id : ids) {
                TaskDAO.deleteTasksById(id);
            }
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        return mapping.findForward("Delete");

    }

}
