package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C18_Scholar.java
   Program Name  : 読込クラス (C18_Scholar)
   Program Date  : 2009/02/14
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/02/14 : 新規作成
================================================================================
********************************************************************************
*/

public class C18_Scholar {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 奨学金コード **/
    private String _strC18_CD;
    public void setC18_CD(String strC18_CD) {
        _strC18_CD = strC18_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 奨学金名 **/
    private String _strC18_NM;
    public String getC18_NM() {
        return _strC18_NM;
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
        stbSql.append("\n" + "    C18_NM");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C18_Scholar");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C18_CD = '" + DB_CtrlChar.edit(_strC18_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC18_NM = dbr.getString("C18_NM");

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

