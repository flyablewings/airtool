package com.events
{
	import flash.events.Event;

	public class RemoveColumnEvent extends Event
	{
		public static const remove:String = "removeColumn";
		public function RemoveColumnEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event{
			return new RemoveColumnEvent(type);
		}		
	}
}