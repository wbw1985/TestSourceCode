package com.joke.template.globalstatic;

import android.os.Environment;

public class GlobalStatic {
	/**
	 * 多线程任务的taskId
	 * 在调用superActivity中的Asynctask时使用
	 */
	
	public static final int SEND_TASK = 0x000000101;//发送
	public static final int DOWNLOAD_TASK = 0x000000102;//下载
	public static final int REQUEST_TASK = 0x000000103;


	public static final String SDCORD_PATH = Environment.getExternalStorageDirectory() + "";
	public static final String MY_FOLDER_PATH = SDCORD_PATH + "/joke/";
	public static final String HEAD_IMG_FOLDER_PATH = SDCORD_PATH + "/joke/hdimg/";
	
	//
	public static final int type_xiaohua = 0x00001000;
	public static final int type_yingwei = 0x00001001;
	public static final int type_yulu = 0x00001002;
	
	//update data task
	public static final int TASK_UPDATE_DATA_SHOUYE = 0x00010000;
	public static final int TASK_MORE_DATA_SHOUYE = 0x00010001;
	
	
	//the count of each time to pull down the data
	public static final int DOWNLOAD_DATA_COUNT_EACH_TIME = 20;
	
	public static String m_url_base = "http://www.59travel.cn/joke/joke_json_show.php?";
	
	public static final int TASK_douniyixiao = 0x00000001;//逗你一笑
	public static final int TASK_yulebagua = 0x00000002;//娱乐八卦
	public static final int TASK_wangluojingdian = 0x00000003;//网络经典
	public static final int TASK_duanxinjingdian= 0x00000004;//短信经典
	public static final int TASK_aiqinghuayu = 0x00000005;//爱情话语
	public static final int TASK_shenghuoyulu = 0x00000006;//"生活语录
	public static final int TASK_mingrenyulu = 0x00000007;//名人语录
	public static final int TASK_tuwentonghua = 0x00000008;//图文童话
	public static final int TASK_qitayulu = 0x00000009;//其它语录
	
	public static final int TASK_SHOUYE_TUIJIAN = 0x00000010;//其它语录
	
	public static final int JokeContentGallery_TASK_PIC_DOWNLOADING = 0x00000010;//其它语录
}
