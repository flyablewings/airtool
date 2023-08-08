package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K05_Crc.java
   Program Name  : 読込クラス (K05_Crc)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K05_Crc {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 科目コード **/
    private String _strK05_CD;
    public void setK05_CD(String strK05_CD) {
        _strK05_CD = strK05_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 科目名 **/
    private String _strK05_NM;
    public String getK05_NM() {
        return _strK05_NM;
    }

    /** 科目略称 **/
    private String _strK05_RN;
    public String getK05_RN() {
        return _strK05_RN;
    }

    /** 科目英語名 **/
    private String _strK05_NM_E;
    public String getK05_NM_E() {
        return _strK05_NM_E;
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
        stbSql.append("\n" + "    K05_NM,");
        stbSql.append("\n" + "    K05_RN,");
        stbSql.append("\n" + "    K05_NM_E");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K05_Crc");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K05_CD = '" + DB_CtrlChar.edit(_strK05_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK05_NM = dbr.getString("K05_NM");
                _strK05_RN = dbr.getString("K05_RN");
                _strK05_NM_E = dbr.getString("K05_NM_E");

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

