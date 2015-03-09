/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/DeleteListUserForm.java,v $
 * LastModified By: $Author: fengjianyuan $
 * $Date: 2005/01/21 08:05:44 $
 * 
 */

package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;



/**
 * *DeleteReaderGroupsForm class
 * 
 * @version $Revision: 1.3 $
 * @author LiuJiaqi
 * @author $Author: fengjianyuan $
 */

public class DeleteListUserForm  extends ActionForm {
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String[] userId;
	
	/**
	 * @return Returns the userId.
	 */
	public String[] getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String[] userId) {
		this.userId = userId;
	}

}