package com.joke.template.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class FaceAdapter extends BaseAdapter {
	private static int[] faceList;
	private Context mContecxt;
	private static int _position = 0;

	public FaceAdapter(Context _mContecxt, int[] facelist2) {
		mContecxt = _mContecxt;
		faceList = facelist2;
	}
	
	public static void setPosition(int position, int[] facelist2){
		int eachpage = facelist2.length/2;
		_position = position;
		if(_position < eachpage){
			for(int i = 0; i < eachpage; i++){
				faceList[i] = facelist2[i];
			}
		}else{
			for(int i = eachpage; i < facelist2.length; i++){
				faceList[i] = facelist2[i];
			}
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return faceList.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return faceList[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		
		if (convertView == null) {
			imageView = new ImageView(mContecxt);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setLayoutParams(new GridView.LayoutParams(30, 30));
		   } else {
			   imageView = (ImageView) convertView;
		   }
		
		Drawable yy = mContecxt.getResources().getDrawable(faceList[position]);
		Bitmap bitmap =  ((BitmapDrawable)yy).getBitmap();
		Bitmap map2 = Bitmap.createBitmap(bitmap);//Utils.createSizeImage(faceList.get(position), 75, 100);// 设置图片显示长宽
																			// 真实图片
//		imageView.setImageBitmap(Utils.overlay(unSelected, map2)); // 图片框架和真实图片叠加显示
		imageView.setImageBitmap(map2); 
		return imageView;
	}
	
	//图片叠加方法
	public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
	  Bitmap bmOverlay = Bitmap.createBitmap(100,
	    125, Config.ARGB_8888);
	  Canvas canvas = new Canvas(bmOverlay);
	  canvas.drawBitmap(bmp1, 0, 0, null);
	  canvas.drawBitmap(bmp2, 12, 12, null);
	  canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
	  canvas.restore();// 存储
	  return bmOverlay;
	 } 

}
