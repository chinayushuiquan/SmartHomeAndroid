package kap.com.smarthome.android.presenter.utils;

import android.os.Environment;
import android.util.Log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class MyLogUtils {


    private static final String MTAG = "KOTITAG";

    /**
     * @param TAG  打印头
     * @param i    答应信息的等级 自己打印信息分三个等级信息 i>0 是 log.i ，i<0 log.e  i=0 是log.w
     * @param logMessage 具体的打印的log内容
     */
    public  static void LogUtils(String TAG , int i , String logMessage){
        if(AllVariable.ISDEBUG){
            if(i > 0){
            Log.i(TAG, "LogUtils: " + logMessage);
            }else if(i == 0){
                Log.w(TAG, "LogUtils: " + logMessage);
            }else{
                Log.e(TAG, "LogUtils: "+ logMessage);
            }
        }
    }

    /**
     * 统一的打印头  MTAG :  KOTITAG  方便调试
     * @param i    答应信息的等级 自己打印信息分三个等级信息 i>0 是 log.i ，i<0 log.e  i=0 是log.w
     * @param logMessage 具体的打印的log内容
     */
    public  static void LogUtils( int i , String logMessage){
        if(AllVariable.ISDEBUG_KOTITAG){
            if(i > 0){
                Log.i(MTAG, "LogUtils: " + logMessage);
            }else if(i == 0){
                Log.w(MTAG, "LogUtils: " + logMessage);
            }else{
                Log.e(MTAG, "LogUtils: "+ logMessage);
            }
        }
    }

    /**
     * 是否将log信息输出到Sdcard的Koti_Log目录下,方便读取调试
     * @param isoutFile
     * @param logMessage
     */
    public static  void LogUtils(boolean isoutFile, String logMessage){
        if(AllVariable.ISDEBUG_KOTITAG  && isoutFile){
            Loger(logMessage);
        }
    }

    public final static String DEFAULT_FILENAME = "Koti_Log";

    public static boolean Loger(String value) {
        BufferedWriter bw = null;
        value = value + " : " + getTimer() + "\n";
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    DEFAULT_FILENAME);
            //第二个参数意义是说是否以append方式添加内容
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(value);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private static String getTimer() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        return t1;
    }



}
