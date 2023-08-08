package com.hokukou.database;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.HashMap;
import com.hokukou.HK_Cnst;

public class DB_Exception extends Exception{
	private static final long serialVersionUID = 1L;
	
	private String _strAllMessage;
	
	private static String _getErrMsg(Exception e){
		String strAllMsg;
		strAllMsg=e.getMessage();
		for (int i=0; i<e.getStackTrace().length; i++){
			strAllMsg = strAllMsg + "\n" + e.getStackTrace()[i].toString();
		}
		return strAllMsg;
	}
	
	public String getAllMessage(){
		return _strAllMessage;
	}
	
	public DB_Exception(Exception e) {
		super(e.getMessage());
		
		/***** 2008/05/27 UPDATE START *****/
		/*
		_strAllMessage=e.getMessage();
		for (int i=0; i<e.getStackTrace().length; i++){
			_strAllMessage = _strAllMessage + "\n" + e.getStackTrace()[i].toString();
		}
		*/
		_strAllMessage = _getErrMsg(e);
		/***** 2008/05/27 UPDATE E N D *****/
		
		System.out.println("--------------- com.hokukou.database.DB_Exception Constract DB_Exception(Exception e)Start ---------------");
		System.out.println(_strAllMessage);
		System.out.println("--------------- com.hokukou.database.DB_Exception Constract DB_Exception(Exception e) E n d ---------------");
	}

	public static ArrayList<ArrayList> setErrorInfo(DB_Exception e){
		
		ArrayList<ArrayList> sendAll;
		ArrayList<HashMap> sendStatus;
		HashMap<String, String> row = null;
		String strErrMsg;
		
		sendAll = new ArrayList<ArrayList>();
		sendStatus = new ArrayList<HashMap>();
		
		/***** 2008/05/27 UPDATE START *****/
		//strErrMsg = convDuplicateKeyErr(e.getMessage());
		strErrMsg = _getErrMsg(e);
		strErrMsg = convDuplicateKeyErr(strErrMsg);
		/***** 2008/05/27 UPDATE E N D *****/

		row = new HashMap<String, String>();
		row.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_ERROR);
		row.put(HK_Cnst.RESULT_INFO, strErrMsg);
		sendStatus.add(row);
		
		sendStatus.trimToSize();
		
		sendAll.add(sendStatus);
		
		return sendAll; 
	}
	
	public static ArrayList<ArrayList> setErrorInfo(Exception e){
		ArrayList<ArrayList> sendAll;
		ArrayList<HashMap> sendStatus;
		HashMap<String, String> row = null;
		String strErrMsg;
		
		sendAll = new ArrayList<ArrayList>();
		sendStatus = new ArrayList<HashMap>();
		
		/***** 2008/05/27 UPDATE START *****/
		//strErrMsg = convDuplicateKeyErr(e.getMessage());
		strErrMsg = _getErrMsg(e);
		strErrMsg = convDuplicateKeyErr(strErrMsg);
		/***** 2008/05/27 UPDATE E N D *****/
		
		row = new HashMap<String, String>();
		row.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_ERROR);
		row.put(HK_Cnst.RESULT_INFO, strErrMsg);
		sendStatus.add(row);
		
		sendStatus.trimToSize();
		
		sendAll.add(sendStatus);

		System.out.println("--------------- com.hokukou.database.DB_Exception setErrorInfo(Exception e) Start ---------------");
		System.out.println(strErrMsg);
		System.out.println("--------------- com.hokukou.database.DB_Exception setErrorInfo(Exception e) E n d ---------------");

		return sendAll; 
	}
	
	private static String convDuplicateKeyErr(String strErrMsg){
		String strBuf;
		
		if (strErrMsg == null) {
			strErrMsg = "";
		}
		
		if (strErrMsg.indexOf("duplicate key violates unique constraint")>-1) {
			strBuf = "該当データは、他のクライアントで追加されましたので、" + "\n" + 
					 "書き込みが行えませんでした。" + "\n" + "\n" + 
			         "[詳細情報]" + "\n" +  
			         strErrMsg;
		}else{
			strBuf = strErrMsg;
		}
			
		return strBuf; 
	}
	
}
