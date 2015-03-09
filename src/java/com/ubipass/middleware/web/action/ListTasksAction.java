// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/ListTasksAction.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/04/19 02:23:48 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.TaskDeviceDAO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.vo.TaskListVO;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Action class to list task.
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.30 $
 */
public class ListTasksAction extends Action {

    /**
     * Action to display task list.
     *
     * @param mapping  ActionMapping
     * @param form     ActionForm
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward forward to Struts page
     * @throws Exception
     * @see org.apache.struts.action.Action
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response)
        throws Exception {

        request.getSession().setAttribute("page", "3,7");

        List<TaskPO> allTasks;
        HttpSession session = request.getSession();

        try {
            if (request.getRemoteUser() != null && session.getAttribute("isOperator") != null
                && session.getAttribute("isOperator").equals("yes")) {
                allTasks = TaskDAO.getTaskByUser(request.getRemoteUser());
            } else {
                allTasks = TaskDAO.getAllTask();
            }
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        List<TaskListVO> taskVoList = new ArrayList<TaskListVO>();
        String deviceID;
        String deviceName;

        for (TaskPO po : allTasks) {
            TaskListVO taskvo = new TaskListVO();
            taskvo.setDescription(po.getDescription());
            taskvo.setId(String.valueOf(po.getId()));

            taskvo.setState(TMSWorker.isMonitorTaskWorking(po.getId()));
            taskvo.setStartupType(po.getStartUp());
            taskvo.setTaskName(po.getTaskName());
            taskvo.setTaskType(po.getTaskType());

            // Only for manual package, set running status of the device
            if (taskvo.getTaskType().equals("M")) {
                try {
                    deviceID = TaskDeviceDAO.getDeviceIDFromTask(po.getId());
                    deviceName = TaskDeviceDAO.getDeviceNameFromTask(po.getId());
                } catch (Exception e) {
                    SystemLogger.error(e.getMessage());
                    throw e;
                }

                taskvo.setRelationDeviceName(escape(deviceName));
                taskvo.setStatusDeviceID(DeviceList.getDeviceStatus(deviceID));
            } else {
                taskvo.setStatusDeviceID(false);
            }

            taskvo.setTriggerMode(po.getTriggerMode());
            taskVoList.add(taskvo);
        }

        request.setAttribute("taskList", taskVoList);

        if (request.getParameter("operation") != null && request.getParameter("operation").equals("delete")) {
            return mapping.findForward("Delete");
        } else {
            return mapping.findForward("List");
        }

    }

    /**
     * Escape " and ' in the string.
     *
     * @param s source string
     * @return escaped string
     */
    private String escape(String s) {

        if (s == null) {
            return null;
        }

        StringBuffer result = new StringBuffer();
        char ch;

        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);

            switch (ch) {
                case '\'':
                    result.append("%27");
                    break;

                case '"':
                    result.append("%22");
                    break;

                default:
                    result.append(ch);
            }
        }

        return result.toString();

    }

}