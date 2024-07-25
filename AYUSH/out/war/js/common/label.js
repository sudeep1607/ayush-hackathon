/**
 * @author rabindranath.s
 */
function Label(opt_options)
{
	//Initialization
	this.setValues(opt_options);

	//Label specific
	var span = this.span_label = document.createElement('span');
	span.style.cssText = 'position: relative; left: -50%; top: -8px; white-space: nowrap; border: 1px solid blue; padding: 2px; background-color: white';

	var div = this.div_label = document.createElement('div');
	div.appendChild(span);
	div.style.cssText = 'position: absolute; display: none';
};
Label.prototype = new google.maps.OverlayView;

Label.prototype.onAdd = function()
{
	 var pane = this.getPanes().overlayLayer;
	 pane.appendChild(this.div_label);

	//Ensures the label is redrawn if the text or position is changed.
	var me = this;
	this.listeners_label = [
		google.maps.event.addListener(this, 'position_changed', function() { me.draw(); }),
		google.maps.event.addListener(this, 'content_changed', function() { me.draw(); })
	];
};

Label.prototype.onRemove = function()
{
	this.div_label.parentNode.removeChild(this.div_label);
	//Label is removed from the map, stop updating its position/text.
	for (var i = 0, I = this.listeners_label.length; i < I; ++i)
	{
		google.maps.event.removeListener(this.listeners_label[i]);
	}
};

Label.prototype.draw = function()
{
	var projection = this.getProjection();
	var position = projection.fromLatLngToDivPixel(this.get('position'));

	var div = this.div_label;
	div.style.left = position.x + 'px';
	div.style.top = position.y + 'px';
	div.style.display = 'block';
	this.span_label.innerHTML = this.get('content');
};