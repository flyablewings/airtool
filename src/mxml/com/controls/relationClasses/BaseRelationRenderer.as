package com.controls.relationClasses
{
	import com.managers.IRelationManager;
	import com.objects.EntityPoint;
	import com.yahoo.astra.utils.DisplayObjectUtil;
	
	import flash.events.Event;
	import flash.geom.Point;
	import flash.ui.ContextMenu;
	
	import mx.core.UIComponent;
	
	public class BaseRelationRenderer extends UIComponent implements IRelationRenderer
	{
		protected var relContextMenu:ContextMenu;
	//--------------------------------------
	//  Constructor
	//--------------------------------------
		/**
		 *Contructor 
		 * 
		 */		
		public function BaseRelationRenderer()
		{
			//TODO: implement function
			super();
		}
	//--------------------------------------
	//  Properties
	//--------------------------------------

		/**
		 *Storage Entity point 1 
		 */		
		private var _entitypoint1:EntityPoint;
		
		public function get entitypoint1():EntityPoint
		{
			//TODO: implement function
			return this._entitypoint1;
		}
		
		public function set entitypoint1(value:EntityPoint):void
		{
			//TODO: implement function
			this._entitypoint1 = value;
			//this._entitypoint1.entity.addEventListener(ResizeEvent.RESIZE, onInitialize_entity);
		}
		
		/**
		 *Storage Entity point 2 
		 */		
		private var _entitypoint2:EntityPoint;
		
		public function get entitypoint2():EntityPoint
		{
			//TODO: implement function
			return this._entitypoint2;
		}
		
		public function set entitypoint2(value:EntityPoint):void
		{			
			this._entitypoint2 = value;
		}
		
		private var _relationManager:IRelationManager;
		
		public function get relationManager():IRelationManager
		{
			//TODO: implement function
			return this._relationManager;
		}
		
		public function set relationManager(value:IRelationManager):void
		{
			//TODO: implement function
			this._relationManager = value;
		}
	//--------------------------------------
	//  Public Methods
	//--------------------------------------
		public function disconnect():void
		{
			//TODO: implement function
			if(this.entitypoint1 || this.entitypoint2)
			{
				this.relationManager.disconnect(this.entitypoint1, this.entitypoint2);
			}
		}
		/**
		 * @private
		 * The last position of point1.
		 */
		private var _lastpoint1Position:Point;
		
		/**
		 * @private
		 * The last position of point2.
		 */
		private var _lastpoint2Position:Point;
		/**
		 * @private
		 * The last height of entity1 control
		 */
		private var _lastHeightOnEntity1:Number;
		/**
		 * @private
		 * The last width of entity1 control
		 */
		private var _lastWidthOnEntity1:Number;
		/**
		 * @private
		 * The last height of entity2 control
		 */
		private var _lastHeightOnEntity2:Number;
		/**
		 * @private
		 * The last width of entity2 control
		 */
		private var _lastWidthOnEntity2:Number;
		/**
		 * @private
		 * The last vertical scrollbar position of entity2 control
		 */
		private var _lastVerScrollPosition1:Number;
		/**
		 * @private
		 * The last vertical scrollbar position of entity2 control
		 */
		private var _lastVerScrollPosition2:Number;
		
	//--------------------------------------
	//  Protected Methods
	//--------------------------------------
	
		/**
		 * @private
		 */
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			this.graphics.clear();
			
			if(!this.entitypoint1 || !this.entitypoint2)
			{
				this.removeEventListener(Event.ENTER_FRAME, enterFrameHandler);
				return;
			}
			
			//we have to know when the connection point moves, or one of its parents
			//moves. there's no event for that, so we have to check every frame
			//possible optimization: do it in the manager.
			if(!this.hasEventListener(Event.ENTER_FRAME))
			{
				this.addEventListener(Event.ENTER_FRAME, enterFrameHandler);
			}
			
			var pointPositions:Array = this.calculatepointPositions();
			this._lastpoint1Position = Point(pointPositions[0]);
			this._lastpoint2Position = Point(pointPositions[1]);
			// Track for resize changed
			this._lastHeightOnEntity1 = this._entitypoint1.entity.height;
			this._lastWidthOnEntity1 = this._entitypoint1.entity.width;
			this._lastHeightOnEntity2 = this._entitypoint2.entity.height;
			this._lastWidthOnEntity2 = this._entitypoint2.entity.width;
			this._lastVerScrollPosition1 = this._entitypoint1.entity.verticalScrollPosition;
			this._lastVerScrollPosition2 = this._entitypoint2.entity.verticalScrollPosition;
		}
		
	//--------------------------------------
	//  Protected Methods
	//--------------------------------------
		
		/**
		 * @private
		 * Determines the current positions of the connection points.
		 */
		protected function calculatepointPositions():Array
		{
			var start:Point = new Point(this.entitypoint1.attribute.x, this.entitypoint1.attribute.y);
			start = DisplayObjectUtil.localToLocal(start, this.entitypoint1.entity, this);
			var end:Point = new Point(this.entitypoint2.attribute.x, this.entitypoint2.attribute.y);
			end = DisplayObjectUtil.localToLocal(end, this.entitypoint2.entity, this);
			
			return [start, end]
		}
		
	//--------------------------------------
	//  Protected Event Handlers
	//--------------------------------------
		
		/**
		 * @private
		 * Every frame, we check to see if one of the points has moved. If one
		 * or the other isn't in the same position, then we need to redraw.
		 */
		protected function enterFrameHandler(event:Event):void
		{
			if(!this.entitypoint1 || !this.entitypoint2)
			{
				return;
			}
			
			var pointPositions:Array = this.calculatepointPositions();
			var start:Point = Point(pointPositions[0]);
			var end:Point = Point(pointPositions[1]);
			if(!this._lastpoint1Position.equals(start) || !this._lastpoint2Position.equals(end)
				|| this._lastHeightOnEntity1 != this._entitypoint1.entity.height 
				|| this._lastWidthOnEntity1 != this._entitypoint1.entity.width
				|| this._lastHeightOnEntity2 != this._entitypoint2.entity.height 
				|| this._lastWidthOnEntity2 != this._entitypoint2.entity.width
				|| this._lastVerScrollPosition1 != this._entitypoint1.entity.verticalScrollPosition
				|| this._lastVerScrollPosition2 != this._entitypoint2.entity.verticalScrollPosition)
			{
				this.invalidateDisplayList();
			}
		}
	}
}