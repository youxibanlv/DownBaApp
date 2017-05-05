package com.strike.downba_app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.view.WheelViewPage;
import com.strike.downbaapp.R;

/**
 * Created by strike on 2017/5/4.
 */

public class WheelAdapter extends MyBaseAdapter<AppAd> {
    public WheelAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WheelPageHolder wheelPageHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_wheel_page, parent, false);
            wheelPageHolder = new WheelPageHolder();
            wheelPageHolder.myWheelPages = (WheelViewPage) convertView.findViewById(R.id.myWheelPages);
            convertView.setTag(wheelPageHolder);
        }else {
            wheelPageHolder = (WheelPageHolder) convertView.getTag();
        }
        if (list.size()>0){
            wheelPageHolder.myWheelPages.setViewPage(list);
        }
        return convertView;
    }

    class WheelPageHolder {
        WheelViewPage myWheelPages;
    }
}
