package kap.com.smarthome.android.mapp;

import android.app.Application;

import kap.com.smarthome.android.data.control.DBManageUtils;
import kap.com.smarthome.android.presenter.control.SharedPreferencesHandle;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class MyApplication extends Application {

    private static MyApplication mApplication;
    private DBManageUtils mDbManage;


    public MyApplication(){
        mApplication = this;
    }


    public static MyApplication getApplication(){
        return  mApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        initDb();
        //初始化SharedPreferences
        initSharedPreferences();
    }



    /**
     * 初始化数据库DB,实例化一个数据库的会话Sesssion, (采用的是GreenDao框架)
     */
    private void initDb(){
        mDbManage =  DBManageUtils.getDbInstance(this);
    }


    /**
     * 提供公共方法， 保证外界访问的DBManage唯一
     */
    public  DBManageUtils getDbManage(){
        return  mDbManage;
    }


    /**
     * 初始化这个SharedPreferencesHandle中的SharedPreferences对象
     * 以供其他地方调用
     */
    private void initSharedPreferences() {
        SharedPreferencesHandle.initSharedPreferencesHandle(this);
    }


}
