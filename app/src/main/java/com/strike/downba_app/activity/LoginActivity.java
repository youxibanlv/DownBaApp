package com.strike.downba_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.strike.downba_app.MainActivity;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.base.MyApplication;
import com.strike.downba_app.db.dao.UserDao;
import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.req.LoginReq;
import com.strike.downba_app.http.req.ThirdLoginReq;
import com.strike.downba_app.http.rsp.UserInfoRsp;
import com.strike.downba_app.login.LoginApi;
import com.strike.downba_app.login.OnLoginListener;
import com.strike.downba_app.login.UserInfo;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by strike on 16/6/3.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.edt_username)
    private EditText edtUsername;

    @ViewInject(R.id.edt_password)
    private EditText edtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
    }

    private void login(String userName, String password) {
        LoginReq loginReq = new LoginReq(userName, password);
        showProgressDialogCloseDelay("登录中，请稍后...", HttpConstance.DEFAULT_TIMEOUT);
        loginReq.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                UserInfoRsp rsp = (UserInfoRsp) BaseRsp.getRsp(result, UserInfoRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                    User user = rsp.resultData.user;
                    UserDao.saveUser(user);
                    MyApplication.token = user.getUser_id();
                    UiUtils.showTipToast(true, getString(R.string.login_success));
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                }
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }

    @Event(value = {R.id.iv_back, R.id.btn_login, R.id.login_weixin, R.id.login_qq, R.id.login_weibo, R.id.tv_forgot_pass, R.id.tv_register})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                String userName = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (!VerifyUtils.checkUserName(userName)) {
                    UiUtils.showTipToast(false, getString(R.string.format_error_username));
                    return;
                }
                if (!VerifyUtils.checkPassword(password)) {
                    UiUtils.showTipToast(false, getString(R.string.format_error_password));
                    return;
                }
                login(userName, password);
                break;
            case R.id.tv_forgot_pass:

                break;
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.login_qq:
                login("QQ");
                break;
            case R.id.login_weixin:
                login("Wechat");
                break;
            case R.id.login_weibo:
                login("SinaWeibo");
                break;
        }
    }

    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                User user = new User();
                if ("SinaWeibo".equals(platform)) {
                    user.setUser_name("SinaWeibo" + res.get("id"));
                    user.setUser_icon((String) res.get("profile_image_url"));
                    user.setNickname((String) res.get("name"));
                }else if ("QQ".equals(platform)){

                }else if ("".equals(platform)){

                }
                if (TextUtils.isEmpty(user.getUser_name())){
                    return true;
                }
                ThirdLoginReq req = new ThirdLoginReq(user);
                req.sendRequest(new NormalCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        UserInfoRsp rsp = (UserInfoRsp) BaseRsp.getRsp(result, UserInfoRsp.class);
                        if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                            User user = rsp.resultData.user;
                            UserDao.saveUser(user);
                            MyApplication.token = user.getUser_id();
                            UiUtils.showTipToast(true, getString(R.string.login_success));
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                        }else {
                            UiUtils.showTipToast(false,rsp.failReason);
                        }
                    }

                    @Override
                    public void onFinished() {
                        dismissProgressDialog();
                    }
                });
                return false;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(this);
    }
}
