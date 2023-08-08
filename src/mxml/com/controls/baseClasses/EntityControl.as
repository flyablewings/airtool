package com.controls.baseClasses
{
	import com.assets.iconEmbed;
	import com.controls.EntityPanel;
	import com.controls.relationClasses.IRelationRenderer;
	import com.events.FieldEvent;
	import com.managers.IRelationManager;
	import com.managers.RelationManager;
	import com.objects.EntityPoint;
	import com.objects.data.Column;
	
	import flash.events.MouseEvent;
	import flash.events.NativeDragEvent;
	import flash.geom.Point;
	
	import mx.containers.Canvas;
	import mx.controls.List;
	import mx.core.ClassFactory;
	import mx.core.DragSource;
	import mx.core.IFlexDisplayObject;
	import mx.core.IUIComponent;
	import mx.events.DragEvent;
	import mx.events.ListEvent;
	import mx.managers.DragManager;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;

	//--------------------------------------
	//  Styles
	//--------------------------------------

	[Style(name="dragImage", type="Class")]
	[Event(name="doubleClickFieldEntity", type="com.events.FieldEvent")]
	public class EntityControl extends List
	{
		private var _entityPanel:EntityPanel;
		private var _indext:int;
		public static var _titleWindow:Canvas;
		public static var _source:DragSource;
		public static var _firstColumnName:String;
		public static var _firstTableName:String;
		public static var _firstColumnDescription:String;
		public static var _firstTableDescription:String;

		//--------------------------------------
		//  Static Properties
		//--------------------------------------		
		/**
		 * The dragFormat used by wire jacks for drag and drop operations.
		 */
		protected static const RELATION_DRAG_FORMAT:String="relation::entityPoint";
		protected static const COLUMN_DRAG_FORMAT:String="relation::column";

		/**
		 * @private
		 * Sets the default styles for the WireManager
		 */
		private function initializeStyles():void
		{
			var styles:CSSStyleDeclaration=styleManager.getStyleDeclaration("EntityControl");
			if (!styles)
			{
				styles=new CSSStyleDeclaration();
			}

			styles.defaultFactory=function():void
			{
				this.dragImage=iconEmbed.dragImage;
			}
			styleManager.setStyleDeclaration("EntityControl", styles, false);
		}

		//--------------------------------------
		//  Contructor
		//--------------------------------------
		public function EntityControl()
		{
			super();
			this.dragEnabled = true;
			this.dragMoveEnabled = true;
			this.setStyle("color", 0x000000);
			this.doubleClickEnabled=true;
			this.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown_this);
			this.addEventListener(ListEvent.ITEM_DOUBLE_CLICK, onItemDblClick_this);
			this.addEventListener(DragEvent.DRAG_ENTER, onDragEnter_this, true);
			this.addEventListener(DragEvent.DRAG_DROP, dragDropHandler, true);
			this.addEventListener(NativeDragEvent.NATIVE_DRAG_DROP, dragNativeDropHandler);
			if(_titleWindow == null){_titleWindow = new Canvas();}
			this.relationManager=_titleWindow["relationManager"];
			initializeStyles();			
		}

		//--------------------------------------
		//  Properties
		//--------------------------------------
		public function set entityPanel(value:EntityPanel):void
		{
			_entityPanel=value;
		}

		public function get entityPanel():EntityPanel
		{
			return _entityPanel;
		}

		/**
		 * @private
		 * Storage for the relationManager property.
		 */
		private var _relationManager:IRelationManager;

		[Bindable]
		/**
		 * The IRelationManager with which this entity point is registered.
		 */
		public function get relationManager():IRelationManager
		{
			return this._relationManager;
		}

		/**
		 * @private
		 */
		public function set relationManager(value:IRelationManager):void
		{
			if (!value)
			{
				value=RelationManager.defaultRelationManager;
			}

			this._relationManager=value;

			if (this._relationManager)
			{
				if ((this._relationManager as RelationManager).myHKTitleWindow == null)
				{
					(this._relationManager as RelationManager).myHKTitleWindow=_titleWindow;
				}
				else
				{
					if ((this._relationManager as RelationManager).myHKTitleWindow != _titleWindow)
					{
						(this._relationManager as RelationManager).myHKTitleWindow=_titleWindow;
					}
				}
			}
		}

		//--------------------------------------
		//  Public method
		//--------------------------------------
		public function isVerticalScrollBarShowing():Boolean
		{
			if (this.verticalScrollBar == null)
			{
				return false;
			}
			return this.verticalScrollBar.visible;
		}

		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}

		private function isFocusOnScroll(event:MouseEvent):Boolean
		{
			var pMouse:Point=this.globalToLocal(new Point(event.stageX, event.stageY));
			if (this.verticalScrollBar.x <= pMouse.x && pMouse.x <= this.verticalScrollBar.x + this.verticalScrollBar.width)
			{
				return true;
			}
			return false;
		}

		private function onMouseDown_this(event:MouseEvent):void
		{
			if (this.verticalScrollBar && this.verticalScrollBar.visible && isFocusOnScroll(event))
			{
				return;
			}
			if (this.selectedItem)
			{
				var p:EntityPoint=new EntityPoint(this, new Point(0, 0), this.selectedIndex);
				this.relationManager.beginConnectionRequest(p);
				var source:DragSource=new DragSource();
				source.addData(p, RELATION_DRAG_FORMAT);
				var o:Object = this.selectedItem;
				o["TableDescription"] = owner["title"];
				source.addData(o, COLUMN_DRAG_FORMAT);
				
				var dragImageType:Class=iconEmbed.dragImage;
				var image:IFlexDisplayObject=new dragImageType();
				DragManager.doDrag(this, source, event, image, this.mouseX, this.mouseY);

				_source=source;
				var objCol:Column=source.dataForFormat(COLUMN_DRAG_FORMAT) as Column;
				var objEntity:EntityPoint=source.dataForFormat(RELATION_DRAG_FORMAT) as EntityPoint;
				_firstColumnName=objCol.FieldName;
				_firstColumnDescription=objCol.Description;
				_firstTableName=objEntity.entity.entityPanel.tableName
				_firstTableDescription=objEntity.entity.entityPanel.table.Description;
			}
		}

		private function onDragEnter_this(event:DragEvent):void
		{
			event.preventDefault();
			if (!this.enabled)
			{
				return;
			}
			var source:DragSource=event.dragSource;
			if (!source.hasFormat(RELATION_DRAG_FORMAT))
			{
				return;
			}

			var otherJack:EntityPoint=source.dataForFormat(RELATION_DRAG_FORMAT) as EntityPoint;
			if (otherJack.entity == this)
			{
				return;
			}
			//make sure both jacks are compatible
			//if(this.isCompatibleWithJack(otherJack) && otherJack.isCompatibleWithJack(this))
			//{
			var dragInitiator:List=List(event.currentTarget);
			DragManager.acceptDragDrop(dragInitiator);
			DragManager.showFeedback(DragManager.LINK);

			this.addEventListener(DragEvent.DRAG_EXIT, dragExitHandler, true);
			this.addEventListener(DragEvent.DRAG_DROP, dragDropHandler);
			//}
		}

		/**
		 * @private
		 */
		override protected function dragExitHandler(event:DragEvent):void
		{
			event.preventDefault();
			this.hideDropFeedback(event);
			this.removeEventListener(DragEvent.DRAG_EXIT, dragExitHandler, true);
			this.removeEventListener(DragEvent.DRAG_DROP, dragDropHandler, true);
			_indext=this.calculateDropIndex(event);
		}

		/**
		 * @private
		 * Assuming the data is compatible, connect this jack to the other one.
		 */
		override protected function dragDropHandler(event:DragEvent):void
		{
		/*event.preventDefault();
		   this.hideDropFeedback(event);
		   this.removeEventListener(DragEvent.DRAG_EXIT, dragExitHandler,true);
		   this.removeEventListener(DragEvent.DRAG_DROP, dragDropHandler,true);

		   var source:DragSource = new DragSource();
		   source.addData(p, RELATION_DRAG_FORMAT);
		   source.addData(this.selectedItem, COLUMN_DRAG_FORMAT);
		   if(!source.hasFormat(RELATION_DRAG_FORMAT))
		   {
		   return;
		   }
		   var otherJackCol:Column = source.dataForFormat(COLUMN_DRAG_FORMAT) as Column;
		   var otherJack:EntityPoint = source.dataForFormat(RELATION_DRAG_FORMAT) as EntityPoint;
		   if(otherJack.entity == this)
		   {
		   return;
		   }
		   otherJack.columnName = otherJackCol.FieldName;
		   otherJack.entityPanel = this.entityPanel;
		   var indext:int = this.calculateDropIndex(event);
		   var pCol:Column = this.dataProvider[indext] as Column;
		   var p:EntityPoint = new EntityPoint(this, new Point(0, 0), indext);
		   p.entityPanel = this.entityPanel;
		   p.columnName = pCol.FieldName;
		   //(this.parent as SuperPanel).title = indext.toString();
		 this.relationManager.connect(otherJack, p); */
		}

		private function dragNativeDropHandler(event:NativeDragEvent):void
		{
			event.preventDefault();
			//this.removeEventListener(NativeDragEvent.NATIVE_DRAG_DROP, dragNativeDropHandler);
			//note: we don't care if the connection was successful here.
			//instead, the wire manager will notify us through an event that the
			//connection was made
			var source:DragSource=_source;
			if (!source.hasFormat(RELATION_DRAG_FORMAT))
			{
				return;
			}
			var otherJackCol:Column=source.dataForFormat(COLUMN_DRAG_FORMAT) as Column;
			var otherJack:EntityPoint=source.dataForFormat(RELATION_DRAG_FORMAT) as EntityPoint;
			if (otherJack.entity == this)
			{
				return;
			}
			otherJack.columnName=otherJackCol.FieldName;
			//otherJack.entityPanel = this.entityPanel;
			//var indext:int = this.calculateDropIndex(event);
			var pCol:Column=this.dataProvider[_indext] as Column;
			var p:EntityPoint=new EntityPoint(this, new Point(0, 0), _indext);
			//p.entityPanel = this.entityPanel;
			p.columnName=pCol.FieldName;
			this.relationManager.connect(otherJack, p);
		}

		private function onItemDblClick_this(event:ListEvent):void
		{
			if (this.selectedItem)
			{
				dispatchEvent(new FieldEvent(FieldEvent.DOUBLECLICKFIELDENTITY, this.selectedItem));
			}
		}

	}
}