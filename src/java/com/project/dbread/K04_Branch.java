package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K04_Branch.java
   Program Name  : 読込クラス (K04_Branch)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K04_Branch {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 分野コード **/
    private String _strK04_CD;
    public void setK04_CD(String strK04_CD) {
        _strK04_CD = strK04_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 分野名 **/
    private String _strK04_NM;
    public String getK04_NM() {
        return _strK04_NM;
    }

    /** 分野略称 **/
    private String _strK04_RN;
    public String getK04_RN() {
        return _strK04_RN;
    }

    /** 分野英語名 **/
    private String _strK04_NM_E;
    public String getK04_NM_E() {
        return _strK04_NM_E;
    }

    /** 背景色 **/
    private int _intK04_BGColor;
    public int getK04_BGColor() {
        return _intK04_BGColor;
    }

    /** 文字色 **/
    private int _intK04_FGColor;
    public int getK04_FGColor() {
        return _intK04_FGColor;
    }

    /** 太字 **/
    private int _intK04_Bold;
    public int getK04_Bold() {
        return _intK04_Bold;
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
        stbSql.append("\n" + "    K04_NM,");
        stbSql.append("\n" + "    K04_RN,");
        stbSql.append("\n" + "    K04_NM_E,");
        stbSql.append("\n" + "    K04_BGColor,");
        stbSql.append("\n" + "    K04_FGColor,");
        stbSql.append("\n" + "    K04_Bold");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K04_Branch");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K04_CD = '" + DB_CtrlChar.edit(_strK04_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK04_NM = dbr.getString("K04_NM");
                _strK04_RN = dbr.getString("K04_RN");
                _strK04_NM_E = dbr.getString("K04_NM_E");
                _intK04_BGColor = dbr.getInt("K04_BGColor");
                _intK04_FGColor = dbr.getInt("K04_FGColor");
                _intK04_Bold = dbr.getInt("K04_Bold");

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

