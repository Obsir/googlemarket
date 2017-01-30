package com.obser.googlemarket.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.obser.googlemarket.R;
import com.obser.googlemarket.fragment.BaseFragment;
import com.obser.googlemarket.fragment.FragmentFactory;
import com.obser.googlemarket.utils.UIUtils;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager mViewPager;
    private ActionBar actionBar;
    private PagerTabStrip mPagerTabStrip;
    private String[] tab_names;//标签的名字

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);

        mViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                BaseFragment fragment = FragmentFactory.createFragment(position);
                fragment.show();//当切换界面的时候，重新请求服务器
            }
        });
        //设置标签下划线的颜色
        mPagerTabStrip.setTabIndicatorColorResource(R.color.indicatorcolor);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //设置侦听Listener
        mDrawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void init() {
        super.init();
        tab_names = UIUtils.getStringArray(R.array.tab_names);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private class MainAdapter extends FragmentStatePagerAdapter{

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * 每个条目返回的fragment
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            //通过Fragment工厂 生产Fragment
            return FragmentFactory.createFragment(position);
        }

        /**
         * 一共有几个条目
         * @return
         */
        @Override
        public int getCount() {
            return tab_names.length;
        }

        //返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return tab_names[position];
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        //让开关和ActionBar建立关系
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 处理actionBar菜单条目的点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_search)
            Toast.makeText(this, "搜索", Toast.LENGTH_LONG).show();

        //处理ActionBarDrawerToggle的点击事件
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //当搜索提交的时候
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "提交", Toast.LENGTH_LONG).show();
        return true;
    }

    //当搜索的文本发生变化
    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, "文本", Toast.LENGTH_LONG).show();
        return true;
    }

}



//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


//        MyTabListener myTabListener = new MyTabListener();
//
//        ActionBar.Tab tab1 = actionBar.newTab().setText("标签一").setTabListener(myTabListener);
//        ActionBar.Tab tab2 = actionBar.newTab().setText("标签二").setTabListener(myTabListener);
//        ActionBar.Tab tab3 = actionBar.newTab().setText("标签三").setTabListener(myTabListener);
//
//        actionBar.addTab(tab1);
//        actionBar.addTab(tab2);
//        actionBar.addTab(tab3);


//参数1 当前actionBar 所在的activity
//参数2 控制的抽屉的参数
//参数3 按钮的图片(v7包取消此参数)
//参数4 抽屉打开的描述
//参数5 抽屉关闭的描述



//    private class MyTabListener implements ActionBar.TabListener{
//
//        @Override
//        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//            //同步设置ViewPager当前页面
//            mViewPager.setCurrentItem(tab.getPosition());
//        }
//
//        @Override
//        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//        }
//
//        @Override
//        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//        }
//    }