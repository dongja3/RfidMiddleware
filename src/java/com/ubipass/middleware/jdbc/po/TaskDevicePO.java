//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/TaskDevicePO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/21 03:40:34 $

package com.ubipass.middleware.jdbc.po;

/**
 * * TaskDevicePo class
 *
 * @author shenxiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.7 $
 */
public class TaskDevicePO {

    private int id;
    private String deviceName;
    private String deviceID;
    private String groupName;
    private int groupID;
    private int taskID;
    private String command;
    private int did; //Table id

    /**
     * Return device id.
     *
     * @return device id
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * @param deviceID
     */
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    /**
     * @return String
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return int
     */
    public int getGroupID() {
        return groupID;
    }

    /**
     * @param groupID
     */
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    /**
     * @return String
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

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
     * @return int
     */
    public int getTaskID() {
        return taskID;
    }

    /**
     * @param taskID
     */
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * @return the Table id.
     */
    public int getDid() {
        return did;
    }

    /**
     * @param did The table id  set.
     */
    public void setDid(int did) {
        this.did = did;
    }

    /**
     * Return device command stirng.
     *
     * @return command string
     */
    public String getCommand() {
        return command;
    }

    /**
     * Set device command string.
     *
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }

}
