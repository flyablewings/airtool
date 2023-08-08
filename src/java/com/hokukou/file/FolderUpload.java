package com.hokukou.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hokukou.HK_Cnst;
import com.hokukou.data.HK_Data;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : FolderUpload.java
	Program Name    : 一括アップロード処理
	Program Date    : 2009/02/20
	Programmer      : T.Ogawa
   
===< Update History >===========================================================
	2009/02/20  : 新規作成
================================================================================
********************************************************************************
*/

public class FolderUpload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FolderUpload() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, 
	                                                                                       ServletException{
		Map mapPara = request.getParameterMap();
		String strClassID = ""; 
		
		if (mapPara.get("id") == null) {
			this._initDir();
			this._doGetNormal(request, response);
		} else {
			strClassID = ((String[]) mapPara.get("id"))[0];
			IFile fileOparate = null;
			
			try {
				fileOparate = (IFile) Class.forName(strClassID).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			fileOparate.doUploading(request, response);
		}
		
	}
	
	private void _doGetNormal(HttpServletRequest request, HttpServletResponse response) throws IOException, 
                                                                                                  ServletException{
		try {
			Map mapPara = request.getParameterMap();
			String strTempDir = ((String[])mapPara.get("tempDir"))[0];
			String strFileName = ((String[])mapPara.get("fileName"))[0];
			
			String strAppPath = this.getServletContext().getRealPath("/");
			
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			ServletInputStream inputStream = request.getInputStream();
			
			//仮フォルダを作成
			if (strTempDir == null || strTempDir.equals("")) {
				strTempDir = makeUploadTempDir();
			}
			
			FileOutputStream fOut = new FileOutputStream(strAppPath + HK_Cnst.CNT_FolderUploadTempDir + strTempDir + "/" + strFileName);
			
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				fOut.write(buffer, 0, i);
			}
			fOut.flush();
			fOut.close();
			
			ServletOutputStream outStream = response.getOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(outStream);
			objOut.writeObject("ok");
			objOut.writeObject(strTempDir);
			objOut.flush();
			objOut.close();
		} catch (Exception e) {
			ServletOutputStream outStream = response.getOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(outStream);
			objOut.writeObject("error");
			objOut.writeObject(e.getMessage());
			objOut.flush();
			objOut.close();
		}
	}

	public void _initDir() throws ServletException {

		String strAppPath = this.getServletContext().getRealPath("/");
		
		File dirUpload = new File(strAppPath + HK_Cnst.CNT_FolderUploadTempDir);
		if (!dirUpload.exists()) {
			dirUpload.mkdirs();
		}
		
		//２日以上前のファイルがあるときは削除
		File[] fileTempDirs = dirUpload.listFiles();
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
	}
	
	/**
	 * アップロード仮フォルダを作成する処理
	 */
	private String makeUploadTempDir() {

		String strAppPath = this.getServletContext().getRealPath("/");
		String strTempDir = String.valueOf(System.currentTimeMillis());
		
		File dirTempDir = new File(strAppPath+ "/" + HK_Cnst.CNT_FolderUploadTempDir + "/" + strTempDir);
		if (dirTempDir.exists() == false) {
			dirTempDir.mkdirs();
		}
		
		return strTempDir;
	}
}
