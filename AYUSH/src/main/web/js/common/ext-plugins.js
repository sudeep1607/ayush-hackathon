/**
 * @author rabindranath.s
 * applies tool-tip to all the columns that overflows or ellipsis is applied
 */
Ext.define('Ext.grid.plugin.GridColumnToolTip',
{
    extend	: 'Ext.plugin.Abstract',
    alias	: 'plugin.gridcolumntooltip',
    init	: function(grid)
    {
    	var me = this;
        grid.on('columnresize', function(ct, c, width)
		{
            me.setTooltip(c);
            if (c.applyCellTooltip)	grid.getView().refresh();
        });

        /*grid.on('beforerender', function()
		{
            Ext.Array.each(grid.columns, function(c)
    		{
            	c.initRenderData = me.createSequence(c.initRenderData, c);
    		});
        });*/

        grid.on('render', function()
		{
            Ext.Array.each(grid.columns, function(c)
    		{
            	c.on('render', function(col)
    			{
            		me.setTooltip(col);
				});

            	if (c.applyCellTooltip)
                {
            		/**
            		 * This config hampers the performance since the entire grid data is rerendered, so apply this any the specific column where it is essntial 
            		 */
                	if (c.renderer)
                    {
                        c.renderer = me.createRendererSequence(c.renderer);
                    }
                    else
                    {
                        c.renderer = me.renderer;
                    }
                }
    		});
        });
    }
	,setTooltip: function(c)
	{
		if ((!c.titleEl.getAttribute('data-qtip') || c.titleEl.getAttribute('manual-qtip')) && this.checkWidth(c))
        {
        	c.titleEl.dom.setAttribute('manual-qtip', true);
        	c.titleEl.dom.setAttribute('data-qtip', c.text);
        }
        else
        {
        	c.titleEl.dom.removeAttribute('data-qtip');
        }
	}
	,checkWidth: function(c)
	{
		var tm = new Ext.util.TextMetrics(c.textEl);
		if ((c.getEl().getWidth() || 10) <= ((tm.getSize(c.text).width + 20) || 0))
		{
			tm.destroy();
			return true;
		}

		tm.destroy();
		return false;
	}
	,createSequence: function(fn, c)
	{
		var me = this;
		return function()
        {
            var result = fn.apply(this, arguments);
            if (!result.tipMarkup && me.checkWidth(c))
            {
            	result.tipMarkup = 'data-qtip="' + (result.text) + '"';
            }
            return result;
        };
	}
	,renderer: function(a, b, c, d, e, f, g)
	{
		if(a)
        {
			var tm = new Ext.util.TextMetrics();
			/*console.log(g);
			console.log(c);
			console.log(g.ownerCt.columns[e]);
			console.log(d);
			var row = g.getRow(d);
			console.log(row);
			console.log(Ext.fly(row).down(column.getCellSelector()));

			console.log(g.getCell(c, g.ownerCt.columns[e]));*/
            if ((g.ownerCt.columns[e].getEl().getWidth() || 10) <= (((tm.getSize(a).width + 20) || 0)))
            {
                b.tdAttr += 'data-qtip="' + (a) + '"';
            }

            tm.destroy();
        }

		return a;
	}
	,createRendererSequence: function(fn)
	{
		var me = this;
        return function()
        {
            var value = fn.apply(this, arguments);
            //arguments[0] = value;
            me.renderer.apply(this, arguments);
            return value;
        };
    }
	,destroy: function()
	{
		
	}
});

/**
 * @author rabindranath.s
 * A small grid nested within a parent grid's row. 
 */
/*Ext.define('Ext.grid.plugin.SubTable',
{
    extend: 'Ext.grid.plugin.RowExpander'
    ,alias: 'plugin.subtable'
    ,rowBodyTpl:
	[
	 	'<table class="' + Ext.baseCSSPrefix + 'grid-subtable"><tbody>',
          '{%',
          'this.owner.renderTable(out, values);',
          '%}',
        '</tbody></table>'
    ]
    ,init: function(grid)
    {
        var me = this,
            columns = me.columns,
            len, i, columnCfg;

        me.callParent(arguments);

        me.columns = [];
        if (columns) {
            for (i = 0, len = columns.length; i < len; ++i) {
                // Don't register with the component manager, we create them to use
                // their rendering smarts, but don't want to treat them as real components
                columnCfg = Ext.apply({
                    preventRegister: true
                }, columns[i]);
                columnCfg.xtype = columnCfg.xtype || 'gridcolumn';
                me.columns.push(Ext.widget(columnCfg));
            }
        }
    }
    ,destroy: function()
    {
        var columns = this.columns,
            len, i;

        if (columns) {
            for (i = 0, len = columns.length; i < len; ++i) {
                columns[i].destroy();
            }
        }
        this.columns = null;
        this.callParent();
    }
    ,getRowBodyFeatureData: function(record, idx, rowValues)
    {
        this.callParent(arguments);
        rowValues.rowBodyCls += ' ' + Ext.baseCSSPrefix + 'grid-subtable-row';
    }
    ,renderTable: function(out, rowValues)
    {
        var me = this,
            columns = me.columns,
            numColumns = columns.length,
            associatedRecords = me.getAssociatedRecords(rowValues.record),
            recCount = associatedRecords.length,
            rec, column, i, j, value;

        out.push('<thead>');
        for (j = 0; j < numColumns; j++) {
            out.push('<th class="' + Ext.baseCSSPrefix + 'grid-subtable-header">', columns[j].text, '</th>');
        }
        out.push('</thead>');
        for (i = 0; i < recCount; i++) {
            rec = associatedRecords[i];
            out.push('<tr>');
            for (j = 0; j < numColumns; j++) {
                column = columns[j];
                value = rec.get(column.dataIndex);
                if (column.renderer && column.renderer.call) {
                    value = column.renderer.call(column.scope || me, value, {}, rec);
                }
                out.push('<td class="' + Ext.baseCSSPrefix + 'grid-subtable-cell"');
                if (column.width != null) {
                    out.push(' style="width:' + column.width + 'px"');
                }
                out.push('><div class="' + Ext.baseCSSPrefix + 'grid-cell-inner">', value, '</div></td>');
            }
            out.push('</tr>');
        }
    }
    ,getRowBodyContentsFn: function(rowBodyTpl)
    {
        var me = this;
        return function (rowValues) {
            rowBodyTpl.owner = me;
            return rowBodyTpl.applyTemplate(rowValues);
        };
    }
    ,getAssociatedRecords: function(record)
    {
        return record[this.association]().getRange();
    }
});*/
