package com.strike.downba_app.activity;

import android.content.Intent;
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
import com.strike.downba_app.http.request.RegisterReq;
import com.strike.downba_app.http.response.LoginRsp;
import com.strike.downba_app.http.response.RegisterRsp;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import gson.Gson;

/**
 * Created by strike on 16/6/4.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.edt_username)
    private EditText edtUsername;

    @ViewInject(R.id.edt_password)
    private EditText edtPassword;

    @ViewInject(R.id.edt_confirm_password)
    private EditText confirmPassword;

    @ViewInject(R.id.ivpwd_showhide)
    private ImageView ivpwdShowhide;

    @ViewInject(R.id.ivpwd_showhide_confirm)
    private ImageView ivpwdShowhideConfirm;

    private boolean isShowPass = false;//是明文显示密码
    private boolean isShowPassConfirm = false;
    private int inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD;
    private int inputTypeConfirm = InputType.TYPE_NUMBER_VARIATION_PASSWORD;


    @Event(value = {R.id.btn_register, R.id.tv_login,R.id.ivpwd_showhide,R.id.ivpwd_showhide_confirm})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String userName = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                if (!VerifyUtils.checkUserName(userName)) {
                    UiUtils.showTipToast(false, getString(R.string.format_error_username));
                    return;
                }
                if (!VerifyUtils.checkPassword(password) || !VerifyUtils.checkPassword(confirmPass)) {
                    UiUtils.showTipToast(false, getString(R.string.format_error_password));
                    return;
                }
                if (!password.equals(confirmPass)) {
                    UiUtils.showTipToast(false, getString(R.string.confirm_error_pass));
                    return;
                }
                register(userName,password);

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
            case R.id.ivpwd_showhide_confirm:
                if (isShowPassConfirm){
                    isShowPassConfirm = false;
                    inputTypeConfirm = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                }else{
                    isShowPassConfirm = true;
                    inputTypeConfirm = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                }
                ivpwdShowhideConfirm.setImageResource(isShowPassConfirm ? R.mipmap.pwd_show : R.mipmap.pwd_hide);
                confirmPassword.setInputType(inputTypeConfirm);
                confirmPassword.setSelection(confirmPassword.getText().toString().length());//光标聚焦到行尾
                break;
            case R.id.tv_login:
                Intent intent = new Intent(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    private void register(final String userName, final String password) {
        RegisterReq registerReq = new RegisterReq(userName, password);
        showProgressDialogCloseDelay("正在注册，请稍后...", HttpConstance.DEFAULT_TIMEOUT);
        registerReq.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                dismissProgressDialog();
                if (!TextUtils.isEmpty(result)) {
                    Gson gson = new Gson();
                    RegisterRsp rsp = (RegisterRsp) BaseResponse.getRsp(result, RegisterRsp.class);
                    if (rsp != null) {
                        if (rsp.result == HttpConstance.HTTP_SUCCESS) {
                            User user = gson.fromJson(gson.toJson(rsp.resultData), User.class);
                            if (user != null) {
                                UserDao.saveUser(user);
                                UiUtils.showTipToast(true, getString(R.string.register_success));
                                login(userName,password);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFinished() {
            }
        });
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
                                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                RegisterActivity.this.finish();
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
}
