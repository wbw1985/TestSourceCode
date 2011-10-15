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
public class EnglishBroadcastActivity extends BaseActivity{
	private final String[] mLabelArray = { "View1"};
	private FlingGallery mGallery;
	private static int positionLayout = 0;
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
		int type = this.getIntent().getExtras().getInt("type");
		switch(type){
		case GlobalStatic.type_xiaohua:
			positionLayout = 2;
			break;
		case GlobalStatic.type_yingwei:
			positionLayout = 1;
			break;
		case GlobalStatic.type_yulu:
			positionLayout = 0;
			break;
		}
		mGallery.setIsGalleryCircular(true);
		super.onCreate(savedInstanceState, layout, this);
//		getTopLeftBtn().setVisibility(View.GONE);
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
			
			
			
			top_left_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			top_center_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			top_right_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			
			center_left_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			center_center_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			center_right_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			
			bottom_left_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			bottom_center_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			bottom_right_1_button.setOnClickListener(EnglishBroadcastActivity.this);
			
			TextView top_left_1_text = (TextView)findViewById(R.id.top_left_1_text);
			TextView top_center_1_text = (TextView)findViewById(R.id.top_center_1_text);
			TextView top_right_1_text = (TextView)findViewById(R.id.top_right_1_text);
			TextView center_left_1_text = (TextView)findViewById(R.id.center_left_1_text);
			TextView center_center_1_text = (TextView)findViewById(R.id.center_center_1_text);
			TextView center_right_1_text = (TextView)findViewById(R.id.center_right_1_text);
			TextView bottom_left_1_text = (TextView)findViewById(R.id.bottom_left_1_text);
			TextView bottom_center_1_text = (TextView)findViewById(R.id.bottom_center_1_text);
			TextView bottom_right_1_text = (TextView)findViewById(R.id.bottom_right_1_text);
			
			positionLayout = position;
			top_left_1_button.setBackgroundResource(R.drawable.grid_btn_1);
			top_left_1_text.setText("美食");
			top_center_1_text.setText("健康");
			top_right_1_text.setText("星座");
			
			center_left_1_text.setText("影视");
			center_center_1_text.setText("宠物");
			center_right_1_text.setText("历史");
			
			bottom_left_1_text.setText("摄影");
			bottom_center_1_text.setText("名人");
			bottom_right_1_text.setText("创意");
		}
	}
	
	public static final int TASK_TIYU = 0x00000001;
	public static final int TASK_QICHE = 0x00000002;
	public static final int TASK_IT = 0x00000003;
	public static final int TASK_shuma = 0x00000004;
	public static final int TASK_junshi = 0x00000005;
	public static final int TASK_jiaju = 0x00000006;
	public static final int TASK_qinggan = 0x00000007;
	public static final int TASK_jianzhu = 0x00000008;
	public static final int TASK_kexue = 0x00000009;
	
	public static final int TASK_caijing = 0x00000010;
	public static final int TASK_youxi = 0x000000011;
	public static final int TASK_yinyue = 0x00000012;
	public static final int TASK_shenghuo = 0x00000013;
	public static final int TASK_lvxing = 0x00000014;
	public static final int TASK_yishu = 0x00000015;
	public static final int TASK_yuedu = 0x00000016;
	public static final int TASK_dongman = 0x00000017;
	public static final int TASK_shishang = 0x00000018;
	
	public static final int TASK_meishi = 0x00000019;
	public static final int TASK_jiankang = 0x00000020;
	public static final int TASK_xingzuo = 0x00000021;
	public static final int TASK_yingshi = 0x00000022;
	public static final int TASK_chongwu = 0x00000023;
	public static final int TASK_lishi = 0x00000024;
	public static final int TASK_sheying = 0x00000025;
	public static final int TASK_mingren = 0x00000026;
	public static final int TASK_chuangyi = 0x00000027;


	@Override
	public void onClick(View v) {
		Log.d("&&&&&&&&&&&&", "onclick"+v.getId());
		switch (v.getId()) {
		case R.id.top_left_1_button:
			if(positionLayout == 2){
				startActivity(TASK_TIYU);
			}else if(positionLayout == 1){
				startActivity(TASK_caijing);
			}else if(positionLayout == 0){
				startActivity(TASK_meishi);
			}
			
			break;
		case R.id.top_center_1_button:
			if(positionLayout == 2){
				startActivity(TASK_QICHE);
			}else if(positionLayout == 1){
				startActivity(TASK_youxi);
			}else if(positionLayout == 0){
				startActivity(TASK_jiankang);
			}
			break;
		case R.id.top_right_1_button:
			if(positionLayout == 2){
				startActivity(TASK_IT);
			}else if(positionLayout == 1){
				startActivity(TASK_yinyue);
			}else if(positionLayout == 0){
				startActivity(TASK_xingzuo);
			}
			break;
		case R.id.center_left_1_button:
			if(positionLayout == 2){
				startActivity(TASK_shuma);
			}else if(positionLayout == 1){
				startActivity(TASK_shenghuo);
			}else if(positionLayout == 0){
				startActivity(TASK_yingshi);
			}
			break;
		case R.id.center_center_1_button:
			if(positionLayout == 2){
				startActivity(TASK_junshi);
			}else if(positionLayout == 1){
				startActivity(TASK_lvxing);
			}else if(positionLayout == 0){
				startActivity(TASK_chongwu);
			}
			break;
		case R.id.center_right_1_button:
			if(positionLayout == 2){
				startActivity(TASK_jiaju);
			}else if(positionLayout == 1){
				startActivity(TASK_yishu);
			}else if(positionLayout == 0){
				startActivity(TASK_lishi);
			}
			break;
		case R.id.bottom_left_1_button:
			if(positionLayout == 2){
				startActivity(TASK_qinggan);
			}else if(positionLayout == 1){
				startActivity(TASK_yuedu);
			}else if(positionLayout == 0){
				startActivity(TASK_sheying);
			}
			break;
		case R.id.bottom_center_1_button:
			if(positionLayout ==2){
				startActivity(TASK_jianzhu);
			}else if(positionLayout == 1){
				startActivity(TASK_dongman);
			}else if(positionLayout == 0){
				startActivity(TASK_mingren);
			}
			break;
		case R.id.bottom_right_1_button:
			if(positionLayout == 2){
				startActivity(TASK_kexue);
			}else if(positionLayout == 1){
				startActivity(TASK_shishang);
			}else if(positionLayout == 0){
				startActivity(TASK_chuangyi);
			}
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
