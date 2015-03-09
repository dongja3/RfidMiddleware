<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head>
    <title><fmt:message key="delHttpCommandReply.title" /></title>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <script src="../js/web.js"></script>
    
    <script>
    
		function deleteHttpCommandAction(){
			var checkName = "httpCommandId";
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
	        
			if(confirm("<fmt:message key='javascript.httpcommandconfig.prompt.delete' />"))
					document.forms[0].submit();
				else
					return false;
			
		}
	</script>
		
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(0,1)">
<html:form action="DeleteHttpCommandReplyAction">
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>	
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td  valign="top"> <p></p>
      <p>&nbsp;</p> <p><br>
      </p></td>
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td  align="center" valign="top"> <p><br>
        <br>
        <br>
        <br>
        <br>
       <br>
         <img src="../images/systemLogo.jpg" width="102" height="101"><br>
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
                <p><span class="section"><fmt:message key="httpCommandReply.action.del" /></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
            <p><span class="bodytxt"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
                </span>
              </p>
       
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

				<!-- list of reader content-->
				<table width="100%"  border="0" cellspacing="2" cellpadding="2">
                  <tr >
                      <td class="bg-gray" align="center" nowrap><input type="checkbox" name="aa" onclick="allSelect(this,'httpCommandId')" ></td>
					 <td class="bg-gray" nowrap><fmt:message key="listHttpCommandReply.ip" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="listHttpCommandReply.port" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="listHttpCommandReply.path" /></td>
					  <td class="bg-gray" nowrap><fmt:message key="listHttpCommandReply.username" /></td>
				  </tr>
               
				  
			 <c:forEach var="httpCommandReplyList" items="${httpCommandReplyList}"  varStatus="index" >
				<c:if test="${index.count%2==1}">
				  <tr onmouseover="onover(this)"   onmouseout="onout(this)" ondblclick="ondblClickSelect(this,'httpCommandId')" class="bg-shallow" height="27">
				</c:if>
				<c:if test="${index.count%2==0}">
				  <tr onmouseover="onover(this)"   onmouseout="onout(this)" ondblclick="ondblClickSelect(this,'httpCommandId')" class="bg-shallow1" height="27">
				</c:if>
          
            <td nowrap align="center">
               <html:checkbox property="httpCommandId" onclick="onClickCheckBox(this)" value="${httpCommandReplyList.id}" />
			        </td>
            <td nowrap>&nbsp;&nbsp;
          <c:out value="${httpCommandReplyList.ip}" />
            </td>
              <td nowrap>&nbsp;&nbsp;
          <c:out value="${httpCommandReplyList.port}" />
              </td>
              <td nowrap>&nbsp;&nbsp;
               <c:out value="${httpCommandReplyList.path}" />
              </td>
              <td nowrap>&nbsp;&nbsp;<c:out value="${httpCommandReplyList.userName}" /></td>
            
            </tr>
       		</c:forEach>


                </table> 
				<!--end list of reader content-->

			 </td>
                <td width="1" bgcolor="#cccccc"></td>
              </tr> <tr>
			 <td width="1" bgcolor="#cccccc"></td>
             <td align="center">
				<table>
					<tr>
						<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
							<a onclick="deleteHttpCommandAction();" style="cursor:hand" ><fmt:message key="form.delete" /></a> 
						</td>
					</tr>
				</table>
			</td>
			<td width="1" bgcolor="#cccccc"></td>
            </tr>
              <tr>
                <td colspan="7" height=1 bgcolor="#cccccc"></td>
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