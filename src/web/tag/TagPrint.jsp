<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
<head><title><fmt:message key="writetag.title"/></title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script src="../js/web.js"></script>

<script>
	
	function submit(){
	    return true;
	}
	
	function setPringStyle(){
	    labelBlock.innerText="";
	    infoBlock1.innerText="";
	    infoBlock2.innerText="";
	    infoBlock3.innerText="";
	    infoBlock4.innerText="";
	    document.forms[0].writeLabel.value="";
	    document.forms[0].startTagId.value="";
	    document.forms[0].printNum.value="";
	}
	
	function batchsubmit(){
	    if (confirm("<fmt:message key="writetag.js.batchPrint"/>")) {
	        document.forms[0].printStyle.value="batch"
	        process.style.visibility = 'visible';
	        document.frames['processbar'].document.getElementById("abc").innerHTML = '<fmt:message key="writetag.batchPrint.innerHtml"/>';
	        document.forms[0].submit();
	    }
	}

</script>
</head>
<DIV id=process style="Z-INDEX: 1; LEFT: 431px; VISIBILITY: hidden; WIDTH: 337px; POSITION: absolute; TOP: 48px; HEIGHT: 185px">
    <iframe name="processbar" src="../reader/ProcessBar.htm" SCROLLING="no"  style="width:337px; height:185px;"></iframe>
</DIV>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(1,3)">
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
         <img src="../images/printer.jpg" width="102" height="101"><br>
      </p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <span class="bodytxt"><font color="#333333"></font></span> 
      <p>&nbsp;</p>
    </td>
    <td  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td  align="left" valign="top" width="80%"> 
	<html:form action="/TagPrint">
      <table width="96% border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td  align="center" valign="top"> <div class="bodyhdr"> 
              <div align="right"> 
                <p>
				<span class="section"><fmt:message key="writetag.title"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<select class="field" onChange="gotoPage(this.value)">
	            	<option value="" selected><fmt:message key="form.AvailableActions"/></option>
	              	<option value="/middleware/ListDevicesAction.do" ><fmt:message key="device.action.list" /></option>
              	<c:if test="${isAdministrator == 'yes'}">
		            <option value="/middleware/AddDeviceAction.do"><fmt:message key="device.action.create" /></option>
    		        <option value="/middleware/ListDevicesAction.do?userOperation=delete"><fmt:message key="device.action.delete" /></option>
    		    </c:if>
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
       
            <table  border="0" cellspacing="0" cellpadding="0" width="300">
              <tr >
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="0" bgcolor="#cccccc"></td>
                <td>


				<table  border="0" cellspacing="2" cellpadding="2" >
                	<tr class="bg-shallow" height="35">
					  	<td nowrap width="20%">&nbsp;&nbsp;<fmt:message key="addDevice.deviceName"/><br></td>
					 	<td norwap>
					 	    &nbsp;&nbsp;
					 	    <b><c:out value="${tagPrint.deviceName}"/></b>
							<input type="hidden" name="deivceId" value="${tagPrint.deviceId}"/>
					  	</td>
                	</tr>
					
					<tr class="bg-shallow" height="35">
						<td nowrap width="20%">&nbsp;&nbsp;<fmt:message key="writetag.printingLabel"/></td>
						<td nowrap>
						<b>
						<span id="labelBlock">
						    &nbsp;&nbsp;<font color="blue"><c:out value="${tagPrint.printingLabel}"/></font>
						 </span>
						</b>
						</td>
					</tr>	
					
					<tr class="bg-shallow">
						<td nowrap width="20%">&nbsp;&nbsp;<fmt:message key="writetag.algorithm"/></td>
						<td nowrap>
						    <select name="algorithm" Class="field" onChange="setPringStyle()">
						        <option value="HEX" <c:if test="${tagPrint.algorithm eq 'HEX'}">selected</c:if>><fmt:message key="writetag.algorithm.hex"/></option>
						        <option value="DEC" <c:if test="${tagPrint.algorithm eq 'DEC'}">selected</c:if>><fmt:message key="writetag.algorithm.dec"/></option>
					        </select>
						</td>						
					</tr>
						
					<tr class="bg-shallow">
						<td nowrap width="20%">&nbsp;&nbsp;<fmt:message key="writetag.writelabel"/></td>
						<td nowrap>
						<input type="text" name="writeLabel" value="${tagPrint.writeLabel}" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field">
						</td>
					</tr>
									
					<tr class="bg-shallow">
						<td nowrap width="20%">&nbsp;&nbsp;<fmt:message key="writetag.startTagid"/></td>
						<td nowrap>
							<input type="text" name="startTagId" value="${tagPrint.startTagId}" onblur="fnRemoveStyle(this);" onfocus="fnSetStyle(this);this.select()" class="field" >
						</td>						
					</tr>
					
					<tr class="bg-shallow">
						<td nowrap width="20%">&nbsp;&nbsp;<fmt:message key="writetag.increment"/></td>
						<td nowrap>
						    <select name="increment" Class="field">
						        <option value="YES" <c:if test="${tagPrint.increment eq 'YES'}">selected</c:if>><fmt:message key="writetag.increment.yes"/></option>
						        <option value="NO" <c:if test="${tagPrint.increment eq 'NO'}">selected</c:if>><fmt:message key="writetag.increment.no"/></option>
					        </select>
						</td>						
					</tr>
					
					<tr class="bg-shallow">
						<td nowrap width="20%">&nbsp;&nbsp;<fmt:message key="writetag.count"/></td>
						<td nowrap>					
						    <input type="text" name="printNum" value="${tagPrint.printNum}" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field">
						</td>						
					</tr>
					
					<tr class="bg-shallow">
						<td nowrap colspan="2" align="center">
						    <b><font color="green">
						    <span id="infoBlock1">
						        <c:if test="${printedNumber != null}">
						            <fmt:message key="writetag.printedNumber"/>
						            <c:out value="${printedNumber}"/>
						        </c:if>
						     </span> 
						        &nbsp;&nbsp;&nbsp;&nbsp;
						    </font></b>
						</td>
					</tr>
					
					<tr class="bg-shallow">
						<td nowrap colspan="2" align="center">
						    <b><font color="green">
						    <span id="infoBlock2">
						        <c:out value="${printedTag}"/>
						    </span>&nbsp;
						    </font></b>
						</td>
					</tr>
					
					<tr class="bg-shallow">
						<td nowrap colspan="2" align="center">
						    <b><font color="green">
						     <span id="infoBlock3">
						         <fmt:message key="writetag.${printInfo}"/>
						     </span>&nbsp;
						    </font></b>
						</td>
					</tr>
					<tr class="bg-shallow">
						<td nowrap colspan="2" align="center">
						    <b><font color="red">
						     <span id="infoBlock4">
						        <fmt:message key="writetag.${printError}"/>
						     </span>&nbsp;
						    </font></b>
						</td>
					</tr>
					
					<tr class="bg-shallow">
						<td colspan="2" align="center">
						<table>
			   	          <tr align="center">
							<td width="78" height="21" background="/middleware/images/button_80.jpg"  align="center">
								<a onclick="submit();" style="cursor:hand" ><fmt:message key="writetag.itemPrint"/></a> 
		              		</td>
		              		<td width="50">
		              		    <html:hidden property="printStyle" value=""/>
		              		</td>
		              		<td width="78" height="21" background="/middleware/images/button_80.jpg"  align="center">
								<a onclick="batchsubmit();" style="cursor:hand" ><fmt:message key="writetag.batchPrint"/></a>  
		              		</td>
	    		          </tr>
    	        		</table>
						</td>
					</tr>
					
                </table> 


				</td>
                <td width="0" bgcolor="#cccccc"></td>
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