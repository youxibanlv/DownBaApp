package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.http.entity.WheelPage;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downbaapp.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/8.
 */
public class ImageAdapter extends PagerAdapter {

    private List<WheelPage> pages;
    private List<ImageView> imageViews;
    private Context context;

    public ImageAdapter(Context context,List<WheelPage> list) {
        this.pages = list;
        this.context = context;
        imageViews = new ArrayList<>();
        ImageOptions.Builder builder = new ImageOptions.Builder();
        builder.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageOptions options = builder.build();
        if (list!= null && list.size()>0){
            for (WheelPage wheelPage : list) {
                ImageView imageView = new ImageView(context);
                x.image().bind(imageView, UrlConfig.BASE_URL+wheelPage.getResource_url(),options);
                imageViews.add(imageView);
            }
        }

    }

    public void setPages(List<WheelPage> wheelPages){
        pages = wheelPages;
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
                String url = pages.get(position).getUrl();
                 String id = "";
                if (url.contains("id=")){
                    id = url.substring(url.indexOf("id=")+3,url.length());
                }
                if(!"".equals(id)){
                    intent.putExtra(Constance.APP_ID,id);
                }else{
                    UiUtils.showTipToast(false,context.getString(R.string.error_null_id));
                    return;
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
