package com.controls
{
	import com.controls.baseClasses.EntityControl;
	import com.controls.relationClasses.IRelationRenderer;
	import com.events.AddTableEvent;
	import com.events.EntityEvent;
	import com.events.FieldEvent;
	import com.managers.RelationManager;
	import com.objects.EntityPoint;
	import com.objects.data.Column;
	import com.objects.data.JoinOperator;
	import com.objects.data.Relation;
	import com.objects.data.Table;
	import com.utils.DictionaryUtil;
	
	import flash.display.NativeMenu;
	import flash.display.NativeMenuItem;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.utils.Dictionary;
	
	import mx.containers.Canvas;
	import mx.states.SetStyle;
	import mx.styles.StyleManager;
	
    [Event(name="changeEntity",type="com.events.EntityEvent")]
	[Event(name="clickEntity",type="com.events.EntityEvent")]
	[Event(name="doubleClickFieldEntity",type="com.events.FieldEvent")]
	[Event(name="addTable", type="com.events.AddTableEvent")]
	public class EntityLayout extends Canvas
	{		
		<!--
		************************************************************
		***** public 変数
		************************************************************
		-->
		public var arrAllTables:Array;//array of all tables in database
		public var arrAllDefaultRelation:Array;//array of all default relationship in database
				
		//--------------------------------
		// Private Variables
		//--------------------------------
		private var _dicDataProvider:Dictionary;
		private var _activeEntityPanel:EntityPanel;
		private var _entityPanels:Array;
		private var _iEntityCount:int = 0;
		private var _arrTablesAdded:Array = new Array;
		private var menuItem:NativeMenuItem;
				
		private var defaultRelationManager:RelationManager;
		public function get relationManager():RelationManager{
			return defaultRelationManager;
		}
		
		public function set relationManager(value:RelationManager):void{
			defaultRelationManager = value;
		}
		
		private var _relationRenderer:IRelationRenderer;
		public function get relationRenderer():IRelationRenderer{
			return _relationRenderer;
		}
		
		public function set relationRenderer(value:IRelationRenderer):void{
			_relationRenderer = value;
		}
		
		//--------------------------------
		// Contructor
		//--------------------------------
		public function EntityLayout()
		{
			super();
			this.init();
			this.addEventListener(MouseEvent.CLICK, this_click);
			defaultRelationManager = new RelationManager();
			defaultRelationManager.myHKTitleWindow = this;			
		}
		
		private function this_click(event:MouseEvent):void{
			try{
				this.activeEntityPanel.titleBarDragDropHandler(event);
			}catch(e:Error){}
		}
		
		//--------------------------------
		// Properties
		//--------------------------------
		[Bindable]
		public function set dataProvider(value:Dictionary):void
		{
			_dicDataProvider = value;
			this.clearLayout();
			callLater(createLayout);			
		}

		public function get dataProvider():Dictionary
		{
			return _dicDataProvider;
		}
		
		public function getTables():Array
		{
			return DictionaryUtil.getValues(_dicDataProvider);//array object = array table in dictionary
		}
		
		public function get arrTablesAdded():Array{
			return this._arrTablesAdded;
		}
		
		public function get entityCount():int
		{
			return this._iEntityCount;
		}
		
		public function get entityPanels():Array
		{
			return this._entityPanels;
		}
		
		public function get activeEntityPanel():EntityPanel
		{
			return this._activeEntityPanel;
		}
		
		public function set activeEntityPanel(value:EntityPanel):void{
			this._activeEntityPanel = value;
		}
		
		//--------------------------------
		// Private methods
		//--------------------------------
		private function init():void
		{
			this._entityPanels = new Array();
			var menu:NativeMenu = new NativeMenu();
			menu.addEventListener(Event.DISPLAYING, contextDisplayingHandler);
			
			menuItem = new NativeMenuItem("ﾃｰﾌﾞﾙ追加");
			menuItem.addEventListener(Event.SELECT, menuItemSelect);			
			menu.addItem(menuItem);
			
			menuItem = new NativeMenuItem("", true);
			menu.addItem(menuItem);
			
			menuItem = new NativeMenuItem("新");
			menuItem.addEventListener(Event.SELECT, menuItemNewSelect);
			menu.addItem(menuItem);
			
			menuItem = new NativeMenuItem("削除");
			menuItem.addEventListener(Event.SELECT, menuItemRemoveSelect);
			menu.addItem(menuItem);
			
			menuItem = new NativeMenuItem("上書保存");
			menuItem.addEventListener(Event.SELECT, menuItemSaveSelect);
			menu.addItem(menuItem);
			
			menuItem = new NativeMenuItem("新規保存");
			menuItem.addEventListener(Event.SELECT, menuItemSaveAsSelect);
			menu.addItem(menuItem);
			
			menuItem = new NativeMenuItem("実行");
			menuItem.addEventListener(Event.SELECT, menuItemExecSelect);
			menu.addItem(menuItem);
			
			menuItem = new NativeMenuItem("リセット");
			menuItem.addEventListener(Event.SELECT, menuItemResetSelect);
			menu.addItem(menuItem);			
			
			this.contextMenu = menu;	
		}
		
		private function contextDisplayingHandler(event:Event):void{
			var items:Array = event.target["items"] as Array;
			var doc:EntityDesign = this.parentDocument as EntityDesign;
			items[3]["enabled"] = doc.btnRemove.enabled;
			//items[4]["enabled"] = doc.btnSave.enabled;
			//items[5]["enabled"] = doc.btnSaveAs.enabled;
			items[6]["enabled"] = doc.btnExecute.enabled;
			items[7]["enabled"] = doc.btnReset.enabled;
		}
		
		private function menuItemSelect(event:Event):void{
			dispatchEvent(new AddTableEvent(AddTableEvent.addTable));
		}
		
		private function menuItemNewSelect(event:Event):void{
			this.parentDocument["addNewDesigner"]();
		}
		
		private function menuItemRemoveSelect(event:Event):void{
			this.parentDocument["removeView"]();
		}
		
		private function menuItemSaveSelect(event:Event):void{
			this.parentDocument["btnViewAction_Click"]();
		}
		
		private function menuItemSaveAsSelect(event:Event):void{
			this.parentDocument["openSaveView"]();
		}
		
		private function menuItemExecSelect(event:Event):void{
			this.parentDocument["clickExecuteQuery"]();
		}
		
		private function menuItemResetSelect(event:Event):void{
			this.parentDocument["resetDesigner"]();
		}
				
		//--------------------------------
		// create layout include entity panel from tables list
		//--------------------------------
		protected function createLayout():void
		{
			if(this.dataProvider == null)
			{
				return;
			}
			_iEntityCount = 0;
			var py:int = 0;			
			for each(var tbl:Table in this.dataProvider)
			{	
				if(tbl != null)
				{					
					var ent:EntityPanel = new EntityPanel(tbl);
					ent.width = 150;
					ent.height = 190;
					var row:int = (_iEntityCount/5)
					var col:int = (_iEntityCount%5)
					if(col == 0)
					{
						py = row*ent.height + row*50;
					}					
					ent.x = col*ent.width + col*100;
					ent.y = py;
					//ent.addEventListener(EntityEvent.CLICKENTITY, onClickEntity_ent);
					ent.entityControl.addEventListener(FieldEvent.DOUBLECLICKFIELDENTITY, onDblClickFieldEntity);
					this.addChild(ent);
					this._entityPanels.push(ent);
					_iEntityCount++; 
				}				
			}			
			callLater(createRelationShip);
		}
		
		//--------------------------------
		// add new entity into entity board
		//--------------------------------
		public function addNewEntityByColumns(strTableName:String,strTableDescription:String, columns:Array):void
		{
			var tbl:Table;
			var col:Column;
			if(_dicDataProvider == null)
			{
				this._dicDataProvider = new Dictionary();
			}
			if (dataProvider[strTableName] == undefined)
           	{                        	
           		tbl = new Table(strTableName,strTableDescription);
           		dataProvider[strTableName] = tbl;
            }
            else
            {
            	tbl = dataProvider[strTableName];
            }
			tbl.Columns = columns;
			this.addNewEntity(tbl);            
		}
		
		private function getTableDescription(tableDesc:String, tables:Array, idx:int = 1):String{				
			var isDup:Boolean = false;
			var newTableDesc:String = "";
			
			for(var i:int = 0; i < tables.length; i++){
				if(tableDesc == tables[i]["Description"]){
					isDup = true;				
					
					if(tableDesc.indexOf("_") < 0){
						newTableDesc = tableDesc + "_" + idx.toString();
					}
					else{
						newTableDesc = tableDesc.split("_")[0] + "_" + idx.toString();
					}
					
					idx++;
				}
				if(isDup){
					break;
				}
			}
			
			if(isDup){					
				return getTableDescription(newTableDesc, tables, idx);
			}
			else{
				return tableDesc;
			}
		} 
				
		public function addNewEntity(tbl:Table,createRealtionFromDatabase:Boolean= true, isLoadView:Boolean = false):void
		{			
			if(_dicDataProvider == null)
			{
				this._dicDataProvider = new Dictionary();
			}
        	if(isLoadView){
            	var posSize:String = tbl.TableName.split('~')[1];
            	tbl.TableName = tbl.TableName.split('~')[0].toString().split('"')[0] + "~" + posSize;
        	}
        	tbl.Description = getTableDescription(tbl.Description, this.arrTablesAdded);
        	this._arrTablesAdded.push(tbl);
        	                  	
        	var py:int = 0;			
			if(tbl != null)
			{	
				var ent:EntityPanel = new EntityPanel(tbl);
				var row:int = (_iEntityCount/5);
				var col:int = (_iEntityCount%5);
				
				ent.width = 150;
				ent.height = 300;
					
				if(row >= 1)
				{
					py = row*ent.height + row*50;
				}
				if(isLoadView){
					var temp:String = tbl.TableName; 
					tbl.TableName = tbl.TableName.split('~')[0];
					ent = new EntityPanel(tbl);
					ent.x = temp.split('~')[1].toString().split('$')[0].toString().split(':')[0];
					ent.y = temp.split('~')[1].toString().split('$')[0].toString().split(':')[1];
					ent.width = temp.split('~')[1].toString().split('$')[1].toString().split(':')[0];
					ent.height = temp.split('~')[1].toString().split('$')[1].toString().split(':')[1];
				}
				else{				
					ent.x = col*ent.width + col*80;
					ent.y = py;						
				}										
				
				ent.addEventListener(EntityEvent.CLICKENTITY, onClickEntity_ent);
				ent.entityControl.addEventListener(FieldEvent.DOUBLECLICKFIELDENTITY, onDblClickFieldEntity);
				this.addChild(ent);
				this._entityPanels.push(ent);
				this._dicDataProvider[ent.tableName.split('~')[0]] = tbl;
				_iEntityCount++;
				
				if (createRealtionFromDatabase && !isLoadView)
				{
					createConnectRelationFromDatabase(ent,arrAllDefaultRelation);
				}
			}            
		}
		
		public function removeActiveEntityPanel():void
		{
			if (_activeEntityPanel != null)
			{
				var strTableName:String = _activeEntityPanel.tableName;
				var index:int = _entityPanels.indexOf(_activeEntityPanel);
				if (index >= 0)
				{
					this.removeChild(_activeEntityPanel);
					_activeEntityPanel = null;
					_entityPanels.splice(index,1);
					_dicDataProvider[strTableName] = undefined;
					defaultRelationManager.removeRemoveRelationshipOfTable(strTableName);
				}
			}	
		}
		
		public function resetEntityLayout():void 
        {
        	try{
	        	var i:int;
	        	for(i= _entityPanels.length - 1;i>=0;i--)
				{
					var objEntityPanel:EntityPanel = _entityPanels[i] as EntityPanel;
					var strTableName:String = objEntityPanel.tableName;
					this.removeChild(objEntityPanel);
					_entityPanels.splice(i,1);
					_dicDataProvider[strTableName] = undefined;
					defaultRelationManager.removeRemoveRelationshipOfTable(strTableName);
				}
        	}catch(e:Error){}
			this._arrTablesAdded = null;
			this._arrTablesAdded = new Array();	
			//_activeEntityPanel = null;
			_iEntityCount = 0;			  	
        }	
		
		private function createConnectRelationFromDatabase(ent:EntityPanel,arrRealtion:Array):void
		{
			var i:int;
			var j:int;
			var startEntityControl:EntityControl;
			var endEntityControl:EntityControl;
			var startPoint:EntityPoint;
			var endPoint:EntityPoint;
			var relationmanager:RelationManager = defaultRelationManager;
			if (arrRealtion == null)
			{
				return;
			} 
			for (i = 0;i < arrRealtion.length;i++)
			{
				if (arrRealtion[i]["primaryTableName"] == ent.tableName)
				{
					for (j = 0;j< _entityPanels.length;j++)
					{
						if ((_entityPanels[j] as EntityPanel).tableName == arrRealtion[i]["foreignKeyTable"])
						{
							startEntityControl = ent.entityControl;
							endEntityControl = (_entityPanels[j] as EntityPanel).entityControl;
							startPoint = new EntityPoint(startEntityControl, new Point(0, 0), getIndexOfFieldName(ent,arrRealtion[i]["primaryTableColumn"]));
							startPoint.columnName = arrRealtion[i]["primaryTableColumn"].toString();
							startPoint.relationType.text = "1";
							startPoint.showRelationType = true;
							endPoint = new EntityPoint(endEntityControl, new Point(0, 0), getIndexOfFieldName(_entityPanels[j],arrRealtion[i]["foreignKeyColumn"]));
							endPoint.columnName = arrRealtion[i]["foreignKeyColumn"].toString();
							endPoint.relationType.text = "n";
							endPoint.showRelationType = true;
							relationmanager.connect(startPoint, endPoint);
						}	
					}					
				}
				else
				{
					if (arrRealtion[i]["foreignKeyTable"] == ent.tableName)
					{
						for (j = 0;j< _entityPanels.length;j++)
						{
							if ((_entityPanels[j] as EntityPanel).tableName == arrRealtion[i]["primaryTableName"])
							{
								startEntityControl = ent.entityControl;
								endEntityControl = (_entityPanels[j] as EntityPanel).entityControl;
								startPoint = new EntityPoint(startEntityControl, new Point(0, 0), getIndexOfFieldName(ent,arrRealtion[i]["foreignKeyColumn"]));
								startPoint.columnName = arrRealtion[i]["foreignKeyColumn"].toString();
								startPoint.relationType.text = "n";
								startPoint.showRelationType = true;
								endPoint = new EntityPoint(endEntityControl, new Point(0, 0), getIndexOfFieldName(_entityPanels[j],arrRealtion[i]["primaryTableColumn"]));
								endPoint.columnName = arrRealtion[i]["primaryTableColumn"].toString();
								endPoint.relationType.text = "1";
								endPoint.showRelationType = true;
								relationmanager.connect(startPoint, endPoint);
							}
						}
					}
				}
			}
			this.relationRenderer = relationManager.relationRenderered;
		}
		
		private function replaceStringData(strSource:String, strOldValue:String, strNewValue:String):String{
			while(strSource.indexOf(strOldValue)>=0){
				strSource = strSource.replace(strOldValue, strNewValue);
			}
			
			return strSource;
		}
		
		public function createRealation(strStartTable:String,strStartColumn:String,strEndTable:String,strEndColumn:String, joinOperator:String = JoinOperator.INNER_JOIN):void
		{
			var startEntityPanel:EntityPanel = getEntityPanelByTableName(replaceStringData(strStartTable, '"', ''));
			var endEntityPanel:EntityPanel = getEntityPanelByTableName(replaceStringData(strEndTable, '"', ''));
			if (startEntityPanel != null && endEntityPanel != null)
			{
				var relationmanager:RelationManager = defaultRelationManager;
				var startEntityControl:EntityControl = startEntityPanel.entityControl;
				var endEntityControl:EntityControl = endEntityPanel.entityControl;
				var startPoint:EntityPoint;
				var endPoint:EntityPoint;
				startPoint = new EntityPoint(startEntityControl, new Point(0, 0), getIndexOfFieldName(startEntityPanel,strStartColumn));
				startPoint.columnName = strStartColumn;				
				startPoint.relationType.visible =true;
				startPoint.showRelationType = true;
				startPoint.joinOperator = joinOperator;
				startPoint.showDisplayName = true;
				endPoint = new EntityPoint(endEntityControl, new Point(0, 0), getIndexOfFieldName(endEntityPanel,strEndColumn));
				endPoint.columnName = strEndColumn				
				endPoint.relationType.visible =true;
				endPoint.showRelationType = true;
				endPoint.joinOperator = joinOperator;
				endPoint.showDisplayName = true;
				//Identify relation type
				startPoint.relationType.text = "1";
				endPoint.relationType.text = "n";
				relationmanager.connect(startPoint, endPoint);
				this.relationRenderer = relationManager.relationRenderered;
			}
		}
		
		private function getEntityPanelByTableName(strTableName:String):EntityPanel
		{
			var i:int;
			var objEntityPanel:EntityPanel;
			for (i = 0;i< _entityPanels.length;i++)
			{
				objEntityPanel = _entityPanels[i] as EntityPanel
				if (objEntityPanel["title"] == strTableName)
				{
					return objEntityPanel;
				}
			}
			return null;	
		}
		
		private function getIndexOfFieldName(ent:EntityPanel,strFieldName:String):int
		{
			var arrColumn:Array = ent.columns;
			var i:int;
			for (i = 0;i < arrColumn.length;i++)
			{
				var tmp:String = strFieldName.replace(' \n','');
				tmp = tmp.replace(' ', '');
				tmp = tmp.replace(" ","");
				if ((arrColumn[i] as com.objects.data.Column).FieldName == tmp)
				{
					return i;
				}
			}
			return 0;
		}
		
		//--------------------------------
		// create relationship among the entities on ER
		//--------------------------------
		protected function createRelationShip():void
		{
			var arrTables:Array;
			var startPoint:EntityPoint;
			var endPoint:EntityPoint;
			var index:int;
			var tmpEntity:EntityPanel;
			var arrTmpCols:Array;
			for each(var ent:EntityPanel in _entityPanels)
			{
				if(ent.relations == null || ent.relations.length == 0)
				{
					break;
				}
				startPoint = null;
				endPoint = null;
				for each(var rel:Relation in ent.relations)
				{
					//Get table have be relationship with this table.
					arrTables = this._entityPanels.filter(function(entp:EntityPanel, index:int, source:Array):Boolean
														{
															return entp.tableName == rel.TableName;
														});
					if(arrTables.length > 0)
					{
						arrTmpCols = ent.columns.filter(function(col:Column, index:int, source:Array):Boolean
														{
															return col.FieldName == rel.ThisFieldName;
														});
						if(arrTmpCols.length > 0)
						{
							index = ent.columns.indexOf(arrTmpCols[0] as Column, 0);
							startPoint = new EntityPoint(ent.entityControl, new Point(0, 0), index);
						}
						
						tmpEntity = arrTables[0] as EntityPanel;
						arrTmpCols = tmpEntity.columns.filter(function(col:Column, index:int, source:Array):Boolean
													{
														return col.FieldName == rel.FieldName;
													});
						if(arrTmpCols.length > 0)
						{
							index = tmpEntity.columns.indexOf(arrTmpCols[0] as Column, 0);
							endPoint = new EntityPoint(tmpEntity.entityControl, new Point(0, 0), index);
						}
						if(startPoint != null && endPoint != null)
						{
							defaultRelationManager.connect(startPoint, endPoint);							
						}
					}
				}
			}
		}
		
		//--------------------------------
		// Public methods
		//--------------------------------
		public function clearLayout():void
		{ 
			this.removeAllChildren();
		}
		
		private function onClickEntity_ent(event:EntityEvent):void
		{
			_activeEntityPanel = event.entity as EntityPanel;			
		}
		
		private function onDblClickFieldEntity(event:FieldEvent):void
		{
			dispatchEvent(event.clone());
		}
	}
}