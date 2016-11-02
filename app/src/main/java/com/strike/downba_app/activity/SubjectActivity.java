package com.strike.downba_app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.strike.downba_app.adapter.SubjectDetailsAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Subject;
import com.strike.downba_app.http.request.GetAppsByIdListReq;
import com.strike.downba_app.http.response.GetAppListRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
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

    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.area_html)
    private TextView area_html;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private Subject subject;
    private SubjectDetailsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            try {
                subject = (Subject) bundle.getSerializable(Constance.SUBJECT);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        adapter = new SubjectDetailsAdapter(this);
        pull_to_refresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        adapter = new SubjectDetailsAdapter(this);
        pull_to_refresh.setAdapter(adapter);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getAppsByIdList(subject.getId_list());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (subject != null){
            title.setText(subject.getTitle());
            area_html.setText(Html.fromHtml(subject.getArea_html()));
            getAppsByIdList(subject.getId_list());
        }
    }

    private void getAppsByIdList(String idList){
        GetAppsByIdListReq req = new GetAppsByIdListReq(idList);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                GetAppListRsp rsp = (GetAppListRsp) BaseResponse.getRsp(result,GetAppListRsp.class);
                List<App> list = rsp.getAppList();
                if (list != null){
                    adapter.refresh(list);
                }
            }

            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
                dismissProgressDialog();
            }
        });
    }

    @Event(value = {R.id.iv_back})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
