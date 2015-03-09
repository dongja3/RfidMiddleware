<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="middleware">
  <html:base />
  <html>
    <head>
      <title>
        <fmt:message key="login.title" />
      </title>
      <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
      <link href="/middleware/css/css.css" rel="stylesheet" type="text/css">
    </head>
    <script>
      function Submit() {
        if (isEmpty(document.forms[0].j_password.value)) {
          alert("<fmt:message key='javascript.login.password.isempty' />");

          return false;
        }

        if (isEmpty(document.forms[0].j_username.value)) {
          alert("<fmt:message key='javascript.login.username.isempty' />");

          return false;
        }

        document.forms[0].submit();
      }

      function Reset() {
        document.forms[0].reset();
        document.forms[0].j_username.focus();
      }

      function isEmpty(value) {
        //lable is form's text
        if (value == "") {
          return true;
        } else {
          return false;
        }
      }
    </script>
    <body>
      <p>
        &nbsp;
      </p>
      <p>
        &nbsp;
      </p>
      <form method="POST" action='<%= response.encodeURL("j_security_check") %>' name="loginForm">
        <table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td colspan="3" height=1 bgcolor="#cccccc"></td>
          </tr>
          <tr>
            <td width="1" bgcolor="#cccccc"></td>
            <td>
              <table width="100%" border="0" cellspacing="2" cellpadding="2">
                <tr>
                  <td>
                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                      <tr>
                        <td height="35" colspan="2">
                          <span class="section">
                            <fmt:message key="login.middlewareName" />
                            <br>
                            <br>
                          </span>
                        </td>
                      </tr>
                      <tr>
                        <td width="78%" colspan="2">
                          <b><font color="red"><fmt:message key="login.errorusername.password" /></font>
                          </b>
                        </td>
                      </tr>
                      <tr>
                        <td height="27" nowarp>
                          <fmt:message key="login.username" />
                        </td>
                        <td align="right">
                          <input type="text" name="j_username" size=20 maxlength=100 class="field" id="username" onkeydown="if (event.keyCode==13) Submit();">
                        </td>
                        <td width="15%">
                          &nbsp;
                        </td>
                      </tr>
                      <tr>
                        <td width="22%" height="10"></td>
                        <td width="78%"></td>
                      </tr>
                      <tr>
                        <td height="27" nowrap>
                          <fmt:message key="login.password" />
                        </td>
                        <td align="right">
                          <input type="password" name="j_password" size=22 maxlength=100 class="field" id="password" onkeydown="if (event.keyCode==13) Submit();">
                        </td>
                        <td width="15%">
                          &nbsp;
                        </td>
                      </tr>
                      <tr>
                        <td width="22%" height="10"></td>
                        <td width="78%"></td>
                      </tr>
                      <tr>
                        <td height="25" colspan="2" align="center">
                          <table>
                            <tr align="center">
                              <td width="50" height="21" background="/middleware/images/button_3.jpg" align="center">
                                <a onclick="Submit();" style="cursor:hand"><fmt:message key="login.login"/></a>
                              </td>
                              <td>
                                &nbsp;
                              </td>
                              <td width="50" height="21" background="/middleware/images/button_3.jpg" align="center">
                                <a onclick="Reset();" style="cursor:hand"><fmt:message key="login.reset"/></a>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td width="350">
                    <img src="/middleware/images/middleware.jpg" width="350" height="446">
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
      </form>
    </body>
  </html>
</fmt:bundle>
<script>
  document.forms[0].j_username.focus();
</script>
