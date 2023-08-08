package com.hokukou.report;

import java.io.File;
import java.io.FileOutputStream;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.hokukou.HK_Cnst;
import com.hokukou.data.HK_Data;
import com.hokukou.file.FolderDownload;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : JExcelLib.java
	Program Name    : JExcel操作用
	Program Date    : 2009/02/18
	Programmer      : T.OGAWA
   
===< Update History >===========================================================
	2009/02/18  : 新規作成
	2009/11/06  : copyWorkBookFromTemplateFileのﾃﾝﾌﾟﾚｰﾄ指定をﾌﾙﾊﾟｽに変更
	2009/11/06  : SheetIndexからSheet名を取得する機能を追加
================================================================================
********************************************************************************
*/

public class JExcelLib {
	
	/**
	 * ダウンロード仮フォルダを作成する処理
	 */
	public static String makeDownloadTempDir() {

		File dirDownload = new File(HK_Data.getRealPath() + "/" + HK_Cnst.CNT_FolderDownloadTempDir);
		if (dirDownload.exists() == false) {
			dirDownload.mkdirs();
		}
		
		String strTempDir = String.valueOf(System.currentTimeMillis());
		
		File dirTempDir = new File(FolderDownload.getDownloadTempDirFull(strTempDir));
		if (dirTempDir.exists() == false) {
			dirTempDir.mkdirs();
		}
		
		return strTempDir;
	}
	
	/**
	 * テンプレートファイルのフルパスを取得する処理
	 */
	public static String getTemplateFull(String strFile) {
		return HK_Data.getRealPath() + "/" + PrinterReport.REPORT_DIR + "/" +strFile;
	}
	
	/**
	 * テンプレートファイルが存在するかどうかのチェック
	 */
	public static boolean isExistTemplate(String strFile) {
		File fileStyle = new File(JExcelLib.getTemplateFull(strFile));
		return fileStyle.exists();
	}
	
	/**
	 * セル属性をコピーする処理
	 */
	public static void copyCellStyles(WritableSheet sheet, int intRowMoto, int intRowSaki, int intColStart, int intColEnd) throws Exception {
		//行高のコピー
		sheet.setRowView(intRowSaki, sheet.getRowView(intRowMoto));
		
		//スタイルのコピー
		for (int intCol=intColStart; intCol<=intColEnd; intCol++) {
			JExcelLib.copyCellStyle(sheet, intRowMoto, intCol, intRowSaki, intCol);
		}
	}
	
	/**
	 * セル属性をコピーする処理
	 */
	public static void copyCellStyle(WritableSheet sheet, int intRowMoto, int intColMoto, int intRowSaki, int intColSaki) throws Exception {
		WritableCell cellMoto = sheet.getWritableCell(intColMoto, intRowMoto);
		WritableCell cellSaki = sheet.getWritableCell(intColSaki, intRowSaki);
		
		if (cellSaki.getType() == CellType.EMPTY) {
			if (cellMoto.getType() != CellType.ERROR) {
				sheet.addCell(cellMoto.copyTo(intColSaki, intRowSaki));
			}
		}
		cellSaki = sheet.getWritableCell(intColSaki, intRowSaki);

		if (cellMoto.getCellFormat() != null) {
			cellSaki.setCellFormat(cellMoto.getCellFormat());
		}
	}
	
	/**
	 * セルに値を設定する処理
	 */
	public static void setCellValue(WritableSheet sheet, int intRow, int intCol, Object objValue) throws Exception {
		WritableCell cell = sheet.getWritableCell(intCol, intRow);
		
		if (cell.getType() == CellType.EMPTY) {
			if (cell.getCellFormat() != null) {
				sheet.addCell(new jxl.write.Label(intCol, intRow, String.valueOf(objValue), cell.getCellFormat()));
			} else {
				sheet.addCell(new jxl.write.Label(intCol, intRow, String.valueOf(objValue)));
			}
		} else {
			if (cell.getType() == CellType.LABEL) {
			    ((jxl.write.Label) cell).setString(String.valueOf(objValue));
			} else if (cell.getType() == CellType.NUMBER) {
			    ((jxl.write.Number) cell).setValue(Double.valueOf(String.valueOf(objValue)));
			} else {
				throw new Exception("not support cellType - " + cell.getType());
			}
		}
	}

	/**
	 * セルから値を取得設定する処理
	 */
	public static String getCellText(Sheet sheet, int intRow, int intCol) throws Exception {
		
		String strReturnValue = "";
		
		Cell cell = sheet.getCell(intCol, intRow);
		
		strReturnValue = cell.getContents();
		
		//		 セルの形式に関わらず文字列として取得
//		String value = cell.getContents();
//		
//		//		 セルの形式に合わせて内容を取得
//		CellType type = cell.getType();
//		//		 真偽値
//		if (type == CellType.BOOLEAN) {
//			jxl.BooleanCell booleanCell = (jxl.BooleanCell) cell;
//			boolean value = booleanCell.getValue();
//		//		 真偽関数
//		} else if (type == CellType.BOOLEAN_FORMULA) {
//			jxl.BooleanFormulaCell booleanCell = (jxl.BooleanFormulaCell) cell;
//			// 値の確認
//		boolean value = booleanCell.getValue();
//		// 関数の確認
//		String formula = booleanCell.getFormula();
//		//		 日付
//		} else if (type == CellType.DATE) {
//			jxl.DateCell dateCell = (jxl.DateCell) cell;
//			// 値の確認
//		java.util.Date date = dateCell.getDate();
//		// 表示形式の確認
//		java.text.DateFormat dateFormat = dateCell.getDateFormat();
//		//		 日付関数
//		} else if (type == CellType.DATE_FORMULA) {
//			jxl.DateCell dateCell = (jxl.DateFormulaCell) cell;
//			// 値の確認
//		java.util.Date date = dateCell.getDate();
//		// 表示形式の確認
//		java.text.DateFormat dateFormat = dateCell.getDateFormat();
//		// 関数の確認
//		String formula = dateCell.getFormula();
//		//		 エラー
//		} else if (type == CellType.ERROR) {
//			jxl.ErrorCell errorCell = (jxl.ErrorCell) cell;
//			// エラーコードの確認
//		int errorCode = errorCell.getErrorCode();
//		//		 関数エラー
//		} else if (type == CellType.FORMULA_ERROR) {
//			jxl.ErrorFormulaCell errorCell = (jxl.ErrorFormulaCell) cell;
//			// エラーコードの確認
//		int errorCode = errorCell.getErrorCode();
//		// 関数の確認
//		String formula = errorCell.getFormula();
//		//		 文字列
//		} else if (type == CellType.LABEL) {
//			jxl.LabelCell = labelCell = (jxl.LabelCell) cell;
//			String value = labelCell.getString();
//		//		 文字列関数
//		} else if (type == CellType.STRING_FORMULA) {
//			jxl.StringFormulaCell = labelCell = (jxl.StringFormulaCell) cell;
//			// 値の確認
//		String value = labelCell.getString();
//		// 関数の確認
//		String formula = errorCell.getFormula();
//		//		 数値
//		} else if (type == CellType.NUMBER) {
//			jxl.NumberCell numberCell = (jxl.NumberCell) cell;
//			// 値の確認
//		double value = numberCell.getDate();
//		// 表示形式の確認
//			java.text.NumberFormat numberFormat = dateCell.getNumberFormat();
//		} else if (type == CellType.NUMBER_FORMULA) {
//			jxl.NumberCell numberCell = (jxl.NumberCell) cell;
//			// 値の確認double value = numberCell.getDate();
//			// 表示形式の確認java.text.NumberFormat numberFormat = dateCell.getNumberFormat();
//			// 関数の確認String formula = numberCell.getFormula();
//		}
		
		return strReturnValue;
	}

	/**
	 * セルに数式を設定する処理
	 */
	public static void setFormula(WritableSheet sheet, int intRow, int intCol, String strFormula) throws Exception {
		WritableCell cell = sheet.getWritableCell(intCol, intRow);
		
		if (cell.getCellFormat() != null) {
			sheet.addCell(new jxl.write.Formula(intCol, intRow, strFormula, cell.getCellFormat()));
		} else {
			sheet.addCell(new jxl.write.Formula(intCol, intRow, strFormula));
		}
	}

	/**
	 * シートをコピーする処理
	 */
	public static void copySheet(WritableWorkbook wb, String strSheetNameMoto, String strSheetNameSaki) {
		wb.copySheet(strSheetNameMoto, strSheetNameSaki, wb.getNumberOfSheets());
	}

	/**
	 * シートを削除する処理
	 */
	public static void deleteSheet(WritableWorkbook wb, String sheetName) {
		wb.removeSheet(JExcelLib.getSheetIndex(wb, sheetName));
	}

	/**
	 * シートIndexを取得する処理
	 */
	public static int getSheetIndex(WritableWorkbook wb, String sheetName) {
		int returnValue = -1;
		String strSheetNames[] = wb.getSheetNames();
		for (int i=0; i<strSheetNames.length; i++) {
			if (sheetName.equals(strSheetNames[i])) {
				returnValue = i;
				break;
			}
		}
		return returnValue;
	}
	
	/***** 2009/11/06 INSERT START *****/
	/**
	 * シート名を取得する処理
	 */
	public static String getSheetName(WritableWorkbook wb, int index) {
		String strSheetNM = "";
		String strSheetNames[] = wb.getSheetNames();
		
		if (index >= 0 && index <= wb.getSheetNames().length) {
			strSheetNM = strSheetNames[index];
			
		}
		return strSheetNM;
	}
	/***** 2009/11/06 INSERT E N D *****/
	
	/***** 2009/11/06 UPDATE START *****/
	///**
	// * テンプレートを読込する処理
	// */
	//public static WritableWorkbook copyWorkBookFromTemplateFile(String strTemplateFile, String strNewFullPath, FileOutputStream opsIn) throws Exception {
	//	Workbook wbInput = null;
	//	
	//	try {
	//		wbInput = Workbook.getWorkbook(new File(JExcelLib.getTemplateFull(strTemplateFile)));
	//		opsIn = new FileOutputStream(new File(strNewFullPath));
	//		
	//		//GCの設定(バグがあるので、必須)
	//		WorkbookSettings settings = new WorkbookSettings();
	//		settings.setGCDisabled(true);
	//		
	//		return Workbook.createWorkbook(opsIn, wbInput, settings);
	//		
	//	} catch (Exception e) {
	//		throw e;
	//	}finally{
	//		if (wbInput != null){ wbInput.close(); wbInput = null;}
	//	} 
	//}
	/**
	 * テンプレートを読込する処理
	 */
	public static WritableWorkbook copyWorkBookFromTemplateFile(String strTemplateFullPath, String strNewFullPath, FileOutputStream opsIn) throws Exception {
		Workbook wbInput = null;
		
		try {
			wbInput = Workbook.getWorkbook(new File(strTemplateFullPath));
			opsIn = new FileOutputStream(new File(strNewFullPath));
			
			//GCの設定(バグがあるので、必須)
			WorkbookSettings settings = new WorkbookSettings();
			settings.setGCDisabled(true);
			
			return Workbook.createWorkbook(opsIn, wbInput, settings);
			
		} catch (Exception e) {
			throw e;
		}finally{
			if (wbInput != null){ wbInput.close(); wbInput = null;}
		} 
	}
	/***** 2009/11/06 UPDATE E N D *****/

	/**
	 * ワークブックを読込する処理
	 */
	public static Workbook getWorkBookFromFile(String strFullPath) throws Exception {
		return Workbook.getWorkbook(new File(strFullPath));
	}
	
	/**
	 * ワークブックを保存する処理
	 */
	public static void storeWorkBook(WritableWorkbook wb) throws Exception {
		wb.write();
	}
}
