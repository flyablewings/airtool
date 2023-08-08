package com.hokukou.report;

import java.io.*;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : HK_ReportResources.java
	Program Name    : 印刷用のファイル管理
	Program Date    : 2008/07/06
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/06  	: 新規作成
================================================================================
********************************************************************************
*/
public class HK_ReportResources implements Serializable {

	private static final long serialVersionUID = 1L;
	private String strPath1 = "";			//文件路径名
	private String strPath2 = "";			//文件名 
	private String strDesDir = "";			//文件序列化后保存的目标路径
	
	/**
	 * 类构造方法
	 * @param strPath				文件路径名
	 * @param strFileName			文件名
	 * @throws FileNotFoundException
	 */
	public HK_ReportResources(String strPath,String strFileName) throws FileNotFoundException{
		File fle = new File(strPath,strFileName);
		if(fle.exists() && fle.isFile()){
			this.strPath1 = fle.getParent();
			this.strPath2 = fle.getName();
		}else{
			throw new FileNotFoundException();
		}
		strDesDir = "./";
	}
	/**
	 * 设定文件序列化后的目标路径
	 * @param strPath				目标路径名
	 */
	public void setDesDir(String strPath){
		this.strDesDir = strPath;
	}
	
	/***
	 * 返回文件名
	 * @return						文件名
	 */
	public String getName(){
		return strPath2;
	}
	
	public String getAbsName(){
		File fle = new File(strPath1,strPath2);
		return fle.getAbsolutePath();
	}
	
	/**
	 * 删除文件
	 * @return						true:删除成功   false:删除失败
	 */
	public boolean delete(){
		File fle = new File(strPath1,strPath2);
		return fle.delete();
	}
	
	/**
	 * 对象序列化时自动调用
	 * @param stream				
	 * @throws IOException			
	 */
	private void  writeObject(ObjectOutputStream stream)  throws IOException {

		stream.writeObject(strDesDir);
		stream.writeObject(strPath2);
		BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(new File(strPath1,strPath2)));
		byte[] bytData = new byte[1024];
		int intLen = 0;
		while((intLen = bufInput.read(bytData)) != -1){
			stream.write(bytData,0,intLen);
		}
		stream.writeObject(null);
		bufInput.close();
	}

	/***
	 * 对象反序列化时自动调用
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void  readObject(ObjectInputStream stream)  throws IOException, ClassNotFoundException {
		this.strPath1 = (String) stream.readObject();
		this.strPath2 = (String) stream.readObject();
		File fle = new File(strPath1,strPath2);
		File flePath = new File(fle.getParent());
		if(!flePath.exists()){
			flePath.mkdirs();
		}
		if(fle.exists()){
			fle.delete();
		}else{
			fle.createNewFile();
		}
		BufferedOutputStream bufOutput = new BufferedOutputStream(new FileOutputStream(fle));
		byte[] bytData = new byte[1024];
		int intLen = 0;
		while((intLen = stream.read(bytData)) != -1){
			bufOutput.write(bytData,0,intLen);
		}
		bufOutput.flush();
		bufOutput.close();
	}	
}
