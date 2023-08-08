package com.objects.data{

	public class Relation {

		public var ThisFieldName:String;
		
		public var TableName:String;

		public var FieldName:String;

		public var Type:String;


		public function Relation(ThisFieldName:String, TableName:String, FieldName:String, Type:String = "") 
		{			 
			this.ThisFieldName = ThisFieldName;
			this.TableName = TableName;
			this.FieldName = FieldName;
			this.Type = Type;
		}

	} // end class
} // end package