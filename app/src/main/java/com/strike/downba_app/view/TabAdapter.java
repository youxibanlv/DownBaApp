package com.strike.downba_app.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.strike.downba_app.base.BaseFragment;

import java.util.List;


/**
 * The Class TabAdapter.
 */
public class TabAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter {
    /**
     * The Constant TAG.
     */
    public static final String TAG = TabAdapter.class.getName().toString();


    private List<BaseFragment> mFragments;

    public TabAdapter(List<BaseFragment> fragments, FragmentManager fm) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getIconResId(int index) {
        return mFragments.get(index).getIconId();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
