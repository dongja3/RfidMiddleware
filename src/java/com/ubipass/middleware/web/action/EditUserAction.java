// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/EditUserAction.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/04/18 08:22:32 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.DeviceGroupDAO;
import com.ubipass.middleware.jdbc.UsersDAO;
import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.jdbc.po.UserPO;
import com.ubipass.middleware.util.exception.NotConnectException;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.vo.EditUserVO;
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
 * Action class to add/edit user.
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.30 $
 */
public class EditUserAction extends Action {

    /**
     * Action to add/edit user.
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

        request.getSession().setAttribute("page", "2,5");

        int userID = 0;
        EditUserVO editUserVO = new EditUserVO();
        boolean isAddUser;

        try {
            if (request.getParameter("userid") != null) {
                // update a user
                isAddUser = false;

                try {
                    userID = Integer.parseInt(request.getParameter("userid"));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("User ID is not a number");
                }

                UserPO userPO;
                userPO = UsersDAO.getUserByID(userID);

                if (userPO == null) {
                    // user does not exist
                    ActionMessages messages = new ActionMessages();
                    messages.add("exist", new ActionMessage("error.record.notexist"));
                    saveMessages(request, messages);
                    return mapping.findForward("failure");
                }

                editUserVO.setUserName(userPO.getUserName());
                editUserVO.setRole(userPO.getRole());
                editUserVO.setDescription(userPO.getDescription());
                editUserVO.setSelectDevice(UsersDAO.getSelectDeviceByUserID(userID));
                editUserVO.setUnselectDevice(UsersDAO.getUnSelectDeviceByUserID(userID));
            } else {
                // add a user
                isAddUser = true;
                editUserVO.setUnselectDevice(DeviceDAO.getAllDevices());
            }

            // set goup-device relation info. in deviceList
            List<DeviceGroupPO> deviceList;
            deviceList = DeviceGroupDAO.getAllDeviceGroups();

            for (DeviceGroupPO po : deviceList) {
                po.setDeviceList(DeviceGroupDAO.selectedReadersForGroup(po.getReaderGroupId()));
            }

            editUserVO.setDeviceGroup(deviceList);
            request.setAttribute("user", editUserVO);
            request.setAttribute("id", String.valueOf(userID));

            if (isAddUser) {
                // add a user
                return mapping.findForward("add");
            } else {
                // update a user
                request.setAttribute("own", editUserVO.getUserName().equals(request.getRemoteUser()));

                return mapping.findForward("edit");
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