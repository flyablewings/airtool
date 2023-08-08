package com.project.dbread;

import java.math.BigDecimal;
import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 入試 】
================================================================================
   Program Number: N02_Test.java
   Program Name  : 読込クラス (N02_Test)
   Program Date  : 2009/01/31
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/31 : 新規作成
================================================================================
********************************************************************************
*/

public class N02_Test {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 処理年度 **/
    private int _intN02_Year;
    public void setN02_Year(int intN02_Year) {
        _intN02_Year = intN02_Year;
    }

    /** 試験コード **/
    private String _strN02_CD;
    public void setN02_CD(String strN02_CD) {
        _strN02_CD = strN02_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 更新回数 **/
    private int _intUpdate_Count;
    public int getUpdate_Count() {
        return _intUpdate_Count;
    }

    /** 試験名 **/
    private String _strN02_NM;
    public String getN02_NM() {
        return _strN02_NM;
    }

    /** 試験略名 **/
    private String _strN02_SNM;
    public String getN02_SNM() {
        return _strN02_SNM;
    }

    /** 試験日 **/
    private int _intN02_DT;
    public int getN02_DT() {
        return _intN02_DT;
    }

    /** 試験区分 **/
    private int _intN02_TestDiv;
    public int getN02_TestDiv() {
        return _intN02_TestDiv;
    }

    /** 内申点ボーダー **/
    private BigDecimal _decN02_GradeB;
    public BigDecimal getN02_GradeB() {
        return _decN02_GradeB;
    }

    /** 内申点合計ボーダー **/
    private BigDecimal _decN02_GradeSumB;
    public BigDecimal getN02_GradeSumB() {
        return _decN02_GradeSumB;
    }

    /** 順位ボーダー **/
    private int _intN02_RankB;
    public int getN02_RankB() {
        return _intN02_RankB;
    }

    /** 偏差値 **/
    private BigDecimal _decN02_Deviation;
    public BigDecimal getN02_Deviation() {
        return _decN02_Deviation;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_01;
    public int getN02_GradeSum_01() {
        return _intN02_GradeSum_01;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_02;
    public int getN02_GradeSum_02() {
        return _intN02_GradeSum_02;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_03;
    public int getN02_GradeSum_03() {
        return _intN02_GradeSum_03;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_04;
    public int getN02_GradeSum_04() {
        return _intN02_GradeSum_04;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_05;
    public int getN02_GradeSum_05() {
        return _intN02_GradeSum_05;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_06;
    public int getN02_GradeSum_06() {
        return _intN02_GradeSum_06;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_07;
    public int getN02_GradeSum_07() {
        return _intN02_GradeSum_07;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_08;
    public int getN02_GradeSum_08() {
        return _intN02_GradeSum_08;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_09;
    public int getN02_GradeSum_09() {
        return _intN02_GradeSum_09;
    }

    /** 内申集計ﾌﾗｸﾞ **/
    private int _intN02_GradeSum_10;
    public int getN02_GradeSum_10() {
        return _intN02_GradeSum_10;
    }

    /** 入試種別 **/
    private int _intN02_TestType;
    public int getN02_TestType() {
        return _intN02_TestType;
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
        stbSql.append("\n" + "    Update_Count,");
        stbSql.append("\n" + "    N02_NM,");
        stbSql.append("\n" + "    N02_SNM,");
        stbSql.append("\n" + "    N02_DT,");
        stbSql.append("\n" + "    N02_TestDiv,");
        stbSql.append("\n" + "    N02_GradeB,");
        stbSql.append("\n" + "    N02_GradeSumB,");
        stbSql.append("\n" + "    N02_RankB,");
        stbSql.append("\n" + "    N02_Deviation,");
        stbSql.append("\n" + "    N02_GradeSum_01,");
        stbSql.append("\n" + "    N02_GradeSum_02,");
        stbSql.append("\n" + "    N02_GradeSum_03,");
        stbSql.append("\n" + "    N02_GradeSum_04,");
        stbSql.append("\n" + "    N02_GradeSum_05,");
        stbSql.append("\n" + "    N02_GradeSum_06,");
        stbSql.append("\n" + "    N02_GradeSum_07,");
        stbSql.append("\n" + "    N02_GradeSum_08,");
        stbSql.append("\n" + "    N02_GradeSum_09,");
        stbSql.append("\n" + "    N02_GradeSum_10,");
        stbSql.append("\n" + "    N02_TestType");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    N02_Test");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    N02_Year = " + String.valueOf(_intN02_Year));
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    N02_CD = '" + DB_CtrlChar.edit(_strN02_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _intUpdate_Count = dbr.getInt("Update_Count");
                _strN02_NM = dbr.getString("N02_NM");
                _strN02_SNM = dbr.getString("N02_SNM");
                _intN02_DT = dbr.getInt("N02_DT");
                _intN02_TestDiv = dbr.getInt("N02_TestDiv");
                _decN02_GradeB = dbr.getBigDecimal("N02_GradeB");
                _decN02_GradeSumB = dbr.getBigDecimal("N02_GradeSumB");
                _intN02_RankB = dbr.getInt("N02_RankB");
                _decN02_Deviation = dbr.getBigDecimal("N02_Deviation");
                _intN02_GradeSum_01 = dbr.getInt("N02_GradeSum_01");
                _intN02_GradeSum_02 = dbr.getInt("N02_GradeSum_02");
                _intN02_GradeSum_03 = dbr.getInt("N02_GradeSum_03");
                _intN02_GradeSum_04 = dbr.getInt("N02_GradeSum_04");
                _intN02_GradeSum_05 = dbr.getInt("N02_GradeSum_05");
                _intN02_GradeSum_06 = dbr.getInt("N02_GradeSum_06");
                _intN02_GradeSum_07 = dbr.getInt("N02_GradeSum_07");
                _intN02_GradeSum_08 = dbr.getInt("N02_GradeSum_08");
                _intN02_GradeSum_09 = dbr.getInt("N02_GradeSum_09");
                _intN02_GradeSum_10 = dbr.getInt("N02_GradeSum_10");
                _intN02_TestType = dbr.getInt("N02_TestType");

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

