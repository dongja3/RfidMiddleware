/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/HttpCommandReplyPO.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/04/22 05:32:02 $
 * 
 */

package com.ubipass.middleware.jdbc.po;

/**
 * *HttpCommandReplyPO  class
 *
 * @author DongHongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.4 $
 */
public class HttpCommandReplyPO {

    private int id;
    private String ip;
    private boolean status;

    /**
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

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
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
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

    private int port;
    private String path;
    private String userName;
    private String password;

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
     * @return Returns the status.
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
