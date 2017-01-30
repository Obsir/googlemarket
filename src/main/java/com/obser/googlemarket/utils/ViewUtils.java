package com.obser.googlemarket.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class ViewUtils {
    public static void removeParent(View v){
        //先找到父类，再通过父类移除子类
        ViewParent parent = v.getParent();
        //所有的控件都有 ViewParent，一般情况下就是ViewGroup，比如根部局就不一定是group
        if(parent instanceof ViewGroup){
            ViewGroup group = (ViewGroup) parent;
            group.removeView(v);
        }
    }
}
