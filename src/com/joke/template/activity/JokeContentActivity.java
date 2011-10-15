package com.joke.template.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.joke.template.R;
import com.joke.template.basetemplate.BaseActivity;
import com.joke.template.logs.SuperLogs;
import com.joke.template.net.BasicInfomation;
import com.joke.template.net.Parser;
import com.joke.template.utils.StringUtils;

public class JokeContentActivity extends BaseActivity{
	private WebView mWebView = null;
	private Context mContext;
	private static BasicInfomation base;
	
	public static void startThisActivity(Context _context, BasicInfomation _base){
		base = _base;
		Intent in = new Intent();
		in.setClass(_context, JokeContentActivity.class);
		_context.startActivity(in);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.webview_map, this);
		mContext = this;
		setupViews();
	}

	private void setupViews() {


		mWebView = (WebView) findViewById(R.id.WebView01);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new SuperWebViewClient());
		new WebViewTask().execute(base);
	}

	public void onTopBtnClick() {
		this.finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mWebView.clearHistory();
		mWebView.clearCache(true);
	}

	private class WebViewTask extends AsyncTask<BasicInfomation, Void, Boolean> {

		@Override
		protected Boolean doInBackground(BasicInfomation... params) {
			// TODO Auto-generated method stub
			try {
				
//				String shead= Parser.getContentOfPage(params[0].siteurl);
				mWebView.loadDataWithBaseURL("about:blank", params[0].detail_content + "</body></html>", "text/html", "utf-8", null);
				SuperLogs.info(params[0].detail_content);
				//mWebView.loadUrl(params[0]);
//				mWebView.loadDataWithBaseURL("http://www.baidu.com",
//						sbody, null, "gb2312", null);
				// mWebView.canGoBackOrForward(10);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}

			return true;
		}
	}
	private class SuperWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
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