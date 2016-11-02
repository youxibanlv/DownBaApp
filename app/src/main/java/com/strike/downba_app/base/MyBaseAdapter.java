package com.strike.downba_app.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/14.
 */
public abstract class MyBaseAdapter <T> extends BaseAdapter {
    protected List<T> list = new ArrayList<>();
    protected Context context;
    protected LayoutInflater inflater;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public MyBaseAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public void refresh(List<T> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void addData(List<T> dataList){
        this.list.addAll(dataList);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
