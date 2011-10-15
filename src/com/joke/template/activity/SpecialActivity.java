package com.joke.template.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.joke.template.R;
import com.joke.template.basetemplate.BaseActivity;
import com.joke.template.globalstatic.GlobalStatic;
import com.joke.template.views.FlingGallery;


//标签列表
public class SpecialActivity extends BaseActivity{
	private final String[] mLabelArray = { "View1"};
	private FlingGallery mGallery;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return mGallery.onGalleryTouchEvent(ev);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGallery = new FlingGallery(this);
		mGallery.setPaddingWidth(5);
		mGallery.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, mLabelArray) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				Log.d("111", "count=" + position);

				return new GalleryViewItem(getApplicationContext(), position);
			}
		});

		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);

		layoutParams.setMargins(10, 10, 10, 10);
		layoutParams.weight = 1.0f;
		layout.addView(mGallery, layoutParams);
		mGallery.setIsGalleryCircular(false);
//		super.onCreate(savedInstanceState, layout, this);
		setContentView(layout);
	}

	@Override
	public void onBottomLeft1BtnClick() {
		getBottomLeft2Btn().setPressed(false);
		this.finish();
	}
	
	@Override
	public void onBottomLeft2BtnClick() {
		getBottomLeft2Btn().setPressed(false);
		return;
	}
	
	@Override
	public void onBottomLeft3BtnClick() {
		super.onBottomLeft3BtnClick();
	}
	
	@Override
	public void onBottomLeft4BtnClick() {
		super.onBottomLeft4BtnClick();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}

	private class GalleryViewItem extends TableLayout {

		public GalleryViewItem(Context context, int position) {
			super(context);

			this.setOrientation(LinearLayout.VERTICAL);

			this.setLayoutParams(new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
//			
			View view = View.inflate(context, R.layout.my_gridview, null);
			this.addView(view, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			Button top_left_1_button = (Button)findViewById(R.id.top_left_1_button);
			Button top_center_1_button = (Button)findViewById(R.id.top_center_1_button);
			Button top_right_1_button = (Button)findViewById(R.id.top_right_1_button);
			Button center_left_1_button = (Button)findViewById(R.id.center_left_1_button);
			Button center_center_1_button = (Button)findViewById(R.id.center_center_1_button);
			Button center_right_1_button = (Button)findViewById(R.id.center_right_1_button);
			Button bottom_left_1_button = (Button)findViewById(R.id.bottom_left_1_button);
			Button bottom_center_1_button = (Button)findViewById(R.id.bottom_center_1_button);
			Button bottom_right_1_button = (Button)findViewById(R.id.bottom_right_1_button);
			
			
			
			top_left_1_button.setOnClickListener(SpecialActivity.this);
			top_center_1_button.setOnClickListener(SpecialActivity.this);
			top_right_1_button.setOnClickListener(SpecialActivity.this);
			
			center_left_1_button.setOnClickListener(SpecialActivity.this);
			center_center_1_button.setOnClickListener(SpecialActivity.this);
			center_right_1_button.setOnClickListener(SpecialActivity.this);
			
			bottom_left_1_button.setOnClickListener(SpecialActivity.this);
			bottom_center_1_button.setOnClickListener(SpecialActivity.this);
			bottom_right_1_button.setOnClickListener(SpecialActivity.this);
			
			TextView top_left_1_text = (TextView)findViewById(R.id.top_left_1_text);
			TextView top_center_1_text = (TextView)findViewById(R.id.top_center_1_text);
			TextView top_right_1_text = (TextView)findViewById(R.id.top_right_1_text);
			TextView center_left_1_text = (TextView)findViewById(R.id.center_left_1_text);
			TextView center_center_1_text = (TextView)findViewById(R.id.center_center_1_text);
			TextView center_right_1_text = (TextView)findViewById(R.id.center_right_1_text);
			TextView bottom_left_1_text = (TextView)findViewById(R.id.bottom_left_1_text);
			TextView bottom_center_1_text = (TextView)findViewById(R.id.bottom_center_1_text);
			TextView bottom_right_1_text = (TextView)findViewById(R.id.bottom_right_1_text);
			
			Log.d("ttt","position=" + position);
			if (position == 0) {
				top_left_1_button.setBackgroundResource(R.drawable.grid_btn_1);
				top_left_1_text.setText("逗你一笑");
				top_center_1_text.setText("娱乐八卦");
				top_right_1_text.setText("网络经典");
				
				center_left_1_text.setText("短信经典");
				center_center_1_text.setText("爱情话语");
				center_right_1_text.setText("生活语录");
				
				bottom_left_1_text.setText("名人语录");
				bottom_center_1_text.setText("图文童话");
				bottom_right_1_text.setText("其它语录");
			}
		}
	}



	@Override
	public void onClick(View v) {
		Log.d("&&&&&&&&&&&&", "onclick"+v.getId());
		switch (v.getId()) {
		case R.id.top_left_1_button:
			startActivity(GlobalStatic.TASK_douniyixiao);
			
			break;
		case R.id.top_center_1_button:
			startActivity(GlobalStatic.TASK_yulebagua);
			break;
		case R.id.top_right_1_button:
			startActivity(GlobalStatic.TASK_wangluojingdian);
			break;
		case R.id.center_left_1_button:
			startActivity(GlobalStatic.TASK_duanxinjingdian);
			break;
		case R.id.center_center_1_button:
			startActivity(GlobalStatic.TASK_aiqinghuayu);
			break;
		case R.id.center_right_1_button:
			startActivity(GlobalStatic.TASK_shenghuoyulu);
			break;
		case R.id.bottom_left_1_button:
			startActivity(GlobalStatic.TASK_mingrenyulu);
			break;
		case R.id.bottom_center_1_button:
			startActivity(GlobalStatic.TASK_tuwentonghua);
			break;
		case R.id.bottom_right_1_button:
			startActivity(GlobalStatic.TASK_qitayulu);
			break;
		}
		super.onClick(v);
	}

	private void startActivity(int taskIt) {
		
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putInt("tagname", taskIt);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), GirdTagContentDataList.class);
		startActivity(intent);
	}
}
