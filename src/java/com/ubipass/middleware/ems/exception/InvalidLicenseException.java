//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/ems/exception/InvalidLicenseException.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/10 09:08:35 $

package com.ubipass.middleware.ems.exception;

/**
 * DBOperateException class for ems operate db exception.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public class InvalidLicenseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public InvalidLicenseException() {
        super("Invalid license");
    }

}
