package com.strike.downba_app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.strike.downba_app.activity.SearchActivity;
import com.strike.downba_app.adapter.KeywordAdapter;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Keyword;
import com.strike.downba_app.http.request.KeywordsReq;
import com.strike.downba_app.http.response.KeywordsRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by strike on 16/6/6.
 */
public class SearchTitleBar extends RelativeLayout {

    @ViewInject(R.id.rv_user_icon)
    private ImageView rvUserIcon;

    @ViewInject(R.id.iv_manager)
    private ImageView ivManager;

    @ViewInject(R.id.edt_search)
    private EditText edtSearch;

    @ViewInject(R.id.btn_search)
    private ImageView btn_search;

    private View view;

    private int keySize = 5;
    private Context context;
    private PopupWindow popupWindow;
    private KeywordAdapter adapter;

    private List<Keyword> keywords;

    public SearchTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.extr_title, this, true);
        x.view().inject(view);
        edtSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showKeywords(edtSearch.getText().toString());
                } else {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtSearch.hasFocus()){
                    showKeywords(s.toString());
                    edtSearch.setSelection(s.toString().length());
                }
            }
        });
    }
    public void refresh(){
        if (!edtSearch.hasFocus()&& popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
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
    //根据关键词查询应用
    private void searAppByKeyword(String keyword){
        Intent intent = new Intent(context,SearchActivity.class);
        Bundle b = new Bundle();
        b.putString(Constance.KEYWORD,keyword);
        intent.putExtras(b);
        context.startActivity(intent);
    }
    //从列表中设置搜索关键词
    private void doSearch(String key) {
        if (edtSearch != null) {
            edtSearch.setText(key);
        }
        searAppByKeyword(key);
    }
    //显示关键词列表
    private void showPopuWindow() {
        if (popupWindow == null) {
            int width = edtSearch.getWidth();
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
                        doSearch(keyword.getQ());
                    }
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    edtSearch.clearFocus();
                }
            });
        }
        popupWindow.showAsDropDown(edtSearch);
        if (keywords == null || keywords.size() == 0) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
        adapter.refresh(keywords);
    }
    @Event(value = {R.id.iv_back, R.id.iv_manager, R.id.btn_search})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_back://用户信息界面
//                getContext().getA
                break;
            case R.id.iv_manager://app管理界面

                break;
            case R.id.btn_search://搜索界面
                String key = edtSearch.getText().toString();
                if (!"".equals(key)){
                    searAppByKeyword(key);
                }
                UiUtils.closeKeybord(edtSearch,context);
                break;
        }
    }

}
