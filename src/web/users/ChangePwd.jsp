<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:errors />
<html>
<head>
	<title><fmt:message key="changepassword.title" /></title>
	<link href="/middleware/css/css.css" rel="stylesheet" type="text/css">
	<script src="/middleware/js/web.js"></script>
	<script>		
        function ispsw (ssn){	
	        var re=/^[0-9a-z][\w-.]*[0-9a-z]$/i;
	        
	        if(re.test(ssn))
		        return true;
	        else
		       return false;
         }
         
         
		function clickSubmit(){

			var oldLength = document.forms[0].oldPassword.value;
			var reLength =  document.forms[0].rePassword.value;
			var newLength =  document.forms[0].newPassword.value;

			if(isEmpty(oldLength)){
				alert("<fmt:message key="js.AddUser.pswLength"/>")	
				document.forms[0].oldPassword.focus()
				return false;				
			}
						
			if(isEmpty(newLength)){
				alert("<fmt:message key="js.AddUser.pswLength"/>")	
				document.forms[0].newPassword.focus()
				return false;				
			}
			
			if(isEmpty(reLength)){
				alert("<fmt:message key="js.AddUser.pswLength"/>")	
				document.forms[0].rePassword.focus()
				return false;				
			}

			
			if(!ispsw(oldLength)){
				alert("<fmt:message key="js.AddUser.pswNotLegalityCharacter"/>");
				document.forms[0].oldPassword.focus();
				return false;
			}

			if(!ispsw(newLength)){
				alert("<fmt:message key="js.AddUser.pswNotLegalityCharacter"/>");
				document.forms[0].newPassword.focus();
				return false;
			}

			if(!ispsw(reLength)){
				alert("<fmt:message key="js.AddUser.pswNotLegalityCharacter"/>");
				document.forms[0].rePassword.focus();
				return false;
			}
			
			if(isWhiteWpace(oldLength)){
				alert("<fmt:message key="js.AddUser.pswNotWhite"/>")	
				document.forms[0].oldPassword.focus()
				return false;				
			}
						
			if(isWhiteWpace(newLength)){
				alert("<fmt:message key="js.AddUser.pswNotWhite"/>")	
				document.forms[0].newPassword.focus()
				return false;				
			}
			
			if(isWhiteWpace(reLength)){
				alert("<fmt:message key="js.AddUser.pswNotWhite"/>")	
				document.forms[0].rePassword.focus()
				return false;				
			}

				
			if(reLength != newLength){
				alert("<fmt:message key="js.AddUser.pswNotAlso"/>");
				document.forms[0].newPassword.focus();
				return false;
			}
		

	
			if(oldLength.length < 6){
				alert("<fmt:message key="js.AddUser.pswLength"/>");
				document.forms[0].oldPassword.focus();
				return false;
			}
			
					
			if(newLength.length<6){
				alert("<fmt:message key="js.AddUser.pswLength"/>");
				document.forms[0].newPassword.focus();
				return false;
			}
			
			if(reLength.length < 6){
				alert("<fmt:message key="js.AddUser.pswLength"/>");
				document.forms[0].rePassword.focus();
				return false;
			}
		
			if(confirm("<fmt:message key='javascript.changepassword.prompt.save' />")){
				document.forms[0].submit();
			}else{
				return false;
			}
		}
	</script>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(2,6)">

<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="2" />
</jsp:include></td></tr><tr><td>	


<BR>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr><html:form action="/ChangePasswordAction"> 
    <td  valign="top"></td>
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td  align="center" valign="top"> <p><br>
        <br>
        <br>
        <br>
        <br>
        <img src="/middleware/images/UserLogo.jpg" width="102" height="101"><br>
        <span class="bodytxt"></span></p>
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
                <p><span class="section"> <fmt:message key="changepassword.user" />&nbsp;<%=request.getRemoteUser()%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
                <hr width="100%" size="1" noshade></p>
              </div>
            </div></td>
        </tr>
        <tr> 
          <td  align="center" valign="top"> <div align="left">

            <table width="350" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

			<table width="100%"  border="0" cellspacing="2" cellpadding="2">
            <tr class="bg-shallow">
              <td colspan="2" >
             <font color="red">
                 <html:messages id="message" message="true">
                      <li><%=message%></li>
                 </html:messages>
			</font></td>
            </tr>
          <html:hidden property="username" value="<%=request.getRemoteUser()%>"  />
            <tr class="bg-shallow">
              <td width="40%" >&nbsp;<fmt:message key="changepassword.oldPassword" /></td>
              <td width="60%" ><html:password property="oldPassword" styleClass="field" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" redisplay="false"/></td>
            </tr>
             <tr class="bg-shallow">
              <td width="40%" >&nbsp;<fmt:message key="changepassword.newPassword" /></td>
              <td width="60%" ><html:password property="newPassword" styleClass="field" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" redisplay="false"/></td>
            </tr>
             <tr class="bg-shallow">
              <td width="40%" >&nbsp;<fmt:message key="changepassword.rePassword" /></td>
              <td width="60%" ><html:password property="rePassword" styleClass="field" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" redisplay="false"/></td>
            </tr>
            
            <tr class="bg-shallow">
              <td height="25" colspan="2" align="center">
			    <table>
				<tr>
              		<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
					<a onclick="clickSubmit();" style="cursor:hand" ><fmt:message key="form.Change" /></a> 
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
</td></tr><tr><td>
<%@ include file="/foot.jsp" %>
</td><tr></table>
</body>
</html>
</fmt:bundle>
<script>
document.forms[0].oldPassword.focus();
</script>