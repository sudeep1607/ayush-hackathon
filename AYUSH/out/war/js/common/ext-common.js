/**
 * Common Store For Yes or No Values
 */
 
var yesNoStore = Ext.create('Ext.data.Store',
		{
			model		:	'ComboModel'
			,data		:
			[
				{	name	:	'Yes',	id	: true		}
				,{	name	:	'No',	id	: false		}
			]
		});

var activeInactiveStore = Ext.create('Ext.data.Store',
	{
		model		:	'ComboModel'
		,data		:
		[
			{	name	:	'Active'	, id	: true		}
			,{	name	:	'Inactive'	, id	: false		}
		]
	});

var languageStore = Ext.create('Ext.data.Store',
		{
			model		:	'ComboModel'
			,data		:
			[
				{	name	:	'English'	, id	: 'ENG'		}
				,{	name	:	'Telugu'	, id	: 'TEL'		}
			]
		});

var statusStore = Ext.create('Ext.data.ComboStore',
		{
			autoDestroy: false
			,data	: (function()
			{
				var data = [];
				for (key in COMMON_STATUS)
				{
					if (key != 1)	data.push({id: key, name: COMMON_STATUS[key]});
				}

				return data;
			})()
		});
