/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/ReadersPO.java,v $
 * LastModified By: $Author: shenjun $
 * $Date: 2005/05/08 08:35:18 $
 * 
 */

package com.ubipass.middleware.jdbc.po;

/**
 * Readers class.
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.2 $
 */
public class ReadersPO {
    
    private int id;
    private String readerName;
    private String readerType;
    private String connectionType;
    private String readerId;
    private String username;
    private String password;
    private String connectionName;
    private int port;
    private int persistTime;
    private int pluginId;
    private String readerClass;
    private String startUp;
    private String description;

    /**
     * @return Returns the connectionName.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * @param connectionName The connectionName to set.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * @return Returns the connectionType.
     */
    public String getConnectionType() {
        return connectionType;
    }

    /**
     * @param connectionType The connectionType to set.
     */
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
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
     * @return Returns the persistTime.
     */
    public int getPersistTime() {
        return persistTime;
    }

    /**
     * @param persistTime The persistTime to set.
     */
    public void setPersistTime(int persistTime) {
        this.persistTime = persistTime;
    }

    /**
     * @return Returns the pluginId.
     */
    public int getPluginId() {
        return pluginId;
    }

    /**
     * @param pluginId The pluginId to set.
     */
    public void setPluginId(int pluginId) {
        this.pluginId = pluginId;
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
     * @return Returns the readerClass.
     */
    public String getReaderClass() {
        return readerClass;
    }

    /**
     * @param readerClass The readerClass to set.
     */
    public void setReaderClass(String readerClass) {
        this.readerClass = readerClass;
    }

    /**
     * @return Returns the readerId.
     */
    public String getReaderId() {
        return readerId;
    }

    /**
     * @param readerId The readerId to set.
     */
    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    /**
     * @return Returns the readerName.
     */
    public String getReaderName() {
        return readerName;
    }

    /**
     * @param readerName The readerName to set.
     */
    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    /**
     * @return Returns the readerType.
     */
    public String getReaderType() {
        return readerType;
    }

    /**
     * @param readerType The readerType to set.
     */
    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    /**
     * @return Returns the startUp.
     */
    public String getStartUp() {
        return startUp;
    }

    /**
     * @param startUp The startUp to set.
     */
    public void setStartUp(String startUp) {
        this.startUp = startUp;
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
