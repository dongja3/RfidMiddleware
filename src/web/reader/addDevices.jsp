<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
	<title>
	   <fmt:message key="addDevice.addDevice" />
	</title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script src="../js/web.js">
		</script>
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
				var ecod = /^[\w]+$/;
			
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
					var ip = document.forms[0].connectionName.value.replace(/(^\s*)|(\s*$)/g, "");
				    if(!isIP(ip) && !e.test(ip)){
						alert("<fmt:message key='javascript.device.IPIllegal'/>");
						document.forms[0].connectionName.focus();
						return;
					}
					var port = document.forms[0].port.value.replace(/(^\s*)|(\s*$)/g, "");
					if(isEmpty(port)){
				    	alert("<fmt:message key='javascript.device.port.illegal.isempty'/>");
						document.forms[0].port.focus();
				   		return false;
					}
					if(!isNumber(port) || port > 65535 || port < 0){
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
				if(document.forms[0].readerClass.value==""){
					document.forms[0].pluginId.value="";
				}
			}
		</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(1,3)">
<jsp:include page="../header.jsp" />
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr><td></td></tr>
	<tr>
	<td>	
		<br>
		<table width=100%"  border="0" cellpadding="0" cellspacing="0">	
			<tr>
			<html:form action="SaveDevicesAction">
    			<td  valign="top">
    				 <p></p><p>&nbsp;</p> <p><br></p>
    			</td>
    			<td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    			<td  align="center" valign="top"> <p><br>
			        <br><br><br><br>
        			<img src="../images/image_1.jpg" width="102" height="101"><br>
        			</p>
				    <p>&nbsp;</p>
				    <p>&nbsp;</p>
      				<span class="bodytxt">
     				</span> 
      				<p>&nbsp;</p>
    			</td>
    			<td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    			<td  align="left" valign="top" width="80%"> 
      				<table width="96%"border="0" cellspacing="0" cellpadding="0">
        				<tr> 
          					<td  align="center" valign="top"> <div class="bodyhdr"> 
              					<div align="right"> 
	                				<p>
	                				<span class="section">	
	              						<fmt:message key="addDevice.addDevice" />
	              					</span>
	              					<html:hidden property="id" value="${device.id}" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="field" onChange="gotoPage(this.value)">
								        <option value="" selected><fmt:message key="form.AvailableActions"/></option>
							          	<option value="/middleware/ListDevicesAction.do" ><fmt:message key="device.action.list" /></option>
								         <option value="/middleware/AddDeviceAction.do"><fmt:message key="device.action.create" /></option>
							    	     <option value="/middleware/ListDevicesAction.do?userOperation=delete"><fmt:message key="device.action.delete" /></option>
							        </select>
				  
					                <hr width="100%" size="1" noshade></p>
				              </div> </div>
           				  </td>
                     </tr>
        <tr> 
          <td  align="center" valign="top"> <div align="left">
            <p><span class="bodytxt"> <br>
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
              <td width="80%" ><html:text property="readerName"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><font color="red">*</font></td>
            </tr>
            <tr class="bg-shallow">
              <td width="200" >&nbsp;&nbsp;<fmt:message key="addDevice.deviceId" /></td>
              <td width="70%" ><html:text property="readerId"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><font color="red">*</font></td>
            </tr>
             <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.connectType" /></td>
              <td width="70%">
              
	              <html:select property="connectionType" styleClass="field" onchange="changeConnectType(this)">
	                    <html:option value="S" >Serial Port</html:option>
	                    <html:option value="T" ><fmt:message key="addDevice.tcp_ip"/></html:option>
	              		
	              </html:select>
	             
	             <font color="red">*</font>
	            </td>
             </tr>
            
             <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.deviceType" /></td>
              <td width="70%">
              
              <html:select property="readerType" styleClass="field">
                   	<html:option value="R" >Reader</html:option>
              		<html:option value="P" >Printer</html:option>
              </html:select>
              
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
              <td width="70%"><html:text property="connectionName" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><font color="red">*</font></td>
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
              	<html:text property="port" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/><span id="portRequired"><font color="red">*</font></span>
              </td>
            </tr>
           <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.persistTime" /></td>
              <td width="70%"><html:text property="persistTime" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /><fmt:message key="addDevice.UnitOfPersisTime" /></td>
            </tr>
            <tr class="bg-shallow">
            	<td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.username" /></td>
	            <td width="70%"><html:text property="username"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
           
             <tr class="bg-shallow">
    	         <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.password" /></td>
        	     <td width="70%"><html:text property="password" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /></td>
            </tr>
            
            <tr class="bg-shallow">
            	<td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.startupType" /></td>
              	<td width="70%">

                <html:select property="startUp" styleClass="field">
                     		<html:option value="A"><fmt:message key="addDevice.automatic" /></html:option>
		              		<html:option value="M"><fmt:message key="addDevice.manual" /></html:option>
             	</html:select>
            	
            	<font color="red">*</font>
              	</td>
            </tr>
            
            <tr class="bg-shallow">
            	<td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.devicePlugin" /></td>
            	<td width="70%">

				  <html:select property="pluginId" styleClass="field" onchange="showCustomizeClass(this)" >
				        <c:forEach var="plugins" items="${plugin}">
                       	<html:option value="${plugins.pluginClass}"><c:out value="${plugins.pluginName}" /></html:option>
             	        </c:forEach>
                        <html:option value=""><fmt:message key="addDevice.userCustomized" /></html:option>
				 </html:select>
				 
				</td>
            </tr>
            <tr class="bg-shallow" >
	              <td width="200" >&nbsp;&nbsp;<fmt:message key="addDevice.class" /></td>
	              <td width="70%"><html:text property="readerClass" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"  size="60"/><font color="red">*</font></td>
            </tr>
             <tr class="bg-shallow">
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.command" /></td>
              <td width="70%"><html:text property="command"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
            <tr class="bg-shallow" >
              <td width="200">&nbsp;&nbsp;<fmt:message key="addDevice.description" /></td>
              <td width="70%"><html:text property="description" maxlength="200" size="60" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
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