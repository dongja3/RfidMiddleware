//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/EditTaskAction.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/18 08:22:32 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.DeviceGroupDAO;
import com.ubipass.middleware.jdbc.FileDestinationDAO;
import com.ubipass.middleware.jdbc.HttpDestinationDAO;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.TaskDeviceDAO;
import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.jdbc.po.FileDestinationPO;
import com.ubipass.middleware.jdbc.po.HttpDestinationPO;
import com.ubipass.middleware.jdbc.po.TaskDevicePO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.vo.EditTaskVO;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Action class to add/edit task.
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.39 $
 */
public class EditTaskAction extends Action {

    /**
     * Action to add/edit a task.
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

        request.getSession().setAttribute("page", "3,7");

        int id;
        EditTaskVO taskvo = new EditTaskVO();
        List<DeviceGroupPO> dgList;
        ActionMessages messages = new ActionMessages();

        try {
            dgList = DeviceGroupDAO.getAllDeviceGroups();
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        // no device group defined in system
        if (dgList == null || dgList.size() == 0) {
            messages.add("exist", new ActionMessage("error.nofound.devicegroup"));
            saveMessages(request, messages);
            return mapping.findForward("failure");
        }

        List<String> groupNameList = new ArrayList<String>();
        List<String> groupIDList = new ArrayList<String>();
        List<String> deviceNameList = new ArrayList<String>();
        List<String> deviceIDList = new ArrayList<String>();

        try {
            for (DeviceGroupPO dgPO : dgList) {
                groupNameList.add(dgPO.getReaderGroupName());
                groupIDList.add(String.valueOf(dgPO.getReaderGroupId()));
                List<DevicePo> deviceList = DeviceGroupDAO.selectedReadersForGroup(dgPO.getReaderGroupId());

                if (deviceList != null && deviceList.size() > 0) {
                    String deviceName = "";
                    String deviceID = "";

                    for (DevicePo dPO : deviceList) {
                        deviceName = deviceName + dPO.getDeviceName() + ",";
                        deviceID = deviceID + dPO.getId() + ",";
                    }

                    if (deviceName != null && deviceName.length() > 0)
                        deviceNameList.add(deviceName.substring(0, deviceName.length() - 1));
                    else
                        deviceNameList.add("");

                    if (deviceID != null && deviceID.length() > 0)
                        deviceIDList.add(deviceID.substring(0, deviceID.length() - 1));
                    else
                        deviceIDList.add("");
                }
            }
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        taskvo.setGroupIDList(groupIDList);
        taskvo.setGroupNameList(groupNameList);
        taskvo.setDeviceIDList(deviceIDList);
        taskvo.setDeviceNameList(deviceNameList);

        if (request.getParameter("taskid") == null || request.getParameter("taskid").equals("")) {
            // add a task
            taskvo.setId("0");

            // keep http destinations and file destinations previously inputed
            // so user can view old input if the last time saving is unsuccessful
            if (request.getAttribute("task") != null) {
                EditTaskVO taskvoKeep = (EditTaskVO) request.getAttribute("task");
                taskvo.setFileList(taskvoKeep.getFileList());
                taskvo.setHttpList(taskvoKeep.getHttpList());
            }

            request.setAttribute("task", taskvo);
            return mapping.findForward("Add");
        }

        // update a task
        try {
            id = Integer.parseInt(request.getParameter("taskid"));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Task ID is not a number");
        }

        TaskPO taskpo;

        try {
            taskpo = TaskDAO.getTaskByID(id);
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        if (taskpo == null) {
            // task does not exist
            messages.add("exist", new ActionMessage("error.record.notexist"));
            saveMessages(request, messages);
            return mapping.findForward("failure");
        }

        if (TMSWorker.isMonitorTaskWorking(id)) {
            throw new Exception("Task cannot be changed because it is running");
        }

        List<TaskDevicePO> tdList;

        try {
            tdList = TaskDeviceDAO.selectTaskDeviceByTaskId(id);
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        taskvo.setGroupId(tdList.get(0).getGroupID());
        taskvo.setDeviceId(tdList.get(0).getDid());

        List<HttpDestinationPO> httpList;

        try {
            httpList = HttpDestinationDAO.selectHttpDestinationByTaskId(id);
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        taskvo.setHttpList(httpList);

        List<FileDestinationPO> fileList;

        try {
            fileList = FileDestinationDAO.getFileDestinationByTaskId(id);
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        taskvo.setFileList(fileList);

        taskvo.setId(String.valueOf(taskpo.getId()));
        taskvo.setTaskName(taskpo.getTaskName());
        taskvo.setStartup(taskpo.getStartUp());
        taskvo.setTaskType(taskpo.getTaskType());
        taskvo.setTriggerMode(taskpo.getTriggerMode());
        taskvo.setParameter(String.valueOf(taskpo.getParameter()));
        taskvo.setDescription(taskpo.getDescription());
        taskvo.setFormatType(taskpo.getFormatType());
        taskvo.setCommand(taskpo.getCommand());
        taskvo.setReaderID(taskpo.getReaderIDType());
        taskvo.setTopLevelID(taskpo.getTopLevelIDType());

        request.setAttribute("task", taskvo);
        return mapping.findForward("Edit");

    }

}
