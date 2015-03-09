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
      if (request.getParameter("taskId") != null && !request.getParameter("taskId").equals("")) {
        String      taskId     = request.getParameter("taskId");
        String      sessionID  = request.getRequestedSessionId();
        HttpSession s          = request.getSession();
        String      attribute  = taskId + "|MStartID|" + sessionID;
        String      attributeM = taskId + "|M|" + sessionID;

        if (s.getAttribute(attribute) != null && !s.getAttribute(attribute).equals("")) {
          s.removeAttribute(attribute);
        }

        if (s.getAttribute(attributeM) != null || !s.getAttribute(attributeM).equals("")) {
          s.removeAttribute(attributeM);
        }

        out.println("<script>window.close();</script>");
      }
%>
    </body>
  </html>
</fmt:bundle>
