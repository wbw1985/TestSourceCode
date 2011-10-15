package com.joke.template.net;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

import com.joke.template.globalstatic.GlobalStatic;
import com.joke.template.logs.SuperLogs;
import com.joke.template.utils.Utils;

public class GlobeConfData {
	
//	private SmUtilStorage<String> m_globe_conf = new SmUtilStorage<String>("/sm/conf.dat");

	
	
	private boolean m_init_succ = false;
	
	private List<BasicInfomation> m_WeiboListData = null;		//Î¢²©ÏêÏ¸ÄÚÈÝ
	
	
	public String m_android_id;
	private Context m_context = null;
	private GlobeConfData(){		
	}
	
	private static GlobeConfData m_instance=null;
	public static synchronized GlobeConfData getInstance(){
		if(m_instance==null){
			m_instance= new GlobeConfData();
		}
		return m_instance;
	}
	
	public synchronized void close(){
		m_WeiboListData = null;
		m_instance = null;
	}

	public void ClearSinaWeicoInfo(){
		SaveConf();
	}
	
	public void SaveConf(){
		if(m_context == null){
			return;
		}
//		SharedPreferences settings = m_context.getSharedPreferences(GlobeConfData.KEY_INFO, 0);
//		settings.edit()
//			.putString(GlobeConfData.KEY_SINA_WEICO_USERNAME, m_sina_bind_username)
//			.putString(GlobeConfData.KEY_SINA_WEICO_TOKEN, m_sina_token)
//			.putString(GlobeConfData.KEY_SINA_WEICO_TOKENSECRET, m_sina_tokenSecret)
//			.putLong(GlobeConfData.KEY_SINA_WEICO_USERID, m_sina_weibo_id)			 
//			.putString(GlobeConfData.KEY_MOBILE_ID, m_android_id)
//			.putLong(GlobeConfData.KEY_WEIWEI_ID, get_weiwei_id())
//			
//			.commit();
	}

	public void LoadConf(){
		if(m_context == null){
			return;
		}
//		SharedPreferences settings = m_context.getSharedPreferences(GlobeConfData.KEY_INFO, 0);
//		m_sina_bind_username = settings.getString(GlobeConfData.KEY_SINA_WEICO_USERNAME,"");
//		m_sina_token = settings.getString(GlobeConfData.KEY_SINA_WEICO_TOKEN,"");
//		m_sina_tokenSecret = settings.getString(GlobeConfData.KEY_SINA_WEICO_TOKENSECRET,"");
//		m_sina_weibo_id = settings.getLong(GlobeConfData.KEY_SINA_WEICO_USERID,0);
//		m_android_id = settings.getString(GlobeConfData.KEY_MOBILE_ID,"");		
//		set_weiwei_id(settings.getLong(GlobeConfData.KEY_WEIWEI_ID,0));
	}
	
	public void Init(Context context){
		if(m_init_succ){
			return;
		}
		m_init_succ =  true;
		m_context = context;
		LoadConf();

		if(m_android_id == null || m_android_id.length() == 0){
			m_android_id = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
			if(m_android_id == null || m_android_id.length() == 0){
				long time=System.currentTimeMillis();
				final Calendar mCalendar=Calendar.getInstance();
				mCalendar.setTimeInMillis(time);
				m_android_id = String.format("%04d%02d%02d-%02d:%02d-%s",mCalendar.get(Calendar.YEAR),
						mCalendar.get(Calendar.MONTH),
						mCalendar.get(Calendar.DATE), 
						mCalendar.get(Calendar.HOUR),
						mCalendar.get(Calendar.MINUTE),Utils.getRandomString(10)
						);
			}
			SaveConf();
		}
		//if(m_weiwei_id )
	}
	
	public BasicInfomation GetbsInfo(String siteurl){
		for(BasicInfomation t : m_WeiboListData)
		{
			if(t.siteurl == siteurl){
				return t;
			}
		}
		return null;
	}
	
	public synchronized List<BasicInfomation> LoadClassifyData(Context mContext, String classifyName, int startPos, int endPos) {
		DownloadOne down = new DownloadOne();//start=0&end=20
		String sbody = down.download(GlobalStatic.m_url_base + "start=" + startPos +"&end=" + endPos + "&type=" + classifyName);
		SuperLogs.info("url = " + GlobalStatic.m_url_base + "start=" + startPos +"&end=" + endPos + "&type=" + classifyName);
		List<BasicInfomation> newlist = parseWeiboData(sbody);

		return newlist;
	}

	public synchronized List<BasicInfomation> parseWeiboData(String sbody) {
		List<BasicInfomation> bsinfolist = new ArrayList<BasicInfomation>();
		try {			
			JSONObject root = new JSONObject(sbody);//.getJSONObject("userbean");
			JSONArray nameList = root.getJSONArray("array"); 
			if(nameList == null)
			{
				return null;
			}
			int length = nameList.length();
            for(int i = 0; i < length; i++){//±éÀúJSONArray
                Log.d("debugTest",Integer.toString(i));
                JSONObject oj = nameList.getJSONObject(i);
                
                BasicInfomation bs = new BasicInfomation();
                bs.title = oj.getString("title");
                bs.image = oj.getString("image");
                bs.description = oj.getString("description");
                bs.startTime = oj.getString("startTime");
                bs.type = oj.getString("type");
                bs.siteurl=oj.getString("siteurl");
                bs.detail_content = oj.getString("detail_content");
                bsinfolist.add(bs);
            }
           
		} catch (JSONException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return bsinfolist;
		} 
		
		return bsinfolist;
	}
	
	
}
