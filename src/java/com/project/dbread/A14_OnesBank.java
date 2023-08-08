package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 ホクコウ共通 】
================================================================================
   Program Number: A14_OnesBank.java
   Program Name  : 読込クラス (A14_OnesBank)
   Program Date  : 2009/03/09
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/03/09 : 新規作成
================================================================================
********************************************************************************
*/

public class A14_OnesBank {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 取引銀行コード **/
    private int _intA14_CD;
    public void setA14_CD(int intA14_CD) {
        _intA14_CD = intA14_CD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 銀行コード **/
    private int _intA12_BankCD;
    public int getA12_BankCD() {
        return _intA12_BankCD;
    }

    /** 銀行支店コード **/
    private int _intA13_BranchCD;
    public int getA13_BranchCD() {
        return _intA13_BranchCD;
    }

    /** 委託者コード **/
    private String _strA14_Entrust;
    public String getA14_Entrust() {
        return _strA14_Entrust;
    }

    /** 口座区分 **/
    private int _intA14_Div;
    public int getA14_Div() {
        return _intA14_Div;
    }

    /** 口座番号 **/
    private String _strA14_AccountNum;
    public String getA14_AccountNum() {
        return _strA14_AccountNum;
    }

    /** 口座名義(漢字) **/
    private String _strA14_Name;
    public String getA14_Name() {
        return _strA14_Name;
    }

    /** 口座名義(カナ) **/
    private String _strA14_NameKana;
    public String getA14_NameKana() {
        return _strA14_NameKana;
    }

    /** 全銀区分 **/
    private int _intA14_BankKB;
    public int getA14_BankKB() {
        return _intA14_BankKB;
    }

    /** 引落ファイル名(銀行引渡しﾌｧｲﾙ名) **/
    private String _strA14_OutFile;
    public String getA14_OutFile() {
        return _strA14_OutFile;
    }

    /** 引落ファイル名(銀行受入ﾌｧｲﾙ名) **/
    private String _strA14_InFile;
    public String getA14_InFile() {
        return _strA14_InFile;
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
        stbSql.append("\n" + "    A12_BankCD,");
        stbSql.append("\n" + "    A13_BranchCD,");
        stbSql.append("\n" + "    A14_Entrust,");
        stbSql.append("\n" + "    A14_Div,");
        stbSql.append("\n" + "    A14_AccountNum,");
        stbSql.append("\n" + "    A14_Name,");
        stbSql.append("\n" + "    A14_NameKana,");
        stbSql.append("\n" + "    A14_BankKB,");
        stbSql.append("\n" + "    A14_OutFile,");
        stbSql.append("\n" + "    A14_InFile");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    A14_OnesBank");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    A14_CD = " + String.valueOf(_intA14_CD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _intA12_BankCD = dbr.getInt("A12_BankCD");
                _intA13_BranchCD = dbr.getInt("A13_BranchCD");
                _strA14_Entrust = dbr.getString("A14_Entrust");
                _intA14_Div = dbr.getInt("A14_Div");
                _strA14_AccountNum = dbr.getString("A14_AccountNum");
                _strA14_Name = dbr.getString("A14_Name");
                _strA14_NameKana = dbr.getString("A14_NameKana");
                _intA14_BankKB = dbr.getInt("A14_BankKB");
                _strA14_OutFile = dbr.getString("A14_OutFile");
                _strA14_InFile = dbr.getString("A14_InFile");

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

