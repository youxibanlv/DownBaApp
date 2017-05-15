package com.strike.downba_app.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.strike.downba_app.adapter.GridRecommendAdapter;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.http.bean.AppAd;
import com.strike.downba_app.http.bean.Guess;
import com.strike.downba_app.http.bean.Info;
import com.strike.downba_app.http.req.GuessReq;
import com.strike.downba_app.http.req.InfoDetailsReq;
import com.strike.downba_app.http.rsp.GuessRsp;
import com.strike.downba_app.http.rsp.InfoDetailsRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.TimeUtil;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downba_app.view.NoScrollGridView;
import com.strike.downbaapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.net.URL;
import java.util.List;

/**
 * Created by jacky on 16/4/6.
 */
@ContentView(R.layout.activity_info)
public class InfoActivity extends BaseActivity {

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.tv_from)
    private TextView tv_from;

    @ViewInject(R.id.info_content)
    private TextView info_content;

    @ViewInject(R.id.gv_recommend)
    private NoScrollGridView gv_recommend;

    @ViewInject(R.id.body)
    private RelativeLayout body;

    private Info info;
    private String webTitle = "";
    private int id = -1;
    private GridRecommendAdapter suspectAdapter;

    private Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            try {
                if (!VerifyUtils.isUrl(source)) {
                    source = UrlConfig.BASE_URL + source;
                }
                URL url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "");
                int screenWidth = DensityUtil.getScreenWidth();
                int drawableWidth = drawable.getIntrinsicWidth();
                int drawableHeight = (drawable.getIntrinsicHeight() * screenWidth / drawableWidth);
                drawable.setBounds(0, 0, screenWidth, drawableHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return drawable;
        }
    };

    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        try {
            info = (Info) bundle.getSerializable(Constance.INFO);
            if (info != null) {
                webTitle = info.getInfo_title();
                id = info.getInfo_id();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        struct();
//        info_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        initView();
    }

    private void initView() {
        tv_title.setText(webTitle);
        suspectAdapter = new GridRecommendAdapter(this);
        gv_recommend.setAdapter(suspectAdapter);
        getInfoDetails();
    }
    private void getSuspect() {
        GuessReq req = new GuessReq(info.getCate_id());
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

    private void getInfoDetails() {
        if (id != -1) {
            InfoDetailsReq req = new InfoDetailsReq(id);
            showProgressDialogCloseDelay(getString(R.string.loading),HttpConstance.DEFAULT_TIMEOUT);
            req.sendRequest(new NormalCallBack() {
                @Override
                public void onSuccess(String result) {
                    InfoDetailsRsp rsp = (InfoDetailsRsp) BaseRsp.getRsp(result, InfoDetailsRsp.class);
                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS) {
                        Info tem = rsp.resultData.info;
                        if (tem != null) {
                            if (!TextUtils.isEmpty(tem.getInfo_body())) {
                                Document document = Jsoup.parse(tem.getInfo_body());
                                //处理图片
                                Elements imgs = document.select("img[src]");
                                for (Element img : imgs) {
                                    String imgUrl = img.attr("src");
                                    if (!VerifyUtils.isUrl(imgUrl)) {
                                        imgUrl = UrlConfig.BASE_URL + imgUrl;
                                        img.attr("src", imgUrl);
                                    }
                                }
                                //处理a标签
//                                Elements aa = document.select("a[href]");
//                                for (Element a : aa) {
//                                    String url = a.attr("href");
//                                }
                                Spanned text = Html.fromHtml(document.toString(), imageGetter, null);
                                info_content.setText(text);
                                String from = "来源:"+tem.getInfo_from() + "|" + " 时间："+
                                        TimeUtil.longToDateStr(tem.getUpdate_time(), "yyyy-MM-dd HH:mm:ss");
                                tv_from.setText(from);
                                getSuspect();
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

    public void onResume() {
        super.onResume();
        body.requestFocus();
    }

}
