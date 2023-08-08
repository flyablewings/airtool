package com.hokukou.report;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_JRCsvDataSource.java
	Program Name    : JRCsvDataSourceのiReport用カスタムクラス
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
================================================================================
********************************************************************************
*/
public class HK_JRCsvDataSource extends JRCsvDataSource {

	/**
	 * コンストラクター
	 * useFirstRowAsHeader = true
	 * fieldDelimiter = TAB
	 * を初期設定
	 */
	public HK_JRCsvDataSource(File arg0) throws FileNotFoundException {
		super(arg0);
		this.setUseFirstRowAsHeader(true);
		this.setFieldDelimiter('\t');
	}

	/**
	 * override 次のデータが無い時は、自動的にcloseを実行
	 */
	public boolean next() throws JRException {
		boolean blnNext;
		blnNext = super.next();
		if (blnNext == false) {
			this.close();
		}
		return blnNext;
	}

}
