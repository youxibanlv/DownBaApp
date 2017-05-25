package com.strike.downba_app.images;


import android.widget.ImageView;

import com.strike.downbaapp.R;

import org.xutils.image.ImageOptions;

/**
 * Created by strike on 16/6/16.
 */
public class ImgConfig {
    public static ImageOptions getImgOption(){
        ImageOptions options = new ImageOptions.Builder()
                .setFailureDrawableId(R.mipmap.load_faild)
                .setLoadingDrawableId(R.mipmap.loading)
                .setIgnoreGif(false)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setUseMemCache(true)
                .setAutoRotate(true)
//                .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .build();
        return options;
    }
    public static ImageOptions getImgOptionNoCache(){
        ImageOptions options = new ImageOptions.Builder()
                .setFailureDrawableId(R.mipmap.load_faild)
                .setLoadingDrawableId(R.mipmap.loading)
                .setIgnoreGif(false)
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setUseMemCache(true)
                .setAutoRotate(true)
                .build();
        return options;
    }
    public static ImageOptions getMatrixImgOption(){
        ImageOptions options = new ImageOptions.Builder()
                .setFailureDrawableId(R.mipmap.load_faild)
                .setLoadingDrawableId(R.mipmap.loading)
                .setIgnoreGif(false)
                .setImageScaleType(ImageView.ScaleType.MATRIX)
                .setUseMemCache(true)
                .setAutoRotate(true)
                .build();
        return options;
    }
}
