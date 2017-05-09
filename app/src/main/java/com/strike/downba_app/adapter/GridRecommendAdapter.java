package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.http.bean.AppAd;
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
    private List<AppAd> list = new ArrayList<>();
    private LayoutInflater inflater;
    private List<View> views = new ArrayList<>();

    public void refreshHolder(DownloadInfo info){
        if (views.size()>0){
            View view = null;
            for (View v:views){
                if (v.getTag(R.id.gv_recommend).equals(info.getObjId())){
                    view = v;
                    break;
                }
            }
            if (view != null){
                String msg;
                switch (info.getState()){
                    case WAITING:
                        msg = "队列中。。";
                        break;
                    case STARTED:
                        msg = info.getProgress()+"%";
                        break;
                    case FINISHED:
                        msg ="打 开";
                        break;
                    case STOPPED:
                        msg = "继续下载";
                        break;
                    case ERROR:
                        msg ="重新下载";
                        break;
                    default:
                        msg = "安 装";
                }
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.grid_recommend.refreshDownload(msg);
            }
        }
    }

    public GridRecommendAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<AppAd> appList) {
        list = appList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AppAd getItem(int position) {
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
        AppAd ad = getItem(position);
        holder.grid_recommend.setApp(ad);
        holder.grid_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constance.APP_ID,getItem(position).getObj_id());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        convertView.setTag(R.id.gv_recommend,ad.getObj_id());
        views.add(convertView);
        return convertView;
    }

    class ViewHolder {
        ExtrAppVertical grid_recommend;
    }
}
