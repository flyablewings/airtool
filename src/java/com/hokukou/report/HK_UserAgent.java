package com.hokukou.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HK_UserAgent extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,
			               HttpServletResponse response) throws ServletException, IOException {
		response.reset();
		response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        String strUserAgent = request.getHeader("user-agent");
        
        out.println("function HK_UserAgent() {");
        
        if (strUserAgent != null && strUserAgent.equals("") == false) {
	        out.println("    return \"" + escapeJSString(strUserAgent) + "\";");
        } else {
	        out.println("    return navigator.appVersion;");
        }
        
        out.println("}");
        out.close();
	}
	
	private static String escapeJSString(String strValue){
		String strReturn = "";
		for (int i=0; i<strValue.length(); i++) {
			
			if (String.valueOf(strValue.charAt(i)).equals("\"")) {
				strReturn = strReturn + "\\";
			}
			if (String.valueOf(strValue.charAt(i)).equals("'")) {
				strReturn = strReturn + "\\";
			}
			if (String.valueOf(strValue.charAt(i)).equals("\\")) {
				strReturn = strReturn + "\\";
			}
			strReturn = strReturn + strValue.charAt(i);
		}
		return strReturn;
	}
}
