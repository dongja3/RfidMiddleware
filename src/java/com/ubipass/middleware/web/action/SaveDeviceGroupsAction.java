/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SaveDeviceGroupsAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:33 $
 * 
 */

package com.ubipass.middleware.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.DeviceGroupDAO;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.SaveDeviceGroupsForm;
import com.ubipass.middleware.web.vo.DeviceGroupVO;

/**
 * update/add devicegroup
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.33 $
 */
public class SaveDeviceGroupsAction extends Action {

    /**
     * Action to save device group
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

        // Keep menu's position.
        request.getSession().setAttribute("page", "1,4");

        if (form == null) {
            throw new NoParameterException("");
        }

        SaveDeviceGroupsForm saveReaderGroupsForm = (SaveDeviceGroupsForm) form;
        String groupIdString = saveReaderGroupsForm.getGroupId();
        String groupName = saveReaderGroupsForm.getGroupName().trim();
        String description = saveReaderGroupsForm.getDescription();
        String readerIds = saveReaderGroupsForm.getReaderIds().trim();

        // Read out all of devices from in form and save to list.
        List<DevicePo> readers = new ArrayList<DevicePo>();
        if (readerIds != null && !readerIds.equals("")) {
            String[] readersArray = readerIds.split(",");

            // Convert string to integer and put in list.
            for (int i = 0; i < readersArray.length; i++) {
                DevicePo r = new DevicePo();

                try {
                    r.setId(Integer.parseInt(readersArray[i]));
                    readers.add(r);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Device ID is not a number");
                }
            }
        }

        ActionMessages messages = new ActionMessages();
        DeviceGroupVO groupVo = new DeviceGroupVO();

        try {
            if (groupIdString != null && !groupIdString.equals("")) {
                int groupid;

                // Update the device group.
                try {
                    groupid = Integer.parseInt(groupIdString);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Device Group ID is not a number");
                }

                // Can't save if the group is deleted by other user.
                if (DeviceGroupDAO.selectGroupForId(groupid) == null) {
                    // group doesn't exist
                    messages.add("save", new ActionMessage("fail.group.save", ""));
                    saveMessages(request, messages);
                    return mapping.findForward("Edit");
                }

                if (!DeviceGroupDAO.isUniqueGroupName(groupName, groupid)) {
                    // group name already exists
                    groupVo.setGroupName(groupName);
                    groupVo.setId(groupid);
                    groupVo.setDescription(description);
                    groupVo.setReaderGroupId(readerIds);
                    List<DevicePo> unSelectedReadersList = DeviceGroupDAO.getAllDevices();
                    request.setAttribute("unSelectedReadersList", unSelectedReadersList);
                    messages.add("save", new ActionMessage("fail.group.exist", groupName));
                    saveMessages(request, messages);
                    request.setAttribute("readerGroup", groupVo);
                    return mapping.findForward("Edit");
                }

                List<DevicePo> selectReader;

                selectReader = DeviceGroupDAO.selectedReadersForGroup(groupid);

                for (DevicePo dpo : selectReader) {
                    boolean newAddReader = true;

                    for (DevicePo newDPO : readers) {
                        if (dpo.getId() == newDPO.getId()) {
                            newAddReader = false; // It's not new add reader.
                            break;
                        }
                    }

                    if (newAddReader && TaskDAO.isGroupAndDeviceUsedByTask(dpo.getId(), groupid)) {
                        groupVo.setGroupName(groupName);
                        groupVo.setId(groupid);
                        groupVo.setDescription(description);
                        groupVo.setReaderGroupId(readerIds);
                        List<DevicePo> unSelectedReadersList = DeviceGroupDAO.getAllDevices();
                        request.setAttribute("unSelectedReadersList", unSelectedReadersList);
                        messages.add("change", new ActionMessage("fail.group.change", dpo.getDeviceName()));
                        saveMessages(request, messages);
                        request.setAttribute("readerGroup", groupVo);
                        return mapping.findForward("Edit");
                    }
                }

                DeviceGroupPO p = new DeviceGroupPO();
                p.setReaderGroupId(groupid);
                p.setDescription(description);
                p.setReaderGroupName(groupName);

                DeviceGroupDAO.updateDeviceGroup(p, readers);
            } else {
                // add a new device group
                if (!DeviceGroupDAO.isUniqueGroupName(groupName, 0)) {
                    messages.add("save", new ActionMessage("fail.group.exist", groupName));
                    saveMessages(request, messages);
                    return mapping.findForward("Create");
                }

                DeviceGroupPO p = new DeviceGroupPO();
                p.setDescription(description);
                p.setReaderGroupName(groupName);
                DeviceGroupDAO.insertGroup(p, readers);
            }
        } catch (NotConnectException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        } catch (DBOperateException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        return mapping.findForward("Save");

    }

}