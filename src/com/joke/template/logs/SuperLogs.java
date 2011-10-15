package com.joke.template.logs;

import android.util.Log;

public class SuperLogs {
	private static boolean Loginfo = true;
	private static String LoginfoLablel = "****loginfo****";
	
	public static void info(String info){
		if(Loginfo){
			Log.i(LoginfoLablel, info);
		}
	}
}
