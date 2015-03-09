<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<fmt:bundle basename="middleware">
  <html:base/>
  <html>
    <head></head>
    <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%
      if (request.getParameter("deviceID") != null && !request.getParameter("deviceID").equals("")) {
        String      deviceID  = request.getParameter("deviceID");
        String      sessionID = request.getRequestedSessionId();
        HttpSession s         = request.getSession();

        if (s.getAttribute(deviceID + "|startId|" + sessionID) != null && !s.getAttribute(deviceID + "|startId|" + sessionID)
                .equals("")) {
          s.removeAttribute(deviceID + "|startId|" + sessionID);
        }

        if (s.getAttribute(deviceID + "|" + sessionID) != null || !s.getAttribute(deviceID + "|" + sessionID).equals("")) {
          s.removeAttribute(deviceID + "|" + sessionID);
        }

        out.println("<script>window.close();</script>");
      } else {
        out.println("Illegal DeviceId,pelase close the window and try again.");
      }
%>
    </body>
  </html>
</fmt:bundle>
