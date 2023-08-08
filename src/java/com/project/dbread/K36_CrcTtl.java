package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K36_CrcTtl.java
   Program Name  : 読込クラス (K36_CrcTtl)
   Program Date  : 2009/02/07
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/02/07 : 新規作成
   2009/02/07 : 名称のみ取得用にカスタマイズ = H.Fujimoto
================================================================================
********************************************************************************
*/

public class K36_CrcTtl {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 入学年度 **/
    private int _intK36_Year;
    public void setK36_Year(int intK36_Year) {
        _intK36_Year = intK36_Year;
    }

    /** 科目集計式コード **/
    private String _strK36_CD;
    public void setK36_CD(String strK36_CD) {
        _strK36_CD = strK36_CD;
    }

    /** 科目コード **/
    private String _strK05_CD;
    public void setK05_CD(String strK05_CD) {
        _strK05_CD = strK05_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 科目集計式名 **/
    private String _strK36_NM;
    public String getK36_NM() {
        return _strK36_NM;
    }

    /** 認定上限単位 **/
    private int _intK36_LmtCdt;
    public int getK36_LmtCdt() {
        return _intK36_LmtCdt;
    }

    /** 認定上限科目数 **/
    private int _intK36_LmtCrcCnt;
    public int getK36_LmtCrcCnt() {
        return _intK36_LmtCrcCnt;
    }

    /** 他科目群認定単位 **/
    private int _intK36_SpcCdt;
    public int getK36_SpcCdt() {
        return _intK36_SpcCdt;
    }

    /** 他科目群認定科目数 **/
    private int _intK36_SpcCrcCnt;
    public int getK36_SpcCrcCnt() {
        return _intK36_SpcCrcCnt;
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
        stbSql.append("\n" + "    K36_NM,");
        stbSql.append("\n" + "    K36_LmtCdt,");
        stbSql.append("\n" + "    K36_LmtCrcCnt,");
        stbSql.append("\n" + "    K36_SpcCdt,");
        stbSql.append("\n" + "    K36_SpcCrcCnt");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K36_CrcTtl");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K36_Year = " + String.valueOf(_intK36_Year));
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    K36_CD = '" + DB_CtrlChar.edit(_strK36_CD) + "'");
        if (_strK05_CD != null) {
            stbSql.append("\n" + "AND");
            stbSql.append("\n" + "    K05_CD = '" + DB_CtrlChar.edit(_strK05_CD) + "'");
        }

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK36_NM = dbr.getString("K36_NM");
                if (_strK05_CD != null) {
                    _intK36_LmtCdt = dbr.getInt("K36_LmtCdt");
                    _intK36_LmtCrcCnt = dbr.getInt("K36_LmtCrcCnt");
                    _intK36_SpcCdt = dbr.getInt("K36_SpcCdt");
                    _intK36_SpcCrcCnt = dbr.getInt("K36_SpcCrcCnt");
                }
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

