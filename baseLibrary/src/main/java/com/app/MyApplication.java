package com.app;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;


public class MyApplication extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();
    private static volatile MyApplication instance;


    //私有構造方法
    private MyApplication() {

    }

    //單例模式獲取唯一的MyApplication
    public static MyApplication getInstance() {
        if (null == instance) {
            synchronized (MyApplication.class) {
                if (null == instance) {
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();



    }

    //添加activity到容器中
    public void addActivity(Activity aty) {
        activityList.add(aty);
    }


    //退出時關閉所有的Activity
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }


    public void deleteActivity(Activity aty) {
        activityList.remove(aty);
    }
}
