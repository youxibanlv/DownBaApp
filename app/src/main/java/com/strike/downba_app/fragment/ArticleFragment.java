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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = super.onCreateView(inflater, container, savedInstanceState);
//        rg_nav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.subject://专题
//                        if (subjectFragment == null){
//                            subjectFragment = new SubjectFragment();
//                            addFragment(R.id.content,subjectFragment);
//                        }
//                        showFragment(subjectFragment);
//                        break;
//                    case R.id.original://原创
//                        if (originalFrament == null){
//                            originalFrament = new InfoFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt(Constance.INFO_TYPE,Constance.cate_original);
//                            originalFrament.setArguments(bundle);
//                            addFragment(R.id.content,originalFrament);
//                        }
//                        showFragment(originalFrament);
//                        break;
//                    case R.id.review://评测
//                        if (reviewFragment == null){
//                            reviewFragment = new InfoFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt(Constance.INFO_TYPE,Constance.cate_review);
//                            reviewFragment.setArguments(bundle);
//                            addFragment(R.id.content,reviewFragment);
//                        }
//                        showFragment(reviewFragment);
//                        break;
//                    case R.id.news://行业新闻
//                        if (newsFragment == null){
//                            newsFragment = new InfoFragment();
//                            Bundle bundle = new Bundle();
//                            bundle.putInt(Constance.INFO_TYPE,Constance.cate_news);
//                            newsFragment.setArguments(bundle);
//                            addFragment(R.id.content,newsFragment);
//                        }
//                        showFragment(newsFragment);
//                        break;
//                }
//            }
//        });
//        return view;
//    }


    @Override
    public void onStart() {
        super.onStart();
        int checkId = rg_nav.getCheckedRadioButtonId();
        if (checkId == -1) {
            rg_nav.check(R.id.app_subject);
        }
    }
}
