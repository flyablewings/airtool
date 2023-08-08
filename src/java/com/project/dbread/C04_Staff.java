package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C04_Staff.java
   Program Name  : 読込クラス (C04_Staff)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C04_Staff {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 教職員コード **/
    private String _strC04_CD;
    public void setC04_CD(String strC04_CD) {
        _strC04_CD = strC04_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 教職員氏名 **/
    private String _strC04_NM;
    public String getC04_NM() {
        return _strC04_NM;
    }

    /** 教職員氏名英字 **/
    private String _strC04_NM_E;
    public String getC04_NM_E() {
        return _strC04_NM_E;
    }

    /** 教職員正式氏名 **/
    private String _strC04_SNM;
    public String getC04_SNM() {
        return _strC04_SNM;
    }

    /** 教職員正式氏名英字 **/
    private String _strC04_SNM_E;
    public String getC04_SNM_E() {
        return _strC04_SNM_E;
    }

    /** 教職員カナ名 **/
    private String _strC04_KN;
    public String getC04_KN() {
        return _strC04_KN;
    }

    /** 教職員略称 **/
    private String _strC04_RN;
    public String getC04_RN() {
        return _strC04_RN;
    }

    /** 生年月日 **/
    private int _intC04_BirthDT;
    public int getC04_BirthDT() {
        return _intC04_BirthDT;
    }

    /** 死亡年月日 **/
    private int _intC04_DeathDT;
    public int getC04_DeathDT() {
        return _intC04_DeathDT;
    }

    /** 性別 **/
    private int _intC04_Sex;
    public int getC04_Sex() {
        return _intC04_Sex;
    }

    /** 郵便番号 **/
    private String _strC04_Zip;
    public String getC04_Zip() {
        return _strC04_Zip;
    }

    /** 住所１ **/
    private String _strC04_Adrs1;
    public String getC04_Adrs1() {
        return _strC04_Adrs1;
    }

    /** 住所２ **/
    private String _strC04_Adrs2;
    public String getC04_Adrs2() {
        return _strC04_Adrs2;
    }

    /** 電話番号 **/
    private String _strC04_Tel;
    public String getC04_Tel() {
        return _strC04_Tel;
    }

    /** FAX番号 **/
    private String _strC04_Fax;
    public String getC04_Fax() {
        return _strC04_Fax;
    }

    /** 携帯電話 **/
    private String _strC04_Mobile;
    public String getC04_Mobile() {
        return _strC04_Mobile;
    }

    /** メールアドレス **/
    private String _strC04_Mail;
    public String getC04_Mail() {
        return _strC04_Mail;
    }

    /** パスワード **/
    private String _strC04_PW;
    public String getC04_PW() {
        return _strC04_PW;
    }

    /** 採用日付 **/
    private int _intC04_SaiyoDT;
    public int getC04_SaiyoDT() {
        return _intC04_SaiyoDT;
    }

    /** 退職日付 **/
    private int _intC04_TaisykDT;
    public int getC04_TaisykDT() {
        return _intC04_TaisykDT;
    }

    /** 役職コード **/
    private String _strC08_CD;
    public String getC08_CD() {
        return _strC08_CD;
    }

    /** 部課コード **/
    private String _strC11_CD;
    public String getC11_CD() {
        return _strC11_CD;
    }

    /** 教員分野コード **/
    private int _intC28_CD;
    public int getC28_CD() {
        return _intC28_CD;
    }

    /** 教員専攻コード **/
    private int _intC29_CD;
    public int getC29_CD() {
        return _intC29_CD;
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
        stbSql.append("\n" + "    C04_NM,");
        stbSql.append("\n" + "    C04_NM_E,");
        stbSql.append("\n" + "    C04_SNM,");
        stbSql.append("\n" + "    C04_SNM_E,");
        stbSql.append("\n" + "    C04_KN,");
        stbSql.append("\n" + "    C04_RN,");
        stbSql.append("\n" + "    C04_BirthDT,");
        stbSql.append("\n" + "    C04_DeathDT,");
        stbSql.append("\n" + "    C04_Sex,");
        stbSql.append("\n" + "    C04_Zip,");
        stbSql.append("\n" + "    C04_Adrs1,");
        stbSql.append("\n" + "    C04_Adrs2,");
        stbSql.append("\n" + "    C04_Tel,");
        stbSql.append("\n" + "    C04_Fax,");
        stbSql.append("\n" + "    C04_Mobile,");
        stbSql.append("\n" + "    C04_Mail,");
        stbSql.append("\n" + "    C04_PW,");
        stbSql.append("\n" + "    C04_SaiyoDT,");
        stbSql.append("\n" + "    C04_TaisykDT,");
        stbSql.append("\n" + "    C08_CD,");
        stbSql.append("\n" + "    C11_CD,");
        stbSql.append("\n" + "    C28_CD,");
        stbSql.append("\n" + "    C29_CD");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C04_Staff");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C04_CD = '" + DB_CtrlChar.edit(_strC04_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC04_NM = dbr.getString("C04_NM");
                _strC04_NM_E = dbr.getString("C04_NM_E");
                _strC04_SNM = dbr.getString("C04_SNM");
                _strC04_SNM_E = dbr.getString("C04_SNM_E");
                _strC04_KN = dbr.getString("C04_KN");
                _strC04_RN = dbr.getString("C04_RN");
                _intC04_BirthDT = dbr.getInt("C04_BirthDT");
                _intC04_DeathDT = dbr.getInt("C04_DeathDT");
                _intC04_Sex = dbr.getInt("C04_Sex");
                _strC04_Zip = dbr.getString("C04_Zip");
                _strC04_Adrs1 = dbr.getString("C04_Adrs1");
                _strC04_Adrs2 = dbr.getString("C04_Adrs2");
                _strC04_Tel = dbr.getString("C04_Tel");
                _strC04_Fax = dbr.getString("C04_Fax");
                _strC04_Mobile = dbr.getString("C04_Mobile");
                _strC04_Mail = dbr.getString("C04_Mail");
                _strC04_PW = dbr.getString("C04_PW");
                _intC04_SaiyoDT = dbr.getInt("C04_SaiyoDT");
                _intC04_TaisykDT = dbr.getInt("C04_TaisykDT");
                _strC08_CD = dbr.getString("C08_CD");
                _strC11_CD = dbr.getString("C11_CD");
                _intC28_CD = dbr.getInt("C28_CD");
                _intC29_CD = dbr.getInt("C29_CD");

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

