package com.hokukou.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : DB_Connection.java
	Program Name    : JDBC Connectionカスタムクラス
	Program Date    : 2008/07/05
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/07/05  : 新規作成
	2008/07/05  : setAutoCommit = false に設定(setFetchSizeを有効にするため) = H.Fujimoto
	2008/07/05  : CONCUR_READ_ONLY に設定 = H.Fujimoto
	2008/10/25  : getMetaData を追加 = H.Fujimoto
	2009/05/07  : 接続先DB名を指定できる機能を追加 = T.Ogawa
================================================================================
********************************************************************************
*/
public class DB_Connection{

	Connection conn;

	public final static int MAXCNT = 99999999;
	
	public void getConnection(String dsName) throws DB_Exception {
		DataSource ds = null;
		InitialContext ctx = null;
		
		try{
			ctx = new InitialContext();
			ds=(DataSource)ctx.lookup(dsName);
			conn = ds.getConnection();
		} catch (NamingException e) {
			throw new DB_Exception(e);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	
	public Connection getConnection(){
		return conn;
	}

	/***** 2009/05/07 INSERT START *****/
	public void getConnection(String dsName, String dbName) throws DB_Exception {
		BasicDataSource dsBase = null;
		BasicDataSource dsNew = null;
		InitialContext ctx = null;
		
		try{
			ctx = new InitialContext();
			dsBase = (BasicDataSource)ctx.lookup(dsName);
			
			dsNew = _copyDataSource(dsBase);
			
			if (dbName != null && dbName.equals("") == false) {
				String strURL = dsNew.getUrl();
				int intIdx = strURL.lastIndexOf("/");
				
				if (intIdx > -1) {
					strURL = strURL.substring(0, intIdx);
					strURL = strURL + "/" + dbName;
				} else {
					throw new DB_Exception(new Exception("DBURL(" + strURL + ") IS INVALID."));
				}
				dsNew.setUrl(strURL);
			} else {
				throw new DB_Exception(new Exception("DBNAME IS EMPTY."));
			}
			conn = dsNew.getConnection();
		} catch (NamingException e) {
			throw new DB_Exception(e);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}
	/***** 2009/05/07 INSERT E N D *****/

	/***** 2009/05/07 INSERT START *****/
	private BasicDataSource _copyDataSource(BasicDataSource dsBase) throws SQLException {
		BasicDataSource dsNew = new BasicDataSource();
		
		//dsNew.setAccessToUnderlyingConnectionAllowed(dsBase.getAccessToUnderlyingConnectionAllowed());
		dsNew.setDefaultAutoCommit(dsBase.getDefaultAutoCommit());
		dsNew.setDefaultCatalog(dsBase.getDefaultCatalog());
		dsNew.setDefaultReadOnly(dsBase.getDefaultReadOnly());
		dsNew.setDefaultTransactionIsolation(dsBase.getDefaultTransactionIsolation());
		dsNew.setDriverClassName(dsBase.getDriverClassName());
		dsNew.setInitialSize(dsBase.getInitialSize());
		//dsNew.setLogAbandoned(dsBase.getLogAbandoned());
		//dsNew.setLoginTimeout(dsBase.getLoginTimeout());
		//dsNew.setLogWriter(dsBase.getLogWriter());
		dsNew.setMaxActive(dsBase.getMaxActive());
		dsNew.setMaxIdle(dsBase.getMaxIdle());
		dsNew.setMaxOpenPreparedStatements(dsBase.getMaxOpenPreparedStatements());
		dsNew.setMaxWait(dsBase.getMaxWait());
		dsNew.setMinEvictableIdleTimeMillis(dsBase.getMinEvictableIdleTimeMillis());
		dsNew.setMinIdle(dsBase.getMinIdle());
		dsNew.setNumTestsPerEvictionRun(dsBase.getNumTestsPerEvictionRun());
		dsNew.setPassword(dsBase.getPassword());
		//dsNew.setPoolPreparedStatements(dsBase.getPoolPreparedStatements());
		//dsNew.setRemoveAbandoned(dsBase.getRemoveAbandoned());
		//dsNew.setRemoveAbandonedTimeout(dsBase.getRemoveAbandonedTimeout());
		dsNew.setTestOnBorrow(dsBase.getTestOnBorrow());
		dsNew.setTestOnReturn(dsBase.getTestOnReturn());
		dsNew.setTestWhileIdle(dsBase.getTestWhileIdle());
		dsNew.setTimeBetweenEvictionRunsMillis(dsBase.getTimeBetweenEvictionRunsMillis());
		dsNew.setUrl(dsBase.getUrl());
		dsNew.setUsername(dsBase.getUsername());
		dsNew.setValidationQuery(dsBase.getValidationQuery());
		
		return dsNew;
	}
	/***** 2009/05/07 INSERT E N D *****/
	
	public DB_Statement prepareStatement(String sql) throws DB_Exception {
		DB_Statement stmt = null;
		try {
			/***** 2008/07/04 UPDATE START *****/
			//stmt = new DB_Statement(conn.prepareStatement(sql));
			stmt = new DB_Statement(conn.prepareStatement(sql,
					                                       ResultSet.TYPE_FORWARD_ONLY,
					                                       ResultSet.CONCUR_READ_ONLY));
			/***** 2008/07/04 UPDATE E N D *****/
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
		return stmt;
	}

	public void setAutoCommit(boolean autoCommit) throws DB_Exception {
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}  

	public DatabaseMetaData getMetaData() throws DB_Exception {
		DatabaseMetaData dmd = null;
		try {
			dmd = conn.getMetaData();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
		return dmd; 
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			// Nothing
		}
	}    
	public void commit() throws DB_Exception {
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new DB_Exception(e);
		}
	}    
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// Nothing
		}
	}

	public Timestamp getNow() {
		return new Timestamp(System.currentTimeMillis());
	}

}
