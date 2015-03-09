/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/LogOutAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/05 06:39:41 $
 * 
 */

package com.ubipass.middleware.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action class to logout.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.7 $
 */
public class LogOutAction extends Action {

    /**
     * Action to logout.
     *
     * @param mapping  ActionMapping
     * @param form     ActionForm
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward forward to Struts page
     * @see org.apache.struts.action.Action
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) {

        HttpSession s = request.getSession();

        s.invalidate();

        if (request.isUserInRole("ExternalUser")) {
            return mapping.findForward("externalLogin");
        } else {
            return mapping.findForward("logout");
        }

    }

}
