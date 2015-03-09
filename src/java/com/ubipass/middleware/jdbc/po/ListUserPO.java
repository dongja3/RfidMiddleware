/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/ListUserPO.java,v $
 * LastModified By: $Author: liujiaqi $
 * $Date: 2005/01/18 07:31:34 $
 * 
 */
package com.ubipass.middleware.jdbc.po;

import java.util.ArrayList;
import java.util.List;


/**
 * Users PO class,
 *
 * @author LiuJiaqi
 * @author $Author: liujiaqi $
 * @version $Revision: 1.1 $
 */

public class ListUserPO {

    private int id;
    private String userName;
    private List<String> roleType = new ArrayList<String>();
    private String description;

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
     * @return Returns the roleType.
     */
    public List<String> getRoleType() {
        return roleType;
    }

    /**
     * @param roleType The roleType to set.
     */
    public void setRoleType(List<String> roleType) {
        this.roleType = roleType;
    }
    
}
