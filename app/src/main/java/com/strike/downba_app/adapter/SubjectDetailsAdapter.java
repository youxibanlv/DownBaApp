package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.activity.ImgDetailsActivity;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.view.MyHorizontalScrollView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by strike on 16/8/3.
 */
public class SubjectDetailsAdapter extends MyBaseAdapter<App> {
    private HorizontalScrollViewAdapter adapter;
    public SubjectDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_subject_details,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //初始化界面
        final App app = getItem(position);
        if (app.getApp_logo()!= null){
            x.image().bind(holder.icon,app.getApp_logo());
        }
        if (app.getApp_title()!= null){
            holder.app_name.setText(app.getApp_title());
        }
        int score = app.getApp_recomment() == null ? 0 : (int) (Float.parseFloat(app.getApp_recomment()) / 2);
        holder.app_score.setNumStars(score);
        String sizeAndVersion="";
        if (app.getApp_size()!= null){
            sizeAndVersion += "大小："+app.getApp_size();
        }
        if (app.getApp_version() != null){
            sizeAndVersion += " | 版本："+app.getApp_version();
        }
        holder.version_uptime.setText(sizeAndVersion);
        if (app.getApp_desc()!= null){
            holder.tv_des.setText(Html.fromHtml(app.getApp_desc()));
        }
        if (app.getResource()!= null && app.getResource().size()>0){
            adapter = new HorizontalScrollViewAdapter(context,app.getResource());
            holder.imgList.initDatas(adapter);
        }
        holder.imgList.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Intent intent = new Intent();
                intent.setClass(context, ImgDetailsActivity.class);
                intent.putExtra(ImgDetailsActivity.EXTRA_IMAGE_INDEX, pos);
                intent.putStringArrayListExtra(ImgDetailsActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) app.getResource());
                context.startActivity(intent);
            }
        });
        holder.item_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constance.APP,app);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder{

        @ViewInject(R.id.item_subject)
        private LinearLayout item_subject;

        @ViewInject(R.id.icon)
        ImageView icon;

        @ViewInject(R.id.app_name)
        TextView app_name;

        @ViewInject(R.id.app_score)
        RatingBar app_score;

        @ViewInject(R.id.version_uptime)
        TextView version_uptime;

        @ViewInject(R.id.tv_down)
        TextView tv_down;

        @ViewInject(R.id.tv_des)
        TextView tv_des;

        @ViewInject(R.id.imgList)
        MyHorizontalScrollView imgList;

        @ViewInject(R.id.details)
        TextView details;

        public ViewHolder(View view){
            x.view().inject(this,view);
        }

    }
}
