package hkc;

import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;

import com.hokukou.file.IFile;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;

@RemotingService
/*
 ********************************************************************************
 ================================================================================
   ホクコウ共通プログラム
 ================================================================================
   Program Number  : HKC31301.java
   Program Name    : 銀行データファイルの形式チェック
   Program Date    : 2007/10/24
   Programmer      : 山本
 
 ===< Update History >===========================================================
   2008/09/18:エラー処理の強化｡											= 山 本 
   2009/01/13:AIR化｡													= 山 本 
 ================================================================================
 ********************************************************************************
 */
public class HKC31301 implements IFile {
	/* +-----------------------------------------------------------------------+ */
	/* | 読込用のBUffer定義                                                    | */
	/* +-----------------------------------------------------------------------+ */

	// +---------------------------------+ データ情報用
	private String _strInfo = "";
	
	// +---------------------------------+ ファイル操作情報用
	private ServletInputStream _FileInStream;
	private ArrayList<Object>   _FileSendData = null;
	private ServletOutputStream _FileOutStream;
	private ObjectOutputStream _FileOut;
    private byte[] _FileByteData = null;

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
			// *---------------------------------------* 受取ﾊﾟﾗﾒｰﾀの保存
			//Map mapPara = request.getParameterMap();
			
			// *---------------------------------------* 読込処理
			_FileInStream = request.getInputStream();
			_FileByteData = new byte[1024];
		    _FileOutStream = response.getOutputStream();
			_FileOut = new ObjectOutputStream(_FileOutStream);
			String strBuf;
			String strWk;
			int intCnt = 0;
			
			int i = -1;
			while ((i = _FileInStream.readLine(_FileByteData, 0, 1024)) != -1){
				strBuf = new String(_FileByteData, 0, i);
				
				intCnt = intCnt + 1;
				if (intCnt != 1){
					if (strBuf.length() != 21){
						_strInfo = "銀行データの桁数が違います。";
						break;
					}
					strWk = strBuf.substring(0, 4);
					if (_check_number(strWk) == false){
						_strInfo = "銀行コードに数字以外の文字が含まれています。";
						break;
					}
				}
			}
			if (intCnt < 2){
				_strInfo = "銀行データの内容が０件です。";
			}
			
			// *---------------------------------------* 終了情報の設定
			_FileSendData = new ArrayList<Object>();
			HashMap<String, String> hsmStatus = new HashMap<String, String>();

			if (_strInfo == ""){
				hsmStatus.put(com.hokukou.HK_Cnst.RESULT_STATUS, com.hokukou.HK_Cnst.RESULT_OK);
				hsmStatus.put(com.hokukou.HK_Cnst.RESULT_INFO, "");
			}else{
				hsmStatus.put(com.hokukou.HK_Cnst.RESULT_STATUS, com.hokukou.HK_Cnst.RESULT_NG);
				hsmStatus.put(com.hokukou.HK_Cnst.RESULT_INFO, _strInfo);
			}
			_FileSendData.add(hsmStatus);
	        _FileOut.writeObject(_FileSendData);
	        _FileOut.flush();
	        _FileOut.close();
	        
		} catch (Exception e) {
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
	/* | 数字のチェック                                                        | */
	/* +-----------------------------------------------------------------------+ */
	private boolean _check_number(String strIn) throws Exception {
		try{
			boolean blnCheck = true;
			int intLen = strIn.length();
			String strWk = "";
			
			for (int i = 0; i < intLen ; i++){
				strWk = strIn.substring(i, i + 1);
				if (strWk.compareTo("0") < 0){
					blnCheck = false;
					break;
				}
				if (strWk.compareTo("9") > 0){
					blnCheck = false;
					break;
				}
			}
			return blnCheck;
		} catch (Exception e) {
			throw e ;
		} finally {
		}
	}
}
