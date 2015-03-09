<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
	<title>
		<fmt:message key="addDeviceGroup.editGroup" />
	</title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script src="../js/web.js"></script>
		
		<script>
		
			function SelectAdd(){
				var sReader = document.forms[0].selectedReader;
				var uReader = document.forms[0].unselectedReader;
				
				if (sReader.selectedIndex==-1)
					return false;
					
				for(j=0; j<sReader.length; j++){
					if(sReader.options[j].selected){
						uReader[uReader.length] = new Option(sReader.options[j].text, sReader.options[j].value, false, false);
					}
				}
				
				while(sReader.selectedIndex!=-1 && sReader.options[sReader.selectedIndex].selected){
					sReader.options[sReader.selectedIndex] = null;
				}
						
			}
				
				
			function SelectDel(){
			
				if (document.forms[0].unselectedReader.selectedIndex==-1)
					return false;
						
					var sReader = document.forms[0].selectedReader;
					var uReader = document.forms[0].unselectedReader;
					
					for(var i=0; i<uReader.length; i++){
						if(uReader.options[i].selected){
							sReader[sReader.length] = new Option(uReader.options[i].text, uReader.options[i].value, false, false);
						}
					}
					
					while(uReader.selectedIndex!=-1 && uReader.options[uReader.selectedIndex].selected){
						uReader.options[uReader.selectedIndex] = null;
					}
					
				}
				
				
				function clickSubmit(){
					var sReader = document.forms[0].selectedReader;
					var SReaderID="";
					var groupName = document.forms[0].groupName.value.replace(/(^\s*)|(\s*$)/g, "");
					if(isEmpty(groupName)){
						alert("<fmt:message key='javascript.devicegroup.groupname.illegal.isempty' />");
						return false;
					}
					if(isIllegalTitleName(groupName)){
						alert("<fmt:message key='javascript.devicegroup.groupname.illegal' />");
						return false;
					}

					if (sReader.length>0){
						SReaderID = sReader.options[0].value
						for (j=1;j < sReader.length;j++)
						{
							SReaderID = SReaderID + ","  + sReader.options[j].value;
						}
					}
					
					document.forms[0].readerIds.value=SReaderID;
	
					if(confirm("<fmt:message key='javascript.devicegroup.prompt.save' />"))
						document.forms[0].submit();
					else
						return false;
						
				}
				
				
				function initSelect(){	
					var sReader = document.forms[0].selectedReader;
					var uReader = document.forms[0].unselectedReader;
					var readerIdArray = document.forms[0].readerIds.value;
				
					if(readerIdArray!=null && readerIdArray!="" && readerIdArray != "," ){
						var readerIDs = readerIdArray.split(",");
						
						for(var i=0; readerIDs[i] != null && readerIDs[i] != "" && i< readerIDs.length; i++){
							for(var j=0; j < uReader.length; j++){
								if(uReader.options[j].value==readerIDs[i]){
									uReader.options[j].selected = true;
									break;
								}	
							}
						}
						
						SelectDel();
					}
				}
				
		</script>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(1,4)">
<jsp:include page="../header.jsp" />

<html:form action="SaveDeviceGroupsAction">
   <html:hidden property="groupId" value="${readerGroup.id}" />
   <html:hidden property="readerIds" value="${readerGroup.readerGroupId}" />

<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
</td></tr><tr><td>	
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>

 
    <td  valign="top"></td>
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
                <p><span class="section">	
			     	<fmt:message key="addDeviceGroup.editGroup" />
              		</span>
              		

                
			   <select class="field" onChange="gotoPage(this.value)">
              	<option value="" selected><fmt:message key="form.AvailableActions"/></option>
              	<option value="/middleware/ListDeviceGroupsAction.do"><fmt:message key="deviceGroup.action.listDeviceGroup" /></option>              	
                <option value="/middleware/AddDeviceGroup.do" ><fmt:message key="deviceGroup.action.addDeviceGroup" /></option>
    	        <option value="/middleware/ListDeviceGroupsAction.do?userOperation=delete"><fmt:message key="deviceGroup.action.delDeviceGroup" /></option>
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
       
            <table width="540" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                 
			<tr class="bg-shallow">
              <td width="30%"  >&nbsp;&nbsp;<fmt:message key="addDeviceGroup.groupName" /></td>
            
              <td width="70%" ><html:text property="groupName"  value="${readerGroup.groupName}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
            <tr class="bg-shallow">
              <td width="30%" >&nbsp;&nbsp;<fmt:message key="addDeviceGroup.description" /></td>
              <td width="70%" ><html:text property="description" maxlength="200" value="${readerGroup.description}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /></td>
            </tr>
            <tr class="bg-shallow">
              <td colspan="2" class="bg-title-shallowegreen">&nbsp;&nbsp;<fmt:message key="user.DistributeReaders" /></td>
            </tr>
            <tr class="bg-shallow" >
            <td colspan="2" >
				<table width="100%">
					<tr  >
						<td align="center" height="20" valign="middle"> 
						<div height="20"><br><fmt:message key="addDeviceGroup.unselected" /><br></div>
						</td>
						<td align="center" height="20" valign="middle">
						<div height="20"><br><fmt:message key="addDeviceGroup.selected" /><br></div>
						</td>
					</tr>
					<tr class="bg-shallow">
						<td align="right" rowspan=2>
								<select name="unselectedReader" multiple="multiple" size="10" ondblclick="SelectDel();" style="width:220">
												 	<c:choose>
			             						 		<c:when test="${allReadersList == null}">
						             						 <c:forEach var="unSelected" items="${unSelectedReadersList}">
						             						 	<option value="${unSelected.id}"><c:out value="${unSelected.deviceName}" /></option>
						             						 </c:forEach>
			             						 		</c:when>
			             						 		<c:otherwise>
			             						 			<c:forEach var="select" items="${allReadersList}">
						             						 	<option value="${select.id}"><c:out value="${select.deviceName}" /></option>
						             						</c:forEach>
			             						 		</c:otherwise>
			             						   </c:choose>
							 </select>
						</td>
						<td align="center" valign=top>
							<table border=0 cellPadding=0 cellSpacing=0 width=100%>
								<tr >
									<td width=60 align="center">
										<img src="../images/add.gif" onclick="SelectDel();" title='Add' style="cursor:hand"><br><br><br>
										<img src="../images/del.gif" onclick="SelectAdd();" title='Remove' style="cursor:hand">
									</td>
									<td>
									<select name="selectedReader" multiple="multiple" size="10" ondblclick="SelectAdd();" style="width:220">
								        <c:forEach var="selected" items="${selectedReadersList}">
						             		<option value="${selected.id}"><c:out value="${selected.deviceName}" /></option>
						             	</c:forEach>
						            </select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
            </td>
            </tr>
            <tr class="bg-shallow">
              <td colspan="2"  align="center">&nbsp;</td>
            </tr>
            <tr class="bg-shallow">
              <td colspan="2"  align="center">
				<table>
			   		<tr align="center">
						<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
							<a onclick="clickSubmit();" style="cursor:hand" ><fmt:message key="form.savedata"/></a> 
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
</td></tr><tr><td>
</td><tr></table>
</html:form>
</body>
</html>
<%@ include file="/foot.jsp" %>
</fmt:bundle>
<script>
document.forms[0].groupName.focus();
initSelect();
</script>