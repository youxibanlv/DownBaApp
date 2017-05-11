package com.strike.downba_app.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.utils.Constance;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by strike on 16/6/5.
 */
@ContentView(R.layout.frament_app)
public class AppFragment extends BaseFragment {

    @ViewInject(R.id.rg_nav)
    private RadioGroup rg_nav;

    private CategoryFragment categoryFragment;
    private ListFragment hot;
    private ListFragment newF;
    private List<BaseFragment> fragmentList = new ArrayList<>();

    @Event(value = {R.id.app_hot, R.id.app_new, R.id.app_cate})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.app_hot:
                if (hot == null) {
                    hot = new ListFragment();
                    addFragment(R.id.fl_app, hot);
                    hot.refresh(Constance.ORDER_TYPE_SORT, Constance.PARENT_APP);
                }
                showFragment(hot);
                break;
            case R.id.app_new:
                if (newF == null) {
                    newF = new ListFragment();
                    addFragment(R.id.fl_app, newF);
                    newF.refresh(Constance.ORDER_TYPE_TIME, Constance.PARENT_APP);
                }
                showFragment(newF);
                break;
            case R.id.app_cate:
                if (categoryFragment == null) {
                    categoryFragment = new CategoryFragment();
                    addFragment(R.id.fl_app, categoryFragment);
                    categoryFragment.refresh(Constance.PARENT_APP);
                }
                showFragment(categoryFragment);
                break;
        }
    }

    @Override
    protected void addFragment(int viewId, BaseFragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        fragmentList.add(fragment);
        transaction.add(viewId, fragment);
        transaction.commit();
    }

    @Override
    protected void showFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (fragmentList.size() > 0) {
            for (BaseFragment f : fragmentList) {
                if (f != null) {
                    transaction.hide(f);
                }
            }
        }
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (rg_nav.getCheckedRadioButtonId() == -1){
                rg_nav.check(R.id.app_hot);
                if (hot == null) {
                    hot = new ListFragment();
                    addFragment(R.id.fl_app, hot);
                    hot.refresh(Constance.ORDER_TYPE_SORT, Constance.PARENT_APP);
                }
                showFragment(hot);
            }
        }
    }
}
