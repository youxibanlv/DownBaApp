package com.strike.downba_app.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.strike.downba_app.base.AppContext;
import com.strike.downba_app.view.library.PullToRefreshAdapterViewBase;
import com.strike.downbaapp.R;


/**
 * Created by strike on 16/5/31.
 */
public class UiUtils {

    private static String tempMessage = "";
    private static long tipTime = 0;
    private static long waitTime = 0;

    /**
     * 打卡软键盘
     *
     * @param mEditText输入框
     * @param mContext上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    /**
     * 关闭软键盘
     *
     * @param
     * @param
     */
    public static void closeKeybord(EditText mEditText, Context mContext)
    {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
    //停止刷新
    public static void stopRefresh(final PullToRefreshAdapterViewBase pull_to_refresh){
        pull_to_refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                pull_to_refresh.onRefreshComplete();
            }
        },1000);
    }

    public static void showTipToast(boolean isSuccess,String msg){
        waitTime = System.currentTimeMillis() - tipTime;
        if (TextUtils.isEmpty(msg)){
            return;
        }
        if (tempMessage.equals(msg) && waitTime<3000){
            return;
        }else{
            tempMessage = msg;
            tipTime = System.currentTimeMillis();
        }
        Toast toast = new Toast(AppContext.getContext());
        LayoutInflater inflater = (LayoutInflater) AppContext.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_error_layout,null);
        TextView mTxt = (TextView) view.findViewById(R.id.toast_msg_txt);
        Drawable leftDrawable = mTxt.getCompoundDrawables()[0];
        if (isSuccess){
            leftDrawable = AppContext.getContext().getResources().getDrawable(R.mipmap.toast_success_icon);
        }else{
            leftDrawable = AppContext.getContext().getResources().getDrawable(R.mipmap.toast_error_icon);
        }
        leftDrawable.setBounds(0,0,leftDrawable.getIntrinsicWidth(),leftDrawable.getIntrinsicHeight());
        mTxt.setCompoundDrawables(leftDrawable,mTxt.getCompoundDrawables()[1],mTxt.getCompoundDrawables()[2],
                mTxt.getCompoundDrawables()[3]);
        mTxt.setText(msg);
        toast.setView(view);
        int yoffset = (int) AppContext.getContext().getResources().getDimension(R.dimen.wdp100);
        toast.setGravity(Gravity.CENTER, 0, yoffset);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }

}
