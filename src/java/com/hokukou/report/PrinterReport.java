package com.hokukou.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.hokukou.data.HK_Data;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : PrinterReport.java
	Program Name    : 印刷用の設定情報管理
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
	2008/10/25  : JasperのFieldを取得する処理を追加                 = H.Fujimoto
	2008/10/25  : 複数レポート印刷への対応                          = H.Fujimoto
	2009/08/03  : １ジョブ化の際の並び替え情報管理機能追加          = H.Fujimoto
	2009/11/12  : PDFのﾌｧｲﾙ分割機能の追加          					= H.Fujimoto
================================================================================
********************************************************************************
*/
public class PrinterReport {

	public static final String REPORT_DIR = "reports";

	private List dataSource = null;
	private Map _mapParam = new HashMap();
	private String _strStyleFile = null;
	private boolean _blnPrintFromCsv = false;
	private String _strCsvFile = null;
	private String _strRetState = null;
	private String _strRetInfo = null;
	private ArrayList<String> _aryResources = new ArrayList<String>();
	private ArrayList<String> _aryResourcesTemp = new ArrayList<String>();
	private ArrayList<PrinterReport> _aryPrinterReport;
	
	/***** 2009/08/03 INSERT START *****/
	private boolean _blnSortPage = false;
	private boolean _blnSortPageByNum = false;
	private String _strSortPageField = null;
	private ArrayList<String> _arySubStyles = null;
	/***** 2009/08/03 INSERT E N D *****/

	/***** 2009/11/12 INSERT START *****/
	private boolean _blnIsPdfPageSeparate = false;
	private String _strPdfPageSeparateField = null;
	private String _strPdfFileNMField = null;
	private boolean _blnIsDeleteTempFile = true;
	/***** 2009/11/12 INSERT E N D *****/
	
	public PrinterReport() {
		super();
		_strRetState = "";
		_strRetInfo = "";
		_aryPrinterReport = null;
	}
	/**
	 * @return Returns the dataSource.
	 */
	public List getDataSource() {
		return dataSource;
	}
	/**
	 * @param dataSource The dataSource to set.
	 */
	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * @return Returns the parameter.
	 */
	public Map getParameter() {
		if (_mapParam == null) {
			return new HashMap(); 
		} else {
			return _mapParam;
		}
	}
	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(Map mapParam) {
		this._mapParam = mapParam;
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
		this._strStyleFile = strStyleFile;
	}

	/**
	 * @return Returns the PrintFromCsv.
	 */
	public boolean getPrintFromCsv() {
		return _blnPrintFromCsv;
	}
	
	/**
	 * @param PrintFromCsv The PrintFromCsv to set.
	 */
	public void setPrintFromCsv(boolean blnPrintFromCsv) {
		_blnPrintFromCsv = blnPrintFromCsv;
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
	 * Jasperのデザインファイルが存在するかどうかのチェック
	 */
	public static boolean isExistStyleFile(String strFile){
		File fileStyle;
		fileStyle = new File(HK_Data.getRealPath() + "/" + REPORT_DIR + "/" +strFile);
		return fileStyle.exists();
	}	

	/**
	 * JasperのFieldを取得する処理
	 * @throws JRException 
	 */
	public static String[] getFieldsStr(String strJasperFullPath) throws JRException {
		ArrayList<String> aryFieldNM;
		String[] strFieldNMs;
		aryFieldNM = _getFieldsByArray(strJasperFullPath);
		strFieldNMs = aryFieldNM.toArray(new String[0]);
		Arrays.sort(strFieldNMs);
		return strFieldNMs;
	}	

	public static ArrayList<String> getFieldsAry(String strJasperFullPath) throws JRException{
		return _getFieldsByArray(strJasperFullPath);
	}	
	private static ArrayList<String> _getFieldsByArray(String strJasperFullPath) throws JRException {
		Object objJasper;
		JasperReport jrrCompliedObj;
		JRField[] jrfFields;
		ArrayList<String> aryFieldNM;
		
		aryFieldNM = new ArrayList<String>();
		//HK_Data.getRealPath() + "/" + REPORT_DIR + "/" + strFile
		objJasper = JRLoader.loadObject(strJasperFullPath);
		if (objJasper instanceof JasperReport) {
			jrrCompliedObj = (JasperReport)objJasper;
			jrfFields = jrrCompliedObj.getFields();
			for (int intIdx = 0; intIdx < jrfFields.length; intIdx++) {
				aryFieldNM.add(jrfFields[intIdx].getName());
			}
		}
		
		return aryFieldNM;
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
	public void addReport(PrinterReport printerReport){
		if (_aryPrinterReport == null) {
			_aryPrinterReport = new ArrayList<PrinterReport>();
		}
		if (printerReport != null) {
			_aryPrinterReport.add(printerReport);
		}
	}

	public ArrayList<PrinterReport> getAddedReports(){
		return _aryPrinterReport;
	}

	/***** 2009/08/03 INSERT START *****/
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
	
	/***** 2009/11/12 INSERT START *****/
	/**
	 * @param PdfPageSeparate true/false The PdfPageSeparate true/false to set.
	 */
	public void setPdfIsPageSeparate(boolean blnPdfPageSeparate) {
		_blnIsPdfPageSeparate = blnPdfPageSeparate;
	}
	/**
	 * @return Returns PdfPageSeparate true/false.
	 */
	public boolean getPdfIsPageSeparate() {
		return _blnIsPdfPageSeparate;
	}
	/**
	 * @param strSortPageField The strSortPageField to set.
	 */
	public void setPdfPageSeparateField(String strSortPageField) {
		_strPdfPageSeparateField = strSortPageField;
	}
	/**
	 * @return Returns strSortPageField
	 */
	public String getPdfPageSeparateField() {
		return _strPdfPageSeparateField;
	}
	/**
	 * @param PdfFileNMField The PdfFileNMField to set.
	 */
	public void setPdfFileNMField(String strPdfFileNMField) {
		_strPdfFileNMField = strPdfFileNMField;
	}
	/**
	 * @return Returns PdfFileNMField
	 */
	public String getPdfFileNMField() {
		return _strPdfFileNMField;
	}
	/**
	 * @param IsDeleteTempFile true/false The IsDeleteTempFile true/false to set.
	 */
	public void setIsDeleteTempFile(boolean blnIsDeleteTempFile) {
		_blnIsDeleteTempFile = blnIsDeleteTempFile;
	}
	/**
	 * @return Returns IsDeleteTempFile true/false.
	 */
	public boolean getIsDeleteTempFile() {
		return _blnIsDeleteTempFile;
	}
	/***** 2009/11/12 INSERT E N D *****/
}
