package com.strike.downba_app.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
/**
 *用于显示图片查看器
 *@author strike
 * **/
public class ImageViewPager extends ViewPager {

	private static final String TAG = "ImageViewPager";
	
	public ImageViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ImageViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		try {
			return super.onInterceptTouchEvent(arg0);
		} catch (IllegalArgumentException  e) {
			 // 不理会  
            Log.e(TAG, "ImagerViewPager viewpager error1");  
            return false;  
		}catch (ArrayIndexOutOfBoundsException  e) {
			 Log.e(TAG, "ImagerViewPager viewpager error2");  
	            return false; 
		}
		
	}

}
