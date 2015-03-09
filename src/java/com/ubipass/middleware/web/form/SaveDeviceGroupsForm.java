/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/SaveDeviceGroupsForm.java,v $
 * LastModified By: $Author: fengjianyuan $
 * $Date: 2005/03/23 10:42:36 $
 * 
 */
package com.ubipass.middleware.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * *SaveReaderGroupsForm class
 * 
 * @version $Revision: 1.4 $
 * @author Feng Jianyuan
 * @author $Author: fengjianyuan $
 */
public class SaveDeviceGroupsForm extends ActionForm {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String groupId;
	private String groupName;
	private String description;
	private String readerIds;

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the groupId.
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId The groupId to set.
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return Returns the groupName.
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName The groupName to set.
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return Returns the readerId.
	 */
	public String getReaderIds() {
		return readerIds;
	}
	/**
	 * @param readerIds 
	 */
	public void setReaderIds(String readerIds) {
		this.readerIds = readerIds;
	}
    /**
     *  Reset all var
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
        this.description     = null;
        this.readerIds        = null;
    	this.groupId         = null;
    	super.reset(arg0,arg1);
    }
}
