//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/TaskPO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/14 10:10:14 $

package com.ubipass.middleware.jdbc.po;

/**
 * TaskPO
 *
 * @author Shen Xiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.4 $
 */
public class TaskPO {

    private int id;
    private String taskName;
    private String taskType;
    private String startUp;
    private String triggerMode;
    private int parameter;
    private String formatType;
    private String topLevelIDType;
    private String readerIDType;
    private String command;
    private String description;

    /**
     * @return String
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String
     */
    public String getFormatType() {
        return formatType;
    }

    /**
     * @param formatType
     */
    public void setFormatType(String formatType) {
        this.formatType = formatType;
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
    public int getParameter() {
        return parameter;
    }

    /**
     * @param parameter
     */
    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    /**
     * @return String
     */
    public String getReaderIDType() {
        return readerIDType;
    }

    /**
     * @param readerIDType
     */
    public void setReaderIDType(String readerIDType) {
        this.readerIDType = readerIDType;
    }

    /**
     * @return String
     */
    public String getStartUp() {
        return startUp;
    }

    /**
     * @param startUp
     */
    public void setStartUp(String startUp) {
        this.startUp = startUp;
    }

    /**
     * @return String
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return String
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * @param taskType
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * @return String
     */
    public String getTopLevelIDType() {
        return topLevelIDType;
    }

    /**
     * @param topLevelIDType
     */
    public void setTopLevelIDType(String topLevelIDType) {
        this.topLevelIDType = topLevelIDType;
    }

    /**
     * @return String
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
}
