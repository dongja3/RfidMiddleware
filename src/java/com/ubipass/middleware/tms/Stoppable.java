//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/Stoppable.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/14 09:00:25 $

package com.ubipass.middleware.tms;

/**
 * Interface used to stop the thread.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public interface Stoppable {

    /**
     * Stop the thread.
     */
    public void safeStop();

}
