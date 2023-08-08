package com.objects.data 
{
	public class Column 
	{
		public static const COLUMNNAME:String = "FieldName";
		public static const COLUMNDESCRIPTION:String = "Description";
		public var IsPrimaryKey:Boolean;
		public var TableName:String;
		public var FieldName:String;
		public var FieldType:String;
		public var Lenght:Number;
		public var Description:String;
		public var TableDescription:String;
		public function Column(TableName:String="", FieldName:String="",Description:String="", FieldType:String = "varchar", Lenght:Number = 10, IsPrimaryKey:Boolean = false, TableDescription:String="") 
		{
			this.TableName = TableName;
			this.TableDescription = TableDescription;
			this.FieldName = FieldName;
			this.FieldType = FieldType;
			this.Lenght = Lenght;
			this.Description = Description;
			this.IsPrimaryKey = IsPrimaryKey;
		}
	} // end class
} // end package