package flexlib.events
{
	import flash.events.Event;

	public class SuperTabEvent extends Event
	{
		public static const TAB_CLOSE:String = "tabClose";
		public static const TAB_UPDATED:String = "tabUpdated";
		public static const TAB_ADDED:String = "tabAdded";
		public static const TAB_REMOVED:String = "tabRemoved";
		public static const TAB_CHANGEDEVENT:String = "tabChangedEvent";
				
		public var tabOldIndex:Number;
		public var tabIndex:Number;
		public var tabTitle:String;
		public function SuperTabEvent(type:String, tabIndex:Number=-1, tabOldIndex:Number=-1, bubbles:Boolean=false, cancelable:Boolean=false, title:String="")
		{
			this.tabIndex = tabIndex;
			this.tabOldIndex = tabOldIndex;
			this.tabTitle = title;
			super(type, bubbles, cancelable);
		}
		
		override public function clone():Event {
			return new SuperTabEvent(type, tabIndex,tabOldIndex, bubbles, cancelable, tabTitle);
		}
		
	}
}