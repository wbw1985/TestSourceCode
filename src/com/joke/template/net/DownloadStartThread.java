package com.joke.template.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Message;

import com.joke.template.handlertype.MsgHandlerType;

public class DownloadStartThread {
	public static ExecutorService m_DownloadImage_Thread_Manager = Executors.newFixedThreadPool(20);
	public static DownloadStartThread downloadStartThread = null;
	
	public static synchronized DownloadStartThread getInstance(){
		if(downloadStartThread == null)
			downloadStartThread = new DownloadStartThread();
		return downloadStartThread;
	}
	
	public DownloadStartThread(){
		
	}
	
	
	//SELECT CHOICE of using which method to download
	public void downloadCoverStartThread(String url, String referUrl, String path, String name, Handler mHandler, boolean flag) {
		DownloadCoverThread covertone = new DownloadCoverThread(url, referUrl, path, name, mHandler, flag);
		if (null != covertone) {
			m_DownloadImage_Thread_Manager.execute(covertone);
		}
	}

	
	//single thread to download
	class DownloadThread extends Thread {
		private Handler m_Handler;
		private String urlstr;
		
		private String path;
		private String name;
		private boolean flag;
		
		
		public DownloadThread(String url, String path, String name, Handler mHandler, boolean flag) {
			m_Handler = mHandler;
			urlstr = url;
			
			this.path = path;
			this.name = name;
			this.flag = flag;
		}

		@Override
		public void run() {
			DownloadOne dt = new DownloadOne();
			int result = dt.download(urlstr, path, name, flag);
			if(m_Handler != null){
				if(result == MsgHandlerType.downloadStatus_Completed ){
					Message msg = new Message();
					msg.what = MsgHandlerType.downloadStatus_Completed;
					m_Handler.sendMessage(msg);
				}else if(result == MsgHandlerType.downloadStatus_IsExist){
					Message msg = new Message();
					msg.what = MsgHandlerType.downloadStatus_IsExist;
					m_Handler.sendMessage(msg);
				}else{
					Message msg = new Message();
					msg.what = MsgHandlerType.downloadStatus_error;
					m_Handler.sendMessage(msg);
				}
			}
		}
	}
	
	//single Cover thread to download
	class DownloadCoverThread extends Thread {
		private Handler m_Handler;
		private String urlstr;
		private String referUrl;
		private String path;
		private String name;
		private boolean flag;
		public DownloadCoverThread(String url, String _referUrl, String path, String filename, Handler mHandler, boolean flag) {
			m_Handler = mHandler;
			urlstr = url;
			referUrl = _referUrl;
			this.path = path;
			this.name = filename;
			this.flag = flag;
		}

		@Override
		public void run() {
			DownloadOne dt = new DownloadOne();
			int result = dt.downloadCover(urlstr, referUrl, path, name, flag);
			if(m_Handler != null){
				if(result == MsgHandlerType.SINGLE_COVER_DOWNLOAD ){
					Message msg = new Message();
					msg.what = MsgHandlerType.SINGLE_COVER_DOWNLOAD;
					m_Handler.sendMessage(msg);
				}else if(result == MsgHandlerType.SINGLE_COVER_DOWNLOAD_EXIST){
					Message msg = new Message();
					msg.what = MsgHandlerType.SINGLE_COVER_DOWNLOAD_EXIST;
					m_Handler.sendMessage(msg);
				}else{
					Message msg = new Message();
					msg.what = MsgHandlerType.downloadStatus_error;
					m_Handler.sendMessage(msg);
				}
			}
		}
	}

}
