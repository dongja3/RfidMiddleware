/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/AddHttpCommandInterfaceAction.java,v $
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ubipass.middleware.jdbc.HttpCommandReplyDAO;
import com.ubipass.middleware.jdbc.po.HttpCommandReplyPO;
import com.ubipass.middleware.util.log.SystemLogger;

/**
 * Add HttpCommandInterface Action Class.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.19 $
 */
public class AddHttpCommandInterfaceAction extends Action {

    /**
     * Action to add/edit httpcommandinterface.
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

        request.getSession().setAttribute("page", "0,1");

        String idString = request.getParameter("id") == null ? "" : request.getParameter("id").trim();

        if (!idString.equals("")) {
            // update an interface
            int id;

            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                // httpcommand id cannot be converted to integer
                throw new NumberFormatException("Httpcommand ID is not a number");
            }

            HttpCommandReplyPO po;

            try {
                po = HttpCommandReplyDAO.selectHttpCommandReplyById(id);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            if (po == null) {
                // interface doesn't exist
                ActionMessages messages = new ActionMessages();
                messages.add("exist", new ActionMessage("error.record.notexist"));
                saveMessages(request, messages);
                return mapping.findForward("failure");
            }

            request.setAttribute("httpcommand", po);
            return mapping.findForward("Edit");
        }else{
            // add a new interface
            return mapping.findForward("Create");
        }
        
    }

}
