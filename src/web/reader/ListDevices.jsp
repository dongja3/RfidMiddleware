<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
<title><fmt:message key="listDevice.title" /></title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="../js/web.js"></script>
<script>

//goto page
function gotoExecuteCommand(index,value){
	if(index == ""){
	    alert("No value");
	} else {
		if (value == 'start'){
			if (!confirm("<fmt:message key='javascript.device.prompt.start' />")) {
				return;
			}
		} else {
			if (!confirm("<fmt:message key='javascript.device.prompt.stop' />")) {
				return;
			}
		}

		process.style.visibility = 'visible';

		if (value == 'start') {
			document.frames['processbar'].document.getElementById("abc").innerHTML = "<fmt:message key='device.starting' />";
		} else {
			document.frames['processbar'].document.getElementById("abc").innerHTML = "<fmt:message key='device.stoping' />";
		}

		window.location=index;
	}
}

function openPage(value){
	var x = (window.screen.width - 640) / 2;
	var y = (window.screen.height - 660) / 2 - 50;

    if (y < 10) {
        y = 10;
    }
    
	cc = window.open(value, null, "left=" + x + ",top=" + y + ",height=665,width=640,resizable=no,status=no,toolbar=no,menubar=no", true);
	cc.focus();
}

</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(1,3)">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>	
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td  valign="top"> <p></p>
      <p>&nbsp;</p> <p><br>
      </p></td>
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
     <td  align="center" valign="top"> <p><br>
        <br>
        <br>
        <br>
        <br>
        <img src="../images/image_1.jpg" width="102" height="101"><br>
        </p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <span class="bodytxt"><font color="#333333"></font></span> 
      <p>&nbsp;</p>
    </td>
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td  align="left" valign="top" width="80%"> 
      <table width="96% border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td  align="center" valign="top"> <div class="bodyhdr"> 
              <div align="right"> 
                <p><span class="section"><fmt:message key="listDevice.title" /> </span>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:choose>
					<c:when test="${isAdministrator == 'yes'}">
						<select class="field" onChange="gotoPage(this.value)" >
							<option value="" selected><fmt:message key="form.AvailableActions"/></option>
		        	      	<option value="/middleware/ListDevicesAction.do" ><fmt:message key="device.action.list" /></option>
		            		<option value="/middleware/AddDeviceAction.do"><fmt:message key="device.action.create" /></option>
    				        <option value="/middleware/ListDevicesAction.do?userOperation=delete"><fmt:message key="device.action.delete" /></option>
    			    	</select>
				   </c:when>
				   <c:otherwise>
				   		
				   </c:otherwise>
			   </c:choose>


             
                <hr width="100%" size="1" noshade></p>
              </div>
            </div></td>
        </tr>
        <tr> 
          <td  align="center" valign="top"> <div align="left">
            <p><span class="bodytxt">
             <font color="red">
                 <html:messages id="message" message="true">
                      <li><c:out value="${message}" /></li>
                 </html:messages>
			</font>
                </span>
              </p>
            <br>
            <br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                  <tr >
					  <td class="bg-gray" nowrap><fmt:message key="device.onecell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.twocell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.threecell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.fourcell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.fivecell.title" /></td>
					  <td class="bg-gray" width="30" nowrap align="left"><fmt:message key="device.sixcell.title" /></td>
					  <td class="bg-gray" align="left"></td>
                  </tr>
			<c:forEach var="devicePo" items="${readers}"  varStatus="index" >
			<c:if test="${index.count%2==1}">
				<tr onmouseover="onover(this)"   onmouseout="onout(this)" class="bg-shallow" height="27">
			</c:if>
			<c:if test="${index.count%2==0}">
				<tr onmouseover="onover(this)"   onmouseout="onout(this)" class="bg-shallow1" height="27">
			</c:if>
            <td nowrap>
			<c:choose>
	        	<c:when test="${not devicePo.status && isAdministrator == 'yes'}">
	            	<a href="/middleware/AddDeviceAction.do?deviceId=<c:out value='${devicePo.id}' />" ><c:out value="${devicePo.deviceName}" /></a>
            	</c:when>
	            <c:otherwise>
	            	<c:out value="${devicePo.deviceName}" />
	            </c:otherwise>
            </c:choose>
            </td>
            <td nowrap><c:out value="${devicePo.deviceID}" /></td>
			<td align="left" nowrap>
	        <c:choose>
			<c:when test="${devicePo.status}">
	    	 	<FONT COLOR="#00CC00"><B><fmt:message key="device.status.started" /></B></FONT>
	        </c:when>
	        <c:otherwise>
			   	<FONT COLOR="#FF0000"><B><fmt:message key="device.status.stopped" /></B></FONT>
		    </c:otherwise>
			</c:choose>
              </td>
              <td nowrap>
              <c:choose>
	              <c:when test="${devicePo.startup=='A'}">
	             	<fmt:message key="addDevice.automatic" />
	              </c:when>
	              <c:otherwise>
	              	<fmt:message key="addDevice.manual" />
	              </c:otherwise>
              </c:choose>
              </td>
              <td nowrap>
			 <c:choose>
              <c:when test="${devicePo.deviceType=='P'}">
				Printer
			  </c:when>
			  <c:otherwise>
				Reader
			  </c:otherwise>
			  </c:choose>
				</td>
              <td nowrap><c:out value="${devicePo.description}" /></td>
              <td align="left" nowrap>
	            <c:if test="${isViewer != 'yes'}">
	             &nbsp;&nbsp;
    	         <c:choose>
              		<c:when test="${not devicePo.status}">
           	      		<img src="../images/started.gif" alt="<fmt:message key="device.StartDevice"/>" onmouseover="this.style.cursor='hand'"  onclick="gotoExecuteCommand('/middleware/ExecuteDevice.do?deviceID=${devicePo.id}&Action=start','start')">
               		</c:when>
	               	<c:otherwise>	
    	             	<img src="../images/stopped.gif" alt="<fmt:message key="device.StopDevice"/>" onmouseover="this.style.cursor='hand'" onclick="gotoExecuteCommand('/middleware/ExecuteDevice.do?deviceID=${devicePo.id}&Action=stop','stop')">
              		</c:otherwise>
        			</c:choose>
	            </c:if>
  				<c:if test="${devicePo.deviceType == 'R' and devicePo.status}">
			    &nbsp;&nbsp;<img src="../images/monitorDevice.gif" alt="<fmt:message key="monitorDevice.title"/>" width="22" height="22" onmouseover="this.style.cursor='hand'" onclick="openPage('/middleware/MonitorDeviceAction.do?deviceId=${devicePo.id}&clear=y')">
                </c:if>
                <c:if test="${devicePo.readerViewEPC and devicePo.deviceType == 'R' and devicePo.status}">
					&nbsp;&nbsp;<img src="../images/viewTag.gif" alt="<fmt:message key="device.ViewTag"/>" width="22" height="22" onmouseover="this.style.cursor='hand'" onclick="gotoPage('/middleware/ViewEPCAction.do?deviceId=${devicePo.id}')">
                </c:if>
	            <c:if test="${devicePo.printerViewEPC and isViewer != 'yes' and devicePo.deviceType == 'P' and devicePo.status}">
					&nbsp;&nbsp;<img src="../images/printTag.gif" alt="<fmt:message key="device.PrintTag"/>" width="22" height="22" onmouseover="this.style.cursor='hand'" onclick="gotoPage('/middleware/TagPrint.do?deviceId=${devicePo.deviceID}')">
				</c:if>
				
              </td>
            </tr>
       		</c:forEach>
			</table> 
				<!--end list of reader content-->
				</td>
                <td width="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td colspan="3" height="1" bgcolor="#cccccc"></td>
              </tr>
            </table>
            <p>&nbsp;</p>
          </div></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</td></tr><tr><td>
<%@ include file="/foot.jsp" %>
</td><tr></table>
</body>
</html>
</fmt:bundle>
<div id="process"  style="position:absolute; left:331px; top:198px; width:337px; height:185px; z-index:1;visibility: hidden ">
<iframe name="processbar" src="ProcessBar.htm" SCROLLING="no"  style="width:337px; height:185px;"></iframe>
</div>