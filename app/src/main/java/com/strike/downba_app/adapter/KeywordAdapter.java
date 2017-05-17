package com.strike.downba_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.strike.downba_app.http.bean.Keyword;
import com.strike.downbaapp.R;

import java.util.List;

/**
 * Created by strike on 16/6/13.
 */
public class KeywordAdapter extends BaseAdapter {

    private List<Keyword> keywords;
    private Context context;
    private LayoutInflater inflater;

    public KeywordAdapter(Context context,List<Keyword> keywords){
        this.keywords = keywords;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void refresh(List<Keyword> list){
        this.keywords = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return keywords.size();
    }

    @Override
    public Keyword getItem(int position) {
        return keywords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_pop,parent,false);
            holder = new ViewHolder();
            holder.tv_pop = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Keyword keyword = getItem(position);
        String q = keyword.getQ() == null?"":keyword.getQ();
        int num = keyword.getQnum() == null?0:keyword.getQnum();
        holder.tv_pop.setText(q);
        holder.tv_num.setText(num+"");
        return convertView;
    }

    class ViewHolder{
        TextView tv_pop;
        TextView tv_num;
    }
}
