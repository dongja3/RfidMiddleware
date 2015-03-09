<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="java.util.*"%>
<fmt:bundle basename="middleware">

<html:base/>
<html>
	<head>
		<title>
		<c:if test="${task.id == 0}">
			<fmt:message key="task.CreateTask" />
		</c:if>
        <c:if test="${task.id > 0}">
			<fmt:message key="task.EditingTask" />
		</c:if>
		</title>
		<link href="../css/css.css" rel="stylesheet" type="text/css">
		<script src="../js/web.js">
		</script>
		<script>
		var page=1
		var destinationSize=0
		var httpAddSize=175
		var fileAddSize=77
		var httppagesize=80
		var filepagesize=60
		var Httptext=
           	"<Table>"
			+"<tr class=\"bg-shallow\">"
            +" 	<td nowrap width=\"30%\" >&nbsp;&nbsp;<fmt:message key="task.http.ip"/></td>"
            +"	<td nowrap width=\"70%\" class=\"bg-shallow\">"
           	+"	<input type=\"text\" name=\"ipHTTP\" onblur=\"fnRemoveStyle(this)\" onfocus=\"fnSetStyle(this)\" class=\"field\" >"
			+"	</td>"
            +"</tr>"
            +"<tr class=\"bg-shallow\">"
            +"	<td nowrap width=\"30%\" >&nbsp;&nbsp;<fmt:message key="task.http.port"/></td>"
            +"	<td nowrap width=\"70%\" class=\"bg-shallow\">"
           	+"	<input type=\"text\" name=\"portHTTP\"  onblur=\"fnRemoveStyle(this)\" onfocus=\"fnSetStyle(this)\" class=\"field\">"
			+"	</td>"
            +"</tr>"
            +"<tr class=\"bg-shallow\">"
            +"  	<td nowrap width=\"30%\" >&nbsp;&nbsp;<fmt:message key="task.http.path"/></td>"
            +"	<td nowrap width=\"70%\" class=\"bg-shallow\">"
           	+"	<input type=\"text\" name=\"pathHTTP\"  onblur=\"fnRemoveStyle(this)\" onfocus=\"fnSetStyle(this)\" class=\"field\">"
			+"	</td>"
            +"</tr>"
            +"<tr class=\"bg-shallow\">"
            +"  	<td nowrap width=\"30%\" >&nbsp;&nbsp;<fmt:message key="task.http.userName"/></td>"
            +"	<td nowrap width=\"70%\" class=\"bg-shallow\">"
           	+"	<input type=\"text\" name=\"userNameHTTP\" onblur=\"fnRemoveStyle(this)\" onfocus=\"fnSetStyle(this)\" class=\"field\">"
			+"	</td>"
            +"</tr>"
            +"<tr class=\"bg-shallow\">"
            +"  	<td nowrap width=\"30%\" >&nbsp;&nbsp;<fmt:message key="task.http.password"/></td>"
            +"	<td nowrap width=\"70%\" class=\"bg-shallow\">"
           	+"	<input type=\"text\" name=\"passwordHTTP\" onblur=\"fnRemoveStyle(this)\" onfocus=\"fnSetStyle(this)\" class=\"field\">"
			+"	</td>"
            +"</tr>"
            +"<tr class=\"bg-shallow\">"
            +"<td  colspan=\"2\" align=\"center\">"
            +" 	<table ><tr>"
            +" 	<td width=\"50\" height=\"21\" background=\"/middleware/images/button_3.jpg\"  align=\"center\">"
            +" 	<a onclick=\"httpremove(this)\" style=\"cursor:hand\" ><fmt:message key='task.del'/></a>"
            +"	<td></tr></table></td>"
            +"</tr>"
            +"<tr class=\"bg-shallow\">"
            +"	<td colspan=\"2\"><hr height=\"1\"></td>"
            +"</tr>"
            +"</Table>";

        var filetext=
				"<Table>"
            	+"<tr class=\"bg-shallow\">"
	            +"	<td nowrap width=\"30%\" >&nbsp;&nbsp;<fmt:message key="task.file.folder"/></td>"
    	        +"	<td nowrap width=\"70%\" class=\"bg-shallow\">"
        		+"		<input value=\"\" type=\"text\" name=\"folderFile\"  onblur=\"fnRemoveStyle(this)\" onfocus=\"fnSetStyle(this)\" class=\"field\">"
				+"	</td>"
    	        +"</tr>"
            	+"<tr class=\"bg-shallow\">"
            	+"	<td colspan=\"2\" align=\"center\">"
            	+"	<table ><tr><td width=\"50\" height=\"21\" background=\"/middleware/images/button_3.jpg\"  align=\"center\">"
            	+"	<a onclick=\"fileremove(this)\" style=\"cursor:hand\" ><fmt:message key='task.del'/></a>"
            	+"	<td></tr></table></td>"
	            +"</tr>"
        	    +"<tr class=\"bg-shallow\">"
            	+"	<td colspan=\"2\"><hr height=1></td>"
	            +"</tr>"
    	        +"</Table>";

    	  var paramListBox="<select name=\"parameter\" class=\"field\">"
			+"<option value=\"1\" >GTIN</option>"
			+"<option value=\"2\">SSCC</option>"
			+"</select>"

			
	var paramInputBox="<input type=\"text\" name=\"parameter\" class=\"field\" onfocus=\"fnSetStyle(this)\" onblur=\"fnRemoveStyle(this)\"/>"

	function httpadd(){
		if(destinationSize<10){
			http.innerHTML = Httptext+http.innerHTML;
			//alert( http.innerHTML);
			httppagesize=httppagesize+httpAddSize;
			destinationSize=destinationSize+1;
			topage(3);
		}else{
			alert("<fmt:message key="task.js.maxDestinationSize"/>");
		}
	}

	function httpremove(index){
      if(confirm("<fmt:message key='form.sureDelete'/>")){
		index.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.outerHTML = "";
		//		alert( http.innerHTML);
		httppagesize=httppagesize-httpAddSize;
		destinationSize=destinationSize-1;
		topage(3);
		}
	}

	function fileadd(){
		if(destinationSize<10){
			file.innerHTML = filetext+file.innerHTML;
			//alert( http.innerHTML);
			filepagesize=filepagesize+fileAddSize;
			destinationSize=destinationSize+1;
			topage(4);
		}else{
			alert("<fmt:message key="task.js.maxDestinationSize"/>");
		}
	}

	function fileremove(index){
      if(confirm("<fmt:message key='form.sureDelete'/>")){
		index.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.outerHTML = "";
		//		alert( http.innerHTML);
		filepagesize=filepagesize-fileAddSize;
		destinationSize=destinationSize-1;
		topage(4);
	  }
	}

//-------------------------------------

		var groupid=new Array();
		var groupName=new Array();
		var deviceidList = new Array();
		var deviceList = new Array();

		<c:forEach var="deviceGroup" items="${task.groupIDList}" varStatus="s">
			groupid[<c:out value="${s.count-1}"/>]=<c:out value="${deviceGroup}"/>
		</c:forEach>
		<c:forEach var="deviceGroup" items="${task.groupNameList}" varStatus="s">
			groupName[<c:out value="${s.count-1}"/>]='<c:out value="${deviceGroup}"/>'
		</c:forEach>
		<c:forEach var="deviceidList" items="${task.deviceIDList}" varStatus="s">
				deviceidList[<c:out value="${s.count-1}"/>]='<c:out value="${deviceidList}"/>'
		</c:forEach>
		<c:forEach var="deviceList" items="${task.deviceNameList}" varStatus="s">
				deviceList[<c:out value="${s.count-1}"/>]='<c:out value="${deviceList}"/>'
		</c:forEach>

		function SelectGroup(){
				var cd = document.forms[0].devicesId;
				var cdl =cd.length;
				var selectid=document.forms[0].group.value;
				if (selectid == "") return false;
			 	var Tempid= deviceidList[selectid].split(",");
				var y=0;
				var Temp= deviceList[selectid].split(",");

				for(j=0 ;cd.length>0;j++){
					cd.options[0]=null;
				}
				if(deviceidList[selectid]!=''){
					for (i=0;i<Tempid.length;i++){
						cd[y++] = new Option(Temp[i],Tempid[i], false, false);
						cd.options[y-1].innerHTML=Temp[i]
					}
				}
			}

				function clickSubmit(){

					if(isEmpty(document.forms[0].taskName.value.replace(/(^\s*)|(\s*$)/g, ""))){
						topage(1);
						document.forms[0].taskName.focus();
						alert("<fmt:message key="task.js.taskNameNotEmpty"/>");
						return false;
					}

					if (document.forms[0].taskType.value=='A' && !checkTriggerMode()) {
						return false;
                    }

					if (document.forms[0].group.value == ""){
						topage(2);
						document.forms[0].group.focus();
						alert("<fmt:message key="task.js.selectedGroup"/>");
						return false;
					}

					document.forms[0].groupId.value = groupid[document.forms[0].group.value];
					
				
			    if(document.forms[0].devicesId.value==''){
			        topage(2);
			        document.forms[0].devicesId.focus();
			        alert("<fmt:message key="task.js.deviceEmpty"/>");
			        return false;
			     }
			     
					if(destinationSize<1){
						topage(3);
						alert ("<fmt:message key="task.js.destinationSizeLessThanOne"/>");
						return false;
					}

					if(!checkHttpDestination() || !checkFileDestination()) {
						return false;
                    }

					if(confirm("<fmt:message key="task.js.savetask"/>"))
						document.forms[0].submit();
					else
						return false;
				}


			function checkTriggerMode() {
				var value=document.forms[0].triggerMode.value
				if(value == 'T' || value == 'I' || value == 'N'){
					if (document.forms[0].parameter.value==""){
						topage(1);
						document.forms[0].parameter.focus()
						alert("<fmt:message key="task.js.inputParameter"/>");
						return false;
					}else{
						if(!isNumber(document.forms[0].parameter.value)){
							topage(1);
							document.forms[0].parameter.focus()
							alert("<fmt:message key="task.js.inputnumber"/>");
							return false;
						}else{
							if	(document.forms[0].parameter.value<=0){
								topage(1);
								document.forms[0].parameter.focus()
								alert("<fmt:message key="task.js.notzero"/>");
								return false;
							}
						}
					}
				}

				return true;
			}

			function checkHttpDestination(){
				if(document.forms[0].ipHTTP==undefined)
					return true;
				if(document.forms[0].ipHTTP.length==undefined){
					if(isEmpty(document.forms[0].ipHTTP.value.replace(/(^\s*)|(\s*$)/g, ""))){
						topage(3);
						alert("<fmt:message key="task.js.notspace"/>");
						document.forms[0].ipHTTP.focus();
						return false;
					}
					if(!isIP(document.forms[0].ipHTTP.value)){
						topage(3);
						alert("<fmt:message key="task.js.noisip"/>");
						document.forms[0].ipHTTP.focus();
						return false;
					}
					if(isEmpty(document.forms[0].portHTTP.value.replace(/(^\s*)|(\s*$)/g, ""))){
						topage(3);
						alert("<fmt:message key="task.js.portNoEmpty"/>");
						document.forms[0].portHTTP.focus();
						return false;
					}
					if(!isNumber(document.forms[0].portHTTP.value)){
						topage(3);
						alert("<fmt:message key="task.js.inputnumber"/>");
						document.forms[0].portHTTP.focus();
						return false;
					}
					if(isEmpty(document.forms[0].pathHTTP.value.replace(/(^\s*)|(\s*$)/g, ""))) {
						topage(3);
						alert("<fmt:message key="task.js.notspace"/>");
						document.forms[0].pathHTTP.focus();
						return false;
					}
				}else{
					for(a=0;a<document.forms[0].ipHTTP.length;a++){
						if(!isIP(document.forms[0].ipHTTP[a].value)){
							topage(3);
							alert("<fmt:message key="task.js.noisip"/>");
							document.forms[0].ipHTTP[a].focus();
							return false;
						}
						if(isEmpty(document.forms[0].portHTTP[a].value.replace(/(^\s*)|(\s*$)/g, ""))){
							topage(3);
							alert("<fmt:message key="task.js.portNoEmpty"/>");
							document.forms[0].portHTTP[a].focus();
							return false;
						}
						if(!isNumber(document.forms[0].portHTTP[a].value)){
							topage(3);
							alert("<fmt:message key="task.js.inputnumber"/>");
							document.forms[0].portHTTP[a].focus();
							return false;
						}
						if(isEmpty(document.forms[0].pathHTTP[a].value.replace(/(^\s*)|(\s*$)/g, ""))){
							topage(3);
							alert("<fmt:message key="task.js.notspace"/>");
							document.forms[0].pathHTTP[a].focus();
							return false;
						}
					}
				}

				return true;
			}

			  function checkFileDestination(){
				if(document.forms[0].folderFile==undefined)
					return true;
				if(document.forms[0].folderFile.length==undefined){
					if(isEmpty(document.forms[0].folderFile.value.replace(/(^\s*)|(\s*$)/g, ""))){
						topage(4);
						alert("<fmt:message key="task.js.notspace"/>");
						document.forms[0].folderFile.focus();
						return false;
					}
				}else{
					for(a=0;a<document.forms[0].folderFile.length;a++){
						
						if(isEmpty(document.forms[0].folderFile[a].value.replace(/(^\s*)|(\s*$)/g, ""))){
							topage(4);
							alert("<fmt:message key="task.js.notspace"/>");
							document.forms[0].folderFile[a].focus();
							return false;
					}
					}
				}
				return true;
			}

			function isPath(value){
			var s = value.substr(1,1)
				if (s==':'){
					s = value.substr(0,1)
					var re=/^\w*\w$/;
					if(re.test(s)&&s!='_'){
						s = value.substr(2,1);
						if (s == '\\' || s=='\/'){
						   	temp = value.substring(3,value.lenth);
						   	if (temp=="") return true;
							re = /\//g;
							temp = temp.replace(re, "");
							re = /\\/g;
							temp = temp.replace(re, "");
							var re=/^[\w-.]*[\w-.]$/i;
							if(re.test(temp))
								return true;
							else
								return false;
						}else{
							return false;}
					}else{
						return false;
					}
				}else{
					s = value.substr(0,1)
					if(s=='\\'||s=='\/'){
						temp = value
						re = /\//g;
						temp = temp.replace(re, "");
						re = /\\/g;
						temp = temp.replace(re, "");

						var re=/^[\w\s-.]*[\w\s-.]$/i;
						if(re.test(temp))
							return true;
						else
							return false;
					}else{
						return false;
					}
				}
			}

			function isPathHttp(value){

						temp = value
						re = /\//g;
						temp = temp.replace(re, "");
						re = /\\/g;
						temp = temp.replace(re, "");

						var re=/^[\w-.]*[\w-.]$/i;
						if(re.test(temp))
							return true;
						else
							return false;

			}


				function back(){
					topage(page-1);
				}
				function next(){
					topage(page+1);
				}

				function topage(i){
					if (i==1){
						b.style.visibility ="hidden";
						n.style.visibility ="visible";
						n1.style.visibility='visible'
						n2.style.visibility='hidden'
						n3.style.visibility='hidden'

						n6.style.visibility='hidden'
						document.getElementById('tableId').height='200px';
						document.forms[0].taskName.focus();
						space1.innerHTML="";
						space2.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						space3.innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						page=1;
					}
					if (i==2){
						b.style.visibility ="visible";
						n.style.visibility ="visible";
						n1.style.visibility='hidden'
						n2.style.visibility='visible'
						n3.style.visibility='hidden'
						n6.style.visibility='hidden'
						document.getElementById('tableId').height='200px';
						space1.innerHTML="&nbsp;&nbsp;";
						space2.innerHTML=""
						space3.innerHTML="";
						page=2;
					}
					if (i==3){
						b.style.visibility ="visible";
						n.style.visibility ="visible";
						n1.style.visibility='hidden'
						n2.style.visibility='hidden'
						n3.style.visibility='visible'

						n6.style.visibility='hidden'
						document.getElementById('tableId').height=httppagesize+'px';
						if (document.getElementById('tableId').height<200) document.getElementById('tableId').height=200
						space1.innerHTML="&nbsp;&nbsp;";
						space2.innerHTML="";
						space3.innerHTML="";
						page=3;
					}

					if (i==4){
						b.style.visibility ="visible";
						n.style.visibility ="hidden";
						n1.style.visibility='hidden'
						n2.style.visibility='hidden'
						n3.style.visibility='hidden'
						n6.style.visibility='visible'
						document.getElementById('tableId').height=filepagesize+'px';
						if (document.getElementById('tableId').height<200) document.getElementById('tableId').height=200
						space1.innerHTML="&nbsp;&nbsp;";
						space2.innerHTML="";
						space3.innerHTML="";
						page=4;
					}
				}

				function setTaskType(){
				type=document.forms[0].taskType.value;

					if (type=='M'){
						document.forms[0].startup.disabled=true;

						document.forms[0].triggerMode.value="A";
						document.forms[0].triggerMode.disabled=true;

					}else{
						document.forms[0].startup.disabled=false;

						document.forms[0].triggerMode.disabled=false;
					}
					setTriggerMode();
				}

			function setTriggerMode(){
		        type=document.forms[0].triggerMode.value;

				if (type=='I') {
				    parameter.innerText="<fmt:message key='task.IdlePeriod'/>";
				    parameterValue.innerHTML= paramInputBox;
				} else if (type=='N'){
				    parameter.innerText="<fmt:message key='task.ReadNumber'/>";
				    parameterValue.innerHTML= paramInputBox;
				} else if (type=='P'){
				    parameter.innerText="<fmt:message key='task.EncodingScheme'/>";
				    parameterValue.innerHTML= paramListBox;
				} else {
				    parameter.innerText="<fmt:message key="task.Parameter"/>";
					parameter.innerText="";
					parameterValue.innerHTML= "";
				}
            }
		</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init(3,7)">
<html:form action="/SaveTaskAction">
<html:hidden property="groupId"  />
<html:hidden property="dId" value="" />
<table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td>
<jsp:include page="../header.jsp">
<jsp:param name="index" value="1" />
</jsp:include></td></tr><tr><td>
	<br>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td nowrap  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td nowrap  align="center" valign="top"> <p><br>
        <br>
        <br>
        <br>
        <br>
        <img src="../images/TaskLogo.jpg" width="102" height="101"><br>
        <span class="bodytxt"></span></p>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <span class="bodytxt"><font color="#333333"></font></span>
      <p>&nbsp;</p>
    </td>
    <td nowrap  align="center" valign="top">&nbsp;&nbsp;&nbsp;</td>
    <td nowrap  align="left" valign="top" width="80%">
      <table width="96% border="0" cellspacing="0" cellpadding="0">
			<tr>
         			<td  align="center" valign="top" class="section">
          				<div class="bodyhdr">
			            <div align="right">
                		<p>
                	<span class="section">
                	<c:if test="${task.id == 0}">
						<fmt:message key="task.CreateTask" />
					</c:if>
                	<c:if test="${task.id > 0}">
						<fmt:message key="task.EditingTask" />
					</c:if>
					</span>
					<select class="field" onChange="gotoPage(this.value)">
						<option value="" selected><fmt:message key="form.AvailableActions"/></option>
        				<option value="/middleware/ListTaskAction.do" ><fmt:message key="task.ListTask" /></option>
	    			    <option value="/middleware/EditTaskAction.do?action=create" ><fmt:message key="task.CreateTask" /></option>
    				    <option value="/middleware/ListTaskAction.do?operation=delete"><fmt:message key="task.DeleteTask" /></option>
       				</select>
                <hr width="100%" size="1" noshade></p>
              </div>
			   <div align="left">
			   	 <font color="red">
                          <html:messages id="message" message="true">
                              <li><c:out value="${message}" /></li>
                          </html:messages>
				  </font><br><br>
			   </div>
            </div></td>
        </tr>
        <tr>
          <td nowrap  align="center" valign="top"> <div align="left">
            <p>
              </p>

            <table width="430" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td nowrap colspan="3" height=1 bgcolor="#cccccc"></td>
              </tr>
              <tr id="tableId" height="200">
                <td nowrap width="1" bgcolor="#cccccc"></td>
                <td nowrap width="100%" class="bg-shallow">


			<span id="n1" style="position:absolute ; width: 100%; height: 100%;">
			<table width="100%"  border="0" cellspacing="2" cellpadding="2">
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.taskName"/></td>
            	<td nowrap width="70%" class="bg-shallow">
				 <html:text property="taskName"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"  />	
         			<font color="red">*</font>				
			</td>
            </tr>
			<tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.TaskType"/></td>
            	<td nowrap width="70%" class="bg-shallow">
					<html:select property="taskType" styleClass="field" onchange="setTaskType()">
						<html:option value="M" ><fmt:message key="task.taskType.M"/></html:option>
						<html:option value="A" ><fmt:message key="task.taskType.A"/></html:option>
					</html:select>
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.Startup"/></td>
            	<td nowrap width="70%" class="bg-shallow">
            		<html:select property="startup" styleClass="field">
						<html:option value="A"><fmt:message key="task.startupType.A"/></html:option>
						<html:option value="M"><fmt:message key="task.startupType.M"/></html:option>
					</html:select>
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.TriggerMode"/></td>
            	<td nowrap width="70%" class="bg-shallow">
            		<html:select property="triggerMode" styleClass="field"  onchange="setTriggerMode()" >
			    <html:option value="A" ><fmt:message key="task.triggermode.A"/></html:option>
			    <html:option value="R" ><fmt:message key="task.triggermode.R"/></html:option>
			    <html:option value="E" ><fmt:message key="task.triggermode.E"/></html:option>
			    <html:option value="I" ><fmt:message key="task.triggermode.I"/></html:option>
			    <html:option value="N" ><fmt:message key="task.triggermode.N"/></html:option>
			    <html:option value="P" ><fmt:message key="task.triggermode.P"/></html:option>
			</html:select>
		</td>
            </tr>
            
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<span id ="parameter"><fmt:message key="task.Parameter"/></span></td>
            	<td nowrap width="70%" class="bg-shallow">
            	    <span id = "parameterValue"></span>	
		</td>
            </tr>
		
           <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.formattype"/></td>
            	<td nowrap width="70%" class="bg-shallow">
            		<html:select property="formatType" styleClass="field">
						<html:option value="F" ><fmt:message key="task.formatType.F"/></html:option>
						<html:option value="S"><fmt:message key="task.formatType.S"/></html:option>
					</html:select>
				</td>
            </tr>
          </table>
             </span>


          <span id="n2" style="position:absolute ; width: 100%; height: 100%;  visibility: hidden;overflow: auto;">
          <table width="100%"  border="0" cellspacing="2" cellpadding="2">

            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.command"/></td>
            	<td nowrap width="70%" class="bg-shallow">
            		<html:text property="command"  styleClass="field" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)" />&nbsp;
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.ReaderID"/></td>
            	<td nowrap width="70%" class="bg-shallow">
              		<html:select property="readerID" styleClass="field">
						<html:option value="N" >Device Name</html:option>
						<html:option value="I">Device ID</html:option>
					</html:select>
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.TopLevelID"/></td>
            	<td nowrap width="70%" class="bg-shallow">

            		<html:select property="topLevelID" styleClass="field">
						<html:option value="M" >Middleware Name</html:option>
						<html:option value="G" >Device Group Name</html:option>
					</html:select>
				</td>
            </tr>
              <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.Description"/>:</td>
            	<td nowrap width="70%" class="bg-shallow">
				<html:text property="description" styleClass="field" size="40" onfocus="fnSetStyle(this)" onblur="fnRemoveStyle(this)"  />&nbsp;
				</td>
            </tr>
            <tr class="bg-shallow">
            	<td nowrap colspan="2" class="bg-title-shallowegreen">
					&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="task.DistributeDevices"/>:
            	</td>
            </tr>
           </tr>
              <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.SelectGroup"/>:</td>
            	<td nowrap width="70%" class="bg-shallow">
            		<select name="group"  onchange="SelectGroup();"  style="width:250">
					</select>
				</td>
            </tr>
			<tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.SelectDevice"/>:</td>
            	<td nowrap width="70%" class="bg-shallow">
					<select name="devicesId" size="1"  style="width:250">

					</select>
				</td>
            </tr>
            </table>
           </span>

        <span id="n3" style="position:absolute ; width: 100%; height: 100%;  visibility: hidden;">

        	<table width="100%"  border="0" cellspacing="2" cellpadding="2">
            <tr>
            	<td colspan=2 align="center">
            	<table width="250"><tr><td align="center"><fmt:message key="task.httpDestination"/></td><td align="left">
            	<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
            	<a onclick="httpadd();" style="cursor:hand" ><fmt:message key="task.add"/></a>
            	<td></tr></table></td>
            </tr>
            <tr>
            	<td colspan="2"><hr height=1></td>
            </tr>

            <tr><td colspan="2" align="center">
            <span id="http" >
            <c:forEach var="http" items="${task.httpList}">
            <Table>
			<tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.http.ip"/></td>
            	<td nowrap width="70%" class="bg-shallow">
           		<input value="<c:out value="${http.ip}"/>" type="text" name="ipHTTP" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field" >
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.http.port"/></td>
            	<td nowrap width="70%" class="bg-shallow">
           		<input value="<c:out value="${http.port}"/>" type="text" name="portHTTP"  onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field">
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.http.path"/></td>
            	<td nowrap width="70%" class="bg-shallow">
           		<input value="<c:out value="${http.path}"/>" type="text" name="pathHTTP"  onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field">
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.http.userName"/></td>
            	<td nowrap width="70%" class="bg-shallow">
           		<input value="<c:out value="${http.username}"/>" type="text" name="userNameHTTP" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field">
				</td>
            </tr>
            <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.http.password"/></td>
            	<td nowrap width="70%" class="bg-shallow">
           		<input value="<c:out value="${http.password}"/>" type="text" name="passwordHTTP" onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field">
				</td>
            </tr>
            <tr class="bg-shallow">
            <td  colspan="2" align="center">
            	<table ><tr >
            	<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
            	<a onclick="httpremove(this);" style="cursor:hand" ><fmt:message key="task.del"/></a>
            	<td></tr></table></td>
            </tr>
            <tr class="bg-shallow">
            	<td colspan="2"><hr height=1></td>
            </tr>
            </Table>
            <script>
            	httppagesize=httppagesize+httpAddSize
            	destinationSize=destinationSize+1
            </script>
            </c:forEach>
            </span>
            </td><tr>
        	</table>
        </span>

        <span id="n6" style="position:absolute ; width: 100%; height: 100%;  visibility: hidden;">

        	<table width="100%"  border="0" cellspacing="2" cellpadding="2">

            <tr>
            	<td align="center"><fmt:message key="task.fileDestination"/></td><td align="left">
            	<table ><tr >
            	<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
            	<a onclick="fileadd();" style="cursor:hand" ><fmt:message key="task.add"/></a>
            	<td></tr></table></td>
            </tr>
            <tr>
            	<td colspan="2"><hr height=1></td>
            </tr>

            <tr><td colspan="2" align="center">
            <span id="file" >
            <c:forEach var="file" items="${task.fileList}">
            <Table>
	        <tr class="bg-shallow">
               	<td nowrap width="30%" >&nbsp;&nbsp;<fmt:message key="task.file.folder"/></td>
            	<td nowrap width="70%" class="bg-shallow">
           		<input value="<c:out value="${file.folder}"/>" type="text" name="folderFile"  onblur="fnRemoveStyle(this)" onfocus="fnSetStyle(this)" class="field">
				</td>
            </tr>
            <tr class="bg-shallow">
            	<td colspan="2" align="center">
            	<table ><tr><td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
            	<a onclick="fileremove(this);" style="cursor:hand" ><fmt:message key="task.del"/></a>
            	<td></tr></table></td>
            </tr>
            <tr class="bg-shallow">
            	<td colspan="2"><hr height=1></td>
            </tr>
            </Table>
            <script>
            	filepagesize=filepagesize+fileAddSize
            	destinationSize=destinationSize+1
            </script>
            </c:forEach>
            </span>
            </td><tr>
        	</table>
        </span>

				</td>
                <td nowrap width="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
               <td nowrap width="1" bgcolor="#cccccc"></td>
                <td nowrap height=2 bgcolor="#cccccc">

		<table width="100%"  border="0" cellspacing="0" cellpadding="0">

            <tr class="bg-shallow">
              <td nowrap colspan="2"  align="center">
              <table>
              <tr align="center">
              	<td id="b" width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
					<div><a  onclick="back();"  style="cursor:hand" ><fmt:message key="form.Back"/></a></div>
				</td>
				<td><span id ="space1"></span></td>
              	<td id="n" width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
					<div><a   onclick="next();" style="cursor:hand" ><fmt:message key="form.Next"/></a></div>
              	</td>
				<td><span id ="space2"></span>&nbsp;&nbsp;</td>
				<td width="50" height="21" background="/middleware/images/button_3.jpg"  align="center">
					<a onclick="clickSubmit();" style="cursor:hand" ><fmt:message key="form.savedata"/></a>
              	</td>
              	<td><span id ="space3"></span></td>
              </tr>
              </table>

              </td>
            </tr>
            <tr>
              <td nowrap colspan="2" class="bg-darkblue"></td>
            </tr>
        </table>

        </td>
         <td nowrap width="1" bgcolor="#cccccc"></td>
              </tr>
              <tr>
                <td nowrap colspan="3" height=2 bgcolor="#cccccc"></td>
              </tr>
            </table>
            <p>&nbsp;</p>
          </div></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
	<script>
	for (z=0;z<groupid.length;z++){
		document.forms[0].group[document.forms[0].group.length] = new Option(groupName[z],z, false, false);
	}

	for (z=0;z<groupid.length;z++){
		if (groupid[z] == document.forms[0].groupId.value)
			document.forms[0].group.value=z;
	}
	SelectGroup();
	if(document.forms[0].dId.value>0)
		document.forms[0].devicesId.value=document.forms[0].dId.value;

	<c:if test="${selectedDeviceId !=null and selectedDeviceId != ''}">
		document.forms[0].devicesId.value='<c:out value="${selectedDeviceId}" />';
	</c:if>
	topage(1);
	setTaskType();
	setTriggerMode();
	document.forms[0].taskName.focus();
	</script>
</html:form>
</td></tr><tr><td>
<%@ include file="/foot.jsp" %>
</td><tr></table>
	</body>
</html>
</fmt:bundle>