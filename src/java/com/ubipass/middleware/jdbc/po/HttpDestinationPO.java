//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/HttpDestinationPO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/14 10:10:14 $

package com.ubipass.middleware.jdbc.po;

/**
 * HttpDestinationPO
 *
 * @author Shen Xiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.3 $
 */
public class HttpDestinationPO {

    private int id;
    private int taskId;
    private String ip;
    private int port;
    private String path;
    private String username;
    private String password;

    /**
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return String
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return int
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return int
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
}
