
package com.hokukou.report;

import java.io.IOException; 
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter; 
import javax.servlet.FilterChain; 
import javax.servlet.FilterConfig; 
import javax.servlet.ServletException; 
import javax.servlet.ServletRequest; 
import javax.servlet.ServletResponse; 
import javax.servlet.http.HttpServletRequest;

/*
********************************************************************************
================================================================================
	ホクコウ共通ライブラリ In Java
================================================================================
	Program Number  : SetCharacterEncodingFilter.java
	Program Name    : 全角文字の文字化け対応
	Program Date    : 2008/06/24
	Programmer      : H.Fujimoto
   
===< Update History >===========================================================
	2008/06/24  : 新規作成
	2009/01/13  : POST時の全角文字化けに対応                           = T.Ogawa
================================================================================
********************************************************************************
*/
public class SetCharacterEncodingFilter implements Filter { 
 
    protected String encoding = null; 
    protected FilterConfig filterConfig = null; 
    protected boolean ignore = true; 
     
    public void destroy() { 
        this.encoding = null; 
        this.filterConfig = null; 
    } 
     
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
		
	    // Conditionally select and set the character encoding to be used
	    if (ignore || (request.getCharacterEncoding() == null)) {
	        String encoding = selectEncoding(request);
	        if (encoding != null) request.setCharacterEncoding(encoding);
	        
	        /***** 2009/01/13 UPDATE START *****/
			//// GET parameter encoding
			//if ((request instanceof HttpServletRequest) &&
			//    ("GET".equalsIgnoreCase(((HttpServletRequest)request).getMethod()))) {
			//
			//    Map parameterMap = request.getParameterMap();
			//    if (parameterMap != null) {
			//
			//        for (Iterator it = parameterMap.keySet().iterator(); it.hasNext(); ) {
			//            String name = (String)it.next();
			//            String[] values = request.getParameterValues(name);
			//            if (values != null) {
			//                for (int i = 0; i < values.length; i++) {
			//                    try {
			//                        values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
			//                    } catch (UnsupportedEncodingException e) {
			//                    }
			//                }
			//            }
			//        }
			//    }
			//}
	        
	        // parameter encoding
	        if (request instanceof HttpServletRequest) {
	            Map parameterMap = request.getParameterMap();
	            if (parameterMap != null) {
	
	                for (Iterator it = parameterMap.keySet().iterator(); it.hasNext(); ) {
	                    String name = (String)it.next();
	                    String[] values = request.getParameterValues(name);
	                    if (values != null) {
	                        for (int i = 0; i < values.length; i++) {
	                            try {
	                                values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
	                            } catch (UnsupportedEncodingException e) {
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        /***** 2009/01/13 UPDATE E N D *****/
	    }
	    
	    // Pass control on to the next filter
	    chain.doFilter(request, response);
	}
     
    public void init(FilterConfig filterConfig) throws ServletException { 
        this.filterConfig = filterConfig; 
        this.encoding = filterConfig.getInitParameter("encoding"); 
        String value = filterConfig.getInitParameter("ignore"); 
        if (value == null) this.ignore = true; else  if (value.equalsIgnoreCase("true")) this.ignore = true; else  if (value.equalsIgnoreCase("yes")) this.ignore = true; else  this.ignore = false; 
    } 
     
    protected String selectEncoding(ServletRequest request) { 
        return (this.encoding); 
    } 
}
 
