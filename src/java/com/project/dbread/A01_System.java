package com.project.dbread;
import com.hokukou.database.*;
/*
********************************************************************************
================================================================================
   ＤＢ入出力ライブラリー
================================================================================
   Program Number  : A01_System.java
   Program Name    : システム管理表
   Program Date    : 2007/11/01
   Programmer      : 山本
   
===< Update History >===========================================================
   2007/11/01 : read:読込機能
================================================================================
********************************************************************************
*/

public class A01_System {
	
	private String _strA01_SysCD = "";
	private String _strA01_SysNM = "";
	private String _strA01_SysVL = "";
	
	public void setA01_SysCD(String strValue){
		_strA01_SysCD = strValue;
	}
	public String getA01_SysNM(){
		return _strA01_SysNM;
	}
	public String getA01_SysVL(){
		return _strA01_SysVL;
	}
	
	/* +------------------------------------------------------------------------+ */
	/* |  システム管理表の読込                                                  | */
	/* +------------------------------------------------------------------------+ */
	public boolean read(DB_Connection dbcArg) throws Exception{
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		StringBuffer stbSql = new StringBuffer();
		boolean blnExist;
		
		try{
			stbSql.append("\n"+ "SELECT");
			stbSql.append("\n"+ "    A01_SysNM,");
			stbSql.append("\n"+ "    A01_SysVL");
			stbSql.append("\n"+ "FROM");
			stbSql.append("\n"+ "    A01_System");
			stbSql.append("\n"+ "WHERE");
			stbSql.append("\n"+ "    A01_SysCD = '" + DB_CtrlChar.edit(_strA01_SysCD) + "'");
			stbSql.append("\n"+ ";");
			
			blnExist = false;	
			
			dbs = dbcArg.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
			
			if (dbr.next()) {
				_strA01_SysNM = dbr.getString("A01_SysNM");
				_strA01_SysVL = dbr.getString("A01_SysVL");
				blnExist = true;	
			}
			
		} catch (DB_Exception e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		return 	blnExist;	
	}
}
