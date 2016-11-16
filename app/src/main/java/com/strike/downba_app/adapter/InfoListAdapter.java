package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strike.downba_app.activity.CommonWebviewActivity;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.http.entity.Info;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by strike on 16/8/4.
 */
public class InfoListAdapter extends MyBaseAdapter<Info> {
    public InfoListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_info,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Info info = getItem(position);
        x.image().bind(viewHolder.icon,info.getInfo_img(), ImgConfig.getImgOption());
        viewHolder.title.setText(info.getInfo_title());
        viewHolder.body.setText(info.getInfo_body());
        viewHolder.visitors.setText(info.getInfo_visitors()+"");
//        viewHolder.ll_info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, InfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Constance.INFO,info);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });
        viewHolder.ll_info.setOnClickListener(new View.OnClickListener() {
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
        return convertView;
    }

    class ViewHolder{

        @ViewInject(R.id.ll_info)
        private LinearLayout ll_info;

        @ViewInject(R.id.icon)
        ImageView icon;

        @ViewInject(R.id.title)
        TextView title;

        @ViewInject(R.id.body)
        TextView body;

        @ViewInject(R.id.visitors)
        TextView visitors;

        public ViewHolder(View view){
            x.view().inject(this,view);
        }

    }
}
