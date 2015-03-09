/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ListDeviceGroupsAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.DeviceGroupDAO;
import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.util.log.SystemLogger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Action class to list/delete device group.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.17 $
 */
public class ListDeviceGroupsAction extends Action {

    /**
     * Action to display/delete device group.
     *
     * @param mapping  ActionMapping
     * @param form     ActionForm
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward forward to Struts page
     * @throws Exception
     * @throws Exception
     * @see org.apache.struts.action.Action
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        request.getSession().setAttribute("page", "1,4");

        // get all device groups
        List<DeviceGroupPO> deviceGroupList;

        try {
            deviceGroupList = DeviceGroupDAO.listReaderGroup();
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        request.setAttribute("readerGroups", deviceGroupList);
        String userOperation = request.getParameter("userOperation") == null ? "" : request.getParameter("userOperation").trim();

        if (userOperation.equals("delete"))
            return (mapping.findForward("Delete"));
        else
            return (mapping.findForward("List"));

    }

}