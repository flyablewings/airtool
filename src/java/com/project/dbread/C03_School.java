package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C03_School.java
   Program Name  : 読込クラス (C03_School)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C03_School {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 出身校コード **/
    private String _strC03_CD;
    public void setC03_CD(String strC03_CD) {
        _strC03_CD = strC03_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 出身校名称 **/
    private String _strC03_NM;
    public String getC03_NM() {
        return _strC03_NM;
    }

    /** 出身校名称英字 **/
    private String _strC03_NM_E;
    public String getC03_NM_E() {
        return _strC03_NM_E;
    }

    /** 出身校略称 **/
    private String _strC03_RN;
    public String getC03_RN() {
        return _strC03_RN;
    }

    /** 出身校カナ **/
    private String _strC03_KN;
    public String getC03_KN() {
        return _strC03_KN;
    }

    /** 国コード **/
    private int _intC02_CD;
    public int getC02_CD() {
        return _intC02_CD;
    }

    /** 県コード **/
    private int _intC01_CD;
    public int getC01_CD() {
        return _intC01_CD;
    }

    /** 郵便番号 **/
    private String _strC03_Zip;
    public String getC03_Zip() {
        return _strC03_Zip;
    }

    /** 住所１ **/
    private String _strC03_Adrs1;
    public String getC03_Adrs1() {
        return _strC03_Adrs1;
    }

    /** 住所２ **/
    private String _strC03_Adrs2;
    public String getC03_Adrs2() {
        return _strC03_Adrs2;
    }

    /** 電話番号 **/
    private String _strC03_Tel;
    public String getC03_Tel() {
        return _strC03_Tel;
    }

    /** FAX番号 **/
    private String _strC03_Fax;
    public String getC03_Fax() {
        return _strC03_Fax;
    }

    /** 学校区分 **/
    private int _intC03_SchoolKB;
    public int getC03_SchoolKB() {
        return _intC03_SchoolKB;
    }

    /** 課程(全日制) **/
    private int _intC03_Katei_Zen;
    public int getC03_Katei_Zen() {
        return _intC03_Katei_Zen;
    }

    /** 課程(定時制) **/
    private int _intC03_Katei_Tei;
    public int getC03_Katei_Tei() {
        return _intC03_Katei_Tei;
    }

    /** 課程(通信制) **/
    private int _intC03_Katei_Tsu;
    public int getC03_Katei_Tsu() {
        return _intC03_Katei_Tsu;
    }

    /** 出身学科(普通科) **/
    private int _intC03_Gakka_Fut;
    public int getC03_Gakka_Fut() {
        return _intC03_Gakka_Fut;
    }

    /** 出身学科(理数科) **/
    private int _intC03_Gakka_Ris;
    public int getC03_Gakka_Ris() {
        return _intC03_Gakka_Ris;
    }

    /** 出身学科(農業科) **/
    private int _intC03_Gakka_Nou;
    public int getC03_Gakka_Nou() {
        return _intC03_Gakka_Nou;
    }

    /** 出身学科(工業科) **/
    private int _intC03_Gakka_Kou;
    public int getC03_Gakka_Kou() {
        return _intC03_Gakka_Kou;
    }

    /** 出身学科(商業科) **/
    private int _intC03_Gakka_Syo;
    public int getC03_Gakka_Syo() {
        return _intC03_Gakka_Syo;
    }

    /** 出身学科(その他) **/
    private int _intC03_Gakka_Etc;
    public int getC03_Gakka_Etc() {
        return _intC03_Gakka_Etc;
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
        stbSql.append("\n" + "    C03_NM,");
        stbSql.append("\n" + "    C03_NM_E,");
        stbSql.append("\n" + "    C03_RN,");
        stbSql.append("\n" + "    C03_KN,");
        stbSql.append("\n" + "    C02_CD,");
        stbSql.append("\n" + "    C01_CD,");
        stbSql.append("\n" + "    C03_Zip,");
        stbSql.append("\n" + "    C03_Adrs1,");
        stbSql.append("\n" + "    C03_Adrs2,");
        stbSql.append("\n" + "    C03_Tel,");
        stbSql.append("\n" + "    C03_Fax,");
        stbSql.append("\n" + "    C03_SchoolKB,");
        stbSql.append("\n" + "    C03_Katei_Zen,");
        stbSql.append("\n" + "    C03_Katei_Tei,");
        stbSql.append("\n" + "    C03_Katei_Tsu,");
        stbSql.append("\n" + "    C03_Gakka_Fut,");
        stbSql.append("\n" + "    C03_Gakka_Ris,");
        stbSql.append("\n" + "    C03_Gakka_Nou,");
        stbSql.append("\n" + "    C03_Gakka_Kou,");
        stbSql.append("\n" + "    C03_Gakka_Syo,");
        stbSql.append("\n" + "    C03_Gakka_Etc");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C03_School");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C03_CD = '" + DB_CtrlChar.edit(_strC03_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC03_NM = dbr.getString("C03_NM");
                _strC03_NM_E = dbr.getString("C03_NM_E");
                _strC03_RN = dbr.getString("C03_RN");
                _strC03_KN = dbr.getString("C03_KN");
                _intC02_CD = dbr.getInt("C02_CD");
                _intC01_CD = dbr.getInt("C01_CD");
                _strC03_Zip = dbr.getString("C03_Zip");
                _strC03_Adrs1 = dbr.getString("C03_Adrs1");
                _strC03_Adrs2 = dbr.getString("C03_Adrs2");
                _strC03_Tel = dbr.getString("C03_Tel");
                _strC03_Fax = dbr.getString("C03_Fax");
                _intC03_SchoolKB = dbr.getInt("C03_SchoolKB");
                _intC03_Katei_Zen = dbr.getInt("C03_Katei_Zen");
                _intC03_Katei_Tei = dbr.getInt("C03_Katei_Tei");
                _intC03_Katei_Tsu = dbr.getInt("C03_Katei_Tsu");
                _intC03_Gakka_Fut = dbr.getInt("C03_Gakka_Fut");
                _intC03_Gakka_Ris = dbr.getInt("C03_Gakka_Ris");
                _intC03_Gakka_Nou = dbr.getInt("C03_Gakka_Nou");
                _intC03_Gakka_Kou = dbr.getInt("C03_Gakka_Kou");
                _intC03_Gakka_Syo = dbr.getInt("C03_Gakka_Syo");
                _intC03_Gakka_Etc = dbr.getInt("C03_Gakka_Etc");

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

