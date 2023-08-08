package com.events
{
	import com.controls.EntityPanel;	
	import flash.events.Event;

	public class EntityEvent extends Event
	{
		public static var ERROR:String = "error";
		public static var CHANGEENTITY:String = "changeEntity";
		public static var CLICKENTITY:String = "clickEntity";
		public var errorMsg:String;
		public var entity:EntityPanel;
		public function EntityEvent(type:String, entity:EntityPanel, cancelable:Boolean=false,errorMsg:String="")
		{
			super(type, false, cancelable);
			this.entity = entity;
			this.errorMsg = errorMsg;
		}		
	}
}