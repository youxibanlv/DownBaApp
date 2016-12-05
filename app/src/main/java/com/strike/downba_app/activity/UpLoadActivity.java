package com.strike.downba_app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.dao.UserDao;
import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseResponse;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.request.UploadUserIconReq;
import com.strike.downba_app.http.response.UserRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by strike on 16/7/11.
 */
@ContentView(R.layout.activity_upload)
public class UpLoadActivity extends BaseActivity {

    @ViewInject(R.id.up_img)
    private ImageView up_img;

    private String path1;

    @Override
    protected void onResume() {
        super.onResume();
        path1 = getIntent().getStringExtra(Constance.IMG);
        if (!TextUtils.isEmpty(path1)){
            up_img.setImageBitmap(reSizePic(path1,480,640));
        }
    }
    @Event(value = {R.id.btn_update,R.id.iv_back})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_update:
                if (path1 == null){
                    return;
                }
                upDataUserIcon(path1);
                break;
        }
    }
    //上传头像
    private void upDataUserIcon(String path){
        UploadUserIconReq req = new UploadUserIconReq(path,UserDao.getUser());
        req.upLoadFile(path, new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                UserRsp rsp = (UserRsp) BaseResponse.getRsp(result, UserRsp.class);
                if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS) {
                    User user = rsp.resultData;
                    if (user != null) {
                        UserDao.saveUser(user);
                        UiUtils.showTipToast(true, "修改成功！");
                        finish();
                    }
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }
    //图片压缩
    public Bitmap reSizePic(String path, int width, int heith) {

        BitmapFactory.Options option = new BitmapFactory.Options();

        // 设置inJustDecodeBounds 为true之后，BitmapFactory.decodeFile 不返回bitmap,只是在option中返回图片的大小

        option.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, option);

        int oWidth = option.outWidth;

        int oHeihth = option.outHeight;
        // 计算缩放量，这里只是简单的计算。

        int scaleFactor = Math.min(oWidth / width, oHeihth / heith);

        // 重新设置为false，返回图片

        option.inJustDecodeBounds = false;

        // 设置获得的图片的所放量，当为2时，返回的图片大小为原来的1/2

        option.inSampleSize = scaleFactor;

        Bitmap res = BitmapFactory.decodeFile(path, option);

        return res;

    }

}
