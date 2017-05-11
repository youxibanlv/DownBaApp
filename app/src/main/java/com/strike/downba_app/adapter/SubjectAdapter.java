package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.activity.SubjectActivity;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.http.bean.Subject;
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
        x.image().bind( holder.icon,subject.getLogo());
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (subject.getSb_type()){
                    case Constance.SB_ONE_APP:
                        Intent intent = new Intent(context, AppDetailsActivity.class);
                        intent.putExtra(Constance.ID,subject.getObj_id());
                        context.startActivity(intent);
                        break;
                    case Constance.SB_LIST_APP:
                        Intent sbIntent = new Intent(context, SubjectActivity.class);
                        sbIntent.putExtra(Constance.ID,subject.getSb_id());
                        context.startActivity(sbIntent);
                        break;
                    case Constance.SB_APP_INFO:
                        // TODO: 2017/5/11 跳转app+info界面
                        break;
                }

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
