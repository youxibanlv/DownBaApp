package com.strike.downba_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.strike.downba_app.adapter.HomeAdapter;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.HomeBean;
import com.strike.downba_app.http.entity.PageBean;
import com.strike.downba_app.http.entity.Recommend;
import com.strike.downba_app.http.request.HomeBeanReq;
import com.strike.downba_app.http.request.RecommendReq;
import com.strike.downba_app.http.response.HomeBeanRsp;
import com.strike.downba_app.http.response.RecommendRsp;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by strike on 16/6/5.
 */
@ContentView(R.layout.fragment_list)
public class HomeFragment extends BaseFragment {

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private HomeAdapter adapter;

    private int pageNo = 1,pageSize = 3,totalPage;//热门游戏部分

    private long waitTime = 0,lastRefreshTime = 0,minWaitTime = 15000;//等待时间，最后一次刷新时间,最小等待时间，用于控制用户的刷新频率
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        adapter = new HomeAdapter(getActivity());
        pull_to_refresh.setAdapter(adapter);
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化提示文字
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (lastRefreshTime == 0 || waitTime > minWaitTime){
                    lastRefreshTime = System.currentTimeMillis();
                    pageNo = 1;
                    getRecommend(String.valueOf(Recommend.TYPE_WHEEL));
                    getHomeList(pageNo,pageSize,true);
                }else{
                    waitTime = System.currentTimeMillis() - lastRefreshTime;
                    showTipToast(false,String.format(getString(R.string.refresh_too_fast),minWaitTime/1000));
                    UiUtils.stopRefresh(pull_to_refresh);
                }
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    if (pageNo <= totalPage){
                        pageNo ++;
                       getHomeList(pageNo,pageSize,false);
                    }else{
                        UiUtils.showTipToast(false,getString(R.string.this_is_last));
                        UiUtils.stopRefresh(pull_to_refresh);
                    }
            }
        });
        //加载轮播图片
        getRecommend(String.valueOf(Recommend.TYPE_WHEEL));
        getHomeList(1, pageSize,true);
        return view;
    }

    private void getHomeList(int pageNo, int pageSize, final boolean isRefresh){
        HomeBeanReq req = new HomeBeanReq(pageNo,pageSize);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                HomeBeanRsp rsp = (HomeBeanRsp) BaseResponse.getRsp(result,HomeBeanRsp.class);
                List<HomeBean> beans = rsp.resultData.homeBeans;
                PageBean pageBean = rsp.resultData.pageBean;
                totalPage = pageBean.totalPage;
                if (beans != null && beans.size()>0){
                    if (isRefresh){
                        adapter.refreshRecommends(beans);
                    }else{
                        adapter.loadMore(beans);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
            }
        });
    }
    //加载轮播图
    private void getRecommend(final String type){
        RecommendReq req = new RecommendReq(type);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                RecommendRsp rsp = (RecommendRsp) BaseResponse.getRsp(result,RecommendRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS){
                    List<Recommend> list = rsp.getAppList();
                    if (list!= null && list.size()>0){
                        adapter.refreshWheelPages(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
            }
        });
    }


}
