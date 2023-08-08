package com.hokukou.data;

import java.math.BigDecimal;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_LatLng.java
	Program Name    : HK_LatLngクラス
	Program Date    : 2010/04/20
	Programmer      : T.Ogawa
   
===< Update History >===========================================================
	2010/04/20  : 新規作成
================================================================================
	//Accuracy
	// 0 不明な場所
	// 1 国レベルの精度
	// 2 地域 (州、省、県など) レベルの精度
	// 3 準地域 (郡、市区町村など) レベルの精度
	// 4 町 (番地) レベルの精度
	// 5 郵便番号レベルの精度
	// 6 通りレベルの精度
	// 7 交差点レベルの精度
	// 8 住所レベルの精度
	// 9 建物（建物名、不動産名、ショッピングセンターなど）レベルの精度
********************************************************************************
*/

public class HK_LatLng {
	/*
	************************************************************************
	**  private変数
	************************************************************************
	*/
	public String Latitude = null;			//緯度
	public String Longitude = null;		//経度
	public int Accuracy = -1;				//精度(Googleジオコーディング結果)
	
	/*
	************************************************************************
	**  コンストラクタ
	************************************************************************
	*/
	public HK_LatLng() {
		
	}
	
	public HK_LatLng(String lat, String lng) {
		this.Latitude  = lat;
		this.Longitude = lng;
	}
	
	public HK_LatLng(BigDecimal lat, BigDecimal lng) {
		this.Latitude  = HK_Data.Format(lat, "##0.000000", 10).trim();
		this.Longitude = HK_Data.Format(lng, "##0.000000", 10).trim();
	}
	
	public HK_LatLng(double lat, double lng) {
		this.Latitude  = HK_Data.Format(new BigDecimal(lat), "##0.000000", 10).trim();
		this.Longitude = HK_Data.Format(new BigDecimal(lng), "##0.000000", 10).trim();
	}
	
	/*
	************************************************************************
	**  publicメソッド
	************************************************************************
	*/
	/**
	 * ２点間の距離(メートル)を算出する処理
	 */
	public int getDistanceM(HK_LatLng to) {
		return HK_Location.getDistanceM(this, to);
	}

	/**
	 * ２点間の距離(キロメートル)を算出する処理
	 */
	public BigDecimal getDistanceKM(HK_LatLng to) {
		return HK_Location.getDistanceKM(this, to);
	}

	/**
	 * 方角を算出する処理
	 */
	public int getAngle(HK_LatLng to) {
		return HK_Location.getAngle(this, to);
	}

	/*
	************************************************************************
	**  public static メソッド
	************************************************************************
	*/
	/**
	 * 入力値が緯度のフォーマットに適合するかチェックする処理
	 */
	public static boolean checkLatValue(String strLat) {
		boolean blnResult = true;
		
		//未指定
		if (norm(strLat) == null) {
			blnResult = false;
		}
		
		//桁数オーバー
		if (blnResult == true) {
			if (HK_Data.ByteLen(strLat) > 10) {
				blnResult = false;
			}
		}
		
		//数値以外
		if (blnResult == true) {
			try {
				new BigDecimal(strLat);
			} catch (NumberFormatException e) {
				blnResult = false;
			}
		}
		
		//範囲外
		if (blnResult == true) {
			BigDecimal decLat = HK_Data.parseDec(strLat);
			if (decLat.compareTo(new BigDecimal(-90)) < 0 || decLat.compareTo(new BigDecimal(90)) > 0) {
				blnResult = false;
			}
		}
		return blnResult;
	}

	/**
	 * 入力値が経度のフォーマットに適合するかチェックする処理
	 */
	public static boolean checkLngValue(String strLng) {
		boolean blnResult = true;
		
		//未指定
		if (norm(strLng) == null) {
			blnResult = false;
		}
		
		//桁数オーバー
		if (blnResult == true) {
			if (HK_Data.ByteLen(strLng) > 11) {
				blnResult = false;
			}
		}
		
		//数値以外
		if (blnResult == true) {
			try {
				new BigDecimal(strLng);
			} catch (NumberFormatException e) {
				blnResult = false;
			}
		}
		
		//範囲外
		if (blnResult == true) {
			BigDecimal decLng = HK_Data.parseDec(strLng);
			if (decLng.compareTo(new BigDecimal(-180)) < 0 || decLng.compareTo(new BigDecimal(180)) > 0) {
				blnResult = false;
			}
		}
		return blnResult;
	}
	
	/*
	************************************************************************
	**  private static メソッド
	************************************************************************
	*/
	public static String norm(String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			return value;
		}
	}
}
