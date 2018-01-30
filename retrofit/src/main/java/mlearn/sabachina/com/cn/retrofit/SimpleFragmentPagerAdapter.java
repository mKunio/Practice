package mlearn.sabachina.com.cn.retrofit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhc on 2017/8/21 0021.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private List<Fragment> fragments;

    public SimpleFragmentPagerAdapter(FragmentManager fm, String[] title, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
//        return Math.min(title.length, fragments.size());
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
