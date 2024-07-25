/*
 * @author keshab.g
 * Empover i-tech
 * version 0.1
 * Drag drop upload media file
 * calling ::::::::::
 * new DragDropUploadMedia({  container : "dragandrophandler",mediaController : "attachment.htm"});
 */

var DragDropUploadMedia = function(options) {
	$ = jQuery;
	this.defaults = {
		container : "uploadMediaBase",
		mediaController : "attachment.htm",
		formData : {},
	};
	var rowCount= 0;
	this.options = options;
	this.ext = {};
	this.saveFiles = function(formData)
	{
		var _t = this;
	    var uploadURL =this.ext.mediaController; //Upload URL
	    var extraData ={}; //Extra Data.
	    var jqXHR=$.ajax({
	            xhr: function() {
	            var xhrobj = $.ajaxSettings.xhr();
	            if (xhrobj.upload) {
	                    xhrobj.upload.addEventListener('progress', function(event) {
	                        var percent = 0;
	                        var position = event.loaded || event.position;
	                        var total = event.total;
	                        if (event.lengthComputable) {
	                            percent = Math.ceil(position / total * 100);
	                        }
	                        //Set progress
	                        _t.setProgress(percent);
	                    }, false);
	                }
	            return xhrobj;
	        },
	    url: uploadURL,
	    type: "POST",
	    contentType:false,
	    processData: false,
	        cache: false,
	        data: formData,
	        success: function(data){
	            // console.log("done");
	        	setTimeout(function(){$(".upload_progressBar").remove();}, 200);
	        	if (typeof _t.ext.callback == 'function') {
					if (data.substring(0, 3) == "<p>")
						data = $(data).html();
					_t.ext.callback(data);
					$(".upload_progressBar").remove();
				}
	        }
	    });
	}
	
	this.handleFileUpload = function(files,obj){
		$(".upload_progressBar").remove();
		$("<div class='upload_progressBar'><div></div></div>").appendTo(obj);
		
		$(".upload_progressBar").css({"border-radius": "5px","overflow": "hidden",
		    "display": "inline-block","margin": "0px 10px 5px 5px","vertical-align": "top","bottom" : "0px",
		    "left":"0","position" : "absolute","font" :"14px helvetica,arial,serif"})
		.width(obj.width() - 8).height(25)
		 
		$(".upload_progressBar div").css({"height": " 100%","color": " #fff","text-align": "right","line-height": "22px",
		    "width": "0","background-color": "#670A9D; border-radius: 3px"});
	   
		this.progressBar = $(".upload_progressBar");
	   
	   for (var i = 0; i < files.length; i++)
	   {
	        var fd = new FormData();
	        fd.append('file', files[i]);
	 
	        var fileName = files[i].name;
	        
	        var fileSize="";
	        var sizeKB = files[i].size/1024;
	        if(parseInt(sizeKB) > 1024)
	        {
	            var sizeMB = sizeKB/1024;
	            fileSize = sizeMB.toFixed(2)+" MB";
	        }
	        else
	        {
	        	fileSize = sizeKB.toFixed(2)+" KB";
	        }
	        
	        
	        this.saveFiles(fd);
	   }
	}
	
	this.setProgress = function(progress)
    {      
        var progressBarWidth =progress*this.progressBar.width()/ 100; 
        this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");
        if(parseInt(progress) >= 100)
        {
            //while progrees we can hide some div;
        }
    }
	this.startProcess = function(){
		var _t = this;
		var obj = $("#"+this.ext.container);
		obj.on('dragenter', function (e)
		{
		    e.stopPropagation();
		    e.preventDefault();
		    //$(this).css('border', '2px solid #0B85A1');
		});
		obj.on('dragover', function (e)
		{
		     e.stopPropagation();
		     e.preventDefault();
		});
		obj.on('drop', function (e)
		{
		    // $(this).css('border', '2px dotted #0B85A1');
		     e.preventDefault();
		     var files = e.originalEvent.dataTransfer.files;
		 
		     //We need to send dropped files to Server
		     _t.handleFileUpload(files,obj);
		});
		$(document).on('dragenter', function (e)
		{
			$("#"+_t.ext.container+" iframe").hide();
		    e.stopPropagation();
		    e.preventDefault();
		});
		$(document).on('dragover', function (e)
		{
			$("#"+_t.ext.container+" iframe").hide();
		  e.stopPropagation();
		  e.preventDefault();
		  //obj.css('border', '2px dotted #0B85A1');
		});
		
		$(document).mouseover(function(){
			setTimeout(function(){$("#"+_t.ext.container+" iframe").show()}, 200);
		});
		
		$(document).on('drop', function (e)
		{
		    e.stopPropagation();
		    e.preventDefault();
		});
	}
	this.init = function() {
		var _this = this
		this.ext = $.extend(this.defaults, this.options);
		this.startProcess();
	};
	this.init();
};
