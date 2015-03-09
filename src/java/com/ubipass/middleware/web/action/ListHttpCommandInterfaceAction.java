/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ListHttpCommandInterfaceAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.HttpCommandReplyDAO;
import com.ubipass.middleware.jdbc.po.HttpCommandReplyPO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.command.HttpCmdReplyList;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Action class to list HTTP command interface.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.10 $
 */

public class ListHttpCommandInterfaceAction extends Action {

    /**
     * Action to display HTTP command interface.
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

        List<HttpCommandReplyPO> po;

        try {
            po = HttpCommandReplyDAO.getHttpList();
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        List<HttpCommandReplyPO> http = new ArrayList<HttpCommandReplyPO>();

        for (HttpCommandReplyPO p : po) {
            if (HttpCmdReplyList.getHttpReplyInfo(p.getIp()) == null) {
                p.setStatus(false);
            } else {
                p.setStatus(true);
            }

            http.add(p);
        }

        request.setAttribute("httpCommandReplyList", http);
        String userOperation = request.getParameter("userOperation") == null ? "" : request.getParameter("userOperation").trim();

        if (userOperation.equals("delete")) {
            return mapping.findForward("Delete");
        } else {
            return mapping.findForward("List");
        }

    }

}
