package com.joke.template.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.joke.template.adapter.BasicContentAdapter;
import com.joke.template.basetemplate.ListBaseActivity;
import com.joke.template.globalstatic.GlobalStatic;
import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.logs.SuperLogs;
import com.joke.template.net.BasicInfomation;
import com.joke.template.net.GlobeConfData;
/*
 * 用户taglist
 */
public class GirdTagContentDataList extends ListBaseActivity
{
	int m_tagtask;
	private Context mContext;
	private BasicContentAdapter m_notes;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,this);
		mContext = this;
		Intent intent = getIntent();
		if(intent == null){
			this.finish();
			return;
		}
		Bundle bb = intent.getExtras();
		if(bb == null){
			this.finish();
			return;
		}
		m_tagtask = bb.getInt("tagname");
		setTopCenterText(getCategoryName(m_tagtask));
		Init(m_tagtask,true);
		setupView();
	} 

	private void setupView() {
		setBottomVisble(false);
		getTopLeftBtn().setText("返回");
		getTopRightBtn().setText("刷新");
	}
	
	@Override
	public void InitStart(int arg1) {
		if(arg1 == GlobalStatic.TASK_UPDATE_DATA_SHOUYE){//pull update the data
			SuperLogs.info("arg1 start");
		}
	}

	private static String tagClass = "douniyixiao";
	@Override
	public int doneAsyncTask(int arg1) {
		if(arg1 == GlobalStatic.TASK_UPDATE_DATA_SHOUYE){//pull update the data
			SuperLogs.info("arg1 done" + m_notes.getCount());
			List<BasicInfomation> newlist = GlobeConfData.getInstance().LoadClassifyData(getApplicationContext(), "douniyixiao",m_notes.getCount() , m_notes.getCount() + GlobalStatic.DOWNLOAD_DATA_COUNT_EACH_TIME);
			if(newlist.size() == 0){
				return INIT_FAIL;
			}
			SuperLogs.info("arg1 done newlist" + newlist.size());
			addAdapterListFirstHeadData(newlist);
		}else{//download the data
			SuperLogs.info("downlodad");
			tagClass = getCategory(arg1);
			List<BasicInfomation> list = GlobeConfData.getInstance().LoadClassifyData(getApplicationContext(), tagClass,0 , GlobalStatic.DOWNLOAD_DATA_COUNT_EACH_TIME);
			if(list.size() == 0){
				return INIT_FAIL;
			}
			m_notes = new BasicContentAdapter(this,  m_Handler, list);
		}
		
		return super.doneAsyncTask(arg1);
	}
	
	@Override
	public void InitFinish(int taskId, int init_status) {
		if(taskId == GlobalStatic.TASK_UPDATE_DATA_SHOUYE){//pull update the data
			SuperLogs.info("arg1 InitFinish");
			super.InitFinish(taskId, init_status);
		}else{
			SuperLogs.info("downlodad InitFinish");
			setListAdapter(m_notes);
		}
		
	}
	
	private String getCategory(int category){
		switch(category){
		case GlobalStatic.TASK_douniyixiao:
			tagClass = "douniyixiao";
			break;
		case GlobalStatic.TASK_yulebagua:
			tagClass="yulebagua";
			break;
		case GlobalStatic.TASK_wangluojingdian:
			tagClass = "wangluojingdian";
			break;
		case GlobalStatic.TASK_duanxinjingdian:
			tagClass = "duanxinjingdian";
			break;
		case GlobalStatic.TASK_aiqinghuayu:
			tagClass="aiqinghuayu";
			break;
		case GlobalStatic.TASK_shenghuoyulu:
			tagClass = "shenghuoyulu";
			break;
			
		case GlobalStatic.TASK_mingrenyulu:
			tagClass = "mingrenyulu";
			break;
		case GlobalStatic.TASK_tuwentonghua:
			tagClass="tuwentonghua";
			break;
		case GlobalStatic.TASK_qitayulu:
			tagClass = "qitayulu";
			break;
		}
		return tagClass;
	}
	
	private String getCategoryName(int category){
		switch(category){
		case GlobalStatic.TASK_douniyixiao:
			tagClass = "逗你一笑";
			break;
		case GlobalStatic.TASK_yulebagua:
			tagClass="娱乐八卦";
			break;
		case GlobalStatic.TASK_wangluojingdian:
			tagClass = "网络经典";
			break;
		case GlobalStatic.TASK_duanxinjingdian:
			tagClass = "短信经典";
			break;
		case GlobalStatic.TASK_aiqinghuayu:
			tagClass="爱情话语";
			break;
		case GlobalStatic.TASK_shenghuoyulu:
			tagClass = "生活语录";
			break;
			
		case GlobalStatic.TASK_mingrenyulu:
			tagClass = "名人语录";
			break;
		case GlobalStatic.TASK_tuwentonghua:
			tagClass="图文童话";
			break;
		case GlobalStatic.TASK_qitayulu:
			tagClass = "其它语录";
			break;
		}
		return tagClass;
	}
	
	@Override
	public void onMsgData(Message msg) {

		switch (msg.what) {
		case MsgHandlerType.downloadStatus_Completed:{
			m_notes.notifyDataSetChanged();
			break;
		
		}case MsgHandlerType.downloadStatus_IsExist:{
			break;
		
		}case MsgHandlerType.TASK_YULU_LIST_WEBVIEW_ONCLICK:{
			getProgressDialog().show();
			break;
		}
			
		default:
			super.onMsgData(msg);
			break;
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		JokeContentActivity.startThisActivity(mContext, m_notes.getItem(position - 1));
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
	
	private void addAdapterListFirstHeadData(List<BasicInfomation> update_data_list){
		// TODO Auto-generated method stub
//		m_notes.addFirst("Added after refresh...");
		m_notes.addUpdateDataToListFirst(update_data_list);
	}
	
	@Override
	public void pullToReshDataMethod() {
		super.pullToReshDataMethod();
		Init(GlobalStatic.TASK_UPDATE_DATA_SHOUYE,true);
	}
}
