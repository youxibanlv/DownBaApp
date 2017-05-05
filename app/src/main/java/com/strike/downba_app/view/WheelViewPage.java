package com.strike.downba_app.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.strike.downba_app.adapter.ImageAdapter;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by strike on 16/6/9.
 */
public class WheelViewPage extends LinearLayout {
    private static final int UPTATE_VIEWPAGER = 0;

    @ViewInject(R.id.vp_hottest)
    private ViewPager vp_hottest;

    @ViewInject(R.id.ll_hottest_indicator)
    private LinearLayout ll_hottest_indicator;

    private Context context;

    private int autoCurrIndex = 0;

    private ImageView[] mBottomImages;//底部只是当前页面的小圆点

    private List<AppAd> list = new ArrayList<>();

    private Timer timer = new Timer(); //为了方便取消定时轮播，将 Timer 设为全局

    private ImageAdapter adapter;

    private View view;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPTATE_VIEWPAGER:
                    if (msg.arg1 != 0) {
                        vp_hottest.setCurrentItem(msg.arg1);
                    } else {
                        //false 当从末页调到首页是，不显示翻页动画效果，
                        vp_hottest.setCurrentItem(msg.arg1, false);
                    }
                    break;
            }
        }
    };

    public WheelViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.extr_wheel_page, this, true);
        // 设置自动轮播图片，5s后执行，周期是5s
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPTATE_VIEWPAGER;
                if (autoCurrIndex == list.size() - 1) {
                    autoCurrIndex = -1;
                }
                message.arg1 = autoCurrIndex + 1;
                mHandler.sendMessage(message);
            }
        }, 5000, 5000);
        x.view().inject(view);
    }
    public void setViewPage(List<AppAd> pagelist) {
        list = pagelist;
        if (list.size() <1){
            return;
        }
        if(adapter == null){
            adapter = new ImageAdapter(context);
            adapter.setPages(list);
            vp_hottest.setAdapter(adapter);
        }else{
            adapter.setPages(list);
            adapter.notifyDataSetChanged();
        }
        mBottomImages = new ImageView[list.size()];
        //圆点指示器
        ll_hottest_indicator.removeAllViews();
        for (int i = 0; i < mBottomImages.length; i++) {
            ImageView imageView = new ImageView(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            params.setMargins(5,0,5,0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.mipmap.circle_select);
            } else {
                imageView.setImageResource(R.mipmap.circle_unselect);
            }
            mBottomImages[i] = imageView;
            ll_hottest_indicator.addView(mBottomImages[i]);
        }

        vp_hottest.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //图片左右滑动时候，将当前页的圆点图片设为选中状态
            @Override
            public void onPageSelected(int position) {
                int total = mBottomImages.length;
                for (int j = 0; j < total; j++) {
                    if (j == position) {
                        mBottomImages[j].setImageResource(R.mipmap.circle_select);
                    } else {
                        mBottomImages[j].setImageResource(R.mipmap.circle_unselect);
                    }
                }
                //设置全局变量，currentIndex为选中图标的 index
                autoCurrIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
