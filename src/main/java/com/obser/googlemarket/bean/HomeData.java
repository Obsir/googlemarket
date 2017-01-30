package com.obser.googlemarket.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class HomeData {

    public ArrayList<AppInfo> list;

    public class AppInfo {
        public long id;
        public String name;
        public String packageName;
        public String iconUrl;
        public double stars;
        public long size;
        public String downloadUrl;
        public String des;

        @Override
        public String toString() {
            return "AppInfo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", stars=" + stars +
                    ", size=" + size +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", des='" + des + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "list=" + list +
                '}';
    }
}
