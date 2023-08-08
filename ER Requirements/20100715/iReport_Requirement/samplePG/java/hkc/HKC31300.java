package hkc;

import java.util.ArrayList;
import java.util.HashMap;

import com.hokukou.HK_Cnst;
import com.hokukou.database.*;
import com.project.PJ_Cnst;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;

@RemotingService
/*
 ********************************************************************************
 ================================================================================
   ホクコウ共通プログラム
 ================================================================================
   Program Number  : HKC31300.java
   Program Name    : 金融機関データ受入(入力チェック用)
   Program Date    : 2007/10/24
   Programmer      : 山本
 
 ===< Update History >===========================================================
   2009/01/13:AIR化｡
 ================================================================================
 ********************************************************************************
 */
public class HKC31300 {
	/* +-----------------------------------------------------------------------+ */
	/* | 読込用のBUffer定義                                                    | */
	/* +-----------------------------------------------------------------------+ */
	DB_Connection _dbcMsfa = null;     // 読込用
	DB_Connection _dbcMsfa2 = null;    // 書込用
	
	ArrayList<HashMap>   _aryStatus = null;
	ArrayList<HashMap>   _aryDataMsfa = null; 
	ArrayList<HashMap>   _aryData = null; 
	ArrayList<ArrayList> _arySendData = null;
	
	/*
	********************************************************************************
	*** 管理表の読込処理
	********************************************************************************
	*/
	public ArrayList readA01_System(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		try{
			HashMap<String, String> hsmRow = null;
			
			_arySendData = new ArrayList<ArrayList>();
			_aryStatus = new ArrayList<HashMap>();
			_aryDataMsfa = new ArrayList<HashMap>();
			Boolean blnIsRead = false;
			
			StringBuffer stbSql = new StringBuffer();

			stbSql.append("\n" + "SELECT ");
			stbSql.append("\n" + "	  A01_SysCD,");
			stbSql.append("\n" + "	  A01_SysNM,");
			stbSql.append("\n" + "	  A01_SysVL ");
			stbSql.append("\n" + "FROM ");
			stbSql.append("\n" + "	  A01_System ");
			stbSql.append("\n" + "WHERE ");
			stbSql.append("\n" + "	  A01_SysCD = 'HKC00011' ");
			stbSql.append("\n" + "OR  A01_SysCD = 'HKC00012' ");

			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);

			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
			hsmRow = new HashMap<String, String>();
			while (dbr.next()) {
				blnIsRead = true;
				if (dbr.getString("A01_SysCD").equals("HKC00011")){
					hsmRow.put("BankFile", dbr.getString("A01_SysVL"));
				}else{
					hsmRow.put("BranchFile", dbr.getString("A01_SysVL"));
				}
				_aryDataMsfa.add(hsmRow);
			}

			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				_aryStatus.add(hsmRow);

			} else {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_NODATA);
				_aryStatus.add(hsmRow);
			}
		} catch (DB_Exception e) {
			dbc.rollback();
			return DB_Exception.setErrorInfo(e);
		} catch (Exception e) {
			dbc.rollback();
			return DB_Exception.setErrorInfo(e);
		} finally {
		    if (dbr != null) {
		        dbr.close();
		        dbr = null;
		    }
		    if (dbs != null) {
		        dbs.close();
		        dbs = null;
		    }
			if(dbc != null){
				dbc.close();
			}
		}
	
		_aryStatus.trimToSize();
		_aryDataMsfa.trimToSize();
		_arySendData.add(_aryStatus);
		_arySendData.add(_aryDataMsfa);
		_arySendData.trimToSize();

		return _arySendData;
	}
}
