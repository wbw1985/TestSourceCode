package com.joke.template.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class Utils {
	public static final int INVALID = -1;
	
	public static long getBucketIdFromUri(final ContentResolver cr, final Uri uri) {
        if (uri.getScheme().equals("file")) {
            String string = "/";
            List<String> paths = uri.getPathSegments();
            int numPaths = paths.size();
            for (int i = 0; i < numPaths - 1; ++i) {
                string += paths.get(i);
                if (i != numPaths - 2)
                    string += "/";
            }
            return getBucketId(string);
        } else {
            Cursor cursor = null;
            try {
                long id = ContentUris.parseId(uri);
                cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns.BUCKET_ID }, MediaStore.Images.ImageColumns._ID + "=" + id,
                        null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        long setVal = cursor.getLong(0);
                        cursor.close();
                        return setVal;
                    }
                }
                cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Video.VideoColumns.BUCKET_ID }, MediaStore.Images.ImageColumns._ID + "=" + id,
                        null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        long setVal = cursor.getLong(0);
                        cursor.close();
                        return setVal;
                    }
                }
            } catch (Exception e) {
                ;
            }
            return INVALID;
        }
    }
	
	/**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    public static int getBucketId(String path) {
        return (path.toLowerCase().hashCode());
    }
    

	public static String getRandomString(int length){ 
	    StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); 
	    StringBuffer sb = new StringBuffer(); 
	    Random r = new Random(); 
	    int range = buffer.length(); 
	    for (int i = 0; i < length; i ++){ 
	        sb.append(buffer.charAt(r.nextInt(range))); 
	    } 
	    return sb.toString();
	}
	
	public static List<String> split_string(final String str_split,final char c){ 
		List<String> list = new ArrayList<String>();
		int nCurPos = 0;
		int nPos = 0;
		while((nPos = str_split.indexOf(c, nCurPos)) >= 0)
		{
			if(nPos > nCurPos || 0 == nCurPos){
				String substr = str_split.substring(nCurPos, nPos).trim();
				list.add(substr);				
			}
			nCurPos = nPos + 1;
		}
		if(nCurPos < str_split.length() - 1){
			String substr = str_split.substring(nCurPos).trim();
			list.add(substr);			
		}
		return list;
	}
	
	public static int SafeParseInt(String str) {
		if(null == str){
			return 0;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
