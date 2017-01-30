package com.obser.googlemarket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obser.googlemarket.view.LoadingPage;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public abstract class BaseFragment extends Fragment{

    private LoadingPage loadingPage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (loadingPage == null) { //之前的frameLayout已经有一个父类，是之前的ViewPager
            loadingPage = new LoadingPage(getActivity()) {
                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadResult load() {
                    return BaseFragment.this.load();
                }
            };
//            init();// 在FrameLayout中 添加4中不同的界面
        }
//        else {
//            //先移除之前的父类
//            LogUtils.e(frameLayout.getParent()==null ? "NULL" : "NOT NULL");
//            ViewUtils.removeParent(frameLayout);
//        }
        //show();// 根据服务器的数据，切换状态

        return loadingPage;//拿到当前的ViewPager 添加当前的frameLayout

    }

    /**
     * 创建成功的界面
     * @return
     */
    protected abstract View createSuccessView();
    /**
     * 请求服务器
     * @return
     */
    protected abstract LoadingPage.LoadResult load();

    public void show(){
        if(loadingPage != null){
            loadingPage.show();
        }
    }
}
