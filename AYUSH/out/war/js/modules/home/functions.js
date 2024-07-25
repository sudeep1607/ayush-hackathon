/**
 * @author rabindranath.s
 * @email rabindra.nitr@gmail.com
 */
var HOME = {
	activeMenuId: null
	,activeScreenId: null
	,generateToolBar: function()
	{
		var me = this,
			toolbarItems = [];
		var jsonData = Ext.JSON.decode(AMS.menuJsonData);

		for (var index = 0; index < jsonData.length; index++) 
		{
			var menuId	= jsonData[index].id;
			var menuText= jsonData[index].menuText;
			var viewMode = jsonData[index].viewMode;
			var btnConfig = { };

			btnConfig.xtype		= 'menubutton';
			btnConfig.notification	= jsonData[index].count;
			btnConfig.ntfnRefId	= 0;
			btnConfig.notificationCls= 'notificationCls';
			btnConfig.menuId	= menuId;
			btnConfig.text		= menuText;
			btnConfig.iconCls	= jsonData[index].iconCls;
			btnConfig.toggleGroup= 'mainMenu';
			btnConfig.handler	= me.replaceCenterRegion.bind(me, menuId, menuText, viewMode);
			me.activeMenuId = jsonData[0].id;
			btnConfig.listeners = {
				click: function(btn)
				{
					
					/*switch(btn.menuId)
					{
						case 1 : // User management
									if(AMS.usermanagement == 0)
									{
										loadExternalFile("../../AMS/js/modules/userInfo/user.js","js");
										AMS.usermanagement = 1;
										break;
									}
									
						case 2 : // masters
									if(AMS.masters == 0)
									{
										loadExternalFile("../../AMS/js/modules/userInfo/role.js","js");
										loadExternalFile("../../AMS/js/modules/masters/project.js","js");
										loadExternalFile("../../AMS/js/modules/masters/designation.js","js"); 
										AMS.masters = 1;
									}
									break;
						default: 
									break;
					}*/
					
					me.activeMenuId = btn.menuId;
					var toolbar = btn.up('toolbar');
					if (toolbar.activeBtn)
					{
						toolbar.activeBtn.removeCls('vpcs-menubar-btn-active');
					}
					btn.addCls('vpcs-menubar-btn-active');
					btn.removeCls('vpcs-menubar-btn-notify');
					toolbar.activeBtn = btn;
					
					/** 
					 *  Below commented code is show the notification along with records count
					 */
					//if(btn.notification) btn.btnNotificationEl.removeCls(this.notificationCls);
					
				}
			};
			toolbarItems.push(btnConfig,'-');
		}

		return toolbarItems;
	}
	/**
	 * Preferred Method to update viewport centre region
	 */
	,replaceCenterRegion: function(menuId, menuText, viewMode, btn, e, screenId)
	{
		var me = this, viewport, centerRegion, content;
		try
		{
			viewport = Ext.ComponentQuery.query('viewport')[0];
			centerRegion = viewport.down('[region=center]');
			viewport.remove(centerRegion);
			viewport.setLoading(true);
			content = me.getCenterRegionContent(menuId, menuText, viewMode, screenId);
			content.region = 'center';
			viewport.add(content);
			//viewport.updateLayout();
			viewport.setLoading(false);
			
			
		}
		catch(err) 
		{
			me.print(err);
		}
	}
	,addItems2CenterRegion: function(menuId, menuText, viewMode, btn, e, screenId)
	{
		var me = this, viewport, centerRegion, content;
		viewport = Ext.ComponentQuery.query('viewport', 1)[0];
		centerRegion = viewport.down('[region=center]');
		content = me.getCenterRegionContent(menuId, menuText, viewMode, screenId);
		centerRegion.removeAll();
		centerRegion.add(content);
		//centerRegion.updateLayout();
	}
	,getCenterRegionContent: function(menuId, menuText, viewMode, screenId2Activate)
	{
		if (viewMode == 'TreePanel')
		return this.getContentAsTreePanel(menuId, menuText);
		return this.getContentAsTabPanel(menuId, menuText, screenId2Activate);
		
	}
	,getContentAsTreePanel: function(menuId, menuText)
	{
		var me = this;
		return {
			title		: menuText
			,xtype		: 'container'
			,layout		: 'border'
			,style		: 'background: #0f0f0f;'
			,items		:
			[
			 	{
			 		title		: menuText
					,region		: 'west'
					,xtype		: 'treepanel'
					,rootVisible: false
					,store		: me.getTreeStore(menuId)
					,width		: 200
					//,margin		: '0 2 0 0'
					,collapsible: true
					,split		: {width: 4, style: 'background-color:#ffaa99; '}
					,collapseDirection: 'left'
					,header		:
					{
						items	:
						[
						 	{
						 		xtype		: 'menutool'
					 			,items	:
						 		[
									{
										xtype	: 'menucheckitem'
										,text	: 'Collapse On Node Click'
										,checkHandler: function(item, value)
										{
											this.up().menutool.up('treepanel').collapseOnNodeClick = value;
										}
										,handler: function(item, e)
										{
											this.up().hide();
										}
									}
						 		]
						 	}
						]
					}
					,listeners	:
					{
						itemClick:  me.replaceInnerCenterRegion.bind(me) 
					}
				},{
					title		: menuText
					,region		: 'center'
					,xtype		: 'panel'
					,layout		: 'fit'
				}
			]
		}
	}
	,getTreeStore: function(menuId)
	{
		var treeItems = null;
		Ext.Ajax.request(
		{
			url			: './getLoggedInUserScreens?menuId='+ menuId
			,method 	: 'GET'
			,waitMsg	: 'Fetching...'
			,async		: false
			,success	: function (response) 
			{
				treeItems = Ext.decode(response.responseText);
			}
		});

		var store = Ext.create('Ext.data.TreeStore', 
		{
			root	: 
			{
				expanded  : true
				,children	: treeItems
			}
		});

		return store;
	}
	,replaceInnerCenterRegion: function(view, record, item, index, e, eOpts )
	{
		var me = this, container, centerRegion, content, module, refMethod, err = {message: ''};
		try
		{
			if(!record.data.leaf) return;
	
			view.panel.mask();
			container = view.panel.up();
			centerRegion = container.down('[region=center]');
			container.remove(centerRegion);
			refMethod = record.get('method').trim();
			module = record.get('module').trim().toUpperCase();

			if (window[module] && window[module][refMethod])
			{
				content = eval(module + '.' + refMethod + '(' + record.get('id') +')');
			}
			else
			{
				content = {title: 'Something Went Wrong...', layout: 'center' , data: '<div style="text-align:center; position:relative; top:45%; font:16px helvetica,arial,verdana,sans-serif; color:gray;">Something Went Wrong...</div>'};

				err.message = window[module] ? (module + '.' + refMethod + '() is ' + window[module][refMethod]) : (module + ' is ' + window[module]);
				me.print(err)
			}
			content.region = 'center';

			if (view.panel.collapseOnNodeClick  && !view.panel.floatedFromCollapse)
			{
				view.panel.collapse();
			}
			container.add(content);
			view.panel.unmask();
		}
		catch(err) 
		{
			me.print(err);
		}
	}
	,getContentAsTabPanel: function(menuId, menuText, screenId2Activate)
	{
		var me = this,
			screenItems,
			screenItem,
			screen,
			module,
			refMethod,
			screenName,
			iconCls,
			screenId,
			tabPanelItems = [],
			len,
			tabPanel,
			err = {message: ''},
			count,
			activeTabIndex = 0,
			activeScreenId;
		try
		{
			Ext.Ajax.request(
			{
				url			: AMS.Urls.getScreensByMenuId
				,params		: {menuId: menuId,roleId:UserData.defaultRoleId}
				,method		: 'POST'
				,waitMsg	: 'Fetching...'
				,async		: false
				,success	: function (response) 
				{
					screenItems = Ext.decode(response.responseText);
				}
			});

			for (var i = 0, len = screenItems.length; i < len; i++) 
			{
				screenItem = screenItems[i];
				module = screenItem.module.trim().toUpperCase();
				refMethod = screenItem.method;
				screenName = screenItem.text;
				iconCls = screenItem.iconCls;
				screenId = screenItem.id;
				count	= screenItem.count;

				if (!window[module] || !window[module][refMethod])
				{
					err.message = window[module] ? (module + '.' + refMethod + '() is ' + window[module][refMethod]) : (module + ' is ' + window[module]);
					me.print(err);
					continue;
				}

				screen = eval(module + '.' + refMethod + '(' + screenId +')');

				if (screen)
				{
					if(menuId != 6)
						{ 
					   screen.tabConfig = {width : 140, title: screenName, iconAlign: 'top', iconCls: iconCls, tooltip: screenName};
						}
					else
						{
						   screen.tabConfig = { iconAlign: '', cls: 'mydashboard'};
						}
					if (screenItem.count != null || screenItem.count != undefined && screenItem.count > 0)
					{
					/*	screen.tabConfig.notification	= count;
						screen.tabConfig.notificationCls= 'notificationCls';*/
						screen.tabConfig.iconTpl = me.getIconTpl(count);
					}

					Ext.applyIf(screen, {
						title	: screenName
						,autoScroll: true
						,screenId: screenId
					});

					if (screen instanceof Ext.panel.Panel)
					{
						
					}
					else
					{
						/**
						 * Here screen should be an object with configs and items property
						 */
						if (screen.height)	delete screen.height;
						if (screen.width)	delete screen.width;
						if (screen.maxHeight)	delete screen.maxHeight;
						if (screen.maxWidth)	delete screen.maxWidth;
						screen.layout = 'fit';
					}
				}
				tabPanelItems.push(screen);
				if (screenId2Activate && screenId2Activate == screenId)
				{
					activeTabIndex = i;
					activeScreenId = screenId2Activate;
				}
			}

			if (tabPanelItems.length > 0 && !activeScreenId)
			{
				activeScreenId = tabPanelItems[0].screenId;
			}

			me.activeScreenId = activeScreenId;
			tabPanel = Ext.create('Ext.tab.Panel', 
			{
				tabPosition	: 'left'
				,renderTo 	: document.body
				,cls		: 'x-odch-hometab'
				,bodyCls	: 'x-odch-hometab-body'
				,items		: tabPanelItems
				,removePanelHeader: false
				,bodyBorder	: true
				,autoScroll	: false
				,overflow	: true
				,enableOverflow: true
				,itemId		: 'menuItemsTabPanel'
				,activeTab	: activeTabIndex
				,tabBar		:
				{
					plain		: true
					,tabRotation: 0
					,tabStretchMax: true
					,defaults	: {overflow	: false
					,enableOverflow: false,height: 99}
					,margin		: '0 2 0 0'
				}
				,listeners:
				{
					tabchange: function(tp, newCard, oldCard)
					{
						(Ext.ComponentQuery.query('#'+newCard.itemId)[0]).getStore().load(); //added by kp  on 05th sep 2015 
						me.activeScreenId = newCard.screenId;
					}
				}
			});

			return tabPanel;
		}
		catch(err) 
		{
			me.print(err);
		}
	}
	,getTabWidth: function()
	{
		return 140;
	}
	,print: function(err)
	{
		var error = [err.message];
		if (err.fileName) error.push('File Name: ' + err.fileName);
		if (err.lineNumber) error.push('Line No: ' + err.lineNumber);
		if (console && AMS.APPLICATION_MODE == 'DEVELOPMENT')
			console.log(error.join('\n'));
	}
	,getIconTpl: function(count)
	{
		/**
		 * Private Method
		 */
		var iconTpl =
			/*'<span class="blinksc" style="font-size: 15px;font-weight: bolder; height: 15px; width: auto;color : magenta; margin-left: 98px;">'+ count +'</span>';*/
			'<span class="blinksc" id="{id}-btnIconEl" data-ref="btnIconEl" role="presentation" unselectable="on" class="{baseIconCls} ' +
					'{baseIconCls}-{ui} {iconCls} {glyphCls}{childElCls}" style="color:red;font-weight:bold; font-size:20px;' +
				'<tpl if="iconUrl">background-image:url({iconUrl});</tpl>' +
				'<tpl if="glyph && glyphFontFamily">font-family:{glyphFontFamily};</tpl>">' +
				'<tpl if="glyph">&#{glyph};</tpl><tpl if="iconCls || iconUrl"></tpl>' + count +
				//'<tpl if="iconValue"></tpl>{iconValue}</tpl>' +
				
			'</span>';

		return iconTpl;
	}
	,activateDefaultMenu: function()
	{
		var me =  this, menuId, screenId, viewport, toolbar, menu, screen;
		menuId = me.activeMenuId;//AMS.Constants.menus[0].id;
		screenId = 36

		viewport = Ext.ComponentQuery.query('viewport')[0];
		toolbar = viewport.down('toolbar[region=north]', 1);
		menu = toolbar.down('button[menuId=' + menuId + ']');
		if (menu && !menu.disabled)
		{
			menu.doToggle();
			if (menu.fireEvent('click', menu) !== false && !menu.isDestroyed)
			{
				menu.handler(menu, null, screenId);
			}
		}
	},
   getScreenId:function()
	{
	   var me= this;
		 var activeScreeid = me.activeScreenId;
		 return activeScreeid;
	}
}
