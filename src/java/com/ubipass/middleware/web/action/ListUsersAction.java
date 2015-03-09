/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ListUsersAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/19 05:47:44 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.UsersDAO;
import com.ubipass.middleware.jdbc.po.ListUserPO;
import com.ubipass.middleware.util.log.SystemLogger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Action class to list user.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.25 $
 */
public class ListUsersAction extends Action {

    /**
     * Action to display user list.
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

        List<ListUserPO> list;

        try {
            list = UsersDAO.listAllUser();
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        String user = request.getRemoteUser().trim();

        if (request.getParameter("operation") != null
            && request.getParameter("operation").equals("delete")) {
            List<ListUserPO> listUser = new ArrayList<ListUserPO>();

            for (ListUserPO po : list) {
                // don't include login user himself and the administrator in the list
                if ((!po.getUserName().trim().equals(user))
                    && (!po.getUserName().trim().equals("admin"))) {
                    listUser.add(po);
                }
            }

            request.setAttribute("listUser", listUser);
            return mapping.findForward("delete");
        } else {
            request.setAttribute("listUser", list);
            return mapping.findForward("List");
        }

    }

}