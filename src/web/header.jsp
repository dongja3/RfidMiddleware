<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%
    HttpSession s = request.getSession();
   
    if (request.isUserInRole("Administrator") && s.getAttribute("isAdministrator")==null) {
        s.setAttribute("isAdministrator", "yes");
    } else if (request.isUserInRole("Operator") && s.getAttribute("isOperator")==null) {
        s.setAttribute("isOperator", "yes");
    } else if (request.isUserInRole("Viewer") && s.getAttribute("isViewer")==null) {
        s.setAttribute("isViewer", "yes");
    }
%>

<fmt:bundle basename="middleware">
<script>
function gotoPage(index){
	if(index == ""){
	   return false;
	} else {
		window.location=index;
	}
}

	var backg = Array(5);
	backgroup = "tab"
	backg[0] = backgroup
	backg[1] = backgroup
	backg[2] = backgroup
	backg[3] = backgroup
	backg[4] = backgroup

	var link = Array(5);
	link[0] = '<a class=\"whitebold\" href=\"/middleware/SysConfigAction.do\" ><fmt:message key="menu.server.sysconfig" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"submenu\" href=\"/middleware/ListHttpCommandReplyAction.do\" ><fmt:message key="menu.server.httpcommandreply" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a  class=\"submenu\"  href=\"/middleware/LicenceMgt.do\"><fmt:message key="menu.server.licesekeymanagement" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
    link[1] = '<a class=\"submenu\" href=\"/middleware/SysConfigAction.do\" ><fmt:message key="menu.server.sysconfig" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"whitebold\" href=\"/middleware/ListHttpCommandReplyAction.do\" ><fmt:message key="menu.server.httpcommandreply" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"submenu\" href=\"/middleware/LicenceMgt.do\" ><fmt:message key="menu.server.licesekeymanagement" /></font>&nbsp;&nbsp;&nbsp;&nbsp;';
    link[2] = '<a class=\"submenu\" href=\"/middleware/SysConfigAction.do\" ><fmt:message key="menu.server.sysconfig" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"submenu\" href=\"/middleware/ListHttpCommandReplyAction.do\" ><fmt:message key="menu.server.httpcommandreply" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a  class=\"whitebold\"  href=\"/middleware/LicenceMgt.do\"><fmt:message key="menu.server.licesekeymanagement" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
	
	<c:choose>
		<c:when test="${isAdministrator=='yes'}">
			link[3] = '<a class=\"whitebold\" href=\"/middleware/ListDevicesAction.do\" ><fmt:message key="menu.device.device" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"submenu\" href=\"/middleware/ListDeviceGroupsAction.do\" ><fmt:message key="menu.device.devicegroup" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
	    </c:when>
    	<c:otherwise>
			link[3] = '<a class=\"whitebold\" href=\"/middleware/ListDevicesAction.do\" ><fmt:message key="menu.device.device" /></a> &nbsp;&nbsp;&nbsp;&nbsp;';
	    </c:otherwise>
    </c:choose>
	
	link[4] = '<a class=\"submenu\" href=\"/middleware/ListDevicesAction.do\" ><fmt:message key="menu.device.device" /></a> &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"whitebold\" href=\"/middleware/ListDeviceGroupsAction.do\" ><fmt:message key="menu.device.devicegroup" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
	link[5] = '<a class=\"whitebold\" href=\"/middleware/ListUserAction.do\"  ><fmt:message key="menu.user.user" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"submenu\" href=\"/middleware/users/ChangePwd.jsp" ><fmt:message key="menu.user.changepassword" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
	    
   	<c:choose>
		<c:when test="${isAdministrator=='yes'}">
			link[6] = '<a class=\"submenu\" href=\"/middleware/ListUserAction.do\" ><fmt:message key="menu.user.user"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"whitebold\" href=\"/middleware/users/ChangePwd.jsp" ><fmt:message key="menu.user.changepassword" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
	    </c:when>
    	<c:otherwise>
			link[6] = '<a class=\"whitebold\" href=\"/middleware/users/ChangePwd.jsp\" ><fmt:message key="menu.user.changepassword" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
	    </c:otherwise>
    </c:choose>
    
	link[7] = '<a class=\"whitebold\" href=\"/middleware/ListTaskAction.do\" ><fmt:message key="menu.task.task" /></a>&nbsp;&nbsp;&nbsp;&nbsp;';
	link[8] = '';

function background(index){
	backg[index] = "tabsel"
	<c:if test="${isAdministrator=='yes'}">
		m1.className=backg[0];
	</c:if>
	m2.className   =backg[1];m3.className   =backg[2];
	<c:if test="${isViewer!='yes'}">
		m4.className   =backg[3];
	</c:if>
}

function showMenu(index1,index2){
	background(index1);

	document.getElementById("oneLink").style.visibility = "visible";
	document.getElementById("oneLink").innerHTML = link[index2];
}

function logout(){
	if(confirm("<fmt:message key='logout.prompt' />")){
		window.location="/middleware/LogOutAction.do";
	}else{
		return false;
	}
}

function init(index1,index2){
	showMenu(index1,index2)
}

</script>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#336699">
  <tr>
    <td width="150" background="/middleware/images/tab.bg.dln.gif" valign="top"><a href="http://www.ubipass.com" target="_blank" ><img src="/middleware/images/banner.gif" width="150" height="67" border="0"></a></td>
    <td background="/middleware/images/tab.bg.dln.gif" valign="middle"><div align="left"><img src="/middleware/images/autoid.gif" width="200" height="24"></div></td>
    <td width="109" background="/middleware/images/tab.bg.dln.gif"><div align="right"><img src="/middleware/images/tab.slide.hm.li.gif" width="109" height="88"></div></td>
    
    <c:if test="${isAdministrator=='yes'}">
		 <td width="300" background="/middleware/images/tab.bg.rt.gif" valign="bottom" >
	</c:if>
	
	<c:if test="${isOperator=='yes'}">
		 <td width="240" background="/middleware/images/tab.bg.rt.gif" valign="bottom" >
	</c:if>
	
	<c:if test="${isViewer=='yes'}">
		 <td width="180" background="/middleware/images/tab.bg.rt.gif" valign="bottom" >
	</c:if>
   
<TABLE class=tabtable id=Table4 cellSpacing=0 cellPadding=0  border=0>
        <TBODY>
        <TR>
          <TD class=tabspace colSpan=3 height=10>&nbsp;</TD></TR>
        <TR>

		 <c:if test="${isAdministrator=='yes'}">
		  <TD nowrap id="m1" class=tab  align=middle  height=35 width=60><a class="menu" href="#" onclick="gotoPage('/middleware/SysConfigAction.do');return false;" >&nbsp;<span id='t1'>&nbsp;<fmt:message key="menu.server"/>&nbsp;</span>&nbsp;</a>
			</TD>
		 </c:if>
		 
          <TD nowrap id="m2" class=tab  align=middle height=35 width=60><a class="menu" href="#" onclick="gotoPage('/middleware/ListDevicesAction.do');return false;" style="cursor:hand" >&nbsp;<span id='t2'>&nbsp;<fmt:message key="menu.device"/>&nbsp;</span>&nbsp;</a>
          </TD>
          
          <c:choose>
          <c:when test="${isAdministrator!='yes'}">
          	<TD nowrap id="m3" class=tab  align=middle height=35 width=60><a class="menu" href="#" onclick="gotoPage('/middleware/users/ChangePwd.jsp');return false;" style="cursor:hand">&nbsp;<span id='t3'>&nbsp;<fmt:message key="menu.user"/>&nbsp;</span>&nbsp;</a>
          </c:when>
          <c:otherwise>
          	<TD nowrap id="m3" class=tab  align=middle height=35 width=60><a class="menu" href="#" onclick="gotoPage('/middleware/ListUserAction.do');return false;" style="cursor:hand">&nbsp;<span id='t3'>&nbsp;<fmt:message key="menu.user"/>&nbsp;</span>&nbsp;</a>
          </c:otherwise>
          </c:choose>
			</TD>
			
		  <c:if test="${isViewer!='yes'}">
		  <TD nowrap id="m4" class=tab  align=middle height=35 width=60><a class="menu" href="#" onclick="gotoPage('/middleware/ListTaskAction.do');return false;" style="cursor:hand">&nbsp;<span id='t4' >&nbsp;<fmt:message key="menu.task"/>&nbsp;</span>&nbsp;</a>
			</TD>
		  </c:if>	
		  <TD nowrap id="m5" class=tab  align=middle height=35 width=60><a class="menu" href="#" onclick="logout();return false;" style="cursor:hand" >&nbsp;&nbsp;<fmt:message key="menu.top.logout"/>&nbsp;</a>
			</TD>
			
			
		</TR></TBODY></TABLE>
	

	</td>
  </tr>
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="/middleware/images/subbg.gif" height="25"  align="right">	
	
	<span align="right" class="bodytxt" id="oneLink" style="visibility:visible">&nbsp;&nbsp;
			</span>
			
			</td>
  </tr>
</table>
</body>

</fmt:bundle>