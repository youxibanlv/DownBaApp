package com.strike.downba_app.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.strike.downba_app.images.ImgConfig;
import com.strike.downba_app.photoview.PhotoViewAttacher;
import com.strike.downbaapp.R;

import org.xutils.common.Callback;
import org.xutils.x;


/**
 * 查看 图片详情界面 显示图片 以及 加载 的progressBar
 * 支持手势放大，缩小
 * @author strike
 * **/
public class ImageDetailFragment extends Fragment {

	private String mImageUrl;
	private ImageView mImageView;
	private PhotoViewAttacher mAttacher;

	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();
		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);
		return f;

	}
	public ImageDetailFragment() {
	}
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url"): null;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment,
				container, false);
		mImageView = (ImageView) v.findViewById(R.id.imag_details);
		mAttacher = new PhotoViewAttacher(mImageView);
		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float x, float y) {
				if (getActivity()!= null) {
					getActivity().finish();
				}
			}
		});
		return v;
	}

	// 加载图片
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		x.image().bind(mImageView, mImageUrl, ImgConfig.getMatrixImgOption(),new Callback.CommonCallback<Drawable>() {
			@Override
			public void onSuccess(Drawable result) {
				mAttacher.update();
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				ex.printStackTrace();
			}

			@Override
			public void onCancelled(CancelledException cex) {

			}

			@Override
			public void onFinished() {

			}
		});
//		final ImageLoader instance = ImageLoader.getInstance();
//		instance.displayImage(mImageUrl, mImageView,
//				null, new ImageLoadingListener() {
//					@Override
//					public void onLoadingStarted(String imageUri, View view) {
//						progressBar.setVisibility(View.VISIBLE);
//						instance.getMemoryCache();
//					}
//
//					@Override
//					public void onLoadingFailed(String imageUri, View view,FailReason failReason) {
//						String message = null;
//						switch (failReason.getType()) {
//						case IO_ERROR:
//							message = "下载错误";
//							break;
//						case DECODING_ERROR:
//							message = "图片无法显示";
//							break;
//						case NETWORK_DENIED:
//							message = "无法连接网络";
//							break;
//						case OUT_OF_MEMORY:
//							message = "图片太大，无法显示";
//							instance.clearMemoryCache();
//							instance.displayImage(mImageUrl, mImageView);
//							break;
//						case UNKNOWN:
//							message = "未知错误";
//							break;
//						}
//						if (getActivity() != null) {
//							HintToast.setText(getActivity(), message);
//						}
//						progressBar.setVisibility(View.GONE);
//					}
//
//					@Override
//					public void onLoadingComplete(String imageUri, View view,
//							Bitmap loadedImage) {
//						progressBar.setVisibility(View.GONE);
//						mAttacher.update();
//					}
//
//					@Override
//					public void onLoadingCancelled(String imageUri, View view) {
//
//					}
//				},new ImageLoadingProgressListener() {
//
//					@Override
//					public void onProgressUpdate(String imageUri, View view, int current,
//							int total) {
//						progressBar.setProgress(current);
//					}
//				});
	}
}
