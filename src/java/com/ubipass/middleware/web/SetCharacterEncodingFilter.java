//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/SetCharacterEncodingFilter.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/25 06:53:35 $

package com.ubipass.middleware.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Set Character Encoding Filter
 *
 * @author Feng Jianyuan
 * @author $Author: shenjun $
 * @version $Revision: 1.7 $
 */
public class SetCharacterEncodingFilter implements Filter {

    private String encoding = null;

    /**
     * Take this filter out of service.
     */
    public void destroy() {

    }

    /**
     * Select and set (if specified) the character encoding to be used to
     * interpret request parameters for this request.
     *
     * @param request  The servlet request we are processing
     * @param response
     * @param chain    The filter chain we are processing
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        // Conditionally select and set the character encoding to be used
        if ((request.getCharacterEncoding() == null) && encoding != null) {
            request.setCharacterEncoding(encoding);
        }

        // Pass control on to the next filter
        chain.doFilter(request, response);

    }

    /**
     * Place this filter into service.
     *
     * @param filterConfig
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        encoding = filterConfig.getInitParameter("encoding");

    }

}
