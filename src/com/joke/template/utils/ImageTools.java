package com.joke.template.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.Typeface;

import com.joke.template.activity.JokeContentGalleryActivity;

/***
 * Tools about image.
 */
public class ImageTools {

    /**
     * detect bytes's image type by file
     * @param fileItem
     * @return
     * @see #getImageType(byte[])
     * 
     */
	private byte[] content;
	private String name;
	private String ImageType;
	
	public ImageTools(String name,byte[] content) throws Exception{
		String imgtype=getImageType(content);
		
	    if(imgtype!=null&&(imgtype.equalsIgnoreCase("image/gif")||imgtype.equalsIgnoreCase("image/png")
	            ||imgtype.equalsIgnoreCase("image/jpeg"))){
	    	this.content=content;
	    	this.name=name;
	    	this.ImageType=imgtype;
	    }else{
	    	throw new IllegalStateException(
            "Unsupported image type, Only Suport JPG ,GIF,PNG!");
	    }
	}
	
	public byte[] getContent() {
		return content;
	}
	public String getName() {
		return name;
	}
	public String getImageType() {
		return ImageType;
	}
    public static String getImageType(File file){
        if(file == null||!file.exists()){
            return null;
        }
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            String type = getImageType(in);
            return type;
        } catch (IOException e) {
            return null;
        }finally{
            try{
                if(in != null){
                    in.close();
                }
            }catch(IOException e){
            }
        }
    }
    
    /**
     * detect bytes's image type by inputstream
     * @param in
     * @return
     * @see #getImageType(byte[])
     */
    public static String getImageType(InputStream in) {
        if(in == null){
            return null;
        }
        try{
            byte[] bytes = new byte[8];
            in.read(bytes);
            return getImageType(bytes);
        }catch(IOException e){
            return null;
        }
    }

    /**
     * detect bytes's image type
     * @param bytes 2~8 byte at beginning of the image file  
     * @return image mimetype or null if the file is not image
     */
    public static String getImageType(byte[] bytes) {
        if (isJPEG(bytes)) {
            return "image/jpeg";
        }
        if (isGIF(bytes)) {
            return "image/gif";
        }
        if (isPNG(bytes)) {
            return "image/png";
        }
        if (isBMP(bytes)) {
            return "application/x-bmp";
        }
        return null;
    }

    private static boolean isJPEG(byte[] b) {
        if (b.length < 2) {
            return false;
        }
        return (b[0] == (byte)0xFF) && (b[1] == (byte)0xD8);
    }

    private static boolean isGIF(byte[] b) {
        if (b.length < 6) {
            return false;
        }
        return b[0] == 'G' && b[1] == 'I' && b[2] == 'F' && b[3] == '8'
                && (b[4] == '7' || b[4] == '9') && b[5] == 'a';
    }

    private static boolean isPNG(byte[] b) {
        if (b.length < 8) {
            return false;
        }
        return (b[0] == (byte) 137 && b[1] == (byte) 80 && b[2] == (byte) 78
                && b[3] == (byte) 71 && b[4] == (byte) 13 && b[5] == (byte) 10
                && b[6] == (byte) 26 && b[7] == (byte) 10);
    }

    private static boolean isBMP(byte[] b) {
        if (b.length < 2) {
            return false;
        }
        return (b[0] == 0x42) && (b[1] == 0x4d);
    }

    /**
	 * 绘制文字到图片上
	 * 得到字符串信息包括行数，页数等信息
	 * @param mPaint 
	 * @param mTextSize 
	 * @param mAlpha 
	 * @param mFontColor 
	 * @param mCanvasBGColor 
	 * @param mTextHeight 
	 * @param mTextWidth 
	 * @param mTextPosy 
	 * @param mTextPosx 
	 * @param mStrText 
	 */
	public static void GetTextIfon(Paint mPaint, Bitmap newb,Vector<String> mString, String mStrText, int mTextPosx, int mTextPosy, int mTextWidth, int mTextHeight, int mCanvasBGColor, int mFontColor, int mAlpha, int mTextSize) {
		char ch;
		int w = 0;
		int istart = 0;
		int mRealLine = 0;
		FontMetrics fm = mPaint.getFontMetrics();// 得到系统默认字体属性
		int mFontHeight = (int) (Math.ceil(fm.descent - fm.top) + 2);// 获得字体高度
		int mPageLineNum = mTextHeight / mFontHeight;// 获得行数
		int count = mStrText.length();
		for (int i = 0; i < count; i++) {
			ch = mStrText.charAt(i);
			float[] widths = new float[1];
			String str = String.valueOf(ch);
			mPaint.getTextWidths(str, widths);
			if (ch == '\n') {
				mRealLine++;// 真实的行数加一
				mString.addElement(mStrText.substring(istart, i));
				istart = i + 1;
				w = 0;
			} else {
				w += (int) Math.ceil(widths[0]);
				if (w > mTextWidth) {
					mRealLine++;// 真实的行数加一
					mString.addElement(mStrText.substring(istart, i));
					istart = i;
					i--;
					w = 0;
				} else {
					if (i == count - 1) {
						mRealLine++;// 真实的行数加一
						mString.addElement(mStrText.substring(istart,
								count));
					}
				}
			}
		}
		
		// 绘制字符串
		Canvas canvas = new Canvas( newb );      
		canvas.drawColor(Color.alpha(0)); 
        int mCurrentLine = 0;
		for (int i = mCurrentLine, j = 0; i < mRealLine; i++, j++) {
			if (j > mPageLineNum) {
				break;
			}
			canvas.drawText((String) (mString.elementAt(i)), mTextPosx,
					mTextPosy + mFontHeight * j, mPaint);
		}
	}
	
	 /**
	 * 绘制文字到图片上
	 * 得到字符串信息包括行数，页数等信息
	 * @param mPaint 
	 * @param mTextSize 
	 * @param mAlpha 
	 * @param mFontColor 
	 * @param mCanvasBGColor 
	 * @param mTextHeight 
	 * @param mTextWidth 
	 * @param mTextPosy 
	 * @param mTextPosx 
	 * @param mStrText 
	 */
	static Vector<String> mString = null;
	public static Bitmap GetTextIfon(String description, Context mContext, Bitmap srcBitmap) {
		if(description == null)
			return null;
		if(mString == null)
			mString = new Vector<String>();
		if(!mString.isEmpty())
			mString.clear();
		Bitmap newb = Bitmap.createBitmap( JokeContentGalleryActivity.displayWidthOfScreen, JokeContentGalleryActivity.displayWidthOfScreen, Config.ARGB_8888 );      
            
        Paint mPaint = new Paint();    
        String familyName = "宋体";      //Typeface.SERIF
        Typeface font = Typeface.create(familyName, Typeface.BOLD);     
//        Typeface font = Typeface.createFromAsset(mContext.getAssets(),
//        "fonts/samplefont.ttf");
        mPaint.setTypeface(font);      
        mPaint.setTextSize(35f);
        mPaint.setColor(Color.parseColor("#D6BA6B"));
//		mPaint..setTextAlign(Align.CENTER);//.setColor();    //R.color.gold
		String mStrText = description;
		int mTextPosx = 30;
		int mTextPosy = 100;
		int mTextWidth = JokeContentGalleryActivity.displayWidthOfScreen - mTextPosx;
		int mTextHeight = JokeContentGalleryActivity.displayHeightOfScreen;
		char ch;
		int w = 0;
		int istart = 0;
		int mRealLine = 0;
		FontMetrics fm = mPaint.getFontMetrics();// 得到系统默认字体属性
		int mFontHeight = (int) (Math.ceil(fm.descent - fm.top) + 20);// 获得字体高度
		int mPageLineNum = mTextHeight / mFontHeight;// 获得行数
		int count = mStrText.length();
		mString.addElement("题记：");
		for (int i = 0; i < count; i++) {
			ch = mStrText.charAt(i);
			float[] widths = new float[1];
			String str = String.valueOf(ch);
			mPaint.getTextWidths(str, widths);
			if (ch == '\n') {
				mRealLine++;// 真实的行数加一
				mString.addElement(mStrText.substring(istart, i));
				istart = i + 1;
				w = 0;
			} else {
				w += (int) Math.ceil(widths[0]);
				if (w > mTextWidth) {
					mRealLine++;// 真实的行数加一
					mString.addElement(mStrText.substring(istart, i));
					istart = i;
					i--;
					w = 0;
				} else {
					if (i == count - 1) {
						mRealLine++;// 真实的行数加一
						mString.addElement(mStrText.substring(istart,
								count));
					}
				}
			}
		}
		
		// 绘制字符串
		Canvas canvas = new Canvas( newb );  
		canvas.drawColor(Color.alpha(0)); 
		Paint paint = new Paint();      
		Rect oldrec = new Rect();
		oldrec.left = 0;
		oldrec.top = 0;
		oldrec.right = srcBitmap.getWidth();
		oldrec.bottom = srcBitmap.getHeight();
		Rect newrec = new Rect();
		oldrec.left = 0;
		oldrec.top = 0;
		oldrec.right = JokeContentGalleryActivity.displayWidthOfScreen;
		oldrec.bottom = JokeContentGalleryActivity.displayHeightOfScreen;
		//.drawBitmap(srcBitmap, oldrec, newrec, paint);//
		canvas.drawBitmap(srcBitmap, 0, 0, paint);  
        int mCurrentLine = 0;
		for (int i = mCurrentLine, j = 0; i < mRealLine; i++, j++) {
			if (j > mPageLineNum) {
				break;
			}
			canvas.drawText((String) (mString.elementAt(i)), mTextPosx,
					mTextPosy + mFontHeight * j, mPaint);
		}
		
		return newb;
	}
	
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

}
