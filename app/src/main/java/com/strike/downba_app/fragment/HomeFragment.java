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
import com.strike.downba_app.http.entity.Recommend;
import com.strike.downba_app.http.request.RecommendReq;
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
@ContentView(R.layout.fragment_recommend)
public class HomeFragment extends BaseFragment {

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private HomeAdapter adapter;

    private int pageNo = 0,pageSize = 5,totalPage;//热门游戏部分
    private int recommedPageNo = 0,recommendPageSize = 3;//精品推荐显示数目

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
                    pageNo = 0;
//                    getRecommend(recommedPageNo,recommendPageSize,"0",true);
//                    getRecommend(pageNo,pageSize,"1",true);
                    getRecommend("1");
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
//                        getRecommend(pageNo,pageSize,"1",false);
                    }else{
                        UiUtils.showTipToast(false,getString(R.string.this_is_last));
                        UiUtils.stopRefresh(pull_to_refresh);
                    }
            }
        });
        //加载轮播图片
//        getRecommend(recommedPageNo,recommendPageSize,"0",true);
//        getRecommend(pageNo,pageSize,"1",true);
        return view;
    }

    //加载精品推荐
    private void getRecommend(final String type){
        RecommendReq req = new RecommendReq(type);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                RecommendRsp rsp = (RecommendRsp) BaseResponse.getRsp(result,RecommendRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS){
                    List<Recommend> list = rsp.getAppList();
                    if (list!= null && list.size()>0){
                        int recType = Integer.parseInt(type);
                        if (Recommend.TYPE_WHEEL == recType){
                            adapter.refreshWheelPages(list);
                        }



//                        if ("0".equals(type)){
//                            adapter.refreshRecommends(list);
//                        }else{
//                            if (pageNo == 0){
//                                totalPage = rsp.getTotalPage();
//                            }
//                            if (isRefresh){
//                                adapter.refreshGuessYouLike(list);
//                            }else {
//                                adapter.loadMore(list);
//                            }
//
//                        }
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
