// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/UserPO.java,v $
// LastModified By: $Author: liujiaqi $
 // $Date: 2005/01/18 07:31:34 $
package com.ubipass.middleware.jdbc.po;
/**
* * UserPO class 
* 
* @version $Revision: 1.1 $ 
* @author LiuJiaqi 
* @author $Author: liujiaqi $ 
*/
public class UserPO {
	private String userName;
	private String description;
	private String role;

	/**
	 * @return Returns the role.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            The role to set.
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
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
}