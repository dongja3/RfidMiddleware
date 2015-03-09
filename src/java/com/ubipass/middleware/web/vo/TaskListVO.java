//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/vo/TaskListVO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 09:05:50 $

package com.ubipass.middleware.web.vo;

/**
 * TaskList.jsp VO
 *
 * @author Liu Jiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.9 $
 */
public class TaskListVO {

    private String id;
    private String taskName;
    private String description;
    private String startupType;
    private String triggerMode;
    private boolean state;
    private String taskType;
    private String runningType;
    private String relationDeviceName;
    private boolean statusDeviceID; //keep status for Manual Packing.

    /**
     * constructor
     */
    public TaskListVO() {
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
     * @return Returns the statrupType.
     */
    public String getStartupType() {
        return startupType;
    }

    /**
     * @param statrupType The statrupType to set.
     */
    public void setStartupType(String statrupType) {
        this.startupType = statrupType;
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
     * @return Returns the state.
     */
    public boolean isState() {
        return state;
    }

    /**
     * @param state The state to set.
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * @return Returns the statusDeviceID.
     */
    public boolean getStatusDeviceID() {
        return statusDeviceID;
    }

    /**
     * @param statusDeviceID The statusDeviceID to set.
     */
    public void setStatusDeviceID(boolean statusDeviceID) {
        this.statusDeviceID = statusDeviceID;
    }

    /**
     * @return Returns the relationDeviceName.
     */
    public String getRelationDeviceName() {
        return relationDeviceName;
    }

    /**
     * @param relationDeviceName The relationDeviceName to set.
     */
    public void setRelationDeviceName(String relationDeviceName) {
        this.relationDeviceName = relationDeviceName;
    }

}