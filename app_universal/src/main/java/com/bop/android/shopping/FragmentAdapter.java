package com.bop.android.shopping;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import google.architecture.common.base.BaseFragment;

/**
 * <p>Fragments适配器 </p>
 * @name ResourcePagerAdapter
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
