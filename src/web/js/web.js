var select="#c2bab6";
var onse="#c7f1cf";
var defaultBggGoudColor="#f9f9f9";
var defaultBggGoudColor1="#d9d9d9";

//Onmouseover tr will change color #99ffff
function onover(on){
	if(on.style.background!=select){
		on.style.background=onse;	
	}
}

//Onmouseout tr will change color #ffffff
function onout(out){
	if(out.style.background!=select){
		if (out.rowIndex%2==1){
			out.style.background=defaultBggGoudColor;
		}else{
			out.style.background=defaultBggGoudColor1;	
		}
   }
}

//on click checkbox will change tr's background color
function onClickCheckBox(checkbox){
	var clk =checkbox.parentElement.parentElement;

	if (clk.style.background==select) {
		if (clk.rowIndex%2==1) {
			clk.style.background=defaultBggGoudColor;
		} else {
			clk.style.background=defaultBggGoudColor1;	
		}
	} else{
		clk.style.background=select;
	}
}

//goto page
function gotoPage(index){
	if(index == ""){
	   return false;
	} else {
		window.location=index;
	}
}

function allSelect(checkbox,checkName){
	if (checkbox.checked){
		for (i=0;i<document.getElementsByName(checkName).length;i++){
			document.getElementsByName(checkName)[i].checked=true;
			document.getElementsByName(checkName)[i].parentElement.parentElement.style.background = select;
		}
	} else {
		for (i=0;i<document.getElementsByName(checkName).length;i++){
			document.getElementsByName(checkName)[i].checked="";

			if (document.getElementsByName(checkName)[i].parentElement.parentElement.rowIndex%2==1){
				document.getElementsByName(checkName)[i].parentElement.parentElement.style.background=defaultBggGoudColor;
			} else {
				document.getElementsByName(checkName)[i].parentElement.parentElement.style.background=defaultBggGoudColor1;	
			}
		}
	}
}

function ondblClickSelect(ondbl,checkName){
    alert(ondb1.rowIndex);
	var postion = ondbl.rowIndex - 3;

	if (document.getElementsByName(checkName)[postion].checked == true){
		document.getElementsByName(checkName)[postion].checked = "";
		ondbl.style.background = defaultBggGoudColor;
	} else {
		document.getElementsByName(checkName)[postion].checked = true;
		ondbl.style.background = select;
	}
}

function fnSetStyle(cur){
	cur.className="accessible";
}

function fnRemoveStyle(cur){
	cur.className="field";	
}

function isWhiteWpace (s){
	var whitespace = " \t\n\r";
	var i;

	for (i = 0; i < s.length; i++){   
		var c = s.charAt(i);

		if (whitespace.indexOf(c) >= 0) {
		    return true;
		}
	}

	return false;
}
			
function isSsnString (ssn){
	var re=/^[\w-.]*[\w-.]$/i;

	if(re.test(ssn))
		return true;
	else
		return false;
}

function isEmpty(value){
	//lable is form's text
	if(value == ""){
		return true;
	}else{
		return false;
	}
}

function isNumber(value){
	var e= /^\d*\d$/;

	if(e.test(value) && value <= 65535){
		return true;
	} else {
		return false;
	}
}
	
	
function isIP(value){
	x=value.substring(0,1);

	if (x == '.'|| x == '0'){
		return false;
	}

	if (isNumber(x)){
		var e= /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/i;

		if(e.test(value)){
			var t = value.split(".");
			if (t.length != 4)
			    return false;

			for (i=0;i<4;i++){
				if(t[i]>255||t[i]<0)
					break;
			}

			if (i==4&&t[3]>0)
				return true;
			else
				return false;
		} else {
			return false;
		}
	} else {
		return true;
    }
}

function isHexChar(x){
 	var t=/^[0-9a-f][\w]*[0-9a-f]$/i;

	if(t.test(x)){
		return true;
	} else {
		return false;
	}
}

function isIllegalTitleName(value){
	var e = /^[\w]*[\s]*[\w]*$/i;

	if(e.test(value)){
		return false;
	} else {
		return true;
	}
}

