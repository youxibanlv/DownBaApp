package com.strike.downba_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.strike.downba_app.activity.LoginActivity;
import com.strike.downba_app.activity.ManagerActivity;
import com.strike.downba_app.activity.UserInfoActivity;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.base.MyApplication;
import com.strike.downba_app.fragment.AppFragment;
import com.strike.downba_app.fragment.ArticleFragment;
import com.strike.downba_app.fragment.GameFragment;
import com.strike.downba_app.fragment.HomeFragment;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.bean.DevInfo;
import com.strike.downba_app.http.req.ReportDevReq;
import com.strike.downba_app.http.rsp.DevInfoRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.utils.UpdateManager;
import com.strike.downba_app.view.IconTabPageIndicator;
import com.strike.downba_app.view.TabAdapter;
import com.strike.downbaapp.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    /**
     * The instance.
     */
    public static MainActivity instance;

    private int retry = 1;

    /**
     * The Constant TAG.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    @ViewInject(R.id.indicator)
    private IconTabPageIndicator mIndicator;

    @ViewInject(R.id.view_pager)
    private ViewPager mViewPager;


    private long mExitTime;

    private TabAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        List<BaseFragment> fragments = initFragments();
        mAdapter = new TabAdapter(fragments, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size());//三页都进行预加载，避免每次都多次切换进行重新创建
        mIndicator.setViewPager(mViewPager);
        //上报设备信息
        reportDevInfo();
        //版本更新
        new UpdateManager(this).checkUpdate(true);
    }
    private void reportDevInfo() {
        DevInfo info = MyApplication.devInfo;
        ReportDevReq req = new ReportDevReq(info);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                DevInfoRsp rsp = (DevInfoRsp) BaseRsp.getRsp(result,DevInfoRsp.class);
                if (rsp != null){
                    if (rsp.result == HttpConstance.HTTP_SUCCESS){
                        MyApplication.devInfo = rsp.resultData.devInfo;
                    }else {
                        if (retry<4){
                            reportDevInfo();
                            LogUtil.e("上报设备信息失败，进行第"+retry+"次上报");
                            retry++;
                        }else {
                            LogUtil.e("上报设备信息失败");
                        }
                    }
                }

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Event(value = {R.id.rv_user_icon, R.id.iv_manager, R.id.title_bar})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.rv_user_icon://用户信息界面
                String token = MyApplication.token;
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                }
                break;
            case R.id.iv_manager://app管理界面
                startActivity(new Intent(MainActivity.this, ManagerActivity.class));
                break;
            case R.id.title_bar://搜索界面
//                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
    }

    private List<BaseFragment> initFragments() {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>();
        BaseFragment recommend = new HomeFragment();
        recommend.setTitle("推荐");
        recommend.setIconId(R.drawable.recommend_icon_selector);
        fragments.add(recommend);

        BaseFragment game = new GameFragment();
        game.setTitle("游戏");
        game.setIconId(R.drawable.game_icon_selector);
        Bundle gameBundle = new Bundle();
        gameBundle.putInt("cateId", Constance.PARENT_GAME);
        game.setArguments(gameBundle);
        fragments.add(game);

        BaseFragment app = new AppFragment();
        app.setTitle("应用");
        app.setIconId(R.drawable.app_icon_selector);
        Bundle appBundle = new Bundle();
        appBundle.putInt("cateId", Constance.PARENT_APP);
        app.setArguments(appBundle);


        fragments.add(app);

        BaseFragment article = new ArticleFragment();
        article.setTitle("资讯");
        article.setIconId(R.drawable.info_icon_selector);
        fragments.add(article);

        return fragments;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                UiUtils.showTipToast(true,getString(R.string.press_to_exit));
                mExitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
