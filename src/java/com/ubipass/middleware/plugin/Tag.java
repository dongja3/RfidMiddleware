//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/Tag.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/01/24 08:54:19 $

package com.ubipass.middleware.plugin;

/**
 * Tag interface for bar code or RFID EPC.
 *
 * @version $Revision: 1.3 $
 * @author Shen Jun
 * @author $Author: shenjun $
 */
public interface Tag {

    /**
     * Return tag ID. 
	 *
     * @return tag ID 
     */
	public String getTagID();
	
    /**
     * Return timestamp when tag is first detected by reader. 
	 *
     * @return first seen time
     */
	public long getFirstSeenTime();

    /**
     * Set tag ID. 
	 *
     * @param tagID tag ID 
     */
	public void setTagID(String tagID);

	/**
     * Set tag fist seen time. 
	 *
     * @param firstSeenTime timestamp when tag is first detected by reader
     */
	public void setFirstSeenTime(long firstSeenTime);

}
