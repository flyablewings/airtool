package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K38_JdgRequire.java
   Program Name  : 読込クラス (K38_JdgRequire)
   Program Date  : 2009/02/09
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/02/09 : 新規作成
================================================================================
********************************************************************************
*/

public class K38_JdgRequire {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 入学年度 **/
    private int _intK38_Year;
    public void setK38_Year(int intK38_Year) {
        _intK38_Year = intK38_Year;
    }

    /** 判定条件式コード **/
    private String _strK38_CD;
    public void setK38_CD(String strK38_CD) {
        _strK38_CD = strK38_CD;
    }

    /** 分野集計式コード **/
    private String _strK37_CD;
    public void setK37_CD(String strK37_CD) {
        _strK37_CD = strK37_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 判定条件式名 **/
    private String _strK38_NM;
    public String getK38_NM() {
        return _strK38_NM;
    }

    /** 判定区分 **/
    private int _intK38_Judgment;
    public int getK38_Judgment() {
        return _intK38_Judgment;
    }

    /** 学部学科コード **/
    private String _strC10_CD;
    public String getC10_CD() {
        return _strC10_CD;
    }

    /** コースコード **/
    private String _strC16_CD;
    public String getC16_CD() {
        return _strC16_CD;
    }

    /** 学生区分 **/
    private String _strK02_CD;
    public String getK02_CD() {
        return _strK02_CD;
    }

    /** 資格コード **/
    private String _strK51_CD;
    public String getK51_CD() {
        return _strK51_CD;
    }

    /** 必要単位 **/
    private int _intK38_NeedCdt;
    public int getK38_NeedCdt() {
        return _intK38_NeedCdt;
    }

    /** 必要科目数 **/
    private int _intK38_NeedCrcCnt;
    public int getK38_NeedCrcCnt() {
        return _intK38_NeedCrcCnt;
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
        stbSql.append("\n" + "    K38_NM,");
        stbSql.append("\n" + "    K38_Judgment,");
        stbSql.append("\n" + "    C10_CD,");
        stbSql.append("\n" + "    C16_CD,");
        stbSql.append("\n" + "    K02_CD,");
        stbSql.append("\n" + "    K51_CD,");
        stbSql.append("\n" + "    K38_NeedCdt,");
        stbSql.append("\n" + "    K38_NeedCrcCnt");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K38_JdgRequire");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K38_Year = " + String.valueOf(_intK38_Year));
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    K38_CD = '" + DB_CtrlChar.edit(_strK38_CD) + "'");
        if (_strK37_CD != null) {
            stbSql.append("\n" + "AND");
            stbSql.append("\n" + "    K37_CD = '" + DB_CtrlChar.edit(_strK37_CD) + "'");
        }

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK38_NM = dbr.getString("K38_NM");
                _intK38_Judgment = dbr.getInt("K38_Judgment");
                _strC10_CD = dbr.getString("C10_CD");
                _strC16_CD = dbr.getString("C16_CD");
                _strK02_CD = dbr.getString("K02_CD");
                _strK51_CD = dbr.getString("K51_CD");
                if (_strK37_CD != null) {
	                _intK38_NeedCdt = dbr.getInt("K38_NeedCdt");
	                _intK38_NeedCrcCnt = dbr.getInt("K38_NeedCrcCnt");
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

