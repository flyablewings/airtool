package com.events
{
	import flash.events.Event;

	public class SaveSuccessEvent extends Event
	{
		public static const saveSuccess:String = "saveSuccess";
		public var _viewName:String;
		
		public function SaveSuccessEvent(type:String, viewName:String="", bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			this._viewName = viewName;
		}
		
		public override function clone():Event{
			return new SaveSuccessEvent(this.type, this._viewName, this.bubbles, this.cancelable);
		}
	}
}