//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/DeleteTaskForm.java,v $
//LastModified By: $Author: liujiaqi $
//$Date: 2005/03/17 10:30:24 $
package com.ubipass.middleware.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * *DeleteReaderGroupsForm class
 * 
 * @version $Revision: 1.4 $
 * @author LiuJiaqi
 * @author $Author: liujiaqi $
 */
public class DeleteTaskForm  extends ActionForm{
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	/**
     * restet task id  
     * 
	 * @param arg0 ActionMapping
	 * @param arg1 HttpServletRequest
	 * @see org.apache.struts.action.ActionForm
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		taskId=null;
	}
    private String[] taskId;
	/**
	 * @return Returns the taskId.
	 */
	public String[] getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId The taskId to set.
	 */
	public void setTaskId(String[] taskId) {
		this.taskId = taskId;
	}
}
