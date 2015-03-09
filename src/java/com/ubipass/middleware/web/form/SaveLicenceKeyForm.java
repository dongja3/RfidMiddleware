/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/SaveLicenceKeyForm.java,v $
 * LastModified By: $Author: donghongshan $
 * $Date: 2005/05/11 03:54:42 $
 * 
 */

package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;

/**
 * *SaveSysConfigAction class
 *
 * @author Donghongshan
 * @author $Author: donghongshan $
 * @version $Revision: 1.2 $
 * */
public class SaveLicenceKeyForm extends ActionForm{
	
	private static final long serialVersionUID = 1L;
	private String exprityDate;
	private String userName;
	private String licenceKey;
	
	/**
	 * @return String exprityDate
	 */
	public String getExprityDate() {
		return exprityDate;
	}
	/**
	 * @param exprityDate
	 */
	public void setExprityDate(String exprityDate) {
		this.exprityDate = exprityDate;
	}
	
	/**
	 * @return String licenceKey
	 */
	public String getLicenceKey() {
		return licenceKey;
	}
	/**
	 * @param licenceKey
	 */
	public void setLicenceKey(String licenceKey) {
		this.licenceKey = licenceKey;
	}
	/**
	 * @return String userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
