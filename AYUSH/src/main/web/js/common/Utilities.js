AMS.Date = AMS.Date || { };
AMS.Date.Patterns = {
		DefaultFormat: 'd-m-Y',
		DateFieldRendering : 'd-m-Y',
		DateFieldSubmit : 'Y-m-d'
	};


AMS.usermanagement = 0;
AMS.masters = 0;

/**
 * The date format string that the Ext.util.Format.dateRenderer and Ext.util.Format.date functions use.
 */
Ext.Date.defaultFormat = AMS.Date.Patterns.DefaultFormat;
Ext.Date.useStrict = true;
/**
 * Default Date Field Format.
 */
Ext.form.field.Date.prototype.format = AMS.Date.Patterns.DateFieldRendering;
Ext.form.field.Date.prototype.submitFormat = AMS.Date.Patterns.DateFieldSubmit;

/**
 * Default End Date
 */
//AMS.Date.DefaultEndDate = new Date('2099-12-31');

AMS.mandatoryIndicator = '<font style = "color:red; font-weight : bold; font-size:17px;"> *</font>';
//var AMS.editableFiedIndication = '<span style="font-size:9px;color:red;font-style:bold;bottom:3px;left:0px;position:relative;">#</span>';
AMS.msgConfig = {
		message			: 'Saving your data, please wait...'
		,progress 		: true
		,progressText 	: 'Processing...'
		,width			: 300		
		,closable 		: false
		,wait			: true
		,waitConfig		: {
							interval	: 200
							,text		: 'Processing...'
						}
	};

//AMS.Utilities = AMS.Utilities || { };

Ext.define('AMS.Utilities',
{
	alternateClassName: ['Utilities', 'U']
	,statics:
	{
		viewPort:
		{
			getWinWidth : function() 
			{
				this.width = 0;
				if (window.innerWidth)
					this.width = window.innerWidth - 18;
				else if (document.documentElement && document.documentElement.clientWidth)
					this.width = document.documentElement.clientWidth;
				else if (document.body && document.body.clientWidth)
					this.width = document.body.clientWidth;
				return this.width;
			}
			,getWinHeight : function() 
			{
				this.height = 0;
			    if (window.innerHeight)
			    	this.height = window.innerHeight - 18;
			    else if (document.documentElement && document.documentElement.clientHeight)
			    	this.height = document.documentElement.clientHeight;
			    else if (document.body && document.body.clientHeight)
			    	this.height = document.body.clientHeight;
			    return this.height;
			  }
			  ,getScrollX : function() 
			  {
				  this.scrollX = 0;
				  if (typeof window.pageXOffset == "number")
					  this.scrollX = window.pageXOffset;
				  else if (document.documentElement && document.documentElement.scrollLeft)
					  this.scrollX = document.documentElement.scrollLeft;
				  else if (document.body && document.body.scrollLeft)
					  this.scrollX = document.body.scrollLeft;
				  else if (window.scrollX)
					  this.scrollX = window.scrollX;
				  return this.scrollX;
			  }
			  ,getScrollY : function() 
			  {
				  this.scrollY = 0;
				  if (typeof window.pageYOffset == "number")
					  this.scrollY = window.pageYOffset;
				  else if (document.documentElement && document.documentElement.scrollTop)
					  this.scrollY = document.documentElement.scrollTop;
				  else if (document.body && document.body.scrollTop)
					  this.scrollY = document.body.scrollTop;
				  else if (window.scrollY)
					  this.scrollY = window.scrollY;
				  return this.scrollY;
			  }
			  ,getAll : function()
			  {
				  this.getWinWidth();
				  this.getWinHeight();
				  this.getScrollX();
				  this.getScrollY();
			  }
		}
		,getWidth: function()
		{
			return this.viewPort.getWinWidth();
		}
		,getHeight: function()
		{
			return this.viewPort.getWinHeight();
		}
		,renderFormattedDate: function(value)
		{
			if (value != '' && value != null)
			{
				var format = value.length == 10 ? 'd-m-Y' : 'd-m-Y H:i:s'
				var date = Ext.Date.format(new Date(value), format);
			}
			return date;
		}
		,renderFormattedTime: function(value)
		{
			if (value != '' && value != null)
			{
				var date = Ext.Date.format(new Date(value), 'g:i A');
			}
			return date;
		}
		,getReqApprovalStatusStore	: function()
		{
			return	  Ext.create('Ext.data.Store', {
			fields: ['label', 'value'],
			data : [
					{"label":"APPROVE", "value":"APPROVED"},
					{"label":"REVERT", "value":"REVERT"},
				  ]
			});
		}
		,getPurchaseTypeStore	: function()
		{
			return	  Ext.create('Ext.data.Store', {
			fields: ['label', 'value'],
			data : [
					{"label":"Local Purchase", "value":"Local Purchase"},
					{"label":"Tender Process", "value":"Tender Process"},
					{"label":"GeM", "value":"GeM"},
					{"label":"Rate Contract", "value":"Rate Contract"}
				  ]
			});
		}
		,getRepairTypeStore	: function()
		{
			return	  Ext.create('Ext.data.Store', {
			fields: ['label', 'value'],
			data : [
					{"label":"AMC", "value":"AMC"},
					{"label":"Local Repair", "value":"Local Repair"},
				  ]
			});
		}
		//for authentication assigning
		,getAuthenticationApprovalStatusStore	: function()
		{
			return	  Ext.create('Ext.data.Store', {
			fields: ['label', 'value'],
			data : [
					{"label":"ASSIGN AUTHENTICATION", "value":"ASSIGNED"},
					{"label":"REMOVE AUTHENTICATION", "value":"REMOVED"},
				  ]
			});
		}
		
		,formatMsg: function(operation)
		{
			var verb = Ext.String.capitalize(operation.action);
			if(operation.success)
			{
				if(verb == 'Destroy')
					verb += 'ed';
				else
					verb += 'd';
				var response = Ext.JSON.decode(operation.response.responseText);
				var message = response.message ? response.message : "Record "+verb+" Successfully.";
			}
			else
			{
				message = operation.error ? operation.error : "Unable To "+verb+" The Record.";
			}
			return message;
		}
		,showAlert: function(title, msg, icon, button, fn, animateTarget)
		{
			Ext.MessageBox.show(
                    {
                        title    : title? title : "Info"
                        ,msg     : msg? msg: ""
                        ,modal   : true
                        ,closable: false
                        ,icon    : icon? icon: Ext.MessageBox.INFO
                        ,buttons : button? button: Ext.MessageBox.OK
                        ,fn		: fn ? fn : Ext.emptyFn
                        ,animateTarget: animateTarget ? animateTarget : null
                    });
		}
		/**
		 * This function used to load external JS and CSS file as we are using only one JSP(Page.jsp) for all the modules.
		 * @param filePath
		 * @param fileType
		 */
		,loadExternalFile: function(filePath, fileType)
		{
			if ("js" == fileType)
			{
			     document.write('<script type="text/javascript" charset="UTF-8" src="' + filePath  +'.js"></script>');
			}
			else if ("css" == fileType)
			{
				document.write('<link type="text/css" rel="stylesheet" href="' + filePath  +'.css"></link>');
			}
		}
		,YesNoRenderer: function(v)
		{
			return v ? 'Yes' : 'No';
		}
		,renderFormattedStatus: function(value)
		{
			if(value == true)
				return 'Active'.fontcolor("#006600");
			else
				return 'Inactive'.fontcolor("#FF0033");
		}
		,renderFormattedRootStatus: function(value)
		{
			if(value == true)
				return 'Yes'.fontcolor("#006600");
			else
				return 'No'.fontcolor("#FF0033");
		}
		/**
		 * Common loader for all the data requests
		 * @param target
		 * @returns {Ext.LoadMask}
		 */
		,showLoadMask: function(target)
		{
			return new Ext.LoadMask({msg: 'Please wait...'	,target: target})
		}
		,floatRenderer: function(value)
		{
			return Ext.util.Format.number(value, '0.00');
		}
		,showProgresText: function()
		{
			Ext.MessageBox.show(AMS.msgConfig);
		}
		,sosStatusRenderer: function(v, metaData, record)
		{
			return v == C_SOSStatus.OPEN ? '<span class="x-vtp-sos-open" data-qtip="' + C_SOSStatus.OPEN + '"></span>' : '<span class="x-vtp-sos-close" data-qtip="' + C_SOSStatus.CLOSED + '"></span>';
		}
		,showWindow: function(winConfig, items, height, width, minHeight, minWidth)
		{
			var config = 
			{
					layout		: 'fit'
					,maxHeight	: this.viewPort.getWinHeight()
					,modal		: true
					,draggable	: true
			};

			//Backward compatibility
			if (height) config.height = height;
			if (width) config.width = width;
			if (minHeight) config.minHeight = minHeight;
			if (minWidth) config.minWidth = minWidth;
			if (items) config.items = items;
			//

			if (Ext.isObject(winConfig))
			{
				Ext.apply(config, winConfig || { });
			}
			else
			{
				//Backward compatibility
				config.title = winConfig;
			}

			var win = Ext.create('Ext.custom.window.Window',config);
			/*win.on('afterrender', function(w){
				w.center();
			});*/

			return win;
		}
		,toFixedDecimal: function(value, precision)
		{
			var precision = precision || 2;
			return value.toFixed(precision);
		}
		,validateUniqueness: function(field, value, record, url)
		{
			var id = record ? record.get('id') : null;
			Ext.Ajax.request(
		    {
		    	url 	: url
	    		,method	: 'POST'
				,params : {value: value, id: id}
	    		,success: function (response) 
	    		{
	    			var response = Ext.JSON.decode(response.responseText);
					field.setValidation(response.message);
	    		}
	    		,failure	: function (response)
	    		{
	    			field.setValidation("Unable to validate.");
	    		}
			 });
		}
		,validateUniquenessOfItems: function(field, value, record,storeId, url)
		{
			var id = record ? record.get('id') : null;
			Ext.Ajax.request(
		    {
		    	url 	: url
	    		,method	: 'POST'
				,params : {value: value, id: id,storeId: storeId}
	    		,success: function (response) 
	    		{
	    			var response = Ext.JSON.decode(response.responseText);
					field.setValidation(response.message);
	    		}
	    		,failure	: function (response)
	    		{
	    			field.setValidation("Unable to validate.");
	    		}
			 });
		}
		,selectDivContent : function(containerid)
		{
			if (document.selection) 
			{
	            var range = document.body.createTextRange();
	            range.moveToElementText(document.getElementById(containerid));
	            range.select();
        	}
        	else if (window.getSelection) 
        	{
            	var range = document.createRange();
            	range.selectNode(document.getElementById(containerid));
            	window.getSelection().addRange(range);
        	}
		}
		,IsJsonString : function(str)
		{
			try 
			{
        		JSON.parse(str);
		    } 
		    catch (e) 
		    {
		        return false;
		    }
		    return true;
		}
		,getBarCodeRfidStore	: function()
		{
			return	  Ext.create('Ext.data.Store', {
			fields: ['label', 'value'],
			data : [
					{"label":"BARCODE", "value":"BARCODE"},
					{"label":"RFID", "value":"RFID"},
					{"label":"NOT APPLICABLE", "value":"NOT APPLICABLE"},
				  ]
			});
		}
		,getTechnicalYesNoStore	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
						{"label":"YES", "value":"YES"},
						{"label":"NO", "value":"NO"}
					]
			});
		}
		,getIssueToStoress : function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
						{"label":"User", "value":"User"},
						{"label":"Building", "value":"Building"},
						{"label":"Section", "value":"Section"},
						//{"label":"Sub Store", "value":"SubStr"}
					]
			});
		}/*,
		getIssueToStore : function()
		{
			return Ext.create('Ext.data.Store', {
		    fields: ['issueToId', 'issueToName'],
		    data : [
		        {"issueToId":"1", "issueToName":"User"},
		        {"issueToId":"2", "issueToName":"Building"},
		        {"issueToId":"3", "issueToName":"Section"}
		        ]
			});
		}*/
		,getIssueToStoreVisitor : function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
                        
						{"label":"User",    "value":"User"},
						{"label":"Building", "value":"Building"},
						{"label":"Section", "value":"Section"},
						//{"label":"Visitor", "value":"Visitor"}
					     {"label":"Sub Store", "value":"SubStr"}
					]
			});
		},
		getIssueToStoreVisitorInv : function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
                        
						{"label":"Self",    "value":"User"},
						{"label":"Building", "value":"Building"},
						{"label":"Section", "value":"Section"}
						//{"label":"Visitor", "value":"Visitor"}
					//	 {"label":"Sub Store", "value":"SubStr"}
					]
			});
		},
		getTechnicalGoodBadStore	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
						{"label":"SERVICEABLE", "value":"SERVICEABLE"},
						{"label":"NON-SERVICEABLE", "value":"NON-SERVICEABLE"}
					]
			});
		},
		getLedgerViewCombo	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
						{"label":"SUM", "value":"SUM"},
						{"label":"INDIVIDUAL", "value":"INDIVIDUAL"}
					]
			});
		},
		renderLeaveType:function(value)
		{
			if(value == true)
				return 'On Leave'.fontcolor("#FF0033");
			else
				return 'On Duty'.fontcolor("#006600");
		},
		getAuthenticatedOfficerId : function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
                        
						{"label":UserData.firstname+" "+UserData.lastName,"value":UserData.userId},
					]
			});
		}
		,renderFormattedApprovalStatus: function(value)
		{
			if(value == "ASSIGNED")
				return 'ASSIGNED'.fontcolor("#006600");
			else
				return 'REMOVED'.fontcolor("#FF0033");
		},
		getRequestTypeCombo	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
						{"label":"Manual", "value":"MIE"},
						{"label":"Scan Copy Upload", "value":"SC"}
					]
			});
		}
		,renderFormatForRequestType: function(value)
		{
			if(value =="MIE")
				return 'Manual';
			else if (value =="SC")
			return '<b>Scanned Copy</b>';
			else
				return ''.fontcolor("red");
		}
		 
		,getPriorityStore	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['name', 'id'],
				data : [
                        {"name":"High", "id":"HIGH"}
						,{"name":"Medium", "id":"MEDIUM"}
                       , {"name":"Low", "id":"LOW"}
					]
			});
		}
		
		,getFileTypeStore	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['name', 'id'],
				data : [
                        {"name":"Open/General", "id":"OPENGEN"}
						,{"name":"Secured", "id":"SECURE"}
					]
			});
		}
		
		,getFileStatusStore	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['status', 'id'],
				data : [
                        {"status":"DRAFT", "id":"1"}
						,{"status":"SUBMITTED", "id":"2"}
						//,{"status":"FORWARD", "id":"3"}
						//,{"status":"TRANSFER", "id":"4"}
						//,{"status":"IN TRANSIT", "id":"5"}
						//,{"status":"HOLD", "id":"6"}
						//,{"status":"REVERT", "id":"7"}
						//,{"status":"REJECT", "id":"8"}
						//,{"status":"CLOSE", "id":"9"}
						//,{"status":"PENDING", "id":"10"}
						//,{"status":"WAITINGAPPROVAL", "id":"11"}
						//,{"status":"APPROVED", "id":"12"}
						//,{"status":"APPROVE", "id":"13"}
						,{"status":"CLOSED", "id":"14"}
						//,{"status":"UNDERPROCESS", "id":"15"}
					]
			});
		}
		
		
		,getIssueStoreForItems	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
                        {"label":"MAIN STORE", "value":"MAINSTR"},
						{"label":"SUB STORE", "value":"SUBSTR"}
						
					]
			});
		}
		,getRequestedSelectedTypeOfStoreForItems	: function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
						{"label":"SUB STORE", "value":"SUBSTR"},
						{"label":"MAIN STORE", "value":"MAINSTR"}
					]
			});
		},
		getRequestStoreForAssetRequestPending :function(type)
		{
			return	Ext.create('Ext.data.Store',
			{
				 model		: 'DashBoardAssetRequestModel'
				,pageSize	: 20
				,autoLoad 	: true
				,proxy 	: 
				{
					 type 		: 'ajax'
					,url 		: AMS.Urls.gridData
					,extraParams: {actionType : 'pendingAssetRequestsDashBaordDetailsService',extraParams: type}
					,reader 	: 
					{
						type 			: 'json'
						,rootProperty	: 'records'
						,totalProperty	: 'total'
					}
				}
			});
		},
		getIssuedItemsHistoryStore :function(storeandCode)
		{
			return	Ext.create('Ext.data.Store',
			{
				 model		: 'IssuedItemsHistory1Model'
				,pageSize	: 20
				,autoLoad 	: true
				,proxy 	: 
				{
					 type 		: 'ajax'
					,url 		: AMS.Urls.gridData
					,extraParams: {actionType : 'mainInventoryIssuedQtyHistoryDetailsService',extraParams: storeandCode}
					,reader 	: 
					{
						type 			: 'json'
						,rootProperty	: 'records'
						,totalProperty	: 'total'
					}
				}
			});
		},
		getItemWiseInventoryReportsTypeStore : function()
		{
			return	Ext.create('Ext.data.Store', {
				fields: ['label', 'value'],
				data : [
                        
						{"label":"Employee",    "value":"User"},
						{"label":"Building", "value":"Building"},
						{"label":"Section", "value":"Section"},
					    {"label":"Date Wise Report", "value":"Dwr"}
					]
			});
		},
		getSectionWiseFieStatusStore :function(type)
		{
			return	Ext.create('Ext.data.Store',
			{
				 model		: 'fileStatusDashBoardModel'
				,pageSize	: 20
				,autoLoad 	: true
				,proxy 	: 
				{
					 type 		: 'ajax'
					,url 		: AMS.Urls.gridData
					,extraParams: {actionType : 'fileDashBoardService',extraParams: type}
					,reader 	: 
					{
						type 			: 'json'
						,rootProperty	: 'records'
						,totalProperty	: 'total'
					}
				}
			});
		},
		getFileStatusReportStoreForReports : function()
		{
			return Ext.create('Ext.data.Store',
					{
						 model			: 'fileStatusReportModel'
						,autoLoad		:	true
						,pageSize		:	20
						,remoteSort     : true
						,proxy 	: 
						{
							type 		: 'ajax'
							,url 		: AMS.Urls.gridData
							,extraParams: {actionType : 'fileStatusReportService'}
							,reader 	: 
							{
								type 			: 'json'
								,rootProperty	: 'records'
								,totalProperty	: 'total'
							}
						}
			});
			
		}
	}
});

/**
 * @author rabindranath.s
 * Convert Camel Case to Regular String
 */
String.prototype.camelCaseToRegular = function()
{
	return this.replace( /(^[a-z]+)|[0-9]+|[A-Z][a-z]+|[A-Z]+(?=[A-Z][a-z]|[0-9])/g,
		function(match, first)
		{
			if (first)
			{
				match = match[0].toUpperCase() + match.substr(1);
				return match;
			}

			return ' ' + match;
		}
   	)
}

function renderStatus(val)
{
	if(val == COMMON_STATUS["2"])
	{
		return "<img src='./images/assign.png' title='Assigned' />";
	}
	else if(val == COMMON_STATUS["3"])
	{
		return "<img src='./images/closed1.png' title='Closed' />";
	}
	else
	{
		return "<img src='./images/open.png' title='Open' />";
	}
}


function loadExternalFile(filePath, fileType, callBackFn)
{
	var head = document.head || document.getElementsByTagName('head')[0];
    if("js"==fileType)
    {
         var script = document.createElement("script");
         script.type = "text/javascript";
         script.src = filePath;
         head.appendChild(script);
    }
    else if("css"==fileType)
    {
        var linkEL = document.createElement('link');
        linkEL.type = 'text/css';
        linkEL.rel = 'stylesheet';
        linkEL.href = filePath;
        head.appendChild(linkEL);
    }
}
function showPrintFormInNewTab(url)
{
	window.open(url, '_blank');
	//sleep(500);
	//window.print();
	/*win.onload = function() { 
			window.print();
		};*/
}

function printBarCodeTab(url)
{
	window.open(url, '_blank');
	sleep(500);
	//window.print();
	win.onload = function() { 
			window.print();
	};
}


