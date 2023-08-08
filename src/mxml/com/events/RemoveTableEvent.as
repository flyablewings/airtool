package com.events
{
	import flash.events.Event;

	public class RemoveTableEvent extends Event
	{
		public static const removeTable:String = "removeTable";
		public function RemoveTableEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event{
			return new RemoveTableEvent(type);
		}
	}
}