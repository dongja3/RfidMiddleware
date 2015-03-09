<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
  <html>
    <head>
      <title>
        <fmt:message key="monitorDevice.title"/>
      </title>
      <link href="./css/css.css" rel="stylesheet" type="text/css">
      <script src="./js/web.js"></script>
      <script>
        function clearTag() {
          if (confirm("<fmt:message key='javascript.clear' />")) {
            parent.location = "/middleware/MonitorDeviceAction.do?deviceId=" + document.forms[0].deviceId.value + "&clear=y";
          } else {
            return;
          }
        }

        function closeW() {
          if (confirm("<fmt:message key='javascript.closeMonitorDevice' />")) {
            parent.location = "./reader/CloseMonitor.jsp?deviceID=" + document.forms[0].deviceID.value;
          } else {
            return;
          }
        }
      </script>
    </head>
    <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" oncontextmenu="return false" ondragstart="return false" onselectstart="return false" onselect="document.selection.empty()" oncopy="document.selection.empty()" onbeforecopy="return false" onmouseup="document.selection.empty()">
      <table border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
          <td align="left" valign="top" width="100%">
            <table border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td align="center" valign="top">
                  <div class="bodyhdr">
                    <div align="right">
                      <p>
                        <br>
                        &nbsp;
                        <span class="section">
                          <form action="#">
                            <input type="hidden" name="deviceId" value="<c:out value='${deviceId}' />">
                            <input type="hidden" name="deviceID" value="<c:out value='${deviceID}' />">
                          </form>
                          <center><table><tr align="center" class="bg-shallow"><td width="50" height="21" background="/middleware/images/button_3.jpg" align="center"><a onclick="clearTag();" style="cursor:hand"><fmt:message key='clear' /></a>
                              </td>
                              <td width="100" height="21">
                                &nbsp;
                              </td>
                              <td width="50" height="21" background="/middleware/images/button_3.jpg" align="center">
                                <a onclick="closeW();" style="cursor:hand"><fmt:message key='close' /></a>
                              </td>
                            </tr>
                          </table>
                          </center></span>
                        <center><hr width="400" size="1" noshade></center>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td align="center" valign="top">
                      <p align="left">
                        <span class="bodytxt">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          <br>
                          <b><fmt:message key="viewEPC.deviceName" />:&nbsp;&nbsp;</b>
                          <c:out value="${deviceName}" />
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          <b><fmt:message key="viewEPC.deviceID" />:&nbsp;&nbsp;</b>
                          <c:out value='${deviceID}' />
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          <b><fmt:message key="viewEPC.viewTime" />:</b>
                          </b> &nbsp;&nbsp;
                          <c:out value="${monitorViewTime}" />
                        </span>
                      </p>
                      <table width="562" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>
                            <table border="0" cellspacing="2" cellpadding="2" align="center">
                              <tr>
                                <td colspan="3"></td>
                              </tr>
                              <tr>
                                <td class="bg-gray" width="60" align="center" nowrap>
                                  <fmt:message key="viewEPC.Number" />
                                </td>
                                <td class="bg-gray" align="center" width="200" nowrap>
                                  <fmt:message key="viewEPC.TagID" />
                                </td>
                                <td class="bg-gray" width="200" align="center" nowrap>
                                  <fmt:message key="viewEPC.ReadTime" />
                                </td>
                              </tr>
                              <tr>
                                <td class="bg-gray" colspan="3" height="300"  align="center">
                                  <span style=" width: 100%; height: 100%;">
                                    <iframe src="about:blank" name="mainFrame" marginheight=0 marginwidth=0 frameborder=0 width=560 height="495">
                                    </span>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </div>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
          <iframe src="/middleware/MonitorDeviceServlet?deviceID=<c:out value='${deviceID}' />&clear=<c:out value='${clear}' />" name="topFrame" scrolling="NO" width="0" height=0 noresize>
          </body>
          <html>
          </fmt:bundle>
