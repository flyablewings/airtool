package hkc;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.hokukou.data.HK_Data;
import com.hokukou.database.*;
import com.hokukou.file.IFile;
import com.project.PJ_Cnst;
import com.project.dbread.*;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;

@RemotingService
/*
 ********************************************************************************
 ================================================================================
   ホクコウ共通プログラム
 ================================================================================
   Program Number  : HKC31304.java
   Program Name    : 金融機関データ受入(銀行支店)
   Program Date    : 2007/10/24
   Programmer      : 山本
 
 ===< Update History >===========================================================
  2008/09/18:エラー処理の強化｡											= 山 本 
  2009/01/13:AIR化｡														= 山 本 
  2009/07/03:一括削除をやめて書替機能を追加｡							= 山 本 
 ================================================================================
 ********************************************************************************
 */
public class HKC31304 implements IFile {
	/* +-----------------------------------------------------------------------+ */
	/* | 読込用のBUffer定義                                                    | */
	/* +-----------------------------------------------------------------------+ */
	private DB_Connection _dbcMsfa = null;     // 読込用
	private DB_Connection _dbcMsfa2 = null;    // 書込用
	
	private String _strUser;
	private String _strTerm;
	private String _strDateTime;

	// +---------------------------------+ 引数用エリア
	private String _strInFile;

	// +---------------------------------+ データ情報用
	private String _strBuf[] = new String[20];
	
	// +---------------------------------+ ファイル操作情報用
	private ServletInputStream _FileInStream;
	private ArrayList<Object>   _FileSendData = null;
	private ServletOutputStream _FileOutStream;
	private ObjectOutputStream _FileOut;
    byte[] _FileByteData = null;
	
	/*
	********************************************************************************
	*** アップローデイング処理
	********************************************************************************
	*/	
	public void doUploading(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		//response.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("shift_jis");
		
		try{
			// *---------------------------------------* ﾃﾞｰﾀﾍﾞｰｽ ｾｯｼｮﾝの接続
			_dbcMsfa = new DB_Connection();
			_dbcMsfa.getConnection(PJ_Cnst.CNT_INFO);
			_dbcMsfa2 = new DB_Connection();
			_dbcMsfa2.getConnection(PJ_Cnst.CNT_INFO);
			_dbcMsfa2.setAutoCommit(false);        // Transaction Start !
			
			// *---------------------------------------* 受取ﾊﾟﾗﾒｰﾀの保存
			Map mapPara = request.getParameterMap();
			_strInFile = ((String[]) mapPara.get("BankFile"))[0];
			_strUser = ((String[]) mapPara.get("User"))[0];
			_strTerm = ((String[]) mapPara.get("Term"))[0];
			
			// *---------------------------------------* 読込処理
			_FileInStream = request.getInputStream();
			_FileByteData = new byte[1024];
		    _FileOutStream = response.getOutputStream();
			_FileOut = new ObjectOutputStream(_FileOutStream);
			String strBuf;
			
			_strDateTime = _dbcMsfa2.getNow().toString();
			
			int i = -1;
			int intCnt = 0;
			
			while ((i = _FileInStream.readLine(_FileByteData, 0, 1024)) != -1){
				strBuf = new String(_FileByteData, 0, i);
				if (strBuf == ""){ break; }
				intCnt = intCnt + 1;
				if (intCnt != 1){
					_save_Rec(strBuf);                 		// １行を分割する
					
					if (_read_A13_Branch() == false){
						_insert_A13_Branch();              // 銀行支店表への書込み
					}else{
						_update_A13_Branch();              // 銀行支店表への書替
					}
				}
			}
			
			C13_Log drdC13_Log = new C13_Log();
			String strMsg;
			strMsg = "[金融機関データの受入処理]" +
				" <銀行データ> " + 
				" <受入ﾌｧｲﾙ名> " + _strInFile +
				" <件数> " + String.valueOf(intCnt-1) +
				" を受入ました。";
			drdC13_Log.store(_dbcMsfa2,
						_strUser,
						_strTerm, 
						"HKC31300",
						strMsg );
			_dbcMsfa2.commit(); // Transaction Commit
			// *---------------------------------------* 終了情報の設定
			_FileSendData = new ArrayList<Object>();
			HashMap<String, String> hsmStatus = new HashMap<String, String>();

			hsmStatus.put(com.hokukou.HK_Cnst.RESULT_STATUS, com.hokukou.HK_Cnst.RESULT_OK);
			hsmStatus.put(com.hokukou.HK_Cnst.RESULT_INFO, "");

			_FileSendData.add(hsmStatus);
	        _FileOut.writeObject(_FileSendData);
	        _FileOut.flush();
	        _FileOut.close();
	        
		} catch (DB_Exception e) {
			// ##### Insert Start 2008/09/18 #####
			if (_dbcMsfa2 != null){ _dbcMsfa2.rollback(); }
			_FileSendData = new ArrayList<Object>();
			HashMap<String, String> hsmStatus = new HashMap<String, String>();
			hsmStatus.put(com.hokukou.HK_Cnst.RESULT_STATUS, com.hokukou.HK_Cnst.RESULT_ERROR);
			hsmStatus.put(com.hokukou.HK_Cnst.RESULT_INFO, e.getAllMessage());
			_FileSendData.add(hsmStatus);

			try{
		        _FileOut.writeObject(_FileSendData);
		        _FileOut.flush();
		        _FileOut.close();
			} catch (Exception ex) {
			} finally{
			}
		} catch (Exception e) {
			if (_dbcMsfa2 != null){ _dbcMsfa2.rollback(); }
			// ##### Insert Start 2008/09/18 #####
			_FileSendData = new ArrayList<Object>();
			HashMap<String, String> hsmStatus = new HashMap<String, String>();
			hsmStatus.put(com.hokukou.HK_Cnst.RESULT_STATUS, com.hokukou.HK_Cnst.RESULT_ERROR);
			hsmStatus.put(com.hokukou.HK_Cnst.RESULT_INFO, e.getMessage());
			_FileSendData.add(hsmStatus);

			try{
		        _FileOut.writeObject(_FileSendData);
		        _FileOut.flush();
		        _FileOut.close();
			} catch (Exception ex) {
			} finally{
			}
			// ##### Insert Start 2008/09/18 #####
		} finally{
			if (_dbcMsfa != null){ _dbcMsfa.close();  _dbcMsfa = null; }	
			if (_dbcMsfa2 != null){ _dbcMsfa2.close();  _dbcMsfa2 = null; }	
		}
	}

	/*
	********************************************************************************
	*** ダウンローディング処理
	********************************************************************************
	*/	
	public void doDownloading(HttpServletRequest request, HttpServletResponse response) {
		
	}
	/* +-----------------------------------------------------------------------+ */
	/* | １レコードの保存                                                      | */
	/* +-----------------------------------------------------------------------+ */
	private void _save_Rec(String strBuf) throws Exception {
		int i;
		
		try{
			for (i = 0; i<20; i++){	_strBuf[i] = ""; }
			_strBuf[0] = strBuf.substring(0, 4);
			_strBuf[1] = strBuf.substring(4, 7);
			_strBuf[2] = strBuf.substring(7, 22);
		} catch (Exception e) {
			throw e ;
		} finally {
		}
	}
	/* +-----------------------------------------------------------------------+ */
	/* | 銀行表の読込データの有無のチェックを行う処理                          | */
	/* +-----------------------------------------------------------------------+ */
	private  boolean _read_A13_Branch() throws Exception {
		DB_Statement dbs = null;
		DB_ResultSet dbr=null;
		StringBuffer stbSql = new StringBuffer();
		boolean blnChk = false;
		
		try {
			
			stbSql.append("\n"+"SELECT ");
			stbSql.append("\n"+"     A12_BankCD,");
			stbSql.append("\n"+"     A13_BranchCD,");
			stbSql.append("\n"+"     A13_BranchKN ");
			stbSql.append("\n"+"FROM ");
			stbSql.append("\n"+"     A13_BankBranch ");
			stbSql.append("\n"+"WHERE");
			stbSql.append("\n"+"     A12_BankCD = " + _strBuf[0] + " ");
			stbSql.append("\n"+" AND A13_BranchCD = " + _strBuf[1] + " ");
			stbSql.append("\n"+";");
			
			dbs = _dbcMsfa.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
			if (dbr.next()){
				blnChk = true;
			}
		} catch (DB_Exception e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		return blnChk;
	}
	/* +-----------------------------------------------------------------------+ */
	/* | 銀行支店表への書込み処理                                              | */
	/* +-----------------------------------------------------------------------+ */
	private void _insert_A13_Branch() throws Exception {
		DB_Statement dbs = null;
		StringBuffer stbSql = new StringBuffer();
		
		try {
			stbSql.append("\n" + "INSERT INTO");
			stbSql.append("\n" + "    A13_BankBranch ");
			stbSql.append("\n" + "(");
			stbSql.append("\n" + "    Create_Date,");
			stbSql.append("\n" + "    Create_Term,");
			stbSql.append("\n" + "    Create_User,");
			stbSql.append("\n" + "    Update_Date,");
			stbSql.append("\n" + "    Update_Term,");
			stbSql.append("\n" + "    Update_User,");
			//stbSql.append("\n" + "    Update_Count,");
			stbSql.append("\n" + "    A12_BankCD,");
			stbSql.append("\n" + "    A13_BranchCD,");
			stbSql.append("\n" + "    A13_BranchNM,");
			stbSql.append("\n" + "    A13_BranchKN,");
			stbSql.append("\n" + "    A13_BranchRN ");
			stbSql.append("\n" + ")");
			stbSql.append("\n" + "VALUES");
			stbSql.append("\n" + "(");
			stbSql.append("\n" + "    '" + _strDateTime + "',");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(_strTerm) + "',");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(_strUser) + "',");
			stbSql.append("\n" + "    '" + _strDateTime + "',");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(_strTerm) + "',");
			stbSql.append("\n" + "    '" + DB_CtrlChar.edit(_strUser) + "',");
			//stbSql.append("\n" + "    0,");
			stbSql.append("\n" + "    '" + _strBuf[0] + "',");
			stbSql.append("\n" + "    '" + _strBuf[1] + "',");
			stbSql.append("\n" + "    '',");
			stbSql.append("\n" + "    '" + _strBuf[2] + "',");
			stbSql.append("\n" + "    '' ");
			stbSql.append("\n" + ")");
			stbSql.append("\n" + ";");

			dbs = _dbcMsfa2.prepareStatement(stbSql.toString());
			dbs.executeUpdate();

		} catch (DB_Exception e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		return ;
	}
	/* +-----------------------------------------------------------------------+ */
	/* | 銀行支店表の書替処理　　　　  　　　　　　　                          | */
	/* +-----------------------------------------------------------------------+ */
	private void _update_A13_Branch() throws Exception {
		DB_Statement dbs = null;
		StringBuffer stbSql = new StringBuffer();
		try {
			
			stbSql.append("\n"+"UPDATE ");
			stbSql.append("\n"+"     A13_BankBranch ");
			stbSql.append("\n"+"SET ");
			stbSql.append("\n"+"     Update_Date = '" + _strDateTime + "',");
			stbSql.append("\n"+"     Update_Term = '" + DB_CtrlChar.edit(_strTerm) + "',");
			stbSql.append("\n"+"     Update_User = '" + DB_CtrlChar.edit(_strUser) + "',");
			stbSql.append("\n"+"     A13_BranchKN  = '" + DB_CtrlChar.edit(_strBuf[2]) + "' ");
			stbSql.append("\n"+"WHERE ");
			stbSql.append("\n"+"     A12_BankCD = " + _strBuf[0] + " ");
			stbSql.append("\n"+" AND A13_BranchCD = " + _strBuf[1] + " ");
			stbSql.append("\n"+";");
			
			dbs = _dbcMsfa2.prepareStatement(stbSql.toString());
			dbs.executeUpdate();

		} catch (DB_Exception e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		return ;
	}
}
