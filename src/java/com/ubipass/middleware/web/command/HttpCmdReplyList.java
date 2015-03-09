//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/command/HttpCmdReplyList.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/22 05:32:02 $

package com.ubipass.middleware.web.command;

import com.ubipass.middleware.jdbc.HttpCommandReplyDAO;
import com.ubipass.middleware.jdbc.po.HttpCommandReplyPO;

import java.util.ArrayList;
import java.util.HashMap;
import java.net.InetAddress;

/**
 * HttpCommandReply List class
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.17 $
 */
public class HttpCmdReplyList {

    // note key is ip address of reply server, it shd be in numeric dot format
    private static HashMap<String, HttpCommandReplyPO> httpReplyMap;

    /**
     * get httpList via HttpCommandReplyDAO
     *
     * @throws Exception
     */
    public static void loadHttpList() throws Exception {

        httpReplyMap = new HashMap<String, HttpCommandReplyPO>();

        // get list from dao
        ArrayList<HttpCommandReplyPO> httpList = HttpCommandReplyDAO.getHttpList();

        // put all record to hashmap
        if (httpList != null) {
            for (HttpCommandReplyPO httpPo : httpList) {
                // convert ip address to numeric dot format if necessary
                httpReplyMap.put(InetAddress.getByName(httpPo.getIp()).getHostAddress(), httpPo);
            }
        }

    }

    /**
     * get a commandReplyPo from httpList,if list does not contain one, return null
     *
     * @param ip
     * @return HttpCommandReplyPO
     */
    public static HttpCommandReplyPO getHttpReplyInfo(String ip) {

        return httpReplyMap.get(ip);

    }

}
