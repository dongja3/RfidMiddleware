//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/vo/DeviceGroupVO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 09:05:50 $

package com.ubipass.middleware.web.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * DeviceGroupVO class
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.3 $
 */
public class DeviceGroupVO {

    private int id;
    private String groupName;
    private String readerGroupId;
    private String description;
    private List<DeviceVO> device = new ArrayList<DeviceVO>();

    /**
     * @return Returns the device.
     */
    public List<DeviceVO> getDevice() {
        return device;
    }

    /**
     * @param device The device to set.
     */
    public void setDevice(List<DeviceVO> device) {
        this.device = device;
    }

    /**
     * @return Returns the groupName.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName The groupName to set.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
     * @return Returns the readerGroupId.
     */
    public String getReaderGroupId() {
        return readerGroupId;
    }

    /**
     * @param readerGroupId The readerGroupId to set.
     */
    public void setReaderGroupId(String readerGroupId) {
        this.readerGroupId = readerGroupId;
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

}
