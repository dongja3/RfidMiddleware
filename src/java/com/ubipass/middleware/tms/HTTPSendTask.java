//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/HTTPSendTask.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 09:20:57 $

package com.ubipass.middleware.tms;

import com.ubipass.middleware.jdbc.po.HttpDestinationPO;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.util.HttpXmlPost;

/**
 * Task to send PML to HTTP destination.
 *
 * @author Shen Xiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.37 $
 */
public class HTTPSendTask implements Runnable {

    private String pml;
    private HttpDestinationPO httpPo;

    /**
     * Constructor.
     *
     * @param httpPo
     * @param pml pml to be sent
     */
    public HTTPSendTask(HttpDestinationPO httpPo, String pml) {
        this.httpPo = httpPo;
        this.pml = pml;
    }

    /**
     * Send pml to HTTP destination.
     */
    public void run() {

        String url = getFullUrl(httpPo.getIp(), httpPo.getPort(), httpPo.getPath());

        try {
            HttpXmlPost.postXml(httpPo.getIp(), httpPo.getPort(), httpPo.getPath(), httpPo.getUsername(), httpPo.getPassword(), pml);
        } catch (Exception e) {
            SystemLogger.error("[HTTPSendTask] Send PML to destination " + url + " error: " + e.getMessage());
        }

    }

    /**
     * Return full Url based on ip, port and path.
     *
     * @param ip
     * @param port
     * @param path
     * @return full url
     */
    private String getFullUrl(String ip, int port, String path) {

        StringBuffer buf = new StringBuffer("http://");

        buf.append(ip);
        buf.append(':');
        buf.append(port);

        if (path != null) {
            path = path.trim();

            if (path.length() > 0) {
                // check leading char of path
                if (path.charAt(0) != '/') {
                    buf.append('/');
                }

                buf.append(path);
            } else {
                buf.append('/');
            }
        } else {
            buf.append('/');
        }

        return buf.toString();

    }

}
