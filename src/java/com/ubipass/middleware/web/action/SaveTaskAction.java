// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SaveTaskAction.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/04/18 08:22:33 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.jdbc.TaskDAO;
import com.ubipass.middleware.jdbc.po.FileDestinationPO;
import com.ubipass.middleware.jdbc.po.HttpDestinationPO;
import com.ubipass.middleware.jdbc.po.TaskPO;
import com.ubipass.middleware.tms.TMSWorker;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.form.SaveTaskForm;
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
 * Action class to save task.
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.40 $
 */
public class SaveTaskAction extends Action {

    /**
     * Action class to save task.
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

        request.getSession().setAttribute("page", "3,7");

        SaveTaskForm saveTask = (SaveTaskForm) form;

        if (form == null) {
            throw new NoParameterException();
        }

        int id = saveTask.getId();

        if (TMSWorker.isMonitorTaskWorking(id)) {
            throw new Exception("The task cannot be changed because it is running");
        }

        TaskPO task = new TaskPO();
        task.setTaskName(saveTask.getTaskName().trim());
        task.setTaskType(saveTask.getTaskType());

        if (saveTask.getTaskType().equals("M"))
            task.setStartUp("");
        else
            task.setStartUp(saveTask.getStartup());

        task.setCommand(saveTask.getCommand());
        task.setDescription(saveTask.getDescription());
        task.setFormatType(saveTask.getFormatType());

        String t = saveTask.getTriggerMode();

        if (t != null) {
            if (t.equals("I") || t.equals("N")) {
                if (saveTask.getParameter().equals("")) {
                    task.setParameter(1);
                } else {
                    task.setParameter(Integer.parseInt(saveTask.getParameter()));
                }
            } else if (t.equals("P")) {
                if (saveTask.getParameter().equals("")) {
                    task.setParameter(0);
                } else {
                    task.setParameter(Integer.parseInt(saveTask.getParameter()));
                }
            } else {
                task.setParameter(0);
            }
        } else {
            t = "A";
        }

        task.setReaderIDType(saveTask.getReaderID());
        task.setTopLevelIDType(saveTask.getTopLevelID());
        task.setTriggerMode(t);
        task.setId(id);

        // HttpDestination
        List<HttpDestinationPO> httpList = new ArrayList<HttpDestinationPO>();
        if (saveTask.getIpHTTP() != null) {
            for (int i = 0; i < saveTask.getIpHTTP().length; i++) {
                HttpDestinationPO httppo = new HttpDestinationPO();
                httppo.setIp(saveTask.getIpHTTP()[i]);
                httppo.setUsername(saveTask.getUserNameHTTP()[i]);
                httppo.setPassword(saveTask.getPasswordHTTP()[i]);
                httppo.setPath(saveTask.getPathHTTP()[i]);
                httppo.setPort(Integer.parseInt(saveTask.getPortHTTP()[i]));
                httpList.add(httppo);
            }
        }

        // FileDestination
        List<FileDestinationPO> fileList = new ArrayList<FileDestinationPO>();
        if (saveTask.getFolderFile() != null) {
            for (int i = 0; i < saveTask.getFolderFile().length; i++) {
                FileDestinationPO filepo = new FileDestinationPO();
                filepo.setFolder(saveTask.getFolderFile()[i]);
                fileList.add(filepo);
            }
        }

        // save task
        try {
            ActionMessages messages = new ActionMessages();

            if (id == 0) {
                // add a new task
                if (!TaskDAO.isTaskNameUnique(saveTask.getTaskName().trim(), 0)) {
                    // task name has existed
                    EditTaskVO taskvo = new EditTaskVO();
                    taskvo.setHttpList(httpList);
                    taskvo.setFileList(fileList);
                    request.setAttribute("task", taskvo);
                    request.setAttribute("selectedDeviceId", saveTask.getDevicesId());

                    // set error message
                    messages.add("save", new ActionMessage("fail.taskName.exist", saveTask.getTaskName()));
                    saveMessages(request, messages);

                    // return back to Task Add page
                    return mapping.findForward("Create");
                } else {
                    // insert this task
                    TaskDAO.insertTask(task, saveTask.getGroupId(), saveTask.getDevicesId(), httpList, fileList);
                }
            } else {
                // update a task
                if (!TaskDAO.isTaskNameUnique(saveTask.getTaskName().trim(), saveTask.getId())) {
                    // task name is changed and this updated name has existed
                    // set error message
                    messages.add("save", new ActionMessage("fail.taskName.exist", saveTask.getTaskName()));
                    saveMessages(request, messages);

                    // reload old task info from database and return back to Task Edit page
                    ActionForward ret = new ActionForward();
                    ret.setPath("/EditTaskAction.do?taskid=" + saveTask.getId());
                    return ret;
                } else {
                    // update this task
                    TaskDAO.updateTask(task, saveTask.getGroupId(), saveTask.getDevicesId(), httpList, fileList);
                }
            }
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            throw e;
        }

        return mapping.findForward("success");

    }

}