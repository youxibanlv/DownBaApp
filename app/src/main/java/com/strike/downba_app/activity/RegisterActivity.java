package com.strike.downba_app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

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
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by strike on 16/6/4.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.edt_username)
    private EditText edtUsername;

    @ViewInject(R.id.edt_password)
    private EditText edtPassword;

    @Event(value = {R.id.btn_register, R.id.login,R.id.iv_back,R.id.provision})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
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
                register(userName,password);
                break;
            case R.id.login:
                Intent intent = new Intent(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.provision:
                startActivity(new Intent(this,ProtocolActivity.class));
                break;
        }
    }

    private void register(final String userName, final String password) {
        RegisterReq registerReq = new RegisterReq(userName, password);
        showProgressDialogCloseDelay("正在注册，请稍后...", HttpConstance.DEFAULT_TIMEOUT);
        registerReq.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                BaseResponse rsp = BaseResponse.getRsp(result,BaseResponse.class);
                if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS){
                    UiUtils.showTipToast(true, getString(R.string.register_success));
                    login(userName,password);
                }
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }

    private void login(String userName, String password) {
        LoginReq loginReq = new LoginReq(userName,password);
        showProgressDialogCloseDelay("登录中，请稍后...", HttpConstance.DEFAULT_TIMEOUT);
        loginReq.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                LoginRsp rsp = (LoginRsp) BaseResponse.getRsp(result,LoginRsp.class);
                if (rsp.result == HttpConstance.HTTP_SUCCESS){
                    User user = rsp.resultData.user;
                    UserDao.saveUser(user);
                    UiUtils.showTipToast(true, getString(R.string.login_success));
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    RegisterActivity.this.finish();
                }
            }
            @Override
            public void onFinished() {
                dismissProgressDialog();
            }
        });
    }
}
