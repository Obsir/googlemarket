package com.obser.googlemarket.protocol;

import com.google.gson.Gson;
import com.lidroid.xutils.util.IOUtils;
import com.obser.googlemarket.bean.HomeData;
import com.obser.googlemarket.http.HttpHelper;
import com.obser.googlemarket.utils.FileUtils;
import com.obser.googlemarket.utils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class HomeProtocol {
    //传入index 用于分页
    public HomeData load(int index){
        // 加载本地数据
        String json = loadLocal(index);
        LogUtils.d("LocalJson=" + json);
        if(json == null){
            //请求服务器
            json = loadServer(index);
//            System.out.println("ServerJson=" + json + "data from Server");
            if(json != null){
                saveLocal(json, index);
            }
        }
        if(json != null){
            return paserJson(json);
        } else {
            return null;
        }
    }

//    //传入index 用于分页
//    public void load(final int index){
//        // 加载本地数据
//        HomeData data = null;
//        String json = loadLocal(index);
//        if(json != null) {
//            data = paserJson(json);
//            LogUtils.d("Local = " + json);
//        } else {
//            HttpUtils httpUtils = new HttpUtils();
//            httpUtils.send(HttpRequest.HttpMethod.GET, "http://127.0.0.1:8090/home?index=" + index, new RequestCallBack<String>() {
//                @Override
//                public void onSuccess(ResponseInfo<String> responseInfo) {
//                    String result = responseInfo.result;
//                    if(result != null){
//                        saveLocal(result, index);
//                        LogUtils.d("result = " + result);
//                        paserJson(result);
//                    }
//                }
//                @Override
//                public void onFailure(HttpException e, String s) {
//
//                }
//            });
//        }
//    }


//    private List<AppInfo> paserJson(String json){
//        List<AppInfo> appInfos = new ArrayList<AppInfo>();
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray jsonArray = jsonObject.getJSONArray("list");
//            for(int i = 0; i < jsonArray.length(); i++){
//                JSONObject object = jsonArray.getJSONObject(i);
//                long id = object.getLong("id");
//                String name = object.getString("name");
//                String packageName = object.getString("packageName");
//                String iconUrl = object.getString("iconUrl");
//                double stars = object.getDouble("stars");
//                long size = object.getLong("size");
//                String downloadUrl = object.getString("downloadUrl");
//                String des = object.getString("des");
//                AppInfo appInfo = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, des);
//                appInfos.add(appInfo);
//            }
//
//            return appInfos;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    private HomeData paserJson(String json) {
        Gson gson = new Gson();
        HomeData data = gson.fromJson(json, HomeData.class);
        return data;
    }

    private void saveLocal(String json, int index) {
        // 1.把整个json文件写到一个本地文件中
        // 2.把每条数据都抽取出来保存到数据库中
        // 在第一行写一个过期时间

        File dir = FileUtils.getCacheDir();
        File file = new File(dir, "home_" + index);// /mnt/sdcard/GoogleMarket/cache/home_0
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(System.currentTimeMillis() + 1000 * 100 + "");
            bufferedWriter.newLine();//换行
            bufferedWriter.write(json);//把整个json文件保存起来
//            System.out.println(json);
//            System.out.println("ServerJson=" + json + "write data from Server");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
    }

    private String loadServer(int index) {
        return HttpHelper.get(HttpHelper.URL + "home?index=" + index).getString();

    }

//    private String loadServer(int index) {
//        final String[] json = {null};
//        HttpUtils httpUtils = new HttpUtils();
//        httpUtils.send(HttpRequest.HttpMethod.GET, "http://127.0.0.1:8090/home?index=" + index, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//
//                json[0] = responseInfo.result;
//                LogUtils.d("onSuccess in CallBack=" + json[0]);
////                System.out.println(responseInfo.result);
//            }
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//            }
//        });
//        LogUtils.d("loadServer=" + json[0]);
//        return json[0];
//    }

    //如果发现文件已经过期，就不要再复用缓存
    private String loadLocal(int index) {
        //获取缓存所在的文件夹
        File file = new File(FileUtils.getCacheDir(), "home_" + index);
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            if(System.currentTimeMillis() > Long.parseLong(bufferedReader.readLine())){
                System.out.println("Data from local is out of time !!!");
                return null;
            } else {
                String line = null;
                StringWriter stringWriter = new StringWriter();
                while((line = bufferedReader.readLine()) != null){
                    stringWriter.write(line);
                }
                return stringWriter.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
    }


}
