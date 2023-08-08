package com.hokukou.database;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : DB_ResultSet.java
	Program Name    : DB_ResultSetクラス
	Program Date    : 2008/07/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/05  : 新規作成
	2010/06/21  : getBigDecimalIsNullを追加        		             = T.Ogawa
================================================================================
********************************************************************************
*/

import java.sql.*;
import java.util.Calendar;
import java.math.BigDecimal;

public class DB_ResultSet{

	ResultSet res;
	
	public DB_ResultSet(ResultSet res){
		this.res=res;
	}

	public boolean absolute(int row) throws DB_Exception{
		try {
			return res.absolute(row);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}    
 	public void afterLast() throws DB_Exception{
		try {
			res.afterLast();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}     
	public void beforeFirst() throws DB_Exception{
		try {
			res.beforeFirst();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void cancelRowUpdates() throws DB_Exception{
		try {
			res.cancelRowUpdates();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void clearWarnings() throws DB_Exception{
		try {
			res.clearWarnings();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void close(){
		try {
			res.close();
		} catch (SQLException e) {
			// Nothing
		}
	}
	public int findColumn(String columnName) throws DB_Exception{
		try {
			return res.findColumn(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	} 
	public boolean first() throws DB_Exception{
		try {
			return res.first();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	

	public boolean getBoolean(int columnIndex) throws DB_Exception{
		try {
			return res.getBoolean(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean getBoolean(String columnName) throws DB_Exception{
		try {
			return res.getBoolean(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	 
	public byte getByte(int columnIndex) throws DB_Exception{
		try {
			return res.getByte(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public byte getByte(String columnName) throws DB_Exception{
		try {
			return res.getByte(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public byte[] getBytes(int columnIndex)  throws DB_Exception{
		try {
			return res.getBytes(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public byte[] getBytes(String columnName) throws DB_Exception{
		try {
			return res.getBytes(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public Date getDate(int columnIndex) throws DB_Exception{
		try {
			return res.getDate(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	} 
	public Date getDate(int columnIndex, Calendar cal) throws DB_Exception{
		try {
			return res.getDate(columnIndex,cal);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	} 
	public Date getDate(String columnName) throws DB_Exception{
		try {
			return res.getDate(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	} 
	public Date getDate(String columnName, Calendar cal) throws DB_Exception{
		try {
			return res.getDate(columnName,cal);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public double getDouble(int columnIndex) throws DB_Exception{
		try {
			return res.getDouble(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public double getDouble(String columnName) throws DB_Exception{
		try {
			return res.getDouble(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public float getFloat(int columnIndex) throws DB_Exception{
		try {
			return res.getFloat(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public float getFloat(String columnName) throws DB_Exception{
		try {
			return res.getFloat(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public int getInt(int columnIndex) throws DB_Exception{
		try {
			return res.getInt(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public int getInt(String columnName) throws DB_Exception{
		try {
			int dat = res.getInt(columnName);
			if (res.wasNull() == true){
				dat = 0;
			}
			return dat; 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public boolean getIntIsNull(String columnName) throws DB_Exception{
		try {
			res.getInt(columnName);
			return res.wasNull(); 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public long getLong(int columnIndex) throws DB_Exception{
		try {
			return res.getLong(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public long getLong(String columnName) throws DB_Exception{
		try {
			long dat = res.getLong(columnName);
			if (res.wasNull() == true){
				dat = 0;
			}
			return dat; 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public BigDecimal getBigDecimal(String columnName) throws DB_Exception{
		try {
			BigDecimal dat = res.getBigDecimal(columnName);
			if (res.wasNull() == true){
				dat = new BigDecimal(0);
			}
			return dat; 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public boolean getBigDecimalIsNull(String columnName) throws DB_Exception{
		try {
			res.getBigDecimal(columnName);
			return res.wasNull(); 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	
	public ResultSetMetaData getMetaData() throws DB_Exception{
		try {
			return res.getMetaData();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public int getRow() throws DB_Exception{
		try {
			return res.getRow();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	} 
	public short getShort(int columnIndex)  throws DB_Exception{
		try {
			return res.getShort(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public short getShort(String columnName) throws DB_Exception{
		try {
			short dat = res.getShort(columnName);
			if (res.wasNull() == true){
				dat = 0;
			}
			return dat; 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public String getString(int columnIndex) throws DB_Exception{
		try {
			return res.getString(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public String getString(String columnName) throws DB_Exception{
		try {
			String str = res.getString(columnName);
			if (res.wasNull() == true){
				str = "";
			}
			return str; 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public Boolean getStringIsNull(String columnName) throws DB_Exception{
		try {
			res.getString(columnName);
			return res.wasNull(); 
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	
	public Time getTime(int columnIndex) throws DB_Exception{
		try {
			return res.getTime(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public Time getTime(int columnIndex, Calendar cal) throws DB_Exception{
		try {
			return res.getTime(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public Time getTime(String columnName) throws DB_Exception{
		try {
			return res.getTime(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public Time getTime(String columnName, Calendar cal) throws DB_Exception{
		try {
			return res.getTime(columnName, cal);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public Timestamp getTimestamp(int columnIndex) throws DB_Exception{
		try {
			return res.getTimestamp(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws DB_Exception{
		try {
			return res.getTimestamp(columnIndex);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public String getTimestampStr(String columnName) throws DB_Exception{
		try {
			Timestamp ts = res.getTimestamp(columnName);
			if (res.wasNull() == true){
				return  "";
			}else{
				return ts.toString();
			}
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	
	public Timestamp getTimestamp(String columnName) throws DB_Exception{
		try {
			return res.getTimestamp(columnName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public Timestamp getTimestamp(String columnName, Calendar cal) throws DB_Exception{
		try {
			return res.getTimestamp(columnName, cal);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

	public int getType() throws DB_Exception{
		try {
			return res.getType();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean isAfterLast()throws DB_Exception{
		try {
			return res.isAfterLast();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean isBeforeFirst() throws DB_Exception{
		try {
			return res.isBeforeFirst();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean isFirst() throws DB_Exception{
		try {
			return res.isFirst();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean isLast() throws DB_Exception{
		try {
			return res.isLast();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean last() throws DB_Exception{
		try {
			return res.last();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean next() throws DB_Exception{
		try {
			return res.next();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public boolean previous() throws DB_Exception{
		try {
			return res.previous();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	
}
