package com.joke.template.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.logs.SuperLogs;
import com.joke.template.utils.FileUtils;


public class DownloadOne {
	
	private URL m_url;

	public DownloadOne(){
		
	}
	
	//flag is true means return exist and not to download
	public int download(String urlStr, String path, String name, boolean flag){
		if(FileUtils.isFileExist(path + name) && flag) 
			return MsgHandlerType.downloadStatus_IsExist;
		
		InputStream inputStream = null;
		FileOutputStream fos = null;
		
		try {
			URL m_url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) m_url.openConnection();
			urlConn.setDoInput(true);   
			urlConn.connect();
			inputStream = urlConn.getInputStream();
			
			File dir = new File(path);
			if(!dir.exists())
			{
				if(!dir.mkdirs())
				{
					return MsgHandlerType.downloadStatus_error;
				}
			}
			
			File file = new File(path + name);
			file.createNewFile();
			
			fos = new FileOutputStream(file);
    		byte[] bs = new byte[30*1024];
    		int n = 0;
    		while((n=inputStream.read(bs))!=-1){
    			fos.write(bs,0,n);
    			n++;
    		}
    		
    		fos.flush();
    		fos.close();
    		return MsgHandlerType.downloadStatus_Completed;
		} catch (Exception e) {
			e.printStackTrace();
			return MsgHandlerType.downloadStatus_error;
		} 
		
	}
	
	//flag is true means return exist and not to download
	public int downloadCover(String urlStr, String referUrl, String path, String name, boolean flag){
		SuperLogs.info("name=" + name);
		if(FileUtils.isFileExist(path + name) && !FileUtils.isFileEmpty(path + name) && flag) 
			return MsgHandlerType.SINGLE_COVER_DOWNLOAD_EXIST;
		
		InputStream inputStream = null;
		FileOutputStream fos = null;
		
		try {
			URL m_url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) m_url.openConnection();
			urlConn.setRequestMethod("GET") ;
			urlConn.setConnectTimeout(3000) ; //3秒超时
			urlConn.setDoInput(true) ;
			urlConn.setDoOutput(true) ;
			urlConn.setUseCaches(false) ;
			urlConn.addRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/x-shockwave-flash, */*"); 
			urlConn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; .NET CLR 2.0.50727; MS-RTC LM 8)"); 
			if(referUrl != null){
				urlConn.addRequestProperty("Referer", referUrl);
			}
			urlConn.connect() ;
			inputStream = urlConn.getInputStream();
			
			File dir = new File(path);
			if(!dir.exists())
			{
				if(!dir.mkdirs())
				{
					return MsgHandlerType.downloadStatus_error;
				}
			}
			
			File file = new File(path + name);
			file.createNewFile();
			
			fos = new FileOutputStream(file);
    		byte[] bs = new byte[30*1024];
    		int n = 0;
    		while((n=inputStream.read(bs))!=-1){
    			fos.write(bs,0,n);
    			n++;
    		}
    		
    		fos.flush();
    		fos.close();
    		return MsgHandlerType.SINGLE_COVER_DOWNLOAD;
		} catch (Exception e) {
			e.printStackTrace();
			return MsgHandlerType.downloadStatus_error;
		} 
		
	}
	
	/**
	 * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
	 * 1.创建一个URL对象
	 * 2.通过URL对象，创建一个HttpURLConnection对象
	 * 3.得到InputStram
	 * 4.从InputStream当中读取数据
	 * @param urlStr
	 * @return
	 */
	public String download(String urlStr) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			m_url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection)m_url
					.openConnection();
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(urlConn
					.getInputStream(), "utf-8"));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
