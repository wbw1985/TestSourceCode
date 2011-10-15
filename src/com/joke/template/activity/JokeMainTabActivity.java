package com.joke.template.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.TabHost;
import com.joke.template.R;
import com.joke.template.basetemplate.BaseTabActivity;
import com.joke.template.globalstatic.GlobalStatic;

public class JokeMainTabActivity extends BaseTabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		TabHost tabHost = getTabHost();
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		tabHost.setBackgroundResource(R.drawable.activity_template_background);
		tabHost.addTab(tabHost
				.newTabSpec("笑话推荐")
				.setIndicator(
						LayoutInflater.from(this).inflate(
								R.layout.textview_buy, null))
				.setContent(
						new Intent(this, JokeBroadcastActivity.class).putExtra(
								"type", GlobalStatic.type_xiaohua)));

//		// Sell
//		tabHost.addTab(tabHost
//				.newTabSpec("英文广场")
//				.setIndicator(
//						LayoutInflater.from(this).inflate(
//								R.layout.textview_sell, null))
//				.setContent(
//						new Intent(this, EnglishBroadcastActivity.class)
//								.putExtra("type", GlobalStatic.type_yingwei)));

		// My listed
		tabHost.addTab(tabHost
				.newTabSpec("语录广场")
				.setIndicator(
						LayoutInflater.from(this).inflate(
								R.layout.textview_my_listed, null))
				.setContent(
						new Intent(this, SpecialActivity.class).putExtra(
								"type", GlobalStatic.type_yulu)));

	}
}
