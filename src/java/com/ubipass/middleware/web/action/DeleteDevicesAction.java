/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/DeleteDevicesAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 *
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.DeviceGroupDAO;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.DeleteDevicesForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class to delete device.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.30 $
 */
public class DeleteDevicesAction extends Action {

    /**
     * Action to delete device.
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

        // keep menu position.
        request.getSession().setAttribute("page", "1,3");

        if (form == null) {
            throw new NoParameterException();
        }

        DeleteDevicesForm deleteReadersForm = (DeleteDevicesForm) form;
        String[] deviceIds = deleteReadersForm.getReaderId();

        if (deviceIds == null || deviceIds.length <= 0) {
            throw new NoParameterException("Device ID is not defined");
        }

        ActionMessages messages = new ActionMessages();
        int ids[] = new int[deviceIds.length];
        DevicePo pos[] = new DevicePo[deviceIds.length];

        try {
            // check if parameters are valid
            for (int i = 0; i < deviceIds.length; i++) {
                ids[i] = Integer.parseInt(deviceIds[i]);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Device ID is not a number");
        }

        // check if the device is running
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < ids.length; i++) {
            pos[i] = DeviceDAO.selectDeviceById(ids[i]);

            if (pos[i] != null && DeviceList.getDeviceStatus(pos[i].getDeviceID())) {
                if (buf.length() > 0) {
                    // there is something in the stringbuffer
                    buf.append(", ");
                }

                buf.append(pos[i].getDeviceName());
            }
        }

        if (buf.length() > 0) {
            messages.add("delete", new ActionMessage("fail.device.delete", buf));
            saveMessages(request, messages);
            return mapping.findForward("Failure");
        }

        // check if the device is used by task
        buf = new StringBuffer();

        for (int i = 0; i < ids.length; i++) {
            try {
                if (TaskDAO.isDeviceUsedByTask(ids[i])) {
                    if (buf.length() > 0) {
                        // there is something in the stringbuffer
                        buf.append(", ");
                    }

                    buf.append(pos[i].getDeviceName());
                }
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }
        }

        if (buf.length() > 0) {
            messages.add("assign", new ActionMessage("fail.device.assign", buf));
            saveMessages(request, messages);
            return mapping.findForward("Failure");
        }

        for (int id : ids) {
            try {
                // delete the device by id
                DeviceGroupDAO.deleteDeviceById(id);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }
        }

        return mapping.findForward("Successful");

    }

}
