package com.joke.template.basetemplate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joke.template.R;
import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.views.SuperDialog;

public class BaseActivity extends SuperActivity implements OnClickListener {
	//private Context mContext;
	protected static boolean isSearchState = false;	
	public void onCreate(Bundle savedInstanceState, int sublayoutid,
			Context _mContext) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.basetemplate);
		//mContext = _mContext;
		RelativeLayout centerviewbox = (RelativeLayout) findViewById(R.id.centerview);
		View view = View.inflate(this, sublayoutid, null);
		centerviewbox.addView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));


		getBottomLeft1Btn().setOnClickListener(this);
		getBottomLeft2Btn().setOnClickListener(this);
		getBottomLeft3Btn().setOnClickListener(this);
		getBottomLeft4Btn().setOnClickListener(this);
		getBottomLeft5Btn().setOnClickListener(this);
		setupViews();
	}
	
	public void onCreate(Bundle savedInstanceState, LinearLayout sublayout,
			Context _mContext) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.basetemplate);
		//mContext = _mContext;
		RelativeLayout centerviewbox = (RelativeLayout) findViewById(R.id.centerview);
		centerviewbox.addView(sublayout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));


		getBottomLeft1Btn().setOnClickListener(this);
		getBottomLeft2Btn().setOnClickListener(this);
		getBottomLeft3Btn().setOnClickListener(this);
		getBottomLeft4Btn().setOnClickListener(this);
		getBottomLeft5Btn().setOnClickListener(this);
		setupViews();
	}
	
	protected void setBottomVisble(boolean is_show) {
		LinearLayout layout = (LinearLayout)findViewById(R.id.basebar);
		if(is_show){
			layout.setVisibility(View.VISIBLE);
		}else{
			layout.setVisibility(View.GONE);
		}
	}
	
	public boolean NetWorkStatus() {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();

		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}

		return netSataus;
	}

	// -----------------------------------------------------------------------
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.basebar_button_1:
			onBottomLeft1BtnClick();
			break;
		case R.id.basebar_button_2:
			onBottomLeft2BtnClick();
			break;
		case R.id.basebar_button_3:
			onBottomLeft3BtnClick();
			break;
		case R.id.basebar_button_4:
			onBottomLeft4BtnClick();
			break;
		case R.id.basebar_button_5:
			onBottomLeft5BtnClick();
			break;
		}
	}

	private void setupViews() {
	}

	public void onBottomLeft1BtnClick() {
	}

	public void onBottomLeft2BtnClick() {
		
	}

	public void onBottomLeft3BtnClick() {
	}

	public void onBottomLeft4BtnClick() {
	}

	public void onBottomLeft5BtnClick() {
	}

	public void onBottomLeft1LayoutClick() {
		onBottomLeft1BtnClick();
	}

	public void onBottomLeft2LayoutClick() {
		onBottomLeft2BtnClick();
	}

	public void onBottomLeft3LayoutClick() {
		onBottomLeft3BtnClick();
	}

	public void onBottomLeft4LayoutClick() {
		onBottomLeft4BtnClick();
	}

	public void onBottomLeft5LayoutClick() {
		onBottomLeft5BtnClick();
	}
	
	@Override
	public void onMsgData(Message msg){
		switch (msg.what) {
		case MsgHandlerType.MSG_ID_TEST:			
		
			break;				
		default:
			super.onMsgData(msg);
			break;
		}
	}


	// -----------------------------------------------------------------------

	/**
	 * 用户的头像
	 */
//	protected final ImageView getTopImageIconView() {
//		return (ImageView) findViewById(R.id.topbar_icon);
//	}
//	
//	/**
//	 * 用户名
//	 * @return
//	 */
//	protected final TextView getTopUsernameView() {
//		return (TextView) findViewById(R.id.topbar_user_name);
//	}
//	
//	/**
//	 * 用户是否为vip
//	 * @return
//	 */
//	protected final ImageView getTopImageVipView() {
//		return (ImageView) findViewById(R.id.topbar_user_vip);
//	}
	
	/**
	 * 进入该用户的图标>
	 * @return
	 */
//	protected final View getTopCominView() {
//		return (View) findViewById(R.id.topbar_user_comein);
//	}

	/**
	 * 底部的导航条
	 * 第一个button
	 * @return
	 */
	protected final TextView getBottomLeft1Btn() {
		return (TextView) findViewById(R.id.basebar_button_1);
	}

	/**
	 * 底部的导航条
	 * 第二个button
	 * @return
	 */
	protected final TextView getBottomLeft2Btn() {
		return (TextView) findViewById(R.id.basebar_button_2);
	}

	/**
	 * 底部的导航条
	 * 第三个button
	 * @return
	 */
	protected final TextView getBottomLeft3Btn() {
		return (TextView) findViewById(R.id.basebar_button_3);
	}

	/**
	 * 底部的导航条
	 * 第四个button
	 * @return
	 */ final TextView getBottomLeft4Btn() {
		return (TextView) findViewById(R.id.basebar_button_4);
	}

	 /**
	 * 底部的导航条
	 * 第五一个button
	 * @return
	 */
	protected final TextView getBottomLeft5Btn() {
		return (TextView) findViewById(R.id.basebar_button_5);
	}
	
	@Override
	public SUPER_ACTIVITY_STATUS getActivityStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActivityStatus(SUPER_ACTIVITY_STATUS status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int doneAsyncTask2(int taskId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public SuperDialog getProgressBar(Context context) {
		return super.getProgressBar(context);
	}

}
