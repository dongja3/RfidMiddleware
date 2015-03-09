/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ChangePasswordAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/25 10:39:08 $
 *
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.ChangePasswordForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Action class to change password.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.19 $
 */
public class ChangePasswordAction extends Action {

    /**
     * Action to change password.
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

        // keep menu position
        request.getSession().setAttribute("page", "2,6");

        if (form == null) {
            throw new NoParameterException();
        }

        ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;

        String oldPassword = changePasswordForm.getOldPassword().trim();
        String newPassword = changePasswordForm.getNewPassword().trim();
        String userName = request.getRemoteUser().trim();
        String rePassword = changePasswordForm.getRePassword().trim();

        ActionMessages messages = new ActionMessages();

        if (newPassword.length() < 6) {
            messages.add("length", new ActionMessage("fail.changepassword.password.length"));
        }

        if (!newPassword.equals(rePassword)) {
            messages.add("match", new ActionMessage("fail.changepassword.password.match"));
        }

        if (!messages.isEmpty()) {
            saveMessages(request, messages);
            return mapping.findForward("failure");
        } else {
            if (changePassword(userName, oldPassword, newPassword)) {
                messages.add("success", new ActionMessage("success.changepassword"));
                saveMessages(request, messages);
                return mapping.findForward("success");
            } else {
                messages.add("wrong", new ActionMessage("fail.changepassword.oldpassword.wrong"));
                saveMessages(request, messages);
                return mapping.findForward("failure");
            }
        }

    }

    /**
     * Change user password.
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return true if operation succeeds
     */
    private boolean changePassword(String username, String oldPassword, String newPassword) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnPool.getConnection();
            ps = conn.prepareStatement("UPDATE users SET passwd=? WHERE username=? AND passwd=?");
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.setString(3, oldPassword);

            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                // user is updated
                conn.commit();
                return true;
            } else {
                // user doen't exist or wrong old password
                return false;
            }
        } catch (Exception e) {
            SystemLogger.error("[ChangePasswordAction] changePassword() error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

    }

}