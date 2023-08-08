package com.hokukou.data;

//ソース元　http://ja.doukaku.org/38/

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_JapaneseNumber.java
	Program Name    : HK_JapaneseNumberクラス
	Program Date    : 2008/07/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/05  : 新規作成
================================================================================
********************************************************************************
*/

public class HK_JapaneseNumber {

    private static final Map<Integer, String> UNIT;

    private static final Map<Integer, String> NUM;

    static {
        UNIT = new HashMap<Integer, String>();
        UNIT.put(0, ""); UNIT.put(1, "十"); UNIT.put(2, "百"); UNIT.put(3, "千");
        UNIT.put(4, "万"); UNIT.put(8, "億"); UNIT.put(12, "兆"); UNIT.put(16, "京");
        // and so on.....
        NUM = new HashMap<Integer, String>();
        NUM.put(0, ""); NUM.put(1, "一"); NUM.put(2, "二"); NUM.put(3, "三");
        NUM.put(4, "四"); NUM.put(5, "五"); NUM.put(6, "六"); NUM.put(7, "七");
        NUM.put(8, "八"); NUM.put(9, "九");
    }
    
    public static BigInteger toNumber(String value) {
        long minus = 1L;
        
        if (value == null || value.equals("")) { 
        	return new BigInteger("0");
        }
        
        //反転
        value = new StringBuffer(value).reverse().toString();
        
        if (value.endsWith("負")) {
            minus = -1L;
            value = value.substring(0, value.length() - 1);
        }
        BigInteger result = BigInteger.ZERO;
        BigInteger unit = BigInteger.ONE;
        BigInteger smallUnit = BigInteger.ONE;
        for (int i = 0; i < value.length(); i++) {
            String s = String.valueOf(value.charAt(i));
            String next = i + 1 >= value.length() ? null : String.valueOf(value.charAt(i + 1));
            int key = getKey(UNIT, s);
            if (key > 3) {
                unit = BigInteger.valueOf((long) Math.pow((double) 10, (double) key));
            } else if (key > 0) {
                smallUnit = BigInteger.valueOf((long) Math.pow((double) 10, (double) key));
            } else if (NUM.containsValue(s)) {
                result = result .add(BigInteger.valueOf((long) getKey(NUM, s)).multiply(unit.multiply(smallUnit)));
                smallUnit = BigInteger.ONE;
            }
            if ((next == null || (!NUM.containsValue(next) && getKey(UNIT, next) > 3)
                              || (!NUM.containsValue(next) && getKey(UNIT, next) <= 3 && key <= 3))
                    && key > 0) {
                result = result.add(unit.multiply(smallUnit));
                smallUnit = BigInteger.ONE;
            }
        }
        return result.multiply(BigInteger.valueOf(minus));
    }

    private static int getKey(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return 0;
    }

	public static String toKanji(Integer value) {
		return HK_JapaneseNumber.toKanji(new BigInteger(value.toString()));
	}

	public static String toKanji(BigInteger value) {
		if (value == null) { 
			return "";
		}
		if(value.equals(BigInteger.ZERO)) {
			return "〇";
		}
		String s = new StringBuffer(value.toString()).reverse().toString();
		StringBuffer sb = new StringBuffer();
		String minus = "";
		if (s.endsWith("-")) {
			minus = "負";
			s = s.substring(0, s.length() - 1);
		}
		for (int i = 0; i < s.length(); i++) {
			int n = Integer.parseInt(String.valueOf(s.charAt(i)));
			int key = UNIT.containsKey(i) ? i : i % 4;
			if (n == 0 && key > 3 && !s.substring(n, n + 4).equals("0000")) {
				sb.append(UNIT.get(key));
			} else if (n > 1 || (key > 3 && n == 1)) {
				sb.append(UNIT.get(key)).append(NUM.get(n));
			} else if (n > 0) {
				sb.append(!UNIT.get(key).equals("") ?  UNIT.get(key) : NUM.get(n));
			}
		}
		sb.append(minus);
		return sb.reverse().toString();
	}
}
