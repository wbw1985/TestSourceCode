package com.joke.template.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Gallery.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ViewFlipper;

import com.joke.template.ActivityStatus;
import com.joke.template.ActivityStatus.SUPER_ACTIVITY_STATUS;
import com.joke.template.R;

public class SuperWebViewDialog {
	private ActivityStatus mActivityLifeStatus = null;
	private Context mContext = null;
	private Dialog mDialog;
	private WebView progressWebView;
	private View mView;

	private static WindowManager.LayoutParams params = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.FILL_PARENT,
			WindowManager.LayoutParams.FILL_PARENT,
			WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
			WindowManager.LayoutParams.ALPHA_CHANGED, PixelFormat.TRANSLUCENT);

	public SuperWebViewDialog(Context context, ActivityStatus mActivityStatus) {
		mContext = context;
		mActivityLifeStatus = mActivityStatus;
	}

	public void show(String url) {
		if (mActivityLifeStatus == null) {
			return;
		}
		SUPER_ACTIVITY_STATUS t = mActivityLifeStatus.getActivityStatus();
		if (t == SUPER_ACTIVITY_STATUS.RUN) {
			createADialog(url);
		}

	}

	private void createADialog(String url) {
		if (mDialog == null) {
			mDialog = new Dialog(mContext);

			params.gravity = Gravity.CENTER | Gravity.CENTER;
			mDialog.addContentView(mView, params);
			mDialog.setTitle("Loding data");

			mDialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					Log.d("1234", "dddd");
					if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
						Log.d("1234", "dddd1");
						dismiss();
					}
					return false;
				}

			});

			try {
				mDialog.show();
			} catch (Exception e) {
				mDialog = null;
			}
		}
	}

	public void dismiss() {
		Log.d("1234", "dismiss");
		if (mDialog != null && mDialog.isShowing()) {
			try {
				mDialog.dismiss();
				mDialog = null;
			} catch (IllegalArgumentException e) {
				mDialog = null;
			}
		}
	}

	private class WebViewTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				progressWebView.loadUrl(params[0]);

				progressWebView.getSettings().setJavaScriptEnabled(true);
				progressWebView.setWebViewClient(new SuperWebViewClient());
				// mWebView.canGoBackOrForward(10);
			} catch (RuntimeException e) {
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
			// getProgressDialog().show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			// getProgressDialog().dismiss();
		}
	}


}
