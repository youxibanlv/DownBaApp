package com.strike.downba_app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strike.downba_app.adapter.AppLIstAdapter;
import com.strike.downba_app.adapter.KeywordAdapter;
import com.strike.downba_app.adapter.SpacesItemDecoration;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.bean.AppInfo;
import com.strike.downba_app.http.entity.Keyword;
import com.strike.downba_app.http.bean.PageBean;
import com.strike.downba_app.http.request.GetAppByKeywordReq;
import com.strike.downba_app.http.request.KeywordsReq;
import com.strike.downba_app.http.response.KeywordsRsp;
import com.strike.downba_app.http.rsp.GetAppListRsp;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/14.
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    private static final int SHOWDEFAULT = 1;
    private static final int SHOWAPPS = 2;

    @ViewInject(R.id.edt_search)
    private EditText edt_search;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    @ViewInject(R.id.rl_key)
    private RecyclerView rl_key;
    @ViewInject(R.id.rl_default_key)
    private RelativeLayout rl_default_key;

    private PopupWindow popupWindow;
    private KeywordAdapter adapter;
    private KeyAdapter defaultKeyAdapter;

    private List<Keyword> keywords;
    private PageBean pageBean;


    private int pageNo = 0,pageSize = 7,total,keySize = 5;
    private AppLIstAdapter appLIstAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOWDEFAULT:
                    pull_to_refresh.setVisibility(View.GONE);
                    rl_default_key.setVisibility(View.VISIBLE);
                    break;
                case SHOWAPPS:
                    pull_to_refresh.setVisibility(View.VISIBLE);
                    rl_default_key.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        appLIstAdapter = new AppLIstAdapter(this);
        pull_to_refresh.setAdapter(appLIstAdapter);

        rl_key.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
        defaultKeyAdapter = new KeyAdapter();
        rl_key.setAdapter(defaultKeyAdapter);
        rl_key.addItemDecoration(new SpacesItemDecoration(16));
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefaultKey();
    }

    private void setListener(){
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
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 0;
                String key = edt_search.getText().toString();
                searchAppByKey(true,key);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if ( ++pageNo<= total){
                    String key = edt_search.getText().toString();
                    searchAppByKey(false,key);
                }else {
                    UiUtils.showTipToast(false,getString(R.string.this_is_last));
                    UiUtils.stopRefresh(pull_to_refresh);
                }

            }
        });
    }
    //获取热搜词
    private void getDefaultKey(){
        KeywordsReq req = new KeywordsReq("", 12);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                KeywordsRsp rsp = (KeywordsRsp) BaseResponse.getRsp(result,KeywordsRsp.class);
                List<Keyword> list = rsp.getKeywords();
                if (list!= null && list.size()>0){
                   defaultKeyAdapter.refresh(list);
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }
    @Event(value = {R.id.iv_back,R.id.btn_search})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_search:
                String key = edt_search.getText().toString();
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
    //热搜词适配
    class KeyAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<Keyword> list = new ArrayList<>();

        public void refresh(List<Keyword> list){
            this.list = list;
            notifyDataSetChanged();
        }
        public KeyAdapter() {
            list.clear();
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_key,parent,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
           final Keyword keyword = list.get(position);
            holder.keyword.setText(keyword.getQ());
            if (position<3){
                holder.keyword.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_keyword_select));
                holder.keyword.setTextColor(context.getResources().getColor(R.color.default_bg));
            }else{
                holder.keyword.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_keyword_normal));
                holder.keyword.setTextColor(context.getResources().getColor(R.color.text_gray));
            }
            holder.keyword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pageNo = 1;
                    edt_search.setText(keyword.getQ());
                    searchAppByKey(true,keyword.getQ());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //holder
    class ViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.keyword)
        TextView keyword;

        public ViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }
    }
    //搜索结果适配
    private void searchAppByKey(final boolean isRefresh, String key){

        GetAppByKeywordReq req = new GetAppByKeywordReq(key,pageNo,pageSize);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)){
                    GetAppListRsp rsp = (GetAppListRsp) BaseRsp.getRsp(result,GetAppListRsp.class);
                    if (rsp!= null && rsp.result == HttpConstance.HTTP_SUCCESS){
                        List<AppInfo> list = rsp.resultData.appList;
                        pageBean = rsp.resultData.pageBean;
                        total = pageBean.getTotalPage();
                        if (list != null && list.size()>0){
                            handler.obtainMessage(SHOWAPPS).sendToTarget();
                            if (isRefresh){
                                appLIstAdapter.refresh(list);
                            }else{
                                appLIstAdapter.addData(list);
                            }
                        }else {
                            handler.obtainMessage(SHOWDEFAULT).sendToTarget();
                        }
                    }else {
                        handler.obtainMessage(SHOWDEFAULT).sendToTarget();
                    }
                }
            }

            @Override
            public void onFinished() {
                pull_to_refresh.onRefreshComplete();
                if (popupWindow!= null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
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
                        pageNo=1;
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
