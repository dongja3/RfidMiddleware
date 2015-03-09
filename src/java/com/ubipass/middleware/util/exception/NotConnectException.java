//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/util/exception/NotConnectException.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/03/09 02:33:35 $

package com.ubipass.middleware.util.exception;

/**
 * Database NotConnectException class
 *
 * @author Donghongshan
 * @author $Author: donghongshan $
 * @version $Revision: 1.5 $
 */
public class NotConnectException extends Exception {

    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Create a NotConnectException with null as its detailed message.
     */
    public NotConnectException() {

        super();

    }

    /**
     * Create a NotConnectException with the given detailed message.
     *
     * @param msg the detailed message
     */
    public NotConnectException(String msg) {

        super(msg);

    }

}
