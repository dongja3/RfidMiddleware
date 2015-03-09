// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/vo/EditUserVO.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/04/26 09:03:36 $

package com.ubipass.middleware.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.ubipass.middleware.jdbc.po.DeviceGroupPO;
import com.ubipass.middleware.jdbc.po.DevicePo;

/**
 * * EditUserVO class
 *
 * @author LiuJiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.8 $
 */
public class EditUserVO {

    private String userName;
    private String role;
    private String Description;
    private List<DevicePo> selectDevice = new ArrayList<DevicePo>();
    private List<DevicePo> unselectDevice = new ArrayList<DevicePo>();
    private List<DeviceGroupPO> deviceGroup = new ArrayList<DeviceGroupPO>();

    /**
     * Get the role.
     *
     * @return the role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the role.
     *
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return Returns the selectDevice.
     */
    public List<DevicePo> getSelectDevice() {
        return selectDevice;
    }

    /**
     * @param selectDevice The selectDevice to set.
     */
    public void setSelectDevice(List<DevicePo> selectDevice) {
        this.selectDevice = selectDevice;
    }

    /**
     * @return Returns the unselectDevice.
     */
    public List<DevicePo> getUnselectDevice() {
        return unselectDevice;
    }

    /**
     * @param unselectDevice The unselectDevice to set.
     */
    public void setUnselectDevice(List<DevicePo> unselectDevice) {
        this.unselectDevice = unselectDevice;
    }

    /**
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return Returns the deviceGroup.
     */
    public List<DeviceGroupPO> getDeviceGroup() {
        return deviceGroup;
    }

    /**
     * @param deviceGroup The deviceGroup to set.
     */
    public void setDeviceGroup(List<DeviceGroupPO> deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * @return Returns the deviceGroupSize.
     */
    public int getGroupSize() {
        return deviceGroup.size();
    }

    /**
     * @return Returns the deviceidList.
     */
    public List<String> getDeviceidList() {

        List<String> idlist = new ArrayList<String>();
        String deviceid;

        for (DeviceGroupPO groupPO : deviceGroup) {
            deviceid = "";

            if (groupPO.getDeviceList() != null && groupPO.getDeviceList().size() > 0) {
                for (DevicePo devicePO : groupPO.getDeviceList()) {
                    deviceid = deviceid + String.valueOf(devicePO.getId()) + ',';
                }

                idlist.add(deviceid.substring(0, deviceid.length() - 1));
            } else {
                idlist.add("");
            }
        }

        return idlist;

    }

    /**
     * @return Returns the deviceList.
     */
    public List<String> getDeviceList() {

        List<String> namelist = new ArrayList<String>();

        for (DeviceGroupPO groupPO : deviceGroup) {
            String devicename = "";

            if (groupPO.getDeviceList() != null && groupPO.getDeviceList().size() > 0) {
                for (DevicePo devicePO : groupPO.getDeviceList()) {
                    devicename = devicename + devicePO.getDeviceName() + ',';
                }

                namelist.add(devicename.substring(0, devicename.length() - 1));
            } else {
                namelist.add("");
            }
        }

        return namelist;

    }

}
