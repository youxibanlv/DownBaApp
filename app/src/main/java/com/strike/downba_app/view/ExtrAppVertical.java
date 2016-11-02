package com.strike.downba_app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downba_app.db.table.App;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by strike on 16/6/10.
 */
public class ExtrAppVertical extends LinearLayout {
    @ViewInject(R.id.app_icon)
    private ImageView app_icon;

    @ViewInject(R.id.app_title)
    private TextView app_title;

    @ViewInject(R.id.app_score)
    private RatingBar app_score;

    @ViewInject(R.id.app_size)
    private TextView app_size;

    @ViewInject(R.id.app_install)
    private DownloadBtn app_install;

    private View view;

    public ExtrAppVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.app_v, this, true);
        x.view().inject(view);
    }

    public void setApp(App app){
        String appLogo = app.getApp_logo();
       if (app_icon != null && appLogo != null && !"".equals(appLogo)){
           x.image().bind(app_icon,app.getApp_logo(), ImgConfig.getImgOption());
       }
        String title = app.getApp_title();
        if (title == null){
            title = "";
        }
        if (app_title != null) {
            app_title.setText(title);
        }
        String score = app.getApp_recomment();
        if (score == null || "".equals(score)){
            score = "0";
        }
        if (app_score != null ) {
            app_score.setNumStars(Integer.parseInt(score)/2);
        }
        String size = app.getApp_size();
        if (size == null){
            size = "";
        }
        if (app_size != null) {
            app_size.setText(size);
        }
    }
}
