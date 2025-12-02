package com.app.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.widgts.tablayout.XTabLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private float pageWidth = 1.0f;
    private List<Fragment> mFragmentList = new ArrayList();
    private List<CharSequence> mTitleList = new ArrayList();

    public TabFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addTab(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTitleList.get(position);
    }

    public void cleanTabList() {
        mFragmentList.clear();
        mTitleList.clear();
    }

    /**
     * @deprecated
     */
    public void setPageTitle(int position, CharSequence title) {
        this.mTitleList.set(position, title);
    }

    public CharSequence getTitleList(int position) {
        return mTitleList.get(position);
    }

    public void setPageTitle(TabLayout tabLayout, int position, CharSequence title) {
        this.setPageTitle(position, title);
        if (position < tabLayout.getTabCount()) {
            tabLayout.getTabAt(position).setText(title);
        }

    }

    public void setPageTitle(XTabLayout tabLayout, int position, CharSequence title) {
        this.setPageTitle(position, title);
        if (position < tabLayout.getTabCount()) {
            tabLayout.getTabAt(position).setText(title);
        }

    }

    public void setPageWidth(float pageWidth) {
        this.pageWidth = pageWidth;
    }

    @Override
    public float getPageWidth(int position) {
        return pageWidth;
    }
}
