package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K02_StdKB.java
   Program Name  : 読込クラス (K02_StdKB)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K02_StdKB {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** コード **/
    private int _intK02_CD;
    public void setK02_CD(int intK02_CD) {
        _intK02_CD = intK02_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 学生区分名 **/
    private String _strK02_NM;
    public String getK02_NM() {
        return _strK02_NM;
    }

    /** 科目等履修生区分 **/
    private int _intK02_NoDgrKB;
    public int getK02_NoDgrKB() {
        return _intK02_NoDgrKB;
    }

    /** 背景色 **/
    private int _intK02_BGColor;
    public int getK02_BGColor() {
        return _intK02_BGColor;
    }

    /** 文字色 **/
    private int _intK02_FGColor;
    public int getK02_FGColor() {
        return _intK02_FGColor;
    }

    /** 太字 **/
    private int _intK02_Bold;
    public int getK02_Bold() {
        return _intK02_Bold;
    }

    /** 卒業判定 **/
    private int _intK02_GradJudg;
    public int getK02_GradJudg() {
        return _intK02_GradJudg;
    }

    /** 進級判定 **/
    private int _intK02_PromJudg;
    public int getK02_PromJudg() {
        return _intK02_PromJudg;
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
        stbSql.append("\n" + "    K02_NM,");
        stbSql.append("\n" + "    K02_NoDgrKB,");
        stbSql.append("\n" + "    K02_BGColor,");
        stbSql.append("\n" + "    K02_FGColor,");
        stbSql.append("\n" + "    K02_Bold,");
        stbSql.append("\n" + "    K02_GradJudg,");
        stbSql.append("\n" + "    K02_PromJudg");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K02_StdKB");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K02_CD = " + String.valueOf(_intK02_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK02_NM = dbr.getString("K02_NM");
                _intK02_NoDgrKB = dbr.getInt("K02_NoDgrKB");
                _intK02_BGColor = dbr.getInt("K02_BGColor");
                _intK02_FGColor = dbr.getInt("K02_FGColor");
                _intK02_Bold = dbr.getInt("K02_Bold");
                _intK02_GradJudg = dbr.getInt("K02_GradJudg");
                _intK02_PromJudg = dbr.getInt("K02_PromJudg");

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

