package com.joke.template.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Gallery Tools
 * One time fling one picture in the gallery
 * @author wangbaowei.wei
 * Created at 2011-10-12
 */
public class CustomGallery extends Gallery{

	public CustomGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CustomGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.setStaticTransformationsEnabled(true);
	}

	public CustomGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setStaticTransformationsEnabled(true);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
}
