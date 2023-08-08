package com.hokukou.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hokukou.data.HK_Data;
import com.hokukou.database.DB_Exception;
import com.hokukou.file.FileUtility;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : SavaToPDF.java
	Program Name    : 印刷時のServlet処理
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
	2009/08/03  : １ジョブ化の際の並び替え情報管理機能追加          = H.Fujimoto
	2009/11/12  : PDFのﾌｧｲﾙ分割機能の追加          					= H.Fujimoto
================================================================================
********************************************************************************
*/
public class SavaToPDF extends HttpServlet {
	
	public static final String REPORT_RESOURCE_TEMP_DIR = "reportsResourcesTemp";
	public static final String REPORT_RESOURCE_DIR = "reportsResources";

	/***** 2009/08/03 INSERT START *****/
	public static final String REPORT_SORT_FIELD = "REPORT_SORT_FILED";
	public static final String REPORT_IS_SORT = "REPORT_IS_SORT";
	public static final String REPORT_SORT_NUM = "REPORT_SORT_NUM";
	/***** 2009/08/03 INSERT E N D *****/

	/***** 2009/11/12 INSERT START *****/
	public static final String PDF_IS_PAGE_SEPARATE = "PDF_IS_PAGE_SEPARATE";
	public static final String PDF_PAGE_SEPARATE_FIELD = "PDF_PAGE_SEPARATE_FIELD";
	public static final String PDF_FILENAME_FIELD = "PDF_FILENAME_FIELD";
	/***** 2009/11/12 INSERT E N D *****/
	
	private static final long serialVersionUID = 1L;
	private String _strRetState = null;
	private String _strRetInfo = null;
	
	public static String getAllErrMsg(Exception ex) {
		String strAllMsg = "";
		strAllMsg += ex.getMessage();
		for (int i=0; i<ex.getStackTrace().length; i++){
			strAllMsg = strAllMsg + "\n" + ex.getStackTrace()[i].toString();
		}
		return strAllMsg;
	}

	/**
	 * 印刷用Resourceファイルを格納用のフォルダ作成する処理（２日以上前のファイルが残っていれば削除する）
	 * フォルダは、YYYYMMDD-HHMMDD-99999
	 */
	public static String createResTmpDir(String strBase) throws IOException {
		File fileResDir;
		String strFileName;
		String strPrefix;
		int intCnt;
		
		//========== CSVファイルの保存場所を作成 ==========
		fileResDir = new File(HK_Data.getRealPath() + "/" +  REPORT_RESOURCE_TEMP_DIR);
		if (!fileResDir.exists()) {
			fileResDir.mkdirs();
		}

		//========== ２日以上前のファイルがあるときは削除 ==========
		fileResDir = new File(HK_Data.getRealPath() + "/" +  REPORT_RESOURCE_TEMP_DIR);
		File[] fileContents = fileResDir.listFiles();
		if (fileContents.length > 0) {
			int intToday;
			int int1DayBef;
			int[] intTodays;
			GregorianCalendar cln1DayBef;

			intToday = HK_Data.parseInt(HK_Data.getDate(""));
			intTodays = HK_Data.devideDate(intToday);

			cln1DayBef = new GregorianCalendar(intTodays[0], intTodays[1]-1, intTodays[2]);
			cln1DayBef.add(Calendar.DATE, -1);
			
			int1DayBef = cln1DayBef.get(Calendar.YEAR) * 10000 +
			             (cln1DayBef.get(Calendar.MONTH) + 1) * 100 +
			             cln1DayBef.get(Calendar.DATE);
			
			for (int intIdx = 0; intIdx < fileContents.length; intIdx++) {
				if (fileContents[intIdx].getName().compareTo(String.valueOf(int1DayBef)) < 0) {
					FileUtility.deleteFileAll(fileContents[intIdx].getAbsolutePath());
				}
			}
		}
		
		//========== 基本フォルダ名を作成 ==========
		if (strBase.length() == 0) {
			strPrefix = HK_Data.getDate("") + "-" + HK_Data.getTime("") ;
		} else {
			strPrefix = strBase;
		}

		//========== フォルダを作成 ==========
		intCnt = 0;
		while(true){
			intCnt ++;
			strFileName = strPrefix + "-" + HK_Data.Format(intCnt, "00000", 5);
			
			fileResDir = new File(HK_Data.getRealPath() + "/" + REPORT_RESOURCE_TEMP_DIR + "/" + strFileName);
			
			if (fileResDir.exists() == false) {
				fileResDir.mkdirs();
				break;
			}
			if (intCnt > 99999) {
				new IOException("印刷データを格納するファイルが作成できませんでした。");
			}
		}
		
		return strFileName; 
	}

	private PrinterReport initializeReportData(Map map) throws DB_Exception, Exception {
		String classID = ((String[])map.get("id"))[0];
		PrinterReport reportData = null;

		iHK_ReportOut printReport = (iHK_ReportOut) Class.forName(classID).newInstance();
		reportData = printReport.getPrinterReportRes(map);

		return reportData;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		ServletOutputStream servletOutputStream = null;
	    
		try {
			_strRetState = "";
			_strRetInfo = "";
			_execPrint(request, response);
			if (_strRetState.length() != 0) {
				servletOutputStream = response.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(servletOutputStream);
				oos.writeObject(_strRetState);
				oos.writeObject(_strRetInfo);
				oos.flush();
				oos.close();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			servletOutputStream = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(servletOutputStream);
		    oos.writeObject("server_inner_error");
			oos.flush();
			oos.writeObject(PrintReport.getAllErrMsg(e));
			oos.flush();
			oos.close();
			servletOutputStream.flush();
			servletOutputStream.close();
		}
	}

	//※※※※※※ reportBase.getParameter().put(REPORT_IS_SORT, String.valueOf(reportBase.getSortPage())); で警告が出る為にこの記述を追加 ※※※※※
	@SuppressWarnings("unchecked")
	private void _execPrint(
			HttpServletRequest request,
			HttpServletResponse response
			) throws ServletException, IOException {
		
		Map mapMxmlParam = request.getParameterMap();
		ArrayList<PrinterReport> aryAllReports;
		ArrayList<PrintReportCollection> aryReportCollection;
		PrinterReport reportBase = null;
		PrinterReport reportOne = null;
		PrintReportCollection prcReportCollection;
		
		String strArg1 = "";
		String strArg2 = "";
		String strClientTmpPath = ((String[])mapMxmlParam.get("ReportOut_java_ClientTmpPath"))[0];
		                                             
		//========== 各プログラムをコールし、印刷に関わるデータの設定をしてもらう ==========
		try {
			reportBase = initializeReportData(mapMxmlParam);
		} catch (DB_Exception ex) {
			throw new ServletException(PrintReport.getAllErrMsg(ex));
		} catch (Exception ex) {
			throw new ServletException(PrintReport.getAllErrMsg(ex));
		}
		if (reportBase == null) {
			throw new ServletException("印刷にかかわる情報が何も作成されませんでした。");
 	    }

		//----- 各プログラムでStatusが設定された時は、処理を終了！
		if (reportBase.getReturnState().length() != 0) {
			_strRetState = reportBase.getReturnState();
			_strRetInfo = reportBase.getReturnInfo();				
			return;
		}

		//----- 単一レポートであれば、自分をレポートリストに追加する
		aryAllReports = reportBase.getAddedReports();
		if (aryAllReports == null) {
			aryAllReports = new ArrayList<PrinterReport>();
			aryAllReports.add(reportBase);
		}
		
		aryReportCollection = new ArrayList<PrintReportCollection>();
		
		//----- 設定されたレポート分だけ処理を繰り返す。（一度に複数のレポートを使用する場合は、ここが複数回、回る）
		for (int intRptIdx = 0; intRptIdx < aryAllReports.size(); intRptIdx++) {
			reportOne = aryAllReports.get(intRptIdx);

			//========== iReportへ渡すパラメータを取得 ==========
			Map mapRptParam = reportOne.getParameter();

			/***** 2009/08/03 INSERT START *****/
			mapRptParam.put(REPORT_IS_SORT, String.valueOf(reportOne.getSortPage()));
			mapRptParam.put(REPORT_SORT_NUM, String.valueOf(reportOne.getSortPageByNum()));
			mapRptParam.put(REPORT_SORT_FIELD, reportOne.getSortPageField());
			/***** 2009/08/03 INSERT E N D *****/

			/***** 2009/11/12 INSERT START *****/
			mapRptParam.put(PDF_IS_PAGE_SEPARATE, String.valueOf(reportOne.getPdfIsPageSeparate()));
			mapRptParam.put(PDF_PAGE_SEPARATE_FIELD, reportOne.getPdfPageSeparateField());
			mapRptParam.put(PDF_FILENAME_FIELD, reportOne.getPdfFileNMField());
			/***** 2009/11/12 INSERT E N D *****/

			//========== iReportのデザインファイル(メイン)を取得 ==========
			String strStyleFile = reportOne.getStyleFile();
			ArrayList<HK_ReportResources> aryStyles = new ArrayList<HK_ReportResources>();
			if (strStyleFile == null) {
				throw new ServletException("印刷デザインが指定されていません。");
			}

			HK_ReportResources reportResource = null;
			try {
				reportResource = new HK_ReportResources(this.getServletContext().getRealPath("/"), strStyleFile);
			} catch (FileNotFoundException e) {
				throw new ServletException("指定された印刷デザインがありません。[" + strStyleFile + "]");
			}

			reportResource.setDesDir(strClientTmpPath);
			aryStyles.add(reportResource);

			//========== iReportのデザインファイル(サブレポートのデザイン)を取得(もしあれば...) ==========
			File fileStyleDir;
			fileStyleDir = new File(this.getServletContext().getRealPath("/") + PrinterReport.REPORT_DIR);
			File[] fileStyleContents = fileStyleDir.listFiles();
			if (fileStyleContents.length > 0) {
				String strFilePrifx = new File(this.getServletContext().getRealPath("/") + strStyleFile).getName().replace(".jasper", "");
				for (int intIdx = 0; intIdx < fileStyleContents.length; intIdx++) {
					if(fileStyleContents[intIdx].getName().startsWith(strFilePrifx) == true &&
					   !fileStyleContents[intIdx].getName().replace(".jasper", "").equals(strFilePrifx)	){
						strArg1 = this.getServletContext().getRealPath("/") + PrinterReport.REPORT_DIR;
						strArg2 = fileStyleContents[intIdx].getName();
						HK_ReportResources reportResourceSub = new HK_ReportResources(strArg1, strArg2);
						reportResourceSub.setDesDir(strClientTmpPath);
						aryStyles.add(reportResourceSub);
					}
				}
			} 	    

			/***** 2009/08/03 INSERT START *****/
			//========== 個別指定のiReportのデザインファイルを取得(もしあれば...) ==========
			if (reportOne.getSubStyle() != null) {
				for (int intIdx = 0; intIdx < reportOne.getSubStyle().size(); intIdx++) {
					File fileStyleSub;
					fileStyleSub = new File(this.getServletContext().getRealPath("/") + reportOne.getSubStyle().get(intIdx));
					strArg1 = fileStyleSub.getParent();
					strArg2 = fileStyleSub.getName();
					HK_ReportResources reportResourceSub = new HK_ReportResources(strArg1, strArg2);
					reportResourceSub.setDesDir(strClientTmpPath);
					aryStyles.add(reportResourceSub);
				}
			}
			/***** 2009/08/03 INSERT E N D *****/
			
			//========== iReportへ渡す印刷明細データを取得 ==========
			Collection dataSource = null;
		    ArrayList<HK_ReportResources> aryResources = new ArrayList<HK_ReportResources>();
		    if (reportOne.getPrintFromCsv() == false) {
		    	//----- ArrayList - HasMap に格納したデータを使用する場合
		    	dataSource = reportOne.getDataSource();
	 	 	    if (dataSource == null || dataSource.size() == 0) {
	 	 	    	throw new ServletException("印刷データが設定されていません。[ArrayList]");
	 	 	    }
		    } else {
		    	//----- CSVファイルに格納したデータを使用する場合
		    	if (reportOne.getCsvFile().length() == 0) {
		    		throw new ServletException("印刷データが設定されていません。[CSV]");
		    	}
		    	
		    	strArg1 = this.getServletContext().getRealPath("/");
				strArg2 = PrintReportFromCsv.REPORT_TEMP_DIR + "/" + reportOne.getCsvFile();
				HK_ReportResources csvFile = new HK_ReportResources(strArg1, strArg2);
		    	csvFile.setDesDir(strClientTmpPath);
				aryResources.add(csvFile);
				File fileCsvDir;
				fileCsvDir = new File(HK_Data.getRealPath() + "/" + PrintReportFromCsv.REPORT_TEMP_DIR);
				File[] fileDataContents = fileCsvDir.listFiles();
				if (fileDataContents.length > 0) {
					String strFilePrifx = reportOne.getCsvFile().replace(".csv", "");
					for (int intIdx = 0; intIdx < fileDataContents.length; intIdx++) {
						if(fileDataContents[intIdx].getName().startsWith(strFilePrifx) == true &&
						   !fileDataContents[intIdx].getName().replace(".csv", "").equals(strFilePrifx)	){
							
							strArg1 = this.getServletContext().getRealPath("/");
							strArg2 = PrintReportFromCsv.REPORT_TEMP_DIR + "/" + fileDataContents[intIdx].getName();
							
							HK_ReportResources csvFileSub = new HK_ReportResources(strArg1, strArg2);
							csvFileSub.setDesDir(strClientTmpPath);
							aryResources.add(csvFileSub);
						}
					}
				}
				
				// 印刷用 固定ファイルの転送設定処理
				List<String> lstResources;
				lstResources = reportOne.getResources();
				for(int intIdx = 0; intIdx < lstResources.size(); intIdx ++){
					strArg1 = this.getServletContext().getRealPath("/");
					strArg2 = PrintReport.REPORT_RESOURCE_DIR + "/" + lstResources.get(intIdx);
					HK_ReportResources fleResources = new HK_ReportResources(strArg1, strArg2);
					fleResources.setDesDir(strClientTmpPath);
					aryResources.add(fleResources);
				}

				// 印刷用 動的生成ファイルの転送設定処理
				lstResources = reportOne.getResourcesTemp();
				for(int intIdx = 0; intIdx < lstResources.size(); intIdx ++){
					strArg1 = this.getServletContext().getRealPath("/");
					strArg2 = PrintReport.REPORT_RESOURCE_TEMP_DIR + "/" + lstResources.get(intIdx);
					HK_ReportResources fleResources = new HK_ReportResources(strArg1, strArg2);
					fleResources.setDesDir(strClientTmpPath);
					aryResources.add(fleResources);
				}
		    }
		    
		    prcReportCollection = new PrintReportCollection();
		    prcReportCollection.setResources(aryResources);
		    prcReportCollection.setDataSource(dataSource);
		    prcReportCollection.setRptParam(mapRptParam);
		    prcReportCollection.setStyles(aryStyles);
		    aryReportCollection.add(prcReportCollection);
		}

 	    ServletOutputStream servletOutputStream = null;
	    try {
			servletOutputStream = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(servletOutputStream);
			
		    oos.writeObject("server_inner_ok");
			oos.flush();
			
			//----- 処理されたレポート分だけ書き出しを繰り返す。（一度に複数のレポートを使用する場合は、ここが複数回、回る）
			for (int intIdx = 0; intIdx < aryReportCollection.size(); intIdx++) {
				if(reportBase.getPrintFromCsv()){
				    oos.writeObject(aryReportCollection.get(intIdx).getResources());
				}else{
					oos.writeObject(aryReportCollection.get(intIdx).getDataSource());
				}
				oos.flush();
				oos.writeObject(aryReportCollection.get(intIdx).getRptParam());
				oos.writeObject(aryReportCollection.get(intIdx).getStyles());
				oos.flush();
			}

		    oos.writeObject("end");
			oos.flush();

			oos.close();
			servletOutputStream.flush();
			servletOutputStream.close();
	    } catch (Exception ex) {
	    	throw new ServletException(PrintReport.getAllErrMsg(ex));
	    }
	    
    	// CSVファイルに格納したデータを使用する場合
		// 印刷後は、CSVファイルを削除

	    /***** 2009/11/12 UPDATE START *****/
	    //if (reportBase.getPrintFromCsv() == true) {
		if (reportBase.getPrintFromCsv() == true && reportBase.getIsDeleteTempFile() == true) {
		    /***** 2009/11/12 UPDATE E N D *****/
			File fileCsvDir;
			String strCsvFile;
			
			//----- 処理されたレポートの使用ファイルの削除を繰り返す。（一度に複数のレポートを使用する場合は、ここが複数回、回る）
			for (int intRptIdx = 0; intRptIdx < aryAllReports.size(); intRptIdx++) {
				reportOne = aryAllReports.get(intRptIdx);
				strCsvFile = this.getServletContext().getRealPath("/")
	 				       + PrintReportFromCsv.REPORT_TEMP_DIR + "/" 
	 				       + reportOne.getCsvFile();
				fileCsvDir = new File(HK_Data.getRealPath() + "/" + PrintReportFromCsv.REPORT_TEMP_DIR);
				File[] fileResourcesContents = fileCsvDir.listFiles();
				if (fileResourcesContents.length > 0) {
					for (int intIdx = 0; intIdx < fileResourcesContents.length; intIdx++) {
						String strFile1 = strCsvFile.replace(".csv", "");
						String strFile2 = fileResourcesContents[intIdx].toString();
						strFile1 = strFile1.replace("\\", "/");
						strFile2 = strFile2.replace("\\", "/");
						if (strFile2.indexOf(strFile1) >= 0) {
							fileResourcesContents[intIdx].delete();
						}
					}
				}
			}
			
			//印刷用 動的生成ファイルの削除処理
			File fileRes;
			String strResFile;
			List<String> lstResources = reportOne.getResourcesTemp();
			for(int intIdx = 0; intIdx < lstResources.size(); intIdx ++){
				strResFile = this.getServletContext().getRealPath("/")
			               + PrintReport.REPORT_RESOURCE_TEMP_DIR + "/" + lstResources.get(intIdx);
				fileRes = new File(strResFile);
				fileRes.delete();
				
				if (PrintReport.REPORT_RESOURCE_TEMP_DIR.equals(fileRes.getParent()) == false) {
					fileRes = new File(fileRes.getParent());
					if (fileRes.length() == 0) {
						fileRes.delete();
					}
				}
			}
		}
	}
}
