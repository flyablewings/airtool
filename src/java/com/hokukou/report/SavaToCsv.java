package com.hokukou.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : SavaToCsv.java
	Program Name    : Csv保存時のServlet処理
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
================================================================================
********************************************************************************
*/
public class SavaToCsv extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SavaToCsv() {
		super();
	}

	private List getDataSource(CsvReport csvReport) {
		List dataSource = csvReport.getDataSource();
		return dataSource;
	}

	private String getFileName(CsvReport csvReport) {
		String fileName = csvReport.getFileName();
		return fileName;
	}

	private List getHead(CsvReport csvReport) {
		List head = csvReport.getHead();
		return head;
	}

	private Map getHeadMap(CsvReport csvReport) {
		Map headMap = csvReport.getHeadMap();
		return headMap;
	}

	private CsvReport initializeReportData(Map map) {
		String classID = ((String[]) map.get("id"))[0];
		CsvReport reportData = null;
		try {
			iHK_ReportOut printReport = (iHK_ReportOut) Class.forName(classID)
					.newInstance();
			reportData = printReport.getCsvReportRes(map);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return reportData;
	}

	private String getColumnHead(List head) {
		String columnHead = "";
		for (int i = 0; i < head.size(); i++) {
			if (i == (head.size() - 1))
				columnHead += (String) head.get(i) + "\n";
			else
				columnHead += (String) head.get(i) + ",";
		}
		return columnHead;
	}

	private String getRowData(Map recode, Map headMap, List head) {
		String strRow = "";
		for (int i = 0; i < head.size(); i++) {
			if (i == (head.size() - 1))
				strRow += (String) recode.get((String) headMap
						.get((String) head.get(i)))
						+ "\n";
			else
				strRow += (String) recode.get((String) headMap
						.get((String) head.get(i)))
						+ ",";
		}
		return strRow;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Map Parameters = request.getParameterMap();
		CsvReport reportData = initializeReportData(Parameters);
		if (reportData == null)
			return;
		List fileHead = getHead(reportData);
		if (fileHead.size() <= 0)
			return;
		Map headMap = getHeadMap(reportData);
		if (headMap.size() <= 0)
			return;
		String fileName = getFileName(reportData);
		if (fileName == null)
			return;
		fileName = new String(fileName.getBytes("shift_jis"), "ISO8859_1");

		List dataSource = getDataSource(reportData);

		if (dataSource.size() <= 0)
			return;

		try {

			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("shift_jis");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			PrintWriter ouputStream = response.getWriter();
			ouputStream.write(getColumnHead(fileHead));

			for (int i = 0; i < dataSource.size(); i++) {
				Map row = (Map) dataSource.get(i);
				String strRow = this.getRowData(row, headMap, fileHead);
				ouputStream.write(strRow);
			}

			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
