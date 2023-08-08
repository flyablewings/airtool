package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 mathfia 共通 】
================================================================================
   Program Number: C07_Sisetu.java
   Program Name  : 読込クラス (C07_Sisetu)
   Program Date  : 2009/01/16
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/01/16 : 新規作成
================================================================================
********************************************************************************
*/

public class C07_Sisetu {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 施設コード **/
    private String _strC07_CD;
    public void setC07_CD(String strC07_CD) {
        _strC07_CD = strC07_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 施設名称 **/
    private String _strC07_NM;
    public String getC07_NM() {
        return _strC07_NM;
    }

    /** 施設名称英字 **/
    private String _strC07_NM_E;
    public String getC07_NM_E() {
        return _strC07_NM_E;
    }

    /** 施設略称 **/
    private String _strC07_RN;
    public String getC07_RN() {
        return _strC07_RN;
    }

    /** ｷｬﾝﾊﾟｽｺｰﾄﾞ **/
    private int _intC06_CD;
    public int getC06_CD() {
        return _intC06_CD;
    }

    /** 建物ｺｰﾄﾞ **/
    private int _intC30_CD;
    public int getC30_CD() {
        return _intC30_CD;
    }

    /** 施設分類ｺｰﾄﾞ **/
    private int _intC31_CD;
    public int getC31_CD() {
        return _intC31_CD;
    }

    /** 収容人数 **/
    private int _intC07_Ninzu;
    public int getC07_Ninzu() {
        return _intC07_Ninzu;
    }

    /** 設備 **/
    private String _strC07_Setubi;
    public String getC07_Setubi() {
        return _strC07_Setubi;
    }

    /** 重複可能区分 **/
    private int _intC07_DoubleKB;
    public int getC07_DoubleKB() {
        return _intC07_DoubleKB;
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
        stbSql.append("\n" + "    C07_NM,");
        stbSql.append("\n" + "    C07_NM_E,");
        stbSql.append("\n" + "    C07_RN,");
        stbSql.append("\n" + "    C06_CD,");
        stbSql.append("\n" + "    C30_CD,");
        stbSql.append("\n" + "    C31_CD,");
        stbSql.append("\n" + "    C07_Ninzu,");
        stbSql.append("\n" + "    C07_Setubi,");
        stbSql.append("\n" + "    C07_DoubleKB");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    C07_Sisetu");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    C07_CD = '" + DB_CtrlChar.edit(_strC07_CD) + "'");

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strC07_NM = dbr.getString("C07_NM");
                _strC07_NM_E = dbr.getString("C07_NM_E");
                _strC07_RN = dbr.getString("C07_RN");
                _intC06_CD = dbr.getInt("C06_CD");
                _intC30_CD = dbr.getInt("C30_CD");
                _intC31_CD = dbr.getInt("C31_CD");
                _intC07_Ninzu = dbr.getInt("C07_Ninzu");
                _strC07_Setubi = dbr.getString("C07_Setubi");
                _intC07_DoubleKB = dbr.getInt("C07_DoubleKB");

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

