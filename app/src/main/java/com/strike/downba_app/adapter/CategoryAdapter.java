package com.strike.downba_app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.http.entity.Category;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by strike on 16/6/13.
 */
public class CategoryAdapter extends MyBaseAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> categoryList) {
        super(context);
        list = categoryList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_pop_category,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Category category = getItem(position);
        String q = category.getCname() == null?"":category.getCname();
        if (category.isChecked()){
            convertView.setBackgroundColor(context.getResources().getColor(R.color.green));
        }else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        holder.tv_pop.setText(q);
        return convertView;
    }
    class ViewHolder{
        @ViewInject(R.id.tv_title)
        TextView tv_pop;

        public ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
