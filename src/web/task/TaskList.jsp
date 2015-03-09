<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
<title><fmt:message key="task.ListTask"/></title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="../js/web.js"></script>
<script>

//goto page
function gotoExecuteCommand(index,value,oper){
	if (confirm(oper + "?")) {
		if(index == ""){
		    alert("No value");
		} else {
			process.style.visibility = 'visible';

			if (value=='start') {
			    document.frames['processbar'].document.getElementById("abc").innerHTML = '<fmt:message key="task.startPacking"/>';
			} else if (value == 'stop') {
				document.frames['processbar'].document.getElementById("abc").innerHTML = '<fmt:message key="task.stopPacking"/>';
			} else {
				document.frames['processbar'].document.getElementById("abc").innerHTML = '<fmt:message key="task.manualPacking"/>';
			}

			window.location=index;
		}
	} else {
		return false;
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
<DIV id=process 
style="Z-INDEX: 1; LEFT: 431px; VISIBILITY: hidden; WIDTH: 337px; POSITION: absolute; TOP: 48px; HEIGHT: 185px">
<iframe name="processbar" src="../reader/ProcessBar.htm" SCROLLING="no"  style="width:337px; height:185px;"></iframe>
</DIV>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(3,7)">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>	
	<br>  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
    	
	    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    	<td  align="center" valign="top">
    		<br><br><br><br><br>
	        <img src="../images/TaskLogo.jpg" width="102" height="101">
	        <br><br><br><br><br><br><br><br><br><br>
	    </td>
    	<td  align="center" valign="top" >&nbsp;&nbsp;&nbsp;</td>
    	<td  align="left" valign="top" width="80%"> 
      		<table width="96% border="0" cellspacing="0" cellpadding="0">
        		<tr> 
          			<td  align="center" valign="top" class="section">
          				<div class="bodyhdr"> 
			            <div align="right"> 
                		<p><span class="section"><fmt:message key="task.ListTask"/></span>

				<c:choose>
					<c:when test="${isAdministrator == 'yes'}">
						<select class="field" onChange="gotoPage(this.value)">
						    <option value="" selected><fmt:message key="form.AvailableActions"/></option>
        				    <option value="/middleware/ListTaskAction.do" ><fmt:message key="task.ListTask" /></option>
	    			        <option value="/middleware/EditTaskAction.do?action=create" ><fmt:message key="task.CreateTask" /></option>
    				        <option value="/middleware/ListTaskAction.do?operation=delete"><fmt:message key="task.DeleteTask" /></option>
       					</select>
				   </c:when>
			   </c:choose>
						<hr width="100%" size="1" noshade></p>
			            </div>
			            <div align="left">
			             <font color="red"><html:messages id="message" message="true">
                      <li><c:out value="${message}" /></li>
                 </html:messages></font>
			            </div><br>
					</td>
        		</tr>
        		<tr> 
          			<td align="left" valign="top">      
			            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            				<tr>
				                <td colspan="3" height="1" bgcolor="#cccccc"></td>
              				</tr>
							<tr>
				                <td width="1" bgcolor="#cccccc"></td>
                			<td>
				                <table width="100%"  border="0" cellspacing="2" cellpadding="2">
                					<tr class="bg-gray" >
								    	<td nowrap class="bg-gray"><fmt:message key="task.taskName" /></td>
								    	<td nowrap class="bg-gray"><fmt:message key="task.TaskType" /></td>
								    	<td nowrap class="bg-gray"><fmt:message key="task.Startup"/></td>
								    	<td nowrap class="bg-gray"><fmt:message key="task.TriggerMode" /></td>
								    	<td nowrap class="bg-gray"><fmt:message key="State" /></td>
								    	<td nowrap class="bg-gray"><fmt:message key="Description" /></td>
								    	<td nowrap class="bg-gray">&nbsp;</td>
									</tr>
									<c:forEach var="task" items="${taskList}"  varStatus="index" >
										<c:if test="${index.count%2==1}">
											<tr onmouseover="onover(this)"   onmouseout="onout(this)" class="bg-shallow">
										</c:if>
										<c:if test="${index.count%2==0}">
											<tr onmouseover="onover(this)"   onmouseout="onout(this)" class="bg-shallow1">
										</c:if>
            							<td nowrap>&nbsp;&nbsp;
										<c:choose>
										<c:when test="${task.state or isAdministrator !='yes'}">
											<c:out value="${task.taskName}"/>
										</c:when>
										<c:otherwise>
											<A HREF="/middleware/EditTaskAction.do?taskid=<c:out value="${task.id}"/>"><c:out value="${task.taskName}"/></A>
										</c:otherwise>
										</c:choose>&nbsp;&nbsp;
										</td>
									    <td><fmt:message key="task.taskType.${task.taskType}"/>&nbsp;</td>
								    	<td nowrap>
								    	<c:if test="${task.taskType =='A'}">
								    		<fmt:message key="task.startupType.${task.startupType}"/>&nbsp;
								    	</c:if>
								    	</td>
								    	<td nowrap>
											<fmt:message key="task.triggermode.${task.triggerMode}"/>
										</td>
								    	<td nowrap>
								    	<c:if test="${task.taskType =='A'}">
									    	<c:if test="${task.state}">
												 <FONT COLOR="#00CC00"><B>Started</B></FONT>
											</c:if>
											<c:if test="${!task.state}">
												 <FONT COLOR="#FF0000"><B>Stopped</B></FONT>
											</c:if>
										</c:if>
										</td>								    	
								    	<td nowrap><c:out value="${task.description}"/>&nbsp;</td>
								    	<td nowrap>

								    	<c:if test="${task.state and task.taskType =='A'}">
											<img src="../images/stopped.gif" alt="<fmt:message key="task.js.stoptask"/>" onmouseover="this.style.cursor='hand'"  onclick="gotoExecuteCommand('/middleware/ExecuteTask.do?stopTask=${task.id}','stop','<fmt:message key="task.js.doyouwantstop"/>')">
										</c:if>

										<c:if test="${!task.state and task.taskType =='A'}">
											<img src="../images/started.gif" alt="<fmt:message key="task.js.starttask"/>" onmouseover="this.style.cursor='hand'"  onclick="gotoExecuteCommand('/middleware/ExecuteTask.do?startTask=${task.id}','start','<fmt:message key="task.js.doyouwantstart"/>')">
										</c:if>

										<c:if test="${isVeiwer !='yes' and task.taskType =='M'}">
											<c:choose>
											<c:when test="${task.statusDeviceID}">
												<img src="../images/package.gif"  alt="<fmt:message key='task.js.manualpascking'/>" onmouseover="this.style.cursor='hand'"  onclick="openPage('/middleware/PackageAction.do?taskId=${task.id}&clear=y')">
											</c:when>
											<c:otherwise>
                                         	   <img src="../images/package.gif"  alt="<fmt:message key='task.js.manualpascking'/>" style="filter='Alpha(Opacity=30)'" onmouseover="this.style.cursor='hand'" onclick="alert(unescape('<fmt:message key='task.js.alertstartdevice'><fmt:param value='${task.relationDeviceName}'/></fmt:message>'))">
											</c:otherwise>
											</c:choose>
										</c:if>
										</td>
									</tr>
									</c:forEach>
								</table>
				</td>
                <td width="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td colspan="3" height="1" bgcolor="#cccccc"></td>
              </tr>
            </table>
            <br><br>
            </td>
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