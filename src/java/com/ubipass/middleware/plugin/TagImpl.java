//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/TagImpl.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/01/27 01:56:57 $

package com.ubipass.middleware.plugin;

/**
 * Implementation class of interface Tag.
 *
 * @author Liu Jiaqi
 * @author $Author: shenjun $
 * @version $Revision: 1.5 $
 */
public class TagImpl implements Tag {

    private String tagID = null;
    private long readTime;

    /**
     * Default constructor.
     */
    public TagImpl() {

    }

    /**
     * Constructor with parameters.
     *
     * @param tagID    tag ID
     * @param readTime read time
     */
    public TagImpl(String tagID, long readTime) {
        this.tagID = tagID;
        this.readTime = readTime;
    }

    /**
     * Return tag ID.
     *
     * @return tag ID
     */
    public String getTagID() {
        return this.tagID;
    }

    /**
     * Return timestamp when tag is first detected by reader.
     *
     * @return first seen time
     */
    public long getFirstSeenTime() {
        return this.readTime;
    }

    /**
     * Set tag ID.
     *
     * @param tagID tag ID
     * @see com.ubipass.middleware.plugin.Tag#setTagID(java.lang.String)
     */
    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    /**
     * Set tag fist seen time.
     *
     * @param readTime timestamp when tag is first detected by reader
     */
    public void setFirstSeenTime(long readTime) {
        this.readTime = readTime;
    }

}
