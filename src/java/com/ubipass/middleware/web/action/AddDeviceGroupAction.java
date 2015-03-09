/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/AddDeviceGroupAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/05/08 10:06:35 $
 *
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.DeviceGroupDAO;
import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.vo.DeviceGroupVO;
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
 * Add Device Group action class.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.31 $
 */
public class AddDeviceGroupAction extends Action {
    /**
     * Action to add/edit device group.
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

        request.getSession().setAttribute("page", "1,4");
        String groupIdString = request.getParameter("groupId") == null ? "" : request.getParameter("groupId").trim();

        if (!groupIdString.equals("")) {
            // update a device group
            int groupId;

            try {
                // convert String to Integer
                groupId = Integer.parseInt(groupIdString);
            } catch (NumberFormatException e) {
                // group id cannot be converted to integer
                throw new NumberFormatException("Group ID is not a number");
            }

            // add devicegroup and device
            DeviceGroupPO group;
            List<DevicePo> selectedReadersList;
            List<DevicePo> unSelectedReadersList;

            try {
                group = DeviceGroupDAO.selectGroupForId(groupId);

                if (group == null) {
                    // group doesn't exist
                    ActionMessages messages = new ActionMessages();

                    messages.add("exist", new ActionMessage("error.record.notexist"));
                    saveMessages(request, messages);
                    return mapping.findForward("failure");
                }

                unSelectedReadersList = DeviceGroupDAO.unSelectedReadersForGroup(groupId);
                selectedReadersList = DeviceGroupDAO.selectedReadersForGroup(groupId);
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            DeviceGroupVO vo = new DeviceGroupVO();
            vo.setId(group.getReaderGroupId());
            vo.setGroupName(group.getReaderGroupName());
            vo.setDescription(group.getDescription());
            request.setAttribute("readerGroup", vo);

            request.setAttribute("unSelectedReadersList", unSelectedReadersList);
            request.setAttribute("selectedReadersList", selectedReadersList);

            return mapping.findForward("Edit");
        } else {
            // add a device group
            List<DevicePo> allReadersList;

            try {
                allReadersList = DeviceGroupDAO.getAllDevices();
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                throw e;
            }

            //check if the system has reader record
            if (allReadersList.size() == 0) {
                ActionMessages messages = new ActionMessages();
                messages.add("exist", new ActionMessage("error.nofound.device"));
                saveMessages(request, messages);
                return mapping.findForward("failure");
            }

            request.setAttribute("unSelectedReadersList", allReadersList);
            return mapping.findForward("Create");
        }

    }

}