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
@ContentView(R.layout.fragment_article)
public class ArticleFragment extends BaseFragment {

    @ViewInject(R.id.rg_nav)
    private RadioGroup rg_nav;

    private View view;

    private List<BaseFragment> fragmentList = new ArrayList<>();

    private SubjectFragment subjectFragment;

    private InfoFragment originalFrament,reviewFragment,newsFragment;

    @Event(value = {R.id.app_subject,R.id.app_original,R.id.app_review,R.id.app_news})
    private void getEvent(View view){
        switch (view.getId()){
            case R.id.app_subject:
                if (subjectFragment == null){
                    subjectFragment = new SubjectFragment();
                    addFragment(subjectFragment);
                }
                showFragment(subjectFragment);
                break;
            case R.id.app_original:
                if (originalFrament == null){
                    originalFrament = new InfoFragment();
                    addFragment(originalFrament);
                    originalFrament.refresh(Constance.cate_original);
                }
                showFragment(originalFrament);
                break;
            case R.id.app_review:
                if (reviewFragment == null){
                    reviewFragment = new InfoFragment();
                    addFragment(reviewFragment);
                    reviewFragment.refresh(Constance.cate_review);
                }
                showFragment(reviewFragment);
                break;
            case R.id.app_news:
                if (newsFragment == null){
                    newsFragment = new InfoFragment();
                    addFragment(newsFragment);
                    newsFragment.refresh(Constance.cate_news);
                }
                showFragment(newsFragment);
                break;
        }
    }


    protected void addFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        fragmentList.add(fragment);
        transaction.add(R.id.fl_article, fragment);
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
            int checkId = rg_nav.getCheckedRadioButtonId();
            if (checkId == -1) {
                rg_nav.check(R.id.app_subject);
                if (subjectFragment == null){
                    subjectFragment = new SubjectFragment();
                    addFragment(subjectFragment);
                }
                showFragment(subjectFragment);
            }
        }
    }
}
