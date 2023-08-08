package com.hokukou.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hokukou.data.HK_Data;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : ExcelReport.java
	Program Name    : Excel保存用の設定情報管理
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
	2009/08/03  : 複数レポートの１ジョブ印刷機能追加                = H.Fujimoto
	2009/08/03  : １ジョブ化の際の並び替え情報管理機能追加          = H.Fujimoto
	2009/09/11  : setParameterにnullをセットされるとエラーが発生    = T.Ogawa
================================================================================
********************************************************************************
*/
public class ExcelReport {
	private List _dataSource = null;
	private Map _mapParam = new HashMap();
	private String _strStyleFile = null;
	private String _strFileName = null;
	public boolean IS_ONE_PAGE_PER_SHEET = false;
	public String[] SHEET_NAMES = null;
	
	private boolean _blnExcelFromCsv = false;
	private String _strCsvFile = null;
	private String _strSheetNameFile = null;
	private String _strRetState = null;
	private String _strRetInfo = null;

	/***** 2009/08/03 INSERT START *****/
	private ArrayList<String> _aryResources = new ArrayList<String>();
	private ArrayList<String> _aryResourcesTemp = new ArrayList<String>();
	private ArrayList<ExcelReport> _aryExcelReport;	
	private boolean _blnSortPage = false;
	private boolean _blnSortPageByNum = false;
	private String _strSortPageField = null;
	private ArrayList<String> _arySubStyles = null;
	/***** 2009/08/03 INSERT E N D *****/
	
	public ExcelReport() {
		super();
		_strRetState = "";
		_strRetInfo = "";
	}
	/**
	 * @return Returns the dataSource.
	 */
	public List getDataSource() {
		return _dataSource;
	}
	/**
	 * @param dataSource The dataSource to set.
	 */
	public void setDataSource(List dataSource) {
		_dataSource = dataSource;
	}
	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return _strFileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String strFileName) {
		_strFileName = strFileName;
	}
	/**
	 * @return Returns the parameter.
	 */
	public Map getParameter() {
		return _mapParam;
	}
	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(Map mapParam) {
		/***** 2009/09/11 INSERT START *****/
		//_mapParam = mapParam;
		if (mapParam != null) {
			_mapParam = mapParam;
		} else {
			_mapParam = new HashMap();
		}
		/***** 2009/09/11 INSERT E N D *****/
	}
	/**
	 * @return Returns the styleFile.
	 */
	public String getStyleFile() {
		return _strStyleFile;
	}
	/**
	 * @param styleFile The styleFile to set.
	 */
	public void setStyleFile(String strStyleFile) {
		_strStyleFile = strStyleFile;
	}

	/**
	 * @return Returns the ExcelFromCsv.
	 */
	public boolean getExcelFromCsv() {
		return _blnExcelFromCsv;
	}
	
	/**
	 * @param ExcelFromCsv The PrintFromCsv to set.
	 */
	public void setExcelFromCsv(boolean blnExcelFromCsv) {
		_blnExcelFromCsv = blnExcelFromCsv;
	}

	/**
	 * @return Returns the CsvFile.
	 */
	public String getCsvFile() {
		return _strCsvFile;
	}
	
	/**
	 * @param CsvFile The CsvFile to set.
	 */
	public void setCsvFile(String strCsvFile) {
		_strCsvFile = strCsvFile;
	}

	/**
	 * @return Returns the SheetNameFile.
	 */
	public String getSheetNameFile() {
		return _strSheetNameFile;
	}
	
	/**
	 * @param SheetNameFile The SheetNameFile to set.
	 */
	public void setSheetNameFile(String strSheetNameFile) {
		_strSheetNameFile = strSheetNameFile;
	}

	/**
	 * Jasperのデザインファイルが存在するかどうかのチェック
	 */
	public static boolean isExistStyleFile(String strFile){
		File fileStyle;
		fileStyle = new File(HK_Data.getRealPath() + "/" + PrinterReport.REPORT_DIR + "/" +strFile);
		return fileStyle.exists();
	}	

	/**
	 * @return Returns State.
	 */
	public String getReturnState() {
		return _strRetState;
	}
	
	/**
	 * @param State The State to set.
	 */
	public void setReturnState(String strState) {
		_strRetState = strState;
	}

	/**
	 * @return Returns Info.
	 */
	public String getReturnInfo() {
		return _strRetInfo;
	}
	
	/**
	 * @param Info The Info to set.
	 */
	public void setReturnInfo(String strInfo) {
		_strRetInfo= strInfo;
	}

	/***** 2009/08/03 INSERT START *****/
	/**
	 * @param FileName Resources FileName to add.
	 */
	public void addResources(String strFileName){
		_aryResources.add(strFileName);
	}

	/**
	 * @return Resources FileName to add.
	 */
	public List<String> getResources(){
		return _aryResources;
	}

	/**
	 * @param FileName Resources FileName to add.
	 */
	public void addResourcesTemp(String strFileName){
		_aryResourcesTemp.add(strFileName);
	}

	/**
	 * @return Resources FileName to add.
	 */
	public List<String> getResourcesTemp(){
		return _aryResourcesTemp;
	}
	
	/**
	 * 複数のレポートを一気に印刷する時にレポート蓄積するプロパティ
	 */
	public void addReport(ExcelReport excelReport){
		if (_aryExcelReport == null) {
			_aryExcelReport = new ArrayList<ExcelReport>();
		}
		if (excelReport != null) {
			_aryExcelReport.add(excelReport);
		}
	}

	public ArrayList<ExcelReport> getAddedReports(){
		return _aryExcelReport;
	}
	
	/**
	 * @param SortPage true/false The SortPage true/false to set.
	 */
	public void setSortPage(boolean blnSortPage) {
		_blnSortPage = blnSortPage;
	}
	/**
	 * @return Returns SortPage true/false.
	 */
	public boolean getSortPage() {
		return _blnSortPage;
	}
	
	/**
	 * @param SortPageByNum true/false The SortPageByNum true/false to set.
	 */
	public void setSortPageByNum(boolean blnSortPageByNum) {
		_blnSortPageByNum = blnSortPageByNum;
	}
	/**
	 * @return Returns SortPageByNum true/false.
	 */
	public boolean getSortPageByNum() {
		return _blnSortPageByNum;
	}

	/**
	 * @param SortPageField The SortPageField to set.
	 */
	public void setSortPageField(String strSortPageField) {
		_strSortPageField = strSortPageField;
	}
	/**
	 * @return Returns SortPageField
	 */
	public String getSortPageField() {
		return _strSortPageField;
	}

	/**
	 * サブレポートを個別に設定するプロパティ
	 */
	public void addSubStyle(String strSubStyle){
		if (_arySubStyles == null) {
			_arySubStyles = new ArrayList<String>();
		}
		if (strSubStyle != null) {
			_arySubStyles.add(strSubStyle);
		}
	}

	public ArrayList<String> getSubStyle(){
		return _arySubStyles;
	}
	/***** 2009/08/03 INSERT E N D *****/

}
