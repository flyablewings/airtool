package com.events
{
	import flash.events.Event;

	public class CloseViewEvent extends Event
	{
		public static const closeView:String = "close";
		
		public function CloseViewEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		public override function clone():Event{
			return new CloseViewEvent(this.type, this.bubbles, this.cancelable);
		}
	}
}