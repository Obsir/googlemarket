package com.obser.googlemarket.utils;

import android.content.Context;
import android.content.res.Resources;

import com.obser.googlemarket.global.BaseApplication;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class UIUtils {


    /**
     * 获取到字符数组
     * @param tabNames 字符数组的id
     * @return
     */
    public static String[] getStringArray(int tabNames){
       return getResource().getStringArray(tabNames);
    }

    public static Resources getResource(){
        return  BaseApplication.getApplication().getResources();
    }

    /**
     * dip 转换 px
     * @param dip
     * @return
     */
    public static int dip2px(int dip){
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px 转换 dip
     * @param px
     * @return
     */
    public static int px2dip(int px){
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取Context对象
     * @return
     */
    public static Context getContext(){
        return BaseApplication.getApplication();
    }

    /**
     * runOnUiThread(Runnable runnable) 方法提交到主线程中运行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable){
        //在主线程运行
        if(android.os.Process.myTid() == BaseApplication.getMainTid()){
            runnable.run();
        } else {
            BaseApplication.getHandler().post(runnable);
        }
    }

}
