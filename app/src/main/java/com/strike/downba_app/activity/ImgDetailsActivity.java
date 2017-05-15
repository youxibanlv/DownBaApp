package com.strike.downba_app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.strike.downba_app.fragment.ImageDetailFragment;
import com.strike.downba_app.http.UrlConfig;
import com.strike.downba_app.utils.VerifyUtils;
import com.strike.downba_app.view.ImageViewPager;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by strike on 16/7/4.
 *
 * 图片查看器的主界面，可以
 * 用于放大显示图片
 * @author strike
 *
 */
@ContentView(R.layout.activity_img_detals)
public class ImgDetailsActivity extends FragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";//下标
    public static final String EXTRA_IMAGE_URLS = "image_urls";//图片地址

    @ViewInject(R.id.pager)
    private ImageViewPager mPager;
    private int pageerPosition;//位置
    @ViewInject(R.id.indicator)
    private TextView indicator;//显示下标

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        // 图片的下标
        pageerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urls = getIntent().getStringArrayListExtra(
                EXTRA_IMAGE_URLS);
        // 单个图片的路径
        String url = getIntent().getStringExtra(EXTRA_IMAGE_URLS);
        if (!TextUtils.isEmpty(url)&& !VerifyUtils.isUrl(url)){
            url = UrlConfig.BASE_URL+url;
        }
        if (urls == null && url != null) {
            urls = new ArrayList<String>();
            urls.add(url);
            indicator.setVisibility(View.GONE);
        }
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
                .getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        if (savedInstanceState != null) {
            pageerPosition = savedInstanceState.getInt(STATE_POSITION);
        }
        mPager.setCurrentItem(pageerPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            if (!TextUtils.isEmpty(url)&& !VerifyUtils.isUrl(url)){
                url = UrlConfig.BASE_URL+url;
            }
            return ImageDetailFragment.newInstance(url);
        }

    }
}
