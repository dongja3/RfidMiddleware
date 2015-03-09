//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/Event.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/03/21 02:21:20 $

package com.ubipass.middleware.plugin;


/**
 * Event interface.
 *
 * @version $Revision: 1.4 $
 * @author Shen Jun
 * @author $Author: donghongshan $
 */
public interface Event {

	/**
	 * Comment for <code>ADD</code>
	 */
	public static final int ADD = 0;
	/**
	 * Comment for <code>REMOVE</code>
	 */
	public static final int REMOVE = 1;
	/**
	 * Comment for <code>READ</code>
	 */
	public static final int READ = 2;
	
    /**
     * Return event type: ADD, REMOVE or READ. 
	 *
     * @return event type
     */
	public int getEventType();	
	
    /**
     * Return tag ID. 
	 *
     * @return tag ID 
     */
	public String getTagID();
	
    /**
     * Return reader ID. 
	 *
     * @return reader ID 
     */
	public String getReaderID();
	
    /**
     * Return event generated timestamp. 
	 *
     * @return event time
     */
	public long getEventTime();

    /**
     * Set event type. 
	 *
     * @param eventType event Type
     */
	public void setEventType(int eventType);
	
    /**
     * Set tag ID. 
	 *
     * @param tagID tag ID
     */
	public void setTagID(String tagID);

    /**
     * Set reader ID. 
	 *
     * @param readerID reader ID
     */
	public void setReaderID(String readerID);
	
    /**
     * Set event generated time stamp. 
	 *
     * @param eventTime event time
     */
	public void setEventTime(long eventTime);

}
