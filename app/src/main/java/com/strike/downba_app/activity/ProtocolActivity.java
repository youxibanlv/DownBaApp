package com.strike.downba_app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.strike.downba_app.base.AppConfig;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@ContentView(R.layout.activity_protocol)
public class ProtocolActivity extends BaseActivity {

    @ViewInject(R.id.content1_tv)
    private TextView content1Tv;

    private MyHandler handler = new MyHandler();

    private String msgStr = "";

    private String line = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyThread().start();
        showProgressDialogCloseDelay("加载中，请稍后。。", HttpConstance.DEFAULT_TIMEOUT);
    }

    @Event(R.id.btnLeft_titlelayout)
    private void clickEvent(View view) {
        int id = view.getId();
        if (id == R.id.btnLeft_titlelayout) {
            this.finish();
        }

    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String str = (String) msg.obj;
                content1Tv.setText(Html.fromHtml(str));
            }
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader r = null;
            try {
                is = this.getClass().getClassLoader().getResourceAsStream("assets/" + "provision.txt");
                isr = new InputStreamReader(is, AppConfig.DEFAULT_CHARSET);
                r = new BufferedReader(isr);
                while ((line = r.readLine()) != null) {
                    msgStr += line;
                }
                if (msgStr != null && !"".equals(msgStr)) {
                    handler.obtainMessage(1, msgStr).sendToTarget();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (r != null) {
                    try {
                        r.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dismissProgressDialog();
            }
        }
    }
}
