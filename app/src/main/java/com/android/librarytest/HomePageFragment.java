package com.android.librarytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/22.
 * 这是首页，包含3个分类的fragment
 */
public class HomePageFragment extends Fragment{
    final String[] TITLE = new String[]{"推荐","热门","分类"};
    TabPageIndicator indicator;
    ViewPager pager;
    List<Fragment> fragments;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.homepagelayout,null);
        indicator = (TabPageIndicator) v.findViewById(R.id.homepagetitle);
        pager = (ViewPager) v.findViewById(R.id.homepageviewpager);
        fragments = new ArrayList<Fragment>();
        /**
         * To add right fragments here!!
         */
        Log.d("APP_TAG3","ON CREATE");
        fragments.add(new RecommendeFragment());
        fragments.add(new CollectFragment());
        fragments.add(new SettingsFragment());
        HomepageAdapter adapter = new HomepageAdapter(getChildFragmentManager(),fragments);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("APP_TAG4","SAVE");
        super.onSaveInstanceState(outState);
    }

    class HomepageAdapter extends FragmentPagerAdapter{
        List<Fragment> fragmentList;
        public HomepageAdapter(FragmentManager fm,List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position%3];
        }
    }
}
