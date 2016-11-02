package com.strike.downba_app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.strike.downba_app.adapter.AppLIstAdapter;
import com.strike.downba_app.adapter.KeywordAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Keyword;
import com.strike.downba_app.http.request.GetAppByKeywordReq;
import com.strike.downba_app.http.request.KeywordsReq;
import com.strike.downba_app.http.response.GetAppListRsp;
import com.strike.downba_app.http.response.KeywordsRsp;
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

/**
 * Created by strike on 16/6/14.
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    @ViewInject(R.id.edt_search)
    private EditText edt_search;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    private PopupWindow popupWindow;
    private KeywordAdapter adapter;

    private List<Keyword> keywords;

    private String key;

    private int pageNo = 0,pageSize = 5,total,keySize = 5;
    private AppLIstAdapter appLIstAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null){
            key = bundle.getString(Constance.KEYWORD);
            if (key!= null){
                edt_search.setText(key);
            }
        }
        edt_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showKeywords(edt_search.getText().toString());
                } else {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showKeywords(s.toString());
                edt_search.setSelection(s.toString().length());
            }
        });
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        appLIstAdapter = new AppLIstAdapter(this);
        pull_to_refresh.setAdapter(appLIstAdapter);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 0;
                searchAppByKey(true,key);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if ( ++pageNo<= total){
                    searchAppByKey(false,key);
                }else {
                    UiUtils.showTipToast(false,getString(R.string.this_is_last));
                    UiUtils.stopRefresh(pull_to_refresh);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        key = edt_search.getText().toString();
        searchAppByKey(true,key);
    }

    @Event(value = {R.id.rv_back,R.id.btn_search})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.rv_back:
                finish();
                break;
            case R.id.btn_search:
                key = edt_search.getText().toString();
                if (popupWindow!= null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                searchAppByKey(true,key);
                UiUtils.closeKeybord(edt_search,this);
                break;
        }
    }
    //获取热搜词列表
    private void showKeywords(String keyword) {
        KeywordsReq req = new KeywordsReq(keyword, keySize);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    KeywordsRsp rsp = (KeywordsRsp) BaseResponse.getRsp(result, KeywordsRsp.class);
                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS) {
                        keywords = rsp.getKeywords();
                        showPopuWindow();
                    }
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void searchAppByKey(final boolean isRefresh, String key){
        GetAppByKeywordReq req = new GetAppByKeywordReq(key,pageNo,pageSize);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)){
                    GetAppListRsp rsp = (GetAppListRsp) BaseResponse.getRsp(result,GetAppListRsp.class);
                    if (rsp!= null && rsp.result == HttpConstance.HTTP_SUCCESS){
                        if (pageNo == 0){
                            total = rsp.getTotalPage();
                        }
                        List<App> list = rsp.getAppList();
                        if (isRefresh){
                            appLIstAdapter.refresh(list);
                        }else{
                            appLIstAdapter.getList().addAll(list);
                            appLIstAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
                LogUtil.e("pageNo = "+pageNo+",totalPage = "+total);
            }
        });
    }
    //显示关键词列表
    private void showPopuWindow() {
        if (popupWindow == null) {
            int width = edt_search.getWidth();
            View viewContent = LayoutInflater.from(context).inflate(R.layout.pop_keyword, null);
            popupWindow = new PopupWindow(viewContent, width, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(viewContent);

            ListView listView = (ListView) viewContent.findViewById(R.id.lv_key);
            adapter = new KeywordAdapter(context, keywords);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Keyword keyword = keywords.get(position);
                    if (keyword != null && keyword.getQ() != null) {
                        edt_search.setText(keyword.getQ());
                        searchAppByKey(true,keyword.getQ());
                    }
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
        }
        popupWindow.showAsDropDown(edt_search);
        if (keywords == null || keywords.size() == 0) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
        adapter.refresh(keywords);
    }
}
