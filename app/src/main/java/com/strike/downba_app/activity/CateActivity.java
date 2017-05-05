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
import com.strike.downba_app.download.DataChanger;
import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.download.Watcher;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Category;
import com.strike.downba_app.http.entity.PageBean;
import com.strike.downba_app.http.request.GetAppByCateIdReq;
import com.strike.downba_app.http.response.GetAppListRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Observable;

/**
 * Created by strike on 16/6/28.
 */
@ContentView(R.layout.activity_cate)
public class CateActivity extends BaseActivity {
    @ViewInject(R.id.iv_back)
    private ImageView iv_back;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private AppLIstAdapter adapter;
    private int pageNo = 1,pageSize = 7,total = 0;
    private Category category;
    private int orderType = Constance.ORDER_NEW;
    private Watcher watcher = new Watcher() {
        @Override
        public void ontifyDownloadDataChange(Observable observable, DownloadInfo info) {
            if (info != null){
                adapter.refreshHolder(info);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            category = (Category) getIntent().getSerializableExtra(Constance.CATE);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (category != null){
            tv_title.setText(category.getCname());
        }
        DataChanger.getInstance().addObserver(watcher);
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        adapter = new AppLIstAdapter(this);
        pull_to_refresh.setAdapter(adapter);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 1;
                getAppList(true,orderType,category.getCate_id(),pageNo,pageSize);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (pageNo < total){
                    pageNo ++;
                }else{
                    UiUtils.showTipToast(false,getString(R.string.this_is_last));
                    UiUtils.stopRefresh(pull_to_refresh);
                    return;
                }
                getAppList(false,orderType,category.getCate_id(),pageNo,pageSize);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAppList(false,orderType,category.getCate_id(),pageNo,pageSize);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (watcher != null){
            DataChanger.getInstance().deleteObserver(watcher);
        }
    }

    @Event(value = {R.id.iv_back})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
    private void getAppList(final boolean isRefresh, Integer orderType, Integer cateId, final int pageNo, int pageSize){
        final GetAppByCateIdReq req = new GetAppByCateIdReq(cateId,orderType,pageNo,pageSize);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                GetAppListRsp rsp = (GetAppListRsp) BaseResponse.getRsp(result,GetAppListRsp.class);
                List<App> list = rsp.getAppList();
                if (pageNo == 1 || pageNo == 0){
                    PageBean pageBean = rsp.getPageBean();
                    total = pageBean.getTotalPage();
                }
                if (isRefresh){
//                    adapter.refresh(list);
                }else{
//                    adapter.addData(list);
                }

            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
                pull_to_refresh.onRefreshComplete();
                long temp = System.currentTimeMillis() - this.getRequestTime();
                LogUtil.e(req.methodName+":请求时间 = "+ temp);
            }
        });
    }
}
