<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<fmt:bundle basename="middleware">
<html:base/>
<html>
	<head>
        <title><fmt:message key="user.CreateUser" /></title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script src="../js/web.js"></script>
		
		<script>
		    var groupid=new Array(<c:out value="${user.groupSize}"/>);
		    var groupName=new Array(<c:out value="${user.groupSize}"/>);
		    var deviceidList = new Array(<c:out value="${user.groupSize}"/>);
		    var deviceList = new Array(<c:out value="${user.groupSize}"/>);
		
		    <c:forEach var="deviceGroupPO" items="${user.deviceGroup}" varStatus="s"> 
			    groupid[<c:out value="${s.count-1}"/>]=<c:out value="${deviceGroupPO.readerGroupId}"/>
			    groupName[<c:out value="${s.count-1}"/>]='<c:out value="${deviceGroupPO.readerGroupName}"/>'
		    </c:forEach>
		
		    <c:forEach var="deviceidList" items="${user.deviceidList}" varStatus="s"> 
				deviceidList[<c:out value="${s.count-1}"/>]='<c:out value="${deviceidList}"/>'
		    </c:forEach>
		
		    <c:forEach var="deviceList" items="${user.deviceList}" varStatus="s"> 
				deviceList[<c:out value="${s.count-1}"/>]='<c:out value="${deviceList}"/>'
		    </c:forEach>		

			function AddGroup(){
				var group = document.forms[0].ReaderGroup;
				var sReader = document.forms[0].selectedReader;
				
				if (group.selectedIndex==-1)
					{return false;}	
								
				for (x=0;x<groupid.length;x++){
					if (groupid[x]==group.options[group.selectedIndex].value)
						break;
				}
				
				if (x>=groupid.length)
					return false;
					
				if (deviceidList[x]!='' && deviceList[x]!=''){
					var devIdList= deviceidList[x].split(",");
					var devList =  deviceList[x].split(",");
					for (i=0;i<devList.length;i++){
						for (j = 0 ; j < document.forms[0].selectedReader.length; j++) {
							if (sReader.options[j].value==devIdList[i]){
							  break;
							}
						}
						
						if (j>=document.forms[0].selectedReader.length){
							sReader[document.forms[0].selectedReader.length] = new Option(devList[i],devIdList[i], false, false);
							sReader.options[document.forms[0].selectedReader.length-1].innerHTML=devList[i]
						}
						
						for (y = 0 ; y < document.forms[0].unselectedReader.length; y++) {
							if (document.forms[0].unselectedReader.options[y].value==devIdList[i]){
							  document.forms[0].unselectedReader.options[y]=null;
							}
						}
						
					}
				}
			}							

			
			function SelectAdd(){
				var sReader = document.forms[0].selectedReader;
				var uReader = document.forms[0].unselectedReader;
				
				if (sReader.selectedIndex==-1)
					return false;
					
				for (i = 0 ; i < uReader.length; i++) {
					if (uReader.options[i].value==sReader.options[sReader.selectedIndex].value) 
					return false;
				    }
				uReader[uReader.length] = new Option(sReader.options[sReader.selectedIndex].text, sReader.options[sReader.selectedIndex].value, false, false);
				sReader.options[sReader.selectedIndex] = null;
				}
				
			function SelectAddAll(){
				var sReader = document.forms[0].selectedReader;
				var uReader = document.forms[0].unselectedReader;
				var uReaderLength=uReader.length;	
				
					for (i = 0 ; i < uReaderLength; i++) {
						sReader[sReader.length] = new Option(uReader.options[0].text, uReader.options[0].value, false, false);
						uReader.options[0] = null;
					}
					
				}
				
			function SelectDelAll(){
				var uReader = document.forms[0].unselectedReader;
				var sReader = document.forms[0].selectedReader;
				var sReaderLength=sReader.length;	
				
					for (i = 0 ; i < sReaderLength; i++) {
						uReader[uReader.length] = new Option(sReader.options[0].text, sReader.options[0].value, false, false);
						sReader.options[0] = null;
					}
					
				}
				
				
			function SelectDel(){	
					if (document.forms[0].unselectedReader.selectedIndex==-1)
						return false;
					var sReader = document.forms[0].selectedReader;
					var uReader = document.forms[0].unselectedReader;
					sReader[sReader.length] = new Option(uReader.options[uReader.selectedIndex].text, uReader.options[uReader.selectedIndex].value, false, false);
					document.forms[0].unselectedReader.options[document.forms[0].unselectedReader.selectedIndex] = null;
				}
		function add(){
			for(i=0;document.forms[0].unselectedReader.selectedIndex!=-1;i++)
			SelectDel();
		
		}
			
		function del(){
			for(i=0;document.forms[0].selectedReader.selectedIndex!=-1;i++)
			SelectAdd();
		
		}
				
       function isUserName (ssn){	
	       var re=/^[0-9a-z][\w-.]*[0-9a-z]$/i;
	       
	       if(re.test(ssn))
		       return true;
	       else
		       return false;
       }

			function clickSubmit(){
					var ssn=document.forms[0].userName.value.toLowerCase();
					if (!checkUserName(ssn)){
						return false;
					}
			
					if (!checkpsw()){
						return false;
					}
			
					var sReader = document.forms[0].selectedReader;
					var sReaderIDs="";
					if (sReader.length>0){
						sReaderIDs = sReader.options[0].value
						for (j=1;j < sReader.length;j++)
						{
							sReaderIDs = sReaderIDs + ","  + sReader.options[j].value;
						}
					}
					document.forms[0].readerIds.value=sReaderIDs;
	
					if(confirm("<fmt:message key="js.usersave"/>"))
						document.forms[0].submit();
					else
						return false;
				}
				
			function checkUserName(ssn){
				ssn=ssn.replace(/(^\s*)|(\s*$)/g, "")
				if (isWhiteWpace(ssn)){
					alert("<fmt:message key="js.AddUser.userNameNotWhite"/>")
					document.forms[0].userName.focus()
					return false;
				}
				if(isEmpty(ssn)){
					alert("<fmt:message key="js.AddUser.userNameNotSpace"/>")
					document.forms[0].userName.focus()
					return false;
				}
				return true;
			}
			
			function checkpsw() {
				var psw=document.forms[0].password.value
				if( document.forms[0].repsw.value != psw) {
					alert("<fmt:message key="js.AddUser.pswNotAlso"/>")
					document.forms[0].password.focus()
					return false;
				}
				if( psw.length < 6) {
					alert("<fmt:message key="js.AddUser.pswLength"/>")
					document.forms[0].password.focus()
					return false;
				}
				if(isWhiteWpace(psw)){
					alert("<fmt:message key="js.AddUser.pswNotWhite"/>")	
					document.forms[0].password.focus()
					return false;				
				}
				if (!isUserName(psw)){
					alert("<fmt:message key="js.AddUser.userNameNotLegalityCharacter"/>")
					document.forms[0].password.focus()
					return false;
				}
				if( psw == document.forms[0].userName.value ) {
					alert("<fmt:message key="js.AddUser.PswNotAlseUserName"/>")
					document.forms[0].password.focus()
					return false;
				}
				return true;
			}

		function selectRole(role){
			type=role.value;
			if (type=='Administrator'){
				block1.style.visibility='hidden'
				document.getElementById('tableId').height='10px';
			}else{
				block1.style.visibility='visible'
				document.getElementById('tableId').height='220px';
			}
		}
		</script>
	</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(2,5)">
<html:form action="/SaveUserAction">
<html:hidden property="readerIds" value="" />
<html:hidden property="id" value="0" />
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
                <p><span class="section"><fmt:message key="user.CreateUser" /></span>
					<select class="field" onChange="gotoPage(this.value)">
						<option value="" selected><fmt:message key="form.AvailableActions"/></option>
        				<option value="/middleware/ListUserAction.do" ><fmt:message key="user.ListUser" /></option>
	    			    <option value="/middleware/EditUserAction.do?action=create" ><fmt:message key="user.CreateUser" /></option>
    				    <option value="/middleware/ListUserAction.do?operation=delete"><fmt:message key="user.DeleteUser" /></option>
       				</select>
       				<hr width="100%" size="1" noshade></p>
              </div>
              <div align="left">
              <font color="red">
                 <html:messages id="message" message="true">
                      <li><c:out value="${message}" /></li>
                 </html:messages>
			  </font>
              </div><br><br>
            </div></td>
        </tr>
        <tr> 
          <td  align="center" valign="top"> <div align="left">
            <table width="480" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td width="1" bgcolor="#cccccc"></td>
                <td>

			<table width="100%"  border="0" cellspacing="2" cellpadding="2">
             <tr class="bg-shallow">
               <td width="30%" >&nbsp;&nbsp;<fmt:message key="user.UserName"/></td>            
			<td width="70%" class="bg-shallow">
				<html:text property="userName" maxlength="100" size="20" styleClass="field" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" /></td>
            </tr>
            <tr class="bg-shallow">
	            <td width="30%" >&nbsp;&nbsp;<fmt:message key="user.Password"/></td>  
            <td width="70%" >
				<input type="password" maxlength="100" name="password" size="20" styleClass="field" value="" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field"></td>
            </tr>
            <tr class="bg-shallow">
	            <td width="30%" >&nbsp;&nbsp;<fmt:message key="user.rePassword"/></td>  
			<td width="70%" >
				<input type="password" maxlength="100" name="repsw" size="20" styleClass="field" value="" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field"></td>
            </tr>      
			<tr class="bg-shallow">
				<td width="30%" >&nbsp;&nbsp;<fmt:message key="user.RoleType" /></td>
				<td width="70%" >
				<html:select property="role" styleClass="field" onchange="selectRole(this)">
					<html:option value="Administrator"><fmt:message key="role.Administrator"/></html:option>
					<html:option value="Operator"><fmt:message key="role.Operator"/></html:option>
					<html:option value="Viewer"><fmt:message key="role.Viewer"/></html:option>
					<html:option value="ExternalUser"><fmt:message key="role.ExternalUser"/></html:option>
        		</html:select>
				</td>
            </tr>
			<tr class="bg-shallow">
              	<td width="30%" >
              		&nbsp;&nbsp;<fmt:message key="Description"/>
              	</td>
				<td width="70%" >
				<html:text property="description" maxlength="200" size="40" styleClass="field" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" /></td>

				</td>
			</tr>
			
			<tr id="tableId"  hight="220" class="bg-shallow"><td colspan="2">
			
			<span id="block1" style="position:absolute ; width: 100%; height: 100%;"> 
			<table>
			<tr class="bg-shallow">
				<td colspan="2" class="bg-title-shallowegreen">&nbsp;&nbsp;<fmt:message key="user.DistributeReaders"/></td>
            </tr>
            <tr class="bg-shallow">
            <td colspan="2" >
				<table width="100%">
					<tr align="center" >
						<td><div height="20"><br><fmt:message key="user.ReaderGroup"/><br></div></td>
						<td>&nbsp;</td>
						<td><div height="20"><br><fmt:message key="user.Selected"/><br></div></td>
						<td>&nbsp;</td>
						<td><div height="20"><br><fmt:message key="user.UnSelected"/><br></div></td>
					</tr>
					<tr >
						<td align="right" rowspan=2>
							<select   name="ReaderGroup" size="10" ondblclick="AddGroup();" style="width:180">
							<c:forEach var="deviceGroup" items="${user.deviceGroup}"> 
								<option value="<c:out value="${deviceGroup.readerGroupId}"/>">
									<c:out value="${deviceGroup.readerGroupName}" />
								</option>
							</c:forEach>
							</select>
						</td>
						<td width=60 align="center">
							<img src="../images/add.gif" onclick="AddGroup();" title='Add' style="cursor:hand"><br><br><br>
						</td>									
						<td align="right" rowspan=2>
							<select multiple name="selectedReader" size="10" ondblclick="SelectAdd();" style="width:180">
							<c:forEach var="selectDevice" items="${user.selectDevice}"> 
								<option value="<c:out value="${selectDevice.id}"/>">
									<c:out value="${selectDevice.deviceName}" />
								</option>
							</c:forEach>
							</select>
						</td>
						<td width=60 align="center">
							<img src="../images/del.gif" onclick="add();" title='Add' style="cursor:hand">
							<img src="../images/add.gif" onclick="del();" title='Remove' style="cursor:hand">
							<img src="../images/bit_del.gif" onclick="SelectAddAll();" title='Add All Devices' style="cursor:hand">							
							<img src="../images/bit_add.gif" onclick="SelectDelAll();" title='Remove All Devices' style="cursor:hand">
						</td>
						<td>
							<select multiple  name="unselectedReader" size="10" ondblclick="SelectDel();" style="width:180">
							<c:forEach var="unselectDevice" items="${user.unselectDevice}"> 
								<option value="<c:out value="${unselectDevice.id}"/>">
									<c:out value="${unselectDevice.deviceName}" />
								</option>
							</c:forEach>
							</select>
						</td>
					</tr>
					</table>
					</span>
					<script>
					document.getElementById('tableId').height='220px';
					</script>
					</td>
					</tr>
				</table>
            </td>
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
            <tr>
              <td colspan="2" class="bg-darkblue"></td>
            </tr>
        </table>
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
<SCRIPT LANGUAGE="JavaScript">
    document.forms[0].userName.focus();
    block1.style.visibility='hidden'
    document.getElementById('tableId').height='10px';
</SCRIPT>
</html:form>
</td></tr><tr><td>
<%@ include file="/foot.jsp" %>
</td><tr></table>
	</body>
</html>
</fmt:bundle>
       