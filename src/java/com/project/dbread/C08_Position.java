package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C08_Position.java
   Program Name  : 読込クラス (C08_Position)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C08_Position {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 役職コード **/
    private String _strC08_CD;
    public void setC08_CD(String strC08_CD) {
        _strC08_CD = strC08_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 役職名称 **/
    private String _strC08_NM;
    public String getC08_NM() {
        return _strC08_NM;
    }

    /** 役職名称英字 **/
    private String _strC08_NM_E;
    public String getC08_NM_E() {
        return _strC08_NM_E;
    }

    /** 役職略称 **/
    private String _strC08_RN;
    public String getC08_RN() {
        return _strC08_RN;
    }

    /** 役職略称英字 **/
    private String _strC08_RN_E;
    public String getC08_RN_E() {
        return _strC08_RN_E;
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
        stbSql.append("\n" + "    C08_NM,");
        stbSql.append("\n" + "    C08_NM_E,");
        stbSql.append("\n" + "    C08_RN,");
        stbSql.append("\n" + "    C08_RN_E");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C08_Position");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C08_CD = '" + DB_CtrlChar.edit(_strC08_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC08_NM = dbr.getString("C08_NM");
                _strC08_NM_E = dbr.getString("C08_NM_E");
                _strC08_RN = dbr.getString("C08_RN");
                _strC08_RN_E = dbr.getString("C08_RN_E");

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

