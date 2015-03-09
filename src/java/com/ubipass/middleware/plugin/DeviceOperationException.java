//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/DeviceOperationException.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/03/21 02:21:20 $

package com.ubipass.middleware.plugin;

/**
 * Throw this exception when device operation error occurs.
 *
 * @version $Revision: 1.2 $
 * @author Shen Jun
 * @author $Author: donghongshan $
 */
public class DeviceOperationException extends Exception {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a DeviceOperationException with null as its detailed message.
	 */
	public DeviceOperationException() {

		super();

	}

	/**
	 * Create a DeviceOperationException with the given detailed message.
	 *
	 * @param msg the detailed message
	 */
	public DeviceOperationException(String msg) {

		super(msg);

	}

}
