/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/AddDeviceAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 *
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.PluginsDAO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.jdbc.po.PluginsPO;
import com.ubipass.middleware.util.log.SystemLogger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Add Device action class.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.40 $
 */
public class AddDeviceAction extends Action {

    /**
     * use this action to add/edit deivce
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

        request.getSession().setAttribute("page", "1,3");

        List<PluginsPO> plugins = PluginsDAO.getAllPlugins();
        request.setAttribute("plugin", plugins);

        String deviceIdString = request.getParameter("deviceId") == null ? "" : request.getParameter("deviceId").trim();

        if (!deviceIdString.equals("")) {
            // update a device
            int deviceId;

            try {
                // switch String to int
                deviceId = Integer.parseInt(request.getParameter("deviceId"));
            } catch (NumberFormatException e) {
                // device id cannot be converted to integer
                throw new NumberFormatException("Device ID is not a number");
            }

            // retrieves device by id
            DevicePo devicePo;

            try {
                devicePo = DeviceDAO.selectDeviceById(deviceId);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            ActionMessages messages = new ActionMessages();

            if (devicePo == null) {
                // device does not exist
                messages.add("exist", new ActionMessage("error.record.notexist"));
                saveMessages(request, messages);
                return mapping.findForward("failure");
            }

            if (DeviceList.getDeviceStatus(devicePo.getDeviceID())) {
                messages.add("change", new ActionMessage("fail.device.change"));
                saveMessages(request, messages);
                return mapping.findForward("failure");
            }

            request.setAttribute("device", devicePo);
            return mapping.findForward("Edit");
        } else {
            // add a device
            return mapping.findForward("Create");
        }
    }

}