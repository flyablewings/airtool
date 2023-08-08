package com.managers
{
	import com.controls.relationClasses.DefaultRelationRenderer;
	import com.controls.relationClasses.IRelationRenderer;
	import com.events.RelationManagerEvent;
	import com.objects.EntityPoint;
	
	import flash.display.DisplayObject;
	import flash.events.EventDispatcher;
	
	import mx.containers.Canvas;
	import mx.core.Application;
	import mx.core.ClassFactory;
	import mx.core.FlexGlobals;
	import mx.core.IFactory;
	import mx.core.IUIComponent;
	import mx.core.UIComponent;
	import mx.managers.PopUpManager;
	
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
	public class RelationManager extends EventDispatcher implements IRelationManager
	{		
		//--------------------------------------
		//  Static Properties
		//--------------------------------------			
		/**
		 * The default relation manager that is used if no relation manager is specified.
		 */
		public static function get defaultRelationManager():RelationManager
		{			
			return new RelationManager();
		}
		//Tuyen
		//private var _myHKTitleWindow:TitleWindow;
		private var _myHKTitleWindow:Canvas;
		//public function get myHKTitleWindow():TitleWindow
		public function get myHKTitleWindow():Canvas
		{
			return _myHKTitleWindow;
		}
		
		private static var relRenderer:IRelationRenderer;
		public function get relationRenderered():IRelationRenderer{
			return relRenderer;
		}
		
		//Tuyen
		//public function set myHKTitleWindow(myTitle:TitleWindow):void
		public function set myHKTitleWindow(myTitle:Canvas):void
		{
			_myHKTitleWindow = myTitle;			
		}
		
		//--------------------------------------
		//  Constructor
		//--------------------------------------
		public function RelationManager(surface:IUIComponent = null)
		{
			//TODO: implement function
			if(!surface)
			{
				this.relationSurface = new UIComponent();
			}
			else
			{
				this.relationSurface = surface;
			}
		}
		//--------------------------------------
		//  Properties
		//--------------------------------------
	
		/**
		 * @private
		 * The surface on which to draw relation.
		 */
		protected var relationSurface:IUIComponent;
		
		/**
		 * @private
		 * The entity points registered with this relation manager.
		 */
		//protected var entityPoints:Array = [];
		
		/**
		 * @private
		 * The relationships created by this relation manager.
		 */
		protected var _relationships:Array = [];
		
		public function get relationships():Array
		{
			return _relationships;
		}
		
		/**
		 * @private
		 * Flag indicating that a relation is connecting.
		 */
		protected var connecting:Boolean = false;
		
		private var _relationRenderer:IFactory = new ClassFactory(DefaultRelationRenderer);
		
		public function get relationRenderer():IFactory
		{
			return this._relationRenderer;
		}
		
		public function set relationRenderer(value:IFactory):void
		{
			this._relationRenderer = value;
		}
	//--------------------------------------
	//  Public Methods
	//--------------------------------------
		public function registerEntityPoint(entitypoint:EntityPoint):void
		{
			//TODO: implement function
			_relationships.push(entitypoint);
		}
		
		public function deleteEntityPoint(entitypoint:EntityPoint):void
		{
			//TODO: implement function
			var index:int = _relationships.indexOf(entitypoint);
			if(index >= 0){
				//
				_relationships.splice(index, 1);
			}
		}
		
		public function beginConnectionRequest(entitypoint:EntityPoint):void
		{
			//TODO: implement function
			var begin:RelationManagerEvent = new RelationManagerEvent(RelationManagerEvent.BEGIN_CONNECTION_REQUEST, entitypoint);
			this.dispatchEvent(begin);
		}
		
		public function endConnectionRequest(entitypoint:EntityPoint):void
		{
			//TODO: implement function
			var end:RelationManagerEvent = new RelationManagerEvent(RelationManagerEvent.END_CONNNECTION_REQUEST, entitypoint);
			this.dispatchEvent(end);
		}
		
		public function connect(startEntityPoint:EntityPoint, endEntityPoint:EntityPoint):Boolean
		{
			var relationships:Array = _relationships.filter(function(relation:IRelationRenderer, index:int, source:Array):Boolean
			{
				/* trace("relattindex1:" + relation.entitypoint1.attributeIndex
						+ " relattindex2:" + relation.entitypoint2.attributeIndex
						+ " startattindex:" + startEntityPoint.attributeIndex
						+ " endtattindex:" + endEntityPoint.attributeIndex
						+ " index:"+index.toString()); */
				return (
							(relation.entitypoint1.entity == startEntityPoint.entity && relation.entitypoint1.attributeIndex == startEntityPoint.attributeIndex)
							|| 
							(relation.entitypoint1.entity == endEntityPoint.entity && relation.entitypoint1.attributeIndex == endEntityPoint.attributeIndex )
						)
						&&
						(
							(relation.entitypoint2.entity == startEntityPoint.entity && relation.entitypoint2.attributeIndex == startEntityPoint.attributeIndex) 
							||
							(relation.entitypoint2.entity == endEntityPoint.entity && relation.entitypoint2.attributeIndex == endEntityPoint.attributeIndex)
						);
			});
			if(relationships.length <= 0){
				//TODO: implement function
				var creating:RelationManagerEvent = new RelationManagerEvent(RelationManagerEvent.CREATING_CONNECTION, startEntityPoint, endEntityPoint, true);
				var result:Boolean = this.dispatchEvent(creating);
				if(result)
				{
					//if the connection was successful, create the wire
					var relation:IRelationRenderer = this.relationRenderer.newInstance();
					relation.relationManager = this;
					relation.entitypoint1 = startEntityPoint;
					relation.entitypoint2 = endEntityPoint;
					relRenderer = relation;					
					_myHKTitleWindow.addChild(DisplayObject(relation));
					_myHKTitleWindow.addChild(relation.entitypoint1.relationType);
					_myHKTitleWindow.addChild(relation.entitypoint2.relationType);
					_relationships.push(relation);
					
					//this flag indicates that a connection is in progress.
					//disconnect requests during this time will be queued until
					//the connection is complete because not all jacks may have
					//saved the connection while the flag is still true.
					this.connecting = true;
					var create:RelationManagerEvent = new RelationManagerEvent(RelationManagerEvent.CREATE_CONNECTION, startEntityPoint, endEntityPoint);
					this.dispatchEvent(create);
					this.connecting = false;
					return result;
				}
			}
			return false;
		}
		
		public function disconnect(startEntityPoint:EntityPoint, endEntityPoint:EntityPoint):void
		{
			if(this.connecting)
			{
				//can't disconnect if a connection is in progress.
				FlexGlobals.topLevelApplication.callLater(disconnect, [startEntityPoint, endEntityPoint]);
				return;
			}
			//TODO: implement function
			/* var relationships:Array = this.relationships.filter(function(relation:IRelationRenderer, index:int, source:Array):Boolean
			{
				return (relation.entitypoint1 == startEntityPoint || relation.entitypoint1 == endEntityPoint) &&
					(relation.entitypoint2 == startEntityPoint || relation.entitypoint2 == endEntityPoint);
			}); */
			var relationships:Array = _relationships.filter(function(relation:IRelationRenderer, index:int, source:Array):Boolean
			{
				return (
							(relation.entitypoint1.entity == startEntityPoint.entity && relation.entitypoint1.attributeIndex == startEntityPoint.attributeIndex)
							|| 
							(relation.entitypoint1.entity == endEntityPoint.entity && relation.entitypoint1.attributeIndex == endEntityPoint.attributeIndex )
						)
						&&
						(
							(relation.entitypoint2.entity == startEntityPoint.entity && relation.entitypoint2.attributeIndex == startEntityPoint.attributeIndex) 
							||
							(relation.entitypoint2.entity == endEntityPoint.entity && relation.entitypoint2.attributeIndex == endEntityPoint.attributeIndex)
						);
			});
			
			for each(var relation:IRelationRenderer in relationships)
			{
				var index:int = _relationships.indexOf(relation);
				_relationships.splice(index, 1);
				_myHKTitleWindow.removeChild(DisplayObject(relation));
				_myHKTitleWindow.removeChild(relation.entitypoint1.relationType);
				_myHKTitleWindow.removeChild(relation.entitypoint2.relationType);				
				relation.entitypoint1 = null;
				relation.entitypoint2 = null;
				relation = null;
				relRenderer = relation;
			}
			
			var deleteRel:RelationManagerEvent = new RelationManagerEvent(RelationManagerEvent.DELETE_CONNECTION, startEntityPoint, endEntityPoint);
			this.dispatchEvent(deleteRel);
		}

		public function removeRemoveRelationshipOfTable(strTable:String):void
		{
			var i:int = 0;
			for (i = _relationships.length - 1; i >=0;i--)
			{
				var relation:IRelationRenderer = _relationships[i];
				if (relation.entitypoint1.entity.entityPanel.tableName == strTable 
					||relation.entitypoint2.entity.entityPanel.tableName == strTable)
					{
						disconnect(relation.entitypoint1,relation.entitypoint2);
					}
				relRenderer = relation;
			}
		}		
	}
}