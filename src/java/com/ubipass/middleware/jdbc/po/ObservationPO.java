//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/ObservationPO.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/03/21 02:10:21 $
package com.ubipass.middleware.jdbc.po;

/**
 * ObservationPO
 *
 * @author shenxiaodong
 * @author $Author: donghongshan $
 * @version $Revision: 1.4 $
 */
public class ObservationPO {
	private int id;
	
	private String tagId;
	private String deviceId;
	private long addTime;
	private long removeTime;
	
	/**
	 * @return int
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
	 * @return long
	 */
	public long getAddTime() {
		return addTime;
	}
	/**
	 * @param addTime
	 */
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}
	/**
	 * @return String
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @return long
	 */
	public long getRemoveTime() {
		return removeTime;
	}
	/**
	 * @param removeTime
	 */
	public void setRemoveTime(long removeTime) {
		this.removeTime = removeTime;
	}
	/**
	 * @return String
	 */
	public String getTagId() {
		return tagId;
	}
	/**
	 * @param tagId
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
}
