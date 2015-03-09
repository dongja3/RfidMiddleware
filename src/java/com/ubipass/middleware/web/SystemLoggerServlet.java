//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/SystemLoggerServlet.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/18 08:33:05 $

package com.ubipass.middleware.web;

import com.ubipass.middleware.util.log.SystemLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Servlet to capture HTTP request and write them to system logger.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.2 $
 */
public class SystemLoggerServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        doPost(req, resp);

    }

    /**
     * Write posted XML to system logger.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        Reader in = new InputStreamReader(req.getInputStream());
        StringBuffer buf = new StringBuffer();
        int ch;

        while ((ch = in.read()) != -1) {
            buf.append((char) ch);
        }

        in.close();

        // write XML to system logger
        SystemLogger.info("[SystemLoggerServlet] HTTP request content:\n\r" + buf.toString());

        resp.setStatus(200);

    }

}