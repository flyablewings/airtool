package com.project.dbread;

import com.hokukou.database.*;
/*
********************************************************************************
================================================================================
			大学システムパッケージの教務プログラム
================================================================================
	Program Number  : K46_CtfNo_AutoNumber.java
	Program Name    : 自動採番(証明書用)
	Program Date    : 2009/02/17
	Programmer      : T.KOJIMA
   
===< Update History >===========================================================
	2009/02/17 : 新規作成
================================================================================
********************************************************************************
*/
public class K46_CtfNo_AutoNumber {
	
	private int _intK46_No = 0;
	
	public int getNumCnt(){
		return this._intK46_No;
	}
	
	/*
	*************************************************************************** 
	***自動採番
	***************************************************************************
	*/
	public boolean read(	DB_Connection dbc,
							int intK46_Year,
							int intK46_KB,
							String strK51_CD,
							int intK52_State,
							String strTerm,
							String strUser) throws Exception{
		
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		StringBuffer stbSql = new StringBuffer();
		int intK46_No = 0;
		boolean blnExist;
				
		stbSql.append("\n" + "SELECT");
		stbSql.append("\n" + "    K46_No");
		stbSql.append("\n" + "FROM");
		stbSql.append("\n" + "    K46_CtfNo");
		stbSql.append("\n" + "WHERE");
		stbSql.append("\n" + "	  K46_Year  =  " + String.valueOf(intK46_Year) + " ");
		stbSql.append("\n" + "AND");
		stbSql.append("\n" + "	  K46_KB    =  " + String.valueOf(intK46_KB) + " ");
		stbSql.append("\n" + "AND");
		stbSql.append("\n" + "    K51_CD    = '" + DB_CtrlChar.edit(strK51_CD) + "' ");
		stbSql.append("\n" + "AND");
		stbSql.append("\n" + "	  K52_State =  " + String.valueOf(intK52_State) + " ");
		stbSql.append("\n" + ";");
		
		blnExist = false;	
		
		try {
			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
			
			if(dbr.next()) {
				intK46_No = dbr.getInt("K46_No");
				if (intK46_No == 0) {
					intK46_No = 1;
				}
				this._intK46_No = intK46_No;
				blnExist = this._updateK46_CtfNo(	dbc,
													intK46_Year,
													intK46_KB,
													strK51_CD,
													intK52_State,
													intK46_No,
													strTerm,
													strUser		);
			}else{
				intK46_No = 1;
				this._intK46_No = intK46_No;
				blnExist = this._insertK46_CtfNo(	dbc,
													intK46_Year,
													intK46_KB,
													strK51_CD,
													intK52_State,
													intK46_No,
													strTerm,
													strUser		);
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
	
	private boolean _insertK46_CtfNo(	DB_Connection dbc,
									  	int intK46_Year,
									  	int intK46_KB,
									  	String strK51_CD,
									  	int intK52_State,
									  	int intK46_No,
									  	String strTerm,
									  	String strUser	)throws Exception{
		int intResult;
		DB_Statement dbs = null;
		StringBuffer stbSql = new StringBuffer();
		intK46_No = intK46_No + 1;
		
		stbSql.append("\n" + "INSERT INTO");
		stbSql.append("\n" + "    K46_CtfNo");
		stbSql.append("\n" + "(");
		stbSql.append("\n" + "    Create_Date,");
		stbSql.append("\n" + "    Create_Term,");
		stbSql.append("\n" + "    Create_User,");
		stbSql.append("\n" + "    Update_Date,");
		stbSql.append("\n" + "    Update_Term,");
		stbSql.append("\n" + "    Update_User,");
		stbSql.append("\n" + "    K46_Year,   ");
		stbSql.append("\n" + "    K46_KB,     ");
		stbSql.append("\n" + "    K51_CD,     ");
		stbSql.append("\n" + "    K52_State,  ");
		stbSql.append("\n" + "    K46_No      ");
		stbSql.append("\n" + ")");
		stbSql.append("\n" + "VALUES");
		stbSql.append("\n" + "(");
		stbSql.append("\n" + "    '" + dbc.getNow() + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strTerm) + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strUser) + "',");
		stbSql.append("\n" + "    '" + dbc.getNow() + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strTerm) + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strUser) + "',");
		stbSql.append("\n" + "     " + String.valueOf(intK46_Year) + ",");
		stbSql.append("\n" + "     " + String.valueOf(intK46_KB) + ",");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strK51_CD) + "',");
		stbSql.append("\n" + "     " + String.valueOf(intK52_State) + ",");
		stbSql.append("\n" + "     " + String.valueOf(intK46_No) + "");
		stbSql.append("\n" + ")");
		stbSql.append("\n" + ";");

		try{
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();
		} catch (DB_Exception e) {
			throw e ;
		} catch (Exception ex) {
			throw ex ;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		if(intResult == 0 ){
			return false;
		}
		return true;
	}
	
	private boolean _updateK46_CtfNo(	DB_Connection dbc,
		  								int intK46_Year,
		  								int intK46_KB,
		  								String strK51_CD,
		  								int intK52_State,
		  								int intK46_No,
		  								String strTerm,
		  								String strUser	)throws Exception{
		int intResult;
		DB_Statement dbs = null;
		StringBuffer stbSql = new StringBuffer();
		
		intK46_No = intK46_No + 1;
		
		stbSql.append("\n" + "UPDATE");
		stbSql.append("\n" + "    K46_CtfNo");
		stbSql.append("\n" + "SET");
		stbSql.append("\n" + "    K46_No 	  =  " + String.valueOf(intK46_No) + ",");
		stbSql.append("\n" + "    Update_Date = '" + dbc.getNow() + "',");
		stbSql.append("\n" + "    Update_Term = '" + DB_CtrlChar.edit(strTerm) + "',");
		stbSql.append("\n" + "    Update_User = '" + DB_CtrlChar.edit(strUser) + "' ");
		stbSql.append("\n" + "WHERE");
		stbSql.append("\n" + "	  K46_Year  =  " + String.valueOf(intK46_Year) + " ");
		stbSql.append("\n" + "AND");
		stbSql.append("\n" + "	  K46_KB    =  " + String.valueOf(intK46_KB) + " ");
		stbSql.append("\n" + "AND");
		stbSql.append("\n" + "    K51_CD    = '" + DB_CtrlChar.edit(strK51_CD) + "' ");
		stbSql.append("\n" + "AND");
		stbSql.append("\n" + "	  K52_State =  " + String.valueOf(intK52_State) + " ");
		stbSql.append("\n" + ";");

		try{
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();
		} catch (DB_Exception e) {
			throw e ;
		} catch (Exception ex) {
			throw ex ;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		if(intResult == 0 ){
			return false;
		}
		return true;
	}
}
