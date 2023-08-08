package com.hokukou.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hokukou.HK_Cnst;
import com.hokukou.data.HK_Data;
import com.hokukou.report.HK_ReportResources;
import com.hokukou.report.PrintReport;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : FolderDownload.java
	Program Name    : 一括ダウンロード処理
	Program Date    : 2009/02/18
	Programmer      : T.Ogawa
   
===< Update History >===========================================================
	2009/02/18  : 新規作成
================================================================================
********************************************************************************
*/
public class FolderDownload extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		ServletOutputStream servletOutputStream = null;
	    
		try {
			this._execPrint(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			servletOutputStream = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(servletOutputStream);
		    oos.writeObject("server_inner_error");
			oos.flush();
			oos.writeObject(FolderDownload.getAllErrMsg(e));
			oos.flush();
			oos.close();
			servletOutputStream.flush();
			servletOutputStream.close();
		}
	}

	private void _execPrint(
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		
		Map mapMxmlParam = request.getParameterMap();
		String strTempDir = ((String[])mapMxmlParam.get("tempDir"))[0];
		String strFix = ((String[])mapMxmlParam.get("fix"))[0];
		String strClientPath = ((String[])mapMxmlParam.get("clientDir"))[0];

		//========== ２日以上前のファイルがあるときは削除(消え残りを削除) ==========
		File fileDownloadDir = new File(FolderDownload.getDownloadDirFull());
		File[] fileTempDirs = fileDownloadDir.listFiles();
		if (fileTempDirs.length > 0) {
			int intToday;
			int[] intTodays;
			GregorianCalendar cln1DayBef;

			intToday = HK_Data.parseInt(HK_Data.getDate(""));
			intTodays = HK_Data.devideDate(intToday);

			cln1DayBef = new GregorianCalendar(intTodays[0], intTodays[1]-1, intTodays[2]);
			cln1DayBef.add(Calendar.DATE, -1);
			
			for (int intIdx = 0; intIdx < fileTempDirs.length; intIdx++) {
				if (fileTempDirs[intIdx].getName().compareTo(String.valueOf(cln1DayBef.getTimeInMillis())) < 0) {
					File[] fileFiles = fileTempDirs[intIdx].listFiles();
					for (int i = 0; i < fileFiles.length; i++) {
						fileFiles[i].delete();
					}
					fileTempDirs[intIdx].delete();
				}
			}
		}
		
	    //	  ========== 仮フォルダ内の全ファイルを読込 ==========
		ArrayList<HK_ReportResources> arrFiles = new ArrayList<HK_ReportResources>();
		File fileTempDir = new File(FolderDownload.getDownloadTempDirFull(strTempDir));
		
		if (fileTempDir.isDirectory()) {
			File[] fileWKs = fileTempDir.listFiles();
			for (int i=0; i<fileWKs.length; i++) {
				if (fileWKs[i].isFile()) {
					//拡張子をチェック
					if (this._checkFix(fileWKs[i].getName(), strFix) == true) {
				 	    try {
				 	    	HK_ReportResources reportResource = new HK_ReportResources(fileWKs[i].getParent(), fileWKs[i].getName());
					 	    reportResource.setDesDir(strClientPath);
				 	    	arrFiles.add(reportResource);
				 	    } catch (FileNotFoundException e) {
				 	    	throw new ServletException("サーバー上にファイルがありません。[" + fileWKs[i].getName() + "]");
				 	    }
					}
				}
			}
		}
		arrFiles.trimToSize();

		//	  ========== ファイルの送出 ==========
 	    ServletOutputStream servletOutputStream = null;
	    try {
			servletOutputStream = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(servletOutputStream);
			
		    oos.writeObject("server_inner_ok");
			oos.flush();
			
			oos.writeObject(arrFiles);
			oos.flush();
			
			oos.close();
			servletOutputStream.flush();
			servletOutputStream.close();
	    } catch (Exception ex) {
	    	throw new ServletException(PrintReport.getAllErrMsg(ex));
	    }

	    //	  ========== 仮フォルダ内の全ファイルを削除 ==========
		if (fileTempDir.isDirectory()) {
			File[] fileWKs = fileTempDir.listFiles();
			for (int i=0; i<fileWKs.length; i++) {
				fileWKs[i].delete();
			}
			fileTempDir.delete();
		}
	}
	
	/**
	 * ダウンロードフォルダのフルパスを取得する処理
	 */
	public static String getDownloadDirFull() {
		return HK_Data.getRealPath() + "/" + HK_Cnst.CNT_FolderDownloadTempDir;
	}
	
	/**
	 * ダウンロード仮フォルダのフルパスを取得する処理
	 */
	public static String getDownloadTempDirFull(String strTempDir) {
		return FolderDownload.getDownloadDirFull() + "/" +strTempDir;
	}
	
	public static String getAllErrMsg(Exception ex) {
		String strAllMsg = "";
		strAllMsg += ex.getMessage();
		for (int i=0; i<ex.getStackTrace().length; i++){
			strAllMsg = strAllMsg + "\n" + ex.getStackTrace()[i].toString();
		}
		return strAllMsg;
	}
	
	private boolean _checkFix(String strPath, String strFix) {
		
		boolean isOK = false;
		String fix[] = strFix.split(",");
		
		if (fix == null || fix.length ==0) {
			isOK = true;
		}
		
		//拡張子をチェック
		String fileNM = strPath.toUpperCase();
		for (int i=0; i<fix.length; i++) {
			if (fileNM.endsWith(fix[i].toUpperCase())) {
				isOK = true;
				break;
			}	
		}
		
		return isOK;
	}
}
