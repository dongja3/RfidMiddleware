<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<fmt:bundle basename="middleware">
  <html>
    <head>
      <title>
        <fmt:message key="noauthorization.error.title" />
      </title>
      <link href="./css/css.css" rel="stylesheet" type="text/css">
    </head>
<%
    if (request.isUserInRole("ExternalUser")) {
%>
      <script language="JavaScript">
        <!--
window.location="/middleware/LogOutAction.do";
//-->
        
      </script>
<%
    } else {
%>
      <body onload="init(4,8)">
        <jsp:include page="header.jsp">
          <jsp:param name="index" value="1" />
        </jsp:include>
        <br>
        <br>
        <br>
        <br>
        <font color="red">
          <h3>
            &nbsp;&nbsp;&nbsp;
            <fmt:message key="noauthorization.error" />
          </h3>
        </font>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <%@ include file="/foot.jsp" %>
<%
      }
%>
    </body>
  </html>
</fmt:bundle>
