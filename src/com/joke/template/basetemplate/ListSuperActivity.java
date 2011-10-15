package com.joke.template.basetemplate;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.joke.template.ActivityStatus;
import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.views.SuperDialog;
import com.joke.template.views.SuperWebViewDialog;

public abstract class ListSuperActivity extends ListActivity implements
		ActivityStatus {
	protected ProgressDialog m_progressDlg_base;
	private SuperDialog mProgress = null;
	private SuperWebViewDialog mWebViewProgress = null;
	private SUPER_ACTIVITY_STATUS mStatus;
	
	/**
	 * This handler is for the base activity to resolve something in the asynctask
	 */
	protected Handler m_Handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			onMsgData(msg);
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setActivityStatus(SUPER_ACTIVITY_STATUS.RUN);
		m_progressDlg_base = new ProgressDialog(this);
		m_progressDlg_base.setCancelable(true);
	}

	@Override
	public void finish() {
		super.finish();
		setActivityStatus(SUPER_ACTIVITY_STATUS.STOP);
	}

	@Override
	protected void onDestroy() {
		setActivityStatus(SUPER_ACTIVITY_STATUS.STOP);
		super.onDestroy();
	}

	protected void onResume() {
		super.onResume();
		setActivityStatus(SUPER_ACTIVITY_STATUS.RUN);
	}

	protected void onStart() {
		super.onStart();
		setActivityStatus(SUPER_ACTIVITY_STATUS.RUN);

	}

	protected void onPause() {
		setActivityStatus(SUPER_ACTIVITY_STATUS.STOP);
		super.onPause();
	}

	protected void onStop() {
		setActivityStatus(SUPER_ACTIVITY_STATUS.STOP);
		super.onStop();
	}

	@Override
	public void onMsgData(Message msg) {

		switch (msg.what) {
		case MsgHandlerType.MSG_TYPE_INIT_START:// asynctask start
		{
			InitStart(msg.arg1);
			boolean showDialog = (Boolean) msg.obj;
			if (showDialog && m_progressDlg_base != null) {
				m_progressDlg_base.show();
			}
		}

			break;
		case MsgHandlerType.MSG_TYPE_INIT_FINISH: {//asynctask is finished
			InitFinish(msg.arg1, (Integer) msg.arg2);
			boolean showDialog = (Boolean) msg.obj;
			if (showDialog && m_progressDlg_base != null) {
				m_progressDlg_base.cancel();
				m_progressDlg_base = null;
			}
		}
			break;

		case MsgHandlerType.MSG_TYPE_INIT_ERROR: {
			InitFinishError(msg.arg1, (Integer) msg.arg2);
			
			boolean showDialog = (Boolean) msg.obj;
			if (showDialog && m_progressDlg_base != null) {
				m_progressDlg_base.cancel();
				m_progressDlg_base = null;
			}
		}
			break;
		default:
			break;
		}
	}
	
	class InitAsyncClass extends AsyncTask<Void, Void, Void> {

		private Handler m_Handler;
		private int taksId;
		private boolean isShownDialog = true;
		public InitAsyncClass(int _taskId, Handler mHandler, boolean showDialog) {
			m_Handler = mHandler;
			taksId = _taskId;
			isShownDialog = showDialog;
		}

		protected void onPostExecute(String paramString) {
			
		}

		protected void onPreExecute() {
			Message msg = m_Handler.obtainMessage(MsgHandlerType.MSG_TYPE_INIT_START, taksId, 0, isShownDialog);
			m_Handler.sendMessage(msg);
		}

		@Override
		protected Void doInBackground(Void... params) {
			int status = doneAsyncTask(taksId);
			Message msg = m_Handler.obtainMessage(MsgHandlerType.MSG_TYPE_INIT_FINISH, taksId, status, isShownDialog);
			m_Handler.sendMessage(msg);
			return null;
		}
	}

	/*
	 * Prepare for the asynctask before doit function (non-Javadoc)
	 * 
	 * @see com.test.template.ActivityStatus#InitStart(int)
	 */
	@Override
	public void InitStart(int arg1) {
	}

	/**
	 * not display dialog
	 * 
	 * @param taskId
	 */
	final public void Init(int taskId) {
		new InitAsyncClass(taskId, m_Handler, false).execute();
	}

	/**
	 * show dialog or not when use asynctask
	 * 
	 * @param taskId
	 *            int the id of task
	 * @param showProgressbar
	 *            boolean display dialog or not
	 */
	final public void Init(int taskId, boolean showDialog) {
		new InitAsyncClass(taskId, m_Handler, showDialog).execute();
	}

	/*
	 * To Override this function and do you want to do in the asynctask
	 * 
	 * @see com.test.template.ActivityStatus#doneAsyncTask(int)
	 */
	@Override
	public int doneAsyncTask(int taskId) {
		return 0;
	}

	/*
	 * When your asynctask is finished, this function will be called and you
	 * should override this function when you want to do something in your
	 * activity.
	 * 
	 * @see com.test.template.ActivityStatus#InitFinish(int, int)
	 */
	@Override
	public void InitFinish(int taskId, int init_status) {
	}

	/*
	 * When something is wrong in your asynctask, this function will be called,
	 * and you should override this function to resolve the problem or not.
	 * 
	 * @see com.test.template.ActivityStatus#InitFinishError(int, int)
	 */
	@Override
	public void InitFinishError(int taskId, int init_status) {
	}
	
	public SuperDialog getProgressBar(Context context) {
		if (mProgress == null) {
			mProgress = new SuperDialog(context, this);
		}
		return mProgress;
	}
	
	public SuperDialog getProgressDialog() {
		if (mProgress == null)
			mProgress = new SuperDialog(this, this);
		return mProgress;
	}
	
	public SuperWebViewDialog getWebViewProgressBar(Context context) {
		if (mWebViewProgress == null) {
			mWebViewProgress = new SuperWebViewDialog(context, this);
		}
		return mWebViewProgress;
	}
	
	public SuperWebViewDialog getWebViewProgressDialog() {
		if (mWebViewProgress == null)
			mWebViewProgress = new SuperWebViewDialog(this, this);
		return mWebViewProgress;
	}
	
	
	public void setActivityStatus(SUPER_ACTIVITY_STATUS status) {
		mStatus = status;
	}

	public SUPER_ACTIVITY_STATUS getActivityStatus() {
		return mStatus;
	}
}
