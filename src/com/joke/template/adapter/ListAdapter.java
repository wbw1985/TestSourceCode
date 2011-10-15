package com.joke.template.adapter;

import java.util.List;
import com.joke.template.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private List<String> m_Data = null;
	
	public ListAdapter(Context context,List<String> frindsList){
		this.mInflater = LayoutInflater.from(context);
		m_Data = frindsList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(m_Data == null){
			return 0;			
		}else{
			return m_Data.size() ;
		}
	}

	@Override
	public String getItem(int arg0) {
		// TODO Auto-generated method stub
		return m_Data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return  getCount() - arg0 - 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(position >= m_Data.size()){
			//
			//这里到尾了需要重新刷
			return convertView;
		}
		WeiboFriendsHolder holder = null;
		if (convertView == null) {
			
			convertView = (LinearLayout) mInflater.inflate(
					R.layout.listitemlayout, parent, false);
			
			//头像
			final TextView indexCreatedAt = (TextView)convertView.findViewById(R.id.friendsname);
			holder = new WeiboFriendsHolder();
			
			holder.name = indexCreatedAt;
			
			
			convertView.setTag(holder);
			
		}else {
			holder = (WeiboFriendsHolder)convertView.getTag();
		}
		holder.name.setText(m_Data.get(position).toString());

		return convertView;
	}
	
	class WeiboFriendsHolder {
		TextView name;
	}
}