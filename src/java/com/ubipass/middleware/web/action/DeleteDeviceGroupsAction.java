/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/DeleteDeviceGroupsAction.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/18 08:22:32 $
 *
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.DeviceGroupDAO;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.DeleteDeviceGroupsForm;
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
 * Action class to delete device group.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.25 $
 */
public class DeleteDeviceGroupsAction extends Action {

    /**
     * Action to delete device group.
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

        if (form == null) {
            throw new NoParameterException();
        }

        DeleteDeviceGroupsForm deleteReaderGroupsForm = (DeleteDeviceGroupsForm) form;

        String[] groupIds = deleteReaderGroupsForm.getGroupId();

        if (groupIds == null || groupIds.length == 0) {
            throw new NoParameterException("Group ID is not defined");
        }

        ActionMessages messages = new ActionMessages();
        StringBuffer groupNameCompose = new StringBuffer();

        try {
            // check if group is used by tasks
            List<DevicePo> selectReader;
            int[] ids = new int[groupIds.length];

            for (int i = 0; i < groupIds.length; i++) {
                try {
                    ids[i] = Integer.parseInt(groupIds[i]);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Device Group ID is not a number");
                }

                selectReader = DeviceGroupDAO.selectedReadersForGroup(ids[i]);

                for (DevicePo dpo : selectReader) {
                    if (TaskDAO.isGroupAndDeviceUsedByTask(dpo.getId(), ids[i])) {
                        DeviceGroupPO po = DeviceGroupDAO.selectGroupForId(ids[i]);

                        if (groupNameCompose.length() > 0) {
                            groupNameCompose.append(",");
                        }

                        groupNameCompose.append(po.getReaderGroupName());
                        break;
                    }
                }
            }

            if (groupNameCompose.toString().trim().equals("")) {
                // no error found
                for (int i = 0; i < ids.length; i++) {
                    DeviceGroupDAO.deleteGroup(ids[i]);
                }

                return mapping.findForward("successful");
            } else {
                messages.add("assign", new ActionMessage("fail.group.assign", groupNameCompose));
                saveMessages(request, messages);
                return mapping.findForward("failure");
            }
        } catch (DBOperateException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        } catch (NotConnectException e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

    }

}
