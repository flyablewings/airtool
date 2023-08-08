package com.sharesingle
{
	import de.polygonal.ds.HashTable;
	
	public class GlobalShare
	{
		//key: table name, value: array of field name
		private static var _hashDesignTables:HashTable; 
		
		//objFieldName can be array
		public static function insertColumns(strTableName:String, objFieldName:Object):void
		{
			if (GlobalShare._hashDesignTables==null)
			{
				GlobalShare._hashDesignTables = new HashTable(1);
			}
			if(GlobalShare._hashDesignTables.find(strTableName) != objFieldName)
			{
				GlobalShare._hashDesignTables.insert(strTableName,objFieldName);
			}
		}
		
		public static function getColumns(strTableName:String):Object
		{
			if (GlobalShare._hashDesignTables!=null)
			{
				return GlobalShare._hashDesignTables.find(strTableName);
			}
			return null;
		}
	}
}