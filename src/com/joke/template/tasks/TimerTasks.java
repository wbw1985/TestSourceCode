package com.joke.template.tasks;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

public class TimerTasks extends TimerTask {
	private static Handler mHandler = null;
	private static Timer mTimer = null;
	private static int timeTaskType = -1;
	private static int stopTaskType = -1000;
	private static TimerTasks mTimerTask;

	/**
	 * 每隔一段时间就会执行这个任务直到停止 _mHandler是负责传递消息回去
	 */
	public TimerTasks(Handler _mHandler, int _taskType, int _stopTaskType) {
		mHandler = _mHandler;
		timeTaskType = _taskType;
		stopTaskType= _stopTaskType;
	}

	@Override
	public void run() {
		//TODO 需要执行的动作
		//TODO 
		Message msg = new Message();
		msg.what = timeTaskType;
		mHandler.sendMessage(msg);
	}

	public static void startThisTask(Handler _mHandler, int _taskType, int _stopTaskType) {

		if (mTimer == null) {
			mTimerTask = new TimerTasks(_mHandler, _taskType, stopTaskType);
			// mTimerTask.scheduledExecutionTime();
			mTimer = new Timer();

			// 第一个参数为执行的mTimerTask
			// 第二个参数为延迟的时间 这里写1000的意思是mTimerTask将延迟1秒执行
			// 第三个参数为多久执行一次 这里写1000表示每1秒执行一次mTimerTask的Run方法
			mTimer.schedule(mTimerTask, 1000, 1000);
		}

	}

	public static void stopThisTask() {
		// 在这里关闭mTimer 与 mTimerTask
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask = null;
		}

		/** ID重置 **/

		// 这里发送一条只带what空的消息
		mHandler.sendEmptyMessage(stopTaskType);
	}
}
