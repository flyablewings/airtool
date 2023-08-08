package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 ホクコウ共通 】
================================================================================
   Program Number: A07_Pgm.java
   Program Name  : 読込クラス (A07_Pgm)
   Program Date  : 2009/01/19
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/19 : 新規作成
================================================================================
********************************************************************************
*/

public class A07_Pgm {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** プログラムコード **/
    private String _strA07_PgmCD;
    public void setA07_PgmCD(String strA07_PgmCD) {
        _strA07_PgmCD = strA07_PgmCD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** プログラム名称 **/
    private String _strA07_PgmNM;
    public String getA07_PgmNM() {
        return _strA07_PgmNM;
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
        stbSql.append("\n" + "    A07_PgmNM");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    A07_Pgm");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    A07_PgmCD = '" + DB_CtrlChar.edit(_strA07_PgmCD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strA07_PgmNM = dbr.getString("A07_PgmNM");

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

