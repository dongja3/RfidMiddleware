//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/vo/EditTaskVO.java,v $
//LastModified By: $Author: shenjun $
// $Date: 2005/04/26 09:05:50 $

package com.ubipass.middleware.web.vo;

import com.ubipass.middleware.jdbc.po.FileDestinationPO;
import com.ubipass.middleware.jdbc.po.HttpDestinationPO;

import java.util.ArrayList;
import java.util.List;

/**
 * EditTaskVO class
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.13 $
 */
public class EditTaskVO {

    private String id;
    private String taskName;
    private String startup;
    private String taskType;
    private String triggerMode;
    private String parameter;
    private String formatType;
    private String command;
    private String readerID;
    private String topLevelID;
    private String description;
    private List<DeviceGroupVO> groupList = new ArrayList<DeviceGroupVO>();
    private List<DeviceVO> deviceList = new ArrayList<DeviceVO>();
    private int groupId;
    private int deviceId;

    private List<HttpDestinationPO> httpList = new ArrayList<HttpDestinationPO>();
    private List<FileDestinationPO> fileList = new ArrayList<FileDestinationPO>();

    private List<String> groupNameList = new ArrayList<String>();
    private List<String> groupIDList = new ArrayList<String>();
    private List<String> deviceNameList = new ArrayList<String>();
    private List<String> deviceIDList = new ArrayList<String>();

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
     * @return Returns the httpList.
     */
    public List<HttpDestinationPO> getHttpList() {
        return httpList;
    }

    /**
     * @param httpList The httpList to set.
     */
    public void setHttpList(List<HttpDestinationPO> httpList) {
        this.httpList = httpList;
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
     * @return Returns the tiggerMode.
     */
    public String getTriggerMode() {
        return triggerMode;
    }

    /**
     * @param triggerMode
     */
    public void setTriggerMode(String triggerMode) {
        this.triggerMode = triggerMode;
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
     * @return Returns the deviceList.
     */
    public List<DeviceVO> getDeviceList() {
        return deviceList;
    }

    /**
     * @param deviceList The deviceList to set.
     */
    public void setDeviceList(List<DeviceVO> deviceList) {
        this.deviceList = deviceList;
    }

    /**
     * @return Returns the groupList.
     */
    public List<DeviceGroupVO> getGroupList() {
        return groupList;
    }

    /**
     * @param groupList The groupList to set.
     */
    public void setGroupList(List<DeviceGroupVO> groupList) {
        this.groupList = groupList;
    }

    /**
     * @return Returns the deviceIDList.
     */
    public List<String> getDeviceIDList() {
        return deviceIDList;
    }

    /**
     * @param deviceIDList The deviceIDList to set.
     */
    public void setDeviceIDList(List<String> deviceIDList) {
        this.deviceIDList = deviceIDList;
    }

    /**
     * @return Returns the deviceNameList.
     */
    public List<String> getDeviceNameList() {
        return deviceNameList;
    }

    /**
     * @param deviceNameList The deviceNameList to set.
     */
    public void setDeviceNameList(List<String> deviceNameList) {
        this.deviceNameList = deviceNameList;
    }

    /**
     * @return Returns the groupIDList.
     */
    public List<String> getGroupIDList() {
        return groupIDList;
    }

    /**
     * @param groupIDList The groupIDList to set.
     */
    public void setGroupIDList(List<String> groupIDList) {
        this.groupIDList = groupIDList;
    }

    /**
     * @return Returns the groupNameList.
     */
    public List<String> getGroupNameList() {
        return groupNameList;
    }

    /**
     * @param groupNameList The groupNameList to set.
     */
    public void setGroupNameList(List<String> groupNameList) {
        this.groupNameList = groupNameList;
    }


    /**
     * @return Returns the fileList.
     */
    public List<FileDestinationPO> getFileList() {
        return fileList;
    }

    /**
     * @param fileList The fileList to set.
     */
    public void setFileList(List<FileDestinationPO> fileList) {
        this.fileList = fileList;
    }

    /**
     * @return Returns the deviceId.
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId The deviceId to set.
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return Returns the groupId.
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * @param groupId The groupId to set.
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
