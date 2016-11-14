package com.strike.downba_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downba_app.adapter.CommentAdapter;
import com.strike.downba_app.adapter.HorizontalScrollViewAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.entity.Category;
import com.strike.downba_app.http.request.AppDetailsReq;
import com.strike.downba_app.http.request.GetCategoryReq;
import com.strike.downba_app.http.response.AppDetailsRsp;
import com.strike.downba_app.http.response.GetCategoryRsp;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.DownLoadUtils;
import com.strike.downba_app.utils.NumberUtil;
import com.strike.downba_app.view.DownloadBtn;
import com.strike.downba_app.view.MyHorizontalScrollView;
import com.strike.downba_app.view.MyListView;
import com.strike.downba_app.view.NoScrollGridView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/21.
 */
@ContentView(R.layout.activity_detals)
public class AppDetailsActivity extends BaseActivity {


    @ViewInject(R.id.iv_app_icon)
    private ImageView iv_app_icon;

    @ViewInject(R.id.tv_app_title)
    private TextView tv_app_title;

    @ViewInject(R.id.app_cate)
    private TextView app_cate;//分类

    @ViewInject(R.id.app_score)
    private RatingBar app_score;

    @ViewInject(R.id.tv_size)
    private TextView tv_size;

    @ViewInject(R.id.tv_down)
    private DownloadBtn tv_down;

    @ViewInject(R.id.imgList)
    private MyHorizontalScrollView imgList;

    @ViewInject(R.id.tv_des)
    private TextView tv_des;

    @ViewInject(R.id.des_open)
    private ImageView des_open;

    @ViewInject(R.id.comment_open)
    private ImageView comment_open;

    @ViewInject(R.id.fl_comment)
    private FrameLayout fl_comment;

    @ViewInject(R.id.lv_comment)
    private MyListView lv_comment;

    @ViewInject(R.id.gv_recommend)
    private NoScrollGridView gv_recommend;

    @ViewInject(R.id.no_comment)
    private TextView no_comment;

    @ViewInject(R.id.comment_content)
    private EditText comment_content;

    @ViewInject(R.id.btn_send)
    private ImageView btn_send;

    private DownLoadUtils downloadUtils;

    private HorizontalScrollViewAdapter adapter;

    private CommentAdapter commentAdapter;

    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadUtils = new DownLoadUtils(this);
        commentAdapter = new CommentAdapter(this);
        try {
            app = (App) getIntent().getExtras().getSerializable(Constance.APP);
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgList.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Intent intent = new Intent();
                intent.setClass(AppDetailsActivity.this, ImgDetailsActivity.class);
                intent.putExtra(ImgDetailsActivity.EXTRA_IMAGE_INDEX, pos);
                intent.putStringArrayListExtra(ImgDetailsActivity.EXTRA_IMAGE_URLS, (ArrayList<String>) app.getResource());
                AppDetailsActivity.this.startActivity(intent);
            }
        });
        des_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_des.getVisibility() == View.VISIBLE){
                    tv_des.setVisibility(View.GONE);
                    des_open.setImageResource(R.mipmap.up);
                }else {
                    tv_des.setVisibility(View.VISIBLE);
                    des_open.setImageResource(R.mipmap.down);
                }
            }
        });
        comment_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fl_comment.getVisibility() ==View.VISIBLE){
                    fl_comment.setVisibility(View.GONE);
                    comment_open.setImageResource(R.mipmap.up);
                }else{
                    fl_comment.setVisibility(View.VISIBLE);
                    comment_open.setImageResource(R.mipmap.down);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (app != null){
            getAppDetails(app.getApp_id());
            getCate(NumberUtil.parseToInt(app.getLast_cate_id()));
        }
        lv_comment.setAdapter(commentAdapter);
    }

    @Event(value = {R.id.iv_back})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void getCate(int cateId){
        GetCategoryReq req = new GetCategoryReq(cateId);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                GetCategoryRsp rsp = (GetCategoryRsp) BaseResponse.getRsp(result,GetCategoryRsp.class);
                List<Category> list = rsp.getResultList();
                if (list!= null && list.size()>0){
                    Category category = list.get(0);
                    if (category!= null){
                        app_cate.setText(category.getCname());
                    }
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getAppDetails(String app_id) {
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        AppDetailsReq req = new AppDetailsReq(app_id);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    AppDetailsRsp rsp = (AppDetailsRsp) BaseResponse.getRsp(result, AppDetailsRsp.class);
                    if (rsp != null) {
                        if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                            App app1 = rsp.getApp();
                            if (app1 != null)
                                if (app1.getResource()!= null){
                                    app.setResource(app1.getResource());
                                }
                                if (app1.getCommentList()!= null){
                                    app.setCommentList(app1.getCommentList());
                                }
                                loadResource();
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

    private void loadResource(){
        //游戏截图
        if (app.getResource() != null && app.getResource().size() > 0) {
            adapter = new HorizontalScrollViewAdapter(this, app.getResource());
            imgList.initDatas(adapter);
        }
        //评论列表
        if (app.getCommentList()!= null && app.getCommentList().size()>0){
            commentAdapter.refresh(app.getCommentList());
            lv_comment.setVisibility(View.VISIBLE);
            no_comment.setVisibility(View.GONE);
        }else{
            lv_comment.setVisibility(View.GONE);
            no_comment.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        downloadUtils.initDownLoad(app, tv_down);
        if (app.getApp_logo() != null) {
            x.image().bind(iv_app_icon, app.getApp_logo(), ImgConfig.getImgOption());
        }
        if (app.getApp_title() != null) {
            tv_app_title.setText(app.getApp_title());
        }
        int score = app.getApp_recomment() == null ? 0 : (int) (Float.parseFloat(app.getApp_recomment()) / 2);
        app_score.setNumStars(score);
        if (app.getApp_size() != null) {
            tv_size.setText(app.getApp_size());
        }
        if (app.getApp_desc() != null) {
            tv_des.setText(Html.fromHtml(app.getApp_desc()));
        }
    }

}
