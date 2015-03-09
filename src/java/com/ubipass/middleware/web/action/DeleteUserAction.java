// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/DeleteUserAction.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/04/18 08:22:32 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.UsersDAO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.DeleteListUserForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class to delete user.
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.24 $
 */
public class DeleteUserAction extends Action {

    /**
     * Action to delete user.
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

        request.getSession().setAttribute("page", "2,5");

        if (form == null) {
            throw new NoParameterException();
        }

        DeleteListUserForm deleteListUserForm = (DeleteListUserForm) form;

        String[] userIds = deleteListUserForm.getUserId();

        if (userIds == null || userIds.length == 0) {
            throw new NoParameterException("User ID is not defined");
        }

        try {
            for (int i = 0; i < userIds.length; i++) {
                UsersDAO.deleteUserById(Integer.parseInt(userIds[i]));
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("User ID is not a number");
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        return mapping.findForward("listdelete");

    }

}