// Flash Player Version Detection - Rev 1.5
// Detect Client Browser type
// Copyright(c) 2005-2006 Adobe Macromedia Software, LLC. All rights reserved.

//add by T.Ogawa    2010/04/03
var _strUserAgent = "";
if(typeof HK_UserAgent == 'function') {
	_strUserAgent = HK_UserAgent();			//HK_UserAgent(servlet)
}
if (_strUserAgent == null || _strUserAgent == "") {
	_strUserAgent = navigator.appVersion;
}
//add fin

var isIE  = (_strUserAgent.indexOf("MSIE") != -1) ? true : false;
var isWin = (_strUserAgent.toLowerCase().indexOf("win") != -1) ? true : false;
var isOpera = (_strUserAgent.indexOf("Opera") != -1) ? true : false;

//add by zhangfei   2007/10/31  16:00
var _info = _strUserAgent;
var _ns = false;
var _ns6 = false;
var _ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);
_ns = (navigator.appName.indexOf("Netscape") >= 0 && ((_info.indexOf("Win") > 0 && _info.indexOf("Win16") < 0 && java.lang.System.getProperty("os.version").indexOf("3.5") < 0) || (_info.indexOf("Sun") > 0) || (_info.indexOf("Linux") > 0) || (_info.indexOf("AIX") > 0) || (_info.indexOf("OS/2") > 0) || (_info.indexOf("IRIX") > 0)));
_ns6 = ((_ns == true) && (_info.indexOf("Mozilla/5") >= 0));
var isFirefox = _info.indexOf("Firefox") >= 0

function ControlVersion()
{
	var version;
	var axo;
	var e;

	// NOTE : new ActiveXObject(strFoo) throws an exception if strFoo isn't in the registry

	try {
		// version will be set for 7.X or greater players
		axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
		version = axo.GetVariable("$version");
	} catch (e) {
	}

	if (!version)
	{
		try {
			// version will be set for 6.X players only
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
			
			// installed player is some revision of 6.0
			// GetVariable("$version") crashes for versions 6.0.22 through 6.0.29,
			// so we have to be careful. 
			
			// default to the first public version
			version = "WIN 6,0,21,0";

			// throws if AllowScripAccess does not exist (introduced in 6.0r47)		
			axo.AllowScriptAccess = "always";

			// safe to call for 6.0r47 or greater
			version = axo.GetVariable("$version");

		} catch (e) {
		}
	}

	if (!version)
	{
		try {
			// version will be set for 4.X or 5.X player
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3");
			version = axo.GetVariable("$version");
		} catch (e) {
		}
	}

	if (!version)
	{
		try {
			// version will be set for 3.X player
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3");
			version = "WIN 3,0,18,0";
		} catch (e) {
		}
	}

	if (!version)
	{
		try {
			// version will be set for 2.X player
			axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
			version = "WIN 2,0,0,11";
		} catch (e) {
			version = -1;
		}
	}
	
	return version;
}

// JavaScript helper required to detect Flash Player PlugIn version information
function GetSwfVer(){
	// NS/Opera version >= 3 check for Flash plugin in plugin array
	var flashVer = -1;
	
	if (navigator.plugins != null && navigator.plugins.length > 0) {
		if (navigator.plugins["Shockwave Flash 2.0"] || navigator.plugins["Shockwave Flash"]) {
			var swVer2 = navigator.plugins["Shockwave Flash 2.0"] ? " 2.0" : "";
			var flashDescription = navigator.plugins["Shockwave Flash" + swVer2].description;			
			var descArray = flashDescription.split(" ");
			var tempArrayMajor = descArray[2].split(".");
			var versionMajor = tempArrayMajor[0];
			var versionMinor = tempArrayMajor[1];
			if ( descArray[3] != "" ) {
				tempArrayMinor = descArray[3].split("r");
			} else {
				tempArrayMinor = descArray[4].split("r");
			}
			var versionRevision = tempArrayMinor[1] > 0 ? tempArrayMinor[1] : 0;
			var flashVer = versionMajor + "." + versionMinor + "." + versionRevision;
		}
	}
	// MSN/WebTV 2.6 supports Flash 4
	else if (_strUserAgent.toLowerCase().indexOf("webtv/2.6") != -1) flashVer = 4;
	// WebTV 2.5 supports Flash 3
	else if (_strUserAgent.toLowerCase().indexOf("webtv/2.5") != -1) flashVer = 3;
	// older WebTV supports Flash 2
	else if (_strUserAgent.toLowerCase().indexOf("webtv") != -1) flashVer = 2;
	else if ( isIE && isWin && !isOpera ) {
		flashVer = ControlVersion();
	}	
	return flashVer;
}

// When called with reqMajorVer, reqMinorVer, reqRevision returns true if that version or greater is available
function DetectFlashVer(reqMajorVer, reqMinorVer, reqRevision)
{
	versionStr = GetSwfVer();
	if (versionStr == -1 ) {
		return false;
	} else if (versionStr != 0) {
		if(isIE && isWin && !isOpera) {
			// Given "WIN 2,0,0,11"
			tempArray         = versionStr.split(" "); 	// ["WIN", "2,0,0,11"]
			tempString        = tempArray[1];			// "2,0,0,11"
			versionArray      = tempString.split(",");	// ['2', '0', '0', '11']
		} else {
			versionArray      = versionStr.split(".");
		}
		var versionMajor      = versionArray[0];
		var versionMinor      = versionArray[1];
		var versionRevision   = versionArray[2];

        	// is the major.revision >= requested major.revision AND the minor version >= requested minor
		if (versionMajor > parseFloat(reqMajorVer)) {
			return true;
		} else if (versionMajor == parseFloat(reqMajorVer)) {
			if (versionMinor > parseFloat(reqMinorVer))
				return true;
			else if (versionMinor == parseFloat(reqMinorVer)) {
				if (versionRevision >= parseFloat(reqRevision))
					return true;
			}
		}
		return false;
	}
}

function AC_AddExtension(src, ext)
{
  if (src.indexOf('?') != -1)
    return src.replace(/\?/, ext+'?'); 
  else
    return src + ext;
}

function AC_Generateobj(objAttrs, params, embedAttrs) 
{ 
    var str = '';
    if (isIE && isWin && !isOpera)
    {
  		str += '<object ';
  		for (var i in objAttrs)
  			str += i + '="' + objAttrs[i] + '" ';
  		for (var i in params)
  			str += '><param name="' + i + '" value="' + params[i] + '" /> ';
  		str += '></object>';
    } else {
  		str += '<embed ';
  		for (var i in embedAttrs)
  			str += i + '="' + embedAttrs[i] + '" ';
  		str += '> </embed>';
    }

    document.write(str);
}

function AC_FL_RunContent(){
  var ret = 
    AC_GetArgs
    (  arguments, ".swf", "movie", "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
     , "application/x-shockwave-flash"
    );
  AC_Generateobj(ret.objAttrs, ret.params, ret.embedAttrs);
}

function AC_GetArgs(args, ext, srcParamName, classid, mimeType){
  var ret = new Object();
  ret.embedAttrs = new Object();
  ret.params = new Object();
  ret.objAttrs = new Object();
  for (var i=0; i < args.length; i=i+2){
    var currArg = args[i].toLowerCase();    

    switch (currArg){	
      case "classid":
        break;
      case "pluginspage":
        ret.embedAttrs[args[i]] = args[i+1];
        break;
      case "src":
      case "movie":	
        args[i+1] = AC_AddExtension(args[i+1], ext);
        ret.embedAttrs["src"] = args[i+1];
        ret.params[srcParamName] = args[i+1];
        break;
      case "onafterupdate":
      case "onbeforeupdate":
      case "onblur":
      case "oncellchange":
      case "onclick":
      case "ondblClick":
      case "ondrag":
      case "ondragend":
      case "ondragenter":
      case "ondragleave":
      case "ondragover":
      case "ondrop":
      case "onfinish":
      case "onfocus":
      case "onhelp":
      case "onmousedown":
      case "onmouseup":
      case "onmouseover":
      case "onmousemove":
      case "onmouseout":
      case "onkeypress":
      case "onkeydown":
      case "onkeyup":
      case "onload":
      case "onlosecapture":
      case "onpropertychange":
      case "onreadystatechange":
      case "onrowsdelete":
      case "onrowenter":
      case "onrowexit":
      case "onrowsinserted":
      case "onstart":
      case "onscroll":
      case "onbeforeeditfocus":
      case "onactivate":
      case "onbeforedeactivate":
      case "ondeactivate":
      case "type":
      case "codebase":
        ret.objAttrs[args[i]] = args[i+1];
        break;
      case "id":
      case "width":
      case "height":
      case "align":
      case "vspace": 
      case "hspace":
      case "class":
      case "title":
      case "accesskey":
      case "name":
      case "tabindex":
        ret.embedAttrs[args[i]] = ret.objAttrs[args[i]] = args[i+1];
        break;
      default:
        ret.embedAttrs[args[i]] = ret.params[args[i]] = args[i+1];
    }
  }
  ret.objAttrs["classid"] = classid;
  if (mimeType) ret.embedAttrs["type"] = mimeType;
  return ret;
}

// about applet						add by zhangfei  2007/10/31 16:00
function HK_Applet_RunContent(){
  var ret = HK_GetArgs(arguments,"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93");
  HK_Generateobj(ret.objAttrs,ret.objParams ,ret.embedAttrs, ret.appAttrs,ret.appParams);
}

// about applet						add by zhangfei  2007/10/31 16:00
function HK_GetArgs(args,classid){
  var ret = new Object();
  ret.appAttrs = new Object();
  ret.objParams = new Object();
  ret.appParams = new Object();
  ret.objAttrs = new Object();
  ret.embedAttrs = new Object();
  for (var i=0; i < args.length; i=i+2){
    var currArg = args[i].toLowerCase();    

    switch (currArg){	
      case "classid":
        break;
      case "width":
      case "height":
      case "align":
      case "vspace": 
      case "hspace":
      case "class":
      case "title":
        ret.appAttrs[args[i]] = ret.objAttrs[args[i]] = ret.embedAttrs[args[i]] = args[i+1];
        break;      
      case "name":
        ret.appParams[args[i]] = ret.appAttrs[args[i]] = ret.objAttrs[args[i]] = ret.embedAttrs[args[i]] = args[i+1];
        break;
      case "code":
      		ret.appAttrs[args[i]] = args[i+1];
      case "archive":
      case "type":
      case "scriptable":      
      	ret.objParams[args[i]] = ret.appParams[args[i]] = ret.embedAttrs[args[i]] = args[i+1];
      	break;  
	  case "wininstall":
	  	ret.objAttrs["codebase"] = 	args[i+1];   
	  	break;	
      case "codebase":
      	ret.appParams[args[i]] = ret.appAttrs["JAVA_CODEBASE"] = ret.objParams["JAVA_CODEBASE"] = args[i+1];
      	break;	
	  case "pluginspage":
	    ret.embedAttrs[args[i]] = args[i+1];
	    break;    	
      default:
		ret.appParams[args[i]] = args[i+1];
    }
  }
  ret.objAttrs["classid"] = classid;

  return ret;
}

// about applet  				add by zhangfei  2007/10/31 16:00
function HK_Generateobj(objAttrs,objParams, embedAttrs, appAttrs,appParams) 
{ 
    var str = '';
    var isIE7 = (_strUserAgent.indexOf("MSIE 7") != -1) ? true : false;
  		
    if ((isIE7 == true))
    {
  		str += '<object ';
  		for (var i in objAttrs)
  			str += i + '="' + objAttrs[i] + '" ';
		str += '>';    			
  		for (var i in objParams)
  			str += '<param name="' + i + '" value="' + objParams[i] + '" /> ';
  		str += '></object>';
    } else if((_ns == true && _ns6 == false) || (isOpera == true)){
  		str += '<embed ';
  		for (var i in embedAttrs)
  			str += i + '="' + embedAttrs[i] + '" ';
  		str += '/>';	
    }else{
  		str += '<applet ';
  		for (var i in appAttrs)
  			str += i + '="' + appAttrs[i] + '" ';
		str += ' MAYSCRIPT>';  			
  		for (var i in appParams)
  			str += '<param name="' + i + '" value="' + appParams[i] + '" /> ';
  		str += '</applet>';    	
    }
    document.write(str);
}

// about applet  				add by H.Fujimoto  2008/11/10
function HK_getBrowserSizeW() 
{ 
	if (isIE) {
		return document.body.clientWidth;
	} else {
		return window.innerWidth;
	}
}
function HK_getBrowserSizeH() 
{ 
	if (isIE) {
		return document.body.clientHeight;
	} else {
		return window.innerHeight;
	}
}

