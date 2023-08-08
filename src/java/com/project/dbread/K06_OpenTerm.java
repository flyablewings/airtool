package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K06_OpenTerm.java
   Program Name  : 読込クラス (K06_OpenTerm)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K06_OpenTerm {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** コード **/
    private int _intK06_CD;
    public void setK06_CD(int intK06_CD) {
        _intK06_CD = intK06_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 開講期間名 **/
    private String _strK06_NM;
    public String getK06_NM() {
        return _strK06_NM;
    }

    /** 前期期間 **/
    private int _intK06_First;
    public int getK06_First() {
        return _intK06_First;
    }

    /** 後期期間 **/
    private int _intK06_Second;
    public int getK06_Second() {
        return _intK06_Second;
    }

    /** 開始月日 **/
    private int _intK06_StartMD;
    public int getK06_StartMD() {
        return _intK06_StartMD;
    }

    /** 終了月日 **/
    private int _intK06_EndMD;
    public int getK06_EndMD() {
        return _intK06_EndMD;
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
        stbSql.append("\n" + "    K06_NM,");
        stbSql.append("\n" + "    K06_First,");
        stbSql.append("\n" + "    K06_Second,");
        stbSql.append("\n" + "    K06_StartMD,");
        stbSql.append("\n" + "    K06_EndMD");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K06_OpenTerm");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K06_CD = " + String.valueOf(_intK06_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK06_NM = dbr.getString("K06_NM");
                _intK06_First = dbr.getInt("K06_First");
                _intK06_Second = dbr.getInt("K06_Second");
                _intK06_StartMD = dbr.getInt("K06_StartMD");
                _intK06_EndMD = dbr.getInt("K06_EndMD");

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

