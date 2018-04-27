package mlearn.sabachina.com.cn.tab;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    String[] mTitles = new String[]{"主页", "微博", "相册"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager);
        T<Man> t = new T<>();
    }
   class MyAdapter extends FragmentPagerAdapter{
       public MyAdapter(FragmentManager fm) {
           super(fm);
       }

       @Override
       public Fragment getItem(int position) {
           return new MyFragment();
       }

       @Override
       public int getCount() {
           return mTitles.length;
       }

       @Override
       public CharSequence getPageTitle(int position) {
           return mTitles[position];
       }
   }
}
