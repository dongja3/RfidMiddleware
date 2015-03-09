<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
  <html>
    <title>
      <fmt:message key="task.js.manualpascking"/>
    </title>
    <link href="./css/css.css" rel="stylesheet" type="text/css">
    <script src="./js/web.js"></script>
    <script>
      function clearTag() {
        if (confirm("<fmt:message key='javascript.clear' />")) {
          window.location = "/middleware/PackageAction.do?taskId=" + document.forms[1].taskId.value + "&clear=y";
        } else {
          return;
        }
      }
      
      function refresh(){
		window.location = "/middleware/PackageAction.do?taskId="+document.forms[1].taskId.value;
	}

      function closeW() {
        if (confirm("<fmt:message key='javascript.close' />")) {
          window.location = "./tag/CloseManualPack.jsp?taskId=" + document.forms[1].taskId.value;
        } else {
          return;
        }
      }

      function manualSubmit() {
        document.forms[1].endId.value = document.forms[0].endIDPacking.value;
        document.frames("packing").document.body.innerHTML = document.frames("mainFrame").document.body.innerHTML;
        document.all("mainFrame").width = "0";
        document.all("mainFrame").height = "0";
        document.all("packing").height = "495";
        document.all("packing").width = "560";

        if (document.forms[1].startId.value == "") {
          alert("<fmt:message key='package.empty' />");
           refresh();

          return false;
        }

        if (document.forms[1].endId.value == "") {
          alert("<fmt:message key='package.empty' />");
           refresh();
          return false;
        }

        if (document.forms[1].taskId.value == "") {
          alert("<fmt:message key='package.empty' />");
           refresh();
          return false;
        }

        document.frames["packing"].scrollTo(0, 99999999999);

        if (confirm("<fmt:message key='task.js.doyouwantpackage' />?")) {
          document.forms[1].submit();

          window.location = "/middleware/PackageAction.do?taskId=" + document.forms[1].taskId.value + "&clear=y&startId=" + 
                  document.forms[1].endId.value;
        } else {
          document.all("packing").height = "0";
          document.all("packing").width = "0";
          document.all("mainFrame").width = "560";
          document.all("mainFrame").height = "495";

          return false;
        }
      }
    </script>
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
                            <input type="hidden" name='endIDPacking' />
                          </form>
                          <center><table><form name="package" action="/middleware/SavePackageAction.do" method="GET" target="hiddenPackingeInfo"><input type="hidden" name="startId" value=""><input type="hidden" name="endId" value=""><input type="hidden" name="taskId" value="${taskId}"><tr align="center" class="bg-shallow"><td width="50" height="21" background="/middleware/images/button_3.jpg" align="center"><a onclick="manualSubmit();" style="cursor:hand"><fmt:message key="pack" /></a>
                                </td>
                                <td width="50" height="21">
                                  &nbsp;
                                </td>
                                <td width="50" height="21" background="/middleware/images/button_3.jpg" align="center">
                                  <a onclick="clearTag();" style="cursor:hand"><fmt:message key="clear" /></a>
                                </td>
                                <td width="50" height="21">
                                  &nbsp;
                                </td>
                                <td width="50" height="21" background="/middleware/images/button_3.jpg" align="center">
                                  <a onclick="closeW();" style="cursor:hand"><fmt:message key="close" /></a>
                                </td>
                              </tr>
                            </form>
                          </table>
                          </center></span>
                        <center><hr width="400" size="1" noshade></center>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <td align="center" valign="top">
                    <div align="left">
                      <p>
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
                          <c:out value="${packageViewTime}" />
                        </span>
                      </p>
                      <table width="562" border="0" cellspacing="0" cellpadding="0" align="center">
                        <tr>
                          <td>
                            <table border="0" cellspacing="2" cellpadding="2">
                              <tr>
                                <td colspan="3" width="470"></td>
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
                                <td class="bg-gray" colspan="3" height="300">
                                  <span style=" width: 100%; height: 100%;">
                                    <iframe src="about:blank" name="mainFrame" marginheight=0 marginwidth=0 frameborder=0 width=560 height="495">
                                      <iframe src="about:blank" name="packing" marginheight=0 marginwidth=0 frameborder=0 width=0 height="0">
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
            <iframe src="/middleware/GetTagListServlet?clear=<c:out value='${clear}' />&taskId=<c:out value='${taskId}' />&startId=<c:out value='${startId}' />" name="topFrame" scrolling="NO" width="0" height=0 noresize>
              <iframe src="about:blank" name="hiddenPackingeInfo" scrolling="NO" width="0" height=0 noresize>
              </body>
              <html>
              </fmt:bundle>
