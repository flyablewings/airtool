package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C02_Country.java
   Program Name  : 読込クラス (C02_Country)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C02_Country {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 国コード **/
    private int _intC02_CD;
    public void setC02_CD(int intC02_CD) {
        _intC02_CD = intC02_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 国名 **/
    private String _strC02_NM;
    public String getC02_NM() {
        return _strC02_NM;
    }

    /** 国名英字 **/
    private String _strC02_NM_E;
    public String getC02_NM_E() {
        return _strC02_NM_E;
    }

    /** 国名略称 **/
    private String _strC02_RN;
    public String getC02_RN() {
        return _strC02_RN;
    }

    /** 国名略称英字 **/
    private String _strC02_RN_E;
    public String getC02_RN_E() {
        return _strC02_RN_E;
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
        stbSql.append("\n" + "    C02_NM,");
        stbSql.append("\n" + "    C02_NM_E,");
        stbSql.append("\n" + "    C02_RN,");
        stbSql.append("\n" + "    C02_RN_E");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C02_Country");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C02_CD = " + String.valueOf(_intC02_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC02_NM = dbr.getString("C02_NM");
                _strC02_NM_E = dbr.getString("C02_NM_E");
                _strC02_RN = dbr.getString("C02_RN");
                _strC02_RN_E = dbr.getString("C02_RN_E");

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

