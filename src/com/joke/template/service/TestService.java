package com.joke.template.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class TestService extends Service{
	private Context mContext;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		String action = intent.getAction();
		if(action.equalsIgnoreCase("com.haypi.kingdom.activity.pushservice.regisger")){
			//TODO
		}

	}

}
