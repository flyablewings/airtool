package com.hokukou.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : ExcelReportCollection.java
	Program Name    : １つのレポートに必要な情報をCollection管理する
	Program Date    : 2009/08/04
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2009/08/04  : 新規作成
================================================================================
********************************************************************************
*/
public class ExcelReportCollection {
	
	private ArrayList<HK_ReportResources> _aryResources = null;
	private Collection _clcDataSource = null;
	private Map _mapRptParam = null;
	private ArrayList<HK_ReportResources> _aryStyles;
	private boolean _blnOnePagePerSheet;
	private String[] _strSheetNames;
	
	/**
	 * リソース情報のプロパティ
	 */
	public void setResources(ArrayList<HK_ReportResources> aryResources){
		_aryResources = aryResources;
	}
	public ArrayList<HK_ReportResources> getResources(){
		return _aryResources;
	}

	/**
	 * データソース情報のプロパティ
	 */
	public void setDataSource(Collection clcDataSource){
		_clcDataSource = clcDataSource;
	}
	public Collection getDataSource(){
		return _clcDataSource;
	}

	/**
	 * レポートパラメータ情報のプロパティ
	 */
	public void setRptParam(Map mapRptParam){
		_mapRptParam = mapRptParam;
	}
	public Map getRptParam(){
		return _mapRptParam;
	}

	/**
	 * スタイルファイル情報のプロパティ
	 */
	public void setStyles(ArrayList<HK_ReportResources> aryStyles){
		_aryStyles = aryStyles;
	}
	public ArrayList<HK_ReportResources> getStyles(){
		return _aryStyles;
	}


	/**
	 * OnePagePerSheet情報のプロパティ
	 */
	public void setOnePagePerSheet(boolean blnOnePagePerSheet){
		_blnOnePagePerSheet = blnOnePagePerSheet;
	}
	public boolean getOnePagePerSheet(){
		return _blnOnePagePerSheet;
	}

	/**
	 * SheetNames情報のプロパティ
	 */
	public void setSheetNames(String[] strSheetNames){
		_strSheetNames = strSheetNames;
	}
	public String[] getSheetNames(){
		return _strSheetNames;
	}
}
