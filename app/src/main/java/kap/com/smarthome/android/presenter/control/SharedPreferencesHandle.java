package kap.com.smarthome.android.presenter.control;

import android.content.Context;
import android.content.SharedPreferences;

import kap.com.smarthome.android.presenter.constants.AllConstants;

/**
 * Created by Administrator on 2017/9/20 0020.
 */

public class SharedPreferencesHandle {

    private static SharedPreferencesHandle mSharedPreferencesHandle;

    private SharedPreferences mSharedPreferences = null;
    private SharedPreferences.Editor  mEditor = null;

    private Context mContext;

    private SharedPreferencesHandle(Context context){
        mContext = context;
        createSharedPreferences(mContext);
    }

    private void createSharedPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(AllConstants.SP_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static synchronized SharedPreferencesHandle initSharedPreferencesHandle(Context context){
        if (mSharedPreferencesHandle == null){
            mSharedPreferencesHandle = new SharedPreferencesHandle(context);
        }
        return mSharedPreferencesHandle;
    }


    /**
     * 判断是否是安装APP之后首次打开
     */
    public  boolean  isFirstOpenApp(){
        if(mSharedPreferences != null){
         return mSharedPreferences.getBoolean(AllConstants.IS_OPEN_FIRST, true);
        }
        return true;
    }


    /**
     * 首次打开App后写入false
     * 表示已经不是第一次打开APP，不再做数据库的初始化
     */
    public  boolean  setFirstOpenAppFlag(boolean isFalse){
        if(mContext == null){
            return false;
        }
        if (mSharedPreferences == null){
            createSharedPreferences(mContext);
        }
        mEditor.putBoolean(AllConstants.IS_OPEN_FIRST , isFalse);
        return mEditor.commit();
    }


    /**
     * 在登录成功之后，将当前登录用户的user_id保存起来 ，
     * 保存的user_id 在和中继盒子和服务器的通行协议头和协议体中都会用到
     * @param userId
     * @return
     */
    public  boolean  saveCurrentLoginUserId (String userId){
        if(mContext == null){
            return false;
        }
        if (mSharedPreferences == null){
            createSharedPreferences(mContext);
        }
        mEditor.putString(AllConstants.USER_ID , userId);
        return mEditor.commit();
    }

    /**
     * 获取保存的当前登录用户的User_id
     * @return
     */
    public  String  getCurrentLoginUserId(){
        if(mSharedPreferences != null){
            return mSharedPreferences.getString(AllConstants.USER_ID, "");
        }
        return "";
    }

}
