package com.strike.downba_app.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by strike on 2016/11/26.
 */

public class AppListViewHolder {
    @ViewInject(R.id.ll_item)
    public LinearLayout ll_item;

    @ViewInject(R.id.iv_app_icon)
    public ImageView iv_app_icon;

    @ViewInject(R.id.tv_app_title)
    public TextView tv_app_title;

    @ViewInject(R.id.app_score)
    public RatingBar app_score;

    @ViewInject(R.id.app_size)
    public TextView app_size;

    @ViewInject(R.id.app_version)
    public TextView app_version;

    @ViewInject(R.id.tv_des)
    public TextView tv_des;

    @ViewInject(R.id.tv_down)
    public TextView tv_down;

    @ViewInject(R.id.tv_down_num)
    public TextView tv_down_num;

    public AppListViewHolder(View view){
        x.view().inject(this,view);
    }
}
