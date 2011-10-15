package com.joke.template.basetemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joke.template.R;
import com.joke.template.globalstatic.GlobalStatic;
import com.joke.template.views.PullToRefreshListView;
import com.joke.template.views.PullToRefreshListView.OnRefreshListener;
import com.joke.template.views.SuperDialog;
import com.joke.template.views.SuperWebViewDialog;

public class ListBaseActivity extends ListSuperActivity implements
		OnClickListener {
	/** 
     * 设置布局显示属性 
     */  
    private LayoutParams mLayoutParams = new LinearLayout.LayoutParams(  
            LinearLayout.LayoutParams.WRAP_CONTENT,  
            LinearLayout.LayoutParams.WRAP_CONTENT);  
    /** 
     * 设置布局显示目标最大化属性 
     */  
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(  
            LinearLayout.LayoutParams.FILL_PARENT,  
            LinearLayout.LayoutParams.FILL_PARENT);
	
	private LinearLayout loadingLayout;
	private ProgressBar progressBar;
	private LinearLayout layout;
	//private Context mContext;
	public void onCreate(Bundle savedInstanceState, Context _mContext) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.template_list);
		//mContext = _mContext;
		getTopLeftBtn().setOnClickListener(this);
		getTopRightBtn().setOnClickListener(this);
		
		getBottomLeft1Btn().setOnClickListener(this);
		getBottomLeft2Btn().setOnClickListener(this);
		getBottomLeft3Btn().setOnClickListener(this);
		getBottomLeft4Btn().setOnClickListener(this);
		
		getBottomLeft1Layout().setOnClickListener(this);
		getBottomLeft2Layout().setOnClickListener(this);
		getBottomLeft3Layout().setOnClickListener(this);
		getBottomLeft4Layout().setOnClickListener(this);
		
		addFootView();
		pullTOFreshTheData();
	}
	private void addFootView() {
		layout = new LinearLayout(this);  
        // 设置布局 水平方向  
        layout.setOrientation(LinearLayout.HORIZONTAL);  
        // 进度条  
        progressBar = new ProgressBar(this);  
        // 进度条显示位置  
        progressBar.setPadding(0, 0, 15, 0);  
        // 把进度条加入到layout中  
        // layout.addView(progressBar, mLayoutParams);  
        // 文本内容  
        TextView textView = new TextView(this);  
        textView.setText("展开更多");  
        textView.setGravity(Gravity.CENTER);  
//        textView.setPadding(100, 10, 0, 20);  
        textView.setTextColor(Color.BLUE);  
        textView.setTextSize(20);  
//        textView.setGravity(Gravity.CENTER);
        // 把文本加入到layout中  
        layout.addView(textView, FFlayoutParams);  
        // 设置layout的重力方向，即对齐方式是  
        layout.setGravity(Gravity.CENTER);  
		 // 设置ListView的页脚layout  
        loadingLayout = new LinearLayout(this);  
        loadingLayout.addView(layout, mLayoutParams);  
        loadingLayout.setGravity(Gravity.CENTER);  
        loadingLayout.setOnClickListener(new OnClickListener() {  
  
            @Override  
            public void onClick(View v) {  
                /* 
                 * if (adapter.count<=41) { adapter.count += 10; 
                 * adapter.notifyDataSetChanged(); 
                 *  
                 * } 
                 */  
               loadingMoreDataFromTheBootom();
               layout.addView(progressBar, mLayoutParams); 
            }

        });  
        ((PullToRefreshListView) getListView()).addFooterView(loadingLayout);  
	}
	
	private void pullTOFreshTheData() {
//		((PullToRefreshListView) getListView()).setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Do work to refresh the list here.
////            	Init(GlobalStatic.TASK_UPDATE_DATA_SHOUYE,true);
//            	pullToReshDataMethod();
//            }
//
//        });
		  ((PullToRefreshListView) getListView()).onRefreshComplete(); 
	}
	
	protected void setBottomVisble(boolean is_show) {
		LinearLayout layout = (LinearLayout)findViewById(R.id.foot);
		if(is_show){
			layout.setVisibility(View.VISIBLE);
		}else{
			layout.setVisibility(View.GONE);
		}
	}
	
	protected void setTopVisble(boolean is_show) {
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.template_top);
		if(is_show){
			layout.setVisibility(View.VISIBLE);
		}else{
			layout.setVisibility(View.GONE);
		}
	}
	// -----------------------------------------------------------------------
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_button_left:	
			onTopLeftBtnClick();
			break;		
		case R.id.top_button_right:
			onTopRightBtnClick();
			break;
		case R.id.bottom_Layout_left1:			
		case R.id.bottom_button_left1:
			onBottomLeft1BtnClick();
			break;
		case R.id.bottom_Layout_left2:
		case R.id.bottom_button_left2:
			onBottomLeft2BtnClick();
			break;
		case R.id.bottom_Layout_left3:
		case R.id.bottom_button_left3:
			onBottomLeft3BtnClick();
			break;
		case R.id.bottom_Layout_left4:
		case R.id.bottom_button_left4:
			onBottomLeft4BtnClick();
			break;
		}
	}

	public void onBottomLeft4BtnClick() {
		onSearchRequested();
	}

	public void onBottomLeft3BtnClick() {
		// TODO Auto-generated method stub

	}

	public void onBottomLeft2BtnClick() {
		// TODO Auto-generated method stub
		
	}

	public void onBottomLeft1BtnClick() {
	}

	public void onTopRightBtnClick() {
		
	}

	public void onTopLeftBtnClick() {
		this.finish();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		final String action = intent.getAction();
		if (Intent.ACTION_SEARCH.equals(action)) {
			onSearch(intent);
		} 
	}
	
	protected void onSearch(Intent intent) {
//		final String queryString = intent.getStringExtra(SearchManager.QUERY);
//        Log.d("ListBaseActivity", "queryString=" + queryString);
//        
//		String select = ServerBookListDb.name + " like '%" + queryString + "%' or " +  ServerBookListDb.name_pinyin_f
//			+ " like '%" + queryString + "%' or " + ServerBookListDb.name_pinyin_h + " like '%" + queryString + "%'";
//        showCategoryData(select);
    }
	
	protected void showCategoryData(String selection) {
		
	}

	// -----------------------------------------------------------------------
	protected final Button getTopLeftBtn() {
		return (Button) findViewById(R.id.top_button_left);
	}

	protected final TextView getTopCenterTextView() {
		return (TextView) findViewById(R.id.top_text_center);
	}

	protected final Button getTopRightBtn() {
		return (Button) findViewById(R.id.top_button_right);
	}

	protected final Button getBottomLeft1Btn() {
		return (Button) findViewById(R.id.bottom_button_left1);
	}

	protected final Button getBottomLeft2Btn() {
		return (Button) findViewById(R.id.bottom_button_left2);
	}

	protected final Button getBottomLeft3Btn() {
		return (Button) findViewById(R.id.bottom_button_left3);
	}

	protected final Button getBottomLeft4Btn() {
		return (Button) findViewById(R.id.bottom_button_left4);
	}

	protected final LinearLayout getfootLayout() {
		return (LinearLayout) findViewById(R.id.foot);
	}
	
	// -------------------------------layout-----------------------------------
	protected final LinearLayout getBottomLeft1Layout() {
		return (LinearLayout) findViewById(R.id.bottom_Layout_left1);
	}

	protected final LinearLayout getBottomLeft2Layout() {
		return (LinearLayout) findViewById(R.id.bottom_Layout_left2);
	}

	protected final LinearLayout getBottomLeft3Layout() {
		return (LinearLayout) findViewById(R.id.bottom_Layout_left3);
	}

	protected final LinearLayout getBottomLeft4Layout() {
		return (LinearLayout) findViewById(R.id.bottom_Layout_left4);
	}

	protected final void setTopCenterText(String str) {
//		getTopCenterTextView().setText(str);
	}

	protected final void setBottomLeft1BtnText(String str) {
		getBottomLeft1Btn().setText(str);
	}

	protected final void setBottomLeft2BtnText(String str) {
		getBottomLeft2Btn().setText(str);
	}

	protected final void setBottomLeft3BtnText(String str) {
		getBottomLeft3Btn().setText(str);
	}

	protected final void setBottomLeft4BtnText(String str) {
		getBottomLeft4Btn().setText(str);
	}

	// -----------------------------------------------------------------------
	protected final void setTopLeftBtnBackground(int id) {
		getTopLeftBtn().setBackgroundResource(id);
	}

	protected final void setTopRightBtnBackground(int id) {
		getTopRightBtn().setBackgroundResource(id);
	}

	protected final void setBottomLeft1BtnBackground(int id) {
		getBottomLeft1Btn().setBackgroundResource(id);
	}

	protected final void setBottomLeft2BtnBackground(int id) {
		getBottomLeft2Btn().setBackgroundResource(id);
	}

	protected final void setBottomLeft3BtnBackground(int id) {
		getBottomLeft3Btn().setBackgroundResource(id);
	}

	protected final void setBottomLeft4BtnBackground(int id) {
		getBottomLeft4Btn().setBackgroundResource(id);
	}
	
	@Override
	public int doneAsyncTask2(int taskId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public SuperDialog getProgressBar(Context context) {
		return super.getProgressBar(context);
	}
	
	@Override
	public SuperDialog getProgressDialog() {
		return super.getProgressDialog();
	}
	
	@Override
	public SuperWebViewDialog getWebViewProgressBar(Context context) {
		return super.getWebViewProgressBar(context);
	}
	
	@Override
	public SuperWebViewDialog getWebViewProgressDialog() {
		return super.getWebViewProgressDialog();
	}
	
	public void pullToReshDataMethod() {
		// TODO Auto-generated method stub
		
	}
	
	public void loadingMoreDataFromTheBootom() {
//		((PullToRefreshListView) getListView()).removeFooterView(loadingLayout);  
	}  
	
	
	@Override
	public void InitFinish(int taskId, int init_status) {
		if(taskId == GlobalStatic.TASK_UPDATE_DATA_SHOUYE){//pull update the data
			 ((PullToRefreshListView) getListView()).onRefreshComplete();
		}
		
		if(taskId == GlobalStatic.TASK_MORE_DATA_SHOUYE){//More  the data
			if(layout != null)
				layout.removeViewInLayout(progressBar); 
		}
	}
	
}
