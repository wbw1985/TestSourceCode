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
public class JokeBroadcastActivity extends ListBaseActivity{
	private BasicContentAdapter m_notes;
	private static String tagClass = "tuwentonghua";
	private Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,this);
		mContext = JokeBroadcastActivity.this;
		setTopCenterText("首页推荐");
		Init(GlobalStatic.TASK_SHOUYE_TUIJIAN,true);
		setupView();
	} 

	private void setupView() {
		setBottomVisble(false);
		setTopVisble(false);
		  
		getTopLeftBtn().setText("返回");
		getTopRightBtn().setText("刷新");
	}
	
	@Override
	public void InitStart(int arg1) {
		if(arg1 == GlobalStatic.TASK_UPDATE_DATA_SHOUYE){//pull update the data
			SuperLogs.info("arg1 start");
		}
	}
	
	@Override
	public int doneAsyncTask(int arg1) {
		if(arg1 == GlobalStatic.TASK_SHOUYE_TUIJIAN){//download the data
			List<BasicInfomation> list = GlobeConfData.getInstance().LoadClassifyData(getApplicationContext(), tagClass,0 , GlobalStatic.DOWNLOAD_DATA_COUNT_EACH_TIME);
			if(list.size() == 0){
				return INIT_FAIL;
			}
			SuperLogs.info("---ff--" + list.size());
			m_notes = new BasicContentAdapter(this,  m_Handler, list);
		}
		
		else if(arg1 == GlobalStatic.TASK_UPDATE_DATA_SHOUYE){//pull update the data
			if(m_notes != null){
				List<BasicInfomation> newlist = GlobeConfData.getInstance().LoadClassifyData(getApplicationContext(), tagClass, m_notes.getCount() , m_notes.getCount() + GlobalStatic.DOWNLOAD_DATA_COUNT_EACH_TIME);
				if(newlist.size() == 0){
					return INIT_FAIL;
				}
				SuperLogs.info("---new--" + newlist.size());
				addAdapterListFirstHeadData(newlist);
			}
			
		}
		
		else if(arg1 == GlobalStatic.TASK_MORE_DATA_SHOUYE){//More  the data
				int startpositon = 0;
				if(m_notes == null){
					startpositon = 0;
				}else{
					startpositon = m_notes.getCount();
				}
				List<BasicInfomation> newlist = GlobeConfData.getInstance().LoadClassifyData(getApplicationContext(), tagClass, startpositon , startpositon + GlobalStatic.DOWNLOAD_DATA_COUNT_EACH_TIME);
				if(newlist.size() == 0){
					return INIT_FAIL;
				}
				SuperLogs.info("-----" + newlist.size());
				for(int i = 0; i < 2; i++){
					SuperLogs.info("---new--title=" + newlist.get(i).title);
				}
				if(m_notes == null){
					m_notes = new BasicContentAdapter(this,  m_Handler, newlist);
					setListAdapter(m_notes);
				}else{
					addAdapterListBottomData(newlist);
				}
				
			
		}
		
		return super.doneAsyncTask(arg1);
	}
	
	@Override
	public void InitFinish(int taskId, int init_status) {
		if(taskId == GlobalStatic.TASK_UPDATE_DATA_SHOUYE){//pull update the data
			SuperLogs.info("arg1 InitFinish");
			super.InitFinish(taskId, init_status);
		}
		
		if(taskId == GlobalStatic.TASK_SHOUYE_TUIJIAN){
			SuperLogs.info("downlodad InitFinish");
			setListAdapter(m_notes);
		}
		
		if(taskId == GlobalStatic.TASK_MORE_DATA_SHOUYE){//More  the data
			if(m_notes != null){
				m_notes.notifyDataSetChanged();
			}
			super.InitFinish(taskId, init_status);
		}
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
		JokeContentGalleryActivity.startThisActivity(mContext, m_notes.getItem(position - 1));
//		Intent in = new Intent();
//		in.setClass(mContext, Test_DrawText.class);
//		mContext.startActivity(in);
		
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
	
	private void addAdapterListFirstHeadData(List<BasicInfomation> update_data_list){
		// TODO Auto-generated method stub
		m_notes.addUpdateDataToListFirst(update_data_list);
	}
	
	private void addAdapterListBottomData(List<BasicInfomation> update_data_list){
		// TODO Auto-generated method stub
		m_notes.addMoreUpdateDataToListFirst(update_data_list);
	}
	
	@Override
	public void pullToReshDataMethod() {
		super.pullToReshDataMethod();
		Init(GlobalStatic.TASK_UPDATE_DATA_SHOUYE,true);
	}
	
	@Override
	public void loadingMoreDataFromTheBootom() {
		//TODO start the task of loading more data
		Init(GlobalStatic.TASK_MORE_DATA_SHOUYE,true);
		super.loadingMoreDataFromTheBootom();
	}  
}
