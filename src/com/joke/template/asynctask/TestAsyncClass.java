package com.joke.template.asynctask;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.joke.template.handlertype.MsgHandlerType;

public class TestAsyncClass extends AsyncTask<Void, Void, Void> {

	private Handler m_Handler;
	private int taksId;
	private boolean isShownDialog = true;
	public TestAsyncClass(int _taskId, Handler mHandler, boolean showDialog) {
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
		Message msg = m_Handler.obtainMessage(MsgHandlerType.MSG_TYPE_INIT_RUNNING, taksId, 0, isShownDialog);
		m_Handler.sendMessage(msg);
		return null;
	}
}