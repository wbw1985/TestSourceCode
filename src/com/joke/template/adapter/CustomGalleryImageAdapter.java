package com.joke.template.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.joke.template.R;
import com.joke.template.globalstatic.GlobalStatic;
import com.joke.template.logs.SuperLogs;
import com.joke.template.net.BasicInfomation;
import com.joke.template.utils.FileUtils;
import com.joke.template.utils.ImageTools;

public class CustomGalleryImageAdapter extends BaseAdapter {

	private Context mContext; // 定义Context

	private ArrayList<String> mImageIds = null;
	private String descriptions = null;

	
	
//	private int mTextPosx = 0;// x坐标
//	private int mTextPosy = 0;// y坐标
//	private int mTextWidth = 0;// 绘制宽度
//	private int mTextHeight = 0;// 绘制高度
//	private int mFontHeight = 0;// 绘制字体高度
//	private int mPageLineNum = 0;// 每一页显示的行数
//	private int mCanvasBGColor = 0;// 背景颜色
//	private int mFontColor = 0;// 字体颜色
//	private int mAlpha = 0;// Alpha值
//	private int mRealLine = 0;// 字符串真实的行数
//	private int mCurrentLine = 0;// 当前行
//	private int mTextSize = 0;// 字体大小
//	private String mStrText = "";
//	private Vector<String> mString = null;
//	private Paint mPaint = null;
	
	public CustomGalleryImageAdapter(Context c, Handler oldHandler, ArrayList<String> picUrltList, String discription) { // 声明
		descriptions = discription;																			// ImageAdapter
		mContext = c;
		mImageIds = picUrltList;
	}

	public int getCount() { // 获取图片的个数
		return mImageIds.size();
	}

	public String getItem(int position) {// 获取图片在库中的位置
		return mImageIds.get(position);
	}
	
	public String getName(int position) {// 获取图片在库中的位置
		SuperLogs.info("Filenameff=" + mImageIds.get(position));
		if(mImageIds.get(position).contains("aid")){
			return  FileUtils.getUrlFileName_phpid(mImageIds.get(position));
		}else{
			return  FileUtils.getUrlFileName(mImageIds.get(position));
		}
	
	}

	public long getItemId(int position) {// 获取图片在库中的位置
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		
		if(position == 0){
			descriptions = descriptions.replace("<br/>", "\n");
			descriptions = descriptions.replace("<br />", "\n");
			
			InputStream is = mContext.getResources().openRawResource(R.drawable.activity_template_background);      
			Bitmap mBitmap = BitmapFactory.decodeStream(is);
			
			Bitmap newb = ImageTools.GetTextIfon(descriptions, mContext, mBitmap);
	        
	        if (newb != null) {
	        	 Drawable drawable = new BitmapDrawable(newb);      
	 	         i.setBackgroundDrawable(drawable);  
			} else {
				i.setImageResource(R.drawable.face28);// 给ImageView设置资源
			}
		}else{
			String imageName;
			if(mImageIds.get(position).contains("aid")){
				imageName = FileUtils.getUrlFileName_phpid(mImageIds.get(position));
			}else{
				imageName = FileUtils.getUrlFileName(mImageIds.get(position));
			}
			
			SuperLogs.info("imageName=" + imageName);
			Bitmap cover = null;
			if (FileUtils
					.isFileExist(GlobalStatic.HEAD_IMG_FOLDER_PATH + imageName)) {
				try {
					cover = BitmapFactory
							.decodeFile(GlobalStatic.HEAD_IMG_FOLDER_PATH
									+ imageName);
				} catch (OutOfMemoryError e) {
					cover = null;
				}
				if(cover != null && cover.getWidth() == -1){
					//如果读出的图片宽度为-1，说明图片错误，将其删除
					FileUtils.deleteFile(GlobalStatic.HEAD_IMG_FOLDER_PATH + imageName);
				}
			}
			if (cover != null) {
				i.setImageBitmap(cover);// 给ImageView设置资源
			} else {
				i.setImageResource(R.drawable.face28);// 给ImageView设置资源
			}
		}
		

		i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));// 设置布局
																// 图片200×200显示
		i.setScaleType(ImageView.ScaleType.FIT_XY);// 设置比例类型

		return i;
	}
	
	
}
