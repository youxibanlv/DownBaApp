package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.http.bean.Subject;
import com.strike.downba_app.utils.Constance;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/8.
 */
public class ImageAdapter extends PagerAdapter {

    private List<AppAd> pages;
    private List<ImageView> imageViews;
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public void setPages(List<AppAd> list){
        pages = list;
        imageViews = new ArrayList<>();
        ImageOptions.Builder builder = new ImageOptions.Builder();
        builder.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageOptions options = builder.build();
        if (list!= null && list.size()>0){
            for (AppAd wheelPage : list) {
                ImageView imageView = new ImageView(context);
                if (!TextUtils.isEmpty(wheelPage.getLogo())){
                    x.image().bind(imageView, wheelPage.getLogo(),options);
                }
                imageViews.add(imageView);

            }
        }
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(imageViews.get(position));
        ImageView view = imageViews.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailsActivity.class);
                Bundle bundle = new Bundle();
                AppAd appAd = pages.get(position);
                Subject sb = appAd.getSubject();
                if (sb == null){
                    return;
                }else {
                    switch (sb.getSb_type()){
                        case Constance.SB_ONE_APP://跳转详情

                            break;
                        case Constance.SB_LIST_APP://app列表

                            break;
                        case Constance.SB_APP_INFO://app+资讯

                            break;
                    }
                }
                context.startActivity(intent);
            }
        });
        return imageViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView(imageViews.get(position));
    }


}
