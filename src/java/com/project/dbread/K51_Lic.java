package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K51_Lic.java
   Program Name  : 読込クラス (K51_Lic)
   Program Date  : 2009/02/07
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/02/07 : 新規作成
================================================================================
********************************************************************************
*/

public class K51_Lic {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 資格コード **/
    private String _strK51_CD;
    public void setK51_CD(String strK51_CD) {
        _strK51_CD = strK51_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 資格名 **/
    private String _strK51_NM;
    public String getK51_NM() {
        return _strK51_NM;
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
        stbSql.append("\n" + "    K51_NM");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K51_Lic");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K51_CD = '" + DB_CtrlChar.edit(_strK51_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK51_NM = dbr.getString("K51_NM");

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

