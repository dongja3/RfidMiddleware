//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/PrintTagServlet.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/26 07:36:51 $

package com.ubipass.middleware.web;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.SysConfigDAO;
import com.ubipass.middleware.jdbc.UsersDAO;
import com.ubipass.middleware.jdbc.po.HttpCommandReplyPO;
import com.ubipass.middleware.plugin.Device;
import com.ubipass.middleware.util.DateUtil;
import com.ubipass.middleware.util.HttpXmlPost;
import com.ubipass.middleware.util.log.SystemLogger;
import com.ubipass.middleware.web.command.HttpCmdReplyList;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Servlet to process tag print PML command.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.12 $
 */
public class PrintTagServlet extends HttpServlet {

    private static final String separator = System.getProperty("line.separator");
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    /*
     * Process received PML, invoke a thread to do tag printing and send result back.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // this servlet doesn't use declarative security
        // have to enforce BASIC authentication by itself

        // check header Authorization
        String auth = request.getHeader("Authorization");

        if (auth == null || auth.length() == 0) {
            response.setHeader("WWW-authenticate", "BASIC realm=\"Print Tag\"");
            response.setStatus(401);
            return;
        }

        // get Base64 password
        StringTokenizer tokenizer = new StringTokenizer(auth);

        if (tokenizer.countTokens() < 2) {
            response.setHeader("WWW-authenticate", "BASIC realm=\"Print Tag\"");
            response.setStatus(401);
            return;
        }

        // 2nd token is Base64 password
        tokenizer.nextToken();
        String password = tokenizer.nextToken();

        // decode password
        password = new String(Base64.decodeBase64(password.getBytes()));

        // note that password form is <user>:<password>
        tokenizer = new StringTokenizer(password, ":");

        if (tokenizer.countTokens() < 2) {
            response.setHeader("WWW-authenticate", "BASIC realm=\"Print Tag\"");
            response.setStatus(401);
            return;
        }

        String role;
        String user = tokenizer.nextToken();

        // get role of the user
        try {
            role = UsersDAO.getUserRole(user, tokenizer.nextToken());
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
            response.setHeader("WWW-authenticate", "BASIC realm=\"Print Tag\"");
            response.setStatus(401);
            return;
        }

        if (role == null) {
            response.setHeader("WWW-authenticate", "BASIC realm=\"Print Tag\"");
            response.setStatus(401);
            return;
        } else if (!role.equals("Administrator") && !role.equals("Operator")) {
            // set Unauthorized status
            response.setStatus(403);
            return;
        }

        // parse received PML
        ArrayList<String> tagList = xmlParse(request.getRemoteAddr(), request.getInputStream());

        if (tagList == null || tagList.size() < 2) {
            // PML format error
            // bad request
            response.setStatus(400);
            return;
        }

        if (role.equals("Operator")) {
            // check if device is assigned to the user
            try {
                if (!DeviceDAO.isDeviceAssignedToUser(user, tagList.get(0))) {
                    // user is not authorized to use this device
                    response.setStatus(403);
                    return;
                }
            } catch (Exception e) {
                SystemLogger.error(e.getMessage());
                response.setStatus(403);
                return;
            }
        }

        if (DeviceList.getDeviceMap().get(tagList.get(0)) == null) {
            // device doesn't exit or it isn't running
            SystemLogger.warn("System cannot process tag commissioning command because device " + tagList.get(0) + " does not exist or is not running");

            // internal server error
            response.setStatus(500);
            return;
        }

        // print tag(s) and send result back
        response.setStatus(printTag(request.getRemoteAddr(), tagList));

    }

    /**
     * Print tags.
     *
     * @param ip      ip address of sender
     * @param tagList printed tag list
     * @return HTTP status code, 200 or 500
     */
    private int printTag(String ip, ArrayList<String> tagList) {

        String pmluid = null;
        String printerID = tagList.get(0);
        String command = null;
        int status = 200;

        try {
            pmluid = SysConfigDAO.getSystemName();
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
        }

        pmluid = (pmluid == null) ? "" : pmluid;

        try {
            command = DeviceDAO.getDeviceCommand(printerID);
        } catch (Exception e) {
            SystemLogger.error(e.getMessage());
        }

        command = (command == null) ? "" : command;

        boolean isSucceed;
        StringBuffer pml = null;

        HttpCommandReplyPO po = HttpCmdReplyList.getHttpReplyInfo(ip);

        if (po != null) {
            pml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?> <pmlcore:Sensor xmlns:pmlcore=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1\" xmlns:pmluid=\"urn:autoid:specification:universal:Identifier:xml:schema:1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
            pml.append(separator);
            pml.append("<pmluid:ID>").append(pmluid).append("</pmluid:ID>");
            pml.append(separator);
            pml.append("<pmlcore:Observation>");
            pml.append(separator);
            pml.append("<pmlcore:DateTime>")
                .append(DateUtil.convertToAIIDateFormat(new Date()))
                .append("</pmlcore:DateTime>");
            pml.append(separator);
            pml.append("<pmlcore:Command>").append(command).append("</pmlcore:Command>");
        }

        Device device;

        for (int i = 1; i < tagList.size(); i++) {
            // see if printer is running
            device = DeviceList.getDeviceMap().get(printerID);

            if (device == null) {
                // device doesn't exist or it stopped
                status = 500;
                break;
            }

            try {
                // try to write tag
                isSucceed = device.writeTag(null, tagList.get(i));
            } catch (Exception e) {
                status = 500;
                break;
            }

            if (po != null) {
                // update pml
                pml.append(separator).append("<pmlcore:Tag>").append(separator);
                pml.append("<pmluid:ID>").append(tagList.get(i)).append("</pmluid:ID>");
                pml.append(separator).append("<pmlcore:Data><pmlcore:XML>");
                pml.append("<EPCStatus>").append(isSucceed ? "WS" : "WU").append("</EPCStatus>");
                pml.append("</pmlcore:XML></pmlcore:Data>");
                pml.append(separator).append("</pmlcore:Tag>");
            }
        }

        if (po != null) {
            // complete whole PML
            pml.append(separator).append("<pmlcore:Data><pmlcore:XML><ReaderID>");
            pml.append(printerID).append("</ReaderID></pmlcore:XML></pmlcore:Data>");
            pml.append(separator).append("</pmlcore:Observation>");
            pml.append(separator).append("</pmlcore:Sensor>");

            // send result PML back
            try {
                HttpXmlPost.postXml(po.getIp(), po.getPort(), po.getPath(),
                    po.getUserName(), po.getPassword(), pml.toString());
            } catch (IOException e) {
                SystemLogger.error("[HttpXmlPost] postXml() error: " + e.getMessage());
            }

        }

        // everything is fine, return OK status
        return status;

    }

    /**
     * Parse tag print PML.
     *
     * @param is
     * @return list of string
     */
    private ArrayList<String> xmlParse(String ip, InputStream is) {

        Document doc;
        StringBuffer buf = new StringBuffer();
        String content = null;

        try {
            // get the content in inputstream
            int ch;

            while ((ch = is.read()) != -1) {
                buf.append((char) ch);
            }

            content = buf.toString();

            doc = factory.newDocumentBuilder().parse(new InputSource(new StringReader(content)));
        } catch (Exception e) {
            SystemLogger.error("[HttpCmdParse] Parsing received PML from " + ip + " error: " + e.getMessage() + "\r\n"
                + ((content == null) ? "" : content));
            return null;
        }

        ArrayList<String> list = new ArrayList<String>();

        NodeList tagData = doc.getElementsByTagName("WriteTagData");

        if (tagData == null) {
            return null;
        }

        //get readerID
        String readerID = ((Element) tagData.item(0)).getAttribute("readerID");

        if (readerID == null) {
            return null;
        }

        // the first element in list is reader id
        list.add(readerID);

        NodeList items = doc.getElementsByTagName("Field");

        if (items == null) {
            return null;
        }

        Element element;

        for (int i = 0; i < items.getLength(); i++) {
            element = (Element) items.item(i);

            if ("EPC".equalsIgnoreCase(element.getAttribute("name"))) {
                list.add(element.getFirstChild().getNodeValue());

                // ignore the remaining nodes
                continue;
            }
        }

        return list;

    }

}
