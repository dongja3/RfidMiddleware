//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/vo/DeviceVO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 09:05:50 $

package com.ubipass.middleware.web.vo;

/**
 * DeviceVO class
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.2 $
 */
public class DeviceVO {

    private String id;
    private String deviceName;

    /**
     * @return Returns the deviceName.
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName The deviceName to set.
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return Returns the value.
     */
    public String getId() {
        return id;
    }

    /**
     * @param value The value to set.
     */
    public void setId(String value) {
        this.id = value;
    }
    
}
