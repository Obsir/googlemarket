package com.obser.googlemarket.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class FileUtils {
    public static final String CACHE = "cache";
    public static final String ROOT = "GoogleMarket";
    public static File getDir(String str){
        StringBuilder path = new StringBuilder();
        if(isSDAvailable()){

            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            path.append(File.separator);
            path.append(ROOT);
            path.append(File.separator);
            path.append(str);

        } else {
            File cacheDir = UIUtils.getContext().getCacheDir(); // getCacheDir->cache getFileDir->file
            path.append(cacheDir.getAbsolutePath());// /data/data/com.obser.googlemarket/cache
            path.append(File.separator);
            path.append(str);
        }
        File file = new File(path.toString());
        if(!file.exists() || !file.isDirectory()){
            file.mkdirs();//创建文件夹
        }

        return file;
    }
    public static File getCacheDir(){
        return getDir(CACHE);
    }

    private static boolean isSDAvailable(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

}

