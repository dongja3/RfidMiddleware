// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/SaveTaskForm.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/03/07 08:19:33 $

package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * * SaveTaskForm class
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.11 $
 */
public class SaveTaskForm extends ActionForm {

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping arg0, HttpServletRequest arg1) {
        id = 0;

        ipFTP = null;
        usernameFTP = null;
        passwordFTP = null;
        folderFTP = null;

        ipHTTP = null;
        portHTTP = null;
        pathHTTP = null;
        userNameHTTP = null;
        passwordHTTP = null;

        connectionFactoryJMS = null;
        typeJMS = null;
        destinationJMS = null;

        folderFile = null;
    }

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4050486720254654004L;

    private int id = 0;
    private String taskName;
    private String startup;
    private String taskType;
    private String triggerMode;
    private String parameter;
    private String formatType;
    private String command;
    private String readerID;
    private String topLevelID;
    private String runningType;
    private String strartTime;
    private String endTime;
    private String encodingScheme;
    private String description;
    private String groupId;
    private String devicesId;

    private String[] ipFTP;
    private String[] usernameFTP;
    private String[] passwordFTP;
    private String[] folderFTP;

    private String[] ipHTTP;
    private String[] portHTTP;
    private String[] pathHTTP;
    private String[] userNameHTTP;
    private String[] passwordHTTP;

    private String[] connectionFactoryJMS;
    private String[] typeJMS;
    private String[] destinationJMS;

    private String[] folderFile;

    /**
     * @return Returns the command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command The command to set.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return Returns the connectionFactoryJMS.
     */
    public String[] getConnectionFactoryJMS() {
        return connectionFactoryJMS;
    }

    /**
     * @param connectionFactoryJMS The connectionFactoryJMS to set.
     */
    public void setConnectionFactoryJMS(String[] connectionFactoryJMS) {
        this.connectionFactoryJMS = connectionFactoryJMS;
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
     * @return Returns the destinationJMS.
     */
    public String[] getDestinationJMS() {
        return destinationJMS;
    }

    /**
     * @param destinationJMS The destinationJMS to set.
     */
    public void setDestinationJMS(String[] destinationJMS) {
        this.destinationJMS = destinationJMS;
    }

    /**
     * @return Returns the devicesId.
     */
    public String getDevicesId() {
        return devicesId;
    }

    /**
     * @param devicesId The devicesId to set.
     */
    public void setDevicesId(String devicesId) {
        this.devicesId = devicesId;
    }

    /**
     * @return Returns the encodingScheme.
     */
    public String getEncodingScheme() {
        return encodingScheme;
    }

    /**
     * @param encodingScheme The encodingScheme to set.
     */
    public void setEncodingScheme(String encodingScheme) {
        this.encodingScheme = encodingScheme;
    }

    /**
     * @return Returns the endTime.
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime The endTime to set.
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return Returns the folderFTP.
     */
    public String[] getFolderFTP() {
        return folderFTP;
    }

    /**
     * @param folderFTP The folderFTP to set.
     */
    public void setFolderFTP(String[] folderFTP) {
        this.folderFTP = folderFTP;
    }

    /**
     * @return Returns the formatType.
     */
    public String getFormatType() {
        return formatType;
    }

    /**
     * @param formatType The formatType to set.
     */
    public void setFormatType(String formatType) {
        this.formatType = formatType;
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
     * @return Returns the ipFTP.
     */
    public String[] getIpFTP() {
        return ipFTP;
    }

    /**
     * @param ipFTP The ipFTP to set.
     */
    public void setIpFTP(String[] ipFTP) {
        this.ipFTP = ipFTP;
    }

    /**
     * @return Returns the ipHTTP.
     */
    public String[] getIpHTTP() {
        return ipHTTP;
    }

    /**
     * @param ipHTTP The ipHTTP to set.
     */
    public void setIpHTTP(String[] ipHTTP) {
        this.ipHTTP = ipHTTP;
    }

    /**
     * @return Returns the parameter.
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * @param parameter The parameter to set.
     */
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    /**
     * @return Returns the passwordFTP.
     */
    public String[] getPasswordFTP() {
        return passwordFTP;
    }

    /**
     * @param passwordFTP The passwordFTP to set.
     */
    public void setPasswordFTP(String[] passwordFTP) {
        this.passwordFTP = passwordFTP;
    }

    /**
     * @return Returns the pathHTTP.
     */
    public String[] getPathHTTP() {
        return pathHTTP;
    }

    /**
     * @param pathHTTP The pathHTTP to set.
     */
    public void setPathHTTP(String[] pathHTTP) {
        this.pathHTTP = pathHTTP;
    }

    /**
     * @return Returns the portHTTP.
     */
    public String[] getPortHTTP() {
        return portHTTP;
    }

    /**
     * @param portHTTP The portHTTP to set.
     */
    public void setPortHTTP(String[] portHTTP) {
        this.portHTTP = portHTTP;
    }

    /**
     * @return Returns the readerID.
     */
    public String getReaderID() {
        return readerID;
    }

    /**
     * @param readerID The readerID to set.
     */
    public void setReaderID(String readerID) {
        this.readerID = readerID;
    }

    /**
     * @return Returns the runningType.
     */
    public String getRunningType() {
        return runningType;
    }

    /**
     * @param runningType The runningType to set.
     */
    public void setRunningType(String runningType) {
        this.runningType = runningType;
    }

    /**
     * @return Returns the startup.
     */
    public String getStartup() {
        return startup;
    }

    /**
     * @param startup The startup to set.
     */
    public void setStartup(String startup) {
        this.startup = startup;
    }

    /**
     * @return Returns the strartTime.
     */
    public String getStrartTime() {
        return strartTime;
    }

    /**
     * @param strartTime The strartTime to set.
     */
    public void setStrartTime(String strartTime) {
        this.strartTime = strartTime;
    }

    /**
     * @return Returns the taskName.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName The taskName to set.
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return Returns the taskType.
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * @param taskType The taskType to set.
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * @return Returns the topLevelID.
     */
    public String getTopLevelID() {
        return topLevelID;
    }

    /**
     * @param topLevelID The topLevelID to set.
     */
    public void setTopLevelID(String topLevelID) {
        this.topLevelID = topLevelID;
    }

    /**
     * @return Returns the triggerMode.
     */
    public String getTriggerMode() {
        return triggerMode;
    }

    /**
     * @param triggerMode The triggerMode to set.
     */
    public void setTriggerMode(String triggerMode) {
        this.triggerMode = triggerMode;
    }

    /**
     * @return Returns the typeJMS.
     */
    public String[] getTypeJMS() {
        return typeJMS;
    }

    /**
     * @param typeJMS The typeJMS to set.
     */
    public void setTypeJMS(String[] typeJMS) {
        this.typeJMS = typeJMS;
    }

    /**
     * @return Returns the usernameFTP.
     */
    public String[] getUsernameFTP() {
        return usernameFTP;
    }

    /**
     * @param usernameFTP The usernameFTP to set.
     */
    public void setUsernameFTP(String[] usernameFTP) {
        this.usernameFTP = usernameFTP;
    }

    /**
     * @return Returns the userNameHTTP.
     */
    public String[] getUserNameHTTP() {
        return userNameHTTP;
    }

    /**
     * @param userNameHTTP The userNameHTTP to set.
     */
    public void setUserNameHTTP(String[] userNameHTTP) {
        this.userNameHTTP = userNameHTTP;
    }

    /**
     * @return Returns the passwordHttp.
     */
    public String[] getPasswordHTTP() {
        return passwordHTTP;
    }

    /**
     * @param passwordHTTP The passwordHttp to set.
     */
    public void setPasswordHTTP(String[] passwordHTTP) {
        this.passwordHTTP = passwordHTTP;
    }

    /**
     * @return Returns the folderFile.
     */
    public String[] getFolderFile() {
        return folderFile;
    }

    /**
     * @param folderFile The folderFile to set.
     */
    public void setFolderFile(String[] folderFile) {
        this.folderFile = folderFile;
    }

    /**
     * @return Returns the groupId.
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId The groupId to set.
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
