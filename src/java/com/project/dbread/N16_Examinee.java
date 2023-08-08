package com.project.dbread;

import java.math.BigDecimal;
import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 入試 】
================================================================================
   Program Number: N16_Examinee.java
   Program Name  : 読込クラス (N16_Examinee)
   Program Date  : 2009/01/31
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/31 : 新規作成
================================================================================
********************************************************************************
*/

public class N16_Examinee {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 処理年度 **/
    private int _intN16_Year;
    public void setN16_Year(int intN16_Year) {
        _intN16_Year = intN16_Year;
    }

    /** 志願者ID **/
    private int _intN16_Seq;
    public void setN16_Seq(int intN16_Seq) {
        _intN16_Seq = intN16_Seq;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 更新回数 **/
    private int _intUpdate_Count;
    public int getUpdate_Count() {
        return _intUpdate_Count;
    }

    /** 受験番号 **/
    private String _strN16_No;
    public String getN16_No() {
        return _strN16_No;
    }

    /** 受付日付 **/
    private int _intN16_ReceptDT;
    public int getN16_ReceptDT() {
        return _intN16_ReceptDT;
    }

    /** 氏名 **/
    private String _strN16_NM;
    public String getN16_NM() {
        return _strN16_NM;
    }

    /** ふりがな **/
    private String _strN16_KN;
    public String getN16_KN() {
        return _strN16_KN;
    }

    /** 性別 **/
    private int _intN16_Sex;
    public int getN16_Sex() {
        return _intN16_Sex;
    }

    /** 生年月日 **/
    private int _intN16_Birth;
    public int getN16_Birth() {
        return _intN16_Birth;
    }

    /** 現浪区分 **/
    private int _intN16_Genro;
    public int getN16_Genro() {
        return _intN16_Genro;
    }

    /** 試験ｺｰﾄﾞ **/
    private String _strN02_CD;
    public String getN02_CD() {
        return _strN02_CD;
    }

    /** 試験会場ｺｰﾄﾞ **/
    private String _strN01_PlaceCD;
    public String getN01_PlaceCD() {
        return _strN01_PlaceCD;
    }

    /** 学生所属ｺｰﾄﾞ **/
    private String _strC10_01;
    public String getC10_01() {
        return _strC10_01;
    }

    /** 学生所属ｺｰﾄﾞ **/
    private String _strC10_02;
    public String getC10_02() {
        return _strC10_02;
    }

    /** 学生所属ｺｰﾄﾞ **/
    private String _strC10_03;
    public String getC10_03() {
        return _strC10_03;
    }

    /** 学生所属ｺｰﾄﾞ **/
    private String _strC10_04;
    public String getC10_04() {
        return _strC10_04;
    }

    /** 学生所属ｺｰﾄﾞ **/
    private String _strC10_05;
    public String getC10_05() {
        return _strC10_05;
    }

    /** 大学入試ｾﾝﾀｰ受験番号 **/
    private String _strN13_DNC_Num1;
    public String getN13_DNC_Num1() {
        return _strN13_DNC_Num1;
    }

    /** 大学入試ｾﾝﾀｰ受験番号 **/
    private String _strN13_DNC_Num2;
    public String getN13_DNC_Num2() {
        return _strN13_DNC_Num2;
    }

    /** 大学入試ｾﾝﾀｰ受験番号 **/
    private String _strN13_DNC_NumCD;
    public String getN13_DNC_NumCD() {
        return _strN13_DNC_NumCD;
    }

    /** 大学入試ｾﾝﾀｰ受験番号 **/
    private String _strN13_DNC_Cnt;
    public String getN13_DNC_Cnt() {
        return _strN13_DNC_Cnt;
    }

    /** 成績請求票 **/
    private int _intN13_DNC_Div;
    public int getN13_DNC_Div() {
        return _intN13_DNC_Div;
    }

    /** 出身校ｺｰﾄﾞ **/
    private String _strC03_CD;
    public String getC03_CD() {
        return _strC03_CD;
    }

    /** 課程 **/
    private int _intN13_Course;
    public int getN13_Course() {
        return _intN13_Course;
    }

    /** 学科 **/
    private int _intN13_Department;
    public int getN13_Department() {
        return _intN13_Department;
    }

    /** 卒業の有無 **/
    private int _intN13_GraduateFlg;
    public int getN13_GraduateFlg() {
        return _intN13_GraduateFlg;
    }

    /** 卒業年度 **/
    private int _intN13_GraduateYear;
    public int getN13_GraduateYear() {
        return _intN13_GraduateYear;
    }

    /** 高校以外の出願資格 **/
    private int _intN13_Requirement;
    public int getN13_Requirement() {
        return _intN13_Requirement;
    }

    /** 内申点１ **/
    private BigDecimal _decN13_ConfRep_01;
    public BigDecimal getN13_ConfRep_01() {
        return _decN13_ConfRep_01;
    }

    /** 内申点２ **/
    private BigDecimal _decN13_ConfRep_02;
    public BigDecimal getN13_ConfRep_02() {
        return _decN13_ConfRep_02;
    }

    /** 内申点３ **/
    private BigDecimal _decN13_ConfRep_03;
    public BigDecimal getN13_ConfRep_03() {
        return _decN13_ConfRep_03;
    }

    /** 内申点４ **/
    private BigDecimal _decN13_ConfRep_04;
    public BigDecimal getN13_ConfRep_04() {
        return _decN13_ConfRep_04;
    }

    /** 内申点５ **/
    private BigDecimal _decN13_ConfRep_05;
    public BigDecimal getN13_ConfRep_05() {
        return _decN13_ConfRep_05;
    }

    /** 内申点６ **/
    private BigDecimal _decN13_ConfRep_06;
    public BigDecimal getN13_ConfRep_06() {
        return _decN13_ConfRep_06;
    }

    /** 内申点７ **/
    private BigDecimal _decN13_ConfRep_07;
    public BigDecimal getN13_ConfRep_07() {
        return _decN13_ConfRep_07;
    }

    /** 内申点８ **/
    private BigDecimal _decN13_ConfRep_08;
    public BigDecimal getN13_ConfRep_08() {
        return _decN13_ConfRep_08;
    }

    /** 内申点９ **/
    private BigDecimal _decN13_ConfRep_09;
    public BigDecimal getN13_ConfRep_09() {
        return _decN13_ConfRep_09;
    }

    /** 内申点１０ **/
    private BigDecimal _decN13_ConfRep_10;
    public BigDecimal getN13_ConfRep_10() {
        return _decN13_ConfRep_10;
    }

    /** 内申点の総合計 **/
    private BigDecimal _decN13_ConfRep_Total;
    public BigDecimal getN13_ConfRep_Total() {
        return _decN13_ConfRep_Total;
    }

    /** 内申点の平均 **/
    private BigDecimal _decN13_ConfRep_Average;
    public BigDecimal getN13_ConfRep_Average() {
        return _decN13_ConfRep_Average;
    }

    /** 内申点の累計１の合計 **/
    private BigDecimal _decN13_ConfRep_Total2;
    public BigDecimal getN13_ConfRep_Total2() {
        return _decN13_ConfRep_Total2;
    }

    /** 内申点の累計１の平均 **/
    private BigDecimal _decN13_ConfRep_Avrg2;
    public BigDecimal getN13_ConfRep_Avrg2() {
        return _decN13_ConfRep_Avrg2;
    }

    /** １年次欠席日数 **/
    private int _intN13_Absence_1;
    public int getN13_Absence_1() {
        return _intN13_Absence_1;
    }

    /** ２年次欠席日数 **/
    private int _intN13_Absence_2;
    public int getN13_Absence_2() {
        return _intN13_Absence_2;
    }

    /** ３年次欠席日数 **/
    private int _intN13_Absence_3;
    public int getN13_Absence_3() {
        return _intN13_Absence_3;
    }

    /** 欠席日数計 **/
    private int _intN13_Absence;
    public int getN13_Absence() {
        return _intN13_Absence;
    }

    /** 郵便番号 **/
    private String _strN13_Zip;
    public String getN13_Zip() {
        return _strN13_Zip;
    }

    /** 住所１ **/
    private String _strN13_Adres01;
    public String getN13_Adres01() {
        return _strN13_Adres01;
    }

    /** 住所２ **/
    private String _strN13_Adres02;
    public String getN13_Adres02() {
        return _strN13_Adres02;
    }

    /** 電話番号 **/
    private String _strN13_Tel;
    public String getN13_Tel() {
        return _strN13_Tel;
    }

    /** 国籍 **/
    private int _intC02_CountryCD;
    public int getC02_CountryCD() {
        return _intC02_CountryCD;
    }

    /** 保護者の氏名 **/
    private String _strN13_PName;
    public String getN13_PName() {
        return _strN13_PName;
    }

    /** 保護者のふりがな **/
    private String _strN13_PKana;
    public String getN13_PKana() {
        return _strN13_PKana;
    }

    /** 保護者の続柄 **/
    private String _strN13_PZoku;
    public String getN13_PZoku() {
        return _strN13_PZoku;
    }

    /** 保護者の郵便番号 **/
    private String _strN13_Pzip;
    public String getN13_Pzip() {
        return _strN13_Pzip;
    }

    /** 保護者の住所１ **/
    private String _strN13_PAdres01;
    public String getN13_PAdres01() {
        return _strN13_PAdres01;
    }

    /** 保護者の住所２ **/
    private String _strN13_PAdres02;
    public String getN13_PAdres02() {
        return _strN13_PAdres02;
    }

    /** 保護者の電話番号 **/
    private String _strN13_Ptel;
    public String getN13_Ptel() {
        return _strN13_Ptel;
    }

    /** 備考１ **/
    private String _strN13_Remark01;
    public String getN13_Remark01() {
        return _strN13_Remark01;
    }

    /** 備考２ **/
    private String _strN13_Remark02;
    public String getN13_Remark02() {
        return _strN13_Remark02;
    }

    /** 備考３ **/
    private String _strN13_Remark03;
    public String getN13_Remark03() {
        return _strN13_Remark03;
    }

    /** DNC請求日付 **/
    private int _intN13_DNC_Date;
    public int getN13_DNC_Date() {
        return _intN13_DNC_Date;
    }

    /** DNC請求時刻 **/
    private String _strN13_DNC_Time;
    public String getN13_DNC_Time() {
        return _strN13_DNC_Time;
    }

    /** 第1段階不合格日付 **/
    private int _intN13_DNC_Reject;
    public int getN13_DNC_Reject() {
        return _intN13_DNC_Reject;
    }

    /** DNC送信日 **/
    private int _intN13_DNC_RejectDate;
    public int getN13_DNC_RejectDate() {
        return _intN13_DNC_RejectDate;
    }

    /** 試験の出欠 **/
    private int _intN13_Attendance;
    public int getN13_Attendance() {
        return _intN13_Attendance;
    }

    /** 合否結果 **/
    private int _intN13_Pass;
    public int getN13_Pass() {
        return _intN13_Pass;
    }

    /** 合格した所属 **/
    private String _strN13_PassDepartment;
    public String getN13_PassDepartment() {
        return _strN13_PassDepartment;
    }

    /** DNC送信日 **/
    private int _intN13_DNC_EntDate;
    public int getN13_DNC_EntDate() {
        return _intN13_DNC_EntDate;
    }

    /** 入学手続日付 **/
    private int _intN13_EntProc;
    public int getN13_EntProc() {
        return _intN13_EntProc;
    }

    /** DNC送信日 **/
    private int _intN13_DNC_NotEnt;
    public int getN13_DNC_NotEnt() {
        return _intN13_DNC_NotEnt;
    }

    /** 入学辞退区分 **/
    private int _intN13_Refusal;
    public int getN13_Refusal() {
        return _intN13_Refusal;
    }

    /** 入学辞退日付 **/
    private int _intN13_RefusalDate;
    public int getN13_RefusalDate() {
        return _intN13_RefusalDate;
    }

    /** 受験資格 **/
    private int _intN13_Shikaku;
    public int getN13_Shikaku() {
        return _intN13_Shikaku;
    }

    /** 個人番号 **/
    private int _intP10_ID;
    public int getP10_ID() {
        return _intP10_ID;
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
        stbSql.append("\n" + "    N16_No,");
        stbSql.append("\n" + "    N16_ReceptDT,");
        stbSql.append("\n" + "    N16_NM,");
        stbSql.append("\n" + "    N16_KN,");
        stbSql.append("\n" + "    N16_Sex,");
        stbSql.append("\n" + "    N16_Birth,");
        stbSql.append("\n" + "    N16_Genro,");
        stbSql.append("\n" + "    N02_CD,");
        stbSql.append("\n" + "    N01_PlaceCD,");
        stbSql.append("\n" + "    C10_01,");
        stbSql.append("\n" + "    C10_02,");
        stbSql.append("\n" + "    C10_03,");
        stbSql.append("\n" + "    C10_04,");
        stbSql.append("\n" + "    C10_05,");
        stbSql.append("\n" + "    N13_DNC_Num1,");
        stbSql.append("\n" + "    N13_DNC_Num2,");
        stbSql.append("\n" + "    N13_DNC_NumCD,");
        stbSql.append("\n" + "    N13_DNC_Cnt,");
        stbSql.append("\n" + "    N13_DNC_Div,");
        stbSql.append("\n" + "    C03_CD,");
        stbSql.append("\n" + "    N13_Course,");
        stbSql.append("\n" + "    N13_Department,");
        stbSql.append("\n" + "    N13_GraduateFlg,");
        stbSql.append("\n" + "    N13_GraduateYear,");
        stbSql.append("\n" + "    N13_Requirement,");
        stbSql.append("\n" + "    N13_ConfRep_01,");
        stbSql.append("\n" + "    N13_ConfRep_02,");
        stbSql.append("\n" + "    N13_ConfRep_03,");
        stbSql.append("\n" + "    N13_ConfRep_04,");
        stbSql.append("\n" + "    N13_ConfRep_05,");
        stbSql.append("\n" + "    N13_ConfRep_06,");
        stbSql.append("\n" + "    N13_ConfRep_07,");
        stbSql.append("\n" + "    N13_ConfRep_08,");
        stbSql.append("\n" + "    N13_ConfRep_09,");
        stbSql.append("\n" + "    N13_ConfRep_10,");
        stbSql.append("\n" + "    N13_ConfRep_Total,");
        stbSql.append("\n" + "    N13_ConfRep_Average,");
        stbSql.append("\n" + "    N13_ConfRep_Total2,");
        stbSql.append("\n" + "    N13_ConfRep_Avrg2,");
        stbSql.append("\n" + "    N13_Absence_1,");
        stbSql.append("\n" + "    N13_Absence_2,");
        stbSql.append("\n" + "    N13_Absence_3,");
        stbSql.append("\n" + "    N13_Absence,");
        stbSql.append("\n" + "    N13_Zip,");
        stbSql.append("\n" + "    N13_Adres01,");
        stbSql.append("\n" + "    N13_Adres02,");
        stbSql.append("\n" + "    N13_Tel,");
        stbSql.append("\n" + "    C02_CountryCD,");
        stbSql.append("\n" + "    N13_PName,");
        stbSql.append("\n" + "    N13_PKana,");
        stbSql.append("\n" + "    N13_PZoku,");
        stbSql.append("\n" + "    N13_Pzip,");
        stbSql.append("\n" + "    N13_PAdres01,");
        stbSql.append("\n" + "    N13_PAdres02,");
        stbSql.append("\n" + "    N13_Ptel,");
        stbSql.append("\n" + "    N13_Remark01,");
        stbSql.append("\n" + "    N13_Remark02,");
        stbSql.append("\n" + "    N13_Remark03,");
        stbSql.append("\n" + "    N13_DNC_Date,");
        stbSql.append("\n" + "    N13_DNC_Time,");
        stbSql.append("\n" + "    N13_DNC_Reject,");
        stbSql.append("\n" + "    N13_DNC_RejectDate,");
        stbSql.append("\n" + "    N13_Attendance,");
        stbSql.append("\n" + "    N13_Pass,");
        stbSql.append("\n" + "    N13_PassDepartment,");
        stbSql.append("\n" + "    N13_DNC_EntDate,");
        stbSql.append("\n" + "    N13_EntProc,");
        stbSql.append("\n" + "    N13_DNC_NotEnt,");
        stbSql.append("\n" + "    N13_Refusal,");
        stbSql.append("\n" + "    N13_RefusalDate,");
        stbSql.append("\n" + "    N13_Shikaku,");
        stbSql.append("\n" + "    P10_ID");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    N16_Examinee");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    N16_Year = " + String.valueOf(_intN16_Year));
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    N16_Seq = " + String.valueOf(_intN16_Seq));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _intUpdate_Count = dbr.getInt("Update_Count");
                _strN16_No = dbr.getString("N16_No");
                _intN16_ReceptDT = dbr.getInt("N16_ReceptDT");
                _strN16_NM = dbr.getString("N16_NM");
                _strN16_KN = dbr.getString("N16_KN");
                _intN16_Sex = dbr.getInt("N16_Sex");
                _intN16_Birth = dbr.getInt("N16_Birth");
                _intN16_Genro = dbr.getInt("N16_Genro");
                _strN02_CD = dbr.getString("N02_CD");
                _strN01_PlaceCD = dbr.getString("N01_PlaceCD");
                _strC10_01 = dbr.getString("C10_01");
                _strC10_02 = dbr.getString("C10_02");
                _strC10_03 = dbr.getString("C10_03");
                _strC10_04 = dbr.getString("C10_04");
                _strC10_05 = dbr.getString("C10_05");
                _strN13_DNC_Num1 = dbr.getString("N13_DNC_Num1");
                _strN13_DNC_Num2 = dbr.getString("N13_DNC_Num2");
                _strN13_DNC_NumCD = dbr.getString("N13_DNC_NumCD");
                _strN13_DNC_Cnt = dbr.getString("N13_DNC_Cnt");
                _intN13_DNC_Div = dbr.getInt("N13_DNC_Div");
                _strC03_CD = dbr.getString("C03_CD");
                _intN13_Course = dbr.getInt("N13_Course");
                _intN13_Department = dbr.getInt("N13_Department");
                _intN13_GraduateFlg = dbr.getInt("N13_GraduateFlg");
                _intN13_GraduateYear = dbr.getInt("N13_GraduateYear");
                _intN13_Requirement = dbr.getInt("N13_Requirement");
                _decN13_ConfRep_01 = dbr.getBigDecimal("N13_ConfRep_01");
                _decN13_ConfRep_02 = dbr.getBigDecimal("N13_ConfRep_02");
                _decN13_ConfRep_03 = dbr.getBigDecimal("N13_ConfRep_03");
                _decN13_ConfRep_04 = dbr.getBigDecimal("N13_ConfRep_04");
                _decN13_ConfRep_05 = dbr.getBigDecimal("N13_ConfRep_05");
                _decN13_ConfRep_06 = dbr.getBigDecimal("N13_ConfRep_06");
                _decN13_ConfRep_07 = dbr.getBigDecimal("N13_ConfRep_07");
                _decN13_ConfRep_08 = dbr.getBigDecimal("N13_ConfRep_08");
                _decN13_ConfRep_09 = dbr.getBigDecimal("N13_ConfRep_09");
                _decN13_ConfRep_10 = dbr.getBigDecimal("N13_ConfRep_10");
                _decN13_ConfRep_Total = dbr.getBigDecimal("N13_ConfRep_Total");
                _decN13_ConfRep_Average = dbr.getBigDecimal("N13_ConfRep_Average");
                _decN13_ConfRep_Total2 = dbr.getBigDecimal("N13_ConfRep_Total2");
                _decN13_ConfRep_Avrg2 = dbr.getBigDecimal("N13_ConfRep_Avrg2");
                _intN13_Absence_1 = dbr.getInt("N13_Absence_1");
                _intN13_Absence_2 = dbr.getInt("N13_Absence_2");
                _intN13_Absence_3 = dbr.getInt("N13_Absence_3");
                _intN13_Absence = dbr.getInt("N13_Absence");
                _strN13_Zip = dbr.getString("N13_Zip");
                _strN13_Adres01 = dbr.getString("N13_Adres01");
                _strN13_Adres02 = dbr.getString("N13_Adres02");
                _strN13_Tel = dbr.getString("N13_Tel");
                _intC02_CountryCD = dbr.getInt("C02_CountryCD");
                _strN13_PName = dbr.getString("N13_PName");
                _strN13_PKana = dbr.getString("N13_PKana");
                _strN13_PZoku = dbr.getString("N13_PZoku");
                _strN13_Pzip = dbr.getString("N13_Pzip");
                _strN13_PAdres01 = dbr.getString("N13_PAdres01");
                _strN13_PAdres02 = dbr.getString("N13_PAdres02");
                _strN13_Ptel = dbr.getString("N13_Ptel");
                _strN13_Remark01 = dbr.getString("N13_Remark01");
                _strN13_Remark02 = dbr.getString("N13_Remark02");
                _strN13_Remark03 = dbr.getString("N13_Remark03");
                _intN13_DNC_Date = dbr.getInt("N13_DNC_Date");
                _strN13_DNC_Time = dbr.getString("N13_DNC_Time");
                _intN13_DNC_Reject = dbr.getInt("N13_DNC_Reject");
                _intN13_DNC_RejectDate = dbr.getInt("N13_DNC_RejectDate");
                _intN13_Attendance = dbr.getInt("N13_Attendance");
                _intN13_Pass = dbr.getInt("N13_Pass");
                _strN13_PassDepartment = dbr.getString("N13_PassDepartment");
                _intN13_DNC_EntDate = dbr.getInt("N13_DNC_EntDate");
                _intN13_EntProc = dbr.getInt("N13_EntProc");
                _intN13_DNC_NotEnt = dbr.getInt("N13_DNC_NotEnt");
                _intN13_Refusal = dbr.getInt("N13_Refusal");
                _intN13_RefusalDate = dbr.getInt("N13_RefusalDate");
                _intN13_Shikaku = dbr.getInt("N13_Shikaku");
                _intP10_ID = dbr.getInt("P10_ID");

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

