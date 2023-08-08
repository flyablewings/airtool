package com.hokukou.database;

import java.sql.*;
import java.sql.Date;
import java.util.*;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : DB_Statement.java
	Program Name    : JDBC PreparedStatementカスタムクラス
	Program Date    : 2008/07/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/05  : 新規作成
	2008/07/05  : setFetchSize(500)を設定							= H.Fujimoto
================================================================================
********************************************************************************
*/
public class DB_Statement{

	PreparedStatement _stmt;
	boolean _blnIsAutoCommitFalse;
	
	public DB_Statement(PreparedStatement stmt){
		_stmt = stmt;
		_blnIsAutoCommitFalse = false;
	}
	
	public void close() {
		try {
			/***** 2008/07/04 INSERT START *****/
			if (_blnIsAutoCommitFalse == true) {
				_stmt.getConnection().setAutoCommit(true);
			}
			/***** 2008/07/04 INSERT E N D *****/
			_stmt.close();
		} catch (SQLException e){
			// Nothing
		}
	}
	
	public DB_ResultSet executeQuery() throws DB_Exception{
		try {
			/***** 2008/07/04 INSERT START *****/
			if (_stmt.getConnection().getAutoCommit() == true) {
				_stmt.getConnection().setAutoCommit(false);
				_blnIsAutoCommitFalse = true;
			}
			_stmt.setFetchSize(500);
			/***** 2008/07/04 INSERT E N D *****/
			return new DB_ResultSet(_stmt.executeQuery());
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
    public int executeUpdate() throws DB_Exception{
    	try {
			return _stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
    }
    public void addBatch(String sql) throws DB_Exception{
    	try {
			_stmt.addBatch(sql);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
    } 
    public void addBatch() throws DB_Exception{
    	try {
			_stmt.addBatch();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
    }
    public int[] executeBatch() throws DB_Exception{
    	try {
			return _stmt.executeBatch();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
    } 
    
    public void setQueryTimeout(int seconds) throws DB_Exception{
    	try {
			_stmt.setQueryTimeout(seconds);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
    }
	
	public void setDate(int parameterIndex, Date x) throws DB_Exception{
		try {
			_stmt.setDate(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setDate(int parameterIndex, Date x, Calendar cal) throws DB_Exception{
		try {
			_stmt.setDate(parameterIndex, x, cal);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setDouble(int parameterIndex, double x) throws DB_Exception{
		try {
			_stmt.setDouble(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setFloat(int parameterIndex, float x) throws DB_Exception{
		try {
			_stmt.setFloat(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setInt(int parameterIndex, int x) throws DB_Exception{
		try {
			_stmt.setInt(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setLong(int parameterIndex, long x) throws DB_Exception{
		try {
			_stmt.setLong(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setNull(int parameterIndex, int sqlType) throws DB_Exception{
		try {
			_stmt.setNull(parameterIndex, sqlType);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setNull(int paramIndex, int sqlType, String typeName) throws DB_Exception{
		try {
			_stmt.setNull(paramIndex, sqlType, typeName);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setObject(int parameterIndex, Object x) throws DB_Exception{
		try {
			_stmt.setObject(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws DB_Exception{
		try {
			_stmt.setObject(parameterIndex, x, targetSqlType);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws DB_Exception{
		try {
			_stmt.setObject(parameterIndex, x, targetSqlType, scale);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setRef(int i, Ref x) throws DB_Exception{
		try {
			_stmt.setRef(i, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setShort(int parameterIndex, short x) throws DB_Exception{
		try {
			_stmt.setShort(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setString(int parameterIndex, String x) throws DB_Exception{
		try {
			_stmt.setString(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setTime(int parameterIndex, Time x) throws DB_Exception{
		try {
			_stmt.setTime(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setTime(int parameterIndex, Time x, Calendar cal) throws DB_Exception{
		try {
			_stmt.setTime(parameterIndex, x, cal);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setTimestamp(int parameterIndex, Timestamp x) throws DB_Exception{
		try {
			_stmt.setTimestamp(parameterIndex, x);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws DB_Exception{
		try {
			_stmt.setTimestamp(parameterIndex, x, cal);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}

}
