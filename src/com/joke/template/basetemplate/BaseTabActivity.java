package com.joke.template.basetemplate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.joke.template.R;

public class BaseTabActivity extends SuperTabActivity implements OnClickListener {
	//private Context mContext;
	protected static boolean isSearchState = false;	
	public void onCreate(Bundle savedInstanceState, int id,
			Context _mContext) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.basetemplate);
		//mContext = _mContext;
		RelativeLayout centerviewbox = (RelativeLayout) findViewById(R.id.centerview);
		View view = View.inflate(this, id, null);
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

	public void setupViews() {
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
	

}
