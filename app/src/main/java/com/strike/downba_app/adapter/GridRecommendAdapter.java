package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.view.ExtrAppVertical;
import com.strike.downbaapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/10.
 */
public class GridRecommendAdapter extends BaseAdapter {

    private Context context;
    private List<App> list = new ArrayList<>();
    private LayoutInflater inflater;

    public GridRecommendAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<App> appList) {
        list = appList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public App getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_recommed_grid, parent, false);
            holder = new ViewHolder();
            holder.grid_recommend = (ExtrAppVertical) convertView.findViewById(R.id.grid_recommend);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.size()>3){
            holder.grid_recommend.hideDownBtn(true);
        }
        //配置app界面
        holder.grid_recommend.setApp(list.get(position));
        holder.grid_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constance.APP,getItem(position));
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    class ViewHolder {
        ExtrAppVertical grid_recommend;
    }
}
