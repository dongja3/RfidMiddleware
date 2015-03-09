//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/util/XMLUtil.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/03/04 01:50:08 $

package com.ubipass.middleware.util;

/**
 * Utility class to escape XML or HTML string.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public class XMLUtil {

    /**
     * Escape XML or HTML string.
     *
     * @param str input string
     * @return escaped string
     */
    public static String escape(String str) {

        StringBuffer buf = new StringBuffer();
        char ch;

        if (str == null) {
            return null;
        }

        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);

            switch (ch) {
                case '<':
                    buf.append("&lt;");
                    break;

                case '>':
                    buf.append("&gt;");
                    break;

                case '&':
                    buf.append("&amp;");
                    break;

                case '"':
                    buf.append("&quot;");
                    break;

                case '\'':
                    buf.append("&apos;");
                    break;

                default:
                    buf.append(ch);
            }
        }

        return buf.toString();
    }

}
