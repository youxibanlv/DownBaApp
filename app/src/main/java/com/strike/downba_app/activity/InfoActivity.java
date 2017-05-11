package com.strike.downba_app.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.http.bean.Info;
import com.strike.downba_app.http.request.InfoDesReq;
import com.strike.downba_app.http.response.InfoDesRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downbaapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.net.URL;

import static com.strike.downba_app.activity.CommonWebviewActivity.INFO_ID;
import static com.strike.downba_app.activity.CommonWebviewActivity.WEB_TITLE;

/**
 * Created by jacky on 16/4/6.
 */
@ContentView(R.layout.activity_info)
public class InfoActivity extends BaseActivity {

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.info_content)
    private TextView info_content;

    private Info info;
    private String webTitle = "";
    private int id = 0;

    private Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            try{
                if (!VerifyUtils.isUrl(source)){
                    source = UrlConfig.WEB_URL + source;
                }
                URL url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(),"");
//                int screenWidth = DensityUtil.getScreenWidth();
//                int drawableWidth = drawable.getIntrinsicWidth();
//                int drawableHeight = (drawable.getIntrinsicHeight()* screenWidth/drawableWidth);
                drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            }catch (Exception e){
                e.printStackTrace();
            }
            return drawable;
        }
    };


    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        webTitle = intent.getStringExtra(WEB_TITLE);
        id = intent.getIntExtra(INFO_ID,-1);
        info = (Info) intent.getExtras().getSerializable(Constance.INFO);
        struct();
        info_content.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void onResume() {
        super.onResume();
        tv_title.setText(webTitle);
        if (id!= -1){
            InfoDesReq req = new InfoDesReq(id);
            req.sendRequest(new NormalCallBack() {
                @Override
                public void onSuccess(String result) {
                    InfoDesRsp rsp = (InfoDesRsp) BaseResponse.getRsp(result,InfoDesRsp.class);
                    if (rsp!=null){
                        String body = rsp.resultData.infoBody;
                        if (!TextUtils.isEmpty(body)){
                            Document document = Jsoup.parse(body);
                            Elements imgs = document.select("img[src]");
                            for(Element img :imgs){
                                String imgUrl = img.attr("src");
                                if (!VerifyUtils.isUrl(imgUrl)) {
                                    imgUrl = UrlConfig.WEB_URL + imgUrl;
                                    img.attr("src",imgUrl);
                                }
                            }
//                          info_content.setText(document.toString());
                            Spanned text = Html.fromHtml(document.toString(),imageGetter,null);
                            info_content.setText(text);
                        }
                    }
                }

                @Override
                public void onFinished() {

                }
            });
        }

//        String body =
//        Spanned text = Html.fromHtml(body,imageGetter,null);
//        info_content.setText(text);
//        if (info!= null){
//            tv_title.setText(info.getInfo_title());
//            Spanned text = Html.fromHtml(info.getInfo_body(),imageGetter,null);
//            info_content.setText(text);
//        }
    }
    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }

}
