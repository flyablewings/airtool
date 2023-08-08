package com.hokukou.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hokukou.data.HK_Data;
import com.hokukou.database.DB_Exception;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : SavaToExcel.java
	Program Name    : Excel保存時のServlet処理
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
	2009/08/03  : 複数レポートの１ジョブ印刷機能追加 = H.Fujimoto
	2009/08/03  : １ジョブ化の際の並び替え情報管理機能追加 = H.Fujimoto
================================================================================
********************************************************************************
*/

public class SavaToExcel extends HttpServlet {

	/***** 2009/08/03 INSERT START *****/
	public static final String REPORT_SORT_FIELD = "REPORT_SORT_FILED";
	public static final String REPORT_IS_SORT = "REPORT_IS_SORT";
	public static final String REPORT_SORT_NUM = "REPORT_SORT_NUM";
	/***** 2009/08/03 INSERT E N D *****/
	
	private static final long serialVersionUID = 1L;
	private String _strRetState = null;
	private String _strRetInfo = null;

	public SavaToExcel() {
		super();
	}

	private ExcelReport initializeReportData(Map map) throws DB_Exception, Exception {
		String classID = ((String[]) map.get("id"))[0];
		ExcelReport reportData = null;

		iHK_ReportOut printReport = (iHK_ReportOut) Class.forName(classID).newInstance();
		reportData = printReport.getExcelReportRes(map);
	
		return reportData;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletOutputStream servletOutputStream = null;
	    
		try {
			_strRetState = "";
			_strRetInfo = "";
			_execExcel(request, response);
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

	//※※※※※※ mapExlParam.put(REPORT_IS_SORT, String.valueOf(excelBase.getSortPage())); で警告が出る為にこの記述を追加 ※※※※※
	@SuppressWarnings("unchecked")
	protected void _execExcel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Map mapMxmlParam = request.getParameterMap();
		/***** 2009/08/03 UPDATE START *****/
		//ExcelReport excelBase = null;
		ArrayList<ExcelReport> aryAllReports;
		ArrayList<ExcelReportCollection> aryReportCollection;
		ExcelReport excelBase = null;
		ExcelReport excelOne = null;
		ExcelReportCollection prcReportCollection;
		String strArg1 = "";
		String strArg2 = "";
		/***** 2009/08/03 UPDATE E N D *****/
		String strClientTmpPath = ((String[])mapMxmlParam.get("ReportOut_java_ClientTmpPath"))[0];

		//========== 各プログラムをコールし、印刷に関わるデータの設定をしてもらう ==========
		try {
			excelBase = initializeReportData(mapMxmlParam);
		} catch (DB_Exception ex) {
			throw new ServletException(PrintReport.getAllErrMsg(ex));
		} catch (Exception ex) {
			throw new ServletException(PrintReport.getAllErrMsg(ex));
		}
		if (excelBase == null) {
			throw new ServletException("印刷処理でデータが何も作成されませんでした。");
		}
		//----- 各プログラムでStatusが設定された時は、処理を終了！
		if (excelBase.getReturnState().length() != 0) {
			_strRetState = excelBase.getReturnState();
			_strRetInfo = excelBase.getReturnInfo();				
			return;
		}

		/***** 2009/08/03 INSERT START *****/
		//----- 単一レポートであれば、自分をレポートリストに追加する
		aryAllReports = excelBase.getAddedReports();
		if (aryAllReports == null) {
			aryAllReports = new ArrayList<ExcelReport>();
			aryAllReports.add(excelBase);
		}
		/***** 2009/08/03 INSERT E N D *****/

		aryReportCollection = new ArrayList<ExcelReportCollection>();

		/***** 2009/08/03 INSERT START *****/
		//----- 設定されたレポート分だけ処理を繰り返す。（一度に複数のレポートを使用する場合は、ここが複数回、回る）
		for (int intRptIdx = 0; intRptIdx < aryAllReports.size(); intRptIdx++) {
			excelOne = aryAllReports.get(intRptIdx);
			/***** 2009/08/03 INSERT E N D *****/

		
			//========== iReportへ渡すパラメータと、iReportのデザインファイルを取得 ==========
			Map mapExlParam = excelOne.getParameter();
	
			/***** 2009/08/03 INSERT START *****/
			mapExlParam.put(REPORT_IS_SORT, String.valueOf(excelOne.getSortPage()));
			mapExlParam.put(REPORT_SORT_NUM, String.valueOf(excelOne.getSortPageByNum()));
			mapExlParam.put(REPORT_SORT_FIELD, excelOne.getSortPageField());
			/***** 2009/08/03 INSERT E N D *****/
	 	    
	 	    //========== iReportのデザインファイル(メイン)を取得 ==========
			ArrayList<HK_ReportResources> aryStyles = new ArrayList<HK_ReportResources>();
			String strStyleFile = excelOne.getStyleFile();
			if (strStyleFile == null) {
	 	    	throw new ServletException("Excel出力デザインが指定されていません。");
			}
	
			HK_ReportResources reportResource;
			try {
				 reportResource = new HK_ReportResources(this.getServletContext().getRealPath("/"),strStyleFile);
			} catch (FileNotFoundException e) {
	 	    	throw new ServletException("指定されたExcel出力デザインがありません。[" + strStyleFile + "]");
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
					if(fileStyleContents[intIdx].getName().startsWith(strFilePrifx) &&
					   !fileStyleContents[intIdx].getName().replace(".jasper", "").equals(strFilePrifx)	){
						HK_ReportResources reportResourceSub = new HK_ReportResources(this.getServletContext().getRealPath("/") + PrinterReport.REPORT_DIR,
								                                                       fileStyleContents[intIdx].getName());
						reportResourceSub.setDesDir(strClientTmpPath);
						aryStyles.add(reportResourceSub);
					}
				}
			} 	

			/***** 2009/08/03 INSERT START *****/
			//========== 個別指定のiReportのデザインファイルを取得(もしあれば...) ==========
			if (excelOne.getSubStyle() != null) {
				for (int intIdx = 0; intIdx < excelOne.getSubStyle().size(); intIdx++) {
					File fileStyleSub;
					fileStyleSub = new File(this.getServletContext().getRealPath("/") + excelOne.getSubStyle().get(intIdx));
					strArg1 = fileStyleSub.getParent();
					strArg2 = fileStyleSub.getName();
					HK_ReportResources reportResourceSub = new HK_ReportResources(strArg1, strArg2);
					reportResourceSub.setDesDir(strClientTmpPath);
					aryStyles.add(reportResourceSub);
				}
			}
			/***** 2009/08/03 INSERT E N D *****/

			//========== iReportへ渡すExcel明細データを取得==========
			Collection dataSource = null;
			
			/***** 2009/08/03 DELETE START *****/
//		    String strCsvFile = null;
			/***** 2009/08/03 DELETE E N D *****/
		    ArrayList<HK_ReportResources> aryResources = new ArrayList<HK_ReportResources>();
		    if (excelOne.getExcelFromCsv() == false) {
		    	//----- ArrayList - HasMap に格納したデータを使用する場合
		    	dataSource = excelOne.getDataSource();
	 	 	    if (dataSource == null || dataSource.size() == 0) {
	 	 	    	throw new ServletException("Excel保存データが設定されていません。[ArrayList]");
	 	 	    }
		    } else {
		    	//----- CSVファイルに格納したデータを使用する場合
		    	if (excelOne.getCsvFile().length() == 0) {
		    		throw new ServletException("Excel保存データが設定されていません。[CSV]");
		    	}
				/***** 2009/08/03 DELETE START *****/
//		    	strCsvFile = this.getServletContext().getRealPath("/") + 
//	       	 				 PrintReportFromCsv.REPORT_TEMP_DIR + "/" + 
//	       	 				 excelBase.getCsvFile();
				/***** 2009/08/03 DELETE E N D *****/
		    	
		    	HK_ReportResources csvFile = new HK_ReportResources(this.getServletContext().getRealPath("/"),
						   											PrintReportFromCsv.REPORT_TEMP_DIR + "/" + excelOne.getCsvFile());
		    	csvFile.setDesDir(strClientTmpPath);
				aryResources.add(csvFile);
				File fileCsvDir;
				fileCsvDir = new File(HK_Data.getRealPath() + "/" + PrintReportFromCsv.REPORT_TEMP_DIR);
				File[] fileDataContents = fileCsvDir.listFiles();
				if (fileDataContents.length > 0) {
					String strFilePrifx = excelOne.getCsvFile().replace(".csv", "");
					for (int intIdx = 0; intIdx < fileDataContents.length; intIdx++) {
						if(fileDataContents[intIdx].getName().startsWith(strFilePrifx) &&
						   !fileDataContents[intIdx].getName().replace(".csv", "").equals(strFilePrifx)	){
							HK_ReportResources csvFileSub = new HK_ReportResources(this.getServletContext().getRealPath("/"),
		   							   											   PrintReportFromCsv.REPORT_TEMP_DIR + "/" + fileDataContents[intIdx].getName());
							csvFileSub.setDesDir(strClientTmpPath);
							aryResources.add(csvFileSub);
						}
					}
				}
				
				/***** 2009/08/03 INSERT START *****/
				// 2009/08/03 現在 Excelへの画像の出力ができないため、この機能は意味を成していないが、
				// 今後この機能を有することを念頭において機能追加した。				 
				// 印刷用 固定ファイルの転送設定処理
				List<String> lstResources;
				lstResources = excelOne.getResources();
				for(int intIdx = 0; intIdx < lstResources.size(); intIdx ++){
					strArg1 = this.getServletContext().getRealPath("/");
					strArg2 = PrintReport.REPORT_RESOURCE_DIR + "/" + lstResources.get(intIdx);
					HK_ReportResources fleResources = new HK_ReportResources(strArg1, strArg2);
					fleResources.setDesDir(strClientTmpPath);
					aryResources.add(fleResources);
				}

				// 印刷用 動的生成ファイルの転送設定処理
				lstResources = excelOne.getResourcesTemp();
				for(int intIdx = 0; intIdx < lstResources.size(); intIdx ++){
					strArg1 = this.getServletContext().getRealPath("/");
					strArg2 = PrintReport.REPORT_RESOURCE_TEMP_DIR + "/" + lstResources.get(intIdx);
					HK_ReportResources fleResources = new HK_ReportResources(strArg1, strArg2);
					fleResources.setDesDir(strClientTmpPath);
					aryResources.add(fleResources);
				}
				/***** 2009/08/03 INSERT E N D *****/
	 	    }
		    
			// SHEET_NAMES を CSV から取得
			if (excelOne.SHEET_NAMES == null && excelOne.getSheetNameFile() != null){
				ArrayList<String> arySheetNames = new ArrayList<String>();
				
				File fileSheetName = new File(HK_Data.getRealPath() + 
						                      "/" + PrintReportFromCsv.REPORT_TEMP_DIR + 
						                      "/" + excelOne.getSheetNameFile());
				//FileReader fileR = new FileReader(fileSheetName);
				FileInputStream fisCsv = new FileInputStream(fileSheetName);
				InputStreamReader isrReader = new InputStreamReader(fisCsv, "MS932");
	
				
				BufferedReader fileBR = new BufferedReader(isrReader);
				String strline;
				int intRow;
				intRow = 0;
				while ((strline = fileBR.readLine()) != null) {
					intRow ++;
					if (intRow > 1) {  // １行目は列ヘッダーなので除く
						if (strline.length() > 1) {
							strline = strline.substring(1, strline.length() - 1);
						}
						arySheetNames.add(strline);
					}
				}
				fileBR.close();
				isrReader.close();
				fisCsv.close();
				
				excelOne.SHEET_NAMES = arySheetNames.toArray(new String[0]);
			}

			/***** 2009/08/03 INSERT START *****/
		    prcReportCollection = new ExcelReportCollection();
		    prcReportCollection.setResources(aryResources);
		    prcReportCollection.setDataSource(dataSource);
		    prcReportCollection.setRptParam(mapExlParam);
		    prcReportCollection.setStyles(aryStyles);
		    prcReportCollection.setOnePagePerSheet(excelOne.IS_ONE_PAGE_PER_SHEET);
		    prcReportCollection.setSheetNames(excelOne.SHEET_NAMES);
		    
		    aryReportCollection.add(prcReportCollection);
		    
		}
		/***** 2009/08/03 INSERT E N D *****/

 	    ServletOutputStream servletOutputStream = null;

		try {
			servletOutputStream = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(servletOutputStream);

		    oos.writeObject("server_inner_ok");
			oos.flush();

			/***** 2009/08/03 UPDATE START *****/
//			if(excelBase.getExcelFromCsv()){
//			    oos.writeObject(aryResources);
//			}else{
//				oos.writeObject(dataSource);
//			}
//			oos.flush();
//			oos.writeObject(mapExlParam);
//			oos.writeObject(aryStyles);
//			oos.writeObject(excelBase.IS_ONE_PAGE_PER_SHEET);
//			oos.writeObject(excelBase.SHEET_NAMES);

			//----- 処理されたレポート分だけ書き出しを繰り返す。（一度に複数のレポートを使用する場合は、ここが複数回、回る）
			for (int intIdx = 0; intIdx < aryReportCollection.size(); intIdx++) {
				if(excelBase.getExcelFromCsv()){
				    oos.writeObject(aryReportCollection.get(intIdx).getResources());
				}else{
					oos.writeObject(aryReportCollection.get(intIdx).getDataSource());
				}
				oos.flush();
				oos.writeObject(aryReportCollection.get(intIdx).getRptParam());
				oos.writeObject(aryReportCollection.get(intIdx).getStyles());
				oos.writeObject(aryReportCollection.get(intIdx).getOnePagePerSheet());
				oos.writeObject(aryReportCollection.get(intIdx).getSheetNames());
				oos.flush();
			}

		    oos.writeObject("end");
			/***** 2009/08/03 UPDATE E N D *****/
			oos.flush();
			oos.close();
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception ex) {
	    	throw new ServletException(PrintReport.getAllErrMsg(ex));
		}
		
    	// CSVファイルに格納したデータを使用する場合
		// 印刷後は、CSVファイルを削除
		if (excelBase.getExcelFromCsv() == true) {
			/***** 2009/08/03 UPDATE START *****/
//			File fileCsvFiles;
//			fileCsvFiles = new File(HK_Data.getRealPath() + "/" + PrintReportFromCsv.REPORT_TEMP_DIR);
//			File[] fileContents = fileCsvFiles.listFiles();
//			if (fileContents.length > 0) {
//				for (int intIdx = 0; intIdx < fileContents.length; intIdx++) {
//					String strFile1 = strCsvFile.replace(".csv", "");
//					String strFile2 = fileContents[intIdx].toString();
//					strFile1 = strFile1.replace("\\", "/");
//					strFile2 = strFile2.replace("\\", "/");
//					if (strFile2.indexOf(strFile1) >= 0) {
//						fileContents[intIdx].delete();
//					}
//				}
//			}
			
			File fileCsvDir;
			String strCsvFile;
			
			//----- 処理されたレポートの使用ファイルの削除を繰り返す。（一度に複数のレポートを使用する場合は、ここが複数回、回る）
			for (int intRptIdx = 0; intRptIdx < aryAllReports.size(); intRptIdx++) {
				excelOne = aryAllReports.get(intRptIdx);
				strCsvFile = this.getServletContext().getRealPath("/")
	 				       + PrintReportFromCsv.REPORT_TEMP_DIR + "/" 
	 				       + excelOne.getCsvFile();
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
			/***** 2009/08/03 UPDATE E N D *****/
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
