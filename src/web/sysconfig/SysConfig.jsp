<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
	<title><fmt:message key="sysconfig.title" /></title>
	<link href="../css/css.css" rel="stylesheet" type="text/css">
	<script src="../js/web.js"></script>
	<script>
		function submitAction(){
		
			if(document.forms[0].systemName.value=="" ){
				alert("<fmt:message key='javascript.sysconfig.sysname.valueIllegal' />");
				document.forms[0].systemName.focus();
				return false;
			}
			
			if(document.forms[0].queuesize.value==""){
				alert("<fmt:message key='javascript.sysconfig.queuesize.valueIllegal' />");
				document.forms[0].queuesize.focus();
				return;
			}
			
			if(!isNumber(document.forms[0].queuesize.value)){
				alert("<fmt:message key='javascript.sysconfig.queuesize.valueIllegal' />");
				document.forms[0].queuesize.focus();
				return false;
			}
			
			if(1000 > document.forms[0].queuesize.value || 10000 < document.forms[0].queuesize.value){
				alert("<fmt:message key='javascript.sysconfig.queuesize.valueIllegal' />");
				document.forms[0].queuesize.focus();
				return;
			}
			
			if(confirm("<fmt:message key='javascript.sysconfig.prompt.save' />")){
			
				if(document.forms[0].queueSizeBak.value!=document.forms[0].queuesize.value){
					alert("<fmt:message key='sysconfig.availability' />");
				}
				
				document.forms[0].submit();
				
			}else{
			    return false;
			}
		}
	</script>	
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(0,0)">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>	
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 	<html:form action="SaveSysConfigAction">
    <td  valign="top"> </td>
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
                <p><span class="section"><fmt:message key="sysconfig.title" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <hr width="100%" size="1" noshade></p>
              </div>
            </div></td>
        </tr>
        <tr> 
          <td  align="center" valign="top"> <div align="left">
            <p><span class="bodytxt"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
                </span>
              </p>
       
            <table width="450" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
              <tr class="bg-shallow">
              <td  nowrap width="40%" >&nbsp;&nbsp;<fmt:message key="sysconfig.middlewareName" /></td>
              <td  nowrap width="60%" ><html:text property="systemName"  maxlength="100" value="${sysconfig.systemName}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /></td>
            </tr>
           
            <tr class="bg-shallow">
              <td  nowrap width="40%" >&nbsp;&nbsp;<fmt:message key="sysconfig.queueSize" /></td>
              <td  nowrap width="60%" ><html:text property="queuesize"  value="${sysconfig.queueSize}" styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" /></td>
            </tr>
          
            
            <tr class="bg-shallow">
              <td colspan="2" align="center">&nbsp;</td>
            </tr>
            <tr class="bg-shallow">
              <td colspan="2" align="center">
				<table>
			   		<tr align="center">
						<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
							<a onclick="submitAction();" style="cursor:hand" ><fmt:message key="form.savedata"/></a> 
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
<input type="hidden" name="queueSizeBak" value="${sysconfig.queueSize}">
</html:form>
</td></tr><tr><td>
<%@ include file="/foot.jsp" %>
</td><tr></table>
</body>
</html>
</fmt:bundle>

<script>
document.forms[0].systemName.focus();
</script>


