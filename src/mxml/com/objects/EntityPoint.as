package com.objects
{
	import com.controls.baseClasses.EntityControl;
	import com.objects.data.JoinOperator;
	
	import flash.geom.Point;
	
	import mx.controls.Label;
	
	public class EntityPoint
	{
		private var _blnShowDisplayName:Boolean;//display name or not
		private var _blnShowRelationType:Boolean;//display relation type or not
		
		private var _displayName:String;//name for display
		//private var _relationType:String;//relation type: 1, n
		private var _relationType:Label;//relation type: 1, n
		
		private var _columnName:String;//column name of relation
		
		private var _entity:EntityControl;
		private var _attribute:Point;
		private var _attributeIndex:int;
		<!--
		***************************************************************
		* XungNV added this variable on 2010-10-22															  *
		*************************************************************** 
		-->
		private var _joinName:String = JoinOperator.INNER_JOIN;
	//--------------------------------------
	//  Constructor
	//--------------------------------------
		/**
		 *Constructor
		 * @param valEntity The Entity object.
		 * @param valAtt The attribute with the entity.
		 * 
		 */		
		public function EntityPoint(valEntity:EntityControl, valAtt:Point, valAttIndex:int)
		{
			this._entity = valEntity;
			this._attribute = valAtt;
			this._attributeIndex = valAttIndex;
			
			this._relationType = new Label();
			_relationType.text = "1";
			_relationType.height = 20;
			_blnShowRelationType = true;
			_blnShowDisplayName = true;
		}
		
		public function set columnName(value:String):void
		{
			_columnName = value;
		}
		public function get columnName():String
		{
			return _columnName;
		}
		
		public function set showDisplayName(value:Boolean):void
		{
			_blnShowDisplayName = value;
		}
		public function get showDisplayName():Boolean
		{
			return _blnShowDisplayName;
		}
		
		public function set displayName(value:String):void
		{
			_displayName = value;
		}
		public function get displayName():String
		{
			return _displayName;
		}

		public function set showRelationType(value:Boolean):void
		{
			_blnShowRelationType = value;
		}
		public function get showRelationType():Boolean
		{
			return _blnShowRelationType;
		}
		public function set relationType(value:Label):void
		{
			_relationType = value;
		}
		public function get relationType():Label
		{
			return _relationType;
		}

		/* public function set entity(val:EntityControl):void
		{
			_entity = val;
		} */
		/**
		 *Gets entity. 
		 * @return 
		 * 
		 */
		public function get entity():EntityControl
		{
			return _entity;
		}

		/* public function set attribute(val:Point):void
		{
			_attribute = val;
		} */
		/**
		 *Get attribute position in entity. 
		 * @return 
		 * 
		 */
		public function get attribute():Point
		{
			return _attribute;
		}
		/**
		 *Get attribute index in entity 
		 * @return 
		 * 
		 */		
		public function get attributeIndex():int
		{
			return _attributeIndex;
		}
		
		<!-- Begin XungNV added -->
		public function set joinOperator(value:String):void{
			this._joinName = value;
		}
		
		public function get joinOperator():String{
			return this._joinName;
		}		
	}
}