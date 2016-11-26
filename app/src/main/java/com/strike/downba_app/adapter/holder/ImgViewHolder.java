package com.strike.downba_app.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by strike on 2016/11/26.
 */

public class ImgViewHolder {
    @ViewInject(R.id.icon)
    public ImageView icon;

    public ImgViewHolder(View view){
        x.view().inject(this,view);
    }
}
