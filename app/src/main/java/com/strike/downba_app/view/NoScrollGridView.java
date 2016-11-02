package com.strike.downba_app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/*****
 * 重写了gridview的 onMeasure方法
 * ***/
public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context) {
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	 @Override  
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	                MeasureSpec.AT_MOST);  
	        super.onMeasure(widthMeasureSpec, expandSpec);  
	    }  

	 
}
