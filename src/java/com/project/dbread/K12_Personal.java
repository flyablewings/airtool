package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 教務 】
================================================================================
   Program Number: K12_Personal.java
   Program Name  : 読込クラス (K12_Personal)
   Program Date  : 2009/01/27
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/27 : 新規作成
================================================================================
********************************************************************************
*/

public class K12_Personal {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 個人ID **/
    private int _intK12_ID;
    public void setK12_ID(int intK12_ID) {
        _intK12_ID = intK12_ID;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

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

    /** 旧姓 **/
    private String _strK12_OldNM;
    public String getK12_OldNM() {
        return _strK12_OldNM;
    }

    /** 改姓 **/
    private String _strK11_ChgNM;
    public String getK11_ChgNM() {
        return _strK11_ChgNM;
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
    private int _intN13_Course;
    public int getN13_Course() {
        return _intN13_Course;
    }

    /** 高校学科 **/
    private int _intN13_Department;
    public int getN13_Department() {
        return _intN13_Department;
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

    /** 現_郵便バー **/
    private String _strK12_NowZipBar;
    public String getK12_NowZipBar() {
        return _strK12_NowZipBar;
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
    private String _strK12_Zip;
    public String getK12_Zip() {
        return _strK12_Zip;
    }

    /** 住所1 **/
    private String _strK12_Adrs1;
    public String getK12_Adrs1() {
        return _strK12_Adrs1;
    }

    /** 住所2 **/
    private String _strK12_Adrs2;
    public String getK12_Adrs2() {
        return _strK12_Adrs2;
    }

    /** 郵便バー **/
    private String _strK12_ZipBar;
    public String getK12_ZipBar() {
        return _strK12_ZipBar;
    }

    /** 電話 **/
    private String _strK12_Tel;
    public String getK12_Tel() {
        return _strK12_Tel;
    }

    /** 保護者 **/
    private String _strK12_Guardian;
    public String getK12_Guardian() {
        return _strK12_Guardian;
    }

    /** 続柄 **/
    private String _strK12_Relationship;
    public String getK12_Relationship() {
        return _strK12_Relationship;
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

    /** 保_郵便バー **/
    private String _strK12_GdnZipBar;
    public String getK12_GdnZipBar() {
        return _strK12_GdnZipBar;
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

    /*--------------------------------------------------*
     *--- メインの処理                               ---*
     *--------------------------------------------------*/

    public boolean read(DB_Connection dbc) throws DB_Exception, Exception {
        DB_Statement dbs = null;
        DB_ResultSet dbr = null;

        boolean blnExist;
        StringBuffer stbSql = new StringBuffer();

        stbSql.append("\n" + "SELECT");
        stbSql.append("\n" + "    K12_NM,");
        stbSql.append("\n" + "    K12_KN,");
        stbSql.append("\n" + "    K12_NM_E,");
        stbSql.append("\n" + "    K12_OldNM,");
        stbSql.append("\n" + "    K11_ChgNM,");
        stbSql.append("\n" + "    K12_Sex,");
        stbSql.append("\n" + "    C03_CD,");
        stbSql.append("\n" + "    N13_Course,");
        stbSql.append("\n" + "    N13_Department,");
        stbSql.append("\n" + "    K12_SchGdtDT,");
        stbSql.append("\n" + "    K12_Birth,");
        stbSql.append("\n" + "    K12_Domicile,");
        stbSql.append("\n" + "    K12_NowZip,");
        stbSql.append("\n" + "    K12_NowAdrs1,");
        stbSql.append("\n" + "    K12_NowAdrs2,");
        stbSql.append("\n" + "    K12_NowZipBar,");
        stbSql.append("\n" + "    K12_NowTel,");
        stbSql.append("\n" + "    K12_NowMobile,");
        stbSql.append("\n" + "    K12_NowMail,");
        stbSql.append("\n" + "    K12_Zip,");
        stbSql.append("\n" + "    K12_Adrs1,");
        stbSql.append("\n" + "    K12_Adrs2,");
        stbSql.append("\n" + "    K12_ZipBar,");
        stbSql.append("\n" + "    K12_Tel,");
        stbSql.append("\n" + "    K12_Guardian,");
        stbSql.append("\n" + "    K12_Relationship,");
        stbSql.append("\n" + "    K12_GdnKN,");
        stbSql.append("\n" + "    K12_GdnZip,");
        stbSql.append("\n" + "    K12_GdnAdrs1,");
        stbSql.append("\n" + "    K12_GdnAdrs2,");
        stbSql.append("\n" + "    K12_GdnZipBar,");
        stbSql.append("\n" + "    K12_GdnTel,");
        stbSql.append("\n" + "    K12_GdnEmgTel,");
        stbSql.append("\n" + "    K12_AlumniNo,");
        stbSql.append("\n" + "    C20_CD");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    K12_Personal");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    K12_ID = " + String.valueOf(_intK12_ID));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strK12_NM = dbr.getString("K12_NM");
                _strK12_KN = dbr.getString("K12_KN");
                _strK12_NM_E = dbr.getString("K12_NM_E");
                _strK12_OldNM = dbr.getString("K12_OldNM");
                _strK11_ChgNM = dbr.getString("K11_ChgNM");
                _intK12_Sex = dbr.getInt("K12_Sex");
                _strC03_CD = dbr.getString("C03_CD");
                _intN13_Course = dbr.getInt("N13_Course");
                _intN13_Department = dbr.getInt("N13_Department");
                _intK12_SchGdtDT = dbr.getInt("K12_SchGdtDT");
                _intK12_Birth = dbr.getInt("K12_Birth");
                _intK12_Domicile = dbr.getInt("K12_Domicile");
                _strK12_NowZip = dbr.getString("K12_NowZip");
                _strK12_NowAdrs1 = dbr.getString("K12_NowAdrs1");
                _strK12_NowAdrs2 = dbr.getString("K12_NowAdrs2");
                _strK12_NowZipBar = dbr.getString("K12_NowZipBar");
                _strK12_NowTel = dbr.getString("K12_NowTel");
                _strK12_NowMobile = dbr.getString("K12_NowMobile");
                _strK12_NowMail = dbr.getString("K12_NowMail");
                _strK12_Zip = dbr.getString("K12_Zip");
                _strK12_Adrs1 = dbr.getString("K12_Adrs1");
                _strK12_Adrs2 = dbr.getString("K12_Adrs2");
                _strK12_ZipBar = dbr.getString("K12_ZipBar");
                _strK12_Tel = dbr.getString("K12_Tel");
                _strK12_Guardian = dbr.getString("K12_Guardian");
                _strK12_Relationship = dbr.getString("K12_Relationship");
                _strK12_GdnKN = dbr.getString("K12_GdnKN");
                _strK12_GdnZip = dbr.getString("K12_GdnZip");
                _strK12_GdnAdrs1 = dbr.getString("K12_GdnAdrs1");
                _strK12_GdnAdrs2 = dbr.getString("K12_GdnAdrs2");
                _strK12_GdnZipBar = dbr.getString("K12_GdnZipBar");
                _strK12_GdnTel = dbr.getString("K12_GdnTel");
                _strK12_GdnEmgTel = dbr.getString("K12_GdnEmgTel");
                _strK12_AlumniNo = dbr.getString("K12_AlumniNo");
                _intC20_CD = dbr.getInt("C20_CD");

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

