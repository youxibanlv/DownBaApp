package com.strike.downba_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.strike.downba_app.MainActivity;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.dao.UserDao;
import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.request.LoginReq;
import com.strike.downba_app.http.response.LoginRsp;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gson.Gson;

/**
 * Created by strike on 16/6/3.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.edt_username)
    private EditText edtUsername;

    @ViewInject(R.id.edt_password)
    private EditText edtPassword;

    @ViewInject(R.id.ivpwd_showhide)
    private ImageView ivpwdShowhide;

    private boolean isShowPass = false;//是明文显示密码

    private int inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void login(String userName, String password) {
        LoginReq loginReq = new LoginReq(userName,password);
        showProgressDialogCloseDelay("登录中，请稍后...", HttpConstance.DEFAULT_TIMEOUT);
        loginReq.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                dismissProgressDialog();
                if (!TextUtils.isEmpty(result)){
                    Gson gson = new Gson();
                    LoginRsp rsp = (LoginRsp) BaseResponse.getRsp(result,LoginRsp.class);
                    if (rsp!= null){
                        if (rsp.result == HttpConstance.HTTP_SUCCESS){
                             User user = gson.fromJson(gson.toJson(rsp.resultData), User.class);
                            if (user != null) {
                                UserDao.saveUser(user);
                                UiUtils.showTipToast(true, getString(R.string.login_success));
                                LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                LoginActivity.this.finish();
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

    @Event(value = {R.id.btn_login,R.id.btn_qq,R.id.btn_weixin,R.id.btn_weibo,R.id.tv_forgot_pass,R.id.tv_register,R.id.ivpwd_showhide})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.btn_login:
                String userName = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (!VerifyUtils.checkUserName(userName)){
                    UiUtils.showTipToast(false,getString(R.string.format_error_username));
                    return;
                }
                if (!VerifyUtils.checkPassword(password)){
                    UiUtils.showTipToast(false,getString(R.string.format_error_password));
                    return;
                }
                login(userName,password);
                break;
            case R.id.ivpwd_showhide:
                if (isShowPass){
                    isShowPass = false;
                    inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                }else{
                    isShowPass = true;
                    inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                }
                ivpwdShowhide.setImageResource(isShowPass ? R.mipmap.pwd_show : R.mipmap.pwd_hide);
                edtPassword.setInputType(inputType);
                edtPassword.setSelection(edtPassword.getText().toString().length());//光标聚焦到行尾
                break;
            case R.id.tv_forgot_pass:

                break;
            case R.id.tv_register:
                Intent intent = new Intent(this,RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btn_qq:

                break;
            case R.id.btn_weixin:

                break;
            case R.id.btn_weibo:

                break;
        }
    }
}
