package com.strike.downba_app.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.strike.downba_app.adapter.AppLIstAdapter;
import com.strike.downba_app.adapter.CategoryAdapter;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Category;
import com.strike.downba_app.http.request.GetAppByCateIdReq;
import com.strike.downba_app.http.request.GetCategoryReq;
import com.strike.downba_app.http.response.GetAppListRsp;
import com.strike.downba_app.http.response.GetCategoryRsp;
import com.strike.downba_app.utils.PullToRefreshUtils;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.library.PullToRefreshBase;
import com.strike.downba_app.view.library.PullToRefreshListView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/5.
 */
@ContentView(R.layout.frament_game)
public class GameFragment extends BaseFragment {

    private final int type_hot = 1;
    private final int type_new = 0;

    private final int game = 2;

    @ViewInject(R.id.rg_nav)
    private RadioGroup rg_nav;

    @ViewInject(R.id.pull_to_refresh)
    private PullToRefreshListView pull_to_refresh;

    @ViewInject(R.id.app_class)
    private RadioButton app_class;

    private AppLIstAdapter adapter;
    private int pageNo = 0,pageSize = 6,total = 0;
    private int currrentCateId= -1;//当前分类id

    private PopupWindow popupWindow;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private View view;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        context = getActivity();
        rg_nav.check(R.id.app_hot);
        pull_to_refresh.setMode(PullToRefreshBase.Mode.BOTH);
        PullToRefreshUtils.initRefresh(pull_to_refresh);
        adapter = new AppLIstAdapter(getContext());
        pull_to_refresh.setAdapter(adapter);
        rg_nav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.app_class:
                        if (categoryList!= null && categoryList.size()>0 && popupWindow != null){
                            popupWindow.showAsDropDown(app_class);
                        } else {
                            getGameClass();
                        }
                        break;
                    case R.id.app_hot:
                        getContent(game,type_hot,true);//按下载最多排序查询游戏列表
                        break;
                    case R.id.app_new:
                        getContent(game,type_new,true);//按最新更新排序获取游戏列表
                        break;
                }
            }
        });
        pull_to_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 0;
                switch (rg_nav.getCheckedRadioButtonId()){
                    case R.id.app_class:
                        if (currrentCateId == -1){
                            return;
                        }
                        getContent(currrentCateId,type_hot,true);
                        break;
                    case R.id.app_hot:
                        getContent(game,type_hot,true);//按下载最多排序查询游戏列表
                        break;
                    case R.id.app_new:
                        getContent(game,type_new,true);//按最新更新排序获取游戏列表
                        break;

                }
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
                switch (rg_nav.getCheckedRadioButtonId()){
                    case R.id.app_class:
                        if (currrentCateId == -1){
                            return;
                        }
                        getContent(currrentCateId,type_hot,false);
                        break;
                    case R.id.app_hot:
                        getContent(game,type_hot,false);//按下载最多排序查询游戏列表
                        break;
                    case R.id.app_new:
                        getContent(game,type_new,false);//按最新更新排序获取游戏列表
                        break;
                }
            }
        });
        app_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryList!= null && categoryList.size()>0 && popupWindow != null){
                    popupWindow.showAsDropDown(app_class);
                }else{
                    getGameClass();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContent(game,type_hot,true);
    }

    private void getContent(int cate_id, int orederType, final boolean isRefresh){
        GetAppByCateIdReq req = new GetAppByCateIdReq(cate_id,orederType,pageNo+"",pageSize+"");
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)){
                    GetAppListRsp rsp = (GetAppListRsp) BaseResponse.getRsp(result,GetAppListRsp.class);
                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS){
                        List<App> list = rsp.getAppList();
                        if (pageNo == 0){
                           total = rsp.getTotalPage();
                        }
                        if (isRefresh){
                            adapter.refresh(list);
                        }else {
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

    private void getGameClass(){
        GetCategoryReq req = new GetCategoryReq(game);
        showProgressDialogCloseDelay(getString(R.string.loading),HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)){
                    GetCategoryRsp rsp = (GetCategoryRsp) BaseResponse.getRsp(result,GetCategoryRsp.class);
                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS){
                        categoryList= rsp.getResultList();
                        if (categoryList.size()>0){
                            showPopuWindow(categoryList);
                        }
                    }
                }
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }

    //显示关键词列表
    private void showPopuWindow(final List<Category> categoryList) {
        if (popupWindow == null) {
            int width = app_class.getWidth();
            View viewContent = LayoutInflater.from(getContext()).inflate(R.layout.pop_keyword, null);
            popupWindow = new PopupWindow(viewContent, width, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setContentView(viewContent);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            ListView listView = (ListView) viewContent.findViewById(R.id.lv_key);
            categoryAdapter = new CategoryAdapter(getContext(), categoryList);
            listView.setAdapter(categoryAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i=0;i<categoryList.size();i++){
                       if (i == position){
                           categoryList.get(i).setChecked(true);
                           currrentCateId = categoryList.get(i).getCate_id();//设置当前选中的分类
                       }else {
                           categoryList.get(i).setChecked(false);
                       }
                    }
                    categoryAdapter.notifyDataSetChanged();
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    getContent(currrentCateId,type_hot,true);
                }
            });
        }
        popupWindow.showAsDropDown(app_class);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (rg_nav.getCheckedRadioButtonId() == -1){
            rg_nav.check(R.id.app_hot);
        }
    }
}
