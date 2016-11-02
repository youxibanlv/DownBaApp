package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.strike.downba_app.activity.SubjectActivity;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.http.entity.Subject;
import com.strike.downba_app.utils.Constance;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by strike on 16/8/2.
 */
public class SubjectAdapter extends MyBaseAdapter<Subject> {
    public SubjectAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_subject,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final Subject subject = getItem(position);
        x.image().bind( holder.icon,subject.getArea_logo());
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubjectActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constance.SUBJECT,subject);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{

        @ViewInject(R.id.icon)
        private ImageView icon;

        public ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
