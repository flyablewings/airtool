package com.hokukou.file;

import java.io.*;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

public class FileDownload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileDownload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//
		// response.setContentType("text/html");
		// response.setCharacterEncoding("utf-8");
			
		this.getServletContext().getRealPath("/");
		
		
		String strClassName = "";
		Map mapParameters = request.getParameterMap();
		strClassName = ((String[]) mapParameters.get("id"))[0];

		IFile obj = null;

		try {
			obj = (IFile)(Class.forName(strClassName).newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		obj.doDownloading(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request,response);
	}
}
