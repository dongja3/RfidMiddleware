//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/NoParameterException.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/04/12 02:47:18 $

package com.ubipass.middleware.web.action;

/**
 * Throw this exception when no parameter passed to web action class.
 *
 * @author Shen Jun
 * @author $Author: donghongshan $
 * @version $Revision: 1.2 $
 */
public class NoParameterException extends Exception {
	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NoParameterException() {
        super("No parameter is defined");
    }

    /**
     * Constructor with a message.
     *
     * @param msg
     */
    public NoParameterException(String msg) {
        super(msg);
    }

}
