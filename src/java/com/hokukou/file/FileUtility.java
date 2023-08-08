package com.hokukou.file;

import java.io.File;

public class FileUtility {

	public static void deleteFileAll(String strPath) {
		File dir = new File(strPath);

		if (dir.isDirectory()) {//Directory
			String[] dirList = dir.list();
			for (int i = 0; i < dirList.length; i++) {
				FileUtility.deleteFileAll(strPath + "/" + dirList[i]);
			}
			//Delete Directory
			dir.delete();
		} else if (dir.isFile()) {
			//Delete File
			dir.delete();
		}
	}
}
