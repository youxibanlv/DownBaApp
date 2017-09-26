package com.strike.downba_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.http.bean.Home;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.view.NoScrollGridView;
import com.strike.downba_app.view.WheelViewPage;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 2017/6/5.
 */

public class MainAdapter extends RecyclerView.Adapter {

    private final int HEAD = 0;//轮播图
    private final int GRID = 1;//精品推荐
    private final int LIST = 2;//普通推荐
    private final int BANNER = 3;//专题推荐
    private final int MENU = 4;// 菜单

    private List<Home> list;
    private List<AppAd> wheelPages = new ArrayList<>();//轮播图集合
    private Context context;
    private LayoutInflater inflater;



    public MainAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEAD;
        }else if (position == 1){
            return MENU;
        }else{
            Home home = list.get(position-2);
            if (home.getObjType() == Constance.AD_BANNER){
                return BANNER;
            }else if (home.getObjType() == Constance.AD_RECOMD){
                return GRID;
            }else {
                return LIST;
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD){
            return new HeadViewHolder(inflater.inflate(R.layout.item_wheel_page, parent, false));
        }else if (viewType == MENU){
            return new MenuViewHolder(inflater.inflate(R.layout.item_home_menu,parent,false));
        }else if (viewType == GRID){
            return new GridViewHolder(inflater.inflate(R.layout.item_recommed, parent, false));
        }else if (viewType == LIST){
            return new AppListViewHolder(inflater.inflate(R.layout.item_home_nomal,parent,false));
        }else if (viewType == BANNER){
            return new BannerHolder(inflater.inflate(R.layout.item_subject,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0){
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            headViewHolder.myWheelPages.setViewPage(wheelPages);
        }else if (position ==1){
            MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
            menuViewHolder.mall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            menuViewHolder.weal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            menuViewHolder.game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            menuViewHolder.app.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            menuViewHolder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            menuViewHolder.manager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            menuViewHolder.strategy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else {
            switch (getItemViewType(position)){
                case LIST:

                    break;
                case GRID:

                    break;
                case BANNER:

                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+2;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.myWheelPages)
        WheelViewPage myWheelPages;
        public HeadViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }
    }
    class MenuViewHolder extends RecyclerView.ViewHolder{

        @ViewInject(R.id.icon_mall)
        LinearLayout mall;
        @ViewInject(R.id.icon_weal)
        LinearLayout weal;
        @ViewInject(R.id.icon_game)
        LinearLayout game;
        @ViewInject(R.id.icon_app)
        LinearLayout app;
        @ViewInject(R.id.icon_info)
        LinearLayout info;
        @ViewInject(R.id.icon_manager)
        LinearLayout manager;
        @ViewInject(R.id.icon_strategy)
        LinearLayout strategy;

        public MenuViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }

        @Event(value = {R.id.icon_mall,R.id.icon_weal,R.id.icon_game,R.id.icon_app,
                R.id.icon_info,R.id.icon_manager,R.id.icon_strategy})
        private void getEvent(View view){
            switch (view.getId()){
                case R.id.icon_mall:

                    break;
                case R.id.icon_weal:

                    break;
                case R.id.icon_game:

                    break;
                case R.id.icon_app:

                    break;
                case R.id.icon_info:

                    break;
                case R.id.icon_manager:

                    break;
                case R.id.icon_strategy:

                    break;
            }
        }

    }

    class GridViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.tv_recommend)
        TextView tv_recommend;

        @ViewInject(R.id.tv_more)
        TextView tv_more;

        @ViewInject(R.id.gv_recommend)
        NoScrollGridView gv_recommend;


        public GridViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }
    }

    class AppListViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.tv_recommend)
        private TextView tv_recommend;
        @ViewInject(R.id.lv_recommend)
        private ListView lv_recommend;

        public AppListViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }
    }

    class BannerHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.icon)
        ImageView icon;
        public BannerHolder(View view){
            super(view);
            x.view().inject(this,view);
        }
    }
}
