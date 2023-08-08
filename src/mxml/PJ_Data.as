/********************************************************************************
================================================================================
   	Flex2　プロジェクト共通ライブラリ
================================================================================
	Program Number  : PJ_Data.as
	Program Name    : PJ_Data
	Program Date    : 2007/09/20
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2007/09/20 : 新規作成
================================================================================
********************************************************************************/

package {
	import HK_Common.HK_Data.HK_Encrypt;
	
	public class PJ_Data{
		
		private static const _csstrDecodeKey:String = "05829473610582947361";
		
		/**
		 * パスワードをエンコード（暗号）する処理
		 * @param strDecPw:デコードパスワード
		 * @strDecKey strDecPw:パスワードするためのKEY
		 */		
		public static function encodePw(strDecPw:String) : String {
			var strEncPw:String = "";
			strEncPw = HK_Encrypt.encode(strDecPw, PJ_Data._csstrDecodeKey);

			return strEncPw;
		}

		/**
		 * パスワードをデコード（復号）する処理
		 * @param strDecPw:デコードパスワード
		 * @strDecKey strDecPw:パスワードするためのKEY
		 */		
		public static function decodePw(strEncPw:String) : String {
			var strDecPw:String = "";
			strDecPw = HK_Encrypt.decode(strEncPw, PJ_Data._csstrDecodeKey);

			return strDecPw;
		}
	}
}