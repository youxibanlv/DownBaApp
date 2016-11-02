package com.strike.downba_app.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.strike.downba_app.adapter.ChoiceUpLoadAdapter;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


public class CustomPopupWindow extends PopupWindow{
	private View view;
	LayoutInflater inflater;
	ChoiceUpLoadAdapter adapter;
    @ViewInject(R.id.lv_choice_upload)
	public ListView lv_choice_upload;

    @ViewInject(R.id.tv_cancel_send)
    private TextView tv_cancel_send;

	public CustomPopupWindow(Context context) {
		inflater = LayoutInflater.from(context);
		showPopupWindow(context);
	}

	// 弹出popupWindow
	private void showPopupWindow(Context context) {
		// 1.利用layoutInflater获得View对象
		view = inflater.inflate(R.layout.ppw_search_way, null);
        x.view().inject(this,view);
		adapter = new ChoiceUpLoadAdapter(context);
		lv_choice_upload.setAdapter(adapter);
		setContentView(view);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		// 3.设置popupWindow弹出窗体可点击空白处取消,setFocusable为true
		setFocusable(true);// 设置为点击空白处消失
		// 4.实例化一个colorDrawable颜色为半透明0xb0000000popupWindow背景设置为半透明
		ColorDrawable drawable = new ColorDrawable(0xb0000000);
		setBackgroundDrawable(drawable);
		// 5.设置动画的显示和消失
		setAnimationStyle(R.style.mypopwindow_anim_style);
		// 设置软键盘不能覆盖editText
		setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
		setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        tv_cancel_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

	}
	/*
	 * 刷新视图
	 */
	public void refresh(List<ChoiceBean> list) {
		adapter.refresh(list);
	}

}
