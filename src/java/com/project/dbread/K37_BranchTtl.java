package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K37_BranchTtl.java
   Program Name  : 読込クラス (K37_BranchTtl)
   Program Date  : 2009/02/09
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/02/09 : 新規作成
================================================================================
********************************************************************************
*/

public class K37_BranchTtl {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 入学年度 **/
    private int _intK37_Year;
    public void setK37_Year(int intK37_Year) {
        _intK37_Year = intK37_Year;
    }

    /** 分野集計式コード **/
    private String _strK37_CD;
    public void setK37_CD(String strK37_CD) {
        _strK37_CD = strK37_CD;
    }

    /** 科目集計式コード **/
    private String _strK36_CD;
    public void setK36_CD(String strK36_CD) {
        _strK36_CD = strK36_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 分野集計式名 **/
    private String _strK37_NM;
    public String getK37_NM() {
        return _strK37_NM;
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
        stbSql.append("\n" + "    K37_NM");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K37_BranchTtl");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K37_Year = " + String.valueOf(_intK37_Year));
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    K37_CD = '" + DB_CtrlChar.edit(_strK37_CD) + "'");
        if (_strK36_CD != null) {
            stbSql.append("\n" + "AND");
            stbSql.append("\n" + "    K36_CD = '" + DB_CtrlChar.edit(_strK36_CD) + "'");
        }

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK37_NM = dbr.getString("K37_NM");

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

