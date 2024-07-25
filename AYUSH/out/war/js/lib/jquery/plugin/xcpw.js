/*
 * @author keshab.g
 * Empover i-tech
 * version 0.1
 * Draw uploadMedia
 * calling ::::::::::
 * new UploadMedia({  container : "uploadMediaBase"});
 */
//IE
(function(){function addEvent(e,t,n){if(e.addEventListener){e.addEventListener(t,n,false)}else if(e.attachEvent){e.attachEvent("on"+t,function(){n.call(e)})}else{throw new Error("not supported or DOM not loaded")}}function addResizeEvent(e){var t;addEvent(window,"resize",function(){if(t){clearTimeout(t)}t=setTimeout(e,100)})}function getBox(e){var t,n,r,i;var s=getOffset(e);t=s.left;r=s.top;n=t+e.offsetWidth;i=r+e.offsetHeight;return{left:t,right:n,top:r,bottom:i}}function addStyles(e,t){for(var n in t){if(t.hasOwnProperty(n)){e.style[n]=t[n]}}}function copyLayout(e,t){var n=getBox(e);addStyles(t,{position:"absolute",left:n.left+"px",top:n.top+"px",width:e.offsetWidth+"px",height:e.offsetHeight+"px"})}function fileFromPath(e){return e.replace(/.*(\/|\\)/,"")}function getExt(e){return-1!==e.indexOf(".")?e.replace(/.*[.]/,""):""}function hasClass(e,t){var n=new RegExp("\\b"+t+"\\b");return n.test(e.className)}function addClass(e,t){if(!hasClass(e,t)){e.className+=" "+t}}function removeClass(e,t){var n=new RegExp("\\b"+t+"\\b");e.className=e.className.replace(n,"")}function removeNode(e){e.parentNode.removeChild(e)}if(document.documentElement.getBoundingClientRect){var getOffset=function(e){var t=e.getBoundingClientRect();var n=e.ownerDocument;var r=n.body;var i=n.documentElement;var s=i.clientTop||r.clientTop||0;var o=i.clientLeft||r.clientLeft||0;var u=1;if(r.getBoundingClientRect){var a=r.getBoundingClientRect();u=(a.right-a.left)/r.clientWidth}if(u>1){s=0;o=0}var f=t.top/u+(window.pageYOffset||i&&i.scrollTop/u||r.scrollTop/u)-s,l=t.left/u+(window.pageXOffset||i&&i.scrollLeft/u||r.scrollLeft/u)-o;return{top:f,left:l}}}else{var getOffset=function(e){var t=0,n=0;do{t+=e.offsetTop||0;n+=e.offsetLeft||0;e=e.offsetParent}while(e);return{left:n,top:t}}}var toElement=function(){var e=document.createElement("div");return function(t){e.innerHTML=t;var n=e.firstChild;return e.removeChild(n)}}();var getUID=function(){var e=0;return function(){return"ValumsAjaxUpload"+e++}}();window.AjaxUpload=function(e,t){this._settings={action:"upload.php",name:"userfile",multiple:false,data:{},autoSubmit:true,responseType:false,hoverClass:"hover",focusClass:"focus",disabledClass:"disabled",onChange:function(e,t){},onSubmit:function(e,t){},onComplete:function(e,t){}};for(var n in t){if(t.hasOwnProperty(n)){this._settings[n]=t[n]}}if(e.jquery){e=e[0]}else if(typeof e=="string"){if(/^#.*/.test(e)){e=e.slice(1)}e=document.getElementById(e)}if(!e||e.nodeType!==1){throw new Error("Please make sure that you're passing a valid element")}if(e.nodeName.toUpperCase()=="A"){addEvent(e,"click",function(e){if(e&&e.preventDefault){e.preventDefault()}else if(window.event){window.event.returnValue=false}})}this._button=e;this._input=null;this._disabled=false;this.enable();this._rerouteClicks()};AjaxUpload.prototype={setData:function(e){this._settings.data=e},disable:function(){addClass(this._button,this._settings.disabledClass);this._disabled=true;var e=this._button.nodeName.toUpperCase();if(e=="INPUT"||e=="BUTTON"){this._button.setAttribute("disabled","disabled")}if(this._input){if(this._input.parentNode){this._input.parentNode.style.visibility="hidden"}}},enable:function(){removeClass(this._button,this._settings.disabledClass);this._button.removeAttribute("disabled");this._disabled=false},getInputEle:function(){var e=this._input;return e.getAttribute("id")},_createInput:function(){var e=this;var t=document.createElement("input");t.setAttribute("type","file");t.setAttribute("id","xcpw-file-"+(new Date).getTime());t.setAttribute("name",this._settings.name);if(this._settings.multiple)t.setAttribute("multiple","multiple");addStyles(t,{position:"absolute",right:0,margin:0,padding:0,fontSize:"480px",fontFamily:"sans-serif",cursor:"pointer"});var n=document.createElement("div");addStyles(n,{display:"block",position:"absolute",overflow:"hidden",margin:0,padding:0,opacity:0,direction:"ltr",zIndex:2147483583});if(n.style.opacity!=="0"){if(typeof n.filters=="undefined"){throw new Error("Opacity not supported by the browser")}n.style.filter="alpha(opacity=0)"}addEvent(t,"change",function(){if(!t||t.value===""){return}var n=fileFromPath(t.value);if(false===e._settings.onChange.call(e,n,getExt(n))){e._clearInput();return}if(e._settings.autoSubmit){e.submit()}});addEvent(t,"mouseover",function(){addClass(e._button,e._settings.hoverClass)});addEvent(t,"mouseout",function(){removeClass(e._button,e._settings.hoverClass);removeClass(e._button,e._settings.focusClass);if(t.parentNode){t.parentNode.style.visibility="hidden"}});addEvent(t,"focus",function(){addClass(e._button,e._settings.focusClass)});addEvent(t,"blur",function(){removeClass(e._button,e._settings.focusClass)});n.appendChild(t);document.body.appendChild(n);this._input=t},_clearInput:function(){if(!this._input){return}removeNode(this._input.parentNode);this._input=null;this._createInput();removeClass(this._button,this._settings.hoverClass);removeClass(this._button,this._settings.focusClass)},_rerouteClicks:function(){var e=this;addEvent(e._button,"mouseover",function(){if(e._disabled){return}if(!e._input){e._createInput()}var t=e._input.parentNode;copyLayout(e._button,t);t.style.visibility="visible"})},_createIframe:function(){var e=getUID();var t=toElement('<iframe src="javascript:false;" name="'+e+'" />');t.setAttribute("id",e);t.style.display="none";document.body.appendChild(t);return t},_createForm:function(e){var t=this._settings;var n=toElement('<form method="post" enctype="multipart/form-data"></form>');n.setAttribute("action",t.action);n.setAttribute("target",e.name);n.style.display="none";document.body.appendChild(n);for(var r in t.data){if(t.data.hasOwnProperty(r)){var i=document.createElement("input");i.setAttribute("type","hidden");i.setAttribute("name",r);i.setAttribute("value",t.data[r]);n.appendChild(i)}}return n},_getResponse:function(iframe,file){var toDeleteFlag=false,self=this,settings=this._settings;addEvent(iframe,"load",function(){if(iframe.src=="javascript:'%3Chtml%3E%3C/html%3E';"||iframe.src=="javascript:'<html></html>';"){if(toDeleteFlag){setTimeout(function(){removeNode(iframe)},0)}return}var doc=iframe.contentDocument?iframe.contentDocument:window.frames[iframe.id].document;if(doc.readyState&&doc.readyState!="complete"){return}if(doc.body&&doc.body.innerHTML=="false"){return}var response;if(doc.XMLDocument){response=doc.XMLDocument}else if(doc.body){response=doc.body.innerHTML;if(settings.responseType&&settings.responseType.toLowerCase()=="json"){if(doc.body.firstChild&&doc.body.firstChild.nodeName.toUpperCase()=="PRE"){doc.normalize();response=doc.body.firstChild.firstChild.nodeValue}if(response){response=eval("("+response+")")}else{response={}}}}else{response=doc}settings.onComplete.call(self,file,response);toDeleteFlag=true;iframe.src="javascript:'<html></html>';"})},submit:function(){var e=this,t=this._settings;if(!this._input||this._input.value===""){return}var n=fileFromPath(this._input.value);if(false===t.onSubmit.call(this,n,getExt(n))){this._clearInput();return}var r=this._createIframe();var i=this._createForm(r);removeNode(this._input.parentNode);removeClass(e._button,e._settings.hoverClass);removeClass(e._button,e._settings.focusClass);i.appendChild(this._input);i.submit();removeNode(i);i=null;removeNode(this._input);this._input=null;this._getResponse(r,n);this._createInput()}}})()
// !IE
jQuery.extend({createUploadIframe:function(e,t){var r="jUploadFrame"+e,o='<iframe id="'+r+'" name="'+r+'" style="position:absolute; top:-9999px; left:-9999px"';return window.ActiveXObject&&("boolean"==typeof t?o+=' src="javascript:false"':"string"==typeof t&&(o+=' src="'+t+'"')),o+=" />",jQuery(o).appendTo(document.body),jQuery("#"+r).get(0)},createUploadForm:function(e,t,r){var o="jUploadForm"+e,n="jUploadFile"+e,a=jQuery('<form  action="" method="POST" name="'+o+'" id="'+o+'" enctype="multipart/form-data"></form>');if(r)for(var u in r)jQuery('<input type="hidden" name="'+u+'" value="'+r[u]+'" />').appendTo(a);var c=jQuery("#"+t),d=jQuery(c).clone();return jQuery(c).attr("id",n),jQuery(c).before(d),jQuery(c).appendTo(a),jQuery(a).css("position","absolute"),jQuery(a).css("top","-1200px"),jQuery(a).css("left","-1200px"),jQuery(a).appendTo("body"),a},ajaxFileUpload:function(e){e=jQuery.extend({},jQuery.ajaxSettings,e);var t=(new Date).getTime(),r=jQuery.createUploadForm(t,e.fileElementId,"undefined"==typeof e.data?!1:e.data),o=(jQuery.createUploadIframe(t,e.secureuri),"jUploadFrame"+t),n="jUploadForm"+t;e.global&&!jQuery.active++&&jQuery.event.trigger("ajaxStart");var a=!1,u={};e.global&&jQuery.event.trigger("ajaxSend",[u,e]);var c=function(t){var n=document.getElementById(o);try{n.contentWindow?(u.responseText=n.contentWindow.document.body?n.contentWindow.document.body.innerHTML:null,u.responseXML=n.contentWindow.document.XMLDocument?n.contentWindow.document.XMLDocument:n.contentWindow.document):n.contentDocument&&(u.responseText=n.contentDocument.document.body?n.contentDocument.document.body.innerHTML:null,u.responseXML=n.contentDocument.document.XMLDocument?n.contentDocument.document.XMLDocument:n.contentDocument.document)}catch(c){handleError(e,u,null,c)}if(u||"timeout"==t){a=!0;var d;try{if(d="timeout"!=t?"success":"error","error"!=d){var l=jQuery.uploadHttpData(u,e.dataType);e.success&&e.success(l,d),e.global&&jQuery.event.trigger("ajaxSuccess",[u,e])}else handleError(e,u,d)}catch(c){d="error",handleError(e,u,d,c)}e.global&&jQuery.event.trigger("ajaxComplete",[u,e]),e.global&&!--jQuery.active&&jQuery.event.trigger("ajaxStop"),e.complete&&e.complete(u,d),jQuery(n).unbind(),setTimeout(function(){try{jQuery(n).remove(),jQuery(r).remove()}catch(t){handleError(e,u,null,t)}},100),u=null}};e.timeout>0&&setTimeout(function(){a||c("timeout")},e.timeout);try{var r=jQuery("#"+n);jQuery(r).attr("action",e.url),jQuery(r).attr("method","POST"),jQuery(r).attr("target",o),r.encoding?jQuery(r).attr("encoding","multipart/form-data"):jQuery(r).attr("enctype","multipart/form-data"),jQuery(r).submit()}catch(d){handleError(e,u,null,d)}return jQuery("#"+o).load(c),{abort:function(){}}},uploadHttpData:function(r,type){var data=!type;return data="xml"==type||data?r.responseXML:r.responseText,"script"==type&&jQuery.globalEval(data),"json"==type&&eval("data = "+data),"html"==type&&alert("SUCEESS"),data}});var handleError=function(){};

var UploadMedia = function(options) {
	$ = jQuery;
	this.defaults = {
		container : "uploadMediaBase",
		mediaController : "uploadMedia.htm",
		fileElementId : "file",
		formData : {},
	};
	this.options = options;
	this.settings = {};
	this.saveMediaOtherThanIE = function() {
		var _this = this
		$.ajaxFileUpload({
			url : _this.settings.mediaController,
			secureuri : false,
			fileElementId : _this.settings.fileElementId,
			dataType : 'xml',
			data : _this.settings.formData,
			headers : {
				"Content-Type" : "text/html",
				"Accept" : "text/html"
			},
			success : function(data, status) {
				if (typeof _this.settings.callback == 'function') {
					if (data.substring(0, 3) == "<p>")
						data = $(data).html();
					_this.settings.callback(data);
				}
			}
		});
	};
	this.saveMediaInIE = function() {
		var _this = this
		var ajaxUploadText = new AjaxUpload(_this.settings.container, {
			action : _this.settings.mediaController,
			name : _this.settings.fileElementId,
			onSubmit : function(file, ext) {
				var formData = [];
				this.setData(_this.settings.formData);
			},
			onComplete : function(file, response) {
				if (typeof _this.settings.callback == 'function') {
					if (response.substring(0, 3) == "<p>")
						response = $(response).html();
					_this.settings.callback(response);
				}
			},
			onChange : function(file, response) {
				if (typeof _this.settings.onChange == 'function')
					_this.settings.onChange();
			}
		});
		$("#"+_this.settings.container).focusin(function(){
			setTimeout(function(){$("#"+_this.settings.container).addClass("af-btn-focus");},250)
			_this.fireEvent(_this.settings.container, "mouseover");
			var ele = ajaxUploadText.getInputEle();
			$("#"+ele).focus();
		});
		$("body").click(function(){
			$("#"+_this.settings.container).removeClass("af-btn-focus");
		});
		
	};
	this.fireEvent = function( ElementId, EventName )
	{
	    if( document.getElementById(ElementId) != null )
	    {   
	        if( document.getElementById( ElementId ).fireEvent ) 
	        {
	            document.getElementById( ElementId ).fireEvent( 'on' + EventName );
	        }
	        else 
	        {   
	            var evObj = document.createEvent( 'Events' );
	            evObj.initEvent( EventName, true, false );
	            document.getElementById( ElementId ).dispatchEvent( evObj );
	        }
	    }
	};
	this.handleKeycode = function(e) {
		e.stopImmediatePropagation();
		e.preventDefault();
		return e.keyCode || e.which;
	};
	this.openfileInOneClick = function() {
		var _this = this;
		$.browser.chrome = $.browser.webkit && !!window.chrome;
		$.browser.safari = $.browser.webkit && !window.chrome;
		$("#file").remove();
		$("body")
				.append(
						"<input type='file' id='file' name='file' style='display: none;'>");
		$("#file").change(function() {
			_this.saveMediaOtherThanIE()
			if (typeof _this.settings.onChange == 'function')
				_this.settings.onChange();
		});
		if ($.browser.safari)
			$('#file').show().focus().click();
		else if ($.browser.webkit || $.browser.chrome)
			$('#file').show().focus().click();
		else
			$("#file").trigger("click");
	}
	this.bindFileEvent = function() {
		if (!$.browser.msie) {
			this.openfileInOneClick();
		} else {
			this.saveMediaInIE();
		}
	}
	this.init = function() {
		var _this = this
		this.settings = $.extend(this.defaults, this.options);
		$("#" + this.settings.container).click(function() {
			_this.bindFileEvent()
		});
		this.saveMediaInIE();
	};
	this.init();
};
