/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SaveDevicesAction.java,v $
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.SaveDevicesForm;

/**
 * Action class to add/update a device.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.36 $
 */
public class SaveDevicesAction extends Action {

    /**
     * Action to add/update device
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

        // Key menu position.
        request.getSession().setAttribute("page", "1,3");

        if (form == null) {
            throw new NoParameterException();
        }

        SaveDevicesForm saveDevice = (SaveDevicesForm) form;

        String connectionName = saveDevice.getConnectionName().trim();
        String type = saveDevice.getConnectionType().trim();
        String description = saveDevice.getDescription();
        String password = saveDevice.getPassword().trim();
        String persistTime = saveDevice.getPersistTime() == null ? ""
            : saveDevice.getPersistTime().trim();
        String port = saveDevice.getPort() == null ? "" : saveDevice.getPort()
            .trim();
        String readerClass = saveDevice.getReaderClass() == null ? ""
            : saveDevice.getReaderClass().trim();

        DevicePo DevicePo = new DevicePo();
        DevicePo.setConnectionName(connectionName);
        DevicePo.setConnectionType(type);
        DevicePo.setDescription(description);
        DevicePo.setPasswd(password);

        // The default value of persistime is 0.
        if (persistTime.equals("")) {
            DevicePo.setPersistTime(0);
        } else {
            DevicePo.setPersistTime(Integer.parseInt(persistTime));
        }

        // The default value of port/baudrate is 0.
        if (port.equals("")) {
            DevicePo.setPort(0);
        } else {
            DevicePo.setPort(Integer.parseInt(port));
        }

        if (readerClass == null || readerClass.equals("")) {
            throw new NoParameterException("Plugin class is not defined");
        }

        DevicePo.setClassName(readerClass);
        DevicePo.setDeviceID(saveDevice.getReaderId());
        DevicePo.setDeviceName(saveDevice.getReaderName());
        DevicePo.setDeviceType(saveDevice.getReaderType());
        DevicePo.setStartup(saveDevice.getStartUp());
        DevicePo.setUserName(saveDevice.getUsername());
        DevicePo.setCommand(saveDevice.getCommand());

        ActionMessages messages = new ActionMessages();
        String id = saveDevice.getId() == null ? "" : saveDevice.getId().trim();

        try {
            if (!id.equals("")) {
                // Update the device.
                try {
                    DevicePo.setId(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Device ID is not a number");
                }

                if (!DeviceDAO.isUniqueDeviceName(DevicePo.getDeviceName(),
                    DevicePo.getId())) {
                    // device name already exists
                    messages.add("exist", new ActionMessage("fail.device.value.exist",
                        DevicePo.getDeviceName()));
                    saveMessages(request, messages);
                    request.setAttribute("device", DevicePo);
                    return mapping.findForward("Edit");
                }

                if (!DeviceDAO.isUniqueDeviceID(DevicePo.getDeviceID(),
                    DevicePo.getId())) {
                    // device id already exists
                    messages.add("exist", new ActionMessage("fail.device.value.exist", DevicePo.getDeviceID()));
                    saveMessages(request, messages);
                    request.setAttribute("device", DevicePo);
                    return mapping.findForward("Edit");
                }

                DevicePo d = DeviceDAO.selectDeviceById(DevicePo.getId());

                if (d == null) {
                    // device doesn't exist
                    messages.add("exist", new ActionMessage("error.record.notexist"));
                    saveMessages(request, messages);
                    return mapping.findForward("List");
                } else if (DeviceList.getDeviceStatus(d.getDeviceID())) {
                    // cannot change properties of running task
                    messages.add("change", new ActionMessage("fail.device.change"));
                    saveMessages(request, messages);
                    return mapping.findForward("List");
                }

                DeviceDAO.updateDeviceById(DevicePo);
            } else {
                // add a device
                if (!DeviceDAO.isUniqueDeviceName(DevicePo.getDeviceName(), 0)) {
                    messages.add("exist", new ActionMessage("fail.device.value.exist",
                        DevicePo.getDeviceName()));
                    saveMessages(request, messages);
                    return mapping.findForward("Create");
                }

                if (!DeviceDAO.isUniqueDeviceID(DevicePo.getDeviceID(), 0)) {
                    messages.add("exist", new ActionMessage("fail.device.value.exist", DevicePo.getDeviceID()));
                    saveMessages(request, messages);
                    return mapping.findForward("Create");
                }

                DeviceDAO.insertDevice(DevicePo);
            }
        } catch (NotConnectException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        } catch (DBOperateException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        return mapping.findForward("List");

    }

}