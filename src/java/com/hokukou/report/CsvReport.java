package com.hokukou.report;

import java.util.List;
import java.util.Map;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : CsvReport.java
	Program Name    : Csv保存用の設定情報管理
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
================================================================================
********************************************************************************
*/

public class CsvReport {
	private String fileName = null;
	private List head = null;
	private Map headMap = null;
	private List dataSource = null;
	public CsvReport() {
		super();
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	/**
	 * @return Returns the head.
	 */
	public List getHead() {
		return head;
	}

	/**
	 * @param head The head to set.
	 */
	public void setHead(List head) {
		this.head = head;
	}

	/**
	 * @return Returns the headMap.
	 */
	public Map getHeadMap() {
		return headMap;
	}

	/**
	 * @param headMap The headMap to set.
	 */
	public void setHeadMap(Map headMap) {
		this.headMap = headMap;
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
	

}
