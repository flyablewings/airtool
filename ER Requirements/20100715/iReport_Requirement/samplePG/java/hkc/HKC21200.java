package hkc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;
import java.io.File;

import com.hokukou.HK_Cnst;
import com.hokukou.data.HK_Data;
import com.hokukou.database.*;
import com.hokukou.report.*;
import com.project.PJ_Cnst;
import com.project.dbread.C13_Log;

import net.sf.jasperreports.engine.JRException;
import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;

@RemotingService
/*
********************************************************************************
================================================================================
   ホクコウ共通プログラム
================================================================================
   Program Number  : HKC21200.java
   Program Name    : 帳票作成
   Program Date    : 2008/02/13
   Programmer      : T.OHORI
   
===< Update History >===========================================================
  2008/10/26:基本仕様を変更とcsvﾌｧｲﾙ化を施す｡							= 山本
  2009/01/13:AIR化｡														= 山本
================================================================================
********************************************************************************
*/
public class HKC21200 implements iHK_ReportOut {
	// +-----------------------------------------------------------------------+
	// |  受取パラメータの共通部分の定義                                       |
	// +-----------------------------------------------------------------------+
	private String _strA15_CD = "";
	private String _strA15_NM = "";
	private String _strSQL = "";
	private String _strOperation = "";
	private String _strReportName = "";
	private String _strUser = "";
	private String _strTerm = "";
	private String _strUpdateDate = "";
	private String _strNowDate = "";
	
	private Object[] _objReportCol = null;		//ﾚﾎﾟｰﾄの印刷項目
	private Object[] _objTableCol = null;		//ﾃｰﾌﾞﾙ/ﾋﾞｭｰの項目
	private Object[] _objSelectCol = null;		//ﾚﾎﾟｰﾄの印刷項目に対するﾃｰﾌﾞﾙ項目

	// +-----------------------------------------------------------------------+
	// |  共通変数の定義                                                       |
	// +-----------------------------------------------------------------------+
	private final String _csReportPath = "/reports/";		//ﾚﾎﾟｰﾄ定義ﾌｧｲﾙへのﾊﾟｽ

	// +-----------------------------------------------------------------------+
	// |  Excel保存&印刷用変数の定義                                           |
	// +-----------------------------------------------------------------------+
	private String _strCsvFL = "";
	private PrintReportFromCsv _prtCsvFL = null;
	private String _strSheetFL = "";
	private PrintReportFromCsv _prtSheetName = null;
	private String[] _strPrtCol = null;
	private String[] _strSheetCol = {"SheetName"};

	/*
	********************************************************************************
	*** レポートファイル情報の読込処理
	********************************************************************************
	*/
	public ArrayList readFile_List(Object[] vctInputData) {
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		try {
			String appPath = HK_Data.getRealPath() + _csReportPath;
			final File targetDirectory = new File(appPath);
			final File[] dirContents = targetDirectory.listFiles();
			
			Boolean blnExist = false;
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
	
			for(int i = 0; i < dirContents.length; i++){
				blnExist = true;
				String w = dirContents[i].getName();
				if (w.indexOf(".jasper") >= 0){
					hsmRow = new HashMap<String, String>();
					hsmRow.put("label", w);
					hsmRow.put("data", w);
					aryDBData.add(hsmRow);
				}
			}
	
			if (blnExist == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			} else {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				aryStatus.add(hsmRow);
			}
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
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
	*** テーブル情報の読込処理
	***  (データベースに登録されているテーブルとビューの一覧の取得)
	********************************************************************************
	*/
	public ArrayList readTable_List(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		Boolean blnExist = false;

		try {
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			
			DatabaseMetaData dmd = dbc.getMetaData();
			String[] Tbl_Type = {"TABLE","VIEW"};
			ResultSet rs = dmd.getTables(null, null, "%", Tbl_Type);
			String strTbl = null;
			String strTbl1 = null;

			while(rs.next()){
				blnExist = true;
				strTbl=rs.getString("TABLE_NAME") + "(" + rs.getString("TABLE_TYPE") + ")";
				strTbl1=rs.getString("TABLE_NAME");

				hsmRow = new HashMap<String, String>();
				hsmRow.put("label", strTbl.trim());
				hsmRow.put("data", strTbl1.trim());
				aryDBData.add(hsmRow);
			}
			
			if (blnExist == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			} else {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
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
	***  レポートの定義項目の取得
	********************************************************************************
	*/
	public ArrayList readReport_Column(Object[] objInputData){
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		try {
			HashMap hsmRcvData = (HashMap) objInputData[0];	
			String strReport_Name = (String)hsmRcvData.get("Report_Name");
			String[] strFields;
			
			String appPath = HK_Data.getRealPath() + _csReportPath + strReport_Name;
			strFields = PrinterReport.getFieldsStr(appPath);		//ﾚﾎﾟｰﾄ項目の取得
			
			Boolean blnExist = false;

			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
	
			for(int i = 0; i < strFields.length; i++){
				blnExist = true;
				String w = strFields[i];
				hsmRow = new HashMap<String, String>();
				hsmRow.put("Column_Name", w);
				aryDBData.add(hsmRow);
			}
	
			
			if (blnExist == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			} else {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				aryStatus.add(hsmRow);
			}
		} catch (JRException e) {
			return DB_Exception.setErrorInfo(e);
		} catch (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
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
	***  ﾃｰﾌﾞﾙまたはﾋﾞｭｰに登録されているカラム情報の読込処理
	********************************************************************************
	*/
	public ArrayList readTable_Column(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		try {
			String strTable_Name = null;
			Boolean blnExist = false;
			HashMap hsmRcvData = (HashMap) objInputData[0];	
			strTable_Name = (String)hsmRcvData.get("Table_Name");

			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);

			DatabaseMetaData dmd = dbc.getMetaData();
			ResultSet rs = dmd.getColumns(null, null, strTable_Name, "%");
			String strCol_Name = null;
			String strCol_Type = null;

			while(rs.next()){
				blnExist = true;
				strCol_Name = rs.getString("COLUMN_NAME");
				strCol_Type = rs.getString("TYPE_NAME");

				hsmRow = new HashMap<String, String>();
				hsmRow.put("Column_Name", strCol_Name.trim());
				hsmRow.put("Column_Type", strCol_Type.trim());
				aryDBData.add(hsmRow);
			}
			
			if (blnExist == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			} else {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
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
	***  帳票書式表の読込処理
	********************************************************************************
	*/
	public ArrayList readA15_RptFormat(Object[] objInputData){

		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		ArrayList<HashMap> aryDBData2 = null;
		HashMap<String, String> hsmRow = null;

		try {
			String strA15_RptFormatCD = "";
			String strTable_Name = "";
			boolean blnExist = false;
			StringBuffer stbSql = new StringBuffer();
			
			hsmReceiveData = (HashMap) objInputData[0];
			strA15_RptFormatCD = (String) hsmReceiveData.get("A15_RptFormatCD");
			
			stbSql.append("\n"+"SELECT ");
			stbSql.append("\n"+"     A15_RptFormatCD,");
			stbSql.append("\n"+"     A15_RptFormatNM,");
			stbSql.append("\n"+"     A15_TableNM,");
			stbSql.append("\n"+"     A15_iReportNM,");
			stbSql.append("\n"+"     A15_DataKB,");
			stbSql.append("\n"+"     A15_Seq,");
			stbSql.append("\n"+"     A15_ColNM,");
			stbSql.append("\n"+"     A15_ColType,");
			stbSql.append("\n"+"     A15_Sort,");
			stbSql.append("\n"+"     A15_Junjo,");
			stbSql.append("\n"+"     A15_Syurui,");
			stbSql.append("\n"+"     A15_Enzanshi,");
			stbSql.append("\n"+"     A15_Shiki,");
			stbSql.append("\n"+"     A15_Atai1,");
			stbSql.append("\n"+"     A15_Atai2,");
			stbSql.append("\n"+"     A15_ColNM,");
			stbSql.append("\n"+"     A15_RptColNM,");
			stbSql.append("\n"+"     Update_Date ");
			stbSql.append("\n"+"FROM ");
			stbSql.append("\n"+"     A15_RptFormat ");
			stbSql.append("\n"+"WHERE	");
			stbSql.append("\n"+"     A15_RptFormatCD = '"+ DB_CtrlChar.edit(strA15_RptFormatCD) + "' ");
			stbSql.append("\n"+"ORDER BY ");
			stbSql.append("\n"+"     A15_DataKB,");
			stbSql.append("\n"+"     A15_Seq ");
			stbSql.append("\n"+";");

			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			aryDBData2 = new ArrayList<HashMap>();
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);

			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();

			while(dbr.next()) {
				blnExist = true;
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
				hsmRow = new HashMap<String, String>();
				
				hsmRow.put("A15_RptFormatCD", dbr.getString("A15_RptFormatCD"));
				hsmRow.put("A15_RptFormatNM", dbr.getString("A15_RptFormatNM"));
				hsmRow.put("A15_TableNM", dbr.getString("A15_TableNM"));
				hsmRow.put("A15_iReportNM", dbr.getString("A15_iReportNM"));
				hsmRow.put("A15_DataKB", String.valueOf(dbr.getInt("A15_DataKB")));
				hsmRow.put("A15_Seq", String.valueOf(dbr.getInt("A15_Seq")));
				hsmRow.put("A15_ColNM", dbr.getString("A15_ColNM"));
				hsmRow.put("A15_ColType", dbr.getString("A15_ColType"));
				hsmRow.put("A15_Sort", String.valueOf(dbr.getInt("A15_Sort")));
				hsmRow.put("A15_Junjo", String.valueOf(dbr.getInt("A15_Junjo")));
				hsmRow.put("A15_Syurui", String.valueOf(dbr.getInt("A15_Syurui")));
				hsmRow.put("A15_Enzanshi", String.valueOf(dbr.getInt("A15_Enzanshi")));
				hsmRow.put("A15_Shiki", String.valueOf(dbr.getInt("A15_Shiki")));
				hsmRow.put("A15_Atai1", dbr.getString("A15_Atai1"));
				hsmRow.put("A15_Atai2", dbr.getString("A15_Atai2"));
				hsmRow.put("A15_RptColNM", dbr.getString("A15_RptColNM"));

				hsmRow.put("UpdateDate", dbr.getString("Update_Date"));

				strTable_Name = dbr.getString("A15_TableNM");
				aryDBData.add(hsmRow);
			}
			if (blnExist == false) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				aryStatus.add(hsmRow);
			}else{
				// +-------------------------------------------+ ﾃｰﾌﾞﾙ列名の取得｡
				DatabaseMetaData dmd = dbc.getMetaData();
				ResultSet rs = dmd.getColumns(null, null, strTable_Name, "%");
				String strCol_Name = null;
				String strCol_Type = null;
				while(rs.next()){
					blnExist = true;
					strCol_Name = rs.getString("COLUMN_NAME");
					strCol_Type = rs.getString("TYPE_NAME");

					hsmRow = new HashMap<String, String>();
					hsmRow.put("Column_Name", strCol_Name.trim());
					hsmRow.put("Column_Type", strCol_Type.trim());
					aryDBData2.add(hsmRow);
				}
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
		aryDBData2.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.add(aryDBData2);
		arySendData.trimToSize();
		return arySendData;
	}
	
	/*
	********************************************************************************
	***  帳票書式表への書込処理＜メイン＞
	********************************************************************************
	*/
	public ArrayList storeA15_RptFormat(Object[] objInputData) {
		DB_Connection dbc = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		HashMap<String, String> hsmRow = null;
		C13_Log drdC13_Log = null;

		try {
			int intResult = 0;
			String strMsg = "";
			HashMap hsmRcvData = (HashMap) objInputData[0];
		
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			dbc.setAutoCommit(false); 			// <-----* Transaction Start !

			HashMap hstRows2 = (HashMap) objInputData[0];
			_strA15_CD = (String) hstRows2.get("A15_RptFormatCD");
			_strA15_NM = (String) hstRows2.get("A15_RptFormatNM");
			_strTerm = (String) hstRows2.get("Term");
			_strUser = (String) hstRows2.get("User");
			
			_strUpdateDate = (String) hstRows2.get("UpdateDate");
			_strNowDate = String.valueOf(dbc.getNow());
			
			intResult = 0;
			if (String.valueOf(hsmRcvData.get("ReadState")).equals("afterexist")) {
				intResult = _deleteA15_RptFormat(dbc, _strA15_CD);
			}
			if (intResult == 0){
				for (int intIndex = 0; intIndex < objInputData.length; intIndex++) {
					HashMap hstRows = (HashMap) objInputData[intIndex];
					if (_insertA15_RptFormat(dbc, hstRows) != 0) {
						intResult = -1;
						break;
					}
				}
			}
			
			if (intResult == 0) {
				strMsg = "";
				strMsg += "[保存] 帳票書式表に";
				strMsg += " <コード> " + _strA15_CD;
				strMsg += " <帳票名> " + _strA15_NM; 
				if (String.valueOf(hsmRcvData.get("ReadState")).equals("afternew")) {
					strMsg += " を新規登録しました｡";
				} else {
					strMsg += " を更新しました｡";
				}
				drdC13_Log = new C13_Log();
				drdC13_Log.store(dbc, _strUser, _strTerm, HK_Data.getClassName(this), strMsg);
				
				dbc.commit(); 					// <-----* Transaction Commit
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_OK);
				aryStatus.add(hsmRow);
			} else {
				dbc.rollback(); 				// <-----* Rollback Commit
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NG);
				aryStatus.add(hsmRow);

				strMsg = "該当データは、他のクライアントで変更されましたので、" + "\n"
						+ "書き換えが行えませんでした。(N05_Examination Update)";
				hsmRow.put("info", strMsg);
				aryStatus.add(hsmRow);
			}
		} catch (DB_Exception e) {
			if (dbc != null){ dbc.rollback(); }		// <-----* Rollback Commit
			return DB_Exception.setErrorInfo(e);
		} catch (Exception e) {			
			if (dbc != null){ dbc.rollback(); }		// <-----* Rollback Commit
			return DB_Exception.setErrorInfo(e);
		} finally {
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		arySendData.add(aryStatus);
		arySendData.trimToSize();
		return arySendData;
	}
	/*
	********************************************************************************
	***  帳票書式表の削除処理
	********************************************************************************
	*/
	public ArrayList deleteA15_RptFormat(Object[] objInputData) {
		DB_Connection dbc = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		HashMap<String, String> hsmRow = null;
		C13_Log drdC13_Log = null;

		try {
			String strMsg = "";
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			dbc.setAutoCommit(false); 			// <-----* Transaction Start !

			HashMap hsmRcvData = (HashMap) objInputData[0];
			_strA15_CD = (String) hsmRcvData.get("A15_RptFormatCD");
			_strA15_NM = (String) hsmRcvData.get("A15_RptFormatNM");
			_strUser = (String) hsmRcvData.get("User");
			_strTerm = (String) hsmRcvData.get("Term");
			_strUpdateDate = (String) hsmRcvData.get("UpdateDate");
			
			_deleteA15_RptFormat(dbc, _strA15_CD);
			
			strMsg = "";
			strMsg += "[削除] 帳票書式表の";
			strMsg += " <コード> " + _strA15_CD;
			strMsg += " <帳票名> " + _strA15_NM; 
			strMsg += " を削除しました｡";
			drdC13_Log = new C13_Log();
			drdC13_Log.store(dbc, _strUser, _strTerm, HK_Data.getClassName(this), strMsg);
			
			dbc.commit(); 					// <-----* Transaction Commit
			hsmRow = new HashMap<String, String>();
			hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_OK);
			aryStatus.add(hsmRow);
		} catch (DB_Exception e) {
			if (dbc != null){ dbc.rollback(); }		// <-----* Rollback Commit
			return DB_Exception.setErrorInfo(e);
		} catch (Exception e) {			
			if (dbc != null){ dbc.rollback(); }		// <-----* Rollback Commit
			return DB_Exception.setErrorInfo(e);
		} finally {
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		arySendData.add(aryStatus);
		arySendData.trimToSize();
		return arySendData;
	}
	
	// +---------------------------------------------------------------------------+
	// |  帳票書式表の書込処理                                                     |
	// +---------------------------------------------------------------------------+
	private int _insertA15_RptFormat(
				DB_Connection dbc, 
				HashMap hsmRcvData
		) throws DB_Exception {
		
		DB_Statement dbs = null;
		int intResult;

		try{
			StringBuffer stbSql = new StringBuffer();
			String strTerm = (String) hsmRcvData.get("Term");
			String strUser = (String) hsmRcvData.get("User");
			
			stbSql.append("\n"+"INSERT INTO ");
			stbSql.append("\n"+"   A15_RptFormat ");
			stbSql.append("\n"+"   (");
			stbSql.append("\n"+"        Create_Date,");
			stbSql.append("\n"+"        Create_Term,");	
			stbSql.append("\n"+"        Create_User,");
			stbSql.append("\n"+"        Update_Date,");
			stbSql.append("\n"+"        Update_Term,");
			stbSql.append("\n"+"        Update_User,");		
			//stbSql.append("\n"+"	    Update_Count,");
	
			stbSql.append("\n"+"        A15_RptFormatCD,");
			stbSql.append("\n"+"        A15_RptFormatNM,");
			stbSql.append("\n"+"        A15_TableNM,");
			stbSql.append("\n"+"        A15_iReportNM,");
			stbSql.append("\n"+"        A15_DataKB,");
			stbSql.append("\n"+"        A15_Seq,");
			stbSql.append("\n"+"        A15_ColNM,");
			stbSql.append("\n"+"        A15_ColType,");
			stbSql.append("\n"+"        A15_Sort,");
			stbSql.append("\n"+"        A15_Junjo,");
			stbSql.append("\n"+"        A15_Syurui,");
			stbSql.append("\n"+"        A15_Enzanshi,");
			stbSql.append("\n"+"        A15_Shiki,");
			stbSql.append("\n"+"        A15_Atai1,");
			stbSql.append("\n"+"        A15_Atai2,");
			stbSql.append("\n"+"        A15_RptColNM ");
			stbSql.append("\n"+"    )");
			stbSql.append("\n"+"    VALUES");
			stbSql.append("\n"+"    (");
			stbSql.append("\n"+"        '" + _strNowDate + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit(strTerm) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit(strUser) + "',");
			stbSql.append("\n"+"        '" + _strNowDate + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit(strTerm) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit(strUser) + "',");
			//stbSql.append("\n"+"        0,");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_RptFormatCD")) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_RptFormatNM")) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_TableNM")) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_iReportNM")) + "',");
			stbSql.append("\n"+"         " + String.valueOf(hsmRcvData.get("A15_DataKB")) + ",");
			stbSql.append("\n"+"         " + String.valueOf(hsmRcvData.get("A15_Seq")) + ",");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_ColNM")) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_ColType")) + "',");
			stbSql.append("\n"+"         " + String.valueOf(hsmRcvData.get("A15_Sort")) + ",");
			stbSql.append("\n"+"         " + String.valueOf(hsmRcvData.get("A15_Junjo")) + ",");
			stbSql.append("\n"+"         " + String.valueOf(hsmRcvData.get("A15_Syurui")) + ",");
			stbSql.append("\n"+"         " + String.valueOf(hsmRcvData.get("A15_Enzanshi")) + ",");
			stbSql.append("\n"+"         " + String.valueOf(hsmRcvData.get("A15_Shiki")) + ",");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_Atai1")) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_Atai2")) + "',");
			stbSql.append("\n"+"        '" + DB_CtrlChar.edit((String) hsmRcvData.get("A15_RptColNM")) + "' ");
			stbSql.append("\n"+"	)");
			stbSql.append("\n"+";");

			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();
		} catch (DB_Exception e) {
			throw e;
		} finally{
		    if (dbs != null){ dbs.close();  dbs = null; }		   
		}
		if (intResult == 0) {
			return -1;
		}
		return 0;
	}
	
	// +---------------------------------------------------------------------------+
	// |  帳票書式表の削除サブルーチン処理　　　　　　　　　　　                   |
	// +---------------------------------------------------------------------------+
	private int _deleteA15_RptFormat(
				DB_Connection dbc, 
				String strA15_RptFormatCD
		) throws Exception {

		DB_Statement dbs = null;
		int intResult;
		
		try{
			StringBuffer stbSql = new StringBuffer();
	
			stbSql.append("\n"+"DELETE ");
			stbSql.append("\n"+"FROM ");
			stbSql.append("\n"+"     A15_RptFormat ");
			stbSql.append("\n"+"WHERE ");
			stbSql.append("\n"+"     A15_RptFormatCD = '" + DB_CtrlChar.edit(strA15_RptFormatCD) + "'");
			stbSql.append("\n"+" AND Update_Date = '" + _strUpdateDate + "'");
			stbSql.append("\n"+";");
		
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();

		} catch (DB_Exception e) {
			throw e ;
		} catch (Exception e) {
			throw e ;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		if (intResult == 0) {
			return -1;
		}
		return 0;
	}
	
	/*
	********************************************************************************
	***  条件節に使用する値のコントロールキャラクタ編集する処理
	********************************************************************************
	*/
	public ArrayList Atai_Edit(Object[] vctInputData) {
		HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		try {
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
	
			String w;
			String w1;
			
			for(int i = 0; i < vctInputData.length; i++){
				hsmReceiveData = (HashMap) vctInputData[i];	
	
				w = DB_CtrlChar.edit(String.valueOf(hsmReceiveData.get("Atai1")));
				w1 = DB_CtrlChar.edit(String.valueOf(hsmReceiveData.get("Atai2")));
	
				hsmRow = new HashMap<String, String>();
				hsmRow.put("Atai1_Edit", w);
				hsmRow.put("Atai2_Edit", w1);
				aryDBData.add(hsmRow);
			}
	
			hsmRow = new HashMap<String, String>();
			hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_OK);
			aryStatus.add(hsmRow);

		} catch (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
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
	*** 印刷/Excel保存時のcsvファイルの作成処理
	********************************************************************************
	*/
	public ArrayList create_csvFile(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;

		try {
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			
			HashMap hsmRcvData = (HashMap)objInputData[0];
			_strA15_CD = (String)hsmRcvData.get("A15_RptFormatCD");			//帳票ｺｰﾄﾞ
			_strA15_NM = (String)hsmRcvData.get("A15_RptFormatNM");			//帳票名
			_strSQL = (String)hsmRcvData.get("SQL");						//読込SQL文
			_strOperation = (String)hsmRcvData.get("Operation");			//印刷oeExcel保存
			_strUser = (String)hsmRcvData.get("User");
			_strTerm = (String)hsmRcvData.get("Term");
			_objReportCol = (Object[])hsmRcvData.get("aryReportCol");		//ﾚﾎﾟｰﾄの印刷項目
			_objTableCol = (Object[])hsmRcvData.get("aryTableCol");			//ﾃｰﾌﾞﾙ/ﾋﾞｭｰの項目
			_objSelectCol = (Object[])hsmRcvData.get("arySelectCol");		//ﾚﾎﾟｰﾄの印刷項目に対するﾃｰﾌﾞﾙ項目
			
			// +--------------------------------------------------+ csv項目名の設定
			int intLen = _objReportCol.length;
			_strPrtCol = new String[intLen+1];
			for(int i=0; i<_objReportCol.length; i++){
				HashMap hsmRows = (HashMap) _objReportCol[i];
				String strWk = String.valueOf(hsmRows.get("Column_Name"));
				_strPrtCol[i] = strWk;
			}
			
			_prtCsvFL = new PrintReportFromCsv(_strPrtCol);
			_strCsvFL = _prtCsvFL.createCsvFileName("");
			if (_strOperation.equals("excel")){
				// +--------------------+ Excel保存時にSheet用Csvﾌｧｲﾙを作成する｡
				String strPerFix = _prtCsvFL.getFilePrefix();
				_prtSheetName =  new PrintReportFromCsv(_strSheetCol);
				_strSheetFL = _prtSheetName.createCsvFileName(strPerFix + "_SheetName");
			}

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			
			// +--------------------------------------------------+ SQL文の構成
			StringBuffer stbSql = new StringBuffer();
			stbSql.append("\n"+"SELECT ");
			int iMax = 0;
			for(int i=0; i<_objTableCol.length; i++){
				HashMap hsmRows = (HashMap) _objTableCol[i];
				String strWk = String.valueOf(hsmRows.get("Column_Name"));
				if (strWk.length() != 0){ iMax = i; }
			}
			for(int i=0; i<_objTableCol.length; i++){
				HashMap hsmRows = (HashMap) _objTableCol[i];
				String strWk = String.valueOf(hsmRows.get("Column_Name"));
				if (strWk.length() != 0){
					if (i != iMax){
						stbSql.append("\n"+"     " + strWk + ", ");
					}else{
						stbSql.append("\n"+"     " + strWk + " ");
					}
				}
			}
			stbSql.append(_strSQL);				// 受取SQL文を接続する｡

			// +--------------------------------------------------+ 読込処理
			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();

			boolean blnExist = false;
			while(dbr.next()) {
				blnExist = true;
				hsmRow = new HashMap<String, String>();	
				for(int i=0; i<_objReportCol.length; i++){
					HashMap hsmRe = (HashMap) _objReportCol[i];
					HashMap hsmSe = (HashMap) _objSelectCol[i];
					String strRe = String.valueOf(hsmRe.get("Column_Name")).trim();
					String strSe = String.valueOf(hsmSe.get("SelectCol")).trim();
					if (strSe.length() != 0){
						hsmRow.put(strRe, dbr.getString(strSe));
					}
				}
				_prtCsvFL.setData(hsmRow);
			}
			// +--------------------------------------------------+ 終了情報の設定
			if (blnExist == true) {
				if (_strOperation.equals("excel")){
					// +-----+ Excel保存の場合の処理にｼｰﾄ名の設定を行う｡
					hsmRow = new HashMap<String, String>();	
					String strWk = "";
					strWk += _strA15_NM;
					hsmRow.put("SheetName",  HK_Data.Format(strWk, -30).trim());
					_prtSheetName.setData(hsmRow);
				}
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_OK);
				aryStatus.add(hsmRow);
				
				hsmRow = new HashMap<String, String>();
				hsmRow.put("csvFileName", _strCsvFL);
				hsmRow.put("SheetName", _strSheetFL);
				aryDBData.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません｡");
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
		    if (_prtCsvFL != null){ _prtCsvFL.close();  _prtCsvFL = null; }
		    if (_prtSheetName != null){ _prtSheetName.close();  _prtSheetName = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData = new ArrayList<ArrayList>();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	
	/*
	********************************************************************************
	***  印刷の実行処理
	********************************************************************************
	*/
	public PrinterReport getPrinterReportRes(Map mapCd) {
		DB_Connection dbc = null;
		PrinterReport prrReport = new PrinterReport();
		C13_Log drdC13_Log = new C13_Log();
		
		try{
			_strA15_CD = ((String[])mapCd.get("A15_RptFormatCD"))[0];		//帳票ｺｰﾄﾞ
			_strA15_NM = ((String[])mapCd.get("A15_RptFormatNM"))[0];		//帳票名
			_strCsvFL = ((String[]) mapCd.get("csvFileName"))[0];			//csvﾌｧｲﾙ名
			_strSheetFL = ((String[]) mapCd.get("SheetName"))[0];			//ｼｰﾄ名ﾌｧｲﾙ
			_strReportName = ((String[]) mapCd.get("ReportName"))[0];		//ﾚﾎﾟｰﾄ名
			_strUser = ((String[]) mapCd.get("User"))[0];
			_strTerm = ((String[]) mapCd.get("Term"))[0];
			
			String strReportFile = _csReportPath + _strReportName;

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			
			prrReport = new PrinterReport();
			prrReport.setPrintFromCsv(true);
			prrReport.setCsvFile(_strCsvFL);
			prrReport.setParameter(null);
			prrReport.setStyleFile(strReportFile);
			
			String strMes = "";
			strMes += "[印刷] <帳票ｺｰﾄﾞ> " + _strA15_CD;
            strMes += " <帳票名> " + _strA15_NM; 
            strMes += " を印刷しました。";
			
			drdC13_Log.store(dbc, _strUser, _strTerm, HK_Data.getClassName(this), strMes);
			
		} catch (DB_Exception e) {
		} catch (Exception ex) {
		} finally {
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		return prrReport;
	}

	/*
	********************************************************************************
	***  Excelファイルの出力処理
	********************************************************************************
	*/
	public ExcelReport getExcelReportRes(Map mapCd) {
		DB_Connection dbc = null;
		ExcelReport eclReport = new ExcelReport();
		C13_Log drdC13_Log = new C13_Log();
		
		try{
			_strA15_CD = ((String[])mapCd.get("A15_RptFormatCD"))[0];		//帳票ｺｰﾄﾞ
			_strA15_NM = ((String[])mapCd.get("A15_RptFormatNM"))[0];		//帳票名
			_strCsvFL = ((String[]) mapCd.get("csvFileName"))[0];			//csvﾌｧｲﾙ名
			_strSheetFL = ((String[]) mapCd.get("SheetName"))[0];			//ｼｰﾄ名ﾌｧｲﾙ
			_strReportName = ((String[]) mapCd.get("ReportName"))[0];		//ﾚﾎﾟｰﾄ名
			_strUser = ((String[]) mapCd.get("User"))[0];
			_strTerm = ((String[]) mapCd.get("Term"))[0];
			
			String strReportFile = _csReportPath + _strReportName;

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
		
			eclReport = new ExcelReport();
			eclReport.setExcelFromCsv(true);
			eclReport.setCsvFile(_strCsvFL);
			eclReport.setParameter(null);
			eclReport.setStyleFile(strReportFile);
			eclReport.IS_ONE_PAGE_PER_SHEET = true;
			eclReport.setSheetNameFile(_strSheetFL);

			String strMes = "";
			strMes += "[Excel保存] <帳票ｺｰﾄﾞ> " + _strA15_CD;
            strMes += " <帳票名> " + _strA15_NM; 
            strMes += " を保存しました。";
			
			drdC13_Log.store(dbc, _strUser, _strTerm, HK_Data.getClassName(this), strMes);
			
		} catch (DB_Exception e) {
		} catch (Exception ex) {
		} finally {
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		return eclReport;
	}

	/*
	********************************************************************************
	*** 入力データチェック
	********************************************************************************
	*/
	public ArrayList checkInputData(Object[] objInputData) {
		DB_Connection dbc = null;
		HashMap hsmReceiveData;

		try {
			dbc = new DB_Connection();
			hsmReceiveData = (HashMap) objInputData[0];

			dbc.getConnection(PJ_Cnst.CNT_INFO);
			//dbc.getConnection(PJ_Cnst.CNT_INFO_POST);
			return _checkData(hsmReceiveData, dbc);
		} catch (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally {
			if (dbc != null){ dbc.close();  dbc = null; }
		}
	}
	
	// +---------------------------------------------------------------------------+
	// |  入力データチェックルーチン群                                             |
	// +---------------------------------------------------------------------------+
	private ArrayList<ArrayList> _checkData(
				HashMap hsmReceiveData,
				DB_Connection dbc
		) throws Exception {
		
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		//HashMap<String, String> hsmRow = null;
		//String strControlName;
		//String strControlData;

		arySendData = new ArrayList<ArrayList>();
		aryStatus = new ArrayList<HashMap>();
		aryDBData = new ArrayList<HashMap>();

		/**
		strControlName = hsmReceiveData.get("ControlName").toString();
		strControlData = hsmReceiveData.get("ControlData").toString();
		if (strControlName.equals("N02_TestCD_S")){
			
		}
		*/
		
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	/*
	********************************************************************************
	***  レポートの標準csvﾌｧｲﾙ出力
	********************************************************************************
	*/
	public CsvReport getCsvReportRes(Map map) {
		// TODO Auto-generated method stub
		return null;
	}
}
