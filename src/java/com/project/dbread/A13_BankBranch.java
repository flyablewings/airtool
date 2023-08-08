package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 ホクコウ共通 】
================================================================================
   Program Number: A13_BankBranch.java
   Program Name  : 読込クラス (A13_BankBranch)
   Program Date  : 2009/03/09
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/03/09 : 新規作成
================================================================================
********************************************************************************
*/

public class A13_BankBranch {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 銀行コード **/
    private int _intA12_BankCD;
    public void setA12_BankCD(int intA12_BankCD) {
        _intA12_BankCD = intA12_BankCD;
    }

    /** 支店コード **/
    private int _intA13_BranchCD;
    public void setA13_BranchCD(int intA13_BranchCD) {
        _intA13_BranchCD = intA13_BranchCD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 支店名称 **/
    private String _strA13_BranchNM;
    public String getA13_BranchNM() {
        return _strA13_BranchNM;
    }

    /** 支店カナ名 **/
    private String _strA13_BranchKN;
    public String getA13_BranchKN() {
        return _strA13_BranchKN;
    }

    /** 支店略称 **/
    private String _strA13_BranchRN;
    public String getA13_BranchRN() {
        return _strA13_BranchRN;
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
        stbSql.append("\n" + "    A13_BranchNM,");
        stbSql.append("\n" + "    A13_BranchKN,");
        stbSql.append("\n" + "    A13_BranchRN");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    A13_BankBranch");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    A12_BankCD = " + String.valueOf(_intA12_BankCD));
        stbSql.append("\n" + "AND");
        stbSql.append("\n" + "    A13_BranchCD = " + String.valueOf(_intA13_BranchCD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strA13_BranchNM = dbr.getString("A13_BranchNM");
                _strA13_BranchKN = dbr.getString("A13_BranchKN");
                _strA13_BranchRN = dbr.getString("A13_BranchRN");

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

