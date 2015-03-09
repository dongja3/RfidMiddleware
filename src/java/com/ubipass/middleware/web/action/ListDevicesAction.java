/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ListDevicesAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.po.DevicePo;
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
 * ListDevicesAction class
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.30 $
 */
public class ListDevicesAction extends Action {

    /**
     * Action to display devices list
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

        // Key menu position
        request.getSession().setAttribute("page", "1,3");

        List<DevicePo> allReaders;

        try {
            if (request.isUserInRole("Viewer") || request.isUserInRole("Operator")) {
                // user is Viewer and Operator
                allReaders = DeviceDAO.getAllDevices(request.getRemoteUser());
            } else {
                // user is administrator
                allReaders = DeviceDAO.getAllDevices();
            }
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        List<DevicePo> allDeviceArrayList = new ArrayList<DevicePo>();

        for (DevicePo po : allReaders) {
            po.setStatus(DeviceList.getDeviceStatus(po.getDeviceID()));
            po.setPrinterViewEPC(DeviceList.canPrintTag(po.getDeviceID()));
            po.setReaderViewEPC(DeviceList.canViewTag(po.getDeviceID()));
            allDeviceArrayList.add(po);
        }

        request.setAttribute("readers", allDeviceArrayList);

        String userOperate = request.getParameter("userOperation") == null ? "" : request.getParameter("userOperation").trim();

        if (request.isUserInRole("Administrator") && userOperate.equals("delete")) {
            return mapping.findForward("Delete");
        } else {
            return mapping.findForward("List");
        }

    }

}