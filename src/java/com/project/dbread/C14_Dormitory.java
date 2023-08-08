package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C14_Dormitory.java
   Program Name  : 読込クラス (C14_Dormitory)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C14_Dormitory {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 寮コード **/
    private int _intC14_CD;
    public void setC14_CD(int intC14_CD) {
        _intC14_CD = intC14_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 寮名 **/
    private String _strC14_NM;
    public String getC14_NM() {
        return _strC14_NM;
    }

    /** 郵便番号 **/
    private String _strC14_Zip;
    public String getC14_Zip() {
        return _strC14_Zip;
    }

    /** 住所1 **/
    private String _strC14_Adres1;
    public String getC14_Adres1() {
        return _strC14_Adres1;
    }

    /** 住所2 **/
    private String _strC14_Adres2;
    public String getC14_Adres2() {
        return _strC14_Adres2;
    }

    /** 電話番号 **/
    private String _strC14_Tel;
    public String getC14_Tel() {
        return _strC14_Tel;
    }

    /** 管理者名 **/
    private String _strC14_AdminNM;
    public String getC14_AdminNM() {
        return _strC14_AdminNM;
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
        stbSql.append("\n" + "    C14_NM,");
        stbSql.append("\n" + "    C14_Zip,");
        stbSql.append("\n" + "    C14_Adres1,");
        stbSql.append("\n" + "    C14_Adres2,");
        stbSql.append("\n" + "    C14_Tel,");
        stbSql.append("\n" + "    C14_AdminNM");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C14_Dormitory");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C14_CD = " + String.valueOf(_intC14_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC14_NM = dbr.getString("C14_NM");
                _strC14_Zip = dbr.getString("C14_Zip");
                _strC14_Adres1 = dbr.getString("C14_Adres1");
                _strC14_Adres2 = dbr.getString("C14_Adres2");
                _strC14_Tel = dbr.getString("C14_Tel");
                _strC14_AdminNM = dbr.getString("C14_AdminNM");

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

