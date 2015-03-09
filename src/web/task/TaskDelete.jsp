<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
<title><fmt:message key="task.DeleteTask"/></title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="../js/web.js"></script>
<script>
		function deleteTaskAction(){
			var checkName="taskId";
	        var status = false;
			for (i=0;i<document.getElementsByName(checkName).length;i++){
				if(document.getElementsByName(checkName)[i].checked){
					status=true;
				}
	        }
	        if(!status){
	        	alert("<fmt:message key='form.notselect'/>");
	        	return false;
	        }
			if(confirm("<fmt:message key='task.js.deletetask'/>"))
					document.forms[0].submit();
				else
					return false;
			}
</script>
</head>
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
    	<td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    	<td  align="left" valign="top" width="80%"> 
    	<html:form action="DeleteTaskAction">
      		<table width="96% border="0" cellspacing="0" cellpadding="0">
        		<tr> 
          			<td  align="center" valign="top" class="section">
          				<div class="bodyhdr"> 
			            <div align="right"> 
                		<p><span class="section"><fmt:message key="task.DeleteTask"/></span>
                		<select class="field" onChange="gotoPage(this.value)">
						<option value="" selected><fmt:message key="form.AvailableActions"/></option>
        				<option value="/middleware/ListTaskAction.do" ><fmt:message key="task.ListTask" /></option>
	    			    <option value="/middleware/EditTaskAction.do?action=create" ><fmt:message key="task.CreateTask" /></option>
    				    <option value="/middleware/ListTaskAction.do?operation=delete"><fmt:message key="task.DeleteTask" /></option>
       					</select>
						<hr width="100%" size="1" noshade></p>
			            </div>
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
                						<td nowrap class="bg-gray"><input type="checkbox" name="all" onclick="allSelect(this,'taskId')"></td>
								    	<td nowrap class="bg-gray"><fmt:message key="task.taskName" /></td>
								    	<td nowrap class="bg-gray"><fmt:message key="task.TaskType" /></td>
									    <td nowrap class="bg-gray"><fmt:message key="task.Startup"/></td>
								    	<td nowrap class="bg-gray"><fmt:message key="task.TriggerMode" /></td>
								    	<td nowrap class="bg-gray"><fmt:message key="State" /></td>
								    	<td nowrap class="bg-gray"><fmt:message key="Description" /></td>									    
								    </tr>
									<c:forEach var="task" items="${taskList}"  varStatus="index" >
										<c:if test="${index.count%2==1}">
										<tr onmouseover="onover(this)"   onmouseout="onout(this)" class="bg-shallow" height="27">
										</c:if>
										<c:if test="${index.count%2==0}">
										<tr onmouseover="onover(this)"   onmouseout="onout(this)" class="bg-shallow1" height="27">
										</c:if>
						              	<td>
										<c:choose>
											<c:when test="${task.state}">
												<input type="checkbox" name="re" disabled>
											</c:when>
											<c:otherwise>
												<html:checkbox property="taskId" onclick="onClickCheckBox(this)" value="${task.id}" />
											</c:otherwise>
										</c:choose>
										</td>
            							<td >&nbsp;&nbsp;					
										<c:if test="${task.state}">
											<c:out value="${task.taskName}"/>
										</c:if>
										<c:if test="${!task.state}">
											<A HREF="/middleware/EditTaskAction.do?taskid=<c:out value="${task.id}"/>"><c:out value="${task.taskName}"/></A>
										</c:if>
										</td>
										<td><fmt:message key="task.taskType.${task.taskType}"/>&nbsp;</td>
									    <td>
									    <c:if test="${task.taskType =='A'}">
									    	<fmt:message key="task.startupType.${task.startupType}"/>
									    </c:if>
									    &nbsp;
									    </td>
								    	<td>
											<fmt:message key="task.triggermode.${task.triggerMode}"/>
										</td>
								    	<td>
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
									</tr>
									</c:forEach>
								</table> 
							</td>
			                <td width="1" bgcolor="#cccccc"></td>
            				</tr>
				            <tr >
								<td width="1" bgcolor="#cccccc"></td>
        	    			  	<td class="bg-shallow" align="center" >
									<table>
										<tr>
											<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
												<a onclick="deleteTaskAction();" style="cursor:hand" ><fmt:message key="form.delete" /></a> 
											</td>
										</tr>
									</table>
								</td>
								<td width="1" bgcolor="#cccccc"></td>
					        </tr>
			    	        <tr>
            					<td colspan="3" height="1" bgcolor="#cccccc"></td>
			            	</tr>
            			</table>
		            </html:form>
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