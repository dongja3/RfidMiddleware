//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/test/CommissionTag.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/06/01 04:00:59 $

package com.ubipass.middleware.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * Http client simulator to send tag printing PML.
 *
 * @author dhs
 * @author $Author: shenjun $
 * @version $Revision: 1.13 $
 */
public class CommissionTag {

    /**
     * 2 command line params: user name and password.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        if (args.length != 4) {
            System.out.println("Usage: java CommissionTag <username> <password> <printer_id> <tag_id>");
            return;
        }

        System.out.println("Tag commissioning...");
        HttpClient client = new HttpClient();

        HttpState state = client.getState();
        state.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
            new UsernamePasswordCredentials(args[0], args[1]));

        PostMethod post = new PostMethod("http://localhost:8080/middleware/printtag");
        String pml = "<?xml version=\"1.0\" encoding=\"UTF-16\" ?> <Command xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Command.xsd\"><WriteTagData readerID=\""
            + args[2] + "\"><Item><FieldList><Field name=\"EPC\">"
            + args[3] + "</Field></FieldList></Item><Item><FieldList><Field name=\"EPC\">1234</Field></FieldList></Item></WriteTagData></Command>";

        post.setRequestEntity(new StringRequestEntity(pml));
        post.setDoAuthentication(true);

        // execute the GET
        int status = client.executeMethod(post);
        System.out.println(status + "\n" + post.getStatusLine());

        // release any connection resources used by the method
        post.releaseConnection();

    }

}
