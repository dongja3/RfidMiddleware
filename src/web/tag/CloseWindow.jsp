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
      <script>
        <c:if test="${deviceName!=''}">
          alert("'<c:out value="${deviceName}"/>' <fmt:message key="task.js.alertstartdevice"/>");
          window.close();
        </c:if>
      </script>
    </body>
  </html>
</fmt:bundle>
