<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
    <head>
        <title><fmt:message key="user.ListUser"/></title>
        <link href="../css/css.css" rel="stylesheet" type="text/css">
        <script src="../js/web.js"></script>
     </head>
     
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onload="init(2,5)">
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
	        <img src="../images/UserLogo.jpg" width="102" height="101">
	    </td>
    	<td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    	<td  align="left" valign="top" width="80%"> 
      		<table width="96% border="0" cellspacing="0" cellpadding="0">
        		<tr> 
          			<td  align="center" valign="top" class="section">
          				<div class="bodyhdr"> 
			            <div align="right"> 
                		<p><span class="section"><fmt:message key="user.ListUser"/></span>
                		<select class="field" onChange="gotoPage(this.value)">
		              		<option value="" selected><fmt:message key="form.AvailableActions"/></option>
	        		      	<option value="/middleware/ListUserAction.do"><fmt:message key="user.ListUser" /></option>
		            		<option value="/middleware/EditUserAction.do" ><fmt:message key="user.CreateUser" /></option>
			    	        <option value="/middleware/ListUserAction.do?operation=delete""><fmt:message key="user.DeleteUser" /></option>
       					</select>
						<hr width="100%" size="1" noshade></p>
						
			            </div>
						<div align="left">
 						 <font color="red">
                          <html:messages id="message" message="true">
                              <li><c:out value="${message}" /></li>
                          </html:messages>
					     </font>
						  <br>
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
								    	<td class="bg-gray">&nbsp;&nbsp;<fmt:message key="Name" /></td>
									    <td class="bg-gray"><fmt:message key="Description" /></td>
									    <td class="bg-gray"><fmt:message key="Role" /></td>
									</tr>
									<c:forEach var="user" items="${listUser}"  varStatus="index" >
										<c:if test="${index.count%2==1}">
											<tr onmouseover="onover(this)" onmouseout="onout(this)" class="bg-shallow" height="27">
										</c:if>
										<c:if test="${index.count%2==0}">
											<tr onmouseover="onover(this)" onmouseout="onout(this)" class="bg-shallow1" height="27">
										</c:if>
            							<td >&nbsp;&nbsp;
						        		    <A HREF="/middleware/EditUserAction.do?userid=${user.id}">
											<c:out value="${user.userName}"/></A>
										</td>
									    <td><c:out value="${user.description}"/>&nbsp;</td>
								    	<td>
										<c:forEach var="role" items="${user.roleType}" >
											<fmt:message key="role.${role}"/><br>
										</c:forEach>
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