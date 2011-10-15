package com.joke.template.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joke.template.R;
import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.logs.SuperLogs;
import com.joke.template.net.BasicInfomation;

public class BasicContentAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<BasicInfomation> m_Data = null;
	private Context mContext;
	Handler m_handler = null;
	basicViewHolder holder = null;
	public BasicContentAdapter(Context context,Handler handler,List<BasicInfomation> basic_list){
		mContext = context;
		m_handler = handler;
		this.mInflater = LayoutInflater.from(context);
		m_Data = basic_list;
	}
	
	public BasicContentAdapter(Context context,Handler handler,List<BasicInfomation> basic_list,int cur_pos){
		this(context,handler,basic_list);
	}
	
	public void addUpdateDataToListFirst(List<BasicInfomation> update_data_list){
		List<BasicInfomation> old_data_list = m_Data;
		int size = old_data_list.size();
		if(size > 0){
			m_Data.clear();
			m_Data = update_data_list;
			for(int i = 0; i < size; i++){
				m_Data.addAll(old_data_list);
			}
			old_data_list.clear();
		}
	}
	
	public void addMoreUpdateDataToListFirst(List<BasicInfomation> update_data_list){
		int size = update_data_list.size();
		for(int t = 0; t < 2; t++){
			SuperLogs.info("old=" + m_Data.get(t).title);
		}
		for(int i = 0; i < size; i++){
			m_Data.add(update_data_list.get(i));
		}
		
		for(int t = 0; t < 4; t++){
			SuperLogs.info("newestlllll=" + m_Data.get(t).title);
		}
	}
	
	public BasicInfomation getbasicInfoByPostion(int nposition){
		if(m_Data  == null){
			return null;
		}
		if(nposition < 0 || nposition >= m_Data.size()){
			return null;
		}
		return m_Data.get(nposition);
	}
	
	public List<BasicInfomation> GetbasicList(){
		return m_Data;
	}
	
	@Override
	public int getCount() {
		if(m_Data == null){
			return 0;			
		}else{
			return m_Data.size() ;
		}
	}

	@Override
	public BasicInfomation getItem(int arg0) {
		return m_Data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return  getCount() - arg0 - 1;
	}

	public void ForceUpdate(List<BasicInfomation> basic_list){
		if(null == basic_list){
			return;
		}
		m_Data = basic_list;		
		notifyDataSetInvalidated();
		notifyDataSetChanged();
	}

	private class basicViewHolder
    {

		
		public TextView m_indexScreenType;
		
		public TextView m_indexCreatedAt;

		public TextView m_indexbasicTitle;
	
		public LinearLayout m_sub_content_layout_top;
		public WebView m_subbasicContent;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(position >= m_Data.size()){
			//
			//这里到尾了需要重新刷
			return convertView;
		}
		
		if (convertView == null) {
			
			convertView = (LinearLayout) mInflater.inflate(
					R.layout.my_basic_index_list_item2, parent, false);
			
			final TextView indexScreenType = (TextView)convertView.findViewById(R.id.tvItemType);
			
			final TextView indexCreatedAt = (TextView)convertView.findViewById(R.id.tvItemDate);

			final TextView indexbasicTitle = (TextView)convertView.findViewById(R.id.tvItemTitle);
			
			LinearLayout sub_content_layout_top = (LinearLayout)convertView.findViewById(R.id.subLayout_top);
			
			final WebView subbasicContent = (WebView)convertView.findViewById(R.id.tvItemSubContent);
		
			
			holder = new basicViewHolder();
			
			holder.m_indexScreenType = indexScreenType;
			holder.m_indexCreatedAt = indexCreatedAt;
			holder.m_indexbasicTitle = indexbasicTitle;
			
			
			holder.m_sub_content_layout_top = sub_content_layout_top;
			holder.m_subbasicContent = subbasicContent;
			
			holder.m_subbasicContent.setVerticalScrollBarEnabled(false);
			holder.m_subbasicContent.setHorizontalScrollBarEnabled(false);
			holder.m_subbasicContent.getSettings().setJavaScriptEnabled(true);
			
			holder.m_subbasicContent.getSettings().setPluginsEnabled(true);
			
			convertView.setTag(holder);
			
		}else {
			holder = (basicViewHolder)convertView.getTag();
		}
		

		final BasicInfomation basicinfo = m_Data.get(position);
		if(basicinfo == null){
			return convertView;
		}
		
//		if(basicinfo.image != null && basicinfo.image.length() > 0){
//			//判断文件在不在
//			String filename = GlobalStatic.MY_FOLDER_PATH + basicinfo.image.substring(basicinfo.image.lastIndexOf("/") + 1);;
//			Bitmap bp = BitmapFactory.decodeFile(filename);
//	    	if(bp == null)
//	    	{
//	    		holder.m_image_Portrait.setImageResource(R.drawable.portrait);
//	    		//开启下载图片	
//	    		DownloadStartThread.getInstance().downloadCoverStartThread(
//	    				"http://www.5time.cn/" + basicinfo.image,
//	    				GlobalStatic.MY_FOLDER_PATH,
//	    				filename,
//	    				m_handler,
//	    				false);
//				Log.d("ServerCategoryBooksAdapter",filename + " is not exist,begin down it");    			
//	    	}
//	    	else
//	    	{
//	    		Bitmap scalebp = Bitmap.createScaledBitmap(bp,50, 50, true);
//	    		holder.m_image_Portrait.setImageBitmap(scalebp);	    
//	    	}
//		}else{
//			holder.m_image_Portrait.setImageResource(R.drawable.portrait);
//		}
		
		holder.m_indexScreenType.setText("【 " + basicinfo.type + " 】");
		holder.m_indexCreatedAt.setText(basicinfo.startTime);
			
		if(basicinfo.description == null || basicinfo.description.length() == 0){
			holder.m_sub_content_layout_top.setVisibility(View.GONE);
		}else{
			holder.m_sub_content_layout_top.setVisibility(View.VISIBLE);
			
			if(basicinfo.description.indexOf(".swf") != -1){
				holder.m_subbasicContent.setWebViewClient(new SuperWebViewClient());
				//抽取视频的路径
				String pathStartTag = "http://";
				String pathEndTag = ".swf";
				int pathStart = basicinfo.description.indexOf(pathStartTag);
				int pathEnd = basicinfo.description.indexOf(pathEndTag, pathStart);
				if(pathStart != -1 && pathEnd != -1){
					String path = basicinfo.description.substring(pathStart, pathEnd + pathEndTag.length());
					String preText = null;
					try{
						if(pathStart - 1 > 0){
							preText = basicinfo.description.substring(0, pathStart - 1);
						}else{
							preText = "";
						}
						String afterText = basicinfo.description.substring(pathEnd + pathEndTag.length());
						holder.m_subbasicContent.loadDataWithBaseURL("",
								preText + "{ " + "<a href=\"" + path + "\" + >"+basicinfo.title + "</a>" + " }" + afterText
								, null, "utf-8", null);
					}catch(IndexOutOfBoundsException e){
						holder.m_subbasicContent.loadDataWithBaseURL("", basicinfo.description, null, "utf-8", null);
					}
				}
				
			}else{
				holder.m_subbasicContent.loadDataWithBaseURL("",
						basicinfo.description, null, "utf-8", null);
			}
		}
		BasicInfomation bs = m_Data.get(position);
		DownButtonClick db = new DownButtonClick(bs);
		holder.m_subbasicContent.setClickable(true);
		holder.m_subbasicContent.setOnClickListener(db);
		holder.m_indexbasicTitle.setText(basicinfo.title);
		return convertView;
	}
	
	class DownButtonClick implements OnClickListener{
    	private BasicInfomation bs;
    	DownButtonClick(BasicInfomation _bs){
    		bs = _bs;
    	}
    	
		@Override
		public void onClick(View arg0) {
			holder.m_subbasicContent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,480));
//			holder.m_subbasicContent.setPadding(5, 5, 5, 5);
			Message msg = new Message();
			msg.what = MsgHandlerType.TASK_YULU_LIST_WEBVIEW_ONCLICK;
			msg.obj = bs.siteurl;
			m_handler.sendMessage(msg);
		}
    }
	
	private class SuperWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			holder.m_subbasicContent.loadUrl(url);
			return false;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

	}
}