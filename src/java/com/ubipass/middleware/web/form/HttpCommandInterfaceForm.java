/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/HttpCommandInterfaceForm.java,v $
 * LastModified By: $Author: fengjianyuan $
 * $Date: 2005/01/23 09:45:49 $
 * 
 */
package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;


/**
 * *HttpCommandInterfaceForm class
 * 
 * @version $Revision: 1.1 $
 * @author Feng Jianyuan
 * @author $Author: fengjianyuan $
 */
public class HttpCommandInterfaceForm extends ActionForm{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String ip;
    private String port;
    private String path;
    private String username;
    private String password;
    
    /**
     * @return Returns the serialVersionUID.
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }
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
    public String getPort() {
        return port;
    }
    /**
     * @param port The port to set.
     */
    public void setPort(String port) {
        this.port = port;
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
}
