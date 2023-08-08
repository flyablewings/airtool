package com.controls
{
	import com.controls.baseClasses.EntityControl;
	import com.controls.baseClasses.SuperPanel;
	import com.events.EntityEvent;
	import com.objects.data.Table;
	
	import flash.display.NativeMenu;
	import flash.display.NativeMenuItem;
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.events.FlexEvent;
	[Event(name="clickEntity",type="com.events.EntityEvent")]
	[Event(name="removeTable",type="com.events.RemoveTableEvent")]
	public class EntityPanel extends SuperPanel
	{
		//----------------------------------
		// Variable
		//----------------------------------
		private var _table:Table;
		private var _entityControl:EntityControl = new EntityControl();
		//private var _labelField:String = "FieldName";
		private var _labelField:String = "Description";
		private var menuItem:NativeMenuItem;
		//----------------------------------
		// Contructor
		//----------------------------------
		//public function EntityPanel(name:String = "")
		public function EntityPanel(tbl:Table)
		{			
			super();
			this.table = tbl;
			this._entityControl.entityPanel = this;			
			this.resizeEnabled = true;
			this.dragEnabled = true;
			//this.showControls = true;
			this.setStyle("borderThicknessRight", 2);
			this.setStyle("borderThicknessLeft", 2);
		}
		//----------------------------------
		// Properties
		//----------------------------------
		public function get entityControl():EntityControl
		{
			return _entityControl;
		}
		
		public function set table(value:Table):void
		{
			_table = value;
			//this.title = _table.TableName;
			this.title = _table.Description;
			this._entityControl.dataProvider = _table.Columns;
			this.dispatchEvent(new FlexEvent(FlexEvent.DATA_CHANGE));
		}
		
		public function get table():Table
		{
			return _table;
		}
		
		public function get tableName():String
		{
			return _table.TableName;
		}
		
		[Bindable("dataChange")]
		public function get dataProvider():Object
		{
			return _table.Columns;
		}
		
		public function get columns():Array
		{
			return _table.Columns;
		}

		public function set labelField(value:String):void
		{
			_labelField = value;
		}
		
		public function get labelField():String
		{
			return _labelField;
		}
		
		public function get relations():Array
		{
			return _table.Relations;
		}		
		//----------------------------------
		// Protected method
		//----------------------------------
		override protected function createChildren():void
		{
			super.createChildren();
			this._entityControl.percentHeight = 100;
			this._entityControl.percentWidth = 100;
			this._entityControl.dataProvider = this.dataProvider;
			this._entityControl.labelField = this._labelField;			
			this.addChild(this._entityControl);
			this.titleBar.addEventListener(MouseEvent.CLICK, onClick_titleBar);
			var menu:NativeMenu = new NativeMenu();
			menuItem = new NativeMenuItem("削除")
			menuItem.addEventListener(Event.SELECT, menuItemSelect);			
			menu.addItem(menuItem);
			this.titleBar.contextMenu = menu;	
		}
		
		private function menuItemSelect(event:Event):void{
			this.parent["activeEntityPanel"] = this;			
			this.parent["parentDocument"]["deleteSelectedTable"]();
		}

		private function onClick_titleBar(event:MouseEvent):void
		{
			dispatchEvent(new EntityEvent(EntityEvent.CLICKENTITY, this));
		}
	}
}