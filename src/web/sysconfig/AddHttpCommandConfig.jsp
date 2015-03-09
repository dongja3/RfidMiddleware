<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
</head>
	<title><fmt:message key="addHttpCommandReply.title" /></title>
	<link href="../css/css.css" rel="stylesheet" type="text/css">
	<script src="../js/web.js"></script>
	
	<script>
	
		function httpCommandSubmit(){
			var ip = document.forms[0].ip.value.replace(/(^\s*)|(\s*$)/g, "");
			var port = document.forms[0].port.value.replace(/(^\s*)|(\s*$)/g, "");
			var path = document.forms[0].path.value.replace(/(^\s*)|(\s*$)/g, "");

			if(isEmpty(ip)){
				alert("<fmt:message key='javascript.httpcommandconfig.ip.illegal' />");
				document.forms[0].ip.focus();
				return false;
			}
			
			if(isEmpty(port)){
				alert("<fmt:message key='javascript.httpcommandconfig.port.illegal' />");
				document.forms[0].port.focus();
				return false;
			}
			
			if(isEmpty(path)){
				alert("<fmt:message key='javascript.httpcommandconfig.path.illegal' />");
				document.forms[0].path.focus();
				return false;
			}else{
				var e =/^([/]|[\w])([\w]+[/]?[\w]*)*$/;
				if(!e.test(path)){
					alert("<fmt:message key='javascript.httpcommandconfig.path.illegal' />");
					document.forms[0].path.focus();
					return;
				}
			
			}
			
			if(!isIP(ip)){
				alert("<fmt:message key='javascript.httpcommandconfig.ip.illegal' />");
				document.forms[0].ip.focus();
				return false;
			}
			
			if(!isNumber(port) || port>65535 || port<=0){
				alert("<fmt:message key='javascript.httpcommandconfig.port.illegal' />");
				document.forms[0].port.focus();
				return false;
			}

			if(confirm("<fmt:message key='javascript.httpcommandconfig.prompt.save' />")){
				document.forms[0].submit();
			}else{
				return false;
			}
		
		}
	</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(0,1)">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>	
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
  <html:form action="SaveHttpCommandReplyAction">
	<html:hidden property="id" value="${httpcommand.id}" />
    <td  valign="top"></td>
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td  align="center" valign="top"> <p><br>
        <br>
        <br>
        <br>
        <br>
        <img src="../images/systemLogo.jpg" width="102" height="101"><br>
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
			          <fmt:message key="addHttpCommandReply.title" />
              		</span>
              <html:hidden property="id" value="${httpcommand.id}" />
			   <select class="field" onChange="gotoPage(this.value)">
              	    <option value="" selected><fmt:message key="form.AvailableActions"/></option>
              	  	<option value="/middleware/ListHttpCommandReplyAction.do" ><fmt:message key="httpCommandReply.action.list" /></option>
	            <option value="/middleware/AddHttpCommandReplyAction.do"><fmt:message key="httpCommandReply.action.add" /></option>
    	        <option value="/middleware/ListHttpCommandReplyAction.do?userOperation=delete"><fmt:message key="httpCommandReply.action.del" /></option>
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
       
            <table width="300" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                 
			<tr class="bg-shallow">
              <td width="30%"  >&nbsp;&nbsp;<fmt:message key="listHttpCommandReply.ip" /></td>
              <td width="70%" ><html:text property="ip"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/><font color='red'>*</font></td>
            </tr>
           <tr class="bg-shallow">
              <td width="30%"  >&nbsp;&nbsp;<fmt:message key="listHttpCommandReply.port" /></td>
              <td width="70%" ><html:text property="port" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/><font color='red'>*</font></td>
            </tr>
            <tr class="bg-shallow">
              <td width="30%"  >&nbsp;&nbsp;<fmt:message key="listHttpCommandReply.path" /></td>
              <td width="70%" ><html:text property="path"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/><font color='red'>*</font></td>
            </tr>
            <tr class="bg-shallow">
              <td width="30%"  >&nbsp;&nbsp;<fmt:message key="listHttpCommandReply.username" /></td>
              <td width="70%" ><html:text property="username"   styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
              <tr class="bg-shallow">
              <td width="30%"  >&nbsp;&nbsp;<fmt:message key="listHttpCommandReply.password" /></td>
              <td width="70%" ><html:text property="password"   styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"/></td>
            </tr>
                    
            <tr class="bg-shallow">
              <td colspan="2"  align="center">&nbsp;</td>
            </tr>
            <tr class="bg-shallow">
              <td colspan="2"  align="center">
				<table>
			   		<tr align="center">
						<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
							<a onclick="httpCommandSubmit();" style="cursor:hand" ><fmt:message key="form.savedata"/></a> 
		              	</td>
	    		    </tr>
    	  		</table></td>
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
document.forms[0].ip.focus();
</script>