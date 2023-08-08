package com.hokukou.report;

import java.util.Map;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : iHK_ReportOut.java
	Program Name    : 印刷処理のインターフェイス
	Program Date    : 2008/07/06
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/06  	: 新規作成
================================================================================
********************************************************************************
*/

import com.hokukou.database.DB_Exception;

public interface iHK_ReportOut {
	public PrinterReport getPrinterReportRes(Map map) throws DB_Exception, Exception;
	public ExcelReport getExcelReportRes(Map map) throws DB_Exception, Exception;
	public CsvReport getCsvReportRes(Map map);
}
