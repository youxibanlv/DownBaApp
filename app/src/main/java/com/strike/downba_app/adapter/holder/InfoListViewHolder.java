package com.strike.downba_app.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by strike on 2016/11/26.
 */

public class InfoListViewHolder {
    @ViewInject(R.id.ll_info)
    public LinearLayout ll_info;

    @ViewInject(R.id.icon)
    public ImageView icon;

    @ViewInject(R.id.title)
    public TextView title;

    @ViewInject(R.id.body)
    public TextView body;

    @ViewInject(R.id.visitors)
    public TextView visitors;

    public InfoListViewHolder(View view){
        x.view().inject(this,view);
    }
}
