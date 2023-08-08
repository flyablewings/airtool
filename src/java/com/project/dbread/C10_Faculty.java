package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C10_Faculty.java
   Program Name  : 読込クラス (C10_Faculty)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C10_Faculty {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 学部学科コード **/
    private String _strC10_CD;
    public void setC10_CD(String strC10_CD) {
        _strC10_CD = strC10_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 学部学科名称 **/
    private String _strC10_NM;
    public String getC10_NM() {
        return _strC10_NM;
    }

    /** 学部学科名称英字 **/
    private String _strC10_NM_E;
    public String getC10_NM_E() {
        return _strC10_NM_E;
    }

    /** 学部学科略称 **/
    private String _strC10_RN;
    public String getC10_RN() {
        return _strC10_RN;
    }

    /** 学部学科略称英字 **/
    private String _strC10_RN_E;
    public String getC10_RN_E() {
        return _strC10_RN_E;
    }

    /** 第１学部学科名コード **/
    private String _strC09_CD1;
    public String getC09_CD1() {
        return _strC09_CD1;
    }

    /** 第２学部学科名コード **/
    private String _strC09_CD2;
    public String getC09_CD2() {
        return _strC09_CD2;
    }

    /** 第３学部学科名コード **/
    private String _strC09_CD3;
    public String getC09_CD3() {
        return _strC09_CD3;
    }

    /** 第４学部学科名コード **/
    private String _strC09_CD4;
    public String getC09_CD4() {
        return _strC09_CD4;
    }

    /** 第５学部学科名コード **/
    private String _strC09_CD5;
    public String getC09_CD5() {
        return _strC09_CD5;
    }

    /** 使用開始日付 **/
    private int _intC10_Start;
    public int getC10_Start() {
        return _intC10_Start;
    }

    /** 使用終了日付 **/
    private int _intC10_End;
    public int getC10_End() {
        return _intC10_End;
    }

    /** 代表者氏名 **/
    private String _strC10_Dai_NM;
    public String getC10_Dai_NM() {
        return _strC10_Dai_NM;
    }

    /** 代表者氏名英字 **/
    private String _strC10_Dai_NM_E;
    public String getC10_Dai_NM_E() {
        return _strC10_Dai_NM_E;
    }

    /** 代表者役職名 **/
    private String _strC10_DaiPostNM;
    public String getC10_DaiPostNM() {
        return _strC10_DaiPostNM;
    }

    /** 代表者役職名英字 **/
    private String _strC10_DaiPostNM_E;
    public String getC10_DaiPostNM_E() {
        return _strC10_DaiPostNM_E;
    }

    /** 入試センター学部コード **/
    private String _strC10_DNC_FCD;
    public String getC10_DNC_FCD() {
        return _strC10_DNC_FCD;
    }

    /** 入試センター学部名 **/
    private String _strC10_DNC_FNM;
    public String getC10_DNC_FNM() {
        return _strC10_DNC_FNM;
    }

    /** 証明書印刷用学位 **/
    private String _strC10_DegreeNM;
    public String getC10_DegreeNM() {
        return _strC10_DegreeNM;
    }

    /** 学部学科区分 **/
    private int _intC10_FacultyKB;
    public int getC10_FacultyKB() {
        return _intC10_FacultyKB;
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
        stbSql.append("\n" + "    C10_NM,");
        stbSql.append("\n" + "    C10_NM_E,");
        stbSql.append("\n" + "    C10_RN,");
        stbSql.append("\n" + "    C10_RN_E,");
        stbSql.append("\n" + "    C09_CD1,");
        stbSql.append("\n" + "    C09_CD2,");
        stbSql.append("\n" + "    C09_CD3,");
        stbSql.append("\n" + "    C09_CD4,");
        stbSql.append("\n" + "    C09_CD5,");
        stbSql.append("\n" + "    C10_Start,");
        stbSql.append("\n" + "    C10_End,");
        stbSql.append("\n" + "    C10_Dai_NM,");
        stbSql.append("\n" + "    C10_Dai_NM_E,");
        stbSql.append("\n" + "    C10_DaiPostNM,");
        stbSql.append("\n" + "    C10_DaiPostNM_E,");
        stbSql.append("\n" + "    C10_DNC_FCD,");
        stbSql.append("\n" + "    C10_DNC_FNM,");
        stbSql.append("\n" + "    C10_DegreeNM,");
        stbSql.append("\n" + "    C10_FacultyKB");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C10_Faculty");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C10_CD = '" + DB_CtrlChar.edit(_strC10_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC10_NM = dbr.getString("C10_NM");
                _strC10_NM_E = dbr.getString("C10_NM_E");
                _strC10_RN = dbr.getString("C10_RN");
                _strC10_RN_E = dbr.getString("C10_RN_E");
                _strC09_CD1 = dbr.getString("C09_CD1");
                _strC09_CD2 = dbr.getString("C09_CD2");
                _strC09_CD3 = dbr.getString("C09_CD3");
                _strC09_CD4 = dbr.getString("C09_CD4");
                _strC09_CD5 = dbr.getString("C09_CD5");
                _intC10_Start = dbr.getInt("C10_Start");
                _intC10_End = dbr.getInt("C10_End");
                _strC10_Dai_NM = dbr.getString("C10_Dai_NM");
                _strC10_Dai_NM_E = dbr.getString("C10_Dai_NM_E");
                _strC10_DaiPostNM = dbr.getString("C10_DaiPostNM");
                _strC10_DaiPostNM_E = dbr.getString("C10_DaiPostNM_E");
                _strC10_DNC_FCD = dbr.getString("C10_DNC_FCD");
                _strC10_DNC_FNM = dbr.getString("C10_DNC_FNM");
                _strC10_DegreeNM = dbr.getString("C10_DegreeNM");
                _intC10_FacultyKB = dbr.getInt("C10_FacultyKB");

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

