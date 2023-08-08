package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C28_KyBunya.java
   Program Name  : 読込クラス (C28_KyBunya)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C28_KyBunya {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 教員分野 **/
    private int _intC28_CD;
    public void setC28_CD(int intC28_CD) {
        _intC28_CD = intC28_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 教員分野名 **/
    private String _strC28_NM;
    public String getC28_NM() {
        return _strC28_NM;
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
        stbSql.append("\n" + "    C28_NM");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C28_KyBunya");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C28_CD = " + String.valueOf(_intC28_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC28_NM = dbr.getString("C28_NM");

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

