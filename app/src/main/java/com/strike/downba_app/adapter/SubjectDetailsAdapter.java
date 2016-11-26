package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.activity.CommonWebviewActivity;
import com.strike.downba_app.adapter.holder.AppListViewHolder;
import com.strike.downba_app.adapter.holder.ImgViewHolder;
import com.strike.downba_app.adapter.holder.InfoListViewHolder;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.entity.Info;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.DownLoadUtils;
import com.strike.downba_app.utils.NumberUtil;
import com.strike.downbaapp.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/8/3.
 */
public class SubjectDetailsAdapter extends MyBaseAdapter {
    private static final int VIEW_TYPE = 4;
    private static final int TYPE_IMG = 0;
    private static final int TYPE_APP = 1;
    private static final int TYPE_INO = 2;
    private static final int TYPE_TITLE = 3;

    List<View> views = new ArrayList<>();
    private String logoUrl;
    private List<App> apps = new ArrayList<>();
    private List<Info> infos = new ArrayList<>();

    public SubjectDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        int count = 0;
        if (logoUrl != null) {
            count = count + 1;
        }
        if (apps.size() > 0) {
            count = count + apps.size();
        }
        if (infos.size() > 0) {
            count = count + infos.size();
        }
        return count;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_IMG;
        }else if (position > 0 && position <= apps.size()){
            return TYPE_APP;
        }else {
            return TYPE_INO;
        }
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return logoUrl;
        } else if (position > 0 && position <= apps.size()) {
            return apps.get(position - 1);
        } else {
            return infos.get(position - apps.size());
        }
    }

    public void refreshImg(String logoUrl){
        if (logoUrl!= null){
            this.logoUrl = logoUrl;
        }
        notifyDataSetChanged();
    }
    public void refreshApps(List<App> appList){
        if (appList!= null && appList.size()>0){
            apps = appList;
        }
        notifyDataSetChanged();
    }
    public void refreshInfos(List<Info> infoList){
        if (infoList!= null && infoList.size()>0){
            infos = infoList;
        }
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImgViewHolder imgHolder = null;
        AppListViewHolder appListViewHolder = null;
        InfoListViewHolder infoListViewHolder = null;
        if (convertView == null) {
            switch (getItemViewType(position)) {
                case TYPE_IMG:
                    convertView = inflater.inflate(R.layout.item_img,parent,false);
                    imgHolder = new ImgViewHolder(convertView);
                    convertView.setTag(imgHolder);
                    break;
                case TYPE_APP:
                    convertView = inflater.inflate(R.layout.item_app_list, parent, false);
                    appListViewHolder = new AppListViewHolder(convertView);
                    convertView.setTag(appListViewHolder);
                    break;
                case TYPE_INO:

                    break;
            }
        } else {
            switch (getItemViewType(position)) {
                case TYPE_IMG:
                    imgHolder = (ImgViewHolder) convertView.getTag();
                    break;
                case TYPE_APP:
                    appListViewHolder = (AppListViewHolder) convertView.getTag();
                    break;
                case TYPE_INO:

                    break;
            }
        }
        //设置资源
        switch (getItemViewType(position)) {
            case TYPE_IMG:
                if (imgHolder != null)
                x.image().bind(imgHolder.icon,logoUrl);
                break;
            case TYPE_APP:
                App app = (App) getItem(position);
                if (app != null) {
                    if (app.getApp_logo() != null) {
                        x.image().bind(appListViewHolder.iv_app_icon, app.getApp_logo(), ImgConfig.getImgOption());
                    }
                    if (app.getApp_title() == null) {
                        appListViewHolder.tv_app_title.setText("");
                    } else {
                        appListViewHolder.tv_app_title.setText(app.getApp_title());
                    }
                    int score = app.getApp_recomment() == null ? 0 : (int) (Float.parseFloat(app.getApp_recomment()) / 2);
                    appListViewHolder.app_score.setNumStars(score);
                    if (app.getSeo_keywords() != null) {
                        appListViewHolder.tv_des.setText(app.getSeo_keywords());
                    } else {
                        appListViewHolder.tv_des.setText("");
                    }
                    if (app.getApp_size() != null) {
                        appListViewHolder.app_size.setText(app.getApp_size());
                    }
                    if (app.getApp_version() != null) {
                        appListViewHolder.app_version.setText("版本：" + app.getApp_version());
                    }
                    int num = NumberUtil.parseToInt(app.getApp_down());
                    appListViewHolder.tv_down_num.setText("下载：" + NumberUtil.numToString(num));
                    DownLoadUtils.initDownLoad(app, appListViewHolder.tv_down);
                    appListViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            App app1 = (App) getItem(position);
                            Intent intent = new Intent(context, AppDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Constance.APP, app1);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case TYPE_INO:
                final Info info = (Info) getItem(position);
                x.image().bind(infoListViewHolder.icon,info.getInfo_img(), ImgConfig.getImgOption());
                infoListViewHolder.title.setText(info.getInfo_title());
                infoListViewHolder.body.setText(info.getInfo_desc());
                infoListViewHolder.visitors.setText(info.getInfo_visitors()+"");
                infoListViewHolder.ll_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CommonWebviewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt(CommonWebviewActivity.INFO_ID,info.getInfo_id());
                        bundle.putString(CommonWebviewActivity.WEB_TITLE,info.getInfo_title());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
        }
        return convertView;
    }
}
