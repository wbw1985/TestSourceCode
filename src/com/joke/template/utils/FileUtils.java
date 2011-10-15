package com.joke.template.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.joke.template.logs.SuperLogs;

import android.util.Log;

public class FileUtils {

	static public boolean renameFile(final String srcfilename,
			final String destfilename) {
		File ffrom = new File(srcfilename);
		File fto = new File(destfilename);
		return ffrom.renameTo(fto);
	}

	static public File creatFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	static public File creatFile(String fileName, long time) throws IOException {
		File file = creatFile(fileName);
		file.setLastModified(time);
		return file;
	}

	static public long getFileLastModifyTime(String fileName)
			throws IOException {
		File file = new File(fileName);
		return file.lastModified();
	}

	static public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}

	static public String getUrlFileName(String Filename) {
		int nPos = Filename.lastIndexOf("/");
		if (nPos < 0) {
			nPos = Filename.indexOf("\\");
			if (nPos < 0) {
				return Filename;
			}
		}

		return Filename.substring(nPos + 1);
	}
	
	static public String getUrlFileName_phpid(String Filename) {
		SuperLogs.info("Filename=" + Filename);
		int nPos = Filename.lastIndexOf(".");
		if (nPos < 0) {
			nPos = Filename.indexOf("\\");
			if (nPos < 0) {
				return Filename;
			}
		}
		String filenameTag = Filename.substring(nPos + 1);
		if(filenameTag.contains("=")){
			filenameTag = filenameTag.replace("=", "59");
			if(filenameTag.contains("?")){
				filenameTag = filenameTag.replace("?", "11");
			}
			return  filenameTag + ".jpg";
		}
		else if(Filename.contains("pic.baohe.com")){
			return  filenameTag + ".jpg";
		}
		return  filenameTag;
		
	}
	
	static public String getUrlFileType(String Filename) {
		int nPos = Filename.lastIndexOf(".");
		if (nPos < 0) {
			nPos = Filename.indexOf("\\");
			if (nPos < 0) {
				return Filename;
			}
		}

		return Filename.substring(nPos);
	}

	static public void deleteDirectory(String path) {
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("not exists");
			return;
		}
		if (f.delete()) {
			System.out.println("delete directory : " + f.getAbsolutePath());
		} else {
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].isDirectory()) {
					if (!fs[i].delete())
						deleteDirectory(fs[i].getAbsolutePath());
					else
						System.out.println("delete directory : "
								+ fs[i].getAbsolutePath());
				} else {
					fs[i].delete();
					System.out.println("delete file : "
							+ fs[i].getAbsolutePath());
				}
			}
			f.delete();
			System.out.println("delete directory : " + f.getAbsolutePath());
		}
	}

	static public void deleteFiles(String path) {
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("not exists");
			return;
		}
		File[] fs = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				if (!fs[i].delete())
					deleteDirectory(fs[i].getAbsolutePath());
				else
					System.out.println("delete directory : "
							+ fs[i].getAbsolutePath());
			} else {
				fs[i].delete();
				System.out.println("delete file : " + fs[i].getAbsolutePath());
			}
		}
		return;
	}

	/**
	 * 鍒涘缓鐩綍
	 * 
	 * @param dirName
	 */
	static public File creatDir(final String dirName) {
		File dir = new File(dirName);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				Log.e("create", "can't create " + dirName);
			}
		}
		return dir;
	}

	/**
	 * 鍒ゆ柇SD鍗′笂鐨勬枃浠跺す鏄惁瀛樺湪
	 */
	static public boolean isFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}
	
	/**
	 * 判断文件的大小是否是0，空文件
	 * @param fileName
	 * @return
	 */
	static public boolean isFileEmpty(String fileName) {
		File file = new File(fileName);
		return file.length() == 0;
	}

	/**
	 * 鍒ゆ柇SD鍗′笂鐨勬枃浠跺す鏄惁瀛樺湪
	 */
	static public boolean isAbsFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 鑾峰彇鏂囦欢闀垮害
	 */
	static public long getFileSize(String fileName) {
		File file = new File(fileName);
		return file.length();
	}

	/**
	 * 灏嗕竴涓狪nputStream閲岄潰鐨勬暟鎹啓鍏ュ埌SD鍗′腑
	 */
	static public File write2FromInput(String path, String fileName,
			InputStream input, int length) {
		File file = null;
		FileOutputStream fos = null;
		try {
			creatDir(path);
			file = creatFile(path + fileName);

			fos = new FileOutputStream(file);
			byte[] bs = new byte[30 * 1024];
			int n = 0;
			while ((n = input.read(bs)) != -1) {
				fos.write(bs, 0, n);
				n++;
			}
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	static public List<String> getDirFilesList(final String path,
			final String filter) {
		List<String> list = new ArrayList<String>();
		String[] filenames = new File(path).list();
		if (null == filenames)
			return list;

		for (int i = 0, size = filenames.length; i < size; i++) {
			if (filenames[i].endsWith(filter)) {
				list.add(filenames[i]);
			}
		}
		return list;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtils.isBlank(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}
	
	/**
	 * 根据文件绝对路径获取文件名
	 * @param filePath
	 * @return
	 */
	public static String getFileName( String filePath )
	{
		if( StringUtils.isBlank(filePath) )	return "";
		return filePath.substring( filePath.lastIndexOf( File.separator )+1 );
	}
}