//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/FileDestinationPO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/14 10:10:14 $

package com.ubipass.middleware.jdbc.po;

/**
 * HttpDestinationPO
 *
 * @author Dong Hongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.3 $
 */
public class FileDestinationPO {

    private int id;

    private int taskId;

    private String taskName;

    private String folder;

    /**
     * @return the folder.
     */
    public String getFolder() {
        return folder;
    }

    /**
     * @param folder The folder to set.
     */
    public void setFolder(String folder) {
        this.folder = folder;
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
     * @return Returns the taskId.
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * @param taskId The taskId to set.
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

}
