package com.project.dbread;

import java.math.BigDecimal;
import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K24_Schedule.java
   Program Name  : 読込クラス (K24_Schedule)
   Program Date  : 2009/02/04
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/02/04 : 新規作成
================================================================================
********************************************************************************
*/

public class K24_Schedule {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 開講年度 **/
    private int _intK24_Year;
    public void setK24_Year(int intK24_Year) {
        _intK24_Year = intK24_Year;
    }

    /** 講義コード **/
    private String _strK24_CD;
    public void setK24_CD(String strK24_CD) {
        _strK24_CD = strK24_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 科目コード **/
    private String _strK05_CD;
    public String getK05_CD() {
        return _strK05_CD;
    }

    /** 講義名 **/
    private String _strK24_NM;
    public String getK24_NM() {
        return _strK24_NM;
    }

    /** 講義単位 **/
    private BigDecimal _decK24_Credit;
    public BigDecimal getK24_Credit() {
        return _decK24_Credit;
    }

    /** 開講期間 **/
    private int _intK06_CD;
    public int getK06_CD() {
        return _intK06_CD;
    }

    /** 開始日付 **/
    private int _intK24_StartDT;
    public int getK24_StartDT() {
        return _intK24_StartDT;
    }

    /** 終了日付 **/
    private int _intK24_EndDT;
    public int getK24_EndDT() {
        return _intK24_EndDT;
    }

    /** 周期 **/
    private int _intK24_Cycle;
    public int getK24_Cycle() {
        return _intK24_Cycle;
    }

    /** 重複チェック **/
    private int _intK24_Double;
    public int getK24_Double() {
        return _intK24_Double;
    }

    /** 主曜日 **/
    private int _intK24_Day1;
    public int getK24_Day1() {
        return _intK24_Day1;
    }

    /** 主時限 **/
    private BigDecimal _decK24_Period1;
    public BigDecimal getK24_Period1() {
        return _decK24_Period1;
    }

    /** 主コマ数 **/
    private BigDecimal _decK24_Times1;
    public BigDecimal getK24_Times1() {
        return _decK24_Times1;
    }

    /** 児ｬ員コード **/
    private String _strC04_CD1;
    public String getC04_CD1() {
        return _strC04_CD1;
    }

    /** 児ｬ室コード **/
    private String _strC07_CD1;
    public String getC07_CD1() {
        return _strC07_CD1;
    }

    /** 全曜日時谀Rマ **/
    private String _strK24_DPTs;
    public String getK24_DPTs() {
        return _strK24_DPTs;
    }

    /** 全教員コード **/
    private String _strC04_CDs;
    public String getC04_CDs() {
        return _strC04_CDs;
    }

    /** 全教室コード **/
    private String _strC07_CDs;
    public String getC07_CDs() {
        return _strC07_CDs;
    }

    /** 総コマ数 **/
    private BigDecimal _decK24_TimesSum;
    public BigDecimal getK24_TimesSum() {
        return _decK24_TimesSum;
    }

    /** 備考1 **/
    private String _strK24_Remark1;
    public String getK24_Remark1() {
        return _strK24_Remark1;
    }

    /** 備考2 **/
    private String _strK24_Remark2;
    public String getK24_Remark2() {
        return _strK24_Remark2;
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
        stbSql.append("\n" + "    K05_CD,");
        stbSql.append("\n" + "    K24_NM,");
        stbSql.append("\n" + "    K24_Credit,");
        stbSql.append("\n" + "    K06_CD,");
        stbSql.append("\n" + "    K24_StartDT,");
        stbSql.append("\n" + "    K24_EndDT,");
        stbSql.append("\n" + "    K24_Cycle,");
        stbSql.append("\n" + "    K24_Double,");
        stbSql.append("\n" + "    K24_Day1,");
        stbSql.append("\n" + "    K24_Period1,");
        stbSql.append("\n" + "    K24_Times1,");
        stbSql.append("\n" + "    C04_CD1,");
        stbSql.append("\n" + "    C07_CD1,");
        stbSql.append("\n" + "    K24_DPTs,");
        stbSql.append("\n" + "    C04_CDs,");
        stbSql.append("\n" + "    C07_CDs,");
        stbSql.append("\n" + "    K24_TimesSum,");
        stbSql.append("\n" + "    K24_Remark1,");
        stbSql.append("\n" + "    K24_Remark2");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K24_Schedule");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K24_Year = " + String.valueOf(_intK24_Year));
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    K24_CD = '" + DB_CtrlChar.edit(_strK24_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK05_CD = dbr.getString("K05_CD");
                _strK24_NM = dbr.getString("K24_NM");
                _decK24_Credit = dbr.getBigDecimal("K24_Credit");
                _intK06_CD = dbr.getInt("K06_CD");
                _intK24_StartDT = dbr.getInt("K24_StartDT");
                _intK24_EndDT = dbr.getInt("K24_EndDT");
                _intK24_Cycle = dbr.getInt("K24_Cycle");
                _intK24_Double = dbr.getInt("K24_Double");
                _intK24_Day1 = dbr.getInt("K24_Day1");
                _decK24_Period1 = dbr.getBigDecimal("K24_Period1");
                _decK24_Times1 = dbr.getBigDecimal("K24_Times1");
                _strC04_CD1 = dbr.getString("C04_CD1");
                _strC07_CD1 = dbr.getString("C07_CD1");
                _strK24_DPTs = dbr.getString("K24_DPTs");
                _strC04_CDs = dbr.getString("C04_CDs");
                _strC07_CDs = dbr.getString("C07_CDs");
                _decK24_TimesSum = dbr.getBigDecimal("K24_TimesSum");
                _strK24_Remark1 = dbr.getString("K24_Remark1");
                _strK24_Remark2 = dbr.getString("K24_Remark2");

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

