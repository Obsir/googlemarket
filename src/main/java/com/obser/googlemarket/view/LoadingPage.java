package com.obser.googlemarket.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.obser.googlemarket.R;
import com.obser.googlemarket.manager.ThreadManager;
import com.obser.googlemarket.utils.UIUtils;

/**
 * 创建了自定义的帧布局，把BaseFragment 一部分代码抽取到本类中
 *
 * Created by Administrator on 2016/11/29 0029.
 */
public abstract class LoadingPage extends FrameLayout{

    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public int state = STATE_UNKNOWN;//当前状态，不能置为静态


    private View loadingView; //加载中的界面
    private View errorView;// 错误界面
    private View emptyView;// 空界面
    private View successView; //加载成功的界面


    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 在FrameLayout中 添加4种不同的界面
    private void init() {

        if(loadingView == null){
            loadingView = createLoadingView();//创建了加载中的界面
            this.addView(loadingView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if(errorView == null){
            errorView = createErrorView();//创建了加载错误的界面
            this.addView(errorView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        if(emptyView == null){
            emptyView = createEmptyView();//创建了加载为空的界面
            this.addView(emptyView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        showPage();// 根据不同的状态，显示不同的界面

    }

    // 根据不同的状态，显示不同的界面
    private void showPage() {
        if(loadingView != null)
            loadingView.setVisibility(state == STATE_UNKNOWN || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        if (errorView != null)
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        if (emptyView != null)
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);

        if(state == STATE_SUCCESS) {
            successView = createSuccessView();
            if(successView != null) {
                this.addView(successView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                successView.setVisibility(View.VISIBLE);
            }
        }else {
            if(successView != null)
                successView.setVisibility(View.INVISIBLE);
        }

    }

    /* 返回加载为空的界面 */
    private View createEmptyView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_empty, null);

        return view;
    }

    /* 返回加载错误的界面 */
    private View createErrorView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_error, null);

        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });

        return view;
    }

    /* 返回加载中的界面 */
    private View createLoadingView() {

        View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_loading, null);

        return view;
    }

    public enum LoadResult{
        error(2), empty(3), success(4);
        private int value;
        LoadResult(int value){
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    // 根据服务器的数据，切换状态
    public void show() {

        if(state == STATE_EMPTY || state == STATE_ERROR){
            state = STATE_LOADING;
        }

        //请求服务器 获取服务器上的数据，进行判断
        //请求服务器，返回一个结果

        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                final LoadResult result = load();
                UIUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result != null){
                            state = result.getValue();
                            //获得到新的状态后，重新判断当前应该显示哪个界面
                            showPage();
                        }
                    }
                });
            }
        });

        showPage();

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
    protected abstract LoadResult load();

}
