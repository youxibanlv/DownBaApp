package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.DownLoadUtils;
import com.strike.downba_app.utils.NumberUtil;
import com.strike.downba_app.view.DownloadBtn;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/14.
 */
public class AppLIstAdapter extends MyBaseAdapter<App> {

    private List<View> viewList = new ArrayList<>(); //View对象集合
    private DownLoadUtils utils;
    public AppLIstAdapter(Context context) {
        super(context);
        utils = new DownLoadUtils(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        AppListViewHolder holder ;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_app_list, parent, false);
            holder = new AppListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AppListViewHolder) convertView.getTag();
        }
         App app = list.get(position);
        if (app != null){
            if (app.getApp_logo()!= null){
                x.image().bind(holder.iv_app_icon, app.getApp_logo(), ImgConfig.getImgOption());
            }
            if (app.getApp_title()== null){
                holder.tv_app_title.setText("");
            }else{
                holder.tv_app_title.setText(app.getApp_title());
            }
            int score = app.getApp_recomment() == null ? 0 : (int) (Float.parseFloat(app.getApp_recomment()) / 2);
            holder.app_score.setNumStars(score);
            if (app.getSeo_keywords()!= null){
                holder.tv_des.setText(app.getSeo_keywords());
            }else {
                holder.tv_des.setText("");
            }
            if (app.getApp_size()!= null){
                holder.app_size.setText(app.getApp_size());
            }
            if (app.getApp_version()!= null){
                holder.app_version.setText("版本："+app.getApp_version());
            }
            int num = NumberUtil.parseToInt(app.getApp_down());
            holder.tv_down_num.setText("下载：" + NumberUtil.numToString(num));

            utils.initDownLoad(app,holder.tv_down);
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App app1 = list.get(position);
                    Intent intent = new Intent(context, AppDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constance.APP,app1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        /* 标识View对象 */
        //将list_view的ID作为Tag的Key值
        //此处将位置信息作为标识传递
        convertView.setTag(R.id.pull_to_refresh,position);
        viewList.add(convertView);
        return convertView;
    }

    class AppListViewHolder {
        @ViewInject(R.id.ll_item)
        private LinearLayout ll_item;

        @ViewInject(R.id.iv_app_icon)
        ImageView iv_app_icon;

        @ViewInject(R.id.tv_app_title)
        TextView tv_app_title;

        @ViewInject(R.id.app_score)
        RatingBar app_score;

        @ViewInject(R.id.app_size)
        TextView app_size;

        @ViewInject(R.id.app_version)
        TextView app_version;

        @ViewInject(R.id.tv_des)
        TextView tv_des;

        @ViewInject(R.id.tv_down)
        DownloadBtn tv_down;

        @ViewInject(R.id.tv_down_num)
        TextView tv_down_num;

        public AppListViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
