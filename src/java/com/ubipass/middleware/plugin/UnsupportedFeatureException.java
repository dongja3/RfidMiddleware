//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/UnsupportedFeatureException.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/03/21 02:21:20 $

package com.ubipass.middleware.plugin;

/**
 * Throw this exception when reader plug-in doesn't support 
 * the feature defined in Reader interface.
 *
 * @version $Revision: 1.3 $
 * @author Shen Jun
 * @author $Author: donghongshan $
 */
public class UnsupportedFeatureException extends Exception {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a UnsupportedFeatureException with null as its detailed message.
	 */
	public UnsupportedFeatureException() {
		
		super();
		
	}
	
	/**
	 * Create a UnsupportedFeatureException with the given detailed message.
	 * 
	 * @param msg the detailed message
	 */
	public UnsupportedFeatureException(String msg) {
		
		super(msg);
		
	}
}
