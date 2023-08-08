package com.hokukou.data;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_ChangeFullHalf.java
	Program Name    : HK_ChangeFullHalfクラス
	Program Date    : 2008/07/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/05  : 新規作成
	2009/12/01  : 記号の半角全角変換処理を追加                         = T.Ogawa
================================================================================
********************************************************************************
*/

public class HK_ChangeFullHalf {

	/**
	 * 全角アルファベットを半角に変換します。
	 * @param s 変換元文字列
	 * @return 変換後文字列
	 */
	public static String toHalfAlpha(String s) {
		StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= 'ａ' && c <= 'ｚ') {
				sb.setCharAt(i, (char) (c - 'ａ' + 'a'));
			} else if (c >= 'Ａ' && c <= 'Ｚ') {
				sb.setCharAt(i, (char) (c - 'Ａ' + 'A'));
			}
		}
		return sb.toString();
	}

	/**
	 * 半角アルファベットを全角に変換します。
	 * @param s 変換元文字列
	 * @return 変換後文字列
	 */
	public static String toFullAlpha(String s) {
		StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 'a' && c <= 'z') {
				sb.setCharAt(i, (char)(c - 'a' + 'ａ'));
			} else if (c >= 'A' && c <= 'Z') {
				sb.setCharAt(i, (char)(c - 'A' + 'Ａ'));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 全角数字を半角に変換します。
	 * @param s 変換元文字列
	 * @return 変換後文字列
	 */
	public static String toHalfNum(String s) {
		StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if (c >= '０' && c <= '９') {
				sb.setCharAt(i, (char)(c - '０' + '0'));
			}
		}
		return sb.toString();
	}

	/**
	 * 半角数字を全角に変換します。
	 * @param s 変換元文字列
	 * @return 変換後文字列
	 */
	public static String toFullNum(String s) {
		StringBuffer sb = new StringBuffer(s);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				sb.setCharAt(i, (char) (c - '0' + '０'));
			}
		}
		return sb.toString();
	}

	/*--------------------------------------------------------------------------*/
	/*=============== 全角カタカナを半角カタカナに変換する処理群 ===============*/
	/*--------------------------------------------------------------------------*/

	private static final char[] _cschrToH_Full_KATAKANA = { 'ァ', 'ア', 'ィ', 'イ', 'ゥ',
		'ウ', 'ェ', 'エ', 'ォ', 'オ', 'カ', 'ガ', 'キ', 'ギ', 'ク', 'グ', 'ケ', 'ゲ',
		'コ', 'ゴ', 'サ', 'ザ', 'シ', 'ジ', 'ス', 'ズ', 'セ', 'ゼ', 'ソ', 'ゾ', 'タ',
		'ダ', 'チ', 'ヂ', 'ッ', 'ツ', 'ヅ', 'テ', 'デ', 'ト', 'ド', 'ナ', 'ニ', 'ヌ',
		'ネ', 'ノ', 'ハ', 'バ', 'パ', 'ヒ', 'ビ', 'ピ', 'フ', 'ブ', 'プ', 'ヘ', 'ベ',
		'ペ', 'ホ', 'ボ', 'ポ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ャ', 'ヤ', 'ュ', 'ユ',
		'ョ', 'ヨ', 'ラ', 'リ', 'ル', 'レ', 'ロ', 'ヮ', 'ワ', 'ヰ', 'ヱ', 'ヲ', 'ン',
		'ヴ', 'ヵ', 'ヶ'};

	private static final String[] _csstrToH_Half_KATAKANA = { "ｧ", "ｱ", "ｨ", "ｲ", "ｩ",
		"ｳ", "ｪ", "ｴ", "ｫ", "ｵ", "ｶ", "ｶﾞ", "ｷ", "ｷﾞ", "ｸ", "ｸﾞ", "ｹ","ｹﾞ", 
		"ｺ", "ｺﾞ", "ｻ", "ｻﾞ", "ｼ", "ｼﾞ", "ｽ", "ｽﾞ", "ｾ", "ｾﾞ", "ｿ","ｿﾞ", "ﾀ", 
		"ﾀﾞ", "ﾁ", "ﾁﾞ", "ｯ", "ﾂ", "ﾂﾞ", "ﾃ", "ﾃﾞ", "ﾄ", "ﾄﾞ","ﾅ", "ﾆ", "ﾇ", 
		"ﾈ", "ﾉ", "ﾊ", "ﾊﾞ", "ﾊﾟ", "ﾋ", "ﾋﾞ", "ﾋﾟ", "ﾌ","ﾌﾞ", "ﾌﾟ", "ﾍ", "ﾍﾞ", 
		"ﾍﾟ", "ﾎ", "ﾎﾞ", "ﾎﾟ", "ﾏ", "ﾐ", "ﾑ", "ﾒ","ﾓ", "ｬ", "ﾔ", "ｭ", "ﾕ", 
		"ｮ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ","ﾜ", "ｲ", "ｴ", "ｦ", "ﾝ", 
		"ｳﾞ", "ｶ", "ｹ"};

	private static final char _cschrToH_Full_KATAKANA_FIRST_CHAR = _cschrToH_Full_KATAKANA[0];
	private static final char _cschrToH_Full_KATAKANA_LAST_CHAR = _cschrToH_Full_KATAKANA[_cschrToH_Full_KATAKANA.length - 1];

	private static String _toHalfKanaChar(char c) {
		if (c >= _cschrToH_Full_KATAKANA_FIRST_CHAR && c <= _cschrToH_Full_KATAKANA_LAST_CHAR) {
			return _csstrToH_Half_KATAKANA[c - _cschrToH_Full_KATAKANA_FIRST_CHAR];
		} else {
			switch (c) {
				case 'ー': return "ｰ";
				case '－': return "ｰ";
			}
			return String.valueOf(c);
		}
	}

	/**
	 * 2文字目が濁点・半濁点で、1文字目に加えることができる場合は、合成した文字を返します。
	 * 合成ができないときは、c1を返します。
	 * @param c1 変換前の1文字目
	 * @param c2 変換前の2文字目
	 * @return 変換後の文字
	 */
	private static String _mergeChar1(char c1, char c2) {
		if (c2 == '゛') {
			if ("カキクケコサシスセソタチツテトハヒフヘホ".indexOf(c1) > 0) {
				switch (c1) {
				case 'カ': return "ｶﾞ";
				case 'キ': return "ｷﾞ";
				case 'ク': return "ｸﾞ";
				case 'ケ': return "ｹﾞ";
				case 'コ': return "ｺﾞ";
				case 'サ': return "ｻﾞ";
				case 'シ': return "ｼﾞ";
				case 'ス': return "ｽﾞ";
				case 'セ': return "ｾﾞ";
				case 'ソ': return "ｿﾞ";
				case 'タ': return "ﾀﾞ";
				case 'チ': return "ﾁﾞ";
				case 'ツ': return "ﾂﾞ";
				case 'テ': return "ﾃﾞ";
				case 'ト': return "ﾄﾞ";
				case 'ハ': return "ﾊﾞ";
				case 'ヒ': return "ﾋﾞ";
				case 'フ': return "ﾌﾞ";
				case 'ヘ': return "ﾍﾞ";
				case 'ホ': return "ﾎﾞ";
				}
			}
		} else if (c2 == '゜') {
			if ("ハヒフヘホ".indexOf(c1) > 0) {
				switch (c1) {
				case 'ハ': return "ﾊﾟ";
				case 'ヒ': return "ﾋﾟ";
				case 'フ': return "ﾌﾟ";
				case 'ヘ': return "ﾍﾟ";
				case 'ホ': return "ﾎﾟ";
				}
			}
		}
		return String.valueOf(c1);
	}

	/**
	 * 半角数字を全角に変換します。
	 * @param s 変換元文字列
	 * @return 変換後文字列
	 */
	/* 2009/05/27 T.KOJIMA UPDATE START 
	public static String toHalfKana(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char originalChar = s.charAt(i);
			String convertedChar = _toHalfKanaChar(originalChar);
			sb.append(convertedChar);
		}
		return sb.toString();

	}
	*/
	public static String toHalfKana(String s) {
		if (s.length() == 0) {
			return s;
		} else if (s.length() == 1) {
			return _toHalfKanaChar(s.charAt(0)) + "";
		} else {
			StringBuffer sb = new StringBuffer(s);
			int i = 0;
			for (i = 0; i < sb.length()-1; i++) {
				char originalChar1 = sb.charAt(i);
				char originalChar2 = sb.charAt(i + 1);
				String margedStr = _mergeChar1(originalChar1, originalChar2);
				
				if (margedStr.equals(String.valueOf(originalChar1)) == false) {
					sb.replace(i, i+2, margedStr);
				} else {
					String convertedStr = _toHalfKanaChar(originalChar1);
					if (convertedStr.equals(String.valueOf(originalChar1)) == false) {
						sb.replace(i, i+1, convertedStr);
					}
				}
			}
			if (i < sb.length()) {
				char originalChar1 = sb.charAt(i);
				String convertedStr = _toHalfKanaChar(originalChar1);
				if (convertedStr.equals(String.valueOf(originalChar1)) == false) {
					sb.replace(i, i+1, convertedStr);
				}
			}
			return sb.toString();
		}
	}
	/* 2009/05/27 T.KOJIMA UPDATE E N D 
	
	/*--------------------------------------------------------------------------*/
	/*=============== 半角カタカナを全角カタカナに変換する処理群 ===============*/
	/*--------------------------------------------------------------------------*/

	private static final char[] _cschrToZ_Half_KATAKANA = { '｡', '｢', '｣', '､', '･',
		'ｦ', 'ｧ', 'ｨ', 'ｩ', 'ｪ', 'ｫ', 'ｬ', 'ｭ', 'ｮ', 'ｯ', 'ｰ', 'ｱ', 'ｲ',
		'ｳ', 'ｴ', 'ｵ', 'ｶ', 'ｷ', 'ｸ', 'ｹ', 'ｺ', 'ｻ', 'ｼ', 'ｽ', 'ｾ', 'ｿ',
		'ﾀ', 'ﾁ', 'ﾂ', 'ﾃ', 'ﾄ', 'ﾅ', 'ﾆ', 'ﾇ', 'ﾈ', 'ﾉ', 'ﾊ', 'ﾋ', 'ﾌ',
		'ﾍ', 'ﾎ', 'ﾏ', 'ﾐ', 'ﾑ', 'ﾒ', 'ﾓ', 'ﾔ', 'ﾕ', 'ﾖ', 'ﾗ', 'ﾘ', 'ﾙ',
		'ﾚ', 'ﾛ', 'ﾜ', 'ﾝ', 'ﾞ', 'ﾟ' };

	private static final char[] _csstrToZ_Full_KATAKANA = { '。', '「', '」', '、', '・',
		'ヲ', 'ァ', 'ィ', 'ゥ', 'ェ', 'ォ', 'ャ', 'ュ', 'ョ', 'ッ', 'ー', 'ア', 'イ',
		'ウ', 'エ', 'オ', 'カ', 'キ', 'ク', 'ケ', 'コ', 'サ', 'シ', 'ス', 'セ', 'ソ',
		'タ', 'チ', 'ツ', 'テ', 'ト', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'ヒ', 'フ',
		'ヘ', 'ホ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ヤ', 'ユ', 'ヨ', 'ラ', 'リ', 'ル',
		'レ', 'ロ', 'ワ', 'ン', '゛', '゜' };

	private static final char _cschrToZ_Half_KATAKANA_FIRST_CHAR = _cschrToZ_Half_KATAKANA[0];

	private static final char _cschrToZ_Half_KATAKANA_LAST_CHAR = _cschrToZ_Half_KATAKANA[_cschrToZ_Half_KATAKANA.length - 1];

	/**
	 * 半角カタカナから全角カタカナへ変換します。
	 * @param c 変換前の文字
	 * @return 変換後の文字
	 */
	private static char _toFullKakaChar(char c) {
		if (c >= _cschrToZ_Half_KATAKANA_FIRST_CHAR && c <= _cschrToZ_Half_KATAKANA_LAST_CHAR) {
			return _csstrToZ_Full_KATAKANA[c - _cschrToZ_Half_KATAKANA_FIRST_CHAR];
		} else {
			return c;
		}
	}
	/**
	 * 2文字目が濁点・半濁点で、1文字目に加えることができる場合は、合成した文字を返します。
	 * 合成ができないときは、c1を返します。
	 * @param c1 変換前の1文字目
	 * @param c2 変換前の2文字目
	 * @return 変換後の文字
	 */
	private static char _mergeChar(char c1, char c2) {
		if (c2 == 'ﾞ') {
			if ("ｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾊﾋﾌﾍﾎ".indexOf(c1) > 0) {
				switch (c1) {
				case 'ｶ': return 'ガ';
				case 'ｷ': return 'ギ';
				case 'ｸ': return 'グ';
				case 'ｹ': return 'ゲ';
				case 'ｺ': return 'ゴ';
				case 'ｻ': return 'ザ';
				case 'ｼ': return 'ジ';
				case 'ｽ': return 'ズ';
				case 'ｾ': return 'ゼ';
				case 'ｿ': return 'ゾ';
				case 'ﾀ': return 'ダ';
				case 'ﾁ': return 'ヂ';
				case 'ﾂ': return 'ヅ';
				case 'ﾃ': return 'デ';
				case 'ﾄ': return 'ド';
				case 'ﾊ': return 'バ';
				case 'ﾋ': return 'ビ';
				case 'ﾌ': return 'ブ';
				case 'ﾍ': return 'ベ';
				case 'ﾎ': return 'ボ';
				}
			}
		} else if (c2 == 'ﾟ') {
			if ("ﾊﾋﾌﾍﾎ".indexOf(c1) > 0) {
				switch (c1) {
				case 'ﾊ': return 'パ';
				case 'ﾋ': return 'ピ';
				case 'ﾌ': return 'プ';
				case 'ﾍ': return 'ペ';
				case 'ﾎ': return 'ポ';
				}
			}
		}
		return c1;
	}

	/**
	 * 文字列中の半角カタカナを全角カタカナに変換します。
	 * @param s 変換前文字列
	 * @return 変換後文字列
	 */
	public static String toFullKana(String s) {
		if (s.length() == 0) {
			return s;
		} else if (s.length() == 1) {
			return _toFullKakaChar(s.charAt(0)) + "";
		} else {
			StringBuffer sb = new StringBuffer(s);
			int i = 0;
			for (i = 0; i < sb.length() - 1; i++) {
				char originalChar1 = sb.charAt(i);
				char originalChar2 = sb.charAt(i + 1);
				char margedChar = _mergeChar(originalChar1, originalChar2);
				if (margedChar != originalChar1) {
					sb.setCharAt(i, margedChar);
					sb.deleteCharAt(i + 1);
				} else {
					char convertedChar = _toFullKakaChar(originalChar1);
					if (convertedChar != originalChar1) {
						sb.setCharAt(i, convertedChar);
					}
				}
			}
			if (i < sb.length()) {
				char originalChar1 = sb.charAt(i);
				char convertedChar = _toFullKakaChar(originalChar1);
				if (convertedChar != originalChar1) {
					sb.setCharAt(i, convertedChar);
				}
			}
			return sb.toString();
		}

	}
	
	/*------------------------------------------------------------------------------------*/
	/*=============== 半角記号を全角記号に変換する処理群 2009/12/01 INSERT ===============*/
	/*------------------------------------------------------------------------------------*/
	private static final String _cschrHalf_SIGN = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~｡｢｣､･ﾞﾟ";
	private static final String _cschrFull_SIGN = "　！”＃＄％＆’（）＊＋，－．／：；＜＝＞？＠［￥］＾＿‘｛｜｝～。「」、・゛゜";

	/**
	 * 文字列中の半角記号を全角記号に変換します。
	 * @param s 変換前文字列
	 * @return 変換後文字列
	 */
	public static String toFullSign(String s) {
		StringBuffer sb = new StringBuffer(s);
		StringBuffer sbr = new StringBuffer();
		for (int i=0; i<sb.length(); i++) {
			int numIdx = _cschrHalf_SIGN.indexOf(sb.charAt(i));
			if (numIdx != -1) {
				sbr.append(_cschrFull_SIGN.charAt(numIdx));
			} else {
				sbr.append(sb.charAt(i));
			}
		}
		return sbr.toString();
	}
	
	/**
	 * 文字列中の全角記号を半角記号に変換します。
	 * @param s 変換前文字列
	 * @return 変換後文字列
	 */
	public static String toHalfSign(String s) {
		StringBuffer sb = new StringBuffer(s);
		StringBuffer sbr = new StringBuffer();
		for (int i=0; i<sb.length(); i++) {
			int numIdx = _cschrFull_SIGN.indexOf(sb.charAt(i));
			if (numIdx != -1) {
				sbr.append(_cschrHalf_SIGN.charAt(numIdx));
			} else {
				sbr.append(sb.charAt(i));
			}
		}
		return sbr.toString();
	}
}
