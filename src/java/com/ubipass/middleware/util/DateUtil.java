//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/util/DateUtil.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/26 06:32:16 $

package com.ubipass.middleware.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date Utility for Date Format Translation
 *
 * @author Shen Xiaodong
 * @author $Author: shenjun $
 * @version $Revision: 1.11 $
 */
public class DateUtil {

    /**
     * Convert a java.util.Date To AIIDateFormat(String) like "2005-01-01T14:19:20.453+08:00"
     *
     * @param date
     * @return converted string
     */
    public static String convertToAIIDateFormat(Date date) {
        String formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(date);
        formatDate = formatDate.replaceAll("GTM", "");

        // add colon at 2nd char from the end
        return formatDate.substring(0, formatDate.length() - 2) + ":00";
    }

}
