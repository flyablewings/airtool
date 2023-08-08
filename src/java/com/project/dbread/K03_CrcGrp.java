package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K03_CrcGrp.java
   Program Name  : 読込クラス (K03_CrcGrp)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K03_CrcGrp {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 科目群コード **/
    private String _strK03_CD;
    public void setK03_CD(String strK03_CD) {
        _strK03_CD = strK03_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 科目群名 **/
    private String _strK03_NM;
    public String getK03_NM() {
        return _strK03_NM;
    }

    /** 科目群略称 **/
    private String _strK03_RN;
    public String getK03_RN() {
        return _strK03_RN;
    }

    /** 科目群英語名 **/
    private String _strK03_NM_E;
    public String getK03_NM_E() {
        return _strK03_NM_E;
    }

    /** 背景色 **/
    private int _intK03_BGColor;
    public int getK03_BGColor() {
        return _intK03_BGColor;
    }

    /** 文字色 **/
    private int _intK03_FGColor;
    public int getK03_FGColor() {
        return _intK03_FGColor;
    }

    /** 太字 **/
    private int _intK03_Bold;
    public int getK03_Bold() {
        return _intK03_Bold;
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
        stbSql.append("\n" + "    K03_NM,");
        stbSql.append("\n" + "    K03_RN,");
        stbSql.append("\n" + "    K03_NM_E,");
        stbSql.append("\n" + "    K03_BGColor,");
        stbSql.append("\n" + "    K03_FGColor,");
        stbSql.append("\n" + "    K03_Bold");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K03_CrcGrp");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K03_CD = '" + DB_CtrlChar.edit(_strK03_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK03_NM = dbr.getString("K03_NM");
                _strK03_RN = dbr.getString("K03_RN");
                _strK03_NM_E = dbr.getString("K03_NM_E");
                _intK03_BGColor = dbr.getInt("K03_BGColor");
                _intK03_FGColor = dbr.getInt("K03_FGColor");
                _intK03_Bold = dbr.getInt("K03_Bold");

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

