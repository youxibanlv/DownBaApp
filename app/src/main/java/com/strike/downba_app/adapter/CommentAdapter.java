package com.strike.downba_app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.http.entity.Comment;
import com.strike.downba_app.utils.TimeUtil;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by strike on 2016/11/14.
 */

public class CommentAdapter extends MyBaseAdapter<Comment> {
    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_comment,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Comment comment = getItem(position);
        if (!TextUtils.isEmpty(comment.getUname())){
            holder.userName.setText(comment.getUname());
        }
        holder.time.setText(TimeUtil.longToDateStr(comment.getDate_add(),"yyyy-MM-dd"));
        holder.content.setText(comment.getContent());
        return convertView;
    }

    class ViewHolder{
        @ViewInject(R.id.userName)
        TextView userName;
        @ViewInject(R.id.time)
        TextView time;
        @ViewInject(R.id.content)
        TextView content;

        public ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
