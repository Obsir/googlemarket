package com.obser.googlemarket.activity;

import android.support.v7.app.ActionBar;

import com.obser.googlemarket.R;

public class DetailActivity extends BaseActivity {


    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(item.getItemId()==android.R.id.home){
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
