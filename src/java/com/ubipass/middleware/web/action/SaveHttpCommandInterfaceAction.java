/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SaveHttpCommandInterfaceAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/21 08:38:24 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.HttpCommandReplyDAO;
import com.ubipass.middleware.jdbc.po.HttpCommandReplyPO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.HttpCommandInterfaceForm;
import com.ubipass.middleware.web.command.HttpCmdReplyList;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SaveHttpCommandInterface Action.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.16 $
 */
public class SaveHttpCommandInterfaceAction extends Action {

    /**
     * Action class to save httpCommandInterface
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

        request.getSession().setAttribute("page", "1,1");

        HttpCommandInterfaceForm httpcommandform = (HttpCommandInterfaceForm) form;
        String idString = httpcommandform.getId() == null ? "" : httpcommandform.getId().trim();
        int port;

        if (form == null) {
            throw new NoParameterException();
        }

        try {
            port = Integer.parseInt(httpcommandform.getPort());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Prot is not a number");
        }

        HttpCommandReplyPO po = new HttpCommandReplyPO();
        po.setPort(port);
        po.setIp(httpcommandform.getIp());
        po.setPassword(httpcommandform.getPassword());
        po.setPath(httpcommandform.getPath());
        po.setUserName(httpcommandform.getUsername());

        if (!idString.equals("")) {
            // update an interface
            try {
                po.setId(Integer.parseInt(idString));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("HTTP Command Interface ID is not a number");
            }

            try {
                if (!HttpCommandReplyDAO.isUniqueIPForHttpCommandReplys(po.getIp(), po.getId())) {
                    // IP already exists
                    ActionMessages messages = new ActionMessages();
                    messages.add("save", new ActionMessage("fail.httpcommandreplys.ip.exist", po.getIp()));
                    saveMessages(request, messages);
                    request.setAttribute("httpcommand", po);
                    return mapping.findForward("Edit");
                } else {
                    // update interface
                    HttpCommandReplyDAO.updateHttpCommandReply(po);
                    HttpCmdReplyList.loadHttpList();
                    return mapping.findForward("List");
                }
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }
        }

        try {
            // insert a new interface
            if (!HttpCommandReplyDAO.isUniqueIPForHttpCommandReplys(po.getIp(), 0)) {
                // IP already exists
                ActionMessages messages = new ActionMessages();
                messages.add("save", new ActionMessage("fail.httpcommandreplys.ip.exist", po.getIp()));
                saveMessages(request, messages);
                request.setAttribute("httpcommand", po);
                return mapping.findForward("Create");
            } else {
                // add interface
                HttpCommandReplyDAO.addHttpCommandReply(po);
                HttpCmdReplyList.loadHttpList();
                return mapping.findForward("List");
            }
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

    }

}
