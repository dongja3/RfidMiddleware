/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/DeviceGroupPO.java,v $
 * LastModified By: $Author: liujiaqi $
 * $Date: 2005/01/18 07:31:33 $
 * 
 */

package com.ubipass.middleware.jdbc.po;

import java.util.ArrayList;
import java.util.List;


/**
 * *ReaderGroupPO  class
 * 
 * @version $Revision: 1.1 $
 * @author Feng Jianyuan
 * @author $Author: liujiaqi $
 */
public class DeviceGroupPO {
	private int readerGroupId;
	private String readerGroupName;
	private String description;
	private List<DevicePo> deviceList= new ArrayList<DevicePo>();

	
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
	 * @return Returns the readerGroupId.
	 */
	public int getReaderGroupId() {
		return readerGroupId;
	}
	/**
	 * @param readerGroupId The readerGroupId to set.
	 */
	public void setReaderGroupId(int readerGroupId) {
		this.readerGroupId = readerGroupId;
	}
	/**
	 * @return Returns the readerGroupName.
	 */
	public String getReaderGroupName() {
		return readerGroupName;
	}
	/**
	 * @param readerGroupName The readerGroupName to set.
	 */
	public void setReaderGroupName(String readerGroupName) {
		this.readerGroupName = readerGroupName;
	}

	/**
	 * @return Returns the deviceList.
	 */
	public List<DevicePo> getDeviceList() {
		return deviceList;
	}
	/**
	 * @param deviceList The deviceList to set.
	 */
	public void setDeviceList(List<DevicePo> deviceList) {
		this.deviceList = deviceList;
	}
}
