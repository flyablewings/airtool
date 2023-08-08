package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C31_InstDiv.java
   Program Name  : 読込クラス (C31_InstDiv)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C31_InstDiv {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 施設分類ｺｰﾄﾞ **/
    private int _intC31_CD;
    public void setC31_CD(int intC31_CD) {
        _intC31_CD = intC31_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 施設分類名 **/
    private String _strC31_NM;
    public String getC31_NM() {
        return _strC31_NM;
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
        stbSql.append("\n" + "    C31_NM");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C31_InstDiv");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C31_CD = " + String.valueOf(_intC31_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC31_NM = dbr.getString("C31_NM");

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

