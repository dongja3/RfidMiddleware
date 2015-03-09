// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/SaveDevicesForm.java,v $
// LastModified By: $Author: fengjianyuan $
// $Date: 2005/03/23 10:42:46 $
package com.ubipass.middleware.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * * SaveDevicesForm class
 * 
 * @version $Revision: 1.6 $
 * @author LiuJiaqi
 * @author $Author: fengjianyuan $
 */
public class SaveDevicesForm extends ActionForm {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String readerName;

    private String readerType;

    private String connectionType;

    private String readerId;

    private String username;

    private String password;

    private String connectionName;

    private String port;

    private String persistTime;

    private String pluginId;

    private String readerClass;

    private String startUp;

    private String description;

    private String command;

    /**
     * @return Returns the connectionName.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * @param connectionName
     *            The connectionName to set.
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
     * @param connectionType
     *            The connectionType to set.
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
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Returns the persistTime.
     */
    public String getPersistTime() {
        return persistTime;
    }

    /**
     * @param persistTime
     *            The persistTime to set.
     */
    public void setPersistTime(String persistTime) {
        this.persistTime = persistTime;
    }

    /**
     * @return Returns the pluginId.
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * @param pluginId
     *            The pluginId to set.
     */
    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * @return Returns the port.
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port
     *            The port to set.
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return Returns the readerClass.
     */
    public String getReaderClass() {
        return readerClass;
    }

    /**
     * @param readerClass
     *            The readerClass to set.
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
     * @param readerId
     *            The readerId to set.
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
     * @param readerName
     *            The readerName to set.
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
     * @param readerType
     *            The readerType to set.
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
     * @param startUp
     *            The startUp to set.
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
     * @param username
     *            The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Returns the command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command
     *            The command to set.
     */
    public void setCommand(String command) {
        this.command = command;
    }


    /**
     *  Reset all var.
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
      
    }
}
