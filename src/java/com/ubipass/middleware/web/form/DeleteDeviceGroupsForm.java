/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/DeleteDeviceGroupsForm.java,v $
 * LastModified By: $Author: fengjianyuan $
 * $Date: 2005/01/21 08:05:44 $
 * 
 */
package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;

/**
 * *DeleteReaderGroupsForm class
 * 
 * @version $Revision: 1.2 $
 * @author Feng Jianyuan
 * @author $Author: fengjianyuan $
 */
public class DeleteDeviceGroupsForm  extends ActionForm{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String[] groupId;
	/**
	 * @return Returns the groupId.
	 */
	public String[] getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(String[] groupId) {
		this.groupId = groupId;
	}
}