package com.hokukou.data;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_Encrypt.java
	Program Name    : HK_Encryptクラス
	Program Date    : 2008/07/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/05  : 新規作成
================================================================================
********************************************************************************
*/

public class HK_Encrypt {
	
	/**
	 * パスワードをエンコード（暗号）する処理
	 * @param strDecPw:デコードパスワード
	 * @strDecKey strDecPw:パスワードするためのKEY
	 */
	public static String encode(String strDecPw, String strDecKey) {
		String strEncPw = "";
		String strDecPwChr = "";
		String strDecKeyChr = "";
		
		if (strDecPw.length() > 0) {
			for (int numIdx = 0; numIdx < strDecPw.length(); numIdx++) {
				strDecPwChr = strDecPw.substring(numIdx, numIdx + 1);
				strDecKeyChr = strDecKey.substring(numIdx, numIdx + 1);
				int numT1 = (int) strDecPwChr.charAt(0);
				int numT2 = Integer.parseInt("FF", 16);
				int numT3 = (int) strDecKeyChr.charAt(0);
				int numT = numT1 ^ numT2 ^ numT3;
				String str16 = Integer.toHexString(numT);	
				String strAnsCode = "00" + str16;
				strEncPw = strEncPw + strAnsCode.substring(strAnsCode.length() - 2, 4);
			}
		}
		return strEncPw.toUpperCase();
	}

	/**
	 * パスワードをデコード（復号）する処理
	 * @param strDecPw:デコードパスワード
	 * @strDecKey strDecPw:パスワードするためのKEY
	 */
	public static String decode(String strEncPw, String strDecKey) {
		String strDecPw = "";
		String strEncPwChr = "";
			
		if (strEncPw.length() > 0) {
		    for (int numIdx = 0; numIdx < strEncPw.length() / 2; numIdx++) {
				strEncPwChr = strEncPw.substring(numIdx * 2, numIdx * 2 + 2);
				int numT1 = Integer.parseInt(strEncPwChr, 16);
				int numT2 = (int) strDecKey.substring(numIdx, numIdx + 1).charAt(0);
				int numT3 = Integer.parseInt("FF", 16);
				int numT = numT1 ^ numT2 ^ numT3;
				strDecPw = strDecPw + (char) numT;
		    }
		}
		return strDecPw;
	}

	/**
	 * 全角パスワードをエンコード（暗号）する処理
	 * @param strDecPw:デコードパスワード
	 * @strDecKey strDecPw:パスワードするためのKEY
	 */		
	public static String encodeFull(String strDecPw, String strDecKey) {
		String strEncPw = "";
		String strDecPwChr = "";
		String strDecKeyChr = "";
		
		if (strDecPw.length() > 0) {
			for (int numIdx = 0; numIdx < strDecPw.length(); numIdx++) {
				strDecPwChr = strDecPw.substring(numIdx, numIdx + 1);
				strDecKeyChr = strDecKey.substring(numIdx, numIdx + 1);
				int numT1 = (int) strDecPwChr.charAt(0);
				int numT2 = Integer.parseInt("FFFF", 16);
				int numT3 = (int) strDecKeyChr.charAt(0);
				int numT = numT1 ^ numT2 ^ numT3;
				String str16 = Integer.toHexString(numT);	
				String strAnsCode = "0000" + str16;
				strEncPw = strEncPw + HK_Data.ByteRight(strAnsCode, 4);
			}
		}
		return strEncPw.toUpperCase();
	}

	/**
	 * 全角パスワードをデコード（復号）する処理
	 * @param strDecPw:デコードパスワード
	 * @strDecKey strDecPw:パスワードするためのKEY
	 */		
	public static String decodeFull(String strEncPw, String strDecKey) {
		String strDecPw = "";
		String strEncPwChr = "";
			
		if (strEncPw.length() > 0) {
		    for (int numIdx = 0; numIdx < strEncPw.length() / 4; numIdx++) {
				strEncPwChr = strEncPw.substring(numIdx * 4, numIdx * 4 + 4);
				int numT1 = Integer.parseInt(strEncPwChr, 16);
				int numT2 = (int) strDecKey.substring(numIdx, numIdx + 3).charAt(0);
				int numT3 = Integer.parseInt("FFFF", 16);
				int numT = numT1 ^ numT2 ^ numT3;
				strDecPw = strDecPw + (char) numT;
		    }
		}
		return strDecPw;
	}
}
