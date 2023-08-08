package com.project.dbread;

import java.math.BigDecimal;
import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K07_Grade.java
   Program Name  : 読込クラス (K07_Grade)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K07_Grade {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** コード **/
    private int _intK07_CD;
    public void setK07_CD(int intK07_CD) {
        _intK07_CD = intK07_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 評価名 **/
    private String _strK07_NM;
    public String getK07_NM() {
        return _strK07_NM;
    }

    /** 評価英語名 **/
    private String _strK07_NM_E;
    public String getK07_NM_E() {
        return _strK07_NM_E;
    }

    /** 合否区分 **/
    private int _intK07_Success;
    public int getK07_Success() {
        return _intK07_Success;
    }

    /** GPAウエイト **/
    private BigDecimal _decK07_GPA;
    public BigDecimal getK07_GPA() {
        return _decK07_GPA;
    }

    /** 背景色 **/
    private int _intK07_BGColor;
    public int getK07_BGColor() {
        return _intK07_BGColor;
    }

    /** 文字色 **/
    private int _intK07_FGColor;
    public int getK07_FGColor() {
        return _intK07_FGColor;
    }

    /** 太字 **/
    private int _intK07_Bold;
    public int getK07_Bold() {
        return _intK07_Bold;
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
        stbSql.append("\n" + "    K07_NM,");
        stbSql.append("\n" + "    K07_NM_E,");
        stbSql.append("\n" + "    K07_Success,");
        stbSql.append("\n" + "    K07_GPA,");
        stbSql.append("\n" + "    K07_BGColor,");
        stbSql.append("\n" + "    K07_FGColor,");
        stbSql.append("\n" + "    K07_Bold");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K07_Grade");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K07_CD = " + String.valueOf(_intK07_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK07_NM = dbr.getString("K07_NM");
                _strK07_NM_E = dbr.getString("K07_NM_E");
                _intK07_Success = dbr.getInt("K07_Success");
                _decK07_GPA = dbr.getBigDecimal("K07_GPA");
                _intK07_BGColor = dbr.getInt("K07_BGColor");
                _intK07_FGColor = dbr.getInt("K07_FGColor");
                _intK07_Bold = dbr.getInt("K07_Bold");

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

