package com.strike.downba_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.strike.downba_app.adapter.SubjectAdapter;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Subject;
import com.strike.downba_app.http.request.SubjectReq;
import com.strike.downba_app.http.response.SubjectRsp;
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
public class SubjectFragment extends BaseFragment {

    private View view;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private SubjectAdapter subjectAdapter;

    private int pageNo = 0,pageSize = 4,totalPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        subjectAdapter = new SubjectAdapter(getActivity());
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 0;
                getSubject(true);
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
                getSubject(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (subjectAdapter.getList()==null ||subjectAdapter.getList().size()==0){
            getSubject(true);
        }
    }

    private void getSubject(final boolean isRefresh) {
        SubjectReq req = new SubjectReq(pageNo, pageSize);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                SubjectRsp rsp = (SubjectRsp) BaseResponse.getRsp(result, SubjectRsp.class);
                if (pageNo == 0 || pageNo == 1){
                    totalPage = rsp.resultData.pageBean.getTotalPage();
                }
                List<Subject> list = rsp.resultData.subjects;
                pull_to_refresh.setAdapter(subjectAdapter);
                if (isRefresh) {
                    subjectAdapter.refresh(list);
                } else {
                    subjectAdapter.addData(list);
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
