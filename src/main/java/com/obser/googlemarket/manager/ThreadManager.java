package com.obser.googlemarket.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 管理线程池
 * Created by Administrator on 2016/11/30 0030.
 */
public class ThreadManager {

    private ThreadPoolProxy longPool;
    private ThreadPoolProxy shortPool;

    private ThreadManager(){

    }

    private static ThreadManager instance = new ThreadManager();
    public static ThreadManager getInstance(){
        return instance;
    }

    // 2 * cpu核数 + 1
    //联网比较耗时
    //读取本地文件
    //最好使用两个线程池管理
    //保证同步

    //连接网络
    public synchronized ThreadPoolProxy createLongPool(){
        if(longPool == null)
            longPool = new ThreadPoolProxy(5, 5, 5000);
        return longPool;
    }
    //保证同步
    //操作本地文件
    public synchronized ThreadPoolProxy createShortPool(){
        if(shortPool == null)
            shortPool = new ThreadPoolProxy(3, 3, 5000);
        return shortPool;
    }


    public class ThreadPoolProxy{

        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        //线程池
        ThreadPoolExecutor pool;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime){
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        public void execute(Runnable runnable){
            if(pool == null){
                //创建线程池
                /**
                 * 1.线程池里面管理多少个线程
                 * 2.如果排队满了，额外开的线程，一般情况下与corePoolSize保持一致即可
                 * 3.如果线程池没有要执行的任务 存活多久
                 * 4.时间的单位
                 * 5.如果线程池里管理的线程都已经用了，把剩下的任务临时存储到LinkedBlockingDeque中
                 */
                pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(10) {
                });
            }
            pool.execute(runnable); //调用线程池执行异步任务
        }

        /**
         * 取消异步任务
         * @param runnable
         */
        public void cancel(Runnable runnable){
            //线程池不为空，且没关闭，没有停止运行
            if (pool != null && !pool.isShutdown() && !pool.isTerminated()){
                pool.remove(runnable);
            }
        }

    }

}
