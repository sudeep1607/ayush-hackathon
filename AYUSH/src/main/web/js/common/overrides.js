/**
 * @author Rabindranath.s
 * For Extjs Version 5.1
 * Tested Version 5.1.0.107
 */

/*Ext.override(Ext.form.field.Base, {
    checkChangeEvents: Ext.isIE && (!document.documentMode || document.documentMode <= 11) 
                    ['change', 'propertychange', 'keyup'] :
                    ['change', 'input', 'textInput', 'keyup', 'dragdrop'],
});*/

/**
 * Overridden to render mandatory field mark
 */
Ext.override(Ext.form.field.Base,
{
	getFieldLabel: function()
	{
		var label = this.callParent(arguments);
		return label = (label && this.allowBlank === false) ? label + AMS.mandatoryIndicator : label;;
	}
});

Ext.override(Ext.form.FieldContainer,
{
	/**
	 * Overridden to render mandatory field mark
	 * set {@link #fieldLabel}. Otherwise returns the fieldLabel like normal. You can also override
	 * this method to provide a custom generated label.
	 * @return {String} The label, or empty string if none.
	 */
	getFieldLabel: function()
	{
		var label = this.fieldLabel || '';
		if (!label && this.combineLabels) {
			label = Ext.Array.map(this.query('[isFieldLabelable]'), function(field) {
				return field.getFieldLabel();
			}).join(this.labelConnector);
		}

		return label = (label && this.allowBlank === false) ? label + AMS.mandatoryIndicator : label;;
	}
});

/**
 * @author rabindranath.s
 * @class Ext.form.field.Hidden
 * @overrides Ext.form.field.Hidden
 * Overridden not to submit the hidden fields (xtype: 'hidden') value if it contains empty string
 */
Ext.override(Ext.form.field.Hidden,
{
	getSubmitValue: function()
	{
		var value = this.callParent(arguments);
		return value == "" ? null : value;
    }
});

/**
 * @author rabindranath.s
 * @class Ext.tab.Tab
 * @overrides Ext.tab.Tab
 * overridden to handle icon alignment
 */
Ext.override(Ext.tab.Tab,
{
	setCard: function(card)
	{
		var me = this;

		me.card = card;
		if (card.textAlign) {
			me.setTextAlign(card.textAlign);
		}
		me.setText(me.title || card.title);
		me.setIconCls(me.iconCls || card.iconCls);
		me.setIcon(me.icon || card.icon);
		me.setGlyph(me.glyph || card.glyph);
	}
	,setIconValue: function(v)
	{
		this.btnIconEl.setHtml(v || ' ');
	}
	,getIconValue: function(v)
	{
		var value = this.btnIconEl.getHtml();
		var trimedValue =  value.trim();

		return (trimedValue ? parseInt(trimedValue) : 0);
	}
});

/**
 * @author rabindranath.s
 * @class Ext.grid.filters.filter.Base
 * @overrides Ext.grid.filters.filter.Base
 * overridden to pass fieldName
 * Provide the fieldName config in {@link Ext.data.Field} in case the server side mapping is different from mapping or name
 */
Ext.override(Ext.grid.filters.filter.Base,
{
	createFilter: function (config, key)
	{
		var filterConfig = this.getFilterConfig(config, key);

		if (this.getGridStore().getRemoteFilter())
		{
			var model = this.getGridStore().getModel();
			var field = model && model.getField(this.dataIndex);
			filterConfig.fieldName = (field && (field.fieldName || field.mapping)) || this.dataIndex;
		}

		return new Ext.util.Filter(filterConfig);
	}
	,getActiveState: function (config, value)
	{
		// An `active` config must take precedence over a `value` config.
		var active = config.active;

		return (active != undefined) ? active : value !== undefined;
	}
});

/**
 * @author rabindranath.s
 * @class Ext.grid.filters.filter.SingleFilter
 * @overrides Ext.grid.filters.filter.SingleFilter
 * This is the code for overriding constructor method
 * overridden to pass data type: "type"
 */
Ext.override(Ext.grid.filters.filter.SingleFilter,
{
	constructor: function (config)
	{
		var me = this, filter, value;
		/**
		 * callSuper to bypass the overridden method
		 */
		me.callSuper(arguments);
		value = me.value;
		filter = me.getStoreFilter();
		if (filter)
		{
		    me.active = true;
		}
		else
		{
			if (me.grid.stateful && me.getGridStore().saveStatefulFilters) {
				value = undefined;
			}

			me.active = me.getActiveState(config, value);
			filter = me.createFilter({
				operator: me.operator,
				value: value,
				type : config.type
			});

			if (me.active) {
				me.addStoreFilter(filter);
			}
		}

		if (me.active) {
		    me.setColumnActive(true);
		}

		me.filter = filter;
	}
});

/**
 * @author rabindranath.s
 * @class Ext.grid.filters.filter.TriFilter
 * @overrides Ext.grid.filters.filter.TriFilter
 * This is the code for overriding constructor method
 * overridden to pass two extra params: "type" and "dateFormat" in case date type filter
 * Now it supports two more type filter i.e. "less than equal" and "greater than equal"
 * Greater Than Equal and Less Than Equal Support Added and not tested in case of local filter
 */
Ext.override(Ext.grid.filters.filter.TriFilter,
{
	constructor: function (config)
	{
		var me = this, stateful = false, filter = { }, filterGt, filterLt, filterEq, value, operator,
			dateFormat, filterLe, filterGe;
		/**
		 * callSuper to bypass the overridden method
		 */
		me.callSuper([config]);
		value = me.value;
		filterLt = me.getStoreFilter('lt');
		filterGt = me.getStoreFilter('gt');
		filterEq = me.getStoreFilter('eq');
		//Added
		filterLe = me.getStoreFilter('le');
		filterGe = me.getStoreFilter('ge');
		//

		if (filterLt || filterGt || filterEq || filterLe || filterGe)
		{
			/**
			 * This filter was restored from stateful filters on the store so enforce it as active.
			 */
			stateful = me.active = true;
			if (filterLt) {me.onStateRestore(filterLt);}
			if (filterGt) {me.onStateRestore(filterGt);}
			if (filterEq) {me.onStateRestore(filterEq);}
			if (filterLe) {me.onStateRestore(filterLe);}
			if (filterGe) {me.onStateRestore(filterGe);}
		}
		else
		{
			if (me.grid.stateful && me.getGridStore().saveStatefulFilters)
			{
				value = undefined;
			}

			me.active = me.getActiveState(config, value);
		}

		if (config.type == 'date')
		{
			dateFormat = (config.dateFormat || Ext.Date.defaultFormat);
			dateFormat = me.convertExtToJavaFormat(dateFormat);
		}

		filter.lt = filterLt || me.createFilter({
			operator: 'lt',
			value: (!stateful && value && value.lt) || null,
			type: config.type,
			dateFormat: dateFormat
		}, 'lt');

		filter.gt = filterGt || me.createFilter({
			operator: 'gt',
			value: (!stateful && value && value.gt) || null,
			type: config.type,
			dateFormat: dateFormat
		}, 'gt');

		filter.eq = filterEq || me.createFilter({
			operator: 'eq',
			value: (!stateful && value && value.eq) || null,
			type: config.type,
			dateFormat: dateFormat
		}, 'eq');

		//Added
		filter.le = filterLe || me.createFilter({
			operator: 'le',
			value: (!stateful && value && value.le) || null,
			type: config.type,
			dateFormat: dateFormat
		}, 'le');

		filter.ge = filterGe || me.createFilter({
			operator: 'ge',
			value: (!stateful && value && value.ge) || null,
			type: config.type,
			dateFormat: dateFormat
		}, 'ge');
		//

		me.filter = filter;
		if (me.active)
		{
			me.setColumnActive(true);
			if (!stateful) {for (operator in value) {me.addStoreFilter(me.filter[operator]);}}
			// TODO: maybe call this.activate?
		}
	}
	/**
	 * Convert Extjs date literal to Java date literal
	 */
	,convertExtToJavaFormat: function(format)
	{
		var code = [], special = false, ch = '', i;

		for (i = 0; i < format.length; ++i)
		{
			ch = format.charAt(i);
			if (!special && ch == "\\")
			{
				special = true;
			}
			else if (special)
			{
				special = false;
				code.push(Ext.String.escape(ch));
			}
			else
			{
				code.push(this.getJavaFormatCode(ch));
			}
		}

		return (code.join(''));
	}
	,javaFormatCode :
	{
		d: "dd",
		m: "MM",
		Y: "yyyy"
	}
	,getJavaFormatCode : function(character)
	{
		var f = this.javaFormatCode[character];
		// note: unknown characters are treated as literals
		return f || (Ext.String.escape(character));
	}
	,setValue: function (value)
	{
		var me = this,
			fields = me.fields,
			filters = me.filter,
			add = [],
			remove = [],
			active = false,
			filterCollection = me.getGridStore().getFilters(),
			field, filter, v, i, len;

		if (me.preventFilterRemoval) {
			return;
		}

		me.preventFilterRemoval = true;

		if ('eq' in value)
		{
			v = filters.lt.getValue();
			if (v || v === 0) {
				remove.push(fields.lt);
			}

			v = filters.gt.getValue();
			if (v || v === 0) {
				remove.push(fields.gt);
			}

			//Added
			v = filters.le.getValue();
			if (v || v === 0) {
				remove.push(fields.le);
			}

			v = filters.ge.getValue();
			if (v || v === 0) {
				remove.push(fields.ge);
			}
			//

			v = value.eq;
			if (v || v === 0)
			{
				add.push(fields.eq);
				filters.eq.setValue(v);
			}
			else
			{
				remove.push(fields.eq);
			}
		}
		else if ('lt' in value || 'gt' in value)
		{
			v = filters.eq.getValue();
			if (v || v === 0) {
				remove.push(fields.eq);
			}

			//Added
			v = filters.le.getValue();
			if (v || v === 0) {
				remove.push(fields.le);
			}

			v = filters.ge.getValue();
			if (v || v === 0) {
				remove.push(fields.ge);
			}
			//

			if ('lt' in value)
			{
				v = value.lt;
				if (v || v === 0)
				{
					add.push(fields.lt);
					filters.lt.setValue(v);
				}
				else
				{
					remove.push(fields.lt);
				}
			}

			if ('gt' in value)
			{
				v = value.gt;
				if (v || v === 0)
				{
					add.push(fields.gt);
					filters.gt.setValue(v);
				}
				else
				{
					remove.push(fields.gt);
				}
			}
		}
		else if ('le' in value || 'ge' in value)
		{
			//Added
			v = filters.eq.getValue();
			if (v || v === 0) {
				remove.push(fields.eq);
			}

			v = filters.lt.getValue();
			if (v || v === 0) {
				remove.push(fields.lt);
			}

			v = filters.gt.getValue();
			if (v || v === 0) {
				remove.push(fields.gt);
			}

			if ('le' in value)
			{
				v = value.le;
				if (v || v === 0)
				{
					add.push(fields.le);
					filters.le.setValue(v);
				}
				else
				{
					remove.push(fields.le);
				}
			}

			if ('ge' in value)
			{
				v = value.ge;
				if (v || v === 0)
				{
					add.push(fields.ge);
					filters.ge.setValue(v);
				}
				else
				{
					remove.push(fields.ge);
				}
			}
			//
		}

		if (remove.length || add.length)
		{
			filterCollection.beginUpdate();

			if (remove.length)
			{
				for (i = 0, len = remove.length; i < len; i++)
				{
					field = remove[i];
					filter = field.filter;

					field.setValue(null);
					filter.setValue(null);
					me.removeStoreFilter(filter);
				}
			}

			if (add.length)
			{
				for (i = 0, len = add.length; i < len; i++)
				{
					me.addStoreFilter(add[i].filter);
				}
			
				active = true;
			}

			filterCollection.endUpdate();
		}

		if (!active && filterCollection.length) { active = me.hasActiveFilter(); }
		if (!active || !me.active) { me.setActive(active); }
		me.preventFilterRemoval = false;
	}
});

/**
 * @author rabindranath.s
 * @class Ext.grid.filters.filter.Date
 * @overrides Ext.grid.filters.filter.Date
 * overridden to handle remote filtering
 */
Ext.override(Ext.grid.filters.filter.Date,
{
	config:
	{
		/**
		 * @cfg {Object} [fields]
		 * Configures field items individually. These properties override those defined
		 * by `{@link #itemDefaults}`.
		 *
		 * Example usage:
		 *      fields: {
		 *          gt: { // override fieldCfg options
		 *              width: 200
		 *          }
		 *      },
		 */
		fields: {
			lt: {text: 'Before'},
			gt: {text: 'After'},
			eq: {text: 'On'},
			le: {text: 'On Or Before'},
			ge: {text: 'On Or After'}
		}
	
		/**
		 * @cfg {Object} pickerDefaults
		 * Configuration options for the date picker associated with each field.
		 */
		,pickerDefaults: {
			xtype: 'datepicker',
			border: 0
		}
		,updateBuffer: 0
		,dateFormat: undefined
	}
	,onMenuSelect: function (picker, date)
	{
		var fields = this.fields,
			field = fields[picker.itemId],
			gt = fields.gt,
			lt = fields.lt,
			eq = fields.eq,
			le = fields.le,
			ge = fields.ge
			v = {};

		field.up('menuitem').setChecked(true, /*suppressEvents*/ true);

		//
		if (field === eq || field === lt || field === gt)
		{
			if (le)	le.up('menuitem').setChecked(false, true);
			if (ge)	ge.up('menuitem').setChecked(false, true);
		}
		if (field === eq || field === le || field === ge)
		{
			if (lt)	lt.up('menuitem').setChecked(false, true);
			if (gt)	gt.up('menuitem').setChecked(false, true);
		}
		//

		if (field === lt || field === gt)
		{
			eq.up('menuitem').setChecked(false, true);
			if (field === gt && (+lt.value < +date)) {
				lt.up('menuitem').setChecked(false, true);
				v.lt = null;
			} else if (field === lt && (+gt.value > +date)) {
			 	gt.up('menuitem').setChecked(false, true);
			 	v.gt = null;
			}
		}
		else if (field === le || field === ge)
		{
			eq.up('menuitem').setChecked(false, true);
			if (field === ge && (+le.value < +date)) {
				le.up('menuitem').setChecked(false, true);
				v.le = null;
			} else if (field === le && (+ge.value > +date)) {
				ge.up('menuitem').setChecked(false, true);
				v.ge = null;
			}
		}

		v[field.filterKey] = date;
		this.setValue(v);

		picker.up('menu').hide();
	}
});

/**
 * @author rabindranath.s
 * @class Ext.grid.column.Column
 * @overrides Ext.grid.column.Column
 * overridden to handle remote sorting
 * returns sorting param
 */
Ext.override(Ext.grid.column.Column,
{
	requires	: 'Ext.grid.column.Column'
	,getSortParam: function()
	{
		var grid = this.up('tablepanel');
		if (grid.store.getRemoteSort())
		{
			var model = grid.store.getModel();
			var field = model && model.getField(this.dataIndex);
			return (field && (field.fieldName || field.mapping )) || this.dataIndex;
		}
		else
		{
			return this.dataIndex;
		}
	}
});

/**
 * Manual Methods to check whether ellipsis is applied to the element
 */
Ext.override(Ext.dom.Element,
{
	clone: function ()
	{
		var el = Ext.clone(this.dom);
		el.id = el.id + "-clone";
		return new Ext.dom.Element(el, true);
	}
	,isEllipsis: function ()
	{
		var clone = this.clone();
		clone.setStyle({ display: 'inline', width: 'auto', visibility: 'hidden', position: 'absolute', top: 0, left: 0, overflow: 'auto', textOverflow: 'unset', maxWidth: 'none' });
		clone.appendTo(this.parent());
		var ellipsis = clone.getWidth() > this.getWidth();
		clone.remove();
		return ellipsis;
	}
});

/**
 * @author rabindranath.s
 * @class Ext.tab.Tab
 * @overrides Ext.tab.Tab
 * overridden to add tool tip only when ellipsis is applied to the element
 */
Ext.override(Ext.tab.Tab,
{
	setTooltip: function(tooltip, initial)
	{
		var me = this;
		if (me.rendered)
		{
			if (!initial || !tooltip) {
				me.clearTip();
			}
		
			if (tooltip && me.btnInnerEl.isEllipsis())
			{
				if (Ext.quickTipsActive && Ext.isObject(tooltip))
				{
					Ext.tip.QuickTipManager.register(Ext.apply({target: me.el.id}, tooltip));
					me.tooltip = tooltip;
				}
				else
				{
					me.el.dom.setAttribute(me.getTipAttr(), tooltip);
				}
			}
		}
		else
		{
			me.tooltip = tooltip;
		}
		return me;
	}
});

/**
 * @author rabindranath.s
 * @class Ext.form.field.Date
 * @overrides Ext.form.field.Date
 * Purpose: To show java formatted date error message
 */
Ext.override(Ext.form.field.Date,
{
	getErrors: function(value)
	{
		value = arguments.length > 0 ? value : this.formatDate(this.processRawValue(this.getRawValue()));

		var me = this,
			format = Ext.String.format,
			clearTime = Ext.Date.clearTime,
			errors = me.callSuper([value]),
			disabledDays = me.disabledDays,
			disabledDatesRE = me.disabledDatesRE,
			minValue = me.minValue,
			maxValue = me.maxValue,
			len = disabledDays ? disabledDays.length : 0,
			i = 0,
			svalue,
			fvalue,
			day,
			time;

		if (value === null || value.length < 1) { // if it's blank and textfield didn't flag it then it's valid
			return errors;
		}

		svalue = value;
		value = me.parseDate(value);
		if (!value) {
			errors.push(format(me.invalidText, svalue, Ext.Date.unescapeFormat(me.convertExtToJavaFormat(me.format))));
			return errors;
		}

		time = value.getTime();
		if (minValue && time < clearTime(minValue).getTime()) {
			errors.push(format(me.minText, me.formatDate(minValue)));
		}

		if (maxValue && time > clearTime(maxValue).getTime()) {
			errors.push(format(me.maxText, me.formatDate(maxValue)));
		}

		if (disabledDays) {
			day = value.getDay();
			
			for(; i < len; i++) {
				if (day === disabledDays[i]) {
					errors.push(me.disabledDaysText);
					break;
				}
			}
		}

		fvalue = me.formatDate(value);
		if (disabledDatesRE && disabledDatesRE.test(fvalue)) {
			errors.push(format(me.disabledDatesText, fvalue));
		}

		return errors;
	}
	,convertExtToJavaFormat: function(format)
	{
		var code = [], special = false, ch = '', i;
	
		for (i = 0; i < format.length; ++i)
		{
			ch = format.charAt(i);
			if (!special && ch == "\\")
			{
			    special = true;
			}
			else if (special)
			{
				special = false;
				code.push(Ext.String.escape(ch));
			}
			else
			{
				code.push(this.getJavaFormatCode(ch));
			}
		}

		return (code.join(''));
	}
	,javaFormatCode :
	{
		d: "DD",
		m: "MM",
		Y: "YYYY"
	}
	,getJavaFormatCode : function(character)
	{
		var f = this.javaFormatCode[character];
		// note: unknown characters are treated as literals
		return f || (Ext.String.escape(character));
	}
});


/**
*	Combo Field
*/
Ext.override(Ext.form.field.ComboBox,
{
	/**
	 * This method exists on Extjs 5.1.0 and may be onwards
	 * It is overridden to pass record as an array instead of single record for previous version compatibility in select event
	 */
	onValueCollectionEndUpdate: function()
	{
		var me = this,
			store = me.store,
			selectedRecords = me.valueCollection.getRange(),
			selectedRecord = selectedRecords[0],
			selectionCount = selectedRecords.length;

		me.updateBindSelection(me.pickerSelectionModel, selectedRecords);

		if (me.isSelectionUpdating()) {return;}
		Ext.suspendLayouts();
		me.updateValue();

		// If we have selected a value, and it's not possible to select any more values
		// or, we are configured to hide the picker each time, then collapse the picker.
		if (selectionCount && ((!me.multiSelect && store.contains(selectedRecord)) || me.collapseOnSelect || !store.getCount())) {
			me.updatingValue = true;
			me.collapse();
			me.updatingValue = false;
		}
		Ext.resumeLayouts(true);
		if (selectionCount && !me.suspendCheckChange) {
			/*if (!me.multiSelect) {
				selectedRecords = selectedRecord;
			}*/
			me.fireEvent('select', me, selectedRecords);
		}
	}
});

/**
 * @author rabindranath.s
 * @class Ext.selection.CheckboxModel
 * @overrides Ext.selection.CheckboxModel
 * Purpose: Deselct Event doesn't fire issue resolved
 */
Ext.override(Ext.selection.CheckboxModel,
{
	privates:
	{
		selectWithEventMulti: function(record, e, isSelected)
		{
			var me = this;
		
			if (!e.shiftKey && !e.ctrlKey && e.getTarget(me.checkSelector))
			{
				if (isSelected) {
					me.doDeselect(record);	// Second parameter is for supress event
				} else {
					me.doSelect(record, true);
				}
			} else {
			    me.callSuper([record, e, isSelected]);
			}
		}
	}
});


Ext.define('DynamicSize',
{
	override: 'Ext.panel.Tool'
	,cacheWidth: false
	,cacheHeight: false
});

/**
 * @author rabindranath.s
 * @class Ext.tab.Panel
 * @overrides Ext.tab.Panel
 * Overridden to facilate not to remove the "Panel Header" of a panel inside a Tab Panel for required individual tab
 * In case it is required to keep all Panel's Header for the Tab Panel, then use "removePanelHeader: false" in Tab Panel Config
 * @cfg {Boolean} keepPanelHeader
 */
Ext.override(Ext.tab.Panel,
{
	onAdd: function(item, index)
	{
		var me = this,
			cfg = Ext.apply({}, item.tabConfig),
			tabBar = me.getTabBar(),
			defaultConfig = {
				xtype: 'tab',
				title: item.title,
				icon: item.icon,
				iconCls: item.iconCls,
				glyph: item.glyph,
				ui: tabBar.ui,
				card: item,
				disabled: item.disabled,
				closable: item.closable,
				hidden: item.hidden && !item.hiddenByLayout, // only hide if it wasn't hidden by the layout itself
				tooltip: item.tooltip,
				tabBar: tabBar,
				tabPosition: tabBar.dock,
				rotation: tabBar.getTabRotation()
			};

		if (item.closeText !== undefined) {
			defaultConfig.closeText = item.closeText;
		}
		
		cfg = Ext.applyIf(cfg, defaultConfig);
		
		// Create the correspondiong tab in the tab bar
		item.tab = me.tabBar.insert(index, cfg);

		item.on({
			scope : me,
			enable: me.onItemEnable,
			disable: me.onItemDisable,
			beforeshow: me.onItemBeforeShow,
			iconchange: me.onItemIconChange,
			iconclschange: me.onItemIconClsChange,
			glyphchange: me.onItemGlyphChange,
			titlechange: me.onItemTitleChange
		});

		if (item.isPanel)
		{
			if (me.removePanelHeader && !cfg.keepPanelHeader) {
				if (item.rendered) {
					if (item.header) {
						item.header.hide();
					}
				} else {
					item.header = false;
				}
			}
			if (item.isPanel && me.border) {
				item.setBorder(false);
			}
		}

		// Force the view model to be created, see onRender
		if (me.rendered) {
			item.getBind();
		}

		// Ensure that there is at least one active tab. This is only needed when adding tabs via a loader config, i.e., there
		// may be no pre-existing tabs. Note that we need to check if activeTab was explicitly set to `null` in the tabpanel
		// config (which tells the layout not to set an active item), as this is a valid value to mean 'do not set an active tab'.
		if (me.rendered && me.loader && me.activeTab === undefined && me.layout.activeItem !== null) {
			me.setActiveTab(0);
		}
	}
});

/**
 * @author rabindranath.s
 * @class Ext.selection.Model
 * @overrides Ext.selection.Model
 * Ovridden to fire the grid view selectionchange event when grid refresh and it has a selection
 * @cfg {Boolean} fireSelectionChangeOnStoreRefresh, pass true to fire
 */
Ext.override(Ext.selection.Model,
{
	updateSelectedInstances: function(selected)
	{
		this.callParent(arguments);
		if (/*this.hasSelection() && */this.fireSelectionChangeOnStoreRefresh)
		{
			this.fireEvent('selectionchange', this, this.getSelection());
		}
	}
});

/**
 * @author rabindranath.s
 * Bug Fixes
 * Date Object Comparision
 */
Ext.Date.isEqual = function(date1, date2) {
        // check we have 2 date objects
        if (Ext.isDate(date1) && Ext.isDate(date2)) {
            return (date1.getTime() === date2.getTime());
        }
        // one or both isn't a date, only equal if both are falsey
        return !(date1 || date2);
    }

/**
 * @author rabindranath.s
 */
/*Ext.override(Ext.grid.plugin.RowEditing,
{
	initEditorConfig: function()
	{
		return Ext.apply(this.callParent(), {trackResetOnLoad: true});
	}
});*/

/**
 * @author rabindranath.s
 * 
 */
Ext.override(Ext.grid.RowEditor,
{
	onViewScroll: function()
	{
		var me = this,
			viewEl = me.editingPlugin.view.el,
			scrollingView = me.scrollingView,
			scrollTop = scrollingView.getScrollY(),
			scrollLeft = scrollingView.getScrollX(),
			scrollTopChanged = scrollTop !== me.lastScrollTop,
			row;
		me.lastScrollTop = scrollTop;
		me.lastScrollLeft = scrollLeft;
		if (me.isVisible())
		{
			row = Ext.getDom(me.context.row);
			if (row && viewEl.contains(row))
			{
				if (scrollTopChanged)
				{
					me.context.row = row;
					me.reposition(null, true);
					if ((me.tooltip && me.tooltip.isVisible()) || me.hiddenTip) {
					    me.repositionTip();
					}
					me.syncEditorClip();
				}
			}
			else 
			{
				//me.setLocalY(-400);
			}
		}
	}
});

Ext.define('Ext.overrides.grid.column.Widget', {
    override: 'Ext.grid.column.Widget',
    
    privates: {
        // OVERRIDE : Bug in ExtJS 5.1.0 - EXTJS-16368
        getFreeWidget: function() {
            var me = this,
                result = me.freeWidgetStack ? me.freeWidgetStack.pop() : null;


            if (!result) {
                result = Ext.widget(me.widget);


                result.resolveListenerScope = me.listenerScopeFn;
                result.getWidgetRecord = me.widgetRecordDecorator;
                result.getWidgetColumn = me.widgetColumnDecorator;
                result.dataIndex = me.dataIndex;
                result.measurer = me;
                result.ownerCmp = me;
                
                // Override
                // For FIREFOX, attach a render listener to force focus on form fields
                if (Ext.isGecko && result.isXType('field')) {
                    result.on({
                        render : me.onWidgetRender
                    })
                }
            }
            return result;
        },
        
        // Override : New method to force focus on widget element when run in FIREFOX
        onWidgetRender : function(widget) {
            var element = widget.getEl();
            if (element) {
                element.on({
                   mouseup : function(event, htmlElement) {
                       if (htmlElement.focus) {
                            htmlElement.focus();
                       }
                   }
                });
            }
        }
    }
});
