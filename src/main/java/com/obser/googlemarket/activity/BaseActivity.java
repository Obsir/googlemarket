package com.obser.googlemarket.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    //管理运行的所有Activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    //使用广播终止所有进程
    private KillAllReceiver receiver;
    private class KillAllReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        receiver = new KillAllReceiver();
        IntentFilter filter = new IntentFilter("com.obser.googlemarket.KILL_ALL");
        registerReceiver(receiver, filter);

        //增加同步代码块，以防线程安全的问题
        synchronized (mActivities){
            mActivities.add(this);
        }


        init();
        initView();
        initActionBar();

    }


    //终止所有进程
    public static void finishAll(){

        List<BaseActivity> copy;
        //复制了一份mActivities集合
        synchronized (mActivities){
            copy = new LinkedList<BaseActivity>(mActivities);
        }

        //不允许在遍历集合的过程中删除集合中的元素，所以要拷贝一份
        for(BaseActivity activity : copy){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        //杀死当前进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }

        synchronized (mActivities){
            mActivities.remove(this);
        }

    }

    protected void initView() {

    }

    protected void initActionBar() {

    }

    protected void init() {

    }
}
