package com.project.dbread;
import com.hokukou.database.*;
/*
********************************************************************************
================================================================================
   メソフィア　共通プログラム
================================================================================
   Program Number  : A10_Zip.java
   Program Name    : 共通関数群(郵便表読込)
   Program Date    : 2007/05/28
   Programmer      : 劉斌
   
===< Update History >===========================================================
   2007/05/28 : 新規作成
   2007/08/30 : 修正		＝　張飛
   2007/11/01 : 修正		＝　山本
================================================================================
********************************************************************************
*/
public class A10_Zip {
	/** * 郵便番号 * */
	private String _strA10_Zip = "";
	
	/** * 複数の郵便番号 * */
	private boolean _blnDouZip = false;
	
	/** * 管理番号 * */
	private String _strA10_ZipSEQ = "";
	
	/** * 住所 * */
	private String _strAddress = "";
	
	/** * 都道府県名 * */
	private String _strA10_KenNM = "";
	
	/** * 市区町村名 * */
	private String _strA10_ShiNM = "";
	
	/** * 町域名 * */
	private String _strA10_ChoNM = "";

	/** * 件数 * */
	private String _strCount = "0";
	
	public void setA10_Zip(String strValue) {
		_strA10_Zip = strValue;
	}
	/*
	********************************************************************************
	*** 複数の郵便番号を戻るかどうか
	********************************************************************************
	*/
	public boolean IsDouZip() {
		return _blnDouZip;
	}

	public String getA10_ZipSEQ() {
		return _strA10_ZipSEQ;
	}
	
	public void setA10_ZipSEQ(String A10_ZipSEQ){
		this._strA10_ZipSEQ = A10_ZipSEQ;
	}

	public String getAddress() {
		return this._strAddress;
	}

	/*
	********************************************************************************
	*** 郵便表の読込処理
	********************************************************************************
	*/
	public boolean read(DB_Connection dbc) throws Exception {
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		StringBuffer stbSql = new StringBuffer();
		boolean blnExist = false;

		try{
			stbSql.append("\n" + "SELECT");
			stbSql.append("\n" + "    A10_ZipSEQ,");
			stbSql.append("\n" + "    A10_KenNM,");
			stbSql.append("\n" + "    A10_ShiNM,");
			stbSql.append("\n" + "    A10_ChoNM");
			stbSql.append("\n" + "FROM");
			stbSql.append("\n" + "    A10_Zip");
			stbSql.append("\n" + "WHERE");
			stbSql.append("\n"+ " 	1 = 1");
			
			if (!_strA10_Zip.equals("") ){
				stbSql.append("\n"+ "AND");
				stbSql.append("\n"+ "    A10_Zip   = '" + DB_CtrlChar.edit(_strA10_Zip) + "'");
			}
			if(!_strA10_ZipSEQ.equals("")){
				stbSql.append("\n"+ "AND");
				stbSql.append("\n"+ "    A10_ZipSEQ  = " + Integer.parseInt(_strA10_ZipSEQ));
			}
			stbSql.append("\n" + "ORDER BY");
			stbSql.append("\n" + "    A10_Zip, A10_ZipSEQ");
			stbSql.append("\n" + ";");


			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();

			int intRows = 0;

			while (dbr.next()) {
				blnExist = true;
				intRows++;

				if (intRows > 1) {
					_blnDouZip = true;
					break;
				}

				_strA10_ZipSEQ = String.valueOf(dbr.getInt("A10_ZipSEQ"));
				_strAddress = dbr.getString("A10_KenNM") +
							  dbr.getString("A10_ShiNM") + 
							  dbr.getString("A10_ChoNM");
				
				_strA10_KenNM = dbr.getString("A10_KenNM");
				_strA10_ShiNM = dbr.getString("A10_ShiNM");
				_strA10_ChoNM = dbr.getString("A10_ChoNM");
			}
		} catch (DB_Exception e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
		}
		return blnExist;
	}
	
	public String getA10_KenNM() {
		return _strA10_KenNM;
	}
	public void setA10_KenNM(String kenNM) {
		_strA10_KenNM = kenNM;
	}
	public String getA10_ShiNM() {
		return _strA10_ShiNM;
	}
	public void setA10_ShiNM(String shiNM) {
		_strA10_ShiNM = shiNM;
	}
	public String getA10_ChoNM() {
		return _strA10_ChoNM;
	}
	public void setA10_ChoNM(String choNM) {
		_strA10_ChoNM = choNM;
	}

	/*
	********************************************************************************
	*** 郵便表の読込処理(件数取得)
	********************************************************************************
	*/
	public void readCount(DB_Connection dbc) throws Exception {
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		StringBuffer stbSql = new StringBuffer();

		try{
			stbSql.append("\n" + "SELECT");
			stbSql.append("\n" + "    COUNT(*)	AS Count");
			stbSql.append("\n" + "FROM");
			stbSql.append("\n" + "    A10_Zip");
			stbSql.append("\n" + "WHERE");
			stbSql.append("\n"+ "    A10_Zip   = '" + DB_CtrlChar.edit(_strA10_Zip) + "'");
			stbSql.append("\n" + ";");

			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();

			_strCount = "0";
			
			if (dbr.next()) {
				_strCount = dbr.getString("Count");
			}

		} catch (DB_Exception e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
		}
	}
	
	public String getCount() {
		return _strCount;
	}
}
