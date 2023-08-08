package com.controls.relationClasses
{
	import com.managers.IRelationManager;
	import com.objects.EntityPoint;
	
	import mx.core.IUIComponent;
	/**
	 *Renderers used by an IRelationManager must implement this interface. 
	 * @author Administrator
	 * 
	 */
	public interface IRelationRenderer extends IUIComponent
	{
		/**
		 * A entitypoint to which this relation is connected.
		 */
		function get entitypoint1():EntityPoint;
		
		/**
		 * @private
		 */
		function set entitypoint1(value:EntityPoint):void;
		
		/**
		 * Another entitypoint to which this relation is connected.
		 */
		function get entitypoint2():EntityPoint;
		
		/**
		 * @private
		 */
		function set entitypoint2(value:EntityPoint):void;
		
		/**
		 * The manager that displays and controls this relation.
		 */
		function get relationManager():IRelationManager;
		
		/**
		 * @private
		 */
		function set relationManager(value:IRelationManager):void;
		
		/**
		 * Removes the relation, if it is connected to any entitypoints.
		 */
		function disconnect():void;
	}
}