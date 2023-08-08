package com.hokukou.mail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import com.hokukou.data.HK_Data;
import com.sk_jp.mail.JISDataSource;
import com.sk_jp.mail.MailUtility;

/*
********************************************************************************
================================================================================
   ホクコウ共通ライブラリ					
================================================================================
   Program Number  : HK_Mail.java
   Program Name    : メール転送
   Program Date    : 2007/10/29
   Programmer      : H.Fujimoto
   
===< Update History >===========================================================
   2007/10/29      : 新規作成
   2007/11/05      : CCを追加										= T.Ogawa
   2008/12/02      : POP before SMTPに対応							= T.Ogawa
   2008/12/19      : 機種依存文字の文字化けを修正					= T.Ogawa
   2008/12/26      : 添付ファイルに対応								= T.Ogawa
   2009/06/30      : POP3接続設定方法を変更							= T.Ogawa
   2009/07/02      : POP3のコネクションクローズ処理を追加			= T.Ogawa
================================================================================
********************************************************************************
*/
public class HK_Mail {

	private String _strSmtpServer = "";
	private String _strSmtpUserID = "";
	private String _strSmtpPass = "";
	private String _strFromAddress = "";
	private String _strSubject = "";
	private String _strContent = "";
	private String _strBcc = "";
	private String _strCc = "";
	public Boolean isHtmlMail = false;
	/***** 2008/12/02 INSERT START *****/
	private String _strPop3Server = "";
	private int _intPop3Port = 110;
	private int _intSmtpPort = 25;
	/***** 2008/12/02 INSERT E N D *****/
	/***** 2009/01/26 INSERT START *****/
	private String _strAttachFileTmpNM = "";
	private String _strAttachFileNM = "";
	/***** 2009/01/26 INSERT E N D *****/
	
	public HK_Mail(String strSmpt, String strFromAddress) {
		super();
		this._strSmtpServer = strSmpt;
		this._strFromAddress = strFromAddress;
	}
	
	public HK_Mail(String strSmpt, String strUser, String strPass, String strFromAddress) {
		super();
		this._strSmtpServer = strSmpt;
		this._strSmtpUserID = strUser;
		this._strSmtpPass = strPass;
		this._strFromAddress = strFromAddress;
	}

	public HK_Mail(String strPop3, int intPop3Port, String strSmtp, int intSmtpPort, String strUser, String strPass, String strFromAddress) {
		super();
		this._strSmtpServer = strSmtp;
		this._strSmtpUserID = strUser;
		this._strSmtpPass = strPass;
		this._strFromAddress = strFromAddress;
		/***** 2008/12/02 INSERT START *****/
		this._strPop3Server = strPop3;
		this._intPop3Port = intPop3Port;
		this._intSmtpPort = intSmtpPort;
		/***** 2008/12/02 INSERT E N D *****/
	}
	
	public void setContent(String strFileName, HashMap hsmData) {
		FileInputStream fileStream;
		InputStreamReader fileReader;
		BufferedReader contentBuffer;
		try {
			fileStream = new FileInputStream(HK_Data.getRealPath() + "/mailtemp/" + strFileName);
			fileReader = new InputStreamReader(fileStream ,"SJIS");
			
			contentBuffer = new BufferedReader(fileReader, 10);
			String strTemp = "";
			String strBuf = "";
	        
			while ((strTemp = contentBuffer.readLine()) != null) {
				strBuf = strBuf + strTemp + "\n";
	        }

			
			//Mapオブジェクトの「キー」の一覧をSetオブジェクトに取得
			Set set = hsmData.keySet();
			//Setインターフェースのiterator()メソッドを使い、イテレータ取得
			Iterator iterator = set.iterator();
			Object object;
			//オブジェクト内のデータを全て取得
			while(iterator.hasNext()){
				object = iterator.next();
				strBuf = strBuf.replace("$[" + String.valueOf(object) + "]", String.valueOf(hsmData.get(object)));
			}
			
			int intStart;
			int intEnd;
			while(true){
				intStart = strBuf.indexOf("$[[[[[例");
				if (intStart < 0) {
					break;
				}
				intEnd = strBuf.indexOf("]]]]]", intStart - 1);
				
				strBuf = strBuf.substring(0, intStart) + strBuf.substring(intEnd + 6);
			}
			this._strContent = strBuf  + "\n";

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSubject(String strSubject) {
		this._strSubject = strSubject;
	}
	public void setBcc(String strBcc) {
		this._strBcc = strBcc;
	}
	public void setCc(String strCc) {
		this._strCc = strCc;
	}
	/***** 2009/01/26 UPDATE START *****/
	public void setAttachFile(String strAttachFileTmpNM, String strAttachFileNM) {
		this._strAttachFileTmpNM = strAttachFileTmpNM;
		this._strAttachFileNM = strAttachFileNM;
	}
	/***** 2009/01/26 INSERT E N D *****/
	
	public Session getSesssion() {
		Properties mailProp = new Properties();
		Session mailSession;
		Authenticator mailAuth;
		
		if (this._strSmtpUserID.equals("")) {
			mailProp.put("mail.smtp.host",this._strSmtpServer);										// SMTPサーバ名
			mailProp.put("mail.host",this._strSmtpServer);											// 接続するサーバ名
			/***** 2008/12/02 INSERT START *****/
			if (this._intSmtpPort != -1) {
				mailProp.put("mail.smtp.port", new String(String.valueOf(this._intSmtpPort)));		// SMTPサーバPort
			}
			/***** 2008/12/02 INSERT E N D *****/
			mailSession= Session.getDefaultInstance(mailProp, null);								// メールセッションを確立
		} else {
			mailProp.put("mail.smtp.host",this._strSmtpServer);										// SMTPサーバ名
			mailProp.put("mail.host",this._strSmtpServer);											// 接続するサーバ名
			/***** 2008/12/02 INSERT START *****/
			if (this._intSmtpPort != -1) {
				mailProp.put("mail.smtp.port", new String(String.valueOf(this._intSmtpPort)));		// SMTPサーバPort
			}
			/***** 2008/12/02 INSERT E N D *****/
			mailProp.put("mail.smtp.auth", "true");
			mailAuth = new MyAuthenticator(this._strSmtpUserID, this._strSmtpPass); 
			mailSession= Session.getInstance(mailProp, mailAuth);									// メールセッションを確立
		}
		return mailSession;
	}	
	
	public void sendMail(String strToAddress) throws AddressException, MessagingException, MessagingException, UnsupportedEncodingException {

		Properties mailProp = new Properties();
		Authenticator mailAuth;
		Session mailSession;
		
		if (this._strSmtpUserID.equals("")) {
			mailProp.put("mail.smtp.host", this._strSmtpServer);									// SMTPサーバ名
			mailProp.put("mail.host", this._strSmtpServer);											// 接続するサーバ名
			/***** 2008/12/02 INSERT START *****/
			if (this._intSmtpPort != -1) {
				mailProp.put("mail.smtp.port", new String(String.valueOf(this._intSmtpPort)));		// SMTPサーバPort
			}
			/***** 2008/12/02 INSERT E N D *****/
			mailSession = Session.getDefaultInstance(mailProp, null);								// メールセッションを確立
		} else {
			mailProp.put("mail.smtp.host", this._strSmtpServer);									// SMTPサーバ名
			mailProp.put("mail.host", this._strSmtpServer);											// 接続するサーバ名
			/***** 2008/12/02 INSERT START *****/
			if (this._intSmtpPort != -1) {
				mailProp.put("mail.smtp.port", new String(String.valueOf(this._intSmtpPort)));		// SMTPサーバPort
			}
			/***** 2008/12/02 INSERT E N D *****/
			mailProp.put("mail.smtp.auth", "true");
			mailAuth = new MyAuthenticator(this._strSmtpUserID, this._strSmtpPass); 
			mailSession = Session.getInstance(mailProp,mailAuth);									// メールセッションを確立
		}
		
		sendMailWithSession(strToAddress, mailSession);
	}
	
	public void sendMailWithSession(String strToAddress, Session mailSession) throws AddressException, MessagingException, MessagingException, UnsupportedEncodingException {

		this._connectPop3(mailSession);										// POP操作(POP before SMTP)
		
		MimeMessage mailMsg=new MimeMessage(mailSession);					// 送信メッセージを生成
	    
    	mailMsg.setRecipients(Message.RecipientType.TO, strToAddress);		// 送信先（TOのほか、CCやBCCも設定可能）
    	mailMsg.setFrom(new InternetAddress(this._strFromAddress));		// Fromヘッダ
    	
    	/***** 2008/12/19 UPDATE START *****/
    	//mailMsg.setSubject(this._strSubject);								// 件名
    	mailMsg.setSubject(MailUtility.encodeWordJIS(this._strSubject));	// 件名
    	/***** 2008/12/19 UPDATE E N D *****/
    	
    	/***** 2009/01/26 UPDATE START *****/
    	///***** 2008/12/19 UPDATE START *****/
		////// Update 2008/02/14  文字化け対策
		//////mailMsg.setText(this._strContent, "ISO-2022-JP");					// 本文
		////String encodedBody = MimeUtility.encodeText(this._strContent, "iso-2022-jp", "B"); 
		////mailMsg.setContent(encodedBody, "text/plain; charset=iso-2022-jp"); 
		////mailMsg.setText(this._strContent); 
    	//mailMsg.setDataHandler(new DataHandler(new JISDataSource(this._strContent)));	// 本文
    	///***** 2008/12/19 UPDATE E N D *****/
    	if (this._strAttachFileTmpNM.equals("")) {
    		//添付なし
        	mailMsg.setDataHandler(new DataHandler(new JISDataSource(this._strContent)));	// 本文
    	} else {
    		//添付あり
	        MimeBodyPart mbp1 = new MimeBodyPart();
	        mbp1.setDataHandler(new DataHandler(new JISDataSource(this._strContent)));
	        
	        MimeBodyPart mbp2 = new MimeBodyPart();
	        FileDataSource fds = new FileDataSource(HK_Data.getRealPath() + "/uploadfile/" + this._strAttachFileTmpNM);
	        mbp2.setDataHandler(new DataHandler(fds));
	        mbp2.setFileName(MimeUtility.encodeWord(this._strAttachFileNM));
	
	        Multipart mp = new MimeMultipart();
	        mp.addBodyPart(mbp1);
	        mp.addBodyPart(mbp2);
	        
	        mailMsg.setContent(mp);
    	}
    	/***** 2009/01/26 UPDATE E N D *****/
    	
    	if (_strBcc.length() != 0) {
        	Address adrBCC = new InternetAddress(_strBcc);
        	mailMsg.addRecipient(Message.RecipientType.BCC, adrBCC);
    	}
    	
    	if (_strCc.length() != 0) {
        	Address adrCC = new InternetAddress(_strCc);
        	mailMsg.addRecipient(Message.RecipientType.CC, adrCC);
    	}
    	
    	/***** 2009/01/26 DELETE START *****/
    	//mailMsg.setHeader("Content-Type", "text/plain; charset=\"iso-2022-jp\"");
    	/***** 2009/01/26 DELETE E N D *****/
    	
    	mailMsg.setHeader("Content-Transfer-Encoding","7bit");
    	
    	Transport.send(mailMsg);											// メール送信
    }
	
	/***** 2008/12/02 INSERT START *****/
	private void _connectPop3(Session mailSession) throws MessagingException {
		if (this._strPop3Server.equals("") == false && this._strSmtpUserID.equals("") == false && this._strSmtpPass.equals("") == false) {
			
			Store store = null;
			try {
				/***** 2009/06/30 UPDATE START *****/
				//Session session = Session.getDefaultInstance(System.getProperties(), null);
				Properties mailProp = new Properties();
				mailProp.put("mail.pop3.host",this._strPop3Server);						// POP3サーバ名
				mailProp.put("mail.pop3.port",this._intPop3Port);						// POP3サーバPort
				mailProp.put("mail.pop3.user",this._strSmtpUserID);						// POP3サーバ名
				Session session = Session.getDefaultInstance(mailProp, null);			// メールセッションを確立
				/***** 2009/06/30 UPDATE E N D *****/
				store = session.getStore("pop3");
				store.connect(this._strPop3Server, this._intPop3Port, this._strSmtpUserID, this._strSmtpPass);
			} finally {
				/***** 2009/07/02 UPDATE START *****/
				if (store != null) {
					store.close();														//POP3接続の切断
					store = null;
				}
				/***** 2009/07/02 UPDATE E N D *****/
			}
		}
	}
	/***** 2008/12/02 INSERT E N D *****/
	
	class MyAuthenticator extends Authenticator {
		private String _strUser = "";
		private String _strPass = "";
		public MyAuthenticator(String strUser, String strPass) {
			this._strUser = strUser;
			this._strPass = strPass;
		}
			
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(_strUser, _strPass);
		}
	}
}
