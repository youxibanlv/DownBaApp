package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.http.bean.AppInfo;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.NumberUtil;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/14.
 */
public class AppAdLIstAdapter extends MyBaseAdapter<AppAd> {

    private List<View> views = new ArrayList<>();
    public AppAdLIstAdapter(Context context) {
        super(context);
    }

    public void refreshHolder(DownloadInfo info){
        if (info.getObjId() != null){
            View view = null;
            for (View v:views){
                if (v.getTag(R.id.pull_to_refresh).equals(info.getObjId())){
                    view = v;
                    break;
                }
            }
            if (view != null){
                AppListViewHolder holder = (AppListViewHolder) view.getTag();
                holder.tv_down.setBackgroundColor(context.getResources().getColor(R.color.text_gray));
                if (holder != null){
                    switch (info.getState()){
                        case WAITING:
                            holder.tv_down.setText("队列中。。");
                            break;
                        case STARTED:
                            holder.tv_down.setText(info.getProgress()+"%");
                            break;
                        case FINISHED:
                            holder.tv_down.setText("打 开");
                            break;
                        case STOPPED:
                            holder.tv_down.setText("继续下载");
                            break;
                        case ERROR:
                            holder.tv_down.setText("重新下载");
                            break;
                    }
                }
            }
        }
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
        AppAd ad = list.get(position);
        AppInfo app = ad.getApp();
        if (app != null){
            if (app.getApp_logo()!= null){
                x.image().bind(holder.iv_app_icon, app.getApp_logo(), ImgConfig.getImgOption());
            }
            if (app.getApp_title()== null){
                holder.tv_app_title.setText("");
            }else{
                holder.tv_app_title.setText(app.getApp_title());
            }
            int score = app.getApp_grade()/2;
            holder.app_score.setNumStars(score);
            if (app.getApp_seo()!= null){
                holder.tv_des.setText(app.getApp_seo());
            }else {
                holder.tv_des.setText("");
            }
            if (app.getApp_size()!= null){
                holder.app_size.setText(app.getApp_size()+"MB");
            }
            if (app.getApp_version()!= null){
                holder.app_version.setText("版本："+app.getApp_version());
            }
            int num = app.getApp_down();
            holder.tv_down_num.setText("下载：" + NumberUtil.numToString(num));
//            DownLoadUtils.initDownLoad(app,holder.tv_down);
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppAd appAd = list.get(position);
                    Intent intent = new Intent(context, AppDetailsActivity.class);
                    intent.putExtra(Constance.ID,appAd.getObj_id());
                    context.startActivity(intent);
                }
            });
        }
        convertView.setTag(R.id.pull_to_refresh,app.getApp_id());
        views.add(convertView);
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
        TextView tv_down;

        @ViewInject(R.id.tv_down_num)
        TextView tv_down_num;

        public AppListViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
