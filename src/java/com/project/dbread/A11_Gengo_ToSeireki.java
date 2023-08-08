package com.project.dbread;

import com.hokukou.database.*;
import com.hokukou.data.*;
/*
********************************************************************************
================================================================================
	MCｼｽﾃﾑ様  献立・配達管理ｼｽﾃﾑ
================================================================================
	Program Number  : A11_Gengo_ToSeireki.java
	Program Name    : 和暦を西暦に変換する処理
	Program Date    : 2007/09/07
	Programmer      : 劉斌
   
===< Update History >===========================================================
   2007/09/07 : 新規作成
================================================================================
********************************************************************************
*/
public class A11_Gengo_ToSeireki {

	private int _intA11_GengoCD = 0;	//元号コード
	private int _intA11_GengoDT = 0;	//元年(西暦日付)
	private int _intYear = 0;			//年
	private int _intMonth = 0;			//月
	private int _intDay = 0;			//日
	private int _intSeireki = 0;		//西暦
	//元号コードを受け取る
	public void setGengoCD(int intValue){
		this._intA11_GengoCD = intValue;
	}
	//年を受け取る
	public void setYear(int intValue){
		this._intYear = intValue;
	}
	//月を受け取る
	public void setMonth(int intValue){
		this._intMonth = intValue;
	}
	//日を受け取る
	public void setDay(int intValue){
		this._intDay = intValue;
	}
	//西暦日付を読取
	public int getSeireki(){
		return this._intSeireki;
	}
	/*
	*************************************************************************** 
	***和暦を西暦に変換する処理
	***************************************************************************
	*/
	public boolean read(DB_Connection dbcArg) throws Exception{
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		StringBuffer stbSql = new StringBuffer();
		boolean blnExist;
		
		stbSql.append("\n"+ "SELECT ");
		stbSql.append("\n"+ "    A11_GengoDT ");
		stbSql.append("\n"+ "FROM ");
		stbSql.append("\n"+ "    A11_Gengo ");
		stbSql.append("\n"+ "WHERE ");
		stbSql.append("\n"+ "    A11_GengoCD = " + this._intA11_GengoCD + " ");
		stbSql.append("\n"+ ";");
		
		blnExist = false;	
		
		try {
			dbs = dbcArg.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
			
			if (dbr.next()) {
				blnExist = true;
				String strGengoDT = String.valueOf(dbr.getInt("A11_GengoDT"));
				this._intA11_GengoDT = new Integer(strGengoDT.substring(0,4)).intValue();
				int intYear = this._intA11_GengoDT + this._intYear - 1;
				String strYear = this._leftPad(intYear,4) 
								+ this._leftPad(this._intMonth,2)
								+ this._leftPad(this._intDay,2);
				this._intSeireki = HK_Data.parseInt(strYear);
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
	
	private String _leftPad(int intValue,int intLeg){
		String strValue = intValue + "";
		for(;strValue.length() < intLeg;){
			strValue = "0" + strValue;
		}
		return strValue;
	}
}
