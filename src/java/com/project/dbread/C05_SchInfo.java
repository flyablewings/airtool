package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C05_SchInfo.java
   Program Name  : 読込クラス (C05_SchInfo)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C05_SchInfo {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 学校コード **/
    private int _intC05_CD;
    public void setC05_CD(int intC05_CD) {
        _intC05_CD = intC05_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 学校名称 **/
    private String _strC05_NM;
    public String getC05_NM() {
        return _strC05_NM;
    }

    /** 学校名称英名 **/
    private String _strC05_NM_E;
    public String getC05_NM_E() {
        return _strC05_NM_E;
    }

    /** 学校名称カナ **/
    private String _strC05_KN;
    public String getC05_KN() {
        return _strC05_KN;
    }

    /** 代表者氏名 **/
    private String _strC05_Dai_NM;
    public String getC05_Dai_NM() {
        return _strC05_Dai_NM;
    }

    /** 代表者氏名英字 **/
    private String _strC05_Dai_NM_E;
    public String getC05_Dai_NM_E() {
        return _strC05_Dai_NM_E;
    }

    /** 代表者役職名 **/
    private String _strC05_DaiPostNM;
    public String getC05_DaiPostNM() {
        return _strC05_DaiPostNM;
    }

    /** 代表者役職名英字 **/
    private String _strC05_DaiPostNM_E;
    public String getC05_DaiPostNM_E() {
        return _strC05_DaiPostNM_E;
    }

    /** 郵便番号 **/
    private String _strC05_Zip;
    public String getC05_Zip() {
        return _strC05_Zip;
    }

    /** 住所１ **/
    private String _strC05_Adrs1;
    public String getC05_Adrs1() {
        return _strC05_Adrs1;
    }

    /** 住所２ **/
    private String _strC05_Adrs2;
    public String getC05_Adrs2() {
        return _strC05_Adrs2;
    }

    /** 電話番号 **/
    private String _strC05_Tel;
    public String getC05_Tel() {
        return _strC05_Tel;
    }

    /** FAX番号 **/
    private String _strC05_Fax;
    public String getC05_Fax() {
        return _strC05_Fax;
    }

    /** 住所１英字 **/
    private String _strC05_Adrs1_E;
    public String getC05_Adrs1_E() {
        return _strC05_Adrs1_E;
    }

    /** 住所２英字 **/
    private String _strC05_Adrs2_E;
    public String getC05_Adrs2_E() {
        return _strC05_Adrs2_E;
    }

    /** 入試センター大学コード **/
    private String _strC05_DNCDaiCD;
    public String getC05_DNCDaiCD() {
        return _strC05_DNCDaiCD;
    }

    /** 学校区分 **/
    private int _intC05_SchoolKB;
    public int getC05_SchoolKB() {
        return _intC05_SchoolKB;
    }

    /** 学長教職員コード **/
    private String _strC05_GakuchoCD;
    public String getC05_GakuchoCD() {
        return _strC05_GakuchoCD;
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
        stbSql.append("\n" + "    C05_NM,");
        stbSql.append("\n" + "    C05_NM_E,");
        stbSql.append("\n" + "    C05_KN,");
        stbSql.append("\n" + "    C05_Dai_NM,");
        stbSql.append("\n" + "    C05_Dai_NM_E,");
        stbSql.append("\n" + "    C05_DaiPostNM,");
        stbSql.append("\n" + "    C05_DaiPostNM_E,");
        stbSql.append("\n" + "    C05_Zip,");
        stbSql.append("\n" + "    C05_Adrs1,");
        stbSql.append("\n" + "    C05_Adrs2,");
        stbSql.append("\n" + "    C05_Tel,");
        stbSql.append("\n" + "    C05_Fax,");
        stbSql.append("\n" + "    C05_Adrs1_E,");
        stbSql.append("\n" + "    C05_Adrs2_E,");
        stbSql.append("\n" + "    C05_DNCDaiCD,");
        stbSql.append("\n" + "    C05_SchoolKB,");
        stbSql.append("\n" + "    C05_GakuchoCD");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C05_SchInfo");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C05_CD = " + String.valueOf(_intC05_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC05_NM = dbr.getString("C05_NM");
                _strC05_NM_E = dbr.getString("C05_NM_E");
                _strC05_KN = dbr.getString("C05_KN");
                _strC05_Dai_NM = dbr.getString("C05_Dai_NM");
                _strC05_Dai_NM_E = dbr.getString("C05_Dai_NM_E");
                _strC05_DaiPostNM = dbr.getString("C05_DaiPostNM");
                _strC05_DaiPostNM_E = dbr.getString("C05_DaiPostNM_E");
                _strC05_Zip = dbr.getString("C05_Zip");
                _strC05_Adrs1 = dbr.getString("C05_Adrs1");
                _strC05_Adrs2 = dbr.getString("C05_Adrs2");
                _strC05_Tel = dbr.getString("C05_Tel");
                _strC05_Fax = dbr.getString("C05_Fax");
                _strC05_Adrs1_E = dbr.getString("C05_Adrs1_E");
                _strC05_Adrs2_E = dbr.getString("C05_Adrs2_E");
                _strC05_DNCDaiCD = dbr.getString("C05_DNCDaiCD");
                _intC05_SchoolKB = dbr.getInt("C05_SchoolKB");
                _strC05_GakuchoCD = dbr.getString("C05_GakuchoCD");

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

