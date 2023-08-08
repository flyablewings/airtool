package com.hokukou.file;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import com.hokukou.HK_Cnst;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : FileUpload.java
	Program Name    : ファイルのアップロード管理
	Program Date    : 2009/08/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2009/08/05  : この部分のコメント文を追加 = H.Fujimoto
	2009/08/05  : AIRPrintとの通信方式を合わせるように修正 = H.Fujimoto
================================================================================
********************************************************************************
*/

public class FileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public FileUpload() {
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
		String strAppPath = this.getServletContext().getRealPath("/");
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		ServletInputStream inputStream = request.getInputStream();

		File filTargetDirectory = new File(strAppPath + HK_Cnst.CNT_FileUploadDir);
		File[] filDirContents = filTargetDirectory.listFiles();
		if (HK_Cnst.CNT_FileUploadMax <= filDirContents.length) {
			for (int intIdx = 0; intIdx <= filDirContents.length - HK_Cnst.CNT_FileUploadMax; intIdx++) {
				filDirContents[intIdx].delete();
			}
		}
		
		String strFileName = System.currentTimeMillis() + "";
		
		FileOutputStream fOut = new FileOutputStream(strAppPath + HK_Cnst.CNT_FileUploadDir + strFileName);
		
		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = inputStream.read(buffer)) != -1) {
			fOut.write(buffer, 0, i);
		}
		fOut.flush();
		fOut.close();
		
		ServletOutputStream outStream = response.getOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(outStream);
		
		/***** 2009/08/05 INSERT START *****/
//		strState1 = (String)b.get("status");
//		strInfo1 = (String)b.get("info");
//		objOut.writeObject("ok");
//		objOut.writeObject(strFileName);

		ArrayList<HashMap<String, String>> ary = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", "ok");
		map.put("info", strFileName);
		ary.add(map);
		objOut.writeObject(ary);
		/***** 2009/08/05 INSERT E N D *****/
		objOut.flush();
		objOut.close();
	}

	public void _initDir() throws ServletException {

		String strAppPath = this.getServletContext().getRealPath("/");
		
		File dirCache = new File(strAppPath + HK_Cnst.CNT_FileCacheDir);
		if (!dirCache.exists()) {
			dirCache.mkdirs();
		}

		File dirUpload = new File(strAppPath + HK_Cnst.CNT_FileUploadDir);
		if (!dirUpload.exists()) {
			dirUpload.mkdirs();
		}

	}
}
