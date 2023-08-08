package hkc;

import java.util.*;
//import java.io.*;
import java.sql.ResultSetMetaData;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;

//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;

import com.hokukou.HK_Cnst;
import com.hokukou.database.*;
import com.project.PJ_Cnst;
//import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

//import org.omg.CORBA.portable.Delegate;
import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;
import org.seasar.framework.util.StringUtil;

//import sun.font.DelegatingShape;
@RemotingService

/*
********************************************************************************
================================================================================
                             ホクコウ共通プログラム
================================================================================
	Program Number  : HKC31400.java
   	Program Name    : ビュー作成ツール
   	Program Date    : 2009/04/01
   	Programmer      : Baondp
   
===< Update History >===========================================================
  2009/04/01 : Create
================================================================================
********************************************************************************
*/

public class HKC31400 {

	/*
	********************************************************************************
	*** Get all tables and column on the tables
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public ArrayList readAllTables_Columns(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;

		Boolean blnIsRead = false;
		
		StringBuffer stbSql = new StringBuffer();

		try {
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			stbSql.append("\n"+"SELECT");
			stbSql.append("\n"+"	t.tablename,");
			stbSql.append("\n"+"	a.attname AS 'fieldname'");
			stbSql.append("\n"+"FROM");
			stbSql.append("\n"+"	pg_tables t,");
			stbSql.append("\n"+"	pg_catalog.pg_attribute a");
			stbSql.append("\n"+"WHERE");
			stbSql.append("\n"+"	a.attnum > 0");
			stbSql.append("\n"+"	AND NOT a.attisdropped");
			stbSql.append("\n"+"	AND a.attrelid = (");
			stbSql.append("\n"+"    	SELECT");
			stbSql.append("\n"+"    		c.oid");
			stbSql.append("\n"+"    	FROM");
			stbSql.append("\n"+"    		pg_catalog.pg_class c");
			stbSql.append("\n"+"    		LEFT JOIN pg_catalog.pg_namespace n");
			stbSql.append("\n"+"    			ON   n.oid = c.relnamespace");
			stbSql.append("\n"+"    	WHERE");
			stbSql.append("\n"+"    		c.relname = t.tablename");
			stbSql.append("\n"+"    		AND pg_catalog.pg_table_is_visible (c.oid)");
			stbSql.append("\n"+"    )");
			stbSql.append("\n"+"	AND t.tablename NOT LIKE 'pg%'");
			stbSql.append("\n"+"	AND t.tablename NOT LIKE 'sql%'");

			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			
			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
						
			while (dbr.next()) {
				blnIsRead = true;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("tablename", dbr.getString("tablename"));
				map.put("fieldname", dbr.getString("fieldname"));
				aryDBData.add(map);
			}

			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	
	/*
	********************************************************************************
	*** Get all tables.
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public ArrayList readAllTables(Object[] objInputData){ 
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;

		Boolean blnIsRead = false;
		
		try {
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			
			dbs = dbc.prepareStatement(getStringQueryAllTable());
			dbr = dbs.executeQuery();
						
			while (dbr.next()) {
				blnIsRead = true;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("tablename", dbr.getString("tablename"));
				map.put("description", dbr.getString("description"));
				aryDBData.add(map);
			}

			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	
	private String getStringQueryAllTable()
	{
		StringBuffer stbSql = new StringBuffer();
		stbSql.append("\n"+"SELECT ");
		stbSql.append("\n"+"     pg_tables.tablename, ");
		stbSql.append("\n"+"     pg_description.description");
		stbSql.append("\n"+"FROM ");
		stbSql.append("\n"+"     (pg_tables LEFT JOIN pg_class ON pg_tables.tablename =  pg_class.relname) LEFT JOIN pg_description ON pg_class.oid = pg_description.objoid");
		stbSql.append("\n"+"WHERE ");
		stbSql.append("\n"+"     pg_tables.tablename NOT LIKE 'pg%'");
		stbSql.append("\n"+" AND pg_tables.tablename NOT LIKE 'sql%'");
		stbSql.append("\n"+" AND pg_description.objsubid = '0'");
		stbSql.append("\n"+"ORDER BY ");
		stbSql.append("\n"+"	pg_tables.tablename");
		stbSql.append("\n"+";");
		return stbSql.toString();
	}
	
	/*
	********************************************************************************
	*** Get a table relationship of database 
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public ArrayList readTableRelationshipOfDatabase(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		//HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		Boolean blnIsRead = false;
		StringBuffer stbSql = new StringBuffer();

		try {
			//hsmReceiveData = (HashMap)objInputData[0];
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			
			stbSql.append("\n"+"SELECT ");
			stbSql.append("\n"+"     tblAll.table_name as primaryTableName,");
			stbSql.append("\n"+"     tblAll.column_name as primaryTableColumn,");
			stbSql.append("\n"+"     tblFK.table_name as foreignKeyTable,");
			stbSql.append("\n"+"     tblFK.column_name as foreignKeyColumn");
			stbSql.append("\n"+"FROM ");
			stbSql.append("\n"+"     information_schema.key_column_usage tblAll");
			stbSql.append("\n"+"  INNER JOIN information_schema.referential_constraints tblAllFK");
			stbSql.append("\n"+"     ON tblAllFK.unique_constraint_name = tblAll.constraint_name");
			stbSql.append("\n"+"  INNER JOIN information_schema.key_column_usage tblFK");
			stbSql.append("\n"+	"    ON tblAllFK.constraint_name = tblFK.constraint_name");
			stbSql.append("\n"+"ORDER BY ");
			stbSql.append("\n"+"     primaryTableName,");
			stbSql.append("\n"+"     primaryTableColumn ");
			stbSql.append("\n"+";");
			
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			
			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
						
			while (dbr.next()) {
				blnIsRead = true;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("primaryTableName", dbr.getString("primaryTableName"));
				map.put("primaryTableColumn", dbr.getString("primaryTableColumn"));
				map.put("foreignKeyTable", dbr.getString("foreignKeyTable"));
				map.put("foreignKeyColumn", dbr.getString("foreignKeyColumn"));
				aryDBData.add(map);
			}

			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	/*
	********************************************************************************
	*** Get a table and column on this
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public ArrayList readTable_Columns(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		ArrayList<ArrayList> aryViewRet = new ArrayList<ArrayList>();
		ArrayList<ArrayList> aryTableCol = new ArrayList<ArrayList>();
		
		String[] arrTableName;
		String strTablename = "";
		Boolean blnIsRead = false;
		@SuppressWarnings("unused")
		String strViewName = "";
		
		StringBuffer stbSql = new StringBuffer();

		try {
			hsmReceiveData = (HashMap)objInputData[0];
			strViewName = (String)hsmReceiveData.get("a02_viewname");
			arrTableName = StringUtil.split((String)hsmReceiveData.get("tablename"),",");
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
	
			stbSql.append("\n"+"SELECT");
			stbSql.append("\n"+"	'{0}' as tablename,");
			stbSql.append("\n"+"	a.attname AS fieldname,");
			stbSql.append("\n"+"	d.description");
			stbSql.append("\n"+"FROM");
			stbSql.append("\n"+"	pg_catalog.pg_attribute a LEFT JOIN pg_description d");
			stbSql.append("\n"+"	ON a.attrelid = d.objoid AND a.attnum =d.objsubid");
			stbSql.append("\n"+"WHERE");
			stbSql.append("\n"+"	a.attnum > 0");
			stbSql.append("\n"+"	AND NOT a.attisdropped");
			stbSql.append("\n"+"	AND a.attrelid = (");
			stbSql.append("\n"+"    	SELECT");
			stbSql.append("\n"+"    		c.oid");
			stbSql.append("\n"+"    	FROM");
			stbSql.append("\n"+"    		pg_catalog.pg_class c");
			stbSql.append("\n"+"    		LEFT JOIN pg_catalog.pg_namespace n");
			stbSql.append("\n"+"    			ON   n.oid = c.relnamespace");
			stbSql.append("\n"+"    	WHERE");
			stbSql.append("\n"+"    		c.relname = '{0}" + "'");
			stbSql.append("\n"+"    		AND pg_catalog.pg_table_is_visible (c.oid)");
			stbSql.append("\n"+"    )");
			
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			String strSQL="";
			for(int intIdx = 0; intIdx < arrTableName.length; intIdx ++){
				strTablename = arrTableName[intIdx];
				strSQL = stbSql.toString().replace("{0}", strTablename);				
				dbs = dbc.prepareStatement(strSQL);
				dbr = dbs.executeQuery();							
				while (dbr.next()) {
					blnIsRead = true;
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("tablename", dbr.getString("tablename"));
					map.put("fieldname", dbr.getString("fieldname"));
					map.put("description", dbr.getString("description"));
					aryDBData.add(map);
				}
				if(dbr != null){ dbr.close(); dbr = null; }
				if(dbs != null){ dbs.close(); dbs = null;}
			}
			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
				/*if(strViewName != null && strViewName != ""){
					aryViewRet = getViewName(objInputData);
					String tmpTbl = "";
					String[] arrTmpTbl;
					String[] arrTables = aryViewRet.get(1).toString().split("=")[1].toString().split(";");
					for(int i = 0; i < arrTables.length; i++){
						tmpTbl += arrTables[i].split("~")[0].toString() + ",";
					}
					arrTmpTbl = tmpTbl.substring(0, tmpTbl.length() - 1).split(",");
					aryTableCol = readTableColums(arrTmpTbl);
				}	*/
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		aryViewRet.trimToSize();
		aryTableCol.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		/*arySendData.add(aryViewRet);
		arySendData.add(aryTableCol);*/
		arySendData.trimToSize();
		return arySendData;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList readTable_Columns2(Object[] objInputData){
		HashMap hsmReceiveData;
		ArrayList<ArrayList> aryViewRet = new ArrayList<ArrayList>();
		ArrayList<ArrayList> aryTableCol = new ArrayList<ArrayList>();		
		@SuppressWarnings("unused")
		String strTablename = "";
		@SuppressWarnings("unused")
		Boolean blnIsRead = false;
		String strViewName = "";
		ArrayList<ArrayList> aryRead1 = new ArrayList<ArrayList>();
		
		try {
			hsmReceiveData = (HashMap)objInputData[0];
			strViewName = (String)hsmReceiveData.get("a02_viewname");
			
			String tmpTbl = "";	
			
			String[] tmpArr = new String[0];
			tmpArr = StringUtil.split((String)hsmReceiveData.get("tablename"),",");
			String tablesStr = "";
			for(int k=0; k<tmpArr.length; k++){
				tablesStr += tmpArr[k].toString().split("\"")[0].toString() + ",";
			}
			
			HashMap<String, String> o2 = new HashMap<String, String>();
			o2.put("tablename", tablesStr.substring(0, tablesStr.length() - 1));
			o2.put("a02_viewname", "");				
			Object[] ob2 = new Object[1];
			ob2[0] = o2;
			
			aryRead1 = readTable_Columns(ob2);			
							
			if(strViewName != null && strViewName != ""){
				aryViewRet = getViewName(objInputData);
				String[] arrTables = aryViewRet.get(1).toString().split("=")[1].toString().split(";");
				for(int i = 0; i < arrTables.length; i++){
					tmpTbl += arrTables[i].split("~")[0].toString().split("\"")[0] + ",";
				}
				HashMap<String, String> o = new HashMap<String, String>();
				o.put("tablename", tmpTbl.substring(0, tmpTbl.length() - 1));
				o.put("a02_viewname", "");				
				Object[] ob = new Object[1];
				ob[0] = o;
				aryTableCol = readTable_Columns(ob);
			}			
		} catch (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} 
		
		aryViewRet.trimToSize();
		aryTableCol.trimToSize();
		aryRead1.add(aryViewRet);
		aryRead1.add(aryTableCol);
		aryRead1.trimToSize();
		
		return aryRead1;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList readTableColums(String[] tableName){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		Boolean blnIsRead = false;
		StringBuffer stbSql = new StringBuffer();

		try {			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);			
			String arrTablesSQL = "'";
			
			for(int i = 0; i < tableName.length; i++){
				arrTablesSQL += tableName[i].toString() + "','";
			}
			
			arrTablesSQL = arrTablesSQL.substring(0, arrTablesSQL.length() - 2);
			
			stbSql.append("\n"+"SELECT ");
			stbSql.append("\n"+"     col.table_name as tables,");
			stbSql.append("\n"+"     col.column_name as columns");
			stbSql.append("\n"+"FROM ");
			stbSql.append("\n"+"     information_schema.columns col");
			stbSql.append("\n"+"WHERE col.table_schema = 'public' and col.table_name in (" + arrTablesSQL + ")");
			stbSql.append("\n"+"ORDER BY ");
			stbSql.append("\n"+"     col.table_name");
			stbSql.append("\n"+";");
			
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			
			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
						
			while (dbr.next()) {
				blnIsRead = true;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("tables", dbr.getString("tables"));
				map.put("columns", dbr.getString("columns"));
				aryDBData.add(map);
			}

			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	
	/*
	********************************************************************************
	*** Get a table and column on this
	********************************************************************************
	*/
	
	@SuppressWarnings("unchecked")
	public ArrayList readDataFromSql(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		
		HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		ArrayList<HashMap> aryColumnDescription = null;
		ArrayList<String> aryColumns = null;
		HashMap<String, String> hsmRow = null;
		
		String strsql = "";
		//Object[] columns;
		Boolean blnIsRead = false;
		
		StringBuffer stbSql = new StringBuffer();

		try {
			hsmReceiveData = (HashMap)objInputData[0];
			strsql = (String)hsmReceiveData.get("sql");
			//columns = (Object[])hsmReceiveData.get("columns");
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
			stbSql.append(strsql);

			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			aryColumnDescription = new ArrayList<HashMap>();
			
			dbs = dbc.prepareStatement(stbSql.toString());
			dbr = dbs.executeQuery();
				
			//get column list 
			//aryColumns = new ArrayList();
			ResultSetMetaData rsMetaData = dbr.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();

		    // get the column names; column indexes start from 1
			aryColumns = new ArrayList<String>();			// <-----------* 山本 Insert
		    for (int i = 1; i < numberOfColumns + 1; i++) {
		      String columnName = rsMetaData.getColumnName(i);
		      aryColumns.add(columnName);
		      // Get the name of the column's table name
		      //String tableName = rsMetaData.getTableName(i);
		    }
			
			while (dbr.next()) {
				blnIsRead = true;
				HashMap<String, String> map = new HashMap<String, String>();
				for (String str : aryColumns) 
				{
					map.put(str, dbr.getString(str));	
				}
				aryDBData.add(map);
			}
			//Close the resultset and statement
			if(dbr != null){ dbr.close(); dbr = null; }
			if(dbs != null){ dbs.close(); dbs = null; }

			//get table name
			dbs = dbc.prepareStatement(getStringQueryAllTable());
			dbr = dbs.executeQuery();
			ArrayList<String> aryTmp = new ArrayList<String>();
			while (dbr.next()) {
				if (strsql.indexOf(dbr.getString("tablename"))>=0)
				{
					aryTmp.add(dbr.getString("tablename"));
				}
			}
			//Close the resultset and statement
			if(dbr != null){ dbr.close(); dbr = null; }
			if(dbs != null){ dbs.close(); dbs = null; }
			
			if(aryTmp.size() >0)
			{
				for (int j = 0;j<aryTmp.size();j++)
				{
					//get column name
					dbs = dbc.prepareStatement(getStringQueryColumn(aryTmp.get(j)));
					dbr = dbs.executeQuery();
					
					while (dbr.next()) {
							
						HashMap<String, String> map1 = new HashMap<String, String>();
						map1.put("fieldname", dbr.getString("fieldname"));	
						map1.put("description", dbr.getString("description"));
						aryColumnDescription.add(map1);
					}
					
					if(dbr != null){ dbr.close(); dbr = null; }
					if(dbs != null){ dbs.close(); dbs = null; }
				}	
			}
			
			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.add(aryColumns);
		arySendData.add(aryColumnDescription);
		arySendData.trimToSize();
		return arySendData;
	}
	
	private String getStringQueryColumn(String strTable)
	{
		StringBuffer stbSql = new StringBuffer();
		stbSql.append("\n"+"SELECT");
		stbSql.append("\n"+"	a.attname AS fieldname,");
		stbSql.append("\n"+"	d.description");
		stbSql.append("\n"+"FROM");
		stbSql.append("\n"+"	pg_catalog.pg_attribute a LEFT JOIN pg_description d");
		stbSql.append("\n"+"	ON a.attrelid = d.objoid AND a.attnum =d.objsubid");
		stbSql.append("\n"+"WHERE");
		stbSql.append("\n"+"	a.attnum > 0");
		stbSql.append("\n"+"	AND NOT a.attisdropped");
		stbSql.append("\n"+"	AND a.attrelid = (");
		stbSql.append("\n"+"    	SELECT");
		stbSql.append("\n"+"    		c.oid");
		stbSql.append("\n"+"    	FROM");
		stbSql.append("\n"+"    		pg_catalog.pg_class c");
		stbSql.append("\n"+"    		LEFT JOIN pg_catalog.pg_namespace n");
		stbSql.append("\n"+"    			ON   n.oid = c.relnamespace");
		stbSql.append("\n"+"    	WHERE");
		stbSql.append("\n"+"    		c.relname = '" + strTable + "'");
		stbSql.append("\n"+"    		AND pg_catalog.pg_table_is_visible (c.oid)");
		stbSql.append("\n"+"    )");
		return stbSql.toString();
	}
	
	/*
	********************************************************************************
	*** Get a information from View Table
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public ArrayList readViewList(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		//HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		//String[] arrTableName;
		String strTablename = "";
		Boolean blnIsRead = false;
		
		StringBuffer stbSql = new StringBuffer();

		try {
			//hsmReceiveData = (HashMap)objInputData[0];
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
		
			stbSql.append("\n"+"SELECT");
			stbSql.append("\n"+"	a02.A02_ViewName,");
			stbSql.append("\n"+"	a02.A02_Tables,");
			stbSql.append("\n"+"	a02.A02_Relations,");
			stbSql.append("\n"+"	a02.A02_Columns");
			stbSql.append("\n"+"FROM");
			stbSql.append("\n"+"	A02_ViewInfo a02");
			
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			String strSQL="";
	
			strSQL = stbSql.toString();				
			dbs = dbc.prepareStatement(strSQL);
			dbr = dbs.executeQuery();							
			while (dbr.next()) {
				blnIsRead = true;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("A02_ViewName", dbr.getString("A02_ViewName"));
				map.put("A02_Tables", dbr.getString("A02_Tables"));
				map.put("A02_Relations", dbr.getString("A02_Relations"));
				map.put("A02_Columns", dbr.getString("A02_Columns"));
				aryDBData.add(map);
			}
			
			if(dbr != null){ dbr.close(); dbr = null; }
			if(dbs != null){ dbs.close(); dbs = null; }
			
			//get allTable of database
			dbs = dbc.prepareStatement(getStringQueryAllTable());
			dbr = dbs.executeQuery();
			String strTable = "";
			while (dbr.next()) 
			{
				if (strSQL.indexOf(dbr.getString("tablename")) >=0)
				{
					strTable += dbr.getString("tablename") + ",";
				}
			}
			if (strTable != "")
			{
				strTable = "'" + strTable.substring(0,strTablename.length() - 1).replace("'", "','") + "'";
			}
		
			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	
	/*
	********************************************************************************
	*** check Exist View Name from View Table
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public int checkExistViewName(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		HashMap hsmReceiveData;
		
		StringBuffer stbSql = new StringBuffer();
		try {
			hsmReceiveData = (HashMap)objInputData[0];
			String strViewName = (String)hsmReceiveData.get("a02_viewname");
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
		
			stbSql.append("\n"+"SELECT");
			stbSql.append("\n"+"	a02.A02_ViewName");
			stbSql.append("\n"+"FROM");
			stbSql.append("\n"+"	A02_ViewInfo a02");
			stbSql.append("\n"+"WHERE");
			stbSql.append("\n"+"	A02_ViewName = '" + strViewName + "'");
			
			String strSQL="";
			strSQL = stbSql.toString();				
			dbs = dbc.prepareStatement(strSQL);
			dbr = dbs.executeQuery();
			while (dbr.next()) {
				return 1;
			}
		} catch  (DB_Exception e) {
			return -1;
		} catch   (Exception e) {
			return -1;
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		return 0;
	}
	
	//xungnv added this function
	@SuppressWarnings("unchecked")
	public ArrayList getViewName(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;
		Boolean blnIsRead = false;
		
		HashMap hsmReceiveData = null;
		ArrayList<ArrayList> arySendData = new ArrayList<ArrayList>();
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		ArrayList<HashMap> aryStatus = new ArrayList<HashMap>();
		
		StringBuffer stbSql = new StringBuffer();
		try {
			hsmReceiveData = (HashMap)objInputData[0];
			String strViewName = (String)hsmReceiveData.get("a02_viewname");
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
		
			stbSql.append("\n"+"SELECT");
			stbSql.append("\n"+"	a19.*");
			stbSql.append("\n"+"FROM");
			stbSql.append("\n"+"	A19_ViewInfo a19");
			stbSql.append("\n"+"WHERE");
			stbSql.append("\n"+"	a19.A19_ViewNM = '" + strViewName + "'");
			
			String strSQL="";
			String allTables = "";
			strSQL = stbSql.toString();				
			dbs = dbc.prepareStatement(strSQL);
			dbr = dbs.executeQuery();
			while (dbr.next()) {
				blnIsRead = true;
				allTables += dbr.getString("A19_TableNM") + "~" + 
							dbr.getString("A19_WinPosi_X") + ":" +
							dbr.getString("A19_WinPosi_Y") + "$" +
							dbr.getString("A19_WinSize_W") + ":" +
							dbr.getString("A19_WinSize_H") + ";";				
			}
			
			if (blnIsRead == true) {				
				allTables = allTables.substring(0, allTables.length() - 1);				
				
				aryDBData = new ArrayList<HashMap>();
				HashMap<String, String> map = new HashMap<String, String>();				
				map.put("a02_tables", allTables);
				aryDBData.add(map);
				
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return null;
		} catch   (Exception e) {
			return null;
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
		}
		
		aryDBData.trimToSize();
		aryStatus.trimToSize();		
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);		
		arySendData.trimToSize();
		
		return arySendData;
	}
	
	/*
	********************************************************************************
	*** save view information 
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public int saveViewInfo(Object[] objInputData) throws Exception {
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		int intResult;		
		HashMap hsmReceiveData;
		String strViewName = "";
		String strTables = "";
		//String strRelations = "";
		//String strColumns = "";
		String strUser = "";
		String strTableName = "";
		String strX = "";
		String strY = "";
		String strW = "";
		String strH = "";
		String sizes = "";
		String positions = "";
		String strTerm = "";
		//Boolean blnIsRead = false;
		
		StringBuffer stbSql;
		
		dbc = new DB_Connection();
		dbc.getConnection(PJ_Cnst.CNT_INFO);
		//dbc.setAutoCommit(false); // Transaction Start !
		
		hsmReceiveData = (HashMap)objInputData[0];
		strViewName = (String)hsmReceiveData.get("a02_viewname");
		strTables = (String)hsmReceiveData.get("a02_tables");
		//strRelations = (String)hsmReceiveData.get("a02_relations");
		//strColumns = (String)hsmReceiveData.get("a02_columns");
		strUser = (String)hsmReceiveData.get("C04_CD");
		strTerm = (String)hsmReceiveData.get("Term") == null ? "" : (String)hsmReceiveData.get("Term");
	
		String[] tables = strTables.split(";");
		String tmp = "";
				
		for(int i=0;i<tables.length;i++){
			
			strTableName = tables[i].split("~")[0];			
			
			tmp = tables[i].split("~")[1].toString();
			
			positions = StringUtil.split(tmp, "$")[0].toString();
			sizes = StringUtil.split(tmp, "$")[1].toString();
			
			strX = positions.split(":")[0];
			strY = positions.split(":")[1];
			
			strW = sizes.split(":")[0];
			strH = sizes.split(":")[1];
			
			stbSql = new StringBuffer();
			
			stbSql.append("\n"+"INSERT INTO");
			stbSql.append("\n"+"	A19_ViewInfo");
			stbSql.append("\n"+"	(Create_Date,");
			stbSql.append("\n"+"	Create_Term,");
			stbSql.append("\n"+"	Create_User,");
			stbSql.append("\n"+"	Update_Date,");
			stbSql.append("\n"+"	Update_Term,");
			stbSql.append("\n"+"	Update_User,");
			stbSql.append("\n"+"	A19_UserCD,");
			stbSql.append("\n"+"	A19_ViewNM,");
			stbSql.append("\n"+"	A19_TableNM,");
			stbSql.append("\n"+"	A19_SEQ,");
			stbSql.append("\n"+"	A19_WinPosi_X,");
			stbSql.append("\n"+"	A19_WinPosi_Y,");
			stbSql.append("\n"+"	A19_WinSize_W,");
			stbSql.append("\n"+"	A19_WinSize_H)");
			stbSql.append("\n"+"VALUES ");
			stbSql.append("\n"+"	('" + dbc.getNow().toString() + "'" );
			stbSql.append("\n"+"	,'" + strTerm+ "'" );
			stbSql.append("\n"+"	,'" + strUser + "'" );
			stbSql.append("\n"+"	,'" + dbc.getNow().toString() + "'" );
			stbSql.append("\n"+"	,'" + strTerm + "'" );
			stbSql.append("\n"+"	,'" + strUser + "'" );
			stbSql.append("\n"+"	,'" + strUser + "'" );
			stbSql.append("\n"+"	,'" + strViewName + "'" );
			stbSql.append("\n"+"	,'" + strTableName + "'" );
			stbSql.append("\n"+"	,'1'" );
			stbSql.append("\n"+"	,'" + strX + "'" );
			stbSql.append("\n"+"	,'" + strY + "'" );
			stbSql.append("\n"+"	,'" + strW + "'" );
			stbSql.append("\n"+"	,'" + strH + "')" );
			
			try {
				dbs = dbc.prepareStatement(stbSql.toString());
				intResult = dbs.executeUpdate();

			}catch (DB_Exception e) {
				dbc.rollback();
				throw e;
			}catch (Exception e) {
				dbc.rollback();
				throw e;
			}
			finally{
				if(dbs != null){
					dbs.close();
					dbs = null;
				}
			}
			if(intResult == 0){
				return -1;
			}
		}
		
		if(dbc != null){
			dbc.close();
			dbc = null;
		}
		
		return 0;	
	}
	
	//update view info
	@SuppressWarnings("unchecked")
	public int updateViewInfo(Object[] objInputData) throws Exception {
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		int intResult;
		HashMap hsmReceiveData;
		//String strViewName = "";
		String strOldViewName = "";
		String strTables = "";
		//String strRelations = "";
		//String strColumns = "";
		String strUser = "";
		String strTableName = "";
		String strX = "";
		String strY = "";
		String strW = "";
		String strH = "";
		String sizes = "";
		String positions = "";
		String strTerm = "";
		//Boolean blnIsRead = false;
		
		StringBuffer stbSql;
		
		dbc = new DB_Connection();
		dbc.getConnection(PJ_Cnst.CNT_INFO);
		//dbc.setAutoCommit(false); // Transaction Start !
		
		hsmReceiveData = (HashMap)objInputData[0];
		//strViewName = (String)hsmReceiveData.get("a02_viewname");
		strTables = (String)hsmReceiveData.get("a02_tables");
		//strRelations = (String)hsmReceiveData.get("a02_relations");
		//strColumns = (String)hsmReceiveData.get("a02_columns");
		strOldViewName = (String)hsmReceiveData.get("a02_oldviewname");
		strUser = (String)hsmReceiveData.get("C04_CD");
		strTerm = (String)hsmReceiveData.get("Term") == null ? "" : (String)hsmReceiveData.get("Term");
		
		String[] tables = strTables.split(";");
		
		String tmp = "";
		
		//Begin delete old tables in previous view
		String tmpSQLTable = "";
		stbSql = new StringBuffer();
		for(int j=0;j<tables.length;j++){
			tmpSQLTable += "'" + StringUtil.split(tables[j].toString(), "~")[0] + "', ";
		}
		
		tmpSQLTable = tmpSQLTable.substring(0, tmpSQLTable.length() - 2);
		
		stbSql.append("\n"+"DELETE FROM ");
		stbSql.append("\n"+"	A19_ViewInfo");
		stbSql.append("\n"+"WHERE ");
		stbSql.append("\n"+"	A19_ViewNM = '" + strOldViewName + "'");
		stbSql.append("\n"+"	AND");
		stbSql.append("\n"+"	A19_TableNM NOT IN (" + tmpSQLTable + ")");
		
		try {
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();

		} catch (DB_Exception e) {
			dbc.rollback();
			throw e;
		} catch (Exception e) {
			dbc.rollback();
			throw e;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }			
		}
				
		//Begin updates tables in view
		for(int i=0;i<tables.length;i++){
			
			strTableName = tables[i].split("~")[0];			
			
			tmp = tables[i].split("~")[1].toString();
			
			positions = StringUtil.split(tmp, "$")[0].toString();			
			sizes = StringUtil.split(tmp, "$")[1].toString();
			
			strX = positions.split(":")[0];
			strY = positions.split(":")[1];
			
			strW = sizes.split(":")[0];
			strH = sizes.split(":")[1];
			
			stbSql  = new StringBuffer();
			
			stbSql.append("\n"+"UPDATE ");
			stbSql.append("\n"+"	A19_ViewInfo");
			stbSql.append("\n"+"SET ");
			stbSql.append("\n"+"	Update_Date = '" + dbc.getNow().toString() + "',");
			stbSql.append("\n"+"	Update_Term = '" + strTerm + "',");
			stbSql.append("\n"+"	Update_User = '" + strUser + "',");
			stbSql.append("\n"+"	A19_WinPosi_X = '" + strX + "',");
			stbSql.append("\n"+"	A19_WinPosi_Y = '" + strY + "',");
			stbSql.append("\n"+"	A19_WinSize_W = '" + strW + "',");
			stbSql.append("\n"+"	A19_WinSize_H = '" + strH + "'");
			stbSql.append("\n"+"WHERE ");
			stbSql.append("\n"+"	A19_ViewNM = '" + strOldViewName + "'");
			stbSql.append("\n"+"	AND");
			stbSql.append("\n"+"	A19_TableNM = '" + strTableName + "'");
				
			try {
				dbs = dbc.prepareStatement(stbSql.toString());
				intResult = dbs.executeUpdate();
				
				if(dbs != null){ dbs.close(); dbs = null; }
				
				if(intResult == 0){//New table is added to View
					stbSql = new StringBuffer();
					
					stbSql.append("\n"+"INSERT INTO");
					stbSql.append("\n"+"	A19_ViewInfo");
					stbSql.append("\n"+"	(Create_Date,");
					stbSql.append("\n"+"	Create_Term,");
					stbSql.append("\n"+"	Create_User,");
					stbSql.append("\n"+"	Update_Date,");
					stbSql.append("\n"+"	Update_Term,");
					stbSql.append("\n"+"	Update_User,");
					stbSql.append("\n"+"	A19_UserCD,");
					stbSql.append("\n"+"	A19_ViewNM,");
					stbSql.append("\n"+"	A19_TableNM,");
					stbSql.append("\n"+"	A19_SEQ,");
					stbSql.append("\n"+"	A19_WinPosi_X,");
					stbSql.append("\n"+"	A19_WinPosi_Y,");
					stbSql.append("\n"+"	A19_WinSize_W,");
					stbSql.append("\n"+"	A19_WinSize_H)");
					stbSql.append("\n"+"VALUES ");
					stbSql.append("\n"+"	('" + dbc.getNow().toString() + "'" );
					stbSql.append("\n"+"	,'" + strTerm + "'" );
					stbSql.append("\n"+"	,'" + strUser + "'" );
					stbSql.append("\n"+"	,'" + dbc.getNow().toString() + "'" );
					stbSql.append("\n"+"	,'" + strTerm + "'" );
					stbSql.append("\n"+"	,'" + strUser + "'" );
					stbSql.append("\n"+"	,'" + strUser + "'" );
					stbSql.append("\n"+"	,'" + strOldViewName + "'" );
					stbSql.append("\n"+"	,'" + strTableName + "'" );
					stbSql.append("\n"+"	,'1'" );
					stbSql.append("\n"+"	,'" + strX + "'" );
					stbSql.append("\n"+"	,'" + strY + "'" );
					stbSql.append("\n"+"	,'" + strW + "'" );
					stbSql.append("\n"+"	,'" + strH + "')" );
					
					try {
						dbs = dbc.prepareStatement(stbSql.toString());
						intResult = dbs.executeUpdate();

					} catch (DB_Exception e) {
						dbc.rollback();						
						throw e;
					} catch (Exception e) {
						dbc.rollback();						
						throw e;
					} finally {
						if (dbs != null){ dbs.close();  dbs = null; }						
					}					
				}

			} catch (DB_Exception e) {
				dbc.rollback();
				throw e;
			} catch (Exception e) {
				dbc.rollback();
				throw e;
			} finally {				
				if (dbs != null){ dbs.close();  dbs = null; }				
			}
			if (intResult == 0) {
				return -1;
			}
		}
		if(dbc != null) {dbc.close(); dbc = null; }
		
		return 0;	
	}
	
	/*
	********************************************************************************
	*** delete view information 
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public int deleteViewInfo(Object[] objInputData) throws Exception {
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		int intResult;
		HashMap hsmReceiveData;
		String strViewName = "";
		//Boolean blnIsRead = false;		
		
		StringBuffer stbSql = new StringBuffer();
		
		dbc = new DB_Connection();
		dbc.getConnection(PJ_Cnst.CNT_INFO);
		//dbc.setAutoCommit(false); // Transaction Start !
		
		hsmReceiveData = (HashMap)objInputData[0];
		strViewName = (String)hsmReceiveData.get("viewname");
		strViewName ="'" +  strViewName.replace(";", "','") + "'";
		
		stbSql.append("\n"+"DELETE FROM ");
		stbSql.append("\n"+"	A19_ViewInfo");
		stbSql.append("\n"+"WHERE ");
		stbSql.append("\n"+"	A19_ViewNM IN (" + strViewName +")");
			
		try {
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();

		} catch (DB_Exception e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null) { dbc.close(); dbc = null; }
		}
		if (intResult == 0) {
			return -1;
		}
		return 0;	
	}
	
	/*
	********************************************************************************
	*** Get a information from View Query SQL
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public ArrayList readViewSql(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		//HashMap hsmReceiveData;
		ArrayList<ArrayList> arySendData = null;
		ArrayList<HashMap> aryStatus = null;
		ArrayList<HashMap> aryDBData = null;
		HashMap<String, String> hsmRow = null;
		
		//String[] arrTableName;
		//String strTablename = "";
		Boolean blnIsRead = false;
		
		StringBuffer stbSql = new StringBuffer();

		try {
			//hsmReceiveData = (HashMap)objInputData[0];
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
		
			stbSql.append("\n"+"SELECT");
			stbSql.append("\n"+"	viewname,");
			stbSql.append("\n"+"	definition");
			stbSql.append("\n"+"FROM");
			stbSql.append("\n"+"	pg_views");
			stbSql.append("\n"+"WHERE");
			stbSql.append("\n"+"	schemaname = 'public'");
			
			arySendData = new ArrayList<ArrayList>();
			aryStatus = new ArrayList<HashMap>();
			aryDBData = new ArrayList<HashMap>();
			String strSQL="";
	
			strSQL = stbSql.toString();				
			dbs = dbc.prepareStatement(strSQL);
			dbr = dbs.executeQuery();							
			while (dbr.next()) {
				blnIsRead = true;
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("viewname", dbr.getString("viewname"));
				map.put("definition", dbr.getString("definition"));
				aryDBData.add(map);
			}
		
			if (blnIsRead == true) {
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS, HK_Cnst.RESULT_EXISTDATA);
				aryStatus.add(hsmRow);
			}else{
				hsmRow = new HashMap<String, String>();
				hsmRow.put(HK_Cnst.RESULT_STATUS,HK_Cnst.RESULT_NODATA);
				hsmRow.put(HK_Cnst.RESULT_INFO, "該当するデータは､ありません。");
				aryStatus.add(hsmRow);
			}
		} catch  (DB_Exception e) {
			return DB_Exception.setErrorInfo(e);
		} catch   (Exception e) {
			return DB_Exception.setErrorInfo(e);
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }
			
		}
		aryStatus.trimToSize();
		aryDBData.trimToSize();
		arySendData.add(aryStatus);
		arySendData.add(aryDBData);
		arySendData.trimToSize();
		return arySendData;
	}
	
	@SuppressWarnings("unchecked")
	public int checkExistViewNameSql(Object[] objInputData){
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		DB_ResultSet dbr = null;

		HashMap hsmReceiveData;
		
		StringBuffer stbSql = new StringBuffer();
		try {
			hsmReceiveData = (HashMap)objInputData[0];
			String strViewName = (String)hsmReceiveData.get("viewname");
			
			dbc = new DB_Connection();
			dbc.getConnection(PJ_Cnst.CNT_INFO);
		
			stbSql.append("\n"+"SELECT");
			stbSql.append("\n"+"	viewname");
			stbSql.append("\n"+"FROM");
			stbSql.append("\n"+"	pg_views ");
			stbSql.append("\n"+"WHERE");
			stbSql.append("\n"+"	viewname = '" + strViewName + "'");
			stbSql.append("\n"+"AND");
			stbSql.append("\n"+"	schemaname = 'public'");
			
			String strSQL="";
			strSQL = stbSql.toString();				
			dbs = dbc.prepareStatement(strSQL);
			dbr = dbs.executeQuery();
			while (dbr.next()) {
				return 1;
			}
		} catch  (DB_Exception e) {			
			return -1;
		} catch   (Exception e) {
			return -1;
		} finally{
			if (dbr != null){ dbr.close();  dbr = null; }
			if (dbs != null){ dbs.close();  dbs = null; }
			if (dbc != null){ dbc.close();  dbc = null; }			
		}
		return 0;
	}
	
	/*
	********************************************************************************
	*** save view information 
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public int saveViewSql(Object[] objInputData) throws Exception {
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		int intResult;
		HashMap hsmReceiveData;
		String strViewName = "";
		String strContent = "";
		//Boolean blnIsRead = false;
		
		StringBuffer stbSql = new StringBuffer();
		
		dbc = new DB_Connection();
		dbc.getConnection(PJ_Cnst.CNT_INFO);
		//dbc.setAutoCommit(false); // Transaction Start !
		
		hsmReceiveData = (HashMap)objInputData[0];
		strViewName = (String)hsmReceiveData.get("viewname");
		strContent = (String)hsmReceiveData.get("definition");
		
		stbSql.append("\n"+"CREATE OR REPLACE VIEW ");
		stbSql.append("\n"+"	" + strViewName);
		stbSql.append("\n"+"	AS");
		stbSql.append("\n"+"	" + strContent);
			
		try {
			//begin xungnv added
			saveViewInfo(objInputData);
			//end xungnv added
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();
		} catch (DB_Exception e) {			
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }		
			if(dbc != null) { dbc.close(); dbc = null; }
		}
		return intResult;	
	}
	   
	@SuppressWarnings("unchecked")
	public int updateViewSql(Object[] objInputData) throws Exception {
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		int intResult;
		HashMap hsmReceiveData;
		String strOldViewName = "";
		String strViewName = "";
		String strContent = "";
		//Boolean blnIsRead = false;
	
		StringBuffer stbSql = new StringBuffer();
		StringBuffer stbSql1 = new StringBuffer();
		
		dbc = new DB_Connection();
		dbc.getConnection(PJ_Cnst.CNT_INFO);
		//dbc.setAutoCommit(false); // Transaction Start !
		
		hsmReceiveData = (HashMap)objInputData[0];
		strOldViewName = (String)hsmReceiveData.get("oldviewname");
		strViewName = (String)hsmReceiveData.get("viewname");
		strContent = (String)hsmReceiveData.get("definition");
		
		//if (strOldViewName != null && strOldViewName != "" && strOldViewName != strViewName)
		//{
		stbSql1.append("\n"+"DROP VIEW ");
		stbSql1.append("\n"+ strOldViewName);
		//}
		
		stbSql.append("\n"+"CREATE OR REPLACE VIEW ");
		stbSql.append("\n"+"	" + strViewName);
		stbSql.append("\n"+"	AS");
		stbSql.append("\n"+"	" + strContent);
			
		try {	
			//begin xungnv added				
			updateViewInfo(objInputData);				
			//end xungnv added
			
			dbs = dbc.prepareStatement(stbSql1.toString());
			intResult = dbs.executeUpdate();
			
			if(dbs != null){ dbs.close(); dbs = null; }
			
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();				

		} catch (DB_Exception e) {			
			throw e;			
		} catch (Exception e) {			
			throw e;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
			if(dbc != null) { dbc.close(); dbc = null; }
		}
		return intResult;	
	}
	
	/*
	********************************************************************************
	*** delete view information 
	********************************************************************************
	*/
	@SuppressWarnings("unchecked")
	public int deleteViewSql(Object[] objInputData) throws Exception {
		DB_Connection dbc = null;
		DB_Statement dbs = null;
		int intResult;
		HashMap hsmReceiveData;
		String strViewName = "";
		//Boolean blnIsRead = false;
		StringBuffer stbSql = new StringBuffer();
		   
		dbc = new DB_Connection();
		dbc.getConnection(PJ_Cnst.CNT_INFO);
		
		hsmReceiveData = (HashMap)objInputData[0];
		strViewName = (String)hsmReceiveData.get("viewname");
		strViewName =strViewName.replace(";", ",");
	 
		stbSql.append("\n"+"DROP VIEW ");
		stbSql.append("\n"+ strViewName);
			
		try {
			dbs = dbc.prepareStatement(stbSql.toString());
			intResult = dbs.executeUpdate();
			if(dbs != null){ dbs.close(); dbs = null; }
			deleteViewInfo(objInputData);

		} catch (DB_Exception e) {			
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			if (dbs != null){ dbs.close();  dbs = null; }
			if(dbc != null) { dbc.close(); dbc = null; }
		}
		if (intResult == 0) {
			return -1;
		}
		return 0;	
	}
}
