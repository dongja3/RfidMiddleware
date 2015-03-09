<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
	<title><fmt:message key="user.DeleteUser"/></title>
	<link href="../css/css.css" rel="stylesheet" type="text/css">
	<script src="../js/web.js"></SCRIPT>
	<script>
		function deleteAction(){
			var checkName="userId";
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
	        
			if(confirm("<fmt:message key='js.userdelete'/>"))
					document.forms[0].submit();
				else
					return false;
					
			}
    </script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(2,5)">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>	
	<br>  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td  align="center" valign="top"> <p><br>
        <br>
        <br>
        <br>
        <br>
        <img src="../images/UserLogo.jpg" width="102" height="101"><br>
        </p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <span class="bodytxt"><font color="#333333"></font></span> 
      <p>&nbsp;</p>
    </td>
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td  align="left" valign="top" width="80%">
	      <table width="96% border="0" cellspacing="0" cellpadding="0">
        <tr > 
          <td  align="center" valign="top" class="section">
          	<div class="bodyhdr"> 
              <div align="right"> 
                <p>
				<span class="section"> <fmt:message key="user.DeleteUser"/></span>  
                <select name="select" class="field" onChange="gotoPage(this.value)">
                    <option value="" selected><fmt:message key="form.AvailableActions"/></option>
                    <option value="/middleware/ListUserAction.do" ><fmt:message key="user.ListUser" /></option>
                    <option value="/middleware/EditUserAction.do" ><fmt:message key="user.CreateUser" /></option>
                    <option value="/middleware/ListUserAction.do?operation=delete"><fmt:message key="user.DeleteUser" /></option>
                  </select>
                <hr width="100%" size="1" noshade></p>
              </div>
            </div></td>
        </tr>
        <tr> 
          <td  align="center" valign="top"> <div align="left">
            <p><span class="bodytxt"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
                </span>
              </p>
	<html:form action="/DeleteUserAction"> 
	            <table width="500" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>
		<table width="100%"  border="0" cellspacing="2" cellpadding="2">
        <tr class="bg-gray" >
          <td align="center"><input type="checkbox" name="aa" onclick="allSelect(this,'userId')" ></td>
          <td class="bg-gray" >&nbsp;&nbsp;<fmt:message key="Name"/></td>
          <td class="bg-gray"><fmt:message key="Role"/></td>
          <td class="bg-gray"><fmt:message key="Description"/></td>
        </tr>
        
        <c:forEach var="userPo" items="${listUser}" varStatus="index"> 
        	<c:if test="${index.count%2==0}">
				<tr onmouseover="onover(this)"   onmouseout="onout(this)"  class="bg-shallow1" height="27"> 
			</c:if>
			<c:if test="${index.count%2==1}">
				<tr onmouseover="onover(this)"   onmouseout="onout(this)"  class="bg-shallow" height="27"> 
			</c:if>
          <td align="center"><html:checkbox property="userId" onclick="onClickCheckBox(this)" value="${userPo.id}" /></td>
          <td><a href="/middleware/EditUserAction.do?userid=${userPo.id}"><c:out value="${userPo.userName}" /></a></td>
          <td>
          <c:forEach var="rolelist" items="${userPo.roleType}">
          	<fmt:message key="role.${rolelist}"/> 
          </c:forEach> </td>
          <td><c:out value="${userPo.description}" /></td>
        </tr>
        </c:forEach> 
        

        <tr > 
          <td colspan="4" class="bg-shallow" align="center" > 
		  	<table>
				<tr>
              		<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
					<a onclick="deleteAction();" style="cursor:hand" ><fmt:message key="form.delete" /></a> 
              	</td>
              </tr>
              </table>
			  </td>
        </tr>
        <tr> 
          <td colspan="4" class="bg-shallow"></td>
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