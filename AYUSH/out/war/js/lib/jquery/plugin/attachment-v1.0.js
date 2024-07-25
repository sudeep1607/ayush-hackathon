var constants = {
		file : {
			defaultProfile : "images/default/defaultprofile.png",
			defaultthumbnail : "images/default/defaultthumbnail.png",
			defaultBluePrint : "images/default/defaultBluePrint.png"
		}
};
var attachmentMsg = {
		'VPCS_JQ_UPLOAD_FILE_NAME' : 'Please enter file name',
		'VPCS_JQ_UPLOAD_FILE_UPLOAD': 'Please upload a file',
		'VPCS_JQ_UPLOAD_FILE_UNIQUE': 'Please enter a unique file name',
		'VPCS_JQ_UPLOAD_FILE_DELETE': 'Are you sure to delete ?'
};

(function($) {
	$.attachment = {
		v : {},
		reset : function(){
		  this.v = {
		  }
		  this.defaults = {
				maxFileLen : 5,
				container : 'body',
				popupHeight : $(window).height() - 40,
				max_height : 560,
				headerHeight : 30,
				footerHeight : 40,
				dragdrop : true,
				files : [],
				readOnly : false,
				afterUpload : function(){
					
				}
		  }
		},
		uploadSuccessDom : null,
		ext : null,
		activeButton : "",
		defaults : {
		},
		init : function(options){
		   this.reset();
		   this.ext = $.extend(this.defaults, options);
		   this.ext.popupHeight = ((this.ext.popupHeight > this.ext.max_height) ? this.ext.max_height : this.ext.popupHeight);
		   this.uploadSuccessDom = null;
		   this.preProcess();
		},
		preProcess : function(){
			var _this = this;
			var files=[];
			var p = this.ext.attachmentDto.attachmentTypeDtoList;
	    	for(var i=0;i< p.length;i++){
	    		var file = {fileType : p[i].fileType, attachmentsId : p[i].attachmentsId, attachmentTypeId : p[i].attachmentTypeId, attachmentTypeName : p[i].attachmentTypeName,
	    				specific : p[i].specific};
	    		files.push(file)
	    	}
	    	_this.ext.files = files;
	    	this.startProcess();
		},
		getFiles : function(container){
			var _this = this;
			var files = [];
			$(container+" .af-imageButton").each(function(){
				var filePath = $(this).attr("filePath");
				var name = $(this).text();
				if(filePath){
					var file = {
							attachmentTypeId : $(this).attr("attachmentTypeId"),
							attachmentTypeName : name,
							filePath : $(this).attr("filePath")
					}
					files.push(file);
				}
			})
			return files
		},
		startProcess : function(){
			var _this = this;
			$(".af-ImgOverlay").remove();
			$(_this.ext.container).height(110);
			$(_this.ext.container).html("");
			var file = {attachmentsId : 0, attachmentTypeId : 0, attachmentTypeName : "add"};
			for(var i=0;i<_this.ext.files.length;i++){
				var files = _this.ext.files[i];
				_this.createButton(files, false);
			}
			if(_this.ext.readOnly != true)_this.createButton(file, true);
		},
		updateCloseButton : function(){
			$(".af-imageButton-main").each(function(){
				$(this).find(".af-close").show();
				if($(this).find("img").attr("type") == 'default'){
					$(this).find(".af-close").hide();
				}
			});
		},
		tabIndex : function(){
			var _t = this;
			
			$(".af-ok").bind('keydown',function(e){var c = _t.handleKeycode(e); 
				if(c==13)$(this).trigger("click");else if(c==9)$(".af-cancel").focus();
			})
			.focusin(function(){$(this).css("border", "1px solid cyan");})
			.focusout(function(){$(this).css("border", "none");});
			$(".af-cancel").bind('keydown',function(e){var c = _t.handleKeycode(e); 
				if(c==13)$(this).trigger("click");else if(c==9)$(".attachHeader input[type='text']").focus();
			})
			.focusin(function(){$(this).css("border", "1px solid cyan");})
			.focusout(function(){$(this).css("border", "none");});
		},
		handleKeycode : function(e){
			e.stopImmediatePropagation();e.preventDefault();
			return e.keyCode || e.which;
		},
		createButton : function(files, t){
			var _this = this;
			var leng = $(".af-imageButton-main").length;
			if(leng == _this.ext.maxFileLen)return false;
			
			var fileType = files.fileType;
			var i = new Date().getTime();
			$(_this.ext.container).append("<div class='af-imageButton-main af-imageButton-main-"+i+" inhtl' index='"+i+"'><div class='af-close odch-close' index='"+i+"'></div>" +
					"<div class='af-download af-download-"+i+"' index='"+i+"' style='display:none'><div class='af-download-text'>Download</div><div class='af-download-img'></div></div>" +
					"<div class='af-imageButton af-imageButton"+i+"' " +
					" fileType='"+fileType+"' attachmentTypeId='"+files.attachmentTypeId+"' attachmentsId='"+files.attachmentsId+"' index='"+i+"'>" +
					"<div class='af-thmbnl'><img src='"+constants.file.defaultthumbnail+"' type='default' /></div>"+
					"<div class='af-imageNm-btn' index='"+i+"'>"+files.attachmentTypeName+"</div></div></div>");
			if(parseInt(files.attachmentsId) != 0){
				//$(".af-imageButton"+i).addClass("af-upload-file-btn-active");
				url = appURL+"/upload/media/id/thumbnail/"+files.attachmentsId+"?"+new Date().getTime();;
				$(".af-imageButton"+i+" .af-thmbnl img").attr({"src" : url, 'type' : 'upload'});
				$(".af-imageButton").attr("filePath", "url");
			}
			if(_this.ext.readOnly == true || files.specific == false || files.specific == "false")$(".af-close").remove();
			$(".af-close").click(function(){
				var t = this;
				$.IAPOC_Message.alert({type : 'confirm', message : attachmentMsg.VPCS_JQ_UPLOAD_FILE_DELETE ,callback : function(r){
					if(r == true){
						var index = $(t).attr("index");
						$(".af-imageButton-main-"+index).remove();
						
						var len = 0;
						$(".af-thmbnl img").each(function(){
							if($(this).attr("type") == "default")len = 1;
						})
						if(len == 0){
							var file = {attachmentsId : 0, attachmentTypeId : 0, attachmentTypeName : "add"};
							_this.createButton(file, true);
						}
					}
				}});
			});
			
			_this.updateCloseButton();
			$(_this.ext.container+" .af-main-upload").remove();
			$(_this.ext.container+" .af-imageButton"+i).click(function(){
				_this.uploadSuccessDom = null;
				_this.activeButton = _this.ext.container+" .af-imageButton"+$(this).attr("index");
				_this.createPopup();
				var html = "<div class='attachHeader posAbs'><input type=text value='"+$(_this.activeButton+" .af-imageNm-btn").html()+"' tabIndex='1' />" +
						"<div id='af-upload-file-btn' tabIndex='2' class='af-upload-file-btn btn'>Browse</div></div><div class='af-cont posAbs'>" +
						"<div class='af-image posAbs' id='af-image-container' style='height:100%;width:100%;top:0;left:0;z-index:1'></div>" +
						"</div>" +
						"<div class='af-footer posAbs'><div class='af-ok btn posAbs' tabIndex='3' index='"+$(this).attr("index")+"'>Ok</div><div tabIndex='4' class='af-cancel btn posAbs'>Cancel</div></div>" +
						"</div>";
				$("#dialog-attachPopup-innerMain").html(html);
				$(".attachHeader").height(_this.ext.headerHeight).css({"width": "100%","top" : "0"});
				var popuHeight = $($.IAPOC_Popup.getPopupMainContainer("attachPopup")).height();
				$(".af-cont").height(popuHeight - (_this.ext.headerHeight+_this.ext.footerHeight)).css({"width": "100%","top" : _this.ext.headerHeight+"px"});
				if(files.specific == false || files.specific == "false" || files.specific == 0 || files.specific == "0")
					$(".attachHeader input[type='text']").attr({"readOnly":"readOnly"}).css({"cursor":"pointer","border" :"none","box-shadow" : "none"});
				else if($(".attachHeader input").val() == 'add'){
					$(".attachHeader input[type='text']").attr('placeholder', 'Enter file name').val("").focus();
				}
				$(".attachHeader input[type='text']").focus();
					
				_this.uploadMedia();
				_this.tabIndex();
				if(_this.ext.readOnly == true){$(".af-ok, #af-upload-file-btn").remove();}
				
				//showImage
				var filePath = $(this).attr("filePath");
				var attachmentsId = $(this).attr("attachmentsId");
				var url = "";
				if(filePath != undefined && filePath != "url"){
					fileType = filePath.split(".")[1];
					url = filePath;
				}else if(attachmentsId != undefined && attachmentsId != "undefined" && parseInt(attachmentsId) != 0){
					fileType = $(this).attr("fileType");
					url = appURL+"/upload/media/id/file/"+attachmentsId+"?"+new Date().getTime();
				}
				if(fileType){
					if(fileType == "pdf"){
						$(".af-image").html("<iframe src ='"+url+"' />");
					}else{
						$(".af-image").html("<img src ='"+url+"' />");
					}
					$(".af-image").load(function(){
						$(".attachHeader input[type='text']").focus();
					})
				}
				$(".attachHeader input[type='text']").keydown(function(e){if(e.keyCode == 110)return false});
				$(".af-ok").click(function(){
					var thisText = $(".attachHeader input[type='text']").val().trim().toLowerCase();
					var index = $(this).attr("index");
					var v = _this.validateFileName(index);
					if(v == true){
						if(_this.uploadSuccessDom && _this.uploadSuccessDom.status == "success"){
							$(_this.activeButton+" .af-imageNm-btn").html(thisText);
							$(_this.activeButton).attr("filePath", _this.uploadSuccessDom.data);
							$(_this.activeButton+" img").attr({"src" : _this.uploadSuccessDom.thumbnaildata, 'type' : 'upload'});
							_this.updateCloseButton();
							if($(_this.activeButton).attr("lastBtn") == "true"){
								var file = {attachmentsId : 0, attachmentTypeId : 0, attachmentTypeName : "add"};
								_this.createButton(file, true);
							}
						}
						$.IAPOC_Popup.close("attachPopup");
					}
				});
				$(".af-cancel").click(function(){
					$.IAPOC_Popup.close("attachPopup")
				});
				_this.dragdrop();
			});
		
			$(".af-imageButton-main").hover(function(){
				var index = $(this).attr("index");
				attachmentsId = $(".af-imageButton"+index).attr("attachmentsId");
				if(attachmentsId != undefined && attachmentsId != "undefined" && parseInt(attachmentsId) != 0){
					$(".af-download-"+$(this).attr("index")).show();
				}
			}, function(){
				var index = $(this).attr("index");
				$(".af-download-"+$(this).attr("index")).hide();
			});
			$(".af-download").click(function(e){
				e.stopImmediatePropagation();
				var index = $(this).attr("index");
				var attachmentsId = $(".af-imageButton"+index).attr("attachmentsId");
				var filePath = $(".af-imageButton"+index).attr("filePath");
				var url = "";
				if(filePath != undefined && filePath != "url"){
					url = filePath;
				}else{
					url = appURL+"/upload/media/download/id/file/"+attachmentsId+"?"+new Date().getTime();
				}
				window.open(url, "_blank", "toolbar=no")
			});
			_this.setLastButton();
		},
		dragdrop : function(){
			var _this = this;
			var $imgc = $("#af-image-container"); 
			if(this.ext.dragdrop == true){
				$("<div class='af-drag-droptext'>Drag & Drop Files Here</div>")
				.appendTo($(".af-cont")).height($(".af-cont").height()).width($(".af-cont").width());
				var d = new DragDropUploadMedia({container : "af-image-container",mediaController : appURL+"/upload/temp/attachment.htm",
					callback : function(d){
			  			if(d){
			  				var result = JSON.parse(d);
			  				_this.processAfterUpload(result);
			  			}
			  			$(".af-cont").nimbleLoader("hide");
			  		}
				});
				
				$imgc.hover(function(){
					$(".af-afterDragDrop").fadeIn(300);
				},function(){
					$(".af-afterDragDrop").fadeOut(300);
				});
			}
		},
		validateFileName : function(index){
			var _this = this;
			var fileName = $(".attachHeader input[type='text']").val().trim().toLowerCase();
			if(fileName == ""){
				$.IAPOC_Message.alert({message : attachmentMsg.VPCS_JQ_UPLOAD_FILE_NAME,error : false});
				$(".attachHeader input[type='text']").focus();
				return false;
			}else if($(".af-image").html() == ""){
				$.IAPOC_Message.alert({message : attachmentMsg.VPCS_JQ_UPLOAD_FILE_UPLOAD ,error : false});
				return false;
			}
			else{
				$(".af-imageNm-btn").each(function(){
					var ind = $(this).attr("index")
					if(parseInt(index) != parseInt(ind)){
						var fNam = $(this).html().trim().toLowerCase();
						if(fNam == fileName)
							fileName = null;
					}
				});
				if(fileName == null){
					$.IAPOC_Message.alert({message : attachmentMsg.VPCS_JQ_UPLOAD_FILE_UNIQUE ,error : false});
					return false;
				}
			}
			return true;
		},
		setLastButton : function(){
			var _this = this;
			var i = 1;
			var len = $(_this.ext.container+" .af-imageButton").length;
			$(_this.ext.container+" .af-imageButton").each(function(){
				$(this).attr("lastBtn", "false");
				if(len == i){
					$(this).attr("lastBtn", "true");
				}
				i++;
			})
			
		},
		createPopup : function(){
			var _this = this;
			$.IAPOC_Popup.init({
				height:_this.ext.popupHeight,
				max_height : _this.ext.max_height,
				width : 610,
				z_index : 99999,
				closeButton:false,
				closeOthers : false,
				closeOnEscape : true,
				closeOnOverlay : false,
				module : "attachPopup",
				scrollTop : true,
				onClose : function(){
					//_this.ext.afterUpload();
				}
			});
		},
	    uploadMedia : function(){
	    	var _this = this;
			new UploadMedia({
		  		container : "af-upload-file-btn",
		  		mediaController : appURL+"/upload/temp/attachment.htm",
		  		fileElementId : "file",
		  		formData : {
		  			"imageName" : "keshab"
		  		},
		  		callback : function(d){
		  			if(d){
		  				var result = JSON.parse(d);
		  				_this.processAfterUpload(result);
		  			}
		  			$(".af-cont").nimbleLoader("hide");
		  		},
		  		onChange : function(){
		  			$(".af-cont").nimbleLoader("show");
		  		}
		  	});
		},
		processAfterUpload : function(j){
			var _this = this;
			if(j){
				_this.uploadSuccessDom = j;
				if(j.status == "success"){
					if(j.fileType == "image"){
						$(".af-image").html("<img src ='"+j.data+"' />");
					}else if(j.fileType == "pdf"){
						$(".af-image").html("<iframe src ='"+j.data+"' />");
					}
					$(".af-ok").focus();
					
					$(".af-afterDragDrop").remove();$("<div class='af-afterDragDrop'>Drag & Drop Files Here</div>").appendTo(".af-cont").hide();
					$(".af-drag-droptext").remove();
				}else{
					$.IAPOC_Message.alert({message : j.data ,error : false});
				}
			}
		},
		bindMultipleMediaToContainer : function (container, referenceId, attachmentTablesId, readOnly, maxFileLen)
		{
			var loaderContainer = "body";
			var data = {};
			 $(loaderContainer).nimbleLoader("show");
			$.ajax({
		        url : appURL+"/upload/media/getAttachments/"+attachmentTablesId+"/"+referenceId,
		        processData : true,
		        type : 'POST',
		        contentType : 'Application/json',
		        data : JSON.stringify(data),
		        success : function(outputJson) {
		             $(loaderContainer).nimbleLoader("hide");
		             var params = {
		            		container : "#"+container,
							attachmentDto : outputJson,
							readOnly	: readOnly
		             };
		             if(maxFileLen)params.maxFileLen = maxFileLen;
		             if(readOnly)params.readOnly = readOnly;
		             $.attachment.init(params);
		        }
		      });
			
		},
		getAttachedFiles : function (container){
			var k = $.attachment.getFiles("#"+container);
			var attachmentTypes = [];
			for(var j=0;j<k.length;j++){
				var attachmentType = {
						attachmentTypeId : k[j].attachmentTypeId,
						attachmentTypeName : k[j].attachmentTypeName,
						filePath : k[j].filePath
				}
				attachmentTypes.push(attachmentType);
			}
			return attachmentTypes;
		},
		bindSingleMediaToContainer : function(button, imageContainer, getMediaPathAfterUpload, type){
			type = (!type) ? "image" : type;
			
			new UploadMedia({
			  		container : button,
			  		mediaController : appURL+"/upload/temp/attachment.htm?type="+type,
			  		fileElementId : "file",
			  		formData : {
			  			"imageName" : "profileImage"
			  		},
			  		callback : function(d){
			  			if(d){
			  				var result = JSON.parse(d);
			  				if(result.status == "success"){
			  					if(typeof getMediaPathAfterUpload == 'function') getMediaPathAfterUpload(result.path);
			  					$("#"+imageContainer).attr("src", result.data);
			  				}else{
			  					$.IAPOC_Message.alert({message : result.data ,error : false});
			  				}
			  			}
			  			$("#"+imageContainer).nimbleLoader("hide");
			  		},
			  		onChange : function(){
			  			$("#"+imageContainer).nimbleLoader("show");
			  		}
			  	});
		},
		getRetriveUrl : function(recordId, attachTypeId){
			return appURL+"/upload/media/details/image/"+recordId+"/"+attachTypeId+"?"+new Date().getTime();
		}
	}
})(jQuery);