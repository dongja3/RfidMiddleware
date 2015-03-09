<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
	<title><fmt:message key="deleteDevice.title" /></title>
	<link href="../css/css.css" rel="stylesheet" type="text/css">
	<script src="../js/web.js"></SCRIPT>
		
	<script>
		function deleteDeviceAction(){
			var checkName="readerId";
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
	        
			if(confirm("<fmt:message key='javascript.device.prompt.delete' />"))
					document.forms[0].submit();
				else
					return false;
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
  <tr> <html:form action="/DeleteDevicesAction">
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
                <p><span class="section"><fmt:message key="deleteDevice.title" /></span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select class="field" onChange="gotoPage(this.value)">
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
            <p><span class="bodytxt">
            <br>
            <font color="red">
                 <html:messages id="message" message="true">
                      <li><c:out value="${message}" /></li>
                 </html:messages>
			</font>
                </span>
              </p>
       
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
		<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                 <tr class="bg-gray">
             <td align="left"><input type="checkbox" name="aa" onclick="allSelect(this,'readerId')" ></td>
             <td class="bg-gray" nowrap><fmt:message key="device.onecell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.twocell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.threecell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.fourcell.title" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="device.fivecell.title" /></td>
              <td class="bg-gray" colspan="2" align="left"><fmt:message key="device.sixcell.title" /></td>
            </tr>
         
           <c:forEach var="devicePo" items="${readers}"  varStatus="index" >
										<c:if test="${index.count%2==1}">
										   <tr  onmouseover="onover(this)"   onmouseout="onout(this)"  class="bg-shallow">
         
										</c:if>
										<c:if test="${index.count%2==0}">
										   <tr  onmouseover="onover(this)"   onmouseout="onout(this)"  class="bg-shallow1">
         
										</c:if>
         
            <td align="left">
             <c:choose>
	              <c:when test="${devicePo.status}">
              <input type="checkbox" name="re" disabled>
                  </c:when>
	              <c:otherwise>
	               <html:checkbox property="readerId" onclick="onClickCheckBox(this)" value="${devicePo.id}" />
	              </c:otherwise>
              </c:choose>
           </td>
            <td nowrap>
             <c:choose>
	              <c:when test="${not devicePo.status}">
            <a href="/middleware/AddDeviceAction.do?deviceId=<c:out value='${devicePo.id}' />" title="Edit Device"><c:out value="${devicePo.deviceName}" /></a>
                  </c:when>
	              <c:otherwise>
	              	<c:out value="${devicePo.deviceName}" />
	              </c:otherwise>
              </c:choose>
            </td>
              <td ><c:out value="${devicePo.deviceID}" /></td>
              <td align="left" nowrap><b>
             <c:choose>
	             <c:when test="${devicePo.status}">
	    	        <font color="green"><fmt:message key="device.status.started" /></font>
	             </c:when>
	             <c:otherwise>
		            <font color="red"><fmt:message key="device.status.stopped" /></font>
	             </c:otherwise>
			</c:choose></b>
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
              <td nowrap colspan="2"><c:out value="${devicePo.description}" /></td>

            </tr>
           </c:forEach>
           <tr class="bg-shallow">
              <td colspan="8" align="left">&nbsp;</td>
            </tr>
             <tr >
              <td colspan="8" class="bg-shallow" align="center" >
			  <table>
				<tr>
              		<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
					<a onclick="deleteDeviceAction()" style="cursor:hand" ><fmt:message key="form.delete" /></a> 
              	</td>
              </tr>
              </table>
			  </td>
            </tr>
              <tr>
              <td colspan="8" class="bg-shallow"></td>
            </tr>


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
</html:form>
</td></tr><tr><td>
<%@ include file="/foot.jsp" %>
</td><tr></table>
</body>
</html>
</fmt:bundle>