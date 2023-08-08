package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C17_Exempt.java
   Program Name  : 読込クラス (C17_Exempt)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C17_Exempt {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 減免コード **/
    private int _intC17_CD;
    public void setC17_CD(int intC17_CD) {
        _intC17_CD = intC17_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 減免名 **/
    private String _strC17_NM;
    public String getC17_NM() {
        return _strC17_NM;
    }

    /** 減免フリガナ **/
    private String _strC17_KN;
    public String getC17_KN() {
        return _strC17_KN;
    }

    /** 減免略称 **/
    private String _strC17_SNM;
    public String getC17_SNM() {
        return _strC17_SNM;
    }

    /** 使用開始日付 **/
    private int _intC17_Start;
    public int getC17_Start() {
        return _intC17_Start;
    }

    /** 使用終了日付 **/
    private int _intC17_End;
    public int getC17_End() {
        return _intC17_End;
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
        stbSql.append("\n" + "    C17_NM,");
        stbSql.append("\n" + "    C17_KN,");
        stbSql.append("\n" + "    C17_SNM,");
        stbSql.append("\n" + "    C17_Start,");
        stbSql.append("\n" + "    C17_End");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C17_Exempt");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C17_CD = " + String.valueOf(_intC17_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC17_NM = dbr.getString("C17_NM");
                _strC17_KN = dbr.getString("C17_KN");
                _strC17_SNM = dbr.getString("C17_SNM");
                _intC17_Start = dbr.getInt("C17_Start");
                _intC17_End = dbr.getInt("C17_End");

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

