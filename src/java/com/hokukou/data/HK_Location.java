package com.hokukou.data;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_Location.java
	Program Name    : HK_Locationクラス
	Program Date    : 2010/04/20
	Programmer      : T.Ogawa
   
===< Update History >===========================================================
	2010/04/20  : 新規作成
================================================================================
********************************************************************************
*/

public class HK_Location {
	/*
	************************************************************************
	**  定数
	************************************************************************
	*/
	//地球の赤道半径 (メートル)
	public static int CNST_EARTH_RADIUS = 6378137;
	
	/*
	************************************************************************
	**  public staicメソッド
	************************************************************************
	*/
	/**
	 * DMS形式をDEG形式に変換する処理
	 */
	public static String convDmsToDeg(String strDms) {
		//http://d.hatena.ne.jp/end0tknr/20081026/1225012257
		//　Degree = D + M/60 + S/3600
		
		String strWK = "";
		
		if (strDms.toUpperCase().startsWith("N") ||
			strDms.toUpperCase().startsWith("S") ||
			strDms.toUpperCase().startsWith("E") ||
			strDms.toUpperCase().startsWith("W") ||
			strDms.toUpperCase().startsWith("+") ||
			strDms.toUpperCase().startsWith("-")) {
			strWK = strDms.substring(1);
		} else {
			strWK = strDms;
		}
		
		String strData[] = strWK.split("\\.");
		
		BigDecimal decD = HK_Data.parseDec(strData[0]);
		BigDecimal decM = HK_Data.parseDec(strData[1]).divide(new BigDecimal(60), 13, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal decS = HK_Data.parseDec(strData[2] + "." + strData[3]).divide(new BigDecimal(3600), 13, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal decDeg = decD.add(decM).add(decS);
		
		return HK_Data.Format(decDeg, "##0.000000", 10).trim();
	}
	
	/**
	 * 経度　日本測地系をWGS84系に変換する処理(DEG形式)
	 */
	public static String convTokyoToWGS84_Lat(String strDegTokyo_Lat, String strDegTokyo_Lng) {
		//http://d.hatena.ne.jp/end0tknr/20081026/1225012257
		//http://blog.gpso.info/2006/08/post_2.html
		//  lat_w = lat_t -lat_t*0.00010695 +lon_t*0.000017464 +0.0046017
		
		BigDecimal decDegTokyo_Lat = HK_Data.parseDec(strDegTokyo_Lat);
		BigDecimal decDegTokyo_Lng = HK_Data.parseDec(strDegTokyo_Lng);

		BigDecimal decLatOffset = decDegTokyo_Lat.multiply(new BigDecimal(0.00010695));
		BigDecimal decLngOffset = decDegTokyo_Lng.multiply(new BigDecimal(0.000017464));
		
		BigDecimal decDegWGS84 = decDegTokyo_Lat;
		decDegWGS84 = decDegWGS84.subtract(decLatOffset);
		decDegWGS84 = decDegWGS84.add(decLngOffset);
		decDegWGS84 = decDegWGS84.add(new BigDecimal(0.0046017));
		
		return HK_Data.Format(decDegWGS84, "##0.000000", 10).trim();
	}
	
	/**
	 * 緯度　日本測地系をWGS84系に変換する処理(DEG形式)
	 */
	public static String convTokyoToWGS84_Lng(String strDegTokyo_Lat, String strDegTokyo_Lng) {
		//http://d.hatena.ne.jp/end0tknr/20081026/1225012257
		//http://blog.gpso.info/2006/08/post_2.html
		//  lon_w = lon_t -lat_t * 0.000046038 - lon_t * 0.000083043 + 0.010040
		
		BigDecimal decDegTokyo_Lat = HK_Data.parseDec(strDegTokyo_Lat);
		BigDecimal decDegTokyo_Lng = HK_Data.parseDec(strDegTokyo_Lng);

		BigDecimal decLatOffset = decDegTokyo_Lat.multiply(new BigDecimal(0.000046038));
		BigDecimal decLngOffset = decDegTokyo_Lng.multiply(new BigDecimal(0.000083043));
		
		BigDecimal decDegWGS84 = decDegTokyo_Lng;
		decDegWGS84 = decDegWGS84.subtract(decLatOffset);
		decDegWGS84 = decDegWGS84.subtract(decLngOffset);
		decDegWGS84 = decDegWGS84.add(new BigDecimal(0.010040));
		
		return HK_Data.Format(decDegWGS84, "##0.000000", 10).trim();
	}
	
	/**
	 * ２点間の距離(メートル)を算出する処理
	 */
	public static int getDistanceM(HK_LatLng from, HK_LatLng to) {
		//http://www.movable-type.co.uk/scripts/latlong.html
		//http://worldmaps.web.infoseek.co.jp/distance_calculation.htm
		
		double lat1 = Double.parseDouble(from.Latitude)  * Math.PI / 180;
		double lat2 = Double.parseDouble(to.Latitude)    * Math.PI / 180;
		double lng1 = Double.parseDouble(from.Longitude) * Math.PI / 180;
		double lng2 = Double.parseDouble(to.Longitude)   * Math.PI / 180;
		
		double x = Math.sin((lat2-lat1)/2)*Math.sin((lat2-lat1)/2)+Math.cos(lat1)*Math.cos(lat2)*Math.sin((lng2-lng1)/2)*Math.sin((lng2-lng1)/2); 
		double y = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1-x)); 
		double D = y * HK_Location.CNST_EARTH_RADIUS;

		return HK_Data.RoundOff(new BigDecimal(D), -1, 4).intValue();
	}

	/**
	 * ２点間の距離(キロメートル)を算出する処理
	 */
	public static BigDecimal getDistanceKM(HK_LatLng from, HK_LatLng to) {
		BigDecimal decDistance;
		
		decDistance = new BigDecimal(HK_Location.getDistanceM(from, to));
		decDistance = decDistance.divide(new BigDecimal(1000), 6, BigDecimal.ROUND_HALF_DOWN);
		decDistance = HK_Data.RoundOff(decDistance, -3, 4);
		
		return decDistance;
	}

	/**
	 * 基点からの方角を算出する処理
	 */
	public static int getAngle(HK_LatLng from, HK_LatLng to) {
		//http://www.movable-type.co.uk/scripts/latlong.html
		//http://worldmaps.web.infoseek.co.jp/distance_calculation.htm

		double lat1 = Double.parseDouble(from.Latitude)  * Math.PI / 180;
		double lat2 = Double.parseDouble(to.Latitude)    * Math.PI / 180;
		double lng1 = Double.parseDouble(from.Longitude) * Math.PI / 180;
		double lng2 = Double.parseDouble(to.Longitude)   * Math.PI / 180;

		double y = Math.sin(lng2-lng1) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lng2-lng1);
		double deg = Math.atan2(y, x) * 180 / Math.PI;
		
		deg = (deg + 360 ) % 360;
		
		BigDecimal decDeg = HK_Data.RoundOff(new BigDecimal(deg), -1, 4);
		
		return decDeg.intValue();
	}
	
	/**
	 * 住所から緯度経度を算出する処理
	 */
	public static HK_LatLng getLatLngFromAddress(String strAddress, String strGoogleApiKey) throws Exception {
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
		
		InputStream is = null;
		HK_LatLng latlng = null;
		String strAccuracy = null;
		
		try {
        	if (strAddress != null && strAddress.trim().equals("") == false) {
				URL url = new URL("http://maps.google.com/maps/geo?"
								+ "key=" + strGoogleApiKey
								+ "&q=" + URLEncoder.encode(strAddress, "UTF-8")
								+ "&sensor=false"
								+ "&output=xml");
				
				URLConnection uc = url.openConnection();
				is = uc.getInputStream();
				
				DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = dbfactory.newDocumentBuilder();
				Document doc = builder.parse(is);
				Element root = doc.getDocumentElement();

				NodeList listPlacemark = root.getElementsByTagName("Placemark");
				if (listPlacemark.getLength() > 0) {
					Element element = (Element) listPlacemark.item(0);
					
					Element elAddressDetails = (Element) element.getElementsByTagName("AddressDetails").item(0);
					strAccuracy = elAddressDetails.getAttribute("Accuracy");
					
					NodeList listPoint = element.getElementsByTagName("Point");
					if (listPoint.getLength() > 0) {
						Element elPoint = (Element) listPoint.item(0);
						Element elCoordinates = (Element) elPoint.getElementsByTagName("coordinates").item(0);
						String strCoordinates = elCoordinates.getFirstChild().getNodeValue();

						String strData[] = strCoordinates.split(",");

						BigDecimal decLat = HK_Data.parseDec(strData[1]);
						decLat = HK_Data.RoundOff(decLat, -7, 9);

						BigDecimal decLng = HK_Data.parseDec(strData[0]);
						decLng = HK_Data.RoundOff(decLng, -7, 9);
						
						latlng = new HK_LatLng();
						latlng.Latitude  = HK_Data.Format(decLat, "##0.000000", 10).trim();
						latlng.Longitude = HK_Data.Format(decLng, "##0.000000", 10).trim();
						latlng.Accuracy = HK_Data.parseInt(strAccuracy);
					}
				}
			}
        } catch (UnknownHostException e) {
        	//ホストに接続できない
        	e.printStackTrace();
        } catch (Exception e) {
            throw e;
        } finally {
        	if (is != null) {
        		is.close();
        		is = null;
        	}
        }
		return latlng;
	}

	/**
	 * 緯度経度から住所を算出する処理
	 * （YahooWebサービスのクレジット表示が必要です）
	 */
	public static String getAddressFromLatLng(HK_LatLng latlng, String strYahooApiKey) throws Exception {
		InputStream is = null;
		String strReturn = "";
		
		try {
			URL url = new URL("http://reverse.search.olp.yahooapis.jp/OpenLocalPlatform/V1/reverseGeoCoder?"
							+ "appid=" + strYahooApiKey
							+ "&datum=wgs"
							+ "&lat=" + latlng.Latitude
							+ "&lon=" + latlng.Longitude
							+ "&output=xml");
			
			URLConnection uc = url.openConnection();
			is = uc.getInputStream();
			
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();

			NodeList listFeature = root.getElementsByTagName("Feature");
			if (listFeature.getLength() > 0) {
				Element element = (Element) listFeature.item(0);
				NodeList listProperty = element.getElementsByTagName("Property");
				if (listProperty.getLength() > 0) {
					Element elProperty = (Element) listProperty.item(0);
					Element elAddress = (Element) elProperty.getElementsByTagName("Address").item(0);
					
					strReturn = elAddress.getFirstChild().getNodeValue();
				}
			}
        } catch (UnknownHostException e) {
        	//ホストに接続できない
        	e.printStackTrace();
        } catch (Exception e) {
            throw e;
        } finally {
        	if (is != null) {
        		is.close();
        		is = null;
        	}
        }
		return strReturn;
	}
}
