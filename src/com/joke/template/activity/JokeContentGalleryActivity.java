package com.joke.template.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.joke.template.R;
import com.joke.template.adapter.CustomGalleryImageAdapter;
import com.joke.template.basetemplate.BaseActivity;
import com.joke.template.globalstatic.GlobalStatic;
import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.logs.SuperLogs;
import com.joke.template.net.BasicInfomation;
import com.joke.template.net.DownloadStartThread;
import com.joke.template.net.Parser;
import com.joke.template.views.CustomGallery;

public class JokeContentGalleryActivity extends BaseActivity {
	private Context mContext;
	private CustomGalleryImageAdapter m_notes;
	private static BasicInfomation base;
	public static ArrayList<String> picUrltList = new ArrayList<String>(0);
	public static ArrayList<String> picBeforeText = new ArrayList<String>(0);
	private DisplayMetrics displayMetrics;

	public static int displayWidthOfScreen = 0;
	public static int displayHeightOfScreen = 0;

	public static void startThisActivity(Context _context, BasicInfomation _base) {
		base = _base;
		Intent in = new Intent();
		in.setClass(_context, JokeContentGalleryActivity.class);
		_context.startActivity(in);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.customgallerycontent, this);
		mContext = this;
		initParserPicUrlList();
		initParserTextList();
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		// 获得手机的宽度和高度像素单位为px
		// String strPM = "手机屏幕分辨率为:" +
		// displayMetrics.widthPixels+"* "+displayMetrics.heightPixels;
		displayWidthOfScreen = displayMetrics.widthPixels;
		displayHeightOfScreen = displayMetrics.heightPixels;
		m_notes = new CustomGalleryImageAdapter(mContext, m_Handler,
				picUrltList, base.description);
		setupViews();

	}

	private void initParserTextList() {
		Parser.getTextFromHtml(base.detail_content);

	}

	private void initParserPicUrlList() {
		if (picUrltList != null && picUrltList.size() > 0) {
			picUrltList.clear();
		}
		picUrltList.add(base.description);
		if (base != null && base.detail_content != null)
			Parser.getPictureUrl(base.detail_content);

	}

	private void setupViews() {
		setBottomVisble(false);
		CustomGallery gallery = (CustomGallery) findViewById(R.id.gallery);// 定义
																			// Gallery控件
		gallery.setAdapter(m_notes);// 设置 Gallery 控件的图片源
		gallery.setSpacing(10);
		gallery.setBackgroundColor(0);
		gallery.setOnItemClickListener(new OnItemClickListener() { // 点击监听事件

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(mContext, "" + arg2, Toast.LENGTH_SHORT).show(); // Toast显示图片位置
			}
		});
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterview, View v,
					int position, long arg3) {
				// TODO 下载图片
				Init(position, true);
				if (m_notes.getCount() > position + 1)
					Init(position + 1, true);
				SuperLogs.info("position=" + position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		//
	}

	public void onTopBtnClick() {
		this.finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void InitStart(int arg1) {

	}

	@Override
	public int doneAsyncTask(int arg1) {
		SuperLogs.info("doneAsyncTask position=" + arg1);
		DownloadStartThread.getInstance().downloadCoverStartThread(
				m_notes.getItem(arg1), base.siteurl,
				GlobalStatic.HEAD_IMG_FOLDER_PATH, m_notes.getName(arg1),
				m_Handler, true);
		return super.doneAsyncTask(arg1);
	}

	@Override
	public void InitFinish(int arg1, int init_status) {

	}

	@Override
	public void onMsgData(Message msg) {
		SuperLogs.info("doneAsyncTask MsgHandlerType.downloadStatus_Completed="
				+ msg.what);
		switch (msg.what) {
		case MsgHandlerType.downloadStatus_Completed: {
//			m_notes.notifyDataSetChanged();
			break;

		}
		case MsgHandlerType.downloadStatus_IsExist: {
			break;
		}
		default:
			m_notes.notifyDataSetChanged();
			super.onMsgData(msg);
			break;
		}
	}
}