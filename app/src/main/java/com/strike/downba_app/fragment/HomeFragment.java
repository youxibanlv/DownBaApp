package com.strike.downba_app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.strike.downba_app.adapter.HomeAdapter;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.download.DataChanger;
import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.download.Watcher;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.http.bean.AppHome;
import com.strike.downba_app.http.entity.Recommend;
import com.strike.downba_app.http.req.AppHomeReq;
import com.strike.downba_app.http.req.WheelReq;
import com.strike.downba_app.http.rsp.AppHomeRsp;
import com.strike.downba_app.http.rsp.WheelRsp;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Observable;

/**
 * Created by strike on 16/6/5.
 */
@ContentView(R.layout.fragment_list)
public class HomeFragment extends BaseFragment {

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private HomeAdapter adapter;

    private int pageNo = 1;

    private long waitTime = 0,lastRefreshTime = 0,minWaitTime = 15000;//等待时间，最后一次刷新时间,最小等待时间，用于控制用户的刷新频率
    private View view;
    private Watcher watcher = new Watcher() {
        @Override
        public void ontifyDownloadDataChange(Observable observable, DownloadInfo info) {
            if (info != null){
                adapter.refreshHolder(info);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataChanger.getInstance().addObserver(watcher);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (watcher != null){
            DataChanger.getInstance().deleteObserver(watcher);
        }
    }

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
                    getWheel(String.valueOf(Recommend.TYPE_WHEEL));
                    getHomeList(pageNo,true);
                }else{
                    waitTime = System.currentTimeMillis() - lastRefreshTime;
                    showTipToast(false,String.format(getString(R.string.refresh_too_fast),minWaitTime/1000));
                    UiUtils.stopRefresh(pull_to_refresh);
                }
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    pageNo ++;
                   getHomeList(pageNo,false);
            }
        });
        //加载轮播图片
        getWheel(String.valueOf(Recommend.TYPE_WHEEL));
        getHomeList(1,true);
        return view;
    }

    private void getHomeList(int pageNo,  final boolean isRefresh){
        final AppHomeReq req = new AppHomeReq(pageNo);
        showProgressDialogCloseDelay(getString(R.string.loading),HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                AppHomeRsp rsp = (AppHomeRsp) BaseRsp.getRsp(result,AppHomeRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS){
                    AppHome home = rsp.resultData.appHome;
                    if (home != null){
                        if (isRefresh){
                            adapter.refreshHome(home);
                        }else{
                            adapter.loadMore(home);
                        }
                        adapter.notifyDataSetChanged();
                    }else {
                        showTipToast(false,"已经到底了");
                    }
                }
            }

            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
                dismissProgressDialog();
                pull_to_refresh.onRefreshComplete();
                long temp = System.currentTimeMillis() - this.getRequestTime();
                LogUtil.e(req.methodName+":请求时间 = "+ temp);
            }
        });
    }
    //加载轮播图
    private void getWheel(final String type){
        final WheelReq req = new WheelReq();
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                WheelRsp rsp = (WheelRsp) BaseRsp.getRsp(result,WheelRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS){
                    List<AppAd> list = rsp.resultData.wheels;
                    if (list!= null && list.size()>0){
                        adapter.refreshWheelPages(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
                long temp = System.currentTimeMillis() - this.getRequestTime();
                LogUtil.e(req.methodName+":请求时间 = "+ temp);
            }
        });
    }


}
