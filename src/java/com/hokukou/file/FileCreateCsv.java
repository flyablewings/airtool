package com.hokukou.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import com.hokukou.data.HK_Data;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : FileCreateCsv.java
	Program Name    : テキスト出力用のCSVファイル管理
	Program Date    : 2009/02/25
	Programmer      : T.OHORI
   
===< Update History >===========================================================
	2009/02/25  : 新規作成
================================================================================
********************************************************************************
*/

public class FileCreateCsv {

	public static final String REPORT_TEMP_DIR = "reportsTemp";
	public static final String CLASSES_DIR = "WEB-INF/classes";
	
	private String[] _strColName;
	private File _fileCsv = null;
	private FileOutputStream _fosCsv = null;
	private OutputStreamWriter _oswWriter = null;
	private BufferedWriter _fileWriter = null;
	
	private boolean _blnIsAddData;
	private String _strPrefix;
	
	/**
	 * コンストラクター
	 */
	public FileCreateCsv(String[] strColName) {
		_strColName = strColName;
	}

	/**
	 * ファイルのフルパスを取得
	 */
	public String getFullFilePath() {
		return _fileCsv.toString();
	}
	
	public String getName(){
		return _fileCsv.getName();
	}

	/**
	 * ファイルの拡張子を除いた部分を取得
	 */
	public String getFilePrefix() {
		return _strPrefix;
	}
	
	/**
	 * CSVファイルを作成する処理（２日以上前のファイルが残っていれば削除する）
	 * ファイル名は、YYYYMMDD-HHMMDD-99999.csv
	 */
	public String createCsvFileName(String strBase) throws IOException {
		File fileCsvDir;
		//StringBuffer stbSql;
		String strFileName;
		String strPrefix;
		int intCnt;
		
		//========== CSVファイルの保存場所を作成 ==========
		fileCsvDir = new File(HK_Data.getRealPath() + "/" +  REPORT_TEMP_DIR);
		if (!fileCsvDir.exists()) {
			fileCsvDir.mkdirs();
		}

		//========== ２日以上前のファイルがあるときは削除 ==========
		fileCsvDir = new File(HK_Data.getRealPath() + "/" +  REPORT_TEMP_DIR);
		File[] fileContents = fileCsvDir.listFiles();
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
					fileContents[intIdx].delete();
				}
			}
		}
		
		//========== 基本ファイル名を作成 ==========
		if (strBase.length() == 0) {
			strPrefix = HK_Data.getDate("") + "-" + HK_Data.getTime("") ;
		} else {
			strPrefix = strBase;
		}

		//========== CSVファイルを作成 ==========
		
		intCnt = 0;
		while(true){
			intCnt ++;
			strFileName = strPrefix + "-" + HK_Data.Format(intCnt, "00000", 5);
			
			_fileCsv = new File(HK_Data.getRealPath() + "/" + REPORT_TEMP_DIR + "/" + strFileName + ".csv");
			
			if (_fileCsv.exists() == false) {
				_strPrefix = strFileName;
				break;
			}
			if (intCnt > 99999) {
				new IOException("印刷データを格納するファイルが作成できませんでした。");
			}
		}
		
		_fileCsv.createNewFile();
		_fosCsv = new FileOutputStream(_fileCsv);
		/***** 2008/08/30 UPDATE START *****/ 
		// SJIS では、"～"などの文字が一部文字化けするため
		//_oswWriter = new OutputStreamWriter(_fosCsv, "SJIS");
		_oswWriter = new OutputStreamWriter(_fosCsv, "MS932");
		/***** 2008/08/30 UPDATE E N D *****/
		_fileWriter = new BufferedWriter(_oswWriter);
		
		//========== タイトル部分をファイルに書込み==========
		StringBuffer stbSql = new StringBuffer();
		for (int intIdx = 0;intIdx < _strColName.length; intIdx++) {
			if (intIdx != 0) {
				stbSql.append(",");
			}
			stbSql.append(_strColName[intIdx]);
		}

		_fileWriter.write(stbSql.toString(), 0, stbSql.toString().length());
		_fileWriter.newLine();
		_fileWriter.flush();
		
		_blnIsAddData = false;
		return _fileCsv.getName(); 
	}

	/**
	 * CSVファイルにデータを設定する処理
	 */
	public void setData(HashMap hsmRow) throws IOException {
		StringBuffer stbSql;

		stbSql = new StringBuffer();
		for (int intIdx = 0;intIdx < _strColName.length; intIdx++) {
			if (intIdx != 0) {
				stbSql.append(",");
			}
			
			stbSql.append("\"");
			if (hsmRow.get(_strColName[intIdx]) != null) {
				stbSql.append(hsmRow.get(_strColName[intIdx]).toString().replaceAll("\"", "\"\""));
			}
			stbSql.append("\"");
		}

		_fileWriter.write(stbSql.toString(), 0, stbSql.toString().length());
		_fileWriter.newLine();
		_fileWriter.flush();
		_blnIsAddData = true;
	}

	/**
	 * CSVファイルを閉じる処理
	 */
	public void close() {
	    try {
	        if (_fileWriter != null) {
	        	_fileWriter.flush();
	        	_fileWriter.close();
	        }
	        if (_oswWriter != null) {
	        	_oswWriter.close();
	        }
	        if (_fosCsv != null) {
	        	_fosCsv.close();
	        }
	        if (_blnIsAddData == false) {
	        	_fileCsv.delete();
	        }
	        if (_fileCsv != null) {
				_fileCsv = null;
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	}

}
