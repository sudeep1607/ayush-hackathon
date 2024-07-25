Ext.onReady(function () 
{
	Ext.tip.QuickTipManager.init();
	getNotificationPopUP();
	if(!FC_PWD_FLAG)
	{
		var isSessionExists = 0;
		Ext.Ajax.request(
		{
			 url		: 'sessionHandler?date=' + new Date()
			,waitMsg	: 'Checking the session info....'
			,callback	: function (options, success, response)
			{
				if (success)
				{
					isSessionExists = Ext.decode(response.responseText);
				
					if (isSessionExists == "1")
					{
						   var htmls ="";
				  			Ext.Ajax.request(
				  		    {
		  				    	 url 		: AMS.Urls.userRoles + UserData.userId
		  				    	,method		: 'POST'
		  			    		,headers	: { 'Content-Type': 'application/json' }  
		  				      	,success	: function (responseJ) 
		  				      	 {
		  				      	    var dataR= Ext.JSON.decode(responseJ.responseText);
		  				      	    var notifcationCount = '<div id="nlogo" class="mousepointer" onclick="pageNavigationToFileProcess()">';
		  				      	    var inboxCount = '<div id="inBox" class="mousepointer" onclick="pageNavigationToFileProcess()">';
		  				      	    
		  				      	   if(alertCount>0){notifcationCount += '<span class="nlogotext" >'+alertCount+'</span>'}
		  				      	    	notifcationCount += 	'</div>'
		  				      	   if(newFilesCount>0){inboxCount += '<span class="nlogotext">'+newFilesCount+'</span>'}
		  				      	    inboxCount += 	'</a></div>'
		  				      	    	
		  				      	    htmls += '<div id="x-odch-home-header-main">'
									+ '<div class="x-odch-header-box" id="x-odch-home-header-logo"> <img src="images/logo.png" class="hlogo" alt="" title=""/><span class="header_text_admin">File Tracking System</span>'
									//+ '<img class="x-odch-home-header-icon" src="images/home_header_icon.png">'
									+ '</div>'
									+'</div>'
									+ '<div class="x-odch-header-box1">'
									
									
									//+ inboxCount
									+ notifcationCount
									+ '<div id="x-odch-role-info"> <img src="images/home/icon_user.png" onclick="roleOnChange()" class="hlogo" alt="change Role" title=""/> '
									+'<nav class="primary_nav_wrap1 jprimary_nav">'
									+ '<span class="user_name"> ' +  UserData.firstname + '</span><br/>'
									+'<ul class="jdrop">'
									+'<li><a href="#">'+ UserData.roleName +'</a>'
									+'<ul>';
						      	    for (j = 0; j < dataR.length; j++) { 
						      		 htmls  += '<li><a href="#" onclick="roWindow('+dataR[j].id+')">'+dataR[j].name+'</a></li>';
					  			    }
						      	 	htmls   += '</ul>'
					  			       	+'</li>'
						  			  	+'</ul>'
								     	+'</nav>'
								     	+'</div>'
										+'</div>'
										+ '<div class="x-odch-header-box" id="x-odch-profile-info">'
										+ '<div id="x-odch-citizen-info"></div>'
										
										+ '<div id="x-odch-change-report"><button type="button"  onclick="openReportsPage()"><img src="images/home/Report1.png" class="hlogo" alt="" title=""></button></div>'
										+ '<div id="x-odch-change-password"><button type="button"  onclick="passwordToChange()"> <img src="images/home/pwd-btn.png" class="hlogo" alt="" title="Change Password"></button></div>'
										+ '<div id="x-odch-signout"><button type="button" style=" " onclick="signOut()"> <img src="images/home/logout-btn.png" class="hlogo" alt="" title="Logout"></button></div></div>'
										+ '</div>';
						
										Ext.create('Ext.Viewport',
										{
											layout	: 'border'
											,id		: 'homeViewPort'
											,items	: 
											[
												{
													region		: 'north'
													,xtype		: 'component'
													,style		: 'background: #fff;'
													,height		: 58
													,html 		: htmls
												},{
													xtype		: 'toolbar'
													,region		: 'north'
													,overflow	: true
													,itemId		: 'toolbarId'
													,enableOverflow: true
													,cls		: 'x-odch-mainmenu'
													,defaults	: 
													{
														scale		: 'medium'
														,iconAlign	: 'left'
														,cls		: 'x-odch-btn'
													}
													,items		: HOME.generateToolBar()
												},{
													region		: 'center'
													,border		: false
													,autoScroll	: false
													,layout		: 'fit'
													,xtype		: 'panel'
													,style		: 'background: #fff;'
												},{
													region 		: 'south'
													,border 	: false
													,xtype		: 'component'
													,cls		: 'x-odch-footer'
													,html		: '<div class="x-odch-home-footer">FTS - <span style="padding-right:5px;" class="footer">Powered by <a href="http://www.empover.com" target="_blank" >Empover i-Tech Pvt. Ltd.</a> </span></div>'
												}
											]
											,listeners:
											{
												boxready: function()
												{
													HOME.activateDefaultMenu();
												}
											 
											 }
										  });
						
			  				      	}
					  		    });
						
						/*if (forcedLogout == "1" && refresh == "1")
						{
							Utilities.showAlert('Information' , 'Another User has logged in with your credentials and his session is forcefully destroyed while you logged in.')
						}*/
					}
					else
					{
						location.href = location.href + "?actionName=signout";
					}
				}
			}
		});
	}
	else
	{
		passwordToChange();
	}
});

function hitServer()
{
	Ext.Ajax.request(
	{
		url: 'sessionHandler?date=' + new Date()
		,callback: function (options, success, response)
		{
			if (success)
			{
				var isSessionExists = response.responseText;
				if ("1" != isSessionExists) 
				{
					Ext.Msg.show(
					{
						title 		: 'Information'
						,msg  		: 'Your session has expired, please reload the page to login.'
						,modal  	: true
						,buttons	: Ext.Msg.OK
						,icon 		: Ext.window.MessageBox.INFO
						,cls		: 'x-odch-window x-odch-alet-window'
						,closable   : false
						,fn 		: function(btn)
						{
							location.reload();
						}
					});
				}
			}
		}
	});
}

//setInterval("hitServer()", 120000); //  After Every 2 minutes
setInterval("getNotificationPopUP()", 100000); //  After Every 1 minutes

function signOut()
{
    var curURL = ((location.href).split('?'))[0];
    curURL = curURL.replace("#", "");
    location.href = Ext.urlAppend(curURL, "actionName=signout");
}

function passwordToChange()
{
	Ext.apply(Ext.form.VTypes, 
	{
			password: function (val, field)
			{
			    if (field.initialPassField)
			    {
			    	var matchnewpass 	= Ext.ComponentQuery.query('#changePasswordForm')[0];
			    	var login = matchnewpass.down('[name=newPassword]');
			        this.passwordText = 'Confirm Password does not matched with the new password ';
			        return (val == login.getValue());
			    }
			    
			    this.passwordText = 'Passwords must adhere to the following <li align="left">Must contain at least 6 characters.<li align="left">Must contain one or more alphabets.<li align="left">Must contain one or more numbers.<li align="left">Must contain one or more valid special characters <br>(!@#$%^&*()-_=+)';
			    var hasAlphabets = val.match(/[a-zA-Z]+/i);
			    var hasNumbers = val.match(/[0-9]+/i);
			    var hasSpecial = val.match(/[!@#\$%\^&\*\(\)\-_=\+]+/i);
			    return (hasAlphabets && hasSpecial && hasNumbers && val.length >= 6);
			}
    });
    var changePwdForm = Ext.create('Ext.custom.hbox.form.Panel',
	{
		itemId			: 'changePasswordForm'
		,monitorValid 	: true
		//,width			: 400
		,items			: 
		[
			{
				padding : {right : 20}
				,items	: 
				[
					{
						fieldLabel	: 'Existing Password'
						,name		: 'existingPassword'
						,allowBlank	: false
						,inputType	: 'password'
						,xtype		: 'textfield'
					}
					,{
						fieldLabel	: 'New Password'
						,name		: 'newPassword'
						,inputType	: 'password'
						,allowBlank	: false
						,xtype		: 'textfield'
						,vtype		: 'password'
					}
					,{
						fieldLabel	: 'Confirm Password'
						,name		: 'confirmPassword'
						,xtype		: 'textfield'
						,inputType	: 'password'
						,vtype		: 'password'
						,initialPassField: 'newPassword'
						,allowBlank	: false
						,listeners	:
						{
							specialkey: function (field, e)
							{
								if (e.getKey() == e.ENTER)
								{
									var form = field.up('form').getForm();
									form.submit();
									changePwd();
								}
							}
						}
					}
				]
			}
		]
		,buttons		: 
		[
			{	xtype	: 'customformsubmitbutton'	,text : 'Submit' ,handler	: changePwd	}
			//,{	xtype	: 'custom-form-submit-btn'	,handler	: function ()	{	changePwdForm.getForm().reset();	}}
		]
	});

	Ext.create('Ext.custom.window.Window',
	{
		itemId		: 'changePassword'
		,title		: 'Change Password'
		,items		: changePwdForm
		,modal		: true
		,autoShow	: true
	});
}

function openReportsPage()
{
	var iframe = document.createElement('iframe');
	var project='K4';
	var projectId=1;
	//iframe.src = UserData.reportsUrl+"?username="+UserData.firstname+"&proName="+UserData.projectName+"&projectId="+UserData.defaultProjectId;
	//iframe.src = "http://172.22.85.8:8080/PMSReports/login?username="+UserData.firstname+"&proName="+UserData.projectName+"&projectId="+UserData.defaultProjectId;
	//iframe.src = "http://172.22.85.8:9090/PMSReports/login?username="+UserData.firstname+"&proName="+UserData.projectName+"&projectId="+UserData.defaultProjectId+"&roleName="+UserData.roleName;
	//iframe.src = "http://192.168.3.33:8081/AMSReports/login?username="+UserData.firstname+"&proName="+UserData.projectName+"&projectId="+UserData.defaultProjectId+"&roleName="+UserData.roleName;
	iframe.src = "http://localhost:9090/AMSReports/login?username="+UserData.firstname+"&proName="+project+"&projectId="+projectId+"&roleName="+UserData.roleName;
	iframe.frameBorder=0;
	iframe.width="100%";
	iframe.height="100%";
	iframe.id="reportsFrame";
	iframe.style.position = "absolute";
	iframe.style.zIndex = "2147483647";
	document.body.appendChild(iframe);
}

function changePwd()
{
	var changePasswordForm 	= Ext.ComponentQuery.query('#changePasswordForm')[0];
	var oldPwd = changePasswordForm.down('[name=existingPassword]').getValue();
    var newPwd = changePasswordForm.down('[name=newPassword]').getValue();
    if (oldPwd != newPwd)
    {
        Ext.Ajax.request(
        	{
        		url			: AMS.Urls.changePassword
        		,method		: 'POST'
        		,waitMsg	: 'Saving...'
        		,params		: {"newPassword":newPwd,"existingPassword":oldPwd}
            	,success	: function (response)
            	{
            		if("FALSE"==response.responseText)
            		{
            			changePasswordForm.down('[name=existingPassword]').setValue("");
            			changePasswordForm.down('[name=existingPassword]').markInvalid("The password does not match with existing password");
            			changePasswordForm.down('[name=existingPassword]').focus();
            		}
	                else
	            	{
	                	Ext.ComponentQuery.query('#changePassword')[0].close();
	                    Ext.Msg.minWidth = 430;
	                    Utilities.showAlert(getLabel('AMS.GLOBAL.RELOGIN'), getLabel('AMS.GLOBAL.PWDCHNG') ,null,null,
	                    		function (btn, text)
	                    		{
	                        		if (btn == 'ok')
	                        			window.location = location.href + "?actionName=signout";//'loginController.htm';
	                    		});
	            	}
            	}
            	,failure: function (response)
            	{
	                Ext.Msg.show(
	                	{
	                		title		: 'Failure'
		                    ,msg		: "Unable to change the password."
		                    ,modal		: true
		                    ,icon		: Ext.MessageBox.ERROR
		                    ,buttons	: Ext.Msg.OK
		                    ,cls		: 'x-odch-window x-odch-alet-window'
		                    ,width		: 450
	                	});
	                changePasswordForm.down('[name=existingPassword]').setValue("");
            }
        });
    }
    else
    {
    	changePasswordForm.down('[name=newPassword]').setValue("");
    	changePasswordForm.down('[name=confirmPassword]').setValue("");
    	changePasswordForm.down('[name=newPassword]').markInvalid('New password should not match with old password.');
    }
}

function getNotificationPopUP()
{
		Ext.Ajax.request(
			{
				url			: AMS.Urls.getNotifiAlerts
				,method		: 'GET'
				/*,success	: function (response)
				{
					var dataR= Ext.JSON.decode(response.responseText);
					for (j = 0; j < dataR.length; j++) {
								Ext.toast({
											html: dataR[j].fileName,
											title: UserData.firstname,
											width: 200,
											align: 't',
											autoClose: true,
											autoCloseDelay:8000
										});
						} 
					var nCount =0;
					var newCount=0;
					try { nCount = parseInt($('#al_count').text()); } catch (e) {console.log(e);  nCount =0; }
					try { newCount = parseInt(dataR.length); } catch (e) {console.log(e);  newCount =0; }
					$('#al_count').text(nCount+newCount);
				}*/
				,success	: function (response)
				{
					if(response.responseText !='')
						{
							var name= UserData.firstname;
							Ext.toast({
										html: response.responseText,
										title: name,
										width: 250,
										align: 't',
										//autoClose: true,
										autoCloseDelay:10000
									});
						}
						 
				}		 
				
			});
}

function roleOnChange()
{
	ChangeRole.change();
}

function pageNavigationToFileProcess()
{
	//HOME.activateDefaultMenu('2','12');
	{
		var me =  this, menuId, screenId, viewport, toolbar, menu, screen;
		menuId = me.activeMenuId;//AMS.Constants.menus[0].id;
		screenId = 36

		viewport = Ext.ComponentQuery.query('viewport')[0];
		toolbar = viewport.down('toolbar[region=north]', 1);
		menu = toolbar.down('button[menuId=' + 2 + ']');
		if (menu && !menu.disabled)
		{
			menu.doToggle();
			if (menu.fireEvent('click', menu) !== false && !menu.isDestroyed)
			{
				menu.handler(menu, null, 12);
			}
		}
	}
}
function roWindow(id){
	window.open('/FTS/home?rl='+id ,'_self' );
}


 

 