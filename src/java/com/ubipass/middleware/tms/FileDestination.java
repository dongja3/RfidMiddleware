//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/FileDestination.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 09:20:57 $

package com.ubipass.middleware.tms;

/**
 * Destination base class.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.2 $
 */
public class FileDestination {

    // XML message
    private String message;

    private String folder;
    private String fileName;

    /**
     * Constructor.
     *
     * @param folder folder name
     * @param fileName file name
     * @param message xml message to be sent
     */
    public FileDestination(String folder, String fileName, String message) {
        this.folder = folder;
        this.fileName = fileName;
        this.message = message;
    }

    /**
     * Return message.
     *
     * @return XML message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return folder.
     *
     * @return folder
     */
    public String getFolder() {
        return folder;
    }

    /**
     * Return file name.
     *
     * @return file name.
     */
    public String getFileName() {
        return fileName;
    }

}
