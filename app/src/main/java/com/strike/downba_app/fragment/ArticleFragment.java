package com.strike.downba_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.strike.downba_app.base.BaseFragment;
import com.strike.downba_app.utils.Constance;
import com.strike.downbaapp.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by strike on 16/6/5.
 */
@ContentView(R.layout.fragment_article)
public class ArticleFragment extends BaseFragment {

    @ViewInject(R.id.rg_nav)
    private RadioGroup rg_nav;

    private View view;

    private SubjectFragment subjectFragment;

    private InfoFragment originalFrament,reviewFragment,newsFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        rg_nav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.subject://专题
                        if (subjectFragment == null){
                            subjectFragment = new SubjectFragment();
                            addFragment(R.id.content,subjectFragment);
                        }
                        showFragment(subjectFragment);
                        break;
                    case R.id.original://原创
                        if (originalFrament == null){
                            originalFrament = new InfoFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constance.INFO_TYPE,Constance.cate_original);
                            originalFrament.setArguments(bundle);
                            addFragment(R.id.content,originalFrament);
                        }
                        showFragment(originalFrament);
                        break;
                    case R.id.review://评测
                        if (reviewFragment == null){
                            reviewFragment = new InfoFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constance.INFO_TYPE,Constance.cate_review);
                            reviewFragment.setArguments(bundle);
                            addFragment(R.id.content,reviewFragment);
                        }
                        showFragment(reviewFragment);
                        break;
                    case R.id.news://行业新闻
                        if (newsFragment == null){
                            newsFragment = new InfoFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt(Constance.INFO_TYPE,Constance.cate_news);
                            newsFragment.setArguments(bundle);
                            addFragment(R.id.content,newsFragment);
                        }
                        showFragment(newsFragment);
                        break;
                }
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        int checkId = rg_nav.getCheckedRadioButtonId();
        if (checkId == -1) {
            rg_nav.check(R.id.subject);
        }
    }
}
