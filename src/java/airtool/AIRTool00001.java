package airtool;

import java.util.*;

import com.hokukou.HK_Cnst;
import com.hokukou.database.*;
import com.project.PJ_Cnst;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;
@RemotingService

/*
********************************************************************************
================================================================================
   AIRTool
================================================================================
   Program Number  : AIRTool00001.java
   Program Name    : パスワード入力
   Program Date    : 2009/01/05
   Programmer      : T.Ogaawa
   
===< Update History >===========================================================
   2009/01/05 : 新規作成。
================================================================================
********************************************************************************
*/

public class AIRTool00001 {

	/*
	********************************************************************************
	*** 教職員保守の読込処理
	********************************************************************************
	*/
	public ArrayList readC04_Staff(Object[] objInputData){

		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;

		String strC04_CD = "";
		boolean blnExist = false;
		StringBuffer stbSql = new StringBuffer();
		hsmReceiveData = (HashMap) objInputData[0];
		strC04_CD = (String) hsmReceiveData.get("C04_CD");

		stbSql.append("\n" + "SELECT ");
		stbSql.append("\n" + "    C04_PW ");
		stbSql.append("\n" + "FROM");
		stbSql.append("\n" + "    C04_Staff	");
		stbSql.append("\n" + "WHERE");
		stbSql.append("\n" + "    C04_CD = '" + DB_CtrlChar.edit(strC04_CD) + "'");
		stbSql.append("\n" + ";");
        
		try {

			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);

			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();

			if (dbr.next()) {
				blnExist = true;
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);

				hsmRow = new HashMap<String, String>();
				hsmRow.put("C04_PW", String.valueOf(dbr.getString("C04_PW")));
				aryDBData.add(hsmRow);
			}

			if (blnExist == false) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				aryStatus.add(hsmRow);
			}

		} catch (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}

	/*
	********************************************************************************
	*** プログラム権限表の読込処理
	********************************************************************************
	*/
	public ArrayList readC12_PgmAuth(Object[] objInputData){

		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;

		String strC04_CD = "";
		boolean blnExist = false;
		StringBuffer stbSql = new StringBuffer();
		hsmReceiveData = (HashMap) objInputData[0];
		strC04_CD = (String) hsmReceiveData.get("C04_CD");

		stbSql.append("\n" + "SELECT");
		stbSql.append("\n" + "	C12.A07_PgmCD,	");
		stbSql.append("\n" + "	C12.C12_AuthKB	");
		stbSql.append("\n" + "FROM");
		stbSql.append("\n" + "	C04_Staff	AS C04");
		stbSql.append("\n" + "LEFT JOIN");
		stbSql.append("\n" + "	C12_PgmAuth	AS C12	 ON C04.C04_CD = C12.C04_CD");
		stbSql.append("\n" + "WHERE");
		stbSql.append("\n" + "	C04.C04_CD = '" + DB_CtrlChar.edit(strC04_CD) + "'");
		stbSql.append("\n" + ";");
		
		try {

			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);

			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();


			hsmRow = new HashMap<String, String>();
			while(dbr.next()) {
				blnExist = true;
				hsmRow.put(String.valueOf(dbr.getString("A07_PgmCD")), String.valueOf(dbr.getString("C12_AuthKB")));
			}

			if (blnExist == true) {
				aryDBData.add(hsmRow);
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			} else {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				aryStatus.add(hsmRow);
			}

		} catch (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
}
 