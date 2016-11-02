package com.strike.downba_app.utils;

import com.strike.downba_app.view.library.ILoadingLayout;
import com.strike.downba_app.view.library.PullToRefreshListView;

/**
 * 配置PullToRefreshListView的提示信息
 * 
 * **/
public class PullToRefreshUtils {

	public static void initRefresh(PullToRefreshListView pullToRefresh){
		ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);  
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示  
        startLabels.setRefreshingLabel("正在载入...");// 刷新时  
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示  
  
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(  
                false, true);  
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示  
        endLabels.setRefreshingLabel("正在载入...");// 刷新时  
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示  
        
		// 设置下拉刷新文本
		pullToRefresh.getLoadingLayoutProxy(false, true)
				.setPullLabel("上拉刷新...");
		pullToRefresh.getLoadingLayoutProxy(false, true).setReleaseLabel(
				"放开刷新...");
		pullToRefresh.getLoadingLayoutProxy(false, true).setRefreshingLabel(
				"正在加载...");
		// 设置上拉刷新文本
		pullToRefresh.getLoadingLayoutProxy(true, false)
				.setPullLabel("下拉刷新...");
		pullToRefresh.getLoadingLayoutProxy(true, false).setReleaseLabel(
				"放开刷新...");
		pullToRefresh.getLoadingLayoutProxy(true, false).setRefreshingLabel(
				"正在加载...");
	}
}
