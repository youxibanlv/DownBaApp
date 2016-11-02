package com.strike.downba_app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.view.ChoiceBean;
import com.strike.downbaapp.R;


public class ChoiceUpLoadAdapter extends MyBaseAdapter<ChoiceBean> {

	public ChoiceUpLoadAdapter( Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder;
		if (v == null) {
			holder = new ViewHolder();
			v = inflater.inflate(R.layout.ppw_search_way_item, null);
			holder.iv_choice_img = (ImageView) v
					.findViewById(R.id.iv_choice_img);
			holder.tv_choice_text = (TextView) v
					.findViewById(R.id.tv_choice_text);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		ChoiceBean bean = list.get(position);
		holder.iv_choice_img.setImageResource(bean.getImg());
		holder.tv_choice_text.setText(bean.getText());
		return v;
	}

	class ViewHolder {
		ImageView iv_choice_img;
		TextView tv_choice_text;
	}

}
