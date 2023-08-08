package com.events
{
	import com.objects.EntityPoint;
	
	import flash.events.Event;

	public class RelationManagerEvent extends Event
	{
		//--------------------------------------
	//  Static Properties
	//--------------------------------------
	
		/**
		 * The RelationManagerEvent.BEGIN_CONNECTION_REQUEST constant defines the
		 * value of the <code>type</code> property of a RelationManagerEvent object
		 * for a <code>beginConnectionRequest</code> event, which indicates that
		 * a EntityPoint is attempting to connect to other compatible EntityPoint instances.
		 */
		public static const BEGIN_CONNECTION_REQUEST:String = "beginConnectionRequest";
		
		/**
		 * The RelationManagerEvent.END_CONNNECTION_REQUEST constant defines the
		 * value of the <code>type</code> property of a RelationManagerEvent object
		 * for a <code>endConnectionRequest</code> event, which indicates that
		 * a EntityPoint is no longer attempting to connect to other compatible
		 * EntityPoint instances.
		 */
		public static const END_CONNNECTION_REQUEST:String = "endConnectionRequest";
		
		/**
		 * The RelationManagerEvent.CREATING_CONNECTION constant defines the
		 * value of the <code>type</code> property of a RelationManagerEvent object
		 * for a <code>creatingConnection</code> event, which indicates that
		 * two EntityPoint instances will soon be connected. This action may be
		 * cancelled.
		 */
		public static const CREATING_CONNECTION:String = "creatingConnection";
		
		/**
		 * The RelationManagerEvent.CREATE_CONNECTION constant defines the
		 * value of the <code>type</code> property of a RelationManagerEvent object
		 * for a <code>createConnection</code> event, which indicates that
		 * two EntityPoint instances have connected.
		 */
		public static const CREATE_CONNECTION:String = "createConnection";
		
		/**
		 * The RelationManagerEvent.DELETE_CONNECTION constant defines the
		 * value of the <code>type</code> property of a RelationManagerEvent object
		 * for a <code>deleteConnection</code> event, which indicates that
		 * two EntityPoint instances have been disconnected.
		 */
		public static const DELETE_CONNECTION:String = "deleteConnection";
		
	//--------------------------------------
	//  Constructor
	//--------------------------------------
		/**
		 *Constructor. 
		 * @param type			Type of event
		 * @param startPoint	A point associated with the event
		 * @param endPoint		An optional second point associated with the event
		 * @param cancelable	If true, event can be cancel
		 * 
		 */		
		public function RelationManagerEvent(type:String, startPoint:EntityPoint, endPoint:EntityPoint = null, cancelable:Boolean=false)
		{
			super(type, false, cancelable);
			this.startPoint = startPoint;
			this.endPoint = endPoint;
		}
		/**
		 * Start point of relationship
		 */
		public var startPoint:EntityPoint;
		/**
		 * End point of relationship 
		 */		
		public var endPoint:EntityPoint;
	}
}