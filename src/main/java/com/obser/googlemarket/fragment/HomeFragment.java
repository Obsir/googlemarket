package com.obser.googlemarket.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.obser.googlemarket.bean.HomeData;
import com.obser.googlemarket.protocol.HomeProtocol;
import com.obser.googlemarket.utils.LogUtils;
import com.obser.googlemarket.view.LoadingPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    //当Fragment挂载的Activity创建成功时调用
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    protected LoadingPage.LoadResult load() {
        HomeProtocol homeProtocol = new HomeProtocol();
        HomeData data = homeProtocol.load(0);

        return checkData(data);

    }

    /* 校验数据 */
    private LoadingPage.LoadResult checkData(HomeData data){
        if(data == null){
            return LoadingPage.LoadResult.error;//请求服务器失败
        } else {
            if(data.list.size() == 0){
                return LoadingPage.LoadResult.empty;
            } else {
                LogUtils.d(data.toString());
                return LoadingPage.LoadResult.success;
            }
        }
    }

    /* 返回加载成功的界面 */
    protected View createSuccessView(){

        TextView tv = new TextView(getActivity());

        tv.setText("加载成功");
        tv.setTextSize(30);

        return tv;
    }

}
