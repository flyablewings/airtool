package com.events
{
	import flash.events.Event;

	public class OpenSaveDialogEvent extends Event
	{
		public static const openDialog:String="openDialog";
		public var _data:Object;
		
		public function OpenSaveDialogEvent(type:String, data:Object=null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			this._data = data;
		}
		
		public override function clone():Event{
			return new OpenSaveDialogEvent(this.type, this._data, this.bubbles, this.cancelable);
		}
	}
}