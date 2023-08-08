package com.project.dbread;

import com.hokukou.database.*;
/*
********************************************************************************
================================================================================
			大学システムパッケージの共通プログラム
================================================================================
	Program Number  : A02_System_AutoNumber.java
	Program Name    : 自動採番
	Program Date    : 2007/09/07
	Programmer      : 劉斌
   
===< Update History >===========================================================
	2007/09/07 : 新規作成
	2007/10/19 : 修正								=劉斌
	2007/12/05 : 採番方法に障害						=山本
================================================================================
********************************************************************************
*/
public class A02_System_AutoNumber {
	
	private int _intNumCnt = 0;
	
	public int getNumCnt(){
		return this._intNumCnt;
	}
	/*
	*************************************************************************** 
	***自動採番
	***************************************************************************
	*/
	public boolean read(DB_Connection dbc,
						int intNend,
						String strNumCD,
						String strNumNM,
						String strTerm,
						String strUser) throws Exception{
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		StringBuffer stbSql = new StringBuffer();
		int intNumCNT = 0;
		boolean blnExist;
				
		stbSql.append("\n"+ "SELECT ");
		stbSql.append("\n"+ "	A02_NumNM,");
		stbSql.append("\n"+ "	A02_NumCNT ");
		stbSql.append("\n"+ "FROM ");
		stbSql.append("\n"+ "	A02_SysNum ");
		stbSql.append("\n"+ "WHERE ");
		stbSql.append("\n"+ "	A02_Nend = " + intNend + " ");
		stbSql.append("\n"+ "	AND ");
		stbSql.append("\n"+ "	A02_NumCD = '" + DB_CtrlChar.edit(strNumCD) + "' ");
		stbSql.append("\n"+ ";");
		
		blnExist = false;	
		
		try {
			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
			
			if(dbr.next()) {
				intNumCNT = dbr.getInt("A02_NumCNT");
				if (intNumCNT == 0) {
					intNumCNT = 1;
				}
				this._intNumCnt = intNumCNT;
				blnExist = this._updateA02_SysNum(dbc,intNend,strNumCD,intNumCNT,strTerm,strUser);
			}else{
				intNumCNT = 1;
				this._intNumCnt = intNumCNT;
				blnExist = this._insertA02_SysNum(dbc,intNend,strNumCD,strNumNM,intNumCNT,strTerm,strUser);
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
	
	private boolean _insertA02_SysNum(DB_Connection dbc,
									  int intNend,
									  String strNumCD,
									  String strNumNM,
									  int intNumCNT,
									  String strTerm,
									  String strUser)throws Exception{
		int intResult;
		DB_Statement dbs = null;
		StringBuffer stbSql = new StringBuffer();
		intNumCNT = intNumCNT + 1;
		
		stbSql.append("\n" + "INSERT INTO");
		stbSql.append("\n" + "    A02_SysNum ");
		stbSql.append("\n" + "(");
		stbSql.append("\n" + "    Create_Date,");
		stbSql.append("\n" + "    Create_Term,");
		stbSql.append("\n" + "    Create_User,");
		stbSql.append("\n" + "    Update_Date,");
		stbSql.append("\n" + "    Update_Term,");
		stbSql.append("\n" + "    Update_User,");
		stbSql.append("\n" + "    A02_Nend,");
		stbSql.append("\n" + "    A02_NumCD,");
		stbSql.append("\n" + "    A02_NumNM,");
		stbSql.append("\n" + "    A02_NumCNT ");
		stbSql.append("\n" + ")");
		stbSql.append("\n" + "VALUES");
		stbSql.append("\n" + "(");
		stbSql.append("\n" + "    '" + dbc.getNow() + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strTerm) + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strUser) + "',");
		stbSql.append("\n" + "    '" + dbc.getNow() + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strTerm) + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strUser) + "',");
		stbSql.append("\n" + "    " + intNend + ",");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strNumCD) + "',");
		stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strNumNM) + "',");
		stbSql.append("\n" + "    " + intNumCNT + "");
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
	
	private boolean _updateA02_SysNum(DB_Connection dbc,
			  						  int intNend,
			  						  String strNumCD,
			  						  int intNumCNT,
									  String strTerm,
									  String strUser)throws Exception{
		int intResult;
		DB_Statement dbs = null;
		StringBuffer stbSql = new StringBuffer();
		
		intNumCNT = intNumCNT + 1;
		
		stbSql.append("\n" + "UPDATE ");
		stbSql.append("\n" + "	A02_SysNum ");
		stbSql.append("\n" + "SET ");
		stbSql.append("\n" + "	A02_NumCNT 	= " + intNumCNT + ",");
		stbSql.append("\n" + "	Update_Date = '" + dbc.getNow() + "',");
		stbSql.append("\n" + "	Update_Term = '" + DB_CtrlChar.edit(strTerm) + "',");
		stbSql.append("\n" + "	Update_User = '" + DB_CtrlChar.edit(strUser) + "' ");
		stbSql.append("\n" + "WHERE ");
		stbSql.append("\n" + "	A02_Nend = " + intNend + " ");
		stbSql.append("\n" + "	AND ");
		stbSql.append("\n" + "	A02_NumCD = '" + DB_CtrlChar.edit(strNumCD) + "'");
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
