package com.hokukou.data;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.naming.NamingContext;
import org.apache.naming.resources.ProxyDirContext;
import org.apache.naming.resources.FileDirContext;

import com.hokukou.report.PrintReport;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_Data.java
	Program Name    : HK_Dataクラス
	Program Date    : 2008/07/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/05  : 新規作成
	2009/07/31  : 桁数指定不要なFormatメソッドを追加                  = T.Ogawa
	2009/09/01  : isDateメソッド 日がゼロ指定の場合trueが返ってしまう = T.Ogawa
	2009/09/02  : 郵便ﾊﾞｰｺｰﾄﾞ生成ﾒｿｯﾄﾞ 住所の最後にｱﾙﾌｧﾍﾞｯﾄ2文字があるとｴﾗｰ = T.Ogawa
	2009/12/01  : 全角半角変換処理に、記号を変換する機能を追加        = T.Ogawa
	2009/12/26  : 郵便ﾊﾞｰｺｰﾄﾞｲﾒｰｼﾞ作成処理を追加                      = H.Fujimoto
	2010/03/26  : long用にparseLong、formatを追加                     = T.Ogawa
================================================================================
********************************************************************************
*/

public class HK_Data {
	
	public static String getRealPath() {
		InitialContext ctx = null;
		NamingContext nmc = null;
		ProxyDirContext pdc = null;
		String strDocBase = "";
		
		try {
			ctx = new InitialContext();
			nmc = (NamingContext)ctx.lookup("java:comp");
			pdc = (ProxyDirContext)nmc.lookup("Resources");
			FileDirContext fdc = (FileDirContext)pdc.getDirContext();
			strDocBase = fdc.getDocBase();
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return strDocBase;
	}
	public static String getDate(String Separate) {

		Calendar calendar = new GregorianCalendar();
		Date trialtime = new Date();
		calendar.setTime(trialtime);

		int intTodayY = calendar.get(Calendar.YEAR);
		int intTodayM = calendar.get(Calendar.MONTH) + 1;
		int intTodayD = calendar.get(Calendar.DAY_OF_MONTH);

		DecimalFormat decFm4 = new DecimalFormat("0000");
		DecimalFormat decFm2 = new DecimalFormat("00");

		String strSdayY = decFm4.format(intTodayY);
		String strSdayM = decFm2.format(intTodayM);
		String strSdayD = decFm2.format(intTodayD);

		return strSdayY + Separate + strSdayM + Separate + strSdayD;

	}

	public static String getTime(String Separate) {

		Calendar calendar = new GregorianCalendar();
		Date trialtime = new Date();
		calendar.setTime(trialtime);

		int intTodayH = calendar.get(Calendar.HOUR_OF_DAY);
		int intTodayM = calendar.get(Calendar.MINUTE);
		int intTodayS = calendar.get(Calendar.SECOND);

		DecimalFormat decFm2 = new DecimalFormat("00");

		String strSdayH = decFm2.format(intTodayH);
		String strSdayM = decFm2.format(intTodayM);
		String strSdayS = decFm2.format(intTodayS);

		return strSdayH + Separate + strSdayM + Separate + strSdayS;

	}

	public static String getClassName(Object objInstance) {

		String strFullClassName = "";
		String strOnlyClassName = "";

		strFullClassName = objInstance.getClass().getName();

		for (int intIdx = strFullClassName.length(); intIdx > 0; intIdx--) {
			if (strFullClassName.substring(intIdx - 1, intIdx).equals(".")) {
				break;
			}
			strOnlyClassName = strFullClassName.substring(intIdx - 1, intIdx) + strOnlyClassName;
		}

		return strOnlyClassName;
	}

	public static String ToFullChar(String s) {
		StringBuffer sb = new StringBuffer(s);

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 'a' && c <= 'z') {
				sb.setCharAt(i, (char) (c - 'a' + 'ａ'));
			} else if (c >= 'A' && c <= 'Z') {
				sb.setCharAt(i, (char) (c - 'A' + 'Ａ'));
			} else if (c >= '0' && c <= '9') {
				sb.setCharAt(i, (char) (c - '0' + '０'));
			}
		}

		return sb.toString();
	}

	public static int[] devideDate(int intDate) {

		DecimalFormat dcf8 = new DecimalFormat("00000000");
		String strDate;
		int intYear;
		int intMonth;
		int intDay;
		int intDevideDate[] = new int[3];

		strDate = dcf8.format(intDate);

		intYear = Integer.parseInt(strDate.substring(0, 4));
		intMonth = Integer.parseInt(strDate.substring(4, 6));
		intDay = Integer.parseInt(strDate.substring(6, 8));

		intDevideDate[0] = intYear;
		intDevideDate[1] = intMonth;
		intDevideDate[2] = intDay;

		return intDevideDate;
	}

	public static int[] devideTime(int intTime) {

		DecimalFormat dcf6 = new DecimalFormat("000000");
		String strDate;
		int intHour;
		int intMinute;
		int intSecond;
		int intDevideTime[] = new int[3];

		strDate = dcf6.format(intTime);

		intHour = Integer.parseInt(strDate.substring(0, 2));
		intMinute = Integer.parseInt(strDate.substring(2, 4));
		intSecond = Integer.parseInt(strDate.substring(4, 6));

		intDevideTime[0] = intHour;
		intDevideTime[1] = intMinute;
		intDevideTime[2] = intSecond;

		return intDevideTime;
	}
	
    /**
      * 日付フォーマット
	  * @param intDate  日付
	  * @param Separate 区切り文字
	  */
	public static String dateFormat(int intDate, String Separate) {
		int intDevideDate[] = new int[3];
		String returnValue = "";
				
		if (intDate > 0){
			DecimalFormat decFm4 = new DecimalFormat("0000");
			DecimalFormat decFm2 = new DecimalFormat("00");

			intDevideDate = devideDate(intDate);

			String strSdayY = decFm4.format(intDevideDate[0]);
			String strSdayM = decFm2.format(intDevideDate[1]);
			String strSdayD = decFm2.format(intDevideDate[2]);

			returnValue = strSdayY + Separate + strSdayM + Separate + strSdayD;
		}
		
		return returnValue;
	}
	
    /**
     * 日付フォーマット
	  * @param intYYYY  年
	  * @param intMM    月
	  * @param intDD    日
	  * @param Separate 区切り文字
	  */
	public static String dateFormat(int intYYYY, int intMM, int intDD, String Separate) {
		String returnValue = "";
				
		DecimalFormat decFm4 = new DecimalFormat("0000");
		DecimalFormat decFm2 = new DecimalFormat("00");

		String strSdayY = decFm4.format(intYYYY);
		String strSdayM = decFm2.format(intMM);
		String strSdayD = decFm2.format(intDD);

		returnValue = strSdayY + Separate + strSdayM + Separate + strSdayD;
		
		return returnValue;
	}
	
    /**
     * 時間フォーマット
	  * @param intTime  時間
	  * @param Separate 区切り文字
	  */
	public static String timeFormat(int intTime, String Separate) {
		int intDevideTime[] = new int[3];
		String returnValue = "";
				
		if (intTime > 0){
			DecimalFormat decFm2 = new DecimalFormat("00");

			intDevideTime = devideTime(intTime);

			String strTimeH = decFm2.format(intDevideTime[0]);
			String strTimeM = decFm2.format(intDevideTime[1]);
			String strTimeS = decFm2.format(intDevideTime[2]);

			returnValue = strTimeH + Separate + strTimeM + Separate + strTimeS;
		}
		
		return returnValue;
	}

	
    /**
      * 文字列をint型に変換
	  * @param strValue 数値に変換する文字列
	  */
	public static int parseInt(String strValue) {
		
		return Integer.parseInt(val(strValue));
		
	}

	
    /**
      * 文字列をlong型に変換
	  * @param strValue 数値に変換する文字列
	  */
	public static long parseLong(String strValue) {
		
		return Long.parseLong(val(strValue));
		
	}
	
	
    /**
      * 文字列をBigDecimal型に変換
	  * @param strValue 数値に変換する文字列
	  */
	public static BigDecimal parseDec(String strValue) {
		
		return new BigDecimal(val(strValue));
		
	}
	
	
    /**
      * 文字をint型に変換
	  * @param chrValue 数値に変換する文字
	  */
	public static int parseInt(char chrValue) {
		
		return Integer.parseInt(val(Character.valueOf(chrValue).toString()));

	}

	
    /**
      * 文字列を数値に変換
	  * @param strValue 数値に変換する文字列
	  */
	private static String val(String strValue) {
		String strReturn = "";
		String strBuf = "";
		boolean blnIsAdd = false;
		boolean blnIsDecimal = false;
		
		if (strValue == null) {
			return "0";
		}

		strValue = strValue.trim();
		if (strValue.length() == 0) {
			return "0";
		}

		// カンマを取除く
		for (int i = 0; i < strValue.length(); i++) {
			if (strValue.charAt(i) != ',') {
				strBuf = strBuf + strValue.charAt(i);
			}
		}

		// 数値のみ抽出
		for (int i = 0; i < strBuf.length(); i++) {
			blnIsAdd = false;
			if (strBuf.charAt(i) == '-'){
				if (strBuf.length() == 1 || i > 0) {
					return "0";
				}
				blnIsAdd = true;
			}
			if (strBuf.charAt(i) == '.'){
				if (blnIsDecimal == true || strBuf.length() == 1) {
					return "0";
				}
				blnIsDecimal = true;
				blnIsAdd = true;
			}
			if (strBuf.charAt(i) == '0' || 
				strBuf.charAt(i) == '1' || 
				strBuf.charAt(i) == '2' || 
				strBuf.charAt(i) == '3' || 
				strBuf.charAt(i) == '4' || 
				strBuf.charAt(i) == '5' || 
				strBuf.charAt(i) == '6' || 
				strBuf.charAt(i) == '7' || 
				strBuf.charAt(i) == '8' || 
				strBuf.charAt(i) == '9'){
				blnIsAdd = true;
			}
			
			if (blnIsAdd == true){
				strReturn = strReturn + strBuf.charAt(i);
			} else {
				return "0";
			}
		}
		
		if (strReturn.equals("") == true){
			return "0";
		}
		
		return strReturn;
	}


    /**
      * 文字列のバイト数を取得
	  * @param strValue バイト数を求める文字列
	  */
	public static int ByteLen(String strValue) {
		
		String strBuf;
		int intCode = 0;
		int intByteCnt = 0;
		
		if (strValue == null) {
			return 0;
		}

		strBuf = strValue;
		
		for(int i=0; i<strBuf.length(); i++){
			intCode = strBuf.codePointAt(i);
			
            if ((Integer.parseInt("20", 16) <= intCode && intCode <= Integer.parseInt("7E", 16)) || 
                 (Integer.parseInt("FF61", 16) <= intCode && intCode <= Integer.parseInt("FF9F", 16)) ) {
                // 半角 英数字記号カナ のUnicode文字コード範囲は、１バイトとする
				intByteCnt = intByteCnt + 1;
            }else{
				intByteCnt = intByteCnt + 2;
			}
		}
		return intByteCnt;
	}

	
    /**
      * 文字列を、指定バイト数で切り取る
	  * @param strValue   文字列
	  * @param beginIndex 開始インデックス (この値を含む)
	  * @param byteLen    切り取るバイト数
	  */
	public static String ByteMid(String strValue, int beginIndex, int byteLen) {
		
		String strBuf;
		int intTotalByteCnt = 0;
		int intChrByteCnt = 0;

		if (strValue == null) {
			return "";
		}
		
		strBuf = "";
		for (int i=0; i<strValue.length(); i++) {
			
			String onechr = strValue.substring(i, i+1); 
            intChrByteCnt = HK_Data.ByteLen(onechr);

			if (beginIndex + byteLen < intTotalByteCnt + intChrByteCnt) {
               	//----- 切り取りバイト数を越えた時
                if (intChrByteCnt == 2) {
                    if (beginIndex + byteLen == intTotalByteCnt + 1) {
						//----- 次の文字が２バイトだが、１バイト分残っているときは、スペースで埋める
						strBuf = strBuf + " ";
					}
				}
                //----- 処理終了
                break;
			}
            if (beginIndex <= intTotalByteCnt) {
                //----- 切り取り開始バイトIndexに達した時
                strBuf = strBuf + onechr;
            }else{
                if (intChrByteCnt == 2 ) {
                    if (beginIndex <= intTotalByteCnt + 1) {
                        //----- ２バイト文字の２バイト目から切り取り開始のときは、１バイト分をスペースにする
                        strBuf = strBuf + " ";
                    }
                }
            }
            intTotalByteCnt = intTotalByteCnt + intChrByteCnt;
        }
        return strBuf;
	}
	
	
    /**
      * 文字列を、左端から指定バイト数で切り取る
	  * @param strValue   文字列
	  * @param byteLen    切り取るバイト数
	  */
	public static String ByteLeft(String strValue, int byteLen) {
		
		return HK_Data.ByteMid(strValue, 0, byteLen);
		
	}
	
	
    /**
      * 文字列を、右端から指定バイト数で切り取る
	  * @param strValue   文字列
	  * @param byteLen    切り取るバイト数
	  */
	public static String ByteRight(String strValue, int byteLen) {
		int startIndex;
		
		if (strValue == null) {
			return "";
		}
		
		startIndex = HK_Data.ByteLen(strValue) - byteLen;

		return HK_Data.ByteMid(strValue, startIndex, byteLen);
	}
	
	
    /**
      * 指定文字を指定数返す
	  * @param str    文字列
	  * @param count  数
	  */
	public static String RepeatStr(String str, int count) {
		String returnValue;
		
		if (str == null) {
			return "";
		}
				
		returnValue = "";
		for (int i=0; i<count; i++){
			returnValue = returnValue + str;
		}
		
		return returnValue;
	}
	
	
    /**
      * 指定文字列を指定桁数の固定長文字列に編集する
	  * @param str      文字列
	  * @param intKeta  桁数(マイナスは左詰)
	  */
	public static String Format(String strValue, int intKeta) {
		String returnValue;
		String strSpace;
		int intTume;
		int intFigure;
		
		if (strValue == null) {
			return "";
		}
		
		returnValue = "";
        if (intKeta < 0){
        	intTume = 1;                   // 1-左詰め 
        	intFigure = intKeta * -1;
        }else{
        	intTume = 2;                   // 2-右詰め
        	intFigure = intKeta;
        }
		
        if (intFigure > 0){
            strSpace = HK_Data.RepeatStr(" ", intFigure);
            
            if (intTume == 1){            // 左詰めの場合
            	returnValue = HK_Data.ByteLeft(strValue + strSpace, intFigure);
            }else{                        // 右詰めの場合
            	returnValue = HK_Data.ByteRight(strSpace + strValue, intFigure);
            }
        }
		return returnValue;
	}
	

    /**
      * 数値をフォーマットし、固定長文字列に編集する
	  * @param intValue  数値
	  * @param strFmt    書式フォーマット
	  * @param intKeta   桁数(マイナスは左詰)
	  */
	public static String Format(int intValue, String strFmt, int intKeta) {
		String targetString;
		
		DecimalFormat decFm = new DecimalFormat(strFmt);

		if (strFmt.equals("") == false){
			targetString = decFm.format(intValue);
		}else{
			targetString = String.valueOf(intValue);
		}
		
		return HK_Data.Format(targetString, intKeta);
	}

	
    /**
      * 数値をフォーマットする
	  * @param intValue  数値
	  * @param strFmt    書式フォーマット
	  */
	public static String Format(int intValue, String strFmt) {
		String targetString;
		
		DecimalFormat decFm = new DecimalFormat(strFmt);

		if (strFmt.equals("") == false){
			targetString = decFm.format(intValue);
		}else{
			targetString = String.valueOf(intValue);
		}
		
		return targetString;
	}


    /**
      * 数値をフォーマットし、固定長文字列に編集する
	  * @param lngValue  数値
	  * @param strFmt    書式フォーマット
	  * @param intKeta   桁数(マイナスは左詰)
	  */
	public static String Format(long lngValue, String strFmt, int intKeta) {
		String targetString;
		
		DecimalFormat decFm = new DecimalFormat(strFmt);

		if (strFmt.equals("") == false){
			targetString = decFm.format(lngValue);
		}else{
			targetString = String.valueOf(lngValue);
		}
		
		return HK_Data.Format(targetString, intKeta);
	}

	
    /**
      * 数値をフォーマットする
	  * @param lngValue  数値
	  * @param strFmt    書式フォーマット
	  */
	public static String Format(long lngValue, String strFmt) {
		String targetString;
		
		DecimalFormat decFm = new DecimalFormat(strFmt);

		if (strFmt.equals("") == false){
			targetString = decFm.format(lngValue);
		}else{
			targetString = String.valueOf(lngValue);
		}
		
		return targetString;
	}
	

    /**
      * 数値をフォーマットし、固定長文字列に編集する
	  * @param intValue  数値
	  * @param strFmt    書式フォーマット
	  * @param intKeta   桁数(マイナスは左詰)
	  */
	public static String Format(BigDecimal decValue, String strFmt, int intKeta) {
		String targetString;
		
		DecimalFormat decFm = new DecimalFormat(strFmt);

		if (strFmt.equals("") == false){
			targetString = decFm.format(decValue);
		}else{
			targetString = String.valueOf(decValue);
		}
		
		return HK_Data.Format(targetString, intKeta);
	}

    /**
      * 数値をフォーマットする
	  * @param intValue  数値
	  * @param strFmt    書式フォーマット
	  */
	public static String Format(BigDecimal decValue, String strFmt) {
		String targetString;
		
		DecimalFormat decFm = new DecimalFormat(strFmt);

		if (strFmt.equals("") == false){
			targetString = decFm.format(decValue);
		}else{
			targetString = String.valueOf(decValue);
		}
		
		return targetString;
	}
	
    /**
      *  文字列の最尾のスペースを取除く。
	  * @param strValue  文字列
	  */
	public static String TrimEnd(String strValue) {
		String strReturn = "";
		int intCutLength = 0;

		if (strValue == null) {
			return "";
		}
		if (strValue.length() == 0) {
			return "";
		}

		for (int intIdx = strValue.length() - 1; intIdx >= 0; intIdx--) {
			if (strValue.charAt(intIdx) == ' ' || strValue.charAt(intIdx) == '　') {
				intCutLength++;
			} else {
				break;
			}
		}
		strReturn = strValue.substring(0, strValue.length() - intCutLength);

		return strReturn;
	}


    /**
      *  数値の丸め処理
	  * @param decInput  数値
	  * @param intKeta   桁位置
	  * @param intSeparateNumber   指定数値
	  *  *********************************************************************
	  *  ***  指定された数値(decInput)を、指定した桁数(intKeta)の個所の値が指定数値(intSeparateNumber)以下は
	  *  ***  切捨て,それより大きい時は切上げする処理
	  *  ***     i = "一"の位から数えて、切上げ/切捨てしたい位までの数
	  *  ***     (0="一",1="十",2="百",-1=小数点第一位,-3=小数点第三位 ...)
	  *  ***  "Round off" means "四捨五入".
	  *  *************************************************************************************************************************
	  *  Ex. 123,456.789 を十の位(i = + 1)で四捨五入する時
	  *        123,500   = RoundOff(123456.789, 1, 4)
	  *      123,456.789 を小数点以下2桁(i = - 2)で切捨する時
	  *        123,456.7 = RoundOff(123456.789, -2, 9)
	  *      123,456.789 を百の位(i = + 2)で切上する時
	  *        124,000   = RoundOff(123456.789, 2, 0)
	  *  10 進型 (Decimal) の変数は、128 ビット (16 バイト) の符号付き整数
	  *  小数点部桁数は、0 ～ 28 の範囲の値です。
	  */
	public static BigDecimal RoundOff(BigDecimal decInput, int intKeta, int intSeparateNumber) {
		BigDecimal decInputBuf;
        String strInputBuf;
        Boolean blnIsPlus = true;
        int intSyousuuten;
        int intSyousuuKeta;
        int intMathKeta;
        int intMathNumber;
        BigDecimal decMathNumber;
        String strMathMinus;
        BigDecimal decReturn;
        String strUp;
        char chrUp[];
        
        decReturn = decInput;
        decInputBuf = decInput;

        blnIsPlus = true;
        //----- 負の値の四捨五入の時 一時的に正の値にする
        if (decInputBuf.compareTo(new BigDecimal(0)) == -1){
            blnIsPlus = false;
            decInputBuf = decInputBuf.multiply(new BigDecimal(-1));
        }

        //----- 文字に置換()
        strInputBuf = String.valueOf(decInputBuf);

        // Insert 2008/02/19
        //----- 処理される数値が、処理する桁よりも小さい場合は、桁数分ゼロ埋めする。
        if (intKeta >= 0) {
        	if (new BigDecimal(Math.pow(10, intKeta + 1)).compareTo(decInputBuf) > 0) {
        		
        		String strFmt = "";
        		for (int intIdx = 0; intIdx <= intKeta + 1; intIdx++) {
        			strFmt = strFmt + "0";
        		}
        		
        		strInputBuf = HK_Data.Format(decInputBuf, strFmt, strFmt.length());
        	}
        }
        
        //----- 四捨五入する桁の数値
        intSyousuuten = strInputBuf.indexOf(".");
        if (intSyousuuten == -1){
            intSyousuuten = strInputBuf.length();
            intSyousuuKeta = 0;
        }else{
            intSyousuuKeta = strInputBuf.length() - intSyousuuten - 1;
        }

        intMathKeta = intSyousuuten - intKeta - 1;

        //----- 小数点以下の表示をなくす
        strInputBuf = strInputBuf.replace(".", "");

        //----- 計算桁が、文字列外であったら そのまま Exit 
        if (intMathKeta > strInputBuf.length() - 1){
            return decInput;
        }

        //----- 計算する桁以降にある数値を取得
        strMathMinus = strInputBuf.substring(intMathKeta);

        if (intSeparateNumber != 0){
            //----- 計算する桁の数値を取得
            intMathNumber = HK_Data.parseInt(strInputBuf.substring(intMathKeta, intMathKeta+1));

            if (intMathNumber <= intSeparateNumber){
                //----- 計算する桁の数値が、引数の切上下の境界数値と同じか、より小さいとき
                //----- 切捨て
                BigDecimal decOrigin = new BigDecimal(0);
                decOrigin = decOrigin.add(HK_Data.parseDec(strInputBuf));
                decOrigin = decOrigin.subtract(HK_Data.parseDec(strMathMinus));
                
                //BigDecimal decPlace = new BigDecimal(Math.pow(10,intSyousuuKeta));
                BigDecimal decPlace = new BigDecimal("1" + HK_Data.RepeatStr("0", intSyousuuKeta));

                decReturn = decOrigin.divide(decPlace);
            }else{
                //----- 計算する桁の数値が、引数の切上下の境界数値より大きいとき
                //----- 切上げ
                strUp = HK_Data.RepeatStr("0", strInputBuf.length());
                strUp = strUp.substring(0, intMathKeta - 1) + "1" + strUp.substring(intMathKeta);
                BigDecimal decOrigin = new BigDecimal(0);
                decOrigin = decOrigin.add(HK_Data.parseDec(strInputBuf));
                decOrigin = decOrigin.subtract(HK_Data.parseDec(strMathMinus));
                decOrigin = decOrigin.add(HK_Data.parseDec(strUp));
                //BigDecimal decPlace = new BigDecimal(Math.pow(10,intSyousuuKeta));
                BigDecimal decPlace = new BigDecimal("1" + HK_Data.RepeatStr("0", intSyousuuKeta));
                decReturn = decOrigin.divide(decPlace);
            }
        }else{
            //----- 計算する桁の数値を取得
            decMathNumber = HK_Data.parseDec(strInputBuf.substring(intMathKeta));

            if (decMathNumber.equals(new BigDecimal(0)) == true){
                decReturn = decInputBuf;
            }else{
                //----- 四捨五入計算桁の１つ位の上の数値を「１」にする
                strUp = HK_Data.RepeatStr("0", strInputBuf.length());
                
                chrUp = strUp.toCharArray();
                chrUp[intMathKeta - 1] = '1';
                strUp = new String(chrUp);

                BigDecimal decOrigin = new BigDecimal(0);
                decOrigin = decOrigin.add(HK_Data.parseDec(strInputBuf));
                decOrigin = decOrigin.subtract(HK_Data.parseDec(strMathMinus));
                decOrigin = decOrigin.add(HK_Data.parseDec(strUp));
                
                //BigDecimal decPlace = new BigDecimal(Double.valueOf(Math.pow(10,intSyousuuKeta)).intValue());
                BigDecimal decPlace = new BigDecimal("1" + HK_Data.RepeatStr("0", intSyousuuKeta));
                decReturn = decOrigin.divide(decPlace);
            }
        }
        //----- マイナス値は戻す
        if (blnIsPlus == false){
            decReturn = decReturn.multiply(new BigDecimal(-1));
        }
        return decReturn;
    }

	/**
	 * 全角を半角に変換します。
	 * @param s 変換元文字列
	 * @return 変換後文字列
	 */
	public static String toHalfAllChar(String s) {
		String strTmp;
		strTmp = s;
		strTmp = HK_ChangeFullHalf.toHalfAlpha(strTmp);
		strTmp = HK_ChangeFullHalf.toHalfNum(strTmp);
		strTmp = HK_ChangeFullHalf.toHalfKana(strTmp);
		/***** 2009/12/01 INSERT START *****/
		strTmp = HK_ChangeFullHalf.toHalfSign(strTmp);
		/***** 2009/12/01 INSERT E N D *****/
		
		return strTmp;
	}

	/**
	 * 半角を全角に変換します。
	 * @param s 変換元文字列
	 * @return 変換後文字列
	 */
	public static String ToFullAllChar(String s) {
		String strTmp;
		strTmp = s;
		strTmp = HK_ChangeFullHalf.toFullAlpha(strTmp);
		strTmp = HK_ChangeFullHalf.toFullNum(strTmp);
		strTmp = HK_ChangeFullHalf.toFullKana(strTmp);
		/***** 2009/12/01 INSERT START *****/
		strTmp = HK_ChangeFullHalf.toFullSign(strTmp);
		/***** 2009/12/01 INSERT E N D *****/
		
		return strTmp;
	}

	/**
	 * 郵便番号、住所をもとに郵便バーコードを作成します。
	 * @param zip 郵便番号
	 * @param adrs1 住所1
	 * @param adrs2 住所2
	 * @return 郵便バーコード
	 */
	public static String createCustmerBarCode(String zip, String adrs1, String adrs2) {
		
		String strAdrs = "";
		String strZip = "";
		String strAddressB = "";
		String strbarcode = "";
		Pattern p, p1, p2;
		Matcher m, m1, m2;

		if (zip == null) zip = "";
		if (adrs1 == null) adrs1 = "";
		if (adrs2 == null) adrs2 = "";
		
		strAdrs = zip + " "+ adrs1 + " "+ adrs2;		// 郵便番号 + 住所1 + 住所2
		strAdrs = strAdrs.trim();
		
		if (!strAdrs.equals("")) {
			// 全角英字を半角英字に変換
			strAdrs = HK_ChangeFullHalf.toHalfAlpha(strAdrs);
			
			// 全角数字を半角数字に変換
			strAdrs = HK_ChangeFullHalf.toHalfNum(strAdrs);
			
			// 英字小字文字を大文字に変換
			strAdrs = strAdrs.toUpperCase();
			
			// &(アンパサンド), /(スラッシュ), ・(中グロ), .(ピリオド)を取り除く
			strAdrs = strAdrs.replaceAll("[&/.＆／・．]", "");
			
			// 全角ハイフンを半角ハイフンに変換
			strAdrs = strAdrs.replace("－", "-");

			// 郵便番号を抽出
			strZip = strAdrs.replaceAll("(^[〒0-9- ]+).*", "$1");
			//郵便番号を数字のみ抽出
			strZip = strZip.replaceAll("([^0-9])", "");
			
			//7字または10字(私書箱番号付)でなければエラー
			if (strZip.length() != 7 && strZip.length() != 10) {
				return "";
			}
			
			// 住所を抽出
			strAdrs = strAdrs.replaceAll("^[〒0-9- ]+(.*)", "$1");
			
			// 住所がある場合
			if (!strAdrs.trim().equals("")) {

				//住所Bを抽出
				strAdrs = strAdrs.replaceAll("^.*?([0-9一二三四五六七八九〇十千百万億兆京]+[丁目|丁|番地|番|号|地割|線|の|ノ]|[0-9]+F|[0-9]+|[A-Z]+)", "$1");	
				
				//住所Bを地番ごとに分ける
				p = Pattern.compile("([0-9一二三四五六七八九〇十千百万億兆京]+[丁目|丁|番地|番|号|地割|線|の|ノ|F|階]|[0-9A-Z一二三四五六七八九〇十千百万億兆京]+)");
				m = p.matcher(strAdrs);
				
				ArrayList<String> tmpString = new ArrayList<String>(); 
			    while (m.find()) {
			    	tmpString.add(m.group(1));
			    }
			    
				//漢数字→半角数字変換、英数字以外の文字と階数を示す"F"の除去
				String kansuji;
				String tmpString2 = "";
				
				for(int loopTimes = 0; loopTimes < tmpString.size(); loopTimes++) {
					//漢数字が含まれている?
					p = Pattern.compile("^([一二三四五六七八九〇十千百万億兆京]+).*");
					m = p.matcher(tmpString.get(loopTimes));
					if (m.matches()) {
						kansuji = m.group(1);
						tmpString.set(loopTimes, HK_JapaneseNumber.toNumber(kansuji).toString());
					}

					//英数字以外を取り除く
					p = Pattern.compile("([0-9A-Z]+).?");
					m = p.matcher(tmpString.get(loopTimes));
					if (m.matches()) {
						tmpString.set(loopTimes, m.group(1));
					}
					
					//階数を示すFを取り除く
					p = Pattern.compile("([0-9A-Z]+)F");
					m = p.matcher(tmpString.get(loopTimes));
					if (m.matches()) {
						tmpString.set(loopTimes, m.group(1));
					}
				}

				//住所Bの組み立て
				int loopTimes = 0;
				while(loopTimes < tmpString.size()) {
					//2字以上の英字?
					p = Pattern.compile(".*[A-Z]{2}.*");
					m = p.matcher(tmpString.get(loopTimes));
					if(m.matches()  && tmpString.get(loopTimes).length() > 1)  {
						loopTimes++;
						
						/***** 2009/09/02 INSERT START *****/
						//ループ終了?
						if(loopTimes > tmpString.size() - 1) break;
						/***** 2009/09/02 INSERT E N D *****/
						
						//次の文字が数字で始まっているか?
						Pattern p6 = Pattern.compile("^[0-9]+.*");
						Matcher m6 = p6.matcher(tmpString.get(loopTimes));
						if (m6.matches()) {
							strAddressB += "-";
						}
						continue;
					}
					tmpString2 = tmpString.get(loopTimes) + "";
					strAddressB += tmpString2;
					loopTimes++;
					//ループ終了?
					if(loopTimes > tmpString.size() - 1) break;
					
					//代入された最後の文字が数字で、次の文字が英字で始まっていたらハイフンは付けない
					p1 = Pattern.compile("[0-9]+$");
					m1 = p1.matcher(tmpString2);
					
					p2 = Pattern.compile("^[A-Z]+.*");
					m2 = p2.matcher(tmpString.get(loopTimes));
					
					if (m1.matches() && m2.matches()) {
						continue;
					}
					
					//英字が続いたらハイフンを付けない
					p1 = Pattern.compile("[A-Z]+$");
					m1 = p1.matcher(tmpString2);
					
					p2 = Pattern.compile("^[A-Z]+.*");
					m2 = p2.matcher(tmpString.get(loopTimes));
					
					if (m1.matches() && m2.matches()) {
						continue;
					}
					
					//代入された最後の文字がで、次の文字が数字で始まっていたらハイフンは付けない
					p1 = Pattern.compile("[A-Z]+$");
					m1 = p1.matcher(tmpString2);
					
					p2 = Pattern.compile("^[0-9]+.*");
					m2 = p2.matcher(tmpString.get(loopTimes));

					if (m1.matches() && m2.matches()){
						continue;
					}
					
					strAddressB += "-";
				}
			}
		}
		strbarcode = strZip + strAddressB;
		
		if (strbarcode.length() > 20) {
			strbarcode = strbarcode.substring(0, 20);
		}
		return strbarcode;
	}

		
	/***** 2009/12/26 INSERT START *****/
	/**
	 * 郵便バーコードの文字列からバーコードイメージをPNG形式の画像で作成します。
	 * webapp/reportsResources/customerBarcodeImage にバーコードイメージの元画像を配置する必要があります。
	 * @param strZipBar 郵便バーコードの文字列
	 * @param strDir イメージ画像の保存ディレクトリ
	 * @param strFile イメージ画像ファイル名
	 * @return なし
	 */
	public static void createCustmerBarImage(String strZipBar, String strDir, String strFile) throws IOException {
		
		Pattern ptn;
		Matcher mtc;
		char[] chrZipBars = strZipBar.toCharArray();
		String tmpString = "";
		ArrayList<String> aryBarData;
		
		//カスタマバーコードデータ生成
		aryBarData = new ArrayList<String>();
		
		for(int intLoopTimes = 0; intLoopTimes < 20; intLoopTimes++) {
			//カスタマバーコードが20桁以下?
			if(intLoopTimes >= chrZipBars.length)
				aryBarData.add("CC4");
			else {
				
				tmpString = String.valueOf(chrZipBars[intLoopTimes]);
					
				ptn = Pattern.compile("[A-J]");
				mtc = ptn.matcher(String.valueOf(chrZipBars[intLoopTimes]));					
				if (mtc.matches()) {
					aryBarData.add("CC1");
					tmpString = (String.valueOf(chrZipBars[intLoopTimes]).codePointAt(0) - "A".codePointAt(0) ) + "";		
				}
				
				ptn = Pattern.compile("[K-T]");
				mtc = ptn.matcher(String.valueOf(chrZipBars[intLoopTimes]));					
				if (mtc.matches()) {
					aryBarData.add("CC2");
					tmpString = (String.valueOf(chrZipBars[intLoopTimes]).codePointAt(0) - "K".codePointAt(0) ) + "";		
				}
				
				ptn = Pattern.compile("[U-Z]");
				mtc = ptn.matcher(String.valueOf(chrZipBars[intLoopTimes]));					
				if (mtc.matches()) {
					aryBarData.add("CC3");
					tmpString = (String.valueOf(chrZipBars[intLoopTimes]).codePointAt(0) - "U".codePointAt(0) ) + "";		
				}
				aryBarData.add(tmpString);
			}
		}
		
		// 20桁以上は削除する
		if(aryBarData.size() > 20) {
			aryBarData = new ArrayList<String>(aryBarData.subList(0, 20));	
		}
		//チェックデジット算出
		int intTmpNum = 0;
		for(int loopTimes = 0; loopTimes < aryBarData.size(); loopTimes++) {			
			intTmpNum += HK_Data.parseInt(aryBarData.get(loopTimes)) - 0;
			
			if(aryBarData.get(loopTimes).equals("-")){
				intTmpNum += 10;
			}
			if(aryBarData.get(loopTimes).equals("CC1")){
				intTmpNum += 11;
			}
			if(aryBarData.get(loopTimes).equals("CC2")){
				intTmpNum += 12;
			}
			if(aryBarData.get(loopTimes).equals("CC3")){
				intTmpNum += 13;
			}
			if(aryBarData.get(loopTimes).equals("CC4")){
				intTmpNum += 14;
			}
			if(aryBarData.get(loopTimes).equals("CC5")){
				intTmpNum += 15;
			}
			if(aryBarData.get(loopTimes).equals("CC6")){
				intTmpNum += 16;
			}
			if(aryBarData.get(loopTimes).equals("CC7")){
				intTmpNum += 17;
			}
			if(aryBarData.get(loopTimes).equals("CC8")){
				intTmpNum += 18;
			}
		}
		
		//19で割り切れる数?	
		if(intTmpNum % 19 == 0){
			intTmpNum = 0;
		}else{
			intTmpNum = (int)(Math.floor(intTmpNum / 19) + 1) * 19 - intTmpNum;
		}
		
		String strCheckDigit;
		//チェックデジットが10?
		if(intTmpNum == 10){
			strCheckDigit = "-";
		//チェックデジットが10以上?
		}else if(intTmpNum > 10) {
			strCheckDigit = "CC"+ (intTmpNum - 10);
		} else{
			strCheckDigit = String.valueOf(intTmpNum);
		}

		//スタートコード、チェックデジット、ストップコードを付加
		aryBarData.add(0, "STC");
		aryBarData.add(strCheckDigit);
		aryBarData.add("SPC");

		String strResource_DIR = HK_Data.getRealPath() + "/" + PrintReport.REPORT_RESOURCE_DIR;
		
		BufferedImage image = new BufferedImage(12 * aryBarData.size(),12, BufferedImage.TYPE_INT_RGB);
		
		int intx = 0;
		
		for (int i = 0; i < aryBarData.size(); i++) {
			String strCode = String.valueOf(aryBarData.get(i));
		
		    File fileOne = new File(strResource_DIR + "/customerBarcodeImage/" + strCode+".png");
		    BufferedImage imageOne = ImageIO.read(fileOne);
		    int width = imageOne.getWidth();//
		    int height = imageOne.getHeight();//
		    
		    int[] ImageArrayOne = new int[width*height];
		    ImageArrayOne = imageOne.getRGB(0,0,width,height,ImageArrayOne,0,width);
		    image.setRGB(intx,0,width,height,ImageArrayOne,0,width);//左のRGBを設定する
		    imageOne.flush();	
		
		    intx = intx + width;
		}
	    
		ImageIO.write(image, "png", new File(strDir + "/" + strFile));//画像を作成
	    image.flush();

	}
	/***** 2009/12/26 INSERT E N D *****/

	/**
	 * 存在する日付かどうかをチェック
	 * @param intYear 年
	 * @param intMonth 月
	 * @param intDay 日
	 * @return 判定結果
	 */
	public static boolean isDate(int intYear, int intMonth, int intDay) {
		int days[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		int day; 
	    int bufY = intYear;
	    int bufM = intMonth;
	    int bufD = intDay;
		
		if (bufY < 1 ) {
			return false;
		}
		if (bufM < 1 || 12 < bufM) {
			return false;
		}
		
		/***** 2009/09/01 INSERT START *****/
		if (bufD < 1 ) {
			return false;
		}
		/***** 2009/09/01 INSERT E N D *****/
		
		day	= days[bufM-1];
						
	    /*** 閏年の判定 ***/
	    if ((bufY % 4) == 0 && bufM == 2) {
	        day = day + 1;	/*** 閏年 ***/
	    
	        if ((bufY % 100) != 0) {
	            if ((bufY % 400) == 0) {
			        day = day - 1;	/*** 閏年でない ***/
	            }
	        }
	    }
	
	    /*** 日の範囲検証 ***/
	    if ( day < bufD) {
	    	return false;
	    }else{
	    	return true;
	    }
	}

	/**
	 * 全角のひらがなを全角のカタカナへ変換
	 * @param strHira 全角のひらがな
	 * @return 全角のカタカナ
	 */
	public static String hiraTokata(String strHira) {
		StringBuffer sb = new StringBuffer(strHira);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= 'ぁ' && c <= 'ん') {
				sb.setCharAt(i, (char)(c - 'ぁ' + 'ァ'));
			}
		}
		return sb.toString();    
	}
}
