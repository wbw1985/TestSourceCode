package com.joke.template.weibooperation;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import weibo4andriod.Comment;
import weibo4andriod.Status;
import weibo4andriod.Weibo;
import weibo4andriod.WeiboException;
import weibo4andriod.androidexamples.OAuthConstant;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.joke.template.handlertype.MsgHandlerType;
import com.joke.template.utils.StringUtils;

public class WeiboOperation {
	// 分享线程GlobeConfData
	private static Handler mHandler = null;
	private static String accessToken = null;
	private static String accessSecret = null;
	private static String shareMsg = null;
	private static String mFilepath = null;

	private static boolean sendOrCommentAtSametime = false;// 为false时表示只发布评论，否则只转发微博
	private static int weiboType = MsgHandlerType.WEIBO_TYPE_SEND;
	private static WeiboInfo mWeiboInfo = null;

	/**
	 * 
	 * @param _mHandler
	 *            返回消息的句柄
	 * @param _shareMsg
	 *            需要发送的消息
	 * @param filepath
	 *            需要附带的图片
	 * @param _sendOrCommentAtSametime
	 *            是否同时发布评论或者微博
	 * @param weibotype
	 *            微博的类型，分三种（1）转发微博（2）评论微博（3）发表微博;在MsgHandlerType类里定义
	 */
	public static void sendWeibo(Handler _mHandler, String _shareMsg,
			String filepath, boolean _sendOrCommentAtSametime, int weibotype,
			WeiboInfo _mWeiboInfo) {
		mHandler = _mHandler;
//		GlobeConfData.getInstance().LoadConf();
//		accessToken = GlobeConfData.getInstance().GetSinaWeicoToken();
//		accessSecret = GlobeConfData.getInstance().GetSinaWeicoTokenSecret();
		shareMsg = _shareMsg;
		mFilepath = filepath;
		sendOrCommentAtSametime = _sendOrCommentAtSametime;

		weiboType = weibotype;
		mWeiboInfo = _mWeiboInfo;

		Thread thread = new Thread(sendWeibo);
		thread.start();
	}

	/**
	 * 发布微博
	 */
	static Runnable sendWeibo = new Runnable() {

		@Override
		public void run() {

			if (accessToken == null || accessSecret == null) {
				Message msg = new Message();
				msg.obj = -1;
				msg.what = MsgHandlerType.MSG_TYPE_INIT_ERROR;
				mHandler.sendMessage(msg);
			} else {
				Weibo weibo = OAuthConstant.getInstance().getWeibo();
				weibo.setToken(accessToken, accessSecret);
				try {
					if (MsgHandlerType.WEIBO_TYPE_COMMENT == weiboType) {// 发布微博评论

						sendWeiboComment(weibo);

						if (sendOrCommentAtSametime) {// 同时发布一条微博
							reTweetWeibo(weibo);
						}
					} else if (MsgHandlerType.WEIBO_TYPE_RETWEET == weiboType) {// 转发微博
						reTweetWeibo(weibo);
						
						if (sendOrCommentAtSametime) {// 同时当做评论发布
							sendWeiboComment(weibo);
						}
					} else if (MsgHandlerType.WEIBO_TYPE_SEND == weiboType) {// 发布一条新微博
						
						sendWeibo(weibo);
					} else {
						// TODO 未知类型，暂不做处理

					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("WeiboPub", e.getMessage());
					Message msg = new Message();
					msg.what = MsgHandlerType.MSG_TYPE_INIT_ERROR;
					msg.obj = -1;
					mHandler.sendMessage(msg);
				} catch (WeiboException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("WeiboPub", e.getMessage());
					Message msg = new Message();
					msg.what = MsgHandlerType.MSG_TYPE_INIT_ERROR;
					msg.obj = -1;
					mHandler.sendMessage(msg);
				}
			}
		}
	};

	/**
	 * 发送微博
	 * 
	 * @param weibo
	 * @throws UnsupportedEncodingException
	 * @throws WeiboException
	 */
	private static void sendWeibo(Weibo weibo)
			throws UnsupportedEncodingException, WeiboException {
		String msg = shareMsg.toString();
		if (msg.getBytes().length != msg.length()) {
			msg = URLEncoder.encode(msg, "UTF-8");
		}

		Status status = null;
		if (StringUtils.isBlank(mFilepath)) {
			status = weibo.updateStatus(msg);
		} else {
			File file = new File(mFilepath);
			status = weibo.uploadStatus(msg, file);
		}

		if (status != null) {
			Message msg2 = new Message();
			msg2.what = MsgHandlerType.MSG_TYPE_INIT_FINISH;
			msg2.obj = 1;
			mHandler.sendMessage(msg2);
		}
	}

	/**
	 * 评论微博
	 * 
	 * @param weibo
	 * @throws WeiboException
	 * @throws UnsupportedEncodingException
	 */
	private static void sendWeiboComment(Weibo weibo) throws WeiboException,
			UnsupportedEncodingException {
		String msg = shareMsg.toString();
		if (msg.getBytes().length != msg.length()) {
			msg = URLEncoder.encode(msg, "UTF-8");
		}

		Comment status = weibo.updateComment("pppppppppp",
				mWeiboInfo.m_id + "", null);

		if (status != null) {
			Message msg2 = new Message();
			msg2.what = MsgHandlerType.MSG_TYPE_INIT_FINISH;
			msg2.obj = 1;
			mHandler.sendMessage(msg2);
		}
	}

	/**
	 * 转发微博
	 * 
	 * @param weibo
	 * @throws WeiboException
	 * @throws UnsupportedEncodingException
	 */
	private static void reTweetWeibo(Weibo weibo) throws WeiboException,
			UnsupportedEncodingException {
		String msg = shareMsg.toString();
		if (msg.getBytes().length != msg.length()) {
			msg = URLEncoder.encode(msg, "UTF-8");
		}

		Status status = weibo.repost(mWeiboInfo.m_id + "", msg);
		if (status != null) {
			Message msg2 = new Message();
			msg2.what = MsgHandlerType.MSG_TYPE_INIT_FINISH;
			msg2.obj = 1;
			mHandler.sendMessage(msg2);
		}
	}
}
