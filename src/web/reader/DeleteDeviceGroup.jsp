<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
	<head>
		<title><fmt:message key="delDeviceGroup.delGroup" /></title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script src="../js/web.js">
		</script>
		<script>
		function deleteDeviceGroupsAction(){
			var checkName = "groupId";
			var status = false;
			for (i=0;i<document.getElementsByName(checkName).length;i++){
				if(document.getElementsByName(checkName)[i].checked){
					status=true;
				}
	        }
	        if(!status){
	        	alert("<fmt:message key='form.checlbox.unselect' />");
	        	return false;
	        }
			if(confirm("<fmt:message key='javascript.devicegroup.prompt.delete' />"))
					document.forms[0].submit();
				else
					return false;
			}
	  </script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(1,4)">
<jsp:include page="../header.jsp" />
<html:form action="DeleteDeviceGroupsAction">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
</td></tr><tr><td>	
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 	
    <td  valign="top"> </td>
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
                <p><span class="section"> <fmt:message key="delDeviceGroup.delGroup" /></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
       
            <table width="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                  
			      <tr class="bg-title-shallowegreen" >
              <td width="5%" class="bg-gray">&nbsp;&nbsp;<input type="checkbox" name="aa" onclick="allSelect(this,'groupId')" ></td>
              <td width="30%" class="bg-gray"><fmt:message key="delDeviceGroup.groupName" /></td>
              <td width="65%" class="bg-gray"><fmt:message key="delDeviceGroup.description" /></td>
            </tr>
         <c:forEach var="devicePo" items="${readerGroups}"varStatus="index" >
				<c:if test="${index.count%2==1}">
					 <tr onmouseover="onover(this)"   onmouseout="onout(this)"  class="bg-shallow" height="27">
          
				</c:if>
				<c:if test="${index.count%2==0}">
					 <tr onmouseover="onover(this)"   onmouseout="onout(this)"  class="bg-shallow1" height="27">
          
				</c:if>
              <td width="5%" >&nbsp;&nbsp;<html:checkbox property="groupId" onclick="onClickCheckBox(this)" value="${devicePo.readerGroupId}" /></td>
              <td width="30%"><a href="/middleware/AddDeviceGroup.do?groupId=<c:out value='${devicePo.readerGroupId}' />"><c:out value="${devicePo.readerGroupName}" /></a></td>
              <td width="65%"><c:out value="${devicePo.description}" /></td>
            </tr>
       	</c:forEach>
        <tr>
              <td colspan="3" class="bg-shallow">&nbsp;</td>
            </tr>
            <tr >
              <td colspan="3" class="bg-shallow" align="center" >
			   <table>
				<tr>
              		<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
					<a onclick="deleteDeviceGroupsAction();" style="cursor:hand" ><fmt:message key="delDeviceGroup.button.delete" /></a> 
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