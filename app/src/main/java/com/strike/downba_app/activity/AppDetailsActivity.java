package com.strike.downba_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.strike.downba_app.adapter.HorizontalScrollViewAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.table.App;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.request.AppDetailsReq;
import com.strike.downba_app.http.response.AppDetailsRsp;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.DownLoadUtils;
import com.strike.downba_app.view.DownloadBtn;
import com.strike.downba_app.view.MyHorizontalScrollView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by strike on 16/6/21.
 */
@ContentView(R.layout.activity_detals)
public class AppDetailsActivity extends BaseActivity {


    @ViewInject(R.id.iv_app_icon)
    private ImageView iv_app_icon;

    @ViewInject(R.id.tv_app_title)
    private TextView tv_app_title;

    @ViewInject(R.id.app_score)
    private RatingBar app_score;

    @ViewInject(R.id.tv_size)
    private TextView tv_size;

    @ViewInject(R.id.tv_version)
    private TextView tv_version;

    @ViewInject(R.id.tv_down)
    private DownloadBtn tv_down;

    @ViewInject(R.id.tv_down_num)
    private TextView tv_down_num;

    @ViewInject(R.id.imgList)
    private MyHorizontalScrollView imgList;

    @ViewInject(R.id.tv_des)
    private TextView tv_des;

    private DownLoadUtils downloadUtils;

    private HorizontalScrollViewAdapter adapter;

    private App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadUtils = new DownLoadUtils(this);
        String appId = getIntent().getStringExtra(Constance.APP_ID);
        try {
            app = (App) getIntent().getExtras().getSerializable(Constance.APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (app != null) {
            initView();
        } else {
            if (appId != null) {
                getAppDetails(appId);
                showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
            }
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
    }

    @Event(value = {R.id.iv_back})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void getAppDetails(String app_id) {

        AppDetailsReq req = new AppDetailsReq(app_id);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    AppDetailsRsp rsp = (AppDetailsRsp) BaseResponse.getRsp(result, AppDetailsRsp.class);
                    if (rsp != null) {
                        if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                            app = rsp.getApp();
                            if (app != null)
                                initView();
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
            tv_size.setText("大小：" + app.getApp_size());
        }
        if (app.getApp_version() != null) {
            tv_version.setText("版本：" + app.getApp_version());
        }
        if (app.getApp_down() != null) {
            tv_down_num.setText("下载：" + app.getApp_down());
        }
        if (app.getResource() != null && app.getResource().size() > 0) {
            adapter = new HorizontalScrollViewAdapter(this, app.getResource());
            imgList.initDatas(adapter);
        }
        if (app.getApp_desc() != null) {
            tv_des.setText(Html.fromHtml(app.getApp_desc()));
        }
    }

}
