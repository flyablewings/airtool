package com.project.dbread;

import com.hokukou.data.HK_Data;
import com.hokukou.database.*;
/*
********************************************************************************
================================================================================
  (株)ホクコウ　共通システム
================================================================================
	Program Number  : A11_Gengo_ToWareki.java
	Program Name    : 西暦を和暦に変換する処理
	Program Date    : 2009/02/12
	Programmer      : T.KOJIMA
   
===< Update History >===========================================================
   2009/02/12 : 新規作成
================================================================================
********************************************************************************
*/

public class A11_Gengo_ToWareki {
	
	private String _strA11_GengoNM = "";
	private String _strA11_GengoTN = "";
	private String _strWarekiYear = "";
	private int _intSeirekiYear = 0;
	private int _intSeirekiMonth = 0;
	private int _intSeirekiDay = 0;
	
	public void setSeirekiYear(int intValue){
		_intSeirekiYear = intValue;
	}
	public void setSeirekiMonth(int intValue){
		_intSeirekiMonth = intValue;
	}
	public void setSeirekiDay(int intValue){
		_intSeirekiDay = intValue;
	}
	public String getGengoNM(){
		return _strA11_GengoNM;
	}
	public String getGengoTN(){
		return _strA11_GengoTN;
	}
	public String getWarekiYear(){
		return _strWarekiYear;
	}
	
	public boolean read(DB_Connection dbcArg) throws Exception{
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		StringBuffer stbSql = new StringBuffer();
		boolean blnExist;
		int intDevideDate[];
		
		try {
			
			int intK11_GengoDT = _intSeirekiYear * 10000 + _intSeirekiMonth * 100 + _intSeirekiDay;
			String strK11_GengoDT = HK_Data.dateFormat(intK11_GengoDT, "");
			int intWarekiY = 0;
			
			stbSql.append("\n"+ "SELECT");
			stbSql.append("\n"+ "    A11_GengoNM,");
			stbSql.append("\n"+ "    A11_GengoTN,");
			stbSql.append("\n"+ "    A11_GengoDT");
			stbSql.append("\n"+ "FROM");
			stbSql.append("\n"+ "    A11_Gengo");
			stbSql.append("\n"+ "WHERE");
			stbSql.append("\n"+ "    A11_GengoDT < " + strK11_GengoDT + "");
			stbSql.append("\n"+ "ORDER BY");
			stbSql.append("\n"+ "    A11_GengoDT DESC");
			stbSql.append("\n"+ ";");
			
			blnExist = false;	
			
			dbs = dbcArg.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
			
			if (dbr.next()) {
				blnExist = true;	
				_strA11_GengoNM = dbr.getString("A11_GengoNM");
				_strA11_GengoTN = dbr.getString("A11_GengoTN");
				
				intDevideDate = HK_Data.devideDate(dbr.getInt("A11_GengoDT"));
				
				intWarekiY = _intSeirekiYear - intDevideDate[0] + 1;
				if (intWarekiY == 1) {
					_strWarekiYear = "元";
				} else {
					_strWarekiYear = HK_Data.Format(intWarekiY, "#0",2);
				}
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
