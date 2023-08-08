package com.project.dbread;

import com.hokukou.data.HK_Data;
import com.hokukou.database.*;
/*
 ********************************************************************************
 ================================================================================
 				ホクコウ共通プログラム
 ================================================================================
 Program Number  : C13_Log.java
 Program Name    : 共通関数群(ログ表書込)
 Program Date    : 2009/01/08
 Programmer      : T.KOJIMA
 
 ===< Update History >===========================================================
 2009/01/08 : 新規作成
 ================================================================================
 ********************************************************************************
 */

public class C13_Log {
	
	public void store(	DB_Connection dbc,
						String strUserCD,
						String strTermCD,
						String strPgmCD,
						String strOperation)	throws Exception{
		
		DB_Statement dbs = null;

		String strC13_Date = HK_Data.getDate("");
		String strC13_Time = HK_Data.getTime("");
		String strC13_Term = strTermCD;
		String strC04_StaffCD = strUserCD;
		
		//SEQ取得
		int intNextSeq = C13_Log.getTimeSeq(dbc, strC13_Date, strC13_Time, strC13_Term,strC04_StaffCD);
		
		StringBuffer stbSql = new StringBuffer();
		
		try{
	 		stbSql.append("\n"+ "INSERT INTO");
			stbSql.append("\n"+ "    C13_Log");
			stbSql.append("\n"+ "(");
			stbSql.append("\n"+ "    C13_Date,");
			stbSql.append("\n"+ "    C13_Time,");
			stbSql.append("\n"+ "    C13_Term,");
			stbSql.append("\n"+ "    C04_StaffCD,");
			stbSql.append("\n"+ "    C13_Seq,");
			stbSql.append("\n"+ "    A07_PgmCD,");
			stbSql.append("\n"+ "    C13_Oparetion");
			stbSql.append("\n"+ ")");
			stbSql.append("\n"+ "VALUES");
			stbSql.append("\n"+ "(");
			stbSql.append("\n" + "    '" + HK_Data.getDate("") + "',");
			stbSql.append("\n" + "    '" + HK_Data.getTime("") + "',");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strTermCD) + "',");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strUserCD) + "',");
			stbSql.append("\n" + "     " + String.valueOf(intNextSeq) + ",");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strPgmCD) + "',");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(strOperation) + "'");
			stbSql.append("\n"+ ")");
			stbSql.append("\n"+ ";");

			dbs = dbc.prepareStatement(stbSql.toString());
			dbs.executeUpdate();
			
		} catch (DB_Exception e) {		
			throw e;	
		} catch (Exception ex) {			
			throw ex;		
		} finally {			
			if (dbs != null){ dbs.close();  dbs = null; }		
		}			
	}
	
    private static int getTimeSeq(DB_Connection dbc, 
    								String strC13_Date, 
    								String strC13_Time, 
    								String strC13_Term,
    								String strC04_StaffCD) throws DB_Exception, Exception {
    	
        DB_Statement dbs = null;
        DB_ResultSet dbr = null;
        
        int intNextSeq = 0;
        
        StringBuffer stbSql = new StringBuffer();
        
        stbSql.append("\n" + "SELECT");
        stbSql.append("\n" + "    C13_Seq");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C13_Log");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C13_Date 		= '" + DB_CtrlChar.edit(strC13_Date) + "'");
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    C13_Time		= '" + DB_CtrlChar.edit(strC13_Time) + "'");
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    C13_Term 		= '" + DB_CtrlChar.edit(strC13_Term) + "'");
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    C04_StaffCD 	= '" + DB_CtrlChar.edit(strC04_StaffCD) + "'");
        stbSql.append("\n" + "ORDER BY");
        stbSql.append("\n" + "    C13_Seq DESC");
        stbSql.append("\n" + "FOR UPDATE");
        
        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
            	intNextSeq = dbr.getInt("C13_Seq") + 1;
            }

        } catch (DB_Exception e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (dbr != null) {
                dbr.close();
                dbr = null;
            }
            if (dbs != null) {
                dbs.close();
                dbs = null;
            }
        }
        return intNextSeq;
    }	
}
