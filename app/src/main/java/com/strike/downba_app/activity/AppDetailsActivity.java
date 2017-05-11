package com.strike.downba_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strike.downba_app.adapter.CommentAdapter;
import com.strike.downba_app.adapter.GridRecommendAdapter;
import com.strike.downba_app.adapter.HorizontalScrollViewAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.dao.UserDao;
import com.strike.downba_app.db.table.User;
import com.strike.downba_app.download.DataChanger;
import com.strike.downba_app.download.DownloadInfo;
import com.strike.downba_app.download.Watcher;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.http.bean.AppInfo;
import com.strike.downba_app.http.bean.Comment;
import com.strike.downba_app.http.bean.Guess;
import com.strike.downba_app.http.req.AddCommentReq;
import com.strike.downba_app.http.req.AppDetailsReq;
import com.strike.downba_app.http.req.CommentReq;
import com.strike.downba_app.http.req.GuessReq;
import com.strike.downba_app.http.rsp.AddCommentRsp;
import com.strike.downba_app.http.rsp.AppDetailsRsp;
import com.strike.downba_app.http.rsp.CommentRsp;
import com.strike.downba_app.http.rsp.GuessRsp;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.view.MyHorizontalScrollView;
import com.strike.downba_app.view.MyListView;
import com.strike.downba_app.view.NoScrollGridView;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Observable;

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
    private TextView tv_down;

    @ViewInject(R.id.imgList)
    private MyHorizontalScrollView imgList;

    @ViewInject(R.id.tv_des)
    private TextView tv_des;

    @ViewInject(R.id.des_open)
    private TextView des_open;

    @ViewInject(R.id.comment_open)
    private ImageView comment_open;

    @ViewInject(R.id.fl_comment)
    private RelativeLayout fl_comment;

    @ViewInject(R.id.lv_comment)
    private MyListView lv_comment;

    @ViewInject(R.id.gv_recommend)
    private NoScrollGridView gv_recommend;

    @ViewInject(R.id.no_comment)
    private TextView no_comment;

    @ViewInject(R.id.comment_content)
    private EditText comment_content;

    @ViewInject(R.id.more_comment)
    private TextView more_comment;

    private HorizontalScrollViewAdapter adapter;

    private CommentAdapter commentAdapter;
    private GridRecommendAdapter suspectAdapter;

    private AppInfo app;
    private int commentNo = 2;//第一页已经显示，从第二页开始

    private Watcher watcher = new Watcher() {
        @Override
        public void ontifyDownloadDataChange(Observable observable, DownloadInfo info) {
            if (info != null && info.getObjId().equals(app.getApp_id())) {
                tv_down.setBackgroundColor(context.getResources().getColor(R.color.text_gray));
                switch (info.getState()) {
                    case WAITING:
                        tv_down.setText("队列中。。");
                        break;
                    case STARTED:
                        tv_down.setText(info.getProgress() + "%");
                        break;
                    case FINISHED:
                        tv_down.setText("打 开");
                        break;
                    case STOPPED:
                        tv_down.setText("继续下载");
                        break;
                    case ERROR:
                        tv_down.setText("重新下载");
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataChanger.getInstance().addObserver(watcher);
        int appId = getIntent().getIntExtra(Constance.ID, -1);
        if (appId > 0) {
            getAppDetails(appId);
        }
        imgList.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Intent intent = new Intent();
                intent.setClass(AppDetailsActivity.this, ImgDetailsActivity.class);
                intent.putExtra(ImgDetailsActivity.EXTRA_IMAGE_INDEX, pos);
                intent.putStringArrayListExtra(ImgDetailsActivity.EXTRA_IMAGE_URLS, app.getRes());
                AppDetailsActivity.this.startActivity(intent);
            }
        });
        commentAdapter = new CommentAdapter(this);
        suspectAdapter = new GridRecommendAdapter(this);
        lv_comment.setAdapter(commentAdapter);
        gv_recommend.setAdapter(suspectAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (watcher != null) {
            DataChanger.getInstance().deleteObserver(watcher);
        }
    }

    @Event(value = {R.id.iv_back, R.id.des_open, R.id.comment_open, R.id.btn_send, R.id.more_comment})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.des_open:
                tv_des.setText(Html.fromHtml(app.getApp_des()));
                des_open.setVisibility(View.GONE);
                break;
            case R.id.comment_open:
                if (fl_comment.getVisibility() == View.VISIBLE) {
                    fl_comment.setVisibility(View.GONE);
                    comment_open.setImageResource(R.mipmap.up);
                } else {
                    fl_comment.setVisibility(View.VISIBLE);
                    comment_open.setImageResource(R.mipmap.down);
                }
                break;
            case R.id.btn_send:
                UiUtils.closeKeybord(comment_content, AppDetailsActivity.this);
                String content = comment_content.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    addComment(content);
                    comment_content.setText("");
                } else {
                    UiUtils.showTipToast(false, "请输入评论内容");
                }
                break;
            case R.id.more_comment:
                //加载更多评论
                CommentReq req = new CommentReq(commentNo, Constance.DEFAULT_COMMENT_SIZE,
                        Constance.C_APP, app.getApp_id());
                showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
                req.sendRequest(new NormalCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        CommentRsp rsp = (CommentRsp) BaseRsp.getRsp(result, CommentRsp.class);
                        if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                            List<Comment> comments = rsp.resultData.comments;
                            if (comments != null && comments.size() > 0) {
                                commentAdapter.addData(comments);
                            }
                            if (rsp.resultData.total > commentAdapter.getList().size()) {
                                more_comment.setVisibility(View.VISIBLE);
                                commentNo++;
                            } else {
                                more_comment.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFinished() {
                        dismissProgressDialog();
                    }
                });
                break;
        }
    }

    private void addComment(String content) {
        Comment comment = new Comment();
        comment.setObj_id(app.getApp_id());
        comment.setObj_type(Constance.C_APP);
        comment.setContent(content);
        comment.setTitle(app.getApp_title());
        comment.setUpdate_time(System.currentTimeMillis());
        User user = UserDao.getUser();
        if (user != null) {
            comment.setUser_name(TextUtils.isEmpty(user.getNickname()) ? user.getUser_name() : user.getNickname());
        } else {
            comment.setUser_name(Constance.DEFAULT_NAME);
        }
        AddCommentReq req = new AddCommentReq(comment);
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                AddCommentRsp rsp = (AddCommentRsp) BaseRsp.getRsp(result, AddCommentRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                    UiUtils.showTipToast(true, "评论成功！");
                    List<Comment> list = rsp.resultData.list;
                    if (list != null && list.size() > 0) {
                        lv_comment.setVisibility(View.VISIBLE);
                        no_comment.setVisibility(View.GONE);
                        commentAdapter.refresh(list);
                        if (rsp.resultData.total > list.size()) {
                            more_comment.setVisibility(View.VISIBLE);
                        } else {
                            more_comment.setVisibility(View.GONE);
                        }
                    } else {
                        lv_comment.setVisibility(View.GONE);
                        no_comment.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }

    private void getSuspect() {
        GuessReq req = new GuessReq(app.getCate_id());
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                GuessRsp rsp = (GuessRsp) BaseRsp.getRsp(result, GuessRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                    Guess guess = rsp.resultData.guess;
                    if (guess != null) {
                        List<AppAd> list = guess.getObjs();
                        if (list != null && list.size() > 0) {
                            suspectAdapter.setList(list);
                        }
                    }
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getAppDetails(int app_id) {
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        AppDetailsReq req = new AppDetailsReq(app_id);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    AppDetailsRsp rsp = (AppDetailsRsp) BaseRsp.getRsp(result, AppDetailsRsp.class);
                    if (rsp != null) {
                        if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                            app = rsp.resultData.appInfo;
                            if (app != null) {
                                initView();
                                loadResource();
                                getSuspect();
                            }
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

    private void loadResource() {
        //游戏截图
        if (app.getRes() != null && app.getRes().size() > 0) {
            adapter = new HorizontalScrollViewAdapter(this, app.getRes());
            imgList.initDatas(adapter);
        }
        //评论列表
        if (app.getComments() != null && app.getComments().size() > 0) {
            lv_comment.setVisibility(View.VISIBLE);
            no_comment.setVisibility(View.GONE);
            commentAdapter.refresh(app.getComments());
        } else {
            lv_comment.setVisibility(View.GONE);
            no_comment.setVisibility(View.VISIBLE);
        }
        if (app.getTotalComment() > Constance.DEFAULT_COMMENT_SIZE) {
            more_comment.setVisibility(View.VISIBLE);
        } else {
            more_comment.setVisibility(View.GONE);
        }
        if (app.getApp_des() != null) {
            String des = app.getApp_des();
            des = des.substring(0, des.length() > Constance.DES_LENGTH ? Constance.DES_LENGTH : des.length()) + ".......";
            tv_des.setText(Html.fromHtml(des));
        }

    }

    private void initView() {
//        DownLoadUtils.initDownLoad(app,tv_down);
        if (app.getApp_logo() != null) {
            x.image().bind(iv_app_icon, app.getApp_logo(), ImgConfig.getImgOption());
        }
        if (app.getApp_title() != null) {
            tv_app_title.setText(app.getApp_title());
        }
        app_cate.setText(app.getCateName());
        int score = app.getApp_grade() / 2;
        app_score.setNumStars(score);
        if (app.getApp_size() != null) {
            tv_size.setText(app.getApp_size() + "MB");
        }
    }

}
