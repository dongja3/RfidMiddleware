//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/EventImpl.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/03/21 02:21:20 $

package com.ubipass.middleware.plugin;

/**
 * Implementation class of interface Event.
 *
 * @author Liu Jiaqi
 * @author $Author: donghongshan $
 * @version $Revision: 1.8 $
 */
public class EventImpl implements Event {

    private int eventType;
    private String tagID;
    private String readerID;
    private long eventTime;

    /**
     * Return event type: ADD, REMOVE or READ.
     *
     * @return event type
     * @see com.ubipass.middleware.plugin.Event#getEventType()
     */
    public int getEventType() {
        return this.eventType;
    }

    /**
     * Return tag ID.
     *
     * @return tag ID
     * @see com.ubipass.middleware.plugin.Event#getTagID()
     */
    public String getTagID() {
        return this.tagID;
    }

    /**
     * Return reader ID.
     *
     * @return reader ID
     * @see com.ubipass.middleware.plugin.Event#getReaderID()
     */
    public String getReaderID() {
        return this.readerID;
    }


    /**
     * Set event type.
     *
     * @param eventType event Type
     * @see com.ubipass.middleware.plugin.Event#setEventType(int)
     */
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    /**
     * Set tag ID.
     *
     * @param tagID tag ID
     * @see com.ubipass.middleware.plugin.Event#setTagID(java.lang.String)
     */
    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    /**
     * Set reader ID.
     *
     * @param readerID reader ID
     * @see com.ubipass.middleware.plugin.Event#setReaderID(java.lang.String)
     */
    public void setReaderID(String readerID) {
        this.readerID = readerID;
    }


    /**
     * (non-Javadoc)
	 * @see com.ubipass.middleware.plugin.Event#setEventTime(long)
	 */
    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;

    }

    /**
     *  (non-Javadoc)
	 * @see com.ubipass.middleware.plugin.Event#getEventTime()
	 */
    public long getEventTime() {
        return eventTime;
    }

}
