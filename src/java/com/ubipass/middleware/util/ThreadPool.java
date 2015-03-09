//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/util/ThreadPool.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/09 06:01:55 $

package com.ubipass.middleware.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread Pool Class.
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.4 $
 */
public class ThreadPool {

    /**
     * Static Thread Pool by using newCachedThreadPool
     */
    private static ExecutorService executor = null;

    /**
     * private constructor
     */
    private ThreadPool() {
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Get singleton instance of the ThreadPoll.
     *
     * @return single instance of ExecutorService
     */
    public static synchronized ExecutorService getInstance() {
        if (executor == null) {
            new ThreadPool();
        }

        return executor;
    }

    /**
     * Shutdown the executor, but allows the tasks in the executor to complete
     */
    public static void shutdown() {
        executor.shutdown();
    }

}
