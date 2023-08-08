package com.project.dbread;

import java.math.BigDecimal;
import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K11_Student.java
   Program Name  : 読込クラス (K11_Student)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K11_Student {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 個人ID **/
    private int _intK12_ID = 0;
    public void setK12_ID(int intK12_ID) {
        _intK12_ID = intK12_ID;
    }

    /** 学籍番号 **/
    private String _strK11_No = "";
    public void setK11_No(String strK11_No) {
        _strK11_No = strK11_No;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 個人ID **/
    public int getK12_ID() {
        return _intK12_ID;
    }

    /** 学籍番号コード **/
    public String getK11_No() {
        return _strK11_No;
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

    /** 学年 **/
    private int _intK11_Grade;
    public int getK11_Grade() {
        return _intK11_Grade;
    }

    /** 組 **/
    private String _strK11_Class;
    public String getK11_Class() {
        return _strK11_Class;
    }

    /** 学籍状態 **/
    private int _intK11_State;
    public int getK11_State() {
        return _intK11_State;
    }

    /** 学生区分 **/
    private int _intK02_CD;
    public int getK02_CD() {
        return _intK02_CD;
    }

    /** 編入生区分 **/
    private int _intK11_AdmitStd;
    public int getK11_AdmitStd() {
        return _intK11_AdmitStd;
    }

    /** 在籍年数 **/
    private BigDecimal _decK11_StayYears;
    public BigDecimal getK11_StayYears() {
        return _decK11_StayYears;
    }

    /** 入学年月日 **/
    private int _intK11_EntDT;
    public int getK11_EntDT() {
        return _intK11_EntDT;
    }

    /** 入学年度 **/
    private int _intK11_EntYear;
    public int getK11_EntYear() {
        return _intK11_EntYear;
    }

    /** 入学年次 **/
    private int _intK11_EntGrade;
    public int getK11_EntGrade() {
        return _intK11_EntGrade;
    }

    /** 履修規定年度 **/
    private int _intK11_CrcYear;
    public int getK11_CrcYear() {
        return _intK11_CrcYear;
    }

    /** 受験番号 **/
    private String _strN16_No;
    public String getN16_No() {
        return _strN16_No;
    }

    /** 試験コード **/
    private String _strN02_CD;
    public String getN02_CD() {
        return _strN02_CD;
    }

    /** 寮希望区分 **/
    private int _intK11_Dormitory;
    public int getK11_Dormitory() {
        return _intK11_Dormitory;
    }

    /** 備考1 **/
    private String _strK11_Remark1;
    public String getK11_Remark1() {
        return _strK11_Remark1;
    }

    /** 備考2 **/
    private String _strK11_Remark2;
    public String getK11_Remark2() {
        return _strK11_Remark2;
    }

    /** 備考3 **/
    private String _strK11_Remark3;
    public String getK11_Remark3() {
        return _strK11_Remark3;
    }
    
    /** 減免コード **/
    private int _intC17_CD;
    public int getC17_CD() {
        return _intC17_CD;
    }

    /** 旧学籍番号 **/
    private String _strK11_OldNo;
    public String getK11_OldNo() {
        return _strK11_OldNo;
    }

    /** 学籍異動日付 **/
    private int _intK11_ChgDT;
    public int getK11_ChgDT() {
        return _intK11_ChgDT;
    }

    /** 卒業日付 **/
    private int _intK11_GdtDT;
    public int getK11_GdtDT() {
        return _intK11_GdtDT;
    }

    /** 卒業年度 **/
    private int _intK11_GdtYear;
    public int getK11_GdtYear() {
        return _intK11_GdtYear;
    }

    /** 卒業保留 **/
    private int _intK11_GdtRsv;
    public int getK11_GdtRsv() {
        return _intK11_GdtRsv;
    }

    /** 卒業見込 **/
    private int _intK11_GdtExpect;
    public int getK11_GdtExpect() {
        return _intK11_GdtExpect;
    }

    /** 学生会役職 **/
    private int _intC19_CD;
    public int getC19_CD() {
        return _intC19_CD;
    }

    /** クラブコード **/
    private int _intC15_CD;
    public int getC15_CD() {
        return _intC15_CD;
    }

    /** 寮コード **/
    private int _intC14_CD;
    public int getC14_CD() {
        return _intC14_CD;
    }

    /** DM発行区分 **/
    private int _intK11_DM;
    public int getK11_DM() {
        return _intK11_DM;
    }

    /** 後援会役職コード **/
    private int _intC21_CD;
    public int getC21_CD() {
        return _intC21_CD;
    }

    /** 学生氏名 **/
    private String _strK12_NM;
    public String getK12_NM() {
        return _strK12_NM;
    }

    /** フリガナ **/
    private String _strK12_KN;
    public String getK12_KN() {
        return _strK12_KN;
    }

    /** ローマ字 **/
    private String _strK12_NM_E;
    public String getK12_NM_E() {
        return _strK12_NM_E;
    }

    /** 旧姓1 **/
    private String _strK12_OldNM1;
    public String getK12_OldNM1() {
        return _strK12_OldNM1;
    }

    /** 旧姓2 **/
    private String _strK12_OldNM2;
    public String getK12_OldNM2() {
        return _strK12_OldNM2;
    }

    /** 性別 **/
    private int _intK12_Sex;
    public int getK12_Sex() {
        return _intK12_Sex;
    }

    /** 出身校コード **/
    private String _strC03_CD;
    public String getC03_CD() {
        return _strC03_CD;
    }

    /** 高校課程 **/
    private int _intN16_Course;
    public int getN16_Course() {
        return _intN16_Course;
    }

    /** 高校学科 **/
    private int _intN16_Department;
    public int getN16_Department() {
        return _intN16_Department;
    }

    /** 出身校卒業日付 **/
    private int _intK12_SchGdtDT;
    public int getK12_SchGdtDT() {
        return _intK12_SchGdtDT;
    }

    /** 生年月日 **/
    private int _intK12_Birth;
    public int getK12_Birth() {
        return _intK12_Birth;
    }

    /** 国籍 **/
    private int _intC02_CD;
    public int getC02_CD() {
        return _intC02_CD;
    }

    /** 本籍 **/
    private int _intK12_Domicile;
    public int getK12_Domicile() {
        return _intK12_Domicile;
    }

    /** 現_郵便番号 **/
    private String _strK12_NowZip;
    public String getK12_NowZip() {
        return _strK12_NowZip;
    }

    /** 現_住所1 **/
    private String _strK12_NowAdrs1;
    public String getK12_NowAdrs1() {
        return _strK12_NowAdrs1;
    }

    /** 現_住所2 **/
    private String _strK12_NowAdrs2;
    public String getK12_NowAdrs2() {
        return _strK12_NowAdrs2;
    }

    /** 現_電話番号 **/
    private String _strK12_NowTel;
    public String getK12_NowTel() {
        return _strK12_NowTel;
    }

    /** 現_携帯 **/
    private String _strK12_NowMobile;
    public String getK12_NowMobile() {
        return _strK12_NowMobile;
    }

    /** 現_メール **/
    private String _strK12_NowMail;
    public String getK12_NowMail() {
        return _strK12_NowMail;
    }

    /** 郵便番号 **/
    private String _strK12_RetZip;
    public String getK12_RetZip() {
        return _strK12_RetZip;
    }

    /** 住所1 **/
    private String _strK12_RetAdrs1;
    public String getK12_RetAdrs1() {
        return _strK12_RetAdrs1;
    }

    /** 住所2 **/
    private String _strK12_RetAdrs2;
    public String getK12_RetAdrs2() {
        return _strK12_RetAdrs2;
    }

    /** 電話 **/
    private String _strK12_RetTel;
    public String getK12_RetTel() {
        return _strK12_RetTel;
    }

    /** 市内県内区分 **/
    private int _intK12_RetLocal;
    public int getK12_RetLocal() {
        return _intK12_RetLocal;
    }

    /** 保護者 **/
    private String _strK12_Guardian;
    public String getK12_Guardian() {
        return _strK12_Guardian;
    }

    /** 続柄 **/
    private String _strK12_Relation;
    public String getK12_Relation() {
        return _strK12_Relation;
    }

    /** 保_フリガナ **/
    private String _strK12_GdnKN;
    public String getK12_GdnKN() {
        return _strK12_GdnKN;
    }

    /** 保_郵便 **/
    private String _strK12_GdnZip;
    public String getK12_GdnZip() {
        return _strK12_GdnZip;
    }

    /** 保_住所1 **/
    private String _strK12_GdnAdrs1;
    public String getK12_GdnAdrs1() {
        return _strK12_GdnAdrs1;
    }

    /** 保_住所2 **/
    private String _strK12_GdnAdrs2;
    public String getK12_GdnAdrs2() {
        return _strK12_GdnAdrs2;
    }

    /** 保_電話 **/
    private String _strK12_GdnTel;
    public String getK12_GdnTel() {
        return _strK12_GdnTel;
    }

    /** 保_緊急連絡先 **/
    private String _strK12_GdnEmgTel;
    public String getK12_GdnEmgTel() {
        return _strK12_GdnEmgTel;
    }

    /** 同窓会番号 **/
    private String _strK12_AlumniNo;
    public String getK12_AlumniNo() {
        return _strK12_AlumniNo;
    }

    /** 同窓会役職コード **/
    private int _intC20_CD;
    public int getC20_CD() {
        return _intC20_CD;
    }

    /** 学部学科名称 **/
    private String _strC10_NM;
    public String getC10_NM() {
        return _strC10_NM;
    }
    
    /** 学部学科略称 **/
    private String _strC10_RN;
    public String getC10_RN() {
        return _strC10_RN;
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
        stbSql.append("\n" + "	  K11.K12_ID,");
        stbSql.append("\n" + "	  K11.K11_No,");
        stbSql.append("\n" + "	  K11.C10_CD,");
        stbSql.append("\n" + "	  K11.C16_CD,");
        stbSql.append("\n" + "    K11.K11_Grade,");
        stbSql.append("\n" + "    K11.K11_Class,");
        stbSql.append("\n" + "    K11.K11_State,");
        stbSql.append("\n" + "    K11.K02_CD,");
        stbSql.append("\n" + "    K11.K11_AdmitStd,");
        stbSql.append("\n" + "    K11.K11_StayYears,");
        stbSql.append("\n" + "    K11.K11_EntDT,");
        stbSql.append("\n" + "    K11.K11_EntYear,");
        stbSql.append("\n" + "    K11.K11_EntGrade,");
        stbSql.append("\n" + "    K11.K11_CrcYear,");
        stbSql.append("\n" + "    K11.N16_No,");
        stbSql.append("\n" + "    K11.N02_CD,");
        stbSql.append("\n" + "    K11.K11_Dormitory,");
        stbSql.append("\n" + "    K11.K11_Remark1,");
        stbSql.append("\n" + "    K11.K11_Remark2,");
        stbSql.append("\n" + "    K11.K11_Remark3,");
        stbSql.append("\n" + "    K11.C17_CD,");
        stbSql.append("\n" + "    K11.K11_OldNo,");
        stbSql.append("\n" + "    K11.K11_ChgDT,");
        stbSql.append("\n" + "    K11.K11_GdtDT,");
        stbSql.append("\n" + "    K11.K11_GdtYear,");
        stbSql.append("\n" + "    K11.K11_GdtRsv,");
        stbSql.append("\n" + "    K11.K11_GdtExpect,");
        stbSql.append("\n" + "    K11.C19_CD,");
        stbSql.append("\n" + "    K11.C15_CD,");
        stbSql.append("\n" + "    K11.C14_CD,");
        stbSql.append("\n" + "    K11.K11_DM,");
        stbSql.append("\n" + "    K11.C21_CD,");
        stbSql.append("\n" + "    K11.K12_AlumniNo,");
        stbSql.append("\n" + "    K11.C20_CD,");
        stbSql.append("\n" + "    K12.K12_NM,");
        stbSql.append("\n" + "    K12.K12_KN,");
        stbSql.append("\n" + "    K12.K12_NM_E,");
        stbSql.append("\n" + "    K12.K12_OldNM1,");
        stbSql.append("\n" + "    K12.K12_OldNM2,");
        stbSql.append("\n" + "    K12.K12_Sex,");
        stbSql.append("\n" + "    K12.C03_CD,");
        stbSql.append("\n" + "    K12.N16_Course,");
        stbSql.append("\n" + "    K12.N16_Department,");
        stbSql.append("\n" + "    K12.K12_SchGdtDT,");
        stbSql.append("\n" + "    K12.K12_Birth,");
        stbSql.append("\n" + "    K12.C02_CD,");
        stbSql.append("\n" + "    K12.K12_Domicile,");
        stbSql.append("\n" + "    K12.K12_NowZip,");
        stbSql.append("\n" + "    K12.K12_NowAdrs1,");
        stbSql.append("\n" + "    K12.K12_NowAdrs2,");
        stbSql.append("\n" + "    K12.K12_NowTel,");
        stbSql.append("\n" + "    K12.K12_NowMobile,");
        stbSql.append("\n" + "    K12.K12_NowMail,");
        stbSql.append("\n" + "    K12.K12_RetZip,");
        stbSql.append("\n" + "    K12.K12_RetAdrs1,");
        stbSql.append("\n" + "    K12.K12_RetAdrs2,");
        stbSql.append("\n" + "    K12.K12_RetTel,");
        stbSql.append("\n" + "    K12.K12_RetLocal,");
        stbSql.append("\n" + "    K12.K12_Guardian,");
        stbSql.append("\n" + "    K12.K12_Relation,");
        stbSql.append("\n" + "    K12.K12_GdnKN,");
        stbSql.append("\n" + "    K12.K12_GdnZip,");
        stbSql.append("\n" + "    K12.K12_GdnAdrs1,");
        stbSql.append("\n" + "    K12.K12_GdnAdrs2,");
        stbSql.append("\n" + "    K12.K12_GdnTel,");
        stbSql.append("\n" + "    K12.K12_GdnEmgTel,");
        stbSql.append("\n" + "    C10.C10_NM,");
        stbSql.append("\n" + "    C10.C10_RN");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K11_Student	AS K11");
        stbSql.append("\n" + "	  LEFT JOIN");
        stbSql.append("\n" + "    	  K12_Personal AS K12 ON K11.K12_ID = K12.K12_ID");
        stbSql.append("\n" + "	  LEFT JOIN");
        stbSql.append("\n" + "    	  C10_Faculty  AS C10 ON C10.C10_CD = K11.C10_CD");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "	  1 = 1");
        
        if (_intK12_ID != 0) {
            stbSql.append("\n" + "AND");
            stbSql.append("\n" + "    K11.K12_ID = " + String.valueOf(_intK12_ID));
        }
        
        if (!_strK11_No.equals("")) {
            stbSql.append("\n" + "AND");
            stbSql.append("\n" + "    K11.K11_No = '" + DB_CtrlChar.edit(_strK11_No) + "'");
        }

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
            	_intK12_ID = dbr.getInt("K12_ID");
                _strK11_No = dbr.getString("K11_No");
                _strC10_CD = dbr.getString("C10_CD");
                _strC16_CD = dbr.getString("C16_CD");
                _intK11_Grade = dbr.getInt("K11_Grade");
                _strK11_Class = dbr.getString("K11_Class");
                _intK11_State = dbr.getInt("K11_State");
                _intK02_CD = dbr.getInt("K02_CD");
                _intK11_AdmitStd = dbr.getInt("K11_AdmitStd");
                _decK11_StayYears = dbr.getBigDecimal("K11_StayYears");
                _intK11_EntDT = dbr.getInt("K11_EntDT");
                _intK11_EntYear = dbr.getInt("K11_EntYear");
                _intK11_EntGrade = dbr.getInt("K11_EntGrade");
                _intK11_CrcYear = dbr.getInt("K11_CrcYear");
                _strN16_No = dbr.getString("N16_No");
                _strN02_CD = dbr.getString("N02_CD");
                _intK11_Dormitory = dbr.getInt("K11_Dormitory");
                _strK11_Remark1 = dbr.getString("K11_Remark1");
                _strK11_Remark2 = dbr.getString("K11_Remark2");
                _strK11_Remark3 = dbr.getString("K11_Remark3");
                _intC17_CD = dbr.getInt("C17_CD");
                _strK11_OldNo = dbr.getString("K11_OldNo");
                _intK11_ChgDT = dbr.getInt("K11_ChgDT");
                _intK11_GdtDT = dbr.getInt("K11_GdtDT");
                _intK11_GdtYear = dbr.getInt("K11_GdtYear");
                _intK11_GdtRsv = dbr.getInt("K11_GdtRsv");
                _intK11_GdtExpect = dbr.getInt("K11_GdtExpect");
                _intC19_CD = dbr.getInt("C19_CD");
                _intC15_CD = dbr.getInt("C15_CD");
                _intC14_CD = dbr.getInt("C14_CD");
                _intK11_DM = dbr.getInt("K11_DM");
                _intC21_CD = dbr.getInt("C21_CD");

                _strK12_NM = dbr.getString("K12_NM");
                _strK12_KN = dbr.getString("K12_KN");
                _strK12_NM_E = dbr.getString("K12_NM_E");
                _strK12_OldNM1 = dbr.getString("K12_OldNM1");
                _strK12_OldNM2 = dbr.getString("K12_OldNM2");
                _intK12_Sex = dbr.getInt("K12_Sex");
                _strC03_CD = dbr.getString("C03_CD");
                _intN16_Course = dbr.getInt("N16_Course");
                _intN16_Department = dbr.getInt("N16_Department");
                _intK12_SchGdtDT = dbr.getInt("K12_SchGdtDT");
                _intK12_Birth = dbr.getInt("K12_Birth");
                _intK12_Domicile = dbr.getInt("K12_Domicile");
                _strK12_NowZip = dbr.getString("K12_NowZip");
                _strK12_NowAdrs1 = dbr.getString("K12_NowAdrs1");
                _strK12_NowAdrs2 = dbr.getString("K12_NowAdrs2");
                _strK12_NowTel = dbr.getString("K12_NowTel");
                _strK12_NowMobile = dbr.getString("K12_NowMobile");
                _strK12_NowMail = dbr.getString("K12_NowMail");
                _strK12_RetZip = dbr.getString("K12_RetZip");
                _strK12_RetAdrs1 = dbr.getString("K12_RetAdrs1");
                _strK12_RetAdrs2 = dbr.getString("K12_RetAdrs2");
                _strK12_RetTel = dbr.getString("K12_RetTel");
                _strK12_Guardian = dbr.getString("K12_Guardian");
                _strK12_Relation = dbr.getString("K12_Relation");
                _strK12_GdnKN = dbr.getString("K12_GdnKN");
                _strK12_GdnZip = dbr.getString("K12_GdnZip");
                _strK12_GdnAdrs1 = dbr.getString("K12_GdnAdrs1");
                _strK12_GdnAdrs2 = dbr.getString("K12_GdnAdrs2");
                _strK12_GdnTel = dbr.getString("K12_GdnTel");
                _strK12_GdnEmgTel = dbr.getString("K12_GdnEmgTel");
                _strK12_AlumniNo = dbr.getString("K12_AlumniNo");
                _intC20_CD = dbr.getInt("C20_CD");
                _strC10_NM = dbr.getString("C10_NM");
                _strC10_RN = dbr.getString("C10_RN");

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

