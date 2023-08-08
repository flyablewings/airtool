package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C11_Section.java
   Program Name  : 読込クラス (C11_Section)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C11_Section {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 部課コード **/
    private String _strC11_CD;
    public void setC11_CD(String strC11_CD) {
        _strC11_CD = strC11_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 部課名称 **/
    private String _strC11_NM;
    public String getC11_NM() {
        return _strC11_NM;
    }

    /** 部課名称英字 **/
    private String _strC11_NM_E;
    public String getC11_NM_E() {
        return _strC11_NM_E;
    }

    /** 部課略称 **/
    private String _strC11_RN;
    public String getC11_RN() {
        return _strC11_RN;
    }

    /** 部課略称英字 **/
    private String _strC11_RN_E;
    public String getC11_RN_E() {
        return _strC11_RN_E;
    }

    /** 使用開始日付 **/
    private int _intC11_Start;
    public int getC11_Start() {
        return _intC11_Start;
    }

    /** 使用終了日付 **/
    private int _intC11_End;
    public int getC11_End() {
        return _intC11_End;
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
        stbSql.append("\n" + "    C11_NM,");
        stbSql.append("\n" + "    C11_NM_E,");
        stbSql.append("\n" + "    C11_RN,");
        stbSql.append("\n" + "    C11_RN_E,");
        stbSql.append("\n" + "    C11_Start,");
        stbSql.append("\n" + "    C11_End");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C11_Section");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C11_CD = '" + DB_CtrlChar.edit(_strC11_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC11_NM = dbr.getString("C11_NM");
                _strC11_NM_E = dbr.getString("C11_NM_E");
                _strC11_RN = dbr.getString("C11_RN");
                _strC11_RN_E = dbr.getString("C11_RN_E");
                _intC11_Start = dbr.getInt("C11_Start");
                _intC11_End = dbr.getInt("C11_End");

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

