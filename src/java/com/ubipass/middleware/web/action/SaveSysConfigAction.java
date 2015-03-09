/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SaveSysConfigAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:33 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.SysConfigDAO;
import com.ubipass.middleware.jdbc.po.SysConfigPO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.SaveSysConfigForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * *SaveSysConfigAction class
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.17 $
 */
public class SaveSysConfigAction extends Action {

    /**
     * Action class to save system information
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

        if (form == null) {
            throw new NoParameterException();
        }

        SaveSysConfigForm s = (SaveSysConfigForm) form;

        if (s.getSystemName() == null || s.getSystemName().equals("")) {
            throw new NoParameterException("System name is not defined");
        }

        if (s.getQueuesize() == null || s.getQueuesize().equals("")) {
            throw new NoParameterException("Queue size is not defined");
        }

        int queuesize;
        try {
            queuesize = Integer.parseInt(s.getQueuesize().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Queue size is not a number");
        }

        if (queuesize < 1000) {
            queuesize = 1000;
        }

        SysConfigPO p = new SysConfigPO();
        p.setQueueSize(queuesize);
        p.setMessageStartup("A");
        p.setSoapStartup("A");
        p.setSystemName(s.getSystemName());

        try {
            SysConfigDAO.configSys(p);
            return mapping.findForward("Successful");
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

    }

}
