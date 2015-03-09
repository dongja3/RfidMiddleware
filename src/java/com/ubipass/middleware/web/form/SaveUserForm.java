// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/SaveUserForm.java,v $
// LastModified By: $Author: liujiaqi $
 // $Date: 2005/01/15 02:56:22 $
package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;

/**
* * SaveUserForm class 
* 
* @version $Revision: 1.3 $ 
* @author LiuJiaqi 
* @author $Author: liujiaqi $ 
*/
public class SaveUserForm extends ActionForm{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private int id=0;
	private String userName;
	private String description;
	private String password;
	private String role;
	private String readerIds;
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the role.
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role The role to set.
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the readerIds.
	 */
	public String getReaderIds() {
		return readerIds;
	}
	/**
	 * @param readerIds The readerIds to set.
	 */
	public void setReaderIds(String readerIds) {
		this.readerIds = readerIds;
	}
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
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
}
