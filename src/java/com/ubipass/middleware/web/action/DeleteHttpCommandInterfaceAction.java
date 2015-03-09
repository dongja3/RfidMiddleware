/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/DeleteHttpCommandInterfaceAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/21 08:38:24 $
 *
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.HttpCommandReplyDAO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.DeleteHttpCommandReplyForm;
import com.ubipass.middleware.web.command.HttpCmdReplyList;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class to delete HTTP command interface.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.17 $
 */
public class DeleteHttpCommandInterfaceAction extends Action {

    /**
     * Action to delete HTTP command interface.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward forward to Struts page
     * @throws Exception
     * @see org.apache.struts.action.Action
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        request.getSession().setAttribute("page", "0,1");

        if (form == null) {
            throw new NoParameterException();
        }

        DeleteHttpCommandReplyForm deletehttpForm = (DeleteHttpCommandReplyForm) form;

        String[] httpComand = deletehttpForm.getHttpCommandId();

        if (httpComand == null || httpComand.length == 0) {
            throw new NoParameterException("HTTP Command Interface ID is not defined");
        }

        int[] ids = new int[httpComand.length];

        for (int i = 0; i < httpComand.length; i++) {
            try {
                ids[i] = Integer.parseInt(httpComand[i]);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("HTTP Command Interface ID is not a number");
            }
        }

        for (int i = 0; i < ids.length; i++) {
            try {
                HttpCommandReplyDAO.delteHttpCommandReply(ids[i]);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            } finally {
                HttpCmdReplyList.loadHttpList();
            }
        }

        return mapping.findForward("Successful");

    }

}
