package com.joke.template.feedtype;

import java.util.Iterator;

public class FeedBackObject {
	public String mAction_key;
	public int mAction_confirm = -1;
	public String mErrorFeedback;// for collect error feedback
	public FeedBackObject() {
		mErrorFeedback="net error";
	}
	
	public FeedBackObject(FeedBackObject feedback) {
		//mErrorFeedback=Kingdom.app.getString(R.string.network_error);
		mAction_key = feedback.mAction_key;
		mAction_confirm = feedback.mAction_confirm;
	}
	
	public FeedBackObject(Iterator<String> itors) throws IllegalArgumentException{
		//mErrorFeedback=Kingdom.app.getString(R.string.network_error);
		if(itors.hasNext())
		{
			mAction_key = itors.next();
		}
		else
		{
			throw new IllegalArgumentException("Could not get tokens from itors.");
		}
		
		if(itors.hasNext())
		{
			mAction_confirm = Integer.parseInt(itors.next());
			
		}
		else
		{
			throw new IllegalArgumentException("Could not get tokens from itors.");
		}
	}
	@Override
	public String toString() {
		return "Feedback :" + "Action_key" + mAction_key + "  "
				+ "Action_confirm  " + mAction_confirm + "  "
				+ "Action error msg  " + mErrorFeedback;
	}
}
