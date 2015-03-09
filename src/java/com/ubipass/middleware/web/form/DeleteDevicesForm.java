/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/DeleteDevicesForm.java,v $
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
public class DeleteDevicesForm  extends ActionForm {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String[] readerId;
	/**
	 * @return Returns the readerId.
	 */
	public String[] getReaderId() {
		return readerId;
	}
	/**
	 * @param readerId The readerId to set.
	 */
	public void setReaderId(String[] readerId) {
		this.readerId = readerId;
	}
}