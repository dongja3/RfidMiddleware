<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<fmt:bundle basename="middleware">
  <html>
    <head>
      <title>
        Auto-ID Middleware
      </title>
    </head>
    <link href="./css/css.css" rel="stylesheet" type="text/css">
    <script src="./js/web.js"></script>
    <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(4,8)">
      <jsp:include page="header.jsp">
        <jsp:param name="index" value="1" />
      </jsp:include>
      <%@ include file="content.jsp" %>
      <br>
      <br>
      <%@ include file="foot.jsp" %>
    </body>
  </html>
</fmt:bundle>
