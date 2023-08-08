package com.controls.baseClasses {
	import com.assets.iconEmbed;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import mx.collections.errors.CursorError;
	import mx.containers.Panel;
	import mx.controls.Button;
	import mx.core.Application;
	import mx.core.FlexGlobals;
	import mx.core.UIComponent;
	import mx.events.DragEvent;
	import mx.events.EffectEvent;
	import mx.managers.CursorManager;
		
	public class SuperPanel extends Panel {
	//--------------------------------------
	//  Variables
	//--------------------------------------
		[Bindable] public var showControls:Boolean 	= false;
		[Bindable] public var resizeEnabled:Boolean = false;
		[Bindable] public var dragEnabled:Boolean	= false;
		
		private var	pTitleBar:UIComponent;
		private var oW:Number;
		private var oH:Number;
		private var oX:Number;
		private var oY:Number;
		private var oAlpha:Number			= 1;
		//private var menuButton:Button		= new Button();
		private var normalMaxButton:Button = new Button();
		private var closeButton:Button		= new Button();
		private var resizeHandler:Button	= new Button();
		
		//private var saveButton:Button		= new Button();
		private var menuPanel:Panel			= new Panel();
		//private var menuBarCanvas:Canvas	= new Canvas();
		//private var colorPick:ColorPicker	= new ColorPicker();
		//private var hRule01:HRule			= new HRule();
		//private var alphaSlider:HSlider		= new HSlider();
		//private var alphaLabel:Label		= new Label();
		//private var colorLabel:Label		= new Label();
		//private var menuLabeButtonl:Label			= new Label();
		//private var titleLabel:Label		= new Label();
		private var oPoint:Point 			= new Point();
		//private var panelTitle:TextInput	= new TextInput();
		private var resizeCur:Number		= 0;
		private var minW:Number;
		private var minH:Number;
	//--------------------------------------
	//  Constructor
	//--------------------------------------
		public function SuperPanel() {
		}
	//--------------------------------------
	//  Protected Methods
	//--------------------------------------
		override protected function childrenCreated():void{
			super.childrenCreated();
			this.minW = 100;
			this.minH = 35;
			super.verticalScrollPolicy = "off";
			super.horizontalScrollPolicy = "off";
		}
		
		override protected function createChildren():void {
			super.createChildren();
			this.pTitleBar = super.titleBar;
			this.setStyle("headerColors", [0x587789, 0x587789]);
			this.setStyle("highlightAlphas", [0.5, 0.25]);
			this.setStyle("borderColor", 0x587789);
			this.setStyle("borderAlpha", 0.2);
			
			if (resizeEnabled) {
				this.resizeHandler.width     = 10;
				this.resizeHandler.height    = 10;
				this.resizeHandler.setStyle("upSkin", iconEmbed.resizeHandler);
				this.resizeHandler.setStyle("overSkin", iconEmbed.resizeHandler);
				this.resizeHandler.setStyle("downSkin", iconEmbed.resizeHandler);
				this.resizeHandler.setStyle("disabledSkin", iconEmbed.resizeHandler);
				this.resizeHandler.toolTip = "リサイズ";				
				this.rawChildren.addChild(resizeHandler);				
				this.initPos();
			}
			
			if (showControls) {
				this.normalMaxButton.width     	= 10;
				this.normalMaxButton.height    	= 10;
				this.normalMaxButton.setStyle("upSkin", iconEmbed.increaseButton);
				this.normalMaxButton.setStyle("overSkin", iconEmbed.increaseButton);
				this.normalMaxButton.setStyle("downSkin", iconEmbed.increaseButton);
				this.normalMaxButton.setStyle("disabledSkin", iconEmbed.increaseButton);
				this.normalMaxButton.toolTip	= "Maximize";
				this.closeButton.width     		= 10;
				this.closeButton.height    		= 10;
				this.closeButton.setStyle("upSkin", iconEmbed.closeButton);
				this.closeButton.setStyle("overSkin", iconEmbed.closeButton);
				this.closeButton.setStyle("downSkin", iconEmbed.closeButton);
				this.closeButton.setStyle("disabledSkin", iconEmbed.closeButton);
				this.closeButton.toolTip		= "Close";
				this.pTitleBar.addChild(this.normalMaxButton);
				this.pTitleBar.addChild(this.closeButton);
			}
			
			this.positionChildren();	
			this.addListeners();
		}
	//--------------------------------------
	//  Public Methods
	//--------------------------------------	
		public function initPos():void {
			this.oW = this.width;
			this.oH = this.height;
			this.oX = this.x;
			this.oY = this.y;
		}
		
		/**
		 * Reposition all children (button)
		 * 
		 */		
		public function positionChildren():void {
			if (showControls) {
				this.normalMaxButton.buttonMode    = true;
				this.normalMaxButton.useHandCursor = true;
				this.normalMaxButton.x = this.unscaledWidth - this.normalMaxButton.width - 24;
				this.normalMaxButton.y = 8;
				this.closeButton.buttonMode	   = true;
				this.closeButton.useHandCursor = true;
				this.closeButton.x = this.unscaledWidth - this.closeButton.width - 8;
				this.closeButton.y = 8;
			}			
			if (resizeEnabled && this.resizeHandler.visible) {
				this.resizeHandler.y = this.unscaledHeight - resizeHandler.height - 1;				
				this.resizeHandler.x = this.unscaledWidth - resizeHandler.width - 1;
			}
		}
		/**
		 *Add events listener 
		 * 
		 */				
		public function addListeners():void {			
			this.addEventListener(MouseEvent.CLICK, panelClickHandler);
			if (dragEnabled) {
				this.pTitleBar.addEventListener(MouseEvent.MOUSE_DOWN, titleBarDownHandler);						
			}
			
			if (showControls) {
				this.closeButton.addEventListener(MouseEvent.CLICK, closeClickHandler);
				this.normalMaxButton.addEventListener(MouseEvent.CLICK, normalMaxClickHandler);
			}
			
			if (resizeEnabled) {
				this.resizeHandler.addEventListener(MouseEvent.MOUSE_OVER, resizeOverHandler);
				this.resizeHandler.addEventListener(MouseEvent.MOUSE_OUT, resizeOutHandler);
				this.resizeHandler.addEventListener(MouseEvent.MOUSE_DOWN, resizeDownHandler);	
				this.resizeHandler.addEventListener(MouseEvent.CLICK, resizeClickHandler);			
				this.resizeHandler.drawFocus(false);
			}
		}
		
		private function resizeClickHandler(event:MouseEvent):void{
			this.resizeHandler.drawFocus(false);
			event.stopPropagation();
		}		
		
		public function panelClickHandler(event:MouseEvent):void {
			this.pTitleBar.removeEventListener(MouseEvent.MOUSE_MOVE, titleBarMoveHandler);
			this.parent.setChildIndex(this, this.parent.numChildren - 1);
			this.panelFocusCheckHandler();
		}
		
		public function titleBarDownHandler(event:MouseEvent):void {
			this.pTitleBar.addEventListener(MouseEvent.MOUSE_MOVE, titleBarMoveHandler);
		}
		
		public function titleBarMoveHandler(event:MouseEvent):void {			
			if (this.width < screen.width) {
				this.pTitleBar.addEventListener(MouseEvent.MOUSE_UP, titleBarDragDropHandler);
				this.pTitleBar.addEventListener(DragEvent.DRAG_DROP,titleBarDragDropHandler);	
				this.pTitleBar.addEventListener(MouseEvent.CLICK, titleBarDragDropHandler);							
				this.parent.setChildIndex(this, this.parent.numChildren - 1);
				this.panelFocusCheckHandler();
				this.alpha = 0.5;
				this.startDrag(false, new Rectangle(0, 0, 10000, 10000));				
				this.cursorManager.currentCursorXOffset = this.x + this.width + this.titleBar.height/2;
				this.cursorManager.currentCursorYOffset = this.y + this.width + this.titleBar.height/2;
			}
		}
		
		public function titleBarDragDropHandler(event:MouseEvent):void {
			this.pTitleBar.removeEventListener(MouseEvent.MOUSE_MOVE, titleBarMoveHandler);
			this.stopDrag();
			if (this.oAlpha < 1) {
				this.alpha = this.oAlpha;
			} else {
				this.alpha = 1;
			}
		}
		
		/**
		 *Set style on focus panel 
		 * 
		 */		
		public function panelFocusCheckHandler():void {
			for (var i:int = 0; i < this.parent.numChildren; i++) {
				var child:UIComponent = UIComponent(this.parent.getChildAt(i));
				if (this.parent.getChildIndex(child) < this.parent.numChildren - 1) {
					child.setStyle("borderAlpha", 0.2);
				} else if (this.parent.getChildIndex(child) == this.parent.numChildren - 1) {
					child.setStyle("borderAlpha", 0.5);
				}
			}
		}
		
		public function endEffectEventHandler(event:EffectEvent):void {
			this.resizeHandler.visible = true;
		}
		
		public function normalMaxClickHandler(event:MouseEvent):void {
			if (this.normalMaxButton.getStyle("upSkin") == iconEmbed.increaseButton) {
				if (this.height > 28) {
					this.parent.addEventListener(Event.RESIZE, onResize_parent);
					this.initPos();
					this.x = 0;
					this.y = 0;
					resizeFullScreenOnParent();
					this.normalMaxButton.setStyle("upSkin", iconEmbed.decreaseButton);
					this.normalMaxButton.setStyle("overSkin", iconEmbed.decreaseButton);
					this.normalMaxButton.setStyle("downSkin", iconEmbed.decreaseButton);
					this.normalMaxButton.setStyle("disabledSkin", iconEmbed.decreaseButton);
					this.normalMaxButton.toolTip = "Restore";
					if(resizeEnabled){
						this.resizeHandler.visible = false;
					}
					this.positionChildren();
				}
			} else {
				this.parent.removeEventListener(Event.RESIZE, onResize_parent);
				this.x = this.oX;
				this.y = this.oY;
				this.width = this.oW;
				this.height = this.oH;
				this.normalMaxButton.setStyle("upSkin", iconEmbed.increaseButton);
				this.normalMaxButton.setStyle("overSkin", iconEmbed.increaseButton);
				this.normalMaxButton.setStyle("downSkin", iconEmbed.increaseButton);
				this.normalMaxButton.setStyle("disabledSkin", iconEmbed.increaseButton);
				this.normalMaxButton.toolTip = "Maximize";
				if(resizeEnabled){
						this.resizeHandler.visible = true;
				}
				this.positionChildren();
			}
		}
		
		public function closeClickHandler(event:MouseEvent):void {
			this.removeEventListener(MouseEvent.CLICK, panelClickHandler);
			this.parent.removeChild(this);
		}
		
		public function resizeOverHandler(event:MouseEvent):void {			
			this.resizeCur = cursorManager.setCursor(iconEmbed.resizeCursor);
		}
		
		public function resizeOutHandler(event:MouseEvent):void {			
			cursorManager.removeCursor(cursorManager.currentCursorID);
		}
		
		public function resizeDownHandler(event:MouseEvent):void {		
			this.resizeHandler.drawFocus(false);
			event.stopPropagation();			
			this.parent.addEventListener(MouseEvent.MOUSE_MOVE, resizeMoveHandler);
			this.parent.addEventListener(MouseEvent.MOUSE_UP, resizeUpHandler);
			this.resizeHandler.addEventListener(MouseEvent.MOUSE_OVER, resizeOverHandler);
			this.panelClickHandler(event);
			this.resizeCur = CursorManager.setCursor(iconEmbed.resizeCursor);
			this.oPoint.x = mouseX;
			this.oPoint.y = mouseY;
			this.oPoint = this.localToGlobal(oPoint);	
		}
		
		public function resizeMoveHandler(event:MouseEvent):void {
			this.resizeHandler.drawFocus(false);
			event.stopPropagation();
			var xPlus:Number = FlexGlobals.topLevelApplication.parent.mouseX - this.oPoint.x;			
			var yPlus:Number = FlexGlobals.topLevelApplication.parent.mouseY - this.oPoint.y;
			if (this.oW + xPlus > minW) {
				this.width = this.oW + xPlus;
			}
			if (this.oH + yPlus > minH) {
				this.height = this.oH + yPlus;
			}
			this.positionChildren();
		}
		
		public function resizeUpHandler(event:MouseEvent):void {
			this.parent.removeEventListener(MouseEvent.MOUSE_MOVE, resizeMoveHandler);
			this.parent.removeEventListener(MouseEvent.MOUSE_UP, resizeUpHandler);
			CursorManager.removeCursor(CursorManager.currentCursorID);
			this.resizeHandler.addEventListener(MouseEvent.MOUSE_OVER, resizeOverHandler);
			this.initPos();
		}
		
		private function resizeFullScreenOnParent():void{			
			this.width = this.parent.width - getBorderThicknessParent()*2;//screen.width;
			this.height = this.parent.height - getBorderThicknessParent()*2;//screen.height;
		}
		
		public function getBorderThicknessParent():Number{
			return (this.parent as UIComponent).getStyle("borderThickness");
		}
		
		private function onResize_parent(evt:Event):void
		{			
			resizeFullScreenOnParent();
			this.positionChildren();
		}
	}
}