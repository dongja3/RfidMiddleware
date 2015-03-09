// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SaveUserAction.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/04/25 10:39:08 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.UsersDAO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.SaveUserForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class to add/edit user.
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.29 $
 */
public class SaveUserAction extends Action {

    /**
     * Action class to save user
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

        SaveUserForm saveUserForm = (SaveUserForm) form;
        ActionMessages messages = new ActionMessages();

        if (saveUserForm.getId() == 0) {
            // add a user
            try {
                if (!UsersDAO.isUniqueUserName(saveUserForm.getUserName())) {
                    // user already exists
                    messages.add("empty", new ActionMessage("username.existe", saveUserForm.getUserName()));
                    saveMessages(request, messages);
                    return mapping.findForward("Create");
                }
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }
        }

        // update a user
        String[] readersArray = null;
        String readersIdString = saveUserForm.getReaderIds() == null ? "" : saveUserForm.getReaderIds().trim();
        if (!readersIdString.equals("")) {
            readersArray = saveUserForm.getReaderIds().split(",");
        }

        String psw = saveUserForm.getPassword();

        if (psw == null) {
            psw = "";
        }

        try {
            UsersDAO.updateUserDevice(readersArray, saveUserForm.getId(), saveUserForm.getDescription(), saveUserForm.getUserName().trim(), saveUserForm.getRole(), psw.trim());
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        return mapping.findForward("List");

    }

}