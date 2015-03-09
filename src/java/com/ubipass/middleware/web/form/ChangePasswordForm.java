/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/ChangePasswordForm.java,v $
 * LastModified By: $Author: fengjianyuan $
 * $Date: 2005/03/24 05:59:56 $
 * 
 */
package com.ubipass.middleware.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * *ChangePasswordAction class
 * 
 * @version $Revision: 1.3 $
 * @author Feng Jianyuan
 * @author $Author: fengjianyuan $
 */

public class ChangePasswordForm extends ActionForm {
	
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String username = "";
	private String oldPassword = "";
	private String newPassword = "";
	private String rePassword = "";
	

	/**
	 * @return Returns the newPassword.
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword The newPassword to set.
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return Returns the oldPassword.
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * @param oldPassword The oldPassword to set.
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * @return Returns the rePassword.
	 */
	public String getRePassword() {
		return rePassword;
	}
	/**
	 * @param rePassword The rePassword to set.
	 */
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

    /**
     *  Reset all of the variable.
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
        this.username = "";
        this.oldPassword = "";
        this.newPassword = "";
        this.rePassword = "";
    }
}