package kap.com.smarthome.android.data.control;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import kap.com.smarthome.android.data.dao.DaoMaster;
import kap.com.smarthome.android.data.dao.DaoSession;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class DBManageUtils {

    private  static   DBManageUtils  mDBManage = null;
    private  DaoSession mDaoSession;
    /**
     * 采用单例模式，放到Application中进行初始，保证全局唯一性
     * @param context
     */
    private DBManageUtils(Context context){
        DaoMaster.DevOpenHelper  helper = new DaoMaster.DevOpenHelper(context, DBContstants.DB_NAME);
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public  synchronized  static  DBManageUtils  getDbInstance(Context context){
        if(mDBManage == null){
            mDBManage = new DBManageUtils(context);
        }
        return mDBManage;
    }


    /**
     * 得到数据库的session
     * @return
     */
    public  DaoSession  getmDaoSession(){
        if(mDaoSession != null) {
            return mDaoSession;
        }
        return  null;
    }

}
