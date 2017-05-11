package com.strike.downba_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.strike.downba_app.adapter.SubjectDetailsAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.bean.AppInfo;
import com.strike.downba_app.http.bean.Subject;
import com.strike.downba_app.http.req.SbDetailsReq;
import com.strike.downba_app.http.request.GetAppsByIdStringReq;
import com.strike.downba_app.http.rsp.GetAppListRsp;
import com.strike.downba_app.http.rsp.SbDetailsRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by strike on 16/8/3.
 */
@ContentView(R.layout.activity_subject)
public class SubjectActivity extends BaseActivity {

    @ViewInject(R.id.pull_to_refresh)
    private ListView pull_to_refresh;

    private Subject subject;
    private SubjectDetailsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int sbId = getIntent().getIntExtra(Constance.ID,-1);
        if (sbId != -1){
            getSubject(sbId);
        }
        adapter = new SubjectDetailsAdapter(this);
        pull_to_refresh.setAdapter(adapter);

    }

    private void getSubject(int sbId) {
        SbDetailsReq req = new SbDetailsReq(sbId);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                SbDetailsRsp rsp = (SbDetailsRsp) BaseRsp.getRsp(result,SbDetailsRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS){
                    subject = rsp.resultData.subject;
                    adapter.refresh(subject);
                }
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }

    private void getAppsByIdList(String idList){
        GetAppsByIdStringReq req = new GetAppsByIdStringReq(idList);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                GetAppListRsp rsp = (GetAppListRsp) BaseRsp.getRsp(result,GetAppListRsp.class);
                List<AppInfo> list = rsp.resultData.appList;
                if (list != null){
                    adapter.refreshApps(list);
                }
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }

    @Event(value = {R.id.iv_back,R.id.iv_manager,R.id.edt_search})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_manager:

                break;
            case R.id.edt_search:
                startActivity(new Intent(this,SearchActivity.class));
                break;
        }
    }
}
