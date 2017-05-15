package com.strike.downba_app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.strike.downba_app.MainActivity;
import com.strike.downba_app.base.BaseActivity;
import com.strike.downba_app.db.dao.UserDao;
import com.strike.downba_app.db.table.User;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.request.UpdateUserReq;
import com.strike.downba_app.http.rsp.UserInfoRsp;
import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.utils.AppUtils;
import com.strike.downba_app.utils.Constance;
import com.strike.downba_app.utils.UiUtils;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downba_app.view.ChoiceBean;
import com.strike.downba_app.view.CustomPopupWindow;
import com.strike.downbaapp.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by strike on 16/6/7.
 */
@ContentView(R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity {

    public final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;// 拍照
    public final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;// 相册(只能选照片)
    public final int REQUEST_CROP = 300;// 裁剪
    public final int PICK_VIDOFILE_ACTIVITY_REQUEST_CODE = 400;// 相册(只能选视频)

    private String photoPath;
    private CustomPopupWindow popupWindow;

    @ViewInject(R.id.iv_user_icon)
    private ImageView iv_user_icon;

    @ViewInject(R.id.tv_account)
    private TextView tv_account;

    @ViewInject(R.id.edt_nick)
    private EditText edt_nick;

    @ViewInject(R.id.tv_phone)
    private EditText tv_phone;

    @ViewInject(R.id.tv_point)
    private TextView tv_point;

    @ViewInject(R.id.edt_alipay)
    private EditText edt_alipay;

    private User user;
    private Uri takeCameraUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popupWindow = new CustomPopupWindow(this);
        popupWindow.lv_choice_upload.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    creatUpdialog();
                } else if (position == 1) {
                    takeCamera();
                } else {
                    return;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        user = UserDao.getUser();
        if (user != null) {
            if (user.getUser_icon() != null) {
                x.image().bind(iv_user_icon, user.getUser_icon(), ImgConfig.getImgOptionNoCache());
            }
            if (user.getUser_name() != null) {
                tv_account.setText(user.getUser_name());
            }
            if (user.getNickname() != null) {
                edt_nick.setText(user.getNickname());
                edt_nick.setSelection(user.getNickname().length());
            }
            tv_point.setText(user.getUser_point() + "");
            if (user.getPhone_num() != null) {
                tv_phone.setText(user.getPhone_num());
            }
//            if (user.getAlipay() != null) {
//                edt_alipay.setText(user.getAlipay());
//                edt_alipay.setSelection(user.getAlipay().length());
//            }
        }
    }

    private void update(User info) {
        UpdateUserReq req = new UpdateUserReq(info);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    UserInfoRsp rsp = (UserInfoRsp) BaseRsp.getRsp(result, UserInfoRsp.class);
                    if (rsp != null && rsp.result == HttpConstance.HTTP_SUCCESS) {
                        user = rsp.resultData.user;
                        if (user != null) {
                            UserDao.saveUser(user);
                            UiUtils.showTipToast(true, "修改成功！");
                            init();
                        }
                    }
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private boolean checkInput(String phone, String alipay, String nickName) {

        if (!"".equals(phone) && !VerifyUtils.isPhoneNum(phone)) {
            UiUtils.showTipToast(false, "电话格式不正确");
            return false;
        }
        if (!"".equals(alipay) && !VerifyUtils.isAlipay(alipay)) {
            UiUtils.showTipToast(false, "支付宝格式不正确！");
            return false;
        }
        return true;
    }

    @Event(value = {R.id.btn_logout, R.id.tv_save, R.id.iv_back, R.id.ll_user_icon})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                UserDao.logOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.tv_save:
                // TODO: 16/7/7 save changes
                String alipay = edt_alipay.getText().toString();
                String nickName = edt_nick.getText().toString();
                String phone = tv_phone.getText().toString();
                if (checkInput(phone, alipay, nickName)) {
                    //更新用户信息
                    User info = user;
//                    info.setAlipay(alipay);
                    info.setNickname(nickName);
                    info.setPhone_num(phone);
                    update(info);
                }

                break;
            case R.id.ll_user_icon:
                // TODO: 16/7/7 click to change userIcon
                ChoiceBean bean = new ChoiceBean(R.mipmap.gallery, "相册");
                ChoiceBean bean2 = new ChoiceBean(R.mipmap.camera, "拍照");
                List<ChoiceBean> list = new ArrayList<>();
                list.add(bean);
                list.add(bean2);
                popupWindow.refresh(list);
                if (!popupWindow.isShowing()) {
                    popupWindow.showAtLocation(findViewById(R.id.rl_show),
                            Gravity.BOTTOM, 0, 0);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /****
     * 打开相册
     *****/
    public void creatUpdialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /*
     * 打开相机
     */
    private void takeCamera() {
        if (!AppUtils.isExitsSdcard()) {
            UiUtils.showTipToast(false, "SD卡不存在");
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = format.format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File image = new File(AppUtils.getAlbumDir(), imageFileName);
//            mCurrentPhotoPath = image.getAbsolutePath();
        /**
         * 使用隐式intent进行跳转
         */
        try {
            if (image.exists()) {
                image.delete();
            }
            image.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        takeCameraUri = Uri.fromFile(image);
        photoPath = AppUtils.getRealPathFromURI(this, takeCameraUri);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, takeCameraUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("界面被回收");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent cameraIntent = new Intent(this, UpLoadActivity.class);
            switch (requestCode) {
                case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:// 拍照
                    if (photoPath != null) {
                        cameraIntent.putExtra(Constance.IMG, photoPath);
                        this.startActivity(cameraIntent);
                    }
                    break;
                case PICK_IMAGE_ACTIVITY_REQUEST_CODE:
                    Uri uri = data.getData();
                    if (uri != null) {
                        String realpath = AppUtils.getRealPathFromURI(
                                UserInfoActivity.this, uri);
                        if (realpath != null) {
                            cameraIntent.putExtra(Constance.IMG, realpath);
                            this.startActivity(cameraIntent);
                        }
                    }
                    break;
            }
        }
    }
}
