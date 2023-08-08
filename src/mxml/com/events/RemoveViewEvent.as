package com.events
{
	import flash.events.Event;

	public class RemoveViewEvent extends Event
	{
		public static const removeView:String="removeView";
		
		public function RemoveViewEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
				
		public override function clone():Event{
			return new RemoveViewEvent(this.type, this.bubbles, this.cancelable);
		} 
	}
}