package com.strike.downba_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.strike.downba_app.adapter.InfoListAdapter;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Info;
import com.strike.downba_app.http.request.InfoReq;
import com.strike.downba_app.http.response.InfoRsp;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by strike on 16/8/7.
 */
@ContentView(R.layout.fragment_list)
public class InfoFragment extends BaseFragment {
    private View view;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private InfoListAdapter adapter;

    private int pageNo = 1,pageSize = 5,totalPage = 0;

    private Integer infoType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        adapter = new InfoListAdapter(getActivity());
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 1;
                getInfo(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (pageNo < totalPage) {
                    pageNo++;
                } else {
                    UiUtils.showTipToast(false, getString(R.string.this_is_last));
                    UiUtils.stopRefresh(pull_to_refresh);
                    return;
                }
                getInfo(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (infoType != null && adapter.getList().size()==0){
            getInfo(true);
        }
    }

    public void refresh(int infoType){
        this.infoType = infoType;
    }

    private void getInfo(final boolean isRefresh) {
        InfoReq req = new InfoReq(pageNo,pageSize,infoType);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                InfoRsp rsp = (InfoRsp) BaseResponse.getRsp(result,InfoRsp.class);
                List<Info> infoList = rsp.resultData.infoList;
                if (pageNo == 1){
                    totalPage = rsp.resultData.pageBean.getTotalPage();
                }
                if (infoList != null){
                    pull_to_refresh.setAdapter(adapter);
                    if (isRefresh){
                        adapter.refresh(infoList);
                    }else {
                        adapter.addData(infoList);
                    }

                }
            }
            @Override
            public void onFinished() {
                dismissProgressDialog();
                pull_to_refresh.onRefreshComplete();
            }
        });
    }

}
