package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.strike.downba_app.activity.SubjectActivity;
import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.http.bean.AppHome;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.view.NoScrollGridView;
import com.strike.downba_app.view.WheelViewPage;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.strike.downbaapp.R.string.subject;

/**
 * Created by strike on 16/6/7.
 */
public class HomeAdapter extends BaseAdapter {

    private final int TYPE_HEAD = 0;//轮播图
    private final int TYPE_RECOMMEND = 1;//精品推荐
    private final int TYPE_LIST = 2;//普通推荐
    private final int TYPE_SUBJECT = 3;//专题推荐

    private List<AppAd> wheelPages = new ArrayList<>();//轮播图集合
    private List<AppHome> homeBeens = new ArrayList<>();//首页元素

    private List<AppLIstAdapter> listAdapters = new ArrayList<>();
    private List<GridRecommendAdapter> gridAdapters = new ArrayList<>();

    private Context context;
    private LayoutInflater inflater;
    //更新下载进度
    public void refreshHolder(DownloadInfo info) {
        //刷新列表项
        if (listAdapters.size()>0){
            for (AppLIstAdapter adapter : listAdapters){
                adapter.refreshHolder(info);
            }
        }
        //刷新格子
        if (gridAdapters.size()>0){
            for (GridRecommendAdapter gAdapter:gridAdapters){
                gAdapter.refreshHolder(info);
            }
        }

    }
    public HomeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //设置轮播图数据
    public void refreshWheelPages(List<AppAd> list) {
        wheelPages = list;
        notifyDataSetChanged();
    }

    // 设置设欧耶数据
    public void refreshHome(AppHome appHome) {
        homeBeens.clear();
        homeBeens.add(appHome);
        notifyDataSetChanged();
    }

    public void loadMore(AppHome appHome){
        homeBeens.add(appHome);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        int count = 1;
        if (homeBeens.size() > 0) {
            count = count + homeBeens.size()*3;
        }
        return count;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public AppHome getItem(int position) {
        if (position < 1) {
            return null;
        } else {
            return homeBeens.get((position-1)/3);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            if ((position-1)%3 ==0){
                return TYPE_RECOMMEND;
            }else if ((position+1)%3==0){
                return TYPE_LIST;
            }else {
                return TYPE_SUBJECT;
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        WheelPageHolder wheelPageHolder = null;
        RecommendHolder recommendHolder = null;
        AppListViewHolder appListViewHolder = null;
        SubjectHolder subjectHolder = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_HEAD:
                    convertView = inflater.inflate(R.layout.item_wheel_page, parent, false);
                    wheelPageHolder = new WheelPageHolder();
                    wheelPageHolder.myWheelPages = (WheelViewPage) convertView.findViewById(R.id.myWheelPages);
                    convertView.setTag(wheelPageHolder);
                    break;
                case TYPE_RECOMMEND:
                    convertView = inflater.inflate(R.layout.item_recommed, parent, false);
                    recommendHolder = new RecommendHolder(convertView);
                    convertView.setTag(recommendHolder);
                    break;
                case TYPE_LIST:
                    convertView = inflater.inflate(R.layout.item_home_nomal,parent,false);
                    appListViewHolder = new AppListViewHolder(convertView);
                    convertView.setTag(appListViewHolder);
                    break;
                case TYPE_SUBJECT:
                    convertView = inflater.inflate(R.layout.item_subject,parent,false);
                    subjectHolder = new SubjectHolder(convertView);
                    convertView.setTag(subjectHolder);
            }
        } else {
            switch (type) {
                case TYPE_HEAD:
                    wheelPageHolder = (WheelPageHolder) convertView.getTag();
                    break;
                case TYPE_RECOMMEND:
                    recommendHolder = (RecommendHolder) convertView.getTag();
                    break;
                case TYPE_LIST:
                    appListViewHolder = (AppListViewHolder) convertView.getTag();
                    break;
                case TYPE_SUBJECT:
                    subjectHolder = (SubjectHolder) convertView.getTag();
            }
        }
        //设置资源
        final AppHome bean = getItem(position);
        switch (type) {
            case TYPE_HEAD:
                if (wheelPages.size() > 0) {
                    wheelPageHolder.myWheelPages.setViewPage(wheelPages);
                }
                break;
            case TYPE_RECOMMEND:
                recommendHolder.tv_recommend.setText(bean.getTitle1());
                GridRecommendAdapter gridAdapter = new GridRecommendAdapter(context);
                recommendHolder.gv_recommend.setAdapter(gridAdapter);
                if (bean.getRecomd().size() > 0) {
                    gridAdapter.setList(bean.getRecomd());
                }
                gridAdapters.add(gridAdapter);
//                recommendHolder.tv_more.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //点击更多，跳转到app推荐列表界面
//                        Intent intent = new Intent(context, CateActivity.class);
//                        context.startActivity(intent);
//                    }
//                });
                break;
            case TYPE_LIST:
                appListViewHolder.tv_recommend.setText(bean.getTitle2());
                AppLIstAdapter appListAdapter = new AppLIstAdapter(context);
                appListViewHolder.lv_recommend.setAdapter(appListAdapter);
                appListAdapter.refresh(bean.getAppAdApp());
                //添加到adapter集合，方便刷新下载进度
                listAdapters.add(appListAdapter);
                break;
            case TYPE_SUBJECT:
                if (bean.getBanner()!= null && !TextUtils.isEmpty(bean.getBanner().getLogo())) {
                    x.image().bind(subjectHolder.icon, bean.getBanner().getLogo());
                }
                subjectHolder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Subject subject = bean.getSubject();
                        Intent intent = new Intent(context, SubjectActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constance.SUBJECT,subject);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
        }
        return convertView;
    }


    class WheelPageHolder {
        WheelViewPage myWheelPages;
    }
    class SubjectHolder{
        @ViewInject(R.id.icon)
        ImageView icon;
        public SubjectHolder(View view){
            x.view().inject(this,view);
        }
    }
    class RecommendHolder {
        @ViewInject(R.id.tv_recommend)
        TextView tv_recommend;

        @ViewInject(R.id.tv_more)
        TextView tv_more;

        @ViewInject(R.id.gv_recommend)
        NoScrollGridView gv_recommend;
        public RecommendHolder(View view) {
            x.view().inject(this, view);
        }
    }
    class AppListViewHolder {
        @ViewInject(R.id.tv_recommend)
        private TextView tv_recommend;
        @ViewInject(R.id.lv_recommend)
        private ListView lv_recommend;

        public AppListViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
