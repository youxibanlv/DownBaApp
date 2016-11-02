package com.strike.downba_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.strike.downba_app.images.ImgConfig;
import com.strike.downbaapp.R;

import org.xutils.x;

import java.util.List;

/***
 * 水平滑动模块的 配置器
 * 主要用于详情界面的图片列表
 * @author strike
 * **/
public class HorizontalScrollViewAdapter {

	@SuppressWarnings("unused")
	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> urls;

	public HorizontalScrollViewAdapter(Context context, List<String> urls)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.urls = urls;
	}
	public int getCount()
	{
		return urls.size();
	}

	public String getItem(int position)
	{
		return urls.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.item_myhorizontalscrollview, parent, false);
			viewHolder.album_image = (ImageView) convertView
					.findViewById(R.id.album_image);
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		x.image().bind(viewHolder.album_image,getItem(position), ImgConfig.getImgOption());
		return convertView;
	}
	class ViewHolder{
		ImageView album_image;
	}
}
