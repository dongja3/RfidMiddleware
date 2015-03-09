//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/util/HttpXmlPost.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/27 10:49:35 $

package com.ubipass.middleware.util;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

/**
 * Post XML over HTTP.
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.1 $
 */
public class HttpXmlPost {

    /**
     * MultiThreadedHttpConnectionManager as Http connection pool
     */
    private static HttpClient client;

    // static block to initialize connection manager
    static {
        MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = manager.getParams();

        // set connection timeout and socket timeout to 5s
        params.setConnectionTimeout(5000);
        params.setSoTimeout(5000);
        manager.setParams(params);

        client = new HttpClient(manager);
    }

    /**
     * Send XML over HTTP.
     *
     * @param ip
     * @param port
     * @param path
     * @param userName
     * @param password
     * @param xml
     * @throws IOException
     */
    static public void postXml(String ip, int port, String path, String userName, String password, String xml)
        throws IOException {

        //set authentication
        if (userName != null && userName.length() > 0) {
            client.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(userName, password));
        }

        HostConfiguration host = new HostConfiguration();
        host.setHost(ip, port);

        // use http post method
        PostMethod post = new PostMethod();
        post.setPath(path);

        post.setRequestEntity(new StringRequestEntity(xml));

        if (userName != null && userName.length() > 0) {
            post.setDoAuthentication(true);
        }

        // set retry handler
        HttpMethodParams params = post.getParams();

        // disable retry in case of exception
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new HttpMethodRetryHandler() {
            public boolean retryMethod(HttpMethod method,
                                       IOException exception,
                                       int executionCount) {
                // don't retry
                return false;
            }
        });

        post.setParams(params);

        try {
            client.executeMethod(host, post);
        } catch (HttpException e) {
            throw new IOException(e.getMessage());
        } finally {
            post.releaseConnection();
        }

    }

}
