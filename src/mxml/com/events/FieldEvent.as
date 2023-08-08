package com.events
{
	import flash.events.Event;

	public class FieldEvent extends Event
	{
		public static var DOUBLECLICKFIELDENTITY:String = "doubleClickFieldEntity";
		public var value:Object = null;
		public function FieldEvent(type:String, value:Object=null, cancelable:Boolean=false)
		{
			super(type, false, cancelable);
			this.value = value;
		}
		
		public override function clone():Event{
			return new FieldEvent(this.type, this.value, false);
		}
	}
}