package com.strike.downba_app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.strike.downba_app.adapter.AppLIstAdapter;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.PageBean;
import com.strike.downba_app.http.request.GetAppByCateIdReq;
import com.strike.downba_app.http.response.GetAppListRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.DownLoadUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by strike on 2016/11/12.
 */
@ContentView(R.layout.fragment_list)
public class ListFragment extends BaseFragment {

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private Integer cateId;
    private Integer orderType;
    private View view;
    private AppLIstAdapter adapter;
    private int pageNo = 1,pageSize = 7,total = 0;


    private Context context;

    private  BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constance.ACTION_DOWNLOAD.equals(intent.getAction())){
                Bundle bundle = intent.getExtras();
                String objId = bundle.getString(Constance.APP_ID,null);
                int position = bundle.getInt(Constance.POSITION,-1);
                int progress = bundle.getInt(Constance.PROGRESS,-1);
                int state = bundle.getInt(Constance.STATE,-1);
                adapter.refreshHolder(objId,position,state,progress);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        DownLoadUtils.unRegistReciver(getActivity(),receiver);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        adapter = new AppLIstAdapter(context);
        DownLoadUtils.registReciver(getActivity(),receiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater,container,savedInstanceState);
        pull_to_refresh.setAdapter(adapter);
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 1;
                getAppList(true,orderType,cateId,pageNo,pageSize);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (orderType == Constance.ORDER_HOT){
                    UiUtils.showTipToast(false,getString(R.string.this_is_last));
                    UiUtils.stopRefresh(pull_to_refresh);
                    return;
                }
                if (pageNo < total){
                    pageNo ++;
                }else{
                    UiUtils.showTipToast(false,getString(R.string.this_is_last));
                    UiUtils.stopRefresh(pull_to_refresh);
                    return;
                }
                getAppList(false,orderType,cateId,pageNo,pageSize);
            }
        });
        return view;
    }

    public void refresh(int orderType,int cateId){
        this.cateId = cateId;
        this.orderType = orderType;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!= null && adapter.getList().size() == 0 && orderType!=null&& cateId != null){
            getAppList(true,orderType,cateId,pageNo,pageSize);
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
                   adapter.refresh(list);
               }else{
                   adapter.addData(list);
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
