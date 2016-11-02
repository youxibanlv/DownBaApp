package com.strike.downba_app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.strike.downba_app.adapter.AppLIstAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.request.RecommendReq;
import com.strike.downba_app.http.response.GetAppListRsp;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by strike on 16/6/28.
 */
@ContentView(R.layout.activity_recommend)
public class RecommendActivity extends BaseActivity {
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private AppLIstAdapter adapter;
    private int pageNo = 0,pageSize = 5,total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        adapter = new AppLIstAdapter(this);
        pull_to_refresh.setAdapter(adapter);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 0;
                getRecommend(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo ++;
                if (pageNo<= total){
                    getRecommend(false);
                }else {
                    UiUtils.showTipToast(false,getString(R.string.this_is_last));
                    UiUtils.stopRefresh(pull_to_refresh);
                }

            }
        });
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        getRecommend(true);
    }

    @Event(value = {R.id.iv_back})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
    //加载精品推荐
    private void getRecommend(final boolean isRefresh){
        RecommendReq req = new RecommendReq(String.valueOf(pageNo),String.valueOf(pageSize),"0");
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                GetAppListRsp rsp = (GetAppListRsp) BaseResponse.getRsp(result,GetAppListRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS){
                    if (pageNo == 0){
                        total = rsp.getTotalPage();
                    }
                    List<App> list = rsp.getAppList();
                    if (list!= null && list.size()>0){
                        if (isRefresh){
                            adapter.refresh(list);
                        }else{
                            adapter.addData(list);
                        }
                    }
                }
            }

            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
                dismissProgressDialog();
            }
        });
    }
}
