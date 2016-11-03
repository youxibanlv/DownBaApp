package com.strike.downba_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downba_app.activity.AppDetailsActivity;
import com.strike.downba_app.activity.RecommendActivity;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.entity.Recommend;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.view.DownloadBtn;
import com.strike.downba_app.view.NoScrollGridView;
import com.strike.downba_app.view.WheelViewPage;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/7.
 */
public class HomeAdapter extends BaseAdapter {

    private final int TYPE_HEAD = 0;//轮播图
    private final int TYPE_RECOMMEND = 1;//精品推荐
    private final int TYPE_LIST = 2;//热门新游

    private List<Recommend> wheelPages = new ArrayList<>();//轮播图集合
    private List<App> recommends = new ArrayList<>();//精品推荐
    private List<App> newGames = new ArrayList<>();//装机

    private GridRecommendAdapter gridAdapter;

    private Context context;
    private LayoutInflater inflater;

    public HomeAdapter(Context context) {
        this.context = context;
        gridAdapter = new GridRecommendAdapter(context);
        inflater = LayoutInflater.from(context);
    }

    //设置轮播图数据
    public void refreshWheelPages(List<Recommend> list) {
        wheelPages = list;
        notifyDataSetChanged();
    }

    //    设置精品推荐数据
    public void refreshRecommends(List<App> list) {
        recommends = list;
        notifyDataSetChanged();
    }

    //设置猜你喜欢
    public void refreshGuessYouLike(List<App> list){
        newGames = list;
        notifyDataSetChanged();
    }
    public void loadMore(List<App> list){
        newGames.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        int count = 2;
        if (newGames.size() > 0) {
            count = count + newGames.size();
        }
        return count;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        if (position < 2) {
            return null;
        } else {
            return newGames.get(position);
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
        } else if (position == 1) {
            return TYPE_RECOMMEND;
        } else {
            return TYPE_LIST;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        WheelPageHolder wheelPageHolder = null;
        RecommendHolder recommendHolder = null;
        AppListViewHolder appListViewHolder = null;
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
                    convertView = inflater.inflate(R.layout.item_app_list,parent,false);
                    appListViewHolder = new AppListViewHolder(convertView);
                    convertView.setTag(appListViewHolder);
                    break;

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
            }
        }
        //设置资源
        switch (type) {
            case TYPE_HEAD:
                if (wheelPages.size() > 0) {
                    wheelPageHolder.myWheelPages.setViewPage(wheelPages);
                }
                break;
            case TYPE_RECOMMEND:
                recommendHolder.tv_recommend.setText(context.getResources().getString(R.string.recommend));
                recommendHolder.gv_recommend.setAdapter(gridAdapter);
                if (recommends.size() > 0) {
                    gridAdapter.setList(recommends);
                }
                recommendHolder.tv_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击更多，跳转到app推荐列表界面
                        Intent intent = new Intent(context, RecommendActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_LIST:
                final App app = newGames.get(position-2);
                x.image().bind(appListViewHolder.iv_app_icon, app.getApp_logo(), ImgConfig.getImgOption());
                appListViewHolder.tv_app_title.setText(app.getApp_title());
                int score = app.getApp_recomment() == null ? 0 : (int) (Float.parseFloat(app.getApp_recomment()) / 2);
                appListViewHolder.app_score.setNumStars(score);
                appListViewHolder.tv_des.setText(Html.fromHtml(app.getApp_desc()));
                String down = app.getApp_down() == null ? "0" : app.getApp_down();
                appListViewHolder.tv_down_num.setText("下载：" + down);
//                new DownLoadUtils(context).initDownLoad(app,appListViewHolder.tv_down);
                appListViewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AppDetailsActivity.class);
                        intent.putExtra(Constance.APP_ID,app.getApp_id());
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
        @ViewInject(R.id.ll_item)
        private LinearLayout ll_item;

        @ViewInject(R.id.iv_app_icon)
        ImageView iv_app_icon;

        @ViewInject(R.id.tv_app_title)
        TextView tv_app_title;

        @ViewInject(R.id.app_score)
        RatingBar app_score;

        @ViewInject(R.id.tv_des)
        TextView tv_des;

        @ViewInject(R.id.tv_down)
        DownloadBtn tv_down;

        @ViewInject(R.id.tv_down_num)
        TextView tv_down_num;

        public AppListViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
