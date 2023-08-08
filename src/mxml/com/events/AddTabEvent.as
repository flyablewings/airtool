package com.events
{
	import flash.events.Event;

	public class AddTabEvent extends Event
	{
		public static const addTab:String="addTab";
		
		public function AddTabEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		public override function clone():Event{
			return new AddTabEvent(this.type, this.bubbles, this.cancelable);
		}
	}
}