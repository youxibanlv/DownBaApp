package com.strike.downba_app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.http.UrlConfig;
import com.strike.downbaapp.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by jacky on 16/4/6.
 */
@ContentView(R.layout.activity_common_webview)
public class CommonWebviewActivity extends BaseActivity {

    public static final String WEB_TITLE = "web_title";
    public static final String INFO_ID = "info_id";


    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;

    @ViewInject(R.id.webview)
    private WebView webview;

    private String webTitle = "";
    private int id = 0;//String url = UrlConfig.BASE_URL + "/index.php?tpl=content_info&id = "+info.getInfo_id();
    private String webUrl;


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        webTitle = intent.getStringExtra(WEB_TITLE);
        id = intent.getIntExtra(INFO_ID,-1);
        webUrl = UrlConfig.BASE_URL+"/app/index.php?tpl=app_content_info&id="+id;
        if (webview != null) {
            webview.setInitialScale(5);
            WebSettings webSettings = webview.getSettings();
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setDomStorageEnabled(true);
            //启动缓存
            webSettings.setAppCacheEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);


            //在Android系统4.3.1~3.0版本,系统webview默认添加了searchBoxJavaBridge_接口,如果未移除该接口可能导致低版本Android系统远程命令执行漏洞
            if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 17) {
                try {
                    webview.removeJavascriptInterface("searchBoxJavaBridge_");
                    webview.removeJavascriptInterface("accessibility");
                    webview.removeJavascriptInterface("accessibilityTraversal");
                } catch (Throwable tr) {
                    tr.printStackTrace();
                }
            }


            webview.loadUrl(webUrl);
            webview.setDownloadListener(new MyWebViewDownLoadListener());
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                    //LogIotUtils.e("shouldOverrideUrlLoading", "url="+url);

                    return false;
                }
            });
            webview.setWebChromeClient(new WebChromeClient());
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webview != null && webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        tv_title.setText(webTitle);
        LogUtil.e(webUrl);
    }

    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview != null && webview.canGoBack()) {
            webview.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

}
