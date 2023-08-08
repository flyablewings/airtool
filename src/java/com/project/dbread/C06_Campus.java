package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C06_Campus.java
   Program Name  : 読込クラス (C06_Campus)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C06_Campus {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** キャンパスコード **/
    private int _intC06_CD;
    public void setC06_CD(int intC06_CD) {
        _intC06_CD = intC06_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** キャンパス名称 **/
    private String _strC06_NM;
    public String getC06_NM() {
        return _strC06_NM;
    }

    /** キャンパス名称英字 **/
    private String _strC06_NM_E;
    public String getC06_NM_E() {
        return _strC06_NM_E;
    }

    /** キャンパス略称 **/
    private String _strC06_RN;
    public String getC06_RN() {
        return _strC06_RN;
    }

    /** 郵便番号 **/
    private String _strC06_Zip;
    public String getC06_Zip() {
        return _strC06_Zip;
    }

    /** 住所１ **/
    private String _strC06_Adrs1;
    public String getC06_Adrs1() {
        return _strC06_Adrs1;
    }

    /** 住所２ **/
    private String _strC06_Adrs2;
    public String getC06_Adrs2() {
        return _strC06_Adrs2;
    }

    /** 電話番号 **/
    private String _strC06_Tel;
    public String getC06_Tel() {
        return _strC06_Tel;
    }

    /** FAX番号 **/
    private String _strC06_Fax;
    public String getC06_Fax() {
        return _strC06_Fax;
    }

    /*--------------------------------------------------*
     *--- メインの処理                               ---*
     *--------------------------------------------------*/

    public boolean read(DB_Connection dbc) throws DB_Exception, Exception {
        DB_Statement dbs = null;
        DB_ResultSet dbr = null;

        boolean blnExist;
        StringBuffer stbSql = new StringBuffer();

        stbSql.append("\n" + "SELECT");
        stbSql.append("\n" + "    C06_NM,");
        stbSql.append("\n" + "    C06_NM_E,");
        stbSql.append("\n" + "    C06_RN,");
        stbSql.append("\n" + "    C06_Zip,");
        stbSql.append("\n" + "    C06_Adrs1,");
        stbSql.append("\n" + "    C06_Adrs2,");
        stbSql.append("\n" + "    C06_Tel,");
        stbSql.append("\n" + "    C06_Fax");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C06_Campus");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C06_CD = " + String.valueOf(_intC06_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC06_NM = dbr.getString("C06_NM");
                _strC06_NM_E = dbr.getString("C06_NM_E");
                _strC06_RN = dbr.getString("C06_RN");
                _strC06_Zip = dbr.getString("C06_Zip");
                _strC06_Adrs1 = dbr.getString("C06_Adrs1");
                _strC06_Adrs2 = dbr.getString("C06_Adrs2");
                _strC06_Tel = dbr.getString("C06_Tel");
                _strC06_Fax = dbr.getString("C06_Fax");

                blnExist = true;
            }

        } catch (DB_Exception e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (dbr != null) {
                dbr.close();
                dbr = null;
            }
            if (dbs != null) {
                dbs.close();
                dbs = null;
            }
        }

        return blnExist;
    }

}

