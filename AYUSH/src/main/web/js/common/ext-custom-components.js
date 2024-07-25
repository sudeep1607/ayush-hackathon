/**
 * @author rabindranath.s
 * List of Custom Extjs Components to be used instead of Extjs Core
 * 1. Ext.custom.grid.Panel for paging grid
 */

Ext.define('Ext.data.reader.JsonReader',
{
	extend			: 'Ext.data.reader.Json'
	,alias 			: 'reader.jsonreader'
	,rootProperty	: 'records'
	,totalProperty	: 'total'
});

Ext.define('Ext.data.reader.ComboJsonReader',
{
	extend			: 'Ext.data.reader.Json'
	,alias 			: 'reader.combojsonreader'
});

/** Combo Field common Model Throughout Application **/

Ext.define('ComboModel', 
{
	extend		: 'Ext.data.Model'
	,fields		: ['id'		, 'name'	, 'refId']
	,idProperty	: 'xyz'
});

Ext.define('Ext.data.GridStore',
{
	 extend		: 'Ext.data.Store'
	,alias		: 'store.gridstore'
	,pageSize	: 20
	,autoLoad 	: false
	,autoDestroy: true
	,remoteSort	: true
	,remoteFilter: true
	,actionType	: undefined
	/*,constructor: function(config)
	{
		console.log(this);
		console.log(config);
		console.log(config.actionType);
		console.log(this.proxy);
		this.proxy.extraParams = {actionType: this.actionType};
		this.callParent(arguments);
	}*/
	/*,applyProxy : function()
	{
		
	}*/
	,proxy 		: 
	{
		type 		: 'ajax'
		,url 		: AMS.Urls.gridData
		//,extraParams: {actionType : 'grievanceCategoryService'}
		,reader 	: 'jsonreader'
	}
});

Ext.define('Ext.data.ComboStore',
{
	 extend		: 'Ext.data.Store'
	,alias		: 'store.combostore'
	,model		: 'ComboModel'
	,autoLoad 	: false
	,autoDestroy: true
	,pageSize	: 0
	,proxy 		: 
	{
		type 	: 'ajax'
		,url 	: AMS.Urls.comboData
		,reader : 'combojsonreader'
	}
});

Ext.define('Ext.custom.grid.Panel',
{
	extend		: 'Ext.grid.Panel'
	,requires	: ['Ext.ux.ProgressBarPager']
	,alias		: 'widget.customgrid'
	,border		: false
	,emptyText	: 'No data Available'
	,columnLines: true
	/**
	 * @cfg {Array} pageSlaps
	 * It defines the pageSlaps to be applied. Default to [25, 50, 75, 100]
	 */
	,pageSlaps	: undefined	//Array
	,forceFit	: true
	/**
	 * @cfg {Boolean} pagination
	 * true to add paging bar, make false to avoid paging bar, default to true
	 */
	,pagination	: true
	/**
	 * @cfg {Boolean} applyFilter
	 * true to add filter plugin, make false to avoid filter plugin, default to true
	 */
	,applyFilter: true
	,applyColTooltip: true
	,cls		: 'x-odch-grid'
	//,allowDeselect: true
	,autoLoad 	: false
	,bufferedRenderer: false 
	,initComponent: function()
	{
		var me = this;
		if (Ext.isObject(me.viewConfig))
		{
			Ext.applyIf(me.viewConfig, {stripeRows: true , deferEmptyText : false});
		}
		else
		{
			me.viewConfig = {stripeRows: true , deferEmptyText : false};
		}

		if (Ext.isArray(me.columns))
		{
			Ext.Array.each(me.columns, function(column)
			{
				//Ext.apply(me.columns[i], {variableRowHeight: true, cellWrap: true});
				if (column.hasOwnProperty('columns'))
				{
					Ext.Array.each(column.columns, function(col)
					{
						Ext.applyIf(col, {filter: {type: 'string'}/*, tooltip: column.text*/});
					});
				}
				else
				{
					Ext.applyIf(column, {filter: {type: 'string'}/*, tooltip: column.text*/});
					/*if (me.forceFit && !column.xtype  && !column.hasOwnProperty('width'))
					{
						Ext.applyIf(column, {flex: 1});
					}*/
				}
			});

			//me.forceFit = false;
		}

		/**
		 * Grid Filter
		 */
		if (me.applyFilter)
		{
			me.applyPlugin({ptype: 'gridfilters'});
		}
		if (me.applyColTooltip)
		{
			me.applyPlugin({ptype: 'gridcolumntooltip'});
		}

		//me.store.currentPage = 1;
		if (me.pagination == true)
		{
			if (!me.pageSlaps)
			{
				me.pageSlaps = [25, 50, 75, 100, 500, 750];
			}

			if (me.store.pageSize)
			{
				if (!Ext.Array.contains(me.pageSlaps), me.store.pageSize)
				{
					/**
					 * If the pageSize doesn't fall in the page slap, then update it.
					 */
					me.updatePageSlap();
				}
			}
			else
			{
				/**
				 * Sets default page size
				 */
				me.store.pageSize = me.pageSlaps[0];
			}

			me.bbar	= {
				xtype		: 'pagingtoolbar'
				,store		: me.store
				,displayInfo: true
				,pageSize	: me.store.pageSize
				,displayMsg	: '<b>Displaying Records {0} - {1} of {2} </b>'
				,emptyMsg	: '<b>No Records to Display</b>'
				,plugins	: Ext.create('Ext.ux.ProgressBarPager', {})
				,items		:
				[
					'-'
					,'->'
					,{
						xtype			: 'combo'
						,fieldLabel		: 'Show'
						,labelSeparator : ''
						,store			: me.pageSlaps
						,width			: 110
						,labelWidth		: 40
						,queryMode		: 'local'
						,value			: me.store.pageSize
						,forceSelection : true
						,listeners		:
						{
							select: function(combo, rec)
							{
								this.up('grid').getStore().pageSize = rec[0].get('field1');
								this.up('grid').child('pagingtoolbar').moveFirst();
							} 
						}
					}
					,{xtype: 'tbtext', text: 'Records Per Page'}
				]
			}
		}

		me.callParent(arguments);
	}
	,updatePageSlap: function()
	{
		var me = this, len, pageSize;
		len = me.pageSlaps.length;
		pageSize = me.store.pageSize;
		for (var i = 0; i < len; i++)
		{
			if (pageSize < me.pageSlaps[i])
			{
				Ext.Array.insert(me.pageSlaps, i, [pageSize]);
				break;
			}
			else if (i == len - 1)
			{
				me.pageSlaps.push(pageSize);
			}
		}
	}
	,applyPlugin: function(plugin)
	{
		var me = this;
		if (me.plugins)
		{
			if (me.plugins.length)
			{
				me.plugins.push(plugin);
			}
			else if ((typeof plugin == "string") || (typeof plugin == "object"))
			{
				me.plugins = [me.plugins, plugin];
			}
			else
			{
				throw new Error('Invalid Plugin Config: ' + this.$className);
			}
		}
		else
		{
			me.plugins = [plugin];
		}
	}
	,onDestroy: function()
	{
		var store = this.store;
		this.callParent();
		if (store)
		{
			store.destroy();
		}
	}
	,listeners	:
	{
		
		/*// This only works in Extjs 5 and later as this listeners not removed if overriden
		afterlayout: function(grid)
		{
			applyScroller(grid);
		}*/
	}
});

/**
*	Combo Field
*/
Ext.define('Ext.custom.form.field.ComboBox',
{
	extend 			: 'Ext.form.ComboBox'
	,xtype			: 'customcombo'
	,forceSelection	: true
	,typeAhead		: true
	,displayField   : 'name'
	,valueField		: 'id'
	,minChars		: 1
	,autoLoadOnValue: false
	,queryMode 		: 'local'
	//,anyMatch		: true
	,initComponent	: function()
	{
		var me = this;
		me.callParent(arguments);
	}
	,getSubmitValue : function()
	{
		return this.getValue();
	}
	,doSetValue: function(value, add)
	{
		var me = this,
			store = me.getStore(),
			Model = store.getModel(),
			matchedRecords = [],
			valueArray = [],
			key,
			autoLoadOnValue = me.autoLoadOnValue,
			isLoaded = store.getCount() > 0 || store.isLoaded(),
			pendingLoad = store.hasPendingLoad(),
			unloaded = autoLoadOnValue && !isLoaded && !pendingLoad,
			forceSelection = me.forceSelection,
			selModel = me.pickerSelectionModel,
			displayTplData = me.displayTplData || (me.displayTplData = []),
			displayIsValue = (me.displayField === me.valueField) && (me.forceSelection !== true),
			i, len, record, dataObj, raw;

		if (add && !me.multiSelect) {
			Ext.Error.raise('Cannot add values to non muiltiSelect ComboBox');
		}

		if (value != null && !displayIsValue && (pendingLoad || unloaded || !isLoaded || store.isEmptyStore))
		{
			if (value.isModel) {
				displayTplData.length = 0;
				displayTplData.push(value.data);
				raw = me.getDisplayValue();
			}
			if (add) {
				me.value = Ext.Array.from(me.value).concat(value);
			} else {
				if (value.isModel) {
					value = value.get(me.valueField);
				}
				me.value = value;
			}
			me.setHiddenValue(me.value);
			
			me.setRawValue(raw || '');
			if (unloaded && store.getProxy().isRemote) {
				store.load();
			}
			return me;
		}

		value = add ? Ext.Array.from(me.value).concat(value) : Ext.Array.from(value);

		for (i = 0 , len = value.length; i < len; i++)
		{
			record = value[i];
			
			if (!record || !record.isModel) {
				record = me.findRecordByValue(key = record);
				
				
				if (!record) {
					record = me.valueCollection.find(me.valueField, key);
				}
			}
			
			
			if (!record)
			{
				if (!forceSelection) {
					if (!record) {
						dataObj = {};
						dataObj[me.displayField] = value[i];
						if (me.valueField && me.displayField !== me.valueField) {
							dataObj[me.valueField] = value[i];
						}
						record = new Model(dataObj);
					}
				}

				else if (me.valueNotFoundRecord) {
					record = me.valueNotFoundRecord;
				}
			}
			
			if (record) {
				matchedRecords.push(record);
				valueArray.push(record.get(me.valueField));
			}
		}
		me.lastSelection = matchedRecords;

		me.suspendEvent('select');
		me.valueCollection.beginUpdate();
		if (matchedRecords.length) {
			selModel.select(matchedRecords, false);
		} else {
			selModel.deselectAll();
		}
		me.valueCollection.endUpdate();
		me.resumeEvent('select');
		return me;
	}
});


/**
 * Form Panel
 */
Ext.define('Ext.custom.hbox.form.Panel',
{
	extend			: 'Ext.form.Panel'
	,alias			: 'widget.customhboxform'
	,layout			: {	type : 'hbox'	,align: 'stretchmax'}
	,autoScroll		: true
	,defaults		: {	flex : 1	, layout: 'anchor', anchor: '100%' , width : 400	,xtype : 'container'}
	,trackResetOnLoad: true
	,viewReadOnly	: false
	,initComponent	: function()
	{
		var me = this;
		var me = this, fieldDefaults;
		fieldDefaults = 
		{
			labelAlign		: 'top'
			,anchor			: '100%'
			,labelSeparator : ''
			,labelStyle		: 'font-weight:bold;'
		};

		if (me.fieldDefaults)
		{
			Ext.applyIf(me.fieldDefaults, fieldDefaults);
		}
		else
		{
			me.fieldDefaults = fieldDefaults;
		}

		if(me.viewReadOnly)
		{
			me.on({
				afterrender: me.makeReadOnly
				,scope: me
				,single: true
			});
		}
		me.callParent(arguments);
	}
	,makeReadOnly: function()
	{
		var fields = this.query('field[isFormField]');
		for (var i = 0, len = fields.length; i < len; i++)
		{
			fields[i].setReadOnly(true);
		}
	}
});

Ext.define('Ext.custom.form.Panel',
{
	extend			: 'Ext.form.Panel'
	,alias			: 'widget.customform'
	,autoScroll		: true
	,bodyPadding	: '5px 5px 0px'
	,trackResetOnLoad: true
	,viewReadOnly	: false
	,initComponent	: function()
	{
		var me = this, fieldDefaults;
		fieldDefaults = {
				labelAlign	: 'left'
				,labelWidth	: 200
				,anchor		: '100%'
				,labelStyle	: 'font-weight:bold;'
				,labelSeparator: ''
		};

		if (me.fieldDefaults)
		{
			Ext.applyIf(me.fieldDefaults, fieldDefaults);
		}
		else
		{
			me.fieldDefaults = fieldDefaults;
		}

		if(me.viewReadOnly)
		{
			me.on({
				afterrender: me.makeReadOnly
				,scope: me
				,single: true
			});
		}
		me.callParent(arguments);
	}
	,makeReadOnly: function()
	{
		var fields = this.query('field[isFormField]');
		for (var i = 0, len = fields.length; i < len; i++)
		{
			fields[i].setReadOnly(true);
		}
	}
	,loadRecord: function(record)
	{
		this.setLoading(true);
		this.callParent(arguments);
		this.setLoading(false);
	}
});

/**
 * Window
 */
Ext.define('Ext.custom.window.Window',
{
	extend			: 'Ext.window.Window'
	,alias			: 'widget.customwindow'
	,cls			: 'x-odch-window'
	,autoShow 		: true
	,itemId			: 'customWindowId'
	,validateDirty	: true
	,draggable 		: false
	,initComponent	: function()
	{
		var me = this;
		me.callParent(arguments);
	}
	,beforeCloseWin: function()
	{
		var me = this;
		me.validateWinBeforeClose();
	}
	,onEsc	: function()
	{
		this.validateWinBeforeClose();
	}
	,validateWinBeforeClose : function()
	{
		var me = this;
		var form = me.down('form');
		if(form && !form.viewReadOnly && form.isDirty() && me.validateDirty)
		{
			Utilities.showAlert("Info", getLabel('FORM.DIRTY.CLOSE'), Ext.Msg.WARNING, Ext.Msg.YESNO, function(btn)
			{
				if (btn == 'yes')
				{
					me.close();
				}
			});
		}
		else
		{
			me.close();
		}
	}
	,initTools: function()
	{
		var me = this,
			tools = me.tools,
			i, tool;

		me.tools = [];
		for (i = tools && tools.length; i; ) {
			--i;
			me.tools[i] = tool = tools[i];
			tool.toolOwner = me;
		}

		// Add a collapse tool unless configured to not show a collapse tool
		// or to not even show a header.
		if (me.collapsible && !(me.hideCollapseTool || me.header === false || me.preventHeader)) {
		me.updateCollapseTool();
		// Prepend collapse tool is configured to do so.
		if (me.collapseFirst) {
			me.tools.unshift(me.collapseTool);
		}
		}

		// Add subclass-specific tools.
		me.addTools();
		
		if (me.pinnable) {
		    me.initPinnable();
		}
		
		// Make Panel closable.
		if (me.closable) {
			me.addClsWithUI('closable');
			me.addTool({
				xtype : 'tool',
				type: 'close',
				scope: me,
				handler: me.beforeCloseWin
			});
		}

		// Append collapse tool if needed.
			if (me.collapseTool && !me.collapseFirst) {
				me.addTool(me.collapseTool);
			}
		}
});

/**
 *  Grid Tool Bar Button custom class
 */
Ext.define('Ext.custom.tbar.Button',
{
	extend			: 'Ext.Button'
	,alias			: 'widget.customtbarbutton'
	,cls			: 'x-odch-tbar-btn'
	,iconCls		: 'x-odch-icon-add'
	,text			: 'Add Record'
	,initComponent	: function()
	{
		var me = this;
		me.callParent(arguments);
	}
});

/**
 *  Form Submission button custom class
 */
Ext.define('Ext.custom.form.submit.Button',
{
	extend			: 'Ext.Button'
	,alias			: 'widget.customformsubmitbutton'
	,cls			: 'x-odch-tbar-btn'
	,iconCls		: 'x-ia-save-icon'
	,height			: 35
	,width			: 110
	,text			: 'Save'
	,formBind 		: true
	,initComponent	: function()
	{
		var me = this;
		me.callParent(arguments);
	}
});

/**
 * 	Custom Ext.form.VTypes
 */
Ext.apply(Ext.form.VTypes,
{
	dateRange : function(val, field)
	{
		var date = field.parseDate(val);
		
		if(!date){
			return false;
		}
		if (field.startDateItemId)
		{
			var start = field.prev('datefield#' + field.startDateItemId);
			if (!start.maxValue || (date.getTime() != start.maxValue.getTime()))
			{
				start.setMaxValue(date);
				start.validate();
			}
		}
		else if (field.endDateItemId)
		{
			var end = field.next('datefield#' + field.endDateItemId);
			if (!end.minValue || (date.getTime() != end.minValue.getTime()))
			{
				end.setMinValue(date);
				end.validate();
			}
		}
		/*
		 * Always return true since we're only using this vtype to set the
		 * min/max allowed values
		 */
		return true;
	}
});

Ext.apply(Ext.form.VTypes,
{
	/**
	 * Currently keyStroke filtering doesn't work in numberfield, use textfield instead
	 */
	mobileNo:  function(v)
	{
		return /^([\d]{10})$/.test(v); 
		//return /^(([0-9]{10}))$/.test(v);
		//return /^([\d]{6,10})$/.test(v);  
	}
	,mobileNoText: 'Exact 10 digits number allowed'
	,mobileNoMask:  /[\d\+]/i
	//,mobileNoMask:  /[0-9]/i
});

Ext.apply(Ext.form.field.VTypes,
{
	colorHexCode:  function(v)
	{
		return /^(#+[A-F0-9]{6})$/.test(v);
	}
	,colorHexCodeText: 'Letters A-F in upper case and digits are allowed'
	,colorHexCodeMask: /[A-F0-9#]/i
});

Ext.apply(Ext.form.field.VTypes,
{
	onlyNumber:  function(v)
	{
		return /^([\d]{*})$/.test(v);
	}
	,onlyNumberMask: /[\d\+]/i
});

Ext.apply(Ext.form.field.VTypes,
{
	textWithSpace:  function(v)
	{
		return /^[a-zA-Z ]+$/.test(v);
	}
	,textWithSpaceText: 'Letters A-Z and space only allowed'
	,textWithSpaceMask: /[a-zA-Z9 ]/i
});

Ext.define('Override.form.field.VTypes', {
    override: 'Ext.form.field.VTypes',
    IPAddress:  function(value) {
        return this.IPAddressRe.test(value);
    },
    IPAddressRe: /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/,
    IPAddressText: 'Must be a numeric IP address',
    IPAddressMask: /[\d\.]/i
});


/**
 * 	End of Custom Ext.form.VTypes
 */

/**
 * MenuTool
 */
Ext.define('MenuTool',
{
	extend		: 'Ext.panel.Tool'
	//,alias	: 'widget.menutool'
	,xtype		: 'menutool'
	,type		: 'gear'
	,privates	:
	{
		onClick	: function(e, target)
		{
			var me = this,
			returnValue = me.callParent(arguments);
			if (returnValue && me.items)
			{
				if (!me.toolMenu)
				{
					me.toolMenu = new Ext.menu.Menu({
						items: me.items
						,menutool: me
					});
				}
		
				me.toolMenu.showBy(me.ownerCt, 'tr-br');
			}
		}
	}
	,onDestroy: function()
	{
		Ext.destroyMembers(this, 'toolMenu');
		this.callParent();
	}
});

/**
 * @author rabindranath.s
 * Custom Search Field is meant for to put in grid tool bar for applying custom filter
 * @cfg {array} filters e.g [{operator:"like",value:"",type:"string",fieldName:"name"}]
 * operator and type are optional and default to "like" and "string" respectively
 */
Ext.define('Ext.grid.field.Search',
{
	extend		: 'Ext.form.field.Text'
	,requires	: 'Ext.form.field.Text'
	,xtype		: 'gridsearchfield'
	,height		: 25
	,enableKeyEvents:true
	,width		: 200
	//,hideTrigger: true
	/**
	 * This line to be deleted
	 */
	,searchParam: 'searchParam'
	/**
	 * @cfg {array} filters
	 * e. g. [{operator:"like", type:"string", fieldName:"skuNo"}, {operator:"like", type:"string", fieldName:"mobileNo"}]
	 * operator and type are optional and default to "like" and "string" respectively
	 */
	,filters	: []
	,initComponent: function()
	{
		var me = this;

		me.filterParam = 'search';

		for (var i = 0; i < me.filters.length; i++)
		{
			Ext.applyIf(me.filters[i], {operator: 'like', type:"string"});
		}
		me.callParent();
	}
	,triggers	: 
	{
		clear: 
		{
			weight	: 0
			,cls	: Ext.baseCSSPrefix + 'form-clear-trigger'
			,hidden	: true
			,handler: function ()
			{
				this.reset();
				//this.clearContent();
			}
		},
		search: 
		{
			weight	: 1
			,cls	: Ext.baseCSSPrefix + 'form-search-trigger'
			,handler: function ()
			{
				this.searchContent();
			}
		}
	}
	,onChange	: function()
	{
		//this.setHideTrigger(!this.getValue());
		var clearTrigger = this.getTrigger('clear');
		clearTrigger[this.getValue() ? 'show' : 'hide'].apply(clearTrigger);
		if (!this.getValue())	this.clearContent();
		this.callParent();
	}
	,searchContent	:	function()
	{
		var me = this, searchText, grid, filters;

		searchText = me.getValue();
		grid = me.up('grid');
		filters = me.filters;
		if (searchText)
		{
			if (filters.length)
			{
				for (var i = 0; i < filters.length; i++)
				{
					filters[i].value = (filters[i].type == 'numeric' || filters[i].type == 'number') ? parseInt(searchText) : searchText;
				}

				grid.store.getProxy().setExtraParam(me.filterParam, Ext.encode(filters));
			}
			else
			{
				/**
				 * This line to be deleted
				 */
				grid.store.getProxy().setExtraParam(me.searchParam, searchText);
			}
			grid.store.load();
		}
		else
		{
			me.clearContent();
		}
	}
	,clearContent	: 	function()
	{
		var me = this, extraParams, grid;

		grid = me.up('grid');
		extraParams = grid.store.getProxy().getExtraParams();
		if (extraParams.hasOwnProperty(me.filterParam))  delete extraParams[me.filterParam];
		/**
		 * This line to be deleted
		 */
		if (extraParams.hasOwnProperty(me.searchParam))  delete extraParams[me.searchParam];
		grid.store.load();
	}
	,listeners	:
	{
		specialkey: function(field, e)
		{
			if (e.getKey() == e.ENTER) 
			{
				this.searchContent();
			}
		}
	}
});

/**
 * @author rabindranath.s
 */
Ext.define('Ext.button.MenuButton',
{
	alias: 'widget.menubutton'
	,extend: 'Ext.button.Button'
	,requires: ['Ext.button.Button']
	,renderTpl: 
		'<span id="{id}-btnWrap" data-ref="btnWrap" role="presentation" unselectable="on" style="{btnWrapStyle}" class="{btnWrapCls} {btnWrapCls}-{ui} {splitCls}{childElCls}">' +
			'<span id="{id}-btnEl" data-ref="btnEl" role="presentation" unselectable="on" style="{btnElStyle}" class="{btnCls} {btnCls}-{ui} {textCls} {noTextCls} {hasIconCls} {iconAlignCls} {textAlignCls} {btnElAutoHeightCls}{childElCls}">' +
				'<tpl if="iconBeforeText">{[values.$comp.renderIcon(values)]}</tpl>' +
				'<span id="{id}-btnInnerEl" data-ref="btnInnerEl" unselectable="on" class="{innerCls} {innerCls}-{ui}{childElCls}">{text}</span>' +
				'<tpl if="!iconBeforeText">{[values.$comp.renderIcon(values)]}</tpl>' +
			'</span>' +
			'<span id="{id}-btnNotificationEl" data-ref="btnNotificationEl" class="{btnCls} {btnCls}-{ui} {textAlignCls} {btnElAutoHeightCls} <tpl if="notification">{notificationCls}</tpl>">' +
				'<tpl if="notification">{notification}</tpl>' +
			'</span>' +
		'</span>' +
		'{[values.$comp.getAfterMarkup ? values.$comp.getAfterMarkup(values) : ""]}' +
		// if "closable" (tab) add a close element icon
		'<tpl if="closable">' +
			'<span id="{id}-closeEl" data-ref="closeEl" class="{baseCls}-close-btn">' +
				'<tpl if="closeText">{closeText}</tpl>' +
			'</span>' +
		'</tpl>'
	,getTemplateArgs: function()
	{
		return Ext.apply(this.callParent(), {notification: this.notification, notificationCls: this.notificationCls});
	}
	,childEls: ['btnEl', 'btnWrap', 'btnInnerEl', 'btnIconEl', 'btnNotificationEl']
	,setNotification: function(v, notify)
	{
		this.btnNotificationEl.setHtml(v || '');
		if (v)
		{
			if (notify)	
			{
				this.btnNotificationEl.addCls(this.notificationCls);
			}
			else 
			{
				this.btnNotificationEl.addCls('notificationInactiveCls');
			}
		}
		else
		{
			this.btnNotificationEl.removeCls(this.notificationCls);
		}
		this.up('toolbar').updateLayout({isRoot: true});
	}
	,getNotification: function(v)
	{
		var value = this.btnNotificationEl.getHtml();
		var trimedValue =  value.trim();

		return (trimedValue ? parseInt(trimedValue) : 0);
	}
});



/**
 * @author rabindranath.s
 */
Ext.define('Ext.form.field.RowEditorTime',
{
	extend		: 'Ext.form.field.Time'
	,requires	: 'Ext.form.field.Time'
	,alias		: 'widget.roweditortimefield'
	,alternateClassName: ['Ext.form.RowEditorTimeField', 'Ext.form.RowEditorTime']
	,getValue	: function()
	{
		/**
		 * Temporal solution
		 * It by passes some validation
		 * Need some improvement
		 */
		var me = this,
		date = me.rawToValue(me.processRawValue(me.getRawValue()));
		if(date != '' && date != null)
		return Ext.Date.format(date, me.format);
		return null;
	}
	/*,getValue	: function()
	{
		console.log(this.self.getName());
		console.log(Ext.getClass(this).superclass.self.getName());
		return this.rawToValue(this.callParent(arguments));
	}*/
});

/**
 * @author rabindranath.s
 * Filter Combo Field is meant for to put in grid tool bar for applying custom filter
 * @cfg {object} filter e.g {operator:"like", type:"string", property:"name"}
 * operator and type are optional and default to "eq" and "string" respectively
 */
/**
 * Note: Take care not to match any filter property config with any column dataIndex
 */
Ext.define('Ext.field.FilterComboBox',
{
	extend		: 'Ext.custom.form.field.ComboBox'
	,alternateClassName: ['Ext.field.FilterCombo']
	,xtype		: ['filtercombo', 'filtercombobox']
	,fieldLabel	: 'Filter By'
	,labelWidth	: 60
	,labelStyle	: 'font-weight: bold;'
	,width		: 400
	,filter		: null
	,triggers	:
	{
		clear: 
		{
			weight	: -1
			,cls	: Ext.baseCSSPrefix + 'form-clear-trigger'
			,hidden	: true
			,handler: function ()
			{
				this.reset();
			}
		}
	}
	,initComponent: function()
	{
		var fieldName = this.getMappingByDataIndex(this.up('grid').store, this.filter.property);
		this.filterConfig = {operator: 'eq', type: 'string', property: 'id'};
		Ext.apply(this.filterConfig, this.filter, {id: this.filter.property, fieldName: fieldName});
		this.callParent(arguments);
	}
	,onChange	: function(newVal, oldVal)
	{
		var clearTrigger = this.getTrigger('clear');
		clearTrigger[newVal ? 'show' : 'hide'].apply(clearTrigger);
		this.callParent(arguments);
		this.filterByStoreFilter(this.up('grid').store, Ext.apply(this.filterConfig, {value: newVal ? newVal + '' : newVal}));
	}
	,filterByStoreFilter: function(store, filterConfig)
	{
		var me = this;
		/**
		 * Removes all filters from the store
		 */
		//store.clearFilter(true/*suppressEvent*/);

		/**
		 * Use this method to apply filter via store
		 */
		if (filterConfig.value)
		{
			store.addFilter(filterConfig);
		}
		else
		{
			store.removeFilter(filterConfig.id);
		}
		//console.log(store.getFilters());
	}
	,getMappingByDataIndex: function(store, dataIndex)
	{
		var model = store.getModel();
		var field = model && model.getField(dataIndex);
		return (field && (field.fieldName || field.mapping )) || dataIndex;
	}
	
});

/**
 * @author rabindranath.s
 */
Ext.define('Ext.grid.column.Action.Print',
{
	extend	: 'Ext.grid.column.Action'
	,alias	: ['widget.printactioncolumn']
	,text	: 'Print'
	,width	: 50
	,align	: 'center'
	,url	: ''
	,tooltip: 'Print'
	,handler: function(view, rowIndex, colIndex, obj, e, rec, rowNode)
	{
		view.getSelectionModel().select(rec);
		window.open(this.url + rec.get('id'), "_blank");
	}
	,getClass	: function(value, metaData, r)
	{
		return (r.get('jurisdiction')) ? 'x-print-action-icon' : 'x-print-no-action-icon';
	}
	,initComponent: function()
	{
		var me = this;
		me.callParent(arguments);
	}
});


/**
 *  Custom Search Field
 */

Ext.define('Ext.custom.form.searchfield',
{
    extend		: 'Ext.form.field.Text'
    ,requires	: 'Ext.form.field.Text'
    ,xtype		: 'custom-form-search-field'
	,height		: 25
	,enableKeyEvents:true
	,width		: 200
	,searched 	: false
   	,triggers	: 
    {
        clear: 
        {
            weight	: 0
            ,cls	: Ext.baseCSSPrefix + 'form-clear-trigger'
            ,hidden	: true
            ,handler: function ()
            {
            	this.clearContent();
            }
        },
        search: 
        {
            weight	: 1
            ,cls	: Ext.baseCSSPrefix + 'form-search-trigger'
            ,handler: function ()
            {
            	this.searchContent();
            }
        }
    }
    ,searchContent	:	function()
	{
		var me = this;
		me.searched = true;
		var searchText = me.getValue();
   		me.store.getProxy().setExtraParam('extraParams',searchText);
   		me.store.load();
   		if(searchText.length > 0)
   		{
   			me.getTrigger('clear').show();
   		}
	}
    ,clearContent	: 	function()
    {
    	var me = this;
    	if(me.searched)
    	{
    		me.setValue(null);
    		me.store.getProxy().setExtraParam('extraParams',null);
    		me.store.load();
    		me.searched = false;
    	}
    	else
    	{
    		me.setValue(null);
    	}
    	me.getTrigger('clear').hide();
    }
 	,listeners	:
 	{
 		specialkey: function(field, e)
 		{
 			if (e.getKey() == e.ENTER) 
 			{
 				this.searchContent();
 			}
 		}
 		,keypress	:	function( obj, eOpts )
 		{
 			this.getTrigger('clear').show();
 		}
 	}
    
});
