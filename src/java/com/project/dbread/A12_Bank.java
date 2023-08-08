package com.project.dbread;

import com.hokukou.database.*;

/*
********************************************************************************
================================================================================
      【 ホクコウ共通 】
================================================================================
   Program Number: A12_Bank.java
   Program Name  : 読込クラス (A12_Bank)
   Program Date  : 2009/03/09
   Programmer    : Excel Macro Auto Create

===< Update History >===========================================================
   2009/03/09 : 新規作成
================================================================================
********************************************************************************
*/

public class A12_Bank {

    /*--------------------------------------------------*
     *--- 引数                                       ---*
     *--------------------------------------------------*/

    /** 銀行コード **/
    private int _intA12_BankCD;
    public void setA12_BankCD(int intA12_BankCD) {
        _intA12_BankCD = intA12_BankCD;
    }

    /*--------------------------------------------------*
     *--- 戻り値                                     ---*
     *--------------------------------------------------*/

    /** 銀行名称 **/
    private String _strA12_BankNM;
    public String getA12_BankNM() {
        return _strA12_BankNM;
    }

    /** 銀行カナ名 **/
    private String _strA12_BankKN;
    public String getA12_BankKN() {
        return _strA12_BankKN;
    }

    /** 銀行略称 **/
    private String _strA12_BankRN;
    public String getA12_BankRN() {
        return _strA12_BankRN;
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
        stbSql.append("\n" + "    A12_BankNM,");
        stbSql.append("\n" + "    A12_BankKN,");
        stbSql.append("\n" + "    A12_BankRN");
        stbSql.append("\n" + "FROM");
        stbSql.append("\n" + "    A12_Bank");
        stbSql.append("\n" + "WHERE");
        stbSql.append("\n" + "    A12_BankCD = " + String.valueOf(_intA12_BankCD));

        blnExist = false;

        try {
            dbs = dbc.prepareStatement(stbSql.toString());
            dbr = dbs.executeQuery();

            if (dbr.next()) {
                _strA12_BankNM = dbr.getString("A12_BankNM");
                _strA12_BankKN = dbr.getString("A12_BankKN");
                _strA12_BankRN = dbr.getString("A12_BankRN");

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

