/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SysConfigAction.java,v $
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

import com.ubipass.middleware.jdbc.SysConfigDAO;
import com.ubipass.middleware.jdbc.po.SysConfigPO;
import com.ubipass.middleware.util.log.SystemLogger;

/**
 * Action class for system config.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.13 $
 */
public class SysConfigAction extends Action {

    /**
     * Action to display system information
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

        request.getSession().setAttribute("page", "0,0");
        SysConfigPO p;

        try {
            p = SysConfigDAO.getSysConfig();
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        request.setAttribute("sysconfig", p);
        return mapping.findForward("Successful");

    }

}
