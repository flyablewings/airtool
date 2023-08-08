package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C16_Course.java
   Program Name  : 読込クラス (C16_Course)
   Program Date  : 2009/02/02
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/02/02 : 新規作成
================================================================================
********************************************************************************
*/

public class C16_Course {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** コースコード **/
    private String _strC16_CD;
    public void setC16_CD(String strC16_CD) {
        _strC16_CD = strC16_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** コース名称 **/
    private String _strC16_NM;
    public String getC16_NM() {
        return _strC16_NM;
    }

    /** コース名称英字 **/
    private String _strC16_NM_E;
    public String getC16_NM_E() {
        return _strC16_NM_E;
    }

    /** コース略称 **/
    private String _strC16_RN;
    public String getC16_RN() {
        return _strC16_RN;
    }

    /** コース略称英字 **/
    private String _strC16_RN_E;
    public String getC16_RN_E() {
        return _strC16_RN_E;
    }

    /** 使用開始日付 **/
    private int _intC16_Start;
    public int getC16_Start() {
        return _intC16_Start;
    }

    /** 使用終了日付 **/
    private int _intC16_End;
    public int getC16_End() {
        return _intC16_End;
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
        stbSql.append("\n" + "    C16_NM,");
        stbSql.append("\n" + "    C16_NM_E,");
        stbSql.append("\n" + "    C16_RN,");
        stbSql.append("\n" + "    C16_RN_E,");
        stbSql.append("\n" + "    C16_Start,");
        stbSql.append("\n" + "    C16_End");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C16_Course");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C16_CD = '" + DB_CtrlChar.edit(_strC16_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC16_NM = dbr.getString("C16_NM");
                _strC16_NM_E = dbr.getString("C16_NM_E");
                _strC16_RN = dbr.getString("C16_RN");
                _strC16_RN_E = dbr.getString("C16_RN_E");
                _intC16_Start = dbr.getInt("C16_Start");
                _intC16_End = dbr.getInt("C16_End");

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

