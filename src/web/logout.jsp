<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="middleware">
  <html:base />
  <html>
    <head>
      <title>
        <fmt:message key="logout.thank" />
      </title>
      <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
      <link href="css/styles.css" rel="stylesheet" type="text/css">
      <link href="css/css.css" rel="stylesheet" type="text/css">
    </head>
    <body>
      <p>
        &nbsp;
      </p>
      <p>
        &nbsp;
      </p>
      <table border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="3" height=1 bgcolor="#cccccc"></td>
        </tr>
        <tr>
          <td width="1" bgcolor="#cccccc"></td>
          <td alien="center">
            <table width="598" border="0" cellspacing="2" cellpadding="2">
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="2" cellpadding="2">
                    <tr>
                      <td height="35" colspan="2" nowrap>
                        <span class="section" nowrap>
                          <fmt:message key="logout.thank" />
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td width="22%" height="10"></td>
                      <td width="78%"></td>
                    </tr>
                    <tr>
                      <td height="25" colspan="2" align="center">
                        <a href="index.jsp" style="cursor:hand"><fmt:message key="logout.gotologin" /></a>
                      </td>
                    </tr>
                  </table>
                </td>
                <td>
                  <img src="images/middleware.jpg" width="350" height="446">
                </td>
              </tr>
            </table>
          </td>
          <td width="1" bgcolor="#cccccc"></td>
        </tr>
        <tr>
          <td colspan="3" height=2 bgcolor="#cccccc"></td>
        </tr>
      </table>
    </body>
  </html>
</fmt:bundle>
