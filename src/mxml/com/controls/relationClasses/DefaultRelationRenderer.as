package com.controls.relationClasses
{
	import com.controls.baseClasses.EntityControl;
	import com.utils.EntityDisplayUtil;
	import com.yahoo.astra.utils.DisplayObjectUtil;
	
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	
	import mx.controls.Label;
	import mx.managers.PopUpManager;
	import mx.managers.PopUpManagerChildList;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.IStyleManager2;
	import mx.styles.StyleManager;
	
	//--------------------------------------
	//  Styles
	//--------------------------------------
	
	[Style(name="fillColor",type="uint")]
	[Style(name="borderColor",type="uint")]
	[Style(name="myThickness", type="Number")]
	[Style(name="thickness",type="Number")]
	[Style(name="myColor",type="uint")]
	
	public class DefaultRelationRenderer extends BaseRelationRenderer
	{
		
		//--------------------------------------
		//  Static Methods
		//--------------------------------------
		private var _myLabel:Label = new Label();		
		/**
		 * @private
		 * Sets the default styles for the WireManager
		 */
		private function initializeStyles():void
		{
			var styles:CSSStyleDeclaration = styleManager.getStyleDeclaration("DefaultRelationRenderer");
			if(!styles)
			{
				styles = new CSSStyleDeclaration();
			}
			
			styles.defaultFactory = function():void
			{
				this.fillColor = 0x000000;
				this.borderColor = 0x000000;
				this.thickness = 2;
				this.borderWidth = 40;
				this.myColor = 0xffffff;				
			}
			
			styleManager.setStyleDeclaration("DefaultRelationRenderer", styles, false);
		}		
		
	//--------------------------------------
	//  Constructor
	//--------------------------------------
		public function DefaultRelationRenderer()
		{
			super();
			this.buttonMode = true;
			this.addEventListener(MouseEvent.CLICK, clickHandler);
			this.addEventListener(MouseEvent.RIGHT_CLICK, rightClickHandler);
			initializeStyles();
		}
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
				return;
			}
			
			var start:Point = EntityDisplayUtil.localTolocalHadScroll(this.entitypoint1);//new Point(this.entitypoint1.attribute.x, this.entitypoint1.attribute.y);
			start = DisplayObjectUtil.localToLocal(start, this.entitypoint1.entity, this);
			var end:Point = EntityDisplayUtil.localTolocalHadScroll(this.entitypoint2);//new Point(this.entitypoint2.attribute.x, this.entitypoint2.attribute.y);
			end = DisplayObjectUtil.localToLocal(end, this.entitypoint2.entity, this);
			
			var minX:Number = Math.min(start.x, end.x);
			var maxX:Number = Math.max(start.x, end.x);
			var minY:Number = Math.min(start.y, end.y);
			var maxY:Number = Math.max(start.y, end.y);
			var middle:Point = new Point(minX + (maxX - minX) / 2, minY + (maxY - minY) / 2);
			
			//Alway draw from left to right
			var bSwappoint:Boolean = false;
			if(minX != start.x){
				var ptmp:Point = start;
				start = end;
				end = ptmp;
				
				start = new Point(start.x + this.entitypoint2.entity.width, start.y);
				end = new Point(end.x, end.y);
				bSwappoint = true;
			}else{
				start = new Point(start.x + this.entitypoint1.entity.width, start.y);
				end = new Point(end.x, end.y);
			}
			
			var angle1:Point = new Point(start.x + 20, start.y);
			var angle2:Point = new Point(end.x - 20, end.y);
			//Display big relation with no color
			var myColor:uint = this.getStyle("myColor");
			var borderThickness:Number = this.getStyle("borderWidth");
			
			this.graphics.lineStyle(40, myColor, 0.01);
			this.graphics.beginFill(myColor);
			this.graphics.drawCircle(start.x, start.y, 0);
			this.graphics.moveTo(start.x, start.y);
			this.graphics.lineTo(angle1.x, angle1.y);
			this.graphics.moveTo(angle1.x, angle1.y);
			this.graphics.lineTo(angle2.x, angle2.y);
			this.graphics.moveTo(angle2.x, angle2.y);
			this.graphics.lineTo(end.x, end.y);
			this.graphics.drawCircle(end.x, end.y, 0);
			//
			var thickness:Number = 2;//this.getStyle("thickness");
			var borderColor:uint = 0x000000;//this.getStyle("borderColor");
			var fillColor:uint = 0x000000;//this.getStyle("fillColor");
			this.graphics.lineStyle(thickness, borderColor);
			this.graphics.beginFill(fillColor);
			this.graphics.drawCircle(start.x, start.y, thickness);
			this.graphics.moveTo(start.x, start.y);
			this.graphics.lineTo(angle1.x, angle1.y);
			this.graphics.moveTo(angle1.x, angle1.y);
			this.graphics.lineTo(angle2.x, angle2.y);
			this.graphics.moveTo(angle2.x, angle2.y);
			this.graphics.lineTo(end.x, end.y);
			this.graphics.drawCircle(end.x, end.y, thickness);
			//display name or not: from entitypoint1 and entitypoint2						
			//display relation type or not
			if (bSwappoint == false)
			{
				if (entitypoint1.showRelationType == true)
				{
					entitypoint1.relationType.x = angle1.x;
					entitypoint1.relationType.y = angle1.y - entitypoint1.relationType.height - 10;
				}
				if (entitypoint2.showRelationType == true)
				{
					entitypoint2.relationType.x = angle2.x - 10;
					entitypoint2.relationType.y = angle2.y - entitypoint2.relationType.height - 10;
				}	
			}
			else
			{
				if (entitypoint1.showRelationType == true)
				{
					entitypoint1.relationType.x = angle2.x;
					entitypoint1.relationType.y = angle2.y - entitypoint1.relationType.height - 10;
				}
				if (entitypoint2.showRelationType == true)
				{
					entitypoint2.relationType.x = angle1.x - 10;
					entitypoint2.relationType.y = angle1.y - entitypoint2.relationType.height - 10;
				}	
			}
			this.graphics.endFill();
		}
		
	//--------------------------------------
	//  Protected Event Handlers
	//--------------------------------------
		
		/**
		 * @private
		 * When you click on the relation, it disconnects.
		 */
		protected function clickHandler(event:MouseEvent):void
		{
			openRelationMenu();
			//this.graphics.clear();
		}
		
		/**
		 * @private
		 * When you click on the relation, it disconnects.
		 */
		protected function rightClickHandler(event:MouseEvent):void
		{
			getContextMenu();
		}
		
		protected function deleteRelation():void
        {
        	this.disconnect();
        }
        
        protected function setupRelation():void
        {
        	openRelationMenu();
        }
        
		protected function openRelationMenu():void
        {
        	var relMenu:RelationMenu = new RelationMenu(); 
        	var objHelpCode:Object = new Object;
        	relMenu.relationRenderer = this;
        	relMenu.x = (this.parentDocument.width - relMenu.width + this.parentApplication["childTitleWindow"]["vbxTree"]["width"])/2;
			relMenu.y = (this.parentDocument.height - relMenu.height)/2;
        	PopUpManager.addPopUp(relMenu,this.parent,true,PopUpManagerChildList.POPUP);
        }
        
        private function menuItemSelectHandler_Setup(event:ContextMenuEvent):void 
        {
            openRelationMenu();
        }
        
        private function menuItemSelectHandler_Delete(event:ContextMenuEvent):void 
        {
          this.disconnect();
        }
        
        protected function getContextMenu():void
        {
        	relContextMenu = new ContextMenu();			
			relContextMenu.hideBuiltInItems();
			
			var delMenuItem:ContextMenuItem = new ContextMenuItem("削除", false,true,true);
			delMenuItem.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,menuItemSelectHandler_Delete);
			relContextMenu.customItems.push(delMenuItem);
			
			var capMenuItem:ContextMenuItem = new ContextMenuItem("結合設定", false,true,true);
			capMenuItem.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,menuItemSelectHandler_Setup);
			relContextMenu.customItems.push(capMenuItem);
			
			this.contextMenu = relContextMenu;
        }		
	}
}