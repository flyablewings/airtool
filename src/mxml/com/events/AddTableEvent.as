package com.events
{
	import flash.events.Event;

	public class AddTableEvent extends Event
	{
		public static const addTable:String = "addTable";
		public function AddTableEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event{
			return new AddTableEvent(type);
		}
	}
}