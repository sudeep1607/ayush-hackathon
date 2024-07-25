
/*
 *  plugin name,  init, callpopup, ubqEvent, close, createHTML
 */
var popup_moduleCache = new HashMap();
(function($) {
  $.IAPOC_Popup = {
    globalVars : {
    },
    reset :function(){
      var _this = this;
      _this.globalVars = {
        settings : {},
        loading : true,
        titleHt : 0
      }
    },
    init : function(options) {
      $( "body" ).data( "bar", { myType: "test", count: 40 } );
      var _this = this;
      this.reset();
      var defaults = {
        closeOthers : false,
        top : null,
        left : null,
        height : null,
        width : null,
        module : 'project',
        title : false,
        titleHeight : 30,
        titleText : "Title",
        titleBackground: "#9aa3a5",
        background : "#fff",
        overlayBackground : "#000",
        backgroundOpacity : "0.3",
        min_height : 400,
        min_width : 600,
        container : 'body',
        overlay : true,
        closeButton : true,
        x_axis_gap : 30,
        y_axis_gap : 30,
        resize : true,
        max_height : 3000,
        max_width : 3000,
        z_index : null,
        closeOnButton : true,  //on clicking close button
        closeOnOverlay : false, //on clicking close overlay
        closeOnEscape : true,  //on escape key
        position : null, // 'full' :  according to container
        onClose : function(){},
  	  	onResize : function(){},
  	  	getContainer : function(){},
  	  	onOpen : function(){},
  	  	effect : false,
  	  	effectOptions : {direction: 'left'},
  	  	showBorder : true,
  	    border : "5px solid #B4B4B4",
  	    pos : "absolute",
  	    scrollTop : false
      }
    
      this.globalVars.settings = $.extend(defaults, options);
      this.startProcess();
    },
    startProcess : function(){
      var _this = this;
      var $set = this.globalVars.settings;
      $set.onOpen();
      
      this.globalVars.settings.dateTime = new Date().getTime();
      $set.p_container = "dialog-"+$set.module;this.globalVars.settings.pId = $set.p_container;
      if($set.closeOthers == true){
        $(".commonPopup").each(function(){
          var module = $(this).attr("module");
          var id = $(this).attr("id");
           $("#"+id).remove();
        })
        popup_moduleCache.clear();
        $(".popUpOverlay").remove();
      }
      $("#popUpOverlay-"+$set.module).remove();
      $('#' +$set.p_container).remove();
      var c = ($set.container == 'body') ? $("body") : $("#"+$set.container);
      $("<div id="+$set.p_container+" class='commonPopup' module="+ $set.module +"></div>")
      .css({"z-index": (popup_moduleCache.size() * 1000)+2, "position" : $set.pos})
      .html("<div id='"+$set.p_container+"-innerMain' class='project-innerMain'></div>")
      .appendTo(c)
      .focus();
      $("#"+$set.p_container+"-innerMain").css({"background" : $set.background, "position" : "absolute","border-radius": "3px"})
      .addClass("fullHeightWidth");
      
      if($set.resize == true)
    	  _this.setPopupOnWindowResize();
      if($set.overlay == true)
    	  _this.addOverlay();
      if($set.closeButton == true)
    	  _this.addClose();
      if($set.closeOnEscape == true)
    	  _this.closeOnEscape();
      
      if($set.position == 'full')$set.effect = false;
      
      popup_moduleCache.put($set.module, $set);
      this.setPositionOfPopup();
      _this.globalVars.loading = false;
      this.addTitle();
      this.addContainer();
      $set.getContainer(this.getContainer($set.module));
      if($set.z_index)
    	  this.updateShowIndex($set.module, $set.z_index);
      
      if($set.effect == true)
    	  $( "#"+$set.p_container ).effect( 'slide', $set.effectOptions, 300, function() {});
      
    },
    addTitle : function(){
    	var $set = this.globalVars.settings;
        if($set.title == true){
        	$("<div class='commonPopupTtl'>"+$set.titleText+"</div>")
        	.height($set.titleHeight).width(this.getInnerContainer($set.module).width()-10)
        	.css({"background": $set.titleBackground})
            .appendTo(this.getInnerContainer($set.module));
            this.globalVars.titleHt = $set.titleHeight;
        }
    },
    addContainer : function(){
    	var $set = this.globalVars.settings;
    	$("<div id='"+$set.p_container+"-innerMain-container'></div>")
    	.height(this.getInnerContainer($set.module).height()- this.globalVars.titleHt)
    	.width(this.getInnerContainer($set.module).width())
    	.css({"position":"absolute", "top": this.globalVars.titleHt+"px"})
        .appendTo(this.getInnerContainer($set.module))
    },
    closeOnEscape : function() {
        var _this = this;
        $(window).keydown(function(e) {
        	e.stopImmediatePropagation();
          if (e.keyCode == 27) {
        	  var allValues = popup_moduleCache.values();
          	  var dateTime = 0;var module = "";
          	  for(var i = 0;i<allValues.length;i++){
          		  if(allValues[i].dateTime > dateTime || dateTime == 0){
          			  if(allValues[i].closeOnEscape == true){
          				  dateTime = allValues[i].dateTime;
          				  module = allValues[i].module;
          			  }
          		  }
          	  }
        	  _this.close(module);
          }
        });
    },
    addClose : function(){
    	var _this = this;
    	$("<div class='popup-close' id='close-"+this.globalVars.settings.module+"' >")
    	.appendTo("#"+this.globalVars.settings.pId);
    	if(this.globalVars.settings.closeOnButton){
    		$("#close-"+this.globalVars.settings.module).click(function(){
    			var id =  $(this).attr("id");
    			id = id.split("close-");
    			_this.close(id[1]);
    		})
    	}
    },
    addOverlay : function(){
    	var _this = this;
    	var $set = this.globalVars.settings;
    	var container = ($set.container == 'body') ? $('body') : $("#"+$set.container);
    	$("<div class='popUpOverlay' id='popUpOverlay-"+this.globalVars.settings.module+"'>")
    	.appendTo(container)
    	.css({"z-index": (popup_moduleCache.size() * 1000)+1, "opacity" : $set.backgroundOpacity, "background" : $set.overlayBackground});
    	if(this.globalVars.settings.closeOnOverlay){
    		$("#popUpOverlay-"+this.globalVars.settings.module).click(function(){
    			var id =  $(this).attr("id");
    			id = id.split("popUpOverlay-");
    			_this.close(id[1]);
    		})
    	}
    },
    getLatestOpenPopup : function(){
      var allValues = popup_moduleCache.values();
  	  var dateTime = 0;var module = "";
  	  for(var i = 0;i<allValues.length;i++){
  		  if(allValues[i].dateTime > dateTime || dateTime == 0){
  			  dateTime = allValues[i].dateTime;
  			  module = allValues[i].module;
  		  }
  	  }
  	  return module;
    },
    setPopupOnWindowResize: function(){
      var _this = this;
      $(window).resize(function() {
        _this.setPositionOfPopup();
      });
    },
    setPositionOfPopup : function(){
      var _this = this;
      $(".commonPopup").each(function(){
        var id = $(this).attr("id");
        var sett = popup_moduleCache.get($(this).attr("module"))
        var windowHeight = $(window).height();
        var windowWidth = $(window).width();
        windowHeight = windowHeight < 500 ? windowHeight = 500 : windowHeight = windowHeight;
        windowWidth = windowWidth < 800 ? windowWidth = 800 : windowWidth = windowWidth;
        var top = 0;
        var left = 0;
        var height = 0;
        var width = 0;
        var pHeight = null;
        var pWidth = null;
        
        if(sett.container == 'body'){
        	var width = (sett.width || sett.width == 0) ? sett.width : (windowWidth - (sett.y_axis_gap * 2));
        	var height = (sett.height || sett.height == 0) ? sett.height : (windowHeight - (sett.x_axis_gap * 2));
        	pHeight = windowHeight;pWidth = windowWidth;
           
        }else{
        	var parentHeight = $("#"+sett.container).height();
        	var parentWidth = $("#"+sett.container).width();
        	var width = (sett.width || sett.width == 0) ? sett.width : (parentWidth - (sett.y_axis_gap * 2));
        	var height = (sett.height || sett.height == 0) ? sett.height : (parentHeight - (sett.x_axis_gap * 2)) ;
        	pHeight = parentHeight;pWidth = parentWidth;
        }
        height = (height > sett.max_height) ? sett.max_height : height;
        width = (width > sett.max_width) ? sett.max_width : width;
        
        top = (sett.top || sett.top == 0) ? sett.top : ((pHeight - height)/2);
        left = (sett.left || sett.left == 0) ? sett.left : ((pWidth - width)/2) ;
        top = (top < 0) ? 0 : top;left = (left < 0) ? 0 : left;
        
        if(sett.position != 'full' && sett.showBorder == true){
        	height = height - 10;width = width - 10;
        	$("#"+id).css({"border" : sett.border, "border-radius" : "4px"});
        }
        
        if(sett.scrollTop == true)top += $(window).scrollTop();
        
        
        $("#"+id).height(height).width(width).css({left : left+"px", top : top+"px"});
      
        if(sett.position == 'full'){
        	$("#"+id).css({left : "0px", top : "0px",height:"100%",width:"100%","z-index":1,"box-shadow":"none"});
        	$("#popUpOverlay-"+sett.module).remove();
        	if($("#"+id).height() < sett.min_height)$("#"+id).height(sett.min_height);
        	if($("#"+id).width() < sett.min_width)$("#"+id).height(sett.min_width);
    	}
        
        if(_this.globalVars.loading == false)sett.onResize();
      });
    },
    updateShowIndex : function(module, zindex){
    	$("#dialog-"+module).css({"z-index": zindex+1});
    	$("#popUpOverlay-"+module).css({"z-index": zindex});
    },
    getPopupMainContainer : function(module){
    	return "#dialog-"+module;
    },
    getContainer : function(module){
    	return $("#dialog-"+module+"-innerMain-container");
    },
    getInnerContainer : function(module){
    	return $("#dialog-"+module+"-innerMain");
    },
    close : function(module){
      if(!module) return false;
      var r = popup_moduleCache.get(module).onClose();
      if(r != false){
    	this.removePopup(module);
      }
    },
    removePopup : function(module){
    	 $("#dialog-"+module).remove();$("#popUpOverlay-"+module).remove();
         popup_moduleCache.remove(module);
    }
  };
})(jQuery);


var jquerySelect = function(options) {
	$ = jQuery;
	this.defaults = {
		container : "xxxxx",
		item : null,
		minLen : 0,
		onSelect : function(){
			
		}
	};
	this.options = options;
	this.ext = {};
	
	this.addDropdown = function(){
		$("#"+this.ext.container+" .e-plug-dropdown").remove();
		$("<div class='e-plug-dropdown'></div>")
		.appendTo($("#"+this.ext.container))
		.css({"position" : "absolute", "right" : 0,"top" : 0, "z-index" : 2, "cursor" : "pointer"});
	};
	this.setItem = function (item){
		var _t =this;
		this.ext.item = item;
		this.populateAutocomplete();
		$("#"+this.ext.container+" .e-plug-dropdown").focus().click(function(e){e.stopImmediatePropagation();
			$("#"+_t.ext.container+" input[type='text']").val("");$("#"+_t.ext.container+" input[type='text']").focus();
		});
	};
	this.deSelect = function(){
		$("#"+this.ext.container+" input[type='text']").val('');
	},
	this.populateAutocomplete = function (){
		var _t = this;
		$("#"+_t.ext.container+" input[type='text']").autocomplete({
			source : _t.ext.item,
	        minLength: _t.ext.minLen,
	        scroll:true,
	        select: function (event, ui) {
	        	_t.ext.onSelect(ui.item)
	        }
		}).focus(function() {
		    $(this).autocomplete('search', $(this).val())
	      });
		this.keyUpEvent();
	};
	this.getSelectedValue = function (){
		var _t = this;
		var items = this.ext.item;
		var txt = $.trim($("#"+_t.ext.container+" input[type='text']").val());
		if(!txt || !items)
			return null;
		else{
			for(var i=0;i<items.length;i++){
				var item = items[i];
				if(item.value+"" == txt+"")
					return item;
			}
		}
		return null;
	};
	this.setValue = function (id){
		var _t = this;
		var items = this.ext.item;
		for(var i=0;i<items.length;i++){
			var item = items[i];
			if(item.id == id){
				$("#"+_t.ext.container+" input[type='text']").val(item.value);
			}
		}
	};
	this.keyUpEvent =  function (){
		var $kele = $("#"+this.ext.container);
		$(this.ext.container).keyup(function(e){e.stopImmediatePropagation();
			if($.trim($kele.val()) == "")$kele.trigger("focus");
		}).click(function() {$kele.trigger("keyup")});
	};
	this.init = function() {
		this.ext = $.extend(this.defaults, this.options);
		this.addDropdown();
		if(this.ext.item)this.setItem(this.ext.item);
	};
	
	this.init();
};
