//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/ems/exception/DBOperateException.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 08:14:58 $

package com.ubipass.middleware.ems.exception;

/**
 * DBOperateException class for ems operate db exception.
 *
 * @author Dong Hongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.4 $
 */
public class DBOperateException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Create a DBOperateException with the given detailed message.
     *
     * @param message the detailed message
     */
    public DBOperateException(String message) {
        super(message);
    }

}
