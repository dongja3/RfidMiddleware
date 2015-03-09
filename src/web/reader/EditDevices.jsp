<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
	<title>
	<c:if test="${device.id == null or device.id == ''}">
	         	<fmt:message key="addDevice.addDevice" />
    </c:if>
    <c:if test="${device.id != null && device.id != ''}">
    	 		<fmt:message key="addDevice.editDevice" />
	</c:if>
	</title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script src="../js/web.js"></script>
		<script>
				function isNum(value){
					var e= /^\d*\d$/;
					if(e.test(value)){
						return true;
					}else{
						return false;
					}
				}
				
				
				function confirmSave(){
					//it will not submit if empty
					var readerName = document.forms[0].readerName.value.replace(/(^\s*)|(\s*$)/g, "");
					if(isEmpty(readerName)){
						alert("<fmt:message key='javascript.device.readerName.illegal.isempty' />");
						document.forms[0].readerName.focus();
						return false;
					}
					var readerId = document.forms[0].readerId.value.replace(/(^\s*)|(\s*$)/g, "");
					if(isEmpty(readerId)){
						alert("<fmt:message key='javascript.device.deviceID.illegal.isempty' />");
						document.forms[0].readerId.focus();
						return false;
					}
					var ecod = /^[\w]+$/
					if(!ecod.test(readerId)){
						alert("<fmt:message key='javascript.device.deviceID.illegal' />");
						document.forms[0].readerId.focus();
						return false;

					}
					var readerClass = document.forms[0].readerClass.value.replace(/(^\s*)|(\s*$)/g, "");
					if(isEmpty(readerClass)){
						alert("<fmt:message key='javascript.device.readerClass.illegal.isEmpty' />");
						document.forms[0].readerClass.focus();
						return false;
					}
					
					var connectionName = document.forms[0].connectionName.value.replace(/(^\s*)|(\s*$)/g, "");
                    if(document.forms[0].connectionType.value == "S"){
						if(isEmpty(connectionName)){
							alert("<fmt:message key='javascript.device.connectionName.illegal.isempty' />");
							document.forms[0].connectionName.focus();
							return false;
						}
					    if(document.forms[0].port.value!=""){
							var portValue = document.forms[0].port.value.replace(/(^\s*)|(\s*$)/g, "");
					    	if(!isNum(portValue) || portValue.length > 9 ||  portValue < 0 ){
								alert("<fmt:message key='javascript.device.BaudRate.illegal.isNumber'/>");
								document.forms[0].port.focus();
						  	    return false;
						    }
					    }
	
					}else{
						if(isEmpty(connectionName)){
							alert("<fmt:message key='javascript.device.ip.illegal.isempty' />");
							document.forms[0].connectionName.focus();
							return false;
						}
						var e = /^[a-zA-z_][\w]*$/;
					    if(!isIP(connectionName) && !e.test(connectionName)){
							alert("<fmt:message key='javascript.device.IPIllegal'/>");
							document.forms[0].connectionName.focus();
							return;
						}
						
						if(isEmpty(document.forms[0].port.value)){
					    	alert("<fmt:message key='javascript.device.port.illegal.isempty'/>");
							document.forms[0].port.focus();
					   		return false;
						}

						if(!isNumber(document.forms[0].port.value)){
					    	alert("<fmt:message key='javascript.device.port.illegal.isNumber'/>");
							document.forms[0].port.focus();
					   		return false;
						}
					
					}
									
					var persistTime = document.forms[0].persistTime.value.replace(/(^\s*)|(\s*$)/g, "");
					if(persistTime!=""){
						
						if(!isNumber(persistTime)){
							alert("<fmt:message key='javascript.device.persistimeIllegal' />");
							document.forms[0].persistTime.focus();
							return false;
						}
					}
					
					

       				if(confirm("<fmt:message key='javascript.device.prompt.save' />")){
						document.forms[0].submit();
					}else{
						return false;
					}

				}
			
		
		     function showCustomizeClass(custom){
		        document.forms[0].readerClass.value=custom.value;
			}
			
			
			function changeConnectType(con){
				if(con.value=="S"){
				    connectionselect.innerHTML = "&nbsp;&nbsp;<fmt:message key='addDevice.serialPort' />";
					connectNameDiv.innerHTML = "&nbsp;&nbsp;<fmt:message key='addDevice.baudRate' />";
					if(document.forms[0].port.value==0){
						document.forms[0].port.value="";
					}
					if(document.forms[0].persistTime.value==0){
						document.forms[0].persistTime.value="";
					}
					portRequired.style.visibility='hidden';
				}else{
				    connectionselect.innerHTML = "&nbsp;&nbsp;<fmt:message key='addDevice.tcp_ip' /> ";
					connectNameDiv.innerHTML = "&nbsp;&nbsp;<fmt:message key='addDevice.port' />";
				portRequired.style.visibility='visible';
				}
			}
	     
	
		</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(1,3)">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>	
<br>
<table width=100%"  border="0" cellpadding="0" cellspacing="0">
<tr> <html:form action="SaveDevicesAction">
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
      <table width="96%"border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td  align="center" valign="top"> <div class="bodyhdr"> 
              <div align="right"> 
                <p><span class="section">	
              		<c:if test="${device.id == null or device.id == ''}">
		              	<fmt:message key="addDevice.addDevice" />
              		</c:if>
              		<c:if test="${device.id != null && device.id != ''}">
              			<fmt:message key="addDevice.editDevice" />
              		</c:if>
                  </span>
              <html:hidden property="id" value="${device.id}" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <select class="field" onChange="gotoPage(this.value)">
	            <option value="" selected><fmt:message key="form.AvailableActions"/></option>
              	              	<option value="/middleware/ListDevicesAction.do" ><fmt:message key="device.action.list" /></option>
	            <option value="/middleware/AddDeviceAction.do"><fmt:message key="device.action.create" /></option>
    	        <option value="/middleware/ListDevicesAction.do?userOperation=delete"><fmt:message key="device.action.delete" /></option>
              </select>
			  
                <hr width="100%" size="1" noshade></p>
              </div>
            </div></td>
        </tr>
        <tr> 
          <td  align="center" valign="top"> <div align="left">
            <p><span class="bodytxt"><br>
             <font color="red">
                 <html:messages id="message" message="true">
                      <li><c:out value="${message}" /></li>
                 </html:messages>
			</font>
                </span>
              </p>
       
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                 

             <tr class="bg-shallow">
              <td width="20%" >&nbsp;&nbsp;<fmt:message key="addDevice.deviceName" /></td>            
              <td width="80%" ><html:text property="readerName" value="${device.deviceName}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><font color="red">*</font></td>
            </tr>
            <tr class="bg-shallow">
              <td width="200" >&nbsp;&nbsp;<fmt:message key="addDevice.deviceId" /></td>
              <td width="70%" ><html:text property="readerId" value="${device.deviceID}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><font color="red">*</font></td>
            </tr>
             <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.connectType" /></td>
              <td width="70%">
              
	              <html:select property="connectionType" styleClass="field" onchange="changeConnectType(this)">
	              <c:choose>
	              	<c:when test="${device.connectionType == 'S'}">
	              	   	<option value="T" ><fmt:message key="addDevice.tcp_ip"/></option>
	              		<option value="S" selected >Serial Port</option>
	              	</c:when>
	              	<c:otherwise>
		              	<option value="T" selected><fmt:message key="addDevice.tcp_ip"/></option>
			            <option value="S" >Serial Port</option>
			        </c:otherwise>
	              </c:choose>
	              </html:select>
	              
	             <font color="red">*</font>
	            </td>
             </tr>
            
             <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.deviceType" /></td>
              <td width="70%">
              <c:if test="${device.deviceType=='P'}">
              <html:select property="readerType" styleClass="field">
             		<option value="R">Reader</option>
              		<option value="P" selected>Printer</option>
              </html:select>	
			  </c:if>
              <c:if test="${device.deviceType=='R'}">
              <html:select property="readerType" styleClass="field">
             		<option value="R" selected>Reader</option>
              		<option value="P">Printer</option>
              </html:select>
              </c:if>
              <font color="red">*</font>
              </td>
            </tr>
        
             <tr class="bg-shallow">
              <td width="200" valign="middle"><div id="connectionselect" valign="middle">
              
             <c:choose >
             	<c:when test="${device.connectionType == 'S'}">
             		&nbsp;&nbsp;<fmt:message key="addDevice.serialName" />
	            </c:when>
	            <c:otherwise>
	             	&nbsp;&nbsp;<fmt:message key="addDevice.IP" />
	            </c:otherwise>
             </c:choose>
             
               </div></td>
              <td width="70%"><html:text property="connectionName" value="${device.connectionName}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><font color="red">*</font></td>
            </tr>
            
             <tr class="bg-shallow">
              <td width="200" valign="middle"><div id="connectNameDiv" valign="middle">
              
              <c:choose >
              	<c:when test="${device.connectionType == 'S'}">
              		&nbsp;&nbsp;<fmt:message key="addDevice.baudRate" />
		        </c:when>
	            <c:otherwise>
	              	&nbsp;&nbsp;<fmt:message key="addDevice.port" />
		        </c:otherwise>
              </c:choose>
              
              </div>
              </td>
              <td width="70%">
              	<html:text property="port" value="${device.port}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/><span id="portRequired"><font color="red">*</font></span>
              </td>
            </tr>
           <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.persistTime" /></td>
              <td width="70%"><html:text property="persistTime" value="${device.persistTime}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><fmt:message key="addDevice.UnitOfPersisTime" /></td>
            </tr>
            <tr class="bg-shallow">
            	<td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.username" /></td>
	            <td width="70%"><html:text property="username" value="${device.userName}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
           
             <tr class="bg-shallow">
    	         <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.password" /></td>
        	     <td width="70%"><html:text property="password" value="${device.passwd}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /></td>
            </tr>
            
            <tr class="bg-shallow">
            	<td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.startupType" /></td>
              	<td width="70%">

                <html:select property="startUp" styleClass="field">
                  	<c:choose>
                  		<c:when test="${device.startup == 'A'}">
		              		<option value="A" selected><fmt:message key="addDevice.automatic" /></option>
		              		<option value="M"><fmt:message key="addDevice.manual" /></option>
	                    </c:when>
	                    <c:otherwise>
		              		<option value="A"><fmt:message key="addDevice.automatic" /></option>
		              		<option value="M" selected><fmt:message key="addDevice.manual" /></option>
              			</c:otherwise>
              		</c:choose>
            	</html:select>
            	
            	<font color="red">*</font>
              	</td>
            </tr>
            
            <tr class="bg-shallow">
            	<td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.devicePlugin" /></td>
            	<td width="70%">

				  <html:select property="pluginId" styleClass="field" onchange="showCustomizeClass(this)" >
				  	<c:set var="customizeSelect" value="unselect" scope="request" />
                       <c:forEach var="plugins" items="${plugin}">
                       		<c:choose>
                       			<c:when test="${plugins.pluginClass == device.className}">
                       				<c:set var="bb" value="into" scope="request" />
                       				<option value="${plugins.pluginClass}" selected><c:out value="${plugins.pluginName}" /></option>
                       			</c:when>
                       			<c:otherwise>
                       				<option value="${plugins.pluginClass}" ><c:out value="${plugins.pluginName}" /></option>
                       			</c:otherwise>
                       		</c:choose>
                       </c:forEach>
                       
                    <c:if test="${device.id == null or device.id == ''}">
						<option value="" selected><fmt:message key="addDevice.userCustomized" /></option>
					</c:if>
             
					<c:if test="${bb!=null and device.id != null}">
						<option value="" ><fmt:message key="addDevice.userCustomized" /></option>
					</c:if>
					
					<!-- selected user customized -->	
					<c:if test="${(bb==null or bb == '') and device.id != null and device.id != null and device.id != ''}">
						<option value="${device.className}" selected><fmt:message key="addDevice.userCustomized" /></option>
					</c:if>
				
				 </html:select>
				 
				</td>
            </tr>
            <tr class="bg-shallow" >
	              <td width="200" >&nbsp;&nbsp;<fmt:message key="addDevice.class" /></td>
	              <td width="70%"><html:text property="readerClass" styleClass="field" value="${device.className}" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"  size="60"/><font color="red">*</font></td>
            </tr>
             <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.command" /></td>
              <td width="70%"><html:text property="command" value="${device.command}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
            <tr class="bg-shallow" >
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.description" /></td>
              <td width="70%"><html:text property="description" maxlength="200" size="60" value="${device.description}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
            
            <tr class="bg-shallow">
              <td colspan="2"  align="center">&nbsp;</td>
            </tr>
            <tr class="bg-shallow">
              <td colspan="2" align="center">
              	<table>
			   		<tr align="center">
						<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
							<a onclick="confirmSave();" style="cursor:hand" ><fmt:message key="form.savedata"/></a> 
		              	</td>
	    		    </tr>
    	        </table>
    	      </td>
            </tr>
                </table> 
				<!--end list of reader content-->

				</td>
                <td width="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td colspan="3" height=2 bgcolor="#cccccc"></td>
              </tr>
            </table>
            <p>&nbsp;</p>
          </div></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html:form>

</body>
</html>
</td></tr><tr><td>
<%@ include file="/foot.jsp" %>
</td><tr></table>


<script>
	document.forms[0].readerName.focus();
	changeConnectType(document.forms[0].connectionType);
</script>
</fmt:bundle>