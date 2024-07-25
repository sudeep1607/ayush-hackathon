// plugin name,  init, callpopup, ubqEvent, createHTML

(function($) {
  $.IAPOC_Message = {
    globalVars : {
      settings : {}
    },
    alert : function(options) {
      var defaults = {
        container : null,
        type : "default",
        message : "hello",
        container : "alert",
        module : "alert",
        error : true,
        menu : true,
        okMessage : "Ok",
        cancelMessage : "Cancel",
        seo : false,
        successTitletext : "Information",
        errorTitleText : "Error",
        callback : function(){}
      }
      this.globalVars.settings = $.extend(defaults, options);
      if (this.globalVars.settings == null) {
        alert("error : IAPOC - Please provide a container");
      }
      this.init();
    },
    init : function() {
      this.callPopup();
      this.start();
      this.eisaiEvent();
      if (typeof this.globalVars.settings.open == 'function') {
        this.globalVars.settings.open();
      }
      if (this.globalVars.settings.okButtonWidth) {
        if (this.globalVars.settings.okButtonWidth > $(".alrt-btn").width()) {
          this.changeOkButton();
        }
      }
      this.setPopupLookAsperType();
    },
    changeOkButton : function() {
    //  var left = 235 - ((this.globalVars.settings.okButtonWidth - $(".alrt-btn").width()) / 2);
      var left = this.globalVars.settings.okButtonleft;
      $(".alrt-ok").css({
        "width" : this.globalVars.settings.okButtonWidth + "px",
        left : left + "px !important"
      });
      if ($.browser.msie) {
        $(".alrt-ok").css({
          "background" : "#5C5263 !important",
          "border-radius" : '3px'
        });
      }
    },
    start : function() {
      this.hideAll();
      $(".alrt-ok").removeClass("alrt-ok-confirm");
      if (this.globalVars.settings.type == "default") {
        $(".alrt-alert").show();
        $(".alrt-alert .alrt-msg").html(this.globalVars.settings.message);
        this.closeByEnter();
      } else if (this.globalVars.settings.type == "confirm") {
    	this.globalVars.settings.okMessage = "Yes";
    	this.globalVars.settings.cancelMessage = "No";
    	  
        this.globalVars.settings.error = false;
        $(".alrt-confirm").show();
        $(".alrt-confirm .alrt-msg").html(this.globalVars.settings.message);
        $(".alrt-ok").addClass("alrt-ok-confirm");
        this.closeByEnter();
      } else if (this.globalVars.settings.type == "prompt") {
        $(".alrt-prompt").show();
        $(".alrt-prompt .alrt-msg").html(this.globalVars.settings.message);
      } else {

      }
    },
    closeByEnter : function() {
      $("#alrt-OKFocus").focus();
      $(".alrt-alert, .alrt-confirm").click(function() {
        $("#alrt-OKFocus").focus();
      });
      $(".alrt-OKFocus").keydown(function(e) {
        if (e.keyCode == 13) {
          $(".alrt-ok").trigger("click");
        }
      });
    },
    hideAll : function() {
      $(".alrt-alert").hide();
      $(".alrt-confirm").hide();
      $(".alrt-prompt").hide();
    },
    show : function() {
      $('#' + this.globalVars.settings.container).show();
    },
    hide : function() {
      $('#' + this.globalVars.settings.container).hide();
    },
    callPopup : function() {
      var _this = this;
      var menu = this.globalVars.settings.menu;
      $.IAPOC_Popup.init({
       // container : _this.globalVars.settings.container,
        module: 'alert',
        closeButton : false,
        menu : menu,
        height : 150,
        width : 446,
        overlayBackground : "#fff",
        backgroundOpacity : 0.3,
        scrollTop : true,
        getContainer : function(c){
			_this.createHTML(c);
		},
      });
      $.IAPOC_Popup.updateShowIndex('alert', 999999);
    },
    setPopupLookAsperType : function() {
      var _this = this;
      $(".alrt-messageMainBg").removeClass("alrt-errorMainBg").removeClass("alrt-successBg");
      $(".alrt-errorMainBg, .alrt-messageBg").hide();
      $(".alrt-msg").removeClass("alrt-msg-errorFont").removeClass("alrt-msg-successFont");
      if (_this.globalVars.settings.error == true) {
        $(".alrt-errorMainBg").show();
        $(".alrt-messageMainBg").addClass("alrt-errorMainBg");
        $(".alrt-msg").addClass("alrt-msg-errorFont");
        $(".alrt-title").html(this.globalVars.settings.errorTitletext)
      } else {
        $(".alrt-messageBg").show();
        $(".alrt-messageMainBg").addClass("alrt-successBg");
        $(".alrt-msg").addClass("alrt-msg-successFont");
        $(".alrt-title").html(this.globalVars.settings.successTitletext);
      }
    },
    confirm : function() {

    },
    eisaiEvent : function() {
      var _this = this;
      $(".alrt-ok").html(_this.globalVars.settings.okMessage);
      $(".alrt-cancel").html(_this.globalVars.settings.cancelMessage);
      $(".alrt-ok, .alrt-close, .alrt-cancel").click(function() {
        _this.close();
        if (typeof _this.globalVars.settings.callback == 'function') {
          _this.globalVars.settings.callback();
        }
      });
      $(".alrt-confirm .alrt-ok").click(function() {
        _this.globalVars.settings.callback(true);
      });

      $(".alrt-confirm .alrt-cancel").click(function() {
        _this.globalVars.settings.callback(false);
      });

    },
    close : function() {
      $.IAPOC_Popup.close("alert")
      if (typeof this.globalVars.settings.close == 'function') {
        this.globalVars.settings.close();
      }
    },
    createHTML : function(c) {
      $("<div class='alrt-messageMain'><div class='alrt-messageMainBg'></div>" 
      	  + "<div class='alrt-title'></div><div class='alrt-close'></div>"
          + "<div class='alrt-alert'><div class='alrt-msg'></div><div class='alrt-ok alrt-btn btn'>OK</div><input type='button' id='alrt-OKFocus' class='alrt-OKFocus' /></div>"
          + "<div class='alrt-confirm'><div class=alrt-msg></div><div class='alrt-ok alrt-btn btn'></div><div class='alrt-cancel alrt-btn btn'></div></div>"
          + "<div class='alrt-prompt'><div class=alrt-msg></div><div class='alrt-ok alrt-btn'></div></div></div></div>")
      .appendTo(c);
      
      $("#dialog-" + this.globalVars.settings.container).css("cursor","move").draggable({
          containment : "body",
          scroll : false
        });
    }
  };
})(jQuery);