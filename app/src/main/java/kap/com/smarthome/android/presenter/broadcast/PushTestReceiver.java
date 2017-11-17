package kap.com.smarthome.android.presenter.broadcast;


import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19 0019.
 */

public class PushTestReceiver extends PushMessageReceiver {

    public final static String API_KEY = "cTTfXVlrMGa1SidN2h3z3hpu";

    //cTTfXVlrMGa1SidN2h3z3hpu
    /** TAG to Log */
    public static final String TAG = PushTestReceiver.class.getSimpleName();

    @Override
    public void onBind(Context arg0, int arg1, String arg2, String arg3,
                       String arg4, String arg5) {
        String content = "onBind - errorCode=" + arg1 + " appid=" + arg2 + " userId="
                + arg3 + " channelId=" + arg4 + " requestId=" + arg5;
        Log.e(TAG, content);
      }

    @Override
    public void onDelTags(Context arg0, int arg1, List<String> arg2,
                          List<String> arg3, String arg4) {
        Log.e(TAG, "onDelTags - arg1=" + arg1 + " arg2=" + arg2 + " arg3="
                + arg3 + " arg4=" + arg4);
    }

    @Override
    public void onListTags(Context arg0, int arg1, List<String> arg2,
                           String arg3) {
        Log.e(TAG, "onListTags - arg1=" + arg1 + " arg2=" + arg2 + " arg3="
                + arg3);
    }

    @Override
    public void onMessage(Context arg0, String arg1, String arg2) {
        Log.e(TAG, "onMessage - arg1=" + arg1 + " arg2=" + arg2);
    }

    @Override
    public void onNotificationArrived(Context arg0, String arg1, String arg2,
                                      String arg3) {
        String customContentString = "onNotificationArrived  - arg1=" + arg1
                + " arg2=" + arg2 + " arg3=" + arg3;
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss.SSS");
        customContentString += " arrived=" + sDateFormat.format(new Date()) ;

        Log.e(TAG, customContentString);
    }

    @Override
    public void onNotificationClicked(Context arg0, String arg1, String arg2,
                                      String arg3) {
        Log.e(TAG, "onNotificationClicked - arg1=" + arg1 + " arg2=" + arg2
                + " arg3=" + arg3);

    }

    @Override
    public void onSetTags(Context arg0, int arg1, List<String> arg2,
                          List<String> arg3, String arg4) {
        Log.e(TAG, "onSetTags - arg1=" + arg1 + " arg2=" + arg2 + " arg3="
                + arg3 + " arg4=" + arg4);
    }

    @Override
    public void onUnbind(Context arg0, int arg1, String arg2) {
        Log.e(TAG, "onUnbind - arg1=" + arg1 + " arg2=" + arg2);

    }


}
