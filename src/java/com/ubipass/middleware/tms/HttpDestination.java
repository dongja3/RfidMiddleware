//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/HttpDestination.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 09:20:57 $

package com.ubipass.middleware.tms;

import com.ubipass.middleware.jdbc.po.HttpDestinationPO;

/**
 * HTTP destination.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.3 $
 */
public class HttpDestination {

    // XML message
    private String message;

    // HTTP destination PO
    private HttpDestinationPO po;

    /**
     * Constructor.
     *
     * @param po      HttpDestinationPO
     * @param message XML message
     */
    public HttpDestination(HttpDestinationPO po, String message) {
        this.po = po;
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
     * Get destination po.
     * 
     * @return httpdestination po
     */
    public HttpDestinationPO getDestinationPo() {
        return po;
    }

}
