var SECTIONFILEDASHBOARD =	
{
	
	init: function()
	{
		this.defineSectionDashboardModel();
	}
	,defineSectionDashboardModel : function()
	{
		Ext.define('SectionFileDashBoard', 
		{
			extend	: 'Ext.data.Model',
			fields	:
			[
				  {	name:  'id'							            }
				, {	name:  'sectionName'							}
				, {	name:  'pendingCount'							}
				, {	name:  'approvedCount'							}
				, {	name:  'rejectedCount'							}
				, {	name:  'closedCount'							}
				 
			 ]
		});
		
		Ext.define('fileStatusDashBoardModel',
				{
					 extend	:	'Ext.data.Model'
					,fields	:
						[
					       {	name :	'id'	                            }
						  ,{ 	name :	'fileId'	                        }
						  ,{	name:  'fileNo'								}
						  ,{	name:  'fileCreatedDate'					}
						  ,{	name:  'section'							}
						  ,{	name:  'fileName'							}
						  ,{	name:  'fileSubject'						}
						  ,{	name:  'fileDetails'						}
						  ,{	name:  'fileComments'						}
						  ,{	name:  'filePriority'						}
						  ,{	name:  'fileType'							}
						  ,{	name:  'fileBarcode'						}
						  ,{	name:  'sectionName'						}
						  ,{	name:  'designation'						}
						  ,{	name:  'fileStatus'							}
						  ,{	name:  'fileInitiatorName'					}
						  ,{	name:  'attachFilePath'						}
						  ,{	name:  'wfaId'								}
						  ,{	name:  'priority'							}
						  ,{	name:  'pendingAt'							}
						  ,{	name:  'pendingWith'						}
						  ,{	name:  'fileInTime'							}
						  ,{	name:  'fileOutTime'						}
						  ,{	name:  'duration'							}
						  ,{	name:  'fileProcess'							}
						  
						]
				});
		
		
	}
	,showTabularFormatData :function()
	{
		var me		   = this;
		var sectionDashboardStore	=	me.getSectoinDashboardStore();
		var sectoinDashboardGrid  =  Ext.create('Ext.custom.grid.Panel', 
				{
					 title			:	'<font color="#42a7f4"><b>Section wise File Status Report  </b></font>'
				    ,titleAlign 	:	'center'
					,itemId			: 	'sectoinDashboardGridId'
					,store			:	sectionDashboardStore
					,columnLines	: 	true
					,width			:  500
					,pagination	    : false
					, features: [{
			            ftype: 'summary'
			        }]
					,scrollable     : 'y'
			    	,border        :true
					,forceFit:true
					,style			: 'margin-left:30px;'
					,columns		: 
								[
								   { align:'center',cellWrap: true, 	text : 'Section Id'               ,width :20	,dataIndex : 'id'            ,hidden:true        ,renderer: 	function (x,y,z) {  return me.renderdbHeader(x,y,z,'');}}
		                          ,{cellWrap: true, 	
		                        	text : 'Sections'                               ,width :150	,dataIndex : 'sectionName'  
		                            ,summaryRenderer: function(value, summaryData, dataIndex) {
		      		                      return '<b>Total File Request Status</b>'.fontcolor("maroon"); 
		        		                  }
		                        	,renderer: 	function (x,y,z) {  return me.renderdbHeader(x,y,z,'');}
		                          }
		                          ,{ align:'center',cellWrap: true, 	text : 'Pending'              ,summaryType: 'sum'       ,width :100	,dataIndex : 'pendingCount'	 ,renderer: 	function (x,y,z) { return me.renderdbdata(x,y,z,'PENDING'); }} 	 
		                          ,{ align:'center',cellWrap: true, 	text : 'In-Transit'     	      ,summaryType: 'sum'       ,width :100	,dataIndex : 'approvedCount'  ,renderer: 	function (x,y,z) { return me.renderdbdata(x,y,z,'APPROVED'); }}                                                                                               
		                          ,{ align:'center',cellWrap: true, 	text : 'Rejected'     	      ,summaryType: 'sum'       ,width :100	,dataIndex : 'rejectedCount'   ,renderer: 	function (x,y,z) { return me.renderdbdata(x,y,z,'REJECT'); }} 
		                          ,{ align:'center',cellWrap: true, 	text : 'Closed'     	      ,summaryType: 'sum'       ,width :100	,dataIndex : 'closedCount'   ,renderer: 	function (x,y,z) { return me.renderdbdata(x,y,z,'CLOSED'); }} 
		                        ]
							,listeners		: 
							{
								renderer : function(value, meta)
								{
									meta.style = "font-weight:bold;color:maroon;";
								    return value;
								}
							}
						 ,tbar	:
								[
						    , '->'
							      ,{
							            xtype: 'exporterbutton',
							            text: 'Export',
							            format: 'excel',
							            iconCls: 'x-ia-excel-icon',
							            title:  'Total File Status Report Section Wise'
							      	}
							     ]
		      });
		return  Utilities.showWindow({
                 title        : 'Section Wise File Status'
                ,items        : sectoinDashboardGrid
                ,width        : 900
                ,height       : 650
        });
	}
	,getSectoinDashboardGrid : function()
	{
		var me		   = this;
		var sectionDashboardStore	=	me.getSectoinDashboardStore();
		
		var  sectionStoreChart = Ext.create('Ext.chart.PolarChart', {
		
		    title: 'Section Wise File Status Chart  <button type="button"  class="remarksbutton" onclick="SECTIONFILEDASHBOARD.showTabularFormatData()"> <font size="2px;">View Tabular Data</font></button>',
		    animation: true,
		    width: 650,
		    height :350,
			collapsible    :true
		    ,renderTo: Ext.getBody()
		    ,style			: ' margin-left:100px;',
		    store: sectionDashboardStore
		   , legend: {
		        docked: 'bottom'
		    },
		    series: [{
		        type: 'pie',
		        angleField: 'id',
		        lengthField: 'length',
		        colors: ["#9aff4f", "#35b4e3", "#ffb400"],
		        label: {
		            field: 'sectionName',
		            display: 'inside'
		        }
		    }]
		});
		

		         
		
		
		var   sectionFileRequestDashBaoardForm	 =	Ext.create('Ext.custom.form.Panel',
		{
			layout		: {type: 'hbox', align: 'stretch' }
			,width 		: 750
			,style			: 'margin-top:40px;'
			,items:
			[
				{
					 xtype :'container',
					 height		:	400
					,items		:	[sectionStoreChart]
				}
			]
		});
return	sectionFileRequestDashBaoardForm;
}
		,renderdbHeader:function(value,x,rec,dep){
			if(value)
				{
					reValue ="<div class=dbHeader><font color='blue'><b><blink>"+value+"</blink></b></font></div>";
				}else
				{
					reValue ="<div class=dbHeader></div>";
				}
			return reValue;
		}
		,renderdbdata:function(value,x,rec,status)
		{
			var reValue="";
			var type = status+','+rec.get('id');
			var sectionName = rec.get('sectionName');
			  x.tdCls = 'mousepointer';
			if(value)
				{
					reValue ="<div class=dblink onclick=viewDBdata('"+ type+"')><b>"+value+"</b></div>";
				}else
				{
					reValue ="<div class=dblink onclick=viewDBdata('"+ type +"')>"+ 0 +"</div>";
				}
			return reValue;
		}
		,getSectoinDashboardStore : function()
		{
			return Ext.create('Ext.data.Store',
			{
				model		: 'SectionFileDashBoard'
				,pageSize	: 20
				,autoLoad 	: true
				,proxy 	: 
				{
					 type 		: 'ajax'
					,url 		: AMS.Urls.gridData
					,extraParams: {actionType : 'sectionWiseFileStausService'}
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


function viewDBdata(storeAndTypeOfCount)
{
	var win = Ext.WindowManager.getActive();
	if (win) {
	    win.close();
	}
	var sectionWiseFieStatusStore = U.getSectionWiseFieStatusStore(storeAndTypeOfCount);
	sectionWiseFieStatusStore.load();
	var keys =  storeAndTypeOfCount.split(',');
	var dbColumns="";

							dbColumns= 	[
										   { cellWrap: true		,text	: 	'File Id'			,width:50	,dataIndex	:	'fileId'	,hidden:true			}
										  ,{ cellWrap: true		,text	:	'Section'			,width:70	,dataIndex	:	'sectionName'						}
										  ,{ cellWrap: true		,text	:	'Initiated By'		,width:70	,dataIndex	:	'fileInitiatorName'					}
										  ,{ cellWrap: true		,text	:	'File Name'			,width:150	,dataIndex	:	'fileName'							}
										  ,{ cellWrap: true		,text	:	'Type'				,width:50	,dataIndex	:	'fileType'							}
										  ,{ cellWrap: true		,text	:	'Created On'		,width:50	,dataIndex	:	'fileCreatedDate'	 ,xtype: 'datecolumn',format: 'd-m-Y'}
										  ,{ cellWrap: true		,text	:	'File Status'		,width:50	,dataIndex	:	'fileStatus'}
										  
										] 
	
	
	var dbHistoryGridForFile	= Ext.create('Ext.custom.grid.Panel',
	{
		itemId			: 	'dbHistoryGrid2'
	   ,store			: sectionWiseFieStatusStore	
		,columnLines	: 	true
		,forceFit		: 	true
 		,scrollable	    : 'y'
		,autoWidth:true
		,height:400
		,pagination	: true
		,columns		: dbColumns
		,tbar	:
			[
		       '->',
		     	{
                    xtype: 'exporterbutton',
                    text: 'Download Data',
                    format: 'excel',
                    iconCls: 'x-ia-excel-icon'
		      	}
		      ]
		
	});
	Ext.create('Ext.custom.window.Window',
			{
		         title       :  '<font color="yellow">File Status Detials </font>'	 
				,items		: [dbHistoryGridForFile]
				,layout		: {type: 'vbox' , align: 'stretch'}
				,autoShow	: true
				,scrollable	: 'y'
				,width	:1000
			});
	
}
SECTIONFILEDASHBOARD.init();