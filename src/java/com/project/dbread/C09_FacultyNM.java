package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C09_FacultyNM.java
   Program Name  : 読込クラス (C09_FacultyNM)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C09_FacultyNM {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 学部学科名コード **/
    private String _strC09_CD;
    public void setC09_CD(String strC09_CD) {
        _strC09_CD = strC09_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 学部学科名称 **/
    private String _strC09_NM;
    public String getC09_NM() {
        return _strC09_NM;
    }

    /** 学部学科名称英字 **/
    private String _strC09_NM_E;
    public String getC09_NM_E() {
        return _strC09_NM_E;
    }

    /** 学部学科名略称 **/
    private String _strC09_RN;
    public String getC09_RN() {
        return _strC09_RN;
    }

    /** 学部学科名略称英字 **/
    private String _strC09_RN_E;
    public String getC09_RN_E() {
        return _strC09_RN_E;
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
        stbSql.append("\n" + "    C09_NM,");
        stbSql.append("\n" + "    C09_NM_E,");
        stbSql.append("\n" + "    C09_RN,");
        stbSql.append("\n" + "    C09_RN_E");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C09_FacultyNM");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C09_CD = '" + DB_CtrlChar.edit(_strC09_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC09_NM = dbr.getString("C09_NM");
                _strC09_NM_E = dbr.getString("C09_NM_E");
                _strC09_RN = dbr.getString("C09_RN");
                _strC09_RN_E = dbr.getString("C09_RN_E");

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

