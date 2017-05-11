package com.strike.downba_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strike.downba_app.activity.CateActivity;
import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.base.MyBaseAdapter;
import com.strike.downba_app.http.BaseRsp;
import com.strike.downba_app.http.HttpConstance;
import com.strike.downba_app.http.NormalCallBack;
import com.strike.downba_app.http.bean.Cate;
import com.strike.downba_app.http.req.GetCategoryReq;
import com.strike.downba_app.http.rsp.GetCategoryRsp;
import com.strike.downba_app.utils.Constance;
import com.strike.downbaapp.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import static com.strike.downba_app.utils.Constance.PARENT_APP;
import static com.strike.downba_app.utils.Constance.PARENT_GAME;

/**
 * Created by strike on 2016/11/11.
 */
@ContentView(R.layout.fragment_category)
public class CategoryFragment extends BaseFragment {

    @ViewInject(R.id.gridView)
    private GridView gridView;

    private View view;
    private GridAdapter adapter;
    private Integer cateId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater,container,savedInstanceState);
        adapter = new GridAdapter(getContext());
        gridView.setAdapter(adapter);
        getCategory(cateId);
        return view;
    }

    public void refresh(int cateId){
        this.cateId = cateId;
    }


    private void getCategory(int cateId){
        final GetCategoryReq req;
        if (cateId == PARENT_GAME){
            req = new GetCategoryReq(PARENT_GAME);
        }else {
            req = new GetCategoryReq(PARENT_APP);
        }
        showProgressDialogCloseDelay(getString(R.string.loading), HttpConstance.DEFAULT_TIMEOUT);
        req.sendRequest(new NormalCallBack() {
            @Override
            public void onSuccess(String result) {
                GetCategoryRsp rsp = (GetCategoryRsp) BaseRsp.getRsp(result,GetCategoryRsp.class);
                List<Cate> list = rsp.resultData.list;
                if (list!= null && list.size()>0){
                    adapter.refresh(list);
                }
            }

            @Override
            public void onFinished() {
                dismissProgressDialog();
                long temp = System.currentTimeMillis() - this.getRequestTime();
                LogUtil.e(req.methodName+":请求时间 = "+ temp);
            }
        });
    }

    class GridAdapter extends MyBaseAdapter<Cate>{

        public GridAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.item_category,parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            final Cate cate = getItem(position);
            holder.name.setText(cate.getCname());
            x.image().bind(holder.icon, cate.getCimg());
            holder.rl_catel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CateActivity.class);
                    intent.putExtra(Constance.CATE, cate);
                    context.startActivity(intent);
                }
            });
            return convertView;
        }
    }

    class ViewHolder{
        @ViewInject(R.id.icon)
        ImageView icon;
        @ViewInject(R.id.name)
        TextView name;
        @ViewInject(R.id.rl_cate)
        LinearLayout rl_catel;

        public ViewHolder(View view){
            x.view().inject(this,view);
        }
    }
}
