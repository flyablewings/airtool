package com.managers
{
	import com.objects.EntityPoint;
	
	import flash.events.IEventDispatcher;
	
	//--------------------------------------
	//  Events
	//--------------------------------------
	
	/**
	 * Dispatched when a registered EntityPoint wants to make a connection with
	 * another EntityPoint. Generally, compatible EntityPoint instances are expected
	 * to highlight themselves so that the user knows where a connection can be
	 * made.
	 * 
	 * @eventType com.events.RelationManagerEvent.BEGIN_CONNECTION_REQUEST
	 */
	[Event(name="beginConnectionRequest",type="com.events.RelationManagerEvent")]
	
	/**
	 * Dispatched when a registered EntityPoint wants to end a connection request.
	 * Generally, compatible EntityPoint instances are expected to remove their
	 * highlight at this time.
	 * 
	 * @eventType com.events.RelationManagerEvent.END_CONNECTION_REQUEST
	 */
	[Event(name="endConnectionRequest",type="com.events.RelationManagerEvent")]
	
	/**
	 * Dispatched when a connection is made between two EntityPoint instances. May
	 * be cancelled.
	 * 
	 * @eventType com.events.RelationManagerEvent.CREATE_CONNECTION
	 */
	[Event(name="createConnection",type="com.events.RelationManagerEvent")]
	
	/**
	 * Dispatched when a connection is deleted between two EntityPoint instances.
	 * 
	 * @eventType com.events.RelationManagerEvent.DELETE_CONNECTION
	 */
	[Event(name="deleteConnection",type="com.events.RelationManagerEvent")]
	
	public interface IRelationManager extends IEventDispatcher
	{
	//--------------------------------------
	//  Methods
	//--------------------------------------
	
		/**
		 * Registers a entitypoint with this entity manager.
		 * 
		 * @param entitypoint		The entitypoint to register.
		 */
		function registerEntityPoint(entitypoint:EntityPoint):void;
		
		/**
		 * Removes a entitypoint's registration with this entity manager.
		 * 
		 * @param entitypoint		The entitypoint to remove.
		 */
		function deleteEntityPoint(entitypoint:EntityPoint):void;
		
		/**
		 * Called by a registered EntityPoint, this begins a connection request.
		 * Generally, if a entitypoint should be highlighted when the user is dragging
		 * a wire, the highlight is turned on during a connection request.
		 * 
		 * @param		The entitypoint that will be connected to another entitypoint.
		 */
		function beginConnectionRequest(startEntityPoint:EntityPoint):void;
		
		/**
		 * Called by a registered EntityPoint, this ends a connection request.
		 * Generally, if other entitypoints are highlighted during a drag-and-drop
		 * operation, the highlight is turned off when a connection request
		 * ends.
		 * 
		 * @param		The entitypoint that started the connection request.
		 */
		function endConnectionRequest(startEntityPoint:EntityPoint):void;
		
		/**
		 * Connects two entitypoints.
		 * 
		 * <p>Note: If one or both of the entitypoints are null, if one or both of the
		 * entitypoints aren't registered, or the entitypoints are equal, an ArgumentError will
		 * be thrown. Returns false only when the connection event is cancelled.</p>
		 * 
		 * @param startEntityPoint		The first entitypoint to connect.
		 * @param endEntityPoint		The second entitypoint to connect.
		 * @return				true if the entitypoints have been connected, false if not.
		 */
		function connect(startEntityPoint:EntityPoint, endEntityPoint:EntityPoint):Boolean;
		
		/**
		 * Disconnects two connected entitypoints.
		 * 
		 * <p>Note: If one or both of the entitypoints are null, if one or both of the
		 * entitypoints aren't registered, or the entitypoints aren't connected, an
		 * ArgumentError will be thrown.</p>
		 * 
		 * @param startEntityPoint		The first entitypoint to connect.
		 * @param endEntityPoint		The second entitypoint to connect.
		 */
		function disconnect(startEntityPoint:EntityPoint, endEntityPoint:EntityPoint):void;
	}
}