<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<fmt:bundle basename="middleware">
  <html>
    <head>
      <title>
        <fmt:message key="error.message" />
      </title>
      <link href="./css/css.css" rel="stylesheet" type="text/css">
    </head>
    <%@ include file="header.jsp" %>
    <body onload="init(<c:out value="${page}"/>)">
      <ul>
        <font color='red'>
          <html:messages id="message" message="true">
            <li>
              <%= message %>
            </li>
          </html:messages>
          <html:errors/>
        </font>
      </ul>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
      <br>
    </body>
  </html>
  <%@ include file="foot.jsp" %>
</fmt:bundle>
