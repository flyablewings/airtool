package com.objects.data{

	public class Table
	{
		public static const TABLENAME:String = "tableName";
		public static const TABLEDESCRIPTION:String = "Description";
		public var TableName:String;
		public var Description:String;
		public var Columns:Array = new Array();
		public var Relations:Array = new Array();
		public function Table(tableName:String,description:String, columns:Array = null, relations:Array = null) 
		{
			this.TableName = tableName;
			this.Description = description;
			if (columns!=null)
			{
				this.Columns = columns;	
			}
			if (relations!=null)
			{
				this.Relations = relations;
			}
		}
	} // end class
} // end package