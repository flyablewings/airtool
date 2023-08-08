package com.hokukou.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IFile {
	public void doDownloading(HttpServletRequest request, HttpServletResponse response);
	public void doUploading(HttpServletRequest request, HttpServletResponse response);
}
