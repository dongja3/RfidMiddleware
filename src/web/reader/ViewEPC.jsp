<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.ubipass.middleware.plugin.Tag" %>
<fmt:bundle basename="middleware">
  <html>
    <head>
      <title>
        <fmt:message key="viewEPC.title" />
      </title>
      <link href="./css/css.css" rel="stylesheet" type="text/css">
      <script src="./js/web.js"></script>
    </head>
    <body onload="init(1,3)">
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td>
            <jsp:include page="../header.jsp" />
          </td>
        </tr>
        <tr>
          <td>
            <br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="top">
                  <p></p>
                  <p>
                    &nbsp;
                  </p>
                  <p>
                    <br>
                  </p>
                </td>
                <td align="center" valign="top">
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td align="center" valign="top">
                  <p>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <br>
                    <img src="./images/image_1.jpg" width="102" height="101">
                    <br>
                  </p>
                  <p>
                    &nbsp;
                  </p>
                  <p>
                    &nbsp;
                  </p>
                  <span class="bodytxt">
                    <font color="#333333"></font>
                  </span>
                  <p>
                    &nbsp;
                  </p>
                </td>
                <td align="center" valign="top">
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td align="left" width="80%">
                  <table width="96%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="center" valign="top">
                        <div class="bodyhdr">
                          <div align="right">
                            <p>
                              <span class="section">
                                <fmt:message key="viewEPC.title" />
                              </span>
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                              <select class="field" onchange="gotoPage(this.value)">
                                <option value="" selected>
                                <fmt:message key="form.AvailableActions"/>
                                </option>
                                <option value="/middleware/ListDevicesAction.do">
                                <fmt:message key="device.action.list" />
                                </option>
                                <c:if test="${isAdministrator == 'yes'}">
                                  <option value="/middleware/AddDeviceAction.do">
                                  <fmt:message key="device.action.create" />
                                  </option>
                                  <option value="/middleware/ListDevicesAction.do?userOperation=delete">
                                  <fmt:message key="device.action.delete" />
                                  </option>
                                </c:if>
                              </select>
                              <hr width="100%" size="1" noshade>
                            </p>
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td nowrap align="center" valign="top" class="bg-shallow">
                      <span class="bodytxt">
                        <br>
                        <b><fmt:message key="viewEPC.deviceName" />:&nbsp;&nbsp;</b>
                        <c:out value="${device.deviceName}" />
                        <c:if test="${notRun}">
                          <span class="red">
                            (
                            <fmt:message key="viewEPC.deviceNotRuning"/>
                            )
                          </span>
                        </c:if>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <b><fmt:message key="viewEPC.deviceID" />:&nbsp;</b>
                        &nbsp;
                        <c:out value="${device.deviceID}" />
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <jsp:useBean id="date" class="java.util.Date" />
                        <b><fmt:message key="viewEPC.viewTime" />:</b>
                        &nbsp;
                        <fmt:formatDate type="both" value="${date}" pattern="yyyy/MM/dd HH:mm:ss" />
                        <br>
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td align="center" valign="top">
                        <div align="center">
                          <p>
                            <span class="bodytxt">
                              &nbsp;&nbsp;
                              <br>
                            </span>
                          </p>
                          <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td colspan="3" height=1 bgcolor="#cccccc"></td>
                            </tr>
                            <tr>
                              <td width="1" bgcolor="#cccccc"></td>
                              <td width="400">
                                <table width="562" border="0" cellspacing="2" cellpadding="2">
                                  <c:choose>
                                    <c:when test="${tag!=null}">
                                      <tr>
                                        <td class="bg-gray" width="30" align="center" nowrap>
                                          <fmt:message key="viewEPC.Number" />
                                        </td>
                                        <td class="bg-gray" align="center" width="270" nowrap>
                                          <fmt:message key="viewEPC.TagID" />
                                        </td>
                                        <td class="bg-gray" width="220" align="center" nowrap>
                                          <fmt:message key="viewEPC.ReadTime" />
                                        </td>
                                      </tr>
                                    </c:when>
                                    <c:otherwise>
                                      <tr>
                                        <td class="bg-gray" colspan="3">
                                          <font color="red">
                                            <fmt:message key="viewEPC.notFound"/>
                                          </font>
                                        </td>
                                      </tr>
                                    </c:otherwise>
                                  </c:choose>
<%
                                  SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                                  if (request.getAttribute("tag") != null) {
                                    Tag[] tag = (Tag[])request.getAttribute("tag");

                                    for (int i = 0; i < tag.length; i++) {
                                      Tag tagView = (Tag)tag[i];

                                      if (i % 2 == 0) {
%>
                                        <tr onmouseover="onover(this)" onmouseout="onout(this)" class="bg-shallow">
<%
                                        } else {
%>
                                          <tr onmouseover="onover(this)" onmouseout="onout(this)" class="bg-shallow1">
<%
                                          }
%>
<%
                                          int j = i + 1;
%>
                                          <td  align="right" style="font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px"> 
                                              <%=j%> &nbsp;
                                          </td>
                                          <td  align="center" nowrap style="font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px">  
                                              <%= tagView.getTagID() %>                                
                                          </td>
                                          <td  align="center" nowrap style="font-family: Lucida Console,Lucida Sans Unicode; font-size: 13px">                                           
<%
                                              Date date1 = new Date(tagView.getFirstSeenTime());
%>
                                              <%= format.format(date1) %>
                                          </td>
                                        </tr>
<%
                                      }
                                    }
%>
                                  </table>
                                </td>
                                <td width="1" bgcolor="#cccccc"></td>
                              </tr>
                              <tr>
                                <td colspan="3" height=2 bgcolor="#cccccc"></td>
                              </tr>
                            </table>
                            <p>
                              &nbsp;
                            </p>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td>
              <%@ include file="/foot.jsp" %>
            </td>
            <tr>
            </table>
          </fmt:bundle>
        </body>
      </html>
