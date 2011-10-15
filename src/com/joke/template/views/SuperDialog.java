package com.joke.template.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.joke.template.ActivityStatus;
import com.joke.template.ActivityStatus.SUPER_ACTIVITY_STATUS;
import com.joke.template.R;

/**
 * Public Dialog in the super template
 * you can use it through getProgressDialog().show().
 * @author wangbaowei.wei
 * Created at 2011-10-12
 */
public class SuperDialog implements OnTouchListener{
	private ActivityStatus mActivityLifeStatus = null;
	private Context mContext = null;
	private Dialog mDialog;
	private ImageView progressImage;
	private View mView;

	private static WindowManager.LayoutParams params = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.FILL_PARENT,
			WindowManager.LayoutParams.FILL_PARENT,
			WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
			WindowManager.LayoutParams.ALPHA_CHANGED, PixelFormat.TRANSLUCENT);

	public SuperDialog(Context context,
			ActivityStatus mActivityStatus) {
		mContext = context;
		mActivityLifeStatus = mActivityStatus;
	}

	public void show() {
		if (mActivityLifeStatus == null) {
			return;
		}
		 SUPER_ACTIVITY_STATUS t = mActivityLifeStatus.getActivityStatus() ;
		if (t == SUPER_ACTIVITY_STATUS.RUN) {
			createADialog();
		}
	}

	private void createADialog() {
		if (mDialog == null) {
			mDialog = new Dialog(mContext);
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = inflater.inflate(R.layout.super_processbar,
					(ViewGroup) ((Activity) mContext)
							.findViewById(R.id.layout_root));
			progressImage = (ImageView) mView.findViewById(R.id.progress_image);

			params.gravity = Gravity.CENTER | Gravity.CENTER;
			mDialog.addContentView(mView, params);
			mDialog.setTitle("Loding data");
			
			mDialog.setOnKeyListener(new OnKeyListener(){

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
						dismiss();
					}
					return false;
				}
			
			});
//			mDialog.setCanceledOnTouchOutside(true);

			try {
				mDialog.show();
				twinklButton(progressImage);
			} catch (Exception e) {
				mDialog = null;
			}
		}
	}

	public void dismiss() {
		if (mDialog != null && mDialog.isShowing()) {
			try {
				mDialog.dismiss();
				mDialog = null;
			} catch (IllegalArgumentException e) {
				mDialog = null;
			}

		}

	}

	private void twinklButton(View v) {
		AlphaAnimation anim = null;
		anim = new AlphaAnimation(0.4f, 1.0f);
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setDuration(1500);
		anim.setStartOffset(0);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		AnimationSet am = new AnimationSet(false);
		am.addAnimation(anim);
		v.setAnimation(am);

		am.startNow();

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		dismiss();
		return false;
	}

}
